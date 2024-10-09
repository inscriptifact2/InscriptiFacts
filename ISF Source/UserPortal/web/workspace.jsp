
<%-- 
    Document   : workspace
    Created on : Nov 18, 2010, 10:49:36 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>InscriptiFact User Portal</title>
    </head>
       
    <table align="center" border="0" width="100%">
        <tr><td colspan="2"> <jsp:include page="header.jsp"/></td></tr>
        <%
        String display = (String)request.getAttribute("showpage");
        String status = (String)request.getAttribute("agreement");
        String sp = request.getParameter("showpage");
        if(display==null || display.trim().length()==0 ){
            display = (sp==null ||sp.trim().length()==0) ? "instructions.jsp":sp ;
        }
        if(status !=null && (status.trim().equalsIgnoreCase("F") || status.trim().equalsIgnoreCase("D")) ){
           
            
        %>
          <tr><td width="50%"><jsp:include page= "userAgreement.jsp" /></td></tr>
          <%} else { %>
        <tr><td width="10%"><jsp:include page="leftmenu.jsp"  /></td> <td width="90%" ><jsp:include page= "<%=display %>" /></td></tr>
        <% } %>
    </table>
</html>
