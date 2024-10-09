package com.isf.app.utils;

import isf.common.utils.LogManager;
import org.apache.log4j.Logger;


public class DataStructureFactory {
    
   static Logger log = LogManager.getLogger(com.isf.app.utils.DataStructureFactory.class);

  
    public static XmlConverter getConverter(String s) {
        Object obj = null;
        log.debug("s in DataStructureFactory:"+s);
        try {
            if (s.equals("initialisationdata")||s.equals("Authentication")||s.equals("Login")||s.equals("UserAcceptance")) {
                obj = new InitializationXmlConverter();
            } else if (s.equals("isfData") || s.equals("isfComboData") ||  s.startsWith("spatialphotoid")) {
                obj = new MetaDataXmlConverter();
            }else if(s.equals("ChangePassword") ||s.equals("ForgotPassword"))
            {
                obj = new GeneralXmlConverter();
            }
            else if(s.startsWith("spatialdata"))
            {
                obj = new SpatialXmlConverter();
            }
            else if(s.startsWith("textdivdata"))
            {
                obj = new TextDivisionXmlConverter();
            }
            else if(s.startsWith("catphotoid") ||s.startsWith("textdivphotoid")||s.startsWith("textsuddivphotoid"))
            {
                obj = new MetaDataXmlConverter();
            }

        } catch (Exception exception) {
            log.error(exception);
        }
        return ((XmlConverter) (obj));
    }
}
