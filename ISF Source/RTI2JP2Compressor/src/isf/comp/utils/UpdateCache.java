/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.comp.utils;




import isf.cache.LRUCache;
//import isf.cache.utils.CacheHandler;
import isf.common.utils.LogManager;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: 0223 Date: Aug 16, 2007 Time: 7:15:14 PM To
 * change this template use File | Settings | File Templates.
 */
public class UpdateCache  {

   
    Logger log = LogManager.getLogger(this.getClass());
   
   static LRUCache cache = null;
    

    public static void main(String[] args)  {
        // LogManager.initialize("log4j.txt");
       

        
        File serFile = new File(CompressValues.cacheDirectory + File.separator + "lruCache_rti.ser");
        // File fptm = new File(cachePath + File.separator + "lruCache.ser");
        try {
            if (!serFile.exists()) {
                cache = new LRUCache();
                cache.setLimit(CompressValues.cacheSize);
                cache.createSynchronizedMap(null);
            } else if (serFile.exists()) {

                FileInputStream fis = new FileInputStream(serFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                cache = (LRUCache) ois.readObject();
                cache.setLimit(CompressValues.cacheSize);
                ois.close();
                fis.close();
                System.out.println("Read Cache with rti");

            } 
        } catch (Exception exp) {
            System.out.println("Exception:"+exp.getMessage());
            
        } finally{
            logCacheItems();
        }

    }

    private static void logCacheItems() {
       
             File f = new File(CompressValues.cacheDirectory);
             File[] files = f.listFiles();
                if (files != null) {
                    String name = "", path = "", config=""; File cfgFile;
                    for (int i = 0; i < files.length; i++) {
                        name = files[i].getName();
                        if (files[i].isDirectory()) {
                            System.out.println("directory found:"+name);
                            path = CompressValues.cacheDirectory + File.separator + name;
                            config = path+File.separator+name+".cfg";
                            cfgFile = new File(config) ;
                            if (!cache.isCached(path) && cfgFile.exists() ) {
                                System.out.println("caching : " + name);
                                cache.updateEntry(path);
                                        
                            } else {
                                System.out.println("Already Cached : " + name);

                            }
                        }
                    }

                
                }
                serialize();

 
    }

    private static void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream(CompressValues.cacheDirectory + File.separator + "lruCache_rti.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(cache);
            obs.flush();
            obs.close();
            fos.close();
            System.out.println("Written cache");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

   
}
