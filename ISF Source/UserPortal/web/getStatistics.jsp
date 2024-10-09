<%-- 
    Document   : getStatistics
    Created on : Jul 12, 2013, 3:48:31 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
com.isf.dao.AdminDAO adm = new com.isf.dao.AdminDAO();
 int[] stats = adm.getUserStats();
 com.isf.beans.UserStats userStats = (com.isf.beans.UserStats)session.getAttribute("userStats");
 userStats.setNoLogged12(stats[0]);
 userStats.setNoLogged24(stats[1]);
 userStats.setNoLoggedIn(stats[2]);
 session.setAttribute("userStats",userStats);
 out.print(stats[0]+","+stats[1]+","+stats[2]);
%>
