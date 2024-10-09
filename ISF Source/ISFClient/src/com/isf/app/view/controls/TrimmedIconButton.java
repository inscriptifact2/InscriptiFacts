package com.isf.app.view.controls;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class TrimmedIconButton extends JButton
        implements ActionListener {

    public TrimmedIconButton left;
    public TrimmedIconButton right;

    public TrimmedIconButton() {
        addActionListener(this);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setMargin(new Insets(5, 5, 5, 5));
    }

    public TrimmedIconButton(String s, String s1, String s2, String s3) {
        this();
        setActionCommand(s);
        ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s1);
        ImageIcon imageicon1 = isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s2);
        ImageIcon imageicon2 = isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s3);
        setSelectedIcon(imageicon);
        setDisabledIcon(imageicon1);
        setIcon(imageicon2);
        setFocusPainted(false);
        setBackground(new Color(0xac5539));
        setMargin(new Insets(5, 5, 5, 5));
        setBorderPainted(false);
        setContentAreaFilled(false);
        //System.out.println(s+" -- "+getPreferredSize() );
    }

    public TrimmedIconButton(String s) {
        super(s);
        setBorderPainted(false);
        setFocusPainted(false);
        setMargin(new Insets(5, 5, 5, 5));
        setHorizontalAlignment(2);
    }
    
    private Image additional;
    public TrimmedIconButton(String s, Image light) {
        super(s);
        additional = light;
        setBorderPainted(false);
        setFocusPainted(false);
        setMargin(new Insets(5, 5, 5, 5));
        setHorizontalAlignment(2);
    }

    public TrimmedIconButton(ImageIcon imageicon) {
        super(imageicon);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setMargin(new Insets(0, 0, 0, 0));
    }

    public TrimmedIconButton(ImageIcon ic, ImageIcon rollover) {
        this(ic);
        this.setRolloverIcon(rollover);
        this.setRolloverEnabled(true);

    }

    public TrimmedIconButton(String ac, String eb, String sb) {
        this();
        setActionCommand(ac);
        ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().loadImage(eb);
        ImageIcon imageicon1 = isf.controls.utils.ResolutionManager.getInstance().loadImage(sb);
        setSelectedIcon(imageicon1);
        setIcon(imageicon);
        setBorderPainted(false);
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
    }

    public TrimmedIconButton(String ac, String eb, String sb, int fill) {
        this();
        setActionCommand(ac);
        ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().loadImage(eb);
        ImageIcon imageicon1 = isf.controls.utils.ResolutionManager.getInstance().loadImage(sb);
        setSelectedIcon(imageicon1);
        setIcon(imageicon);
        setBorderPainted(false);
        setFocusPainted(false);
        setBackground(new Color(0xac5539));
        setMargin(new Insets(0, 0, 0, 0));
    }

    public void disableRight() {
        if (getCountRight() == 1) {
            enableRight();
            return;
        }
        for (TrimmedIconButton trimmediconbutton = right; trimmediconbutton != null; trimmediconbutton = trimmediconbutton.right) {
            trimmediconbutton.setEnabled(false);
            trimmediconbutton.setSelected(false);
        }

    }

    private void enableRight() {
        right.setEnabled(true);
        right.setSelected(false);
    }

    private int getCountRight() {
        int i = 0;
        for (TrimmedIconButton trimmediconbutton = right; trimmediconbutton != null; trimmediconbutton = trimmediconbutton.right) {
            i++;
        }

        return i;
    }

    public void actionPerformed(ActionEvent actionevent) {
        //setSelected(true);
    }

    public void enableLeft() {
        for (TrimmedIconButton trimmediconbutton = left; trimmediconbutton != null; trimmediconbutton = trimmediconbutton.left) {
            trimmediconbutton.setSelected(false);
            trimmediconbutton.setEnabled(true);
        }

    }

    public void setSelected(boolean flag) {
        super.setSelected(flag);
    }

    public Dimension getPreferredSize() {
        Icon icon = getIcon();
        if (icon == null) {
            return super.getPreferredSize();
        } else {
            return new Dimension(icon.getIconWidth(), icon.getIconHeight());
        }
    }

    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    public int getHeight() {
        if (getIcon() != null)
            return getIcon().getIconHeight();
        else
            return super.getHeight();
    }

     public void paint(Graphics g){
         super.paint(g);
         if (additional != null) {
          Rectangle r = getBounds();
          g.drawImage(additional,(r.width - additional.getWidth(this)-4),r.height-additional.getHeight(this)-4,this);
         }

     }
}
