package com.isf.app.utils;

import com.isf.app.exception.NoDataFoundException;
import com.isf.app.models.CatalougeTextDataObject;
import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class CatalougeDataManager {

    private Vector data;
    private String range;
    private Vector rangeVector;
    private Vector isfNos;
    //private Vector actualRan;
    private CacheManagerObject cmo;
    //private boolean flag;
    public ThreadGroup tg;
    private int count;
    Logger logger = LogManager.getLogger(com.isf.app.utils.CatalougeDataManager.class);

    public CatalougeDataManager() {
        range = "";
        // flag = false;
        count = 0;
        cmo = CacheManagerObject.getInstance();
        tg = new ThreadGroup("IDPCache");
    }

    public CatalougeDataManager(Vector vector) {
        range = "";
        //  flag = false;
        count = 0;
        isfNos = vector;
        // System.out.println("isfnos(vectro)*************************************"+isfNos);
        rangeVector = getRanges();
        // initActualRange();
        cmo = CacheManagerObject.getInstance();
        tg = new ThreadGroup("IDPCache");
    }

    public void setRange(String s) {
        range = s;
    }
/*
    private void initActualRange()
    {
        actualRan = new Vector();
    }*/



    public Vector getRangeData(String s) //throws NoDataFoundException
    {
        Vector vector = new Vector();
        setRange(s);
        // setActualRangeData(s);

        obtainCurrentData(s);

        /* StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
         int i = Integer.parseInt(stringtokenizer.nextToken());
         int j = Integer.parseInt(stringtokenizer.nextToken());     */
        int k = 0;
        for (int l = data.size(); k < l; k++) {
            Hashtable hashtable = (Hashtable) data.elementAt(k);
            CatalougeTextDataObject catalougetextdataobject = new CatalougeTextDataObject(15, 30);
            String strTemp = (String) hashtable.get("MAINTEXTORPUBLCNNO");
            if (strTemp == null)
                strTemp = (String) hashtable.get("ISFASSIGNEDTEXTNO");
            if (hashtable.get("TEXTDESCRIPTIVETITLE") == null)
                hashtable.put("TEXTDESCRIPTIVETITLE", "");

            catalougetextdataobject.setValue(strTemp, (String) hashtable.get("TEXTDESCRIPTIVETITLE"));
            catalougetextdataobject.setDisplayType((String) hashtable.get("TEXTSEARCHANDDISPLAYCODE"));
            catalougetextdataobject.setTextCode((String) hashtable.get("ISFASSIGNEDTEXTNO"));
            vector.addElement(catalougetextdataobject);
        }

        buildCacheData(getPrevious(s));
        buildCacheData(getNext(s));


        return vector;
    }


    /**
     * ************* fetch current vector and cache next and previous range **************
     */

    private void obtainCurrentData(String s) //throws NoDataFoundException
    {
        if (cmo.isCached(new CacheEntry(s + ".s2"))) {
            setData((Vector) cmo.retrieveFromCache(new CacheEntry(s + ".s2")));

        } else {
            buildData(s);


        }
        if (data == null) {
            buildData(s);
        }
        cmo.storeToCache(new CacheEntry(s + ".s2"), data);


    }


    /**
     * ***********fetch current data from database and then cache it*********************
     */


    private void buildData(String s) {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
        int i = Integer.parseInt(stringtokenizer.nextToken());
        int j = Integer.parseInt(stringtokenizer.nextToken());
        String s1 = "<QUERY TYPE='textmetadata' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>";
        for (int k = i; k <= j; k++) {
            s1 = s1 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfnumber' VALUE='" + (String) isfNos.elementAt(k - 1) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
        }

        s1 = s1 + "</QUERY>";
        // RequestDispatcherObject requestdispatcherobject = new RequestDispatcherObject();
        String s2 = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(s1);

        MetaDataXmlConverter metadataxmlconverter = new MetaDataXmlConverter();
        
        //saxparser..setContentHandler(metadataxmlconverter);
        InputSource inputsource = new InputSource(new StringReader(s2));
        try {
            SAXParser saxparser = SAXParserFactory.newInstance().newSAXParser();
            saxparser.parse(inputsource,metadataxmlconverter);
        } catch (SAXParseException saxparseexception) {

            logger.error(saxparseexception);
        } catch (Exception exception) {
           logger.error( exception);
        }
        Vector vector = (Vector) metadataxmlconverter.getDataStructure();
        //  System.out.println("vector from cdm in buildData________________________________"+vector);
        setData(vector);
    }


    private void buildCacheData(String s) //throws NoDataFoundException
    {
        CatalougeObserver.updateCount(true);

        if (s.equalsIgnoreCase("First") || s.equalsIgnoreCase("Last")) {
            CatalougeObserver.updateCount(false);

            return;
        }
        if (cmo.isCached(new CacheEntry(s + ".s2"))) {
            CatalougeObserver.updateCount(false);

            return;
        }
        StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
        int i = Integer.parseInt(stringtokenizer.nextToken());
        int j = Integer.parseInt(stringtokenizer.nextToken());
        String s1 = "<QUERY TYPE='textmetadata' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>";
        for (int k = i; k <= j; k++) {
            s1 = s1 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfnumber' VALUE='" + (String) isfNos.elementAt(k - 1) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
        }

        s1 = s1 + "</QUERY>";
        CatalougeDataThread catalougedatathread = new CatalougeDataThread(s1, s, tg, "" + count++);
        //catalougedatathread.start();
        new Thread(tg, catalougedatathread).start();
        //}
    }


    /**
     * ******** method t oassigne ranges to the anchor buttons*****************
     */

    public Vector getRanges() {
        Vector vector = new Vector();
        int i = isfNos.size();
        for (int j = 0; j < i; j += 25) {
            if (j + 25 >= i) {
                vector.addElement((j + 1) + "-" + i);
            } else {
                vector.addElement((j + 1) + "-" + (j + 25));
            }
        }

        return vector;
    }


    public Vector getNextRangeData() throws NoDataFoundException {
        String s = getNext(range);
        if (s.equalsIgnoreCase("Last") || s.equalsIgnoreCase("First"))
            throw new NoDataFoundException("You are already viewing the " + s + " Set of records.");
        else
            return getRangeData(s);

    }

    public Vector getPrevRangeData() throws NoDataFoundException {
        String s = getPrevious(range);
        if (s.equalsIgnoreCase("Last") || s.equalsIgnoreCase("First"))
            throw new NoDataFoundException("You are already viewing the " + s + " Set of records.");
        else
            return getRangeData(s);

    }


    /**
     * ******methods to get next range string or previous range string ************
     */

    private String getPrevious(String s) {
        int i = rangeVector.indexOf(s);
        //System.out.println("Prevois: "+i);
        if (i < 1)
            return "First";
        else
            return (String) rangeVector.elementAt(i - 1);
    }

    private String getNext(String s) {
        int i = rangeVector.indexOf(s);
        //System.out.println("Next: "+i);

        if ((i + 1) == rangeVector.size())
            return "Last";
        else
            return (String) rangeVector.elementAt(i + 1);
    }


    public void setIsfAssignedTextData(Vector vector) {
        isfNos = vector;
        rangeVector = getRanges();
        // initActualRange();
    }

    public void setData(Vector vector) {
        data = vector;
        //    System.out.println("  data  cdm--------------------------------------"+data);
    }

    public Vector getData() {
        return (Vector) data.clone();
    }

    /* PSVM(String args[])
         throws Exception
     {
         String s = "select distinct isfassignedtextno from textobject_ov";
         Vector vector = new Vector();
         vector.addElement("ISF_TXT_00270");
         vector.addElement("ISF_TXT_00271");
         vector.addElement("ISF_TXT_00272");
         vector.addElement("ISF_TXT_00273");
         vector.addElement("ISF_TXT_00274");
         vector.addElement("ISF_TXT_00275");
         vector.addElement("ISF_TXT_00276");
         //CatalougeDataManager catalougedatamanager = new CatalougeDataManager(vector);
     }*/


}
