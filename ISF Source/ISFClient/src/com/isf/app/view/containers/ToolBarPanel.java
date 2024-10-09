package com.isf.app.view.containers;

import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.ToolBarManager;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.JToolBar;

public class ToolBarPanel extends JToolBar {

    private TrimmedIconButton lastAdded;
    Vector v;

    public ToolBarPanel() {
        v = new Vector();
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ToolBarManager.getInstance();
        //etBorder(new LineBorder(Color.blue, 1));
    }

    public Component add(Component component, boolean notSeparator) {
        TrimmedIconButton trimmediconbutton = (TrimmedIconButton) component;
        if (notSeparator) {
            if (lastAdded != null) {
                trimmediconbutton.left = lastAdded;
                lastAdded.right = trimmediconbutton;
            } else {
                trimmediconbutton.removeActionListener(trimmediconbutton);
            }
            v.addElement(lastAdded = trimmediconbutton);
        }
        return super.add(component);
    }

    public void enable(int i) {
        ToolBarManager.getInstance().setEnable(i);
        /*if (i == 5) {
            ToolBarManager.getInstance().setEnable(i + 1);
        }*/
        try {
            TrimmedIconButton trimmediconbutton = (TrimmedIconButton) v.elementAt(i);
            trimmediconbutton.setSelected(true);
            trimmediconbutton.setEnabled(true);
            setRight(i);
            setLeft(i);
        } catch (Exception exception) {
        }
    }

    public void disable(int i) {
        ToolBarManager.getInstance().setDisable(i);
        /* if (i == 5) {
             ToolBarManager.getInstance().setDisable(i + 1);
         }*/
        try {
            TrimmedIconButton trimmediconbutton = (TrimmedIconButton) v.elementAt(i);
            trimmediconbutton.setSelected(false);
            trimmediconbutton.setEnabled(false);
            setRight(i);
            setLeft(i);
        } catch (Exception exception) {
        }
    }

    public void setRight(int i) {
        ToolBarManager toolbarmanager = ToolBarManager.getInstance();
        for (int j = 0; j < i; j++) {
            TrimmedIconButton trimmediconbutton = (TrimmedIconButton) v.elementAt(j);
            boolean flag = toolbarmanager.isEnabled(j);
            trimmediconbutton.setEnabled(flag);
            trimmediconbutton.setSelected(false);
        }

    }

    public void setLeft(int i) {
        ToolBarManager toolbarmanager = ToolBarManager.getInstance();
        for (int j = i + 1; j < v.size(); j++) {
            TrimmedIconButton trimmediconbutton = (TrimmedIconButton) v.elementAt(j);
            trimmediconbutton.setEnabled(toolbarmanager.isEnabled(j));
            trimmediconbutton.setSelected(false);
        }

    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }
}
