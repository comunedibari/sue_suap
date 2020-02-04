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
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>
<%@ page import="it.people.util.PeopleProperties"%>
<%@ page import="it.people.City" %>
<%@ page import="it.people.PeopleConstants" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
<html:xhtml/>
<%--
<script type="text/javascript">
function doMultipleSend(){
	document.getElementById('bottone').style.display = 'none';
	document.getElementById('progress').style.display = '';
	// Modifica Ferretti 13/03/2007 (versione 1.2.3): Aggiunto timeout per visualizzare la gif animata
	var waitFlag = true;
	//executeSubmit('loopBack.do');
	//var p = window.setTimeout("executeSubmit('loopBack.do')", 5 * 1000); 
}
</script>
--%>
<script type="text/javascript">//<![CDATA[
function doMultipleSend(){
        document.getElementById('bottone').style.display = 'none';
        document.getElementById('progress').style.display = '';
        // Modifica Ferretti 13/03/2007 (versione 1.2.3): Aggiunto timeout per visualizzare la gif animata
        var waitFlag = true;
        //                 //executeSubmit('loopBack.do');
        //                         //var p = window.setTimeout("executeSubmit('loopBack.do')", 5 * 1000); 
        $.ajax({ async:false, url:'../servizioMonitoraggioPratiche.jsp?action=confermaInvioPratica&idPratica=<%= pplProcess.getIdentificatoreProcedimento() %>' });
        }
        //]]>
       </script>
	<%  
//	 Determina il codice comune
		String communeId = ((City) session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();

		String linkNuovePratiche = PeopleProperties.SERVIZIPEOPLE_ADDRESS.getValueString(communeId);
		if (linkNuovePratiche == null) {
			linkNuovePratiche = request.getContextPath();
		}
	%>
<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>
<logic:notEqual value="true" name="pplProcess" property="data.internalError">

<logic:messagesPresent>
	<table style="border:2px dotted red; padding: 3px; width:96%;">
		<tr>
			<td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			<b><ppl:errors /></b>
			</td>
		</tr>
	</table>
</logic:messagesPresent>

<jsp:include page="webclock.jsp" flush="true" />

	<% ProcessData dataForm = (ProcessData)pplProcess.getData(); 
		if (dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)){
	%>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
    		<tr>
				<td colspan="2" class="txtNormal" style="padding-left:23px;padding-top:23px;padding-right:23px;vertical-align:top;text-align:center">
					<h4><bean:message key="conferma.serviziocompletato" /></h4>
					<bean:message key="label.firmaCompletata.NuovePratiche"/>
					<a href="<%= linkNuovePratiche %>">
						<bean:message key="label.firmaCompletata.Link"/>
					</a>
					<br />
				</td>
			</tr>
		</table>
	
	
	<% } else { %>
	
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
			<td colspan="2" class="txtNormal" style="padding-left:23px;padding-top:23px;padding-right:23px;vertical-align:top;text-align:center">
				<h4><bean:message key="label.paginaInvio.titolo"/></h4>
				<bean:message key="label.paginaInvio.messaggio"/><br />
				<logic:notPresent name="pplPermission" scope="request">					
					<bean:message key="label.paginaInvio.messaggioConDelegato"/><br />
				</logic:notPresent>
				<br />
			 </td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td>
			<br />
			<p><span id="bottone"><ppl:linkLoopback   property="invio" propertyIndex=""  styleClass="pulsanteFirma" onclick="doMultipleSend()" style="text-decoration: none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Invia</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ppl:linkLoopback></span><br/></p>
			<br /><br />		
			</td>
		</tr> 
    </table>
	<table align="center">
		<tr><td><br/><br/><br/><center><span id="progress" style="display:none;font:normal 12px Arial;"><bean:message key="conferma.invioinorso" /></span></center></td></tr> 
    </table>
	
	<% } %>
	
</logic:notEqual>
