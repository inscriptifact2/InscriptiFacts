/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.utils;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 *
 * @author raok1
 */
public class PropertyLoader {
     public Properties p;
    private static PropertyLoader icl;

    private PropertyLoader() {
        p = new Properties();
        load();
    }

    public static PropertyLoader getInstance() {
        if (icl == null) {
            icl = new PropertyLoader();
        }
        return icl;
    }

    private void load() {
        try {
            p.load(getClass().getResourceAsStream("property.ini"));
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
