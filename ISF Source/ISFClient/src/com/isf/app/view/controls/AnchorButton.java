package com.isf.app.view.controls;

import isf.controls.utils.CursorManager;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class AnchorButton extends JButton
        implements MouseListener {

    String start;
    String end;
    String hstart;
    String label;
    String range;

    public AnchorButton(String s) {
        super(s);
        start = "<html><b><font face='Trebuchet' size='13pts' color='3300CC'>";
        end = "</font></b></html>";
        hstart = "<html><b><font face='Trebuchet' size='13pts' color='00ffff'>";
        label = "";
        range = "";
        label = s;
        setActionCommand(s);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(this);
        setFocusPainted(false);
        //  setCursor(new Cursor(12));
        setMargin(new Insets(0, 0, 0, 0));
        setText(start + s + end);
    }

    public AnchorButton() {
        this("");
    }

    public void addAnchor() {
        setText(start.substring(0, start.lastIndexOf('<')) + label + end);
    }

    public void setRange(String s) {
        range = s;
    }

    public String getRange() {
        return range;
    }

    public void mouseEntered(MouseEvent mouseevent) {
        setText(hstart + label + end);
        updateUI();
        CursorManager.changeCursor(12);

    }

    public void setSelected(boolean flag) {
        super.setSelected(flag);
        if (flag) {
            setText(hstart + label + end);
        } else {
            setText(start + label + end);
        }
    }

    public void mouseExited(MouseEvent mouseevent) {
        CursorManager.changeCursor(-1);

        if (isSelected()) {
            return;
        } else {
            setText(start + label + end);
            updateUI();
            return;
        }
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
        if (com.isf.app.utils.CatalougeObserver.count == 0)
            setSelected(!isSelected());
    }

    public void mouseClicked(MouseEvent mouseevent) {
    }
}
