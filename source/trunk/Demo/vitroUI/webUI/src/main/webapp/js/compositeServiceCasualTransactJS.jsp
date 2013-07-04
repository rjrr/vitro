<%@page session='false' contentType='application/x-javascript' import='java.util.*'
 %>


 
var globalstatusDescDeployComposite = '&nbsp;';


function alertResultDeployComposite()
{
    if (globalstatusDescDeployComposite.toLowerCase() != "OK".toLowerCase())
        alert('There was an error while submitting your VSN request: ' + globalstatusDescDeployComposite);
    else
        alert('Your VSN deploy request was submitted successfully\nFrom the top menu, select Monitor Service to view its status!');

    globalstatusDescDeployComposite = '&nbsp;';
}

function transactAftermathDeployComposite(check, statusDescription, mode)
{
	//if(mode==0) //? special checks
	//{
            globalstatusDescDeployComposite = statusDescription;
            setTimeout("alertResultDeployComposite()", 100);
	//}
}


function RPostDeployComposite(strpost, mode){
	var xmlhttp=null;
	xmlhttp=getXMLHTTPRequest();
	if (xmlhttp==null )
	{
            alert("Your browser does not support XMLHTTP.");
	}
	else
	{
            //alert(strpost);
            xmlhttp.open("POST", "<%=request.getContextPath()%>/roleEndUser/nTransactCompositeServiceParam.jsp", true);
            xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');

            xmlhttp.send(strpost);
            xmlhttp.onreadystatechange=function()
            {
                if (xmlhttp.readyState==4)
                {
                    if (xmlhttp.status==200)
                    {
                        XML_Response = xmlhttp.responseXML;
                        //alert(XML_Response);
                        ParseXMLDeployComposite(XML_Response, mode);
                    }
                    else
                    {
                        globalstatusDescDeployComposite ="Problem retrieving XML data";
                        setTimeout("alertResultDeployComposite()", 100);
                    }
                }
            }
	 }
}
     
function ParseXMLDeployComposite(oxml, mode)
{
	var doc = oxml.documentElement;
	var xmlerror = doc.getElementsByTagName('error');
	var errortag = xmlerror[0];
	var errorno = errortag.getAttribute('errno');
	var errordescr = errortag.getAttribute('errdesc');
	transactAftermathDeployComposite(errorno, errordescr, mode);
}

  

  
// first argument is the friendly name of the composite function.
function deployCompositeService()
{
    globalstatusDescDeployComposite = '&nbsp;';
	var argus = arguments.length;
                               
 	// form fields
        
	var strSend='';
    var friendlyName ='';
    var serviceId = -1;

        
	if( (argus >= 1) && (argus <= 2))
	{
		friendlyName = arguments[0]; // (It could have spaces!)
        serviceId = arguments[1];  // a number
    }

	strSend = strSend + 'pfriend='+friendlyName+ '&pid='+ serviceId;
	//alert(strSend);
	RPostDeployComposite(strSend, serviceId);

}


