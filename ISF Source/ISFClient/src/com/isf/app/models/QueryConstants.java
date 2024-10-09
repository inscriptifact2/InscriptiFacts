package com.isf.app.models;

import com.isf.app.view.gui.PathSelectionManager;
import isf.common.ISFBase64;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class QueryConstants {

    private static String pathStr[] = {
        "Corpus", "Medium", "Find Site", "Time Period", "Language", "Script", "Repository"
    };
    private static Hashtable ht = new Hashtable();
    private boolean flag;
    private static String corpus[] = {
        "ISPARTOFCORPUS", "ISPARTOFCORPUSCATEGORY", "ISPARTOFCORPUSSUBCATEGORY"
    };
    private static String medium[] = {
        "MEDIUM"
    };
    private static String findsite[] = {
        "ISFFINDSITE"
    };
    private static String timeperiod[] = {
        "RELEVANTTIMELINE", "NAMEDTIMEPERIOD"
    };
    private static String language[] = {
        "LANGUAGE"
    };
    private static String script[] = {
        "SCRIPT", "SCRIPTSUBLEVEL"
    };
    private static String collectionowner[] = {
        "LOCATIONOFORIGINAL"
    };

    public QueryConstants() {
        flag = false;
    }

    public static int getStringIndex(String s) {
        for (int i = 0; i < pathStr.length; i++) {
            if (s.equalsIgnoreCase(pathStr[i])) {
                return i;
            }
        }

        return -1;
    }

    public static String[] getColumnNames(int i) {
        switch (i) {
            case 0: // '\0'
                return corpus;

            case 1: // '\001'
                return medium;

            case 2: // '\002'
                return findsite;

            case 3: // '\003'
                return timeperiod;

            case 4: // '\004'
                return language;

            case 5: // '\005'
                return script;

            case 6: // '\006'
                return collectionowner;
        }
        return null;
    }

    public Vector getCondition(String s, PathNode apathnode[][]) {
        Vector vector = new Vector();
        boolean selects = true;
        int i = getStringIndex(s);
        if (i == 3)
            selects = false;
        else
            selects = true;

        String as[] = getColumnNames(i);
        String s2 = "";
        String s3 = "";
        for (int j = 0; j < apathnode.length; j++) {
            String s4 = "";
            if (apathnode[j][0] != null) {
                String s5 = "";
                s2 = s2 + s3 + " (";
                Hashtable hashtable = new Hashtable();
                if (j == apathnode.length - 1) {
                    s4 = "";
                }
                if (apathnode[j][0] != null && apathnode[j][1] != null && apathnode[j][2] != null && j == apathnode.length - 1) {
                    s4 = "";
                }
                if (apathnode[j][0] != null && apathnode[j][1] != null && apathnode[j][2] != null && j != apathnode.length - 1) {
                    s4 = "OR";
                }
                if (apathnode[j][0] == null && apathnode[j][1] == null && apathnode[j][2] == null && j == apathnode.length - 1) {
                    s4 = "";
                }
                hashtable.put("CLAUSE", s4);
                Vector vector1 = new Vector();
                for (int k = 0; k < as.length; k++) {
                    if (apathnode[j][k] != null) {
                        PathNode pathnode = apathnode[j][k];
                        String s1 = pathnode.name;
                        String s6;
                        if (apathnode[j][0] != null && apathnode[j][1] != null && apathnode[j][2] == null && k == as.length - 2) {
                            s6 = "";
                        } else if (apathnode[j][0] != null && apathnode[j][1] == null && apathnode[j][2] == null && k == as.length - 3) {
                            s6 = "";
                        } else if (apathnode[j][0] != null && apathnode[j][1] != null && apathnode[j][2] != null && k == as.length - 1) {
                            s6 = "";
                        } else {
                            s6 = "AND";
                        }
                        if (s.equals("Time Period") && k == 1) {
                            //	System.out.println("_______s1 "+s1);
                            s1 = s1.substring(0, s1.indexOf("("));
                            //   System.out.println("******* s1 "+s1);

                        }
                        if (selects) {                    ////pathnode.isSelected
                            Vector vector2 = new Vector();
                            vector2.addElement(as[k]);
                            vector2.addElement(s1.trim());
                            //  vector2.addElement("like");
                            vector2.addElement("=");
                            vector2.addElement(s6);
                            vector1.addElement(vector2);
                        } else {
                            if (pathnode.isSelected) {
                                Vector vector2 = new Vector();
                                vector2.addElement(as[k]);
                                vector2.addElement(s1.trim());
                                //  vector2.addElement("like");
                                vector2.addElement("=");
                                vector2.addElement(s6);
                                vector1.addElement(vector2);
                            }
                        }
                    }
                }

                hashtable.put("QC", vector1);
                vector.addElement(hashtable);
            }
        }

        return vector;
    }

    public String getQuery() {
        String s = new String();
        if (!isQuerySelected()) {
            return null;
        }
        Hashtable hashtable = PathSelectionManager.getInstance().paths;
        if (hashtable.size() == 0) {
            return null;
        }
        Enumeration enumeration = hashtable.keys();
        boolean flag1 = enumeration.hasMoreElements();
        Vector vector = new Vector();
        while (flag1) {
            PathNode pathnode = (PathNode) enumeration.nextElement();
            flag1 = enumeration.hasMoreElements();
            String s1 = getClause(pathnode.name, (PathNode[][]) hashtable.get(pathnode), flag1);
            s = s + s1;
        }
        return s;
    }

    public void setFlag(boolean flag1) {
        flag = flag1;
    }

    public String getClause(String s, PathNode apathnode[][], boolean flag1) {
        StringBuffer sb = new StringBuffer();
        String s1 = "";
        int i = getStringIndex(s);
        String as[] = getColumnNames(i);
        String s2 = "";
        boolean flag2 = true;
        if (flag) {
            s2 = "AND";
        }
        if (flag1) {
            s2 = "AND";
        }
        sb.append("<CLAUSE CONNECTOR='" + s2 + "'>");
        Vector vector = new Vector();
        for (int j = 0; j < apathnode.length; j++) {
            PathNode apathnode1[] = apathnode[j];
            if (apathnode1[0] != null || apathnode1[1] != null || apathnode1[2] != null) {
                Hashtable hashtable = new Hashtable();
                String s3 = "";
                if (j < apathnode.length - 1) {
                    PathNode apathnode2[] = apathnode[j + 1];
                    if (apathnode2[0] == null && apathnode2[1] == null && apathnode2[2] == null) {
                        s3 = "";
                    } else {
                        s3 = "OR";
                    }
                }
                sb.append("<CLAUSE CONNECTOR='" + s3 + "'>");
                Vector vector1 = new Vector();
                for (int k = 0; k < as.length; k++) {
                    if (apathnode1[k] != null) {
                        String s4 = apathnode1[k].name;
                        //	System.out.println("_______"+s4);
                        if (s4 != null) {
                            if (s.equals("Time Period") && k == 1) {
                                s4 = s4.substring(0, s4.indexOf("("));
                                //	System.out.println("in if   "+s4);
                            }
                            int l = as.length;
                            if (i == 3)
                                flag2 = apathnode1[k].isSelected;
                            if (flag2) {
                                //    System.out.println("*****"+s4.trim());
                                sb.append("<QUERY_CONDITION KEY='" + as[k] + "' VALUE='" + ISFBase64.getQueryVal(s4.trim()) + "' OPERATOR='=' CONNECTOR='");
                                if (k < l - 1) {
                                    String s5 = "";
                                    for (int i1 = k + 1; i1 <= l - 1; i1++) {
                                        boolean parSel = true;
                                        if (i == 3) {
                                            if (apathnode1[i1] != null)
                                                parSel = apathnode1[i1].isSelected;
                                            else
                                                parSel = false;
                                        }
                                        if (apathnode1[i1] == null || !parSel) {
                                            continue;
                                        }
                                        s5 = "AND";
                                        break;
                                    }

                                    sb.append(s5 + "'></QUERY_CONDITION>");
                                } else {
                                    sb.append("'></QUERY_CONDITION>");
                                }
                            }
                        }
                    }
                }

                sb.append("</CLAUSE>");
            }
        }

        sb.append("</CLAUSE>");
        return sb.toString();
    }

    public static boolean isQuerySelected() {
        Hashtable hashtable = PathSelectionManager.getInstance().paths;
        if (hashtable.size() == 0) {
            return false;
        }
        for (Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();) {
            PathNode pathnode = (PathNode) enumeration.nextElement();
            PathNode apathnode[][] = (PathNode[][]) hashtable.get(pathnode);
            for (int i = 0; i < apathnode.length; i++) {
                if (apathnode[0][0] != null || apathnode[1][0] != null) {
                    return true;
                }
                for (int j = 0; j < apathnode[i].length; j++) {
                    if (apathnode[i][j].isSelected) {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    private static void printArr(String as[]) {
        for (int i = 0; i < as.length; i++) {
            System.out.print((as[i] != null ? as[i].toString().trim() : "NULL") + (i != as.length - 1 ? "," : ""));
        }

    }

}
