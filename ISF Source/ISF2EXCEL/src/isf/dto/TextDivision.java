package isf.dto;


public class TextDivision
{

    private String textDivision;
    private String textDivisionDescription;

    public TextDivision(String div, String desc)
    {
        textDivision = div;
        textDivisionDescription = desc;
    }

    public String getTextDivision()
    {
        return textDivision;
    }

    public String getTextDivisionDescription()
    {
        return textDivisionDescription;
    }
}
