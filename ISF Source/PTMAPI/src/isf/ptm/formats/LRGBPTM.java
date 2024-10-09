/*
 * PTMDataBuffer.java
 *
 * Created on September 5, 2004, 5:04 PM
 */
package isf.ptm.formats;

import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.graphics.VRGB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Default
 */
public class LRGBPTM implements PTM, Serializable {

    // private LightSource[] lights = new LightSource[MAX_LIGHTS];
    public void release() {
        if (em != null) {
            em.release();
        }
        em = null;
        environmentMapCache = null;
        environmentMapMap = null;
        if (normals != null) {
            /*
             * for (int i = 0; i < normals.length; i++) { normals[i] = null;
            }
             */
            normals = null;
        }
        coefficients = null;
    }
    /**
     * map
     */
    protected EnvironmentMap em = null;
    /**
     * normals
     */
    private ArrayList normals = null;
    protected int[][] coefficients = null;
    protected int[] environmentMapCache = null;
    protected int[] environmentMapMap = null;

    private void _drawSP1(PTM ptm, int offy, int offx) {
        LRGBPTM lrgbptm = (LRGBPTM) ptm;
        int[][] coeff = lrgbptm.getCoefficients();

        int offset = 0;
        int w = ptm.getWidth();
        for (int y = offy; y < offy + ptm.getHeight(); y++) {
            for (int x = offx; x < offx + ptm.getWidth(); x++) {
                for (int i = 0; i < 7; i++) {
                    coefficients[x + y * width][i] = coeff[offset][i];
                }
                offset++;
            }
        }
    }

    private void _drawSP2(PTM ptm, int w, int h, int offy, int offx) {
        float sx = (float) ptm.getWidth() / (float) w;
        float sy = (float) ptm.getHeight() / (float) h;

        LRGBPTM lrgbptm = (LRGBPTM) ptm;
        int[][] coeff = lrgbptm.getCoefficients();

        int r00, r01, r10, r11;
        int g00, g01, g10, g11;
        int b00, b01, b10, b11;
        int rgb00, rgb01, rgb10, rgb11;
        int r, g, b;
        int offset;
        float xp, yp;
        float alpha, beta;
        int xprime, yprime;
        int sourceW = ptm.getWidth();
        int sourceH = ptm.getHeight();
        for (int y = offy; y < offy + h; y++) {
            for (int x = offx; x < offx + w; x++) {
                xp = (float) x * sx;
                yp = (float) y * sy;

                alpha = xp - (float) Math.floor(xp);
                beta = yp - (float) Math.floor(yp);

                xprime = (int) xp;
                yprime = (int) yp;

                // Coefficients interpolation

                if ((xp < sourceW - 1) && (yp < sourceH - 1)) {
                    offset = xprime + yprime * sourceW;

                    for (int i = 0; i < 6; i++) {
                        coefficients[x + y * width][i] = (int) ((1.0f - alpha) * (1.0f - beta) * (float) coeff[offset][i]
                                + alpha * (1.0f - beta) * (float) coeff[offset + 1][i]
                                + (1.0f - alpha) * beta * (float) coeff[offset + sourceW][i]
                                + alpha * beta * (float) coeff[offset + 1 + sourceW][i]);
                        // RGB interpolation
                    }
                    rgb00 = coeff[offset][6];
                    rgb01 = coeff[offset + 1][6];
                    rgb10 = coeff[offset + sourceW][6];
                    rgb11 = coeff[offset + sourceW + 1][6];

                    r00 = rgb00 & 0x00ff0000;
                    r01 = rgb01 & 0x00ff0000;
                    r10 = rgb10 & 0x00ff0000;
                    r11 = rgb11 & 0x00ff0000;

                    r = (int) ((1.0f - alpha) * (1.0f - beta) * (float) r00 + alpha * (1.0f - beta) * (float) r01
                            + (1.0f - alpha) * beta * (float) r10 + alpha * beta * (float) r11);

                    g00 = rgb00 & 0x0000ff00;
                    g01 = rgb01 & 0x0000ff00;
                    g10 = rgb10 & 0x0000ff00;
                    g11 = rgb11 & 0x0000ff00;

                    g = (int) ((1.0f - alpha) * (1.0f - beta) * (float) g00 + alpha * (1.0f - beta) * (float) g01
                            + (1.0f - alpha) * beta * (float) g10 + alpha * beta * (float) g11);

                    b00 = rgb00 & 0x000000ff;
                    b01 = rgb01 & 0x000000ff;
                    b10 = rgb10 & 0x000000ff;
                    b11 = rgb11 & 0x000000ff;

                    b = (int) ((1.0f - alpha) * (1.0f - beta) * (float) b00 + alpha * (1.0f - beta) * (float) b01
                            + (1.0f - alpha) * beta * (float) b10 + alpha * beta * (float) b11);

                    coefficients[x + y * width][6] = (r << 16) | (g << 8) | b;
                } else {
                    if (xprime + 1 >= sourceW) {
                        xprime--;
                    }
                    if (yprime + 1 >= sourceH) {
                        yprime--;
                    }
                    for (int i = 0; i < 7; i++) {
                        coefficients[x + y * width][i] = coeff[xprime + yprime * sourceW][i];
                    }
                }
            }
        }
    }

