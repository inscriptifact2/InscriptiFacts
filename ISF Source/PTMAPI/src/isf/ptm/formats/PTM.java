/*
 * PTM.java
 *
 * Created on September 5, 2004, 5:07 PM
 */
package isf.ptm.formats;

import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.graphics.VRGB;
import java.util.ArrayList;


/**
 * 
 * @author Default
 */
public interface PTM  {
    //file formats
    public static final int TYPE_RTI_PTM = 1;
    public static final int TYPE_RTI_SH = 2;
    public static final int TYPE_RTI_HSH = 3;
    public static final int TYPE_RTI_APTM = 4;
    public static final int TYPE_RAW_PTM = 0;
    //format types
    public static final int BASIS_LRGB = 1;
    public static final int BASIS_RGB = 2;
    public static final int BASIS_PRIMITIVE = 3;
    //string types
    public static final String PTM_RGB = "PTM_FORMAT_RGB";
    public static final String PTM_LRGB = "PTM_FORMAT_LRGB";
    public static final String PTM_JPEG_LRGB = "PTM_FORMAT_JPEG_LRGB";
    
    public static final int MAX_LIGHTS = 2;

    public void setName(String name);

    public String getName();

    public void setKSpec(float f);

    public void setKDiff(float f);

    public void setExp(int i);

    public void setVersion(String v);

    public void setScaleArray(float[] v);

    public void setScale(float v);

    public void setBias(float[] v);

    public String getVersion();

    public float getScale();

    public float[] getBias();

    public float getKSpec();

    public float getKDiff();

    public float getDGain();

    public void setDGain(float f);

    public int getExp();

    public int getZ();

    public void setZ(int z);

    public void recache();

    public void release();

    public int getFileType();
    
    public int getBasisType();

    public int getWidth();

    public int getHeight();

    public void setWidth(int w);

    public void setHeight(int h);
    
    public int[][] getCoefficients();

    public byte[][] getBCoefficients();

    public void setBCoeffs(int top, int left, int tileh, int tilew, int band, byte[] bgrPixels);

    public int getColors();

    public void setColors(int colors);

    public void computeNormals();

    public VRGB normal(int i);

    public ArrayList getNormals();

    public VRGB normal(int x, int y);

    public void resize(int w, int h);

    public void setEnvironmentMap(EnvironmentMap em);

    public EnvironmentMap getEnvironmentMap();

    public int[] getEnvironmentMapCache();

    public int[] getEnvironmentMapMap();

    public boolean useEnv();

    public void useEnv(boolean b);
    
    public float[] getScaleArray();

    public boolean isExtrapolation();

    public void setExtrapolation(boolean flag);
    
    public void setBasisType(int basis);
    
    public void setFileType(int type);

   
}
