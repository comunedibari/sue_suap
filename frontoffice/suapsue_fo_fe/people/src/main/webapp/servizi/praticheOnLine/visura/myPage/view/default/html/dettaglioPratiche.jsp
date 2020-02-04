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

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@page import="it.wego.cross.webservices.cxf.interoperability.Evento"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>  
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@page import="java.util.*"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.ActivityBean"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData"%>
<%@page import="it.people.process.SubmittedProcessHistory"%>
<%@page import="it.people.util.DateFormatter"%>
<%@page import="it.people.process.SubmittedProcessState"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.TipologiaPraticheSelezionabili" %>
<%@page import="it.people.process.common.entity.UnsignedSummaryAttachment"%>
<%@page import="it.wego.cross.webservices.cxf.interoperability.Allegato"%>
<script type="text/javascript" src="/people/servizi/praticheOnLine/visura/myPage/view/default/html/yetii.js"></script>

<link rel="stylesheet" type="text/css" href="/people/servizi/praticheOnLine/visura/myPage/view/default/css/bookmark.css" />
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<html:xhtml/>
<% ProcessData dataForm = (ProcessData) pplProcess.getData(); 
String base = request.getContextPath();
String basePath = "/servizi/praticheOnLine/visura/myPage/";
String htmlPath=basePath.concat("view/default/html");
String erroriInvioValue = TipologiaPraticheSelezionabili.erroriInvio.getCodice();
String inCompilazioneValue = TipologiaPraticheSelezionabili.inCompilazione.getCodice();
String inviateValue = TipologiaPraticheSelezionabili.inviate.getCodice();
%>

<table style="padding: 5px; width:98%;">
	<tr><td>
		<logic:equal value="true" name="pplProcess" property="data.inCompilazione" >
			<bean:message key="subtitle.dettaglio.compilazione"/>
		</logic:equal>
		<logic:notEqual value="true" name="pplProcess" property="data.inCompilazione" >
			<logic:equal value="<%=inviateValue %>" name="pplProcess" property="data.tipologiaSelezionata">
				<bean:message key="subtitle.dettaglio.inviate"/>
			</logic:equal>
		</logic:notEqual>
	</td></tr>
</table>

