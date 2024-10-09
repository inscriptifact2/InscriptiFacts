package isf.sc.utils;


import isf.common.utils.LogManager;
import java.io.FileNotFoundException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class InscriptConfigServerLoader {

    private Properties p;
    private static InscriptConfigServerLoader icl;
    Logger log = LogManager.getLogger(isf.sc.utils.InscriptConfigServerLoader.class);
    private InscriptConfigServerLoader() {
        p = new Properties();
        load();
    }

    public static InscriptConfigServerLoader getInstance() {
        if (icl == null) {
            icl = new InscriptConfigServerLoader();
        }
        return icl;
    }

    private void load() {
        try {
            p.load(getClass().getResourceAsStream("InscriptEjb.ini"));
        } catch (FileNotFoundException filenotfoundexception) {
            log.error(filenotfoundexception);
        } catch (Exception exception) {
            log.error(exception);
        }
    }

    public String getProperty(String s) {
        return p.getProperty(s);
    }

    public Properties getMailProperties() {
        Properties p = new Properties();
        try {
            p.load(getClass().getResourceAsStream("forgotpassword.ini"));
        } catch (FileNotFoundException filenotfoundexception) {
            log.error(filenotfoundexception);
        } catch (Exception exception) {
            log.error(exception);
        }
        return p;
    }

  
    

    

}
