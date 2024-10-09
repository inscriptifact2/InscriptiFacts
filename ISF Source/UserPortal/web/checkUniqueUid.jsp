<%@page import="com.isf.dao.UserDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<%
    String uid = request.getParameter("uid");
    UserDAO dao = new UserDAO();
    out.print(dao.validateUID(uid));
 %>