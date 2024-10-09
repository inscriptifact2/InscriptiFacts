package com.isf.servlet;

import com.isf.parsers.LazyParser;
import com.isf.parsers.RTIParser;
import com.isf.webutils.CacheHandler;

import com.isf.webutils.InscriptWebLoader;
import isf.cache.LRUCache;
//import isf.cache.utils.CacheHandler;
import isf.common.utils.LogManager;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: 0223 Date: Aug 16, 2007 Time: 7:15:14 PM To
 * change this template use File | Settings | File Templates.
 */
public class CacheManager extends HttpServlet {

    String cachePath, ptmPath, cacheSize, destination;
    Logger log = LogManager.getLogger(this.getClass());
    long interval = 20 * 60 * 1000;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String ptm = request.getParameter("ptm");
        if (type != null && type.equalsIgnoreCase("dump")) {
            LRUCache cache = CacheHandler.getInstance().getCache();
            response.getOutputStream().write(cache.dumpValues().getBytes());
        } else if (type != null && type.equalsIgnoreCase("clean")) {
            LRUCache cache = CacheHandler.getInstance().getCache();
            if (ptm != null) {

                ptm = cachePath + File.separator + ptm;
                cache.deleteEntry(ptm);
            }
        } else if (type != null && type.equalsIgnoreCase("delete")) {
            LRUCache cache = new LRUCache();
            cache.setLimit(cacheSize);
            cache.createSynchronizedMap(null);
            CacheHandler.getInstance().setCache(cache);
        } else if (type != null && type.equalsIgnoreCase("create")) {
            response.getOutputStream().println("Started Caching.....");
            cachePath = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
            ptmPath = InscriptWebLoader.getInstance().getProperty("ptmFolder");
            interval = Long.parseLong(InscriptWebLoader.getInstance().getProperty("cacheInterval"));
            cacheSize = InscriptWebLoader.getInstance().getProperty("cacheSize");
            destination = InscriptWebLoader.getInstance().getProperty("backupFolder");
            LRUCache cache = null;
            File frti = new File(cachePath + File.separator + "lruCache_rti.ser");

            try {
                if (!frti.exists()) {
                    cache = new LRUCache();
                    cache.setLimit(cacheSize);
                    cache.createSynchronizedMap(null);
                } else if (frti.exists()) {

                    FileInputStream fis = new FileInputStream(frti);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    cache = (LRUCache) ois.readObject();
                    cache.setLimit(cacheSize);
                    ois.close();
                    fis.close();
                    log.debug("Read Cache with rti");

                } /*else if (fptm.exists()) {

                 FileInputStream fis = new FileInputStream(fptm);
                 ObjectInputStream ois = new ObjectInputStream(fis);
                 cache = (LRUCache) ois.readObject();
                 cache.setLimit(cacheSize);
                 ois.close();
                 fis.close();
                 log.debug("Read Cache with ptm");

                 }*/
            } catch (Exception exp) {
            } finally {
                CacheHandler.getInstance().setCache(cache);
                response.getOutputStream().println("Caching.....");
                cacheFiles();
                response.getOutputStream().println("Cached....");

            }
        }
    }

    public void init() throws ServletException {
        // LogManager.initialize("log4j.txt");
        super.init();    //To change body of overridden methods use File | Settings | File Templates.

        cachePath = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
        ptmPath = InscriptWebLoader.getInstance().getProperty("ptmFolder");
        interval = Long.parseLong(InscriptWebLoader.getInstance().getProperty("cacheInterval"));
        cacheSize = InscriptWebLoader.getInstance().getProperty("cacheSize");
        destination = InscriptWebLoader.getInstance().getProperty("backupFolder");
        LRUCache cache = null;
        File frti = new File(cachePath + File.separator + "lruCache_rti.ser");
        // File fptm = new File(cachePath + File.separator + "lruCache.ser");
        try {
            if (!frti.exists()) {
                cache = new LRUCache();
                cache.setLimit(cacheSize);
                cache.createSynchronizedMap(null);
            } else if (frti.exists()) {

                FileInputStream fis = new FileInputStream(frti);
                ObjectInputStream ois = new ObjectInputStream(fis);
                cache = (LRUCache) ois.readObject();
                cache.setLimit(cacheSize);
                ois.close();
                fis.close();
                log.debug("Read Cache with rti");

            } /*else if (fptm.exists()) {

             FileInputStream fis = new FileInputStream(fptm);
             ObjectInputStream ois = new ObjectInputStream(fis);
             cache = (LRUCache) ois.readObject();
             cache.setLimit(cacheSize);
             ois.close();
             fis.close();
             log.debug("Read Cache with ptm");

             }*/
        } catch (Exception exp) {
        } finally {
            CacheHandler.getInstance().setCache(cache);
            cacheFiles();
        }

        /*
         * try { FileInputStream fis = new FileInputStream(cachePath +
         * File.separator + "lruCache_rti.ser"); ObjectInputStream ois = new
         * ObjectInputStream(fis); cache = (LRUCache) ois.readObject();
         * cache.setLimit(cacheSize); ois.close(); fis.close(); log.debug("Read
         * Cache"); } catch (FileNotFoundException fnfe) { log.error("Here
         * FileNotFound"); cache = new LRUCache(); cache.setLimit(cacheSize);
         * cache.createSynchronizedMap(null); // cacheFiles(); } catch
         * (Exception ioe) { log.error("FileIOHere"); cache = new LRUCache();
         * cache.createSynchronizedMap(null); cache.setLimit(cacheSize); //
         * cacheFiles(); log.error(ioe); }
         */




    }

    private void cacheFiles() {
        Runnable cacheThread = new Runnable() {
            public void run() {
                boolean caching = false;
                File destdir = new File(destination);
                boolean isPTM = false;
                String ext = "";

                try {
                    boolean found = false;
                    while (true) {
                        if (!caching) {
                            caching = true;
                            File f = new File(ptmPath);
                            File[] files = f.listFiles();
                            if (files != null) {
                                String name = "", path = "";
                                for (int i = 0; i < files.length; i++) {
                                    name = files[i].getName();
                                    System.out.println("Cacheing:"+name);
                                    if (files[i].isDirectory()) {
                                        continue;
                                    }
                                    ext = "";
                                    isPTM = false;
                                    if (name.endsWith(".ptm")) {
                                        ext = ".ptm";
                                        isPTM = true;
                                    } else if (name.endsWith(".rti")) {
                                        ext = ".rti";
                                    } else if (name.endsWith(".hsh")) {
                                        ext = ".hsh";
                                    }
                                    //      if (name.endsWith(".ptm")) {
                                    // name = name.replaceAll(ext, "");

                                    name = name.substring(0, name.lastIndexOf(ext));
                                    path = cachePath + File.separator + name;
                                    if (!CacheHandler.getInstance().getCache().isCached(path)) {
                                        log.debug("caching : " + name);
                                        found = true;
                                        if (isPTM) {
                                            LazyParser.getPTMParser(new FileInputStream(files[i]), name, files[i].length()).cachePTM();
                                        } else {
                                            RTIParser parser = new RTIParser(new FileInputStream(files[i]), name, files[i].length());
                                            parser.cachePTM();
                                        }
                                        CacheHandler.getInstance().getCache().updateEntry(path);
                                        File target = new File(f, name + ext);
                                        File dest = new File(destdir, target.getName());
                                        if (target.exists()) {
                                            if (dest.exists()) {
                                                dest.delete();
                                            }
                                            boolean success = target.renameTo(new File(destdir, target.getName()));
                                            if (!success) {
                                                log.debug(target.getName() + " could not be moved");
                                            }
                                            //  --i;
                                        }

                                    } else {
                                        log.debug("Already Cached : " + name);

                                    }



                                }
                            }
                            if (found) {
                                log.debug("Cached all Files");
                                serialize();
                                caching = false;
                                found = false;
                            }
                        }
                        Thread.sleep(interval); //check every two hours
                    }


                } catch (Exception exp) {
                    log.error(exp);
                    exp.printStackTrace();


                }
            }
        };
        Thread t = new Thread(cacheThread);
        t.start();
    }

    private void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream(cachePath + File.separator + "lruCache_rti.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(CacheHandler.getInstance().getCache());
            obs.flush();
            obs.close();
            fos.close();
            log.debug("Written cache");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void destroy() {
        super.destroy();    //To change body of overridden methods use File | Settings | File Templates.
        // String dir =  PropertyManager.getInstance("server.ini").getProperty("cacheDirectory");

        serialize();

    }
}
