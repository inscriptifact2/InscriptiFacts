/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.common.utils;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Dec 19, 2004
 * Time: 12:00:27 PM
 * To change this template use Options | File Templates.
 */

/*
 *	Imports
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 * Wraps the org.apache.log4j.Logger class. Provides an initialization point
 * for the Log4J system.
 *
 * @author Kamesh
 * @version 1.0
 */
public class LogManager {

    /**
     * Initialize the Log4J system.
     *
     * @param configurationFileName Configuration file name
     */
  /*  public static void initialize(String configurationFileName) {
        PropertyConfigurator.configure(Loader.getResource(configurationFileName));
    }*/
    static{
        Properties p = new Properties();
        
        String appName = "isf" ;
        if(InscriptConfigLoader.getAppName()!=null)
            appName=InscriptConfigLoader.getAppName().toLowerCase();
        System.out.println("appName:"+appName);
        try{
        p.load(isf.common.utils.LogManager.class.getResourceAsStream("log4j.ini"));
        p.setProperty("log4j.appender.file.File", CacheManagerConstants.logFolderName+File.separator+appName+".log");
      //  System.out.println("Folder:"+p.getProperty("log4j.appender.file.File"));
        }catch(IOException ioe){
          ioe.printStackTrace();
        }
        PropertyConfigurator.configure(p);
     }

    /**
     * Get a logger.
     *
     * @param loggerName String
     * @return Logger
     */
    public static Logger getLogger(String loggerName) {
        return Logger.getLogger(loggerName);
    }

    /**
     * Get a logger.
     *
     * @param clazz String
     * @return Logger
     */
    public static Logger getLogger(Class clazz) {
        return Logger.getLogger(clazz);
    }

}	/*  End of the LogManager class. */

