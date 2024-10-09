package com.isf.loader;

import com.isf.utils.ISFConnection;
import java.io.*;
import java.sql.*;

public class Differences
{

    String sourceDirectory;
    String imageType;
    String extension;

    public Differences(String image, String sourceDir)
    {
        sourceDirectory = sourceDir;
        imageType = image;
        extension = getExtension();
        File file = new File(sourceDirectory);
        boolean flag = file.isDirectory();
        File files[] = null;
        int filesLength = 0;
        if(flag)
        {
            files = file.listFiles();
            filesLength = files.length;
        }
        for(int i = 0; i < filesLength; i++)
        {
            if(!files[i].getName().endsWith(extension))
            {
                continue;
            }
            String fileName = files[i].getName();
            String photoId = fileName.substring(0, fileName.indexOf(".") - 1);
            System.out.println(photoId);
            try
            {
                checkDB((new StringBuilder()).append(sourceDirectory).append("/").append(fileName).toString(), photoId);
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }

    }

    private void checkDB(String fileName, String photoid)
        throws Exception
    {
        Connection connection = ISFConnection.getConnection();
        String query = (new StringBuilder()).append("select * from photoobject where Photographidentificationno='").append(photoid).append("'").toString();
        Statement statement = connection.createStatement();
        FileOutputStream oos = new FileOutputStream((new StringBuilder()).append(imageType).append("_difference.txt").toString(), true);
        ResultSet resultset = statement.executeQuery(query);
        if(!resultset.next())
        {
            oos.write((new StringBuilder()).append(photoid).append(" Record Not Found \n").toString().getBytes());
            oos.flush();
        }
        resultset.close();
        statement.close();
        connection.close();
        oos.close();
    }

    public String getExtension()
    {
        if(imageType.equalsIgnoreCase("IMAGESID"))
        {
            return "c.sid";
        }
        if(imageType.equalsIgnoreCase("IMAGEQUICKLOOK"))
        {
            return "q.jpg";
        }
        if(imageType.equalsIgnoreCase("IMAGETHUMBNAIL"))
        {
            return "t.jpg";
        }
        if(imageType.equalsIgnoreCase("INDEXMAPIMAGE"))
        {
            return "i.jpg";
        } else
        {
            return ".txt";
        }
    }

    public static void main(String args[])
    {
        if(args.length < 2)
        {
            System.out.println("SYNTAX: Differences <field> <sourceDirectory>\n eg. Differences ImageSid /var/is" +
"fimages/SIDs"
);
            return;
        }
        Differences rif;
        try
        {
            rif = new Differences(args[0], args[1]);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
}
