package isf.common.utils;

//import com.isf.common.Debug;

import isf.common.utils.LogManager;
import java.io.FileNotFoundException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class InscriptConfigLoader {

    /**
     * @return the appName
     */
    public static String getAppName() {
        return appName;
    }

    /**
     * @param aAppName the appName to set
     */
    public static void setAppName(String aAppName) {
        appName = aAppName;
    }

    public Properties p;
    private static String appName="isf";
    private static InscriptConfigLoader icl;
    Logger log = LogManager.getLogger(isf.common.utils.InscriptConfigLoader.class);

    private InscriptConfigLoader(String fname) {
        p = new Properties();
        load(fname);
    }

    public static InscriptConfigLoader initialize(String fname) {
        if (icl == null) {
            icl = new InscriptConfigLoader(fname);
        }
        return icl;
    }

    public static InscriptConfigLoader getInstance() {
        return icl;
    }

    private void load(String fname) {
        try {
            //Debug.debug(getClass().getResource("InscriptIni.ini"));
            p.load(getClass().getResourceAsStream(fname));
        } catch (FileNotFoundException filenotfoundexception) {
            //Debug.debug("Ini File Not Found");
            log.error(filenotfoundexception);
        } catch (Exception exception) {
            // Debug.debug("*************************");
            // Debug.debug("InscriptConfigLoader : Failed");
            log.error(exception);
            //  Debug.debug("*************************");
        }
    }

   

    public String getProperty(String str) {
        return p.getProperty(str);
    }
   public void setProperty(String str,String value) {
        p.setProperty(str,value);
    }
}
