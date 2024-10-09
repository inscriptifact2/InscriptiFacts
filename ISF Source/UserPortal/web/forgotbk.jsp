<%-- 
    Document   : forgotbk
    Created on : Nov 23, 2010, 12:54:42 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Retrieve Password</title>
    </head>
    <%
        String uid = (String) request.getParameter("userIdentity");
        String email = (String) request.getParameter("email");
        String nextPage = "index.jsp";
        if (uid == null ||uid.trim().length() == 0 ) {
            request.setAttribute("error", "Please enter your UserId");
            nextPage = "forgotpassword.jsp";
        } 
        if (email == null || email.trim().length() == 0) {
            request.setAttribute("error", "Please enter the email address you entered when you registered.");
            nextPage = "forgotpassword.jsp";
        } 
       if((uid!=null && uid.trim().length() > 0 )&& (email!=null && email.trim().length() > 0)) {

            String action = request.getParameter("action");

            if (action.equalsIgnoreCase("forgotpwd")) {
                com.isf.dao.UserDAO dao = new com.isf.dao.UserDAO();
                String msg = "";
                try {
                    msg = dao.forgotPassword(uid, email);
                    request.setAttribute("message", msg);
                    nextPage = "index.jsp";
                } catch (Exception exp) {
                    msg = exp.getMessage();
                    request.setAttribute("error", msg);
                    nextPage = "forgotpassword.jsp";
                }


            }
                       }
    %>
    <jsp:forward page="<%=nextPage%>"/>
    
</html>
