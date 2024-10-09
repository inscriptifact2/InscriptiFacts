package com.isf.app.utils;

import com.isf.app.models.TreeChild;
import com.isf.app.models.TreeParent;
import isf.common.utils.LogManager;
import isf.common.view.gui.TextDivision;
import isf.common.view.gui.TextDivisionSubDivision;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.xml.sax.*;

public class TextDivisionXmlConverter extends XmlConverter
        implements ContentHandler {

    Vector main;
    Hashtable div;
    String sscode;
    String textdivname;
    String textdivsubdiv;
    String mtpno;
    String textdesctitle;
    String isfAssignedTextNo;
    String textDescription;
    String textMuseumNo;
    Vector result;
    Vector textdivVector;
    String topTitle;
    private String sccMsg, errMsg;
    Logger logger = LogManager.getLogger(com.isf.app.utils.TextDivisionXmlConverter.class);

    public TextDivisionXmlConverter() {
        main = null;
        div = null;
        sscode = "";
        textdivname = "";
        textdivsubdiv = "";
        mtpno = "";
        textdesctitle = "";
        isfAssignedTextNo = "";
        textDescription = "";
        textMuseumNo = "";
        result = new Vector();
        textdivVector = new Vector();
        topTitle = "";
        sccMsg = "";
        errMsg = "";
        main = new Vector();
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getSccMsg() {
        return sccMsg;
    }

    public Object getDataStructure() {
        return main;
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument()
            throws SAXException {
        div = new Hashtable();
    }

    public void endDocument()
            throws SAXException {
        Vector vector = new Vector();
        int i = textdivVector.size();
        for (int j = 0; j < i; j++) {
            TextDivision textdivision = (TextDivision) textdivVector.elementAt(j);
            TreeParent treeparent = new TreeParent(textdivision);
            Vector vector1 = textdivision.getTextDivisionSubDivisions();
            int k = vector1.size();
            for (int l = 0; l < k; l++) {
                TextDivisionSubDivision textdivisionsubdivision = (TextDivisionSubDivision) vector1.elementAt(l);
                String s = textdivisionsubdivision.getTextDivisionSubDivisionName();
                if (isValid(s)) {
                    treeparent.addChild(new TreeChild(textdivisionsubdivision));
                }
            }

            vector.addElement(treeparent);
        }

        main.addElement(vector);
        main.addElement(topTitle);
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
            if (s2.equals("RECORD")) {
                textdivname = "";
                textdivsubdiv = "";
                mtpno = "";
                textdesctitle = "";
                isfAssignedTextNo = "";
                textDescription = "";
                textMuseumNo = "";
            } else if (s2.equals("RECORDVALUES")) {
                int i = attributes.getLength();
                String s3 = attributes.getValue("KEY");
                if (s3.equals("SPATIALSEARCHCODE")) {
                    sscode = attributes.getValue("VALUE");
                } else if (s3.equals("TEXTDIVISION")) {
                    textdivname = attributes.getValue("VALUE");
                } else if (s3.equals("TEXTDIVISIONSUBDIVISION")) {
                    textdivsubdiv = attributes.getValue("VALUE");
                } else if (s3.equals("MAINTEXTORPUBLCNNO")) {
                    mtpno = attributes.getValue("VALUE");
                } else if (s3.equals("TEXTDESCRIPTIVETITLE")) {
                    textdesctitle = attributes.getValue("VALUE");
                } else if (s3.equals("ISFASSIGNEDTEXTNO")) {
                    isfAssignedTextNo = attributes.getValue("VALUE");
                }
                if (s3.equals("TEXTDIVISIONDESCRIPTION")) {
                    textDescription = attributes.getValue("VALUE");
                }
                if (s3.equals("TEXTDIVISIONMUSEUMNO")) {
                    textMuseumNo = attributes.getValue("VALUE");
                }
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    public void endElement(String s, String s1, String s2)
            throws SAXException {
        if (s2.equals("RECORD")) {
            topTitle = mtpno.trim() + " " + textdesctitle.trim();
            if (div.isEmpty() || !div.containsKey(textdivname)) {
                TextDivision textdivision = new TextDivision();
                textdivision.setSpatialSearchCode(sscode);
                textdivision.setTextDivisionName(textdivname);
                textdivision.setMainTextPubNo(mtpno);
                textdivision.setTextDescTitle(textdesctitle);
                textdivision.setTextDivisionDesc(textDescription);
                textdivision.setTextDivisionMuseumNo(textMuseumNo);
                Vector vector = new Vector();
                /* TextDivisionSubDivision textdivisionsubdivision = new TextDivisionSubDivision();
                 textdivisionsubdivision.setSpatialSearchCode(sscode);
                 textdivisionsubdivision.setTextDivisionName(textdivname);
                 textdivisionsubdivision.setTextDivisionSubDivisionName(textdivsubdiv);
                 textdivisionsubdivision.setTextDescTitle(topTitle);
                 textdivisionsubdivision.setIsfAssignedTextNo(isfAssignedTextNo);
                 vector.addElement(textdivisionsubdivision);  */
                textdivision.setTextDivisionSubDivisions(vector);
                textdivision.setIsfAssignedTextNo(isfAssignedTextNo);
                div.put(textdivname, textdivision);
                result.addElement(textdivname);
            } else {
                TextDivision textdivision1 = (TextDivision) div.get(textdivname);
                Vector vector1 = textdivision1.getTextDivisionSubDivisions();
                TextDivisionSubDivision textdivisionsubdivision1 = new TextDivisionSubDivision();
                textdivisionsubdivision1.setSpatialSearchCode(sscode);
                textdivisionsubdivision1.setTextDivisionName(textdivname);
                textdivisionsubdivision1.setTextDivisionSubDivisionName(textdivsubdiv);
                textdivisionsubdivision1.setTextDescTitle(topTitle);
                textdivisionsubdivision1.setIsfAssignedTextNo(isfAssignedTextNo);
                vector1.addElement(textdivisionsubdivision1);
                textdivision1.setTextDivisionSubDivisions(vector1);
                div.put(textdivname, textdivision1);
            }
        }
        if (s2.equals("RESPONSE")) {
            for (int i = 0; i < result.size(); i++) {
                textdivVector.addElement(div.get(result.elementAt(i)));
            }

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

    private boolean isValid(String s) {
        if (s.trim().equals("null")) {
            return false;
        }
        return s != null;
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
