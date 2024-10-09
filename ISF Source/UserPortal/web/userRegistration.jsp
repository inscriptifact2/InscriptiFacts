
<%@page import="com.isf.beans.UserRegistration"%>
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
        
        <link rel="stylesheet" href="css/style.css" />
<!--        <script type="text/javascript" src="css/modaldbox.js"> </script>
                <script type="text/javascript" src="getNameSuggestions.js"> </script>
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
            function selectName(str)
            {
                $('userIdentity').value = str;
            }
        
        </script>
        <link rel="stylesheet" href="css/modaldbox.css" type="text/css" /> -->
    </head>
    <body vlink="#525D76" alink="#525D76" link="#525D76" text="#000000" bgcolor="#FFF4ED">

        <jsp:useBean id="userReg" scope="session" class="com.isf.beans.UserRegistration"  />

        <%
            String loggedUser=null;
           if(session!=null){
              session.invalidate();
              }
           
            
        %>


        <table width="100%" border="0">
            <%     if(loggedUser==null){ %>
            <tr><td height="160">
                    <jsp:include page="header.jsp" /></td></tr> <% } %>
            
            <tr><td>
                    <jsp:include page="register.jsp"/>
          
                </td>
            </tr>
        </table>

        

    </body>
</html>
