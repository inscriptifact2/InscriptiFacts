package com.isf.app.utils;

import isf.common.utils.LogManager;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.xml.sax.*;

public class GeneralXmlConverter extends XmlConverter
        implements ContentHandler {

    Vector main;
    private String errMsg;
    private String sccMsg;
    Logger logger = LogManager.getLogger(com.isf.app.utils.GeneralXmlConverter.class);

    public GeneralXmlConverter() {
        main = null;
        errMsg = "";
        sccMsg = "";
        main = new Vector();
    }

    public Object getDataStructure() {
        return main;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getSccMsg() {
        return sccMsg;
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument()
            throws SAXException {
    }

    public void endDocument()
            throws SAXException {
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
            throws SAXException {
        try {
            if (s2.equals("SUCCESSMSG")) {
                sccMsg = attributes.getValue("MSG");
            }
            if (s2.equals("ERRMSG")) {
                errMsg = attributes.getValue("MSG");
            }
            if (!s2.equals("RECORD") && s2.equals("RECORDVALUES")) {
                main.addElement(attributes.getValue("VALUE"));
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    public void endElement(String s, String s1, String s2)
            throws SAXException {
    }

    public void characters(char ac[], int i, int j)
            throws SAXException {
    }

    public void ignorableWhitespace(char ac[], int i, int j)
            throws SAXException {
    }

    public void processingInstruction(String s, String s1)
            throws SAXException {
    }

    public void error(SAXParseException saxparseexception)
            throws SAXParseException {
        throw saxparseexception;
    }

    public void warning(SAXParseException saxparseexception)
            throws SAXParseException {
       logger.debug("** Warning, line " + saxparseexception.getLineNumber() + ", uri " + saxparseexception.getSystemId());
        //System.out.println("   " + saxparseexception.getMessage());
    }

    public void endPrefixMapping(String s) {
    }

    public void startPrefixMapping(String s, String s1) {
    }

    public void skippedEntity(String s) {
    }

  

    @Override
    public String getCount() {
       return "";
    }

    @Override
    public String getListKeys() {
        return "";
    }
}
