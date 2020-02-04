// it_time.js
//
// ver. 1.1  -  2009-11-04

var timeServer = "servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/time.jsp";	// URL del time server
// var timeServer = "time.jsp";	// URL del time server
var nRichiesteInviate = 0;
var nRichiesteRicevute = 0;
var startTime;					// Ora di avvio dello script 
var ultimaSinc = 0;				// Ora dell'ultime sicronizzazione
// var richiestaInviata = false;
var ritardo = new Array();		// ritardo di propagazione tra client e server
var ritardoMedio;				// valore medio del ritardo di propagazione tra client e server
var nValRitardo = 0;			// numero campioni di ritardo di propagazione
var incertezza;
var deriva =0;
var clientTzOffset;			// Timezone offset del client
var serverTzOffset;			// Timezone offset del server
var tzOffset;				// Differenza di Timezone offset tra client e server 
var txTime;					// Ora del client di invio della richiesta
var rxTime;					// Ora del client di ricezione della richiesta
var serverTime;				// Ora del server
var timeOffset;				// Scarto di tempo tra l'ora del client e del server
var dstIsOn;				// Vale uno se e' in vigore l'ora legale, altrimenti vale 0 
var timeCorrection;			// Correzione in ms da applicare all'ora del client 
var jitter = new Array();	// valori del Jitter tra due correzioni successive
var nValJitter = 0;			// numero campioni di Jitter
var jitterMedio = 0;		// valore medio del Jitter
var stabilita=0;			// stabilita della frequenza di aggiornamento della pagina
var nValStabilita = 0;
var precAggiornamento;		// ora del precedente aggiornamento della pagina
var nSecondiPA;				// numero di secondi del precedente aggioranmento dell'orologio

var httpRequest;	
// Crea l'oggetto httpRrequest per connetersi col server
// se non ci riesce e' inutile proseguire lo script
if (window.XMLHttpRequest) { // Mozilla, Safari, ...
    var httpRequest = new XMLHttpRequest();
    if (httpRequest.overrideMimeType) {
    	httpRequest.overrideMimeType('text/xml');
    }
} else if (window.ActiveXObject) { // IE
	try {
    	var httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
	    try {
		    var httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        } 
        catch (e) {}
    }
    if (!httpRequest) {
    	alert('Giving up :( Cannot create an XMLHTTP instance');
    }
}
/*
if (navigator.javaEnabled()) {
	var souniAttivi = true;
} else {
	var souniAttivi = false;
}
*/
var souniAttivi = false;
GDS = ["Domenica", "Luned&igrave;", "Marted&igrave;", "Mercoled&igrave;", 
	"Gioved&igrave;", "Venerd&igrave;", "Sabato"];

MESE =["gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno",
	"luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"];


function timer() {
	// programma tutte le funzioni di aggiornamento della pagina
	// il tempo di aggiornamento in millisecondi
	//if (richiestaInviata === false) {
	window.setTimeout("inviaRichiesta()", 100); 
	//}
	window.setTimeout("aggiornaOra()", 100); 
}


