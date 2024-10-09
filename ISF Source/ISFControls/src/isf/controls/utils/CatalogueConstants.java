package isf.controls.utils;

import isf.common.Debug;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Hashtable;
import java.util.Vector;

public class CatalogueConstants {

    public static final String pStr = "<i><font size=1 face='Arial' color='AC5539'>(Photograph)</font></i>";
    public static final String iStr = "<i><font size=1 face='Arial' color='AC5539'>(Inscription)</font></i>";
    public static final String oStr = "<i><font size=1 face='Arial' color='AC5539'>(Physical Object)</font></i>";
    public static final String photoCatalogueNames[][] = {
        {
            "Type of Item Catalogued:", "TYPEOFITEMCATELOGED"
        }, {
            "Photo Descriptive Title:", "PHOTODESCRIPTIVETITLE"
        }, {
            "Digital Object Type:", "DIGITALOBJECTTYPENOTE"
        }, {
            "Photograph Description:", "PHOTOGRAPHDESCRIPTION"
        }, {
            "Ancient Text Represented:", "ANCIENTTEXTREPRESENTED"
        }, {
            "Medium:", "MEDIUM"
        }, {
            "ISF Digital Object ID Number:", "ISFDIGITALOBJECTIDENTIFIER"
        }, {
            "Photograph Identification Number:", "PHOTOGRAPHIDENTIFICATIONNO"
        }, {
            "WSR Project:", "ISPARTOFWSRPROJECT"
        }, {
            "Date of Digital Conversion:", "DATEOFDIGITALCONVERSION"
        }, {
            "Date of Photograph:", "DATEOFPHOTOGRAPH"
        }, {
            "Language Note :", "LANGUAGENOTE"
        }, {
            "Language:", "LANGUAGE"
        }, {
            "Script Note :", "SCRIPTNOTE"
        }, {
            "Script : Sub-script", "SCRIPT"
        }, {
            "Find Site:", "ISFFINDSITE"
        }, {
            "Time Period:", "NAMEDTIMEPERIOD"
        }, {
            "Photo Museum Accession Number:", "PHOTOMUSEUMACCESSIONNO"
        }, {
            "Photo Museum Accession Number Note:", "PHOTOMUSEUMACCESSIONNONOTE"
        }, {
            "Photo Text or Publication Number Note:", "PHOTOTEXTORPUBLCNNONOTE"
        }, {
            "Photographer:", "PHOTOGRAPHER"
        }, {
            "Digitization Equipment Specifications:", "DIGITIZATIONEQUIPMENTSPECS"
        }, {
            "Collaborator:", "COLABORATOR"
        }, {
            "Rights Digital Object:", "RIGHTSDIGITALOBJECT"
        }, {
            "Rights Publication Permission:", "RIGHTSPUBLICATIONPERMISSION"
        },{
            "Rights Photographic Object:", "RIGHTSPHOTOGRAPH"
        }, {
            "Rights Physical Object:", "RIGHTSPHYSICALOBJECT"
        }, {
            "Archival File Resolution:", "ARCHIVALFILERESOLUTION"
        }, {
            "Archival File Size:", "ARCHIVALFILESIZE"
        }, {
            "Compressed Format Extension:", "COMPRESSEDFORMATEXTENSION"
        }, {
            "Compression Routine:", "COMPRESSIONROUTINE"
        }, {
            "Compression Ratio:", "COMPRESSIONRATIO"
        }, {
            "Digital Object Format:", "DIGITALOBJECTFORMAT"
        }, {
            "Film Type:", "FILMTYPECODE"
        }, {
            "Magnification:", "MAGNIFICATIONCODE"
        }, {
            "Photo Dimensions:", "PHOTODIMENSIONS"
        }
    };
    public static final String photoDublinNames[][] = {
        {
            "Type of Item Catalogued:", "TYPEOFITEMCATELOGED"
        }, {
            "Photo Descriptive Title:", "PHOTODESCRIPTIVETITLE"
        }, {
            "Digital Object Type:", "DIGITALOBJECTTYPENOTE"
        }, {
            "Photograph Description:", "PHOTOGRAPHDESCRIPTION"
        }, {
            "Ancient Text Represented: " + pStr, "ANCIENTTEXTREPRESENTED"
        }, /* {
         "Text or Publication Number Note <i><font size=1 face='Arial' color='AC5539'>(Ins" +
         "cription)</font></i>"
         , "PHOTOTEXTORPUBLCNNONOTE"
         },*/ {
            "Medium: " + oStr, "MEDIUM"
        }, {
            "Script Note :", "SCRIPTNOTE"
        }, {
            "Script : Sub-script " + iStr, "SCRIPT"
        }, {
            "ISF Digital Object ID Number:", "ISFDIGITALOBJECTIDENTIFIER"
        }, {
            "Photograph Identification Number:", "PHOTOGRAPHIDENTIFICATIONNO"
        }, {
            "Is Part of WSR Project: " + pStr, "ISPARTOFWSRPROJECT"
        }, {
            "Date of Digital Conversion:", "DATEOFDIGITALCONVERSION"
        }, {
            "Date of Photograph:", "DATEOFPHOTOGRAPH"
        }, {
            "Language Note :", "LANGUAGENOTE"
        }, {
            "Language: " + iStr, "LANGUAGE"
        }, {
            "Find Site: " + oStr, "ISFFINDSITE"
        }, {
            "Relevant Time Line: " + iStr, "RELEVANTTIMELINE"
        }, {
            "Named Time Period: " + iStr, "NAMEDTIMEPERIOD"
        }, {
            "Photo Museum Accession Number:", "PHOTOMUSEUMACCESSIONNO"
        }, {
            "Photo Museum Accession Number Note:", "PHOTOMUSEUMACCESSIONNONOTE"
        }, {
            "Photo Text or Publication Number Note:", "PHOTOTEXTORPUBLCNNONOTE"
        }, {
            "Photographer:", "PHOTOGRAPHER"
        }, {
            "Digitization Equipment Specifications:", "DIGITIZATIONEQUIPMENTSPECS"
        }, {
            "Collaborator:", "COLABORATOR"
        }, {
            "Rights Digital Object:", "RIGHTSDIGITALOBJECT"
        }, {
            "Rights Publication Permission:", "RIGHTSPUBLICATIONPERMISSION"
        },{
            "Rights Photographic Object:", "RIGHTSPHOTOGRAPH"
        }, {
            "Rights Physical Object:", "RIGHTSPHYSICALOBJECT"
        }, {
            "Archival File Resolution:", "ARCHIVALFILERESOLUTION"
        }, {
            "Archival File Size:", "ARCHIVALFILESIZE"
        }, {
            "Compressed Format Extension:", "COMPRESSEDFORMATEXTENSION"
        }, {
            "Compression Routine:", "COMPRESSIONROUTINE"
        }, {
            "Compression Ratio:", "COMPRESSIONRATIO"
        }, {
            "Digital Object Format:", "DIGITALOBJECTFORMAT"
        }, {
            "Film Type: " + pStr, "FILMTYPECODE"
        }, {
            "Magnification: " + pStr, "MAGNIFICATIONCODE"
        }, {
            "Photograph Dimensions:", "PHOTODIMENSIONS"
        }
    };
    public static final String textCatalogueNames[][] = {
        {
            "Type of Item Catalogued:", "TYPEOFITEMCATELOGED"
        }, {
            "Text/ Uninscribed/ Glyptic:", "TEXTUNINSCRIBEDARTIFACT"
        }, {
            "Main Text or Publication Number:", "MAINTEXTORPUBLCNNO"
        }, {
            "Text Descriptive Title:", "TEXTDESCRIPTIVETITLE"
        }, {
            "Text Alternate Title:", "ALTTEXTTITLE"
        }, {
            "Text Description:", "TEXTDESCRIPTION"
        }, {
            "Text or Publication Number Note:", "TEXTORPUBLCNNONOTE"
        }, {
            "Excavation Description:", "EXCAVATIONDESCRIPTION"
        }, {
            "Geographical Note:", "GEOGRAPHICALCOVERAGENOTE"
        }, {
            "Medium:", "MEDIUM"
        }, {
            "Script Note :", "SCRIPTNOTE"
        }, {
            "Script : Sub-script", "SCRIPT"
        }, {
            "Physical Object Description:", "PHYSICALOBJECTDESCRIPTION"
        }, {
            "Physical Object Note:", "PHYSICALOBJECTNOTE"
        }, {
            "ISF Assigned Text Number:", "ISFASSIGNEDTEXTNO"
        }, {
            "Corpus:", "ISPARTOFCORPUS"
        }, {
            "Corpus Category:", "ISPARTOFCORPUSCATEGORY"
        }, {
            "Corpus Sub Category:", "ISPARTOFCORPUSSUBCATEGORY" //17
        }, {
            "Is Part Of:", "ISPARTOF"
        }, {
            "Is Version Of:", "ISVERSIONOF"
        }, {
            "Date of Inscription Note:", "DATEOFINSCRIPTIONNOTE"
        }, {
            "Language Note :", "LANGUAGENOTE"
        }, {
            "Language:", "LANGUAGE"
        }, {
            "Find Site:", "ISFFINDSITE"
        }, {
            "Ancient City or Location:", "ANCIENTCITYORLOCATION"
        }, {
            "Ancient Structure:", "ANCIENTSTRUCTURE"
        }, {
            "Modern City or Location:", "MODERNCITYORLOCATION"
        }, {
            "Modern Country:", "MODERNCOUNTRY"
        }, {
            "Region:", "REGION"
        }, {
            "Relevant Time Line:", "RELEVANTTIMELINE"
        }, {
            "Named Time Period:", "NAMEDTIMEPERIOD"
        }, {
            "Date Range:", "TEMPORALCOVERAGERANGE"
        }, {
            "Alternate Text or Publication Number:", "TEXTALIASES"
        }, {
            "Museum Accession Number:", "MUSEUMACCESSIONNO"
        }, {
            "Excavation Number:", "EXCAVATIONNO"
        }, {
            "Location of Original:", "LOCATIONOFORIGINAL"
        }, {
            "Rights Physical Object:", "RIGHTSPHYSICALOBJECT"
        }, {
            "Text Division:", "TEXTDIVISION"
        }, {
            "Keyword or Phrase:", "KEYWORDSORPHRASES"
        }
    };
    public static final String textDublinNames[][] = {
        {
            "Type Of Item Catalogued:", "TYPEOFITEMCATELOGED" //1
        }, {
            "Text/ Uninscribed/ Glyptic:", "TEXTUNINSCRIBEDARTIFACT" //2
        }, {
            "Main Text or Publication Number:", "MAINTEXTORPUBLCNNO" //3
        }, {
            "Text Descriptive Title:", "TEXTDESCRIPTIVETITLE" //4
        }, {
            "Text Alternate Title:", "ALTTEXTTITLE" //5
        }, {
            "Text Description:", "TEXTDESCRIPTION" //6
        }, {
            "Text or Publication Number Note:", "TEXTORPUBLCNNONOTE" //7
        }, {
            "Excavation Description: " + oStr, "EXCAVATIONDESCRIPTION" //8
        }, {
            "Geographical Coverage Note: " + oStr, "GEOGRAPHICALCOVERAGENOTE" //9
        }, {
            "Medium: " + oStr, "MEDIUM" //10
        }, {
            "Script Note :", "SCRIPTNOTE" //11
        }, {
            "Script : Sub-script " + iStr, "SCRIPT" //12
        }, {
            "Physical Object Description:", "PHYSICALOBJECTDESCRIPTION" //13
        }, {
            "Physical Object Note:", "PHYSICALOBJECTNOTE" //14
        }, {
            "ISF Assigned Text Number:", "ISFASSIGNEDTEXTNO" //15
        }, {
            "Is Part of Corpus:", "ISPARTOFCORPUS" //16
        }, {
            "Is Part of Corpus Category:", "ISPARTOFCORPUSCATEGORY" //17
        }, {
            "Is Part of Corpus Sub Category:", "ISPARTOFCORPUSSUBCATEGORY" //17
        }, {
            "Is Part Of:", "ISPARTOF" //18
        }, {
            "Is Version Of:", "ISVERSIONOF" //19
        }, {
            "Date of Inscription Note:", "DATEOFINSCRIPTIONNOTE" //20
        }, {
            "Language Note:", "LANGUAGENOTE" //21
        }, {
            "Language:", "LANGUAGE" //22
        }, {
            "Find Site: " + oStr, "ISFFINDSITE" //23
        }, {
            "Ancient City or Location: " + oStr, "ANCIENTCITYORLOCATION" //24
        }, {
            "Ancient Structure: " + oStr, "ANCIENTSTRUCTURE" //25
        }, {
            "Modern City or Location: " + oStr, "MODERNCITYORLOCATION" //26
        }, {
            "Modern Country: " + oStr, "MODERNCOUNTRY" //27
        }, {
            "Region: " + oStr, "REGION" //28
        }, {
            "Relevant Time Line:", "RELEVANTTIMELINE" //29
        }, {
            "Named Time Period:", "NAMEDTIMEPERIOD" //30
        }, /*{
         "Temporal Coverage Range:", "TEMPORALCOVERAGERANGE"
         },*/ {
            "Alternate Text or Publication Number:", "TEXTALIASES" //31
        }, {
            "Museum Accession Number: " + oStr, "MUSEUMACCESSIONNO" //32
        }, {
            "Excavation Number: " + oStr, "EXCAVATIONNO" //33
        }, {
            "Location of Original: " + oStr, "LOCATIONOFORIGINAL" //34
        }, {
            "Rights Physical Object:", "RIGHTSPHYSICALOBJECT" //35
        }, {
            "Text Division:", "TEXTDIVISION" //36
        }, {
            "Keyword or Phrase:", "KEYWORDSORPHRASES" //37
        }
    };

