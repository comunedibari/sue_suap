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
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@page import="java.util.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" >
	<tr>
		<td width="100%" colspan="2" align="right"><b>Sportello: <bean:write filter="false" name="sportello" property="descrizioneSportello" scope="request"/></b></td>
	</tr>
	<tr>
		<td width="100%" colspan="2" align="right"><bean:write name="sportello" filter="false" property="indirizzo" scope="request"/></td>
	</tr>
	<tr>
		<td width="100%" colspan="2" align="right"><bean:write name="sportello" filter="false" property="cap" scope="request"/>&nbsp;<bean:write filter="false" name="sportello" property="citta" scope="request"/></td>
	</tr>
	<tr>
<%
/* Modifica INIT 04/04/2007 (1.3): tolta, in visualizzazione, la parte dell'email dopo il ; (usato per il proxyMail) */
SportelloBean sportelloAttuale = (SportelloBean) request.getAttribute("sportello");
String email = sportelloAttuale.getEmail();
String emailNew = "";
if(email != null){
	int pos = email.indexOf(';');
	emailNew = email;
	//System.out.print(email);
	if(pos != -1){
	    emailNew = email.substring(0, pos);
	}
}
%>	
	
	
		<td width="100%" colspan="2" align="right">eMail : <%=emailNew %></td>
	</tr>
	<tr>
		<td ><br/><b>Oggetto : &nbsp;&nbsp;&nbsp;ISTANZA/COMUNICAZIONE per</b><br/><br/></td>
	</tr>
	<tr>
		<td width="100%" colspan="2">
			<ul>
			<logic:iterate id="rowsTitoliProc" name="listaProcedimenti" scope="request">
				<li><b><bean:write name="rowsTitoliProc" property="descrizione" filter="false"/></b><br/><br/></li>
			</logic:iterate>
			</ul>
		</td>
	</tr>
	
	<tr>
		<td width="100%" colspan="2">
			<table cellpadding="2" cellspacing="0" width="100%" summary="." border="1">
				<tr>
					<td width="20%">Codice domanda</td>
					<td width="80%"><b><bean:write name="pplProcess" filter="false" property="data.identificatorePeople.identificatoreProcedimento"/>&#47;<bean:write name="sportello" property="idx" filter="false" scope="request"/></b></td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td width="100%" colspan="2">
			<table cellpadding="2" cellspacing="0" width="100%" summary="." border="1">
				<tr>
					<td width="100%"><b>spazio riservato all'Ufficio:</b><br/><br/><br/><br/><br/><br/></td>
				</tr>
			</table>
		</td>
	</tr>	

	<tr>
		<td width="100%" colspan="2">
			<table cellpadding="2" cellspacing="0" width="100%" summary="." border="1">
				<logic:iterate id="rowsAnagrafica" name="pplProcess" property="data.anagrafica.htmlHistory">
				<tr>
					<td width="100%"><bean:write name="rowsAnagrafica" filter="yes"/></td>
				</tr>
				</logic:iterate>
			</table>
		</td>
	</tr>	

	<tr>
		<td width="100%" colspan="2" align="center"></br><b>COMUNICA</b></td>
	</tr>
	<tr>
		<td width="100%" colspan="2">Ai sensi delle specifiche norme di riferimento: </td>
	</tr>
	
	<tr>
		<td width="100%" colspan="2">
			<table cellpadding="2" cellspacing="0" width="100%" summary="." border="1">
				<tr>
					<td width="22%" align="center"><b><br/><h6>Istanza/Comunicazione</h6><br/></b></td>
					<td width="28%" align="center"><b><br/><h6>Oggetto</h6><br/></b></td>
					<td width="8%" align="center"><b><br/><h6>Ente competente</h6><br/></b></td>
					<td width="42%" align="center"><b><br/><h6>Normative di riferimento dell'attività amministrativa</h6><br/></b></td>
				</tr>
				<logic:iterate id="rowsIdProc" name="listaProcedimenti" scope="request">
				<tr>
					<td><bean:write name="rowsIdProc" filter="false" property="descrizione"/></td>
					<td>
						<ul>
						<logic:iterate id="rowsIdInterv" name="rowsIdProc" property="listaIntervento">
							<li><bean:write name="rowsIdInterv" filter="false"/></li>
						</logic:iterate>
						</ul>
					</td>
					<td><bean:write name="rowsIdProc" property="ente" filter="false"/></td>
					<td>
						<ul>
						<logic:iterate id="rowsIdNormative" name="rowsIdProc" property="listaTmp">
							<li><b><bean:write name="rowsIdNormative" filter="false" property="nomeRiferimento"/></b>&nbsp;<bean:write name="rowsIdNormative" filter="false" property="titoloRiferimento"/></li>
						</logic:iterate>
						</ul>
					</td>
				</tr>
				</logic:iterate>
			</table>
		</td>
	</tr>
	
	<tr>
		<td width="100%" colspan="2">consapevole che le dichiarazioni false, la falsità negli atti e l'uso di atti falsi comportano l'applicazione delle sanzioni penali previste dall'art. 76 del D.P.R. 445/2000 e la decadenza dai benefici eventualmente conseguenti al provvedimento emanato sulla base della dichiarazione non veritiera<br/></td>
	</tr>
	<tr>
		<td width="100%" colspan="2" align="center"><b>DICHIARA<br/></b></td>
	</tr>

	<logic:iterate id="rowsIdHref" name="listaHref" scope="request">
	<tr>
		<td><bean:write name="rowsIdHref" filter="yes"/></td>
	</tr>
	</logic:iterate>
	
	
	<tr><td align="center" colspan="2"><b><bean:message key="mu.label.documenti.tit"/></b></td></tr>
	<tr><td><br/></td></tr>
	<tr>
		<td colspan="2">
			<table border="1" width="100%" cellpadding="2" cellspacing="0" summary=".">
				<tr>
					<th width="10%"><bean:message key="mu.label.documenti.ente"/></th>
					<th width="45%"><bean:message key="mu.label.allegati.istcom"/></th>
					<th width="45%"><bean:message key="mu.label.allegati.oggetto"/></th>
				</tr>
				<logic:iterate id="rowsIdDoc" name="listaProcedimenti" scope="request">
					<tr>
						<td>
							<bean:write name="rowsIdDoc" filter="false" property="ente"/>
						</td>
						<td>
							<bean:write name="rowsIdDoc" filter="false" property="descrizione"/>
						</td>
						<td>

							<logic:iterate id="rowsIdDoc2" name="rowsIdDoc" property="listaIntervento"> 	
								-&nbsp;<bean:write name="rowsIdDoc2" filter="false"/><br/>
							</logic:iterate>

						</td>
					</tr>
					<tr>
						<td><b><bean:message key="mu.label.allegati.copie"/></b></td>
						<td colspan="2"><b><bean:message key="mu.label.allegati.documento"/></b></td>
					</tr>
					<logic:iterate id="rowsIdAll2" name="rowsIdDoc" property="listaDocumenti" > 
					<tr>
						<td>
							<bean:write name="rowsIdAll2" filter="false" property="copie"/>
						</td>
						<td colspan="2">
							<bean:write name="rowsIdAll2" filter="false" property="titolo"/>
						</td>
					</tr>
					</logic:iterate>
					<tr>
						<td colspan="3" bgcolor="#E0E0E0">
							&nbsp;
						</td>
					</tr>
				</logic:iterate>
			</table>
		</td>
	</tr>
	

	<tr>
		<td width="100%" colspan="2" align="center"><br/><b>ONERI ANTICIPATI<br/></b></td>
	</tr>
	<tr>
		<td width="100%" colspan="2">
			<table cellpadding="2" cellspacing="0" width="100%" summary="." border="1">
				<logic:notEmpty name="pplProcess" property="data.oneriAnticipati">
				<logic:iterate id="rowsOneri" name="pplProcess" property="data.oneriAnticipati">
					<tr>
						<td width="10%"><bean:write name="rowsOneri" filter="false" property="codice"/></td>
						<td width="80%"><bean:write name="rowsOneri" filter="false" property="descrizione"/></td>
						<td width="10%" align="right"><bean:write name="rowsOneri" property="importo"  filter="false" format="0.00"/></td>
					</tr>
				</logic:iterate>
				<tr>
					<td colspan="2" align="right"><b>Totale</b></td>
					<td align="right"><bean:write name="pplProcess" filter="false" property="data.riepilogoOneri.totale"/></td>
				</tr>
				</logic:notEmpty>
				<logic:empty name="pplProcess" property="data.oneriAnticipati">
					<tr>
						<td colspan="3">&nbsp;&nbsp;&nbsp;Nessun onere anticipato</td>
					</tr>
				</logic:empty>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100%" colspan="2" align="center"><br/>&nbsp;</td>
	</tr>
	<tr><td colspan="3">
