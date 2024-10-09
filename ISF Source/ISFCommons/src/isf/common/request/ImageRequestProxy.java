/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.common.request;

import javax.swing.ImageIcon;

/**
 *
 * @author raok1
 */
public abstract class ImageRequestProxy {
    
    public abstract ImageIcon fetchImage(String s);

    public abstract Object getImageProperties(String s);

    public abstract String saveFile(String s, String filename);
}
