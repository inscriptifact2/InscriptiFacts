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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 * @author IN030223
 */
public class DualLightsControlMenu extends ControlMenu implements ItemListener {


    boolean added = false;
    private ISFSlider l1lumSlider = new ISFSlider(0, 40);
    private ISFSlider l2lumSlider = new ISFSlider(0, 40);
    private ISFSlider distanceSlider = new ISFSlider(0, 100);
    private JLabel distanceLabel = new JLabel("<html>distance<br>(x1000)</html>");
    private JRadioButton lightOne = new JRadioButton("<html>Light 1 <br>lum.(x0.1)</html>");
    private JRadioButton lightTwo = new JRadioButton("<html>Light 2 <br>lum.(x0.1)</html>");
    private ButtonGroup group = new ButtonGroup();
    private PTMPanel ptmw;

    public void setDistanceEnabled(boolean b) {
        distanceSlider.setEnabled(b);
        distanceLabel.setEnabled(b);
    }

    public DualLightsControlMenu() {

        super();
        this.setLayout(new SpringLayout());

        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));

        distanceLabel.setBackground(new Color(0xd9d9d9));
        distanceLabel.setForeground(new Color(0xAC5539));

        l1lumSlider.setBackground(new Color(0xd9d9d9));
        l1lumSlider.setForeground(new Color(0xAC5539));
        l2lumSlider.setBackground(new Color(0xd9d9d9));
        l2lumSlider.setForeground(new Color(0xAC5539));
        distanceSlider.setBackground(new Color(0xd9d9d9));
        distanceSlider.setForeground(new Color(0xAC5539));
        lightOne.setBackground(new Color(0xd9d9d9));
        lightOne.setForeground(Color.GREEN);
        lightOne.setActionCommand("Light 1");
        lightTwo.setBackground(new Color(0xd9d9d9));
        lightTwo.setForeground(Color.RED);
        lightTwo.setActionCommand("Light 2");

        group.add(lightOne);
        group.add(lightTwo);


        this.setDistanceEnabled(false);
        this.add(lightOne);
        this.add(l1lumSlider);
        this.add(lightTwo);
        this.add(l2lumSlider);


        this.add(distanceLabel);
        this.add(distanceSlider);


        isf.viewer.utils.SpringUtilities.makeCompactGrid(this, 3, 2, // rows, cols
                1, 1, // initX, initY
                1, 1); // xPad, yPad

        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "General");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);

    }

    public void init() {
        ptmw = (PTMPanel) PTMFrameManager.getInstance().getPTMWindow();
        if (ptmw != null) {
            l1lumSlider.setValue((int) (ptmw.getLuminance(0) * 10));
            if (ptmw.getPTM() != null) {
                distanceSlider.setValue(ptmw.getPTM().getZ() / 1000);
            } else {
                distanceSlider.setValue(1);
            }
            l2lumSlider.setValue((int) (ptmw.getLuminance(1) * 10));

            l1lumSlider.addChangeListener(new ChangeListener() {

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


            l2lumSlider.addChangeListener(new ChangeListener() {

                public void stateChanged(javax.swing.event.ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        float val = ((float) source.getValue() / (float) 10);
                        ptmw.setLuminance(val, 1);
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
            lightOne.addItemListener(this);
            lightTwo.addItemListener(this);
        }

        lightOne.setSelected(ptmw.getActiveLight() == 0 ? true : false);
        lightTwo.setSelected(ptmw.getActiveLight() == 1 ? true : false);
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
        return new Dimension(240, 183);
    }

    public Dimension getMinimumSize() {
        return new Dimension(230, 173);
    }

    public Dimension getMaximumSize() {
        return new Dimension(250, 193);
    }

    public void itemStateChanged(ItemEvent e) {
        JRadioButton radio = (JRadioButton) e.getSource();
        if (e.getStateChange() == ItemEvent.SELECTED && radio.getActionCommand().equalsIgnoreCase("Light 1")) {

            ptmw.setActiveLight(0);
        } else if (e.getStateChange() == ItemEvent.SELECTED && radio.getActionCommand().equalsIgnoreCase("Light 2")) {

            ptmw.setActiveLight(1);
        }
    }

    public void resetControls() {
        ptmw = (PTMPanel) PTMFrameManager.getInstance().getPTMWindow();


        if (ptmw != null && ptmw.getPTM() != null) {
            l1lumSlider.setValue((int) (ptmw.getLuminance(0) * 10));
            distanceSlider.setValue(ptmw.getPTM().getZ() / 1000);
            l2lumSlider.setValue((int) (ptmw.getLuminance(1) * 10));
            lightOne.setSelected(ptmw.getActiveLight() == 0 ? true : false);
            lightTwo.setSelected(ptmw.getActiveLight() == 1 ? true : false);

        }
    }
}