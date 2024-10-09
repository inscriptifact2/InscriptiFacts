<%-- 
    Document   : userStatistics
    Created on : Jan 25, 2013, 7:24:45 PM
    Author     : raok1
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.isf.dao.AdminDAO"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" />
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
            AdminDAO dao = new AdminDAO();
        %>

               
        <table width="75%" border="0">

            <tr><td height="40">
                    <h1 align="center"> InscriptiFact User Portal</h1>
                    <span class="error"><%= error%></span><br/>
                    <span class="message"><%= message%></span></td></tr>
            <tr><td>
                    <table width="75%" align="center" class="regtable" >
                            <tr class="regtr">
                                <td colspan="2" > # of Distinct Users</td>
                                <td colspan="2" > <%= dao.getUniqueUsers()%></td>
                            </tr>
                            <tr class="regtr"><td colspan="4" class="headtd"># of Users By Country</td></tr>
                            <%
                               HashMap map = dao.getCountryUsers();
                               Set keys = map.keySet();
                               Iterator i = keys.iterator();
                               String s="";
                               Integer c=null;
                               while(i.hasNext()){
                                    s = (String) i.next();
                                    c = (Integer)map.get(s);%>
                                   <tr class="regtr"><td colspan="2" > <%=s%></td><td colspan="2"><%=c%> </td></tr>
                             <%  }
                            %>
                            <tr class="regtr"><td   colspan="4" class="headtd"># of Users By Institution</td></tr>
                            <%
                                map = dao.getInstUsers();
                                keys = map.keySet();
                                i = keys.iterator();
                               while(i.hasNext()){
                                    s = (String) i.next();
                                    c = (Integer)map.get(s);%>
                                   <tr class="regtr"><td colspan="2" > <%=s%></td><td colspan="2"><%=c%> </td></tr>
                             <%  }
                            %>
                            
                            
                    </table>
                    </td></tr>
            
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