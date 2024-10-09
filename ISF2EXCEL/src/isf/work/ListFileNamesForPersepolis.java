/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import com.drew.imaging.*;
import com.drew.metadata.*;
import com.drew.metadata.exif.*;
import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author kameshnittala
 */
public class ListFileNamesForPersepolis {
    private static boolean isPersepolis = true;

    public static void main(String[] args)
    {
        if (args.length > 2 || args.length < 2)
        {
            System.out.println(" incorrect arguments: please provide a) the directory to enlist files from."
                    + "b) is this persepolis? ");
            System.exit(0);
        }
        else
        {
            isPersepolis = java.lang.Boolean.valueOf(args[1]);
            ArrayList<String> list = new ArrayList();
            Path base = Paths.get(args[0]);
            try
            {
                Files.walk(base).forEach(entry -> process(entry, list));
            } catch (IOException r)
            {
                r.printStackTrace();
            }
            try
            {
                WritableWorkbook workbook;
                String newDest = args[0].replaceAll("imageupload","working");
                File record = new File(newDest, "list.xls");
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));

                workbook = Workbook.createWorkbook(record, wbSettings);
                WritableSheet sheet = workbook.createSheet("List", 0);
                String[] cell = null;

                for (int i = 0;
                        i < list.size();
                        i++)
                {
                    cell = list.get(i).split("~");
                    sheet.addCell(new Label(0, i, cell[0]));
                    sheet.addCell(new Label(1, i, cell[1]));
                    sheet.addCell(new Label(2, i, cell[2]));
                    sheet.addCell(new Label(3, i, cell[3]));

                }
                workbook.write();
                workbook.close();
            } catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }

    private static void process(Path f, ArrayList list)
    {
        StringBuilder str = new StringBuilder();
        if(f.toFile().isDirectory() || f.toFile().getName().startsWith(".DS_")
           ||f.toFile().getName().endsWith(".jpg")||f.toFile().getName().endsWith(".xls") 
                ||f.toFile().getName().endsWith(".xlsx"))
            return;
        if (isPersepolis && (!f.getParent().endsWith("PTMs") 
                && !f.getParent().endsWith("ConventionalTiffs") 
                && !f.getParent().endsWith("Tiff_16") 
                && !f.toFile().getName().toLowerCase().endsWith(".tif")))
        {
            
            return;
        }    
        else
        {
            

            str.append( (f.toFile().getName())).append("~");
            str.append("" + f.toFile().length()).append("~");
            try
            {
                Metadata data = ImageMetadataReader.readMetadata(f.toFile());
                Directory dir = data.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                int dateTag = ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL;
                if (dir != null && dir.containsTag(dateTag))
                {
                    Date date = dir.getDate(dateTag, TimeZone.getDefault());
                    str.append(new SimpleDateFormat("MMM yyyy").format(date)).append("~");
                }
                else
                {
                    str.append(" ").append("~");
                }
                
            } catch (Exception exp)
            {
                exp.printStackTrace();
                str.append("JAN 1900").append("~");
              //  System.out.println("Could not process file date for :" + f.toFile().getName());
            } finally
            {
                str.append(f.toString());
                System.out.println("added:"+f.toFile().getName());
                list.add(str.toString());
               
            }

        }
    }
}
