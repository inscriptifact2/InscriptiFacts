package isf.sc.utils;

import isf.common.utils.LogManager;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class ISFServiceLocator {

    private static Logger logger = LogManager.getLogger(isf.sc.utils.ISFServiceLocator.class);
    private static ISFServiceLocator ourInstance;
    private InitialContext initLocalContext;
    private InitialContext initRemoteContext;
    private Hashtable services;

    public static synchronized ISFServiceLocator getInstance() {
        if (ourInstance == null) {
            ourInstance = new ISFServiceLocator();
        }
        return ourInstance;
    }

    private ISFServiceLocator() {
        try {
            initLocalContext = new InitialContext();
            String s = InscriptConfigServerLoader.getInstance().getProperty("ejbproviderurl");
            Properties properties = new Properties();
            properties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            properties.put("java.naming.provider.url", s);
            initRemoteContext = new InitialContext(properties);
            services = new Hashtable();
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /*  public Remote getRemoteHome(String str)
    {
    if(!services.containsKey(str))
    {
    try
    {
    Remote home = (Remote)initRemoteContext.lookup(str);
    services.put(str, home);
    }
    catch(Exception e)
    {
    logger.error("Exception:", e);
    }
    }
    return (Remote)services.get(str);
    }*/

    /*  public Local getLocalHome(String str)
    {
    if(!services.containsKey(str))
    {
    try
    {
    Local home = (Local)initLocalContext.lookup(str);
    services.put(str, home);
    }
    catch(Exception e)
    {
    logger.error("Exception:", e);
    }
    }
    return (Local)services.get(str);
    }*/
    public DataSource getLocalDataSource(String ds) {
        if (!services.containsKey("local/" + ds)) {
            try {
                DataSource datasource = (DataSource) initLocalContext.lookup(ds);
                services.put("local/" + ds, datasource);
            } catch (Exception e) {
                logger.error("Exception:", e);
            }
        }
        logger.debug("Datasource in local:" + services.get("local/" + ds));
        return (DataSource) services.get("local/" + ds);
    }

    public DataSource getRemoteDataSource(String ds) {
        if (!services.containsKey("remote/" + ds)) {
            try {
                DataSource datasource = (DataSource) initRemoteContext.lookup(ds);
                services.put("remote/" + ds, datasource);
            } catch (Exception e) {
                logger.error("Exception:", e);
            }
        }
        logger.debug("Datasource in remote:" + services.get("remote/" + ds));
        return (DataSource) services.get("remote/" + ds);
    }
}
