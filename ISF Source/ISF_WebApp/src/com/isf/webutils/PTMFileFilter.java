package com.isf.webutils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 5, 2007
 * Time: 1:52:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class PTMFileFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.toLowerCase().endsWith(".ptm"));
    }
}
