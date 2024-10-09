/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.controls;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.controls.view.containers.PTMPanel;
import isf.controls.view.controls.ISFSlider;
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
public class ExtrapolateSourceMenu extends ControlMenu {
 
    boolean added = false;
    private ISFSlider scaleSlider = new ISFSlider(0, 20);
    private JLabel label = new JLabel("<html>Scale<br>(x0.1)</html>");
    private PTMPanel ptmw;

    public ExtrapolateSourceMenu() {
        super();
        this.setLayout(new SpringLayout());

        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));


        label.setBackground(new Color(0xd9d9d9));
        label.setForeground(new Color(0xAC5539));

        scaleSlider.setBackground(new Color(0xd9d9d9));
        scaleSlider.setForeground(new Color(0xAC5539));
        this.add(label);
        this.add(scaleSlider);

        isf.viewer.utils.SpringUtilities.makeCompactGrid(this, 1, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad

        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Extrapolate Source");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);

    }


    public void init() {
        ptmw = (PTMPanel) PTMFrameManager.getInstance().getPTMWindow();
        if (ptmw != null) {
            scaleSlider.setValue((int) ptmw.getScale() * 10);

            if (!added) {
                scaleSlider.addChangeListener(new ChangeListener() {

                    public void stateChanged(javax.swing.event.ChangeEvent e) {
                        JSlider source = (JSlider) e.getSource();
                        if (!source.getValueIsAdjusting()) {
                            float val = ((float) source.getValue() / (float) 10);
                            ptmw.setScale(val);
                            ptmw.fireTransform();
                        } else {
                        }

                    }
                });
            }
        }
    }

    public void processMouseEvent(MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void processKeyEvent(KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void menuSelectionChanged(boolean isIncluded) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    public Component getComponent() {
        return this;
    }

    public Dimension getPreferredSize() {
        return new Dimension(240, 90);
    }

    public Dimension getMinimumSize() {
        return new Dimension(230, 80);
    }

    public Dimension getMaximumSize() {
        return new Dimension(250, 100);
    }


}
