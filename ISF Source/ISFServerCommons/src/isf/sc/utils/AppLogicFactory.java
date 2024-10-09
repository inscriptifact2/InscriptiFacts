package isf.sc.utils;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.sc.response.*;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class AppLogicFactory
        implements Serializable {

    Logger logger = LogManager.getLogger(isf.sc.utils.AppLogicFactory.class);

    public AppLogicFactory() {
    }

    public Object getResponseObject(JavaRequestObject javarequestobject)
            throws Exception {
        Object obj = null;
        try {
            String type= javarequestobject.getQuery().getType();
            logger.debug("Type in AppLogicFactory:"+type);
            boolean authentication = type.equalsIgnoreCase("Authentication")||type.equalsIgnoreCase("Login")||
                    type.equalsIgnoreCase("Logout")||type.equalsIgnoreCase("ChangePassword")||
                    type.equalsIgnoreCase("ForgotPassword")||type.equalsIgnoreCase("UserAcceptance");
            if (authentication) {
                AuthResponseObject authresponseobject = new AuthResponseObject();
                authresponseobject.setRequestObject(javarequestobject);
                obj = authresponseobject.getResponse();
            }
            /*if (type.equals("Login") 
                    || type.equals("Logout")) {
                AuthResponseObject authresponseobject = new AuthResponseObject();
                authresponseobject.setRequestObject(javarequestobject);
                obj = authresponseobject.getResponse();
            }
            if (type.equals("ChangePassword")) {
                AuthResponseObject authresponseobject1 = new AuthResponseObject();
                authresponseobject1.setRequestObject(javarequestobject);
                obj = authresponseobject1.getResponse();
            }
            if (type.equals("ForgotPassword")) {
                AuthResponseObject authresponseobject2 = new AuthResponseObject();
                authresponseobject2.setRequestObject(javarequestobject);
                obj = authresponseobject2.getResponse();
            }
            if (type.equals("ACCEPT")) {
                AuthResponseObject authresponseobject2 = new AuthResponseObject();
                authresponseobject2.setRequestObject(javarequestobject);
                obj = authresponseobject2.getResponse();
            }*/
            if (type.equals("textmetadata") || type.contains("photometadata")) {
                MetaDataResponseObject metadataresponseobject = new MetaDataResponseObject();
                metadataresponseobject.setRequestObject(javarequestobject);
                obj = metadataresponseobject.getResponse();
            }
            if (type.equals("isfData") || type.equals("isfComboData") || type.equals("isftimedata")) {
                QueryResponseObject queryresponseobject = new QueryResponseObject();
                queryresponseobject.setRequestObject(javarequestobject);
                obj = queryresponseobject.getResponse();
            }
            if (type.equals("textdivdata")) {
                // logger.debug("*****  textdiv data ******** ");
                TextResponseObject textresponseobject = new TextResponseObject();
                textresponseobject.setRequestObject(javarequestobject);
                obj = textresponseobject.getResponse();
            }
            if (type.startsWith("textdivphotoid") ||
                    type.startsWith("textsuddivphotoid") ||
                    type.startsWith("spatialphotoid") ||
                    type.startsWith("catphotoid")) {
                //  logger.debug("*****  textdivphotoid  data ******** ");
                PhotoResponseObject photoresponseobject = new PhotoResponseObject();
                photoresponseobject.setRequestObject(javarequestobject);
                obj = photoresponseobject.getResponse();
            }
            if (type.equals("spatialdata")) {
                SpatialResponseObject spatialresponseobject = new SpatialResponseObject();
                spatialresponseobject.setRequestObject(javarequestobject);
                obj = spatialresponseobject.getResponse();
            }
            if (type.equals("quickimage") ||
                    type.equals("indexmapimage")) {
                ImageResponseObject imageresponseobject = new ImageResponseObject();
                imageresponseobject.setRequestObject(javarequestobject);
                obj = imageresponseobject.getResponse();
            }
            if (type.equals("siddata")) {
                SidResponseObject sidresponseobject = new SidResponseObject();
                sidresponseobject.setRequestObject(javarequestobject);
                obj = sidresponseobject.getResponse();
            }
        } catch (Exception exception) {
            logger.error("Exception", exception);

            throw exception;
        }
        return obj;
    }
}
