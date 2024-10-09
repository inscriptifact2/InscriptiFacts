/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.common.request;

/**
 *
 * @author raok1
 */
public abstract class PTMRequestProxy {
    
     public abstract  byte[] getImageCoeff(String file, int index, int band, int fileType);
    
}
