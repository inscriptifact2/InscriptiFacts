/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.controls;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.ptm.controls.PTMWindow;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author IN030223
 */
public class SpecularControlMenu extends ControlMenu {

    ISFSliderControl kSpecSlider = new ISFSliderControl(0, 100,0.0f,1.0f);
    ISFSliderControl kDiffSlider = new ISFSliderControl(0, 100,0.0f,1.0f);
    ISFSliderControl expSlider = new ISFSliderControl(0, 100,0,150);
    JLabel kSpecLabel = new JLabel("Shine");
    JLabel kDiffLabel = new JLabel("Diffuse Shading");
    JLabel expLabel = new JLabel("Highlight Size");
    PTMWindow pf;

    public SpecularControlMenu() {
        super();

        this.setLayout(new SpringLayout());
        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));

        kSpecLabel.setBackground(new Color(0xd9d9d9));
        kSpecLabel.setForeground(new Color(0xAC5539));
        kDiffLabel.setBackground(new Color(0xd9d9d9));
        kDiffLabel.setForeground(new Color(0xAC5539));
        expLabel.setBackground(new Color(0xd9d9d9));
        expLabel.setForeground(new Color(0xAC5539));

        kSpecSlider.setBackground(new Color(0xd9d9d9));
        kSpecSlider.setForeground(new Color(0xAC5539));
        kDiffSlider.setBackground(new Color(0xd9d9d9));
        kDiffSlider.setForeground(new Color(0xAC5539));
        expSlider.setBackground(new Color(0xd9d9d9));
        expSlider.setForeground(new Color(0xAC5539));

        this.add(kSpecLabel);
        this.add(kSpecSlider);
        this.add(kDiffLabel);
        this.add(kDiffSlider);
        this.add(expLabel);
        this.add(expSlider);

        isf.viewer.utils.SpringUtilities.makeCompactGrid(this, 3, 2, // rows, cols
                1, 1, // initX, initY
                1, 1); // xPad, yPad

        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Specular");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);

    }

    public void init() {

        pf = PTMFrameManager.getInstance().getPTMWindow();

  
         kSpecSlider.setFValue(pf.getKSpec() );
        kDiffSlider.setFValue(pf.getKDiff() );
        expSlider.setIValue(pf.getExp());

        kSpecSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(javax.swing.event.ChangeEvent e) {
                kSpecSliderStateChanged(e);
            }
        });

        kDiffSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(javax.swing.event.ChangeEvent e) {
                kDiffStateChanged(e);
            }
        });

        expSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(javax.swing.event.ChangeEvent e) {
                expStateChanged(e);
            }
        });
        PTMFrameManager.getInstance().registerChangeListener(this,pf);

    }

    private void expStateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            //int val = source.getValue();
            int val = ((ISFSliderControl) source).getIValue();
            pf.setExp(val);
            pf.forceUpdate();
            pf.fireTransform();
        } else {
            // pf.getPTMCanvas().repaint();
        }
    }

    private void kDiffStateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            // int val = (int) source.getValue();
            // pf.setKDiff(((float) val) / kDiffSlider.getMaximum());
            pf.setKDiff(((ISFSliderControl) source).getFValue());
            pf.fireTransform();
        } else {
            // pf.getPTMCanvas().repaint();
        }
    }

    private void kSpecSliderStateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            // int val = (int) source.getValue();
            // pf.setKSpec(((float) val) / kSpecSlider.getMaximum());
            pf.setKSpec(((ISFSliderControl) source).getFValue());
            pf.fireTransform();
        // pf.repaint();
        } else {
            // pf.getPTMCanvas().repaint();
        }
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
        return new Dimension(240, 200);
    }

    public Dimension getMinimumSize() {
        return new Dimension(230, 190);
    }

    public Dimension getMaximumSize() {
        return new Dimension(250, 210);
    }

    public void resetControls() {
        pf = PTMFrameManager.getInstance().getPTMWindow();
        if (pf != null) {
            kSpecSlider.setFValue(pf.getKSpec());
            kDiffSlider.setFValue(pf.getKDiff());
            expSlider.setIValue(pf.getExp());
        }

    }
}
