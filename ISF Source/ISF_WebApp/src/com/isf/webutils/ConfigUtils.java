package com.isf.webutils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Jun 27, 2007
 * Time: 4:36:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigUtils {


    public static final String path = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
    public static final int SEGMENT = Integer.parseInt(InscriptWebLoader.getInstance().getProperty("segment"));


    public static String getConfigString(String name) {

        StringBuffer temp = new StringBuffer();
        //   temp.append(header);
        try {

            FileInputStream fis = new FileInputStream(path + File.separator + name + File.separator + name + ".cfg");
            byte[] bytes = new byte[2000];

            int read = fis.read(bytes);
            temp.append(new String(bytes, 0, read));

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return temp.toString();


    }
/*
    public static String getUnCachedConfigString(String ptmPath, String name) {

        StringBuffer temp = new StringBuffer();
        //   temp.append(header);
        try {
            File f = new File(ptmPath, name + ".ptm");
            FileInputStream in = new FileInputStream(f);
            String version = PTMIO.getLine(in);
            String type = PTMIO.getLine(in);
            int width = Integer.parseInt(PTMIO.getLine(in));
            int height = Integer.parseInt(PTMIO.getLine(in));
            String[] sa;
            
            sa = PTMIO.getLine(in).split(" ");
            float[] scale = new float[sa.length];
            for (int i = 0; i < sa.length; i++) {
                scale[i] = Float.parseFloat(sa[i]);

                // debug("Scale: " + Utils.asString(scale));

                
            }
            sa = PTMIO.getLine(in).split(" ");
            int[] bias = new int[sa.length];
            for (int i = 0; i < sa.length; i++) {
                bias[i] = Integer.parseInt(sa[i]);
            }


            String sep = ",";
            temp.append("    <PTM name=\"");
            temp.append(name);
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
            temp.append(f.length() + "");

            temp.append("\" rows=\"");
            int rows = height / SEGMENT;
            int balance = height % SEGMENT;
            if (((float) balance) / SEGMENT > 0.3)
                rows += 1;
            rows = (rows == 0) ? 1 : rows;
            temp.append(rows);
            int cols = width / SEGMENT;
            balance = width % SEGMENT;
            if (((float) balance) / SEGMENT > 0.3)
                cols += 1;
            cols = (cols == 0) ? 1 : cols;
            temp.append("\" cols=\"");
            temp.append(cols);
            temp.append("\" segment=\"");
            temp.append(SEGMENT);
            temp.append("\" type=\"");
            temp.append(type);
            temp.append("\" version=\"");
            temp.append(version);
            temp.append("\" cached=\"no");
            temp.append("\"/>\n");
            in.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return temp.toString();


    }*/
}
