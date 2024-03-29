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
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean"%>

<%@page import="java.util.*"%>
<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
<html:xhtml/>

<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>

<jsp:include page="webclock.jsp" flush="true" />

<% ProcessData data = (ProcessData)pplProcess.getData(); %>
<logic:notEqual value="true" name="pplProcess" property="data.internalError">
	<br/><h3><bean:message key="interventiFacoltativi.titolo"/></h3>
	<table>
	<% // ArrayList listaInterventiFac = (ArrayList) data.getInterventiFacoltativi(); 
		ArrayList listaInterventiFac = (ArrayList) request.getAttribute("interventiFacoltativi"); 
		if(listaInterventiFac != null) {
		Iterator it = listaInterventiFac.iterator();
		while (it.hasNext()) {
			InterventoBean interventoFac = (InterventoBean) it.next();
			if (interventoFac.isChecked()) {
				%>
				<tr>
					<td width="6%">&nbsp;&nbsp;&nbsp;<input type='checkbox' name='<%=interventoFac.getCodice()%>'  checked="checked" />&nbsp;&nbsp;</td>
					<td width="94%" class="labelOperazioni"><%=interventoFac.getTestoCondizione()%>
					<%if (interventoFac.getCodiceNormativaVisualizzata()!=null && !interventoFac.getCodiceNormativaVisualizzata().equalsIgnoreCase("")) { 
								    String link="normativa.jsp&index="+interventoFac.getCodiceNormativaVisualizzata();%> 
							  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
							  <%} %>
				    </td>
				</tr>
				<%
			} else {
				%>
				<tr>
					<td width="6%">&nbsp;&nbsp;&nbsp;<input type='checkbox' name='<%=interventoFac.getCodice()%>' />&nbsp;&nbsp;</td>
					<td width="94%" class="labelOperazioni"><%=interventoFac.getTestoCondizione()%>
					<%if (interventoFac.getCodiceNormativaVisualizzata()!=null && !interventoFac.getCodiceNormativaVisualizzata().equalsIgnoreCase("")) { 
								    String link="normativa.jsp&index="+interventoFac.getCodiceNormativaVisualizzata();%> 
							  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
							  <%} %>
				    </td>
				</tr>
				<%
			}
		}
		}
	%>
	
	

	</table>
	<br/>
	<%@include file="bookmark.jsp"%>
</logic:notEqual>