    private int[] _gA0(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][0];
                offset++;
            }
        }
        return buffer;
    }

    private int[] _gA1(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][1];
                offset++;
            }
        }
        return buffer;
    }

    private int[] _gA2(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][2];
                offset++;
            }
        }
        return buffer;
    }

    private int[] _gA3(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][3];
                offset++;
            }
        }
        return buffer;
    }

    private int[] _gA4(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][4];
                offset++;
            }
        }
        return buffer;
    }

    private int[] _gA5(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][5];
                offset++;
            }
        }
        return buffer;
    }

    private int[] _gRGB(int right, int left, int bottom, int top) {
        int[] buffer = new int[(right - left + 1) * (bottom - top + 1)];

        int x, y, offset = 0, offset2;
        for (y = top; y <= bottom; y++) {
            for (x = left; x <= right; x++) {
                offset2 = x + y * width;
                buffer[offset] = coefficients[offset2][6];
                offset++;
            }
        }
        return buffer;
    }

    private int[][] _getRC(int wid, int w, int hei, int h, int offx, int offy) {
        int dx = (wid > 0) ? wid : w;
        int dy = (hei > 0) ? hei : h;
        int rw = (offx > 0) ? (wid + offx) : w;
        int rh = (offy > 0) ? (hei + offy) : h;
        int[][] coeffs = new int[dx * dy][7];

        if (coefficients != null) {


            int xp, yp;
            float sx = (float) width / (float) w;
            float sy = (float) height / (float) h;

            // resize coefficients only 
            for (int i = 0; i < 6; i++) {
                for (int y = offy; y < rh; y++) {
                    for (int x = offx; x < rw; x++) {
                        xp = (int) (sx * x);
                        yp = (int) (sy * y);

                        coeffs[(x - offx) + (y - offy) * w][i] = coefficients[xp + yp * width][i];
                    }
                }
            }

            // resize RGB plane
            for (int y = offy; y < rh; y++) {
                for (int x = offx; x < rw; x++) {
                    xp = (int) (sx * x);
                    yp = (int) (sy * y);

                    coeffs[(x - offx) + (y - offy) * w][6] = coefficients[xp + yp * width][6];
                }
            }


        }
        return coeffs;
    }

    private void _initA0(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][0] = a[i];
        }
    }

    private void _initA1(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][1] = a[i];
        }
    }

    private void _initA2(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][2] = a[i];
        }
    }

    private void _initA3(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][3] = a[i];
        }
    }

    private void _initA4(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][4] = a[i];
        }
    }

    private void _initA5(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][5] = a[i];
        }
    }

    private void _initRGB(int[] a) {
        if (coefficients == null) {
            coefficients = new int[a.length][7];
        }
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i][6] = a[i];
        }
    }

    private void _intCoeffs(int[][] coeff) {
        if (coeff == null) {
            coefficients = new int[width * height][7];
        } else {
            //assert(width * height * 7 == coeff.length);
            coefficients = coeff;
        }
    }

    private void _reSize(int w, int h) {
        if (coefficients != null) {
            int[][] coeffs = new int[w * h][7];

            int xp, yp;
            float sx = (float) width / (float) w;
            float sy = (float) height / (float) h;

            // resize coefficients only 
            for (int i = 0; i < 6; i++) {
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        xp = (int) (sx * x);
                        yp = (int) (sy * y);

                        coeffs[x + y * w][i] = coefficients[xp + yp * width][i];
                    }
                }
            }

            // resize RGB plane
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    xp = (int) (sx * x);
                    yp = (int) (sy * y);

                    coeffs[x + y * w][6] = coefficients[xp + yp * width][6];
                }
            }

            // assign new size
            coefficients = coeffs;
            width = w;
            height = h;
        }
    }

    private void createEnvironmentMapCache() {
        environmentMapCache = new int[width * height];

        if (normals == null) {
            this.computeNormals();
        }
        if (this.em == null) {
           // System.out.println("Can't compute environment map cache, no environment map");
        }
        for (int i = 0; i < normals.size(); i++) {
            environmentMapCache[i] = this.em.getPixel((VRGB) normals.get(i));
        }
    }

    private void createEnvironmentMapMap() {
        environmentMapMap = new int[width * height];

        if (normals == null) {
            this.computeNormals();
        }
        if (this.em == null) {
            //System.out.println("Can't compute environment map cache, no environment map");
        }

        for (int i = 0; i < normals.size(); i++) {
            environmentMapMap[i] = this.em.getMapIndex((VRGB) normals.get(i));
        }
    }

    /**
     * This functions updates the current PTM with the data of the given
     * sub-ptm.
     *
     * @param offx Horizontal offset
     * @param offy Vertical offset
     * @param ptm Sub-ptm
     */
    public void drawSubPtm(int offx, int offy, PTM ptm) {
        _drawSP1(ptm, offy, offx);
    }

    /**
     * This function updates the current PTM with the data of the given sub-ptm.
     * The sub-ptm is stretched to the desired dimensions.
     *
     * @param offx Horizontal offset
     * @param offy Vertical offset
     * @param w Target width
     * @param h Target height
     * @param ptm Sub-ptm
     */
    public void drawSubPtm(int offx, int offy, int w, int h, PTM ptm) {
        _drawSP2(ptm, w, h, offy, offx);
    }

    public int[] getEnvironmentMapCache() {
        if (this.environmentMapCache == null) {
            createEnvironmentMapCache();
        }
        return this.environmentMapCache;
    }

    public int[] getEnvironmentMapMap() {
        if (this.environmentMapMap == null) {
            createEnvironmentMapMap();
        }
        return this.environmentMapMap;
    }

    public void recache() {
        this.environmentMapCache = null;
        this.environmentMapMap = null;
    }
    /**
     * PTM Attributes
     */
    private int width = 0;
    private int height = 0;

    /**
     * Creates a new instance of PTMDataBuffer
     */
    public LRGBPTM() {
    }

    /**
     * Get LRGBPTM coefficients.
     *
     * Coefficients are organized as follows:
     *
     * coefficients[i][0] --> coefficient a0 of the i-th pixel
     * coefficients[i][1] --> coefficient a1 of the i-th pixel ...
     * coefficients[i][5] --> coefficient a5 of the i-th pixel
     * coefficients[i][6] --> BASIS_RGB color encoded as 0x00RRGGBB
     */
    public int[][] getCoefficients() {
        return coefficients;
    }

    /**
     * Set LRGBPTM coefficients.
     *
     * If coeff is null the coefficients are allocated.
     */
    public void setCoefficients(int[][] coeff) {
        _intCoeffs(coeff);
    }

    /**
     * settors
     */
    public void setA0(int[] a) {
        _initA0(a);
    }

    public void setA1(int[] a) {
        _initA1(a);
    }

    public void setA2(int[] a) {
        _initA2(a);
    }

    public void setA3(int[] a) {
        _initA3(a);
    }

    public void setA4(int[] a) {
        _initA4(a);
    }

    public void setA5(int[] a) {
        _initA5(a);
    }

    public void setRGB(int[] a) {
        _initRGB(a);
    }

    public int[] getA0() {
        return getA0(0, 0, width - 1, height - 1);
    }

    public int[] getA0(int left, int top, int right, int bottom) {
        return _gA0(right, left, bottom, top);
    }

    public int[] getA1() {
        return getA1(0, 0, width - 1, height - 1);
    }

    public int[] getA1(int left, int top, int right, int bottom) {
        return _gA1(right, left, bottom, top);
    }

    public int[] getA2() {
        return getA2(0, 0, width - 1, height - 1);
    }

    public int[] getA2(int left, int top, int right, int bottom) {
        return _gA2(right, left, bottom, top);
    }

    public int[] getA3() {
        return getA3(0, 0, width - 1, height - 1);
    }

    public int[] getA3(int left, int top, int right, int bottom) {
        return _gA3(right, left, bottom, top);
    }

    public int[] getA4() {
        return getA4(0, 0, width - 1, height - 1);
    }

    public int[] getA4(int left, int top, int right, int bottom) {
        return _gA4(right, left, bottom, top);
    }

    public int[] getA5() {
        return getA5(0, 0, width - 1, height - 1);
    }

    public int[] getA5(int left, int top, int right, int bottom) {
        return _gA5(right, left, bottom, top);
    }

    public int[] getRGB() {
        return getRGB(0, 0, width - 1, height - 1);
    }

    public int[] getRGB(int left, int top, int right, int bottom) {
        return _gRGB(right, left, bottom, top);
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int red(int i) {
        return (coefficients[i][6] >> 16) & 0xff;
    }

    public int green(int i) {
        return (coefficients[i][6] >> 8) & 0xff;
    }

    public int blue(int i) {
        return coefficients[i][6] & 0xff;
    }

    protected int x(int i) {
        return i % width;
    }

    protected int y(int i) {
        return i / height;
    }

    public void computeNormals() {
        if (null == normals) {
            normals = Normals.getNormals(this);
        }
    }

    public VRGB normal(int i) {
        return (VRGB) normals.get(i);
    }

    public VRGB normal(int x, int y) {
        VRGB n = null;

        try {
            return (VRGB) normals.get(y * width + x);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }

        return n;
    }

    public void setEnvironmentMap(EnvironmentMap map) {
        em = map;
    }

    public EnvironmentMap getEnvironmentMap() {
        return em;
    }

    public int getFileType() {
        return type;
    }

    public ArrayList getNormals() {
        if (normals == null) {
            computeNormals();
        }
        return normals;
    }

    public void setKSpec(float f) {
        KSPEC = f;
    }

    public void setKDiff(float f) {
        KDIFF = f;
    }

    public void setExp(int i) {
        EXP = i;
    }

    public int getExp() {
        return EXP;
    }

    public float getKDiff() {
        return KDIFF;
    }

    public float getKSpec() {
        return KSPEC;
    }

    public float getDGain() {
        return DGAIN;
    }

    public void setDGain(float f) {
        DGAIN = Math.max(f, 1.0f);
    }

    /*
     * public float getLuminance(int source) { return
     * lights[source].getLuminance(); }
     *
     * public void setLuminance(float f, int source) {
     * lights[source].setLuminance(f);
    }
     */
    public boolean useEnv() {
        return useEnv;
    }

    public void useEnv(boolean b) {
        useEnv = b;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }

    /**
     * Resize the PTM.
     *
     * @param w Width after resizing
     * @param h Height after resizing
     */
    public void resize(int w, int h) {
        _reSize(w, h);
    }

    public void setVersion(String v) {
        version = v;
    }

    public void setBias(float[] v) {
        bias = v;
    }

    public String getVersion() {
        return version;
    }

    public void setScale(float v) {
        extrapolate_scale = v;
    }

    public void setScaleArray(float[] v) {
        scale = v;
    }

    public float[] getBias() {
        return bias;
    }

    /*
     * public LightSource[] getLights() { return lights;
    }
     */
    public float getScale() {
        return extrapolate_scale;
    }

    public float[] getScaleArray() {
        return scale;
    }

    public boolean isExtrapolation() {
        return extrapolate;
    }

    public void setExtrapolation(boolean flag) {
        extrapolate = flag;
    }

    public int[][] getResizedCoeff(int w, int h) {
        return getResizedCoeff(w, h, 0, 0, 0, 0);
    }

    public int[][] getResizedCoeff(int w, int h, int offx, int offy, int wid, int hei) {
        return _getRC(wid, w, hei, h, offx, offy);


    }

    public byte[][] getBCoefficients() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBCoeffs(int top, int left, int tileh, int tilew, int band, byte[] bgrPixels) {
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public int getBasisType() {
        return basis;
    }

    public void setBasisType(int basis) {
        this.basis = basis;
    }

    public void setFileType(int type) {
        this.type = type;
    }
    private int type, basis;
    private boolean extrapolate = false;
    private float extrapolate_scale;
    private int colors;
    private boolean useEnv = false;
    private float DGAIN = 1.16f;
    private float KSPEC = 0.35f;
    private float KDIFF = 0.4f;
    private int EXP = 60;
    private int Z = 10000;
    private String version;
    private float[] scale;
    private float[] bias;
    private String name;
}
