package com.isf.app.view.gui;

import java.io.File;
import java.io.FilenameFilter;

public class InscriptFileNameFilter
        implements FilenameFilter {

    private String wildCard;

    public InscriptFileNameFilter(String s) {
        wildCard = s;
    }

    public boolean accept(File file, String s) {
        if (wildCard == null || wildCard.equals("*.*")) {
            return true;
        } else {
            return s.endsWith(wildCard);
        }
    }
}
