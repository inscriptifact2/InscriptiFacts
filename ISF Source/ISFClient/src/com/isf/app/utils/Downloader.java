package com.isf.app.utils;

import isf.common.ISFBase64;
import isf.common.request.RequestProxy;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.InscriptConfigLoader;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

public class Downloader extends Thread implements Runnable {

    private long len, dsize;
    private Frame parent;
    private long noThreads = 9L;
    private long chunksize, leftchunk;
    private String file;
    private ThreadGroup tg;
    private String ext = "";
    Logger logger = org.apache.log4j.LogManager.getLogger(com.isf.app.utils.Downloader.class);
   // static String fileDir = "";
    static String downDir="";

    public Downloader(String file, String size, Frame parent) {

        super(file);

        if (downDir.equals("")) {
//            FileDialog fd = new FileDialog(parent) ;
//            fd.setMode(FileDialog.SAVE);
//            fd.setTitle("Choose location for saving downloaded files");
//            fd.setFile(file+".jp2")  ;
//            fd.setVisible(true);
//            fileDir=fd.getDirectory() ;
            //fileDir = System.getProperty("user.dir");
           // fileDir = CacheManagerConstants.folderName;
            downDir = CacheManagerConstants.downloadFolderName;
        }
        dsize = 0L;
        tg = new ThreadGroup(file);
        this.file = file;
        this.parent = parent;
        int k = size.indexOf("KB");
        int m = size.indexOf("MB");
        if (k > -1) {
            size = size.substring(0, k);
            len = Long.parseLong(size.trim()) * 1024;
        } else if (m > -1) {
            size = size.substring(0, m);
            len = Long.parseLong(size.trim()) * 1024 * 1024;
        } else {
            len = Long.parseLong(size.trim());
        }

        ext = InscriptConfigLoader.getInstance().getProperty("ext_download");
        //  len = Long.parseLong(size);
        chunksize = (long) len / noThreads;
        leftchunk = (long) (len % noThreads);
        //setDaemon(false);
        start();
    }


    public void run() {

        try {


            String s25 = "<QUERY TYPE='siddata' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='photoid" +
                    "' VALUE='" + ISFBase64.getQueryVal(file) + "' OPERATOR='=' CONNECTOR='AND'></QUERY_CONDITION>" +
                    "<QUERY_CONDITION KEY='start' VALUE='0' OPERATOR='=' CONNECTOR ='AND'>" +
                    "</QUERY_CONDITION><QUERY_CONDITION KEY='length' VALUE='" + len + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE></QUERY>";
            //  RequestDispatcherObject requestdispatcherobject5 = new RequestDispatcherObject();
            //   byte abyte0[] = (byte[])RequestDispatcherObject.getInstance().handleQueryEvent(s25);
            Object obj = RequestProxy.getRequestDispatcher().handleQueryEvent(s25);
            //CacheManagerObject cachemanagerobject = CacheManagerObject.getInstance();
            // CacheEntry cacheentry = new CacheEntry(file + "c.sid");
            if (obj != null) {

               // File ofile = new File(fileDir, file + ext);
                File dfile = new File(downDir, file + ext);
                FileOutputStream fileoutputstream = null;
                FileOutputStream fos = null;

                if (obj instanceof byte[]) {


                    String contents = new String((byte[]) obj);
                    if (contents.indexOf("ERRMSG MSG") > -1) {
                        JOptionPane.showMessageDialog(parent, "Requested JPEG2000 file " + file + " not found. Please Contact Administrator",
                                "Error", 0, new ImageIcon("images/isf.jpg"));
                        return;
                    }
                    fileoutputstream = new FileOutputStream(dfile);
                    fos = new FileOutputStream(dfile);
                    fileoutputstream.write((byte[]) obj);
                    fos.write((byte[]) obj);
                    fileoutputstream.flush();
                    fos.flush();
                    fileoutputstream.close();
                    fos.close();

                } else if (obj instanceof Hashtable) {
                    Hashtable h = (Hashtable) obj;
                   // RandomAccessFile ras = new RandomAccessFile(ofile, "rw");
                    RandomAccessFile ras1 = new RandomAccessFile(dfile, "rw");
                    long j = 0L;
                    for (int i = 0; i < h.size() - 1; i++) {
                        byte[] byts = (byte[]) h.get(i + "");
                      //  ras.seek(j);
                        ras1.seek(j);
                      //  ras.write(byts);
                        ras1.write(byts);
                        j += (byts.length);
                    }
                  //  ras.close();
                    ras1.close();
                    len = Long.parseLong(((String) h.get("length")).trim());
                    logger.debug("**>>**>>**Length of File " + file + ": " + len);

                } else {
                    /*fileoutputstream = new FileOutputStream(ofile);
                    ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
                    objectoutputstream.writeObject(obj);
                    objectoutputstream.close();
                    fileoutputstream.close();*/
                    FileOutputStream fos1 = new FileOutputStream(dfile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos1);
                    oos.writeObject(obj);
                    oos.close();
                    fos1.close();

                }

                dfile = new File(downDir, file + ext);
                dsize = dfile.length();
                logger.debug(">>**>>**>>**>>Length of File received for " + file + ": " + dsize);
                dfile = null;
                // dir = null;

            }
            


        } catch (Exception exception6) {
            logger.error(exception6);
        }

//        if (dsize == len)
//            JOptionPane.showMessageDialog(parent, "Downloading of JPEG2000 file " + file + " is completed.",
//                    "Download Information", 1, new ImageIcon("images/isf.jpg"));
//        else
//            JOptionPane.showMessageDialog(parent, "Downloading of JPEG2000 file " + file + " failed. Try Again",
//                    "Error", 0, new ImageIcon("images/isf.jpg"));

        if (dsize == len)
            JOptionPane.showMessageDialog(parent, "Requested JPEG2000 file (" + file + ".jp2) has been downloaded successfully. It can be found in the " + downDir + " folder.",
                    "Download Information", 1, new ImageIcon("images/isf.jpg"));
        else
            JOptionPane.showMessageDialog(parent, "Requested JPEG2000 file (" + file + ".jp2) could not be downloaded. Try Again",
                    "Error", 0, new ImageIcon("images/isf.jpg"));


    }

}