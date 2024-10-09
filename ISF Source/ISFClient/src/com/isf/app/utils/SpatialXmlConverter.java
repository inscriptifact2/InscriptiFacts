package com.isf.app.utils;

import com.isf.app.models.SpatialListImageObject;
import isf.common.ISFBase64;
import isf.common.utils.LogManager;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import org.xml.sax.*;

public class SpatialXmlConverter extends XmlConverter
        implements ContentHandler {

    Vector main;
    String photoid;
    String textview;
    String mainpubno;
    String textdestitle;
    byte data[];
    private String errMsg, sccMsg;
    Logger logger = LogManager.getLogger(com.isf.app.utils.SpatialXmlConverter.class);

    public SpatialXmlConverter() {
        main = null;
        photoid = "";
        textview = "";
        mainpubno = "";
        textdestitle = "";
        data = null;
        main = new Vector();
        errMsg = "";
        sccMsg = "";
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
            if (s2.equals("ERRMSG")) {
                errMsg = attributes.getValue("MSG");
            }
            if (s2.equals("SUCCESSMSG")) {
                sccMsg = attributes.getValue("MSG");
            }


            if (s2.equals("RECORD")) {
                photoid = "";
                textview = "";
                mainpubno = "";
                textdestitle = "";
                data = null;
            } else if (s2.equals("RECORDVALUES")) {
                int i = attributes.getLength();
                String s4 = attributes.getValue("KEY");
                if (s4.equals("PHOTOGRAPHIDENTIFICATIONNO")) {
                    photoid = attributes.getValue("VALUE");
                } else if (s4.equals("TEXTVIEW")) {
                    textview = attributes.getValue("VALUE");
                } else if (s4.equals("TEXTDESCRIPTIVETITLE")) {
                    textdestitle = attributes.getValue("VALUE");
                } else if (s4.equals("MAINTEXTORPUBLCNNO")) {
                    mainpubno = attributes.getValue("VALUE");
                }
            } else if (s2.equals("IMAGE_DATA")) {
                String s3 = attributes.getValue("DATA");
                data = ISFBase64.decode(s3);
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    public void endElement(String s, String s1, String s2)
            throws SAXException {
        if (s2.equals("RECORD")) {
            SpatialListImageObject spatiallistimageobject = new com.isf.app.models.SpatialListImageObject();
            spatiallistimageobject.setIndexMapName(photoid);
            spatiallistimageobject.setTextView(textview);
            spatiallistimageobject.setTextViewImage(new ImageIcon(data));
            spatiallistimageobject.setTitle(textdestitle);
            main.addElement(spatiallistimageobject);
        }
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
