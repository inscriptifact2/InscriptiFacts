/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.ptm.formats;

import isf.ptm.fileio.PTMIO;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author IN030223
 */
public class MVRTI extends HSH {
    
    Logger log = LogManager.getLogger(isf.ptm.formats.MVRTI.class);

    private Hashtable files;
    private int orientation;
    private int maxX;
    private int maxY;
    private int views;
    private int useFlow;
    private HSH ptm = null;

    public Hashtable getFiles() {
        return files;
    }

    public void setFiles(Hashtable files) {
        this.files = files;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getUseFlow() {
        return useFlow;
    }

    public void setUseFlow(int useFlow) {
        this.useFlow = useFlow;
    }

    public void setDirectory(String mview) {
        int ls = mview.lastIndexOf(File.separator);
        dir = mview.substring(0, ls);
    }
    String dir = "";
    int currentView = 1;

    public String getFileName(int x) {
        return dir + File.separator + (String) files.get(x + "");
    }

    public PTM getPTM(int x) {
        return _getPTM(x);
    }

    private PTM _getPTM(int x) {
        String file = (String) files.get(x + "");
        currentView = x;
        try {
            FileInputStream fis = new FileInputStream(dir + File.separator + file);
            if (ptm != null) {
                ptm.release();
            }

            ptm = (HSH) PTMIO.getPTMParser(fis, true).readPTM();
            width = ptm.getWidth();
            height = ptm.getHeight();
            basisTerms = ptm.getBasisTerms();
            basisType = ptm.getBasisType();
            type = ptm.getFileType();
            elementSize = ptm.getElementSize();
            name = file;
            version = ptm.getVersion();
            coefficients = ptm.getBCoefficients();
            bias = ptm.getBias();
            scale = ptm.getScaleArray();
            colors = ptm.getColors();
            normals = ptm.getNormals();


        } catch (Exception exp) {
            log.error(exp);
        }
        return ptm;
    }

    public int getCurrentView() {
        return currentView;
    }
}
