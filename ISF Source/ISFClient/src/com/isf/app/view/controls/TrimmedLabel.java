package com.isf.app.view.controls;

import java.awt.Font;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JLabel;

public class TrimmedLabel extends JLabel {

    private Insets ins;

    public TrimmedLabel() {
        ins = new Insets(0, 0, 0, 0);
    }

    public TrimmedLabel(Insets insets) {
        ins = new Insets(0, 0, 0, 0);
        ins = insets;
    }

    public TrimmedLabel(String s) {
        super(s);
        ins = new Insets(0, 0, 0, 0);
    }

    public TrimmedLabel(String s, Insets insets) {
        super(s);
        ins = new Insets(0, 0, 0, 0);
        ins = insets;
    }

    public TrimmedLabel(Icon icon) {
        super(icon);
        ins = new Insets(0, 0, 0, 0);
    }

    public TrimmedLabel(String s, int i) {
        super(s, i);
        ins = new Insets(0, 0, 0, 0);
    }

    public TrimmedLabel(Icon icon, int i) {
        super(icon, i);
        ins = new Insets(0, 0, 0, 0);
    }

    public TrimmedLabel(String s, Icon icon, int i) {
        super(s, icon, i);
        ins = new Insets(0, 0, 0, 0);
    }

    public void setInsets(Insets insets) {
        ins = insets;
    }

    public Insets getInsets() {
        return ins;
    }

    public TrimmedLabel(String s, Font f) {
        super(s);
        ins = new Insets(0, 0, 0, 0);
        super.setFont(f);
    }

}
