<%-- 
    Document   : resetbk
    Created on : Nov 23, 2010, 12:54:27 PM
    Author     : raok1
--%>

<%@page import="com.isf.utils.RequestHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
    </head>
    <% String userID = (String) session.getAttribute("loggedUser");
        if (userID == null)
            response.sendRedirect("../index.jsp");
        else {
           String nextPage = "../workspace.jsp?showpage=instructions.jsp"; //resetusers.jsp
           String msg = RequestHandler.getInstance().getResponse("RESET_USER", userID);
            System.out.println("MSG:" + msg);
            if (!msg.startsWith("Error")) {
                request.setAttribute("message", msg);
            } else {
                request.setAttribute("error", msg);
            }
    %>
    <jsp:forward page="<%=nextPage%>"/>
    <%}%>
</html>
