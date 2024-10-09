package isf.common;

import java.io.Serializable;
import java.util.Comparator;

public class TextPublicationComparator
    implements Comparator, Serializable
{

    public TextPublicationComparator()
    {
    }

    public int compare(Object obj, Object obj1)
    {
        return obj.toString().compareTo(obj1.toString());
    }

    public boolean equals(Object obj)
    {
        return false;
    }
}
