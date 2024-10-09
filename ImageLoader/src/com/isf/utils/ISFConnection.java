package com.isf.utils;

import java.sql.Connection;
import java.sql.DriverManager;

// Referenced classes of package com.isf.utils:
//            InscriptConfigLoader

public class ISFConnection
{

    static String url = InscriptConfigLoader.getInstance().getProperty("driverURL");
    static String user = InscriptConfigLoader.getInstance().getProperty("user");
    static String pwd = InscriptConfigLoader.getInstance().getProperty("password");

    public ISFConnection()
    {
    }

    public static Connection getConnection()
    {
        Connection con = null;
        try
        {
            con = DriverManager.getConnection(url, user, pwd);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return con;
    }

    static 
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
    }
}
