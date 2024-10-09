package com.isf.app.utils;

import com.isf.app.models.TextPublicationObject;
import com.isf.app.models.TreeParent;
import isf.common.utils.LogManager;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.xml.sax.*;

public class InitializationXmlConverter extends XmlConverter
        implements ContentHandler {

    Vector textpub;
    TreeParent top;
    Vector root;
    Logger logger = LogManager.getLogger(com.isf.app.utils.InitializationXmlConverter.class);

    String name[] = {
        "CORPUSNAME", "MEDIUM", "ISFFINDSITE", "TIMELINENAME", "LANGUAGE", "SCRIPT", "LOCATIONOFORIGINAL"
    };
    TreeParent tp[] = {
        new TreeParent("Corpus"), new TreeParent("Medium"), new TreeParent("Find Site"), new TreeParent("Time Period"), new TreeParent("Language"), new TreeParent("Script"), new TreeParent("Repository")
    };
    int c;
    String a[] = {
        null, null, null
    };
    TreeParent temp;
    public String session;
    private String errMsg, sccMsg;

    public InitializationXmlConverter() {
        textpub = null;
        top = null;
        root = null;
        c = 0;
        temp = null;
        errMsg = "";
        sccMsg = "";
        session = "";
        textpub = new Vector();
        top = new TreeParent();
    }

    public Object getDataStructure() {
        //System.out.println("initdata  from initxmlconvrt : "+root);
        return root;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getSccMsg() {
        return sccMsg;
    }


    public String getSession() {
        return session;
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument()
            throws SAXException {
    }

    public void endDocument()
            throws SAXException {
        root = new Vector();
        for (int i = 0; i < tp.length; i++) {
            top.addChild(tp[i]);
        }

        root.addElement(top);
        root.addElement(textpub);
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
            throws SAXException {
        try {
            if (s2.equals("RESPONSE")) {
                session = attributes.getValue("SESSIONID").equals("null") ? "" : attributes.getValue("SESSIONID");
            }
            if (s2.equals("ERRMSG")) {
                errMsg = attributes.getValue("MSG");
            }
            if (s2.equals("SUCCESSMSG")) {
                sccMsg = attributes.getValue("MSG");
            }
            if (s2.equals("RECORD")) {
                c = 0;
                a[0] = null;
                a[1] = null;
                a[2] = null;
                temp = null;
            } else if (s2.equals("RECORDVALUES")) {
                int i = attributes.getLength();
                String s3 = attributes.getValue("KEY");
                if (s3.equals("MAINTEXTORPUBLCNNOPREFIX")) {
                    TextPublicationObject textpublicationobject = new TextPublicationObject(attributes.getValue("VALUE"), "M");
                    textpub.addElement(textpublicationobject);
                    return;
                }
                if (s3.equals("ALTTEXTORPUBLCNNOPREFIX")) {
                    TextPublicationObject textpublicationobject1 = new TextPublicationObject(attributes.getValue("VALUE"), "A");
                    textpub.addElement(textpublicationobject1);
                    return;
                }
                int j = 0;
                for (int k = name.length; j < k; j++) {
                    if (s3.equals(name[j])) {
                        temp = tp[j];
                    }
                }

                for (int l = 0; l < i; l++) {
                    String s4 = attributes.getQName(l);
                    if (s4.equals("VALUE")) {
                        a[c] = attributes.getValue(s4).equals("null") ? null : attributes.getValue(s4);
                        c++;
                    }
                }

            } else if (!s2.equals("RECORD_PROP")) ;
        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    public void endElement(String s, String s1, String s2)
            throws SAXException {
        if (s2.equals("RECORD") && temp != null) {
            temp.addPath(a);
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
        // System.out.println("   " + saxparseexception.getMessage());
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
