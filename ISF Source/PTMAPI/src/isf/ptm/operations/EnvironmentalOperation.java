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

/**
 * 
 * @author Default
 */
public class EnvironmentalOperation extends Operation {

    /** Creates a new instance of ColorChannelOp */
    public EnvironmentalOperation() {
    }

    public void performOperation(int[] pixels, PTM ptm) {
        _operate(pixels, ptm);
    }

    private void _operate(int[] pixels, PTM ptm) {
        int[] localPixels = pixels;
        PTM localPtm = ptm;
        int length = localPixels.length;
        VRGB eye = new VRGB(0, 0, 1);
        BufferedImage tmp = new BufferedImage(ptm.getWidth(), ptm.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int[] buf = Utils.grabPixels(tmp);
        int[] map = null;
        int[] rotatedEnv = ptm.getEnvironmentMap().rotatedMap();
        if (ptm.getEnvironmentMap() != null) {
            map = ptm.getEnvironmentMapMap();
        }

        for (int i = 0; i < length; i++) {
            // localPixels[i] = rotatedEnv[map[i]];
            // N.perturb();
            // VRGB R = VRGB.reflect(ptm.normal(i),new VRGB(0,0,1));
            // float m = (float) Math.sqrt(2*(R.z()+1));
            // float u = -R.x()/m;
            // float v = -R.g/m;
            // int u1 = Math.round((u+1)*ptm.getWidth()/2);
            // int v1 = Math.round((v+1)*ptm.getHeight()/2);
            localPixels[i] = localPtm.getEnvironmentMap().getPixel(
                    ptm.normal(i));

        // VRGB R = VRGB.reflect(localPtm.normal(x,y),eye);
        // // float m = (float) Math.sqrt(2*(R.z()+1));
        // // float u = R.x()/m;
        // // float v = -R.g/m;
        // // int pixel = localPtm.getEnvironmentMap().getPixel(u,v);
        // buf[i] = R.toPixel();
        // }
        //                
        // float sum = (2+4+5+4+2)*2 + (4+9+12+9+4)*2 + 5+12+15+12+5;
        //        
        // float[] elements = {
        // 2f/sum, 4f/sum, 5f/sum, 4.0f/sum, 2.0f/sum,
        // 4f/sum, 9f/sum, 12f/sum, 9f/sum, 4f/sum,
        // 5f/sum, 12f/sum, 15f/sum, 12f/sum, 5f/sum,
        // 4f/sum, 9f/sum, 12f/sum, 9f/sum, 4f/sum,
        // 2.0f/sum, 4.0f/sum, 5.0f/sum, 4.0f/sum, 2.0f/sum };
        //       
        // Kernel kernel = new Kernel(5,5,elements);
        // ConvolveOp cop = new ConvolveOp(kernel);
        // BufferedImage tmp2 = new
        // BufferedImage(ptm.getWidth(),ptm.getHeight(),BufferedImage.TYPE_INT_RGB);
        // cop.filter(tmp,tmp2);
        // int [] buf2 = Utils.grabPixels(tmp2);
        // int unchanged = 0;
        //       
        // // buf2 contains blurred reflection vectors
        //       
        // for ( int i = 0; i < length; i++ ) {
        // int w = localPtm.getWidth();
        // int h = localPtm.getHeight();
        // int x = i % w;
        // int y = i / w;
        // VRGB R = VRGB.convertPixel(buf[i]);
        // float m = (float) Math.sqrt(2*(R.z()+1));
        // float u = R.x()/m;
        // float v = -R.g/m;
        // localPixels[i] = localPtm.getEnvironmentMap().getPixel(u,v);
        // //localPixels[i] = localPtm.getEnvironmentMap().getPixel(i);
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
        return "EnvironmentalMap";
    }
}
