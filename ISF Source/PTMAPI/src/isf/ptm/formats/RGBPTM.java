/*
 * Created on Jul 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package isf.ptm.formats;

import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.graphics.VRGB;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Default
 * <p/>
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RGBPTM implements PTM, Serializable {

    public RGBPTM() {
        super();
    }

    public void computeNormals() {
        if (null == normals) {
            normals = Normals.getNormals(bcoeff, true, scale, bias);
        }
    }

    public void setBCoefficients(byte[][] coeff) {
        _setCoeff(coeff);

    }

    private void _setCoeff(byte[][] coeff) {
        size = width * height;
        if (coeff == null) {
            this.bcoeff = new byte[size][18];
        } else {
            this.bcoeff = coeff;
            size = coeff.length;
        }
    }

    public float getDGain() {
        return m_dGain;
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#getEnvironmentMap()
     */
    public EnvironmentMap getEnvironmentMap() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#getEnvironmentMapCache()
     */
    public int[] getEnvironmentMapCache() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#getEnvironmentMapMap()
     */
    public int[] getEnvironmentMapMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getExp() {
        return EXP;
    }

    public int getHeight() {
        return height;
    }

    public float getKDiff() {
        return KDIFF;
    }

    public float getKSpec() {
        return KSPEC;
    }

    /*
     * public float getLuminance(int source) { return
     * lights[source].getLuminance();
    }
     */
    public ArrayList getNormals() {
        if (normals == null) {
            computeNormals();
        }
        return normals;
    }

    public int getFileType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#getZ()
     */
    public int getZ() {
        // TODO Auto-generated method stub
        return Z;
    }

    public int red(int i) {
        return 0;
    }

    public int green(int i) {
        return 0;
    }

    public int blue(int i) {
        return 0;
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

    /**
     * Resize the PTM.
     *
     * @param ptmWid Width after resizing.
     * @param ptmHei Height after resizing.
     */
    public void resize(int w, int h) {
        _reSize(w, h);
    }

    private void _reSize(int w, int h) {
        byte[][] coeffs = new byte[w * h][18];

        int xp, yp;
        float sx = (float) width / (float) w;
        float sy = (float) height / (float) h;

        // resize coefficients
        for (int channel = 0; channel < 3; channel++) {
            for (int i = 0; i < 6; i++) {
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        xp = (int) (sx * x);
                        yp = (int) (sy * y);

                        coeffs[x + y * w][channel * 6 + i] = bcoeff[xp + yp * width][channel * 6 + i];
                    }
                }
            }
        }

        // assign new size
        bcoeff = coeffs;
        width = w;
        height = h;

        // invoke garbage collector
        System.gc();
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#recache()
     */
    public void recache() {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#release()
     */
    public void release() {
        // TODO Auto-generated method stub
        bcoeff = null;
        if (normals != null) {
            normals = null;
        }
        // lights = null;
        scale = null;
        bias = null;
    }

    public void setCoeff(byte[][] coeff) {
        size = coeff.length;
        this.bcoeff = coeff;
    }

    public void setDGain(float f) {
        m_dGain = Math.max(f, 1.0f);
    }

    public void setExp(int i) {
        EXP = i;
    }

    public void setHeight(int height) {
        size = width * height;
        this.height = height;

    }

    public void setKDiff(float f) {
        KDIFF = f;
    }

    public void setKSpec(float f) {
        KSPEC = f;
    }

    /*
     * public void setLuminance(float f, int source) {
     * lights[source].setLuminance(f);
    }
     */
    public void setWidth(int width) {
        size = width * height;
        this.width = width;
    }

    public void drawSubPtm(int offx, int offy, int wid, int hei, byte[][] rgbcoeff) {
        _drawSP1(hei, wid, offy, offx, rgbcoeff);
    }

    private void _drawSP1(int hei, int wid, int offy, int offx, byte[][] rgbcoeff) {
        int RED = 0;
        int BLUE = 2;
        for (int h = 0; h < hei; h++) {
            for (int w = 0; w < wid; w++) {
                int offset = (h * wid) + w;
                int offset2 = ((h + offy) * width) + ((w + offx));
                for (int band = RED; band < BLUE; band++) {
                    bcoeff[offset2] = rgbcoeff[offset];
                }
            }
        }
    }

    public void drawSubPtm(int offx, int offy, PTM ptm) {
        _drawSP2(ptm, offy, offx);
    }

    private void _drawSP2(PTM ptm, int offy, int offx) {
        RGBPTM rgbptm = (RGBPTM) ptm;
        byte[][] rgbcoeff = rgbptm.getBCoefficients();
        int RED = 0;
        int BLUE = 2;


        int sW = rgbptm.getWidth();
        int sH = rgbptm.getHeight();

        for (int h = 0; h < sH; h++) {
            for (int w = 0; w < sW; w++) {
                int offset = (h * sW) + w;
                int offset2 = ((h + offy) * width) + ((w + offx));
                for (int band = RED; band < BLUE; band++) {
                    for (int i = 0; i < 6; i++) {

                        bcoeff[offset2][band * 6 + i] = rgbcoeff[offset][band * 6 + i];

                    }
                }
            }
        }
    }

    public void drawSubPtm(int offx, int offy, PTM ptm, int block) {
        _drawSP3(ptm, offy, offx);
    }

    private void _drawSP3(PTM ptm, int offy, int offx) {
        RGBPTM rgbptm = (RGBPTM) ptm;
        byte[][] rgbcoeff = rgbptm.getBCoefficients();
        int RED = 0;
        int BLUE = 2;


        int sW = rgbptm.getWidth();
        int sH = rgbptm.getHeight();
        for (int h = 0; h < sH; h++) {
            for (int w = 0; w < sW; w++) {
                int offset = (h) + w * sH;
                int offset2 = ((h + offy)) + ((w + offx) * height);
                for (int band = RED; band < BLUE; band++) {
                    for (int i = 0; i < 6; i++) {

                        bcoeff[offset2][band * 6 + i] = rgbcoeff[offset][band * 6 + i];

                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#setZ(int)
     */
    public void setZ(int z) {
        // TODO Auto-generated method stub
        Z = z;
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#setEnvironmentMap(jpview.graphics.EnvironmentMap)
     */
    public void setEnvironmentMap(EnvironmentMap em) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#useEnv()
     */
    public boolean useEnv() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see jpview.ptms.PTM#useEnv(boolean)
     */
    public void useEnv(boolean b) {
        // TODO Auto-generated method stub
    }


    public void setVersion(String v) {
        version = v;
    }

    public void setScale(float v) {
        extrapolate_scale = v;
    }

    public void setScaleArray(float[] v) {
        scale = v;
    }

    public void setBias(float[] v) {
        bias = v;
    }

    public String getVersion() {
        return version;
    }

    public float[] getScaleArray() {
        return scale;
    }

    public float getScale() {
        return extrapolate_scale;
    }

    public float[] getBias() {
        return bias;
    }

    /*
     * public LightSource[] getLights() { return lights;
    }
     */
    public boolean isExtrapolation() {
        return extrapolate;
    }

    public void setExtrapolation(boolean flag) {
        extrapolate = flag;
    }

    public byte[][] getBCoefficients() {
        return bcoeff;
    }

    public int[][] getCoefficients() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBCoeffs(int top, int left, int tileh, int tilew, int band, byte[] bgrPixels) {
        _setBC(top, tileh, left, tilew, band, bgrPixels);

    }

    private void _setBC(int top, int tileh, int left, int tilew, int band, byte[] bgrPixels) {
        int offset, pos;
        for (int y = top, b = 0; y < tileh + top; y++, b++) {
            for (int x = left, a = 0; x < tilew + left; x++, a++) {
                offset = y * width + x;
                pos = b * tilew + a;
                bcoeff[offset][12 + band] = bgrPixels[pos * 3 + 0];
                bcoeff[offset][6 + band] = bgrPixels[pos * 3 + 1];
                bcoeff[offset][band] = bgrPixels[pos * 3 + 2];

            }
        }
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public byte[][] getResizedCoeffs(int w, int h) {
        return _getRC(w, h);
    }

    private byte[][] _getRC(int w, int h) {
        byte[] bgr = new byte[width * height * 3];
        byte[] processed = new byte[w * h * 3];
        byte[][] coeffs = new byte[w * h][18];
        int offset = 0;
        BufferedImage source = null, dest = null;
        for (int i = 0; i < 6; i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    offset = y * width + x;

                    bgr[offset * 3 + 0] = bcoeff[offset][2 * 6 + i];
                    bgr[offset * 3 + 1] = bcoeff[offset][1 * 6 + i];
                    bgr[offset * 3 + 2] = bcoeff[offset][0 * 6 + i];

                }
            }
            source = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            source.getRaster().setDataElements(0, 0, width, height, bgr);
            dest = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D gd = dest.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance((w / width), (h / height));
            gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gd.drawRenderedImage(source, at);


            processed = ((DataBufferByte) dest.getRaster().getDataBuffer()).getData();
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    offset = y * w + x;
                    coeffs[offset][i + 6 * 2] = processed[offset * 3 + 0];
                    coeffs[offset][i + 6 * 1] = processed[offset * 3 + 1];
                    coeffs[offset][i + 6 * 0] = processed[offset * 3 + 2];
                }
            }

        }
        return coeffs;
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
    private byte[][] bcoeff;
    private int size = 0;
    private boolean extrapolate = false;
    private int width;
    private int height;
    private int EXP = 60;
    private float KDIFF = 0.40f;
    private float KSPEC = 0.35f;
    private float LUM = 1.0f;
    private float m_dGain = 1.16f;
    private int Z = 10000;
    private ArrayList normals;
    private String name;
    private int colors;
        private String version;
    private float[] scale;
    private float[] bias;
    private float extrapolate_scale;
}
