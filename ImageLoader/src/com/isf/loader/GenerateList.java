package com.isf.loader;

import com.isf.utils.ISFConnection;
import java.io.*;
import java.sql.*;
import java.util.Hashtable;

public class GenerateList
{

    private Hashtable ht;
    String userDir;
    int count;

    public GenerateList(String image, String filter)
        throws Exception
    {
        ht = new Hashtable();
        userDir = System.getProperty("user.dir");
        count = 0;
        Connection connection = ISFConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = (new StringBuilder()).append("select photographidentificationno,isfdigitalobjectidentifier,DIGITALOBJECTFORMAT from photoobject wh" +
"ere "
).append(image).append(" is null or dbms_lob.getlength(").append(image).append(") = 0").toString();
        File logFile = new File((new StringBuilder()).append(userDir).append("/").append(image).append(".dat").toString());
        FileOutputStream fileoutputstream = new FileOutputStream(logFile);
        ResultSet resultset;
        for(resultset = statement.executeQuery(query); resultset.next();)
        {
            String s1 = resultset.getString(1);
            String ptm = resultset.getString(2);
            String format = resultset.getString(3)==null?"":resultset.getString(3);
            String tfilter = "";
            if(format.indexOf("ptm")>-1 )
            {
                tfilter = (new StringBuilder()).append("_ptm_").append(filter).toString();
            } 
            else if(format.indexOf("hsh")>-1 ||format.indexOf("rti")>-1 )
            {
                tfilter = (new StringBuilder()).append("_rti_").append(filter).toString();
            }else
            {
                tfilter = filter;
            }
            String s2 = s1.trim();
            String s3 = (new StringBuilder()).append("PIN").append(count).append("=").append(s2).append("\n").toString();
            String s5 = (new StringBuilder()).append(image).append(count).append("=").append(s2).append(tfilter).append("\n").toString();
            String s8 = "----------------------------------------------------------------------\n";
            fileoutputstream.write((new StringBuilder()).append(s3).append(s5).append(s8).toString().getBytes());
            count++;
        }

        fileoutputstream.write((new StringBuilder()).append("count=").append(count).append("\n").toString().getBytes());
        fileoutputstream.close();
        resultset.close();
        statement.close();
        connection.close();
    }

    public int getCount()
    {
        return count;
    }

    public static void main(String args[])
    {
        if(args.length < 2)
        {
            System.out.println("Syntax: GenerateList <field> <filter-pattern>\n eg. GenerateList IMAGESID c.sid");
            return;
        }
        try
        {
            GenerateList pinfilecreator = new GenerateList(args[0], args[1]);
            System.out.println("Finished");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
