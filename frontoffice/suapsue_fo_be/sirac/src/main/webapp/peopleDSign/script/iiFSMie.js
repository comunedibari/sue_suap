// N.B. Richiede 2 Activex: CAPICOM ver 2.0.0.3 e CedafFSM ver 2.1.0.2
// versione: iiFSMie.js v.1.2 by Mauro Palumbo
//.......................................................................................................................................................................................................
/*
	Funzioni, classi e variabili esposti dalla libreria:
	Viene esposto una classe WebSign che comprende i seguenti attributi e metodi:
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Funzione Sign(Content,CType,SMode):int.Funzione che firma digitalmente una stringa o un file
			INPUT
			<Content:string> rappresenta o il contenuto da firmare (CType=CONTENT_TYPE_STRING) oppure il percorso
							 completo del filesystem a cui recuperare il file da firmare (CType=CONTENT_TYPE_FILE)
			<CType:int>	 	 rappresenta il tipo di contenuto da firmare:
							 	CType=CONTENT_TYPE_STRING firmo una stringa
							 	CType=CONTENT_TYPE_FILE firmo un file il cui percorso completo del filesystem � specificato in <Content>
			<SMode:int>	 	 specifica la modalita' di firma:
							 SignType=SIGN_MODE_ATTACHED firmo in modalita' attached (il base64
							                             che rappresenta la firma contiene anche
														 il doumento originale).
							 SignType=SIGN_MODE_DETACHED firmo in modalita' detached (il base64
														 che rappresenta la firma non contiene
														 il documento originale
		OUTPUT
			<ContentSigned:int>    RETURN_OK_VALUE se e' andato tutto a buon fine e <GetSigned> ritorna il contenuto firmato
								   RETURN_ERROR_VALUE se si e' verificato un errore e <GetLastError> ritorna l'errore verificatosi
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Funzione GetLastError():string.Restituisce l'ultimo errore verificatosi in seguito alla chiamata
								 di una funzione di questa libreria che abbia restituito valore RETURN_ERROR_VALUE
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Funzione VerifyExtract(SrcFName,DstFName):int.Funzione di verifica della firma digitale e di estrazione di un documento da un PKCS#7:
			INPUT
				 <SrcFName:string> path completo del file contenente il documento PKCS#7 o la firma digitale detached
				 <DstFName:string> path completo del file originale nel caso di firma detached,ignorato nel caso di firma attached
		 	OUTPUT
				<VerifyExtract:int> ritorna RETURN_OK_VALUE se la funzione termina correttamente
								    altrimenti ritorna RETURN_ERROR_VALUE
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		Funzione getBinaryContent(SrcFName):binaryString.Funzione per la valorizzazione di una variabile javascript con il contenuto di un file binario:
			INPUT
				 <SrcFName:string> path completo del file binario da caricare
		  	OUTPUT
				<getBinaryContent:binarystring> ritorna il contenuto del file binario in una BSTR se la funzione termina correttamente
								  altrimenti ritorna null e viene valorizzata la variabile di errore SignErr con l'errore occorso
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		Funzione getUnicodeContent(SrcFName):string. Funzione per la valorizzazione di una variabile javascript con il contenuto di un file in formato unicode:
			INPUT
			 	<SrcFName:string> path completo del file unicode da caricare	
	  		OUTPUT
				<getUnicodeContent:string> ritorna il contenuto del file unicode in una stringa se la funzione termina correttamente
							    		   altrimenti ritorna null e viene valorizzata la variabile di errore SignErr con l'errore occorso
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		Funzione Bin2Base64(SrcFName):string.Funzione che converte un file binario in una stringa Base64:
			INPUT
			 	<SrcFName:string> path completo del file binario da convertire
	  		OUTPUT
				<Bin2Base64:string> ritorna la stringa BASE64 equivalente al file binario se la funzione termina correttamente
							    	altrimenti ritorna null e viene valorizzata la variabile di errore SignErr con l'errore occorso
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		Funzione getExclusiveAccess(fileName):string.Funzione che verifica se un file � gi� aperto da un'altra applicazione oppure se � possibile guadagnare l'accesso esclusivo:
			INPUT
			 	<SrcFName:string> path completo del file da controllare
	  		OUTPUT
				<getExclusiveAccess:string> ritorna la stringa EXCLUSIVE_ACCESS_TRUE se � stato possibile guadagnare
							    			l'accesso esclusivo al file altrimenti ritorna EXCLUSIVE_ACCESS_FALSE. Se si verifica
											un errore ritorna RETURN_ERROR_VALUE e viene valorizzata la variabile d'errore SignErr.
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		Funzione pollingExclusiveAccess(fileName):boolean.Funzione che rimane in polling sul file specificato fino a quando non riesce a guadagnare l'accesso esclusivo ad esso:
			INPUT
				 <SrcFName:string> path completo del file da controllare
	  		OUTPUT
				<pollingExclusiveAccess:string> il metodo � bloccante e ritorna true se termina ottenendo l'accesso esclusivo
												al file, mentre ritorna false e viene valorizzata la variabile d'errore SignErr se termina a 
												causa di un errore.
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
		Variabile Certificate::CertificateInfo: istanza della classe CertificateInfo valorizzata con le proprieta'
							   					del certificato dell'utente corrente
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	Costanti Esposte:
		Costante SIGN_MODE_ATTACHED =  0: 	firma in modalita' attached
		Costante SIGN_MODE_DETACHED =  1: 	firma in modalita' detached
		Costante CONTENT_TYPE_STRING = 0:	firmo una stringa
		Costante CONTENT_TYPE_FILE =   1:	firmo un file accedendo al FileSystem
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	Ulteriore classe esposta, ma cablata in un oggetto di WebSign
		Classe CertificateInfo: contiene alcune informazioni principali di un certificato di firma digitale
			AuthorityName: 		Nome dell'autorita' di certificazione emittente il certificato
			AuthorityEmail: 	EMail dell'autorita' di certificazione emittente il certificato
			ValidFrom:			Data di partenza della validita' del certificato
			ValidTo:			Data di fine della validita' del certificato
			Subject:			Intestatario del certificato
			SerialNumber:		Numero seriale del certificato
			PublicKey:			Chiave pubblica del certificato
		'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	A partire dalla versione 1.2 degli script e 2.1.0.0 del controllo ActiveX CedafFSM, viene esposta anche la classe
	FileSystemManager con i seguenti metodi:
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	//Funzione Write(fileData): assegna alla variabile membro fileContent, il contenuto binario da salvare su file
	//con il metodo SaveToFile
	//	INPUT
	//		 <fileData:binaryString> dati da salvare nel file su filesystem
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	//Funzione Read(): restituisce la variabile membro fileContent, che contiene il contenuto binario del file
	//letto con il metodo LoadFromFile
	//	OUTPUT
	//		 <fRead:binaryString> dati binari contenuti nel file letto con il metodo LoadFromFile
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Funzione SaveToFile(fileName,fileMode): salva su un file identificato da filename il contenuto della variabile membro fileContent
	//	INPUT
	//		<fileName:String> percorso completo in cui salvare il file
	//		<fileMode:String> modalit� di creazione del file, a scelta tra FILE_CREATE_NEW e FILE_CREATE_ALWAYS;
	//	OUTPUT
	//		<SaveToFile:int> ritorna RETURN_OK_VALUE se l'operazione si � conclusa con successo, RETURN_ERROR_VALUE altrimenti
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Funzione LoadFromFile(fileName): legge da un file binario/testo il contenuto, lo trasforma in Base64 e lo salva nella variabile membro fileContent
	//	INPUT
	//		<fileName:String> percorso completo da cui leggere il file binario/testo
	//	OUTPUT
	//		<LoadFromFile:int> ritorna RETURN_OK_VALUE se l'operazione si � conclusa con successo, RETURN_ERROR_VALUE altrimenti
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Funzione SaveToFileWithAttributes(fileName,fileMode,fileAttributes): salva su un file identificato da filename il contenuto della variabile membro fileContent
	//impostando gli attributi del file specificati in fileAttributes
	//	INPUT
	//		<fileName:String> percorso completo in cui salvare il file
	//		<fileMode:String> modalit� di creazione del file, a scelta tra FILE_CREATE_NEW e FILE_CREATE_ALWAYS;
	//		<fileAttributes:String> attributi da assegnare al file che verr� creato, a scelta tra: FILE_ATTRIBUTES_ARCHIVE,
	//								FILE_ATTRIBUTES_READONLY, FILE_ATTRIBUTES_HIDDEN, FILE_ATTRIBUTES_READONLY_HIDDEN.
	//	OUTPUT
	//		<SaveToFileWithAttributes:int> ritorna RETURN_OK_VALUE se l'operazione si � conclusa con successo, RETURN_ERROR_VALUE altrimenti
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Funzione GetExclusiveAccess(fileName): verifica se un file � gi� aperto da un'altra applicazione oppure se � possibile guadagnare l'accesso esclusivo
	//	INPUT
	//		 <fileName:string> path completo del file da controllare
	//  OUTPUT
	//		<GetExclusiveAccess:string> ritorna la stringa EXCLUSIVE_ACCESS_TRUE se � stato possibile guadagnare
	//						    l'accesso esclusivo al file altrimenti ritorna EXCLUSIVE_ACCESS_FALSE. Se si verifica
	//							un errore ritorna RETURN_ERROR_VALUE e viene valorizzata la variabile d'errore fsErr.
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Funzione PollingExclusiveAccess(fileName): rimane in polling sul file specificato fino a quando non riesce a guadagnare l'accesso esclusivo ad esso
	//	INPUT
	//		 <fileName:string> path completo del file da controllare
	//  OUTPUT
	//		<pollingExclusiveAccess:string> il metodo � bloccante e ritorna true se termina ottenendo l'accesso esclusivo
	//							al file, mentre ritorna false e viene valorizzata la variabile d'errore fsErr se termina a 
	//							causa di un errore.
	'++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/	
//.......................................................................................................................................................................................................

//*******************************************************************************************************************************************************************************************************
//										COSTANTI
//*******************************************************************************************************************************************************************************************************
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Costanti CAPICOM
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var CAPICOM_E_CANCELLED = -2138568446;
var CAPICOM_VERIFY_SIGNATURE_ONLY = 0;
var CAPICOM_VERIFY_SIGNATURE_AND_CERTIFICATE = 1;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Costanti che specificano il contenuto da firmare
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var CONTENT_TYPE_FILE = 1;//Firmo un file
var CONTENT_TYPE_STRING = 0;//Firmo una stringa
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Costanti che specificano la modalita' di firma: attached o detached
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var SIGN_MODE_ATTACHED = 0;//Firma in modalita' attached
var SIGN_MODE_DETACHED = 1;//Firma in modalita' detached
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Costanti che specificano il valore di ritorno per il metodo che verifica la possibilit� di accesso esclusivo ad un file
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var EXCLUSIVE_ACCESS_TRUE="1";//accesso esclusivo al file riuscito
var EXCLUSIVE_ACCESS_FALSE="0";//accesso esclusivo al file non riuscito
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Altre costanti
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Valore di ritorno per le funzioni in caso di errore
var RETURN_ERROR_VALUE = -1;
var RETURN_OK_VALUE=0;
var DOC_TYPE_NULL=1;
var DOC_TYPE_BASE64=2;
var DOC_TYPE_BINARY=3;

//Elemento separatore delle informazioni del certificato di firma digitale
var INFO_SEPARATOR= '\n';
//Identificativo dell'oggetto CedafFSM utilizzato nel tag object del dom del documento
var SIGN_OBJECT_ID="CedafFSM";
//Identificativo dell'oggetto CAPICOM utilizzato nel tag object del dom del documento
var CAPICOM_OBJECT_ID="oCAPICOM";
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Costanti per l'emulazione di ADOStream
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var FILE_CREATE_NEW=1;
var FILE_CREATE_ALWAYS=2;
var FILE_ATTRIBUTES_ARCHIVE=0;
var FILE_ATTRIBUTES_READONLY=1;
var FILE_ATTRIBUTES_HIDDEN=2;
var FILE_ATTRIBUTES_READONLY_HIDDEN=3;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Costanti di errore predefinite
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var ERROR_SIGN_OPERATION_ABORTED="Operazione annullata";
var ERROR_NONE_FILTERED_CERTIFICATE="Nessun certificato presente nello store personale soddisfa i criteri impostati nel filtro di selezione";
var ERROR_NONE_CERTIFICATE="Impossibile firmare.Nessun certificato installato nello store.";
var ERROR_UNAVAILABLE_CERTIFICATE_DISABLED_REMOTE_SIGN="Nessun certificato digitale presente nello store e firma remota disabilitata: impossibile procedere.";
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//Filtri predefiniti sui certificati digitali
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
var FILTER_DIGITAL_SIGNATURE_ENABLED=4096;
var FILTER_DIGITAL_SIGNATURE_DISABLED=8192;
var FILTER_NON_REPUDIATION_DISABLED=8;
var FILTER_NON_REPUDIATION_ENABLED=4;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//*******************************************************************************************************************************************************************************************************
//										CLASSI
//*******************************************************************************************************************************************************************************************************

//*******************************************************************************************************************************************************************************************************
//										CLASSE CertificateInfo
//*******************************************************************************************************************************************************************************************************

//#######################################################################################################################################################################################################
/* Emulazione di una classe di nome CertificateInfo con le seguenti proprieta':
	AuthorityName: 		Nome dell'autorita' di certificazione emittente il certificato
	AuthorityEmail: 	EMail dell'autorita' di certificazione emittente il certificato
	ValidFrom:			Data di partenza della validita' del certificato
	ValidTo:			Data di fine della validita' del certificato
	Subject:			Intestatario del certificato
	SerialNumber:		Numero seriale del certificato
	PublicKey:			Chiave pubblica del certificato
*/
//#######################################################################################################################################################################################################
function CertificateInfo(AuthorityEmail,AuthorityName,PublicKey,SerialNumber,Subject,ValidFrom,ValidTo)
{
	//Dichiarazione dei membri della classe
	var AuthorityEmail=AuthorityEmail;
	var AuthorityName=AuthorityName;
	var PublicKey=PublicKey;
	var SerialNumber=SerialNumber;
	var Subject=Subject;
	var ValidFrom=ValidFrom;
	var ValidTo=ValidTo;
	//Istanzio i valori passati dal costruttore
	this.AuthorityEmail=AuthorityEmail;
	this.AuthorityName=AuthorityName;
	this.PublicKey=PublicKey;
	this.SerialNumber=SerialNumber;
	this.Subject=Subject;
	this.ValidFrom=ValidFrom;
	this.ValidTo=ValidTo;
	
	function Destroy()
	{
		this.AuthorityEmail=null;
		this.AuthorityName=null;
		this.PublicKey=null;
		this.SerialNumber=null;
		this.Subject=null;
		this.ValidFrom=null;
		this.ValidTo=null;
	}
}

