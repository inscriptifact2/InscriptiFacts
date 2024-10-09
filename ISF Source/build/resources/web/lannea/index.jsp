<%-- 
    Document   : index
    Created on : Jul 1, 2018, 2:39:32 PM
    Author     : raok1
--%>

<%@page import="com.isf.webutils.InscriptWebLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String baseUrl = InscriptWebLoader.getInstance().getProperty("imageurl");
            baseUrl = baseUrl.replaceAll("isfimages", "Inscriptifact");
            response.sendRedirect(baseUrl);
            %>
    </body>
</html>
