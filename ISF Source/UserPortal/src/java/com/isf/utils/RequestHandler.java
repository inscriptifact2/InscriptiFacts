/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 *
 * @author raok1
 */
public class RequestHandler {

    public static String server, mode, imageurl, alt_server, encodeRate;//
    private static RequestHandler rdo = new RequestHandler();
    public static String sessionid;
    public static HttpClient client;
    public static HttpState state;
    public static MultiThreadedHttpConnectionManager mthcm;
    public static DefaultHttpMethodRetryHandler dmrh;

    // Logger logger = LogManager.getLogger(RequestDispatcherObject.class);
    static {

        server = Utils.getInstance().getProperty("serverurl");
        mthcm = new MultiThreadedHttpConnectionManager();
        mthcm.getParams().setDefaultMaxConnectionsPerHost(200);
        mthcm.getParams().setMaxTotalConnections(200);
        mthcm.getParams().setConnectionTimeout(30000);
        // mthcm.setMaxConnectionsPerHost(200);
        // mthcm.setMaxTotalConnections(200);
        dmrh = new DefaultHttpMethodRetryHandler(3, false);
        client = new HttpClient(mthcm);
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, dmrh);
        client.getParams().setParameter(HttpConnectionParams.STALE_CONNECTION_CHECK, new Boolean(true));
        client.getParams().setParameter(HttpMethodParams.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        // client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
    }

    private RequestHandler() {
    }

    public static RequestHandler getInstance() {
        return rdo;
    }

    public String getResponse(String mode, String user) {

        //System.out.println("From RDO:" + s);
        String msg = "";
        PostMethod method = null;
        try {
            mthcm.closeIdleConnections(3000L);

            method = new PostMethod(server + "/isf-admin");
            if (mode != null) {
                NameValuePair nvp1 = new NameValuePair("action", mode);
                method.addParameter(nvp1);
            }
            if (user != null) {
                NameValuePair nvp2 = new NameValuePair("uid", user);
                method.addParameter(nvp2);
            }

            int status = 0;

            status = client.executeMethod(method);
            if (status == HttpStatus.SC_OK) {
                msg = method.getResponseBodyAsString();
            } else {
                msg = "Error ";
            }

        } catch (Exception exp) {
            msg = msg + exp.getMessage();
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
            return msg;
        }


    }
}
