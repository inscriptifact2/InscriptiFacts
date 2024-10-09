/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.containers;

import isf.controls.view.controls.ISFProgressBar;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 * @author IN030223
 */
public class StatusPanel extends JPanel {
    private ISFProgressBar bar;

    public StatusPanel() {
        bar = new ISFProgressBar(Color.red);
       // bar.setAnimationSpeed(ISFProgressBar.FAST);
        bar.setText("Ready");
        bar.setBackgroundColor(Color.black);
        bar.setForegroundColor(Color.white);
        this.setLayout(new FlowLayout());
        add(bar);
        setBackground(Color.black);
        this.setVisible(false);
    }

    public void showStatus(String text) {
        if (!this.isVisible())
            setVisible(true);
        bar.setText(text);
    }

    public void start() {
        bar.startAnimation();
    }

    public void stop() {
        bar.stopAnimation();
        setVisible(false);
    }


}
