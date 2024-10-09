package isf.common;

import isf.common.jro.JavaRequestObject;
import java.io.ByteArrayInputStream;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

// Referenced classes of package com.isf.common:
//            ISFRequestDocHandler, Debug

public class XMLProcessorObject
{
    Logger log = LogManager.getLogger(isf.common.XMLProcessorObject.class);
    public XMLProcessorObject()
    {
    }

    public JavaRequestObject getJavaRequest(String s)
    {
        JavaRequestObject javarequestobject = null;
        try
        {
           XMLReader reader = XMLReaderFactory.createXMLReader();
           // SAXParser saxparser = new SAXParser();
            ISFRequestDocHandler isfrequestdochandler = new ISFRequestDocHandler();
          //  saxparser.setDocumentHandler(isfrequestdochandler);
            reader.setContentHandler(isfrequestdochandler);
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
            InputSource inputsource = new InputSource(bytearrayinputstream);
           // XMLInputSource ip = new XMLInputSource()
           // saxparser.parse(inputsource);
           // saxparser.parse(ip);
            reader.parse(inputsource);
            Debug.debug("isValid from XMLProcessorObject :");
            javarequestobject = isfrequestdochandler.returnRequestObject();
            Debug.debug("jreq from XMLProcessorObject :" + javarequestobject);
        }
        catch(Exception exception)
        {
            log.error(exception);
        }
        finally
        {
            return javarequestobject;
        }
    }
}