/* Questa funzione ha il compito di inserire nel DOM i tag object necessari per CAPICOM e CedafFSM, in modo da rendere
   del tutto trasparente alla pagina chiamante quali tag object siano necessari permettendo una eventuale pi� facile
   sostituzione del componente di firma utilizzato
   		objectId:	identificativo del tag object utilizzato per definire l'oggetto CedafFSM
*/
function WebSignInitialize(urlBase)
{
	document.write("<object type='application/x-oleobject' id='" + CAPICOM_OBJECT_ID + "' classid='clsid:A996E48C-D3DC-4244-89F7-AFA33EC60679' codebase='"+urlBase+"capicom.cab#version=2,1,0,1' VIEWASTEXT></object>");
	document.write("<object type='application/x-oleobject' id='" + SIGN_OBJECT_ID + "' classid='CLSID:F0A8EC0B-68F2-4819-B2CC-40AEBF2D6296' codebase='"+urlBase+"CedafFSM.cab#version=2,1,0,4' VIEWASTEXT></object>");
}

function FileSystemInitialize(urlBase)
{
	document.write("<object type='application/x-oleobject' id='" + CAPICOM_OBJECT_ID + "' classid='clsid:A996E48C-D3DC-4244-89F7-AFA33EC60679' codebase='"+urlBase+"capicom.cab#version=2,1,0,1' VIEWASTEXT></object>");
	document.write("<object type='application/x-oleobject' id='" + SIGN_OBJECT_ID + "' classid='CLSID:F0A8EC0B-68F2-4819-B2CC-40AEBF2D6296' codebase='"+urlBase+"CedafFSM.cab#version=2,1,0,4' VIEWASTEXT></object>");
}


