package isf.cache;



import isf.common.utils.LogManager;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 8, 2007
 * Time: 7:52:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class LRUCache implements Serializable {
    Logger log = null; 
    private static final long serialVersionUID = 3674312987828041877L;

    private double fileSize;
    // private Map cache;
    private SortedSet cache; // = Collections.synchronizedSortedSet(new TreeSet(...));
    private boolean isMonitoring;
    double cacheSize = 1024;
    //Object mutex = new Object();

    public LRUCache() {
        log = LogManager.getLogger(isf.cache.LRUCache.class);
        cache = Collections.synchronizedSortedSet(new CacheSet(new CacheItemComparator()));
       // setLimit("2GB");
    }
    
  
   
   

    public void createSynchronizedMap(CacheSet set) {
        try {
            if (set == null)
                cache = Collections.synchronizedSortedSet(new CacheSet(new CacheItemComparator()));
            else
                cache = Collections.synchronizedSortedSet(set);
           // setLimit("2GB");
        } catch (Exception exp) {
           // exp.printStackTrace();
            log.error(exp);
        }
    }


    public void setLimit(String size) {
      
        if (size.endsWith("GB"))
            cacheSize = Integer.parseInt(size.substring(0, size.length() - 2)) * 1024;
        else if (size.endsWith("MB"))
            cacheSize = Integer.parseInt(size.substring(0, size.length() - 2));
        else if (size.endsWith("KB"))
            cacheSize = Integer.parseInt(size.substring(0, size.length() - 2)) / 1024;
    }

    public void updateEntry(String path, double size) {
        CacheItem item;
        // if (cache.contains(path) ) {
        boolean found = false;
        Iterator itr = cache.iterator();
        while (itr.hasNext()) {
            item = (CacheItem) itr.next();
            if (item != null) {
                if (path.equalsIgnoreCase(item.getName())) {
                    item.setSize(size);
                    found = true;
                    break;
                }
            }
        }

        /* item = (CacheItem) cache.get(path);
         item.setSize(size);
         cache.add( item);*/
        // }
        fileSize += size;
//        log.debug("Cached PTM with Size:" + fileSize);
        if (!isMonitoring)
            startMonitor();

    }
    
    public void deleteEntry(String path)
    {
         CacheItem item = null;
        boolean found = false;
        Iterator itr = cache.iterator();

        try {
            while (itr.hasNext()) {
                item = (CacheItem) itr.next();
                if (item.getName().equalsIgnoreCase(path)) {
                    found = true;
                    break;
                }
            }
        }catch(Exception ie){
          log.error(ie);
        }
        if(found)
            cache.remove(item);
    }

    public void updateEntry(String path) {
        CacheItem item;

        boolean found = false;
        Iterator itr = cache.iterator();

        try {
            while (itr.hasNext()) {
                item = (CacheItem) itr.next();
                if (item.getName().equalsIgnoreCase(path)) {
                    item.setLastAccessedTime(System.currentTimeMillis());
                    found = true;
                    break;
                }
            }
            if (found == false) {
                item = new CacheItem(path);
                item.setLastAccessedTime(System.currentTimeMillis());
                cache.add(item);
               // log.debug("Found Entry:" + path);
            }
            if (!isMonitoring)
                startMonitor();
        } catch (ClassCastException cce) {
            // System.out.println(itr.next() );
            //cce.printStackTrace();
            log.error(cce);
        }


    }

    private void trimDirectory(String dirName) {
        File f = new File(dirName);
        if (f!=null && f.list()!=null && f.list().length > 0) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory())
                    trimDirectory(files[i].getAbsolutePath());
                else if (files[i].canWrite()) ;
                files[i].delete();
            }
        }
        log.debug("Deleting directory:" + dirName);
        f.delete();


    }
    
   public void addItem(CacheItem item)
   {
              CacheItem elem;
              String path = item.getName();

        boolean found = false;
        Iterator itr = cache.iterator();

        try {
            while (itr.hasNext()) {
                elem = (CacheItem) itr.next();
                if (elem.getName().equalsIgnoreCase(path)) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                 cache.add(item);
               // log.debug("Found Entry:" + path);
            }
            
        } catch (ClassCastException cce) {
            // System.out.println(itr.next() );
            //cce.printStackTrace();
            log.error(cce);
        } 
   }
    public void startMonitor() {
        isMonitoring = true;
        Runnable r = new Runnable() {
            public void run() {

                while (fileSize > cacheSize && cache.size() >1) {
                     // String key = (String) (cache.keySet().toArray()[0]);
                      CacheItem item = (CacheItem)cache.first();
                      cache.remove( item);
                      fileSize -= item.getSize();
                      trimDirectory(item.getName() );

                  }
                try{
                Thread.sleep(300000);
                }catch(InterruptedException ie)
                {
                    
                }

                isMonitoring = false;
            }

        };
        
        Thread t = new Thread(r);
        t.start();

    }


    public void stopMonitoring() {
        isMonitoring = false;
    }

    public boolean isCached(String path) {
        //  log.debug("Cache Entry Found:" + path);
        boolean found = cache.contains(path);
        //  log.debug("Cache Entry Found:" + path + "::" + found);
        return found;
    }


    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(new ArrayList(cache));
        out.flush();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        ArrayList objects = (ArrayList) in.readObject();
        cache = new CacheSet(objects);
        setLimit("2GB");
    }

    public String dumpValues() {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = cache.iterator();
        while (iter.hasNext()) {
            CacheItem item = (CacheItem) (iter.next());
            if (item != null)
                buffer.append(item.getName() + "::" + item.getLastAccessedTime() + "::" + item.getSize() + "<br>");
        }
        return buffer.toString();
    }





}

