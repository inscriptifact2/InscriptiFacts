/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.controls.utils;


import isf.common.utils.PTMConfig;
import isf.ptm.formats.BLRGBPTM;
import isf.ptm.formats.PTM;
import isf.ptm.formats.RGBPTM;
import java.io.*;

/**
 * @author IN030223
 */
public class PTMUtils {

    public static PTM createCopy(PTM src) {
        PTM dest = null;
        int type = src.getFileType();
        int width = src.getWidth();
        int height = src.getHeight();
        int basis = src.getBasisType();
        byte[][] coeffs;
        if (type == PTM.TYPE_RAW_PTM) {
            switch (basis) {
                case PTM.BASIS_RGB:
                    dest = new RGBPTM();
                    dest.setFileType(PTM.TYPE_RAW_PTM);
                    dest.setBasisType(PTM.BASIS_RGB);
                    coeffs = new byte[width * height][18];
                    System.arraycopy(src.getBCoefficients(), 0, coeffs, 0, width * height);
                    dest.setWidth(width);
                    dest.setHeight(height);
                    dest.setVersion(src.getVersion());
                    dest.setScaleArray(src.getScaleArray());
                    dest.setBias(src.getBias());
                    dest.setName(src.getName() + " Copy");
                    ((RGBPTM) dest).setBCoefficients(coeffs);
                    break;

                case PTM.BASIS_LRGB:
                    dest = new BLRGBPTM();
                    dest.setFileType(PTM.TYPE_RAW_PTM);
                    dest.setBasisType(PTM.BASIS_LRGB);
                    coeffs = new byte[9][width * height];
                    System.arraycopy(src.getBCoefficients(), 0, coeffs, 0, width * height);
                    dest.setWidth(width);
                    dest.setHeight(height);
                    dest.setVersion(src.getVersion());
                    dest.setScaleArray(src.getScaleArray());
                    dest.setBias(src.getBias());
                    dest.setName(src.getName() + " Copy");
                    ((BLRGBPTM) dest).setCoefficients(coeffs);
                    break;
            }
        }
        return dest;
    }

    public static PTMConfig createCopy(PTMConfig src) {
        PTMConfig dest = new PTMConfig();
        dest.setBias(src.getBias());
        dest.setCached(src.getCached());
        dest.setColumns(src.getColumns());
        dest.setHeight(src.getHeight());
        dest.setName(src.getName() + " Copy");
        dest.setRows(src.getRows());
        dest.setScale(src.getScale());
        dest.setSegmentSize(src.getSegmentSize());
        dest.setSize(src.getSize());
        dest.setType(src.getType());
        dest.setVersion(src.getVersion());
        dest.setWidth(src.getWidth());
        dest.setColors(src.getColors());
        dest.setElements(src.getElements());
        dest.setBasistype(src.getBasistype());
        dest.setTerms(src.getTerms());
        return dest;
    }
/*
    PSVM(String[] args) {
        File f = new File("d:\\ThermoAccount");
        File[] ls;
        StringBuffer result = new StringBuffer();
        result.append("<Body><PTMS names=\"");
        String sep = "";
        // FilenameFilter filter = new PTMFileFilter();
        int i;

        for (ls = f.listFiles(), i = 0;
                ls != null && i < ls.length;
                i++) {

            if (ls[i].isDirectory()) {
                result.append(sep);
                result.append(ls[i].getName());
                sep = ",";
            }
        }
        result.append("\"></PTMS></Body>");
        //System.out.println("result:" + result);
        System.out.println("Getting names:" + result.toString());
    }
*/
    public static void copyPTM(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024*1024];
        int len;
        while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
        }

       /* FileChannel source = null;
        FileChannel destination = null;
        try {
            source =   new FileInputStream(src).getChannel();
            destination = new FileOutputStream(dst).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }*/

        in.close();
        out.close();
    }
}
