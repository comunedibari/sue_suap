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
<%@ page import="it.people.layout.*"%>
<%@ include file="taglib.jsp"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
<%@ page import="java.util.Set"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<html:xhtml/>
<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>

<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
}
String base = request.getContextPath();
%>
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
	
	<logic:notEmpty name="validitaError"  scope="request" >
		<table style="border:2px dotted red; padding: 3px; width:96%;">
			<tr>
				<td><br/><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
				<b><%=(String)request.getAttribute("validitaError") %></b><br/><br/>
				</td>
			</tr>
		</table>
		<br/>
	</logic:notEmpty>

	<jsp:include page="webclock.jsp" flush="true" />

	<h1><bean:message key="upload.allegati.titolo"/></h1>
	<br/>

	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" summary="Upload allegati">
		<tr>
		<td>
		<table summary="<bean:message key="upload.allegati.tableSummary"/>">
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4"><input name="uploadDescription" type="hidden"  readonly="readonly"  value="<%=(String)request.getSession().getAttribute("codiceDocumento")%>"/></td>
			</tr>
			<tr>
				<td colspan="4">
				<input type="hidden" name="uploadName" />
				<%
				ProcessData data = (ProcessData)pplProcess.getData();
				boolean stop = false;
				String cod = (String)request.getSession().getAttribute("codiceDocumento");
				Iterator it2 = data.getListaAllegati().keySet().iterator();
				while (it2.hasNext() && !stop){
					String key = (String) it2.next();
					AllegatoBean allB = (AllegatoBean)data.getListaAllegati().get(key);
					if (allB.getCodice()!=null && allB.getCodice().equalsIgnoreCase(cod)){ 
						stop=true;
					%>
						<b><%=allB.getTitolo() %></b> &nbsp;<html:file styleId="nomeallegato" property="data.uploadFile"/>
						<ppl:commandUpload styleClass="btn" property="data.allegati" indexed=""  >
							<bean:message key="upload.allegati.carica"/>
						</ppl:commandUpload>
					<%}
				}	
				%>
				<%if(!stop) { %>
					<bean:message key="upload.allegato.libero"/> &nbsp;<html:file styleId="nomeallegato" property="data.uploadFile"/>
						<ppl:commandUpload styleClass="btn" property="data.allegati" indexed=""  >
							<bean:message key="upload.allegati.carica"/>
						</ppl:commandUpload>
				<%} %>
				</td>
			</tr>

			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
		</td>
		</tr>
	</table>

	<table align="center">
	<tr>
		<td>
			<ppl:linkLoopback accesskey="B" property="uploadFile.jsp" styleClass="btn"><bean:message key="upload.allegati.ritorna"/></ppl:linkLoopback>
		</td>
	</tr>
</table>
	
</logic:notEqual>
