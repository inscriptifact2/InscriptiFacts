<%-- 
    Document   : resetStats
    Created on : Jan 25, 2013, 7:24:45 PM
    Author     : raok1
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" />
        <script type="text/javascript" src="getNameSuggestions.js"> </script>
    </head>
    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">

        <%
            String error = (String) request.getAttribute("error");
            if (error == null) {
                error = "";
            }
            String message = (String) request.getAttribute("message");
            if (message == null) {
                message = "";
            }
        %>

        <jsp:useBean id="userStats" scope="session" class="com.isf.beans.UserStats" />
        
        <table width="75%" border="0">

            <tr><td height="40">
                    <h1 align="center"> InscriptiFact User Portal</h1>
                    <span class="error"><%= error%></span><br/>
                    <span class="message"><%= message%></span></td></tr>
            <tr><td>
                    <form method="POST" action="resetbk.jsp">
                        <table  align="center"  >
                            <tr class="regtr">
                                <td colspan="2" class="headtd">
                                    Reset Users</td></tr>
                            <tr class="regtr" colspan="2"><td>
                                    <input type="radio" name="resettype" value="ONE_DAY" checked="checked" >Reset all users inactive for more than 24 hrs </input></td></tr>
                            <tr class="regtr" colspan="2"><td>
                                    <input type="radio" name="resettype" value="HALF_DAY"  >Reset all users inactive for about 12 hrs </input></td></tr>
                            <tr class="regtr" colspan="2"><td>
                                    <input type="radio" name="resettype" value="ALL"  >Reset all active logged in users </input></td></tr>
                            <tr class="regtr" colspan="2"><td>
                                    <input type="radio" name="resettype" value="USER"  >Reset users whose id starts with/ is </input>
                                    <input type="text" name="uid" id="uid"/></td></tr>
                            <tr class="regtr"><td colspan="2"><input type="hidden" name="action" value="RESET"/>
                                    <input type="submit" name="Reset User" value="Reset Users"/></td></tr>
                            <!--   YOUR CODE GOES IN HERE-->
                            <tr class="regtr"><td class="headtd" colspan="2">
                                    Current User Statistics <a href="javascript:refreshStatistics()"><img src="images/refresh.jpg"/></a></td></tr>
                            <tr class="regtr" ><td colspan="2"><blockquote>

                                        <ul>
                                            <li>No. of users currently logged in: <span id="la"> <b> <jsp:getProperty name="userStats" property="noLoggedIn" /></b></span></li>
                                            <li>No. of users aging over 24 hours:<span id="l24"><b> <jsp:getProperty name="userStats" property="noLogged24" /></b></span></li>
                                            <li>No. of users aging over 12 hours: <span id="l12"><b> <jsp:getProperty name="userStats" property="noLogged12" /></b></span></li>
                                        </ul>

                                    </blockquote>
                                      </td></tr>
                            <!--   YOUR CODE ENDS HERE-->

                        </table> 
                    </form> </td></tr>
            <tr><td>
                    <table  align="center" ></table></td></tr>
            <tr><td colspan="2"><hr size="1" noshade></td></tr><!--PAGE FOOTER-->
            <tr><td colspan="2">
                    <div align="center">
                        <font size="-1" color="#525D76"><em>Copyright &copy; 2001-<%=Calendar.getInstance().get(Calendar.YEAR)%>, University of Southern California.  All Rights Reserved</em></font>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>