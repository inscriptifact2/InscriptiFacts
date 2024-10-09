/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.sc.beans;

import isf.common.ISFBase64;
import isf.common.utils.LogManager;
import isf.common.view.gui.TextDivision;
import isf.common.view.gui.TextDivisionSubDivision;
import isf.sc.utils.SessionInvalidException;
import java.awt.Rectangle;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import oracle.sql.ARRAY;
import oracle.sql.Datum;
import oracle.sql.NUMBER;
import oracle.sql.STRUCT;
import org.apache.log4j.Logger;

/**
 *
 * @author raok1
 */
public class QueryBean extends ISFBean {

    private Logger logger = LogManager.getLogger(isf.sc.beans.QueryBean.class);
    private String displayType;
    private String topTitle;
    long start;
    private static final String QUERY_TYPE = "select ISFASSIGNEDTEXTNO,ALTTEXTTITLE,ANCIENTCITYORLOCATION,ANCIENTSTRUCTURE," +
            "DATEOFINSCRIPTIONNOTE,EXCAVATIONDESCRIPTION,EXCAVATIONNO," +
            "GEOGRAPHICALCOVERAGENOTE,ISPARTOF,ISPARTOFCORPUS,ISPARTOFCORPUSCATEGORY ," +
            "ISPARTOFCORPUSSUBCATEGORY,ISVERSIONOF,ISFFINDSITE,KEYWORDSORPHRASES," +
            "LANGUAGE,LOCATIONOFORIGINAL,MAINTEXTORPUBLCNNO,MAINTEXTORPUBLCNNOPREFIX," +
            "MAINTEXTORPUBLCNNOSUFFIX,MEDIUM,MODERNCITYORLOCATION,MODERNCOUNTRY," +
            "MUSEUMACCESSIONNO,NAMEDTIMEPERIOD,PHYSICALOBJECTDESCRIPTION," +
            "PHYSICALOBJECTNOTE,REGION,RELEVANTTIMELINE," +
            "SCRIPT,TEXTDESCRIPTION,TEXTDESCRIPTIVETITLE,TEXTORPUBLCNNONOTE," +
            "TEXTSEARCHANDDISPLAYCODE,TEXTUNINSCRIBEDARTIFACT,TYPEOFITEMCATELOGED," +
            "TEXTALIASES,RIGHTSPHYSICALOBJECT,TEXTDIVISION,SCRIPTNOTE,LANGUAGENOTE, TEMPORALCOVERAGERANGE,TEXTUNINSCRIBEDARTIFACT " +
            " from textobject  where ISFASSIGNEDTEXTNO in (";
    private static final String PHOTO_TYPE = "select ISFDIGITALOBJECTIDENTIFIER,ARCHIVALFILESIZE,MUSEUMACCESSIONNO,PHOTOTEXTOR" +
            "PUBLCNNONOTE,NAMEDTIMEPERIOD,TYPEOFITEMCATELOGED,TEXTVIEW,THUMBNAILHEADER,PHOT" +
            "ODESCRIPTIVETITLE,DIGITALOBJECTTYPENOTE,PHOTOGRAPHDESCRIPTION,ANCIENTTEXTREPRESE" +
            "NTED,PHOTOGRAPHIDENTIFICATIONNO,ISPARTOFWSRPROJECT,DA" +
            "TEOFDIGITALCONVERSION,DATEOFPHOTOGRAPH,LANGUAGE,SCRIPT,MEDIUM,ISFFINDSITE,RELE" +
            "VANTTIMELINE,PHOTOGRAPHER,DIGITIZATIONEQUIPMENTSPECS,COLABORATOR,RIGHTSDIGITALOB" +
            "JECT,RIGHTSPUBLICATIONPERMISSION,RIGHTSPHOTOGRAPH,RIGHTSPHYSICALOBJECT,ARCHIVALFILERESOLUTION," +
            "COMPRESSEDFORMATEXTENSION,COMPRESSIONROUTINE,COMPRESSIONRATIO,DIGITALOBJECTFOR" +
            "MAT,FILMTYPECODE,MAGNIFICATIONCODE,PHOTODIMENSIONS,IMAGETHUMBNAIL," + //IMAGETHUMBNAIL,
            "PHOTOMUSEUMACCESSIONNO,PHOTOMUSEUMACCESSIONNONOTE,PHOTOTEXTORPUBLCNNONOTE," +
            "LANGUAGENOTE,SCRIPTNOTE,temp_cov2(NAMEDTIMEPERIOD,relevanttimeline)  TEMPORALCOVERAGERANGE from photoobject " +
            " where PHOTOGRAPHIDENTIFICATIONNO in (";
    private static final String END_QUERY = ") order by MAINTEXTORPUBLCNNO";
   // private static final String END_PHOTO = ") order by ISFDIGITALOBJECTIDENTIFIER";
     private static final String END_PHOTO = ") order by PHOTOGRAPHIDENTIFICATIONNO";
      private static final String END_FILMTYPE = ") order by to_number(FILMTYPECODE),PHOTOGRAPHIDENTIFICATIONNO";
       private static final String END_DATE = ") order by to_date(DATEOFPHOTOGRAPH,'YYYY'),PHOTOGRAPHIDENTIFICATIONNO";
        private static final String END_MAGNIFICATION = ") order by to_number(MAGNIFICATIONCODE),PHOTOGRAPHIDENTIFICATIONNO";

