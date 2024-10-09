package com.isf.app.models;

import isf.controls.utils.ResolutionManager;
import java.util.Enumeration;
import java.util.Hashtable;

public class CatalougeTextDataObject
        implements InscriptTableData {

    private Hashtable headerValues;
    private String result;
    private String temp;
    public String displayType;
    public String textCode;
    private int LCON;
    private int RCON;

    public CatalougeTextDataObject() {
        headerValues = new Hashtable();
        result = "";
        temp = "";
        textCode = "";
        LCON = 15;
        RCON = 22;
    }

    public CatalougeTextDataObject(int i, int j) {
        headerValues = new Hashtable();
        result = "";
        temp = "";
        textCode = "";
        LCON = 15;
        RCON = 22;
        LCON = i;
        RCON = j;
    }

    public void setValue(String s, String s1) {
        //     System.out.println("s="+s);
        //     System.out.println("s1="+s1);
        headerValues.put(s, s1);
    }

    public void setTextCode(String s) {
        textCode = s;
    }

    public void setDisplayType(String s) {
        displayType = s;
    }

    public String getName() {
        int i = ResolutionManager.getInstance().getFontSize(13);
        String s = "<html><body><p><font face='Arial' size='" + i + "pts' color='FFFFFF'>";
        temp = "";
        String s1 = "";
        for (Enumeration enumeration = headerValues.keys(); enumeration.hasMoreElements();) {
            String s2 = (String) enumeration.nextElement();
            s = s + s1 + "<B>" + s2 + "</B>";
            temp += s1 + (String) headerValues.get(s2);
            s1 = "<BR>";
        }

        return s + "</font></p></body></html>";
    }

    public int getSize() {
        int i = 0;
        boolean flag = false;
        boolean flag1 = false;
        for (Enumeration enumeration = headerValues.keys(); enumeration.hasMoreElements();) {
            String s = (String) enumeration.nextElement();
            String s1 = (String) headerValues.get(s);
            int l = s.length();
            int j = l / LCON + (l % LCON == 0 ? 0 : 1);
            int i1 = s1.length();
            int k = i1 / RCON + (i1 % RCON == 0 ? 0 : 1);
            i += j <= k ? k : j;
        }

        return i;
    }

    public String getValue() {
        int i = ResolutionManager.getInstance().getFontSize(13);
        return "<html><body><p><font face='Arial' size='" + i + "pts' color='FFFFFF'><b>" + temp + "</b></font></p></body></html>";
    }

    /*PSVM(String args[]) {
        CatalougeTextDataObject catalougetextdataobject = new CatalougeTextDataObject();
        catalougetextdataobject.setValue("Test Description 1", "test1");
        catalougetextdataobject.setValue("Test Description 2", "test2");
        catalougetextdataobject.setValue("Test Description 3", "");
        catalougetextdataobject.setValue("Test Description 4", "test4");
    }*/
}
