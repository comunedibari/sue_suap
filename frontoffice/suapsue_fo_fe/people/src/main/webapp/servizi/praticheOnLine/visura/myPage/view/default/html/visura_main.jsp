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

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@page import="java.util.*"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<bean:define id = "inCompilazioneAction" 
          value = "javascript:executeSubmit('loopBack.do?propertyName=inCompilazione');" />
<bean:define id = "inviateAction" 
          value = "javascript:executeSubmit('loopBack.do?propertyName=completate');" />
<bean:define id = "erroriInvioAction" 
          value = "javascript:executeSubmit('loopBack.do?propertyName=nonInviate');" />

<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.TipologiaPraticheSelezionabili" %>
<link type="text/css" rel="stylesheet" media="all" href="/people/servizi/praticheOnLine/visura/myPage/view/default/html/calendar/aqua.css" />
<script type="text/javascript" src="/people/servizi/praticheOnLine/visura/myPage/view/default/html/yetii.js"></script>
<% 
String basePath = "/servizi/praticheOnLine/visura/myPage/";
String htmlPath=basePath.concat("view/default/html");

String erroriInvioValue = TipologiaPraticheSelezionabili.erroriInvio.getCodice();
String inCompilazioneValue = TipologiaPraticheSelezionabili.inCompilazione.getCodice();
String inviateValue = TipologiaPraticheSelezionabili.inviate.getCodice();

