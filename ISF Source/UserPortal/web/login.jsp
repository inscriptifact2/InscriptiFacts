
<%-- 
    Document   : resetuser
    Created on : Nov 18, 2010, 10:49:36 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<link rel="stylesheet" href="css/style.css" />
<body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">
    
    <h1 align="center"> InscriptiFact User Portal </h1>  

    <%
        String msg = (String) request.getAttribute("message");
        String err = (String) request.getAttribute("error");
    %>

    
    <form action="loginbk.jsp" method="post" name="frmlogin" >
        <table align="center">
            <tr><td colspan ="3">
    <%
        if (err != null && err.length() > 0) {
     %>
     <p class="error">  <%=err%></p>
     <%
        }
     %>
     <%
        if (msg != null && msg.length() > 0) {
     %>
     <p class="message">  <%=msg%></p>
     <%
        }
     %>
             </td></tr>
            <tr><td></td><td><input type ="hidden" name="action" value="login" /></td></tr>
            <tr> <td > User Id: </td><td colspan="2"><input type ="text" id="userIdentity" name="userIdentity"/></td></tr>
            <tr> <td > Password: </td><td colspan="2"><input type ="password" id="userPassword" name="userPassword"/></td></tr>
            <tr><td></td></tr>
             <tr> <td  colspan="3"> New User? <a href="userRegistration.jsp" >Register</a> here </td></tr>
             <tr><td  colspan="3"><a href="forgotpassword.jsp">Forgot</a> Password?</td></tr>
            <tr><td><input type="submit" name="submit" value="Login"/></td><td colspan="2"><input type="button" name="reset" onclick="javascript:clear();" value="Reset"/></td></tr>
        </table>
    </form>
</body>