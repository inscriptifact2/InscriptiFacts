package isf.dto;

import java.text.*;
import java.util.*;

public class PhotoObject {

    private String iSFAssignedTextNumber;
    private String photoDescriptiveTitle;
    private String digitalObjectTypeNote;
    private String photographDescription;
    private String scriptNote;
    private String iSFFindSite;
    private String digitizationEquipmentSpecs;
    private String dateOfPhotograph;
    private String dateOfDigitalConversion;
    private String typeOfItemCateloged;
    private String archivalFileResolution;
    private String archivalFileSize;
    private String digitalObjectFormat;
    private String filmTypeCode;
    private String magnificationCode;
    private String photoDimensions;
    private String languageNote;
    private String iSFDigitalObjectIdentifier;
    private String photographIdentificationNo;
    private String photoMuseumAccessionNoNote;
    private String isPartOfWSRProject;
    private String rightsDigitalObject;
    private String rightsPhotograph;
    private String fileName;
    private String rightsPublicationPermission;
    private ArrayList ancientTextRepresented;
    private ArrayList photographer;
    private ArrayList colaborator;
    private ArrayList photoTextOrPublcnNoNote;
    private ArrayList textDivision;
    private ArrayList rightsPhysicalObject;
    private SimpleDateFormat mYear = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat month = new SimpleDateFormat("MMM yyyy");
    private SimpleDateFormat year = new SimpleDateFormat("yyyy");
    private SimpleDateFormat md = new SimpleDateFormat("MM/yyyy");

    public PhotoObject(String isfassignedtextno)
    {
        ancientTextRepresented = new ArrayList();
        photographer = new ArrayList();
        colaborator = new ArrayList();
        photoTextOrPublcnNoNote = new ArrayList();
        textDivision = new ArrayList();
        rightsPhysicalObject = new ArrayList();
        iSFAssignedTextNumber = isfassignedtextno;
    }

    public PhotoObject()
    {
        ancientTextRepresented = new ArrayList();
        photographer = new ArrayList();
        colaborator = new ArrayList();
        photoTextOrPublcnNoNote = new ArrayList();
        textDivision = new ArrayList();
        rightsPhysicalObject = new ArrayList();
        //iSFAssignedTextNumber = isfassignedtextno;
    }

    public String getPhotoDescriptiveTitle()
    {
        return photoDescriptiveTitle;
    }

    public void setPhotoDescriptiveTitle(String photoDescriptiveTitle)
    {
        this.photoDescriptiveTitle = photoDescriptiveTitle;
    }

    public String getDigitalObjectTypeNote()
    {
        return digitalObjectTypeNote;
    }

    public void setDigitalObjectTypeNote(String digitalObjectTypeNote)
    {
        this.digitalObjectTypeNote = digitalObjectTypeNote;
    }

    public String getPhotographDescription()
    {
        return photographDescription;
    }

    public void setPhotographDescription(String photographDescription)
    {
        this.photographDescription = photographDescription;
    }

    public String getScriptNote()
    {
        return scriptNote;
    }

    public void setScriptNote(String scriptNote)
    {
        this.scriptNote = scriptNote;
    }

    public String getISFFindSite()
    {
        return iSFFindSite;
    }

    public void setISFFindSite(String iSFFindSite)
    {
        this.iSFFindSite = iSFFindSite;
    }

    public String getDigitizationEquipmentSpecs()
    {
        return digitizationEquipmentSpecs;
    }

    public void setDigitizationEquipmentSpecs(String digitizationEquipmentSpecs)
    {
        this.digitizationEquipmentSpecs = digitizationEquipmentSpecs;
    }

    public String getDateOfPhotograph()
    {
        return dateOfPhotograph;
    }

