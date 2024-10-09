package isf.cache.utils;

import isf.cache.LRUCache;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.LogManager;
import isf.common.utils.PTMConfig;
import isf.common.utils.PTMConfig;
import isf.common.utils.ThreadExecutor;
import isf.common.utils.ThreadExecutor;
import isf.ptm.fileio.PTMWriter;
import isf.ptm.formats.PTM;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 24, 2009
 * Time: 12:51:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtils {

    static LRUCache cache;
    static String path = CacheManagerConstants.cacheFolderName;
    
     Logger log = LogManager.getLogger(getClass().getCanonicalName());
    static {

        try {
            File f = new File(path);
            if (!f.exists()) {
                cache = new LRUCache();
            }else{
                File serialized = new File(path + File.separator + "cache.ser");
                if(serialized.exists()){
            FileInputStream fis = new FileInputStream(serialized);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cache = (LRUCache) ois.readObject();
            ois.close();
            fis.close();
                }else{
                    cache = new LRUCache();
                    
                }
            }
        } catch (Exception exp) {
            cache = new LRUCache();
            LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).error(exp);
        }

    }

    public static BufferedImage readUnbuffered(InputStream is)            throws java.io.IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = is.read()) != -1) {
            baos.write(b);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return ImageIO.read(bais);
    }

    public static boolean isCached(String name) {

        return cache.isCached(path + File.separator + name);
    }

    public static String getCachedPath() {
        return path + File.separator;
    }

    public static void cleanCache() {
        try {

            File folder = new File(path);
            String[] names = folder.list();
            if (names != null) {
                int x = names.length;
                for (int i = 0; i < x; i++) {

                    File f = new File(path + File.separator + names[i]);
                    f.delete();
                }
            }
            folder.delete();

            ThreadExecutor.getInstance().shutdown();
        } catch (Exception e) {
             LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).error(e);
        }

    }

   

    public static void saveCache(String name, PTM ptm, PTMConfig config) {

        String file = path + File.separator + name;
        try {
            cache.updateEntry(file);
            FileOutputStream fos = new FileOutputStream(file + config.getExtension());
            PTMWriter writer = PTMWriter.getPTMWriter(ptm);
            if(writer !=null)
               writer.writePTM(ptm, fos);
            else
                LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).debug("from caching writer is null");

            File f = new File(file + config.getExtension());
            long size = f.length();
            cache.updateEntry(file, size);
        

        } catch (Exception exp) {
             LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).error(exp);
        }


    }

    public static void saveThumbnail(String name, BufferedImage img) {
        String file = path + File.separator + name ;
        try {
            OutputStream out = new FileOutputStream(file);
            ImageIO.write(img, "jpeg", out);
        } catch (Exception fnfe) {
             LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).error(fnfe);
        }
    }

    public static BufferedImage getThumbnail(String name) {
        String file = path + File.separator + name + "thumb.jpg";
        BufferedImage img = null;
        try {
            InputStream input = new FileInputStream(file);
            img = ImageIO.read(input);
        } catch (Exception exp) {
             LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).error(exp);
        } finally {
            return img;
        }
    }

/*
    public static PTM clonePTM(PTM src) {
        PTM dest = null;
        byte[][] coeffs = null;
        int width = src.getWidth();
        int height = src.getHeight();
        int size = width * height;
        byte[][] srcArray = src.getBCoefficients();
        switch (src.getFileType()) {
            case PTM.BASIS_LRGB:
                dest = new BLRGBPTM();
                coeffs = new byte[9][size];
                for (int x = 0; x < srcArray.length; x++) {
                    for (int y = 0; y < srcArray[x].length; y++) {
                        coeffs[x][y] = srcArray[x][y];
                    }
                }

                ((BLRGBPTM) dest).setCoefficients(coeffs);
                break;
            case PTM.BASIS_RGB:
                dest = new RGBPTM();
                coeffs = new byte[size][18];
                for (int x = 0; x < srcArray.length; x++) {
                    for (int y = 0; y < srcArray[x].length; y++) {
                        coeffs[x][y] = srcArray[x][y];
                    }
                }
                ((RGBPTM) dest).setBCoefficients(coeffs);
                break;
        }
        dest.setBias(src.getBias());
        dest.setScaleArray(src.getScaleArray());
        dest.setHeight(src.getHeight());
        dest.setWidth(src.getWidth());
        dest.setVersion(src.getVersion());
        dest.setName(src.getName());
        dest.computeNormals();

        return dest;
    }*/
    
  /*   public void updateLight()
    {
       if (theta - dxLight < 0.0)
		dxLight = theta;
	else if (theta - dxLight > M_PI)
		dxLight = theta - M_PI;
	if (phi - dyLight > M_PI_2)
		dyLight = phi - M_PI_2;
	else if (phi - dyLight < -M_PI_2)
		dyLight = M_PI_2 + phi;
	vcg::Matrix33f rotX, rotY;
	rotX.SetRotateRad(dyLight, vcg::Point3f(1,0,0));
	rotY.SetRotateRad(dxLight, vcg::Point3f(0,1,0));
	vcg::Point3f temp = rotX*rotY*lastLight;
	if (temp[2] < 0)
		temp[2] = 0;
	temp.Normalize();
	emit setLightDir(temp, true);
	lastLight = temp;
	dxLight = 0;
	dyLight = 0;
    }*/
}   
