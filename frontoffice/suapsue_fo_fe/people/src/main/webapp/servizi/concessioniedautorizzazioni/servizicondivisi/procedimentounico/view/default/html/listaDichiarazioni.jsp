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
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />


<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
}
%>

<html:xhtml/>
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

	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
		<tr>
			<td> 
				<bean:message key="mu.note.dichiarazioniDinamiche"/><br/><br/>
			</td>
		</tr>
	



		<!-- Versione accessibile -->
		<% 
		String propertyName = "";
		ProcessData dataForm = (ProcessData)pplProcess.getData();
		Iterator it = dataForm.getListaHref().keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			SezioneCompilabileBean scb = (SezioneCompilabileBean)dataForm.getListaHref().get(key);
			%>
			<tr>
				<% 
				propertyName = "renderHref.jsp&href="+scb.getHref();
				if (!scb.isComplete()) {
				%>
					<td align="justify"><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/frecciamenu_sx.gif" />&nbsp;
						<ppl:linkLoopback style="color:blue" property="<%=propertyName %>"><%=scb.getTitolo()%></ppl:linkLoopback>
					</td>
				<%} else { %>
					<td align="justify">
						<span style="color:green"> *&nbsp; 
							<ppl:linkLoopback property="<%=propertyName %>" style="color:green"><%=scb.getTitolo()%></ppl:linkLoopback>
						</span>
					</td>
				<%}%>
			</tr>
		<%} %>
		<tr>
			<td><br />
			</td>
		</tr>
		<tr>
			<td>
			<table style="border:1px solid #EAEAEA; padding: 5px; width:100%;">
				<tr>
					<td align="justify"><bean:message key="mu.note.dichiarazioniDinamicheFooter"/></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<br />

	<table align="center">
		<tr>
			<td>
			<ppl:linkLoopback accesskey="B" property="modelloUnico.jsp" styleClass="btn"><bean:message key="mu.note.dichiarazioniDinamicheBack"/></ppl:linkLoopback>
			</td>
		</tr>
	</table>
</logic:notEqual>
