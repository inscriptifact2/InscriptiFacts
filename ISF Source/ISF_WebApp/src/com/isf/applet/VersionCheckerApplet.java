package com.isf.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class VersionCheckerApplet extends Applet {

    private String javaVersion;
    private String osName;
    private String osVersion;
    private String osArch;
    private float version;

    public VersionCheckerApplet() {
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void init() {
        javaVersion = System.getProperty("java.version").toUpperCase();
        osName = System.getProperty("os.name").toUpperCase();
        osVersion = System.getProperty("os.version").toUpperCase();
        osArch = System.getProperty("os.arch").toUpperCase();
        version = Float.parseFloat(osVersion.substring(0, osVersion.indexOf(".") + 1));
    }

    public void print(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, 300, 100);
        g.setColor(Color.white);
        g.drawString("JVer:" + javaVersion + ",OS:" + osName + ",Ver:" + osVersion + ",OA:" + osArch, 10, 10);
    }

    public boolean isValidOSVersion() {
        boolean valid = false;
        if (osName.indexOf("MAC") == -1) {
            valid = true;
        } else if (javaVersion.startsWith("1.3")) {
            valid = true;
        } else if ((double) version < 10.4D) {
            valid = true;
        }
        return valid;
    }
}
