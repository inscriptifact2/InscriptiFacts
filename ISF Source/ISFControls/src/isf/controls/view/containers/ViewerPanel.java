/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.containers;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.plaf.IPVerticalLabelUI;
import isf.controls.view.listeners.SizeChangeListener;

import javax.swing.*;
import java.awt.*;

//import javax.swing.JProgressBar;

/**
 * @author IN030223
 */
public class ViewerPanel extends JPanel implements SizeChangeListener {
    private ImagePanel panel;
    private StatusPanel status;
    private String type;
    private JLabel label;

    public ViewerPanel(String type) {
        setLayout(new BorderLayout());
        this.type = type;
        label = new JLabel(type);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(new Color(0, 0, 0));
        label.setUI(new IPVerticalLabelUI(false));
        status = new StatusPanel();

        if (type.equalsIgnoreCase("SID")) {
            panel = new SidPanel();
        } else if (type.equalsIgnoreCase("RTI")) {
            panel = PTMFrameManager.getNewPTMPanel();
        }
         panel.setSizeNotifier(this);

        add(label, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
    }

    public ImagePanel getPanel() {
        return panel;
    }

    public StatusPanel getStatus() {
        return status;
    }

    
    public void updateSize(String level) {
        label.setText(type + "  " + level);
        label.repaint();
    }


}
