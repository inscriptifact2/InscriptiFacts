
<%-- 
    Document   : resetuser
    Created on : Nov 18, 2010, 10:49:36 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
  
    <table align="center" border="0" width="100%">
        <tr><td height="160">
                <jsp:include page="header.jsp" /></td></tr>
        <tr align="center"><td  class="message"><%= request.getAttribute("message") %><br/><br/>
                <a href="index.jsp"> HOME</a>
                <br/>
            <a href="/index.jsp"> Start InscriptiFact</a></td></tr></table>
 </html>
