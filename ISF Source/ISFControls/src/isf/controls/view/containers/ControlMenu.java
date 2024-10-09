/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.controls.view.containers;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.listeners.PTMFrameChangeListener;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.MenuElement;

public abstract class ControlMenu extends JPanel implements MenuElement,PTMFrameChangeListener {


    public abstract void init();
    


    public void setEnabled(boolean b) {
        Component[] comp = this.getComponents();
        for (int i = 0; i < comp.length; i++) {
            comp[i].setEnabled(b);
        }
    }

    public void releaseControlMenu() {
        Component[] comp = this.getComponents();
        for (int i = 0; i < comp.length; i++) {
            comp[i] = null;
        }
        this.removeAll();
        PTMFrameManager.getInstance().releaseChangeListener(this);
    }
	
	public void resetControls(){}
}