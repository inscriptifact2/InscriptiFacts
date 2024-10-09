<%-- 
    Document   : resetuser
    Created on : Nov 18, 2010, 10:49:36 PM
    Author     : raok1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <link rel="stylesheet" href="css/style.css" />
    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">

        <table width="100%" border="0">
            <tr><td height="160">
                    <jsp:include page="header.jsp" /></td></tr>
            <tr><td height="40">
                    <h1 align="center"> InscriptiFact User Portal</h1></td></tr>
            <tr><td>
                    <%
                        String msg = (String) request.getAttribute("message");
                        String err = (String) request.getAttribute("error");

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
                    <form action="forgotbk.jsp" method="post" name="frmlogin">
                        <input type ="hidden" name="action" value="forgotpwd" />
                        <table align="center"> <tr> <td > UserID: <span>*</span></td><td colspan="2"><input type ="text" id="userIdentity" name="userIdentity"/></td></tr>
                            <tr> <td > Registered Email Address:<span>*</span> </td><td colspan="2"><input type ="text" id="email" name="email"/></td></tr>
                            <tr> <td><input type="submit" name="submit" value="Generate Password"/></td></tr></table>
                    </form>

                </td></tr>
        </table>
    </body>
</html>
