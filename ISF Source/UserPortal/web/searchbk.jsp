<%-- 
    Document   : searchbk
    Created on : Nov 23, 2010, 12:54:53 PM
    Author     : raok1
--%>

<%@page import="com.isf.beans.UserRegistration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>

    <%
        String action = request.getParameter("action");
        
        String nextPage = "../workspace.jsp";
        com.isf.dao.UserDAO dao = new com.isf.dao.UserDAO();
        if (action!=null && action.equalsIgnoreCase("search")) {
            String uid = request.getParameter("uid");
            UserRegistration ret = (UserRegistration) dao.getUser(uid,false);

            if (ret == null) {
                request.setAttribute("error", "System could not find the specified user.");
            } else {
                ret.setAction("EDIT");
                session.setAttribute("userReg", ret);
                request.setAttribute("showpage","editUser.jsp");
            }
            
        } else {
            
            request.setAttribute("showpage","instructions.jsp");
        }
    %>
    <jsp:forward page="<%=nextPage%>" />

</html>
