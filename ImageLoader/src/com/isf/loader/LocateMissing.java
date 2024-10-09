package com.isf.loader;

import com.isf.utils.ISFConnection;
import java.io.*;
import java.sql.*;
import java.util.Hashtable;

public class LocateMissing
{

    private Hashtable ht;
    String userDir;
    int count;

    public LocateMissing(String image, String filter, String dir)
        throws Exception
    {
        userDir = System.getProperty("user.dir");
        count = 0;
        Connection connection = ISFConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "select trim(photographidentificationno) from photoobject order by photographiden" +
"tificationno "
;
        File srcDir = new File(dir);
        File logFile = new File((new StringBuilder()).append(userDir).append("/").append(image).append("_nofile.dat").toString());
        FileOutputStream fileoutputstream = new FileOutputStream(logFile);
        ResultSet resultset = statement.executeQuery(query);
        do
        {
            if(!resultset.next())
            {
                break;
            }
            String id = resultset.getString(1);
            File fnt = new File(srcDir, (new StringBuilder()).append(id).append(filter).toString());
            if(!fnt.exists())
            {
                fileoutputstream.write((new StringBuilder()).append(id).append("\n").toString().getBytes());
            }
        } while(true);
        fileoutputstream.flush();
        fileoutputstream.close();
        resultset.close();
        statement.close();
        connection.close();
    }

    public static void main(String args[])
    {
        if(args.length < 2)
        {
            System.out.println("Syntax: LocateMissing <field> <filter-pattern> <src-dir>\n eg. LocateMissing IMA" +
"GESID c.sid /var/lizardtech/images/V5022SID"
);
            return;
        }
        try
        {
            LocateMissing pinfilecreator = new LocateMissing(args[0], args[1], args[2]);
            System.out.println("Finished");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
