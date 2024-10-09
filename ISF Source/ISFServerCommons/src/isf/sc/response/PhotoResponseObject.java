package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.QueryBean;
import isf.sc.utils.InscriptConfigServerLoader;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Vector;
import org.apache.log4j.Logger;

public class PhotoResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private QueryBean queryBean;
    String isfno;
    String textdiv;
    String textsubdiv;
    Logger log = LogManager.getLogger(isf.sc.response.PhotoResponseObject.class);
    public PhotoResponseObject()
            throws Exception {
        isfno = "";
        textdiv = "";
        textsubdiv = "";
        try {
            queryBean = new QueryBean();


        } catch (Exception exception) {
            throw exception;
        }
    }

    public void setRequestObject(JavaRequestObject javarequestobject) {
        jro = javarequestobject;
    }

    public Object getResponse() {
        extractValues();
        String query = "";
        String result = "";
        String orderby = "PHOTOGRAPHIDENTIFICATIONNO";
        String columns = " distinct PHOTOGRAPHIDENTIFICATIONNO";
        int size = Integer.parseInt(InscriptConfigServerLoader.getInstance().getProperty("PhotoList"));
        String type = jro.getQuery().getType();
        if (type.contains("FT")) {
            orderby = " order by to_number(FILMTYPECODE)," + orderby;
            columns = columns + ",FILMTYPECODE ";
        } else if (type.contains("MG")) {
            orderby = " order by to_number(MAGNIFICATIONCODE)," + orderby;
            columns = columns + ",MAGNIFICATIONCODE ";
        } else if (type.contains("DP")) {
            orderby = " order by to_date(DATEOFPHOTOGRAPH,'YYYY')," + orderby;
            columns = columns + ",DATEOFPHOTOGRAPH ";
        }else
        {
            orderby = " order by "+orderby;
        }
        try {
            if (type.startsWith("textdivphotoid")) {
                query = "select PHOTOGRAPHIDENTIFICATIONNO from( select " + columns + " from photoobject a,table(isfassignedt" +
                        "extno) b, table(textdivision) c where b.column_value='" + isfno + "' and   c.column_value='" + textdiv + "' " + orderby + ")";
            } else if (type.startsWith("textsuddivphotoid")) {
                query = "select PHOTOGRAPHIDENTIFICATIONNO from( select " + columns + " from photoobject a,table(isfassignedt" +
                        "extno) b, table(textdivision) c,table(textdivisionsubdivision) d where b.column_" +
                        "value='" + isfno + "' and   c.column_value='" + textdiv + "' and d.column_value='" + textsubdiv + "' " + orderby + ")";
            } else if (type.startsWith("spatialphotoid")) {

                String sids = extractSpatial();
                if (sids == null || sids.length() == 0) {
                    // throw new Exception(" No Spatial Matches Found For The Region Selected.") ;
                    String err = "<RESPONSE TYPE='isfData' SESSIONID='" + jro.getQuery().getSessionid() + "'>";
                    err += "<ERRMSG MSG='No Records Found For The Region Selected.'/></RESPONSE>";
                    throw new Exception(err);
                }
                query = "select PHOTOGRAPHIDENTIFICATIONNO from( select " + columns + " from photoobject where PHOTOGRAPHIDENTIFICATIONNO in (" + sids + ")" + orderby + ")";

            } else if (type.startsWith("catphotoid")) {
                query = "select PHOTOGRAPHIDENTIFICATIONNO from( select " + columns + " from photoobject a,table(ISFASSIGNEDTEXTNO) b where b.column_value='" + isfno + "'" + orderby + ")";
            }



            /* try {
            if (jro.getQuery().getType().equals("textdivphotoid")) {
            query = "select distinct PHOTOGRAPHIDENTIFICATIONNO from photoobject a,table(isfassignedt" +
            "extno) b, table(textdivision) c where b.column_value='" + isfno + "' and   c.column_value='" + textdiv + "'";
            } else if (jro.getQuery().getType().equals("textsuddivphotoid")) {
            query = "select distinct PHOTOGRAPHIDENTIFICATIONNO from photoobject a,table(isfassignedt" +
            "extno) b, table(textdivision) c,table(textdivisionsubdivision) d where b.column_" +
            "value='" + isfno + "' and   c.column_value='" + textdiv + "' and d.column_value='" + textsubdiv + "'";
            }
            if (jro.getQuery().getType().equals("spatialphotoid")) {
            String sids = extractSpatial();
            if (sids == null || sids.length() == 0) {
            // throw new Exception(" No Spatial Matches Found For The Region Selected.") ;
            String err = "<RESPONSE TYPE='isfData' SESSIONID='" + jro.getQuery().getSessionid() + "'>";
            err += "<ERRMSG MSG='No Records Found For The Region Selected.'/></RESPONSE>";
            throw new Exception(err);
            }
            query = "select distinct PHOTOGRAPHIDENTIFICATIONNO from photoobject where PHOTOGRAPHIDENTIFICATIONNO in (" + sids + ")";


            }
            if (jro.getQuery().getType().equalsIgnoreCase("catphotoid")) {
            query = "select distinct PHOTOGRAPHIDENTIFICATIONNO from photoobject a,table(ISFASSIGNEDTEXTNO) b where b.column_value='" + isfno + "' order by PHOTOGRAPHIDENTIFICATIONNO ";
            }
            else if (jro.getQuery().getType().equalsIgnoreCase("catphotoidFT")) {
            query = "select PHOTOGRAPHIDENTIFICATIONNO from( select  distinct PHOTOGRAPHIDENTIFICATIONNO, filmtypecode from photoobject a,table(ISFASSIGNEDTEXTNO) b where b.column_value='" + isfno + "' order by FILMTYPECODE,PHOTOGRAPHIDENTIFICATIONNO)  ";
            }
            else if (jro.getQuery().getType().equalsIgnoreCase("catphotoidMG")) {
            query = "select PHOTOGRAPHIDENTIFICATIONNO from(select distinct PHOTOGRAPHIDENTIFICATIONNO,Magnificationcode from photoobject a,table(ISFASSIGNEDTEXTNO) b where b.column_value='" + isfno + "' order by MAGNIFICATIONCODE,PHOTOGRAPHIDENTIFICATIONNO) ";
            }
            else if (jro.getQuery().getType().equalsIgnoreCase("catphotoidDP")) {
            query = "select PHOTOGRAPHIDENTIFICATIONNO from(select distinct PHOTOGRAPHIDENTIFICATIONNO,dateofphotograph from photoobject a,table(ISFASSIGNEDTEXTNO) b where b.column_value='" + isfno + "' order by DATEOFPHOTOGRAPH,PHOTOGRAPHIDENTIFICATIONNO)";
            }*/

            Vector vector = queryBean.getIsfAssignedTextNumbers(query, jro.getQuery().getSessionid(), size, "Photo", jro.getQuery().getType());
            result = (String) vector.elementAt(0);
        //logger.debug("  result from photoresponse:\n"+result);
        } catch (Exception exception) {
            log.error(exception);
            result = exception.getMessage();
        }
        return result;

    }

    private void extractValues() {
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            // logger.debug("Size of QueryCondition" + vector1.size());
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                if (querycondition.getKey().equals("isfassignedtextno")) {
                    isfno = querycondition.getValue();
                } else if (querycondition.getKey().equals("textdivision")) {
                    textdiv = querycondition.getValue();
                } else if (querycondition.getKey().equals("textsubdivision")) {
                    textsubdiv = querycondition.getValue();
                }
            }

        }

    }

    private String extractSpatial() throws Exception {
        String s = "";
        String s1 = "";
        String s2 = "";
        String s3 = "";
        String s4 = "";
        String s5 = "";
        //try
        //{
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            //  logger.debug("Size of QueryCondition" + vector1.size());
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                if (querycondition.getKey().equals("filename")) {
                    s1 = querycondition.getValue();
                } else if (querycondition.getKey().equals("X")) {
                    s2 = querycondition.getValue();
                } else if (querycondition.getKey().equals("Y")) {
                    s3 = querycondition.getValue();
                } else if (querycondition.getKey().equals("WIDTH")) {
                    s4 = querycondition.getValue();
                } else if (querycondition.getKey().equals("HEIGHT")) {
                    s5 = querycondition.getValue();
                }
            }

        }

        s = queryBean.getIntersectingFileNames(s1, new Rectangle((new Double(s2)).intValue(), (new Double(s3)).intValue(),
                (new Double(s4)).intValue(), (new Double(s5)).intValue()), jro.getQuery().getSessionid());
        /*  }
        catch(Exception exception)
        {
        logger.debug(exception);
        }*/
        return s;
    }
}
