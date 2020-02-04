<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy

Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://www.osor.eu/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
<%@page import="java.util.*"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant"%>

<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="it.people.util.NavigatorHelper"%>

<jsp:useBean id="City" scope="session" type="it.people.City" />

<%
	String communeCode = "";
	if (City != null) {
		communeCode = "&amp;communeCode=" + City.getKey() + "&amp;selectingCommune=true";
	}
%>

<script type="text/javascript" src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/yetii.js"></script>
<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/css/bookmark.css" />
<link type="text/css" rel="stylesheet" media="all" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/calendar/aqua.css" />


<script type="text/javascript" src="<%=request.getContextPath()+htmlPath%>/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()+htmlPath%>/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()+htmlPath%>/calendar/lang/calendar-it.js"></script>

<script type="text/javascript">
function  saveConfigurationPU(){
	var el0 = document.getElementById('TB_pu_0');
	var el1 = document.getElementById('TB_pu_1');
	var el2 = document.getElementById('TB_pu_2');
	if (!(el0.checked || el1.checked || el2.checked)) {
		alert("Scelta tipologia bookmark mancante");
		return;
	}
	if (el0.checked) {
		var el_0 = document.getElementById('INVIO_pu_0');
		var el_1 = document.getElementById('INVIO_pu_1');
		if (!(el_0.checked || el_1.checked )) {
			alert("Selezionando la tipologia 'COMPLETO' va specificata la modalità (con invio o senza invio)");
			return;
		}
	}
	el0 = document.getElementById('FP_pu_0');
	el1 = document.getElementById('FP_pu_1');
	if (!(el0.checked || el1.checked )) {
		alert("Scelta tipologia firma mancante");
		return;
	}
	
	el0 = document.getElementById('AP_pu_0');
	el1 = document.getElementById('AP_pu_1');
	el2 = document.getElementById('AP_pu_2');
	if (!(el0.checked || el1.checked || el2.checked )) {
		alert("Scelta modalità pagamento mancante");
		return;
	}
	// alert("OK, posso procedere all'invio"); // action=chiamante.jsp
	executeSubmit('loopBack.do?propertyName=bookmark&action=chiamante.jsp&operazione=insertPU');
	return;
}


function confirmBeforeDelete(newAction) {
    if (confirm('Si conferma la cancellazione del servizio?\n')){ 
    	executeSubmit(newAction);
    }     
}

function confirmBeforeUpdate(newAction) {
    var el2 = document.getElementById('nuovoServizio');
    if(el2.value==''){
		alert('Nome Servizio mancante');
	} else {
	    if (confirm('Si conferma la modifica del servizio?\n')){ 
	    	executeSubmit(newAction);
	    }     
    }
}

function insertNewBookmark(){
	var el1 = document.getElementById('nuovoServizio');
	if(el1.value==''){
		alert('Nome Servizio mancante');
	} else if (!document.forms[0].AAF[0].checked && !document.forms[0].AAF[1].checked){
		alert('Parametro di configurazione obbligatorio mancante (Autenticazione forte)');
	} else if (!document.forms[0].AAD[0].checked && !document.forms[0].AAD[1].checked){
		alert('Parametro di configurazione obbligatorio mancante (Autenticazione debole)');
	} else if (!document.forms[0].AI[0].checked && !document.forms[0].AI[1].checked){
		alert('Parametro di configurazione obbligatorio mancante (Abilita intermediari)');
	} else if (!document.forms[0].AUR[0].checked && !document.forms[0].AUR[1].checked){
		alert('Parametro di configurazione obbligatorio mancante (Abilita utente registrato)');
	} else if (!document.forms[0].TB[0].checked && !document.forms[0].TB[1].checked && !document.forms[0].TB[2].checked){
		alert('Parametro di configurazione obbligatorio mancante (Tipologia Bookmark)');
	} else if (!document.forms[0].FP[0].checked && !document.forms[0].FP[1].checked){
		alert('Parametro di configurazione obbligatorio mancante (Firma della pratica)');
	} else if (!document.forms[0].AP[0].checked && !document.forms[0].AP[1].checked && !document.forms[0].AP[2].checked){
		alert('Parametro di configurazione obbligatorio mancante (Pagamenti)');
	} else if (document.forms[0].TB[0].checked && (!document.forms[0].invioB[0].checked && !document.forms[0].invioB[1].checked)){
		alert('Parametro di configurazione obbligatorio mancante (Tipo invio)');
	} else{
		executeSubmit('loopBack.do?propertyName=bookmark&action=servizi.jsp&operazione=insert');
	}
}