<!-- SEZIONE INFO PAGAMENTI -->
<logic:notEmpty name="pplProcess" property="data.esitoPagamento">
  <logic:notEqual name="pplProcess" property="data.esitoPagamento.esito" value="">	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:100%;" summary="tabella per layout dei campi">
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
<!-- 
			<tr>
				<td colspan="7"><strong>
				<bean:message key="message.esitopagamento.ilpagamentocon" />
				&nbsp;<bean:write  name="pplProcess" property="data.esitoPagamento.descrizioneSistemaPagamento" />
				&nbsp;-&nbsp;<bean:write  name="pplProcess" property="data.esitoPagamento.descrizioneCircuitoAutorizzativo" />
				&nbsp;<bean:message key="message.esitopagamento.esitosuccesso" />
				</strong></td>
			</tr>
			<tr>
				<td colspan="7">&nbsp;</td>
			</tr>
-->
			<tr>
				<td><bean:message key="label.esitopagamento.numeroautorizzazione"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.IDOrdine" ignore="true" /></td>
			</tr>
			<tr>
				<td><bean:message key="label.esitopagamento.dataautorizzazione"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.dataAutorizzazione" format="dd/MM/yyyy" /></td>
			</tr>
			<tr>
				<td><bean:message key="esitopag.sistemapagamento"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.esitoPagamento.descrizioneSistemaPagamento" /></td>
			</tr>
