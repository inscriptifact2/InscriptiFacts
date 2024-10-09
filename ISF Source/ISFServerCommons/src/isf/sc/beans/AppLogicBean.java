/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.sc.beans;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.sc.utils.AppLogicFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author raok1
 */


public class AppLogicBean extends ISFBean {



     private Logger logger = LogManager.getLogger(isf.sc.beans.AppLogicBean.class);

    public Object getResponseObject(JavaRequestObject javarequestobject)
            throws Exception {
        try {
            long start = System.currentTimeMillis();
            logger.debug("Applogic called:" + start);
            //  logger.debug("---------in applogic bean  getRes----------");
            AppLogicFactory applogicfactory = new AppLogicFactory();
            Object obj = applogicfactory.getResponseObject(javarequestobject);
            logger.debug("Applogic ended:" + (System.currentTimeMillis() - start));
            return obj;
        } catch (Exception exception) {
            logger.error("Error:",exception);
            throw exception;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")

}
