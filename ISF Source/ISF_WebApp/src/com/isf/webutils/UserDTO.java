package com.isf.webutils;




import isf.common.utils.LogManager;
import java.io.Serializable;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Dec 20, 2004
 * Time: 11:32:37 PM
 * To change this template use Options | File Templates.
 */
public class UserDTO implements HttpSessionBindingListener, Serializable {
    Logger logger = LogManager.getLogger(UserDTO.class);
   


    public void valueBound(HttpSessionBindingEvent event) {
        logger.debug("Session Bounded");
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        logger.debug("Session Destroyed");

        try {
           // SessionControlRemote vuh = (SessionControlRemote) ISFServiceLocator.getInstance().getRemoteHome("com.isf.ejb.beans.SessionControlBean/remote");
           // SessionControl sc = (SessionControl) vuh.create();
          //todo:  //////////////////////////////////////////////////////////////////////////////////  scb.killSession(userid);
        } catch (Exception ie) {
            logger.debug("could not delete session:", ie);
        }
    }

    private String userid;

    public UserDTO() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsersession() {
        return usersession;
    }

    public void setUsersession(String usersession) {
        this.usersession = usersession;
    }

    private String usersession;




}
