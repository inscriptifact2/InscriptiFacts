/*
 * ColorOperation.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.PTM;
import isf.ptm.utils.LightSource;

/**
 *
 * @author Default
 */
public class ColorOperation extends Operation {

    /** Creates a new instance of ColorOperation */
    public ColorOperation() {
    }

    public void performOperation(int[] pixels, PTM ptm) {
        int[] localPixels = pixels;
        PTM localPtm = ptm;
        int length = localPixels.length;
        for (int i = 0; i < length; i++) {
  //          localPixels[i] = localPtm.red(i) << 16 | localPtm.green(i) << 8 | localPtm.blue(i);
        }
    }

    public void performOperation(int[] pixels, PTM ptm, int mouseX, int mouseY) {
    }

    public void release() {
    }

    public void forceUpdate() {
    }

    public void clearCache() {
    }

    public void performOperation(int[] pixels,LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        return "Color";
    }
}
