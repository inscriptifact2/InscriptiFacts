/*
 * ColorChannelOp.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.LRGBPTM;
import isf.ptm.formats.PTM;
import isf.ptm.utils.LightSource;
import java.awt.Rectangle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Default
 */
public class SpotLightOperation extends Operation {
    Logger log = LogManager.getLogger(isf.ptm.operations.SpotLightOperation.class);

    // private int[] l_lookup = null;
    private int width;
    private int height;
    private boolean isFlashLight = false;
    private int Z = 10000;
    int dW, type, basis;
    LightSource[] sources;
    int[] map = null;
    int[] rotatedEnv = null;
    Rectangle target, ptmRect;

    public void setFlashlight(boolean b) {
        isFlashLight = b;
        // l_lookup = null;
    }

    private void LRGBXformEnv(int[] pixels, byte[][] coeffs, int mouseX, int mouseY, float[] bias, float[] scale) {



        int xp, yp;
        int xoff = 0, yoff = 0;

        int _lightX = 0;
        int _lightY = 0;
        if (map == null || rotatedEnv == null) {
            LRGBXform(pixels, coeffs, mouseX, mouseY, bias, scale);
            return;
        }

        float sx = (float) width / (float) ptmRect.width;
        float sy = (float) height / (float) ptmRect.height;
        for (int s = 0; s < sources.length; s++) {
            if (!sources[s].isEnabled()) {
                continue;
            }
            if (sources[s].isActive()) {

                _lightX = mouseX + ptmRect.x;
                _lightY = mouseY + ptmRect.y;
            } else {
                _lightX = sources[s].getX() + ptmRect.x;
                _lightY = sources[s].getY() + ptmRect.y;
            }

            final int _lum = Math.round(sources[s].getLuminance() * 256);

            /*
             * for (int x = target.width; --x >= _lightX;) { xp = (int) (sx *
             * ((target.x + x) - ptmRect.x)); if (xp >= width) { break; } xoff =
             * x - _lightX; for (int y = target.height; --y >= _lightY;) { yp =
             * (int) (sy * ((target.y + y) - ptmRect.y)); if (yp >= height) {
             * break; } yoff = y - _lightY; processLRGBEnvLight(xp, yp, x, y,
             * xoff, yoff, s, _lum, pixels, coeffs, bias, scale); } for (int y =
             * _lightY; --y >= 0;) { yp = (int) (sy * ((target.y + y) -
             * ptmRect.y)); if (yp >= height) { break; } yoff = _lightY - y;
             * processLRGBEnvLight(xp, yp, x, y, xoff, yoff, s, _lum, pixels,
             * coeffs, bias, scale); } }
             *
             * for (int x = _lightX; --x >= 0;) { xp = (int) (sx * ((target.x +
             * x) - ptmRect.x)); if (xp >= width) { break; } xoff = _lightX - x;
             * for (int y = target.height; --y >= _lightY;) { yp = (int) (sy *
             * ((target.y + y) - ptmRect.y)); if (yp >= height) { break; } yoff
             * = y - _lightY; processLRGBEnvLight(xp, yp, x, y, xoff, yoff, s,
             * _lum, pixels, coeffs, bias, scale); } for (int y = _lightY; --y
             * >= 0;) { yp = (int) (sy * ((target.y + y) - ptmRect.y)); if (yp
             * >= height) { break; } yoff = _lightY - y; processLRGBEnvLight(xp,
             * yp, x, y, xoff, yoff, s, _lum, pixels, coeffs, bias, scale); }
            }
             */
            int pi = 0, index = 0;
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
                    if (pi > pixels.length || pi < 0) {
                        continue;
                    }

                    if (p >= _lightX) {
                        xoff = p - _lightX;
                    } else if (p < _lightX && p >= 0) {
                        xoff = _lightX - p;
                    }
                    if (q >= _lightY) {
                        yoff = q - _lightY;
                    } else if (q < _lightY && q >= 0) {
                        yoff = _lightY - q;
                    }

                    processLRGBEnvLight(pi, index, xoff, yoff, s, _lum, pixels, coeffs, bias, scale);
                }
            }
        }
    }

    private void LRGBXform(int[] pixels, byte[][] coeffs, int mouseX, int mouseY, float[] bias, float[] scale) {

        int xoff = 0, yoff = 0;
        int _lightX, _lightY;
        int xp, yp;
        for (int s = 0; s < sources.length; s++) {
            if (!sources[s].isEnabled()) {
                continue;
            }
            if (sources[s].isActive()) {

                _lightX = mouseX + ptmRect.x;
                _lightY = mouseY + ptmRect.y;
            } else {
                _lightX = sources[s].getX() + ptmRect.x;
                _lightY = sources[s].getY() + ptmRect.y;
            }

            float sx = (float) width / (float) ptmRect.width;
            float sy = (float) height / (float) ptmRect.height;

            final int _lum = Math.round(sources[s].getLuminance() * 256);

            /*
             * for (int x = target.width; --x >= _lightX;) {
             *
             * xp = (int) (sx * ((target.x + x) - ptmRect.x)); if (xp >= width)
             * { break; } xoff = x - _lightX; for (int y = target.height; --y >=
             * _lightY;) { yp = (int) (sy * ((target.y + y) - ptmRect.y)); if
             * (yp >= height) { break; } yoff = y - _lightY;
             * processLRGBLight(xp, yp, x, y, xoff, yoff, s, _lum, pixels,
             * coeffs, bias, scale);
             *
             * }
             * for (int y = _lightY; --y >= 0;) { yp = (int) (sy * ((target.y +
             * y) - ptmRect.y)); if (yp >= height) { break; } yoff = _lightY -
             * y; processLRGBLight(xp, yp, x, y, xoff, yoff, s, _lum, pixels,
             * coeffs, bias, scale); } }
             *
             *
             * for (int x = _lightX; --x >= 0;) { xp = (int) (sx * ((target.x +
             * x) - ptmRect.x)); if (xp >= width) { break; } xoff = _lightX - x;
             *
             * for (int y = target.height; --y >= _lightY;) { yp = (int) (sy *
             * ((target.y + y) - ptmRect.y)); if (yp >= height) { break; } yoff
             * = y - _lightY; processLRGBLight(xp, yp, x, y, xoff, yoff, s,
             * _lum, pixels, coeffs, bias, scale);
             *
             * }
             * for (int y = _lightY; --y >= 0;) { yp = (int) (sy * ((target.y +
             * y) - ptmRect.y)); if (yp >= height) { break; } yoff = _lightY -
             * y; processLRGBLight(xp, yp, x, y, xoff, yoff, s, _lum, pixels,
             * coeffs, bias, scale);
             *
             * }
             * }
             */
            int pi = 0, index = 0;
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
                    if (pi > pixels.length || pi < 0) {
                        continue;
                    }

                    if (p >= _lightX) {
                        xoff = p - _lightX;
                    } else if (p < _lightX && p >= 0) {
                        xoff = _lightX - p;
                    }
                    if (q >= _lightY) {
                        yoff = q - _lightY;
                    } else if (q < _lightY && q >= 0) {
                        yoff = _lightY - q;
                    }

                    processLRGBLight(pi, index, xoff, yoff, s, _lum, pixels, coeffs, bias, scale);
                }
            }


        }
    }

    private void RGBXform(int[] pixels, byte[][] coefficients, int mouseX, int mouseY, float[] bias, float[] scale) {
        int xoff = 0, yoff = 0;
        int _lightX;
        int _lightY;

        int xp, yp;
        for (int s = 0; s < sources.length; s++) {

            if (!sources[s].isEnabled()) {
                continue;
            }

            if (sources[s].isActive()) {
                _lightX = mouseX + ptmRect.x;
                _lightY = mouseY + ptmRect.y;
            } else {
                _lightX = sources[s].getX() + ptmRect.x;
                _lightY = sources[s].getY() + ptmRect.y;
            }

            final int _lum = Math.round(sources[s].getLuminance() * 256);

            float sx = (float) width / (float) ptmRect.width;
            float sy = (float) height / (float) ptmRect.height;
            int pi = 0, index = 0;

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
                    if (pi > pixels.length || pi < 0) {
                        continue;
                    }

                    if (p >= _lightX) {
                        xoff = p - _lightX;
                    } else if (p < _lightX && p >= 0) {
                        xoff = _lightX - p;
                    }
                    if (q >= _lightY) {
                        yoff = q - _lightY;
                    } else if (q < _lightY && q >= 0) {
                        yoff = _lightY - q;
                    }

                    processRGBLight(pi, index, xoff, yoff, s, _lum, pixels, coefficients, bias, scale);
                }
            }
        }

    }

    private void _operate(PTM ptm, LightSource[] sources, int dW, int dH, int posx, int posy, int pW, int pH, int[] pixels, int mouseX, int mouseY) {
        width = ptm.getWidth();
        height = ptm.getHeight();
        type = ptm.getFileType();
        basis = ptm.getBasisType();
        //sources = ptm.getLights();
        this.sources = sources;
        Rectangle displayRect = new Rectangle(0, 0, dW, dH);
        Rectangle temp = new Rectangle(posx, posy, pW, pH);
        if (Z != ptm.getZ() || !temp.equals(ptmRect)) {
            Z = ptm.getZ();
            ptmRect = temp;

        }



        target = displayRect.intersection(ptmRect);
        this.dW = dW;

        if (type == PTM.TYPE_RAW_PTM) {
            switch (basis) {
                case PTM.BASIS_LRGB:
                    if (!ptm.useEnv()) {
                        LRGBXform(pixels, ptm.getBCoefficients(), mouseX, mouseY, ptm.getBias(), ptm.getScaleArray());
                    } else {
                        map = ((LRGBPTM) ptm).getEnvironmentMapMap();
                        rotatedEnv = ptm.getEnvironmentMap().rotatedMap();
                        LRGBXformEnv(pixels, ptm.getBCoefficients(), mouseX, mouseY, ptm.getBias(), ptm.getScaleArray());
                    }
                    break;
                case PTM.BASIS_RGB:
                    RGBXform(pixels, ptm.getBCoefficients(), mouseX, mouseY, ptm.getBias(), ptm.getScaleArray());
                    break;
                case PTM.BASIS_PRIMITIVE:
                    break;
                /*
                 * TODO: simple grey color
                 */
            }
        } else if (type == PTM.TYPE_RTI_HSH) {
        }
    }

    private void processRGBLight(int pi, int offset, int xoff, int yoff, int s, int _lum, int[] pixels, byte[][] coefficients, float[] bias, float[] scale) {
        int pr = 0, pg = 0, pb = 0;

        float lx, ly, tanlen;
        int red, green, blue;
        byte[] coeff;

        int RED = 0, GREEN = 1, BLUE = 2;

        coeff = coefficients[offset];


        lx = xoff;
        ly = yoff;
        tanlen = 0.0f;
        if (!isFlashLight) {
            tanlen = 1 / (float) Math.sqrt(lx * lx + ly * ly + Z);
        } else {
            tanlen = 1 / (float) Math.sqrt(lx + lx + ly + ly + Z);

        }
        lx *= tanlen;
        ly *= -tanlen;

        red = (int) (((int) (((coeff[RED * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * (lx * lx * 256)) >> 8) + ((int) (((coeff[RED * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * (ly * ly * 256)) >> 8) + ((int) (((coeff[RED * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * (lx * ly * 256)) >> 8) + ((int) (((coeff[RED * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * -(lx * 256)) >> 8) + ((int) (((coeff[RED * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * -(ly * 256)) >> 8) + (((coeff[RED * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]));
        blue = (int) (((int) (((coeff[BLUE * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * (lx * lx * 256)) >> 8) + ((int) (((coeff[BLUE * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * (ly * ly * 256)) >> 8) + ((int) (((coeff[BLUE * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * (lx * ly * 256)) >> 8) + ((int) (((coeff[BLUE * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * -(lx * 256)) >> 8) + ((int) (((coeff[BLUE * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * -(ly * 256)) >> 8) + (((coeff[BLUE * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]));
        green = (int) (((int) (((coeff[GREEN * 6 + 0] & 0x000000FF) - bias[0]) * scale[0] * (lx * lx * 256)) >> 8) + ((int) (((coeff[GREEN * 6 + 1] & 0x000000FF) - bias[1]) * scale[1] * (ly * ly * 256)) >> 8) + ((int) (((coeff[GREEN * 6 + 2] & 0x000000FF) - bias[2]) * scale[2] * (lx * ly * 256)) >> 8) + ((int) (((coeff[GREEN * 6 + 3] & 0x000000FF) - bias[3]) * scale[3] * -(lx * 256)) >> 8) + ((int) (((coeff[GREEN * 6 + 4] & 0x000000FF) - bias[4]) * scale[4] * -(ly * 256)) >> 8) + (((coeff[GREEN * 6 + 5] & 0x000000FF) - bias[5]) * scale[5]));


        red = (red * _lum) >> 8;
        green = (green * _lum) >> 8;
        blue = (blue * _lum) >> 8;


        pr = (s > 0) ? (pixels[pi] >> 16) & 0xff : 0;
        pg = (s > 0) ? (pixels[pi] >> 8) & 0xff : 0;
        pb = (s > 0) ? (pixels[pi]) & 0xff : 0;
        pr += red;
        pg += green;
        pb += blue;
        pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
        pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
        pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
        try {
            pixels[pi] = pr << 16 | pg << 8 | pb;
        } catch (ArrayIndexOutOfBoundsException aioobe) {
           log.error( aioobe);
        }

    }

    private void processLRGBLight(int pi, int offset, int xoff, int yoff, int s, int _lum, int[] pixels, byte[][] coeffs, float[] bias, float[] scale) {
        int pr = 0, pg = 0, pb = 0;

        float lx, ly, tanlen;
        int red, green, blue;


        lx = xoff;
        ly = yoff;
        tanlen = 0.0f;
        if (!isFlashLight) {
            tanlen = 1 / (float) Math.sqrt(lx * lx + ly * ly + Z);
        } else {
            tanlen = 1 / (float) Math.sqrt(lx + lx + ly + ly + Z);
        }
        lx *= tanlen;
        ly *= -tanlen;

        int intensity = (int) (((int) (((coeffs[0][offset] & 0x000000FF) - bias[0]) * scale[0] * (lx * lx * 256)) >> 8) + ((int) (((coeffs[1][offset] & 0x000000FF) - bias[1]) * scale[1] * (ly * ly * 256)) >> 8) + ((int) (((coeffs[2][offset] & 0x000000FF) - bias[2]) * scale[2] * (lx * ly * 256)) >> 8) + ((int) (((coeffs[3][offset] & 0x000000FF) - bias[3]) * scale[3] * -(lx * 256)) >> 8) + ((int) (((coeffs[4][offset] & 0x000000FF) - bias[4]) * scale[4] * -(ly * 256)) >> 8) + ((int) ((coeffs[5][offset] & 0x000000FF) - bias[5]) * scale[5]));
        int m = _lum * intensity;
        red = coeffs[6][offset] & 0x000000FF;
        green = coeffs[7][offset] & 0x000000FF;
        blue = coeffs[8][offset] & 0x000000FF;
        red = (red * m) >> 16;
        green = (green * m) >> 16;
        blue = (blue * m) >> 16;

        pr = (s > 0) ? (pixels[pi] >> 16) & 0xff : 0;
        pg = (s > 0) ? (pixels[pi] >> 8) & 0xff : 0;
        pb = (s > 0) ? (pixels[pi]) & 0xff : 0;
        pr += red;
        pg += green;
        pb += blue;
        pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
        pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
        pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
        try {
            pixels[pi] = pr << 16 | pg << 8 | pb;
        } catch (ArrayIndexOutOfBoundsException aioobe) {
        }
    }

    private void processLRGBEnvLight(int pi, int offset, int xoff, int yoff, int s, int _lum, int[] pixels, byte[][] coeffs, float[] bias, float[] scale) {
        int pr = 0, pg = 0, pb = 0;
        float lx, ly, tanlen;
        int red, green, blue;

        int _er = 256, _eg = 256, _eb = 256, pix = 0;



        lx = xoff;
        ly = yoff;
        tanlen = 0.0f;
        if (!isFlashLight) {
            tanlen = 1 / (float) Math.sqrt(lx * lx + ly * ly + Z);
        } else {
            tanlen = 1 / (float) Math.sqrt(lx + lx + ly + ly + Z);
        }
        lx *= tanlen;
        ly *= -tanlen;

        int intensity = (int) (((int) (((coeffs[0][offset] & 0x000000FF) - bias[0]) * scale[0] * (lx * lx * 256)) >> 8) + ((int) (((coeffs[1][offset] & 0x000000FF) - bias[1]) * scale[1] * (ly * ly * 256)) >> 8) + ((int) (((coeffs[2][offset] & 0x000000FF) - bias[2]) * scale[2] * (lx * ly * 256)) >> 8) + ((int) (((coeffs[3][offset] & 0x000000FF) - bias[3]) * scale[3] * -(lx * 256)) >> 8) + ((int) (((coeffs[4][offset] & 0x000000FF) - bias[4]) * scale[4] * -(ly * 256)) >> 8) + ((int) ((coeffs[5][offset] & 0x000000FF) - bias[5]) * scale[5]));
        int m = _lum * intensity;
        red = coeffs[6][offset] & 0x000000FF;
        green = coeffs[7][offset] & 0x000000FF;
        blue = coeffs[8][offset] & 0x000000FF;
        pix = rotatedEnv[map[offset]];
        _er = (pix >> 16) & 0xff;
        _eg = (pix >> 8) & 0xff;
        _eb = pix & 0xff;

        red = (red * ((m * _er) >> 8)) >> 16;
        green = (green * ((m * _eg) >> 8)) >> 16;
        blue = (blue * ((m * _eb) >> 8)) >> 16;


        pr = (s > 0) ? (pixels[pi] >> 16) & 0xff : 0;
        pg = (s > 0) ? (pixels[pi] >> 8) & 0xff : 0;
        pb = (s > 0) ? (pixels[pi]) & 0xff : 0;
        pr += red;
        pg += green;
        pb += blue;
        pr = pr > 255 ? 255 : ((pr < 0) ? 0 : pr);
        pg = pg > 255 ? 255 : ((pg < 0) ? 0 : pg);
        pb = pb > 255 ? 255 : ((pb < 0) ? 0 : pb);
        try {
            pixels[pi] = pr << 16 | pg << 8 | pb;
        } catch (ArrayIndexOutOfBoundsException aioobe) {
        }
    }

    public void release() {
    }

    public void forceUpdate() {
    }

    public void clearCache() {
    }

    public void performOperation(int[] pixels, LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH) {
        _operate(ptm, sources, dW, dH, posx, posy, pW, pH, pixels, mouseX, mouseY);

    }

    @Override
    public String getName() {
        if(isFlashLight)
            return "FlashLight";
        else
            return "SpotLight";
    }
}
