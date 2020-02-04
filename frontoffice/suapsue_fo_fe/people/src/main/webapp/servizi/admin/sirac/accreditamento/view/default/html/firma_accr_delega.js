function SignClickNew(inputIDToStoreResult,action) {
//	var content = encodeURIComponent("<html><body>" + document.getElementById("Content").innerHTML + "</body></html>");
	var content = encodeURIComponent(document.getElementById("Content").innerHTML);
	SignContentNew(content,inputIDToStoreResult,action);
}

function SignContentNew(content,inputIDToStoreResult,action) {
    var codiceFiscale = ""; // document.forms[0].codiceFiscale.value
    var hostname = window.location.hostname;
    var port = window.location.port;
    if (port!="") {
       port=":"+port;
    }
    var protocol = window.location.protocol;
    var url = protocol+'//'+hostname+port+'/';
    var newdiv = document.createElement('div');
    newdiv.setAttribute('id','appletSign');
    var object = '<object height = "0" width = "0" classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" codebase = ".">\
        <param name = "height" value = "0" />\
        <param name = "width" value = "0" />\
        <param name = "code" value = "pdfconverterandsigner/ConverterAndSignerMain" />\
        <param name = "archive" value = "SignedpdfConverterAndSignerApplet.jar" />\
        <param name = "AppletMode" value = "normale" />\
        <param name = "downloadFilesServiceURL" value = "'+url+'firmasemplice/DownloadRequiredFiles" />\
        <param name = "TrustAllCertificate" value = "true" />\
        <param name = "TestFirma" value = "false" />\
        <param name = "InputFile" value = "'+content+'" />\
		<param name = "InputType" value = "string" />\
		<param name = "CodFisc" value = "" />\
        <param name = "InputIDToStoreResult" value = "'+inputIDToStoreResult+'" />\
        <param name = "SubmitActionToPerform" value = "'+action+'" />\
        <param name = "FormIDToSubmit" value = "pplProcess" />\
        <param name = "type" value = "application/x-java-applet" />\
        <param name = "scriptable" value = "false" />\
        <embed type = "application/x-java-applet"\
           height = "0" \
           width  = "0" \
           code = "pdfconverterandsigner/ConverterAndSignerMain"\
           archive = "SignedpdfConverterAndSignerApplet.jar"\
           AppletMode = "normale"\
           downloadFilesServiceURL = "'+url+'firmasemplice/DownloadRequiredFiles"\
           TrustAllCertificate = "true"\
           TestFirma = "false"\
           InputFile = "'+ content +'"\
		   InputType = "string"\
		   CodFisc = ""\
           InputIDToStoreResult = "'+ inputIDToStoreResult +'"\
           SubmitActionToPerform = "'+action+'"\
           FormIDToSubmit = "pplProcess"\
           scriptable = "false"\
           pluginspage = "http://java.sun.com/products/plugin/index.html#download"\
        />\
      </object>';  
        newdiv.innerHTML = object;
        document.body.appendChild(newdiv);
       
    }


// Firma del riepilogo

function SignClick()
{
	var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";
	SignContentNew(content);
}

function SignClickDelega()
{
  var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";
  SignContentDelega(content);
}


// Firma di allegati
function AttachmentSignClick()
{
	var content = document.getElementById("Content").innerHTML;
	SignContent(content);	
}

function SignContent(content)
{
	var ObjSign = new WebSign();
	if(ObjSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED) == RETURN_ERROR_VALUE) {
		alert(ObjSign.LastError());
        // Cerca di eseguire, se definita, la funzione SignContent_nextAction
        try {
          SignContent_nextAction();
        } catch (e) {
        }
	} else 	{
		var signedData = document.getElementsByName("data.accrIntrmForm.autoCert")[0];
		signedData.value = ObjSign.ContentSigned;
		document.forms[0].action = "nextStepProcess.do";
        document.forms[0].submit();
	}
  
}  
  
function SignContentDelega(content)
{
  var ObjSign = new WebSign();
  if(ObjSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED) == RETURN_ERROR_VALUE) {
    alert(ObjSign.LastError());
        // Cerca di eseguire, se definita, la funzione SignContent_nextAction
        try {
          SignContent_nextAction();
        } catch (e) {
        }
  } else  {
    var signedData = document.getElementById("data.delegaForm.certificazione");
    signedData.value = ObjSign.ContentSigned;
    document.forms[0].action = "nextStepProcess.do";
        document.forms[0].submit();
  }
  
}  
  

  	
