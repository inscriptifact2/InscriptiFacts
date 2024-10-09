/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;

/**
 *
 * @author kameshnittala
 */
public class ValidateTransfer {

    public static void main(String[] args)
    {
        if (args.length < 1 || args.length > 1)
        {
            System.out.println(" Please pass the Folder for completed records");

            System.exit(0);
        }
        else
        {
            String compDir = args[0];
            int scansMoved = 0;
            int jpegsMoved = 0;
            File completedFolder = new File(compDir);
            String text = "";
            int s,t=0;
            
            for (File textDir : completedFolder.listFiles())
            {
                if (textDir.isDirectory() && textDir.getName().startsWith("ISF_TXT"))
                {
                    s = (new File(textDir, "Scans")).listFiles().length;
                    t = (new File(textDir, "Thumbnails")).listFiles().length;
                    System.out.println(textDir.getName()+":scans="+s+"::thumbnails="+t);
                    scansMoved += s;
                    jpegsMoved += t;
                }
            }
            System.out.println("Total Scans="+scansMoved);
            System.err.println("Total Thumbs="+jpegsMoved);
        }
    }

    }