    private static String getString(Object obj, String s, String s1, int width, int fontsize) {


        String s2 = "<tr><td width='" + (width * .51 * .9) + "' valign='top' class='head'>" + "<b><font face='Arial' size='" + fontsize + "' color='AC5539'>";
        String s3 = "</font></b></td>";
        String s4 = "<td width='" + (width * .48 * .9) + "' class='val'>" + "<b><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></b></td></tr>";
        String s6 = s1.equals("") ? "" : ""; //"<tr><td colspan =2><hr></td></tr>"
        //String listr = "";
        StringBuffer retString = new StringBuffer();
        if (obj instanceof String) {
            // return isValid((String)obj) ? tabend + tablestart + s + s3 + rowstart + replaceSark((String)obj) + cellend + s6 : "";
            return isValid((String) obj) ? s2 + s1 + s + s3 + s4 + (String) obj + s5 + s6 : "";
        }
        if (obj instanceof Vector) {
            Vector vector = (Vector) obj;
            //  System.out.println("-------- vector  :"+vector);
            if (vector.size() == 0 || vector == null) {
                return "";
            }
            int i = 0;
            for (int j = vector.size(); i < j; i++) {
                Object obj1 = vector.elementAt(i);
                if (obj1 instanceof String) {
                    if (isValid((String) obj1)) {
                        retString.append(s2);
                        retString.append(s1);
                        retString.append(s);
                        retString.append(s3);
                        retString.append(s4);
                        retString.append((String) obj1);
                        retString.append(s5);
                        retString.append(s6);
                    }
                } else if (obj1 instanceof Vector) {

                    Vector vector1 = (Vector) obj1;
                    //    System.out.println(" ************* 1 :"+vector1);
                    //String s8 = "";
                    StringBuffer cats = new StringBuffer();
                    if (vector1.size() == 0 || vector1 == null) {
                        //return "";
                        cats = new StringBuffer();
                        //   break;
                    }

                    int k = 0;
                    for (int l = vector1.size(); k < l; k++) {
                        if (s.equals("Alternate Text or Publication Number:")) {
                            cats.append((String) vector1.elementAt(0) + " ");
                            break;
                        } else if (s.equals("Script : Sub-script") || s.startsWith("Script :")) {
                            String script = (String) vector1.elementAt(0);
                            //	System.out.println("********** 2 : "+script);
                            String subScript = "";
                            if (vector1.size() > 1) {
                                subScript = (String) vector1.elementAt(1);
                                //	System.out.println("********** 3 : "+subScript);
                                cats.append(script);
                                cats.append(" : ");
                                cats.append(subScript);
                                cats.append(" ");
                                //	System.out.println("********** 4 : "+s8);
                            } else if (script == null || script.equals(" ")) {
                                cats = new StringBuffer();
                            } else {
                                cats.append(script + " ");
                            }
                            break;
                        } else if (s.equals("Text Division:")) {
                            int index = vector1.size();
                            cats.append("<br>");
                            String sep = "";
                            for (int m = 0; m < index; m++) {
                                cats.append(sep + (String) vector1.elementAt(m));   //index-1
                                sep = " <br><br>";
                            }
                            /* cats.append( (String) vector1.elementAt(index - 3));
                             cats.append( " <br><br>" );
                             cats.append( (String) vector1.elementAt(index - 2) );*/
                            cats.append(" ");
                            break;
                        } else {
                            cats.append((String) vector1.elementAt(k) + " ");
                        }

                    }
                    if (cats != null && !cats.toString().equals("")) {
                        retString.append(s2);
                        retString.append(s1);
                        retString.append(s);
                        retString.append(s3);
                        retString.append(s4);
                        retString.append(cats.toString());
                        retString.append(s5);
                        retString.append(s6);
                    }
                }
            }

        }
        return retString.toString();
    }

