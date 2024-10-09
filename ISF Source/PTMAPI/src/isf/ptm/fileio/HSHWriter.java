/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.ptm.fileio;

import isf.ptm.formats.HSH;
import isf.ptm.formats.PTM;
import isf.ptm.utils.Utils;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author IN030223
 */
public class HSHWriter extends PTMWriter{

    public void writePTM(PTM ptm, OutputStream os) throws IOException {
        if (ptm == null || ptm.getFileType()!=PTM.TYPE_RTI_HSH ) {
            throw new UnsupportedOperationException("RTI is null or not of type HSH" );
        }
         int width = ptm.getWidth();
        int height = ptm.getHeight();
        int colors = ptm.getColors();
        int basisTerms = ((HSH)ptm).getBasisTerms();
        int basisType = ((HSH)ptm).getBasisType();
            os.write(getLine("#HSH_1.2"));
           os.write(getLine(ptm.getFileType()+""));
          os.write(getLine(width+" "+height+" "+colors));
          os.write(getLine(basisTerms+" "+basisType+" "+((HSH)ptm).getElementSize()));
            
                  
        float[] scale = ptm.getScaleArray();
        for(int i = 0 ; i < scale.length; i++)
        {
            os.write(Utils.floatToByteArray(scale[i]));
        }
        float[] bias = ptm.getBias();
        for(int i = 0 ; i < bias.length; i++)
        {
            os.write(Utils.floatToByteArray(bias[i]));
        }
        
       int segment = basisTerms * colors;
            if (basisType == PTM.BASIS_RGB) {
                segment = basisTerms * colors;
            } else {
                segment = basisTerms + colors;
            }
            int band = (20 * 1024 * 1024) / (width * segment);
            band = band > height ? height : band;

            int length = width * band;

        
        byte[][] coeffs = ptm.getBCoefficients();
         int offset;
         byte[] pixels = new byte[length * segment];
        int from = 0, to = from + band;
            while (from < height) {
                pixels = new byte[length * segment];
                if ((to - from) < band) {
                    pixels = new byte[width * (to - from) * segment];
                }
               
                int x = 0;
                for (int h = from; h < to; h++) {
                    for (int w = 0; w < width; w++) {
                        offset = h * width + w;
                       // for (int block = 0; block < colors; block++) {
                         //   for (int i = 0; i < basisTerms; i++) {
                        for(int block = 0 ; block<segment;block++)
                                 pixels[x++] = coeffs[offset][block ];
                         //   }
                       // }
                    }
                }
                os.write(pixels);
                from = to;
                to = from + band;

                if (to > height) {
                    to = height;
                }
            }
        os.flush();
        os.close();
    }

}