function aggiornaOra() {
	var adesso = new Date().getTime();
	if (precAggiornamento === undefined) {
		precAggiornamento = adesso;
	} else {
		if (Math.abs(adesso - precAggiornamento) > 1000) { 
			// possibile cambio di ora sul client, resetta tutte le correzioni
			timeOffset = timeCorrection = startTime = correzionePrec = undefined;
			ultimaSinc = 0;
			ritardo = jitter = [];
			precAggiornamento = adesso;
			timer();
			return;
		}	
		if ((nRichiesteInviate - nRichiesteRicevute) > 5) { 
			// il server non risponde, resetta tutte le correzioni
			elemId = document.getElementById("txtOraLocale");
	 		elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("oraLocale");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("txtOraUTC");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("oraUTC");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("dataLocale");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("dataUTC");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("inc");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("dataMJD");
		 	elemId.innerHTML = "&nbsp;";
		 	elemId = document.getElementById("siglaOraLocale");
	 		elemId.innerHTML = "&nbsp;";
	 		elemId = document.getElementById("descOraLocale");
	 		elemId.innerHTML = "&nbsp;";
	 		elemId = document.getElementById("siglaOraUTC");
	 		elemId.innerHTML = "&nbsp;";
	 		elemId = document.getElementById("descOraUTC");
	 		elemId.innerHTML = "&nbsp;";
			return;
		}
		if ((nRichiesteInviate - nRichiesteRicevute) > 2) { 
			// il server non risponde, resetta tutte le correzioni
			timeOffset = timeCorrection = startTime = correzionePrec = undefined;
			ritardo = jitter = [];
			nRichiesteInviate = nRichiesteRicevute = ultimaSinc = 0;
			precAggiornamento = adesso;
			timer();
			return;
		}	
		if (nValStabilita < 10) {
			nValStabilita ++;
		} else {
			stabilita -= stabilita / nValStabilita;
		}
		stabilita += (adesso - precAggiornamento - 100) / nValStabilita;
		precAggiornamento = adesso;
	}
	
	if (timeCorrection !== undefined) {
		
		// client DST detection
		stTime = (new Date(2008, 0)).getTimezoneOffset();
		dlTime = (new Date(2008, 6)).getTimezoneOffset();
		dlOffset = dlTime - stTime; 
		if (dlOffset !=0 ) {
			if (((new Date()).getTimezoneOffset() - stTime) == dlOffset) {
				var clientDST = 1;
			} else {
				var clientDST = 0;
			}
		} else {
			var clientDST = 0;
		}
		
		var clientTime = new Date().getTime();
		
		// Ora locale italiana 
		if (dstIsOn == clientDST) {
			var oraLocale = new Date(clientTime + timeCorrection); 
		} else {
			if (clientDST == 0){
				var oraLocale = new Date(clientTime + timeCorrection + dlOffset * 60000);
			} else {
				var oraLocale = new Date(clientTime + timeCorrection - dlOffset * 60000);
			}
		}
				
		// Ora UTC
		var oraUTC = new Date(clientTime + timeOffset);         
		
		if (nSecondiPA === undefined) { 
			nSecondiPA = oraLocale.getSeconds();
		} else {
			if (nSecondiPA == oraLocale.getSeconds()) {
				timer(); 
				return;
			}
		}
		/*
		// Emette un suono in corrispondenza del cambio di secondo
	 	if (souniAttivi === true) {
	 		try {
	 			elemId=document.getElementById("suono");
	 			elemId.Play();
	 		} catch (e) {
	 			souniAttivi = false;
	 			elemId = document.getElementById("cSound");
	 			elemId.value="Abilita i suoni";
	 		}
		}	
	 	*/
		// Aggiorna l'informazione sulla pagina
		var stringaDataLocale = GDS[oraLocale.getDay()] + "  " 
			+ addZero(oraLocale.getDate()) + " "
			+ MESE[oraLocale.getMonth()] + " "
			+ String(oraLocale.getFullYear());
		elemId = document.getElementById("dataLocale");
	 	elemId.innerHTML = stringaDataLocale;
		var stringaOraLocale = addZero(oraLocale.getHours()) + ":"
			+ addZero(oraLocale.getMinutes()) + ":"
			+ addZero(oraLocale.getSeconds());
		elemId = document.getElementById("oraLocale");
		var oraPrecedente = elemId.innerHTML;
	 	elemId.innerHTML = stringaOraLocale;
	 	
	 	var stringaDataUTC = GDS[oraUTC.getUTCDay()] + "  " 
			+ addZero(oraUTC.getUTCDate()) + " "
			+ MESE[oraUTC.getUTCMonth()] + " "
			+ String(oraUTC.getUTCFullYear());
		elemId = document.getElementById("dataUTC");
	 	elemId.innerHTML = stringaDataUTC;
	 	var stringaOraUTC = addZero(oraUTC.getUTCHours()) + ":"
			+ addZero(oraUTC.getUTCMinutes()) + ":"
			+ addZero(oraUTC.getUTCSeconds());
		elemId = document.getElementById("oraUTC");
	 	elemId.innerHTML = stringaOraUTC;
	 	
	 	// ricava la data MJD di oggi
		var MJD = 40587 + oraUTC.getTime() / 86400000;
        var stringaDataMJD = MJD.toPrecision(10);
	 	elemId = document.getElementById("dataMJD");
	 	elemId.innerHTML = stringaDataMJD;
	 	
	 	if (incertezza !== undefined) {
	 		elemId = document.getElementById("inc");
	 		elemId.innerHTML = incertezza.toPrecision(2) + " (secondi) *";
	 	} else {
	 		elemId = document.getElementById("inc");
	 		elemId.innerHTML = "&nbsp;";
	 	}
	 	
	 	nSecondiPA = oraLocale.getSeconds();
	 	
	 	if (dstIsOn == 1) {
	 		elemId = document.getElementById("txtOraLocale");
	 		elemId.innerHTML = "Ora estiva:";
	 		elemId = document.getElementById("txtOraUTC");
	 		elemId.innerHTML = "UTC:";
	 		elemId = document.getElementById("siglaOraLocale");
	 		elemId.innerHTML = "Ora estiva =";
	 		elemId = document.getElementById("descOraLocale");
	 		elemId.innerHTML = "TMEC + 1 h (UTC + 2h)";
	 		elemId = document.getElementById("siglaOraUTC");
	 		elemId.innerHTML = "UTC =";
	 		elemId = document.getElementById("descOraUTC");
	 		elemId.innerHTML = "Tempo Universale Coordinato";
	 	} else {
	 		elemId = document.getElementById("txtOraLocale");
	 		elemId.innerHTML = "TMEC:";
	 		elemId = document.getElementById("txtOraUTC");
	 		elemId.innerHTML = "UTC:";
	 		elemId = document.getElementById("siglaOraLocale");
	 		elemId.innerHTML = "TMEC =";
	 		elemId = document.getElementById("descOraLocale");
	 		elemId.innerHTML = "Tempo Medio Europa Centrale (UTC + 1h)";
	 		elemId = document.getElementById("siglaOraUTC");
	 		elemId.innerHTML = "UTC =";
	 		elemId = document.getElementById("descOraUTC");
	 		elemId.innerHTML = "Tempo Universale Coordinato";
	 	}
	} else {
		elemId = document.getElementById("txtOraLocale");
 		elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("oraLocale");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("txtOraUTC");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("oraUTC");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("dataLocale");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("dataUTC");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("inc");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("dataMJD");
	 	elemId.innerHTML = "&nbsp;";
	 	elemId = document.getElementById("siglaOraLocale");
 		elemId.innerHTML = "&nbsp;";
 		elemId = document.getElementById("descOraLocale");
 		elemId.innerHTML = "&nbsp;";
 		elemId = document.getElementById("siglaOraUTC");
 		elemId.innerHTML = "&nbsp;";
 		elemId = document.getElementById("descOraUTC");
 		elemId.innerHTML = "&nbsp;";
	} 
	
	// rilancia il timer
 	timer(); 
}