//*******************************************************************************************************************************************************************************************************
//										CLASSE WebSign
//*******************************************************************************************************************************************************************************************************

//#######################################################################################################################################################################################################
//Incapsulo le funzioni e gli attributi della firma digitale in un unico oggetto
//#######################################################################################################################################################################################################
function WebSign()
{
	//Dichiarazione dei membri della classe	
	//Variabile contenente l'eventuale messaggio di Errore
	var SignErr;
	//Variabile contenente il contenuto firmato
	var ContentSigned;
	//Variabile contenente le informazioni generali del certificato di firma
	var Certificate;
	//Variant contenente il documento originale in formato binario (tipo variant)
	var OriginalDocument;
	//Identificativo dell'oggetto cheistanzia il controllo ActiveX della pagina html chiamante
	var objectId;
	
	this.objectId=SIGN_OBJECT_ID;
	
	
	function UnloadDll(dllList)
	{
		var DSign;
		DSign=document.getElementById(this.objectId);
		DSign=document.getElementById(this.objectId);
		var stato;
		var arr_dll = dllList.split(",");
		for (var k=0; k<arr_dll.length; k++) {
			stato = DSign.UnloadDll(arr_dll[k]);
			if (stato != "OK") return "DLL: " + arr_dll[k] + " " + stato;
		}
		return true;
	}	

	//Dichiarazione dei metodi della classe
	//#######################################################################################################################################################################################################
	//funzione che realizza la firma digitale:
	//	INPUT
	//		<Content:string> rappresenta o il contenuto da firmare (SignMode=CONTENT_TYPE_STRING) oppure il percorso completo
	//						 del filesystem a cui recuperare il file da firmare (SignMode=CONTENT_TYPE_FILE)
	//		<CType:int>	 rappresenta il tipo di contenuto da firmare:
	//						 CType=CONTENT_TYPE_STRING firmo una stringa
	//						 CType=CONTENT_TYPE_FILE firmo il file specificato in <Content>
	//		<SMode:int>	 specifica la modalita' di firma:
	//						 SMode=SIGN_MODE_ATTACHED firmo in modalita' attached (il base64
	//						                             che rappresenta la firma contiene anche
	//													 il doumento originale).
	//						 SMode=SIGN_MODE_DETACHED firmo in modalita' detached (il base64
	//													 che rappresenta la firma non contiene
	//													 il documento originale
	//	OUTPUT
	//		<ContentSigned:string> e' la stringa base64 encoded che rappresenta la firma di <Content>
	//							   secondo le modalita' specificate
	//#######################################################################################################################################################################################################
	function Sign(Content,CType,SMode)
	{
		var DSign;
		
		//Inizializzo le variabili della classe
		this.SignErr="";
		this.ContentSigned="";
		if(this.Certificate!=null)
			this.Certificate.Destroy();
		this.Certificate=null;

		try
		{
			//*******************************************************************************************************************************************************************************
			//Inizializzazione oggetto CedafDSign.DigiSign
			//*******************************************************************************************************************************************************************************
			//DSign=new ActiveXObject("CedafDSign.DigiSign");
			DSign=document.getElementById(this.objectId);
			
		   	//Effettuo l'operazione di firma
		   	try
		   	{
		   		if(DSign.Sign(Content,CType,SMode)!=RETURN_ERROR_VALUE)
		   		{
		   			//Firma del <Content> avvenuta con successo
		   			this.ContentSigned=DSign.ContentSigned;
		   		}
		   		else
		   		{
		   			//La firma del <Content> ha causato un errore
		   			this.SignErr=DSign.RetLastError();
		   			return RETURN_ERROR_VALUE;
		   		}
			}
			catch (exc)
			{
				this.SignErr="Errore avvenuto in fase di firma. Si prega di ripetere l'operazione.\n"+exc.description;
				return RETURN_ERROR_VALUE;
			}
			//Istanzio un oggetto di tipo CertificateInfo che conterra' alcune proprieta' fondamentali
			//del certificato di firma utilizzato dall'utente
			var CertInfo=DSign.CertInfo.split(INFO_SEPARATOR);
			this.Certificate=new CertificateInfo(CertInfo[0],CertInfo[1],CertInfo[2],CertInfo[3],CertInfo[4],CertInfo[5],CertInfo[6]);
			//Libero le risorse allocate dagli oggetti com
			DSign=null;
			return RETURN_OK_VALUE;
		}
		catch (e)
		{
			if (e.number != CAPICOM_E_CANCELLED)
			{
				this.SignErr="Si e' verificato il seguente errore:\n" + e.description;
				return RETURN_ERROR_VALUE;
			}
		}
	}
	
	
	//#######################################################################################################################################################################################################
	//Funzione di verifica della firma digitale e di estrazione di un documento da un PKCS#7:
	//	INPUT
	//		 <SrcFName:string> path completo del file contenente il documento PKCS#7 o la firma digitale detached
	//		 <DstFName:string> path completo del file originale nel caso di firma detached,ignorato nel caso di firma attached
	//  OUTPUT
	//		<VerifyExtract:int> ritorna RETURN_OK_VALUE se la funzione termina correttamente
	//						    altrimenti ritorna RETURN_ERROR_VALUE
	//#######################################################################################################################################################################################################
	function VerifyExtract(SrcFName,DstFName,SignMode,VerifyMode,DocType)
	{
		this.SignErr="";
		this.ContentSigned="";
		var DSign;
		try
		{
			//DSign=new ActiveXObject("CedafDSign.DigiSign");
			DSign=document.getElementById(this.objectId);
			if(DSign.VerifyExtract(SrcFName,DstFName,SignMode,VerifyMode,DocType)==RETURN_ERROR_VALUE)
			{
				this.SignErr=DSign.RetLastError();
				return RETURN_ERROR_VALUE;
			}
			//valorizzo la proprieta' OriginalDocument della classe
			if(DocType==DOC_TYPE_BINARY)
				//In questo caso il documento binario e' salvato nella propriet� OriginalDocument
				//dell'oggetto DSign
				this.OriginalDocument=DSign.OriginalDocument;
			else if(DocType==DOC_TYPE_BASE64)
				//In questo caso il documento originale base64 e' salvato nella propriet� ContentSigned
				//dell'oggetto DSign
				this.OriginalDocument=DSign.ContentSigned;
			//Istanzio un oggetto di tipo CertificateInfo che conterra' alcune proprieta' fondamentali
			//del certificato di firma utilizzato dall'utente
			var CertInfo=DSign.CertInfo.split(INFO_SEPARATOR);
			this.Certificate=new CertificateInfo(CertInfo[0],CertInfo[1],CertInfo[2],CertInfo[3],CertInfo[4],CertInfo[5],CertInfo[6]);			
			//Libero le risorse allocate dagli oggetti com
			DSign=null;
			return RETURN_OK_VALUE;
		}
		catch(exc)
		{
			alert("Si e' verificato il seguente errore:\n"+exc.description);
			return RETURN_ERROR_VALUE;
		}
	}
	
	
	//#######################################################################################################################################################################################################
	//Funzione per la valorizzazione di una variabile javascript con il contenuto di un file binario:
	//	INPUT
	//		 <SrcFName:string> path completo del file binario da caricare	
	//  OUTPUT
	//		<getBinaryContent:binarystring> ritorna il contenuto del file binario in una BSTR se la funzione termina correttamente
	//						    altrimenti ritorna null e viene valorizzata la variabile di errore SignErr con l'errore occorso
	//#######################################################################################################################################################################################################
	function getBinaryContent(SrcFName)
	{
		if(document.getElementById(this.objectId).getBinFromPath(SrcFName)==RETURN_ERROR_VALUE)
		{
			this.SignErr=document.getElementById(this.objectId).RetLastError();
			return null;
		}
		else
			return document.getElementById(this.objectId).fileContent;
	}
	
	//#######################################################################################################################################################################################################
	//Funzione per la valorizzazione di una variabile javascript con il contenuto di un file in formato unicode:
	//	INPUT
	//		 <SrcFName:string> path completo del file unicode da caricare	
	//  OUTPUT
	//		<getUnicodeContent:string> ritorna il contenuto del file unicode in una stringa se la funzione termina correttamente
	//						    altrimenti ritorna null e viene valorizzata la variabile di errore SignErr con l'errore occorso
	//#######################################################################################################################################################################################################
	function getUnicodeContent(SrcFName)
	{
		if(document.getElementById(this.objectId).getBase64FromPath(SrcFName)==RETURN_ERROR_VALUE)
		{
			this.SignErr=document.getElementById(this.objectId).RetLastError();
			return null;
		}
		else
			return document.getElementById(this.objectId).fileContent;
	}
	
	
	//#######################################################################################################################################################################################################
	//Funzione che converte un file binario in una stringa Base64
	//	INPUT
	//		 <SrcFName:string> path completo del file binario da convertire
	//  OUTPUT
	//		<Bin2Base64:string> ritorna la stringa BASE64 equivalente al file binario se la funzione termina correttamente
	//						    altrimenti ritorna null e viene valorizzata la variabile di errore SignErr con l'errore occorso
	//#######################################################################################################################################################################################################
	function Bin2Base64(SrcFName)
	{
		if(document.getElementById(this.objectId).bin2base64(SrcFName)==RETURN_ERROR_VALUE)
		{
			this.SignErr=document.getElementById(this.objectId).RetLastError();
			return null;
		}
		else
			return document.getElementById(this.objectId).fileContent;		
	}
	//#######################################################################################################################################################################################################
	//Funzione che verifica se un file � gi� aperto da un'altra applicazione oppure se � possibile guadagnare l'accesso esclusivo
	//	INPUT
	//		 <SrcFName:string> path completo del file da controllare
	//  OUTPUT
	//		<getExclusiveAccess:string> ritorna la stringa EXCLUSIVE_ACCESS_TRUE se � stato possibile guadagnare
	//						    l'accesso esclusivo al file altrimenti ritorna EXCLUSIVE_ACCESS_FALSE. Se si verifica
	//							un errore ritorna RETURN_ERROR_VALUE e viene valorizzata la variabile d'errore SignErr.
	//#######################################################################################################################################################################################################
	function getExclusiveAccess(fileName)
	{
		if(document.getElementById(this.objectId).getExcAccess(fileName)==RETURN_ERROR_VALUE)
		{
			this.SignErr=document.getElementById(this.objectId).RetLastError();
			return RETURN_ERROR_VALUE;
		}
		else
			return document.getElementById(this.objectId).exclusiveAccess;
	}
	//#######################################################################################################################################################################################################
	//Funzione che rimane in polling sul file specificato fino a quando non riesce a guadagnare l'accesso esclusivo ad esso
	//	INPUT
	//		 <SrcFName:string> path completo del file da controllare
	//  OUTPUT
	//		<pollingExclusiveAccess:string> il metodo � bloccante e ritorna true se termina ottenendo l'accesso esclusivo
	//							al file, mentre ritorna false e viene valorizzata la variabile d'errore SignErr se termina a 
	//							causa di un errore.
	//#######################################################################################################################################################################################################
	function pollingExclusiveAccess(fileName)
	{
		if(document.getElementById(this.objectId).pollingExcAccess(fileName)==RETURN_ERROR_VALUE)
		{
			this.SignErr=document.getElementById(this.objectId).RetLastError();
			return false;
		}
		else
			return true;
	}
	
	//#######################################################################################################################################################################################################
	//Funzione che permette di impostare il filtro di visualizzazione per i certificati digitali presenti nello store dell'utente; agendo
	//su questo metodo � possibile visualizzare sia i certificati con il flag di non ripudio, sia quelli con il flag digital signature (CRS)
	//	INPUT
	//		<filter:int> un intero che indica un particolare filtro espresso in bit da eseguire sull'elenco dei certificati nello store.
	//			Esistono delle costanti predefinite che gi� implementano alcuni filtri predefiniti (vedi sezione iniziale del file)
	// 			Per calcolare il filtro tenere presente che l'array di bit da valorizzare ha il seguente formato:
	//			----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	//			| IsCritical | IsCRLSign | IsDataEncipher | IsDecipher  | IsDigital        | IsEncipher  | IsKeyAgreement | IsKeyCert   | IsKeyEncipherment | IsNonRepudiation | isPresent |
   	//			|			 | Enabled   | mentEnabled    | OnlyEnabled | SignatureEnabled | OnlyEnabled | Enabled        | SignEnabled | Enabled           | Enabled		   |           |
   	//			----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//			Per ogni flag si hanno a disposizione 2 bit per poter specificare uno dei 3 possibili stati di interesse nel filtro:
	//			0: il flag viene ignorato nel computo del filtro
	//			1: il flag deve obbligatoriamente essere abilitato nel certificato affinch� il certificato venga considerato nel computo del filtro
	//			2: il flag deve obbligatoriamente NON essere abilitato nel certificato affinch� il certificato venga considerato nel computo del filtro
	//			Cos�, per esempio, se voglio filtrare lo store personale dei certificati utente, per visualizzare soltanto i certificati 
	//			che abbiano abilitato nel KeyUsage il flag "Digital Signature" ma non il flag "Non Repudiation" devo calcolare il numero 
	//			in base 10 corrispondente alla seguente stringa binaria:
	//			-----------------------------------------------------------------------------------------
	//			| 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 |
	//			-----------------------------------------------------------------------------------------
	//			quindi il filtro sar� l'intero 2^12 + 2^3 = 4104
	//	OUTPUT
	//		<SetCertificateFilter:int> ritorna RETURN_OK_VALUE se la funzione termina correttamente
	//			altrimenti ritorna RETURN_ERROR_VALUE
	//#######################################################################################################################################################################################################
	function SetCertificateFilter(filter)
	{
		var DSign=document.getElementById(this.objectId);
		if(DSign.SetCertificateFilter(filter)==RETURN_ERROR_VALUE)
		{
			this.SignErr=DSign.RetLastError();
			return RETURN_ERROR_VALUE;
		}
		else
			return RETURN_OK_VALUE;
	}
	
	//#######################################################################################################################################################################################################
	/*funzione che restituisce una stringa di errore:	
		OUTPUT
			<LastError:string>  Restituisce l'ultimo errore verificatosi in seguito alla chiamata
							    di una funzione di questa libreria che abbia restituito valore RETURN_ERROR_VALUE
	*/
	//#######################################################################################################################################################################################################
	this.LastError=function(){return this.SignErr;}
	this.Sign=Sign;
	this.VerifyExtract=VerifyExtract;
	this.getBinaryContent=getBinaryContent;
	this.getUnicodeContent=getUnicodeContent;
	this.Bin2Base64=Bin2Base64;
	this.getExclusiveAccess=getExclusiveAccess;
	this.pollingExclusiveAccess=pollingExclusiveAccess;
	this.UnloadDll=UnloadDll;
	this.SetCertificateFilter=SetCertificateFilter;
}


