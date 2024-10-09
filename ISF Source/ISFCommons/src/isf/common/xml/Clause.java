package isf.common.xml;

import java.io.Serializable;
import java.util.Vector;

// Referenced classes of package com.isf.xml:
//            QueryCondition

public class Clause extends QueryCondition
    implements Serializable
{

    private Vector clausesVector;

    public Clause(String s)
    {
        clausesVector = new Vector();
        super.connector = s;
    }

    public void addClause(Clause clause)
    {
        clausesVector.addElement(clause);
    }

    public Vector getClauses()
    {
        return clausesVector;
    }

    public void addQueryCondition(QueryCondition querycondition)
    {
        super.queryConditions.addElement(querycondition);
    }

    public String getXML()
    {
        String s = "<CLAUSE CONNECTOR='" + super.connector + "'>";
        int i = 0;
        for(int j = clausesVector.size(); i < j; i++)
        {
            s = s + ((Clause)clausesVector.elementAt(i)).getXML();
        }

        int k = 0;
        for(int l = super.queryConditions.size(); k < l; k++)
        {
            s = s + ((QueryCondition)super.queryConditions.elementAt(k)).getXML();
        }

        s = s + "</CLAUSE>";
        return s;
    }
}
