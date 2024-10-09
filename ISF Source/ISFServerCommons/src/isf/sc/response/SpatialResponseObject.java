package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.common.view.gui.TextDivision;
import isf.common.view.gui.TextDivisionSubDivision;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.QueryBean;
import java.io.Serializable;
import java.util.Vector;
import org.apache.log4j.Logger;

public class SpatialResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private QueryBean queryBean;
    private Logger logger = LogManager.getLogger(isf.sc.response.SpatialResponseObject.class);

    public SpatialResponseObject()
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
        String s = "";
        try {
            Object obj = extractValues();
            Vector vector = queryBean.getTextDivisionSpatialData(obj, jro.getQuery().getSessionid());
            s = (String) vector.elementAt(0);
        } catch (Exception exception) {
            logger.error("Exception", exception);
        }
        return s;

    }

    private Object extractValues() {
        String s = "";
        String s1 = "";
        String s3 = "";
        TextDivision textdivision = null;
        TextDivisionSubDivision textdivisionsubdivision = null;
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
                    s = querycondition.getValue();
                }
                if (querycondition.getKey().equals("textdesctitle")) {
                    s3 = querycondition.getValue();
                }
                if (querycondition.getKey().equals("textdivision")) {
                    s1 = querycondition.getValue();
                    textdivision = new TextDivision();
                    textdivision.setIsfAssignedTextNo(s);
                    textdivision.setTextDescTitle(s3);
                    textdivision.setTextDivisionName(querycondition.getValue());
                }
                if (querycondition.getKey().equals("textsubdivision")) {
                    String s4 = querycondition.getValue();
                    textdivisionsubdivision = new TextDivisionSubDivision();
                    textdivisionsubdivision.setIsfAssignedTextNo(s);
                    textdivisionsubdivision.setTextDivisionSubDivisionName(s1);
                    textdivisionsubdivision.setTextDescTitle(s3);
                    textdivisionsubdivision.setTextDivisionName(s4);
                }
            }

        }

        if (textdivision != null) {
            return textdivision;
        }
        if (textdivisionsubdivision != null) {
            return textdivisionsubdivision;
        } else {
            return s;
        }
    }
}
