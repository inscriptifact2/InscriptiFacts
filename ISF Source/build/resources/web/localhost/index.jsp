<html>
<head>
<title>:: Instructions ::</title>
<style>
.title { font-family: Trebuchet MS,Arial,Tahoma; font-size: 14px; font-weight: bold; color: #7F5000; }
.subhead { font-family: Trebuchet MS,Arial,Tahoma; font-size: 15px; font-weight: bold; color: #382300; }
A{font-family: Trebuchet MS,Arial,Tahoma; font-size: 13px; font-weight: bold; color: midnightblue;}
P { font-family: Trebuchet MS,Arial,Tahoma; font-size: 13px; font-weight: normal; color: #382300; }
LI { font-family: Trebuchet MS,Arial,Tahoma; font-size: 13px; font-weight: normal; color: #382300; }
</style>
</head>

<!-- Obtain the OS Name -->
<%
    String userAgent = request.getHeader("User-Agent").toUpperCase();
	System.out.println("userAgent is:"+userAgent);
%>
<body>
 
<table cellpadding="0" cellspacing="0" border="0" width="75%" height="30" bgcolor="#FFF4ED">

<tr><td >
     <a href="http://www.inscriptifact.com/instructions/index.shtml" target="_new" > Instructions for Using Inscriptifact with Samples Searches.</a>
<br><br>

</td></tr>

<tr><td bgcolor="#FFFFFF" class="title"><center>INSTRUCTIONS FOR DOWNLOADING THE INSCRIPTIFACT WEB CLIENT </center></td></tr>
<tr><td bgcolor="#FFFFFF" class="subhead"><center>(Note:&nbsp;<small>  Click to view <i><b><a href="config.html" target="_new" >
Suggested Configuration for Client Machines</a></B></i></small>)</center></td></tr>

</table>
<br>
<table cellpadding="0" cellspacing="1" border="0" width="75%" height="30" bgcolor="#FFF4ED">

<%
    
    if(userAgent.indexOf("WIN")>-1 || userAgent.indexOf("MAC")==-1) //// this is a windows or linux machine. Proceed normally
    {
    %>

<tr><td class="subhead">PC users</td></tr>
<tr><td><p>
You will need to install the latest update to Java Runtime Environment (JRE)<br>
Download the latest version of JRE (free of charge) from <a href="http://java.com/en/download/index.jsp">here</a><br>
Reboot your machine after installing the JRE.<br/><br/>
</p></td></tr>


<tr><td class="subhead"><span class="title">To Start InscriptiFact:</span>
<br/>
Click on the banner:
<a href="/ISFAPP_V9105.jnlp"><img src="/images/isf_banner.jpg" width="300" height="44" alt="Download web client" border="0"></a></LI>
<br>
You'll receive a warning, but start anyway.  Ignore the message and proceed. The InscriptiFact Project verifies that this code is safe.<br/>
<br/>
If Inscriptifact does not automatically launch:
<UL type="disc">
<li> Go to your Browser Downloads.</li>
<li> Move the file ISFAPP_V9105.jnlp to your desktop.</li>
<li> Click on the .jnlp file to start InscriptiFact.</li>
</ul>
<br/>
For subsequent use of InscriptiFact, start InscriptiFact by clicking the .jnlp file.

<br></td></tr>
<tr><td><br></td></tr>

<%
    }
    else if( userAgent.indexOf("MAC")>-1 ) // MAC machine detected
    {
%>

<tr><td class="subhead">Mac users</td></tr>
<tr><td><p>InscriptiFact is compatible with Mac OS X 10.5 and higher. </p></td></tr>
<tr><td><br>
Run your SOFTWARE UPDATE program to make sure that you have the most recent version of Java.<br/><br/>

</td></tr>
<tr><td class="subhead"><span class="title">To Start Inscriptifact</span></td></tr>
 <tr><td>  <br/>
 <UL type="disc">
 <li>Click on the banner:
<a href="/ISFAPP_V9105_INTELMAC.jnlp"><img src="/images/isf_banner.jpg" width="300" height="44" alt="Download web client" border="0"></a></LI>
</li>
<li> Go to your Browser Downloads</li>
<li> Move the file ISFAPP_V9105L_INTELMAC.jnlp to your desktop</li>
<li> Click on the .jnlp file to launch InscriptiFact </li>
<li> You'll receive a warning, but start anyway. Ignore the message and proceed. The InscriptiFact Project verifies that this code is safe. </li>
</ul></td></tr>
<br/>


  <%
    }
  %>
  
</table>

</body>
</html>