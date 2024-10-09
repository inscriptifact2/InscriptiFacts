package isf.comp.parsers;


import com.sun.media.imageio.plugins.jpeg2000.J2KImageWriteParam;
//import isf.cache.utils.CacheHandler;
import isf.ptm.fileio.PTMIO;
import isf.ptm.formats.PTM;
import isf.ptm.utils.Utils;
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
public class RTIParser extends LazyParser{ //extends LazyParser 
 public static  int SEGMENT = 1000;
// public static  String cache_path = "D:\\code-old\\ICF\\cache";


    InputStream __in;
    String fileName;
    int width, height;
    long filesize;

    public RTIParser(InputStream in, String name, long size) {
        __in = in;
        fileName = name;
        filesize = size;

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

    private void saveCFG(float[] scale, float[] bias, int colors, int basisTerms, int termType, int elementsize, int type) {
        File dir = new File(cache_path, fileName);
        dir.mkdir();
        String sep = ",";


        StringBuffer temp = new StringBuffer();
        temp.append("    <PTM name=\"");
        temp.append(fileName);
        temp.append("\" width=\"");
        temp.append(width);
        temp.append("\" height=\"");
        temp.append(height);
        temp.append("\" scale=\"");
        for (int i = 0; i < scale.length; i++) {
            temp.append(scale[i] + sep);
        }
        temp.deleteCharAt(temp.length() - 1);
        temp.append("\" bias=\"");
        for (int i = 0; i < bias.length; i++) {
            temp.append(bias[i] + sep);
        }
        temp.deleteCharAt(temp.length() - 1);
        temp.append("\" size=\"");
        temp.append(filesize);
        temp.append("\" rows=\"");
        int rows = height / SEGMENT;
        int balance = height % SEGMENT;
        if (((float) balance) / SEGMENT > 0.3) {
            rows += 1;
        }
        rows = (rows == 0) ? 1 : rows;
        temp.append(rows);
        int cols = width / SEGMENT;
        balance = width % SEGMENT;
        if (((float) balance) / SEGMENT > 0.3) {
            cols += 1;
        }
        cols = (cols == 0) ? 1 : cols;
        temp.append("\" cols=\"");
        temp.append(cols);
        temp.append("\" segment=\"");
        temp.append(SEGMENT);
        temp.append("\" type=\"");
        temp.append(type);
        temp.append("\" version=\"");
        temp.append(version);
        temp.append("\" colors=\"");
        temp.append(colors);
        temp.append("\" basisterms=\"");
        temp.append(basisTerms); // 
        temp.append("\" termType=\"");
        temp.append(termType);
        temp.append("\" elemSize=\"");
        temp.append(elementsize);
        temp.append("\" cached=\"no");
        temp.append("\"/>\n");
        try {
            FileOutputStream fs = new FileOutputStream(cache_path + File.separator + fileName + File.separator + fileName + ".cfg");
            fs.write(temp.toString().getBytes());
            fs.flush();
            fs.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    private void saveFiles() {
        try {
            int basisTerms = 0;
            int colors = 0;
            int[] det;
            PTMIO.getLine(__in);
            String str = PTMIO.getLine(__in);
            int type = Integer.parseInt(str.trim());
            det = PTMIO.getLineAsInts(__in);
            width = det[0];
            height = det[1];
            colors = det[2];
            det = PTMIO.getLineAsInts(__in);
            basisTerms = det[0];
            int termType = det[1];
            int elementsize = det[2];
            version = new String("HSH_1.2");

            //start reading the scale and bias values as floats
            byte[] values = new byte[4 * basisTerms];
            __in.read(values);
            float[] scale = new float[basisTerms]; //gmin
            for (int i = 0, x = 0; i < values.length; i += 4, x++) {
                scale[x] = Utils.getFloatFromArray(values, i, 4);
               // scale[x] = Utils.byteArrayToFloat(values, i, 4);
            }
            float[] bias = new float[basisTerms]; //gmax
            __in.read(values);
            for (int i = 0, x = 0; i < values.length; i += 4, x++) {
                bias[x] = Utils.getFloatFromArray(values, i, 4);
              // bias[x] = Utils.byteArrayToFloat(values, i, 4);

            }

            saveCFG(scale, bias, colors, basisTerms, termType, elementsize, type);
            int offset;
            int segment = basisTerms * colors;
            if (termType == PTM.BASIS_RGB) {
                segment = basisTerms * colors;
            } else {
                segment = basisTerms + colors;
            }
            int band = (20 * 1024 * 1024) / (width * segment);
            band = band > height ? height : band;

            int length = width * band;

            byte[][] coeff = new byte[segment][width * height];

            BufferedInputStream buff = new BufferedInputStream(__in);
            byte[] pixels = new byte[length * segment];
            int from = 0, to = from + band;
            while (from < height) {
                pixels = new byte[length * segment];
                if ((to - from) < band) {
                    pixels = new byte[width * (to - from) * segment];
                }
                buff.read(pixels);
                int x = 0;
                for (int h = from; h < to; h++) {
                    for (int w = 0; w < width; w++) {
                        offset = h * width + w;
                       // for (int block = 0; block < colors; block++) {
                         //   for (int i = 0; i < basisTerms; i++) {
                        for(int block = 0 ; block<segment;block++)
                                coeff[block ][offset] = pixels[x++];
                         //   }
                       // }
                    }
                }
                from = to;
                to = from + band;

                if (to > height) {
                    to = height;
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
                }
            }
            pixels = null;
            int index = 0;
            int rows = height / SEGMENT;
            int cols = width / SEGMENT;
            int lastW, lastH;
            int hbal = height % SEGMENT;
            if (((float) hbal) / SEGMENT > .3) {
                rows += 1;
                lastH = hbal;
            } else {
                lastH = SEGMENT + hbal;
            }
            int wbal = width % SEGMENT;
            if (((float) wbal) / SEGMENT > .3) {
                cols += 1;
                lastW = wbal;
            } else {
                lastW = SEGMENT + wbal;
            }

            //cahneg logic for writing the jp2 images
            for (int i = 0; i < basisTerms; i++) {
                int top = 0, left = 0;
                for (int row = 0; row < rows; row++) { //8
                    int tileh = (row == rows - 1) ? lastH : SEGMENT;
                    for (int col = 0; col < cols; col++) { //8

                        int tilew = (col == cols - 1) ? lastW : SEGMENT;

                        //  logger.debug("Writing:" + i + " - " + row + " - " + col);
                        top = row * SEGMENT;
                        left = col * SEGMENT;
                        index = (row * cols) + col;

                        byte[] tmp = getSubArray(left, top, tilew, tileh, coeff[i],coeff[i+1*basisTerms],coeff[i+2*basisTerms], width);

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
            File f = new File("D:\\personal\\Downloads\\RTI\\" + "ARC10534Obv_000_CoinHSH10783rd.rti");//Neuman_bulla_v_700.ptm)

            new RTIParser(new FileInputStream(f), "ARC10534Obv_000_CoinHSH10783rd", f.length()).cachePTM();
            //getPTMParser(new FileInputStream(f), "ARC10534Obv_000_CoinHSH10783rd", f.length()).cachePTM();

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
*/
    public byte[] getSubArray(int left, int top, int segWidth, int segHeight, byte[] redcoeffs, byte[] greencoeffs, byte[] bluecoeffs, int imgWidth) {

        byte[] result = new byte[segWidth * segHeight * 3];


        int pixel = 0, pos = 0;
        for (int y = top, b = 0; y < segHeight + top; y++, b++) {
            for (int x = left, a = 0; x < segWidth + left; x++, a++) {
                pixel = y * imgWidth + x;
                pos = b * segWidth + a;
                try {
                    //changes the loading
                    result[pos * 3 + 0] = redcoeffs[pixel ];
                    result[pos * 3 + 1] = greencoeffs[pixel ];
                    result[pos * 3 + 2] = bluecoeffs[pixel ];
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