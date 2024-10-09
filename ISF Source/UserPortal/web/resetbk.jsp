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
        <title>JSP Page</title>
    </head>
    <% String userID = (String) session.getAttribute("loggedUser");
        if (userID == null)
            response.sendRedirect("../index.jsp");
        else {

            String action = request.getParameter("action");
            String nextPage = "../workspace.jsp?showpage=resetStats.jsp"; //resetusers.jsp
            if (action.equalsIgnoreCase("reset")) {
                String type = (String) request.getParameter("resettype");
                String uid = request.getParameter("uid");
                String msg = RequestHandler.getInstance().getResponse(action + "_" + type, uid);
                System.out.println("MSG:"+msg);
                if (!msg.startsWith("Error")) {
                    request.setAttribute("message", msg);
                } else {
                    request.setAttribute("error", msg);

                    nextPage = "../workspace.jsp?showpage=resetStats.jsp"; //resetusers
                }

            }
    %>
    <jsp:forward page="<%=nextPage%>"/>
    <%}%>
</html>
