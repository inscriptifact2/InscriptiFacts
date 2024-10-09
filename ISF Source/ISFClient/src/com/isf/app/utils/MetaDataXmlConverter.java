package com.isf.app.utils;

import isf.common.ISFBase64;
import isf.common.utils.LogManager;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import org.xml.sax.*;

public class MetaDataXmlConverter extends XmlConverter
        implements ContentHandler {

    Vector main;
    Vector va;
    Vector nt;
    Hashtable ht;
    int c;
    String ntKey;
    String vaKey;
    String img;
    private Vector vector;
    private String tempNtKey = "";
    private String sccMsg, errMsg;
    private String count, listkeys;
    Logger logger = LogManager.getLogger(com.isf.app.utils.MetaDataXmlConverter.class);

    public MetaDataXmlConverter() {
        main = null;
        va = null;
        nt = null;
        ht = null;
        c = 0;
        ntKey = null;
        vaKey = null;
        img = "";
        main = new Vector();
        vector = new Vector();
        errMsg = "";
        sccMsg = "";
        count = "";
        listkeys = "";
    }

    public Object getDataStructure() {
        return main;
    }

    public void setDocumentLocator(Locator locator) {
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getSccMsg() {
        return sccMsg;
    }

    public String getCount() {
        return count;
    }

    public String getListKeys() {
        return listkeys;
    }

    public void startDocument()
            throws SAXException {
    }

    public void endDocument()
            throws SAXException {
    }

    public void startElement(String s, String s1, String s2, Attributes attributes)
            throws SAXException {
        // System.out.println("in startelement   : "+s+"  s1  :"+s1+"   attributes  :"+attributes);
        try {
            if (s2.equals("ERRMSG")) {
                errMsg = attributes.getValue("MSG");
            }
            if (s2.equals("SUCCESSMSG")) {
                sccMsg = attributes.getValue("MSG");
            }

            if (s2.equals("RESPONSE")) {
                count = attributes.getValue("RCOUNT");
                listkeys = attributes.getValue("LIST");
            }
            if (s2.equals("RECORD")) {
                ht = new Hashtable();
                tempNtKey = "";
                img = "";
            } else if (s2.equals("RECORDVALUES")) {
                // tempNtKey="";
                String s3 = attributes.getValue("TYPE");
                //  System.out.println("-- s3  attr type-----------"+s3);
                if (s3.equals("CV")) {
                    //  System.out.println("----in CV--"+attributes.getValue("KEY")+"--val--"+attributes.getValue("VALUE"));
                    ht.put(attributes.getValue("KEY"), getVal(attributes.getValue("VALUE")));
                    c = 0;
                } else if (s3.equals("VA")) {
                    c = 1;
                    va = new Vector();
                    vaKey = attributes.getValue("KEY");
                    // System.out.println("----in VA--"+vaKey+"--val--"+va);
                    ht.put(vaKey, va);
                } else if (s3.equals("NT")) {
                    c = 2;
                    nt = new Vector();
                    ntKey = attributes.getValue("KEY");
                    // System.out.println("----in NT  tempNtKey----"+tempNtKey);
                    if (!ntKey.equals(tempNtKey)) {
                        // System.out.println("^^^^^^   in not if  ^^^^^^^^^^" );
                        vector = new Vector();
                    }

                    vector.addElement(nt);
                    // System.out.println("----in NT--"+ntKey+"--val--"+vector);
                    ht.put(ntKey, vector);
                    tempNtKey = ntKey;
                }
                if (c == 1 && !attributes.getValue("VALUE").equals("")) {
                    //  System.out.println("----in c==1--"+attributes.getValue("VALUE"));
                    va.addElement(getVal(attributes.getValue("VALUE")));
                } else if (c == 2 && !attributes.getValue("VALUE").equals("")) {
                    //  System.out.println("in c==2--"+attributes.getValue("VALUE"));
                    if (s3.equalsIgnoreCase("NTV") && tempNtKey.equalsIgnoreCase("TEXTDIVISION") &&
                            (attributes.getValue("KEY").equalsIgnoreCase("TEXTDIVISION") ||
                            attributes.getValue("KEY").equalsIgnoreCase("TEXTDIVISIONDESCRIPTION") ||
                            attributes.getValue("KEY").equalsIgnoreCase("TEXTDIVISIONMUSEUMNO")))
                        nt.addElement(getVal(attributes.getValue("VALUE")));
                    else if (s3.equalsIgnoreCase("NTV") && (tempNtKey.equalsIgnoreCase("TEXTALIASES") || tempNtKey.equalsIgnoreCase("SCRIPT")))
                        nt.addElement(getVal(attributes.getValue("VALUE")));

                }
            } else if (s2.equals("IMAGE")) {
                img = attributes.getValue("ID");
            } else if (s2.equals("IMAGE_DATA")) {
                String s4 = attributes.getValue("DATA");
               // byte abyte0[] = ISFBase64.decode(s4);
                ht.put(img, new ImageIcon(ISFBase64.decode(s4)));
            }
            //System.out.println("ht in start element   :"+ht);
        } catch (Exception exception) {
           logger.error( exception);
        }
    }

    public void endElement(String s, String s1, String s2)
            throws SAXException {
        if (s2.equals("RECORD")) {
            main.addElement(ht);
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

   

    /*  public String getVal(String s) {
          int i = s.indexOf("@sark@");
          if (i > -1)
              s = s.substring(0, i) + "'" + s.substring(i + 6);
          //System.out.println(s);
          return s;
      }*/
}
