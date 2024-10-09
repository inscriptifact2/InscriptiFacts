/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.isf.app.view.controls;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SliderMenuItem extends JSlider implements MenuElement {

    public SliderMenuItem() {
        setBorder(new CompoundBorder(new TitledBorder("Control"), new EmptyBorder(10, 10, 10, 10)));

        setMinimum(0);
        setMaximum(100);
        setPaintTicks(true);
        setValueIsAdjusting(true);
    }

    public void processMouseEvent(MouseEvent e, MenuElement path[], MenuSelectionManager manager) {
    }

    public void processKeyEvent(KeyEvent e, MenuElement path[], MenuSelectionManager manager) {
    }

    public void menuSelectionChanged(boolean isIncluded) {
    }

    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    public Component getComponent() {
        return this;
    }


}