<input name="numTab" id="numTab" type="hidden"  readonly="readonly"  value="1"/>
<div id="tabcont1" class="tabpanel">
	<ul id="tabcont1-nav" class="tabnav">
		<li><a href="#tab1"><span><bean:message key="label.dettaglio.titolo1" /></span></a></li>
		<li><a href="#tab2"><span><bean:message key="label.dettaglio.titolo2" /></span></a></li>
		<%if(dataForm.getEventi() != null && dataForm.getEventi().length > 0) {%>
			<li><a href="#tab4"><span><bean:message key="label.dettaglio.titolo3" /></span></a></li>
		<%} %>
		<%if (!dataForm.isInCompilazione() && dataForm.getDettaglioPratica().getCertificato()!=null && dataForm.getDettaglioPratica().getCertificato().size()>0  ){ %>
			<li><a href="#tab3"><span><bean:message key="label.dettaglio.certificati" /></span></a></li>
		<%} %>
	</ul>
	<div id="tab1" class="tab">
		<br />
		<table style="border:1px solid #EAEAEA; padding: 5px; width:98%;">
			<tr>
				<td class="dimensione15">&nbsp;</td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<tr>
			    <% String initQuery = "/initProcess.do?"
            		+ "processName=" + dataForm.getDettaglioPratica().getProcessName()
            		+ "&amp;processId=" + dataForm.getDettaglioPratica().getProcessDataID() + "&amp;initFromMyPage=true"; 
            	%>
				<td class="dimensione15"><bean:message key="label.dettaglio.pratica.codice" /> </td>
				<td class="dimensione85"><b><bean:write name="pplProcess" property="data.dettaglioPratica.processDataID" /></b>&nbsp;&nbsp;
				<%if (dataForm.getDettaglioPratica().getSubmitted_time()==null || dataForm.getDettaglioPratica().getSubmitted_time().equalsIgnoreCase("") ){
					if (dataForm.getDettaglioPratica().getStatus() != null) {
						if (dataForm.getDettaglioPratica().getStatus().startsWith("S_SIGN")) {
						%>
					<html:link page='<%=initQuery%>' ><img src="/people/servizi/praticheOnLine/visura/myPage/img/document-sign.png" title="Completamento firma ed invio" alt="Completamento firma ed invio" /></html:link>
						<%
						} else {
							%>
					<html:link page='<%=initQuery%>' ><img src="/people/servizi/praticheOnLine/visura/myPage/img/modifica.gif" title="Ripresa della compilazione" alt="Ripresa della compilazione" /></html:link>
							<%
						}
					} else {
				%>
					<html:link page='<%=initQuery%>' ><img src="/people/servizi/praticheOnLine/visura/myPage/img/modifica.gif" title="Ripresa della compilazione" alt="Ripresa della compilazione" /></html:link>
				<%}} %>
				<br /><br /></td>
			</tr>
			<tr>
				<td class="dimensione15"><bean:message key="label.dettaglio.pratica.oggetto" /> </td>
				<td class="dimensione85"><b>
					<logic:empty name="pplProcess" property="data.dettaglioPratica.oggetto">
						<bean:write name="pplProcess" property="data.dettaglioPratica.contentName" />
					</logic:empty>
					<logic:notEmpty name="pplProcess" property="data.dettaglioPratica.oggetto">
						(<bean:write name="pplProcess" property="data.dettaglioPratica.contentName" />) &nbsp;<bean:write name="pplProcess" property="data.dettaglioPratica.oggetto" />
					</logic:notEmpty>
				</b><br /><br /></td>
			</tr>
			<tr>
			<td class="dimensione15"><bean:message key="label.dettaglio.pratica.descrizione" /> </td>
				<td class="dimensione85"><b>
					<logic:empty name="pplProcess" property="data.dettaglioPratica.descrizione">
						--
					</logic:empty>
					<logic:notEmpty name="pplProcess" property="data.dettaglioPratica.descrizione">
						<bean:write name="pplProcess" property="data.dettaglioPratica.descrizione" filter="yes" />
					</logic:notEmpty>
				</b><br /><br /></td>
			</tr>
			<tr>
			<td class="dimensione15"><bean:message key="label.dettaglio.data.crea" /> </td>
				<td class="dimensione85"><b>
					<bean:write name="pplProcess" property="data.dettaglioPratica.creation_time" />
				</b><br /><br /></td>
			</tr>
			<logic:notEmpty name="pplProcess" property="data.dettaglioPratica.submitted_time">
			<tr>
				<td class="dimensione15"><bean:message key="label.dettaglio.data.invio" /> </td>
				<td class="dimensione85"><b>
					<bean:write name="pplProcess" property="data.dettaglioPratica.submitted_time" />
				</b><br /><br /></td>
			</tr>	
			</logic:notEmpty>
			<logic:empty name="pplProcess" property="data.dettaglioPratica.submitted_time">
			<tr>
				<td class="dimensione15"><bean:message key="label.dettaglio.data.modifica" /> </td>
				<td class="dimensione85"><b>
					<bean:write name="pplProcess" property="data.dettaglioPratica.last_modified_time" />
				</b><br /><br /></td>
			</tr>
			</logic:empty>
			<tr>
				<td class="dimensione15">&nbsp;</td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<tr>
				<td class="dimensione15"><b><bean:message key="label.dettaglio.richiedente" /></b></td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<tr>
				<td class="dimensione15"><bean:write name="pplProcess" property="data.dettaglioPratica.processDataPratica.profiloRichiedente.cognome" />&nbsp;<bean:write name="pplProcess" property="data.dettaglioPratica.processDataPratica.profiloRichiedente.nome" />&nbsp;(<bean:write name="pplProcess" property="data.dettaglioPratica.processDataPratica.profiloRichiedente.codiceFiscale" />)</td>
				<td class="dimensione85">&nbsp;<br /><br /></td>
			</tr>
			<logic:notEmpty name="pplProcess" property="data.dettaglioPratica.altriDichiaranti">
			<tr>
				<td class="dimensione15">&nbsp;</td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<tr>
				<td class="dimensione15"><b><bean:message key="label.dettaglio.altri" /></b></td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<% Iterator itDic = dataForm.getDettaglioPratica().getAltriDichiaranti().iterator();
				while (itDic.hasNext()) {
					it.people.fsl.servizi.oggetticondivisi.Titolare titolare = (it.people.fsl.servizi.oggetticondivisi.Titolare) itDic.next();
					if (titolare.getPersonaFisica()!=null) {
						%>
						<tr><td colspan="2"><%=titolare.getPersonaFisica().getCognome()%>&nbsp;<%=titolare.getPersonaFisica().getNome()%>&nbsp;(<%=titolare.getPersonaFisica().getCodiceFiscale()%>)</td></tr>
						<% 
					} else if (titolare.getPersonaGiuridica()!=null) {
						%>
						<tr><td colspan="2"><%=titolare.getPersonaGiuridica().getDenominazione()%>&nbsp;(<%=titolare.getPersonaGiuridica().getPartitaIVA()%>)</td></tr>
						<% 
					}
				}
			%>
			</logic:notEmpty>
			<tr>
				<td class="dimensione15">&nbsp;</td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<tr>
				<td class="dimensione15"><b><bean:message key="label.dettaglio.pratica.stato" /></b></td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/square_red.png" alt="." />&nbsp;<bean:message key="label.dettaglio.attivitaFO" /></td>
			</tr>
			
				<%if (dataForm.getDettaglioPratica().getSubmitted_time()==null || dataForm.getDettaglioPratica().getSubmitted_time().equalsIgnoreCase("") ){
				  	if(dataForm.getDettaglioPratica().getStatus().startsWith("S_SIGN")){ 
				%>
					<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<bean:message key="label.modulionline.InFaseDiFirma" />&nbsp;<html:link page='<%= "/abortSign.do?processName=" + dataForm.getDettaglioPratica().getProcessName()+ "&amp;processId=" + dataForm.getDettaglioPratica().getOid()%>' ><span class="txtNormal"><bean:message key="label.modulionline.AnnullaFirma" /></span></html:link></td></tr>
				<%  } else if(dataForm.getDettaglioPratica().getStatus().equals("S_EDIT")){%>
					<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<bean:message key="label.modulionline.InFaseDiModifica" /></td></tr>
					<%
						if (dataForm.getDettaglioPratica().getPaymentStatus() != null && !dataForm.getDettaglioPratica().getPaymentStatus().equalsIgnoreCase("")) {
					%>
					<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<%=dataForm.getDettaglioPratica().getPaymentStatus() %></td></tr>
					<%
						}
					%>
				<%} 
				}  	else {
					%><tr><td colspan="2"><%
					Iterator itt = dataForm.getDettaglioPratica().getActivityHistory().iterator();
					while (itt.hasNext()){
						  ActivityBean submittedHistory = (ActivityBean)itt.next();
						  String date = DateFormatter.format(submittedHistory.getTimestamp());
					%>	  
                      
                  		<% if (new Long(submittedHistory.getCodice()).compareTo(new Long(0)) == 0) {%>
                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<%=date%>&nbsp;<bean:message key="label.modulionline.PresoInCarico" />
                      <% } else if (new Long(submittedHistory.getCodice()).compareTo(new Long(1)) == 0) {%>
                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<%=date%>&nbsp;<bean:message key="label.modulionline.InviatoAEnte" />
                      	<% } else if (new Long(submittedHistory.getCodice()).compareTo(new Long(-1)) == 0) {%>
                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<%=date%>&nbsp;&raquo;&nbsp;....
                      	<%} %>
                  		<br />
                  	<%} %>
                 	<br />
                 	</td>
             		</tr>
                    <%
					if (dataForm.getDettaglioPratica().getInfoBO()!=null) {
						
						%>&nbsp;&nbsp;-&nbsp;<tr><td>&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/square_red.png" alt="." />&nbsp;<bean:message key="label.dettaglio.attivitaBO" /></td></tr><%
						Date d = new Date(dataForm.getDettaglioPratica().getInfoBO().getTimestamp_evento());
						String ss = DateFormatter.format(d);
		        		%>
		        		<tr><td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<%=ss%>&nbsp;&raquo;&nbsp;Procedimento preso in carico dall'ente destinatario del procedimento<br /></td></tr>
		        		<%
						if (dataForm.getDettaglioPratica().getDettaglioAttivitaBO()!=null && dataForm.getDettaglioPratica().getDettaglioAttivitaBO().length>0) {
							%><tr><td colspan="2"><%
							for (int ii = 0; ii<dataForm.getDettaglioPratica().getDettaglioAttivitaBO().length; ii++) {
								String dataAttivita = DateFormatter.format(dataForm.getDettaglioPratica().getDettaglioAttivitaBO()[ii].getDataAttivita().getTime());
								%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/freccia_dxR.jpg" alt="." />&nbsp;<%=dataAttivita%>&nbsp;&raquo;&nbsp;<%=dataForm.getDettaglioPratica().getDettaglioAttivitaBO()[ii].getTipoAttivita().getDescrizione()%><br/><%
							}
							%></td></tr><%
						}
					}
				}
				%>
		</table>
	</div>
	<div id="tab2" class="tab">
		<br />
		<table style="border:1px solid #EAEAEA; padding: 5px; width:98%;">
			<tr>
				<td class="dimensione15">&nbsp;</td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
					 <tr> 
						 <td colspan="2">
						 	<bean:message key="label.dettaglio.allegati.caricati" />
						 </td>
					</tr>
			<%
			Iterator itt = dataForm.getDettaglioPratica().getProcessDataPratica().getAllegati().iterator(); 
			  int i=0; 
			  while (itt.hasNext()) {
				  it.people.process.common.entity.Attachment att = (it.people.process.common.entity.Attachment) itt.next();
				  if (!((att instanceof it.people.process.common.entity.SignedSummaryAttachment)||(att instanceof UnsignedSummaryAttachment) )){
					String param_ = application.getInitParameter("remoteAttachFile");
				  	if (  (param_==null || param_.equalsIgnoreCase("false")) && (!att.getName().equalsIgnoreCase("riepilogo.html") ) && (!att.getName().equalsIgnoreCase("riepilogo.html.p7m") ) )   {
				  		i++;
				  		String dim = "";
				  		double d = Double.parseDouble(String.valueOf(att.getFileLenght()));
				  		d = d/1000;
				  		java.text.DecimalFormat df = new java.text.DecimalFormat();
				  		df.setMaximumFractionDigits(1);
				  		df.setMaximumIntegerDigits(10);
				  		dim = df.format(d);
				  	%>
				  	
					 <tr> 
						 <td colspan="2"><img src="/people/servizi/praticheOnLine/visura/myPage/img/attach.png" alt="File allegato alla pratica" />&nbsp;<b><%=att.getName() %></b>&nbsp;(<%=dim%> KByte) &nbsp;
							 <!-- img src="/people/servizi/praticheOnLine/visura/myPage/img/view.png" alt="visualizza allegato alla pratica" / -->
						 <br /><br /></td>
					 </tr>
					 <%
				  	}
				  }
			  }
			  %>
					 <tr> 
						 <td colspan="2"><br /><br />
						 	<bean:message key="label.dettaglio.allegati.generati" /><br /><br />
						 </td>
					</tr>
			  
			  <%
			itt = dataForm.getDettaglioPratica().getAltriAllegati().iterator();
			  int j=0; 
			  while (itt.hasNext()) {
				  it.people.process.common.entity.Attachment att = (it.people.process.common.entity.Attachment) itt.next();
				  		j++;
				  		String dim = "";
				  		double d = Double.parseDouble(String.valueOf(att.getFileLenght()));
				  		d = d/1000;
				  		java.text.DecimalFormat df = new java.text.DecimalFormat();
				  		df.setMaximumFractionDigits(1);
				  		df.setMaximumIntegerDigits(10);
				  		dim = df.format(d);
				  	%>
				  	
					 <tr> 
						 <td colspan="2">
						 	<img src="/people/servizi/praticheOnLine/visura/myPage/img/attach.png" alt="File allegato alla pratica" />
						 	&nbsp;<b><%=att.getName() %></b>&nbsp;(<%=dim%> KByte) &nbsp;
							 <!-- img src="/people/servizi/praticheOnLine/visura/myPage/img/view.png" alt="visualizza allegato alla pratica" / -->

								<a href='<%=base+htmlPath+"/openDoc.jsp?cod="%><%=att.getDescrizione() %>' target="_blank" title="download" >						
									<img src="/people/servizi/praticheOnLine/visura/myPage/img/download.png" alt="download certificato" />
								</a>

							 
						 <br /><br /></td>
					 </tr>
					 <%
			  }
			  if (i==0 && j == 0){
				  	%>
				  	
					 <tr> 
						 <td colspan="2"><bean:message key="label.dettaglio.file.nessuno" /><br /><br /></td>
					 </tr>
					 <%
			  }
			%>
		</table>
	</div>
	<%if(dataForm.getEventi() != null && dataForm.getEventi().length > 0) {%>
	<div id="tab4" class="tab">
	<table style="border:1px solid #EAEAEA; padding: 5px; width:98%;">
			<tr>
				<td class="dimensione15">&nbsp;</td>
				<td class="dimensione85">&nbsp;</td>
			</tr>
			<%  
				Evento[] eventi = dataForm.getEventi();
				if(eventi != null && eventi.length != 0){
 					for (int e=0; e < eventi.length; e++){
						Evento evento = eventi[e];
						String[] descrizione = evento.getDescrizioneEvento().split("-");
						Allegato[] allegati = evento.getAllegati();
			%>
						<tr>
							<td class="dimensione15">Comunicazione</td>
							<td class="dimensione85"><b><%=descrizione[0]%></b></td>
						</tr>
						<tr>
							<td class="dimensione15">Data Comunicazione</td>
							<td class="dimensione85"><%=DateFormatter.format(evento.getDataEvento().getTime())%></td>
						</tr>
						<tr>
							<td class="dimensione15">Fascicolo</td>
							<td class="dimensione85"><%=evento.getIdentificativoPratica()%></td>
						</tr>
						<tr>
							<td class="dimensione15">Data Protocollo</td>
							<td class="dimensione85"><%=(evento.getDataProtocollo() != null) ? DateFormatter.format(evento.getDataProtocollo().getTime()) : ""%></td>
						</tr>
						<tr>
							<td class="dimensione15">Protocollo</td>
							<td class="dimensione85"><%=(evento.getNumeroProtocollo() != null) ? evento.getNumeroProtocollo() : ""%></td>
						</tr>
						<tr>
							<td class="dimensione15">Oggetto comunicazione</td>
							<td class="dimensione85"><%=(descrizione.length >1) ? descrizione[1] : ""%></td>
						</tr>
						<tr>
							<td class="dimensione15">Allegati</td>
							<%String allegatiAll = "";
							for(int a=0; a < allegati.length; a++){
								Allegato allegato = allegati[a];
								allegatiAll += " / " + allegato.getNomeFile();
							}
							%>
							<td class="dimensione85"><%=allegatiAll%></td>
						</tr>
						<tr>
							<td class="dimensione15">&nbsp;</td>
							<td class="dimensione85">&nbsp;</td>
						</tr>
			<%
					} 
				}
				//out.write(dataForm.getEventi().length);
				
			%>

			</table>
			<%-- <a class="btn" href='<%=base+htmlPath+"/allega_interop.jsp"%>'  style="text-decoration: none;">&nbsp;&nbsp;<b>Carica Allegato</b>&nbsp;&nbsp;</a> --%>
			<%String link = "allega_interop.jsp";%> 
			<br />
				<ppl:linkLoopback accesskey="B" styleClass="btn" property="<%=link%>" >&nbsp;INVIA&nbsp;COMUNICAZIONE&nbsp;/&nbsp;INTEGRAZIONE</ppl:linkLoopback>
            <br />
	</div>		
	<%} %>
	<%if (!dataForm.isInCompilazione() && dataForm.getDettaglioPratica().getCertificato()!=null && dataForm.getDettaglioPratica().getCertificato().size()>0 ){ %>
		<div id="tab3" class="tab">
			<table style="border:1px solid #EAEAEA; padding: 5px; width:98%;">
				<tr>
					<td class="dimensione15">&nbsp;</td>
					<td class="dimensione85">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2"><b><bean:message key="label.dettaglio.certificati.rilasciati" /></b><br /><br /></td>
				</tr>
				<logic:empty name="pplProcess" property="data.dettaglioPratica.certificato">
					<tr>
						<td colspan="2">
							<bean:message key="label.dettaglio.certificati.nessuno" />
						</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="pplProcess" property="data.dettaglioPratica.certificato">
					<logic:iterate id="rowsCertificati" name="pplProcess" property="data.dettaglioPratica.certificato" >
						<tr>
							<td colspan="2">&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/bullet_red.png" alt="." /><bean:write name="rowsCertificati" property="name" />&nbsp;&nbsp;
								<a href='<%=base+htmlPath+"/openDoc.jsp?cod="%><bean:write name="rowsCertificati"  property="descrizione"/>' target="_blank" title="download" >						
									<img src="/people/servizi/praticheOnLine/visura/myPage/img/download.png" alt="download certificato" />
								</a>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		</div>
	<%} %>
	<!-- h5 align="right"><input type="submit" name="navigation.button.previous" value="&lt;&lt; Indietro" class="btn" /></h5 -->
	
	<div id="footer">
			<input type="submit" name="navigation.button.previous" value="&lt;&lt; Indietro" class="btn" />
    </div>
	<br /><br />
	
	<script type="text/javascript">
		var valTab =  document.getElementById('numTab').value;
		var tabber=new Yetii('tabcont1',valTab);
		tabber.init();
		
		

		// Get the modal
		var modal = document.getElementById("myModal");
		
		// Get the button that opens the modal
		var btn = document.getElementById("myBtn");
		
		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];
		
		// When the user clicks on the button, open the modal 
		btn.onclick = function() {
		  modal.style.display = "block";
		}
		
		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
		  modal.style.display = "none";
		}
		
		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
		  if (event.target == modal) {
		    modal.style.display = "none";
		  }
		}
		
	</script>
	
	
	
			<style type="text/css">
				/* The Modal (background) */
			.modal {
			  display: none; /* Hidden by default */
			  position: fixed; /* Stay in place */
			  z-index: 1; /* Sit on top */
			  left: 0;
			  top: 0;
			  width: 100%; /* Full width */
			  height: 100%; /* Full height */
			  overflow: auto; /* Enable scroll if needed */
			  background-color: rgb(0,0,0); /* Fallback color */
			  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
			}
			
			/* Modal Content/Box */
			.modal-content {
			  background-color: #fefefe;
			  margin: 15% auto; /* 15% from the top and centered */
			  padding: 20px;
			  border: 1px solid #888;
			  width: 80%; /* Could be more or less, depending on screen size */
			}
			
			/* The Close Button */
			.close {
			  color: #aaa;
			  float: right;
			  font-size: 28px;
			  font-weight: bold;
			}
			
			.close:hover,
			.close:focus {
			  color: black;
			  text-decoration: none;
			  cursor: pointer;
			}
			
			</style>





