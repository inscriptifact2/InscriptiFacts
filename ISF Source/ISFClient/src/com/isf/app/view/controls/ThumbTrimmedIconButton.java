package com.isf.app.view.controls;

import java.awt.Dimension;
import javax.swing.ImageIcon;

public class ThumbTrimmedIconButton extends TrimmedIconButton {

    public TrimmedIconButton left;
    public TrimmedIconButton right;
    

    public ThumbTrimmedIconButton() {
    }

    public ThumbTrimmedIconButton(String s, String s1, String s2, String s3) {
        super(s, s1, s2, s3);
    }

    public ThumbTrimmedIconButton(String s) {
        super(s);
    }

    public ThumbTrimmedIconButton(ImageIcon imageicon) {
        super(imageicon);
    }



    public Dimension getPreferredSize() {
        return new Dimension(128, 128);
    }

    
}
