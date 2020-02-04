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
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
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
	
	<jsp:include page="webclock.jsp" flush="true" />
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
	<tr>
		<td>
		<table border="0" cellspacing="1" cellpadding="2" width="100%">
			<tr>
				<td valign="middle">
				<table width="100%" border="1" cellspacing="0" cellpadding="4">
					<tr>
						<td><b><bean:message key="label.oneririep.comune" /> 
							<bean:write name="pplProcess" filter="false" property="data.comuneSelezionato.citta" /></b>
						</td>
					</tr>
					<tr>
						<td><bean:message key="label.oneririep.settore" />
							<b><bean:write name="pplProcess" filter="false" property="data.settoreScelto.descrizione" /></b>
						</td>
					</tr>
					<tr>
						<td><bean:message key="label.oneririep.riepilogo" />
						<ul>   
							<logic:notEmpty name="pplProcess" property="data.riepilogoOneri">
								<bean:define name="pplProcess" property="data.riepilogoOneri" id="oneri" />
								<logic:iterate id="bean" property="oneriBean" name="oneri">
									<li><bean:write name="bean" filter="false" property="descrizione" />&nbsp;:&nbsp;<bean:write name="bean" filter="false" format="0.00" property="importo" /></li>
								</logic:iterate>
							</logic:notEmpty>
							<logic:notEmpty name="pplProcess" property="data.oneriPosticipati">
								<logic:iterate name="pplProcess" property="data.oneriPosticipati" id="bean">
									<li><bean:write name="bean" filter="false" property="descrizione" />: <bean:message key="oneririep.posticipato" /></li>
								</logic:iterate>
							</logic:notEmpty>
							<li><b><bean:message key="label.oneririep.totale" />&nbsp;&euro;&nbsp;<bean:write name="pplProcess" format="0.00" filter="false" property="data.riepilogoOneri.totale" /></b></li>
						</ul>
						<br/>
						<bean:message key="label.oneririep.piede" /></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	<br/>
	<%@include file="bookmark.jsp"%>
</logic:notEqual>
