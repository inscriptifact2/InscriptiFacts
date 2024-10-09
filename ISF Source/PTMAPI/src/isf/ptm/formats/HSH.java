/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.ptm.formats;

import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.graphics.VRGB;
import isf.ptm.operations.Operation;
import isf.ptm.utils.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

/**
 *
 * @author IN030223
 */
public class HSH implements PTM, Serializable {

    int colors;

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        width = w;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
        height = h;
    }

    public int getBasisTerms() {
        return basisTerms;
    }

    public void setBasisTerms(int bt) {
        basisTerms = bt;
    }

    public int getBasisType() {
        return basisType;
    }

    public void setBasisType(int bt) {
        basisType = bt;
    }

    public int getElementSize() {
        return elementSize;
    }

    public void setElementSize(int es) {
        elementSize = es;
    }

    public void setFileType(int type) {
        this.type = type;
    }

    public int getFileType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public byte[][] getBCoefficients() {
        return coefficients;
    }

    public void setBCoefficients(byte[][] coeffs) {
        _initBC(coeffs);
    }

    private void _initBC(byte[][] coeffs) {
        int totalTerms = 0;
        switch (basisType) {
            case BASIS_LRGB:
                totalTerms = basisTerms + colors;
                break;
            case BASIS_RGB:
                totalTerms = basisTerms * colors;
                break;
        }
        if (coeffs == null) {
            coefficients = new byte[width * height][totalTerms];
        } else {
            coefficients = coeffs;
        }
    }

    public String[][] getFunctions() {
        return functions;
    }

    public void setFunctions(String[][] functions) {
        this.functions = functions;
    }

    public String[] getLineFunctions(int band) {
        return functions[band];
    }

    public float[] getScaleArray() {
        return scale;
    }

    public void setScaleArray(float[] scale) {
        this.scale = scale;
    }

    public void computeNormals() {
        _initNormals();
    }

    private void _initNormals() {
        if (null == normals) {
            normals = new ArrayList();
            Point3d l0 = new Point3d(Math.sin(Math.PI / 4) * Math.cos(Math.PI / 6), Math.sin(Math.PI / 4) * Math.sin(Math.PI / 6), Math.cos(Math.PI / 4));
            Point3d l1 = new Point3d(Math.sin(Math.PI / 4) * Math.cos(5 * Math.PI / 6), Math.sin(Math.PI / 4) * Math.sin(5 * Math.PI / 6), Math.cos(Math.PI / 4));
            Point3d l2 = new Point3d(Math.sin(Math.PI / 4) * Math.cos(3 * Math.PI / 2), Math.sin(Math.PI / 4) * Math.sin(3 * Math.PI / 2), Math.cos(Math.PI / 4));
            double[] hweights0, hweights1, hweights2;
            hweights0 = Operation.getHSH(Math.PI / 4, Math.PI / 6);
            hweights1 = Operation.getHSH(Math.PI / 4, 5 * Math.PI / 6);
            hweights2 = Operation.getHSH(Math.PI / 4, 3 * Math.PI / 2);
            Matrix3d L = new Matrix3d();
            Matrix3d LInverse = new Matrix3d();
            L.setRow(0, l0.x, l0.y, l0.z);
            L.setRow(1, l1.x, l1.y, l1.z);
            L.setRow(2, l2.x, l2.y, l2.z);
            LInverse.invert(L);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int offset = y * width + x;
                    Point3d f = new Point3d(0, 0, 0);
                    byte[] localCache = coefficients[offset];
                    double first = 0.0;
                    double second = 0.0;
                    double third = 0.0;
                    for (int c = 0; c < 3; c++) {
                        for (int i = 0; i < basisTerms; i++) {
                            first += (((double) (localCache[c * basisTerms + i] & 0x000000FF)) / 255 * scale[i] + bias[i]) * hweights0[i];
                            second += (((double) (localCache[c * basisTerms + i] & 0x000000FF)) / 255 * scale[i] + bias[i]) * hweights1[i];
                            third += (((double) (localCache[c * basisTerms + i] & 0x000000FF)) / 255 * scale[i] + bias[i]) * hweights2[i];
                        }
                    }
                    f = new Point3d(first, second, third);
                    f.scale((double) 1 / (double) 3);
                    LInverse.transform(f);
                    Point3f normal = new Point3f((float) f.x, (float) f.y, (float) f.z);
                    Utils.normalize(normal);
                    normals.add(new VRGB(normal.x, normal.y, normal.z));
                }
            }

        }
    }

    public VRGB normal(int i) {
        return (VRGB) normals.get(i);
    }

    public ArrayList getNormals() {
        if (normals == null) {
            computeNormals();
        }
        return normals;
    }

    public VRGB normal(int x, int y) {
        VRGB n = null;

        try {
            return (VRGB) normals.get(y * width + x);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }

        return n;
    }

    public void resize(int w, int h) {
    }

    public void setKDiff(float f) {
        KDIFF = f;
    }

    public void setKSpec(float f) {
        KSPEC = f;
    }

    public void setExp(int i) {
        EXP = i;
    }

    public void setScale(float v) {
        extrapolate_scale = v;
    }

    public void setBias(float[] v) {
        bias = v;
    }

    public float getScale() {
        return extrapolate_scale;
    }

    public float[] getBias() {
        return bias;
    }

    public float getKSpec() {
        return KSPEC;
    }

    public float getKDiff() {
        return KDIFF;
    }

    public float getDGain() {
        return m_dGain;
    }

    public void setDGain(float f) {
        m_dGain = Math.max(f, 1.0f);
    }

    public int getExp() {
        return EXP;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }

    public void recache() {
    }

    public void release() {
        coefficients = null;
        if (normals != null) {

            normals = null;
        }
        bias = null;
        scale = null;
        bias = null;
    }

    public int[][] getCoefficients() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setBCoeffs(int top, int left, int tileh, int tilew, int band, byte[] bgrPixels) {
        _setBC(top, tileh, left, tilew, band, bgrPixels);
    }

    private void _setBC(int top, int tileh, int left, int tilew, int band, byte[] bgrPixels) {
        int offset, pos, totalTerms;
        switch (basisType) {
            case PTM.BASIS_LRGB:
                totalTerms = basisTerms + colors;
                break;
            case PTM.BASIS_RGB:
                totalTerms = basisTerms * colors;
                break;
        }
        //System.out.println("Band:" + band);
        for (int y = top, b = 0; y < tileh + top; y++, b++) {
            for (int x = left, a = 0; x < tilew + left; x++, a++) {
                offset = y * width + x;
                pos = b * tilew + a;


                coefficients[offset][band] = bgrPixels[pos * 3 + 0];
                coefficients[offset][band + 1 * basisTerms] = bgrPixels[pos * 3 + 1];
                coefficients[offset][band + 2 * basisTerms] = bgrPixels[pos * 3 + 2];

            }
        }
    }

    public void setEnvironmentMap(EnvironmentMap em) {
    }

    public EnvironmentMap getEnvironmentMap() {
        return null;
    }

    public int[] getEnvironmentMapCache() {
        return null;
    }

    public int[] getEnvironmentMapMap() {
        return null;
    }

    public boolean useEnv() {
        return false;
    }

    public void useEnv(boolean b) {
    }

    public boolean isExtrapolation() {
        return extrapolate;
    }

    public void setExtrapolation(boolean flag) {
        extrapolate = flag;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }
    private boolean extrapolate = false;
    private int EXP = 75;
    private float KDIFF = 0.40f;
    private float KSPEC = 0.35f;
    private float LUM = 1.0f;
    private float m_dGain = 1.16f;
    private int Z = 10000;
    private float extrapolate_scale;
     int type, width, height, basisType, basisTerms, elementSize;
     String name, version;
     float[] bias, scale;
     ArrayList normals;
     byte[][] coefficients;
    private String[][] functions;  // used only for APTM;
}
