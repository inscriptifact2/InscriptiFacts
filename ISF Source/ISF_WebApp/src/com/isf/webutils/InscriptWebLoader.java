package com.isf.webutils;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Dec 25, 2003
 * Time: 10:55:30 AM
 * To change this template use Options | File Templates.
 */

import java.io.FileNotFoundException;
import java.util.Properties;

public class InscriptWebLoader {

    public Properties p;
    private static InscriptWebLoader icl;

    private InscriptWebLoader() {
        p = new Properties();
        load();
    }

    public static InscriptWebLoader getInstance() {
        if (icl == null) {
            icl = new InscriptWebLoader();
        }
        return icl;
    }

    private void load() {
        try {
            p.load(getClass().getResourceAsStream("InscriptWebApp.ini"));
        } catch (FileNotFoundException filenotfoundexception) {
            filenotfoundexception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String getProperty(String name) {
        return p.getProperty(name);
    }


}
