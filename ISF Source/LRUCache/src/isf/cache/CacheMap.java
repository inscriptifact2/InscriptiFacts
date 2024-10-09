package isf.cache;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * *
 */

public class CacheMap extends TreeMap implements Serializable {
    Comparator comparer = null;

    public CacheMap() {
        super();
    }

    public CacheMap(Comparator c) {
        super();
        comparer = c;
    }


    public Object put(Object o, Object o1) {

        Object added = super.put(o, o1);
        //sort();
        return added;
    }

    public Object remove(Object o) {
        CacheItem item = (CacheItem) super.remove(o);
        return item;
    }


}