%>
<script type="text/javascript" src="<%=request.getContextPath()+htmlPath%>/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()+htmlPath%>/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()+htmlPath%>/calendar/lang/calendar-it.js"></script>
<script type="text/javascript">

	function errore(TextBox)
	{
		alert("La data non è valida");
		TextBox.value="";
		TextBox.focus();
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

</script>


<html:xhtml/>

<%
ProcessData data = (ProcessData)pplProcess.getData();
if (data.isInCompilazione()){
	out.write("<input type=\"hidden\" id=\"inCompilazioneSel\" name=\"inCompilazioneSel\" value=\"0\" />");
} else {
	out.write("<input type=\"hidden\" id=\"inCompilazioneSel\" name=\"inCompilazioneSel\" value=\"1\" />");
}
out.write("<input type=\"hidden\" id=\"tipologiaSelezionata\" name=\"tipologiaSelezionata\" value=\"" + data.getTipologiaSelezionata() + "\" />");
String labelPulsante="Cerca&nbsp;&gt;&gt;";
%>

<table style="border:1px solid #EAEAEA; padding: 5px;">
  <tr>
    <td colspan="4" class="dimensione15"></td>
  </tr>
  <tr>
    <td colspan="4"><h1><bean:message key="label.main.titolo"/></h1><br /></td>
  </tr>
  <tr>
    <td colspan="4"><h3 style="subtitle"><bean:message key="label.main.benvenuto"/></h3></td>
  </tr>

<% if (data.isFailedSends()) { %>
  <tr>
    <td colspan="4" align="center">
	    <table cellpadding="8" cellspacing="0" class="margin-top-bottom15px borderedinfoboxtable">
	        <tbody>
	            <tr>
	                <td align="center">
		                <table border="0" cellspacing="1" width="100%">
			                <tbody>
				                <tr>
					                <td>
					                	<html:img styleClass="runtimewarnmsg" page="/img/alert.png" alt="Attenzione!"></html:img>
					                </td>
					                <td style="vertical-align:middle">
					                	<strong>
					                		<span class="usrmsg">
					                			<% if (data.getFailedSendsNumber() > 1) { %>
						                			Attenzione: ci sono <%=data.getFailedSendsNumber() %> pratiche non inviate a causa di un errore.
						                		<% } else { %>
						                			Attenzione: c&rsquo;&egrave; una pratica non inviata a causa di un errore.
						                		<% } %>
							                </span>
							            </strong>
							        </td>
				                </tr>
			                </tbody>
		                </table>
	                </td>
	            </tr>
	        </tbody>
	    </table>
	</td>
  </tr>  
<% } %>

  
<% if (data.isFailedSends()) { %>
  <tr>
    <td width="20%"><bean:message key="label.main.erroriInvio"/></td>
    <td colspan="3">
    	<html:radio value="<%=erroriInvioValue %>" name="pplProcess" property="data.tipologiaSelezionata" styleId="r_comp" onclick="<%=erroriInvioAction %>" />
    </td>
  </tr> 
<% } %>
  <tr>
    <td><bean:message key="label.main.compilazione"/></td>
    <td colspan="3">
    	<html:radio value="<%=inCompilazioneValue %>" name="pplProcess" property="data.tipologiaSelezionata" styleId="r_comp" onclick="<%=inCompilazioneAction %>" />
    </td>
  </tr> 
  <tr>
    <td><bean:message key="label.main.completate"/></td>
    <td colspan="3">
    	<html:radio value="<%=inviateValue %>" name="pplProcess" property="data.tipologiaSelezionata" styleId="r_inv" onclick="<%=inviateAction %>" />
    </td>
  </tr>
  <tr>
    <td colspan="4" class="dimensione15 altezza32"></td>
  </tr>
  <tr id="codicePratica">
    <td><bean:message key="label.main.codicePratica"/></td>
    <td colspan="2"><html:text name="pplProcess" property="data.codicePratica" size="41" maxlength="100" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr id="paramInCompilazione1">
    <td><bean:message key="label.main.data.crea"/></td>
    <td><bean:message key="label.main.data.dal"/>&nbsp;<html:text name="pplProcess" property="data.dataCreazioneDa"  size="11" maxlength="11" onblur="javascript:isValidDate(this,true);" />&nbsp;<bean:message key="label.main.data"/></td>
    <td><bean:message key="label.main.data.al"/>&nbsp;<html:text name="pplProcess" property="data.dataCreazioneA" size="11" maxlength="11" onblur="javascript:isValidDate(this,true);" />&nbsp;<bean:message key="label.main.data"/></td>
    <td>&nbsp;</td>
    						<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataCreazioneDa",
								button         :    "data.dataCreazioneDa"
								});
							</script>
							<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataCreazioneA",
								button         :    "data.dataCreazioneA"
								});
							</script>
  </tr>
  <tr id="paramInCompilazione2">
    <td><bean:message key="label.main.data.modifica"/></td>
    <td><bean:message key="label.main.data.dal"/>&nbsp;<html:text name="pplProcess" property="data.dataUltimaModificaDa" size="11" maxlength="11" onblur="javascript:isValidDate(this,true);"  />&nbsp;<bean:message key="label.main.data"/></td>
    <td><bean:message key="label.main.data.al"/>&nbsp;<html:text name="pplProcess" property="data.dataUltimaModificaA" size="11" maxlength="11" onblur="javascript:isValidDate(this,true);" />&nbsp;<bean:message key="label.main.data"/></td>
    <td><bean:message key="label.ordinamento"/>&nbsp;
    	<html:select property="data.ordinamentoPraticheInCompilazione">
    		<html:option value="<%=ProcessData.ORDINAMENTO_CRESCENTE %>"><bean:message key="label.ordinamento.crescente"/></html:option>
    		<html:option value="<%=ProcessData.ORDINAMENTO_DECRESCENTE %>"><bean:message key="label.ordinamento.decrescente"/></html:option>
    	</html:select>
    </td>
        					<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataUltimaModificaDa",
								button         :    "data.dataUltimaModificaDa"
								});
							</script>
							<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataUltimaModificaA",
								button         :    "data.dataUltimaModificaA"
								});
							</script>
  </tr>
  <tr id="paramInviate1">
    <td><bean:message key="label.main.data.invio"/></td>
    <td><bean:message key="label.main.data.dal"/>&nbsp;<html:text name="pplProcess" property="data.dataInvioDa" size="11" maxlength="11" onblur="javascript:isValidDate(this,true);"  />&nbsp;<bean:message key="label.main.data"/></td>
    <td><bean:message key="label.main.data.al"/>&nbsp;<html:text name="pplProcess" property="data.dataInvioA" size="11" maxlength="11" onblur="javascript:isValidDate(this,true);" />&nbsp;<bean:message key="label.main.data"/></td>
    <td><bean:message key="label.ordinamento"/>&nbsp;
    	<html:select property="data.ordinamentoPraticheInviate">
    		<html:option value="<%=ProcessData.ORDINAMENTO_CRESCENTE %>"><bean:message key="label.ordinamento.crescente"/></html:option>
    		<html:option value="<%=ProcessData.ORDINAMENTO_DECRESCENTE %>"><bean:message key="label.ordinamento.decrescente"/></html:option>
    	</html:select>
    </td>
        					<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataInvioDa",
								button         :    "data.dataInvioDa"
								});
							</script>
							<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataInvioA",
								button         :    "data.dataInvioA"
								});
							</script>
  </tr>  
  <tr id="paramNonInviate1">
    <td colspan="4"><bean:message key="label.ordinamento"/>&nbsp;
    	<html:select property="data.ordinamentoPraticheInviate">
    		<html:option value="<%=ProcessData.ORDINAMENTO_CRESCENTE %>"><bean:message key="label.ordinamento.crescente"/></html:option>
    		<html:option value="<%=ProcessData.ORDINAMENTO_DECRESCENTE %>"><bean:message key="label.ordinamento.decrescente"/></html:option>
    	</html:select>
    </td>
        					<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataInvioDa",
								button         :    "data.dataInvioDa"
								});
							</script>
							<script type='text/javascript'>
								Calendar.setup({
								inputField     :    "data.dataInvioA",
								button         :    "data.dataInvioA"
								});
							</script>
  </tr>  
  <tr id="paramInCompilazione3">
    <td><label for="pendingPayments"><bean:message key="label.lista.pagamentiPendenti"/></label></td>
    <td colspan="3"><html:checkbox styleId="pendingPayments" name="pplProcess" property="data.pendingPayments" /><html:hidden property="data.pendingPayments" value="false"/></td>
  </tr>
  <tr id="paramInCompilazione4">
    <td><label for="abortedPayments"><bean:message key="label.lista.pagamentiFalliti"/></label></td>
    <td colspan="3"><html:checkbox styleId="abortedPayments" name="pplProcess" property="data.failedPayments" /><html:hidden property="data.failedPayments" value="false"/></td>
  </tr>
  <tr id="oggetto"> 
    <td><bean:message key="label.lista.oggetto"/></td>
    <td colspan="3"><html:textarea name="pplProcess" property="data.oggetto" rows="2" cols="55" /></td>
  </tr>
  <!-- 
  <tr>
    <td>Numero pratiche per pagina</td>
    <td colspan="3">
    	<html:select name="pplProcess" property="data.numPratichePerPag" size="1" >
    		<html:option value="5">5</html:option>
            <html:option value="10">10</html:option>
            <html:option value="20">20</html:option>
            <html:option value="50">50</html:option>
        </html:select>
    </td>
  </tr>  
  -->
