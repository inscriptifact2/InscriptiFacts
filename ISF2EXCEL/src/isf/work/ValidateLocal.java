/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;
import java.util.*;
import jxl.*;

/**
 *
 * @author kameshnittala
 */
public class ValidateLocal {
    
        public static void main(String[] args)
    {
        if (args.length < 1 || args.length > 1)
        {
            System.out.println(" Please pass the Folder for completed records");

            System.exit(0);
        }
        else
        {
            
                    WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        Workbook sourceList = null;
            String compDir = args[0];

            File sourceDir = new File(compDir);
            String text = "";

            
            for (File textDir : sourceDir.listFiles())
            {
                if (textDir.isDirectory() && textDir.getName().startsWith("ISF_TXT"))
                {
                    //System.out.println(textDir.getName());
                    
                    try{
                    sourceList = Workbook.getWorkbook(new File(textDir,textDir.getName()+".xls"));
                Sheet sheet = sourceList.getSheet(0);
                int totalRows = sheet.getRows();
                
                     System.out.println(" Records in file "+textDir.getName()+":: "+(totalRows-1));
                    }catch(Exception exp){
                        exp.getStackTrace();
                    }
                    finally{if(sourceList != null)
                        sourceList.close();
                    }

                }
            }

        }
    }
    
    
}
