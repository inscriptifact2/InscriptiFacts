
<%@page import="java.util.Calendar"%>
<%-- 
    Document   : resetuser
    Created on : Nov 18, 2010, 10:49:36 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Instructions</title>

    </head>
    <% 
    String uid = (String) session.getAttribute("loggedUser");
        String userRole = ((String) session.getAttribute("userRole")).trim();
        String agreement = (String)request.getAttribute("agreement");
        if (uid == null)
            response.sendRedirect("index.jsp");
        else {
    %>
    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">

        <jsp:useBean id="userStats" scope="session" class="com.isf.beans.UserStats" />

        <%
            com.isf.dao.AdminDAO adm = new com.isf.dao.AdminDAO();
            int[] stats = adm.getUserStats();
            String message = (String) request.getAttribute("message");
            if (message == null) {
                message = "";
            }
            String error = (String)request.getAttribute("error");
            if(error==null ) error="";
          
            if (userRole.equalsIgnoreCase("EXECADMIN")||userRole.equalsIgnoreCase("ADMIN")) {
        %>
        <jsp:setProperty name="userStats" property="noLogged12" value="<%=stats[0]%>"/>
        <jsp:setProperty name="userStats" property="noLogged24" value="<%=stats[1]%>"/>
        <jsp:setProperty name="userStats" property="noLoggedIn" value="<%=stats[2]%>"/>
        <%
            }
        %>
        <table cellspacing="4" width="100%" border="0"><!--HEADER SEPARATOR-->

            <tr>    <td id="mainBody" align="left" valign="top" width="80%"><h1>InscriptiFact User Portal</h1>
                    <table cellpadding="2" cellspacing="0" border="0">
                        <tr><td class="error" align="center"><%=error%></td></tr>
                         <tr><td class="message" align="center"><%=message%></td></tr>
                        <tr>
                            <td bgcolor="#525D76"><font face="arial,helvetica.sanserif" color="#ffffff">
                                    <strong>Instructions</strong></font></td></tr><tr><td>
                                <ul>
                                    <%
                                        if ( userRole.equalsIgnoreCase("GROUPADMIN")) { //userRole.equalsIgnoreCase("ADMIN") ||
                                    %>                                    
                                    <li> <strong> Manage User Groups: </strong> This allows you to define and manage sub groups within the user group created. To launch this, click the <i>Manage User Groups</i> link at the left hand side. 
                                    <li> <strong> Manage User Group Texts: </strong> To associate text records with user groups, click the <i>Manage User Group Texts</i> link at the left hand side. Fill out the requisite details and submit the form.
                                    <li> <strong> Manage Users for Groups: </strong> To allow access to restricted texts, click the <i>Manage Users for Groups</i> link at the left hand side.  Search for the user using the Login Id and select one of the resulting search records. Click the add button. To revoke access, select an existing user and click the revoke button

                                        <%    }
                                            if (userRole.equalsIgnoreCase("EXECADMIN")||userRole.equalsIgnoreCase("ADMIN")) { // //
                                        %>
<!--                                    <li> <strong> Create User Groups: </strong> To create a new User Group, click the <i>Create User Groups</i> link at the left hand side. Fill out the form and submit it. To associate a group admin to the created user group, click on the appropriate button.-->
                                    <li> <strong> Add User: </strong> To add a new user , click the <i>Add User</i> link at the left hand side. Fill out the form and submit it. 
                                    <li> <strong> Edit User: </strong> To edit an existing user, click the <i>Edit User</i> link at the left hand side. Search for the user by entering the user identity. Modify the details as required and then save the same.
                                    <li> <strong> Reset User Login Status: </strong> To reset users, click the <i>Reset User Login Status</i> link at the left hand side. Select from one of the preset options and submit. If you choose to reset one single user, enter the id of the user and submit. You must enter atleast 3 characters of the user id to reset.

                                        <%    }
                                        %>
                                    <li> <strong> Change Password: </strong> To change your password, click on the <i>Change Password</i> link and confirm your old and new passwords.
                                    <li> <strong> Update Profile: </strong> To update your details, click on the <i>Update Profile</i> link and fill in the form that appears and click the submit button.
                                </ul>

                            </td></tr></table>

                    <%
                        if (userRole.equalsIgnoreCase("EXECADMIN")||userRole.equalsIgnoreCase("ADMIN")) {
                    %>
            <tr> <td bgcolor="#525D76">
                    <font face="arial,helvetica.sanserif" color="#ffffff">
                        <strong>Current User Statistics</strong></font></td></tr>
            <tr><td><blockquote>

                        <ul>
                            <li>No. of users currently logged in:  <b><jsp:getProperty name="userStats" property="noLoggedIn" /></b></li>
                            <li>No. of users aging over 24 hours:<b> <jsp:getProperty name="userStats" property="noLogged24" /></b></li>
                            <li>No. of users aging over 12 hours: <b><jsp:getProperty name="userStats" property="noLogged12" /></b></li>
                        </ul>

                    </blockquote></td></tr>
                    <% }%>

            <!--FOOTER SEPARATOR--><tr><td colspan="2"><hr size="1" noshade></td></tr><!--PAGE FOOTER--><tr><td colspan="2"><div align="center"><font size="-1" color="#525D76"><em>
                                Copyright &copy; 2001-<%=Calendar.getInstance().get(Calendar.YEAR)%>, University of Southern California.  All Rights Reserved
                            </em></font></div></td></tr></table>
    </body>

<% }%>
</html>