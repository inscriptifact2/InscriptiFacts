<%-- 
    Document   : loginbk
    Created on : Nov 23, 2010, 10:27:59 AM
    Author     : raok1
--%>

<%@page import="com.isf.beans.UserRegistration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <% 
    String action = request.getParameter("action");

    String nextPage = "workspace.jsp";
    if (action.equalsIgnoreCase("login")) {
        String uid = request.getParameter("userIdentity");
        String upwd = request.getParameter("userPassword");
       
        com.isf.dao.UserDAO dao = new com.isf.dao.UserDAO();
          UserRegistration user = dao.loginUser(uid,upwd);
        if (user !=null) {
                session.setAttribute("loggedUser", uid);
                session.setAttribute("userRole", user.getUserRole().trim());
                request.setAttribute("agreement",user.getUserAgreementStatus().trim());
                request.setAttribute("showpage","instructions.jsp");
             } else {
             request.setAttribute("error", "Either user name or password is wrong. <br/>");
                nextPage = "index.jsp";
            }
        }
    %>
    <jsp:forward page="<%=nextPage%>" />
    

</html>
