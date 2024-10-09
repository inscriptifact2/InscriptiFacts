package isf.common;

import isf.common.jro.JavaRequestObject;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import java.util.Vector;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.*;

public class ISFRequestDocHandler
    implements ContentHandler
{

    boolean boolQuery;
    boolean boolClause;
    boolean boolQC;
    String typeValue;
    String modeValue;
    String sessionValue;
    String connectorValue;
    String keyValue;
    String valueValue;
    String operatorValue;
    String connectorQCValue;
    String sqlQuery;
    String temp;
    private Query query;
    private Vector clausesVector;
    private Vector clausesFinalVector;
    private Vector qcVector;
    private Vector qcFinalVector;
    private JavaRequestObject jreq;
    private Clause clause;
    private QueryCondition qc;
    Logger log = LogManager.getLogger(isf.common.ISFRequestDocHandler.class);
    public ISFRequestDocHandler()
    {
        boolQuery = false;
        boolClause = false;
        boolQC = false;
        typeValue = "";
        modeValue = "";
        sessionValue = "";
        connectorValue = "";
        keyValue = "";
        valueValue = "";
        operatorValue = "";
        connectorQCValue = "";
        sqlQuery = "";
        temp = "";
        clausesVector = new Vector();
        clausesFinalVector = new Vector();
        qcVector = new Vector();
        qcFinalVector = new Vector();
        clause = null;
        qc = null;
    }

    public void setDocumentLocator(Locator locator1)
    {
    }

    public void startDocument()
        throws SAXException
    {
        query = new Query();
    }

    public void endDocument()
        throws SAXException
    {
        String s = "";
        try
        {
            jreq = new JavaRequestObject();
            jreq.setQuery(query);
        }
        catch(Exception exception)
        {
            log.error(exception);
        }
    }

    public void startElement(String s, AttributeList attributelist)
        throws SAXException
    {
        if(s.equals("QUERY"))
        {
            boolQuery = true;
            int i = attributelist.getLength();
            for(int l = 0; l < i; l++)
            {
                String s1 = attributelist.getName(l);
                if(s1.equals("TYPE"))
                {
                    typeValue = attributelist.getValue(s1);
                    query.setType(typeValue);
                } else
                if(s1.equals("MODE"))
                {
                    modeValue = attributelist.getValue(s1);
                    query.setMode(modeValue);
                }
                if(s1.equals("SESSIONID"))
                {
                    sessionValue = attributelist.getValue(s1);
                    query.setSessionid(sessionValue);
                }
            }

        } else
        if(s.equals("CLAUSE"))
        {
            boolClause = true;
            int j = attributelist.getLength();
            for(int i1 = 0; i1 < j; i1++)
            {
                String s2 = attributelist.getName(i1);
                if(s2.equals("CONNECTOR"))
                {
                    connectorValue = attributelist.getValue(s2);
                    clause = new Clause(connectorValue);
                    clausesVector.addElement(clause);
                }
            }

        } else
        if(s.equals("QUERY_CONDITION"))
        {
            boolQC = true;
            int k = attributelist.getLength();
            for(int j1 = 0; j1 < k; j1++)
            {
                String s3 = attributelist.getName(j1);
                if(s3.equals("KEY"))
                {
                    keyValue = attributelist.getValue(s3);
                } else
                if(s3.equals("VALUE"))
                {
                    valueValue = attributelist.getValue(s3);
                } else
                if(s3.equals("OPERATOR"))
                {
                    operatorValue = attributelist.getValue(s3);
                } else
                if(s3.equals("CONNECTOR"))
                {
                    connectorQCValue = attributelist.getValue(s3);
                }
            }

            qc = new QueryCondition(keyValue, valueValue, operatorValue, connectorQCValue);
            qcVector.addElement(qc);
        }
    }

    public void endElement(String s)
        throws SAXException
    {
        if(!s.equals("QUERY"))
        {
            if(s.equals("CLAUSE"))
            {
                if(clausesVector.size() == 1)
                {
                    Clause clause1 = (Clause)clausesVector.elementAt(0);
                    query.add(clause1);
                    clausesFinalVector.addElement(clause1);
                    clausesVector = new Vector();
                } else
                {
                    Clause clause2 = (Clause)clausesVector.lastElement();
                    int i = clausesVector.indexOf(clause2);
                    Clause clause3 = (Clause)clausesVector.elementAt(i - 1);
                    clause3.addClause(clause2);
                    clausesFinalVector.addElement(clause2);
                    clausesFinalVector.addElement(clause3);
                    clausesVector.remove(clause2);
                }
            } else
            if(s.equals("QUERY_CONDITION"))
            {
                if(qcVector.size() == 1)
                {
                    QueryCondition querycondition = (QueryCondition)qcVector.elementAt(0);
                    clause.addQueryCondition(querycondition);
                    qcFinalVector.addElement(querycondition);
                    qcVector = new Vector();
                } else
                {
                    QueryCondition querycondition1 = (QueryCondition)qcVector.lastElement();
                    int j = qcVector.indexOf(querycondition1);
                    QueryCondition querycondition2 = (QueryCondition)qcVector.elementAt(j - 1);
                    querycondition2.addQueryCondition(querycondition1);
                    qcFinalVector.addElement(querycondition1);
                    qcFinalVector.addElement(querycondition2);
                    qcVector.remove(querycondition1);
                }
            }
        }
    }

    public void characters(char ac[], int i, int j)
        throws SAXException
    {
        if(boolQuery)
        {
            boolQuery = false;
        } else
        if(boolClause)
        {
            log.debug(new String(ac, i, j));
            boolClause = false;
        } else
        if(boolQC)
        {
            boolQC = false;
        }
    }

    public void ignorableWhitespace(char ac1[], int k, int l)
        throws SAXException
    {
    }

    public void processingInstruction(String s2, String s3)
        throws SAXException
    {
    }

    public void error(SAXParseException saxparseexception)
        throws SAXParseException
    {
        throw saxparseexception;
    }

    public void warning(SAXParseException saxparseexception)
        throws SAXParseException
    {
        log.debug("** Warning, line " + saxparseexception.getLineNumber() + ", uri " + saxparseexception.getSystemId());
        log.error( saxparseexception);
    }

    public JavaRequestObject returnRequestObject()
    {
        return jreq;
    }

    public void startPrefixMapping(String arg0, String arg1) throws SAXException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void endPrefixMapping(String arg0) throws SAXException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void startElement(String arg0, String s, String arg2, Attributes attributelist) throws SAXException {
        if(s.equals("QUERY"))
        {
            boolQuery = true;
            int i = attributelist.getLength();
            for(int l = 0; l < i; l++)
            {
                String s1 = attributelist.getQName(l);
                if(s1.equals("TYPE"))
                {
                    typeValue = attributelist.getValue(s1);
                    query.setType(typeValue);
                } else
                if(s1.equals("MODE"))
                {
                    modeValue = attributelist.getValue(s1);
                    query.setMode(modeValue);
                }
                if(s1.equals("SESSIONID"))
                {
                    sessionValue = attributelist.getValue(s1);
                    query.setSessionid(sessionValue);
                }
            }

        } else
        if(s.equals("CLAUSE"))
        {
            boolClause = true;
            int j = attributelist.getLength();
            for(int i1 = 0; i1 < j; i1++)
            {
                String s2 = attributelist.getQName(i1);
                if(s2.equals("CONNECTOR"))
                {
                    connectorValue = attributelist.getValue(s2);
                    clause = new Clause(connectorValue);
                    clausesVector.addElement(clause);
                }
            }

        } else
        if(s.equals("QUERY_CONDITION"))
        {
            boolQC = true;
            int k = attributelist.getLength();
            for(int j1 = 0; j1 < k; j1++)
            {
                String s3 = attributelist.getQName(j1);
                if(s3.equals("KEY"))
                {
                    keyValue = attributelist.getValue(s3);
                } else
                if(s3.equals("VALUE"))
                {
                    valueValue = attributelist.getValue(s3);
                } else
                if(s3.equals("OPERATOR"))
                {
                    operatorValue = attributelist.getValue(s3);
                } else
                if(s3.equals("CONNECTOR"))
                {
                    connectorQCValue = attributelist.getValue(s3);
                }
            }

            qc = new QueryCondition(keyValue, valueValue, operatorValue, connectorQCValue);
            qcVector.addElement(qc);
        }
    }

    public void endElement(String arg0, String s, String arg2) throws SAXException {
        if(!s.equals("QUERY"))
        {
            if(s.equals("CLAUSE"))
            {
                if(clausesVector.size() == 1)
                {
                    Clause clause1 = (Clause)clausesVector.elementAt(0);
                    query.add(clause1);
                    clausesFinalVector.addElement(clause1);
                    clausesVector = new Vector();
                } else
                {
                    Clause clause2 = (Clause)clausesVector.lastElement();
                    int i = clausesVector.indexOf(clause2);
                    Clause clause3 = (Clause)clausesVector.elementAt(i - 1);
                    clause3.addClause(clause2);
                    clausesFinalVector.addElement(clause2);
                    clausesFinalVector.addElement(clause3);
                    clausesVector.remove(clause2);
                }
            } else
            if(s.equals("QUERY_CONDITION"))
            {
                if(qcVector.size() == 1)
                {
                    QueryCondition querycondition = (QueryCondition)qcVector.elementAt(0);
                    clause.addQueryCondition(querycondition);
                    qcFinalVector.addElement(querycondition);
                    qcVector = new Vector();
                } else
                {
                    QueryCondition querycondition1 = (QueryCondition)qcVector.lastElement();
                    int j = qcVector.indexOf(querycondition1);
                    QueryCondition querycondition2 = (QueryCondition)qcVector.elementAt(j - 1);
                    querycondition2.addQueryCondition(querycondition1);
                    qcFinalVector.addElement(querycondition1);
                    qcFinalVector.addElement(querycondition2);
                    qcVector.remove(querycondition1);
                }
            }
        }
    }

    public void skippedEntity(String arg0) throws SAXException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
