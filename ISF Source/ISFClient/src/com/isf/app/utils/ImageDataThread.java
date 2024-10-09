package com.isf.app.utils;

import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import java.io.StringReader;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class ImageDataThread extends Thread {

    String xml;
    String range;
    private CacheManagerObject cmo;
    Logger log = LogManager.getLogger(com.isf.app.utils.ImageDataThread.class);

    public ImageDataThread(String s, String s1, ThreadGroup threadgroup, String s2) {
        super(threadgroup, s2);
        xml = "";
        xml = s;
        range = s1;
        cmo = CacheManagerObject.getInstance();
    }

    public void run() {
        //System.out.println("Thread Started at Image Results Panel");
        // RequestDispatcherObject requestdispatcherobject = new RequestDispatcherObject();
        String s = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(xml);
        MetaDataXmlConverter metadataxmlconverter = new MetaDataXmlConverter();
        //SAXParser saxparser = new SAXParser();
       // saxparser.setContentHandler(metadataxmlconverter);
        InputSource inputsource = new InputSource(new StringReader(s));
        try {
            SAXParser saxparser = SAXParserFactory.newInstance().newSAXParser();
            saxparser.parse(inputsource,metadataxmlconverter);
        } catch (Exception exception) {
            log.error(exception);
        }
        Vector vector = (Vector) metadataxmlconverter.getDataStructure();
        cmo.storeToCache(new CacheEntry(range + ".s6"), vector);
    }
}
