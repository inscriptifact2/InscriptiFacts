package isf.common.xml;

import java.io.Serializable;
import java.util.Vector;

public class QueryCondition
    implements Serializable
{

    protected Vector queryConditions;
    private String key;
    private String value;
    private String operator;
    protected String connector;

    public QueryCondition()
    {
        queryConditions = new Vector();
    }

    public QueryCondition(String s, String s1, String s2, String s3)
    {
        queryConditions = new Vector();
        key = s;
        value = s1;
        operator = s2;
        connector = s3;
    }

    public void addQueryCondition(QueryCondition querycondition)
    {
        queryConditions.addElement(querycondition);
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    public String getOperator()
    {
        return operator;
    }

    public String getConnector()
    {
        return connector;
    }

    public Vector getQueryConditions()
    {
        return queryConditions;
    }

    public String getXML()
    {
        String s = "<QUERY_CONDITION KEY='" + key + "' VALUE='" + value + "' OPERATOR='" + operator + "' CONNECTOR='" + connector + "'>";
        int i = 0;
        for(int j = queryConditions.size(); i < j; i++)
        {
            s = s + ((QueryCondition)queryConditions.elementAt(i)).getXML();
        }

        s = s + "</QUERY_CONDITION>";
        return s;
    }
}
