<%-- 
    Document   : changepassword
    Created on : Nov 18, 2010, 10:49:53 PM
    Author     : raok1
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script language="javascript" type="text/javascript">
            function checkPassword()
            {
                var pwd = document.forms["changePWD"].elements["newpwd"].value   ;
                var cpwd = document.forms["changePWD"].elements["confirmupwd"].value ;

                if(pwd !="" && pwd!=cpwd)
                    alert("Passwords do not match");
                else if(pwd!="" && pwd==cpwd)
                    document.forms["changePWD"].submit();
                else
                    alert("Please check your passwords");
            }
    </script>
    <link rel="stylesheet" href="css/style.css" />
    </head>
    <% String uid = (String)session.getAttribute ("loggedUser") ;
      if(uid==null)
          response.sendRedirect("index.jsp");
      else{
        %>
     <jsp:useBean id="userStats" class="com.isf.beans.UserStats" scope="session" />

    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">

        <table width="100%" border="0">
            
            <tr><td height="40">
                    <h1 align="center"> InscriptiFact User Portal</h1></td></tr>
            <tr><td>
                    <form action="passwordbk.jsp" method="post" name="changePWD" id="changePWD">
                        <table cellpadding="2" cellspacing="0" border="0" align="center"><tr class="regtr">
                                <td class="headtd">Change password</td></tr><tr><td><blockquote>
                                        <table>
                                            <tr class="regtr">
                                                <td>Current Password:</td><td><input type ="password" name="userPassword" /></td>
                                            </tr>
                                            <tr class="regtr">
                                                <td>New Password:</td><td><input type ="password" name="newpwd" /></td>
                                            </tr>
                                            <tr class="regtr">
                                                <td>Confirm New Password:</td><td><input type ="password" name="confirmupwd" /></td>
                                            </tr>
                                            <tr class="regtr">
                                                <td><input type="hidden" name="action" value="pwd"/></td><td><input type ="button" name="Submit" value="Change" onclick="checkPassword()"/></td>
                                            </tr>
                                        </table>

                        </blockquote></td></tr></table>
                    </form>
                    </td></tr>
            <!--FOOTER SEPARATOR--><tr><td colspan="2"><hr size="1" noshade></td></tr><!--PAGE FOOTER--><tr><td colspan="2"><div align="center"><font size="-1" color="#525D76"><em>
                                Copyright &copy; 2001-<%=Calendar.getInstance().get(Calendar.YEAR)%>, University of Southern California.  All Rights Reserved
</em></font></div></td></tr></table></body>
<%} %>
</html>