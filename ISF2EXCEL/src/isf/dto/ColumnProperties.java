package isf.dto;

import java.util.ArrayList;

public class ColumnProperties
{

    private String label;
    private String refMethod;
    private ArrayList varray;
    private ArrayList varray1;
    private ArrayList varray2;
    private ArrayList nested;

    public ColumnProperties(String strlabel, String prop)
    {
        label = "";
        refMethod = "";
        varray = null;
        varray1 = null;
        varray2 = null;
        nested = null;
        label = strlabel;
        refMethod = prop;
    }

    public String getLable()
    {
        return label;
    }

    public String getTargetMethod()
    {
        return refMethod;
    }
}
