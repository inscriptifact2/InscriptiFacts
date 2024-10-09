/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.net.request;

import isf.common.request.RequestProxy;

/**
 *
 * @author raok1
 */
public class NetManager {
    
   public static void initialize(){
       RequestProxy.irp =  ImageRequestObject.getInstance();
       RequestProxy.prp = PTMRequestDispatcher.getInstance();
       RequestProxy.rdp = RequestDispatcherObject.getInstance();
   }
    
}