    private static boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        } else {
            return !s.trim().equals("(   -   )");
        }
    }

    private static String[][] getCatDetails(int type) {
        String as[][] = null;
        switch (type) {
            case 1:
                as = textCatalogueNames;
                break;
            case 2:
                as = photoCatalogueNames;
                break;
            case 3:
                as = textDublinNames;
                break;
            case 4:
                as = photoDublinNames;
                break;
            default:
                as = textCatalogueNames;
                break;
        }
        return as;
    }

    public static String buildCatalougeData(String s, Hashtable hashtable, int width, int type) {
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        int fontsize = 2;
        if (current.width > 1450 && current.height > 900) {
            fontsize = 3;
        }else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }

        String as[][] = getCatDetails(type);

        String s1 = "<html><head><style>"
                + " .tab{background-color:gray; padding:1; margin-top:0;margin-right:0;margin-left:0;margin-bottom:0; } "
                + " .val{background-color:white;margin-bottom:1;margin-left:1;margin-right:0;margin-top:0;padding:10px;}"
                + " .head{background-color:white;margin-bottom:1; margin-left:0;margin-right:0;margin-top:0;padding:10px;} "
                + " tr{background-color:white;margin-top:0;margin-right:0;margin-left:0;margin-bottom:0;} "
                + " </style></head><body ><table align='center' width='" + width  + "' class='tab' >";
        String s2 = "</table></body></html>"; //<tr><td colspan =2></td></tr>

        /* String tablestart = "<html><head><style>" +
         ".headtext{color:#000000;font-family :Arial;font-size:12pt;font-weight :bold;text-decoration:underline;} " +
         ".bullet{color:#AC5539;font-family :Arial;font-size:11pt;font-weight :plain;text-indent:2;margin-left:10px;} " +
         ".bodytext{color:#000000;font-family :Arial;font-size:11pt;font-weight :plain;} </style> " +
         "</head><body><table border=0 align='left' width='" + width + "' cellspacing=2 cellpadding=2 style=\"background-color:black;\" >";
         String tabend = "<tr><td colspan =2></td></tr></table></body></html>";     */
        StringBuffer sb = new StringBuffer();
        sb.append(s1);

        if (s.equals(hashtable.get("ISFASSIGNEDTEXTNO"))) {
            int k = 0;
            for (int l = as.length; k < l;) {

                try {
                    if (!as[k][1].equalsIgnoreCase("ISPARTOFCORPUS")) {
                        sb.append(getString(hashtable.get(as[k][1]), as[k][0], "", width, fontsize));
                        k++;
                    } else {
                        Vector corpus = (Vector) hashtable.get(as[k][1]);
                        Vector corpusCategory = (Vector) hashtable.get(as[k + 1][1]);
                        for (int cor = 0; cor < corpus.size(); cor++) {
                            sb.append(getString((String) corpus.elementAt(cor), as[k][0], "", width, fontsize));
                            if (corpusCategory.size() > cor) {
                                sb.append(getString((String) corpusCategory.elementAt(cor), as[k + 1][0], "", width, fontsize));
                            }
                        }
                        k += 2;
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        } else if (s.equals(hashtable.get("PHOTOGRAPHIDENTIFICATIONNO"))) {
            int k = 0;
            // System.out.println("obj  before for: "+hashtable.get("TEMPORALCOVERAGERANGE"));
            for (int l = as.length; k < l; k++) {
                //    System.out.println("obj  : "+hashtable.get(as[k][1])+"s  :"+as[k][0]);
                try {    ///////////
                    if (as[k][0].equals("Time Period:")) {
                        String tempCovRange = (String) hashtable.get("TEMPORALCOVERAGERANGE");
                        if (!hashtable.get("TEMPORALCOVERAGERANGE").equals("") || hashtable.get("TEMPORALCOVERAGERANGE") != null) {
                            sb.append(getString((String) hashtable.get(as[k][1]) + tempCovRange, as[k][0], "", width, fontsize));
                        } else {
                            sb.append(getString(hashtable.get(as[k][1]), as[k][0], "", width, fontsize));
                        }

                    } else if (as[k][0].equals("Compression Ratio:")) {
                        String cratio = (String) hashtable.get(as[k][1]);
                        //System.out.println("cratio ------------ :"+cratio);
                        //String [] carr=cratio.split(":");
                        int indF = cratio.indexOf(":");
                        int indL = cratio.lastIndexOf(":");


                        String first = cratio.substring(0, indF);
                        String middle = "00";
                        String last = "00";
                        //System.out.println("indF-- "+indF+"  indL-- "+indL);
                        if (indF != indL) {
                            middle = cratio.substring(indF + 1, indL);
                            last = cratio.substring(indL + 1);
                        } else {
                            middle = cratio.substring(indL + 1);
                            //System.out.println("middle  :"+middle);
                        }

                        if (first.startsWith("0")) {
                            first = first.substring(1);
                        }
                        if (middle.startsWith("0")) {
                            middle = middle.substring(1);
                        }
                        if (last.startsWith("0")) {
                            last = last.substring(1);
                        }
                        if (Integer.parseInt(last) == 0) {
                            cratio = first + ":" + middle;
                        } else {
                            cratio = first + ":" + middle + ":" + last;
                        }
                        //System.out.println("cratio final  :"+cratio);
                        sb.append(getString(cratio, as[k][0], "", width, fontsize));
                    } else if (as[k][0].equals("Film Type:")) {
                        //    System.out.println("--------------------------------- in film type ");
                        int code = Integer.parseInt((String) hashtable.get(as[k][1]));
                        //  System.out.println(" FCode------------------------ :"+code);
                        String desc = "";
                        switch (code) {
                            case 1:
                                desc = "Color transparency";
                                break;
                            case 2:
                                desc = "Color negative";
                                break;
                            case 3:
                                desc = "Black and white infrared negative";
                                break;
                            case 4:
                                desc = "High contrast black and white negative";
                                break;
                            case 5:
                                desc = "Medium contrast black and white negative";
                                break;
                            case 6:
                                desc = "Digital color";
                                break;
                            case 7:
                                desc = "Digital infrared";
                                break;
                            case 8:
                                desc = "Digital black and white";
                                break;
                            case 9:
                                desc = "PTM";
                                break;
                            case 10:
                                desc = "RTI";
                                break;
                            default:
                                desc = code + "";
                                break;
                        }
                        // System.out.println(" desc 33333333   :"+desc);
                        sb.append(getString(desc, as[k][0], "", width, fontsize));
                    } else if (as[k][0].equals("Magnification:")) {
                        //  System.out.println("------------------------------in magnifi  ");
                        int code = Integer.parseInt((String) hashtable.get(as[k][1]));
                        // System.out.println("Mcode--------------:"+code);
                        String desc = "";
                        // boolean flag1 = false;
                        switch (code) {
                            //   System.out.println("----------------------------in MSwitch  ");
                            case 1:
                                desc = "Reference";
                                break;
                            case 2:
                                desc = "Sectional";
                                break;
                            case 3:
                                desc = "Detailed";
                                break;


                        }
                        //  System.out.println("desc  44444444444   :"+desc);
                        sb.append(getString(desc, as[k][0], "", width, fontsize));
                    } else /////////////
                    {
                        sb.append(getString(hashtable.get(as[k][1]), as[k][0], "", width, fontsize));
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        }
        sb.append(s2);
        //  }

        return sb.toString();
    }

    public static String buildDubinData(String s, Hashtable hashtable, int width, int type) {
        int fontsize = 2;
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        if (current.width > 1450 && current.height > 900) {
            fontsize = 3;
        }else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }
        String as[][] = getCatDetails(type);
        String tablestart = "<html><head><style>"
                + " .tab{background-color:gray; padding:1; margin-top:0;margin-right:0;margin-left:0;margin-bottom:0; } "
                + " .val{background-color:white;margin-bottom:1;margin-left:1;margin-right:0;margin-top:0;padding:10px;}"
                + " .head{background-color:white;margin-bottom:1; margin-left:0;margin-right:0;margin-top:0;padding:10px;} "
                + " tr{background-color:white;margin-top:0;margin-right:0;margin-left:0;margin-bottom:0;} "
                + " </style></head><body ><table align='center' width='" + width  + "' class='tab' >";
        String tabend = "</table></body></html>"; //<tr><td colspan =2></td></tr>

        /*  String tablestart = "<html><head><style>" +
         ".headtext{color:#000000;font-family :Arial;font-size:13pt;font-weight :bold;text-decoration:underline;background-color:white;margin-right:1px;} " +
         ".bullet{color:#AC5539;font-family :Arial;font-size:12pt;font-weight :plain;text-indent:2;margin-left:10px;} " +
         ".bodytext{color:#000000;font-family :Arial;font-size:12pt;font-weight :plain;} </style> " +
         "</head><body><table border=0 align='left' style=\"bakground-color:black;\" width='" + width + "' cellspacing=2 cellpadding=2>";
         String tabend = "<tr><td colspan =2></td></tr></table></body></html>";      //  String s3 = "<tr><td colspan =2><hr></td></tr>";
         */

       

        String s3 = "";
        String rowstart = "<tr><td width='" + ((width * 52) / 100 -2) + "' class='head'>" + " ";
        String cellend = "</td><td width='" + ((width * 48) / 100 +2)+ "' class='val'>" + "</td></tr>";
        String s6 = "<tr><td class='head'></td><td class='val'></td></tr>";
        String listr = "<li type=disc >";


        // String s8 = "";
        StringBuffer cat = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        sb.append(tablestart);
        //  int i = 0;
        // for (int j = mainData.size(); i < j; i++) {
        //     Hashtable hashtable = (Hashtable) mainData.elementAt(i);
        if (s.equals(hashtable.get("ISFASSIGNEDTEXTNO"))) {
            // System.out.println("length=" + as.length);
            int k = 0;
            for (int l = as.length; k < l; k++) {
                // System.out.println("Processing :" + k);
                try {
                    switch (k) {
                        case 1: // '\002'
                        case 4: // '\003'
                        case 6: // '\005'
                        case 7: // '\006'
                        case 8: // '\007'
                        case 9: // '\b'
                        case 10: // '\t'
                        case 11: // '\n'
                        case 12: // '\013'
                        case 13:
                        //  case 14: // '\016'
                        case 16: // '\017'
                        case 17: // '\020'
                        case 18:
                        case 21: // '\024'
                        case 23: // '\025'
                        case 24: // '\026'
                        case 25: // '\027'
                        case 26: // '\030'
                        case 27: // '\032'
                        case 29: // '\033'
                        case 31: // '\035'
                        case 32: // '\036'
                        case 33: // '\037'
                        case 36:
                        default:
                            break;

                        case 0: // '\0'
                            String s9 = rowstart + "TYPE" + cellend;
                            //   System.out.println("hastype");
                            String s10 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (s10.equals("")) {
                                sb.append(s9);
                                sb.append(cat.toString());
                                sb.append(s6);//+ s8 + s6);
                                //s8 = "";
                                cat = new StringBuffer();
                            }
                            if (!s10.equals("")) {
                                cat.append(s10);

                            }
                            s10 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s10.equals("")) {
                                cat.append(s10);
                            }

                            if (!cat.toString().equals("")) {
                                // sb.append(s9 + s8 + s6);
                                sb.append(s9);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 2: // '\001'
                            String s11 = rowstart + "TITLE" + cellend;
                            //  System.out.println("hastitle");
                            String s12 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s12.equals("")) {
                                cat.append(s12);
                            }
                            s12 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s12.equals("")) {
                                cat.append(s12);
                            }
                            s12 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], listr, width, fontsize);
                            if (!s12.equals("")) {
                                cat.append(s12);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s11);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 5: // '\004'
                            String s13 = rowstart + "DESCRIPTION" + cellend;
                            String s14 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], listr, width, fontsize);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], listr, width, fontsize);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 4][1]), as[k + 4][0], listr, width, fontsize);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 5][1]), as[k + 5][0], listr, width, fontsize);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 6][1]), as[k + 6][0], listr, width, fontsize);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 7][1]), as[k + 7][0], listr, width, fontsize);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 8][1]), as[k + 8][0], listr, width, fontsize);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s13);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 14: // '\f'12
                            String s15 = rowstart + "IDENTIFIER" + cellend;
                            String s16 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s16.equals("")) {
                                cat.append(s16);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s15);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 15: // '\r'13
                            String s17 = rowstart + "RELATION" + cellend;
                            //todo change k and k+1 with corpus and corpus category
                            Vector corpus = (Vector) hashtable.get(as[k][1]);
                            Vector corpusCategory = (Vector) hashtable.get(as[k + 1][1]);
                            StringBuffer vect = new StringBuffer();
                            //check for logic here ///////////////////////////******//////
                            for (int cor = 0; cor < corpus.size(); cor++) {
                                vect.append(getString((String) corpus.elementAt(cor), as[k][0], listr, width, fontsize));
                                if (corpusCategory.size() > cor) {  //>= cor causing arrayindexoutofbounds. not sure how this worked for so long.
                                    vect.append(getString((String) corpusCategory.elementAt(cor), as[k + 1][0], listr, width, fontsize));
                                }
                            }
                            String s18 = vect.toString();
                            if (!s18.equals("")) {
                                cat.append(s18);
                            }       //todo till here : uncomment lower section to revert.
                            /*  String s18 = getString(hashtable.get(as[k][1]), as[k][0], listr);
                             if (!s18.equals("")) {
                             cat.append(s18);
                             }
                             s18 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr);
                             if (!s18.equals("")) {
                             cat.append(s18);
                             }*/

                            s18 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], listr, width, fontsize);
                            if (!s18.equals("")) {
                                cat.append(s18);
                            }
                            s18 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], listr, width, fontsize);
                            if (!s18.equals("")) {
                                cat.append(s18);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s17);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 19: // '\021'17
                            String s19 = rowstart + "DATE" + cellend;
                            String s20 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s20.equals("")) {
                                cat.append(s20);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s19);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 20: // '\022'18
                            String s21 = rowstart + "LANGUAGE" + cellend;
                            String s22 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s22.equals("")) {
                                cat.append(s22);
                            }
                            s22 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s22.equals("")) {
                                cat.append(s22);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s21);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 22: // '\023'19
                            String s23 = rowstart + "GEOGRAPHICAL COVERAGE" + cellend;
                            String s24 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], listr, width, fontsize);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], listr, width, fontsize);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 4][1]), as[k + 4][0], listr, width, fontsize);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 5][1]), as[k + 5][0], listr, width, fontsize);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            /*   s24 = getString(hashtable.get(as[k + 6][1]), as[k + 6][0], listr);
                             if(!s24.equals(""))
                             {
                             cat.append( s24;
                             }*/
                            if (!cat.toString().equals("")) {
                                sb.append(s23);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 28: // '\031'25
                            String s25 = rowstart + "TEMPORAL COVERAGE" + cellend;
                            String s26 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s26.equals("")) {
                                cat.append(s26);
                            }
                            s26 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s26.equals("")) {
                                cat.append(s26);
                            }
                            /*  s26 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], listr);
                             if (!s26.equals("")) {
                             cat.append( s26);
                             }*/
                            if (!cat.toString().equals("")) {
                                sb.append(s25);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 30: // '\034'28
                            String s27 = rowstart + "SOURCE" + cellend;
                            String s28 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], listr, width, fontsize);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], listr, width, fontsize);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s27);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            // System.out.println("source is empty....");
                            break;

                        case 34: // ' '32
                            String s29 = rowstart + "RIGHTS" + cellend;

                            String s30 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s30.equals("")) {
                                cat.append(s30);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s29);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            // System.out.println("rights is empty....");
                            break;

                        case 35: // '!'33
                            String s31 = rowstart + "SUBJECT" + cellend;
                            String s32 = getString(hashtable.get(as[k][1]), as[k][0], listr, width, fontsize);
                            if (!s32.equals("")) {
                                cat.append(s32);
                            }
                            s32 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], listr, width, fontsize);
                            if (!s32.equals("")) {
                                cat.append(s32);
                            }

                            if (!cat.toString().equals("")) {
                                sb.append(s31);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            // System.out.println("subject is empty....");
                            break;
                    }
                    //   System.out.println("finished processing :" + k);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    // System.out.println("Exception:"+ exception);
                }
            }
            sb.append(tabend);
        }
        //  }
        //findNoOfRows(tablestart + sb.toString() + tabend);
        return sb.toString();
    }
}
