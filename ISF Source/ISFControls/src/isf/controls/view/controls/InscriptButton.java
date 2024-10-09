package isf.controls.view.controls;

import isf.controls.utils.ResolutionManager;

import javax.swing.*;
import java.awt.*;

public class InscriptButton extends JButton {

    String imageUrl;

    public InscriptButton(String s) {
        setActionCommand(s.substring(0, s.indexOf('.')));
        setBorder(new InscriptBorder(Color.white));
        setFont(new Font("Arial", 1, 14));
        setIcon(ResolutionManager.getInstance().getImageIcon(s));
        imageUrl = s;
    }

    public void setScaledStatus(boolean flag) {
        if (flag) {
            setIcon(ResolutionManager.getInstance().loadImage(imageUrl));
        }
    }

    public InscriptButton(ImageIcon imageicon) {
        setBorder(new InscriptBorder(Color.white));
        setFont(new Font("Arial", 1, 14));
        setIcon(imageicon);
    }

    public Dimension getPreferredSize() {
        return new Dimension(getIcon().getIconWidth() + 2, getIcon().getIconHeight());
    }
}
