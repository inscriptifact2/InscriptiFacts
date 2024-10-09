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
public class DiffuseGainOperation extends Operation {

    private float DGAIN = 1.16f;
    int width, height, dW, basis, size,type;
    LightSource[] sources;
    Rectangle target, ptmRect;
    float[] bias;
    float[] scale;
    ArrayList normals;

    public void release() {
        sources = null;
        target = null;
        ptmRect = null;
    }

    public void forceUpdate() {
    }

    public void clearCache() {
    }

    private void PTMXform(PTM ptm,int[] pixels, int mouseX, int mouseY) {


        int w = ptmRect.width / 2;
        int h = ptmRect.height / 2;
        int red = 0, green = 0, blue = 0;
        int[] intensity = new int[3];
        int pr = 0, pg = 0, pb = 0;
        float u, v, uu, vv, uv;
         
         byte[] coefficients  = null;
         byte[][] coeffs = ptm.getBCoefficients();

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
            int pi = 0, index = 0, m = 0, pixel = 0;
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
                    index = xp + yp * width;
                    
                 

                    if (type==PTM.TYPE_RAW_PTM && basis == PTM.BASIS_RGB) {
                        int[] coeff  = processCoeff( coeffs[index], index);

                        for (int color = 0; color < 3; color++) {
                            intensity[color] = ((coeff[color * 6 + 0] * _uu) >> 8) 
                                    + ((coeff[color * 6 + 1] * _vv) >> 8) 
                                    + ((coeff[color * 6 + 2] * _uv) >> 8) 
                                    + ((coeff[color * 6 + 3] * _u) >> 8) 
                                    + ((coeff[color * 6 + 4] * _v) >> 8) 
                                    + (coeff[color * 6 + 5]);
                        }

                        red = (intensity[0] * _lum) >> 8;
                        blue = (intensity[2] * _lum) >> 8;
                        green = (intensity[1] * _lum) >> 8;
                    } else if (type==PTM.TYPE_RAW_PTM && basis == PTM.BASIS_LRGB) {
                          coefficients = new byte[9];
                          for(int pos = 0; pos < 9; pos++)
                              coefficients[pos] = coeffs[pos][index];
                        int[] coeff  = processCoeff( coefficients, index);
                        
                        
                        intensity[0] = ((coeff[0] * _uu) >> 8) 
                                + ((coeff[1] * _vv) >> 8)
                                + ((coeff[2] * _uv) >> 8) 
                                + ((coeff[3] * _u) >> 8) 
                                + ((coeff[4] * _v) >> 8) + (coeff[5]);

                        // pixel = coeff[6];
                        m = _lum * intensity[0];

                        
                        red = coeff[6];
                        green = coeff[7];
                        blue = coeff[8];

                        red = (red * m) >> 16;
                        green = (green * m) >> 16;
                        blue = (blue * m) >> 16;
                    }
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

    //System.out.println("TimeTaken:" + (System.currentTimeMillis() - start));
    }

    public void performOperation(int[] pixels, LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
        
        _operate( ptm,sources, dW, dH, posx, posy, pW, pH, pixels, mouseX, mouseY);


    }

    private void _operate(PTM ptm, LightSource[] sources, int dW, int dH, int posx, int posy, int pW, int pH, int[] pixels, int mouseX, int mouseY) {
        width = ptm.getWidth();
        height = ptm.getHeight();
        type = ptm.getFileType();
        basis = ptm.getBasisType();
        this.sources = sources;
        DGAIN = ptm.getDGain();
        bias = ptm.getBias();
        scale = ptm.getScaleArray();
        normals = ptm.getNormals();


        Rectangle displayRect = new Rectangle(0, 0, dW, dH);
        ptmRect = new Rectangle(posx, posy, pW, pH);
        

        target = displayRect.intersection(ptmRect);
        this.dW = dW;
        
        PTMXform( ptm,pixels, mouseX, mouseY);
    }

