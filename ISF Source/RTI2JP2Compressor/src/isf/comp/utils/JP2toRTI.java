/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.comp.utils;

import isf.common.request.RequestProxy;
import isf.common.utils.PTMConfig;
import isf.common.utils.SegmentUtils;
import isf.common.utils.ThreadExecutor;
import isf.ptm.fileio.PTMWriter;
import isf.ptm.formats.BLRGBPTM;
import isf.ptm.formats.HSH;
import isf.ptm.formats.PTM;
import isf.ptm.formats.RGBPTM;
import isf.ptm.utils.SwingWorker;
import isf.ptm.utils.Utils;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author raok1
 */
public class JP2toRTI {
    
    
    public static void main(String[] args){
      JP2toRTI jpe = new JP2toRTI();
      try{
      jpe.rebuildAllRTIs();
      }catch(Exception exp){
         exp.printStackTrace();
      }
    }

    int thread;

    SegmentUtils[] segments;
    PTM ptm = null;
    int rows, cols, segSize, peices;
    int width;
    int height;
    int[] tiles;
    int obtained;

    public void rebuildAllRTIs() throws Exception {
        File f = new File(CompressValues.cacheDirectory);
        File[] files = f.listFiles();
        if (files != null) {
            String name = "", path = "";
            for (int i = 0; i < files.length; i++) {
                name = files[i].getName();
                if (files[i].isFile()) {
                    continue;
                }
                PTMConfig conf = readConfig(name);
                loadFile(conf);
                while(obtained <tiles.length)
                    Thread.sleep(5000);
                dumpPTM(name,ptm,conf);
            }
        }
    }
    private PTMConfig readConfig(String name){
             Document dom = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            dom = builder.parse(CompressValues.cacheDirectory + File.separatorChar + name + File.separatorChar +name+ ".cfg");
        } catch (Exception pe) {
           pe.printStackTrace();
            return null;
        }
        PTMConfig ptm = new PTMConfig();
        ptm.setName(retrieveAttributeValue(dom, "PTM", "name"));
        ptm.setWidth(retrieveAttributeValueInt(dom, "PTM", "width"));
        ptm.setHeight(retrieveAttributeValueInt(dom, "PTM", "height"));
        String scaleStr = retrieveAttributeValue(dom, "PTM", "scale");
        String biasStr = retrieveAttributeValue(dom, "PTM", "bias");
        ptm.setRows(retrieveAttributeValueInt(dom, "PTM", "rows"));
        ptm.setColumns(retrieveAttributeValueInt(dom, "PTM", "cols"));
        ptm.setSegmentSize(retrieveAttributeValueInt(dom, "PTM", "segment"));
        String[] biasA = Utils.split(biasStr, ",");
        int[] bias = new int[biasA.length];
        for (int i = 0; i < biasA.length; i++) {
            bias[i] = (int) Float.parseFloat(biasA[i]);
        }
        String[] scaleA = Utils.split(scaleStr, ",");
        float[] scale = new float[scaleA.length];
        for (int i = 0; i < scaleA.length; i++) {
            scale[i] = Float.parseFloat(scaleA[i]);
        }
        ptm.setScale(scale);
        ptm.setBias(bias);

        ptm.setSize(retrieveAttributeValueLong(dom, "PTM", "size"));
        ptm.setVersion(retrieveAttributeValue(dom, "PTM", "version"));

