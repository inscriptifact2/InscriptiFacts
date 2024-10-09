package com.isf.app.view.containers;

import isf.controls.view.containers.TrimmedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Mar 20, 2005
 * Time: 10:25:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuickImagePanel extends TrimmedPanel {

    private ImageIcon image;


    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public Dimension getPreferredSize() {
        return new Dimension(512, 512);
    }

    public void paint(Graphics g) {
        Image offImg = createImage(512, 512);
        Graphics offScreen = offImg.getGraphics();
        offScreen.setColor(Color.black);
        offScreen.fillRect(0, 0, 512, 512);
        if (image != null) {
            int ax = image.getIconWidth();
            int ay = image.getIconHeight();
            offScreen.drawImage(image.getImage(), Math.abs((ax - 512) / 2), Math.abs((ay - 512) / 2), this);

        }

        g.drawImage(offImg, 0, 0, this);

    }


    /*PSVM(String args[]){
        JFrame f = new JFrame();
        f.setSize(700,700);
        f.getContentPane().setLayout(null);
        QuickImagePanel qip = new QuickImagePanel() ;
        qip.setImage(new ImageIcon("f:\\qvimages\\DSS_3Q15_CT16_RCB_Tq.jpg"));
        qip.setBounds(10,10,512,512);
        f.getContentPane().add(qip);
        f.show();

    }*/
}
