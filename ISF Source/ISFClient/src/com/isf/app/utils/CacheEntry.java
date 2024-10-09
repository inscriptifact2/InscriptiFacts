package com.isf.app.utils;

import isf.common.utils.CacheManagerConstants;
import java.io.File;
import java.io.Serializable;

public class CacheEntry
        implements Serializable {

    public int accessNo;
    public String fileName;

    public CacheEntry(String s) {
        accessNo = 1;
        fileName = s;
    }

    public void setLastAccessedDate() {
        File file = new File(CacheManagerConstants.folderName+File.separator +fileName);
        file.setLastModified(System.currentTimeMillis());
    }

    public void increment() {
        accessNo++;
    }

    public int getAccessNo() {
        return accessNo;
    }

    public int hashCode() {
        return fileName.hashCode();
    }

    public boolean equals(Object obj) {
        return fileName.equals(obj.toString());
    }

    public String toString() {
        return fileName;
    }

   
}