    public void setDateOfPhotograph(String dop)
    {
        String strdop = "";
        Date d = null;
        if (dop != null && !dop.trim().isEmpty())
        {
            strdop = dop.trim();
        }
        if (strdop.equalsIgnoreCase(""))
        {
            this.dateOfPhotograph = "";
            return;
        }
        try
        {
            if (strdop.indexOf(" ") > -1)
            {
                d = month.parse(strdop);
            }
            else if (strdop.length() > 4)
            {
                d = date.parse(strdop);
            }
            else
            {
                d = year.parse(strdop);
            }
        } catch (ParseException pe)
        {
            pe.printStackTrace();
        } finally
        {

            this.dateOfPhotograph = mYear.format(d);
        }
    }

    public String getDateOfDigitalConversion()
    {
        return dateOfDigitalConversion;
    }

    public void setDateOfDigitalConversion(String dodc)
    {
        String strdop = "";
        Date d = null;
        if (dodc != null && !dodc.trim().isEmpty())
        {
            strdop = dodc.trim();
        }
        if (strdop.equalsIgnoreCase(""))
        {
            this.dateOfDigitalConversion = "";
            return;
        }
        
        try
        {
             if(strdop.indexOf("/")>-1){
                 d =md.parse(strdop);
                 strdop=month.format(d);
             }
            if (strdop.indexOf(" ") > -1)
            {
                d = month.parse(strdop);
            }
            else if (strdop.length() > 4)
            {
                d = date.parse(strdop);
            }
            else
            {
                d = year.parse(strdop);
            }
        } catch (ParseException pe)
        {
            pe.printStackTrace();
        }

        this.dateOfDigitalConversion = mYear.format(d);
    }

    public String getTypeOfItemCateloged()
    {
        return typeOfItemCateloged;
    }

    public void setTypeOfItemCateloged(String typeOfItemCateloged)
    {
        this.typeOfItemCateloged = typeOfItemCateloged;
    }

    public String getArchivalFileResolution()
    {
        return archivalFileResolution;
    }

    public void setArchivalFileResolution(String archivalFileResolution)
    {
        this.archivalFileResolution = archivalFileResolution;
    }

    public String getArchivalFileSize()
    {
        return archivalFileSize;
    }

    public void setArchivalFileSize(String archivalFileSize)
    {
        this.archivalFileSize = archivalFileSize;
    }

    public String getDigitalObjectFormat()
    {
        return digitalObjectFormat;
    }

    public void setDigitalObjectFormat(String digitalObjectFormat)
    {
        this.digitalObjectFormat = digitalObjectFormat;
    }

    public String getFilmTypeCode()
    {
        return filmTypeCode;
    }

    public void setFilmTypeCode(String ftc)
    {
        int s = 0;
        if (ftc != null)
        {
            try
            {
                s = Integer.parseInt(ftc.trim());
            } catch (NumberFormatException nfe)
            {
                filmTypeCode = ftc;
                return;
            }
        }
        // String retVal = "";
        switch (s)
        {
            case 1: // '1'
                filmTypeCode = "Color transparency";
                break;

            case 2: // '2'
                filmTypeCode = "Color negative";
                break;

            case 3: // '3'
                filmTypeCode = "Black and white infrared negative";
                break;

            case 4: // '4'
                filmTypeCode = "High contrast black and white negative";
                break;

            case 5: // '5'
                filmTypeCode = "Medium contrast black and white negative";
                break;

            case 6: // '6'
                filmTypeCode = "Digital color";
                break;

            case 7: // '7'
                filmTypeCode = "Digital infrared";
                break;

            case 8: // '8'
                filmTypeCode = "Digital black and white";
                break;

            case 9: // '9'
                filmTypeCode = "PTM";
                break;

            case 10:
                filmTypeCode = "RTI";
                break;
            default:
                filmTypeCode = "";
                break;
        }

    }

    public String getMagnificationCode()
    {
        return magnificationCode;
    }

    public void setMagnificationCode(String mc)
    {
        int code = 0;
        if (mc != null)
        {
            try
            {
                code = Integer.parseInt(mc.trim());
            } catch (NumberFormatException mfe)
            {
                magnificationCode = mc;
                return;
            }
        }
        switch (code)
        {
            case 1: // '\0'
                magnificationCode = "Reference";
                break;

            case 2: // '\001'
                magnificationCode = "Sectional";
                break;

            case 3: // '\002'
                magnificationCode = "Detail";
                break;

            default:
                magnificationCode = "";
                break;
        }

    }

