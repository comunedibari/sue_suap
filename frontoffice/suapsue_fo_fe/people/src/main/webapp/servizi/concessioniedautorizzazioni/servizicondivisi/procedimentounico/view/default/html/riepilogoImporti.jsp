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
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ page import="it.people.util.status.PaymentStatusEnum" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="bottoniNascosti"  scope="request" type="java.util.ArrayList" />

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
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
	<br/>
</logic:messagesPresent>

<%
	if (pplProcess.getPaymentStatus() != null && pplProcess.getPaymentStatus().equals(PaymentStatusEnum.paymentPending)) {
%>
	<table style="border:2px dotted red; padding: 3px; width:96%;">
		<tr>
			<td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			<b><bean:message key="<%=pplProcess.getPaymentStatus().getAlertKey() %>" /></b>
			</td>
		</tr>
	</table>
<%
	}
%>

<jsp:include page="webclock.jsp" flush="true" />

	<br/>
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" summary="tabella per layout dei campi">
		<tr>
			<td colspan="5" align="center"> 

				<%
					if (pplProcess.getServiceError() != null && !pplProcess.getServiceError().isEmpty()) {
			
						bottoniNascosti.add(NavigatorHelper.BOTTONE_PAGAMENTO);
						
				%>
				<div style="padding:10px;font-weight:bold;font-size:110%;"><bean:message key="error.pagamentoDatiOneri"/></div>
				<%
						
					}
				%>
				
				<h4><bean:message key="riepilogopag.titolo" /></h4><br/>
			</td>
		</tr>
		<tr>
			<td colspan="5" align="center"> 
				<bean:message key="riepilogopag.subtitolo" />
			</td>
		</tr>
		<tr>
			<td width="3%"></td>
			<td width="24%"></td>
			<td width="23%"></td>
			<td width="19%"></td>
			<td width="31%"></td>
		</tr>
		<tr>
			<td rowspan="7" >&nbsp;</td>
			<th colspan="4"><div align="left"><bean:message key="message.testpagamento.titolosezione.riepilogoimporti"/></div></th>
		</tr>

		<tr>
			<td class="data"><strong><bean:message key="label.testpagamento.riepilogoimporti.importototale"/></strong></td>
			<td class="data"><div align="right"><strong><bean:write filter="false" name="pplProcess" property="data.riepilogoOneri.totale" format="#,##0.00"/></strong></div></td>		
		</tr>
	</table>
	<br/>

	<%
		if (pplProcess.getServiceError() == null || (pplProcess.getServiceError() != null && pplProcess.getServiceError().isEmpty())) {

	%>
		<div style="padding:10px;font-weight:bold;font-size:110%;"><bean:message key="portale.pagamento.msginvio"/></div>
	<%
			
		}
	%>
    <input type="hidden" id="back" name="back" value="back" />
</logic:notEqual>
