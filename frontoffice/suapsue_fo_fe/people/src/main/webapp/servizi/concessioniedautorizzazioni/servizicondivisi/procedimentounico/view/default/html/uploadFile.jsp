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
<%@ page import="it.people.process.data.AbstractData"%>
<%@ page import="it.people.util.PeopleProperties" %>
<%@ include file="taglib.jsp"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
<%@ page import="it.people.sirac.core.SiracConstants"%>
<%@ page import="it.people.sirac.core.SiracHelper"%>
<%@ page import="it.gruppoinit.commons.Utilities"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.lang.Integer"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="it.people.process.common.entity.Attachment"%>

<html:xhtml/>
<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>
<%
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

<jsp:include page="webclock.jsp" flush="true" />

<h1><bean:message key="upload.allegati.titolo"/></h1>
<br />
<% 
	ProcessData dataForm = (ProcessData)pplProcess.getData();
    SportelloBean sportello = null;
    Set chiaviSettore = dataForm.getListaSportelli().keySet();
    boolean trovatoSportello = false;
    Iterator chiaviSettoreIterator = chiaviSettore.iterator();
    while (chiaviSettoreIterator.hasNext() && !trovatoSportello){
        String chiaveSettore = (String) chiaviSettoreIterator.next();
        if (sportello == null) {
            sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
            trovatoSportello = true;
        }
    }
%>
<% if (trovatoSportello) {
	
	int maxLength = (((AbstractData)dataForm).getAttachmentsMaximunSize());
	String attachmentsMaxTotalSize = "";
	if (sportello.getAttachmentsUploadMaximumValue() == AbstractData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
		attachmentsMaxTotalSize = "illimitata";
	} else if (sportello.getAttachmentsUploadMaximumValue() == AbstractData.PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE) {
		if (pplProcess.getCommune() != null) {
			maxLength = Integer.parseInt(PeopleProperties.ATTACHMENT_MAX_TOTAL_SIZE.getValueString(pplProcess.getCommune().getKey())) / 1024;
		} else {
			maxLength = Integer.parseInt(PeopleProperties.ATTACHMENT_MAX_TOTAL_SIZE.getValueString()) / 1024;
		}
		attachmentsMaxTotalSize = String.valueOf(maxLength) + "MB";
	} else {
		if (maxLength > 0) {
			if (sportello.getAttachmentsUploadUM().equalsIgnoreCase("KB")) {
				maxLength = (maxLength / 1024);
			} else if (sportello.getAttachmentsUploadUM().equalsIgnoreCase("MB")) {
				maxLength = (maxLength / (1024 * 1024));
			} 
		}
		attachmentsMaxTotalSize = String.valueOf(maxLength) + sportello.getAttachmentsUploadUM();
	}

	attachmentsMaxTotalSize = sportello.getAttachmentsUploadMaximumSize();
	
%>
<bean:message key="upload.allegati.maximumTotalSize" arg0=""/>&nbsp;<%=attachmentsMaxTotalSize %>
<br />
<% } %>
<% 
	String userID = (String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
	boolean isAnonymus = SiracHelper.isAnonymousUser(userID);
	if (isAnonymus && dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)){ %>
<table style="width:96%;">
	<tr>
		<td style="border:2px dotted red; padding: 3px;" align="center"><b><br/><bean:message key="upload.controlloDisabilitato" /><br/><br/></b>
		</td>
	</tr>
</table>
<%} %>

