// Firma del riepilogo
function SignClickTest()
{
	var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";
	return SignContentTest(content);
}

function SignContentTest(content)
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
				alert("Nessun certificato presente nello store. Impossibile completare l'operazione di firma");
				return false;
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
