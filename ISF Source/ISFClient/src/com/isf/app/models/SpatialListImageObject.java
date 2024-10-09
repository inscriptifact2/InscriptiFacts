package com.isf.app.models;

import isf.common.view.gui.SpatialListImage;
import javax.swing.ImageIcon;

public class SpatialListImageObject
        implements SpatialListImage {

    private String textView;
    private String title;
    private ImageIcon textViewImage;
    private String indexMapName;
    private ImageIcon indexMapImage;

    public SpatialListImageObject() {
        indexMapImage = null;
    }

    public SpatialListImageObject(String s, ImageIcon imageicon) {
        indexMapImage = null;
        textView = s;
        textViewImage = imageicon;
    }

    public SpatialListImageObject(String s, String s1, ImageIcon imageicon) {
        indexMapImage = null;
        textView = s;
        title = s1;
        textViewImage = imageicon;
    }

    public void setTextView(String s) {
        textView = s;
    }

    public String getTextView() {
        return textView;
    }

    public void setTitle(String s) {
        title = s;
    }

    public String getTitle() {
        return title;
    }

    public void setTextViewImage(ImageIcon imageicon) {
        textViewImage = imageicon;
    }

    public ImageIcon getTextViewImage() {
        return textViewImage;
    }

    public void setIndexMapName(String s) {
        indexMapName = s;
    }

    public String getIndexMapName() {
        return indexMapName;
    }
}
