/*
 * ColorChannelOp.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.HSH;
import isf.ptm.formats.LRGBPTM;
import isf.ptm.formats.PTM;
import isf.ptm.graphics.VRGB;
import isf.ptm.utils.LightSource;
import isf.ptm.utils.Utils;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.vecmath.Point3f;

/**
 *
 * @author Default
 */
public final class SpecularOperation extends Operation {

    int width, height, dW, type, basis;
    LightSource[] sources;
    Rectangle target, ptmRect;
    // private float[] kCache = null;
    // private Point cachedMouse = new Point(-1, -1);
    private boolean forceUpdate = false;
    private int cachedExp = -1;
    private float[] phong = new float[1000];

    public void forceUpdate() {
        forceUpdate = true;
    }

    public void clearCache() {
        //   kCache = null;
        //   cachedMouse = null;
    }

    public void release() {        //  kCache = null;
        //  cachedMouse = null;
    }

    private void RGBXform(int[] pixels, PTM ptm, int mouseX, int mouseY) {


        ArrayList normals = ptm.getNormals();
        long start = System.currentTimeMillis();
        float[] bias = ptm.getBias();
        float[] scale = ptm.getScaleArray();

        final float KSPEC = ptm.getKSpec() * 3;
        final int KDIFF = Math.round(ptm.getKDiff() * 256);
        final int EXP = ptm.getExp();
        if (EXP != this.cachedExp) {
            for (int i = 0; i < phong.length; i++) {
                phong[i] = (float) Math.pow(((float) i) / 1000, EXP);
            }
            cachedExp = EXP;

        }

        byte[][] coeff = ptm.getBCoefficients();


        int w = ptmRect.width / 2;
        int h = ptmRect.height / 2;
        int red = 0, green = 0, blue = 0;




        float u, v, uu, vv, uv;
        int pr = 0, pg = 0, pb = 0;
        final int RED = 0,  GREEN = 1,  BLUE = 2;


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






            float inner = 1 - uu - vv;
            float _w = (inner > 0) ? (float) Math.sqrt(inner) : 0f;

            final float[] L = Utils.normalize(u, v, _w);
            final float[] H = Utils.normalize((L[0] + 0) * 0.5f, (L[1] + 0) * 0.5f, (L[2] + 1) * 0.5f);


            float nDotH;
            float kVal;





            /* for (int y = 0; y < height; y += increment) {
            for (int z = 0; z < width; z += increment) {*/
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
                    // int i = y * width + z;
                    //int i = (k * target.width) + j;
                    int index = xp + yp * width;



                    VRGB N = (VRGB) normals.get(index);
                    // nDotH = Math.min(1f, Math.max(0f, N.dot(H[0],H[1],H[2])));
                    nDotH = N.customMinMaxDot(1f, 0f, H[0], H[1], H[2]);
                    /* k = (float) Math.pow(nDotH,EXP); */
                    kVal = phong[(int) (nDotH * 999)];

                    int _er = 255, _eg = 255, _eb = 255;

                    red = (int) ((((int) (((coeff[index][RED * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * _uu)) >> 8) + ((int) (((coeff[index][RED * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((coeff[index][RED * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((coeff[index][RED * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((coeff[index][RED * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((coeff[index][RED * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);
                    green = (int) ((((int) (((coeff[index][GREEN * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * _uu)) >> 8) + ((int) (((coeff[index][GREEN * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((coeff[index][GREEN * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((coeff[index][GREEN * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((coeff[index][GREEN * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((coeff[index][GREEN * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);
                    blue = (int) ((((int) (((coeff[index][BLUE * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * _uu)) >> 8) + ((int) (((coeff[index][BLUE * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((coeff[index][BLUE * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((coeff[index][BLUE * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((coeff[index][BLUE * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((coeff[index][BLUE * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]);

                    red = (((((red * KDIFF) >> 8) * _er) >> 8) + (int) (_er * kVal * KSPEC));

                    green = (((((green * KDIFF) >> 8) * _eg) >> 8) + (int) (_eg * kVal * KSPEC));
                    blue = (((((blue * KDIFF) >> 8) * _eb) >> 8) + (int) (_eb * kVal * KSPEC));




                    red = (red * _lum) >> 8;
                    green = (green * _lum) >> 8;
                    blue = (blue * _lum) >> 8;
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

        forceUpdate = false;
    //  System.out.println("TimeTaken:" + (System.currentTimeMillis() - start));
    }

    private void HSHform(int[] pixels, PTM ptm, int mouseX, int mouseY) {

//, ((HSH) ptm).getBiasArray(), ptm.getScaleArray(), ((HSH) ptm).getBasisTerms()
        ArrayList normals = ptm.getNormals();
        long start = System.currentTimeMillis();
        float[] bias = ptm.getBias();
        float[] scale = ptm.getScaleArray();

        final float KSPEC = ptm.getKSpec() ;
        final float KDIFF =ptm.getKDiff() ;
        final int EXP = ptm.getExp();
        if (EXP != this.cachedExp) {
            for (int i = 0; i < phong.length; i++) {
                phong[i] = (float) Math.pow(((float) i) / 1000, EXP);
            }
            cachedExp = EXP;

        }

        byte[][] coeff = ptm.getBCoefficients();


        int pi = 0;
        int xp, yp;
        float sx = (float) width / (float) ptmRect.width;
        float sy = (float) height / (float) ptmRect.height;

        int bands = ((HSH) ptm).getBasisTerms();

        final int RED = 0,  GREEN = 1,  BLUE = 2;

        int pr = 0, pg = 0, pb = 0;

        for (int x = 0; x < sources.length; x++) {

            if (!sources[x].isEnabled()) {
                continue;
            }
            Point3f light = sources[x].getLight();
            Point3f temp = new Point3f(light.x, light.y, light.z);
           // Utils.normalize(temp);
            double phi = Math.atan2(temp.y, temp.x);
            double theta = Math.acos(temp.z / Utils.norm(temp));


            double[] weights = getHSH(theta, phi);

             float intensity = sources[x].getLuminance()/2.0f;
            for (int k = 0, q = target.y; k < target.height; k++, q++) {
                yp = (int) (sy * ((target.y + k) - ptmRect.y));
                if (yp >= height) {
                    break;
                }

                for (int j = 0, p = target.x; j < target.width; j++, p++) {//
                    double red = 0, green = 0, blue = 0;
                    xp = (int) (sx * ((target.x + j) - ptmRect.x));
                    if (xp >= width) {
                        break;
                    }

                    pi = (q) * dW + (p);
                    // int i = y * width + z;
                    //int i = (k * target.width) + j;
                    int index = xp + yp * width;
                    byte[] localCache = coeff[index];

                    for (int i = 0; i < bands; i++) {
                        red += (((float) (localCache[RED * bands + i] & 0x000000FF)) / 255 * scale[i] + bias[i]) * weights[i];
                        green += (((float) (localCache[GREEN * bands + i] & 0x000000FF)) / 255 * scale[i] + bias[i]) * weights[i];
                        blue += (((float) (localCache[BLUE * bands + i] & 0x000000FF)) / 255 * scale[i] + bias[i]) * weights[i];
                    }

                    red *= 255 * intensity;
                    blue *= 255 * intensity;;
                    green *= 255 * intensity;;

                    Point3f hm = new Point3f(0, 0, 1);
                    hm.add(sources[x].getLight());
                    hm.scale((float) 1.0 / (float) 2.0);
                    Utils.normalize(hm);
                    VRGB normal = (VRGB) normals.get(index);
                    double nDotH = Utils.cross(hm, normal);

                    if (nDotH < 0) {
                        nDotH = 0.0;
                    } else if (nDotH > 1) {
                        nDotH = 1.0;
                    }
                    nDotH = Math.pow(nDotH, EXP / 5);

                    double _lum = (red + green + blue) / 3;
                    double lum = _lum * KSPEC * 4 * nDotH;

                    red = red * KDIFF + lum;
                    green = green * KDIFF + lum;
                    blue = blue * KDIFF + lum;
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
                    pixels[pi] = 255 << 24 |pr << 16 | pg << 8 | pb;

                }
            }

        }



        forceUpdate = false;
    //  System.out.println("TimeTaken:" + (System.currentTimeMillis() - start));
    }

    private void LRGBXform(int[] pixels, PTM ptm, int mouseX, int mouseY) {

        boolean useCache = false;
        Point mousePos = new Point(mouseX, mouseY);


        ArrayList normals = ptm.getNormals();

        int w = ptmRect.width / 2;
        int h = ptmRect.height / 2;
        int intensity = 0, red = 0, green = 0, blue = 0, pixel = 0;



        final float KSPEC = ptm.getKSpec();
        final int KDIFF = Math.round(ptm.getKDiff() * 256);
        final int EXP = ptm.getExp();

        if (EXP != this.cachedExp) {
            for (int i = 0; i < phong.length; i++) {
                phong[i] = (float) Math.pow(((float) i) / 1000, EXP);
            }
            cachedExp = EXP;
        }



        byte[][] coeffs = ptm.getBCoefficients();
        float[] bias = ptm.getBias();
        float[] scale = ptm.getScaleArray();



        int[] map = null;
        boolean useEnv = false;
        int[] rotatedEnv = null;
        if (ptm.getEnvironmentMap() != null) {
            useEnv = true;
            map = ((LRGBPTM) ptm).getEnvironmentMapMap();
            rotatedEnv = ptm.getEnvironmentMap().rotatedMap();
        }
        int _er = 255, _eg = 255, _eb = 255;




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
            float inner = 1 - uu - vv;


            float _w = (inner > 0) ? (float) Math.sqrt(inner) : 0f;

            final float[] L = Utils.normalize(u, v, _w);
            final float[] H = Utils.normalize((L[0] + 0) * 0.5f, (L[1] + 0) * 0.5f, (L[2] + 1) * 0.5f);


            float nDotH;
            float kVal;



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
                    int index = xp + yp * width;


                    VRGB N = (VRGB) normals.get(index);
                    nDotH = N.customMinMaxDot(1f, 0f, H[0], H[1], H[2]);
                    kVal = phong[(int) (nDotH * 999)];



                    /*
                    intensity = ((coeff[index][0] * _uu) >> 8) 
                    + ((coeff[index][1] * _vv) >> 8) 
                    + ((coeff[index][2] * _uv) >> 8) 
                    + ((coeff[index][3] * _u) >> 8) 
                    + ((coeff[index][4] * _v) >> 8) 
                    + (coeff[index][5]);*/


                    intensity = (int) (((int) (((coeffs[0][index] & 0x000000FF) - bias[0]) * scale[0] * _uu) >> 8) + ((int) (((coeffs[1][index] & 0x000000FF) - bias[1]) * scale[1] * _vv) >> 8) + ((int) (((coeffs[2][index] & 0x000000FF) - bias[2]) * scale[2] * _uv) >> 8) + ((int) (((coeffs[3][index] & 0x000000FF) - bias[3]) * scale[3] * _u) >> 8) + ((int) (((coeffs[4][index] & 0x000000FF) - bias[4]) * scale[4] * _v) >> 8) + ((int) ((coeffs[5][index] & 0x000000FF) - bias[5]) * scale[5]));


                    red = coeffs[6][index] & 0x000000FF;
                    green = coeffs[7][index] & 0x000000FF;
                    blue = coeffs[8][index] & 0x000000FF;

                    if (useEnv) {
                        int pix = rotatedEnv[map[index]];
                        _er = (pix >> 16) & 0xff;
                        _eg = (pix >> 8) & 0xff;
                        _eb = pix & 0xff;
                    }

                    red = (int) (((((red * intensity * KDIFF) >> 8) * _er) >> 8) + (_er * kVal * KSPEC * intensity));

                    green = (int) (((((green * intensity * KDIFF) >> 8) * _eg) >> 8) + (_eg * kVal * KSPEC * intensity));
                    blue = (int) (((((blue * intensity * KDIFF) >> 8) * _eb) >> 8) + (_eb * kVal * KSPEC * intensity));

                    red = (red * _lum) >> 8;
                    green = (green * _lum) >> 8;
                    blue = (blue * _lum) >> 8;

                    red >>= 8;
                    green >>= 8;
                    blue >>= 8;
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

        forceUpdate = false;
    }

    public void performOperation(int[] pixels, LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
        width = ptm.getWidth();
        height = ptm.getHeight();
        type = ptm.getFileType();
        basis = ptm.getBasisType();
        //  sources = ptm.getLights();
        this.sources = sources;


        Rectangle displayRect = new Rectangle(0, 0, dW, dH);
        ptmRect = new Rectangle(posx, posy, pW, pH);
        target = displayRect.intersection(ptmRect);
        this.dW = dW;

        if(type==PTM.TYPE_RAW_PTM){
        switch (basis) {
            case PTM.BASIS_LRGB:
                LRGBXform(pixels, ptm, mouseX, mouseY);
                break;
            case PTM.BASIS_RGB:
                RGBXform(pixels, ptm, mouseX, mouseY);
                break;
            
                
            /* TODO: simple grey color */
        }
        }else if( type ==PTM.TYPE_RTI_HSH){
            HSHform(pixels, ptm, mouseX, mouseY);

        }
    }

    @Override
    public String getName() {
        return "Specular";
    }
}