function inviaRichiesta() {
	var adesso = new Date().getTime();
	
	// controlla che sia stata definita l'ora di avvio dello script
	if (startTime === undefined) {
		startTime = adesso;
	}
	
	// tempo trascorso dall'avvio (in secondi)
	var differenza = (adesso - startTime)/1000; 	
	
	// Frequenza delle sincronizzazioni
	if (differenza < 5) {
		// ogni 1/2 secondo nei primi 5 secondi
		var prossimaSinc = ultimaSinc + 500;
		deriva = 5;
	} else if (differenza < 10) {
		// ogni secondo nei primi 10 secondi
		var prossimaSinc = ultimaSinc + 1000;
		elemId = document.getElementById("fSinc");
	 	elemId.innerHTML = "secondo.";
	 	deriva = 10;
	} else if (differenza < 300) {
		// ogni 10 secondi nei primi 5 minuti
		var prossimaSinc = ultimaSinc + 10000;
		elemId = document.getElementById("fSinc");
	 	elemId.innerHTML = "10 secondi.";
	 	deriva = 100;
	} else {
		// ogni minuto dopo i primi 5 minuti
		var prossimaSinc = ultimaSinc + 60000;
		elemId = document.getElementById("fSinc");
	 	elemId.innerHTML = "minuto.";
	 	deriva = 600;
	}
	
	// Se e' trascorso il tempo minimo, invia la richiesta di sincronizzazione
	if (adesso >= prossimaSinc) {
		//richiestaInviata = true;
		ultimaSinc = new Date().getTime();
		
		// Invia una richiesta al time server
		txTime = new Date().getTime();
		stringa = timeServer + "?txTime=" + String(txTime);
		if (nRichiesteRicevute == 12) {
			// Invia i dati al server
			if (ritardoMedio !== undefined) {
				stringa += "&rit=" + String(Math.round(ritardoMedio)/1000);
			}
			if (incertezza !== undefined) {
				stringa += "&inc=" + String(incertezza);	
			}
		}	
		httpRequest.open("GET", stringa, true);
		httpRequest.setRequestHeader("Cache-Control","no-store,no-cache,must-revalidate");
		httpRequest.setRequestHeader("Pragma", "no-cache");
		httpRequest.onreadystatechange = elaboraRisposta;
		httpRequest.send(null);
		nRichiesteInviate ++;
	}		
}