        if (ptm.getVersion().indexOf("HSH") > -1) {
            ptm.setExtension(".rti");

            ptm.setType(retrieveAttributeValueInt(dom, "PTM", "type"));
            ptm.setTerms(retrieveAttributeValueInt(dom, "PTM", "basisterms"));
            ptm.setColors(retrieveAttributeValueInt(dom, "PTM", "colors"));
            ptm.setElements(retrieveAttributeValueInt(dom, "PTM", "elemSize"));
            ptm.setBasistype(retrieveAttributeValueInt(dom, "PTM", "termType"));
        } else if (ptm.getVersion().indexOf("PTM") > -1) {
            ptm.setExtension(".ptm");

            String type = retrieveAttributeValue(dom, "PTM", "type");
            ptm.setType(PTM.TYPE_RAW_PTM);
            
            if (type.equalsIgnoreCase(PTM.PTM_RGB)) {
                ptm.setBasistype(PTM.BASIS_RGB);
            } else if (type.equalsIgnoreCase(PTM.PTM_LRGB)) {
                ptm.setBasistype(PTM.BASIS_LRGB);
            }
        }
        ptm.setCached(retrieveAttributeValue(dom, "PTM", "cached"));
        return ptm;   
    }
    private void loadFile(PTMConfig ptmCon) {
        //System.out.println("Started Loading File:" + ptmCon.getName());
        // long start = System.currentTimeMillis();
        final String ptmFile = ptmCon.getName();
        width = ptmCon.getWidth();
        height = ptmCon.getHeight();
        int times = 0;
        boolean isRTI = false;

        if (ptmCon.getType() == PTM.TYPE_RAW_PTM || ptmCon.getType() == PTM.TYPE_RTI_PTM) {

            if (ptmCon.getBasistype() == PTM.BASIS_LRGB) {
                ptm = new BLRGBPTM();
                ((BLRGBPTM) ptm).setCoefficients(new byte[9][width * height]);
                ptm.setBasisType(PTM.BASIS_LRGB);
                times = 3;
            } else if (ptmCon.getBasistype() == PTM.BASIS_RGB) {
                ptm = new RGBPTM();
                ((RGBPTM) ptm).setBCoefficients(new byte[width * height][18]);
                ptm.setBasisType(PTM.BASIS_RGB);
                times = 6;
            }
        } else if (ptmCon.getType() == PTM.TYPE_RTI_HSH) {
            ptm = new HSH();
            isRTI = true;
            int totalTerms = 0;
            int basisTerms = ptmCon.getTerms();
            int colors = ptmCon.getColors();

            ((HSH) ptm).setBasisTerms(basisTerms);
            ((HSH) ptm).setColors(colors);
            ((HSH) ptm).setFileType(PTM.TYPE_RTI_HSH);
            ((HSH) ptm).setBasisType(ptmCon.getBasistype());
            ((HSH) ptm).setColors(colors);

            switch (ptmCon.getBasistype()) {
                case PTM.BASIS_LRGB:
                    totalTerms = basisTerms + colors;
                    break;
                case PTM.BASIS_RGB:
                    totalTerms = basisTerms * colors;
                    break;
            }

            ((HSH) ptm).setBCoefficients(new byte[width * height][totalTerms]);
            times = totalTerms / 3;

        }
        // setDefaultLight();
        final int nTimes = times;
        rows = ptmCon.getRows();
        final boolean isHSH = isRTI;
        cols = ptmCon.getColumns();
        peices = rows * cols;
        segments = new SegmentUtils[peices * times];
        tiles = new int[peices * times];
        System.out.println("Size of tiles:" + tiles.length);
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = -1;
            //// segments[i] = new SegmentUtils();
        }

        ptm.setWidth(width);
        ptm.setHeight(height);
        ptm.setName(ptmCon.getName());
        ptm.setVersion(ptmCon.getVersion());
        ptm.setScaleArray(ptmCon.getScale());

        ptm.setBias(ptmCon.getBias());
        segSize = ptmCon.getSegmentSize();

        SwingWorker worker = new SwingWorker() {

            public Object construct() {
                try {

                    int index;
                    SegmentUtils seg;
                    if (isHSH) {
                        for (int i = 0; i <= nTimes - 1; i++) {
                            for (int row = 0; row < rows; row++) { //8
                                for (int col = 0; col < cols; col++) { //8
                                    index = (row * cols) + col;
                                    seg = new SegmentUtils();
                                    seg.setBand(i);
                                    seg.setRow(row);
                                    seg.setCol(col);
                                    seg.setBandTile(index);
                                    seg.setSize(segSize);
                                    seg.setName(ptmFile);
                                    segments[(i * peices) + index] = seg;
                                    FetchImage segment = new FetchImage(seg, ptm);
                                    ThreadExecutor.getInstance().execute(segment, false);
                                    //segment.run();
                                }
                            }
                        }

                    } else {
                        for (int i = nTimes - 1; i >= 0; i--) {
                            for (int row = 0; row < rows; row++) { //8
                                for (int col = 0; col < cols; col++) { //8
                                    index = (row * cols) + col;
                                    seg = new SegmentUtils();
                                    seg.setBand(i);
                                    seg.setRow(row);
                                    seg.setCol(col);
                                    seg.setBandTile(index);
                                    seg.setSize(segSize);
                                    seg.setName(ptmFile);
                                    segments[(i * peices) + index] = seg;
                                    FetchImage segment = new FetchImage(segments[(i * peices) + index], ptm);
                                    ThreadExecutor.getInstance().execute(segment, false);
                                    //segment.run();
                                }
                            }
                        }
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    return null;
                }

                return null;

            }

            @Override
            public void finished() {
                //  saveThumbnail();
                //obtained--;
            }
        };
        worker.start();

    }
    
     private String retrieveAttributeValue(Document dom, String tagname, String attribute) {
        //System.out.println("Fetching values of "+attribute);
        NodeList nodes = dom.getElementsByTagName(tagname);
        //assert(nodes.getLength() == 1);
        Node node = nodes.item(0);
        NamedNodeMap nodemap = node.getAttributes();
        //System.out.println("has node attribute" + nodemap.getLength());
        return nodemap.getNamedItem(attribute).getNodeValue();
    }

    private int retrieveAttributeValueInt(Document dom, String tagname, String attribute) {
        NodeList nodes = dom.getElementsByTagName(tagname);
        //assert(nodes.getLength() == 1);
        Node node = nodes.item(0);
        NamedNodeMap nodemap = node.getAttributes();
        String value = nodemap.getNamedItem(attribute).getNodeValue();
        return Integer.parseInt(value);
    }

    private long retrieveAttributeValueLong(Document dom, String tagname, String attribute) {
        NodeList nodes = dom.getElementsByTagName(tagname);
        //assert(nodes.getLength() == 1);
        Node node = nodes.item(0);
        NamedNodeMap nodemap = node.getAttributes();
        String value = nodemap.getNamedItem(attribute).getNodeValue();
        return Long.parseLong(value);
    }
    
     public void dumpPTM(String name, PTM ptm, PTMConfig config) {

        String file = CompressValues.cacheDirectory + File.separator + name;
        try {
            
            FileOutputStream fos = new FileOutputStream(file + config.getExtension());
            PTMWriter writer = PTMWriter.getPTMWriter(ptm);
            if(writer !=null)
               writer.writePTM(ptm, fos);
            else
                System.out.println("pulling from cache");
              //  LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).debug("from caching writer is null");

            File f = new File(file + config.getExtension());
       //     long size = f.length();
           // cache.updateEntry(file, size);
        

        } catch (Exception exp) {
            exp.printStackTrace();
            // LogManager.getLogger(isf.cache.utils.ImageUtils.class.getCanonicalName()).error(exp);
        }


    }
    

    private class FetchImage implements Runnable { //extends Thread

        private String file;
        private int index;
        private PTM ptm;
        int row, col, tilew, tileh, band;
        int top, left;
        int tileIndex = 0;

        public FetchImage(SegmentUtils seg, PTM ptm) {
            this.file = seg.getName();
            this.ptm = ptm;
            this.row = seg.getRow();
            this.col = seg.getCol();
            int segsize = seg.getSize();
            if (row == rows - 1) {
                tileh = height % segsize;
                if (((float) tileh) / segsize <= .3) {

                    tileh = segsize + tileh;
                }
            } else {
                this.tileh = segsize;
            }
            if (col == cols - 1) {
                tilew = width % segsize;
                if (((float) tilew) / segsize <= .3) {

                    tilew = segsize + tilew;
                }

            } else {
                this.tilew = segsize;
            }
            this.band = seg.getBand();
            this.index = seg.getBandTile();
            top = row * segsize;
            left = col * segsize;
            this.tileIndex = (band * peices) + index;
            //this.setName(tileIndex+"");
        }

        public void run() {

            try {
                
                String fname = CompressValues.cacheDirectory+ File.separator +
                        file + File.separator + file + "_" + band+"_"+index + ".jp2";

                FileInputStream fis = new FileInputStream(fname);
                int buflen = 10 * 1024 * 1024;

                BufferedInputStream bis = new BufferedInputStream(fis, buflen);
                BufferedImage image  = ImageIO.read(bis);
                byte[] srcP = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                if (srcP != null) {
                    ptm.setBCoeffs(top, left, tileh, tilew, band, srcP);
                    tiles[tileIndex] = 0;
                    obtained++;
                } else {
                    tiles[tileIndex] = 1;
                }
                // log.error("Loaded Section:"+top+":"+left+":"+tileh+":"+tilew+":"+band);

            } catch (Exception exp) {
                exp.printStackTrace();
            }

        }
    }
}
