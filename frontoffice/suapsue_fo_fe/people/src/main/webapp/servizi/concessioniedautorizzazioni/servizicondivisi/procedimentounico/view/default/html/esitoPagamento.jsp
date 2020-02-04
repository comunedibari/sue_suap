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
<%@page import="java.util.ArrayList"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>

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
	
	<jsp:include page="webclock.jsp" flush="true" />

	<%
	// Naconde il pulsante avanti/invia/firma
	ProcessData dataForm = (ProcessData)pplProcess.getData();
	if(!dataForm.getEsitoPagamento().getEsito().equalsIgnoreCase("OK")){
		ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
		bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	}
	%>
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" summary="tabella per layout dei campi">
		<tr>
			<td colspan="7" align="center"> 
				<h4><bean:message key="esitopag.titolo.esitopagamento" /></h4>
			</td>
		</tr>
		<tr>
			<td width="21%"></td>
			<td width="22%"></td>
			<td width="8%"></td>
			<td width="17"></td>
			<td width="22%"></td>
			<td width="8%"></td>
			<td width="2%"></td>
		</tr>
		<logic:empty name="pplProcess" property="data.esitoPagamento">
			<tr>
				<td colspan="5">&nbsp;<strong><bean:message key="message.esitopagamento.esitodubbio" /></strong></td>
			</tr>
		</logic:empty>
	
		<logic:notEmpty name="pplProcess" property="data.esitoPagamento">
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="OK">
			<tr>
				<td colspan="7"><strong>
				<bean:message key="message.esitopagamento.ilpagamentocon" />
				&nbsp;<bean:write  name="pplProcess" filter="false" property="data.esitoPagamento.descrizioneSistemaPagamento" />
				&nbsp;<bean:message key="message.esitopagamento.esitosuccesso" />
				</strong></td>
			</tr>
			<tr>
				<td colspan="7">&nbsp;</td>
			</tr>
			<tr>
				<td><bean:message key="label.esitopagamento.numeroautorizzazione"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.IDOrdine" ignore="true" /></td>
			</tr>
			<tr>
				<td><bean:message key="label.esitopagamento.dataautorizzazione"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.dataOrdine" format="dd/MM/yyyy" /></td>
			</tr>
			<tr>
				<td>Data transazione:</td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.dataTransazione" format="dd/MM/yyyy" /></td>
			</tr>
			<tr>
				<td><bean:message key="esitopag.sistemapagamento"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.descrizioneSistemaPagamento" /></td>
			</tr>
			<tr>
				<td><bean:message key="esitopag.esito"/></td>
				<td colspan="6">&nbsp;<bean:message key="esitopag.esitoOK"/></td>
			</tr>
			</logic:equal>
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="OP">
			<tr>
				<td colspan="7">&nbsp;<strong>
				<bean:message key="message.esitopagamento.ilpagamentocon" />
				&nbsp;<bean:write  name="pplProcess" property="data.esitoPagamento.descrizioneSistemaPagamento" filter="false"/>
				&nbsp;-<bean:write  name="pplProcess" property="data.esitoPagamento.descrizioneCircuitoAutorizzativo" filter="false"/>
				&nbsp;<bean:message key="message.esitopagamento.esitoinpgamento" />
				</strong></td>
			</tr>
			</logic:equal>
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="KO">
			<tr>
				<td colspan="7">&nbsp;<strong><bean:message key="message.esitopagamento.esitoko" /></strong></td>
			</tr>
			</logic:equal>
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="ER">
			<tr>
				<td colspan="7">&nbsp;<strong><bean:message key="message.esitopagamento.esitodubbio" /></strong></td>
			</tr>
			</logic:equal>
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="UK">
			<tr>
				<td colspan="7">&nbsp;<strong><bean:message key="message.esitopagamento.esitodubbio" /></strong></td>
			</tr>
			</logic:equal>
		</logic:notEmpty>


		<logic:notEmpty name="pplProcess" property="data.esitoPagamento">
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="OK">
			<tr><td colspan="7">
				<div style="text-align: left; margin-top: 15px;">
				<br/>
				<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" summary="tabella per layout dei campi">
					<caption></caption>
					<tr>
						<td width="70%" style="text-align: center;"></td>
						<td width="30%" style="text-align: center;"></td>
					</tr>
					<tr>
						<th style="font-size: 1.2em; text-align: center;"><bean:message key="message.literal.descrizione" /></th>
						<th style="font-size: 1.2em; text-align: center;"><bean:message key="message.literal.importo" /></th>
					</tr>
					<tr>
						<td style="font-size: 1.2em;">&nbsp;<bean:message key="label.testpagamento.esitopagamento.importocommissionipagamento"/></td>
						<td style="font-size: 1.2em;">
							<bean:write name="pplProcess" filter="false" property="data.datiTemporanei.totalePagatoCommissioni" format="#,#00.00" ignore="true" />&nbsp;Euro
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td style="font-size: 1.2em;">&nbsp;<strong><bean:message key="label.testpagamento.esitopagamento.totalepagato"/></strong></td>
						<td style="font-size: 1.2em;"><strong>
							<bean:write name="pplProcess" filter="false" property="data.datiTemporanei.totalePagato" format="#,#00.00" ignore="true" />&nbsp;Euro
						</strong></td>
					</tr>						
				</table>	
				</div>
			</td></tr>		
			</logic:equal>
		</logic:notEmpty>

		<logic:notEmpty name="pplProcess" property="data.esitoPagamento">
			<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="OK">
				<logic:present name="pplProcess" property="data.riepilogoOneriPagati">
					<logic:notEmpty name="pplProcess" property="data.riepilogoOneriPagati.oneri">
						<tr><td colspan="7">
							<div align="left">
							<br/>
							<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" summary="Riepilogo oneri">
								<caption style="font-size: 1.2em; border-top: 1px solid #EAEAEA; border-left: 1px solid #EAEAEA; border-right: 1px solid #EAEAEA; padding: 10px; font-weight: bolder;">Riepilogo oneri pagati</caption>
								<tr>
									<td width="70%" style="text-align: center;"></td>
									<td width="30%" style="text-align: center;"></td>
								</tr>
								<tr>
									<th style="font-size: 1.2em; text-align: center;">Descrizione</th>
									<th style="font-size: 1.2em; text-align: center;">Importo</th>
								</tr>
								<logic:iterate id="onerePagato" name="pplProcess" property="data.riepilogoOneriPagati.oneri">
									<tr>
										<td style="font-size: 1.2em;"><bean:write name="onerePagato" property="descrizione" filter="false" ignore="true" /></td>
										<td style="font-size: 1.2em;"><bean:write name="onerePagato" format="#,#00.00" property="importo" filter="false" ignore="true" />&nbsp;Euro</td>
									</tr>
								</logic:iterate>
							</table>	
							</div>
						</td></tr>		
					</logic:notEmpty>
				</logic:present>
			</logic:equal>
		</logic:notEmpty>
		
	</table>

	<br/>
</logic:notEqual>
