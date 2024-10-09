<%-- 
    Document   : resetuser
    Created on : Nov 18, 2010, 10:49:36 PM
    Author     : raok1
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>InscriptiFact User Portal - Reset Users</title>
        <style media="print" type="text/css">
            .noPrint {display: none;}
            td#mainBody {width: 100%;}
        </style>

    </head>
     <% String userID = (String)session.getAttribute ("loggedUser") ;
      if(userID==null)
          response.sendRedirect("index.jsp");
      else{
       %>
     <jsp:useBean id="userStats" scope="session" class="com.isf.beans.UserStats" />
      <%
       // com.isf.dao.AdminDAO adm = new com.isf.dao.AdminDAO();
      //  int[] stats = adm.getUserStats();
        String message = (String)request.getAttribute("message") ;
        if(message == null || message.length()==0)
             message = (String)request.getAttribute("error") ;
        if(message ==null || message.length() ==0)
            message = "";


        %>
      
      <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">
        <table cellspacing="0" width="100%" border="0">
            <!--PAGE HEADER--><tr><td><!--PROJECT LOGO--><a href="http://www.inscriptifact.com/">
                    <img border="0" alt=" " align="left" src="images/1_inscript.jpg"></a>
        </td></tr></table>
        <table cellspacing="4" width="100%" border="0"><!--HEADER SEPARATOR-->
            <tr><td colspan="2"><hr size="1" noshade></td></tr>
            <tr><!--LEFT SIDE NAVIGATION--><td class="noPrint" nowrap valign="top" width="20%">
                    <p><strong>Actions</strong></p>
                    <ul><li><a href="adduser.jsp">Add New User</a></li>
                        <li><a href="edituser.jsp">Edit User</a></li>
                        <li><a href="resetusers.jsp">Reset Users</a></li>
                        <li><a href="changepassword.jsp">Change Password</a></li>
                        <br>
                        <li><a href="logout.jsp">LogOut</a></li>
                </ul></td><!--RIGHT SIDE MAIN BODY-->
                <td id="mainBody" align="left" valign="top" width="80%">
                <br>
                    <%=message%> <br>
                    <form action="resetbk.jsp" method="post">
                        <table cellpadding="2" cellspacing="0" border="0"><tr>
                                <td bgcolor="#525D76"><font face="arial,helvetica.sanserif" color="#ffffff">
                            <strong>Reset User</strong></font></td></tr><tr><td><blockquote>
                                        <table><tr><td>
                                        <input type="radio" name="resettype" value="24hr" checked="checked" >Reset all users inactive for more than 24 hrs </input></td></tr>
                                        <tr><td>
                                        <input type="radio" name="resettype" value="12hr"  >Reset all users inactive for about 12 hrs </input></td></tr>
                                        <tr><td>
                                        <input type="radio" name="resettype" value="all"  >Reset all active logged in users </input></td></tr>
                                        <tr><td>
                                        <input type="radio" name="resettype" value="user"  >Reset users whose id starts with/ is </input><input type="text" name="userid" id="userid"/></td></tr>
                                        <tr><td><input type="hidden" name="action" value="reset"/></td></tr>
                                        <tr><td><input type="submit" name="Reset User" value="Reset"/></td></tr>



                    </blockquote></td></tr></table></form>
                     <table cellpadding="2" cellspacing="0" border="0"><tr><td bgcolor="#525D76">
                                <font face="arial,helvetica.sanserif" color="#ffffff">
                        <strong>Current User Statistics</strong></font></td></tr><tr><td><blockquote>

                                    <ul>
                                        <li>No. of users currently logged in:  <b><jsp:getProperty name="userStats" property="noLoggedIn" /></b></li>
                                        <li>No. of users aging over 24 hours:<b> <jsp:getProperty name="userStats" property="noLogged24" /></b></li>
                                        <li>No. of users aging over 12 hours: <b><jsp:getProperty name="userStats" property="noLogged12" /></b></li>
                                    </ul>

            </blockquote></td></tr></table></td></tr>
            <!--FOOTER SEPARATOR--><tr><td colspan="2"><hr size="1" noshade></td></tr><!--PAGE FOOTER--><tr><td colspan="2"><div align="center"><font size="-1" color="#525D76"><em>
                                Copyright &copy; 2001-<%=Calendar.getInstance().get(Calendar.YEAR)%>, University of Southern California.  All Rights Reserved
</em></font></div></td></tr></table></body>
<%} %>
</html>