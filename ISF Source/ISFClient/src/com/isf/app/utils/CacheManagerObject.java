package com.isf.app.utils;

import isf.common.utils.CacheManagerConstants;
import isf.common.utils.LogManager;
import java.io.*;
import java.util.Collections;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CacheManagerObject {

    public int folderSize;
    private Vector entries;
    private CacheEntry lastEdited = null;
    private static CacheManagerObject cmo = null;
    private String ext[] = {
        ".sid", ".jpg", ".gif", ".tiff", ".tif"
    };
    Logger log = LogManager.getLogger(com.isf.app.utils.CacheManagerObject.class);

    private CacheManagerObject() {
        folderSize = 0;
        entries = new Vector();
        File file = new File(CacheManagerConstants.folderName);
        if (!file.exists()) {
            file.mkdir();
        }
        file = null;
        loadEntryTable();
    }

    public static CacheManagerObject getInstance() {
        if (cmo == null) {
            cmo = new CacheManagerObject();
        }
        return cmo;
    }

    public int getCacheSize() {
        return getFolderLength(new File(CacheManagerConstants.folderName));
    }

    private void loadEntryTable() {
        try {
            File file = new File(CacheManagerConstants.folderName+File.separator +"CacheEntriesTable.txt");
            if (!file.exists()) {
                file.createNewFile();
                updateData();
            } else {
                FileInputStream fileinputstream = new FileInputStream(file);
                ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
                entries = (Vector) objectinputstream.readObject();
                objectinputstream.close();
                fileinputstream.close();
            }
            file = null;
        } catch (Exception exception) {
             log.error(exception);
        }
    }

    private boolean isExtension(Object obj) {
        for (int i = 0; i < ext.length; i++) {
            if (obj.toString().indexOf(ext[i]) != -1) {
                return true;
            }
        }

        return false;
    }

    private int getFolderLength(File file) {
        folderSize = 0;
        File afile[] = null;
        try {
            afile = file.getCanonicalFile().listFiles();
        } catch (IOException ioexception) {
            //System.out.println(ioexception);
            log.error(ioexception);
        }
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            if (!afile[i].isDirectory()) {
                folderSize += afile[i].length();
              
            } else {
                 
                //ignore folders....
                //folderSize += getFolderLength(afile[i]);
            }
        }

        return folderSize;
    }

    public void storeToCache(Object obj, Object obj1) {
        if (isCached(obj)) {
            updateCacheEntries(obj);
        } else {
            if (getCacheSize() > 0x6400000) {
                Object obj2 = getLeastRecentlyUsedObject();
                removeFromCache(obj2);
            }
            entries.addElement(obj);
            updateLocalFile(obj, obj1);
        }
        lastEdited = (CacheEntry) obj;

        updateData();
    }

    public void storeToCache(Object obj) {
        if (isCached(obj)) {
            updateCacheEntries(obj);
        } else {
            if (getCacheSize() > 0x6400000) {
                Object obj2 = getLeastRecentlyUsedObject();
                removeFromCache(obj2);
            }
            entries.addElement(obj);
        //writeToFile(obj, obj1,pos);
        }
        lastEdited = (CacheEntry) obj;
        updateData();
    }

    public void writeToFile(Object obj, byte[] obj1, String pos) {
        //System.out.println("pos"+pos);
        try {
            File file = new File(CacheManagerConstants.folderName +File.separator + obj.toString());
            RandomAccessFile rf = new RandomAccessFile(file, "rw");
            rf.seek(new Long(pos).longValue());
            rf.write(obj1);
            rf.close();
            file = null;
        } catch (Exception e) {
            //System.out.println(e);
            log.error(e);
        }

    }

    public void updateLocalFile(Object obj, Object obj1) {
        try {
            File file = new File(CacheManagerConstants.folderName +File.separator + obj.toString());
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            if (obj1 instanceof byte[]) {
                fileoutputstream.write((byte[]) obj1);
            } else {
                ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
                objectoutputstream.writeObject(obj1);
                objectoutputstream.close();
            }
            fileoutputstream.close();
            file = null;
           // System.gc();
        } catch (Exception exception) {
            log.error(exception);
        }
    }

    private void updateData() {
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(CacheManagerConstants.folderName+File.separator +"CacheEntriesTable.txt");
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(entries);
            objectoutputstream.close();
            fileoutputstream.close();
           // System.gc();
        } catch (Exception exception) {
            log.error(exception);
        }
    }

    private void updateCacheEntries(Object obj) {
        Collections.sort(entries, new InscriptComparator());
        updateData();
    }

    public Object retrieveFromCache(Object obj) {
        if (!isCached(obj)) {
            return null;
        }
        updateCacheEntries(obj);
        if (isExtension(obj)) {
            return getFileInputStream(obj);
        } else {
            return readData(obj);
        }
    }

    private Object readData(Object obj) {
        Object obj1 = null;
        try {
            File file = new File(CacheManagerConstants.folderName +File.separator + obj.toString());
            FileInputStream fileinputstream = new FileInputStream(file);
            ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
            obj1 = objectinputstream.readObject();
            fileinputstream.close();
            objectinputstream.close();
            file = null;
          //  System.gc();
        } catch (Exception exception) {
            log.error(exception);
        }
        return obj1;
    }

    public Object getLeastRecentlyUsedObject() {
        Object obj1 = null;
        Collections.sort(entries, new InscriptComparator());
        if (entries.size() > 0) {
            obj1 = entries.elementAt(0);
        }
        return obj1;
    }

    public boolean isCached(Object obj) {

        return entries.contains(obj);
    }

    public void removeFromCache(Object obj) {
        entries.remove(obj);
        final String name = obj.toString();
        
        File file = new File(CacheManagerConstants.folderName+File.separator + name);
        if (file.exists()) {
            file.delete();
        }
        updateData();
        file = null;
         
       // System.gc();
    }

    public FileInputStream getFileInputStream(Object obj) {
        FileInputStream fileinputstream = null;
        try {
            if (!isCached(obj)) {
                entries.addElement(obj);
            }
            updateCacheEntries(obj);
            updateData();
            fileinputstream = new FileInputStream(CacheManagerConstants.folderName+File.separator + obj.toString());
        } catch (Exception exception) {
            log.error(exception);
        }
        return fileinputstream;
    }

    public String getPath(Object obj) {
        if (!isCached(obj)) {
            entries.addElement(obj);
        }
        File file = new File(CacheManagerConstants.folderName+File.separator + obj.toString());
        return file.getAbsolutePath();
    }
    
/*
    PSVM(String args[])            throws Exception {
        CacheManagerObject cachemanagerobject = getInstance();
        CacheEntry cacheentry = new CacheEntry("AT_84_P11445_R_Pc.sid");
    }
    */
    
    /* public  void  updateCache(ImageIcon ic, String pino){
    // entries.get(entries.indexOf(lastEdited))
    Vector obj = (Vector)readData(lastEdited);
    Hashtable ht=null;
    int k=0;
    for (int l = obj.size(); k < l; k++) {
    ht=(Hashtable) obj.elementAt(k);
    if(((String)ht.get("PHOTOGRAPHIDENTIFICATIONNO")).equals(pino)) {
    break;
    }
    }
    if(ht!=null)
    ht.put("IMAGETHUMBNAIL",ic);
    obj.setElementAt(ht,k);
    updateLocalFile(lastEdited,obj);
    }*/
}
