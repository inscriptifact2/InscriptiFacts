/*
 * ColorChannelOp.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.HSH;
import isf.ptm.formats.PTM;
import isf.ptm.utils.LightSource;
import isf.ptm.utils.Utils;
import java.awt.Rectangle;
import javax.vecmath.Point3f;

/**
 *
 * @author Default
 */
public class DirectionalLightOperation extends Operation {

    int width, height, dW, type,basis;
    LightSource[] sources;
    boolean extrapolate;
    float factor = 1.0f;
    float[] bias;
    float[] scale;
    int[] map = null;
    int[] rotatedEnv = null;
    Rectangle target, ptmRect;

    private void LRGBXformEnv(int[] pixels, byte[][] coeffs, int mouseX, int mouseY, float[] bias, float[] scale) {
        int w = ptmRect.width / 2;
        int h = ptmRect.height / 2;

        int intensity = 0, red = 0, green = 0, blue = 0, pixel = 0, m = 0;
        float u, v, uu, vv, uv;
        int pr = 0, pg = 0, pb = 0;
        if (map == null || rotatedEnv == null) {
            LRGBXform(pixels, coeffs, mouseX, mouseY, bias, scale);
            return;
        }
        int _er = 256, _eg = 256, _eb = 256;
        for (int x = 0; x < sources.length; x++) {
            u = 0;
            v = 0;
            if (!sources[x].isEnabled()) {
                continue;
            }
            if (sources[x].isActive()) {
                u = ((float) mouseX - w) / w;
                v = -((float) mouseY - h) / h;
            } else {
                u = ((float) sources[x].getX() - w) / w;
                v = -((float) sources[x].getY() - h) / h;
            }
            uu = u * u;
            vv = v * v;
            uv = u * v;
            final int _u = Math.round(u * 256);
            final int _v = Math.round(v * 256);
            final int _uu = Math.round(uu * 256);
            final int _vv = Math.round(vv * 256);
            final int _uv = Math.round(uv * 256);

            final int _lum = Math.round(sources[x].getLuminance() * 256);

            int pi = 0, i = 0;
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
                    i = xp + yp * width;

                    //intensity = ((coeffs[i][0] * _uu) >> 8) + ((coeffs[i][1] * _vv) >> 8) + ((coeffs[i][2] * _uv) >> 8) + ((coeffs[i][3] * _u) >> 8) + ((coeffs[i][4] * _v) >> 8) + (coeffs[i][5]);
                    intensity = (int) (((int) (((coeffs[0][i] & 0x000000FF) - bias[0]) * scale[0] * _uu) >> 8) + ((int) (((coeffs[1][i] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((coeffs[2][i] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((coeffs[3][i] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((coeffs[4][i] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((int) ((coeffs[5][i] & 0x000000FF) - bias[5]) * scale[5]));

                    // pixel = coeffs[i][6];
                    m = _lum * intensity;
                    // red = (pixel >> 16) & 0xff;
                    //green = (pixel >> 8) & 0xff;
                    // blue = (pixel) & 0xff;
                    red = coeffs[6][i] & 0x000000FF;
                    green = coeffs[7][i] & 0x000000FF;
                    blue = coeffs[8][i] & 0x000000FF;


                    int pix = rotatedEnv[map[i]];
                    _er = (pix >> 16) & 0xff;
                    _eg = (pix >> 8) & 0xff;
                    _eb = pix & 0xff;

                    red = (red * ((m * _er) >> 8)) >> 16;
                    green = (green * ((m * _eg) >> 8)) >> 16;
                    blue = (blue * ((m * _eb) >> 8)) >> 16;

                    pr = (x > 0) ? (pixels[pi] >> 16) & 0xff : 0;
                    pg = (x > 0) ? (pixels[pi] >> 8) & 0xff : 0;
                    pb = (x > 0) ? (pixels[pi]) & 0xff : 0;
                    pr += red;
                    pg += green;
                    pb += blue;
                    pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
                    pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
                    pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
                    pixels[pi] = pr << 16 | pg << 8 | pb;


                }
            }
        }
    }

    private void LRGBXform(int[] pixels, byte[][] coeffs, int mouseX, int mouseY, float[] bias, float[] scale) {

        int w = ptmRect.width / 2;
        int h = ptmRect.height / 2;

        int intensity = 0, red = 0, green = 0, blue = 0, pixel = 0, m = 0;




        int pr = 0, pg = 0, pb = 0;
        float u, v, uu, vv, uv;
        for (int x = 0; x < sources.length; x++) {
            u = 0;
            v = 0;
            if (!sources[x].isEnabled()) {
                continue;
            }
            if (sources[x].isActive()) {

                u = ((float) mouseX - w) / w;
                v = -((float) mouseY - h) / h;
            } else {

                u = ((float) sources[x].getX() - w) / w;
                v = -((float) sources[x].getY() - h) / h;
            }
            uu = u * u;
            vv = v * v;
            uv = u * v;
            final int _u = Math.round(u * 256);
            final int _v = Math.round(v * 256);
            final int _uu = Math.round(uu * 256);
            final int _vv = Math.round(vv * 256);
            final int _uv = Math.round(uv * 256);
            final int _lum = Math.round(sources[x].getLuminance() * 256);
            int pi = 0, i = 0;




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
                    i = xp + yp * width;




                    intensity = (int) (((int) (((coeffs[0][i] & 0x000000FF) - bias[0]) * scale[0] * _uu) >> 8) + ((int) (((coeffs[1][i] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((coeffs[2][i] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((coeffs[3][i] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((coeffs[4][i] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((int) ((coeffs[5][i] & 0x000000FF) - bias[5]) * scale[5]));

                    // pixel = coeffs[i][6];
                    m = _lum * intensity;
                    // red = (pixel >> 16) & 0xff;
                    //green = (pixel >> 8) & 0xff;
                    // blue = (pixel) & 0xff;
                    red = coeffs[6][i] & 0x000000FF;
                    green = coeffs[7][i] & 0x000000FF;
                    blue = coeffs[8][i] & 0x000000FF;

                    red = (red * m) >> 16;
                    green = (green * m) >> 16;
                    blue = (blue * m) >> 16;


                    pr = (x > 0) ? (pixels[pi] >> 16) & 0xff : 0;
                    pg = (x > 0) ? (pixels[pi] >> 8) & 0xff : 0;
                    pb = (x > 0) ? (pixels[pi]) & 0xff : 0;
                    pr += red;
                    pg += green;
                    pb += blue;
                    pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
                    pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
                    pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
                    pixels[pi] = pr << 16 | pg << 8 | pb;
                }
            }
        }
    }

    public void release() {
        /** no local resources */
    }

    public void forceUpdate() {
    }

    public void clearCache() {
    }

    private void RGBXform(int[] pixels, byte[][] coeff, int mouseX, int mouseY, float[] bias, float[] scale) {
        long start = System.currentTimeMillis();
        int w = ptmRect.width / 2;
        int h = ptmRect.height / 2;
        int red = 0, green = 0, blue = 0;
        final int RED = 0,  GREEN = 1,  BLUE = 2;



        float u, v, uu, vv, uv;

        int pr = 0, pg = 0, pb = 0;
        for (int x = 0; x < sources.length; x++) {
            u = 0;
            v = 0;
            if (!sources[x].isEnabled()) {
                continue;
            }
            if (sources[x].isActive()) {

                u = ((float) mouseX - w) / w;
                v = -((float) mouseY - h) / h;
            } else {

                u = ((float) sources[x].getX() - w) / w;
                v = -((float) sources[x].getY() - h) / h;
            }
            uu = u * u;
            vv = v * v;
            uv = u * v;


            final int _u = Math.round(u * 256);
            final int _v = Math.round(v * 256);
            final int _uu = Math.round(uu * 256);
            final int _vv = Math.round(vv * 256);
            final int _uv = Math.round(uv * 256);
            final int _lum = Math.round(sources[x].getLuminance() * 256);
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

                    byte[] localCache = coeff[xp + yp * width];


                    //[channel*6+i];

                    red = (int) ((((int) (((localCache[RED * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * _uu)) >> 8) + ((int) (((localCache[RED * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((localCache[RED * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((localCache[RED * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((localCache[RED * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((localCache[RED * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);
                    green = (int) ((((int) (((localCache[GREEN * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * _uu)) >> 8) + ((int) (((localCache[GREEN * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((localCache[GREEN * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((localCache[GREEN * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((localCache[GREEN * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((localCache[GREEN * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);
                    blue = (int) ((((int) (((localCache[BLUE * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * _uu)) >> 8) + ((int) (((localCache[BLUE * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((localCache[BLUE * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((localCache[BLUE * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((localCache[BLUE * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((localCache[BLUE * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);

                    red = (red * _lum) >> 8;
                    green = (green * _lum) >> 8;
                    blue = (blue * _lum) >> 8;

                    pr = (x > 0) ? (pixels[pi] >> 16) & 0xFF : 0;
                    pg = (x > 0) ? (pixels[pi] >> 8) & 0xFF : 0;
                    pb = (x > 0) ? (pixels[pi]) & 0xFF : 0;
                    pr += red;
                    pg += green;
                    pb += blue;
                    pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
                    pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
                    pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
                    pixels[pi] = pr << 16 | pg << 8 | pb;

                }
            }

        }
    //  System.out.println("TimeTaken:" + (System.currentTimeMillis() - start));

    }

    private void HSHform(int[] pixels, byte[][] coeff, float[] bias, float[] scale, int bands) {
        long start = System.currentTimeMillis();

       
        float red = 0.0f, green = 0.0f, blue = 00.0f;
        int RED = 0, GREEN = 1, BLUE = 2;

        int pr = 0, pg = 0, pb = 0;
      
        for (int x = 0; x < sources.length; x++) {

            if (!sources[x].isEnabled()) {
                continue;
            }
            Point3f light = sources[x].getLight();
            Point3f temp = new Point3f(light.x, light.y, light.z);
            Utils.normalize(temp);
            double phi = Math.atan2(temp.y, temp.x);
            double theta = Math.acos(temp.z / Utils.norm(temp));

        
            double[] weights = getHSH(theta, phi);

            int pi = 0;

            int xp, yp;
            float sx = (float) width / (float) ptmRect.width;
            float sy = (float) height / (float) ptmRect.height;
            
            float lum = sources[x].getLuminance()/2.0f;


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

                    byte[] localCache = coeff[xp + yp * width];
                    red=0.0f;green=0.0f;blue=0.0f;

                    for (int i = 0; i < bands; i++) {
                        red += (((float) (localCache[RED * bands + i]& 0x000000FF))/255  * scale[i] + bias[i]) * weights[i];
                        green += (((float) (localCache[GREEN * bands + i]& 0x000000FF))/255  * scale[i] + bias[i]) * weights[i];
                        blue += (((float) (localCache[BLUE * bands + i]& 0x000000FF))/255  * scale[i] + bias[i]) * weights[i];
                    }

                    red *= 255 * lum;
                    blue *= 255 * lum;
                    green *= 255 * lum;
                    red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                    green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                    blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;


                    pr = (x > 0) ? (pixels[pi] >> 16) & 0xFF : 0;
                    pg = (x > 0) ? (pixels[pi] >> 8) & 0xFF : 0;
                    pb = (x > 0) ? (pixels[pi]) & 0xFF : 0;
                    pr += red;
                    pg += green;
                    pb += blue;
                    pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
                    pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
                    pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
                    pixels[pi] = 255<<24 | pr << 16 | pg << 8 | pb;
                   
                  
                    
                }
                
            }

        }
       
        
    }

    public void performOperation(int[] pixels, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
    }

    public void performOperation(int[] pixels, LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
        width = ptm.getWidth();
        height = ptm.getHeight();
        type = ptm.getFileType();
        basis = ptm.getBasisType();
        this.sources = sources;
        extrapolate = ptm.isExtrapolation();
      //  bias = ptm.getBias();
      //  scale = ptm.getScaleArray();
        factor = (float) pW / (float) width;

        Rectangle displayRect = new Rectangle(0, 0, dW, dH);
        ptmRect = new Rectangle(posx, posy, pW, pH);
        target = displayRect.intersection(ptmRect);
        this.dW = dW;
        if(type==PTM.TYPE_RAW_PTM){
        switch (basis) {
            case PTM.BASIS_LRGB:
                if (!ptm.useEnv()) {
                    LRGBXform(pixels, ptm.getBCoefficients(), mouseX, mouseY, ptm.getBias(), ptm.getScaleArray());
                } else {
                    map = ptm.getEnvironmentMapMap();
                    rotatedEnv = ptm.getEnvironmentMap().rotatedMap();
                    LRGBXformEnv(pixels, ptm.getBCoefficients(), mouseX, mouseY, ptm.getBias(), ptm.getScaleArray());
                }
                break;
            case PTM.BASIS_RGB:
                RGBXform(pixels, ptm.getBCoefficients(), mouseX, mouseY, ptm.getBias(), ptm.getScaleArray());
                break;
            
            
        }
        }else if( type == PTM.TYPE_RTI_HSH){
             HSHform(pixels, ptm.getBCoefficients(),  ptm.getBias(), ptm.getScaleArray(), ((HSH) ptm).getBasisTerms());

        }



    }

    @Override
    public String getName() {
        return "Directional";
    }
}
