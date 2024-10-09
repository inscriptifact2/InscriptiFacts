package com.isf.app.utils;

import org.xml.sax.helpers.DefaultHandler;

public abstract class XmlConverter  extends DefaultHandler{

    public abstract Object getDataStructure();

    public String getVal(String s) {
        return s;
    }
    public abstract String getErrMsg();
    public abstract String getSccMsg();
    public abstract String getCount();
    public abstract String getListKeys();
}
