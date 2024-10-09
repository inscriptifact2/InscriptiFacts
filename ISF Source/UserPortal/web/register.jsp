<%-- 
    Document   : register
    Created on : Jan 23, 2013, 6:19:18 AM
    Author     : raok1
--%>
<%@page import="com.isf.dao.UserDAO"%>
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
        <script type="text/javascript" >
            function confirmAction(flag)
            {
                if(flag)
                {
                    $('userAgreementStatus').value = "A";
                    $('createdBy').value = "Self";
                    $('submit').disabled=false;
                    
                }else
                {
                    $('userAgreementStatus').value = "D"; 
                    window.location="index.jsp";
                }
            }
            function showmodal()
            {
                $('txt').innerHTML = '<h1 align="center">User Agreement</h1>'+
                    '<table width="90%" align="center"><tr><td align="left" class="mtd"> I agree to use the InscriptiFact digital library under the following conditions:</td></tr>'+
                    '<tr><td class="mtd">&nbsp;</td></tr>'+
                    '<tr><td align="left" class="mtd">1. All images accessed through InscriptiFact can only be used for study or classroom purposes.</td></tr>'+
                    '<tr><td>&nbsp;</td></tr>'+
                    '<tr><td align="left" class="mtd">2. Reproduction of any image accessed from InscriptiFact for purposes of publication, e.g., in a book or article-length study or '+
                    'for any commercial purpose, requires prior written permission from the owner(s) of the original text(s) and holder(s) of the photographic rights to the'+
                    ' original image. Anyone wishing to publish such an image must further agree to supply all proper credits as specified by the owner(s) of the original '+
                    'text and holder(s) of the photographic rights. Anyone wishing to publish such an image must also comply with any further conditions and/or '+
                    'restrictions required by the original owner(s) and/or holder(s) of the photographic rights.</td></tr><tr><td>&nbsp;</td></tr>'+
                    '<tr><td align="left" class="mtd">3. Reproduction of any image accessed from InscriptiFact for purposes of reproduction in any form on the Internet also requires '+
                    'prior written permission from the owner(s) of the original text(s) and holder(s) of the photographic rights to the original image. Anyone wishing to '+
                    'reproduce such an image must further agree to supply all proper credits as specified by the owner(s) of the original text and holder(s) of the '+
                    'photographic rights. Anyone wishing to publish such an image must also comply with any further conditions and/or restrictions required by the original'+
                    ' owner(s) and/or holder(s) of the photographic rights.</td></tr>'+
                    '<tr><td>&nbsp;</td></tr><tr><td class="mtd"> <button onclick="hm(\'box\');confirmAction(true)">Accept</button> '+
                    '<button onclick="hm(\'box\');confirmAction(false)">Decline</button> </td></tr>'+
                    '</table>';
                sm('box',650,400);
            }
            
            function hideUAGS()
            {
                document.getElementById('stats').innerHTML='';
                document.getElementById('uags').style.display='none';
                document.getElementById('userAgreementStatus').value = "D"; 
            }
            
        
        </script>
        <link rel="stylesheet" href="css/modaldbox.css" type="text/css" /> 
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

            String val = "";
        %>   
        <table width="95%" align="center"><tr><td height="40">
                    <h1 align="center"> InscriptiFact User Portal</h1>
                    <span class="error"><%= error%></span><br/>
                    <span class="message"><%= message%></span></td></tr>
            <tr>
                <td>
                    <form method="POST" action="userRegbk.jsp">
                        <input type="hidden" id="userRole" value="<%= userReg.getUserRole()%>" >
                        <table width="75%" align="center" class="regtable" >
                            <tr class="regtr">
                                <td colspan="4" class="headtd"> Personal Details</td>
                            </tr>
                            <tr class="regtr"> 
                                <td colspan="1" > Salutation : </td> 
                                <td colspan="1" >
                                    <select id="salutation" name="salutation" value="<jsp:getProperty name='userReg' property='salutation' />">
                                        <%
                                            val = userReg.getSalutation().trim();
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
                                <td colspan="1" >Suffix : </td>
                                <td colspan=1 ><input type=text id="nameSuffix" maxlength="5" size="5" value="<jsp:getProperty name='userReg' property='nameSuffix' />" >
                                </td>
                            </tr>

                            <tr>
                                <td colspan="2">
                                           <tr class="regtr">
                                            <td colspan="2" class="headtd">Primary Contact Details</td>
                                            <td colspan="2" class="headtd">Alternate Contact Details</td>
                                        </tr> 
                                        <tr class="regtr">
                                            <td colspan="1" >Phone Number:
                                                <br/> <small>(Please provide country code, local number, and extension if necessary.  Dashes, spaces, and parenthesis are optional)</small></td>
                                            <td colspan="1" >
                                                <input type=text id="primaryMobile" name="primaryMobile" maxlength="20" size="20" value="<jsp:getProperty name='userReg' property='primaryMobile' />"/>
                                            </td>
                                             <td colspan="1" >Phone Number: </td>
                                            <td colspan="1" >
                                                <input type=text id="altMobile" name="altMobile"maxlength="20" size="20" value="<jsp:getProperty name='userReg' property='altMobile' />"/>
                                            </td>
                                            
                                        </tr>
                                        <tr class="regtr">
                                            <td colspan="1" >Email <span>*</span>: </td>
                                            <td colspan="1" >
                                                <input type=text id="primaryEmail" name="primaryEmail" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='primaryEmail' />">
                                            </td>
                                            <td rowspan="4"  >Emails: </td>
                                            <td colspan="1" ><br/>
                                                <input type=text id="altEmail1" name="altEmail1" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='altEmail1' />">
                                            </td>
                                        </tr>
                                        <tr class="regtr">
                                            <td colspan="1" >Address Line 1: </td>
                                            <td colspan="1" >
                                                <input type=text id="add1" name="add1" maxlength="100" size="50" value="<jsp:getProperty name='userReg' property='add1' />">
                                            </td>
                                            <td>    <input type=text id="altEmail2" name="altEmail2"  maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='altEmail2' />" /></td>
                                        </tr>
                                        <tr class="regtr">
                                            <td colspan="1" >Address Line 2: </td>
                                            <td colspan="1" >
                                                <input type=text id="add2" name="add2" maxlength="100" size="50" value="<jsp:getProperty name='userReg' property='add2' />">
                                            </td>
                                            <td>      <input type=text id="altEmail3" name="altEmail3"maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='altEmail3' />"/></td>
                                        </tr>
                                        <tr class="regtr">
                                            <td colspan="1" >Zip Code: </td>
                                            <td colspan="1" >
                                                <input type=text id="zip" name="zip" maxlength="50" size="50" value="<%= userReg.getZip()%>">
                                            </td>
                                            <td><input type=text id="altEmail4" name="altEmail4" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='altEmail4' />" /></td>
                                        </tr>
                                        <tr class="regtr">
                                            <td colspan="1" >User City <span>*</span>: </td>
                                            <td colspan="1" >
                                                <input type=text id="userCity" name="userCity" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='userCity' />"/>
                                            </td>
                                            <td colspan="1" >Alternate City: </td>
                                            <td colspan="1" >
                                                <input type=text id="altCity" name="altCity" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='altCity' />"/>
                                            </td>
                                        </tr>
                                        <tr class="regtr">
                                            <td colspan="1" >User State: </td>
                                            <td colspan="1" >
                                                <input type=text id="userState" name="userState" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='userState' />"/>
                                            </td>
                                            <td colspan="1" >Alternate State: </td>
                                            <td colspan="1" >
                                                <input type=text id="altState" name="altState" maxlength="50" size="50" value="<jsp:getProperty name='userReg' property='altState' />"/>
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
                                            <td colspan="1" >Alternate Country: </td>
                                            <td colspan="1" >
                                                <select id="altCountry"  name="altCountry" value="<%= userReg.getAltCountry()%>">
                                                    <%
                                                        index = 0;
                                                        val = userReg.getAltCountry();
                                                        for (String s : UserRegistration.codes) {
                                                    %>
                                                    <option value="<%= s%>" <%  if (val.equalsIgnoreCase(s)) {%> selected <%}%>><%=UserRegistration.countries[index]%></option>
                                                    <%
                                                            index++;
                                                        }
                                                    %>
                                                </select>
                                            </td> 
                                        </tr>
                                    
                          


                            <tr class="regtr">
                                <td colspan="4" > &nbsp;</td>
                            </tr>
                            <tr class="regtr">
                                <td colspan="4" class="headtd"> Institution Details</td>
                            </tr>

                            <tr class="regtr">
                                <td colspan="1" >Primary Institution: </td>
                                <td colspan="1" >
                                    <input type=text id="priInstitution" name="priInstitution"maxlength="50" size="50" value="<%= userReg.getPriInstitution()%>" >
                                </td>
                                <td colspan="1" >Alternate Institution: </td>
                                <td colspan="1" >
                                    <input type=text id="altInstitution" name="altInstitution"maxlength="50" size="50" value="<%= userReg.getAltInstitution()%>">
                                </td>
                            </tr>
                            <tr class="regtr">
                                <td colspan="1" >Primary Inst. Country: </td>
                                <td colspan="1" >
                                    <select id="priInstCountry" name="priInstCountry" value="<%= userReg.getPriInstCountry()%>">
                                        <%

                                            index = 0;
                                            val = userReg.getPriInstCountry();
                                            for (String s : UserRegistration.codes) {
                                        %>
                                        <option value="<%= s%>" <%  if (val.equalsIgnoreCase(s)) {%> selected <%}%>><%=UserRegistration.countries[index]%></option>
                                        <%
                                                index++;
                                            }
                                        %>
                                    </select>
                                </td>
                                <td colspan="1" >Alternate Inst. Country: </td>
                                <td colspan="1" >
                                    <select id="altInstCountry" name="altInstCountry" value="<%= userReg.getAltInstCountry()%>" >
                                        <%
                                            index = 0;
                                            val = userReg.getAltInstCountry();
                                            for (String s : UserRegistration.codes) {
                                        %>
                                        <option value="<%= s%>" <%  if (val.equalsIgnoreCase(s)) {%> selected <%}%>><%=UserRegistration.countries[index]%></option>
                                        <%
                                                index++;
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>


                            <tr class="regtr">
                                <td colspan="4" > &nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="4">&nbsp; 
                                    <input type="hidden" id="action" name="action" value="<%= userReg.getAction()%>"/>
                                    <input type="hidden" id="createdBy" name="createdBy" value="<%= userReg.getCreatedBy()%>"/>
                                </td>
                            </tr>
                             
                            <%

                                if (!userReg.getAction().trim().equalsIgnoreCase("UPDATE")) {
                            %>

                            <tr class="regtr">
                                <td colspan="4" class="headtd"> Login Details</td>
                            </tr>          
                            <tr class="regtr">
                                <td colspan="1" >UserID<span>*</span>: </td>
                                <td colspan="1" >
                                    <input type=text id="userIdentity" name="userIdentity"maxlength="10" size="10"  value="<%= userReg.getUserIdentity()%>" onfocus="javascript:hideUAGS();" onblur="javascript:checkUniqueName();"><span id="stats" style="font-size:10px"></span>
                                   
                                </td>
                                <td colspan="2" >
                                    <a href="javascript:loadContent()">Check for Unique UserID</a><div id="names"></div>
                                </td>
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
                            
                                <td colspan=4 align="center"> <div id="uags" style="display:none">Please view and accept the <a href="javascript:showmodal()">User Agreement</a> and then click Register
                                    </div><input type="hidden" id="userAgreementStatus" name="userAgreementStatus" value="<%= userReg.getUserAgreementStatus()%>"/>

                                </td>
                            
                            </tr>
                            <%
                                
                            } else if (userReg.getAction().trim().equalsIgnoreCase("UPDATE")) {
                            %>
                            <input type="hidden" id="userIdentity" name="userIdentity"   value="<%= userReg.getUserIdentity()%>">
                           
                            <%
                                }
                            %>

                            <tr>
                                <td colspan="4">&nbsp;</td>
                            </tr>
                            <tr class="regtr"> 
                                <%
                                    if (!userReg.getAction().trim().equalsIgnoreCase("UPDATE")) {
                                %>
                                <td colspan="2" align="center" >
                                    <input type="submit" id="submit" value="Register" style="font-weight: bold;font-size:14px;" <%  if (!val.equalsIgnoreCase("A")) {%> disabled <%}%>/>
                                </td>
                                <%} else {%>
                                <td colspan="2" align="center" >
                                    <input type="submit" id="submit" value="Update" style="font-weight: bold;font-size:14px;" />
                                </td>
                                <%
                                }
                                            %>

                                <td colspan="2" align="center" >
                                    <input type="button" name="btnclear" value="Clear" onclick="this.form.reset()"/>
                                </td>
                            </tr>
                        </table>
                    </form>
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