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
public class RGBWriter extends PTMWriter{

    public void writePTM(PTM ptm, OutputStream os) throws IOException {
        _initWriter(ptm, os);
    }

    private void _initWriter(PTM ptm, OutputStream os) throws IOException, UnsupportedOperationException {
        if (ptm == null || ptm.getFileType()!= PTM.TYPE_RAW_PTM || ptm.getBasisType() != PTM.BASIS_RGB) {
            throw new UnsupportedOperationException("PTM is null or not of type " + PTM.PTM_RGB);
        }
        int width = ptm.getWidth();
        int height = ptm.getHeight();
        String version = ptm.getVersion();
        os.write(getLine(version));
        os.write(getLine(PTM.PTM_RGB));
        os.write(getLine(width));
        os.write(getLine(height));
        os.write(getLine(ptm.getScaleArray()));
        os.write(getLine(ptm.getBias()));
        //start writing bytes 

        byte[][] coeffs = ptm.getBCoefficients();
         int offset;

        int factor = 6;
        int band = (20 * 1024 * 1024) / (width * factor);
        band = band > height ? height : band;
        int length = width * band;
        int RED = 0;
        int BLUE = 2;

       
        byte[] pixels = new byte[length*6];
        int from, to;

        for (int block = RED; block <= BLUE; block++) {
            from = height;
            to = height - band;
            if (to < 0) {
                to = 0;
            }
            pixels = new byte[length * 6];
            while (from > 0) {
               
                if ((from - to) < band) {
                    pixels = new byte[width * factor * (from - to)];
                }
               

                int x = 0;
                for (int h = from - 1; h >= to; h--) {
                    for (int w = 0; w < width; w++) {
                        offset = h * width + w;
                        for (int i = 0; i < 6; i++) {

                            pixels[x++] = coeffs[offset][block * 6 + i];
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
        }
        os.flush();
        os.close();
    }

}