<tr><td colspan="4">&nbsp;</td></tr>
	<tr  id="footer">
		<td colspan="4">
	  	<a class="btn" id="clearForm" href="/people/loopBack.do?propertyName=clean"><bean:message key="label.main.cancella"/></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" class="btn" id="submitButton" value="Cerca&nbsp;&gt;&gt;" name="navigation.button.next"/>
    </td></tr>
  
</table>

  <script type="text/javascript">
  	var el = document.getElementById('inCompilazioneSel');
  	var tipologiaSelezionata = document.getElementById('tipologiaSelezionata');
	if (el.value=='0'){
		document.getElementById('paramInCompilazione1').style.display = '';
		document.getElementById('paramInCompilazione2').style.display = '';
		document.getElementById('paramInCompilazione3').style.display = '';
		document.getElementById('paramInCompilazione4').style.display = '';
		document.getElementById('paramNonInviate1').style.display = 'none';
		document.getElementById('codicePratica').style.display = '';
		document.getElementById('oggetto').style.display = '';
		document.getElementById('paramInviate1').style.display = 'none';
		document.getElementById('submitButton').value = 'Cerca >>';
		document.getElementById('clearForm').style.display = '';
	} else {
		if (tipologiaSelezionata.value == 'I') {
			document.getElementById('paramInCompilazione1').style.display = 'none';
			document.getElementById('paramInCompilazione2').style.display = 'none';
			document.getElementById('paramInCompilazione3').style.display = 'none';
			document.getElementById('paramInCompilazione4').style.display = 'none';
			document.getElementById('paramNonInviate1').style.display = 'none';
			document.getElementById('codicePratica').style.display = '';
			document.getElementById('oggetto').style.display = '';
			document.getElementById('paramInviate1').style.display = '';
			document.getElementById('submitButton').value = 'Cerca >>';
			document.getElementById('clearForm').style.display = '';
		}
		if (tipologiaSelezionata.value == 'E') {
			document.getElementById('paramInCompilazione1').style.display = 'none';
			document.getElementById('paramInCompilazione2').style.display = 'none';
			document.getElementById('paramInCompilazione3').style.display = 'none';
			document.getElementById('paramInCompilazione4').style.display = 'none';
			document.getElementById('codicePratica').style.display = 'none';
			document.getElementById('oggetto').style.display = 'none';
			document.getElementById('paramNonInviate1').style.display = '';
			document.getElementById('paramInviate1').style.display = 'none';
			document.getElementById('submitButton').value = 'Visualizza >>';
			document.getElementById('clearForm').style.display = 'none';
		}
	}
  </script>

  <html><body></body></html>
