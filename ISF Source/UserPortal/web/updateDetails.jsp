<%-- 
    Document   : updateDetails
    Created on : Jan 20, 2013, 8:22:16 AM
    Author     : raok1
--%>

<%@page import="com.isf.beans.UserRegistration"%>
<%@page import="com.isf.dao.UserDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <%
             String uid = (String)   session.getAttribute("loggedUser");
             String role = (String)  session.getAttribute("userRole");
              UserDAO dao = new UserDAO();
             UserRegistration useReg = (UserRegistration)dao.getUser(uid,role.trim().equalsIgnoreCase("EXECADMIN"));
             String nextPage = "workspace.jsp";
             if(useReg!=null){
             useReg.setAction("UPDATE");
             session.setAttribute("userReg",useReg);
             request.setAttribute("showpage","register.jsp");
              }
             else
             {
                 request.setAttribute("error","System Error. Please login again");
                 nextPage="index.jsp";
                 
             }
             
            
   %>
     
   <jsp:forward page="<%=nextPage%>"/>
</html>
