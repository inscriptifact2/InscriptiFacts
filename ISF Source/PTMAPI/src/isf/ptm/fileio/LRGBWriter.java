/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.ptm.fileio;

import isf.ptm.formats.PTM;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author IN030223
 */
public class LRGBWriter extends PTMWriter {

    public void writePTM(PTM ptm, OutputStream os) throws IOException {
        _initWrite(ptm, os);
    }

    private void _initWrite(PTM ptm, OutputStream os) throws UnsupportedOperationException, IOException {
        if (ptm == null || ptm.getBasisType() != PTM.BASIS_LRGB) {
            throw new UnsupportedOperationException("PTM is null or not of type " + PTM.PTM_LRGB);
        }
        int width = ptm.getWidth();
        int height = ptm.getHeight();
        String version = ptm.getVersion();
        os.write(getLine(version));
        os.write(getLine(PTM.PTM_LRGB));
        os.write(getLine(width));
        os.write(getLine(height));
        os.write(getLine(ptm.getScaleArray()));
        os.write(getLine(ptm.getBias()));
        //start writing bytes 

        byte[][] coeffs = ptm.getBCoefficients();
        int offset = 0, x = 0;
        int factor = 6;
        if (version.equals("PTM_1.1")) {
            factor = 9;
        }
        int band = (20 * 1024 * 1024) / (width * factor);
        band = band > height ? height : band;

        int BUFSIZ = width * band * factor;

        int from = height;
        int to = height - band;
        if (to < 0) {
            to = 0;

        }
        byte[] pixels = new byte[BUFSIZ];
        while (from > 0) {
           // System.out.println("reading from:" + from + "::to:" + to + ":diff:" + (from - to));

            if ((from - to) < band) 
                pixels = new byte[width * factor * (from - to)];
          //  else
             //  pixels = new byte[BUFSIZ]; 
             x = 0;
            for (int h = from - 1; h >= to; h--) {
                for (int w = 0; w < width; w++) {
                    offset = h * width + w;

                    for (int i = 0; i < 6; i++) {

                        pixels[x++] = coeffs[i][offset];
                    }
                    /* do it now */
                    if (version.equals("PTM_1.1")) {
                        pixels[x++] = coeffs[6][offset];
                         pixels[x++] = coeffs[7][offset];
                          pixels[x++] = coeffs[8][offset];

                    }
                }
            }
            from = to;
            to = from - band;
            if (to < 0) {
                to = 0;
            }
            
            os.write(pixels);
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
              
                x = 0;
                // bis = new ByteArrayInputStream(pixels);
                for (int h = from - 1; h >= to; h--) {
                    for (int w = 0; w < width; w++) {
                        offset = h * width + w;
                        pixels[x++] = coeffs[6][offset];
                         pixels[x++] = coeffs[7][offset];
                          pixels[x++] = coeffs[8][offset];
                    }
                }
                from = to;
                to = from - band;
                if (to < 0) {
                    to = 0;
                }
                os.write(pixels);
            }
        }
        os.flush();
        os.close();
    }


}
