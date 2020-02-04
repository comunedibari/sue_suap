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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.people.process.common.entity.Attachment"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%@page import="it.people.util.ServiceParameters"%>

<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
	//bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	//bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
}
%>


<script type="text/javascript">
		function multipleSignClick() {		
			var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";		
			return multipleSignContent(content);		
		}
								
		function multipleSignContent(content) {	
			var ObjSign = new WebSign();
			/*
			var ret=ObjSign.SetCertificateFilter(FILTER_NON_REPUDIATION_ENABLED);
				if(ret==RETURN_ERROR_VALUE)
				{
					alert("Si e' verificato un errore:\n" + objSign.LastError());
					return false;
				}
			*/		
			if(ObjSign.Sign(content, CONTENT_TYPE_STRING, SIGN_MODE_ATTACHED) == RETURN_ERROR_VALUE) {		
				alert(ObjSign.LastError());
		        // Cerca di eseguire, se definita, la funzione SignContent_nextAction		
		        try {		
					SignContent_nextAction();		
		        } catch(e) {}		
		
			} else {		
				var signedData = document.getElementById("signedData");		
				signedData.value = ObjSign.ContentSigned;	
				executeSubmit("loopBack.do?propertyName=multipleSign");		
			}			
		}
		
		function avantiSenzaFirma() {		
			var content = "<html><body>" + document.getElementById("Content").innerHTML + "</body></html>";	
			return avantiSenzaFirma2(content);		
		}
								
		function avantiSenzaFirma2(content) {	
			var signedData = document.getElementById("signedData");		
			signedData.value = content;
			executeSubmit("loopBack.do?propertyName=avantiSenzaFirma");		
		}
</script>


<html:xhtml/>
<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>
<logic:notEmpty name="ERRORE_RIEPILOGO_FIRMA" scope="request">
<table style="border:2px dotted red; padding: 3px; width:96%;">
	<tr>
		<td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
		<bean:message key="error.firma_riepilogo"/>
		</td>
	</tr>
</table>
</logic:notEmpty>

<html:errors/>

<logic:notEqual value="true" name="pplProcess" property="data.internalError">
<logic:messagesPresent>
	<table style="border:2px dotted red; padding: 3px; width:96%;">
		<tr>
			<td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			<b><ppl:errors /></b>
			</td>
		</tr>
	</table>
	<br/>
</logic:messagesPresent>

<jsp:include page="webclock.jsp" flush="true" />

