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

public class TextResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private QueryBean queryBean;
    private Logger logger = LogManager.getLogger(isf.sc.response.TextResponseObject.class);

    public TextResponseObject()
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
        String s1 = extractValues();

        try {
            Vector vector = queryBean.getTextDivisionPanelData(s1, jro.getQuery().getSessionid());
            s = (String) vector.elementAt(0);
        } catch (Exception exception) {
            logger.error("Exception", exception);
        }
        return s;

    }

    private String extractValues() {
        String s = "";
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                s = querycondition.getValue();
            }

        }

        return s;
    }
}
