package isf.common.xml;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package com.isf.xml:
//            QueryCondition, Clause

public class Query
    implements Serializable
{

    private Vector q;
    private String type;
    private String mode;
    private String sessionid;

    public Query()
    {
        q = new Vector();
    }

    public Query(String s, String s1)
    {
        q = new Vector();
        type = s;
        mode = s1;
        sessionid = "";
    }

    public Query(String s, String s1, String s2)
    {
        q = new Vector();
        type = s;
        mode = s1;
        sessionid = s2;
    }

    public void add(QueryCondition querycondition)
    {
        q.addElement(querycondition);
    }

    public void setQueryConditions(Vector vector)
    {
        q = vector;
    }

    public Vector getQueryConditions()
    {
        return q;
    }

    public void setType(String s)
    {
        type = s;
    }

    public String getType()
    {
        return type;
    }

    public void setMode(String s)
    {
        mode = s;
    }

    public String getMode()
    {
        return mode;
    }

    public void setSessionid(String s)
    {
        sessionid = s;
    }

    public String getSessionid()
    {
        return sessionid;
    }

    public String getXML()
    {
        String s = "<QUERY TYPE='" + type + "' MODE='" + mode + "' SESSIONID='" + sessionid + "'>";
        int i = 0;
        for(int j = q.size(); i < j; i++)
        {
            s = s + ((QueryCondition)q.elementAt(i)).getXML();
        }

        s = s + "</QUERY>";
        return s;
    }
/*
    PSVM(String args[])
    {
        Query query = new Query("Meta Data", "next");
        Clause clause = new Clause("AND");
        QueryCondition querycondition = new QueryCondition("language qc1", "hindi", "=", "OR");
        QueryCondition querycondition1 = new QueryCondition("language qc2", "hindi", "=", "OR");
        QueryCondition querycondition2 = new QueryCondition("language qc3", "hindi", "=", "OR");
        querycondition.addQueryCondition(querycondition1);
        querycondition1.addQueryCondition(querycondition2);
        clause.addQueryCondition(querycondition);
        query.add(clause);
        clause = new Clause("AND");
        querycondition = new QueryCondition("language qc4", "hindi", "=", "OR");
        querycondition1 = new QueryCondition("language qc5", "hindi", "=", "OR");
        querycondition2 = new QueryCondition("language qc6", "hindi", "=", "OR");
        querycondition.addQueryCondition(querycondition1);
        querycondition1.addQueryCondition(querycondition2);
        clause.addQueryCondition(querycondition);
        clause.addQueryCondition(new QueryCondition("language qc4", "hindi", "=", "OR"));
        query.add(clause);
    }
*/
}
