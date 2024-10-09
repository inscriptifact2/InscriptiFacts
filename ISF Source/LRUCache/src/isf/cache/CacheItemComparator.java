package isf.cache;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 8, 2007
 * Time: 9:30:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class CacheItemComparator implements Comparator, Serializable {
    public int compare(Object o, Object o1) {
        int retVal = 0;
        if ((o instanceof CacheItem) && (o1 instanceof CacheItem)) {
            // System.out.println("Comparing CI");
            CacheItem one = (CacheItem) o;
            CacheItem two = (CacheItem) o1;
            retVal = (int) (one.getLastAccessedTime() - two.getLastAccessedTime());
            if (retVal == 0) {
                retVal = (int) (one.getSize() - two.getSize());
            }

        } else if ((o instanceof String) && (o1 instanceof String)) {
           // System.out.println("Comparing STRING");
            if (((String) o).equals((String) o1))
                retVal = 0;
            else
                retVal = -1;
        } else             //not CacheItem nor String
            retVal = -1;

        return retVal;
    }
}
