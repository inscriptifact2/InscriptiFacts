<%-- 
    Document   : processbk
    Created on : Jan 26, 2013, 6:02:38 AM
    Author     : raok1
--%>

<%@page import="com.isf.dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <% 
     String uid = (String)session.getAttribute ("loggedUser") ;
     String status = request.getParameter("status");
     String nextPage = "../workspace.jsp";
     UserDAO dao = new UserDAO();
     String msg = dao.updateAgreement(uid,status);
     if(msg.equalsIgnoreCase("SUCCESS")){
         request.setAttribute("showpage","instructions.jsp");
             }
      
      if(uid==null)
          response.sendRedirect("../index.jsp");
      else{
        %>
>
     
    <jsp:forward page="<%=nextPage%>" />
<%} %>
</html>