    public QueryBean() {
        // TODO implement ejbCreate if necessary, acquire resources
        // This method has access to the JNDI context so resource aquisition
        // spanning all methods can be performed here such as home interfaces
        // and data sources.

        displayType = "";
        topTitle = "";

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    public Vector getIsfAssignedTextNumbers(String queryString, String session, int numRec, String mode,String type)
            throws SessionInvalidException, Exception {
        String s1 = "<RESPONSE TYPE='isfData' SESSIONID='" + session + "'>";
        start = System.currentTimeMillis();
        long totRec = 0L;
        StringBuffer results = new StringBuffer();
        String sep = "";
        setupConnection();
      //  logger.debug("Executing getIsfAssignedTextNumbers:"+queryString);
        ResultSet resultset = st.executeQuery(queryString);
        while (resultset.next()) {

            results.append(sep + "'" + resultset.getString(1) + "'");
            totRec++;
            if (totRec % numRec == 0 && totRec > 0) {
                sep = "#";
            } else {
                sep = ",";
            }

        }
        resultset.close();
        if (totRec == 0) {
            results.append(s1);
            results.append("<ERRMSG MSG='No Records Found For The Query.'/></RESPONSE>");
            Vector v = new Vector();
            v.addElement(results.toString());
            release();
            return v;
        }
        //logger.debug("finished getting isfassigned ended in : "+(System.currentTimeMillis() -start));
        //logger.debug("Query:"+results.toString());
        return getMetaData(mode, session, totRec, results.toString(),type);

    }

    public Vector getMetaData(String s, String session) throws SessionInvalidException, Exception {
      
        start = System.currentTimeMillis();
        //logger.debug("Calling getting metadata: "+start);
        Vector vector = new Vector();
        //String s1 = "<RESPONSE  SESSIONID='" + session + "'>";
        StringBuffer sb = new StringBuffer();
        logger.debug("Getting MetaData:" + s);
        sb.append("<RESPONSE  SESSIONID='" + session + "'>");
        boolean flag = true;
        try {
            /////////////////////////////////////////////////////////////////////////////////////updateSession(session);
            setupConnection();
         //   logger.debug("Executing getMetaData:"+s);
            ResultSet resultset = st.executeQuery(s);
            logger.debug(s);
            //logger.debug("got results metadata: "+start);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            int record = 0;
            while (resultset.next()) {
                //  s1 = s1 + "<RECORD>";
                logger.debug("Getting Record:" + (record++));
                
                sb.append("<RECORD>");
                for (int j = 0; j < i; j++) {
                    String s2 = resultsetmetadata.getColumnName(j + 1);
                    Object obj = resultset.getObject(j + 1);
                    logger.debug("Getting Record:Column: " + s2 + ":value:" + obj);


                    if (obj != null) {
                        //logger.debug(s2+"--"+obj.getClass().toString() );
                        if (obj instanceof String) {
                            // s1 = s1 + "<RECORDVALUES KEY='" + s2 + "' VALUE='" + ISFBase64.getVal(((String) obj)) + "' TYPE='CV'> </RECORDVALUES>";
                            sb.append("<RECORDVALUES KEY='" + s2 + "' VALUE='" + ISFBase64.getVal(((String) obj)) + "' TYPE='CV'> </RECORDVALUES>");
                        } else if (obj instanceof weblogic.jdbc.wrapper.Array) {
                          
                           oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
             
                            String s3 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='VA'>";
                            StringBuffer va = new StringBuffer();
                            va.append(s3);
                            Datum adatum[] = array.getOracleArray();

                            for (int k = 0; k < adatum.length; k++) {
                                if (adatum[k] instanceof STRUCT) {
                                    flag = false;
                                    String s5 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='NT'><RECORDVALUES KEY='' VALUE='' TYPE=''>";
                                    Datum adatum1[] = ((STRUCT) adatum[k]).getOracleAttributes();
                                    ResultSetMetaData structData = ((STRUCT) adatum[k]).getDescriptor().getMetaData();


                                    for (int l = 0; l < adatum1.length; l++) {
                                        String s7 = "";

                                        if (!(adatum1[l] instanceof ARRAY) && !(adatum1[l] instanceof NUMBER)) {
                                            if (adatum1[l] != null) {
                                                s7 = (new String(adatum1[l].getBytes())).trim();
                                            }
                                           // logger.debug(s2 + ":column " + l);
                                            s5 = s5 + "<RECORDVALUES KEY='Column" + l + "' VALUE='" + ISFBase64.getVal(s7) + "' TYPE='NTV'></RECORDVALUES>";
                                        }
                                    }

                                    sb.append(s5 + "</RECORDVALUES></RECORDVALUES>");
                                } else {
                                    flag = true;
                                    String s6 = "";
                                    if (adatum[k] != null) {
                                        s6 = (new String(adatum[k].getBytes())).trim();
                                        s3 = s3 + "<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>";

                                    }
                                //   s3 = s3 + "<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>";
                                }
                            }

                            if (flag) {
                                sb.append(s3 + "</RECORDVALUES>");
                            }
                        } else if (obj instanceof Blob) {
                            Blob blob = (Blob) obj;
                            byte abyte0[] = blob.getBytes(1L, (int) blob.length());
                            String s4 = ISFBase64.encodeBytes(abyte0);
                            sb.append("<IMAGE ID='" + s2 + "' START='' SIZE='' TYPE='' EXTENSION='JPG'>");
                            sb.append("<IMAGE_DATA DATA='" + s4 + "' /></IMAGE>");
                        } else if (obj instanceof ARRAY) {
                            String s3 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='VA'>";
                            Datum adatum[] = ((ARRAY) obj).getOracleArray();

                            for (int k = 0; k < adatum.length; k++) {
                                if (adatum[k] instanceof STRUCT) {
                                    flag = false;
                                    String s5 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='NT'><RECORDVALUES KEY='' VALUE='' TYPE=''>";
                                    Datum adatum1[] = ((STRUCT) adatum[k]).getOracleAttributes();
                                    ResultSetMetaData structData = ((STRUCT) adatum[k]).getDescriptor().getMetaData();
                                    logger.debug("adatum1[struct]-- " + adatum1.length);
                                    for (int l = 0; l < adatum1.length; l++) {
                                        String s7 = "";
                                        if (!(adatum1[l] instanceof ARRAY) && !(adatum1[l] instanceof NUMBER)) {
                                            if (adatum1[l] != null) {
                                                s7 = (new String(adatum1[l].getBytes())).trim();
                                            }
                                            //structData.getColumnName(l + 1)
                                            //logger.debug(s2 + ":column " + l);
                                            s5 = s5 + "<RECORDVALUES KEY='Column" + l + "' VALUE='" + ISFBase64.getVal(s7) + "' TYPE='NTV'></RECORDVALUES>";
                                        }
                                    }

                                    sb.append(s5 + "</RECORDVALUES></RECORDVALUES>");
                                } else {
                                    flag = true;
                                    String s6 = "";
                                    if (adatum[k] != null) {
                                        s6 = (new String(adatum[k].getBytes())).trim();
                                        s3 = s3 + "<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>";

                                    }
                                //s3 = s3 + "<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>";
                                }
                            }

                            if (flag) {
                                sb.append(s3 + "</RECORDVALUES>");
                            }
                        }
                    }
                }
                sb.append("</RECORD>");
                logger.debug("RECORD COMPLETED:" + record);
            }
            sb.append("</RESPONSE>");
            resultset.close();
        } catch (Exception exception) {
            //log.error(exception.printStackTrace();
            sb.append("<ERRMSG MSG='" + exception.getMessage() + "' /></RESPONSE>");
            logger.error("Exception", exception);

        } finally {
            vector.addElement(sb.toString());
            //  st.close();
            //   con.close();
            //logger.debug("Finisged getmetadata:"+(System.currentTimeMillis() -start));
            release();

        }
        return vector;
    }

    public byte[] getImageIcon(String s, String session)
            throws SessionInvalidException, Exception {
        byte abyte0[] = null;
        setupConnection();
     //   logger.debug("Executing GetImageIcon:"+s);
        ResultSet resultset = st.executeQuery(s);
        if (resultset.next()) {
            Blob blob = resultset.getBlob(1);
            abyte0 = blob.getBytes(1L, (int) blob.length());
        }
        resultset.close();
        release();
        return abyte0;

    }

    public String getIntersectingFileNames(String s, Rectangle rectangle, String session)
            throws SessionInvalidException, Exception {
        String s1 = "";
        String s2 = " select  DETAILEDFILE, DETAILEDBOTTOM, DETAILEDHEIGHT,DETAILEDLEFT, DETAILEDWIDT" +
                "H from SPATIALREFERENCING where REFERENCEFILE='" + s + "'";
        //logger.debug("intersecting file name:\n"+s2);
        int i = 0;
        //   try
        //   {
        ////////////////////////////////////////////////////////////////////////////////////////updateSession(session);

        setupConnection();
     //   logger.debug("Executing getIntersectingFileNames:"+s2);
        ResultSet resultset = st.executeQuery(s2);
        Rectangle rectangle1 = new Rectangle(rectangle.x, rectangle.y - rectangle.height, rectangle.width,
                rectangle.height);
        String s3 = "";
        while (resultset.next()) {
            i++;
            String s4 = resultset.getString(1).trim();
            if (!s4.equals("")) {
                int j = Integer.parseInt(resultset.getString(2).trim());
                int k = Integer.parseInt(resultset.getString(3).trim());
                int l = Integer.parseInt(resultset.getString(4).trim());
                int i1 = Integer.parseInt(resultset.getString(5).trim());
                Rectangle rectangle2 = new Rectangle(l, j, i1, k);
                if (rectangle2.intersects(rectangle1)) {
                    s1 = s1 + s3 + "'" + s4 + "'";
                    s3 = ",";
                } else {
                    //  logger.debug("Rejected:" + rectangle2.intersection(rectangle));
                }
            }
        }
        resultset.close();
        release();

        return s1;
    }

    public Vector getTextDivisionSpatialData(Object obj, String session)
            throws SQLException, SessionInvalidException, Exception {
        Vector vector = new Vector();
        String s = "";
        String s1 = "";
        String s2 = "<RESPONSE TYPE='spatilData' SESSIONID='" + session + "'>";
        StringBuffer result = new StringBuffer();
        result.append(s2);

        try {
            setupConnection();
            if (obj instanceof TextDivision) {
                TextDivision textdivision = (TextDivision) obj;
                s = "select a.PHOTOGRAPHIDENTIFICATIONNO,b.TEXTVIEW, a.IMAGETHUMBNAIL,a.ISFdigitalobjectidentifier from photoobje" +
                        "ct a,indexmap b where a.PHOTOGRAPHIDENTIFICATIONNO=b.indexmapfilename and b.isfa" +
                        "ssignedtextno='" + textdivision.getIsfAssignedTextNo().trim() + "' and b.textdivision='" + textdivision.getTextDivisionName().trim() + "' order by a.isfdigitalobjectidentifier";
                s1 = "<RECORDVALUES KEY='TEXTDESCRIPTIVETITLE' VALUE='" + textdivision.getTextDescTitle() + "' TYPE='CV'> </RECORDVALUES>";
            }
            if (obj instanceof TextDivisionSubDivision) {
                TextDivisionSubDivision textdivisionsubdivision = (TextDivisionSubDivision) obj;
                s = "select a.PHOTOGRAPHIDENTIFICATIONNO,b.TEXTVIEW, a.IMAGETHUMBNAIL,a.ISFdigitalobjectidentifier  from photoobje" +
                        "ct a,indexmap b where a.PHOTOGRAPHIDENTIFICATIONNO=b.indexmapfilename and b.isfa" +
                        "ssignedtextno='" + textdivisionsubdivision.getIsfAssignedTextNo().trim() + "' and b.TEXTDIVISION='" + textdivisionsubdivision.getTextDivisionName().trim() + "' and" + " b. TEXTSUBDIVISION='" + textdivisionsubdivision.getTextDivisionSubDivisionName().trim() + "' order by a.isfdigitalobjectidentifier";
                s1 = "<RECORDVALUES KEY='TEXTDESCRIPTIVETITLE' VALUE='" + textdivisionsubdivision.getTextDescTitle() + "' TYPE='CV'> </RECORDVALUES>";
            }
            if (obj instanceof String) {
                s = "select a.PHOTOGRAPHIDENTIFICATIONNO, a.IMAGETHUMBNAIL, b.TEXTVIEW,b.maintextorpu" +
                        "blcnno,c.textdescriptivetitle,a.ISFdigitalobjectidentifier from photoobject a, indexmap b,textobject c where " +
                        "a.PHOTOGRAPHIDENTIFICATIONNO=b.indexmapfilename and b.isfassignedtextno='" + (String) obj + "' and c.isfassignedtextno=b.isfAssignedTextNo and  b.textdivision is null and b." + "textsubdivision is null order by a.isfdigitalobjectidentifier";
            }
    //        logger.debug("Executing getTextDivisionSpatialData:"+s);
            ResultSet resultset = st.executeQuery(s);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            while (resultset.next()) {
                result.append("<RECORD>");
                for (int j = 0; j < i; j++) {
                    Object obj1 = resultset.getObject(j + 1);
                    if (obj1 instanceof String) {
                        result.append("<RECORDVALUES KEY='" + resultsetmetadata.getColumnName(j + 1) + "' VALUE='" + ISFBase64.getVal(((String) obj1).trim()) + "' TYPE='CV'> </RECORDVALUES>");
                    } else if (obj1 instanceof Blob) {
                        Blob blob = (Blob) obj1;
                        byte abyte0[] = blob.getBytes(1L, (int) blob.length());
                        String s3 = ISFBase64.encodeBytes(abyte0);
                        result.append("<IMAGE ID='" + resultsetmetadata.getColumnName(j + 1) + "' START='' SIZE='' TYPE='' EXTENSION='JPG'>");
                        result.append("<IMAGE_DATA DATA='" + s3 + "' /></IMAGE>");
                    }
                }

                result.append(s1 + "</RECORD>");
            }
            result.append("</RESPONSE>");
        } catch (Exception ie) {
            result.append("<ERRMSG MSG='" + ie.getMessage() + "'/></RESPONSE>");
            logger.error("Exception", ie);

        } finally {
            vector.addElement(result.toString());
            release();

        }
        return vector;
    }

    public String getSearchDisplayTypeCode() {
        return displayType;
    }

    public Vector getTextDivisionPanelData(String s, String session)
            throws SessionInvalidException, Exception {
        Vector vector = new Vector();
        String s1 = "<RESPONSE TYPE='spatilData' SESSIONID='" + session + "'>";
        StringBuffer result = new StringBuffer();
        result.append(s1);
        try {
            setupConnection();
            String s2 = "select SPATIALSEARCHCODE,TEXTDIVISION,TEXTDIVISIONSUBDIVISION";
            s2 = s2 + ",MAINTEXTORPUBLCNNO,TEXTDESCRIPTIVETITLE,ISFASSIGNEDTEXTNO,TEXTDIVISIONDESCRIPTI" +
                    "ON,TEXTDIVISIONMUSEUMNO";
            s2 = s2 + " from textdivision_ov";
            s2 = s2 + " where ISFASSIGNEDTEXTNO = '" + s + "' order by TEXTDIVISIONSEQNO,TEXTDIVISIONSUBDIVISIONSEQNO";
  //          logger.debug("Executing getTextDivisionPanelData:"+s2);
            ResultSet resultset = st.executeQuery(s2);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            while (resultset.next()) {
                result.append("<RECORD>");
                for (int j = 0; j < i; j++) {
                    result.append("<RECORDVALUES KEY='" + resultsetmetadata.getColumnName(j + 1) + "' VALUE='" + ISFBase64.getVal(resultset.getString(j + 1)) + "' TYPE='CV'> </RECORDVALUES>");
                }

                result.append("</RECORD>");
            }
            result.append("</RESPONSE>");
            resultset.close();
        } catch (Exception exception) {
            result.append("<ERRMSG MSG='" + exception.getMessage() + "'/></RESPONSE>");
            logger.error("Exception", exception);

        } finally {
            vector.addElement(result.toString());
            release();

        }
        return vector;
    }

    private void setTopTitle(String s) {
        topTitle = s;
    }

    public String getTopTitle() {
        return topTitle;
    }

    private Vector getMetaData(String mode, String session, long rec, String list, String type) {
       
        Vector vector = new Vector();
        //logger.debug("getting metadata at: "+(System.currentTimeMillis() -start));
        start = System.currentTimeMillis();
        // String s1 = "<RESPONSE  SESSIONID='" + session + "' RCOUNT='"+rec+"' LIST=\""+list+"\" >";
        StringBuffer sb = new StringBuffer();
        sb.append("<RESPONSE  SESSIONID='" + session + "' RCOUNT='" + rec + "' LIST=\"" + list + "\" >");
        boolean flag = true;
        StringTokenizer strTok = new StringTokenizer(list, "#");

        if (mode.startsWith("Query")) {
            mode = QUERY_TYPE + strTok.nextToken() + END_QUERY;
        } else {
            
            if(type.endsWith("FT"))
                     mode = PHOTO_TYPE + strTok.nextToken() + END_FILMTYPE;
            else if(type.endsWith("MG"))
                     mode = PHOTO_TYPE + strTok.nextToken() + END_MAGNIFICATION;
            else if(type.endsWith("DP"))
                     mode = PHOTO_TYPE + strTok.nextToken() + END_DATE;
            else
                 mode = PHOTO_TYPE + strTok.nextToken() + END_PHOTO;
        }
 
        try {
           logger.debug("Executing private getMetaData:"+mode);
            ResultSet resultset = st.executeQuery(mode);
         
            //logger.debug("records received:"+(System.currentTimeMillis() -start));
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            while (resultset.next()) {
                //s1 = s1 + "<RECORD>";
                sb.append("<RECORD>");
                for (int j = 0; j < i; j++) {
                    String s2 = resultsetmetadata.getColumnName(j + 1);
                    Object obj = resultset.getObject(j + 1);

                
                    //  if(!(obj instanceof String) || !(obj instanceof Blob))
                    if (obj != null) {

                        if (obj instanceof String) {
                            // s1 = s1 + "<RECORDVALUES KEY='" + s2 + "' VALUE='" + ISFBase64.getVal(((String) obj)) + "' TYPE='CV'> </RECORDVALUES>";
                            sb.append("<RECORDVALUES KEY='" + s2 + "' VALUE='" + ISFBase64.getVal(((String) obj)) + "' TYPE='CV'> </RECORDVALUES>");
                        } else if (obj instanceof weblogic.jdbc.wrapper.Array) {//instanceof weblogic.jdbc.vendor.oracle.OracleArray ) {
                            
             
               oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
             
                            String s3 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='VA'>";
                            StringBuffer va = new StringBuffer();
                            va.append(s3);
                            Datum adatum[] = array.getOracleArray();

                            for (int k = 0; k < adatum.length; k++) {
                                if (adatum[k] instanceof STRUCT) {
                                    flag = false;
                                    StringBuffer temp = new StringBuffer();
                                    String s5 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='NT'><RECORDVALUES KEY='' VALUE='' TYPE=''>";
                                    temp.append(s5);
                                    Datum adatum1[] = ((STRUCT) adatum[k]).getOracleAttributes();
                                    ResultSetMetaData structData = ((STRUCT) adatum[k]).getDescriptor().getMetaData();


                                    for (int l = 0; l < adatum1.length; l++) {
                                        String s7 = "";

                                        if (!(adatum1[l] instanceof ARRAY) && !(adatum1[l] instanceof NUMBER)) { //ARRAY
                                            if (adatum1[l] != null) {
                                                s7 = (new String(adatum1[l].getBytes())).trim();
                                            }
                                           
                                            temp.append("<RECORDVALUES KEY='Column" + l + "' VALUE='" + ISFBase64.getVal(s7) + "' TYPE='NTV'></RECORDVALUES>");
                                        }
                                    }

                                    sb.append(temp.toString() + "</RECORDVALUES></RECORDVALUES>");
                                } else {
                                    flag = true;
                                    String s6 = "";
                                    if (adatum[k] != null) {
                                        s6 = (new String(adatum[k].getBytes())).trim();
                                        va.append("<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>");
                                    }

                                }
                            }

                            if (flag) {
                                sb.append(va.toString() + "</RECORDVALUES>");
                            }
                        } else if (obj instanceof Blob) {
                            Blob blob = (Blob) obj;
                            byte abyte0[] = blob.getBytes(1L, (int) blob.length());
                            String s4 = ISFBase64.encodeBytes(abyte0);
                            sb.append("<IMAGE ID='" + s2 + "' START='' SIZE='' TYPE='' EXTENSION='JPG'>");
                            sb.append("<IMAGE_DATA DATA='" + s4 + "' /></IMAGE>");
                        } else if (obj instanceof ARRAY) {
                            //logger.debug("entered in Arrayweblogic:"+s2);
                            String s3 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='VA'>";
                            StringBuffer va = new StringBuffer();
                            va.append(s3);
                            Datum adatum[] = ((ARRAY) obj).getOracleArray();
                            for (int k = 0; k < adatum.length; k++) {
                                if (adatum[k] instanceof STRUCT) {
                                  //  logger.debug("entered in Structweblogic:" + s2);
                                    flag = false;
                                    StringBuffer temp = new StringBuffer();
                                    String s5 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='NT'><RECORDVALUES KEY='' VALUE='' TYPE=''>";
                                    temp.append(s5);
                                    Datum adatum1[] = ((STRUCT) adatum[k]).getOracleAttributes();
                                    ResultSetMetaData structData = ((STRUCT) adatum[k]).getDescriptor().getMetaData();

                                    //= struct.getOracleAttributes();
                                    //      logger.debug("adatum1[]-- " + adatum1.length);
                                    for (int l = 0; l < adatum1.length; l++) {
                                        String s7 = "";

                                        if (!(adatum1[l] instanceof ARRAY) && !(adatum1[l] instanceof NUMBER)) { //ARRAY
                                            if (adatum1[l] != null) {
                                                s7 = (new String(adatum1[l].getBytes())).trim();
                                            }

                                            //structData.getColumnName(l + 1)
                                           // logger.debug(s2 + ":column " + l);
                                            temp.append("<RECORDVALUES KEY='Column" + l + "' VALUE='" + ISFBase64.getVal(s7) + "' TYPE='NTV'></RECORDVALUES>");
                                        }
                                    }

                                    sb.append(temp.toString() + "</RECORDVALUES></RECORDVALUES>");
                                } else {
                                    flag = true;
                                    String s6 = "";
                                    if (adatum[k] != null) {
                                        s6 = (new String(adatum[k].getBytes())).trim();
                                        va.append("<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>");
                                    }

                                // va.append("<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>");
                                }
                            }

                            if (flag) {
                                sb.append(va.toString() + "</RECORDVALUES>");
                            }
                        }
                    }
                }
                sb.append("</RECORD>");
            }
            sb.append("</RESPONSE>");
            resultset.close();
        } catch (Exception exception) {
            //exception.printStackTrace();
            sb.append("<ERRMSG MSG='" + exception.getMessage() + "' /></RESPONSE>");
            logger.error("Exception", exception);

        } finally {
            vector.addElement(sb.toString());
            release();

        //logger.debug("finished converting data to xml: " +(System.currentTimeMillis() -start));

        }
        return vector;
    }
    
    
    public Vector executeQuery(String query) {
       
        Vector vector = new Vector();
        //logger.debug("getting metadata at: "+(System.currentTimeMillis() -start));
        start = System.currentTimeMillis();
        // String s1 = "<RESPONSE  SESSIONID='" + session + "' RCOUNT='"+rec+"' LIST=\""+list+"\" >";
        StringBuffer sb = new StringBuffer();
        sb.append("<RESPONSE  SESSIONID='" + "123" + "' RCOUNT='" + 5 + "' LIST=\"" + "" + "\" >");
        boolean flag = true;
   
        try {
            setupConnection();
  //         logger.debug("Executing private getMetaData:"+mode);
            ResultSet resultset = st.executeQuery(query);

            //logger.debug("records received:"+(System.currentTimeMillis() -start));
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            while (resultset.next()) {
                //s1 = s1 + "<RECORD>";
                sb.append("<RECORD>");
                for (int j = 0; j < i; j++) {
                    String s2 = resultsetmetadata.getColumnName(j + 1);
                    Object obj = resultset.getObject(j + 1);

                
                    //  if(!(obj instanceof String) || !(obj instanceof Blob))
                    if (obj != null) {

                        if (obj instanceof String) {
                            // s1 = s1 + "<RECORDVALUES KEY='" + s2 + "' VALUE='" + ISFBase64.getVal(((String) obj)) + "' TYPE='CV'> </RECORDVALUES>";
                            sb.append("<RECORDVALUES KEY='" + s2 + "' VALUE='" + ISFBase64.getVal(((String) obj)) + "' TYPE='CV'> </RECORDVALUES>");
                        } else if (obj instanceof weblogic.jdbc.wrapper.Array) {//instanceof weblogic.jdbc.vendor.oracle.OracleArray ) {
                            
                           
               oracle.sql.ARRAY array = (oracle.sql.ARRAY)(((weblogic.jdbc.wrapper.Array)obj).unwrap(Class.forName("oracle.sql.ARRAY")));
             
                            String s3 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='VA'>";
                            StringBuffer va = new StringBuffer();
                            va.append(s3);
                            Datum adatum[] = array.getOracleArray();

                            for (int k = 0; k < adatum.length; k++) {
                                if (adatum[k] instanceof STRUCT) {
                                    flag = false;
                                    StringBuffer temp = new StringBuffer();
                                    String s5 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='NT'><RECORDVALUES KEY='' VALUE='' TYPE=''>";
                                    temp.append(s5);
                                    Datum adatum1[] = ((STRUCT) adatum[k]).getOracleAttributes();
                                    ResultSetMetaData structData = ((STRUCT) adatum[k]).getDescriptor().getMetaData();


                                    for (int l = 0; l < adatum1.length; l++) {
                                        String s7 = "";

                                        if (!(adatum1[l] instanceof ARRAY) && !(adatum1[l] instanceof NUMBER)) { //ARRAY
                                            if (adatum1[l] != null) {
                                                s7 = (new String(adatum1[l].getBytes())).trim();
                                            }
                                           
                                            temp.append("<RECORDVALUES KEY='Column" + l + "' VALUE='" + ISFBase64.getVal(s7) + "' TYPE='NTV'></RECORDVALUES>");
                                        }
                                    }

                                    sb.append(temp.toString() + "</RECORDVALUES></RECORDVALUES>");
                                } else {
                                    flag = true;
                                    String s6 = "";
                                    if (adatum[k] != null) {
                                        s6 = (new String(adatum[k].getBytes())).trim();
                                        va.append("<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>");
                                    }

                                }
                            }

                            if (flag) {
                                sb.append(va.toString() + "</RECORDVALUES>");
                            }
                        } else if (obj instanceof Blob) {
                            Blob blob = (Blob) obj;
                            byte abyte0[] = blob.getBytes(1L, (int) blob.length());
                            String s4 = ISFBase64.encodeBytes(abyte0);
                            sb.append("<IMAGE ID='" + s2 + "' START='' SIZE='' TYPE='' EXTENSION='JPG'>");
                            sb.append("<IMAGE_DATA DATA='" + s4 + "' /></IMAGE>");
                        } else if (obj instanceof ARRAY) {
                            //logger.debug("entered in Arrayweblogic:"+s2);
                            String s3 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='VA'>";
                            StringBuffer va = new StringBuffer();
                            va.append(s3);
                            Datum adatum[] = ((ARRAY) obj).getOracleArray();
                            for (int k = 0; k < adatum.length; k++) {
                                if (adatum[k] instanceof STRUCT) {
                                  //  logger.debug("entered in Structweblogic:" + s2);
                                    flag = false;
                                    StringBuffer temp = new StringBuffer();
                                    String s5 = "<RECORDVALUES KEY='" + s2 + "' VALUE='' TYPE='NT'><RECORDVALUES KEY='' VALUE='' TYPE=''>";
                                    temp.append(s5);
                                    Datum adatum1[] = ((STRUCT) adatum[k]).getOracleAttributes();
                                    ResultSetMetaData structData = ((STRUCT) adatum[k]).getDescriptor().getMetaData();

                                    //= struct.getOracleAttributes();
                                    //      logger.debug("adatum1[]-- " + adatum1.length);
                                    for (int l = 0; l < adatum1.length; l++) {
                                        String s7 = "";

                                        if (!(adatum1[l] instanceof ARRAY) && !(adatum1[l] instanceof NUMBER)) { //ARRAY
                                            if (adatum1[l] != null) {
                                                s7 = (new String(adatum1[l].getBytes())).trim();
                                            }

                                            //structData.getColumnName(l + 1)
                                           // logger.debug(s2 + ":column " + l);
                                            temp.append("<RECORDVALUES KEY='Column" + l + "' VALUE='" + ISFBase64.getVal(s7) + "' TYPE='NTV'></RECORDVALUES>");
                                        }
                                    }

                                    sb.append(temp.toString() + "</RECORDVALUES></RECORDVALUES>");
                                } else {
                                    flag = true;
                                    String s6 = "";
                                    if (adatum[k] != null) {
                                        s6 = (new String(adatum[k].getBytes())).trim();
                                        va.append("<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>");
                                    }

                                // va.append("<RECORDVALUES KEY='' VALUE='" + ISFBase64.getVal(s6) + "' TYPE=''></RECORDVALUES>");
                                }
                            }

                            if (flag) {
                                sb.append(va.toString() + "</RECORDVALUES>");
                            }
                        }
                    }
                }
                sb.append("</RECORD>");
            }
            sb.append("</RESPONSE>");
            resultset.close();
        } catch (Exception exception) {
            //exception.printStackTrace();
            sb.append("<ERRMSG MSG='" + exception.getMessage() + "' /></RESPONSE>");
            logger.error("Exception", exception);

        } finally {
            vector.addElement(sb.toString());
            release();

        //logger.debug("finished converting data to xml: " +(System.currentTimeMillis() -start));

        }
        return vector;
    }
}
