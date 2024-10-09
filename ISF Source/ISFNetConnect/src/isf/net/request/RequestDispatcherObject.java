package isf.net.request;

import isf.common.ISFBase64;
import isf.common.request.RequestDispatcherProxy;
import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import isf.common.utils.PTMConfig;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogoutListener;
import isf.ptm.formats.PTM;
import isf.ptm.utils.Utils;
import java.io.*;
import java.net.*;
import java.util.zip.GZIPInputStream;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class RequestDispatcherObject extends RequestDispatcherProxy{

    public static String server,  mode,  imageurl,  alt_server,  encodeRate;//
    private static RequestDispatcherObject rdo = new RequestDispatcherObject();
    public static HttpClient client;
    public static HttpState state;
    public static LogoutListener lol;
    public static MultiThreadedHttpConnectionManager mthcm;
    public static DefaultHttpMethodRetryHandler dmrh;
    private String serverConfig = InscriptConfigLoader.getInstance().getProperty("configUrl");
    private String strMode = "?ptm=";
    Logger logger = LogManager.getLogger(isf.net.request.RequestDispatcherObject.class);
    

    static {
        server = InscriptConfigLoader.getInstance().p.getProperty("stationurl");
        mode = InscriptConfigLoader.getInstance().p.getProperty("mode");
        imageurl = InscriptConfigLoader.getInstance().p.getProperty("imgserver");
        alt_server = InscriptConfigLoader.getInstance().p.getProperty("alt_stationurl");
       // sessionid = "NEW";
        mthcm = new MultiThreadedHttpConnectionManager();
        mthcm.getParams().setDefaultMaxConnectionsPerHost(200);
        mthcm.getParams().setMaxTotalConnections(200);
        mthcm.getParams().setConnectionTimeout(30000);
        dmrh = new DefaultHttpMethodRetryHandler(3, false);
        client = new HttpClient(mthcm);
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, dmrh);
        client.getParams().setParameter(HttpConnectionParams.STALE_CONNECTION_CHECK, new Boolean(true));
        client.getParams().setParameter(HttpMethodParams.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
      

    }

    private RequestDispatcherObject() {
    }

    public static RequestDispatcherObject getInstance() {
        if(rdo==null)
            rdo = new RequestDispatcherObject();
        return rdo;
    }

    public Object handleQueryEvent(String s) {
        Object obj = null;
        String s1 = "<?xml version=\'1.0\'?>\n";
        String s2 = "<!DOCTYPE QUERY PUBLIC  \"-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN" +
                "\"\n\t\t\'" + server + "/query.dtd'>\n";
        String s3 = "<!DOCTYPE RESPONSE PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN" +
                "\"\n\t\t\'" + server + "/response.dtd'>\n<RESPONSE>";
        PostMethod method = null;
        try {
            mthcm.closeIdleConnections(3000L);
            String query = s1 + s2 + s;
            if (mode.equalsIgnoreCase("Web")) {
                String output = ISFBase64.encodeObject(query, ISFBase64.GZIP);
                method = new PostMethod(server + "/ISFGeneralServlet");
                NameValuePair nvp = new NameValuePair("query", output);
                method.addParameter(nvp);
                int status ;
                status = client.executeMethod(method);
                if (status == HttpStatus.SC_OK) {
                    if (query.indexOf("Authentication") > -1 || query.indexOf("Login") > -1) {
                        state = client.getState();
                    }
                    InputStream ins = method.getResponseBodyAsStream();
                    GZIPInputStream zipIn = new GZIPInputStream(ins);
                    ObjectInputStream objectinputstream = new ObjectInputStream(zipIn);
                    obj = objectinputstream.readObject();
                    objectinputstream.close();
                    ins.close();
                } else {
                    InputStream ins = method.getResponseBodyAsStream();
                    byte[] bts = new byte[2048];
                    while (ins.read(bts) > -1) {
                    }
                    ins.close();
                    if (status == 1500) {
                        lol.loggedout("Your Session Has Expired. Please Login Again");
                        return null;
                    } else if (status == 1401) {
                        lol.raiseError("Server Error Reported. Please Contact Administrator");
                        return null;
                    } else if (status == 1404) {
                        lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                        return null;
                    } else if (status == 404) {
                        logger.debug("query:"+query);
                        lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
                        return null;
                    } else {
                        logger.debug("statustext:" + method.getStatusText());
                        logger.debug("statuscode:" + method.getStatusCode());
                        logger.debug("statusline:" + method.getStatusLine().toString());
                        throw new Exception(status + " " + method.getStatusText());
                    }
                }
            } 
        } catch (java.net.ConnectException ce) {
            logger.error(ce);
            obj = s1 + s3 + "<ERRMSG MSG='" + ce.toString() + "'/></RESPONSE>";
            if (ce.getMessage().indexOf("Operation timed out") >= 0) {
                lol.raiseError("Request Timed Out. Please try again.");
            } else {
                lol.loggedout("Remote Server is currently unavailable. Please contact Administrator.");
           }
           logger.error( ce);
            obj=null;
        } catch (NoRouteToHostException nrt) {
            lol.disconnected();
            logger.error( nrt);
        } catch (UnknownHostException uhe) {
            obj = s1 + s3 + "<ERRMSG MSG='SessionInvalidException' /></RESPONSE>";
            lol.disconnected();
            logger.error( uhe);
        } catch (Exception io) {
            String str = io.getMessage();
            logger.error(io);
            if (str.indexOf("1500") > -1) {
                lol.loggedout("Your Session Has Expired. Please Login Again");
            } else if (str.indexOf("1401 ") > -1) {
                lol.raiseError("Server Error Reported. Please Contact Administrator");
            } else if (str.indexOf("1404") > -1) {
                lol.raiseError("Unknown Error Reported. Please Contact Administrator");
            } else {
                if (str.indexOf("No Record") > -1) {
                    lol.raiseError(io.getMessage());
                } else {
                    lol.raiseError("Unspecifed Error at Server. Please Contact Administrator");
                }
            }
           logger.error(io);
           obj =  null;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
            return obj;
        }


    }

    public ImageIcon fetchImage(String s) {
        ImageIcon ic = null;
       
        String source = "";
        GetMethod method = null;
        mthcm.closeIdleConnections(3000L);
        try {
            method = new GetMethod(server + "/ISFGeneralServlet");
            if (mode.equalsIgnoreCase("Web")) {
                logger.debug("imageurl:" + s);
                s = s.replace('&', '^');
                s = s.replace('=', '~');
                NameValuePair[] param = new NameValuePair[3];
                param[0] = new NameValuePair("SESSIONID", sessionid);
                param[1] = new NameValuePair("URT", "gi");
                param[2] = new NameValuePair("TEXT", s);
                method.setQueryString(param);
            } else {
                method.setQueryString("/getimage?" + s);
            }
            method.setFollowRedirects(true);
            int status = client.executeMethod(method);
            if (status == HttpStatus.SC_OK) {       //if status is redirect
                ic = new ImageIcon(method.getResponseBody());
            } else {
                InputStream ins = method.getResponseBodyAsStream();
                byte[] bts = new byte[2048];
                while (ins.read(bts) > -1) {
                }
                ins.close();
                if (status == 1500) {
                    lol.loggedout("Your Session Has Expired. Please Login Again");
                    ic= null;
                } else if (status == 1401) {
                    lol.raiseError("Server Error Reported. Please Contact Administrator");
                    ic= null;
                } else if (status == 1404) {
                    lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                    ic= null;
                } else if (status == 404) {
                    logger.debug("query2getimage:"+s);
                    lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
                    ic= null;
                } else {
                    throw new Exception(status + " " + method.getStatusText());
                }
            }
        } catch (Exception ie) {
            logger.error(ie);
            if (ie instanceof FileNotFoundException) {
                logger.debug("query3getimage:"+s);
                lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
            } else if (ie instanceof ConnectException) {
                if (ie.getMessage().indexOf("Operation timed out") >= 0) {
                    lol.raiseError("Request timed out. Please try again after sometime");
                } else {
                    lol.loggedout("Remote Server is currently unavailable. Please contact Administrator.");
                }
            } else if (ie instanceof UnknownHostException || ie instanceof NoRouteToHostException) {
                lol.disconnected();
            } else if (ie instanceof IOException) {
                String str = ie.getMessage();
                if (str != null && str.indexOf("1500") > -1) {
                    lol.loggedout("Your Session Has Expired. Please Login Again");
                } else if (str != null && str.indexOf("1401 ") > -1) {
                    lol.raiseError("Server Error Reported. Please Contact Administrator");
                } else if (str != null && str.indexOf("1404") > -1) {
                    lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                } else {
                    lol.raiseError("Unspecifed Error at Server. Please Contact Administrator");
                }
            }
           logger.error( ie);

            ic = null;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
           return ic;
        }
    }

    public Object getImageProperties(String s) {
        Object obj = null;
        GetMethod method = null;
        mthcm.closeIdleConnections(3000L);
        try {

            method = new GetMethod(server + "/ISFGeneralServlet");
            String s1 = "";
            if (mode.equalsIgnoreCase("Web")) {
                s = s.replace('&', '^');
                s = s.replace('=', '~');
                NameValuePair[] param = new NameValuePair[3];
                param[0] = new NameValuePair("SESSIONID", sessionid);
                param[1] = new NameValuePair("URT", "br");
                param[2] = new NameValuePair("TEXT", s);

                method.setQueryString(param);
            } else {
                method.setQueryString("/browse?" + s);
            }
            method.setFollowRedirects(true);
            int status = 0;
            status = client.executeMethod(method);
            if (status == HttpStatus.SC_OK) {    // if redirected
                obj = method.getResponseBody();
            } else {
                InputStream ins = method.getResponseBodyAsStream();
                byte[] bts = new byte[2048];
                while (ins.read(bts) > -1) {
                }
                ins.close();
                if (status == 1500) {
                    lol.loggedout("Your Session Has Expired. Please Login Again");
                    obj= null;
                } else if (status == 1401) {
                    lol.raiseError("Server Error Reported. Please Contact Administrator");
                    obj= null;
                } else if (status == 1404) {
                    lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                    obj= null;
                } else if (status == 404) {
                    logger.debug("query4imageproperties:"+s);
                    lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
                    obj= null;
                } else {
                    throw new Exception(status + " " + method.getStatusText());
                }
            }
        } catch (java.net.ConnectException ce) {
            if (ce.getMessage().indexOf("Operation timed out") >= 0) {
                lol.raiseError("Request Timed Out. Please try again");
               
            } else {
                lol.loggedout("Remote Server is currently unavailable. Please contact Administrator.");
            }
           logger.error( ce);
            obj = null;
        } catch (NoRouteToHostException nrt) {
            lol.disconnected();
           logger.error( nrt);
           obj = null;
        } catch (UnknownHostException uhe) {

            lol.disconnected();
           logger.error( uhe);
            obj = null;
        } catch (FileNotFoundException fnfe) {
            logger.debug("query5properties:"+s);
            lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
            obj = null;
           logger.error( fnfe);
        } catch (Exception ie) {
            logger.error(ie);
            if (ie instanceof IOException) {
                String str = ie.getMessage();
                if (str != null && str.indexOf("1500") > -1) {
                    lol.loggedout("Your Session Has Expired. Please Login Again");
                } else if (str != null && str.indexOf("1401 ") > -1) {
                    lol.raiseError("Server Error Reported. Please Contact Administrator");
                } else if (str != null && str.indexOf("1404") > -1) {
                    lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                } else {
                    lol.raiseError("Unspecifed Error at Server. Please Contact Administrator");
                }
            } else {
                logger.error(ie);
            }
            obj = null;
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
            return obj;
        }
    }

    public String saveFile(String s, String filename) {
        String msg = "";
        GetMethod method = null;
        mthcm.closeIdleConnections(3000L);
        try {
            method = new GetMethod(server + "/ISFGeneralServlet");
            if (mode.equalsIgnoreCase("Web")) {
                s = s.replace('&', '^');
                s = s.replace('=', '~');
                NameValuePair[] param = new NameValuePair[3];
                param[0] = new NameValuePair("SESSIONID", sessionid);
                param[1] = new NameValuePair("URT", "gi");
                param[2] = new NameValuePair("TEXT", s);
                method.setQueryString(param);
            } else {
                method.setQueryString("/getimage?" + s);
            }
            java.io.InputStream inputstream = null;
            method.setFollowRedirects(true);
            byte[] obj = null;
            int status = 0;
            status = client.executeMethod(method);
            if (status == HttpStatus.SC_OK) {
                obj = method.getResponseBody();
                FileOutputStream os = new FileOutputStream(filename);
                os.write(obj);
                os.flush();
                os.close();
                msg = "File Saved as " + filename;
            } else {
                InputStream ins = method.getResponseBodyAsStream();
                byte[] bts = new byte[2048];
                while (ins.read(bts) > -1) {
                }
                ins.close();
                if (status == 1500) {
                    lol.loggedout("Your Session Has Expired. Please Login Again");
                    msg = null;
                } else if (status == 1401) {
                    lol.raiseError("Server Error Reported. Please Contact Administrator");
                    msg = null;
                } else if (status == 1404) {
                    lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                    msg = null;
                } else if (status == 404) {
                    logger.debug("query6save:"+filename);
                    lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
                    msg = null;
                } else {
                    throw new Exception(status + " " + method.getStatusText());
                }
            }
        } catch (java.net.ConnectException ce) {
            msg = "Connection to server is lost. Please reconnect to the server.";
            if (ce.getMessage().indexOf("Operation timed out") >= 0) {
                lol.raiseError("Request Timed Out. Please try again");
            } else {
                lol.loggedout("Remote Server is currently unavailable. Please contact Administrator.");
            }
            msg = null;
        } catch (NoRouteToHostException nrt) {
            lol.disconnected();
            msg = null;
        } catch (UnknownHostException uhe) {
            lol.disconnected();
            msg = null;
        } catch (FileNotFoundException fnfe) {
            lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
            msg = null;
        } catch (IOException io) {
            String str = io.getMessage();
            if (str.indexOf("1500") > -1) {
                lol.loggedout("Your Session Has Expired. Please Login Again");
            } else if (str.indexOf("1401 ") > -1) {
                lol.raiseError("Server Error Reported. Please Contact Administrator");
            } else if (str.indexOf("1404") > -1) {
                lol.raiseError("Unknown Error Reported. Please Contact Administrator");
            } else {
                lol.raiseError("Unspecifed Error at Server. Please Contact Administrator");
            }
            msg = null;
        } catch (Exception exception) {
            msg = "Unexpected error in saving. Please contact the administrator. ";
            logger.error(exception );
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
            return msg;
        }
    }

    public int checkConnection() {
        int hasConnection = 0;
        try {
            if (mode.equalsIgnoreCase("Web")) {
               logger.debug("Checking for : " + server);
                URL url = new URL(server);
                URLConnection urlconnection = url.openConnection();
                urlconnection.getInputStream().read();
                hasConnection = 0;

            } else {
                hasConnection = 0;
            }
        } catch (NoRouteToHostException nrt) {
            hasConnection = 1;
        } catch (UnknownHostException e) {
            hasConnection = 1;
        } catch (ConnectException e) {
            hasConnection = 2;
        } catch (IOException ie) {
            hasConnection = 3;

        } finally {
            if (hasConnection > 0 && !server.equalsIgnoreCase(alt_server)) {
                logger.debug("Checking for alternate url: " + alt_server);
                server = alt_server;
                hasConnection = checkConnection();
            }

            return hasConnection;
        }
    }

    public static void addLogoutListener(LogoutListener lo) {
        lol = lo;
    }

    public PTMConfig getPTMConfig(String ptmName) {
        Document dom = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ptmName.indexOf(".ptm") > -1) {
                ptmName = ptmName.substring(0, ptmName.indexOf(".ptm"));
            }else if (ptmName.indexOf(".rti") > -1) {
                ptmName = ptmName.substring(0, ptmName.indexOf(".rti"));
            }
            dom = builder.parse(serverConfig + strMode + ptmName);
        } catch (Exception pe) {
           logger.error( pe);
            return null;
        }
        PTMConfig ptm = new PTMConfig();
        ptm.setName(retrieveAttributeValue(dom, "PTM", "name"));
        ptm.setWidth(retrieveAttributeValueInt(dom, "PTM", "width"));
        ptm.setHeight(retrieveAttributeValueInt(dom, "PTM", "height"));
        String scaleStr = retrieveAttributeValue(dom, "PTM", "scale");
        String biasStr = retrieveAttributeValue(dom, "PTM", "bias");
        ptm.setRows(retrieveAttributeValueInt(dom, "PTM", "rows"));
        ptm.setColumns(retrieveAttributeValueInt(dom, "PTM", "cols"));
        ptm.setSegmentSize(retrieveAttributeValueInt(dom, "PTM", "segment"));
        String[] biasA = Utils.split(biasStr, ",");
        float[] bias = new float[biasA.length];
        for (int i = 0; i < biasA.length; i++) {
            bias[i] =  Float.parseFloat(biasA[i]);
        }
        String[] scaleA = Utils.split(scaleStr, ",");
        float[] scale = new float[scaleA.length];
        for (int i = 0; i < scaleA.length; i++) {
            scale[i] = Float.parseFloat(scaleA[i]);
        }
        ptm.setScale(scale);
        ptm.setBias(bias);

        ptm.setSize(retrieveAttributeValueLong(dom, "PTM", "size"));
        ptm.setVersion(retrieveAttributeValue(dom, "PTM", "version"));

        if (ptm.getVersion().indexOf("HSH") > -1) {
            ptm.setExtension(".rti");

            ptm.setType(retrieveAttributeValueInt(dom, "PTM", "type"));
            ptm.setTerms(retrieveAttributeValueInt(dom, "PTM", "basisterms"));
            ptm.setColors(retrieveAttributeValueInt(dom, "PTM", "colors"));
            ptm.setElements(retrieveAttributeValueInt(dom, "PTM", "elemSize"));
            ptm.setBasistype(retrieveAttributeValueInt(dom, "PTM", "termType"));
        } else if (ptm.getVersion().indexOf("PTM") > -1) {
            ptm.setExtension(".ptm");

            String type = retrieveAttributeValue(dom, "PTM", "type");
            ptm.setType(PTM.TYPE_RAW_PTM);
            
            if (type.equalsIgnoreCase(PTM.PTM_RGB)) {
                ptm.setBasistype(PTM.BASIS_RGB);
            } else if (type.equalsIgnoreCase(PTM.PTM_LRGB)) {
                ptm.setBasistype(PTM.BASIS_LRGB);
            }
        }
        ptm.setCached(retrieveAttributeValue(dom, "PTM", "cached"));
        return ptm;
    }

   /* public InputStream executeMethod(GetMethod method, int status) {
        try {
            status = client.executeMethod(method);
            return method.getResponseBodyAsStream();
        } catch (Exception exp) {
            logger.error(exp);
        }
        return null;
    }*/

    private String retrieveAttributeValue(Document dom, String tagname, String attribute) {
        //System.out.println("Fetching values of "+attribute);
        NodeList nodes = dom.getElementsByTagName(tagname);
        //assert(nodes.getLength() == 1);
        Node node = nodes.item(0);
        NamedNodeMap nodemap = node.getAttributes();
        //System.out.println("has node attribute" + nodemap.getLength());
        return nodemap.getNamedItem(attribute).getNodeValue();
    }

    private int retrieveAttributeValueInt(Document dom, String tagname, String attribute) {
        NodeList nodes = dom.getElementsByTagName(tagname);
        //assert(nodes.getLength() == 1);
        Node node = nodes.item(0);
        NamedNodeMap nodemap = node.getAttributes();
        String value = nodemap.getNamedItem(attribute).getNodeValue();
        return Integer.parseInt(value);
    }

    private long retrieveAttributeValueLong(Document dom, String tagname, String attribute) {
        NodeList nodes = dom.getElementsByTagName(tagname);
        //assert(nodes.getLength() == 1);
        Node node = nodes.item(0);
        NamedNodeMap nodemap = node.getAttributes();
        String value = nodemap.getNamedItem(attribute).getNodeValue();
        return Long.parseLong(value);
    }
}