/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.containers;


import java.awt.Color;
import javax.swing.JPanel;

/**
 * @author IN030223
 */
public abstract class ButtonPopUp extends JPanel {

    public ButtonPopUp() {
        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));
    }

    public abstract void init();
    
    public abstract void releaseControls();
    

}
