package com.isf.app.view.containers;

import com.isf.app.view.controls.ToggleButton;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ToggleButtonPanel extends TrimmedPanel {

    private ToggleButton lastAdded;
    private ActionListener al;

    public ToggleButtonPanel() {
        lastAdded = new ToggleButton();
        al = null;
    }

    public ToggleButtonPanel(Insets insets) {
        super(insets);
        lastAdded = new ToggleButton();
        al = null;
    }

    public Component add(Component component) {
        ToggleButton togglebutton = (ToggleButton) component;
        if (lastAdded != null) {
            lastAdded.next = togglebutton;
            togglebutton.previous = lastAdded;
        }
        lastAdded = togglebutton;
        togglebutton.addActionListener(al);
        return super.add(component);
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        int i = 0;
        for (int j = getComponentCount(); i < j; i++) {
            ToggleButton togglebutton = (ToggleButton) getComponent(i);
            togglebutton.addActionListener(actionlistener);
        }

    }

    public void reset() {
        int i = 0;
        for (int j = getComponentCount(); i < j; i++) {
            ToggleButton togglebutton = (ToggleButton) getComponent(i);
            togglebutton.setSelected(false);
        }
    }
}
