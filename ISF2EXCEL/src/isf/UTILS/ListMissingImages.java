/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.UTILS;

import java.io.*;
import java.util.*;
import javax.print.*;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author kameshnittala
 */
public class ListMissingImages {
    public static void main(String[] args)
    {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        Workbook sourceList = null;
        Workbook textPhotoList = null;
        if (args.length <1 || args.length > 1)
        {
            System.out.println("Please pass the directory to verify the missing images");
            System.exit(0);
        }
        String[] strDirs = args[0].split(",");
        String curDir, txt;
        File scanDir, thumbDir,dir,img, thumb;
        int fCol =0 ;
        String imgName="";
        for(int i = 0 ; i < strDirs.length; i++){
            curDir =strDirs[i];
            System.out.println(curDir);
             dir= new File(curDir);
            for(File f: dir.listFiles()){
                if(f.isDirectory()){
                   txt = f.getName();
                    System.out.println(txt+"-");
                   
            try
            {
                sourceList = Workbook.getWorkbook(new File(f,txt+".xls"));
                Sheet sheet = sourceList.getSheet(0);
                int totalRows = sheet.getRows()-1;
                
                for(int c =0; c< sheet.getColumns(); c++){
                    if(sheet.getCell(c,0).getContents().trim().equalsIgnoreCase("FileName")){
                        fCol = c;
                        break;
                        
                    }
                }
                scanDir = new File(f,"Scans");
                thumbDir = new File(f,"Thumbnails");
                int filesFound =  scanDir.listFiles().length;
                if(totalRows>filesFound){
                    //System.out.println("Missing "+(totalRows-filesFound)+" files in "+ txt);
                    for(int row =1; row < sheet.getRows(); row++){
                        imgName = sheet.getCell(fCol,row).getContents().trim();
                        img = new File(scanDir,imgName) ;
                        if(!img.exists())
                        {
                            System.out.println("    "+ (scanDir.getName() +File.separator+imgName));
                        }
                        if(!imgName.endsWith(".tif")){
                            String t = imgName.substring(0,imgName.indexOf("."))+".jpg";
                           
                          thumb = new File(thumbDir,t)  ;
                          if(!thumb.exists()){
                             System.out.println("    "+ (thumbDir.getName() +File.separator+t)); 
                          }
                        }
                    }
                    
                }
                
            } catch (Exception exp)
            {
                System.out.println(txt+"::"+exp.getMessage());
                //exp.printStackTrace();
            } finally
            {
                sourceList.close();
            }
                
            }
            
            
        }
    }
    
}
}
