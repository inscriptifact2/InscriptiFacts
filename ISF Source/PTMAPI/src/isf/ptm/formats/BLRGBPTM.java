/*
 * PTMDataBuffer.java
 *
 * Created on September 5, 2004, 5:04 PM
 */
package isf.ptm.formats;

import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.graphics.VRGB;
import isf.ptm.utils.LightSource;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Default
 */
public class BLRGBPTM implements PTM,Serializable {


    public void release() {
        if (em != null) {
            em.release();
        }
        em = null;
        environmentMapCache = null;
        environmentMapMap = null;
        if (normals != null) {
            for (int i = 0; i < normals.size(); i++) {
                normals.set(i, null);
            }
            normals = null;
        }
        coefficients = null;
    }
    /** map */
    protected EnvironmentMap em = null;
    /** normals */
    private ArrayList normals = null;
    protected byte[][] coefficients = null;
    protected int[] environmentMapCache = null;
    protected int[] environmentMapMap = null;

    private void _drawSP1(int hei, int wid, int offy, int offx, byte[][] coeff) {
        int offset = 0, offset2 = 0;
          for (int h = 0; h < hei; h++) {
              for (int w = 0; w < wid; w++) {
                  offset = (h * wid) + w;
                  offset2 = ((h + offy) * width) + ((w + offx));
                  for (int i = 0; i < 9; i++) {
                      coefficients[i][offset2] = coeff[i][offset];
                  }
              }
          }
    }

    private void _drawSP2(PTM ptm, int offy, int offx) {
        BLRGBPTM lrgbptm = (BLRGBPTM) ptm;
        byte[][] coeff = lrgbptm.getBCoefficients();

        int offset = 0;
        int w = ptm.getWidth();
        for (int y = offy; y < offy + ptm.getHeight(); y++) {
            for (int x = offx; x < offx + ptm.getWidth(); x++) {
                for (int i = 0; i < 9; i++) {
                    coefficients[i][x + y * width] = coeff[i][offset];
                }
                offset++;
            }
        }
    }

    private byte[][] _getRC(int wid, int w, int hei, int h, int offx, int offy) {
        int dx = (wid > 0) ? wid : w;
        int dy = (hei > 0) ? hei : h;
        int rw = (offx > 0) ? (wid + offx) : w;
        int rh = (offy > 0) ? (hei + offy) : h;
        byte[][] coeffs = new byte[9][dx * dy];

        if (coefficients != null) {


            int xp, yp;
            float sx = (float) width / (float) w;
            float sy = (float) height / (float) h;

            // resize coefficients only
            for (int i = 0; i < 9; i++) {
                for (int y = offy; y < rh; y++) {
                    for (int x = offx; x < rw; x++) {
                        xp = (int) (sx * x);
                        yp = (int) (sy * y);

                        coeffs[i][(x - offx) + (y - offy) * w] = coefficients[i][xp + yp * width];
                    }
                }
            }
        }
        return coeffs;
    }

    private void _initBC(int top, int tileh, int left, int tilew, int band, byte[] bgrPixels) {
        int offset, pos;
        for (int y = top, b = 0; y < tileh + top; y++, b++) {
            for (int x = left, a = 0; x < tilew + left; x++, a++) {
                offset = y * width + x;
                pos = b * tilew + a;
                coefficients[band * 3 + 2][offset] = bgrPixels[pos * 3 + 0];
                coefficients[band * 3 + 1][offset] = bgrPixels[pos * 3 + 1];
                coefficients[band * 3 + 0][offset] = bgrPixels[pos * 3 + 2];
            }
        }
    }

    private void _initCoeff(byte[][] coeff) {
        if (coeff == null) {
            coefficients = new byte[9][width * height];
        } else {
            //assert(width * height * 7 == coeff.length);
            coefficients = coeff;
        }
    }

