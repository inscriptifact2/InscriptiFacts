/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.webutils;

import isf.sc.beans.ValidateUserBean;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author raok1
 */
public class SessionManager implements HttpSessionListener {

    public static ArrayList sessionList = new ArrayList();

    public void sessionCreated(HttpSessionEvent se) {
      //  logger.debug("Creatred Session:" + se.getSession().getId());
       if(!se.getSession().getServletContext().getContextPath().contains("UserPortal"))
           sessionList.add(se.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
       // logger.debug("Session Destroyed:" + session.getId());
        if(!session.getServletContext().getContextPath().contains("UserPortal")){
        String key = getKey("", session);
       // logger.debug("Key:" + key);
        if (key != null) {
            
                //logger.debug("essionDestroyed:killig session:" + dto.getUserid());
                ValidateUserBean vsb = new ValidateUserBean();
                vsb.killSession(key);
            
        } 
        sessionList.remove(se.getSession());
       // logger.debug("-----------------------------");
        }
    }

    public static boolean purgeUser(String userIdentity) {
      //  logger.debug("In Purge User");
        String key = null;
        boolean found = false;
        ValidateUserBean vsb = new ValidateUserBean();
      //  logger.debug("PurgeSession: " + sessionList.size());
        for(int i = 0; i <sessionList.size();i++){
        //for (HttpSession session : (HttpSession) sessionList) {
            HttpSession session = (HttpSession)sessionList.get(i);
            key = getKey(userIdentity, session);
            if (key != null) {
           //     logger.debug("PurgeUser:" + key);
               
                    vsb.killSession(key);
                    session.invalidate();
                    sessionList.remove(session);
                    found = true;
                    break;
               

            } else {
      //          logger.debug("PurgeUser: Key is null");
              }
        }
        if(!found)
            vsb.killSession(userIdentity);
     //   logger.debug("PurgeUser:------------------------");
        return found;
    }

    private static String getKey(String user, HttpSession session) {
        Enumeration names = session.getAttributeNames();
        String key = null,temp="";
        while (names.hasMoreElements()) {
            temp = names.nextElement().toString();
            if (temp.startsWith(user + "---") || temp.indexOf("---") > 0) {
                key=temp;
                break;
            }
        }
        return key;
    }
}