function setValue() {
    var el = document.getElementById('servizi');
    var hidSel = el.value;
    var elHid = document.getElementById("A"+hidSel);
    document.getElementById('descrizioneServizio').value = "";
    document.getElementById('descrizioneServizio').value = elHid.value;
    // Get the editor instance that we want to interact with.
	//var oEditor = FCKeditorAPI.GetInstance('descrizioneServizio') ;
	// Set the editor contents (replace the actual one).
	//oEditor.SetHTML(elHid.value) ;
    document.getElementById('nuovoServizio').value = elHid.name;
    
    var elCheck = document.getElementById("A"+hidSel+"_check");
    if(elCheck.value.indexOf('|')>0)
	{
		var temp = elCheck.value.split('|');
		if(temp[0] == "true")
		{
			document.forms[0].AAF[0].checked = true
		}
		else{
			document.forms[0].AAF[1].checked = true
		}
		if(temp[1] == "true")
		{
			document.forms[0].AAD[0].checked = true
		}
		else{
			document.forms[0].AAD[1].checked = true
		}
		if(temp[2] == "true")
		{
			document.forms[0].AI[0].checked = true
		}
		else{
			document.forms[0].AI[1].checked = true
		}
		if(temp[3] == "true")
		{
			document.forms[0].AUR[0].checked = true
		}
		else{
			document.forms[0].AUR[1].checked = true
		}
	}
	
	var elCheckAltri = document.getElementById("ALTRI"+hidSel+"_check");
    if(elCheckAltri.value.indexOf('|')>0) {
		var tempAltri = elCheckAltri.value.split('|');
		if(tempAltri[0] == "COMPLETO") {
			document.forms[0].TB[0].checked = true
			document.getElementById('invioBookmark').style.display = '';
			document.getElementById('FP_0').disabled = false
			document.getElementById('FP_1').disabled = false
			document.getElementById('AP_0').disabled = false
			document.getElementById('AP_1').disabled = false
			document.getElementById('AP_2').disabled = false
			if(tempAltri[1] == "CONINVIO") {
				document.forms[0].invioB[0].checked = true
			} else {
				document.forms[0].invioB[1].checked = true
			}
		} else {
			document.getElementById('invioBookmark').style.display = 'none';
			document.getElementById('FP_0').disabled = true
			document.getElementById('FP_1').disabled = true
			document.getElementById('AP_0').disabled = true
			document.getElementById('AP_1').disabled = true
			document.getElementById('AP_2').disabled = true
			if(tempAltri[0] == "CORTESIA") {
				document.forms[0].TB[1].checked = true
			} else {
				document.forms[0].TB[2].checked = true
			}
		}
		
		if(tempAltri[2] == "TRUE") {
			document.forms[0].FP[0].checked = true
		} else {
			document.forms[0].FP[1].checked = true
		}
		
		if(tempAltri[3] == "DISABILITA") {
			document.forms[0].AP[0].checked = true
		} else if(tempAltri[3] == "FORZA_PAGAMENTO") {
			document.forms[0].AP[1].checked = true
		} else {
			document.forms[0].AP[2].checked = true
		}
		
		
		
	}
}


function setParameter(val){
	if (val=='0') {
		var el = document.getElementById('FP_0');
		el.disabled = false;
		el = document.getElementById('FP_1');
		el.disabled = false;
		el.checked = false;
		el = document.getElementById('AP_0');
		el.disabled = false;
		el.checked = false;
		el = document.getElementById('AP_1');
		el.disabled = false;
		el.checked = false;
		el = document.getElementById('AP_2');
		el.disabled = false;
		el.checked = false;
	} else if (val=='1' || val=='2'){
		var el = document.getElementById('FP_1');
		el.checked = true;
		el.disabled = true;
		el = document.getElementById('FP_0');
		el.disabled = true;
		el = document.getElementById('AP_0');
		el.checked = true;
		el.disabled = true;
		el = document.getElementById('AP_1');
		el.disabled = true;
		el = document.getElementById('AP_2');
		el.disabled = true;
	}
}

