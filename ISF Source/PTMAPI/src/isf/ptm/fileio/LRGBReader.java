/*
 * LRGBReader.java
 *
 * Created on July 3, 2004, 11:41 PM
 */
package isf.ptm.fileio;

import isf.ptm.formats.BLRGBPTM;
import isf.ptm.formats.PTM;
import isf.ptm.utils.Utils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.nio.ByteBuffer;

/**
 *
 * @author Default
 */
public class LRGBReader implements PTMReader {

    private BLRGBPTM ptm;
    private String version = null;
    private InputStream __in;
    private boolean DEBUG = false;
    private boolean reset = true; /*
     * assume a complete input stream
     */

    boolean fromRTI = false;
    private int type, basis;

    public void reset(boolean b) {
        reset = b;
    }

    /**
     * Creates a new instance of LRGBReader
     */
    public LRGBReader(InputStream in) {
        __in = in;
        type = PTM.TYPE_RAW_PTM;
        basis = PTM.BASIS_LRGB;
    }

    /**
     * Creates a new instance of LRGBReader
     */
    public LRGBReader(InputStream in, boolean isrti) {
        __in = in;
        fromRTI = isrti;
        if (fromRTI) {
            type = PTM.TYPE_RTI_PTM;
        } else {
            type = PTM.TYPE_RAW_PTM;
        }
        basis = PTM.BASIS_LRGB;
    }

    public void setVersion(String s) {
        version = s;
    }

    public void setDebug(boolean b) {
        DEBUG = b;
    }

    private PTM _initRead() throws IOException, NumberFormatException {
        ptm = new BLRGBPTM();
        ptm.setFileType(type);
        ptm.setBasisType(basis);
        int bands = 0;
        int colors = 0;
        float[] bias = null;
        float[] scale = null;

        // try {
        if (!fromRTI) {

            if (reset) {
                version = PTMIO.getLine(__in);

               // debug("Version: " + version);
                String type = PTMIO.getLine(__in);
               // debug("Type: " + type);
            }
            bands = 6;
            colors = 3;

            /*
             * read headers from this stream
             */
            //BufferedReader reader = new BufferedReader(new InputStreamReader(__in));
            int width = Integer.parseInt(PTMIO.getLine(__in));
            int height = Integer.parseInt(PTMIO.getLine(__in));


            /*
             * dimensions
             */
            ptm.setWidth(width);
            ptm.setHeight(height);

            //debug("Width: " + ptm.getWidth());
            //debug("Height: " + ptm.getHeight());

            String[] sa;

            /*
             * scale
             */
            sa = Utils.split(PTMIO.getLine(__in), " ");
            scale = new float[sa.length];
            for (int i = 0; i < sa.length; i++) {
                scale[i] = Float.parseFloat(sa[i]);

                //debug("Scale: " + Utils.asString(scale));

                /*
                 * bias
                 */
            }
            sa = Utils.split(PTMIO.getLine(__in), " ");
            bias = new float[sa.length];
            for (int i = 0; i < sa.length; i++) {
                bias[i] = Float.parseFloat(sa[i]);

                //("Bias: " + Utils.asString(bias));
            }

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
            scale = new float[bands];
            for (int i = 0, x = 0; i < values.length; i += 4, x++) {
                scale[x] = Utils.getFloatFromArray(values, i, 4);
            }
            bias = new float[bands];
            __in.read(values);
            for (int i = 0, x = 0; i < values.length; i += 4, x++) {
                bias[x] = (int) Utils.getFloatFromArray(values, i, 4);
            }

        }

        ptm.setScaleArray(scale);
        ptm.setBias(bias);
        ptm.setVersion(version);
        ptm.setColors(colors);

        // long start = System.currentTimeMillis();
        int width = ptm.getWidth();
        int height = ptm.getHeight();
        byte[][] tmp = new byte[bands + colors][width * height];
        ptm.setCoefficients(tmp);
        int factor = bands;
        if (version.equals("PTM_1.1")) {
            factor = bands + colors;
        }
        int band = (20 * 1024 * 1024) / (width * factor);
        band = band > height ? height : band;

        /*
         * pixels
         */
        int BUFSIZ = width * band * factor;
        //int[] rgb = new int[BUFSIZ];
        int offset;
        int from = height;
        int to = height - band;
        if (to < 0) {
            to = 0;
            /*
             * int length= BUFSIZ * 6; if(version.equals("PTM_1.1")) length =
             * BUFSIZ * 9;
             */
        }
        BufferedInputStream buff = new BufferedInputStream(__in);
        byte[] pixels = new byte[BUFSIZ];
        while (from > 0) {
            //System.out.println("reading from:" + from + "::to:" + to + ":diff:" + (from - to));

            if ((from - to) < band) {
                pixels = new byte[width * factor * (from - to)];
            }
            buff.read(pixels);
            int x = 0;
            //ByteArrayInputStream bis = new ByteArrayInputStream(pixels);

            /*
             * coefficients
             */
            for (int h = from - 1; h >= to; h--) {
                for (int w = 0; w < width; w++) {
                    offset = h * width + w;
                    //int[] raw = new int[6];
                    for (int i = 0; i < 6; i++) {
                        //int c = __in.read();
                        //raw[i] = c;
                        //tmp[i][offset] = (int) PTMIO.cFinal(bis.read(), bias[i], scale[i]);
                        tmp[i][offset] = pixels[x++];
                    }
                    /*
                     * do it now
                     */
                    if (version.equals("PTM_1.1")) {
                        for (int z = 0; z < colors; z++) {
                            tmp[6 + z][offset] = pixels[x++];
                        }
                        //tmp[7][offset] = pixels[x++];
                        //tmp[8][offset] = pixels[x++];

                    }
                }
            }
            from = to;
            to = from - band;
            if (to < 0) {
                to = 0;
            }
        }


        pixels = null;
        if (version.equals("PTM_1.2")) {
            from = height;
            to = height - band;
            if (to < 0) {
                to = 0;
            }
            pixels = new byte[width * band * 3];
            while (from > 0) {
                if ((from - to) < band) {
                    pixels = new byte[width * factor * (from - to)];
                }
                buff.read(pixels);
                int x = 0;
                // bis = new ByteArrayInputStream(pixels);
                for (int h = from - 1; h >= to; h--) {
                    for (int w = 0; w < width; w++) {
                        offset = h * width + w;
                        for (int z = 0; z < colors; z++) {
                            tmp[6 + z][offset] = pixels[x++];
                        }
                        //tmp[6][offset] = pixels[x++];
                        // tmp[7][offset] = pixels[x++];
                        // tmp[8][offset] = pixels[x++];
                    }
                }
                from = to;
                to = from - band;
                if (to < 0) {
                    to = 0;
                }
            }
        }

        
        buff.close();
        //  bis.close();
        pixels = null;
        //long end = System.currentTimeMillis();
        // System.out.println("Time to load:" + (end - start));

        //  ptm.computeNormals();
        // System.out.println("Time to normalize:" + (System.currentTimeMillis()- end));
        return ptm;
    }

    
    public PTM readPTM() throws java.io.IOException {
        return _initRead();

    }


}
