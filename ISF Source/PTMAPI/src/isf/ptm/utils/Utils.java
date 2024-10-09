/*
 * Utils.java
 *
 * Created on July 24, 2004, 5:12 PM
 */
package isf.ptm.utils;

import isf.ptm.graphics.GaussianKernel;
import isf.ptm.graphics.VRGB;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import javax.vecmath.Point3f;


/**
 * @author Default
 */
public class Utils {

  /*  static void printFirst20(byte[] a) {
        System.out.print(a[0]);
        for (int i = 1; i < 20; i++) {
            System.out.print("," + a[i]);
        }
    // System.out.println();
    }

    static void printFirst20(int[] a) {
        System.out.print(a[0]);
        for (int i = 1; i < 20; i++) {
            System.out.print("," + a[i]);
        }
    // System.out.println();
    }

    static void printFirst20(short[] a) {
        System.out.print(a[0]);
        for (int i = 1; i < 20; i++) {
            System.out.print("," + a[i]);
        }
    // System.out.println();
    }*/

    public static int indexOfMax(double[] a) {
        double max = a[0];
        int idx = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                idx = i;
            }
        }
        return idx;
    }

    public static int indexOfMax(float[] a) {
        float max = a[0];
        int idx = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                idx = i;
            }
        }
        return idx;
    }

    /**
     * clamp to 8-bit int
     */
    public static int clamp(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 255) {
            return 255;
        }
        return i;
    }

    public static float[] normalize3(float[] a) {
        float len = (float) Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
        return new float[]{a[0] / len, a[1] / len, a[2] / len};
    }

    public static double[] normalize3(double[] a) {
        double len = Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
        return new double[]{a[0] / len, a[1] / len, a[2] / len};
    }

    public static float[] normalize(float x, float y, float z) {
        float len = (float) Math.sqrt(x * x + y * y + z * z);
        return new float[]{x / len, y / len, z / len};
    }

    public static String asString(double[] a) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a[i]);
        }
        return new String(sb);
    }

    public static String asString(float[] a) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a[i]);
        }
        return new String(sb);
    }

    public static String asString(int[] a) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a[i]);
        }
        return new String(sb);
    }

    public static float translate(int i, int maxVal) {
        if (i < 0) {
            i = 0;
        }
        if (i >= maxVal) {
            i = maxVal - 1;
        }
        int d = maxVal / 2;
        return (float) (i - d) / d;
    }

    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xff;
    }

    public static float[] reflect(float[] normal, float[] incedent) {
        // I - 2.0 * N * dot(N, I);
        // R = 2 * N.I * N - I
        float t1 = dot3(normal, incedent) * 2f;
        float[] n1 = scalarMult3(normal, t1);
        return normalize3(vecSum3(scalarMult3(incedent, -1), n1));
    }

    public static double[] reflect(double[] normal, double[] incedent) {
        // I - 2.0 * N * dot(N, I);
        // R = V - ( 2 * V [dot] N ) N
        double t1 = dot3(normal, incedent) * 2f;
        double[] n1 = scalarMult3(normal, t1);
        return normalize3(vecSum3(scalarMult3(incedent, -1), n1));
    }

    public static float[] vecSum3(float[] a, float[] b) {
        return new float[]{a[0] + b[0], a[1] + b[1], a[2] + b[2]};
    }

    public static double[] vecSum3(double[] a, double[] b) {
        return new double[]{a[0] + b[0], a[1] + b[1], a[2] + b[2]};
    }

    public static float[] scalarMult3(float[] a, float s) {
        float[] r = new float[3];
        r[0] = a[0] * s;
        r[1] = a[1] * s;
        r[2] = a[2] * s;
        return r;
    }

    public static double[] scalarMult3(double[] a, double s) {
        double[] r = new double[3];
        r[0] = a[0] * s;
        r[1] = a[1] * s;
        r[2] = a[2] * s;
        return r;
    }

    public static float dot3(float[] a, float[] b) {
        return (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
    }

    public static double dot3(double[] a, double[] b) {
        return (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
    }

    /*  public static BufferedImage readUnbuffered(InputStream is)
    throws java.io.IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int b;
    while ((b = is.read()) != -1)
    baos.write(b);
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    return ImageIO.read(bais);
    }*/
    public static int[] grabPixels(BufferedImage image) {
        return ((DataBufferInt) ((BufferedImage) image).getRaster().getDataBuffer()).getData();
    }

    public static BufferedImage gaussianBlur(BufferedImage image, int kSize,
            float sigma) {
        BufferedImage target = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Kernel kernel = new Kernel(kSize, kSize, new GaussianKernel(kSize,
                sigma).getKernel());
        ConvolveOp cop = new ConvolveOp(kernel);
        cop.filter(image, target);
        return target;
    }

    public static BufferedImage blurImageSimple(BufferedImage image, int kSize) {
        float patchval = ((float) 1) / (kSize * kSize);
        float[] k = new float[kSize * kSize];
        Arrays.fill(k, patchval);
        BufferedImage target = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Kernel kernel = new Kernel(kSize, kSize, k);
        ConvolveOp cop = new ConvolveOp(kernel);
        cop.filter(image, target);
        return target;
    }

    public static BufferedImage createBufferedImage(int[] pixels, int scanline) {
        BufferedImage bi = new BufferedImage(scanline,
                pixels.length / scanline, BufferedImage.TYPE_INT_RGB);
        int[] p = Utils.grabPixels(bi);
        for (int i = 0; i < p.length; i++) {
            p[i] = pixels[i];
        }
        return bi;
    }

    public static BufferedImage createBufferedImage(VRGB[] pixels, int scanline) {
        BufferedImage bi = new BufferedImage(scanline,
                pixels.length / scanline, BufferedImage.TYPE_INT_RGB);
        int[] p = Utils.grabPixels(bi);
        for (int i = 0; i < p.length; i++) {
            p[i] = pixels[i].toPixel();
        }
        return bi;
    }

    public static VRGB[] intToVec(int[] a) {
        VRGB[] v = new VRGB[a.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = VRGB.convertPixel(a[i]);
        }
        return v;
    }

   

    public static void checkAlgorithm(int width, int height, int offx, int offy) {
        int[][] array = new int[width * height][2];

        for (int x = 0; x < 43; x++) {
            for (int y = 0; y < 21; y++) {
                array[x + (y * width)][0] = x + (y * width);

            //array[x + (y * width)][1] = 0;
            }
        }


        for (int h = 21 - 1; h >= 0; h--) {
            for (int w = 0; w < 43; w++) {
                int offset = h * 43 + w;
                int offset2 = (h + offy) * width + (w + offx);
                array[offset2][1] = offset;

            }
        }

        for (int y = 0; y < 43; y++) {
            for (int x = 0; x < 21; x++) {

                int o = x + (y * width);
                System.out.print("[" + array[o][0] + "," + array[o][1] + "] ");
            }
        // System.out.println("\n");
        }


    }

    public static String[] split(String source, String pattern) {
        ArrayList list = new ArrayList();
        StringTokenizer st = new StringTokenizer(source, pattern);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        String[] vals = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            vals[i] = (String) list.get(i);
        }
        return vals;

    }

    

    public static void toPolar(float ro, float theta, float phi, Point3f light) {
        ro = norm(light);
        theta = (float) Math.atan2(light.z, light.x);
        phi = (float) Math.asin(light.y / ro);

    }
    
    public static float getFloatFromArray(byte[] arr, int start, int len) {
        int i = 0;
        int cnt = 0;
        byte[] tmp = new byte[len];
        for (i = start; i < (start + len); i++) {
            tmp[cnt] = arr[i];
            cnt++;
        }
        int accum = 0;
        i = 0;
        for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
            accum |= ((long) (tmp[i] & 0xff)) << shiftBy;
            i++;
        }
        return Float.intBitsToFloat(accum);
    }
    
    public static float getFloatFromArray(byte[] arr) {
        int i = 0;
        
        int accum = 0;
       
        for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
            accum |= ((long) (arr[i] & 0xff)) << shiftBy;
            i++;
        }
        return Float.intBitsToFloat(accum);
    }
      private static final int MASK = 0xff;

	/**
	 * convert byte array (of size 4) to float
	 * @param test
	 * @return
	 */
	public static float byteArrayToFloat(byte test[]) {
           
        
		int bits = 0;
		int i = 0;
		for (int shifter = 3; shifter >= 0; shifter--) {
			bits |= ((int) test[i] & MASK) << (shifter * 8);
			i++;
		}

		return Float.intBitsToFloat(bits);
	}

	/**
	 * convert float to byte array (of size 4)
	 * @param f
	 * @return
	 */
	public static byte[] floatToByteArray(float f) {
		int i = Float.floatToRawIntBits(f);
		return intToByteArray(i);
	}
        
        public static byte[] floatToBytes(float f)
        {
            int i = Float.floatToIntBits(f);
            return intToByteArray(i);
        }

	/**
	 * convert int to byte array (of size 4)
	 * @param param
	 * @return
	 */
	public static byte[] intToByteArray(int param) {
		byte[] result = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = i * 8;
			result[i] = (byte) ((param >>> offset) & MASK);
		}
		return result;
	}
    
    

    
    
    public static float cross(Point3f t, VRGB b)
    {
       
        return t.x*b.red+t.y*b.green+t.z*b.blue;
    }

    public static Point3f normalize(Point3f temp) {
        float n = (float) Math.sqrt(temp.x * temp.x + temp.y * temp.y + temp.z * temp.z);
        if (n > 0.0) {
            temp.x /= n;
            temp.y /= n;
            temp.z /= n;
        }
        return temp;
    }

    public static float norm(Point3f temp) {
        float n = (float) Math.sqrt(temp.x * temp.x + temp.y * temp.y + temp.z * temp.z);

        return n;
    }

    public static float[] convertToPolar(Point3f light) {
       float ro = norm(light);
       float theta = (float) Math.atan2(light.z, light.x);
       float phi = (float) Math.asin(light.y / ro);
       return new float[]{ro,theta,phi};
    }
    
    
    
    
   
}









