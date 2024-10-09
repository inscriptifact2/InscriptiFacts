/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.controls;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.controls.view.containers.PTMPanel;
import isf.controls.view.controls.ISFSlider;
import isf.viewer.utils.SpringUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 * @author IN030223
 */
public class GeneralControlMenu extends ControlMenu {

    boolean added = false;
    private ISFSlider lumSlider = new ISFSlider(0, 40);
    private JLabel label = new JLabel("<html>luminance<br>(x0.1)</html>");
    private ISFSlider distanceSlider = new ISFSlider(0, 100);
    private JLabel distanceLabel = new JLabel("<html>distance<br>(x1000)</html>");
    private PTMPanel ptmw;

    public void setDistanceEnabled(boolean b) {
        distanceSlider.setEnabled(b);
        distanceLabel.setEnabled(b);
    }

    public GeneralControlMenu() {

        super();
        this.setLayout(new SpringLayout());

        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));


        label.setBackground(new Color(0xd9d9d9));
        label.setForeground(new Color(0xAC5539));
        distanceLabel.setBackground(new Color(0xd9d9d9));
        distanceLabel.setForeground(new Color(0xAC5539));

        lumSlider.setBackground(new Color(0xd9d9d9));
        lumSlider.setForeground(new Color(0xAC5539));
        distanceSlider.setBackground(new Color(0xd9d9d9));
        distanceSlider.setForeground(new Color(0xAC5539));


        this.add(label);
        this.add(lumSlider);
        this.setDistanceEnabled(false);
        this.add(distanceLabel);
        this.add(distanceSlider);


        SpringUtilities.makeCompactGrid(this, 2, 2, // rows, cols
                1, 1, // initX, initY
                1, 1); // xPad, yPad

        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "General");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);

    }

    public void init() {
       
        ptmw = (PTMPanel) PTMFrameManager.getInstance().getPTMWindow();
        if (ptmw != null) {
         
            lumSlider.setValue((int) (ptmw.getLuminance(0) * 10));
            if(ptmw.getPTM()!=null)
                distanceSlider.setValue(ptmw.getPTM().getZ() / 1000);
            else
                distanceSlider.setValue(1);
            if (!added) {
                lumSlider.addChangeListener(new ChangeListener() {

                    public void stateChanged(javax.swing.event.ChangeEvent e) {
                       
                        JSlider source = (JSlider) e.getSource();
                        if (!source.getValueIsAdjusting()) {
                            float val = ((float) source.getValue() / (float) 10);
                            ptmw.setLuminance(val, 0);
                            ptmw.fireTransform();
                        } else {
                        }
                    }
                });

                distanceSlider.addChangeListener(new ChangeListener() {

                    public void stateChanged(javax.swing.event.ChangeEvent e) {
                        JSlider source = (JSlider) e.getSource();
                        if (!source.getValueIsAdjusting()) {
                            int val = (int) source.getValue() * 1000;
                            ptmw.getPTM().setZ(val);
                            ptmw.fireTransform();
                        } else {
                        }
                        distanceSlider.repaint();
                    }
                });
                added = true;
            }


        }
        PTMFrameManager.getInstance().registerChangeListener(this, ptmw);
    }

    public void processMouseEvent(MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }

    public void processKeyEvent(KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }

    public void menuSelectionChanged(boolean isIncluded) {
    }

    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    public Component getComponent() {
        return this;
    }

    public Dimension getPreferredSize() {
        return new Dimension(240, 120);
    }

    public Dimension getMinimumSize() {
        return new Dimension(230, 110);
    }

    public Dimension getMaximumSize() {
        return new Dimension(250, 130);
    }

    public void resetControls() {
        ptmw = (PTMPanel) PTMFrameManager.getInstance().getPTMWindow();
        if (ptmw != null && ptmw.getPTM() != null) {
            lumSlider.setValue((int) (ptmw.getLuminance(0) * 10));
            distanceSlider.setValue(ptmw.getPTM().getZ() / 1000);
        }
    }
}