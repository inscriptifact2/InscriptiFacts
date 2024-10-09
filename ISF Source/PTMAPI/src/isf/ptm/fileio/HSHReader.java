/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.ptm.fileio;

import isf.ptm.formats.HSH;
import isf.ptm.formats.PTM;
import isf.ptm.utils.Utils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author IN030223
 */
public class HSHReader implements PTMReader {

    private HSH ptm;
    private String version = null;
    private InputStream __in;
    private boolean DEBUG = true;
    private boolean reset = true; /* assume a complete input stream */

    private int type;
    int[] details;
    boolean fromRTI = true;

    public void reset(boolean b) {
        reset = b;
      //  System.out.println("Reset:" + reset);
    }

    /** Creates a new instance of LRGBReader */
    public HSHReader(InputStream in, boolean rti, int[] details, int type) {

        __in = in;
        fromRTI = rti;
        this.details = details;
      //  System.out.println("val0 =" + details[0]);
      //  System.out.println("val1=" + details[1]);
      //  System.out.println("val2 =" + details[2]);
     //   System.out.println("Details sent");
        this.type = type;
    }

    public HSHReader(InputStream in, boolean rti) {

        __in = in;
        fromRTI = rti;
       // System.out.println("No Details sent");
    // this.details = details;
    }

    public void setVersion(String s) {
        version = s;
    }

    public void setDebug(boolean b) {
        DEBUG = b;
    }

    private PTM _read() throws IOException, NumberFormatException {
        ptm = new HSH();
        int basisTerms = 0;
        int colors = 0;


        int[] det;

        if (reset) {

            PTMIO.getLine(__in);
            String str = PTMIO.getLine(__in);
            // System.out.println("str:"+str+"ex");
            this.type = Integer.parseInt(str.trim());
            ptm.setFileType(type);
            det = PTMIO.getLineAsInts(__in);
            ptm.setWidth(det[0]);
            ptm.setHeight(det[1]);
            colors = det[2];

        } else {
            ptm.setWidth(details[0]);
            ptm.setHeight(details[1]);
            colors = details[2];

            ptm.setFileType(type);
        }
       // System.out.println("reading next line");
        det = PTMIO.getLineAsInts(__in);
      //  System.out.println("finsihed reading firstline" + det[0] + "--" + det[1] + "--" + det[2]);
        basisTerms = det[0];
        ptm.setBasisTerms(basisTerms);

        int termType = det[1];
        ptm.setBasisType(termType);
        int size = det[2];
        ptm.setElementSize(size);
        version = new String("HSH_1.2");

        //start reading the scale and bias values as floats
        byte[] values = new byte[4 * basisTerms];
       
        
        __in.read(values);
       // System.out.println("0:"+values[0]);
       // System.out.println("1:"+values[1]);
      //  System.out.println("2:"+values[2]);
      //  System.out.println("3:"+values[3]);
        float[] scale = new float[basisTerms]; //gmin
        for (int i = 0, x = 0; i < values.length; i += 4, x++) {
            scale[x] = Utils.getFloatFromArray(values, i, 4);
           // scale[x] = Utils.byteArrayToFloat(values, i, 4);

        }
       // System.out.println("c:"+scale[0]);
        float[] bias = new float[basisTerms]; //gmax
        __in.read(values);
        for (int i = 0, x = 0; i < values.length; i += 4, x++) {
            bias[x] = Utils.getFloatFromArray(values, i, 4);
            //bias[x] = Utils.byteArrayToFloat(values, i, 4);

        }
        ptm.setBias(bias);
        ptm.setScaleArray(scale);
        ptm.setVersion(version);

        ptm.setColors(colors);
        int width = ptm.getWidth();
        int height = ptm.getHeight();

        int offset;
        int segment = basisTerms * colors;
        if(termType == PTM.BASIS_RGB)
            segment = basisTerms * colors;
        else
            segment =  basisTerms + colors;


        int band = (20 * 1024 * 1024) / (width * segment);
        band = band > height ? height : band;

        int length = width * band;




        byte[][] coeff = new byte[width * height][segment];
        // int[][] coeff= new int[6][length ];
          ptm.setBCoefficients(coeff);

       // long start = System.currentTimeMillis();
        BufferedInputStream buff = new BufferedInputStream(__in);
        byte[] pixels = new byte[length * segment];
        int from = 0, to = from + band;



        while (from < height) {


            pixels = new byte[length * segment];
            // while (from  < height) {
           // System.out.println("reading from:" + from + "::to:" + to + ":diff:" + (to - from));

            // System.out.println("Doing Color:"+block);
            if ((to - from) < band) {
                pixels = new byte[width *  (to - from) * segment];
            }
            buff.read(pixels);
            int x = 0;
            for (int h = from; h < to; h++) {
                for (int w = 0; w < width; w++) {
                    offset = h * width + w;

                     for (int i = 0; i < segment; i++) {

                            coeff[offset][ i] = pixels[x++];
                     }

                }
            }
            from = to;
            to = from + band;

            if (to > height) {
                to = height;
            }
        }
        //  System.out.println("Memory USed:"+Runtime.getRuntime().totalMemory());
        buff.close();
        __in.close();

        pixels = null;
      //  ptm.setBCoefficients(coeff);
       ///// ptm.computeNormals();
        System.gc();

        return ptm;
    }

    

    public PTM readPTM() throws java.io.IOException {
        return _read();

    }


}