    public String getPhotoDimensions()
    {
        return photoDimensions;
    }

    public void setPhotoDimensions(String photoDimensions)
    {
        this.photoDimensions = photoDimensions;
    }

    public String getLanguageNote()
    {
        return languageNote;
    }

    public void setLanguageNote(String languageNote)
    {
        this.languageNote = languageNote;
    }

    public String getiSFDigitalObjectIdentifier()
    {
        return iSFDigitalObjectIdentifier;
    }

    public void setiSFDigitalObjectIdentifier(String iSFDigitalObjectIdentifier)
    {
        this.iSFDigitalObjectIdentifier = iSFDigitalObjectIdentifier;
    }

    public String getPhotographIdentificationNo()
    {
        return photographIdentificationNo;
    }

    public void setPhotographIdentificationNo(String photographIdentificationNo)
    {
        this.photographIdentificationNo = photographIdentificationNo;
    }

    public String getPhotoMuseumAccessionNoNote()
    {
        return photoMuseumAccessionNoNote;
    }

    public void setPhotoMuseumAccessionNoNote(String photoMuseumAccessionNoNote)
    {
        this.photoMuseumAccessionNoNote = photoMuseumAccessionNoNote;
    }

    public String getIsPartOfWSRProject()
    {
        return isPartOfWSRProject;
    }

    public void setIsPartOfWSRProject(String isPartOfWSRProject)
    {
        this.isPartOfWSRProject = isPartOfWSRProject;
    }

    public String getRightsDigitalObject()
    {
        return rightsDigitalObject;
    }

    public void setRightsDigitalObject(String rightsDigitalObject)
    {
        this.rightsDigitalObject = rightsDigitalObject;
    }

    public String getRightsPhotograph()
    {
        return rightsPhotograph;
    }

    public void setRightsPhotograph(String rightsPhotograph)
    {
        this.rightsPhotograph = rightsPhotograph;
    }

    public String getRightsPublicationPermission()
    {
        return rightsPublicationPermission;
    }

    public void setRightsPublicationPermission(String rightsPublicationPermission)
    {
        this.rightsPublicationPermission = rightsPublicationPermission;
    }

    public ArrayList getAncientTextRepresented()
    {
        return ancientTextRepresented;
    }

    public void setAncientTextRepresented(ArrayList ancientTextRepresented)
    {
        this.ancientTextRepresented = ancientTextRepresented;
    }

    public ArrayList getPhotographer()
    {
        return photographer;
    }

    public void setPhotographer(ArrayList photographer)
    {
        this.photographer = photographer;
    }

    public ArrayList getColaborator()
    {
        return colaborator;
    }

    public void setColaborator(ArrayList colaborator)
    {
        this.colaborator = colaborator;
    }

    public ArrayList getPhotoTextOrPublcnNoNote()
    {
        return photoTextOrPublcnNoNote;
    }

    public void setPhotoTextOrPublcnNoNote(ArrayList photoTextOrPublcnNoNote)
    {
        this.photoTextOrPublcnNoNote = photoTextOrPublcnNoNote;
    }

    public ArrayList getTextDivision()
    {
        return textDivision;
    }

    public void setTextDivision(ArrayList textDivision)
    {
        this.textDivision = textDivision;
    }

    public ArrayList getRightsPhysicalObject()
    {
        return rightsPhysicalObject;
    }

    public void setRightsPhysicalObject(ArrayList rightsPhysicalObject)
    {
        this.rightsPhysicalObject = rightsPhysicalObject;
    }

    public String getISFAssignedTextNumber()
    {
        return iSFAssignedTextNumber;
    }

    public void setISFAssignedTextNumber(String text)
    {
        iSFAssignedTextNumber = text;
    }