<noscript>
<%
	AbstractPplProcess pplProcess__ = (AbstractPplProcess) session.getAttribute("pplProcess");
	ProcessData dataForm__ = (ProcessData)pplProcess__.getData();
	if (dataForm__.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm__.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)){ 
%>
<table style="border:2px dotted red; padding: 3px; width:96%;">
	<tr>
		<td width="100%" align="center">
                    <bean:message key="warning.firma"/>
		</td>
	</tr>
</table>
<%} %>
</noscript>

	<%if (dataForm__.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm__.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)){ 
	%>
	<div id="Content" style="display: none"> 
		<logic:equal name="dynamicDocument" value="" scope="request">
			<table style="border:2px dotted red; padding: 3px; font-size:120%; width:96%;">
				<tr>	
					<td width="100%" align="center"><br /><br />
					<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
					<b>&nbsp;&nbsp;Attenzione&nbsp;&nbsp;</b>
					<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
					<br /><bean:message key="warning.firmaErroreDocumento"/><br /><br /><br />
					<ppl:linkLoopback  property="abortMultipleSign" propertyIndex="" styleClass="pulsanteFirma" title="Cliccando su questo pulsante tutte le eventuali firma saranno annullate" style="text-decoration: none;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="firma.bottoneIndietro"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ppl:linkLoopback>&nbsp;&nbsp;&nbsp;&nbsp;
					<br /><br /><br /></td>
				</tr>
			</table>
		</logic:equal>	
		<logic:notEqual name="dynamicDocument" value="" scope="request">
			<bean:write  name="pplProcess" property="data.datiTemporanei.htmlRiepilogo"  />
		</logic:notEqual>
	</div>
	<%} else{} { %>
	<div id="Content2"> 
		<logic:equal name="AcceptInvalid" value="true" scope="request">
			<table style="border:2px dotted red; padding: 3px; font-size:120%; width:100%;">
				<tr>	
					<td width="100%" align="center"><br />
					<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
					&nbsp;&nbsp;<bean:message key="warning.firmaAccetta"/><br /><br />
					</td>
				</tr>
			</table>
			<br />
		</logic:equal>
		<table width="100%" style="font-size: 90%;">
			<tr><td align="right"><b><%=((SportelloBean)request.getAttribute("sportello")).getDescrizioneSportello()%></b></td></tr>
			<tr><td align="right"><%=((SportelloBean)request.getAttribute("sportello")).getIndirizzo()%></td></tr>
			<tr><td align="right"><%=((SportelloBean)request.getAttribute("sportello")).getCap()%>&nbsp;<%=((SportelloBean)request.getAttribute("sportello")).getCitta()%></td></tr>
		</table>
		<br />
		<table width="100%" style="font-size: 90%;">
			<tr><td width="20%"  style="border-width:1px; padding:3px; border-style: solid;" ><bean:message key="firma.codiceDomanda"/></td>
			<td width="80%" style="border-width:1px; padding:3px; border-style: solid;"><b><bean:write name="pplProcess" property="data.identificatorePeople.identificatoreProcedimento" />
			<%if (dataForm__.getListaSportelli().size()>1) {%>/<%=((SportelloBean)request.getAttribute("sportello")).getIdx()%><%}%></b></td>
			</tr>
		</table>
		<br />
		<% if (((SportelloBean)request.getAttribute("sportello")).isFlgOggettoRiepilogo()) {
                if (((String)request.getAttribute("oggettoPratica"))!=null ) { %>
			<bean:message key="firma.oggettoTitolo"/>
			<%=((String)request.getAttribute("oggettoPratica")) %>
			<!-- ((String)request.getAttribute("oggettoPraticaTitoloHref")) -->
		<% }
                } %>
		<bean:message key="firma.procedimentiTitolo"/>
		<table width="100%" style="border-width: 1px; border-style: solid;" cellspacing="5" cellpadding="5">
			<tr><td><logic:iterate id="rowsId" name="listaProcedimenti">
						<bean:write name="rowsId" property="descrizione" /> (<bean:write name="rowsId" property="ente" />)
					</logic:iterate>
			</td></tr>
		</table>
		
		<br /><bean:message key="firma.anagraficaTitolo"/>
		<table width="100%" style="border-width: 1px; border-style: solid;" cellspacing="5" cellpadding="5">
			<logic:iterate id="rowsId" name="anagrafica" scope="request">
				<tr><td ><bean:write name="rowsId" filter="yes" /><br/></td></tr>
			</logic:iterate>
		</table>
		<br /><bean:message key="firma.fileAllegatiTitolo"/>
		<table width="100%" style="border-width: 1px; border-style: solid;" cellspacing="5" cellpadding="5">
			<logic:empty name="pplProcess" property="data.allegati"><tr><td ><bean:message key="firma.fileAllegatiNull"/></td></tr></logic:empty>
			<logic:notEmpty name="pplProcess" property="data.allegati">
				<%String param_ = application.getInitParameter("remoteAttachFile");
				  if (param_==null || param_.equalsIgnoreCase("false")){
					  %>
					   	<logic:iterate id="rowsId" name="pplProcess" property="data.allegati">
							<tr><td ><bean:write name="rowsId" property="name" /><br/></td></tr>
						</logic:iterate>
					  <%
				  } else {
					   for (Iterator iterator = dataForm__.getAllegati().iterator(); iterator.hasNext();) { 
					       Attachment attach = (Attachment) iterator.next();
					       String nome = attach.getName();
					       if (nome.indexOf("_")!=-1) {
						       nome = nome.substring(nome.indexOf("_")+1,nome.length());
					       }
					       %><tr><td ><%=nome %></td></tr>
					       <%
					   }
				  }
			    %> 

			</logic:notEmpty>
		</table>
		<%	ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
    		String param = params.get("checkboxPresaVisione"); 
    		if (param!=null && param.equalsIgnoreCase("TRUE")){
    	%>
		<br /><html:checkbox property="data.datiTemporanei.presaVisionePDF" styleId="presaVisionePratica"/><label for="presaVisionePratica"><bean:message key="firma.presaVisione"/></label>
		<%} %>
		<br />
	<%} %>
</div>
	<noscript>
	<%
		if (dataForm__.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm__.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)){ 
	%>
	<table style="border:2px dotted red; padding: 3px; width:96%;">
		<tr>
			<td width="100%" align="center">
				<bean:message key="warning.firma"/>
			</td>
		</tr>
	</table>
	<%} %>
	</noscript>
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td> 
				<%@include file="./multipleSign/genericFooterProgressBar.jsp"%>
			</td>
		</tr>
		<tr>	
			<td> 
				<%if (dataForm__.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm__.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label) && (request.getAttribute("dynamicDocument")==null || ((String)request.getAttribute("dynamicDocument")).equalsIgnoreCase(""))  ){} else { %>
				<%@include file="./multipleSign/genericFooter.jsp"%>
				<%} %>
			</td>	
		</tr>
		<tr>
			<td>
			<!-- 
				...
			-->
			</td>	
		</tr>
   </table>
	<input type="hidden" id="signedData" name="signedData" value="" />
	<input type="hidden" id="policyID" name="policyID" value="<%=it.people.security.Policy.XMLTEXT %>" />
	<input type="hidden" id="userID" name="userID" value='<%= session.getId() %>' />
</logic:notEqual>
