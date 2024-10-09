package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.QueryBean;
import isf.sc.utils.InscriptConfigServerLoader;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;

public class QueryResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private QueryBean queryBean;
    private Logger logger = LogManager.getLogger(isf.sc.response.QueryResponseObject.class);

    public QueryResponseObject()
            throws Exception {
        try {
            queryBean = new QueryBean();
        } catch (Exception exception) {
            logger.error("Exception", exception);

            throw exception;
        }
    }

    public void setRequestObject(JavaRequestObject javarequestobject) {
        jro = javarequestobject;
    }

    public Object getResponse() {
        String query = "";
        String result = "";//' "select distinct ISFASSIGNEDTEXTNO, MAINTEXTORPUBLCNNO from textobject ";
        String whereClause = "";
        String qType = jro.getQuery().getType();
        if (qType.equals("isfData") || qType.equals("isfComboData")) {
            if (qType.equals("isfData")) {
                whereClause = extractValues(new String(""), new String(""));
            } else if (qType.equals("isfComboData")) {
                whereClause = buildComboQuery();
            }
        //   s1 = s1 + "order by MAINTEXTORPUBLCNNO ";
        } else if (qType.equals("isftimedata")) {
            whereClause = extractValues("^", "!");

            int p1 = whereClause.indexOf("^");
            int p2 = whereClause.indexOf("!");
            String q1 = "";
            String q2 = "";
            if (p1 > -1) {
                q1 = whereClause.substring(0, p1 - 1);
            }
            if (p2 > -1) {
                q2 = whereClause.substring(p2 + 1, whereClause.length());
            }
            if (q1.length() == 0) {
                q1 = whereClause;
            }

          
            if (q1.length() > 0 && q2.length() > 0) {
                whereClause = " and ISFASSIGNEDTEXTNO in (" + q2 + " )  ";
            }
        }

        query = formulateFroms(whereClause);

        //  logger.debug("query called:**************\n"+query+"\n********************************\n");

        try {
            int size = Integer.parseInt(InscriptConfigServerLoader.getInstance().getProperty("QueryList"));
            Vector vector = queryBean.getIsfAssignedTextNumbers(query, jro.getQuery().getSessionid(), size, "Query",jro.getQuery().getType());
            result = (String) vector.elementAt(0);
        } catch (Exception exception) {
            logger.error("Exception", exception);

        }
        return result;


    }

    private String extractValues(String sep1, String sep2) {
        String s = "";
        StringBuffer values = new StringBuffer();
        values.append("(");
        //tring s1 = "(";
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            String s2 = clause.getConnector();
            Vector vector1 = clause.getClauses();
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                Object obj = vector1.elementAt(l);
                if (obj instanceof Clause) {
                    Clause clause1 = (Clause) obj;
                    String s4 = clause1.getConnector();
                    Vector vector3 = clause1.getQueryConditions();
                    int l1 = vector3.size();
                    if (l1 != 0) {
                        values.append(" ( ");
                    }
                    for (int i2 = 0; i2 < l1; i2++) {
                        QueryCondition querycondition1 = (QueryCondition) vector3.elementAt(i2);
                        String s6 = querycondition1.getKey();
                        String s8 = querycondition1.getValue().trim();
                        String s9 = querycondition1.getOperator();
                        String s10 = querycondition1.getConnector();
                        values.append(s6 + " " + s9 + " '" + s8 + "' " + s10 + " ");
                    }

                    values.append(") " + s4);
                } else if (obj instanceof QueryCondition);
            }

            if (s2.equals("")) {
                values.append(s2);
            } else {
                values.append(") " + sep1 + s2 + sep2 + " (");
            }
            Vector vector2 = clause.getQueryConditions();
            String s3 = clause.getConnector();
            int j1 = vector2.size();
            for (int k1 = 0; k1 < j1; k1++) {
                Object obj1 = vector2.elementAt(k1);
                if (obj1 instanceof QueryCondition) {
                    QueryCondition querycondition = (QueryCondition) obj1;
                    if (querycondition.getKey().equals("KEYWORDSORPHRASES")) {
                        String s5 = "";
                        StringBuffer tokens = new StringBuffer();
                        String s7 = "";
                        for (StringTokenizer stringtokenizer = new StringTokenizer(querycondition.getValue(), ","); stringtokenizer.hasMoreTokens();) {
                            tokens.append(s7 + "'" + stringtokenizer.nextToken() + "'");
                            s7 = ",";
                        }
                        s = s3 + " UPPER(trim(KEYWORDSORPHRASES)) in ( " + tokens.toString() + ")";
                    }
                }
            }

        }

        values.append(s + " )");
        //logger.debug("EXTRACTED STRING:\n" + s1);
        return values.toString();
    }

    private String buildComboQuery() {
        StringBuffer s = new StringBuffer();
        String s1 = "";
        String s2 = "";
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
                if (querycondition.getKey().equals("MAINTEXTORPUBLCNNOPREFIX")) {
                    s1 = querycondition.getValue();
                    s.append(" MAINTEXTORPUBLCNNOPREFIX = '" + s1 + "'");
                } else if (querycondition.getKey().equals("MAINTEXTORPUBLCNNOSUFFIX")) {
                    s.append(" and  MAINTEXTORPUBLCNNOSUFFIX = '" + querycondition.getValue() + "' or MAINTEXTORPUBLCNNO = '" + s1 + " " + querycondition.getValue() + "'");
                }
                if (querycondition.getKey().equals("ALTTEXTORPUBLCNNOPREFIX")) {
                    s2 = querycondition.getValue();
                    s.append(" or ALTTEXTORPUBLCNNOPREFIX = '" + s2 + "'");
                } else if (querycondition.getKey().equals("ALTTEXTORPUBLCNNOSUFFIX")) {
                    s.append(" and  ALTTEXTORPUBLCNNOSUFFIX = '" + querycondition.getValue() + "' or MAINTEXTORPUBLCNNO = '" + s2 + " " + querycondition.getValue() + "'");
                }
            }

        }
        //logger.debug("from qro s-- "+s);
        return s.toString();
    }

    private String formulateFroms(String where) {
        StringBuffer from = new StringBuffer();
        from.append("Select distinct ISFASSIGNEDTEXTNO, MAINTEXTORPUBLCNNO from textobject ");
        String[] searches = new String[]{"ISPARTOFCORPUSSUBCATEGORY", "ISPARTOFCORPUSCATEGORY", "ISPARTOFCORPUS", "KEYWORDSORPHRASES",
            "LANGUAGE", "LOCATIONOFORIGINAL", "NAMEDTIMEPERIOD"};
        int pos = 0;
        for (int i = 0; i < searches.length; i++) {
            if ((pos = where.indexOf(searches[i])) > -1) {
                from.append(", table(" + searches[i] + ") varray" + i + " ");
                while ((pos = where.indexOf(searches[i])) > -1) {
                    String prefix = where.substring(0, pos);
                    String suffix = where.substring(pos + (searches[i].length()));
                    where = prefix + " varray" + i + ".column_value " + suffix;
                }
            }
        }
        String[] ntStrings = new String[]{"SCRIPT", "ALTTEXTORPUBLCNNO"};
        for (int i = 0; i < ntStrings.length; i++) {
            pos = 0;
            if (where.indexOf(ntStrings[i]) > -1) {
                if (i == 0) {
                    from.append(", table(" + ntStrings[i] + ") (+) nt" + i + " ");
                    while ((pos = where.indexOf(" " + ntStrings[i] + " ")) > -1) {
                        String prefix = where.substring(0, pos);
                        String suffix = where.substring(pos + 2 + (ntStrings[i].length()));
                        where = prefix + " nt" + i + "." + ntStrings[i] + " " + suffix;
                    }
                } else {
                    from.append(", table(TEXTALIASES) (+) nt" + i + " ");
                }


            }
        }
        //TODO:ADD RESTRICTION LOGIC HERE
        from.append(" where " + where + " order by MAINTEXTORPUBLCNNO asc ");
        return from.toString();
    }
}
