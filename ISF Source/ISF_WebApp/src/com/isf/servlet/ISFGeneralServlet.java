package com.isf.servlet;


import com.isf.webutils.InscriptWebLoader;
import com.isf.webutils.UserDTO;
import isf.common.ISFBase64;
import isf.common.XMLProcessorObject;
import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.sc.beans.AppLogicBean;
import isf.sc.beans.ValidateUserBean;
import isf.sc.utils.SessionInvalidException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;


public class ISFGeneralServlet extends HttpServlet {
   
   
    Logger logger = LogManager.getLogger(ISFGeneralServlet.class);

    public void init(ServletConfig servletconfig)
            throws ServletException {
        super.init(servletconfig);
       // LogManager.initialize("log4j.txt");
         try {
         /////todo ://////////////////////   sch.getSessions();
         //   ValidateUserLocal local = lookupValidateUserBean();
         //   local.getSessions();
             
            ValidateUserBean local = new ValidateUserBean();
            local.getSessions();

        } catch (Exception e) {
            logger.error("Error in Servlet while deleting sessions:", e);
        }

    }

    public void service(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        logger.debug("Servlet invoked at:" + start);
        String sessionid = "";

        //  Environment environment = new Environment();
        // logger.debug("ejb 1");
        javax.servlet.ServletOutputStream servletoutputstream = httpservletresponse.getOutputStream();


        try {
         //   AppLogicLocal appl = lookupAppLogicLocalBean();
            //AppLogicRemote appr = appLogicRemoteHome.create();
            AppLogicBean appBean = new AppLogicBean();

            HttpSession httpsession = httpservletrequest.getSession(false);
            String qs = httpservletrequest.getQueryString();
            JavaRequestObject javarequestobject = null;
            String type = "";
            Object obj = null;
            if (qs == null) {
                String query = httpservletrequest.getParameter("query");
                String s = (String) ISFBase64.decodeToObject(query);
                XMLProcessorObject xmlprocessorobject = new XMLProcessorObject();
                javarequestobject = xmlprocessorobject.getJavaRequest(s);
                type = javarequestobject.getQuery().getType();

               
                String session = "";

                   logger.debug("Type in ISFGeneralServlet:"+type);
                if (httpsession != null || (type.equalsIgnoreCase("Authentication") || type.equalsIgnoreCase("Login")
                        || type.equalsIgnoreCase("ChangePassword")|| type.equalsIgnoreCase("UserAcceptance") || type.equalsIgnoreCase("ForgotPassword"))) {
             //       obj = appl.getResponseObject(javarequestobject);
                 //   obj = appr.getResponseObject(javarequestobject);
                   obj =  appBean.getResponseObject(javarequestobject);
                } else {
                    httpservletresponse.sendError(1500, "sessioninvalidated");
                    servletoutputstream.close();
                    return;
                }


                GZIPOutputStream zipOut = new GZIPOutputStream(servletoutputstream);
                ObjectOutputStream objectoutputstream = new ObjectOutputStream(zipOut);
                if (obj instanceof byte[] || obj instanceof Hashtable) {
                    objectoutputstream.writeObject(obj);
                    session = sessionid;

                } else if (obj instanceof String) {
                    String s1 = InscriptWebLoader.getInstance().p.getProperty("stationurl");
                    StringBuffer sb = new StringBuffer();
                    sb.append("<?xml version='1.0'?>");
                    sb.append("<!DOCTYPE RESPONSE PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN\"");
                    sb.append("  '" + s1 + "/response.dtd'>");
                    sb.append((String) obj);
                    String s3 = sb.toString();
                    session = s3.substring(s3.indexOf("SESSIONID='") + 11, s3.indexOf("'", s3.indexOf("SESSIONID='") + 11));
                    objectoutputstream.writeObject(s3);
                }

                if (httpsession == null && (type.equalsIgnoreCase("Authentication") || type.equalsIgnoreCase("Login"))) {
                    httpsession = httpservletrequest.getSession(true);
                    UserDTO dto = new UserDTO();
                    dto.setUserid(session);
                    dto.setUsersession(httpsession.getId());
                    httpsession.setAttribute(session, dto);
                } else if (javarequestobject.getQuery().getType().equalsIgnoreCase("Logout")) {
                    if (httpsession != null) {
                        httpsession.removeAttribute(session);
                        httpsession.invalidate();
                    }
                }
                objectoutputstream.flush();
                objectoutputstream.close();
                zipOut.close();
                servletoutputstream.close();


            } else {    //request for image or image properties
                if (httpsession == null) {
                    httpservletresponse.sendError(1500, "sessioninvalidated");
                    servletoutputstream.close();
                    return;
                } else {
                    sessionid = httpservletrequest.getParameter("SESSIONID");
                    String urmode = httpservletrequest.getParameter("URT");
                    String param = httpservletrequest.getParameter("TEXT");
                    param = param.replace('~', '=');
                    param = param.replace('^', '&');

                    urmode = ("br".equals(urmode)) ? "/browse?" : "/getimage?";
                    String igurl = InscriptWebLoader.getInstance().p.getProperty("imageurl");
                    String imgurl = InscriptWebLoader.getInstance().p.getProperty("imgurl");
                    String ext = InscriptWebLoader.getInstance().p.getProperty("lizard_ext");
                    String indexLoc = InscriptWebLoader.getInstance().p.getProperty("index_directory");
                    String quickLoc = InscriptWebLoader.getInstance().p.getProperty("quick_directory");

                    /*if (param.indexOf("x.jpg") > -1 || param.indexOf("q.jpg") > -1) {
                        param = convertString(param, ext);
                    }
                    logger.debug("After Processing: " + param);*/
                    String category = "";
                    String imgName = "";
                    if (param.indexOf("IndexV4") > -1 || param.indexOf("QuickV4") > -1) {
                        if (param.indexOf("IndexV4") > -1)
                            category = indexLoc;
                        else
                            category = quickLoc;

                        int pos = param.indexOf("&item=/");
                        if (pos > -1)
                            imgName = param.substring(pos + 7);


                        pos = imgName.indexOf("&cp");
                        if (pos > -1)
                            imgName = imgName.substring(0, pos);
                        logger.debug("Finally getting image:"+(imgurl + "/" + category + "/" + imgName));
                        httpservletresponse.sendRedirect(imgurl + "/" + category + "/" + imgName);
                        //todo check here to get the file and push through
                    } else {
                        httpservletresponse.sendRedirect(igurl + urmode + param);
                    }
                }
            }


        } catch (SessionInvalidException sie) {  //SessionInvalidException
            if (sie == null || sie.getMessage() == null) {
                httpservletresponse.sendError(1500, "sessioninvalidated");
                servletoutputstream.close();
            } else if (sie.getMessage() != null && sie.getMessage().startsWith("null")) {
                logger.debug(" nothing in the mesage");
                logger.error("Exception :", sie);
                httpservletresponse.sendError(1401, "General Error at server");
                servletoutputstream.close();
            } else if (sie.getMessage().indexOf("EJB") > -1 || sie.getMessage().indexOf("JNDI") > -1) {
                logger.error("error in server:" + sie.getMessage());
                httpservletresponse.sendError(1404, "WebLogic Error :" + sie.getMessage());
                servletoutputstream.close();
            } else {
                httpservletresponse.sendError(1500, "sessioninvalidated");
                servletoutputstream.close();
            }

        } catch (Exception exception) {

            logger.error("Exception in output:", exception);
            httpservletresponse.sendError(1404, "Unknown Error:" + exception.getMessage());
            servletoutputstream.close();
            try {
                logger.debug("In Servlet 140:" + exception.getMessage());

            } catch (Exception ie) {
                logger.debug("COULD NOT KILL SESSION.");
                logger.error("Session was not killed:", ie);
            }


        } finally {
            logger.debug("Servlet execution completed:" + (System.currentTimeMillis() - start));
        }
    }


    public String convertString(String param, String ext) {
        String converted = "";
        int pos = param.indexOf(".jpg");
        String prefix = param.substring(0, pos);
        String suffix = param.substring(pos + 4);
        converted = prefix + ext + "&wid=512&hei=512";//+suffix;

        return converted;

    }

    public ISFGeneralServlet() {
    }

/*private ValidateUserLocal lookupValidateUserBean()  {
        try {
            Context c = new InitialContext();
            validateUserLocalHome = (ValidateUserLocalHome) c.lookup("java:comp/env/ValidateUserBean");
            return validateUserLocalHome.create();
        } catch (NamingException ne) {

            throw new RuntimeException(ne);
        } catch (CreateException ce) {

            throw new RuntimeException(ce);
        }
    }
private AppLogicLocal lookupAppLogicLocalBean() {
        try {
            Context c = new InitialContext();
            appLogicLocalHome = (AppLogicLocalHome) c.lookup("java:comp/env/AppLogicLocalBean");
            return appLogicLocalHome.create();
        } catch (NamingException ne) {
           // java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        } catch (CreateException ce) {
           // java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ce);
            throw new RuntimeException(ce);
        }
    }*/

}
