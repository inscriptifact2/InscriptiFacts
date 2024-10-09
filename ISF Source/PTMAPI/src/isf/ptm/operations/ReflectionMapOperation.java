/*
 * ColorChannelOp.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.PTM;
import isf.ptm.graphics.VRGB;
import isf.ptm.utils.LightSource;
import isf.ptm.utils.Utils;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * @author Default
 */
public class ReflectionMapOperation extends Operation {

    public void performOperation(int[] pixels, PTM ptm) {
        _operate(pixels, ptm);
    }

    private void _operate(int[] pixels, PTM ptm) {
        int[] localPixels = pixels;
        VRGB eye = new VRGB(0, 0, 1);
        int length = localPixels.length;
        for (int i = 0; i < length; i++) {
            VRGB N = ptm.normal(i);
            if (N.red == 0 && N.green == 0 && N.blue == 0) {
                localPixels[i] = 0;
                continue;
            }
            VRGB R = VRGB.reflect(N, eye);
            localPixels[i] = R.toPixel();
        }
        // apply gaussian blur.

        // make a copy
        BufferedImage tmp = new BufferedImage(ptm.getWidth(), ptm.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int[] buf = Utils.grabPixels(tmp);

        for (int i = 0; i < buf.length; i++) {
            buf[i] = localPixels[i];
        }
        float sum = (2 + 4 + 5 + 4 + 2) * 2 + (4 + 9 + 12 + 9 + 4) * 2 + 5 + 12 + 15 + 12 + 5;

        float[] elements = {2f / sum, 4f / sum, 5f / sum, 4f / sum, 2f / sum,
            4f / sum, 9f / sum, 12f / sum, 9f / sum, 4f / sum, 5f / sum,
            12f / sum, 15f / sum, 12f / sum, 5f / sum, 4f / sum, 9f / sum,
            12f / sum, 9f / sum, 4f / sum, 2f / sum, 4f / sum, 5f / sum,
            4f / sum, 2f / sum
        };

        Kernel kernel = new Kernel(5, 5, elements);
        ConvolveOp cop = new ConvolveOp(kernel);
        BufferedImage tmp2 = new BufferedImage(ptm.getWidth(), ptm.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        cop.filter(tmp, tmp2);

        for (int i = 0; i < buf.length; i++) {
            localPixels[i] = buf[i];
        }
    }

    public void performOperation(int[] pixels, PTM ptm, int mouseX, int mouseY) {
        performOperation(pixels, ptm);
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
        return "ReflectionMap";
    }
}
