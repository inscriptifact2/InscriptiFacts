/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import jxl.*;

/**
 *
 * @author kameshnittala
 */
public class RenameFiles {

    public static void main(String[] args)
    {
        if (args.length < 1 || args.length > 1)
        {
            System.out.println("Incorrect number of parameters. please pass only one parameters only:"
                    + " List of files to Process  ");
            System.exit(0);
        }
        else
        {
            try
            {
                String name = "", oldname = "";
                Workbook source = null;
                Sheet sheet = null;
                source = Workbook.getWorkbook(new File(args[0]));
                sheet = source.getSheet(0);
                String fName = "";
                String oldFile = "", newFile = "";
                int totalRows = sheet.getRows();
                for (int i = 0; i < totalRows; i++)
                {
                    newFile="";
                    fName = sheet.getCell(0, i).getContents().trim();
                    if (fName.indexOf(" 2.") > -1  || fName.indexOf("EB .tif")>-1)
                    {
                        oldFile = sheet.getCell(3, i).getContents().trim();
                        if( oldFile.indexOf(" 2.") > -1 )
                        newFile = oldFile.replaceAll(" 2.tif", "_2.tif");
                        else if( oldFile.indexOf("EB .tif") > -1 )
                         newFile = oldFile.replaceAll("EB .tif", "EB.tif");

                        File f = new File(oldFile);
                        if (f.exists() && !newFile.equalsIgnoreCase(""))
                        {
                            Path oldFilepath
                                    = Paths.get(f.getPath());
                            Files.move(oldFilepath, oldFilepath.resolveSibling(
                                    newFile));
                            System.out.println("Renamed File:" + oldFile + " to:" + newFile);

                        }
                        else
                        {
                            System.out.println("File not found:" + oldFile);
                        }

                    }

                }
            } catch (Exception exp)
            {
                exp.printStackTrace();
            }

        }
    }

}