    private void _reSize(int w, int h) {
        if (coefficients != null) {
            byte[][] coeffs = new byte[9][w * h];

            int xp, yp;
            float sx = (float) width / (float) w;
            float sy = (float) height / (float) h;

            // resize coefficients only
            for (int i = 0; i < 9; i++) {
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        xp = (int) (sx * x);
                        yp = (int) (sy * y);

                        coeffs[i][x + y * w] = coefficients[i][xp + yp * width];
                    }
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
            //System.out.println("Can't compute environment map cache, no environment map");
        }
        for (int i = 0; i < normals.size(); i++) {
            environmentMapCache[i] = this.em.getPixel((VRGB)normals.get(i));
        }
    }

    private void createEnvironmentMapMap() {
        environmentMapMap = new int[width * height];

        if (normals == null) {
            this.computeNormals();
        }
        if (this.em == null) {
           // System.out.println("Can't compute environment map cache, no environment map");
        }

        for (int i = 0; i < normals.size(); i++) {
            environmentMapMap[i] = this.em.getMapIndex((VRGB)normals.get(i));
        }
    }

    public void drawSubPtm(int offx, int offy, int wid, int hei, byte[][] coeff) {
        _drawSP1(hei, wid, offy, offx, coeff);
    }

    /**
     * This functions updates the current PTM with the data of the
     * given sub-ptm.
     *
     * @param offx    Horizontal offset
     * @param offy    Vertical offset
     * @param ptm     Sub-ptm
     */
    public void drawSubPtm(int offx, int offy, PTM ptm) {
        _drawSP2(ptm, offy, offx);
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
    /** PTM Attributes */
    private int width = 0;
    private int height = 0;

    /** Creates a new instance of  */
    public BLRGBPTM() {

     }

    /**
     * Get LRGBPTM coefficients.
     *
     * Coefficients are organized as follows:
     *
     *    coefficients[i][0] --> coefficient a0 of the i-th pixel
     *    coefficients[i][1] --> coefficient a1 of the i-th pixel
     *    ...
     *    coefficients[i][5] --> coefficient a5 of the i-th pixel
     *    coefficients[i][6] --> BASIS_RGB color encoded as 0x00RRGGBB
     */
    public byte[][] getBCoefficients() {
        return coefficients;
    }

    /**
     * Set LRGBPTM coefficients.
     *
     *  If coeff is null the coefficients are allocated.
     */
    public void setCoefficients(byte[][] coeff) {
        _initCoeff(coeff);
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

    protected int x(int i) {
        return i % width;
    }

    protected int y(int i) {
        return i / height;
    }

    public void computeNormals() {
        normals = Normals.getNormals(coefficients, false, scale, bias);
    }

    public VRGB normal(int i) {
        return (VRGB)normals.get(i);
    }

    public VRGB normal(int x, int y) {
        VRGB n = null;

        try {
            return(VRGB) normals.get(y * width + x);
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

   // public float getLuminance(int source) {
       // return lights[source].getLuminance();
 //   }

  //  public void setLuminance(float f, int source) {
       // lights[source].setLuminance(f);
   // }

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
     * @param w    Width after resizing
     * @param h    Height after resizing
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

  //  public LightSource[] getLights() {
  //      return lights;
  //  }

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

    public byte[][] getResizedCoeff(int w, int h) {
        return getResizedCoeff(w, h, 0, 0, 0, 0);
    }

    public byte[][] getResizedCoeff(int w, int h, int offx, int offy, int wid, int hei) {
        return _getRC(wid, w, hei, h, offx, offy);


    }

    public int[][] getCoefficients() {
        throw new UnsupportedOperationException("Not supported yet.");
    // return coefficients;
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
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBCoeffs(int top, int left, int tileh, int tilew, int band, byte[] bgrPixels) {
        _initBC(top, tileh, left, tilew, band, bgrPixels);
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors=colors;
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
    
    private int type,basis;
    
    private LightSource[] lights = new LightSource[MAX_LIGHTS];
    private boolean extrapolate = false;
    private float extrapolate_scale;
    private int colors;
    private boolean useEnv = false;
    private float DGAIN = 1.16f;
    private float KSPEC = 0.35f;
    private float KDIFF = 0.4f;
    private int EXP = 60;
    private float LUM = 1.0f;
    private int Z = 10000;
    private String version;
    private float[] scale;
    private float[] bias;
}
