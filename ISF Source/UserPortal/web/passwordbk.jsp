<%-- 
    Document   : passwordbk
    Created on : Nov 23, 2010, 12:54:42 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <% String uid = (String)session.getAttribute ("loggedUser") ;
      if(uid==null)
          response.sendRedirect("../index.jsp");
      else{

    String action = request.getParameter("action");
    String nextPage = "workspace.jsp";
    if (action.equalsIgnoreCase("pwd")) {
         uid = (String)session.getAttribute("loggedUser");
        String opwd = request.getParameter("userPassword");
        String npwd = request.getParameter("newpwd");
        com.isf.dao.UserDAO dao = new com.isf.dao.UserDAO();
        String msg = dao.updatePassword(uid,opwd,npwd);
        if (msg.equalsIgnoreCase("SUCCESS")) {
                 request.setAttribute("message", "Password changed successfully");
                   } else {
             request.setAttribute("error", msg);
             request.setAttribute("showpage","instructions.jsp");   


            }
        }
    %>
    <jsp:forward page="<%=nextPage%>"/>
<%} %>
</html>
