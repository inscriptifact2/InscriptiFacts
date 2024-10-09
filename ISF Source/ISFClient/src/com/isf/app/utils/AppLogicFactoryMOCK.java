package com.isf.app.utils;

import isf.common.jro.JavaRequestObject;
import java.io.Serializable;

public class AppLogicFactoryMOCK
        implements Serializable {


/*    public AppLogicFactoryMOCK()
    {
    }*/

    public static Object getResponseObject(JavaRequestObject javarequestobject)
            throws Exception {

        //System.out.println("in applogi factory" + System.currentTimeMillis());
        Object obj = null;
        /*   try {
               if (javarequestobject.getQuery().getType().equals("Authentication")) {
                   System.out.println("in applogic factory: 1 : " + System.currentTimeMillis());

                   AuthResponseObjectMOCK authresponseobject = new AuthResponseObjectMOCK();
                   System.out.println("in applogic factory: 2 : " + System.currentTimeMillis());
                   authresponseobject.setRequestObject(javarequestobject);
                   System.out.println("in applogic factory: 3 : " + System.currentTimeMillis());
                   obj = authresponseobject.getResponse();
                   System.out.println("in applogic factory: 4 : " + System.currentTimeMillis());
               }
               if (javarequestobject.getQuery().getType().equals("Login") || javarequestobject.getQuery().getType().equals("Logout")) {
                   AuthResponseObjectMOCK authresponseobject = new AuthResponseObjectMOCK();
                   authresponseobject.setRequestObject(javarequestobject);
                   obj = authresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("ChangePassword")) {
                   AuthResponseObjectMOCK authresponseobject1 = new AuthResponseObjectMOCK();
                   authresponseobject1.setRequestObject(javarequestobject);
                   obj = authresponseobject1.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("ForgotPassword")) {
                   AuthResponseObjectMOCK authresponseobject2 = new AuthResponseObjectMOCK();
                   authresponseobject2.setRequestObject(javarequestobject);
                   obj = authresponseobject2.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("textmetadata") || javarequestobject.getQuery().getType().equals("photometadata")) {
                   MetaDataResponseObjectMOCK metadataresponseobject = new MetaDataResponseObjectMOCK();
                   metadataresponseobject.setRequestObject(javarequestobject);
                   obj = metadataresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("isfData") || javarequestobject.getQuery().getType().equals("isfComboData") || javarequestobject.getQuery().getType().equals("isftimedata")) {
                   QueryResponseObjectMOCK queryresponseobject = new QueryResponseObjectMOCK();
                   queryresponseobject.setRequestObject(javarequestobject);
                   obj = queryresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("textdivdata")) {
                   // System.out.println("*****  textdiv data ******** ");
                   TextResponseObjectMOCK textresponseobject = new TextResponseObjectMOCK();
                   textresponseobject.setRequestObject(javarequestobject);
                   obj = textresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("textdivphotoid") || javarequestobject.getQuery().getType().equals("textsuddivphotoid") || javarequestobject.getQuery().getType().equals("spatialphotoid") || javarequestobject.getQuery().getType().equals("catphotoid")) {
                   //  System.out.println("*****  textdivphotoid  data ******** ");
                   PhotoResponseObjectMOCK photoresponseobject = new PhotoResponseObjectMOCK();
                   photoresponseobject.setRequestObject(javarequestobject);
                   obj = photoresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("spatialdata")) {
                   SpatialResponseObjectMOCK spatialresponseobject = new SpatialResponseObjectMOCK();
                   spatialresponseobject.setRequestObject(javarequestobject);
                   obj = spatialresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("quickimage") || javarequestobject.getQuery().getType().equals("indexmapimage")) {
                   ImageResponseObjectMOCK imageresponseobject = new ImageResponseObjectMOCK();
                   imageresponseobject.setRequestObject(javarequestobject);
                   obj = imageresponseobject.getResponse();
               }
               if (javarequestobject.getQuery().getType().equals("siddata")) {
                   SidResponseObjectMOCK sidresponseobject = new SidResponseObjectMOCK();
                   sidresponseobject.setRequestObject(javarequestobject);
                   obj = sidresponseobject.getResponse();
               }
           } catch (Exception exception) {
               throw exception;
           }    */
        return obj;
    }
}
