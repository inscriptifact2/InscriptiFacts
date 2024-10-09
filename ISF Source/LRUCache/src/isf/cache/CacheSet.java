package isf.cache;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: IN030223
 * Date: Aug 25, 2007
 * Time: 10:15:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class CacheSet extends TreeSet {
    public CacheSet() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CacheSet(Comparator comparator) {
        super(comparator);   
        
        //To change body of overridden methods use File | Settings | File Templates.
    }

    public boolean contains(Object o) {
        Iterator itr = iterator();
        boolean found = false;
        while (itr.hasNext()) {
            CacheItem item = (CacheItem) itr.next();
            if (item != null) {
                if (o.toString().equalsIgnoreCase(item.getName())) {
                    found = true;
                    break;
                }
            }

        }
        return found;
    }

    public CacheSet(Collection collection) {
        super(new CacheItemComparator());
        addAll(collection);
        //  super(collection);    //To change body of overridden methods use File | Settings | File Templates.

    }

    public Object get(Object o) {

        Iterator itr = iterator();
        Object found = null;
        while (itr.hasNext()) {
            CacheItem item = (CacheItem) itr.next();
            if (item.getName().equalsIgnoreCase(o.toString())) {
                found = item;
                break;
            }
        }
        return found;
    }

    public boolean add(Object o) {
        //System.out.println(" Calling This:" + o);
        if (contains(((CacheItem) o).getName())) {
            remove(o);
           // System.out.println(" Calling This: removing");
        }
        return super.add((CacheItem) o);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