<table style="border:1px solid #EAEAEA;  width:96%;" summary="Modello di Domanda" cellspacing="0px" cellpadding="10px"  rules="rows" >
<!-- TODO intestazione tabella  -->
	<tr class="border:5px solid #EAEAEA;" >
		<th width="5%" align="center" ><b>Allegato Obbligatorio</b></th>
		<th width="5%" align="center" ><b>Firma obbligatoria</b></th>
		<th width="60%" align="center"><b>Descrizione allegato</b></th>
		<th width="7%" align="center" ><b>Tipologie file</b></th>
		<th width="5%" align="center" ><b>Num. massimo pagine</b></th>
		<th width="5%" align="center" ><b>Dimensione massima (KB)</b></th>
		<th width="13%" align="center"><b>&nbsp;</b></th>
		<th width="5%" align="center"><b>&nbsp;</b></th>
	</tr>


	<!-- LISTA MODULISTICA RICHIESTA -->
	<% 
		
		ProcessData data = (ProcessData)pplProcess.getData();
		Set s = data.getListaModulistica().keySet();
		for (java.util.Iterator iterator = s.iterator(); iterator.hasNext();) {
			boolean trovato = false;
			String codMod = (String) iterator.next();
			ModulisticaBean modulo = (ModulisticaBean)data.getListaModulistica().get(codMod);
			if (modulo.getNomeFile()!=null && (!modulo.getNomeFile().equalsIgnoreCase(""))){
				String nomeF = modulo.getNomeFile();
				String titolo = modulo.getTitolo();
				String codR = modulo.getCodiceDoc();

				boolean stop = false;
				Iterator it2 = data.getListaAllegati().keySet().iterator();
				String tipologie = "";
				String numPagMax = "";
				String dimMax = "";
				AllegatoBean allB = null;
				while (it2.hasNext() && !stop){
					String key = (String) it2.next();
					allB = (AllegatoBean)data.getListaAllegati().get(key);
					if (allB.getCodice()!=null && allB.getCodice().equalsIgnoreCase(codR)){
						stop=true;
						tipologie = Utilities.NVL(allB.getTipologieAllegati(),"-");
						numPagMax = Utilities.NVL(allB.getNum_max_pag(),"-");
						dimMax = Utilities.NVL(allB.getDimensione_max(),"-");
					}
				}







				
				String rowClass = "allegatoNonObbligatorio";
				if (allB != null && allB.isFlg_obb()) {
					rowClass = "allegatoObbligatorio";
				}				
				
	%>
	<tr class="<%=rowClass %>">
		<td width="5%" align="center">
			<%
						if (allB != null && allB.isFlg_obb()) {
			%>
							SI
						<%} else { %>
							NO
						<%					
						}
			%>
		</td>
		<td width="5%" align="center">
			<%
						if (allB != null && allB.isSignVerify()) {
			%>
							SI 
						<%} else { %>
							NO 

						<%					
						}
			%>
		</td>
		<td width="60%"><%=titolo %>&nbsp;&nbsp;
			<%if (codR!=null && codR.startsWith("PROC_SPEC_")) { %>
				<a href='<%=base+htmlPath+"/openProcuraSpeciale.jsp?cod_doc="+codR%>&amp;language=it&amp;nome_file=<%=nomeF%>' target="_blank" title="<%=nomeF%>" >
					<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/download.png" width="26" height="26" align="middle" alt="scarica il modello del documento" />
				</a>
			<%} else { %>
				<a href='<%=base+htmlPath+"/openDoc.jsp?cod_doc="+codR%>&amp;language=it&amp;nome_file=<%=nomeF%>' target="_blank" title="<%=nomeF%>" >
					<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/download.png" width="26" height="26" align="middle" alt="scarica il modello del documento" />
				</a>
			<%}%>
		</td>
		<td width="7%" align="center"><%=tipologie%></td>
		<td width="5%" align="center"><%=numPagMax%></td>
		<td width="5%" align="center"><%=dimMax%></td>
		<td width="13%" align="center" style="color:#ff7525;" nowrap="nowrap">
			<%
			
			Iterator it = data.getAllegati().iterator();
			String descAll = "";
			while(it.hasNext() && !trovato){
				Attachment all = (Attachment) it.next();
				if (all.getDescrizione().equalsIgnoreCase(codR)){
					trovato = true;
					descAll = all.getName();
				}
			}
			if (trovato) {
			%>
			<b><%=descAll %></b>
			<% } else { %>
				<a class="btn" href="/people/loopBack.do?propertyName=upload_documenti.jsp&amp;index=<%=codR%>"  style="text-decoration: none;">&nbsp;&nbsp;<b>Carica Allegato</b>&nbsp;&nbsp;</a>

			<% } %>
		</td>
		<td width="5%">
			<%if (trovato) { %>
				<a href='/people/loopBack.do?propertyName=delete_doc_confirm.jsp&amp;index=<%=codR%>' >
					<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/remove_all.png" width="16" height="16" align="middle" alt="elimina documento allegato" /></a>
			<%} %>
			&nbsp;
		</td>
	</tr>
	<% 		} // end if 
	
		} // end for
	%>
	
	
	
	<!-- LISTA DOCUMENTI RICHIESTI -->
	<% 

		Set s2 = data.getListaDocRichiesti().keySet();
		for (java.util.Iterator iterator = s2.iterator(); iterator.hasNext();) {
			boolean trovato = false;
			String codDoc = (String) iterator.next();
			DocumentoBean docRichiesto = (DocumentoBean)data.getListaDocRichiesti().get(codDoc);
			if (docRichiesto.getCodiceDoc()!=null && (!docRichiesto.getCodiceDoc().equalsIgnoreCase(""))){
				String titolo = docRichiesto.getTitolo();

				boolean stop = false;
				String tipologie = "";
				String numPagMax = "";
				String dimMax = "";
				Iterator it2 = data.getListaAllegati().keySet().iterator();
				AllegatoBean allB = null;
				while (it2.hasNext() && !stop){
					String key = (String) it2.next();
					allB = (AllegatoBean)data.getListaAllegati().get(key);
					if (allB.getCodice()!=null && allB.getCodice().equalsIgnoreCase(codDoc)){
						stop=true;
						tipologie = Utilities.NVL(allB.getTipologieAllegati(),"-");
						numPagMax = Utilities.NVL(allB.getNum_max_pag(),"-");
						dimMax = Utilities.NVL(allB.getDimensione_max(),"-");
					}
				}
				String rowClass = "allegatoNonObbligatorio";
				if (allB != null && allB.isFlg_obb()) {
					rowClass = "allegatoObbligatorio";
				}				
				
	%>
				<tr class="<%=rowClass %>">
					<td width="5%" align="center">
				<%
						if (allB != null && allB.isFlg_obb()) {
				%>
							SI
						<%} else { %>
							NO
						<%					
						}
				%>
					</td>
					<td width="5%" align="center">
				<%
						if (allB != null && allB.isSignVerify()) {
				%>
							SI
						<%} else { %>
							NO
						<%					
						}
				%>
					</td>
					<td width="60%" >
						<%=titolo %>
					</td>
					<td width="7%" align="center"><%=tipologie%></td>
					<td width="5%" align="center"><%=numPagMax%></td>
					<td width="5%" align="center"><%=dimMax%></td>
					<td width="13%" align="center" style="color:#ff7525;" nowrap="nowrap">
		<%
		
		Iterator it = data.getAllegati().iterator();
		String descAll = "";
		while(it.hasNext() && !trovato){
			Attachment all = (Attachment) it.next();
			if (all.getDescrizione().equalsIgnoreCase(codDoc)){
				trovato = true;
				descAll = all.getName();
			}
		}
		if (trovato) {
		%>
		<b><%=descAll %></b>
		<% } else { %>
			<a class="btn" href="/people/loopBack.do?propertyName=upload_documenti.jsp&amp;index=<%=codDoc%>"  style="text-decoration: none;">&nbsp;&nbsp;<b>Carica Allegato</b>&nbsp;&nbsp;</a>
		<% } %>
	</td>
	<td width="5%">
		<%if (trovato) { %>
			<a href='/people/loopBack.do?propertyName=delete_doc_confirm.jsp&amp;index=<%=codDoc%>'>
				<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/remove_all.png" width="16" height="16" align="middle" alt="rimuovi allegato" />
			</a>
		<%} %>
			&nbsp;
					</td>
				</tr>
	<%	
			}
		}
	%>
	
	<tr>
		<td colspan="8" valign="center">
			<h5><bean:message key="upload.allegati.all_libero"/>&nbsp;
				<a class="btn" href="/people/loopBack.do?propertyName=upload_documenti.jsp&amp;index=FREE"  style="text-decoration: none;">&nbsp;&nbsp;<b>QUI</b>&nbsp;&nbsp;</a>
			</h5>
			<table cellpadding="5px" >
			<% 
				Iterator iter = data.getAllegati().iterator();
				String descAll = "";
				while(iter.hasNext()) {
					Attachment attach = (Attachment) iter.next();
					if (attach.getDescrizione().indexOf("FREE_")!=-1){
			 %>
			 		<tr>
			 			<td>&nbsp;&nbsp;&nbsp;<%=attach.getName() %>
			 			</td>
			 			<td>
			 				<a href='/people/loopBack.do?propertyName=delete_doc_confirm.jsp&amp;index=<%=attach.getDescrizione()%>'>
								<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/remove_all.png" width="16" height="16" align="middle" alt="rimuovi allegato" />
							</a>
			 			</td>
			 		</tr>
			 <%		} 
				}
			 %>
			 </table>
		</td>
	</tr>
</table>
</logic:notEqual>

