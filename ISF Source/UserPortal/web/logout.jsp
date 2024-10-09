<%-- 
    Document   : logout
    Created on : Nov 22, 2010, 5:55:54 AM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <%
    session.invalidate();
    response.sendRedirect("index.jsp");
    %>
</html>
