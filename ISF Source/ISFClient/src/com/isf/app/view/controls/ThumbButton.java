package com.isf.app.view.controls;

import isf.controls.plaf.IPVerticalLabelUI;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ThumbButton extends TrimmedPanel
        implements ActionListener {

    private TrimmedIconButton tib;
    private TrimmedIconButton tt;
    private Dimension dim;
    public boolean selected;
    private ActionListener al;

    public ThumbButton(ImageIcon imageicon, String s, String type) {
        selected = false;
        TrimmedPanel trimmedpanel = new TrimmedPanel();
        
        tib = new ThumbTrimmedIconButton(imageicon);
        if(type.equalsIgnoreCase("SID"))
            tt = new TrimmedIconButton(s);
        else
        {
            Image light = ResolutionManager.getInstance().loadImage("images/lightc.gif").getImage();
             tt = new TrimmedIconButton(s,light);
              if(type.equalsIgnoreCase("RTI")||type.equalsIgnoreCase("HSH"))
                 type = "RTI";
        }
        tib.setCursor(new Cursor(12));
        tt.setCursor(new Cursor(12));
        tt.setContentAreaFilled(false);
        tib.addActionListener(this);
        tt.addActionListener(this);
        tt.setFont(new Font("Arial", 1, 11));
        trimmedpanel.setLayout(new BorderLayout(0, 0));
        trimmedpanel.add(tib, "Center");
        TrimmedPanel panel = new TrimmedPanel();
        
        panel.setLayout(new BorderLayout());
        panel.add(tt,"Center");
        trimmedpanel.add(panel, "South");
        JLabel label = new JLabel(type);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.black);
        // label.setBackground(Color.black);
        label.setOpaque(true);
        label.setUI(new IPVerticalLabelUI(false));
        trimmedpanel.add(label, "West");
        add(trimmedpanel);
    }

    public ThumbButton(String s, String type) {
        selected = false;
        TrimmedPanel trimmedpanel = new TrimmedPanel();
        tib = new ThumbTrimmedIconButton();
        if(type.equalsIgnoreCase("SID"))
            tt = new TrimmedIconButton(s);
        else
        {
            Image light = ResolutionManager.getInstance().loadImage("images/lightc.gif").getImage();
             tt = new TrimmedIconButton(s,light);
             if(type.equalsIgnoreCase("RTI")||type.equalsIgnoreCase("HSH"))
                 type = "RTI";
        }
        tib.setCursor(new Cursor(12));
        tt.setCursor(new Cursor(12));
        tt.setContentAreaFilled(false);
        tib.addActionListener(this);
        tt.addActionListener(this);
        tt.setFont(new Font("Arial", 1, 11));
        trimmedpanel.setLayout(new BorderLayout(0, 0));
        trimmedpanel.add(tib, "Center");
        TrimmedPanel panel = new TrimmedPanel();
        panel.setLayout(new BorderLayout());
        panel.add(tt,"Center");
        trimmedpanel.add(panel, "South");
        JLabel label = new JLabel(type);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.black);
        // label.setBackground(Color.black);
        label.setOpaque(true);
        label.setUI(new IPVerticalLabelUI(false));
        trimmedpanel.add(label, "West");
        add(trimmedpanel);
    }

    public Dimension getPreferredSize() {
        return new Dimension(136, 158);
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
    }

    public void actionPerformed(ActionEvent actionevent) {
        selected = !selected;
        if (al != null) {
            al.actionPerformed(new ActionEvent(this, 1, ""));
        }
    }

    public void setIcon(ImageIcon ic) {
        tib.setIcon(ic);
        tib.validate();
        tib.repaint();
    }

    public void setText(String st) {
        tib.setText(st);
    }
    
   
	


}
