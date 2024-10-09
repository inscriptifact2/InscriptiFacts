/*
 * RGBReader.java
 *
 * Created on July 3, 2004, 11:41 PM
 */
package isf.ptm.fileio;

//import com.isf.common.ISFBase64;
import isf.ptm.formats.PTM;
import isf.ptm.formats.RGBPTM;
import isf.ptm.utils.Utils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 
 * @author Default
 */
public class RGBReader implements PTMReader {

    private RGBPTM ptm;
    private String version = null;
    private InputStream __in;
    private boolean DEBUG = true;
    private boolean reset = true; /* assume a complete input stream */
    int type,basis;
    boolean fromRTI = false;

    public void reset(boolean b) {
        reset = b;
    }

    /** Creates a new instance of LRGBReader */
    public RGBReader(InputStream in) {
      
        __in = in;
        type = PTM.TYPE_RAW_PTM;
        basis = PTM.BASIS_RGB;
    }

    /** Creates a new instance of LRGBReader */
    public RGBReader(InputStream in, boolean rti) {

        __in = in;
        fromRTI = rti;
        if(fromRTI)
         type = PTM.TYPE_RTI_PTM;
        else
            type = PTM.TYPE_RAW_PTM;
        basis = PTM.BASIS_RGB;
    }
    public void setVersion(String s) {
        version = s;
    }

    public void setDebug(boolean b) {
        DEBUG = b;
    }

    private PTM _initReader() throws IOException, NumberFormatException {
        ptm = new RGBPTM();
        ptm.setFileType(type);
        ptm.setBasisType(basis);
        int bands = 0;
        int colors = 0;

        if (!fromRTI) {
            if (reset) {
                version = PTMIO.getLine(__in);
                String type = PTMIO.getLine(__in);
            }
            bands = 6;
            colors = 3;
            ptm.setWidth(Integer.parseInt(PTMIO.getLine(__in)));
            ptm.setHeight(Integer.parseInt(PTMIO.getLine(__in)));
            String[] sa;

            /* scale */
            sa = Utils.split(PTMIO.getLine(__in), " ");
            float[] scale = new float[sa.length];
            for (int i = 0; i < sa.length; i++) {
                scale[i] = Float.parseFloat(sa[i]);

            // debug("Scale: " + Utils.asString(scale));

            /* bias */
            }
            sa = Utils.split(PTMIO.getLine(__in), " ");
            float[] bias = new float[sa.length];
            for (int i = 0; i < sa.length; i++) {
                bias[i] = Float.parseFloat(sa[i]);
            }
            ptm.setBias(bias);
            ptm.setScaleArray(scale);
            ptm.setVersion(version);
        } else {



            while (PTMIO.getLine(__in).startsWith("#"));
            int type = Integer.parseInt(PTMIO.getLine(__in));
            int[] details = PTMIO.getLineAsInts(__in);
            ptm.setWidth(details[0]);
            ptm.setHeight(details[1]);
            colors = details[2];
            details = PTMIO.getLineAsInts(__in);
            bands = details[0];
            int termType = details[1];
            int size = details[2];
            version = new String("PTM_1.2");

            //start reading the scale and bias values as floats
            byte[] values = new byte[4 * bands];
            __in.read(values);
            float[] scale = new float[bands];
            for (int i = 0, x = 0; i < values.length; i += 4, x++) {
                scale[x] = Utils.getFloatFromArray(values, i, 4);
            }
            float[] bias = new float[bands];
            __in.read(values);
            for (int i = 0, x = 0; i < values.length; i += 4, x++) {
                bias[x] =  Utils.getFloatFromArray(values, i, 4);;
            }
            ptm.setBias(bias);
            ptm.setScaleArray(scale);
            ptm.setVersion(version);         //read based on rti format   

            }

            // debug("Bias: " + Utils.asString(bias));
            int width = ptm.getWidth();
            int height = ptm.getHeight();

            int offset;


            int band = (20 * 1024 * 1024) / (width * bands);
            band = band > height ? height : band;

            int length = width * band;

            /* coefficients */
            int RED = 0;
            int BLUE = 2;


            byte[][] coeff = new byte[width * height][bands * colors];
            ptm.setBCoefficients(coeff);
            // int[][] coeff= new int[6][length ];


          //  long start = System.currentTimeMillis();
            BufferedInputStream buff = new BufferedInputStream(__in);
            byte[] pixels = new byte[length * bands];
            int from, to;

            for (int block = RED; block <= BLUE; block++) {
                from = height;
                to = height - band;
                if (to < 0) {
                    to = 0;
                }
                pixels = new byte[length * bands];
                while (from > 0) {
                  //  System.out.println("reading from:" + from + "::to:" + to + ":diff:" + (from - to));

                    // System.out.println("Doing Color:"+block);
                    if ((from - to) < band) {
                        pixels = new byte[width * bands * (from - to)];
                    }
                    buff.read(pixels);
                    // System.out.println("Reading complete:"+block);

                    int x = 0;
                    for (int h = from - 1; h >= to; h--) {
                        for (int w = 0; w < width; w++) {
                            offset = h * width + w;
                            for (int i = 0; i < 6; i++) {

                                coeff[offset][block * 6 + i] = pixels[x++];
                            }
                        }
                    }
                    from = to;
                    to = from - band;
                    if (to < 0) {
                        to = 0;
                    }

                }
            }
            //  System.out.println("Memory USed:"+Runtime.getRuntime().totalMemory());
            buff.close();
            __in.close();

            pixels = null;





            

            long end = System.currentTimeMillis();
          //  System.out.println("TimeTaken1:" + (end - start));
           // System.out.println("Memory Used:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            // System.out.println("Memory USed:"+Runtime.getRuntime().totalMemory());

            // ObjectMatrix2D model = ObjectFactory2D.dense.make(length,18);
            // model.assign(coeff);

           // System.out.println("TimeTakenonjectassign:" + (System.currentTimeMillis() - end));


            ptm.computeNormals();
            System.gc();
          //  System.out.println("Memory Used:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
           // long end1 = System.currentTimeMillis();
          //  System.out.println("TimeTaken2:" + (end1 - end));
            return ptm;
    }

    
    public PTM readPTM() throws java.io.IOException {
            return _initReader();

        }
    


}
