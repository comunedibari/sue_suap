<%@page
	import="it.wego.cross.webservices.cxf.interoperability.TipologiaEventoIntegrazione"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.people.layout.*"%>
<jsp:useBean id="pplProcess" scope="session"
	type="it.people.process.AbstractPplProcess" />
<%@ taglib uri="/WEB-INF/app.tld" prefix="app"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl"%>
<%@page
	import="it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData"%>
<%@ page
	import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
<%@page import="it.people.process.common.entity.Attachment"%>
<%@ page import="java.util.Set"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%@page
	import="it.wego.cross.webservices.cxf.interoperability.TipologiaEventoIntegrazione"%>
<script type="text/javascript"
	src="/people/servizi/praticheOnLine/visura/myPage/view/default/html/yetii.js"></script>

<link rel="stylesheet" type="text/css"
	href="/people/servizi/praticheOnLine/visura/myPage/view/default/css/bookmark.css" />
<html:xhtml />
<bean:define id="loopbackStartRicercaDeleganti"
	value="javascript:executeSubmit('loopBack.do?propertyName=startRicercaDeleganti')" />
<h1>Invia comunicazione / integrazione</h1>
<br />
<%
	ProcessData data = (ProcessData) pplProcess.getData();
	String base = request.getContextPath();
	String basePath = "/servizi/praticheOnLine/visura/myPage/";
	String htmlPath = basePath.concat("view/default/html");
%>

<%
	if (data.getMessaggioErrore().trim().length() != 0) {
%>
<div class="text_block" align="left">
	<ppl:errors />
	<%= data.getMessaggioErrore()%>
<%-- 	<bean:message key="label.invioEvento.messaggioErrore" /> --%>
</div>
<%
	}
%>
<table style="border: 1px solid #EAEAEA; padding: 5px; width: 96%;"
	summary="Upload allegati">
	<tr>
		<td>
			<table summary="Summary" /><tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td><b>Caricamento allegati</b></td>
				</tr>
				<tr>
					<td>Descrizione file</td>
					<td colspan="2"><input name="uploadDescription" type="text"
						value="Descrizione File" size=30/></td>
				</tr>
				<tr>
					<td colspan="4"><input type="hidden" name="uploadName" />
 						<html:file	styleId="nomeallegato" property="data.uploadFile" />  
						<ppl:commandUpload	styleClass="btn" property="data.allegati" indexed="">
 						</ppl:commandUpload> 
					</td>
				</tr>

				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
			</table> <br> Allegati da inviare
			<table style="border: 1px solid #EAEAEA; padding: 5px; width: 98%;">
				<tr>
					<td></td>
					<td></td>
				</tr>
				<%
					List<Attachment> allegatiSalvati = data.getAllegatiSalvati();
					if (allegatiSalvati != null && !allegatiSalvati.isEmpty()) {
						for (int e = 0; e < allegatiSalvati.size(); e++) {
							Attachment allegato = allegatiSalvati.get(e);
				%>
				<tr>
					<td>Allegato</td>
					<td><%=allegato.getName()%></td>
					<td><%=allegato.getDescrizione()%></td>
					<td><br /></td>
					<td>
						<div class="admin_sirac_accr_btn" align="right"
							style="margin-top: 0px;">
							<a
								href="/people/loopBack.do?propertyName=cancellaAllegato&amp;fileName=<%=allegato.getName()%>"
								title="Cancella Allegato"> <img
								src="/people/servizi/praticheOnLine/visura/myPage/img/delete.png"
								alt="cancella" />
							</a>
						</div>
					</td>
				</tr>

				<%
					}
					}
					//out.write(dataForm.getEventi().length);
				%>

			</table>

		</td>
	</tr>
<tr>
	<td><br/><br/></td>
</tr>

<tr>
		<%-- <td><bean:message key="label.tipologia.evento" />&nbsp; <html:select
				property="data.tipologiaEventoIntegrazioneSelez">tipologiaEventoIntegrazioneSelez
				<%
					List<TipologiaEventoIntegrazione> listaTipologiaEventi = data.getListaTipologiaEventi();
						if (listaTipologiaEventi != null && !listaTipologiaEventi.isEmpty()) {
							for (int e = 0; e < listaTipologiaEventi.size(); e++) {
								TipologiaEventoIntegrazione tipoEvento = listaTipologiaEventi.get(e);
				%>
				<html:option value="<%=tipoEvento.getCodiceEvento()%>"><%=tipoEvento.getDescrizioneEvento()%></html:option>
				<%
					}
						}
				%>
			</html:select></td> --%>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>

	<tr>
		<td>Oggetto della comunicazione/integrazione  
		<input name="descrizioneEvento" type="text"	value="Descrizione comunicazione" size = 50 /></td>
	</tr>
	
</table>

<table align="center">
	<tr>
		<td>
			<button
				href="/people/loopBack.do?propertyName=inviaEventoIntegrazione"
				title="inviaEventoIntegrazione">Invio comunicazione/integrazione</button>
		</td>
		<td><ppl:linkLoopback accesskey="B"
				property="dettaglioPratiche.jsp" styleClass="btn">
				<< Indietro&nbsp;
			</ppl:linkLoopback></td>


		<%-- <ppl:linkLoopback accesskey="B" property="inviaEventoIntegrazione"
				styleClass="btn">
				Invia Evento Integrazione
			</ppl:linkLoopback></td> --%>

	</tr>
</table>
