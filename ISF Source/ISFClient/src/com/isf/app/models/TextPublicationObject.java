package com.isf.app.models;

public class TextPublicationObject {

    public String text;
    public String pre;

    public TextPublicationObject(String s, String s1) {
        pre = "";
        text = s;
        pre = s1;
    }

    public String toString() {
        return text;
    }
}
