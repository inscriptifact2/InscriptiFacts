/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;
import java.util.*;
import javax.print.*;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author kameshnittala
 */
public class CopyPersepolisImages extends ProcessImages {

    public CopyPersepolisImages()
    {
    }

    public static void main(String args[])
    {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        Workbook sourceList = null;
        Workbook textPhotoList = null;
        StringBuilder missingScans = new StringBuilder();
        StringBuilder missingThumbs = new StringBuilder();
        if (args.length < 4)
        {
            System.out.println("incomplete arguments : please indicate "
                    + "a) path to the list of records"
                    + " b) directory to save images to records &  "
                    + "c)  place from thumbnail need to be copied"
                    + " and d) position where photorecords start"
            );
        }
        else

        {
            HashMap<String, String> fileList = new HashMap();

            try
            {
                sourceList = Workbook.getWorkbook(new File(args[0]));
                Sheet sheet = sourceList.getSheet(0);
                int totalRows = sheet.getRows();
                for (int i = 0; i < totalRows; i++)
                {
                    fileList.put(sheet.getCell(0, i).getContents().trim(), sheet.getCell(3, i).getContents().trim());
                }
            } catch (Exception exp)
            {
                exp.printStackTrace();
            } finally
            {
                sourceList.close();
            }

            File workingDir = new File(args[1]);
            String text = "", key = "", thumb = "";
            File src = null, dest = null;
            Sheet sheet = null;
            int rows = 0;
            int count = 0;
            System.out.println("Dirs to process:"+workingDir.listFiles().length);
            for (File f : workingDir.listFiles())
            {
                 count++;
                if (!f.isDirectory())
                {
                    continue;
                }
                System.out.println("Processing " +count+ " :"+ f.getName());
                try
                {
                    text = f.getName().trim();
                    textPhotoList = Workbook.getWorkbook(new File(f, text + ".xls"));
                    sheet = textPhotoList.getSheet(0);
                    rows = sheet.getRows();
                    int i = Integer.parseInt(args[3]);
                    for (int c = i; c < rows; c++)
                    {
                        key = sheet.getCell(25, c).getContents().trim();
                        System.out.println("Key:"+key);
                        src = new File(fileList.get(key));
                        if (src.exists())
                        {
                            copyFileUsingChannel(src, new File((new StringBuilder()).append(f).append(File.separatorChar).append("Scans").append(File.separatorChar).append(key).toString()));
                        }
                        else
                        {
                            missingScans.append(text).append(":").append(key).append("\n");
                        }
                        if (key.toLowerCase().endsWith(".tif"))
                        {
                            continue;
                        }
                        else
                        {
                            thumb = (new StringBuilder(key.substring(0, key.lastIndexOf("."))).append(".jpg")).toString();
                            src = new File(args[2], thumb);
                            if (key.toLowerCase().endsWith("tif"))
                            {
                                continue;
                            }
                            else if (src.exists())
                            {
                                copyFileUsingChannel(src, new File((new StringBuilder()).append(f).append(File.separatorChar).append("Thumbnails").append(File.separatorChar).append(thumb).toString()));
                            }
                            else
                            {
                                missingThumbs.append(text).append(":").append(thumb).append("\n");
                            }
                        }
                    }
                } catch (Exception exp)
                {
                    exp.printStackTrace();
                } finally
                {
                    textPhotoList.close();
                }
            }
            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(new File(workingDir, "MissingImages.txt"));
                fos.write(missingScans.toString().getBytes());
                fos.write("\n".getBytes());
                fos.write(missingThumbs.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            } 

        }
    }

}