function setParameterPU(val){
	if (val=='0') {
		var el = document.getElementById('FP_pu_0');
		el.disabled = false;
		el = document.getElementById('FP_pu_1');
		el.disabled = false;
		el.checked = false;
		el = document.getElementById('AP_pu_0');
		el.disabled = false;
		el.checked = false;
		el = document.getElementById('AP_pu_1');
		el.disabled = false;
		el.checked = false;
		el = document.getElementById('AP_pu_2');
		el.disabled = false;
		el.checked = false;
	} else if (val=='1' || val=='2'){
		var el = document.getElementById('FP_pu_1');
		el.checked = true;
		el.disabled = true;
		el = document.getElementById('FP_pu_0');
		el.disabled = true;
		el = document.getElementById('AP_pu_0');
		el.checked = true;
		el.disabled = true;
		el = document.getElementById('AP_pu_1');
		el.disabled = true;
		el = document.getElementById('AP_pu_2');
		el.disabled = true;
		// INVIO_pu_0
		el = document.getElementById('INVIO_pu_0');
		el.checked = false;
		el = document.getElementById('INVIO_pu_1');
		el.checked = false;
	} 
}

	function isValidDate(TextBox,MostraERR)	
	{										
		dateStr = TextBox.value;
		var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{2,4})$/;
	
		if (dateStr == "")
		{
			return true;
		}
		
		if (dateStr.match("/") == null)
		{
			if ((dateStr.length=8)|| (dateStr.length=6)){
				var dateStrTmp = dateStr.substring(0,2) + "/" + dateStr.substring(2,4) + "/" + dateStr.substring(4,dateStr.length);
				dateStr = dateStrTmp;
				TextBox.value = dateStr;
				}else{
				errore(TextBox);
				}
		}else{
			aData=dateStr.split("/");
			day = aData[0];
			month = aData[1];
			year = aData[2];
			if (day.length==1){
				day="0" + day;
			}
			if (month.length==1){
				month="0" + month;
			}	
			var dateStrTmp = day + "/" + month + "/" + year;
			dateStr = dateStrTmp;
			TextBox.value = dateStr;
		}
		
		var matchArray = dateStr.match(datePat);
	
		if (matchArray == null) {
			if (MostraERR)
			{
				errore(TextBox);
			}
			return false;
		}
		month = matchArray[3]; 
		day = matchArray[1];
		year = matchArray[4];
		// controllo lunghezza anno
		if (year.length==3){
			if (MostraERR) {
				errore(TextBox);
			}
			return false;
		}
		// Aggiusta l'anno
		switch (year.length)
		{
			case 2: 
				if (year > "50")
				{
					year = "19" + year.substring(0,2) ;
				}
				else
				{
					year = "20" + year.substring(0,2) ;
				}
				break;
		}	
		dateStr = dateStr.substring(0,6) + year;
		//Riscrive la data nel text box
		TextBox.value = dateStr;
		
		if (month < 1 || month > 12) { 
			if (MostraERR)
			{
				errore(TextBox);
			}
			return false;
		}
	
		if (day < 1 || day > 31) {
			if (MostraERR)
			{
				errore(TextBox);
			}
			return false;
		}
	
		if ((month==4 || month==6 || month==9 || month==11) && day==31) {
			if (MostraERR)
			{
				errore(TextBox);
			}
			return false
		}
	
		if (month == 2) { 
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
			if (day>29 || (day==29 && !isleap)) {
				if (MostraERR)
				{
					errore(TextBox);
				}
				return false;
		   }
		}
		return true;
	}
	
	function errore(TextBox)
	{
		alert("La data non è valida");
		TextBox.value="";
		TextBox.focus();
	}

function abilitaCampi(){
	var elem = document.getElementById('flag_bando');
	if (elem.checked) {
		document.getElementById('tabellaInfoBandi').style.display = '';
	} else {
		document.getElementById('tabellaInfoBandi').style.display = 'none';
	}
}

function checkDate(){
	var elem = document.getElementById('flag_bando');
	if (elem.checked) {
		var elem1 = document.getElementById('dallaData');
		var elem2 = document.getElementById('allaData');
		if (elem1.value=='' || elem2.value==''){
			alert("Attenzione :  l'intervallo di validità del bando non è stato valorizzato");
			return false;
		}
	} else {
		var elem1 = document.getElementById('dallaData');
		var elem2 = document.getElementById('allaData');
		if( ( (elem1.value=='') && !(elem2.value=='') ) || ( !(elem1.value=='') && (elem2.value=='') ) ) {
			alert("Attenzione :  una delle date non risulta valorizzata");
			return false;
		}
	}
	return true;
}
</script>
<script type="text/javascript" src="/people<%=htmlPath%>/FCKeditor/fckeditor.js"></script>
<script type="text/javascript">
      window.onload = function()
      {
        var oFCKeditor = new FCKeditor( 'descrizioneServizio' ) ;
        oFCKeditor.BasePath = '/people<%=htmlPath%>/FCKeditor/' ; 
	oFCKeditor.ToolbarSet = 'People' ;	
        oFCKeditor.ReplaceTextarea();
	
      }
</script>

<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
}
%>

<input name="numTab" id="numTab" type="hidden"  readonly="readonly"  value="<%=(String)request.getAttribute("numTab")%>"/>

<br/>
<table style="padding: 5px; width:100%;">
	<tr><td colspan="2" style="font-size:130%; color:#005094;" align="center">Pagina per la gestione dei bookmark e del Procedimento Unico</td></tr>
</table>

