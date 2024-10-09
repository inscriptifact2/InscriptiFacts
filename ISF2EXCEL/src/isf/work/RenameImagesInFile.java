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
public class RenameImagesInFile {

    public static void main(String[] args)
    {
        if (args.length < 2 || args.length > 2)
        {
            System.out.println("Incorrect number of parameters. please pass two parameters only:"
                    + "A) list of image records and  "
                    + "B) file to read name changes from");
            System.exit(0);
        }
        else
        {

            String name = "";
            Workbook source = null;
            Sheet sheet = null;
            try
            {
                HashMap<String, String> group = new HashMap<String, String>();
                source = Workbook.getWorkbook(new File(args[0]));
                sheet = source.getSheet(0);
                int totalRows = sheet.getRows();

                String fname = "",destFname="",srcFname="",srcPath="";

                for (int i = 0; i < totalRows; i++)
                {
                    fname = sheet.getCell(0, i).getContents().trim();
                    group.put(fname, sheet.getCell(3, i).getContents().trim());
                }
                source.close();
                
                
                source = Workbook.getWorkbook(new File(args[1]));
                sheet = source.getSheet(0);
                totalRows = sheet.getRows();  
                for( int j =1; j<totalRows; j++){
                    destFname = sheet.getCell(1,j).getContents().trim();
                    srcFname = sheet.getCell(2,j).getContents().trim();
                    srcPath = group.get(srcFname);
                    //destPath = srcPath.replaceAll(srcFname,destFname);
                    if(srcPath ==null){
                        System.out.println("empty Path :"+srcFname);
                    }else{
                    
                    File f = new File(srcPath);
                    if(f.exists()){
                     Path oldFile
                                = Paths.get(f.getPath());  
                     Files.move(oldFile, oldFile.resolveSibling(
                                    destFname));
                        System.out.println("Renamed File:"+srcFname+" to:"+destFname);
                     
                    }else{
                        System.out.println("File not found:"+srcPath);
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
