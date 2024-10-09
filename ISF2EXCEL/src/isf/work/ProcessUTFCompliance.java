/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import jxl.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 *
 * @author kameshnittala
 */
public class ProcessUTFCompliance {

    public static void main(String[] args)
    {
        if (args.length < 1 || args.length > 1)
        {
            System.out.println(" Please pass the folder from where to read the "
                    + "files to convert to UTF \n");

            System.exit(0);
        }
        else
        {

            String compDir = args[0];
            File completedFolder = new File(compDir);
            for (File dir : completedFolder.listFiles())
            {
                if (!dir.isDirectory())
                {
                    continue;
                }
                else
                {
                    try
                    {
                        String text = dir.getName();

                        File f = new File(compDir + File.separatorChar + text, text + ".txt");
                        BufferedReader br = new BufferedReader(new FileReader(compDir + File.separatorChar + text + File.separatorChar + text + ".txt"));
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();
                        while (line != null)
                        {
                            sb.append(new String(line.getBytes(),"UTF-8")).append("\n");
                            line = br.readLine();
                        }

                       /* if (f.exists())
                        {
                            f.delete();
                        }*/

                        Path path = Paths.get(compDir + File.separatorChar + text, text + "new.txt");
                        //Creating a BufferedWriter object
                        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
                        //Appending the UTF-8 String to the file
                        writer.append(sb.toString());
                        //Flushing data to the file
                        writer.flush();

                    } catch (Exception exp)
                    {
                        exp.printStackTrace();
                    }

                }
            }
        }
    }

}
