/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.common.request;

import isf.common.utils.PTMConfig;
import javax.swing.ImageIcon;

/**
 *
 * @author raok1
 */
public abstract class RequestDispatcherProxy {
    public String sessionid;
    public abstract Object handleQueryEvent(String s);
    public abstract ImageIcon fetchImage(String s);
    public abstract Object getImageProperties(String s);
    public  abstract String saveFile(String s, String filename);
    public abstract int checkConnection() ;
   // public abstract static void addLogoutListener(LogoutListener lo);
    public abstract PTMConfig getPTMConfig(String ptmName) ;
   // public abstract InputStream executeMethod(GetMethod method, int status) ;
    public RequestDispatcherProxy(){
        sessionid="NEW";
    }
    
}
