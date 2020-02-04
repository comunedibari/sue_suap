function executeSubmit(newAction) {
    if (newAction != "") {
        document.forms[0].action = newAction;
    }   
    document.forms[0].submit();
}		

function SignClickNew() {
	var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";
	SignContentNew(content);
}

function SignContentNew(content) {

    var codiceFiscale = document.forms[0].codiceFiscale.value;
    // var hostname = 'people.gruppoinit.it';
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
        <param name = "InputIDToStoreResult" value = "signedData" />\
        <param name = "SubmitActionToPerform" value = "signStepComplete.do" />\
        <param name = "FormIDToSubmit" value = "signForm" />\
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
           InputIDToStoreResult = "signedData"\
           SubmitActionToPerform = "signStepComplete.do"\
           FormIDToSubmit = "signForm"\
           scriptable = "false"\
           pluginspage = "http://java.sun.com/products/plugin/index.html#download"\
        />\
      </object>';  
        newdiv.innerHTML = object;
        document.body.appendChild(newdiv);
       
    }

// Firma del riepilogo
//function SignClick() {
//	var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";
//	SignContent(content);
//}

// Firma di allegati
//function AttachmentSignClick() {
//	var content = document.getElementById("Content").innerHTML;
//	SignContent(content);	
//}

//function SignContent(content)
//{
//	var objSign=new WebSign();
//	var err=null;
//	var response=null;
//	
//	try
//	{
//		var ret=objSign.SetCertificateFilter(FILTER_NON_REPUDIATION_ENABLED);
//		if(ret==RETURN_ERROR_VALUE)
//		{
//			alert("Si e' verificato un errore:\n" + objSign.LastError());
//			return false;
//		}
//		//tentativo di firma con certificato avente il flag di non ripudio abilitato
//		if(objSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED)==RETURN_ERROR_VALUE)
//		{
//			err=objSign.LastError();
//			response=false;
//			
//			//verifico se lo store � vuoto: in questo caso chiedo all'utente se vuole procedere con la firma remota
//			if(err==ERROR_NONE_CERTIFICATE)
//			{
//				if(remoteSignEnabled)
//				{
//					response=confirm((remoteSignDisclaimer=="" ? "Procedere con la firma remota ?" : remoteSignDisclaimer));
//					if(remoteSignEnabled&&response)//firma remota abilitata
//					{
//						SignContent_nextAction();
//						return false;
//					}
//					else
//					{
//						alert(ERROR_SIGN_OPERATION_ABORTED);
//						return false;
//					}
//				}
//				else
//				{
//					alert(ERROR_UNAVAILABLE_CERTIFICATE_DISABLED_REMOTE_SIGN);
//					return false;
//				}
//			}
//						
//			if(weakSignEnabled&&(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)) response=confirm((weakSignDisclaimer=="" ? "Si intende procedere con la firma debole ?" : weakSignDisclaimer));
//			if(weakSignEnabled&&response)//firma debole abilitata
//			{
//				if(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)
//				{
//					//firma con certificato avente il flag "digital signature" abilitato e il flag "non ripudio" disabilitato
//					var ret=objSign.SetCertificateFilter(FILTER_DIGITAL_SIGNATURE_ENABLED|FILTER_NON_REPUDIATION_DISABLED);
//					if(ret==RETURN_ERROR_VALUE)
//					{
//						alert("Si ? verificato un errore:\n" + objSign.LastError());
//						return false;
//					}
//					if(objSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED)==RETURN_ERROR_VALUE)//errore avvenuto durante la firma debole
//					{
//						err=objSign.LastError();
//						response=false;
//						if(remoteSignEnabled&&(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)) response=confirm((remoteSignDisclaimer=="" ? "Procedere con la firma remota ?" : remoteSignDisclaimer));
//						if(remoteSignEnabled&&response)//firma remota abilitata
//						{
//							if(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)
//							{
//								//firma remota
//								SignContent_nextAction();
//								return false;
//								
//							}
//							else//errore generico in fase di firma debole; lo visualizzo
//							{
//								alert(err);
//								return false;
//							}
//						}
//						else//firma forte fallita, firma debole fallita, firma remota disabilitata; visualizzo l'errore
//						{
//							alert(err);
//							return false;
//						}
//					}
//					else//firma debole avvenuta con successo
//					{
//						var signedData = document.getElementById("signedData");
//						signedData.value = objSign.ContentSigned;
//						executeSubmit("signStepComplete.do");
//						return true;
//					}
//				}
//				else//errore generico in fase di firma forte, lo visualizzo
//				{
//					alert(err);
//					return false;
//				}
//			}
//			else 
//			{
//				response=false;
//				if(remoteSignEnabled&&(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)) response=confirm((remoteSignDisclaimer=="" ? "Procedere con la firma remota ?" : remoteSignDisclaimer));
//				if(remoteSignEnabled&&response)//firma debole non abilitata, firma remota abilitata
//				{
//					if(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)
//					{
//						//firma remota
//						SignContent_nextAction();
//						return false;
//					}
//					else//errore generico, lo visualizzo
//					{
//						alert(err);
//						return false;
//					}
//				}
//				else//firma forte errore, firma debole non abilitata, firma remota non abilitata
//				{
//					alert(err);
//					return false;
//				}
//			}
//			
//		}
//		else //firma forte avvenuta con successo
//		{
//			var signedData = document.getElementById("signedData");
//			signedData.value = objSign.ContentSigned;
//			executeSubmit("signStepComplete.do");
//			return true;
//		}
//	}
//	catch (exc)
//	{
//		alert("Si e' verificato il seguente errore:\n" + exc.description);
//		return false;
//	}
//}