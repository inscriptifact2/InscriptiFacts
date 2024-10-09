/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.common.request;

/**
 *
 * @author raok1
 */
public class RequestProxy {
    
    public static RequestDispatcherProxy rdp;
    public static ImageRequestProxy irp;
    public static PTMRequestProxy prp;
    
    public static RequestDispatcherProxy getRequestDispatcher(){
        return rdp;
    }
    
    public static ImageRequestProxy getImageRequestProxy(){
        return irp;
    }
    
    public static PTMRequestProxy getPTMRequestProxy(){
        return prp;
    }
    
    
    
}
