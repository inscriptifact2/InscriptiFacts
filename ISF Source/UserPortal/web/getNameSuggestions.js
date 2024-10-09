       
var xmlhttp;

function loadContent()
{
   
    fname=document.getElementById('firstName').value
 
    lname=document.getElementById('lastName').value
   
    email=document.getElementById('primaryEmail').value
 
    mobile=document.getElementById('primaryMobile').value
    if(fname=="" || lname=="" || email=="" ){
        alert ("Please fill the mandatory fields first");
        return;
    }
  
    xmlhttp=GetXmlHttpObject();

    if (xmlhttp==null)
    {
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    var url="getSuggestedNames.jsp";
    url=url+"?fname="+fname+"&lname="+lname+"&mobile="+mobile+"&email="+email;

    xmlhttp.onreadystatechange=getOutput;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}

function getOutput()
{
    if (xmlhttp.readyState==4)
    {
        document.getElementById('names').innerHTML=xmlhttp.responseText;
    }
}

function GetXmlHttpObject()
{
    if (window.XMLHttpRequest)
    {
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject)
    {
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}

function selectName(str)
{
    document.getElementById('userIdentity').value = str;
    document.getElementById('uags').style.display='block';
}

function checkUniqueName()
{
    xmlhttp=GetXmlHttpObject();
    fname=document.getElementById('userIdentity').value;

    if (xmlhttp==null)
    {
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    var url="checkUniqueUid.jsp";
    url=url+"?uid="+fname;

    xmlhttp.onreadystatechange=getResults;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
    
}

function getResults()
{
    if (xmlhttp.readyState==4)
    {
        unique = parseInt(xmlhttp.responseText);
        var obj = document.getElementById('stats');
        if(unique==0){
            if(document.getElementById('uags'))
                document.getElementById('uags').style.display='block';
            obj.style.color='#006600';
            obj.innerHTML="Available";
        }else if(unique==1){
            if(document.getElementById('uags'))
                document.getElementById('uags').style.display='none';
            document.getElementById('userAgreementStatus').value = "D"; 
            obj.style.color='#990000';
            obj.innerHTML="Unavailable";
        }
        
            
    }
}


function refreshStatistics()
{
    xmlhttp=GetXmlHttpObject();
   

    if (xmlhttp==null)
    {
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    var url="getStatistics.jsp";
    xmlhttp.onreadystatechange=doPopulateStats;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
    
}

function doPopulateStats()
{
    if (xmlhttp.readyState==4)
    {
        var  values = String(xmlhttp.responseText);
        var arrVals = values.split(",");
        document.getElementById("l12").innerHTML="<b> "+arrVals[0]+"</b>";
        document.getElementById("l24").innerHTML="<b> "+arrVals[1]+"</b>";
        document.getElementById("la").innerHTML="<b> "+arrVals[2]+"</b>";
           
    } 
}


