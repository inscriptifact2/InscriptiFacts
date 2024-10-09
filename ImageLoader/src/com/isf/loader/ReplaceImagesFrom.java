package com.isf.loader;

import com.isf.utils.ISFConnection;
import java.io.*;
import java.sql.*;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

public class ReplaceImagesFrom
{

    String sourceDirectory;
    String imageType;
    String extension;

    public ReplaceImagesFrom(String image, String sourceDir)
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
                writeToDB((new StringBuilder()).append(sourceDirectory).append("/").append(fileName).toString(), photoId);
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }

    }

    private void writeToDB(String fileName, String photoid)
        throws Exception
    {
        Connection connection = ISFConnection.getConnection();
        PreparedStatement preparedstatement = connection.prepareStatement((new StringBuilder()).append("update photoobject set ").append(imageType).append(" =empty_blob() where Photographidentificationno='").append(photoid).append("'").toString());
        preparedstatement.executeUpdate();
        Statement statement = connection.createStatement();
        String query = (new StringBuilder()).append("select ").append(imageType).append(" from photoobject where Photographidentificationno='").append(photoid).append("' for update").toString();
        FileOutputStream oos = new FileOutputStream((new StringBuilder()).append(imageType).append(".txt").toString(), true);
        ResultSet resultset = statement.executeQuery(query);
        if(resultset.next())
        {
            BLOB blob = ((OracleResultSet)resultset).getBLOB(1);
            OutputStream outputstream = blob.getBinaryOutputStream();
            FileInputStream fileinputstream = new FileInputStream(fileName);
            byte abyte0[] = new byte[10240];
            for(int i = 0; (i = fileinputstream.read(abyte0)) != -1;)
            {
                outputstream.write(abyte0, 0, i);
            }

            outputstream.close();
            fileinputstream.close();
        } else
        {
            oos.write((new StringBuilder()).append(photoid).append(" Record Not Found \n").toString().getBytes());
            oos.flush();
        }
        connection.commit();
        resultset.close();
        statement.close();
        connection.close();
        oos.close();
        Thread.sleep(100L);
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
            System.out.println("SYNTAX: ReplaceImagesFrom <field> <sourceDirectory>\n eg. ReplaceImagesFrom Imag" +
"eSid /var/isfimages/SIDs"
);
            return;
        }
        ReplaceImagesFrom rif;
        try
        {
            rif = new ReplaceImagesFrom(args[0], args[1]);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
}
