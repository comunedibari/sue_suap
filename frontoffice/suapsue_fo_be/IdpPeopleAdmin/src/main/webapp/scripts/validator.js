
function ControllaPIVA(param1) {
	if(param1 == "form1:codicefiscaleIntermediario")
	{
		var cf = document.getElementById(param1);
		if(cf.disabled)
		{
			return true;
		}
	}
	var pi = document.getElementById(param1).value;
	if (pi == "") {
		return false;
	}
	if (pi.length != 11) {
		//return "La lunghezza della partita IVA non \xe8\n" + "corretta: la partita IVA dovrebbe essere lunga\n" + "esattamente 11 caratteri.\n";
		return false;
	}
	validi = "0123456789";
	for (i = 0; i < 11; i++) {
		if (validi.indexOf(pi.charAt(i)) == -1) {
			//return "La partita IVA contiene un carattere non valido `" + pi.charAt(i) + "'.\nI caratteri validi sono le cifre.\n";
			return false;
		}
	}
	s = 0;
	for (i = 0; i <= 9; i += 2) {
		s += pi.charCodeAt(i) - "0".charCodeAt(0);
	}
	for (i = 1; i <= 9; i += 2) {
		c = 2 * (pi.charCodeAt(i) - "0".charCodeAt(0));
		if (c > 9) {
			c = c - 9;
		}
		s += c;
	}
	if ((10 - s % 10) % 10 != pi.charCodeAt(10) - "0".charCodeAt(0)) {
		//return "La partita IVA non \xe8 valida:\n" + "il codice di controllo non corrisponde.\n";
		return false;
	}
	return true;
}
function ControllaCF(param1) {
	var cf = document.getElementById(param1).value;	
	var validi, i, s, set1, set2, setpari, setdisp;
	if (cf == "") {
		return false;
	}
	cf = cf.toUpperCase();
	if (cf.length != 16) {
		//return "La lunghezza del codice fiscale non \xe8\n" + "corretta: il codice fiscale dovrebbe essere lungo\n" + "esattamente 16 caratteri.\n";
		return false;
	}
	validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	for (i = 0; i < 16; i++) {
		if (validi.indexOf(cf.charAt(i)) == -1) {
			//return "Il codice fiscale contiene un carattere non valido `" + cf.charAt(i) + "'.\nI caratteri validi sono le lettere e le cifre.\n";
			return false;
		}
	}
	set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
	setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
	s = 0;
	for (i = 1; i <= 13; i += 2) {
		s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
	}
	for (i = 0; i <= 14; i += 2) {
		s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
	}
	if (s % 26 != cf.charCodeAt(15) - "A".charCodeAt(0)) {
		//return "Il codice fiscale non \xe8 corretto:\n" + "il codice di controllo non corrisponde.\n";
		return false;
	}
	return true;
}

