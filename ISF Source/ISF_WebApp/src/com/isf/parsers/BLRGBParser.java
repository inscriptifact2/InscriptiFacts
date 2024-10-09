package com.isf.parsers;


import com.isf.webutils.CacheHandler;
import com.sun.media.imageio.plugins.jpeg2000.J2KImageWriteParam;

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
 * Time: 12:39:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BLRGBParser extends LazyParser {

    InputStream __in;
    String fileName;
    //int[] bias;
    //  float[] scale;
    int width, height;

    public BLRGBParser(InputStream in, String name, int w, int h) {
        __in = in;
        fileName = name;
        type = "PTM_FORMAT_LRGB";
        width = w;
        height = h;

    }

    public void cachePTM() {

        saveFiles();
        double dirSize = getLength(new File(CACHE_PATH + File.separator + fileName)) / (1024 * 1024);
        CacheHandler.getInstance().getCache().updateEntry(CACHE_PATH + File.separator + fileName, dirSize);
        try {
            FileInputStream fis = new FileInputStream(CACHE_PATH + File.separator + fileName + File.separator + fileName + ".cfg");
            String str = PTMIO.getLine(fis);
            fis.close();
            str = str.replaceAll(" cached=\"no\"", " cached=\"yes\"");
            FileOutputStream fs = new FileOutputStream(CACHE_PATH + File.separator + fileName + File.separator + fileName + ".cfg");
            fs.write(str.getBytes());
            fs.flush();
            fs.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public void saveFiles() {
        try {


            byte[][] rgb = new byte[3][width * height * 3];

            int factor = 6;
            if (version.equals("PTM_1.1")) {
                factor = 9;
            }
            int band = (20 * 1024 * 1024) / (width * factor);
            band = band > height ? height : band;


            int BUFSIZ = width * band * factor;

            int r, g, b, offset;
            int from = height;
            int to = height - band;
            if (to < 0) {
                to = 0;

            }
            BufferedInputStream buff = new BufferedInputStream(__in);
            byte[] pixels = new byte[BUFSIZ];
            while (from > 0) {
                


                pixels = new byte[width * factor * (from - to)];
                buff.read(pixels);
                int x = 0;
                for (int h = from - 1; h >= to; h--) {
                    for (int w = 0; w < width; w++) {
                        offset = h * width + w;
                        for (int i = 0; i < 6; i++) {
                            byte cur = pixels[x++];
                            switch (i % 3) {
                                case 0:
                                    rgb[i / 3][offset * 3 + 2] = cur;
                                    break;
                                case 1:
                                    rgb[i / 3][offset * 3 + 1] = cur;
                                    break;
                                case 2:
                                    rgb[i / 3][offset * 3 + 0] = cur;
                                    break;
                                default:
                                    break;
                            }
                        }
                        /* do it now */
                        if (version.equals("PTM_1.1")) {

                            rgb[2][offset * 3 + 2] = pixels[x++];
                            rgb[2][offset * 3 + 1] = pixels[x++];
                            rgb[2][offset * 3 + 0] = pixels[x++];
                        }
                    }
                }
                from = to;
                to = from - band;
                if (to < band / 3) {
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

                while (from > 0) {
                    pixels = new byte[width * 3 * (from - to)];
                    buff.read(pixels);
                    int x = 0;
                    for (int h = from - 1; h >= to; h--) {
                        for (int w = 0; w < width; w++) {
                            offset = h * width + w;
                            rgb[2][offset * 3 + 2] = pixels[x++];
                            rgb[2][offset * 3 + 1] = pixels[x++];
                            rgb[2][offset * 3 + 0] = pixels[x++];
                        }
                    }
                    from = to;
                    to = from - band;
                    if (to < band / 3) {
                        to = 0;
                    }
                }
            }


            ImageWriter writer = null;
            Iterator writers = ImageIO.getImageWritersByFormatName("jpeg2000");//jpeg2000

            for (; writers.hasNext();) {
                writer = (ImageWriter) writers.next();
               // logger.debug("Writer:" + writer.getClass().getName());
                if (!writer.getClass().getName().endsWith("Lib")) {
                    break;
                }
            }
            pixels = null;

            long start = System.currentTimeMillis();
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

            byte[] tmp;

            for (int i = 0; i < 3; i++) { // i<3
 
                int top = 0, left = 0;
                for (int row = 0; row < rows; row++) { //8

                    int tileh = (row == rows - 1) ? lastH : SEGMENT;
                    for (int col = 0; col < cols; col++) { //8

                        int tilew = (col == cols - 1) ? lastW : SEGMENT;

                        top = row * SEGMENT;
                        left = col * SEGMENT;

                        index = (row * cols) + col;


                        tmp = getSubArray(left, top, tilew, tileh, rgb[i], width);
                        BufferedImage bf = null;

                        bf = new BufferedImage(tilew, tileh, BufferedImage.TYPE_3BYTE_BGR);
                        bf.getRaster().setDataElements(0, 0, tilew, tileh, tmp);
                        //  }



                        ImageOutputStream ios = null;

                        J2KImageWriteParam encodeParam = (J2KImageWriteParam) writer.getDefaultWriteParam();
                        // encodeParam.setFilter(J2KImageWriteParam.FILTER_97);
                        encodeParam.setFilter(J2KImageWriteParam.FILTER_53);
                        encodeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        encodeParam.setCompressionType("JPEG2000");
                        encodeParam.setLossless(true);

                        String rate = getEncodeRate("LRGBER", fileName);
                        String file = CACHE_PATH + File.separator + fileName + File.separator + fileName + "_" + i + "_" + index + ".jp2";
                      //  logger.debug("Writing file:" + file);
                      
                      
                        FileOutputStream fos = new FileOutputStream(file);//jpe

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

                            // Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //}
                        //fos.flush();
                    }
                }
            }

            //logger.debug("timtaken to cache:" + (System.currentTimeMillis() - start));

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public byte[] getSubArray(int left, int top, int segWidth, int segHeight, byte[] coeffs, int imgWidth) {

        byte[] result = new byte[segWidth * segHeight * 3];


        int pixel = 0, position;
        for (int y = top, b = 0; y < segHeight + top; y++, b++) {
            for (int x = left, a = 0; x < segWidth + left; x++, a++) {
                pixel = (y * imgWidth + x) * 3;
                position = (b * segWidth + a) * 3;
                try {
                    result[position] = coeffs[pixel];
                    result[position + 1] = coeffs[pixel + 1];
                    result[position + 2] = coeffs[pixel + 2];
                } catch (Exception exp) {
                    System.out.println("pix:" + pixel + "::img:" + imgWidth);
                }

            }
        }

        return result;

    }

    private byte[][] resize(int newWidth, int newHeight, int tileW, int tileH, byte[][] bcoeff) {

        byte[][] coeffs = new byte[3][newWidth * newHeight * 3];

        int xp, yp;
        //     float sx = (float) width / (float) w;
        //     float sy = (float) height / (float) h;
        float sx = (float) tileW / (float) newWidth;
        float sy = (float) tileH / (float) newHeight;


        int offset1 = 0, offset2 = 0;
        for (int i = 0; i < 3; i++) {
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
