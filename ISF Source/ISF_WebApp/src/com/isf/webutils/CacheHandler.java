/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 16, 2007
 * Time: 7:21:00 PM
 * To change this template use File | Settings | File Templates.
 */
package com.isf.webutils;

import isf.cache.LRUCache;

public class CacheHandler {
    private static CacheHandler ourInstance = new CacheHandler();
    private LRUCache cache;

    public static CacheHandler getInstance() {
        return ourInstance;
    }

    private CacheHandler() {
    }

    public void setCache(LRUCache cache) {
        this.cache = cache;
    }

    public LRUCache getCache() {
        return this.cache;
    }

   /* PSVM(String args[]) {
        LRUCache cache = null;
        try {
            String cacheDirectory = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
            FileInputStream fis = new FileInputStream(cacheDirectory + File.separator + "lruCache_rti.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cache = (LRUCache) ois.readObject();
            ois.close();
            fis.close();
            logger.debug("Read Cache");
        } catch (FileNotFoundException fnfe) {
            logger.debug("Here FileNotFound");
            cache = new LRUCache();
            cache.createSynchronizedMap(null);
        } catch (Exception ioe) {
            logger.debug("FileIOHere");
            cache = new LRUCache();
            cache.createSynchronizedMap(null);
            ioe.printStackTrace();
        } finally {
            CacheHandler.getInstance().setCache(cache);
        }
        try {
            String cacheDirectory = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
            FileOutputStream fos = new FileOutputStream(cacheDirectory + File.separator + "lruCache_rti.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(CacheHandler.getInstance().getCache());
            obs.flush();
            obs.close();
            fos.close();
            logger.debug("Written cache");
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }*/
    /*
     public static void writeToCache(OutputStream os, PTMCoefficients source, boolean zip) throws IOException {
         OutputStream ops = os;
         if (zip)
             ops = new GZIPOutputStream(os);
         ObjectOutputStream oos = new ObjectOutputStream(ops);

         oos.writeObject(source);
         oos.flush();
         oos.close();
     }

     public static PTMCoefficients readFromCache(InputStream os, boolean zip) throws IOException {
         InputStream ops = os;
         if (zip)
             ops = new GZIPInputStream(os);
         ObjectInputStream oos = new ObjectInputStream(ops);
         try {

             PTMCoefficients obj = (PTMCoefficients) oos.readObject();
             oos.close();
             return obj;
         } catch (ClassNotFoundException cnfe) {
             cnfe.printStackTrace();
             return null;
         }
     } */


}
