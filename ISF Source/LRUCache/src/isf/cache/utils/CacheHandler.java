/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 16, 2007
 * Time: 7:21:00 PM
 * To change this template use File | Settings | File Templates.
 */
package isf.cache.utils;

import isf.cache.LRUCache;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public static void main(String args[]) {
        LRUCache cache = null;
        String cacheDirectory="C:\\Personal\\ISF\\";
        String str= "/var/lizardtech/images/PTMs/cache/";
        boolean error = false;
         
        try {
           // cacheDirectory = "C:\\Personal\\ISF";
            FileInputStream fis = new FileInputStream(cacheDirectory + "lruCache_rti.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cache = (LRUCache) ois.readObject();
            ois.close();
            fis.close();
       //     System.out.println("Read Cache");
        } catch (FileNotFoundException fnfe) {
            error= true;
        //    System.out.println("Here FileNotFound");
            cache = new LRUCache();
            cache.createSynchronizedMap(null);
        } catch (Exception ioe) {
            error=true;
        //    System.out.println("FileIOHere");
            cache = new LRUCache();
            cache.createSynchronizedMap(null);
           // log.error(ioe);
        } finally {
            if(cache==null){
                cache = new LRUCache();
                cache.createSynchronizedMap(null);
            }
            CacheHandler.getInstance().setCache(cache);
        }
        
            FileInputStream fis = null;
        BufferedReader reader = null;
      
        try {
            fis = new FileInputStream(cacheDirectory+"filelist.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            while(line != null){
              //  System.out.println(line);
                
               if( cache.isCached(str+line)){
                   cache.deleteEntry(str+line);
                   Thread.sleep(5000);
                   System.out.println("Deleted:"+str+line);
                   
                   break;
               }else{
                   System.out.println("NotFound:"+str+line);
               }
               line = reader.readLine();
            }  
            
            
            FileOutputStream fos = new FileOutputStream(cacheDirectory +  "lruCache_rti.ser");
            ObjectOutputStream obs = new ObjectOutputStream(fos);
            obs.writeObject(CacheHandler.getInstance().getCache());
            obs.flush();
            obs.close();
            fos.close();
            //log.debug("Written cache");
        
        }catch(Exception exp){
            
        }


        
 
    }
 
}