    public PhotoObject(ArrayList list)
    {
        ancientTextRepresented = new ArrayList();
        photographer = new ArrayList();
        colaborator = new ArrayList();
        photoTextOrPublcnNoNote = new ArrayList();
        textDivision = new ArrayList();
        rightsPhysicalObject = new ArrayList();
        for (int i = 0;
                i < list.size();
                i++)
        {
            switch (i + 1)
            {
                case 1: // '\001'
                    photoDescriptiveTitle = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 2: // '\002'
                    ancientTextRepresented.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 3: // '\003'
                    iSFAssignedTextNumber = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 4: // '\004'
                    digitalObjectTypeNote = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 5: // '\005'
                    photographDescription = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 6: // '\006'
                    scriptNote = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 8: // '\b'
                    iSFFindSite = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 12: // '\f'
                    photographer.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 13: // '\r'
                    photographer.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 14: // '\016'
                    photographer.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 15: // '\017'
                    photographer.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 16: // '\020'
                    photographer.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 17: // '\021'
                    digitizationEquipmentSpecs = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 18: // '\022'
                    colaborator.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 19: // '\023'
                    colaborator.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 20: // '\024'
                    colaborator.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 21: // '\025'
                    colaborator.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 22: // '\026'
                    colaborator.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 24: // '\030'
                    dateOfPhotograph = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 25: // '\031'
                    dateOfDigitalConversion = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 26: // '\032'
                    typeOfItemCateloged = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 27: // '\033'
                    archivalFileResolution = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 28: // '\034'
                    archivalFileSize = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 29: // '\035'
                    digitalObjectFormat = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 30: // '\036'
                    filmTypeCode = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 31: // '\037'
                    magnificationCode = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 32: // ' '
                    photoDimensions = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 33: // '!'
                    languageNote = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 34: // '"'
                    iSFDigitalObjectIdentifier = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 35: // '#'
                    photographIdentificationNo = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 36: // '$'
                    photoMuseumAccessionNoNote = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 37: // '%'
                    photoTextOrPublcnNoNote.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 40: // '('
                    isPartOfWSRProject = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 41: // ')'
                    textDivision.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 43: // '+'
                    rightsPublicationPermission = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 44: // ','
                    rightsDigitalObject = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 45: // '-'
                    rightsPhotograph = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;

                case 46: // '.'
                    rightsPhysicalObject.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 47: // '/'
                    rightsPhysicalObject.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 48: // '0'
                    rightsPhysicalObject.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 49: // '1'
                    rightsPhysicalObject.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 50: // '2'
                    rightsPhysicalObject.add((new StringBuilder()).append(list.get(i)).append("").toString());
                    break;

                case 56: // '8'
                    fileName = (new StringBuilder()).append(list.get(i)).append("").toString();
                    break;
            }
        }

    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setFileName(String fName, boolean decode)
    {
        this.fileName = fName;
        String ext = fName.substring(fName.lastIndexOf(".") + 1).toLowerCase();
        String name = fName.substring(0, fName.lastIndexOf("."));

        if (decode)
        {
            String[] values = name.split("_");
            String mtopn = values[0] + " " + values[1].replaceAll("-", ".");
            String location = "", order = "", sequence = "";
            StringBuilder filter = new StringBuilder();

            location = getLocation(values[2].substring(0, 2));
            if (values[2].indexOf("-#") > -1)
            {
                //means its a TIFF_16
                order = values[2].substring(2, values[2].indexOf("-#"));
                sequence = values[2].substring(values[2].indexOf("-#"));
            }
            else
            {
                order = values[2].substring(2);
                if (ext.toLowerCase().endsWith("rti") || ext.toLowerCase().endsWith("ptm") || ext.toLowerCase().endsWith("hsh"))
                {
                    if (order.endsWith("p"))
                    {
                        order = order.replaceAll("p", " PTM/RTI");
                    }
                    else
                    {
                        order = order + " PTM/RTI";
                    }
                }

            }
            if (values.length > 3)
            {
                String seb = "";
                String sep = "";
                for (int i = 3; i < values.length; i++)
                {
                    seb = getFilter(values[i]);
                    if (filter.toString().indexOf(seb) == -1)
                    {
                        filter.append(sep).append(seb);
                        sep = ", ";
                    }
                    ;
                }
            }

            ancientTextRepresented.add(mtopn);
            digitalObjectTypeNote = "Digital Image";
            photographer.add("West Semitic Research");
            photographer.add("Oriental Institute");
            colaborator.add("Oriental Institute, University of Chicago");
            typeOfItemCateloged = "image";
            if (ext.equalsIgnoreCase("tif") || ext.equalsIgnoreCase("tiff"))
            {
                digitalObjectFormat = "image/tiff";
            }
            else if (ext.equalsIgnoreCase("ptm"))
            {
                digitalObjectFormat = "image/ptm";
            }
            else if (ext.equalsIgnoreCase("rti") || ext.equalsIgnoreCase("hsh"))
            {
                digitalObjectFormat = "image/rti";
            }
            photographIdentificationNo = name;
            isPartOfWSRProject = "Persepolis Project";
            rightsPublicationPermission = "For educational use only. All requests for permission "
                    + "to publish must be submitted in writing to West Semitic Research and "
                    + "the Oriental Institute. Credit: Photograph by West Semitic Research and "
                    + "Oriental Institute. Courtesy Department of Antiquities, Iran.";
            rightsDigitalObject = "West Semitic Research/ Oriental Institute, University of Chicago";
            rightsPhysicalObject.add("Department of Antiquities, Iran");
            /* photoDescriptiveTitle = new StringBuilder().append(mtopn)
                        .append(" ").append(location).append(" ").append(order).append(" ")
                        .append(sequence).append(", ").append(filter.toString()).toString();*/
            StringBuilder title = new StringBuilder().append(mtopn.trim());
            if (!location.trim().isEmpty())
            {
                title.append(" ").append(location.trim());
            }
            if (!order.trim().isEmpty())
            {
                title.append(" ").append(order.trim());
            }
            if (!sequence.trim().isEmpty())
            {
                title.append(sequence.trim());
            }
            else if (!filter.toString().trim().isEmpty())
            {
                title.append(", ").append(filter.toString().trim());
            }
            photoDescriptiveTitle = title.toString();

            photographDescription = photoDescriptiveTitle;

        }
    }

    private String getLocation(String code)
    {
        String retVal = "";

        switch (code)
        {
            case "UE": // '\0'
                retVal = "Upper Edge";
                break;

            case "BE": // '\001'
                retVal = "Bottom Edge";
                break;

            case "FE": // '\002'
                retVal = "Flat Edge";
                break;

            case "LE": // '\003'
                retVal = "Left Edge";
                break;

            case "RE": // '\004'
                retVal = "Right or Round Edge";
                break;

            case "OB": // '\005'

                retVal = "Obverse";
                break;

            case "RV": // '\006'
                retVal = "Reverse";
                break;

            default:
                retVal = "Obverse";
                break;
        }
        return retVal;
    }

    private String getFilter(String code)
    {
        String retVal = "";
        switch (code)
        {
            case "ir":
                retVal = "infrared";
                break;
            case "xir":
                retVal = "xinfrared";
                break;
            case "neg":
                retVal = "negative";
                break;
            case "pf":
                retVal = "polarizing filter on lens for ink";
                break;
            case "pfpl":
                retVal = "polarizing filter on lens nd lights for ink";
                break;
            case "pf_pl":
                retVal = "polarizing filter on lens nd lights for ink";
                break;
            case "rf":
                retVal = "red filter";
                break;
            case "of":
                retVal = "orange filter";
                break;
            case "yf":
                retVal = "yellow filter";
                break;
            default:
                retVal = "";
                break;

        }
        return retVal;
    }
    private String filePath;

    public void setPath(String path)
    {
        this.filePath = path;
    }

    public String getPath()
    {
        return filePath;
    }
}
