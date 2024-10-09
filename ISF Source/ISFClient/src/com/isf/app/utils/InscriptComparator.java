package com.isf.app.utils;

import isf.common.Debug;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class InscriptComparator
        implements Comparator, Serializable {

    public InscriptComparator() {
    }

    public int compare(Object obj, Object obj1) {
        CacheEntry cacheentry = (CacheEntry) obj;
        CacheEntry cacheentry1 = (CacheEntry) obj1;
        File file = new File(cacheentry.toString());
        File file1 = new File(cacheentry1.toString());
        Date date = new Date(file.lastModified());
        Date date1 = new Date(file1.lastModified());
        Debug.debug("at Inscript Comparator    " + cacheentry + "   " + cacheentry1 + "  " + cacheentry.equals(cacheentry1));
        file = null;
        file1 = null;
        int i = date.compareTo(date1);
        return i != 0 ? i : -1;
    }

    public boolean equals(Object obj) {
        return true;
    }
}
