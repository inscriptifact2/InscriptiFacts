package com.isf.app.view.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//import javax.swing.border.*;

public class CatalougeRendererLabel extends JLabel {

    private int width = 0;
    private int height = 0;
    //  private String str;

    public CatalougeRendererLabel(String s) {
        super(s);
        setBackground(new Color(0));
        setForeground(new Color(0xffffff));
        setIcon(new ImageIcon(s));

    }

    public CatalougeRendererLabel(String s, int i, int j) {
        super(s);
        width = i;
        height = j;
        setBackground(new Color(0));
        setForeground(new Color(0xffffff));
        setIcon(new ImageIcon(s));
        //    str = s;
        //setBorder(new LineBorder(Color.red,1));
    }

    public Dimension getPreferredSize() {
        if (width > 0 && height > 0)
            return new Dimension(width, height);
        else
            return super.getPreferredSize();
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 10);
    }
    /*  public void paintBorder(Graphics g)
      {
         if(str.equals(""))
         {
          g.setColor(Color.white);
          g.drawLine(0,0,width,0);
          g.setColor(Color.red);
          g.drawLine(0,height,width,height);
         }
          //System.out.println("DrawnLine");
      }*/
}
