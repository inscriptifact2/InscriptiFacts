package com.isf.loader;

import com.isf.utils.ISFConnection;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class UpdateSidSize
{

    String userDir;
    Properties prop;
    File files[];
    int filesLength;
    File f;
    FileOutputStream fos;
    PrintWriter pw;

    public UpdateSidSize(File file)
        throws Exception
    {
        userDir = System.getProperty("user.dir");
        prop = new Properties();
        System.out.println((new StringBuilder()).append("Updating imagesizes from tiff files in ---- ").append(file).append("\n").toString());
        boolean flag = file.isDirectory();
        if(flag)
        {
            userDir = file.getAbsolutePath();
            userDir = userDir.replace('\\', '/');
            files = file.listFiles();
            filesLength = files.length;
            f = new File((new StringBuilder()).append(userDir).append("/").append("ErrorLogFile.txt").toString());
            fos = new FileOutputStream(f);
            pw = new PrintWriter(fos);
            loadFileAndProcess();
        } else
        {
            System.out.println((new StringBuilder()).append(file).append(" is not a directory").append("\n").toString());
        }
    }

    private void loadFileAndProcess()
        throws Exception
    {
        System.out.println("Started Updating .........\n");
        Connection connection = ISFConnection.getConnection();
        Statement statement = connection.createStatement(1005, 1008);
        ResultSet rs = statement.executeQuery("select  Photographidentificationno,archivalfilesize from photoobject ");
        int i = 0;
        boolean flag = rs.next();
        do
        {
            if(!flag)
            {
                break;
            }
            String pin = rs.getString("Photographidentificationno");
            long size = 0L;
            File f = new File((new StringBuilder()).append(userDir).append("/").append(pin.trim()).append(".tif").toString());
            System.out.println(f.getAbsolutePath());
            if(f.exists())
            {
                size = f.length();
                rs.updateString("archivalfilesize", (new StringBuilder()).append("").append(size).toString());
                rs.updateRow();
            } else
            {
                fos.write((new StringBuilder()).append(pin.trim()).append(".tif file not found.\n").toString().getBytes());
            }
            if(++i > 4)
            {
                flag = false;
            } else
            {
                flag = rs.next();
            }
            if(i % 20 == 0)
            {
                System.out.println((new StringBuilder()).append("Finished Updating ").append(i).append(" Records\n").toString());
            }
        } while(true);
        System.out.println("Finished Updating\n");
        fos.close();
        pw.close();
        connection.commit();
        rs.close();
        statement.close();
        connection.close();
    }

    public static void main(String args[])
    {
        try
        {
            String s = "";
            String s1 = System.getProperty("user.dir");
            if(args.length == 0)
            {
                s = s1;
            } else
            {
                s = args[0];
            }
            UpdateSidSize sidUpdates = new UpdateSidSize(new File(s));
            System.out.println("Uploading finished");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
