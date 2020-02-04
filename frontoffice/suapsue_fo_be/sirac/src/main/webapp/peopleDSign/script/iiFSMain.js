// Firma del riepilogo
function SignClick()
{
	var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";
	return SignContent(content);
}

function SignContent(content)
{
	var objSign=new WebSign();
	var err=null;
	var response=null;
	try
	{
		var ret=objSign.SetCertificateFilter(FILTER_NON_REPUDIATION_ENABLED);
		if(ret==RETURN_ERROR_VALUE)
		{
			alert("Si è verificato un errore:\n" + objSign.LastError());
			return false;
		}
		//tentativo di firma con certificato avente il flag di non ripudio abilitato
		if(objSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED)==RETURN_ERROR_VALUE)
		{
			err=objSign.LastError();
			response=false;
			//verifico se lo store è vuoto: in questo caso chiedo all'utente se vuole procedere con la firma remota
			if(err==ERROR_NONE_CERTIFICATE)
			{
				if(remoteSignEnabled)
				{
					response=confirm((remoteSignDisclaimer=="" ? "Procedere con la firma remota ?" : remoteSignDisclaimer));
					if(remoteSignEnabled&&response)//firma remota abilitata
					{
						SignContent_nextAction();
						return false;
					}
					else
					{
						alert(ERROR_SIGN_OPERATION_ABORTED);
						return false;
					}
				}
				else
				{
					alert(ERROR_UNAVAILABLE_CERTIFICATE_DISABLED_REMOTE_SIGN);
					return false;
				}
			}
			response=false;
			if(weakSignEnabled&&(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)) response=confirm((weakSignDisclaimer=="" ? "Si intende procedere con la firma debole ?" : weakSignDisclaimer));	
			if(weakSignEnabled&&response)//firma debole abilitata
			{
				if(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)
				{
					//firma con certificato avente il flag "digital signature" abilitato e il flag "non ripudio" disabilitato
					var ret=objSign.SetCertificateFilter(FILTER_DIGITAL_SIGNATURE_ENABLED|FILTER_NON_REPUDIATION_DISABLED);
					if(ret==RETURN_ERROR_VALUE)
					{
						alert("Si è verificato un errore:\n" + objSign.LastError());
						return false;
					}
					if(objSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED)==RETURN_ERROR_VALUE)//errore avvenuto durante la firma debole
					{
						err=objSign.LastError();
						response=false;
						if(remoteSignEnabled&&(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)) response=confirm((remoteSignDisclaimer=="" ? "Procedere con la firma remota ?" : remoteSignDisclaimer));
						if(remoteSignEnabled&&response)//firma remota abilitata
						{
							if(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)
							{
								//firma remota
								SignContent_nextAction();
								return false;
								
							}
							else//errore generico in fase di firma debole; lo visualizzo
							{
								alert(err);
								return false;
							}
						}
						else//firma forte fallita, firma debole fallita, firma remota disabilitata; visualizzo l'errore
						{
							alert(err);
							return false;
						}
					}
					else//firma debole avvenuta con successo
					{
						var signedData = document.getElementById("signedData");
						signedData.value = objSign.ContentSigned;
						return true;
					}
				}
				else//errore generico in fase di firma forte, lo visualizzo
				{
					alert(err);
					return false;
				}
			}
			else 
			{
				response=false;
				if(remoteSignEnabled&&(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)) response=confirm((remoteSignDisclaimer=="" ? "Procedere con la firma remota ?" : remoteSignDisclaimer));
				if(remoteSignEnabled&&response)//firma debole non abilitata, firma remota abilitata
				{
					if(err==ERROR_NONE_FILTERED_CERTIFICATE||err==ERROR_SIGN_OPERATION_ABORTED)
					{
						//firma remota
						SignContent_nextAction();
						return false;
					}
					else//errore generico, lo visualizzo
					{
						alert(err);
						return false;
					}
				}
				else//firma forte errore, firma debole non abilitata, firma remota non abilitata
				{
					alert(err);
					return false;
				}
			}
		}
		else //firma forte avvenuta con successo
		{
			var signedData = document.getElementById("signedData");
			signedData.value = objSign.ContentSigned;
			return true;
		}
	}
	catch (exc)
	{
		alert("Si e' verificato il seguente errore:\n" + exc.description);
		return false;
	}
}

function SignContentOld(content)
{
	var ObjSign = new WebSign();
	if(ObjSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED) == RETURN_ERROR_VALUE)
	{
		alert(ObjSign.LastError());
		//return false;
		// Cerca di eseguire, se definita, la funzione SignContent_nextAction
        try {
			SignContent_nextAction();
			return false;
        } catch(e) {return false;}
	}	
	else
	{
		var signedData = document.getElementById("signedData");
		signedData.value = ObjSign.ContentSigned;
		return true;
	}	
}

function EnableSignButton(signBtnId)
{
    var signBtn=document.getElementById(signBtnId);
    if(signBtn!=null)
        signBtn.disabled=false;
}

function EnableRemoteSignDocLink(docLnkId)
{
    var docLnk=document.getElementById(docLnkId);
    docLnk.attributes["class"].value="visible";
    docLnk.setAttribute("target","_blank");
}