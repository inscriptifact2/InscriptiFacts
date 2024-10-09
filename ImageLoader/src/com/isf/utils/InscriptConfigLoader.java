package com.isf.utils;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Properties;

public class InscriptConfigLoader
{

    public Properties p;
    private static InscriptConfigLoader icl;

    private InscriptConfigLoader()
    {
        p = new Properties();
        load();
    }

    public static InscriptConfigLoader getInstance()
    {
        if(icl == null)
        {
            icl = new InscriptConfigLoader();
        }
        return icl;
    }

    private void load()
    {
        try
        {
            p.load(getClass().getResourceAsStream("InscriptIni.ini"));
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            System.out.println("Ini File Not Found");
            filenotfoundexception.printStackTrace();
        }
        catch(Exception exception)
        {
            System.out.println("*************************");
            System.out.println("InscriptConfigLoader : Failed");
            exception.printStackTrace();
            System.out.println("*************************");
        }
    }

    public static void main(String args[])
    {
        System.out.println(getInstance().p);
    }

    public String getProperty(String str)
    {
        return p.getProperty(str);
    }
}
