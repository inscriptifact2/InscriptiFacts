package isf.sc.response;

import isf.common.jro.JavaRequestObject;
import isf.common.xml.Clause;
import isf.common.xml.Query;
import isf.common.xml.QueryCondition;
import isf.sc.beans.QueryBean;
import java.io.Serializable;
import java.util.Vector;

public class ImageResponseObject
        implements Serializable {

    private JavaRequestObject jro;
    private QueryBean queryBean;

    public ImageResponseObject()
            throws Exception {
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
        String s = "";
        if (jro.getQuery().getType().equals("quickimage")) {
            s = "select IMAGEQUICKLOOK from photoobject where PHOTOGRAPHIDENTIFICATIONNO ='" + getPhotoId() + "'";
        //logger.debug("\nquicklookImage from IRO--" + s);
        }
        if (jro.getQuery().getType().equals("indexmapimage")) {
            s = "select INDEXMAPIMAGE from photoobject where PHOTOGRAPHIDENTIFICATIONNO ='" + getPhotoId() + "'";
        //  logger.debug("\n\n\n"+s+"\n\n\n");
        // logger.debug("\nINDEXMAPIMAGE from IRO--" + s);
        }
        try {
            byte abyte0[] = queryBean.getImageIcon(s, jro.getQuery().getSessionid());
            return abyte0;
        } catch (Exception exception) {
            String s1 = "<RESPONSE>";
            s1 = s1 + "<ERRMSG MSG='" + exception.toString() + "'/></RESPONSE>";
            return s1;
        }
    }

    public String getPhotoId() {
        String s = "";
        Query query = jro.getQuery();
        Vector vector = query.getQueryConditions();
        int j = 0;
        for (int k = vector.size(); j < k; j++) {
            Clause clause = (Clause) vector.elementAt(j);
            Vector vector1 = clause.getQueryConditions();
            //logger.debug("Size of QueryCondition" + vector1.size());
            int l = 0;
            for (int i1 = vector1.size(); l < i1; l++) {
                QueryCondition querycondition = (QueryCondition) vector1.elementAt(l);
                s = querycondition.getValue();
            }

        }

        return s;
    }
}