function elaboraRisposta() {
	if (httpRequest.readyState != 4) {
		// La risposta ricevuta non e' completa
		return;
	}
	
	if (httpRequest.status != 200) {
    	// Il server ha segnalato un errore
    	return;
	}
    var responseString = httpRequest.responseText;
	rxTime = new Date().getTime();
    
	// Estrae l'informazione di tempo e di TZ Offset
	var arrString = responseString.split("\n"); 
	if (arrString.length != 5) {
		// Il server ha fornito una risposta incompleta
		// alert("Il server ha fornito una risposta incompleta");
		return;
	}
	
	txTime = parseInt(arrString[1], 10);
	
	serverTime = parseInt(arrString[2], 10);
	serverTzOffset= parseInt(arrString[3], 10) * 1000;
	dstIsOn = parseInt(arrString[4], 10);
	if (serverTime == 0 || isNaN(serverTime)) {
		// Il server ha fornito un informazione anomala
		return;
	}	
	if (txTime == 0 || isNaN(txTime)) {
		// Il server ha fornito un informazione anomala
		return;
	}
		
	nRichiesteRicevute ++;
	 		
	// Aggiorna la correzione da applicare all'orologio del client
	calcolaCorrezione ();
	
}

function calcolaCorrezione () {
	var n1 = n2 = 0;
	// Rileva il Timezone offset del client
	clientTzOffset = new Date().getTimezoneOffset()*60*1000;
 	
	// Calcola lo scarto di tempo tra client e server
	if (rxTime >= txTime) {
 		n1 = (rxTime + txTime) / 2;
 		timeOffset = serverTime - n1 ;
	} else {
		timeOffset = timeCorrection = startTime = correzionePrec = undefined;
		ritardo = jitter = [];
		ultimaSinc = 0;
		timer();
		//richiestaInviata = false;
		return;
	}
	
 	// Ritardo client-server
 	nValRitardo = ritardo.length;
 	if (nValRitardo >= 10) {
 		ritardo = [];
 		// Utilizziamo solo 10 valori di ritardo, scartiamo il piu' vecchio
 		ritardo.shift();
 	}
 	n2 = (rxTime - txTime) / 2;
 	if (n2 > 0) {
 		nValRitardo = ritardo.push(n2);
 	}	
 	ritardoMedio = 0;
 	for (var i=0; i < nValRitardo; i++) {
 		ritardoMedio += ritardo[i] / nValRitardo;
 	}
 	ritardoMedio = Math.round(ritardoMedio);
 	
 	// Calcola la correzione da applicare all'orologio locale
 	if (timeCorrection !== undefined) {
 		var correzionePrec = timeCorrection; 
 	}	
 	if (timeOffset !== undefined) {
 		timeCorrection = timeOffset + clientTzOffset + serverTzOffset;
 	}
 	
 	// Calcola il Jitter della sincronizzazione
 	nValJitter = jitter.length;
 	if (nValJitter >= 10) {
 		// Utilizziamo solo 10 valori di ritardo, scartiamo il piu' vecchio
 		jitter.shift();
 	}	
 	if (correzionePrec !== undefined) {
 		nValJitter = jitter.push(Math.abs(correzionePrec - timeCorrection));
 	}
 	if (nValJitter != 0){
	 	jitterMedio = 0;
 		for (var i=0; i < nValJitter; i++) {
	 		jitterMedio += jitter[i] / nValJitter;
	 	}
 		jitterMedio = Math.round(jitterMedio);
 	}
 	
 	if (nValJitter >= 10) {
		incertezza =  Math.round(200 + Math.abs(jitterMedio) + Math.abs(ritardoMedio) + Math.abs(stabilita) + deriva) / 1000;	
 	}
 	if (isNaN(incertezza)) {
 		incertezza = undefined;
 	} 
 	// Resetta il flag di controllo dello scambio dati col server
 	//richiestaInviata = false;
}

function addZero(valIn) {
	// Converte il numero in stringa e aggiunge uno zero iniziale 
	// ai numeri di una sola cifra
	if (valIn < 10) {
		stringaOut = "0" + 	String(valIn);
	} else {
		stringaOut = String(valIn);
	}
	return stringaOut;
}

function ctrSound()	{
	elemId = document.getElementById("cSound");
	if (souniAttivi === false) {
		if (navigator.javaEnabled()) {
			elemId.value="Disabilita i suoni";
			souniAttivi = true;
		}
	} else {
		elemId.value="Abilita i suoni";
		souniAttivi = false;
	}
}
