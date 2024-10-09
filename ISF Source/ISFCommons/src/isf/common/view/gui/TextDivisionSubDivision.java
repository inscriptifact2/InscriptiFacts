package isf.common.view.gui;

import java.io.Serializable;

public class TextDivisionSubDivision
    implements Serializable
{

    private String isfAssignedTextNo;
    private String maintextpubno;
    private String sscode;
    private String textdivname;
    private String textdivsubdivname;
    private String textdesctitle;

    public TextDivisionSubDivision()
    {
    }

    public void setIsfAssignedTextNo(String s)
    {
        isfAssignedTextNo = s;
    }

    public String getIsfAssignedTextNo()
    {
        return isfAssignedTextNo;
    }

    public void setMainTextPubNo(String s)
    {
        maintextpubno = s;
    }

    public String getMainTextPubNo()
    {
        return maintextpubno;
    }

    public void setSpatialSearchCode(String s)
    {
        sscode = s;
    }

    public String getSpatialSearchCode()
    {
        return sscode;
    }

    public void setTextDivisionName(String s)
    {
        textdivname = s;
    }

    public String getTextDivisionName()
    {
        return textdivname;
    }

    public void setTextDescTitle(String s)
    {
        textdesctitle = s;
    }

    public String getTextDescTitle()
    {
        return textdesctitle;
    }

    public void setTextDivisionSubDivisionName(String s)
    {
        textdivsubdivname = s;
    }

    public String getTextDivisionSubDivisionName()
    {
        return textdivsubdivname;
    }

    public String toString()
    {
        return textdivsubdivname;
    }
}
