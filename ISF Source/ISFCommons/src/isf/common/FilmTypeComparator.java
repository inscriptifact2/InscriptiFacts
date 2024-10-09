package isf.common;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Hashtable;

public class FilmTypeComparator
    implements Comparator, Serializable
{

    public FilmTypeComparator()
    {
    }

    public int compare(Object obj, Object obj1)
    {
        if(!check(obj, obj1))
        {
            return -1;
        }
        int i;
        try
        {
            Integer integer = new Integer((String)((Hashtable)obj).get("FILMTYPECODE"));
            Integer integer1 = new Integer((String)((Hashtable)obj1).get("FILMTYPECODE"));
            i = integer.compareTo(integer1);
        }
        catch(Exception exception1)
        {
            return -1;
        }
        return i == 0 ? -1 : i;
    }

    private boolean check(Object obj, Object obj1)
    {
        if(obj == null)
        {
            return false;
        } else
        {
            return obj1 != null;
        }
    }

    public boolean equals(Object obj)
    {
        return false;
    }
}
