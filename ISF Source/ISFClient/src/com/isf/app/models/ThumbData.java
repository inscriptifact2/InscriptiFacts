package com.isf.app.models;

import java.io.Serializable;
import javax.swing.ImageIcon;

public interface ThumbData
        extends Serializable {

    public abstract String getHeader();

    public abstract ImageIcon getIcon();

    public abstract void setIcon(ImageIcon i);

    public abstract String getLabel();

    public abstract String getPhotoIdentificationNumber();

    public abstract String getISFDigitalObjIdentifier();

    public abstract String getPhotoTitle();

    public abstract String getArchivalFileSize();

    public abstract boolean equals(Object obj);

    public abstract String getActualSidSize();

    public String getType();

    public void setType(String type);
}
