package com.isf.app.models;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class ImageThumbData
        implements ThumbData, Serializable {

    private String header;
    private String label;
    private String photoid;
    private String size;
    private String title;
    private String digitalid;
    private ImageIcon ii;
    private ImageIcon qli;
    private String filesize;
    private String type = "SID";

    public ImageThumbData() {
    }

    public String getHeader() {
        return header;
    }

    public ImageIcon getIcon() {
        return ii;
    }

    public String getLabel() {
        return label;
    }

    public String getPhotoTitle() {
        return title;
    }

    public String getPhotoIdentificationNumber() {
        return photoid;
    }

    public String getArchivalFileSize() {
        return size;
    }

    public void setHeader(String s) {
        header = s;
    }

    public void setIcon(ImageIcon imageicon) {
        ii = imageicon;
    }

    public void setLabel(String s) {
        label = s;
    }

    public void setPhotoIdentificationNumber(String s) {
        photoid = s;
    }

    public void setArchivalFileSize(String s) {
        size = s;
    }

    public void setPhotoTitle(String s) {
        title = s;
    }

    public String getISFDigitalObjIdentifier() {
        return digitalid;
    }

    public void setISFDigitalObjIdentifier(String s) {
        digitalid = s;
    }

    public String toString() {
        return photoid;
    }

    public int hashCode() {
        return photoid.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ImageThumbData)) {
            return false;
        } else {
            return ((ImageThumbData) obj).photoid.equals(photoid);
        }
    }

    public String getActualSidSize() {
        return filesize;
    }

    public void setActualSidSize(String ss) {
        filesize = ss;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
