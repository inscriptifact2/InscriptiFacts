<%-- 
    Document   : addUser
    Created on : Jan 26, 2013, 11:59:18 AM
    Author     : raok1
--%>

<%@page import="java.util.Calendar"%>
<%@page import="com.isf.beans.UserRegistration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css" />
        <script type="text/javascript" src="getNameSuggestions.js"> </script>
        <link rel="stylesheet" href="css/style.css" />
        <script type="text/javascript" src="css/modaldbox.js"> </script>
        <script type="text/javascript">
            function bypassAgreement(){
               if( document.getElementById("agreement").checked)
                   document.getElementById("userAgreementStatus").value="A";
               else
                   document.getElementById("userAgreementStatus").value="D";
                   
            }
            </script>
    </head>
    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">
        <jsp:useBean id="userReg" scope="session" class="com.isf.beans.UserRegistration" />

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

        <table width="100%" border="0">
            <h1 align="center"> InscriptiFact User Portal</h1>
            <span class="error"><%= error%></span><br/>
            <span class="message"><%= message%></span></td></tr>
    <tr><td>
            <form method="POST" action="userRegbk.jsp">

                <table width="75%" align="center"  >
                    <tr class="regtr">
                        <td colspan="4" class="headtd"> Personal Details</td>
                    </tr>
                    <tr class="regtr"> 
                        <td colspan="1" > Salutation : </td> 
                        <td colspan="1" >
                            <select id="salutation" name="salutation" value="<jsp:getProperty name='userReg' property='salutation' />">
                                <%
                                    String val = userReg.getSalutation().trim();
                                %>

                                <option value="" <%  if (val.equalsIgnoreCase("")) {%> selected <%}%>>--Select--</option>
                                <option value="Mr." <%  if (val.equalsIgnoreCase("Mr.")) {%> selected <%}%>>Mr.</option>
                                <option value="Mrs." <%  if (val.equalsIgnoreCase("Mrs.")) {%> selected <%}%>>Mrs.</option>
                                <option value="Ms." <%  if (val.equalsIgnoreCase("Ms.")) {%> selected <%}%>>Ms.</option>
                                <option value="Miss" <%  if (val.equalsIgnoreCase("Miss")) {%> selected <%}%>>Miss</option>
                                <option value="Dr." <%  if (val.equalsIgnoreCase("Dr.")) {%> selected <%}%>>Dr.</option>
                                <option value="Rev." <%  if (val.equalsIgnoreCase("Rev.")) {%> selected <%}%>>Rev.</option> 
                                <option value="Prof." <%  if (val.equalsIgnoreCase("Prof.")) {%> selected <%}%>>Prof.</option> 
                            </select> 
                        </td>
                        <td colspan="1" >Suffix : </td>
                        <td colspan=1 ><input type=text id="nameSuffix" maxlength="5" size="5" value="<jsp:getProperty name='userReg' property='nameSuffix' />" >
                        </td>
                    </tr>

                    <tr class="regtr"> 
                        <td colspan="1" >Last Name <span>*</span>: </td>
                        <td colspan=1 >
                            <input type=text id="lastName"  name="lastName" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='lastName' />">
                        </td>
                        <td colspan="1" > First Name <span>*</span>:</td>
                        <td colspan="1" > 
                            <input type=text id="firstName" name="firstName" maxlength="25" size="50" value="<jsp:getProperty name='userReg' property='firstName' />">
                        </td>
                    </tr>
                    <tr class="regtr">
                        <td colspan="1" >Middle Name/ Initial :</td>
                        <td colspan=1 >
                            <input type=text id="middleName" name="middleName" size="50" maxlength="20" value="<jsp:getProperty name='userReg' property='middleName' />">
                        </td>
                        <td colspan="1" >User Role<span>*</span>: </td>
                        <td colspan="1" >
                            <select id="userRole" name="userRole" value="<%= userReg.getUserRole()%>">
                                <%
                                    val = userReg.getUserRole().trim();
                                %>

                                <option value="USER" <%  if (val.equalsIgnoreCase("USER")) {%> selected <%}%>>USER</option>
                                <option value="ADMIN" <%  if (val.equalsIgnoreCase("ADMIN")) {%> selected <%}%>>ADMIN</option>
                               <!-- <option value="GROUPADMIN" <%  if (val.equalsIgnoreCase("GROUPADMIN")) {%> selected <%}%>>GROUPADMIN</option> -->

                            </select> 
                        </td>
                    </tr>
                    <tr class="regtr">
                        <td colspan="1" >Primary Email <span>*</span>: </td>
                        <td colspan="1" >
                            <input type=text id="primaryEmail" name="primaryEmail" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='primaryEmail' />">
                        </td>

                        <td colspan="1" >Primary Phone Number:
                            <br/> <small>(Please provide country code, local number, and extension if necessary.  Dashes, spaces, and parenthesis are optional)</small></td>
                        <td colspan="1" >
                            <input type=text id="primaryMobile" name="primaryMobile" maxlength="20" size="20" value="<jsp:getProperty name='userReg' property='primaryMobile' />"/>
                        </td>

                    </tr>
                    <tr class="regtr">
                        <td colspan="1" >User City <span>*</span>: </td>
                        <td colspan="1" >
                            <input type=text id="userCity" name="userCity" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='userCity' />"/>
                        </td>


                        <td colspan="1" >User State: </td>
                        <td colspan="1" >
                            <input type=text id="userState" name="userState" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='userState' />"/>
                        </td>

                    </tr>

                    <tr class="regtr">
                        <td colspan="1" >User Country<span>*</span>: </td>
                        <td colspan="1" >
                            <select id="userCountry" name="userCountry" value="<jsp:getProperty name='userReg' property='userCountry' />">
                                <%
                                    int index = 0;
                                    val = userReg.getUserCountry();
                                    for (String s : UserRegistration.codes) {

                                %>
                                <option value="<%= s%>" <%  if (val.equalsIgnoreCase(s)) {%> selected <%}%> ><%=UserRegistration.countries[index]%></option>
                                <%
                                        index++;
                                    }
                                %>
                            </select>
                        </td>


                        <td colspan="1" >Zip Code: </td>
                        <td colspan="1" >
                            <input type=text id="zip" name="zip" maxlength="50" size="50" value="<%= userReg.getZip()%>">
                        </td>
                    </tr>




                    <tr class="regtr">
                        <td colspan="4" > &nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="4">&nbsp; 
                            <input type="hidden" id="action" name="action" value="ADD"/>
                            <input type="hidden" id="createdBy" name="createdBy" value="<%= userReg.getCreatedBy()%>"/>

                        </td>
                    </tr>
                    <tr class="regtr">
                        <td colspan="4" class="headtd"> Login Details</td>
                    </tr>          
                    <tr class="regtr">
                        <td colspan="1" >UserID<span>*</span>: </td>
                        <td colspan="1" >
                             <input type=text id="userIdentity" name="userIdentity"maxlength="10" size="10"  value="<%= userReg.getUserIdentity()%>"  onblur="javascript:checkUniqueName();"><span id="stats" style="font-size:10px"></span>
                        </td>
                        <td colspan="2" >
                            <a href="javascript:loadContent()">Check for Unique UserID</a><div id="names"></div>
                        </td>
                    </tr>
                    <tr class="regtr">

                    </tr>
                    <tr class="regtr">
                        <td colspan="1" >Password<span>*</span>: </td>
                        <td colspan="1" >
                            <input type=password id="userPassword" name="userPassword"maxlength="10" size="10"  value="<%= userReg.getUserPassword()%>">
                        </td>
                        <td colspan="1" >Confirm Password<span>*</span>: </td>
                        <td colspan="1" >
                            <input type=password id="cnfPassword" name="cnfPassword"maxlength="10" size="10" value="<%= userReg.getCnfPassword()%>">
                        </td>
                    </tr>
                    <tr class="regtr">
                        <td colspan="2" ><input type="checkbox" id="agreement" onclick="javascript:bypassAgreement();"/>Bypass User Agreement ? 
                              <input type="hidden" id="userAgreementStatus" name="userAgreementStatus" value="<%= userReg.getUserAgreementStatus()%>"/>
                        </td>
                       
                    </tr>
                    
                    <tr class="regtr">

                        <td colspan="2" align="center" >
                            <input type="submit" id="submit" value="Add" />
                        </td>


                        <td colspan="2" align="center" >
                            <input type="button" name="btnclear" value="Clear" onclick="this.form.reset()"/>
                        </td>
                    </tr>
                </table>
            </form> 
    <tr><td colspan="2"><hr size="1" noshade></td></tr><!--PAGE FOOTER-->
    <tr><td colspan="2">
            <div align="center">
                <font size="-1" color="#525D76"><em>Copyright &copy; 2001-<%=Calendar.getInstance().get(Calendar.YEAR)%>, University of Southern California.  All Rights Reserved</em></font>
            </div>
        </td>
    </tr>
</table>
<div id="box" class="dialog">
    <div style="text-align:center"><span id="txt">

        </span>
    </div>
</div>
</body>
</html>