package isf.common.view.gui;

import java.io.Serializable;
import java.util.Vector;

public class TextDivision
    implements Serializable
{

    private String isfAssignedTextNo;
    private String maintextpubno;
    private String indexmapfilename;
    private String sscode;
    private String textdesctitle;
    private String textdivname;
    private String textdivdesc;
    private String textdivmuseumno;
    private String textview;
    private Vector textdivsubdivs;

    public TextDivision()
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

    public void setIndexMapFileName(String s)
    {
        indexmapfilename = s;
    }

    public String getIndexMapFileName()
    {
        return indexmapfilename;
    }

    public void setSpatialSearchCode(String s)
    {
        sscode = s;
    }

    public String getSpatialSearchCode()
    {
        return sscode;
    }

    public void setTextDescTitle(String s)
    {
        textdesctitle = s;
    }

    public String getTextDescTitle()
    {
        return textdesctitle;
    }

    public void setTextDivisionName(String s)
    {
        textdivname = s;
    }

    public String getTextDivisionName()
    {
        return textdivname;
    }

    public void setTextDivisionDesc(String s)
    {
        textdivdesc = s;
    }

    public String getTextDivisionDesc()
    {
        return textdivdesc;
    }

    public void setTextDivisionMuseumNo(String s)
    {
        textdivmuseumno = s;
    }

    public String getTextDivisionMuseumNo()
    {
        return textdivmuseumno;
    }

    public void setTextView(String s)
    {
        textview = s;
    }

    public String getTextView()
    {
        return textview;
    }

    public void setTextDivisionSubDivisions(Vector vector)
    {
        textdivsubdivs = vector;
    }

    public Vector getTextDivisionSubDivisions()
    {
        return textdivsubdivs;
    }

    public String toString()
    {
        return textdivname + "                         " + textdivdesc + "                               " + textdivmuseumno;
    }
}