<!-- 
			<tr>
				<td><bean:message key="esitopag.circuitoaut"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" property="data.esitoPagamento.descrizioneCircuitoAutorizzativo" /></td>
			</tr>
			<tr>
				<td><bean:message key="esitopag.circuitoselezionato"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" property="data.esitoPagamento.descrizioneCircuitoSelezionato" /></td>
			</tr>
-->
			<tr>
				<td><bean:message key="esitopag.esito"/></td>
				<td colspan="6">&nbsp;<bean:message key="esitopag.esitoOK"/></td>
			</tr>
			<tr>
				<td><bean:message key="label.testpagamento.esitopagamento.importocommissionipagamento"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.datiTemporanei.totalePagatoCommissioni" format="#,#00.00" ignore="true" />&nbsp;Euro</td>
			</tr>
			<tr>
				<td><bean:message key="label.testpagamento.esitopagamento.totalepagato"/></td>
				<td colspan="6">&nbsp;<bean:write name="pplProcess" filter="false" property="data.datiTemporanei.totalePagato" format="#,#00.00" ignore="true" />&nbsp;Euro</td>
			</tr>
		</logic:equal>
		<logic:equal name="pplProcess" property="data.esitoPagamento.esito" value="OP">
			<tr>
				<td colspan="7">&nbsp;<strong>
				<bean:message key="message.esitopagamento.ilpagamentocon" />
				&nbsp;<bean:write  name="pplProcess" filter="false" property="data.esitoPagamento.descrizioneSistemaPagamento" />
				&nbsp;-<bean:write  name="pplProcess" filter="false" property="data.esitoPagamento.descrizioneCircuitoAutorizzativo" />
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


	</table>
  </logic:notEqual>	
</logic:notEmpty>			
<!-- fine sezione info pagamenti -->
	</td>
	</tr>
	
	
	
	<tr>
		<td width="100%" colspan="2">
			<table cellpadding="2" cellspacing="0" width="100%" summary="." border="1">
				<tr>
					<td width="100%"><b>Data presentazione</b>________________________</td>
				</tr>
				<tr>
					<td width="100%"><b>Il Richiedente</b>________________________</td>
				</tr>
			</table>
		</td>
	</tr>
	
</table>
