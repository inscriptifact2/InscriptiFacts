/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;
import java.nio.file.*;

/**
 *
 * @author kameshnittala
 */
public class RenameThumbs {

    public static void main(String[] args)
    {
        String name = "";
        File dir = new File(args[0]);
        for (File f : dir.listFiles())
        {
            if (f.isFile())
            {
                // File name = new File(f,"Thumbails");
                //  thumb.renameTo(new File(f,"Thumbnails"));
                name = f.getName();
                if (name.indexOf(args[1]) > -1)
                {
                    name = name.replaceAll(args[1], args[2]);
                    Path oldFile
                            = Paths.get(f.getPath());

                    try
                    {
                        Files.move(oldFile, oldFile.resolveSibling(
                                name));

                    } catch (IOException e)
                    {
                        System.out.println("operation failed:" + name);
                    }

                }

            }

        }
    }

}