//*******************************************************************************************************************************************************************************************************
//										CLASSE FileSystemManager per l'emulazione di AdoStream
//*******************************************************************************************************************************************************************************************************
function FileSystemManager()
{
	//dati da scrivere/leggere nel/da file
	var fileContent;
	//Variabile contenente l'eventuale messaggio di Errore
	var fsErr;
	
	//#######################################################################################################################################################################################################
	//Funzione che assegna alla variabile membro fileContent, il contenuto binario da salvare su file
	//con il metodo SaveToFile
	//	INPUT
	//		 <fileData:binaryString> dati da salvare nel file su filesystem
	//#######################################################################################################################################################################################################
	function Write(fileData)
	{
		this.fileContent=fileData;
	}
	//#######################################################################################################################################################################################################
	//Funzione che restituisce la variabile membro fileContent, che contiene il contenuto binario del file
	//letto con il metodo LoadFromFile
	//	OUTPUT
	//		 <fRead:binaryString> dati binari contenuti nel file letto con il metodo LoadFromFile
	//#######################################################################################################################################################################################################	
	function Read()
	{
		return this.fileContent;
	}
	

	//#######################################################################################################################################################################################################
	//Funzione che salva su un file identificato da filename il contenuto della variabile membro fileContent
	//	INPUT
	//		<fileName:String> percorso completo in cui salvare il file
	//		<fileMode:String> modalit� di creazione del file, a scelta tra FILE_CREATE_NEW e FILE_CREATE_ALWAYS;
	//	OUTPUT
	//		<SaveToFile:int> ritorna RETURN_OK_VALUE se l'operazione si � conclusa con successo, RETURN_ERROR_VALUE altrimenti
	//#######################################################################################################################################################################################################
	function SaveToFile(fileName,fileMode)
	{
		//pulisco la variabile che contiene l'errore
		this.fsErr="";
		//istanzio un oggetto di tipo CedafFSM
		var fsMgr=document.getElementById(SIGN_OBJECT_ID);
		//salvo il file specificato su filesystem
		if(fsMgr.SaveToFile(fileName,fileMode,FILE_ATTRIBUTES_ARCHIVE,this.fileContent)==RETURN_ERROR_VALUE)
		{
			this.fsErr=fsMgr.RetLastError();
			return RETURN_ERROR_VALUE;
		}
		else
			return RETURN_OK_VALUE;
	}
	
	//#######################################################################################################################################################################################################
	//Funzione che salva su un file identificato da filename il contenuto della variabile membro fileContent
	//impostando gli attributi del file specificati in fileAttributes
	//	INPUT
	//		<fileName:String> percorso completo in cui salvare il file
	//		<fileMode:String> modalit� di creazione del file, a scelta tra FILE_CREATE_NEW e FILE_CREATE_ALWAYS;
	//		<fileAttributes:String> attributi da assegnare al file che verr� creato, a scelta tra: FILE_ATTRIBUTES_ARCHIVE,
	//								FILE_ATTRIBUTES_READONLY, FILE_ATTRIBUTES_HIDDEN, FILE_ATTRIBUTES_READONLY_HIDDEN.
	//	OUTPUT
	//		<SaveToFileWithAttributes:int> ritorna RETURN_OK_VALUE se l'operazione si � conclusa con successo, RETURN_ERROR_VALUE altrimenti
	//#######################################################################################################################################################################################################
	function SaveToFileWithAttributes(fileName,fileMode,fileAttributes)
	{
		//pulisco la variabile che contiene l'errore
		this.fsErr="";
		//istanzio un oggetto di tipo CedafFSM
		var fsMgr=document.getElementById(SIGN_OBJECT_ID);
		
		
		if(fsMgr.SaveToFile(fileName,fileMode,fileAttributes,this.fileContent)==RETURN_ERROR_VALUE)
		{
			this.fsErr=fsMgr.RetLastError();
			return RETURN_ERROR_VALUE;
		}
		else
			return RETURN_OK_VALUE;
		
	}
	
	//#######################################################################################################################################################################################################
	//Funzione che legge da un file binario/testo il contenuto, lo trasforma in Base64 e lo salva nella variabile membro fileContent
	//	INPUT
	//		<fileName:String> percorso completo da cui leggere il file binario
	//	OUTPUT
	//		<LoadFromFile:int> ritorna RETURN_OK_VALUE se l'operazione si � conclusa con successo, RETURN_ERROR_VALUE altrimenti
	//#######################################################################################################################################################################################################
	function LoadFromFile(fileName)
	{
		//pulisco la variabile d'errore
		this.fsErr="";
		//istanzio un oggetto di tipo CedafFSM
		var fsMgr=document.getElementById(SIGN_OBJECT_ID);
		//valore di ritorno dei metodi di lettura dall'ActiveX
		var retValue;

		if(fsMgr.bin2base64(fileName)==RETURN_ERROR_VALUE)
		{
			this.fsErr=fsMgr.RetLastError();
			return RETURN_ERROR_VALUE;
		}
		else
		{
			this.fileContent=fsMgr.fileContent;
			return RETURN_OK_VALUE;
		}
	}
	
	//#######################################################################################################################################################################################################
	//Funzione che verifica se un file � gi� aperto da un'altra applicazione oppure se � possibile guadagnare l'accesso esclusivo
	//	INPUT
	//		 <fileName:string> path completo del file da controllare
	//  OUTPUT
	//		<GetExclusiveAccess:string> ritorna la stringa EXCLUSIVE_ACCESS_TRUE se � stato possibile guadagnare
	//						    l'accesso esclusivo al file altrimenti ritorna EXCLUSIVE_ACCESS_FALSE. Se si verifica
	//							un errore ritorna RETURN_ERROR_VALUE e viene valorizzata la variabile d'errore fsErr.
	//#######################################################################################################################################################################################################
	function GetExclusiveAccess(fileName)
	{
		if(document.getElementById(SIGN_OBJECT_ID).getExcAccess(fileName)==RETURN_ERROR_VALUE)
		{
			this.fsErr=document.getElementById(SIGN_OBJECT_ID).RetLastError();
			return RETURN_ERROR_VALUE;
		}
		else
			return document.getElementById(SIGN_OBJECT_ID).exclusiveAccess;
	}
	
	//#######################################################################################################################################################################################################
	//Funzione che rimane in polling sul file specificato fino a quando non riesce a guadagnare l'accesso esclusivo ad esso
	//	INPUT
	//		 <fileName:string> path completo del file da controllare
	//  OUTPUT
	//		<pollingExclusiveAccess:string> il metodo � bloccante e ritorna true se termina ottenendo l'accesso esclusivo
	//							al file, mentre ritorna false e viene valorizzata la variabile d'errore fsErr se termina a 
	//							causa di un errore.
	//#######################################################################################################################################################################################################
	function PollingExclusiveAccess(fileName)
	{
		if(document.getElementById(SIGN_OBJECT_ID).pollingExcAccess(fileName)==RETURN_ERROR_VALUE)
		{
			this.fsErr=document.getElementById(SIGN_OBJECT_ID).RetLastError();
			return false;
		}
		else
			return true;
	}
	
	
	//Assegno i metodi all'oggetto
	this.Write=Write;
	this.Read=Read;
	this.SaveToFile=SaveToFile;
	this.LoadFromFile=LoadFromFile;
	this.GetExclusiveAccess=GetExclusiveAccess;
	this.PollingExclusiveAccess=PollingExclusiveAccess;
	this.LastError=function(){return this.fsErr;}

}
