<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" href="css/style.css" />

    </head>
    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">
        <table>
            <tr><td><p>Actions:</p></td></tr>
            <%
                try {
                    String userRole = (String) session.getAttribute("userRole");

                    if (userRole != null && (userRole.trim().equalsIgnoreCase("EXECADMIN") || userRole.trim().equalsIgnoreCase("ADMIN"))) {
            %>

            <!--            <tr><td><a href="workspace.jsp?showpage=userGroup.jsp">Manage User Groups</a></td></tr>
                        <tr><td><a href="workspace.jsp?showpage=userAssociations.jsp">Manage Users for Groups</a></td></tr>
                        <tr><td><a href="workspace.jsp?showpage=userText.jsp">Manage User Group Texts</a></td></tr>-->
            <tr><td><a href="workspace.jsp?showpage=addUser.jsp">Add User</a></td></tr>
            <tr><td><a href="workspace.jsp?showpage=editUser.jsp">Edit User</a></td></tr>
            <tr><td><a href="workspace.jsp?showpage=resetStats.jsp">Reset User Login Status</a></td></tr> 

            <%        //populate GROUPADMIN ROLES
                }

                if (userRole != null && userRole.trim().equalsIgnoreCase("EXECADMIN")) {
            %>                    
            <!--            <tr><td><a href="workspace.jsp?showpage=addUserGroup.jsp">Create User Groups</a></td></tr>-->
            <tr><td><a href="workspace.jsp?showpage=userStatistics.jsp">User Statistics</a><br/></td></tr>
            <tr><td><a href="downloadUsers.jsp" target="_blank">Download User Info<img src="images/excel.jpg" width="32px" height="32px"/></a></td></tr>
         
            <%//populate Admin Roles
                }

                if (userRole != null) {
            %>
            <tr><td class=link><br/> <a href="workspace.jsp?showpage=changepassword.jsp" class=link>Change Password</a>&nbsp;</td></tr>
                    <%
                        if (userRole.equalsIgnoreCase("user")) {
                    %>
            <tr><td class=link> <a href="workspace.jsp?showpage=updateDetails.jsp" class=link>Update Profile</a>&nbsp; </td></tr>
            <% }%>
            <%if (userRole != null && (!userRole.trim().equalsIgnoreCase("EXECADMIN") || !userRole.trim().equalsIgnoreCase("ADMIN"))) {
            %>
            <tr><td class=link><a href="resetSelf.jsp" class=link>Reset Login Status</a> &nbsp;<br/>&nbsp;<br/></td></tr>
                    <% }
                    %>
            <tr><td class=link><a href="logout.jsp" class=link>Logout</a></td></tr>
            <%                            //add generic actions
            } else if (userRole == null) {
            %>
            <span> Need to Login </span>   
            <%            }
            } catch (Exception exp) {
            %>
            <span> exception found: <%=exp.getMessage()%></span>
            <%
                }
            %>
        </table>
    </body>
</html>
