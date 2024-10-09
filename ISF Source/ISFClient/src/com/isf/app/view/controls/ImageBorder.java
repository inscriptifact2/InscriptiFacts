package com.isf.app.view.controls;

import isf.controls.utils.ResolutionManager;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class ImageBorder
        implements Border {

    private Image image;
    private int height = 0;
    private Insets insets;
    private ImageIcon medalIcon = null;
    private int offset = 0, topset = 15;

    public ImageBorder(String s, boolean scale) {
        insets = null;
        ImageIcon icon = null;

        if (scale)
            icon = ResolutionManager.getInstance().getImageIcon(s);
        else
            icon = ResolutionManager.getInstance().loadImage(s);
        height = icon.getIconHeight();
        image = icon.getImage();
        Dimension resolution = ResolutionManager.getInstance().getScreen();  //new Dimension(1600,1200);//

        if (resolution.getWidth() < 1280) {
            medalIcon = ResolutionManager.getInstance().loadImage("images/medal_1024.jpg");//.getImage() ;
            offset = (int) (212 * resolution.getWidth() / 1024);
            topset = 15;
        } else if (resolution.getWidth() >= 1280 && resolution.getWidth() < 1600) {
            medalIcon = ResolutionManager.getInstance().loadImage("images/medal_1280.jpg");//.getImage() ;
            offset = (int) (280 * resolution.getWidth() / 1280);
            // topset=(int)( Math.ceil(15*resolution.getWidth()/1280));
            topset = 17;
        } else if (resolution.getWidth() >= 1600) {
            medalIcon = ResolutionManager.getInstance().loadImage("images/medal_1280.jpg");//.getImage() ;
            offset = (int) (280 * resolution.getWidth() / 1280);
            topset = 18;
        }
        if (Toolkit.getDefaultToolkit().getBestCursorSize(32, 32).width <= 16) { //16
            if (resolution.getWidth() < 1280)
                medalIcon = ResolutionManager.getInstance().loadImage("images/medal_mac_1024.jpg");
            else
                medalIcon = ResolutionManager.getInstance().loadImage("images/medal_mac.jpg");
        }

        height = (height >= medalIcon.getIconHeight()) ? height : medalIcon.getIconHeight();

    }

    public void setBorderInsets(int i, int j, int k, int l) {
        insets = new Insets(i, j, k, l);
    }

    public Insets getBorderInsets(Component component) {
        return insets;
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component component, Graphics g, int i, int j, int k, int l) {
        g.drawImage(image, 0, 10, component);

        //   Dimension resolution =  new Dimension(1600,1200);//
        Image medal = null;

        medal = medalIcon.getImage();

        g.drawImage(medal, offset, topset, component);
    }

    public int getHeight() {
        return height + topset;
    }
}
