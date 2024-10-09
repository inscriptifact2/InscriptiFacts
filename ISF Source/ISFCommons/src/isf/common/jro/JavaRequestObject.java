package isf.common.jro;

import isf.common.xml.Query;
import java.io.Serializable;

public class JavaRequestObject
    implements Serializable
{

    protected Query query;

    public JavaRequestObject()
    {
    }

    public void setQuery(Query query1)
    {
        query = query1;
    }

    public Query getQuery()
    {
        return query;
    }

    public String getXML()
    {
        return query.getXML();
    }
}
