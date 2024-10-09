package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.utils.LogManager;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.ImageBean;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

public class SidResponseObject
        implements Serializable {

    private Logger logger = LogManager.getLogger(isf.sc.response.SidResponseObject.class);
    private JavaRequestObject jro;
    private ImageBean imageBean;

    public SidResponseObject()
            throws Exception {
        try {
            imageBean = new ImageBean();
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
        Vector v = getPhotoId();
        try {
            Hashtable abyte0 = imageBean.getSidFile((String) v.elementAt(0), (String) v.elementAt(2),
                    (String) v.elementAt(1), jro.getQuery().getSessionid());
            return abyte0;
        } catch (Exception exception) {
            String s2 = "<RESPONSE SESSIONID='" + jro.getQuery().getSessionid() + "'>";
            s2 = s2 + "<ERRMSG MSG='" + exception.toString() + "'/></RESPONSE>";
            logger.error("Exception", exception);

            
            return s2.getBytes();
        }
    }

    public Vector getPhotoId() {
        String name = "";
        String start = "";
        String len = "";
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                if (querycondition.getKey().equals("photoid")) {
                    name = querycondition.getValue();
                } else if (querycondition.getKey().equals("start")) {
                    start = querycondition.getValue();
                } else if (querycondition.getKey().equals("length")) {
                    len = querycondition.getValue();
                }
            }

        }
        Vector v = new Vector();
        v.addElement(name);
        v.addElement(start);
        v.addElement(len);
        return v;
    }
}
