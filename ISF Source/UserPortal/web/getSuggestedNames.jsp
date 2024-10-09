<%@page import="com.isf.dao.UserDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>

<%
    String lname = request.getParameter("lname");
    String fname = request.getParameter("fname");
    String email = request.getParameter("email");
    String mobile = request.getParameter("mobile");
  if(lname==null ) lname="nittala";
    if(fname==null ) fname="kamesh";
    if(mobile==null ) mobile="9849203571";
    if(email==null ) email="kamesh.nittala@gmail.com";
    ArrayList list = com.isf.utils.Utils.getValidIDs(lname, fname, mobile, email);
    String str = "Suggested userids are: ";
    
    
    ArrayList names = (ArrayList) application.getAttribute("uniqueIDs") ; 
    if(names==null) {
        UserDAO dao = new UserDAO();
        names= dao.getUniqueIdentities();
        application.setAttribute("uniqueIDs",names);
        
    }
        
    int i = 1;
    String sep = "";
    for (Object s : list) {
        if (!names.contains(s)) {
            str = str+sep + "<a href='javascript:selectName(\"" + s.toString() + "\")'>" + s.toString() + "</a>&nbsp;";
            i++;
            sep=",";
            if (i > 5) {
                break;
            }
        }
    }
    
    out.print(str);
%>
