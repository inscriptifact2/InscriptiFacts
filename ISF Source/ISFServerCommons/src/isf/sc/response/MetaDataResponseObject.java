package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.QueryBean;
import java.io.Serializable;
import java.util.Vector;
import org.apache.log4j.Logger;

public class MetaDataResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private QueryBean queryBean;
    private Logger logger = LogManager.getLogger(isf.sc.response.MetaDataResponseObject.class);

    public MetaDataResponseObject() throws Exception {
        try {
            queryBean = new QueryBean();
        } catch (Exception exception) {
            logger.error("Create Exception", exception);
            throw exception;
        }
    }

    public void setRequestObject(JavaRequestObject javarequestobject) {
        jro = javarequestobject;
    //  logger.debug("jreq from meatadataresobj  :" + jro);
    }

    public Object getResponse() {
        String s = "";
        String s1 = "";
        StringBuffer query = new StringBuffer();
        if (jro.getQuery().getType().equals("textmetadata")) {

            query.append("select ISFASSIGNEDTEXTNO,ALTTEXTTITLE,ANCIENTCITYORLOCATION,ANCIENTSTRUCTURE,");
            query.append("DATEOFINSCRIPTIONNOTE,EXCAVATIONDESCRIPTION,EXCAVATIONNO,");
            query.append("GEOGRAPHICALCOVERAGENOTE,ISPARTOF,ISPARTOFCORPUS,ISPARTOFCORPUSCATEGORY ,");
            query.append("ISPARTOFCORPUSSUBCATEGORY,ISVERSIONOF,ISFFINDSITE,KEYWORDSORPHRASES,");
            query.append("LANGUAGE,LOCATIONOFORIGINAL,MAINTEXTORPUBLCNNO,MAINTEXTORPUBLCNNOPREFIX,");
            query.append("MAINTEXTORPUBLCNNOSUFFIX,MEDIUM,MODERNCITYORLOCATION,MODERNCOUNTRY,");
            query.append("MUSEUMACCESSIONNO,NAMEDTIMEPERIOD,PHYSICALOBJECTDESCRIPTION,");
            query.append("PHYSICALOBJECTNOTE,REGION,RELEVANTTIMELINE,");
            query.append("SCRIPT,TEXTDESCRIPTION,TEXTDESCRIPTIVETITLE,TEXTORPUBLCNNONOTE,");
            query.append("TEXTSEARCHANDDISPLAYCODE,TEXTUNINSCRIBEDARTIFACT,TYPEOFITEMCATELOGED,");
            query.append("TEXTALIASES,RIGHTSPHYSICALOBJECT,TEXTDIVISION,SCRIPTNOTE,LANGUAGENOTE, TEMPORALCOVERAGERANGE," +
                    "TEXTUNINSCRIBEDARTIFACT ");
            query.append(" from textobject  where ISFASSIGNEDTEXTNO in (");
            query.append(extractValues());
            query.append(") order by MAINTEXTORPUBLCNNO");
            logger.debug(" s1 from mro ---- \n" + s1);

        ///////////////////////////////////////////////////////////////////////////////
        } else if (jro.getQuery().getType().contains("photometadata")) {
            // logger.debug("from meatadataResObj  in photometadata");
            query.append("select ISFDIGITALOBJECTIDENTIFIER,ARCHIVALFILESIZE, MUSEUMACCESSIONNO,PHOTOTEXTOR");
            query.append("PUBLCNNONOTE,NAMEDTIMEPERIOD,TYPEOFITEMCATELOGED,TEXTVIEW,THUMBNAILHEADER,PHOTODESCRIPTIVETITLE,");
            query.append("DIGITALOBJECTTYPENOTE,PHOTOGRAPHDESCRIPTION,ANCIENTTEXTREPRESENTED,PHOTOGRAPHIDENTIFICATIONNO,");
            query.append("ISPARTOFWSRPROJECT,DATEOFDIGITALCONVERSION,DATEOFPHOTOGRAPH,LANGUAGE,SCRIPT,MEDIUM,");
            query.append("ISFFINDSITE,RELEVANTTIMELINE,PHOTOGRAPHER,DIGITIZATIONEQUIPMENTSPECS,COLABORATOR,");
            query.append("RIGHTSDIGITALOBJECT,RIGHTSPHOTOGRAPH,RIGHTSPUBLICATIONPERMISSION,RIGHTSPHYSICALOBJECT,ARCHIVALFILERESOLUTION,");
            query.append("COMPRESSEDFORMATEXTENSION,COMPRESSIONROUTINE,COMPRESSIONRATIO,DIGITALOBJECTFORMAT,");
            query.append("FILMTYPECODE,MAGNIFICATIONCODE,PHOTODIMENSIONS,imagethumbnail,PHOTOMUSEUMACCESSIONNO,");
            query.append("PHOTOMUSEUMACCESSIONNONOTE,PHOTOTEXTORPUBLCNNONOTE,LANGUAGENOTE,SCRIPTNOTE,");
            query.append("temp_cov2(NAMEDTIMEPERIOD,relevanttimeline)  TEMPORALCOVERAGERANGE from photoobject ");
            query.append(" where PHOTOGRAPHIDENTIFICATIONNO in (");
            query.append(extractValues());
            //query.append(") order by ISFDIGITALOBJECTIDENTIFIER");
            String type = jro.getQuery().getType();
            if(type.endsWith("FT"))
                query.append(") order by to_number(FILMTYPECODE),PHOTOGRAPHIDENTIFICATIONNO");
            else if(type.endsWith("DP"))
                query.append(") order by to_date(DATEOFPHOTOGRAPH,'YYYY'),PHOTOGRAPHIDENTIFICATIONNO");
            else if(type.endsWith("MG"))
                query.append(") order by to_number(MAGNIFICATIONCODE),PHOTOGRAPHIDENTIFICATIONNO");
            else
                query.append(") order by PHOTOGRAPHIDENTIFICATIONNO");

        }
        try {

            // logger.debug("from MetadataResObj 3" + s1);
            Vector vector = queryBean.getMetaData(query.toString(), jro.getQuery().getSessionid());
            //   logger.debug("from MetadataResObj 4  "+vector);
            s = (String) vector.elementAt(0);
        } catch (Exception exception) {
            logger.error("Exception", exception);
        }
        return s;
    // }
    }

    private String extractValues() {
        StringBuffer s = new StringBuffer();
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        String s1 = "";
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                s.append(s1 + "'" + querycondition.getValue() + "'");
                s1 = ",";
            }
        }
        logger.debug("Values:\n" + s.toString());
        return s.toString();
    }
}