    private int[] processCoeff( byte[] coefficients, int index) {
        int[] coeff = null;
        VRGB normal = (VRGB)normals.get(index);
        float lu = normal.red;
        float lv = normal.green;
        if (type == PTM.BASIS_LRGB && (Math.sqrt(lu * lu + lv * lv) > 1)) {
            lu /= (Math.sqrt(lu * lu + lv * lv));
            lv /= (Math.sqrt(lu * lu + lv * lv));
        }
        float lu2 = lu * lu;
        float lv2 = lv * lv;



        if (type==PTM.TYPE_RAW_PTM && basis == PTM.BASIS_LRGB) {
            coeff = new int[9];
            int color = 0;

            coeff[color * 6 + 0] = (int) (DGAIN * ((coefficients[color * 6 + 0] & 0x000000FF) - bias[0]) * scale[0]);
            coeff[color * 6 + 1] = (int) (DGAIN * ((coefficients[color * 6 + 1] & 0x000000FF) - bias[1]) * scale[1]);
            coeff[color * 6 + 2] = (int) (DGAIN * ((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2]);
            coeff[color * 6 + 3] = (int) (((1 - DGAIN) * ((2 * ((coefficients[color * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * lu) + (((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * lv))) + ((coefficients[color * 6 + 3] & 0x000000FF) - bias[3]) * scale[3]);
            coeff[color * 6 + 4] = (int) (((1 - DGAIN) * ((2 * ((coefficients[color * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * lv) + (((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * lu))) + ((coefficients[color * 6 + 4] & 0x000000FF) - bias[4]) * scale[4]);
            coeff[color * 6 + 5] = (int) (((1 - DGAIN) * (((coefficients[color * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * lu2 + ((coefficients[color * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * lv2 + ((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * lu * lv)) + ((((coefficients[color * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] - coeff[color * 6 + 3]) * lu) + ((((coefficients[color * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] - coeff[color * 6 + 4]) * lv) + ((coefficients[color * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);



            coeff[6] = coefficients[6] & 0x000000FF;
            coeff[7] = coefficients[7] & 0x000000FF;
            coeff[8] = coefficients[8] & 0x000000FF;
        } else if (type==PTM.TYPE_RAW_PTM && basis == PTM.BASIS_RGB) {
            coeff = new int[18];
            for (int color = 0; color < 3; color++) {
                coeff[color * 6 + 0] = (int) (DGAIN * ((coefficients[color * 6 + 0] & 0x000000FF) - bias[0]) * scale[0]);
                coeff[color * 6 + 1] = (int) (DGAIN * ((coefficients[color * 6 + 1] & 0x000000FF) - bias[1]) * scale[1]);
                coeff[color * 6 + 2] = (int) (DGAIN * ((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2]);
                coeff[color * 6 + 3] = (int) (((1 - DGAIN) * ((2 * (((coefficients[color * 6 + 0] & 0x000000FF) - bias[0]) * scale[0]) * lu) + ((((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2]) * lv))) + (((coefficients[color * 6 + 3] & 0x000000FF) - bias[3]) * scale[3]));
                coeff[color * 6 + 4] = (int) (((1 - DGAIN) * ((2 * (((coefficients[color * 6 + 1] & 0x000000FF) - bias[1]) * scale[1]) * lv) + ((((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2]) * lu))) + (((coefficients[color * 6 + 4] & 0x000000FF) - bias[4]) * scale[4]));
                coeff[color * 6 + 5] = (int) (((1 - DGAIN) * ((((coefficients[color * 6 + 0] & 0x000000FF) - bias[0]) * scale[0]) * lu2 + (((coefficients[color * 6 + 1] & 0x000000FF) - bias[1]) * scale[1]) * lv2 + (((coefficients[color * 6 + 2] & 0x000000FF) - bias[2]) * scale[2]) * lu * lv)) + (((((coefficients[color * 6 + 3] & 0x000000FF) - bias[3]) * scale[3]) - coeff[color * 6 + 3]) * lu) + (((((coefficients[color * 6 + 4] & 0x000000FF) - bias[4]) * scale[4]) - coeff[color * 6 + 4]) * lv) + (((coefficients[color * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]));
            }

        
        }
        return coeff;
    }

    @Override
    public String getName() {
        return "DiffuseGain";
    }
}