<div id="tabcont1" class="tabpanel">
	<ul id="tabcont1-nav" class="tabnav">
		<li><a href="#tab1"><span>Bookmark</span></a></li>
		<li><a href="#tab2"><span>Proc.Unico</span></a></li>
		<li><a href="#tab3"><span>Configurazioni avanzate</span></a></li>
		<li onClick="executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;tab=4');"><a href="#tab4"><span>Lista Url Bookmarks</span></a></li>
	</ul>
	<div id="tab1" class="tab">
		<h2>Gestione Bookmark</h2>
		<table style="padding: 5px; width: 100%;">
			<tr>
				<td align="left" valign="top"><b>Eventi della vita:</b><br />
				</td>
			</tr>
			<tr>
				<td valign="top" align="left">
					<html:select style="width: 350px;" property="data.datiTemporanei.eventoVitaSel"	onchange="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp');">
						<logic:notEmpty name="pplProcess" property="data.datiTemporanei.eventiVita">
							<html:optionsCollection filter="yes" name="pplProcess" property="data.datiTemporanei.eventiVita" label="descrizione" value="codiceEventoVita" />
						</logic:notEmpty>
						<logic:empty name="pplProcess" property="data.datiTemporanei.eventiVita">
							Nessun evento della vita presente
						</logic:empty>
					</html:select>
				</td>
			</tr>
			<tr>
				<td valign="top" align="left"><input type="button" class="btn"
					accesskey="E"
					onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=eventiVita.jsp');"
					onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=eventiVita.jsp');"
					value="GESTIONE EVENTI DELLA VITA" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="left" valign="top"><b>Servizi gi&agrave; definiti:</b></td>
			</tr>
			<tr>
				<td valign="top" align="left">
				<html:select styleId="servizi" style="width: 400px;" property="data.datiTemporanei.servizioSel"
					onkeypress="javascript:setValue();" onchange="javascript:setValue();" value=""
					onclick="javascript:setValue();" size="10">
					<logic:notEmpty name="pplProcess" property="data.datiTemporanei.servizi">
						<html:optionsCollection name="pplProcess" property="data.datiTemporanei.servizi" label="nome" value="codiceServizio" />
					</logic:notEmpty>
					
				</html:select>
			 	&nbsp; 
			 	<logic:iterate name="pplProcess" property="data.datiTemporanei.servizi" id="row">
					<input type="hidden" id="A<bean:write name="row" property="codiceServizio" />" name="<bean:write name="row" property="nome" />" value="<bean:write name="row" property="descrizione" />" />
					<input type="hidden" id="A<bean:write name="row" property="codiceServizio" />_check" value="<bean:write name="row" property="checkStatus" />" />
					<input type="hidden" id="ALTRI<bean:write name="row" property="codiceServizio" />_check" value="<bean:write name="row" property="checkAltriParametri" />" />
				</logic:iterate>
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" id="modificaLight" name="modificaLight" value="SI" />&nbsp;In caso di MODIFICA modificare solo il nome e la descrizione del bookmark (non viene modificato il puntamento)
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="left" valign="top"><b>Nome servizio (*): </b></td>
			</tr>
			<tr>
				<td valign="top" align="left">
					<label for="nuovoServizio"><input type="text" name="nuovoServizio" id="nuovoServizio" style="width:400px" />
					</label>	
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="left" valign="top"><b>Descrizione:</b></td>
				<td  valign="top"><b>Autenticazione(*):</b></td>
			</tr>
			<tr>
				<td width="75%"><textarea cols="10" rows="40" name="descrizioneServizio" id="descrizioneServizio" style="width: 400px; padding: 5px;"></textarea></td>
				<td width="45%">
				<table style="border:1px solid #EAEAEA; padding: 5px; font-size: 100%" width="100%">
					<tr>
						<td valign="middle">Abilita autenticazione forte</td>
						<td valign="middle">Si&nbsp;
							<input type="radio" id="AAF" name="AAF" value="si" />
						</td>
						<td valign="middle">No&nbsp;
							<input id="AAF" type="radio" name="AAF" value="no" />
						</td>
					</tr>
					<tr>
						<td>Abilita autenticazione debole</td>
						<td>Si&nbsp;
							<input id="AAD" type="radio" name="AAD" value="si" />
						</td>
						<td>No&nbsp;
							<input id="AAD" type="radio" name="AAD" value="no" />
						</td>
					</tr>
					<tr>
						<td>Abilita intermediari</td>
						<td>Si&nbsp;
							<input id="AI" type="radio" name="AI" value="si" />
						</td>
						<td>No&nbsp;
							<input id="AI" type="radio" name="AI" value="no" />
						</td>
					</tr>
					<tr>
						<td>Abilita utente registrato</td>
						<td>Si&nbsp;
							<input id="AUR" type="radio" name="AUR" value="si" />
						</td>
						<td>No&nbsp;
							<input id="AUR" type="radio"  name="AUR" value="no" />
						</td>
					</tr>
				</table>
				<br/><br/><br/>
				<table style="border:1px solid #EAEAEA; padding: 5px; font-size: 100%" width="100%">
					<tr>
						<td ><b>Altri parametri di configurazione</b><br/></td>
					</tr>
					<tr>
						<td ><i>Tipologia bookmark</i></td>
					</tr>
					<tr>
						<td ><input id="TB" type="radio" name="TB" value="0" onclick="setParameter(this.value);document.getElementById('invioBookmark').style.display = '';"/> - Completo
						<br/>
							<table style="padding: 5px; width:96%;" id="invioBookmark">
								<tr><td style="font-size: 60%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="INVIO_0" type="radio" name="invioB" value="0" /> - con INVIO</td></tr>
								<tr><td style="font-size: 60%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="INVIO_1" type="radio" name="invioB" value="1" /> - senza INVIO</td></tr>
							</table>
						</td>
					</tr>
					<tr>
						<td ><input id="TB" type="radio" name="TB" value="1" onclick="setParameter(this.value);document.getElementById('invioBookmark').style.display = 'none';" /> - Invio di cortesia</td>
					</tr>
					<tr>
						<td ><input id="TB" type="radio" name="TB" value="2" onclick="setParameter(this.value);document.getElementById('invioBookmark').style.display = 'none';" /> - Livello 2 (solo compilazione e stampa)</td>
					</tr>
					<tr>
						<td >&nbsp;</td>
					</tr>
					<tr>
						<td ><i>Firma della pratica</i></td>
					</tr>
					<tr>
						<td ><input id="FP_0" type="radio" name="FP" value="0" /> - Con firma digitale</td>
					</tr>
					<tr>
						<td ><input id="FP_1" type="radio" name="FP" value="1" /> - Senza Firma digitale</td>
					</tr>
					<tr>
						<td >&nbsp;</td>
					</tr>
					<tr>
						<td ><i>Pagamenti</i></td>
					</tr>
					<tr>
						<td ><input id="AP_0" type="radio" name="AP" value="0" /> - Salta step pagamenti</td>
					</tr>
					<tr>
						<td ><input id="AP_1" type="radio" name="AP" value="1" /> - Obbliga step pagamenti
							<br/>
							<table style="padding: 5px; width:96%;" id="invioBookmark">
								<tr><td style="font-size: 60%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_MP_1" type="radio" name="AP_MP" value="1" /> - solo ONLINE</td></tr>
								<tr><td style="font-size: 60%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_MP_2" type="radio" name="AP_MP" value="2" /> - ONLINE ed OFFLINE</td></tr>
							</table>
						</td>
					</tr>
					<tr>
						<td ><input id="AP_2" type="radio" name="AP" value="2" /> - Pagamenti opzionali
							<br/>
							<table style="padding: 5px; width:96%;" id="invioBookmark">
								<tr><td style="font-size: 60%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_MPO_1" type="radio" name="AP_MPO" value="1" /> - solo ONLINE</td></tr>
								<tr><td style="font-size: 60%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_MPO_2" type="radio" name="AP_MPO" value="2" /> - ONLINE ed OFFLINE</td></tr>
							</table>						
						</td>
					</tr>
					<tr>
						<td >&nbsp;</td>
					</tr>
				</table>
				<br/><br/>
				<logic:notEmpty name="pplProcess" property="data.datiTemporanei.comuniBookmark">
				<table style="border:1px solid #EAEAEA; padding: 5px; font-size: 100%" width="100%">
					<tr>
						<td ><b>Selezionare eventuali altri comuni per cui creare il bookmark</b><br/></td>
					</tr>
					<tr>
						<td >
							<html:select style="width: 350px;" property="data.datiTemporanei.comuniBookmarkSel" multiple="true">
								<html:optionsCollection name="pplProcess" property="data.datiTemporanei.comuniBookmark" label="descrizione" value="codice" />
							</html:select>			
						</td>
					</tr>
				</table>
				</logic:notEmpty>

			<tr>
				<td valign="top" align="left"><input type="button" class="btn"
					accesskey="A"
					onkeypress="javascript:insertNewBookmark();"
					onclick="javascript:insertNewBookmark();"
					value="AGGIUNGI NUOVO" />&nbsp;
					<input type="button" class="btn"
					accesskey="M"
					onkeypress="javascript:confirmBeforeUpdate('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=update');"
					onclick="javascript:confirmBeforeUpdate('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=update');"
					value="MODIFICA" />&nbsp;
					<input type="button" class="btn"
					accesskey="E"
					onkeypress="javascript:confirmBeforeDelete('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=delete');"
					onclick="javascript:confirmBeforeDelete('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=delete');"
					value="ELIMINA" />
			   </td>
			</tr>
			
			
			
			
			
			
			
		</table>
	</div>
	
