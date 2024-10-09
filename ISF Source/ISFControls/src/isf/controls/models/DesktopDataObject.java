package isf.controls.models;

//import com.isf.common.Debug;

import isf.common.utils.PTMConfig;


public class DesktopDataObject {

    public String header;
    public String displayName;
    public String fileName;
    public boolean added;
    public String photoIdenNo;
    private PTMConfig config;
    private boolean copy = false;

    public DesktopDataObject() {
        header = "";
        displayName = "";
        fileName = "";
        added = false;
    }

    public DesktopDataObject(String head, String display, String file) {

        added = false;
        header = head;
        displayName = display;
        fileName = file;
    }

    public DesktopDataObject(String head, String display, String file,String pin, PTMConfig config) {

        added = false;
        header = head;
        displayName = display;
        fileName = file;
        photoIdenNo = pin;
        this.config = config;
    }

    public DesktopDataObject(String head, String display, String file, String pin) {

        added = false;
        header = head;
        displayName = display;
        // Debug.debug(s1);
        fileName = file;
        photoIdenNo = pin;
    }

    public String getHeader() {
        return header;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean flag) {
        added = flag;
    }

    public void setPhotoIdentificationNo(String s) {
        photoIdenNo = s;
    }

    public String getPhotoIdentificationNo() {
        return photoIdenNo;
    }

    public String toString() {
        if (displayName == null) {
            return "";
        } else {
            return displayName;
        }
    }

    public PTMConfig getConfig() {
        return config;
    }

    public void setConfig(PTMConfig c) {
        config = c;
    }

    public boolean isCopy() {
        return copy;
    }

    public void setCopy(boolean copy) {
        this.copy = copy;
    }
}
