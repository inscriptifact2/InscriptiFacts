package isf.comp.parsers;


import com.sun.media.imageio.plugins.jpeg2000.J2KImageWriteParam;
//import isf.cache.utils.CacheHandler;
import isf.ptm.fileio.PTMIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 8, 2007
 * Time: 12:40:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RGBParser extends LazyParser {

    InputStream __in;
    String fileName;
    int width, height;
    // float[] scale;
    //  int[] bias;

    public RGBParser(InputStream in, String name, int w, int h) {
        __in = in;
        fileName = name;
        width = w;
        height = h;
    }

    public void cachePTM() {

        saveFiles();
        double dirSize = getLength(new File(cache_path + File.separator + fileName)) / (1024 * 1024);
        //CacheHandler.getInstance().getCache().updateEntry(cache_path + File.separator + fileName, dirSize);
        try {
            FileInputStream fis = new FileInputStream(cache_path + File.separator + fileName + File.separator + fileName + ".cfg");
            String str = PTMIO.getLine(fis);
            fis.close();
            str = str.replaceAll(" cached=\"no\"", " cached=\"yes\"");
            FileOutputStream fs = new FileOutputStream(cache_path + File.separator + fileName + File.separator + fileName + ".cfg");
            fs.write(str.getBytes());
            fs.flush();
            fs.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }


    }

    private void saveFiles() {
        try {


            int offset;

            int factor = 6;
            int band = (20 * 1024 * 1024) / (width * factor);
            band = band > height ? height : band;

            int length = width * band;

            /* coefficients */
            int RED = 0;
            int BLUE = 2;


            // int[][] coeff = new int[6][width * height];
            byte[][] bcoeffs = new byte[6][width * height * 3];

            long start = System.currentTimeMillis();
            BufferedInputStream buff = new BufferedInputStream(__in);
            byte[] pixels = new byte[length * 6];
            int from, to;

            for (int block = RED; block <= BLUE; block++) {
                from = height;
                to = height - band;
                if (to < 0) {
                    to = 0;
                }
                pixels = new byte[length * 6];
                while (from > 0) {
                    

                    pixels = new byte[width * factor * (from - to)];

                    buff.read(pixels);


                    int x = 0;
                    for (int h = from - 1; h >= to; h--) {
                        for (int w = 0; w < width; w++) {
                            for (int i = 0; i < 6; i++) {
                                offset = h * width + w;
                                bcoeffs[i][offset * 3 + (2 - block)] = pixels[x++];
                            }
                        }
                    }
                    from = to;
                    to = from - band;
                    if (to < band / 3) {
                        to = 0;
                    }

                }
            }

            buff.close();
            __in.close();

            pixels = null;
            ImageWriter writer = null;
            Iterator writers = ImageIO.getImageWritersByFormatName("jpeg2000");//jpeg2000

            for (; writers.hasNext();) {
                writer = (ImageWriter) writers.next();
                if (!writer.getClass().getName().endsWith("Lib")) {
                    break;
                    //logger.debug(writer.getClass().getName());
                }
            }
            pixels = null;

            start = System.currentTimeMillis();

            int index = 0;
            int rows = height / seg;
            int cols = width / seg;
            int lastW, lastH;
            int hbal = height % seg;
            if (((float) hbal) / seg > .3) {
                rows += 1;
                lastH = hbal;
            } else {
                lastH = seg + hbal;
            }
            int wbal = width % seg;
            if (((float) wbal) / seg > .3) {
                cols += 1;
                lastW = wbal;
            } else {
                lastW = seg + wbal;
            }
            for (int i = 0; i < 6; i++) {


                int top = 0, left = 0;
                for (int row = 0; row < rows; row++) { //8
                    int tileh = (row == rows - 1) ? lastH : seg;
                    for (int col = 0; col < cols; col++) { //8

                        int tilew = (col == cols - 1) ? lastW : seg;

                      //  logger.debug("Writing:" + i + " - " + row + " - " + col);
                        top = row * seg;
                        left = col * seg;
                        index = (row * cols) + col;

                        byte[] tmp = getSubArray(left, top, tilew, tileh, bcoeffs[i], width);

                        BufferedImage bf = new BufferedImage(tilew, tileh, BufferedImage.TYPE_3BYTE_BGR);
                        bf.getRaster().setDataElements(0, 0, tilew, tileh, tmp);

                        ImageOutputStream ios = null;
                        J2KImageWriteParam encodeParam = (J2KImageWriteParam) writer.getDefaultWriteParam();
                        encodeParam.setFilter(J2KImageWriteParam.FILTER_53);
                        encodeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        encodeParam.setCompressionType("JPEG2000");
                        encodeParam.setLossless(true);


                        String rate = getEncodeRate("RGBER", fileName);

                        String file = cache_path + File.separator + fileName + File.separator + fileName + "_" + i + "_" + index + ".jp2";
                       /* 
                            Path p = Paths.get(file);

    try (OutputStream out = new BufferedOutputStream(
      Files.newOutputStream(p, CREATE, APPEND))) {
      
    } catch (IOException x) {
      System.err.println(x);
    }*/

                        FileOutputStream fos = new FileOutputStream(file);
                        ios = ImageIO.createImageOutputStream(fos);
                        if (!rate.equals("-1")) {
                            encodeParam.setEncodingRate(Double.parseDouble(rate));
                        } else {
                            encodeParam.setEncodingRate(Double.MAX_VALUE);
                        }

                        encodeParam.setTilingMode(ImageWriteParam.MODE_DISABLED);
                        try {
                            writer.setOutput(ios);

                            writer.write(null, new IIOImage(bf, null, null), encodeParam);

                            ios.flush();
                            ios.close();
                            fos.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }


            }


           // logger.debug("timtaken to cache:" + (System.currentTimeMillis() - start));

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

/*
    PSVM(String[] args) {
      //  if (args[0] == null) {
       //     return;
      //  }
        try {
            File f = new File("D:\\code\\IDPCODE-GeneralMenu\\Build\\deployOLD\\" + "PFAT_383_RE1p" + ".ptm");//Neuman_bulla_v_700.ptm)

            getPTMParser(new FileInputStream(f), "PFAT_383_RE1p", f.length()).cachePTM();

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
*/
    public byte[] getSubArray(int left, int top, int segWidth, int segHeight, byte[] coeffs, int imgWidth) {

        byte[] result = new byte[segWidth * segHeight * 3];


        int pixel = 0, pos = 0;
        for (int y = top, b = 0; y < segHeight + top; y++, b++) {
            for (int x = left, a = 0; x < segWidth + left; x++, a++) {
                pixel = y * imgWidth + x;
                pos = b * segWidth + a;
                try {
                    result[pos * 3 + 0] = coeffs[pixel * 3 + 0];
                    result[pos * 3 + 1] = coeffs[pixel * 3 + 1];
                    result[pos * 3 + 2] = coeffs[pixel * 3 + 2];
                } catch (Exception exp) {
                    System.out.println("pix:" + pixel + "::img:" + imgWidth);
                }

            }
        }

        return result;

    }


    private byte[][] resize(int newWidth, int newHeight, int tileW, int tileH, byte[][] bcoeff) {
        byte[][] coeffs = new byte[6][newWidth * newHeight * 3];

        int xp, yp;
        float sx = (float) tileW / (float) newWidth;
        float sy = (float) tileH / (float) newHeight;

        int offset1 = 0, offset2 = 0;
        for (int i = 0; i < 6; i++) {

            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < newWidth; x++) {
                    xp = (int) (sx * x);
                    yp = (int) (sy * y);
                    offset1 = x + y * newWidth;
                    offset2 = xp + yp * tileW;

                    coeffs[i][offset1 * 3 + 0] = bcoeff[i][offset2 * 3 + 0];
                    coeffs[i][offset1 * 3 + 1] = bcoeff[i][offset2 * 3 + 1];
                    coeffs[i][offset1 * 3 + 2] = bcoeff[i][offset2 * 3 + 2];
                }
            }
        }


        return coeffs;

    }

}