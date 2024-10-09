/*
 * ColorChannelOp.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.PTM;
import isf.ptm.graphics.VRGB;
import isf.ptm.utils.LightSource;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Default
 */
public class NormalMapOperation extends Operation {

    public void release() {
    }

    public void forceUpdate() {
    }

    public void clearCache() {
    }

    public void performOperation(int[] pixels,LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
        _operate(ptm, dW, dH, posx, posy, pW, pH, pixels);
        
       


    }

    private void _operate(PTM ptm, int dW, int dH, int posx, int posy, int pW, int pH, int[] pixels) {
        int width = ptm.getWidth();
        int height = ptm.getHeight();
        Rectangle displayRect = new Rectangle(0, 0, dW, dH);
        Rectangle ptmRect = new Rectangle(posx, posy, pW, pH);
        Rectangle target = displayRect.intersection(ptmRect);
        ArrayList normals = ptm.getNormals();

        int pi = 0;
        int xp, yp;
        float sx = (float) width / (float) ptmRect.width;
        float sy = (float) height / (float) ptmRect.height;


        for (int k = 0, q = target.y; k < target.height; k++, q++) {
            yp = (int) (sy * ((target.y + k) - ptmRect.y));
            if (yp >= height) {
                break;
            }

            for (int j = 0, p = target.x; j < target.width; j++, p++) {//

                xp = (int) (sx * ((target.x + j) - ptmRect.x));
                if (xp >= width) {
                    break;
                }
                pi = (q) * dW + (p);
                VRGB N = (VRGB)normals.get(xp + yp * width);
                if (N.red == 0 && N.green == 0 && N.blue == 0) {
                    pixels[pi] = 0;
                } else {
                    pixels[pi] = N.toPixel();
                }

            }
        }
    }

    @Override
    public String getName() {
        return "NormalMap";
    }
}
