package com.isf.app.view.controls;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ToggleButton extends JButton
        implements ActionListener {

    public ToggleButton previous;
    public ToggleButton next;
   // private ActionListener al;

    public ToggleButton() {
    }

    public ToggleButton(String s) {
        this(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s));
        setActionCommand(s.substring(0, s.indexOf('.')));
        addActionListener(this);
    }

    public ToggleButton(String s, ImageIcon imageicon) {
        super(s, imageicon);
        addActionListener(this);
    }

    public ToggleButton(ImageIcon imageicon) {
        super(imageicon);
        addActionListener(this);
    }

    private void disableLeft() {
        for (ToggleButton togglebutton = previous; togglebutton != null; togglebutton = togglebutton.previous) {
            togglebutton.setSelected(false);
        }

    }

    private void disableRight() {
        for (ToggleButton togglebutton = next; togglebutton != null; togglebutton = togglebutton.next) {
            togglebutton.setSelected(false);
        }

    }

    public void actionPerformed(ActionEvent actionevent) {
        ToggleButton togglebutton = (ToggleButton) actionevent.getSource();
        togglebutton.setSelected(true);
        togglebutton.disableLeft();
        togglebutton.disableRight();
    }

    public void setProperties(boolean flag) {
        setBorderPainted(flag);
        setFocusPainted(flag);
        setContentAreaFilled(flag);
        setMargin(new Insets(0, 0, 0, 0));
    }

    public void setSelected(boolean b)
    {
       super.setSelected(b);
      //  System.out.println("Called ToogleButton SetSelecetd: "+this.getActionCommand()+"::"+b);
    }
}