<%
ProcessData data = (ProcessData)pplProcess.getData();
if (data.getDatiTemporanei().getParametriPU()!=null && (data.getDatiTemporanei().getParametriPU().getTipo().equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel) || data.getDatiTemporanei().getParametriPU().getTipo().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label) )){
	out.write("<input type=\"hidden\" id=\"completo\" name=\"completo\" value=\"0\">");
}
%>
	
	<div id="tab2" class="tab"> <!-- sezione per la configurazione del Procedimento UNICO -->
		<h2>Configurazione Procedimento Unico</h2>	
		<table style="padding: 5px; width: 100%;">
		
			<tr>
				<td colspan="4"><b><i>Parametri per l'autenticazione a Procedimento Unico</i></b>
				<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;Per i parametri di autenticazione vanno istanziati i serviceprofile.xml<br/><br/>
				</td>
			</tr>
		 	<%String check = "checked"; 
		 	%>
		 
			<tr><td colspan="4"><b><i>Tipologia Procedimento Unico (*)</i></b></td></tr>
			<tr><td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="TB_pu_0" type="radio" name="TB_pu" value="0" onclick="setParameterPU(this.value);document.getElementById('invio').style.display = '';" 
					<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.tipo" value="COMPLETO"><%=check%></logic:equal>
				/> - Completo
						
					<br/>
					<table style="padding: 5px; width:96%;" id="invio">
						<tr><td style="font-size: 80%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="INVIO_pu_0" type="radio" name="INVIO_pu" value="0"
															<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.tipo" value="COMPLETO">
																	<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.conInvio" value="true"><%=check%></logic:equal>
															</logic:equal>
														/> - con INVIO</td></tr>
						<tr><td style="font-size: 80%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input id="INVIO_pu_1" type="radio" name="INVIO_pu" value="1" 
															<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.tipo" value="COMPLETO">
																<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.conInvio" value="false"><%=check%></logic:equal>
															</logic:equal>
														/> - senza INVIO</td></tr>
					</table>
				</td>
			</tr>
			<tr><td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="TB_pu_1" type="radio" name="TB_pu" value="1"	onclick="setParameterPU(this.value);document.getElementById('invio').style.display = 'none';" 
					<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.tipo" value="CORTESIA"><%=check%></logic:equal>
				/> - Invio di cortesia</td></tr>
			<tr><td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="TB_pu_2" type="radio" name="TB_pu" value="2"	onclick="setParameterPU(this.value);document.getElementById('invio').style.display = 'none';"
					<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.tipo" value="LIVELLO2"><%=check%></logic:equal>				 
				/> - Livello 2 (solo compilazione e stampa)</td></tr>
			<tr><td colspan="4">&nbsp;</td></tr>
			
			<tr><td colspan="4"><b><i>Firma della pratica (*)</i></b></td></tr>
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;<input id="FP_pu_0" type="radio" name="FP_pu" value="0" 
												<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.abilitaFirma" value="true"><%=check%></logic:equal> 
											/> - Con firma digitale</td></tr>
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;<input id="FP_pu_1" type="radio" name="FP_pu" value="1"
												<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.abilitaFirma" value="false"><%=check%></logic:equal>  
											/> - Senza Firma digitale</td></tr>
			<tr><td colspan="4">&nbsp;</td></tr>
			
			<tr><td colspan="4"><b><i>Pagamenti (*)</i></b></td></tr>
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_pu_0" type="radio" name="AP_pu" value="0" 
												<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.pagamenti" value="DISABILITA"><%=check%></logic:equal>
											/> - Salta step pagamenti</td></tr>
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_pu_1" type="radio" name="AP_pu" value="1" 
													<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.pagamenti" value="FORZA_PAGAMENTO"><%=check%></logic:equal>
											/> - Obbliga step pagamenti</td></tr>


			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="AP_MP_pu_1" type="radio" name="AP_MP_pu" value="1" 
													<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.modalitaPagamenti" value="SOLO_ONLINE"><%=check%></logic:equal>
											/> - solo ONLINE</td></tr>
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="AP_MP_pu_2" type="radio" name="AP_MP_pu" value="2" 
													<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.modalitaPagamenti" value="ONLINE_OFFLINE"><%=check%></logic:equal>
											/> - ONLINE ed OFFLINE</td></tr>


											
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;<input id="AP_pu_2" type="radio" name="AP_pu" value="2"
													<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.pagamenti" value="OPZIONALE"><%=check%></logic:equal> 
											/> - Pagamenti opzionali</td></tr>

			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="AP_MPO_pu_1" type="radio" name="AP_MPO_pu" value="1" 
													<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.modalitaPagamentiOpzionali" value="SOLO_ONLINE"><%=check%></logic:equal>
											/> - solo ONLINE</td></tr>
			<tr><td colspan="4">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="AP_MPO_pu_2" type="radio" name="AP_MPO_pu" value="2" 
													<logic:equal name="pplProcess" property="data.datiTemporanei.parametriPU.modalitaPagamentiOpzionali" value="ONLINE_OFFLINE"><%=check%></logic:equal>
											/> - ONLINE ed OFFLINE</td></tr>


											
			<tr><td colspan="4">&nbsp;</td></tr>
			<tr>
				<td colspan="4" valign="top" align="center"><input type="button" class="btn"
					accesskey="A"
					onkeypress="javascript:saveConfigurationPU();"
					onclick="javascript:saveConfigurationPU();"
					value="SALVA CONFIGURAZIONE E TORNA ALLA GENERAZIONE DELL'ITER" />
			   </td>
			</tr>
		</table>
	</div>
	
	
	<div id="tab3" class="tab">
		<logic:empty name="view" scope="request">
			<logic:empty name="edit" scope="request">
				<table>
					<tr>
						<td valign="top" align="left"><input type="button" class="btn"
							accesskey="E"	
							onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiView&amp;tab=3');"
							onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiView&amp;tab=3');"
							value="Configura bookmark (avanzato)" />
						</td>
					</tr>
				</table>
			</logic:empty>
		</logic:empty>
		<logic:notEmpty name="view" scope="request">
			<table padding: 5px; width:80%;" >
				<logic:empty name="listaBookmark" scope="request">
					<tr>
						<td>
							Nessun Bookmark configurabile come bando
						</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="listaBookmark" scope="request">
					<tr>
						<td width="50%" colspan="2">Descrizione</td>
						<td width="15%" align="center">Bando</td>
						<td width="15%">Data attivazione</td>
						<td width="15%">Data scadenza</td>
						<td width="5%">&nbsp;</td>
					</tr>					
					<logic:iterate id="rows1" name="listaBookmark" scope="request">
						<tr>
							<td colspan="6" bordercolor="black"  >
								<b><bean:write name="rows1" property="descrizione" /></b>
							</td>
						</tr>
						<logic:empty name="rows1" property="rows" >
							<tr><td>&nbsp;</td><td colspan="5" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nessun bookamark appartenente a questa categoria</td></tr>  
						</logic:empty>						
						<logic:notEmpty name="rows1" property="rows" >
							<logic:iterate id="rows2" name="rows1" property="rows">
								<tr>
									<td>&nbsp;</td>
									<td><bean:write name="rows2" property="titoloServizio" /></td>
									<td align="center">
										<logic:equal name="rows2" property="flg_bando" value="true">
											<input type="checkbox" disabled="disabled"  checked="checked"/>
										</logic:equal>
										<logic:notEqual name="rows2" property="flg_bando" value="true">
											<input type="checkbox" disabled="disabled" value="0"/>
										</logic:notEqual>
									</td>
									<td><bean:write name="rows2" property="dallaData" /></td>
									<td><bean:write name="rows2" property="allaData" /></td>
									<td>
									<input type="button" class="btn"
										accesskey="E"	
										onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiEdit&amp;tab=3&amp;cod=<bean:write name="rows2" property="cod_servizio" />');"
										onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiEdit&amp;tab=3&amp;cod=<bean:write name="rows2" property="cod_servizio" />');"
										value="CONFIGURA" />
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>

					</logic:iterate>
				</logic:notEmpty>
			</table>
		</logic:notEmpty>
		
		
		<logic:notEmpty name="edit" scope="request">
			<logic:present name="bando" scope="request">
				<br/>
				<h4>Dettaglio</h4>
				<table style="border:1px solid #EAEAEA; padding: 10px; width:80%;" cellpadding="5px" >
					<tr><td><b>Titolo : </b><bean:write name="bando" property="titoloServizio" /></td></tr>
					<tr><td><b>Descrizione : </b><bean:write name="bando" property="descrizioneServizio" filter="yes"/></td></tr>
					<tr><td><html:checkbox property="flg_bando" name="bando" styleId="flag_bando" >&nbsp;&nbsp;Attiva funzionalità avanzate (orologio, ...)</html:checkbox></td></tr>
					<tr id="tabellaInfoBandi">
						<td>
							<b>Valido dalla data (se non specificato il bookmark è sempre accedibile) : </b><html:text name="bando" property="dallaData" size="10"  styleId="dallaData" onblur="javascript:isValidDate(this,true);" />&nbsp;<b>alla data&nbsp;</b><html:text name="bando" property="allaData" size="10" styleId="allaData" onblur="javascript:isValidDate(this,true);" />
							<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "dallaData",
								button         :    "dallaData"
								});
							</script>
							<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "allaData",
								button         :    "allaData"
								});
							</script>
							
							
						</td>
					</tr>
					<tr><td><b>Lista utenti abilitati ad accedere al bookmark</b>&nbsp;&nbsp;(se vuota, tutti gli utenti posso accedere)</td></tr>
					<logic:empty name="bando" property="accessList">
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;<i>Lista vuota</i>
							</td>
						</tr>
					</logic:empty>
					<tr>
						<td>
						<table style="border:0px solid #EAEAEA; padding: 5px; font-size: 110%;">
							<logic:iterate id="rows_accessList" name="bando" property="accessList">
							<tr>
								<td>&nbsp;&nbsp;&nbsp;<bean:write name="rows_accessList" property="descrizione" /></td>
								<td>&nbsp;
									<input type="button" class="btn"
										accesskey="E"	
										onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiRemoveUtente&amp;tab=3&amp;cod=<bean:write name="bando" property="cod_servizio" />&amp;cf=<bean:write name="rows_accessList" property="descrizione" />');"
										onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiRemoveUtente&amp;tab=3&amp;cod=<bean:write name="bando" property="cod_servizio" />&amp;cf=<bean:write name="rows_accessList" property="descrizione" />');"
										value="ELIMINA" />
								</td>
							</tr>	
							</logic:iterate>
						</table>
						</td>
					</tr>
					<tr><td>&nbsp;&nbsp;&nbsp;<input type="text" size="17" name="newUtente" id ="newUtente" maxlength="16"/>&nbsp;
						<input type="button" class="btn" 
							accesskey="E"	
							onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiAddUtente&amp;tab=3&amp;cod=<bean:write name="bando" property="cod_servizio" />');"
							onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiAddUtente&amp;tab=3&amp;cod=<bean:write name="bando" property="cod_servizio" />');"
							value="AGGIUNGI UTENTE" />
					</td></tr>		
				</table>	
		<!--  		
				<script type="text/javascript">
					abilitaCampi();
				</script>	
		  -->
			</logic:present>
			<br/><br/>
			<input type="button" class="btn"
				accesskey="E"	
				onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiView&amp;tab=3');"
				onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiView&amp;tab=3');"
				value="INDIETRO" />
			&nbsp;&nbsp;
			<input type="button" class="btn"
				accesskey="E"	
				onkeypress="javascript:if (checkDate()) {executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiUpdate&amp;tab=3&amp;cod=<bean:write name="bando" property="cod_servizio" />');}"
				onclick="javascript:if (checkDate()) {executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;operazione=bandiUpdate&amp;tab=3&amp;cod=<bean:write name="bando" property="cod_servizio" />');}"
				value="SALVA" />
		</logic:notEmpty>
		
	</div>
	
	
	
	
	
    <div id="tab4" class="tab">  
        <logic:notEmpty name="view" scope="request">
            <table padding: 5px; width:80%;" >
                <logic:empty name="listaBookmark" scope="request">
                    <tr>
                        <td colspan="2">
                            Nessun Servizio
                        </td>
                    </tr>
                </logic:empty>
                <logic:notEmpty name="listaBookmark" scope="request">
                    <logic:iterate id="rows1" name="listaBookmark" scope="request">
                        <tr>
                            <td colspan="2" bordercolor="black"  >
                                <b><bean:write name="rows1" property="descrizione" /></b>
                            </td>
                        </tr>
                        <logic:empty name="rows1" property="listaEventi" >
                            <tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nessun Evento della vita appartenente a questo comune</td></tr>  
                        </logic:empty>						
                        <logic:notEmpty name="rows1" property="listaEventi" >
                            <logic:iterate id="rows2" name="rows1" property="listaEventi">
                                <tr>
                                    <td colspan="2"><bean:write name="rows2" property="descrizione" /></td>
                                </tr>
                                <logic:empty name="rows2" property="listaServizi" >
                                    <tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nessun servizio appartenente a questa categoria</td></tr>  
                                </logic:empty>
                                <logic:notEmpty name="rows2" property="listaServizi" >
                                    <logic:iterate id="rows3" name="rows2" property="listaServizi">
                                        <tr>
                                            <td><bean:write name="rows3" property="nome" /></td>
                                            <td><%=request.getContextPath()%>/initProcess.do?processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico<br/>&amp;idBookmark=<bean:write name="rows3" property="codice" />&amp;codEnte=<bean:write name="rows1" property="codice" />&amp;codEveVita=<bean:write name="rows2" property="codice" /><%=communeCode %></td>
                                        </tr>
                                    </logic:iterate>
                                </logic:notEmpty>

                            </logic:iterate>
                        </logic:notEmpty>

                    </logic:iterate>
                </logic:notEmpty>
            </table>
        </logic:notEmpty>

        <input type="button" class="btn"
               accesskey="E"	
               onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;tab=1');"
               onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp&amp;tab=1');"
               value="INDIETRO" />

    </div>
	
</div>



<script type="text/javascript">
    var valTab=document.getElementById('numTab').value;
    if (!valTab) {
       valTab = 1;
    }
	var tabber=new Yetii('tabcont1',valTab);
	tabber.init();
	var el = document.getElementById('completo');
	if (el.value==0){
		document.getElementById('invio').style.display = 'none';
		el = document.getElementById('FP_pu_1');
		el.disabled = true;
		el = document.getElementById('FP_pu_0');
		el.disabled = true;
		el = document.getElementById('AP_pu_0');
		el.disabled = true;
		el = document.getElementById('AP_pu_1');
		el.disabled = true;
		el = document.getElementById('AP_pu_2');
		el.disabled = true;
	}
	document.getElementById('inviobookmark').style.display = 'none';
</script>




<br/>

<table align="center">
	<tr>
		<td><input type="button" class="btn"
			accesskey="T"
			onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=chiamante.jsp');"
			onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=chiamante.jsp');"
			value="TORNA ALLA GENERAZIONE ITER" /></td>
	</tr>
</table>


<br/>
