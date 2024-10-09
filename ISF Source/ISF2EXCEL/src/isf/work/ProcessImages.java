package isf.work;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Locale;
import jxl.*;
import jxl.write.*;

public class ProcessImages
{

    public ProcessImages()
    {
    }

    public static void main(String args[])
    {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = null;
        Workbook wb = null;
        StringBuilder missingScans = new StringBuilder();
        StringBuilder missingThumbs = new StringBuilder();
        if(args.length < 5)
        {
            System.out.println("incomplete arguments : please indicate the following:"
                    + "a) working directory from where to pick text records, "
                    + "b) directory to save excel records,  "
                    + "c)  the location of images to copy from  "
                    + "d)  place from where the thumbnails need to be copied and "
                    + "e) Boolean isOrganized by ISFTXT"
                    
);
        } else
        {
            String dirList = args[0];
            String textDir = args[1];
            String imgDir = args[2];
            String thmbDir = args[3];
            boolean isOrganized = java.lang.Boolean.getBoolean(args[4]);
            File sourceDir = new File(dirList);
            File imageDir = new File(imgDir);
            File thumbs = new File(thmbDir);
            File imagesFrom = null;
            boolean found = false;
            String photo = "";
            String recName = "";
            int count = 1;
            int from = sourceDir.listFiles().length;
            File afile[] = sourceDir.listFiles();
            int i = afile.length;
            for(int j = 0; j < i; j++)
            {
                File fDir = afile[j];
                System.out.println((new StringBuilder()).append("Processing ").append(count++).append(" text records of ").append(from).toString());
                if(!fDir.isDirectory() || !fDir.getName().startsWith("ISF_TXT"))
                {
                    continue;
                }
                recName = fDir.getName();
                missingScans.append((new StringBuilder()).append(recName).append(":::").toString()).append("\n");
                missingThumbs.append((new StringBuilder()).append(recName).append(":::").toString()).append("\n");
                File scanDir = new File((new StringBuilder()).append(dirList).append(File.separatorChar).append(recName).append(File.separatorChar).append("Scans").toString());
                if(!scanDir.exists())
                {
                    scanDir.mkdir();
                }
                File thumbDir = new File((new StringBuilder()).append(dirList).append(File.separatorChar).append(recName).append(File.separatorChar).append("Thumbnails").toString());
                if(!thumbDir.exists())
                {
                    thumbDir.mkdir();
                }
                String args1[] = imageDir.list();
                
                int k = args1.length;
                int i1 = 0;
                do
                {
                    if(i1 >= k)
                    {
                        break;
                    }
                    String dir = args1[i1];
                    if(dir.startsWith(recName))
                    {
                        found = true;
                        imagesFrom = new File(imageDir, dir);
                        break;
                    }else if(!isOrganized){
                        found = true;
                        imagesFrom = imageDir;
                        break;
                    }
                    i1++;
                } while(true);
                if(!found)
                {
                    continue;
                }
                try
                {
                    File srcXLS = new File((new StringBuilder()).append(dirList).append(File.separatorChar).append(recName).append(File.separatorChar).append(recName).append(".xls").toString());
                    wb = Workbook.getWorkbook(srcXLS);
                    int pos = 1;
                    Sheet sheet = wb.getSheet(0);
                    if(sheet.getCell(0, 1).getContents().startsWith("[Text Descriptive Title:]"))
                    {
                        pos = 2;
                    }
                    int totalRows = sheet.getRows();
                    ArrayList fileNames = new ArrayList();
                    for(int row = pos; row < totalRows; row++)
                    {
                        photo = sheet.getCell(25, row).getContents().trim();
                        found = false;
                        if(photo.indexOf(".") < 0)
                        {
                            File afile1[] = imagesFrom.listFiles();
                            int j1 = afile1.length;
                            int k1 = 0;
                            do
                            {
                                if(k1 >= j1)
                                {
                                    break;
                                }
                                File src = afile1[k1];
                                if(src.getName().indexOf(photo) > -1)
                                {
                                    copyFileUsingChannel(src, new File((new StringBuilder()).append(dirList).append(File.separatorChar).append(recName).append(File.separatorChar).append("Scans").append(File.separatorChar).append(src.getName()).toString()));
                                    fileNames.add(src.getName());
                                    found = true;
                                    break;
                                }
                                k1++;
                            } while(true);
                        } else
                        {
                            File src = new File(imagesFrom, photo);
                            if(src.exists())
                            {
                                copyFileUsingChannel(src, new File((new StringBuilder()).append(dirList).append(File.separatorChar).append(recName).append(File.separatorChar).append("Scans").append(File.separatorChar).append(photo).toString()));
                                found = true;
                                fileNames.add(photo);
                            }
                        }
                        if(!found)
                        {
                            missingScans.append("\t").append(photo).append("\n");
                            fileNames.add(photo);
                        }
                        File tjpg = null;
                        if(photo.indexOf(".") < 0)
                        {
                            tjpg = new File(thumbs, (new StringBuilder()).append(photo).append(".jpg").toString());
                        } else
                        {
                            String tname = (new StringBuilder()).append(photo.substring(0, photo.length() - 3)).append("jpg").toString();
                            tjpg = new File(thumbs, tname);
                        }
                        if(tjpg.exists())
                        {
                            copyFileUsingChannel(tjpg, new File((new StringBuilder()).append(dirList).append(File.separatorChar).append(recName).append(File.separatorChar).append("Thumbnails").append(File.separatorChar).append(tjpg.getName()).toString()));
                            continue;
                        }
                        if(!photo.toLowerCase().endsWith(".tif"))
                        {
                            missingThumbs.append("\t").append(tjpg.getName()).append("\n");
                        }
                    }

                    workbook = Workbook.createWorkbook(srcXLS, wb);
                    for(int p = 0; p < fileNames.size(); p++)
                    {
                        String s = (String)fileNames.get(p);
                        WritableCell cell = workbook.getSheet(0).getWritableCell(25, p + pos);
                        if(cell.getType() == CellType.LABEL)
                        {
                            Label l = (Label)cell;
                            l.setString(s);
                        }
                    }

                    workbook.write();
                    workbook.close();
                    wb.close();
                }
                catch(Exception exp)
                {
                    exp.printStackTrace();
                }
            }

            try
            {
                FileOutputStream fos = new FileOutputStream(new File(dirList, "MissingImages.txt"));
                fos.write(missingScans.toString().getBytes());
                fos.write("\n".getBytes());
                fos.write(missingThumbs.toString().getBytes());
                fos.flush();
                fos.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    protected static void copyFileUsingChannel(File source, File dest)
        throws IOException
    {
        FileChannel sourceChannel;
        FileChannel destChannel;
        if(!dest.exists())
        {
           
     
        sourceChannel = null;
        destChannel = null;
        sourceChannel = (new FileInputStream(source)).getChannel();
        destChannel = (new FileOutputStream(dest)).getChannel();
        destChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
        sourceChannel.close();
        destChannel.close();

        sourceChannel.close();
        destChannel.close();
        }
        
    }
}
