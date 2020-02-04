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
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBeanRows"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
ProcessData data = (ProcessData)pplProcess.getData();
if (bottoniNascosti!=null){
	if (!data.isFineSceltaOp()) {
		bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	}
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
	<jsp:include page="webclock.jsp" flush="true" />
	<br/><h3><bean:message key="operazione.titolo"/>&nbsp;<i><bean:write name="pplProcess" filter="false" property="data.settoreScelto.descrizione"/></i></h3>
	
	<logic:equal value="false" name="pplProcess" property="data.fineSceltaOp">
	
			<logic:equal value="1" name="pplProcess" property="data.livelloSceltaOp"> <!-- sono sulla radice dell'albero della scelta delle operazioni : non ho ancora selezionato niente   -->
				<table style="border:1px solid #EAEAEA;  width:96%; " >
				<% Iterator it = data.getAlberoOperazioni().iterator();
				   while(it.hasNext()) {
					   OperazioneBean operazione = (OperazioneBean)it.next();
					   if (operazione.getProfondita()==1) {
						   if (operazione.getRaggruppamentoCheck()==null || operazione.getRaggruppamentoCheck().equalsIgnoreCase("")) { // è un checkbox
							   if (operazione.getSino().equalsIgnoreCase("N")){
								   if (operazione.isSelezionato()) {
							%>			  
										  <tr>
										  <td width="6%">&nbsp;&nbsp;&nbsp;<input type='checkbox' name='<%=operazione.getCodiceOperazione()%>' id="<%=operazione.getCodiceOperazione()%>"  checked="checked" />&nbsp;&nbsp;</td>
										  <td width="94%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label>
										  <%if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
											    String link="normativa.jsp&index="+operazione.getCodRif();%> 
										  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
										  <%} %>		
										   </td></tr>
							<% 			   
									} else { %>
									   	  <tr>
										  <td width="6%">&nbsp;&nbsp;&nbsp;<input type='checkbox' name='<%=operazione.getCodiceOperazione()%>' id="<%=operazione.getCodiceOperazione()%>"  />&nbsp;&nbsp;</td>
										  <td width="94%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label>
										  <%if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
											    String link="normativa.jsp&index="+operazione.getCodRif();%> 
										  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
										  <%}%>	
										  </td></tr>
										  <%
									}
							   } else {
									%>
									<tr> <!-- RAVENNA -->
										<td colspan="2">
											<input type='radio' name='<%=operazione.getCodiceOperazione()%>' value='SI' <%=(operazione.getValueSiNo()!=null&&operazione.getValueSiNo().equalsIgnoreCase("SI"))?"checked='checked'":""%> />&nbsp;&nbsp;SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type='radio' name='<%=operazione.getCodiceOperazione()%>' value='NO' <%=(operazione.getValueSiNo()!=null&&operazione.getValueSiNo().equalsIgnoreCase("NO"))?"checked='checked'":""%> />&nbsp;&nbsp;NO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<%=operazione.getDescrizioneOperazione()%>
										</td>
									</tr>
								<%
							}
						   } else { // è un radiobutton
							   if (operazione.isSelezionato()) {
								   %>
									  <tr>
									  <td width="6%">&nbsp;&nbsp;&nbsp;<input type='radio' name='<%=operazione.getRaggruppamentoCheck()%>' id="<%=operazione.getCodiceOperazione()%>" value='<%=operazione.getCodiceOperazione()%>'  checked="checked" />&nbsp;&nbsp;</td>
									  <td width="94%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label> 
									  <%if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
										    String link="normativa.jsp&index="+operazione.getCodRif();%> 
									  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
									  <%} %>		
									   </td></tr>								   
								   <%
							   }  else {// fine radiobutton selezionato
								   %>
								   	  <tr>
									  <td width="6%">&nbsp;&nbsp;&nbsp;<input type='radio' id="<%=operazione.getCodiceOperazione()%>" name='<%=operazione.getRaggruppamentoCheck()%>' value='<%=operazione.getCodiceOperazione()%>'   />&nbsp;&nbsp;</td>
									  <td width="94%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label>
									  <%if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
										    String link="normativa.jsp&index="+operazione.getCodRif();%> 
									  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
									  <%}%>	
									  </td></tr>								   
								   <%
							   } // fine radiobutton NON selezioanto
						   }
					   }
				}
				%>

				
					<tr>
						<td colspan="2">
							<br/>
							<!-- PULSANTE AVANTI -->
							<ppl:commandLoopback commandProperty="avanti" validate="true" styleClass="button_AeC_operazioni" >
								<bean:message key="keyloopback.sceltaOperazionesuccessiva"/>&nbsp;&#62;&#62;
							</ppl:commandLoopback>
							<br/><br/>
						</td>
					</tr>
				</table>
			</logic:equal>
	
	
			<logic:notEqual value="1" name="pplProcess" property="data.livelloSceltaOp">
				<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
				
				<% //BaseBeanRows 
					ArrayList listaFigli = (ArrayList) request.getAttribute("listaFigli");
					Iterator iter = listaFigli.iterator();
					while (iter.hasNext()) {
						BaseBeanRows bbr = (BaseBeanRows) iter.next();
						%>
						<tr>	
							<td colspan="2"><br/><b><%=bbr.getDescrizione()%></b></td>
						</tr>
					<%	
						Iterator iter2 = bbr.getRows().iterator();
						while (iter2.hasNext()){
							OperazioneBean operazione = (OperazioneBean) iter2.next();
							if (operazione.getRaggruppamentoCheck()==null || operazione.getRaggruppamentoCheck().equalsIgnoreCase("")) { // è un checkbox
								if (operazione.getSino().equalsIgnoreCase("N")){
									if (operazione.isSelezionato()) {
										%>
										<tr>
											<td width="5%"><input type='checkbox' name='<%=operazione.getCodiceOperazione()%>'  id="<%=operazione.getCodiceOperazione()%>" checked="checked" />&nbsp;&nbsp;</td>
											<td width="95%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label>
											<%
												if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
										    	String link="normativa.jsp&index="+operazione.getCodRif();%> 
									  			<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
									  		<%}%>	
										</td></tr>
										<%
									} else {
										%>
										<tr>
											<td width="5%"><input type='checkbox' name='<%=operazione.getCodiceOperazione()%>' id="<%=operazione.getCodiceOperazione()%>"  />&nbsp;&nbsp;</td>
											<td width="95%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label>
											<%
												if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
										    	String link="normativa.jsp&index="+operazione.getCodRif();%> 
									  			<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
									  		<%}%>	
										 </td></tr>
									  <%
									}
								} else {
									%>
										<tr> <!-- RAVENNA -->
											<td colspan="2">
												<input type='radio' name='<%=operazione.getCodiceOperazione()%>' value='SI' <%=(operazione.getValueSiNo()!=null&&operazione.getValueSiNo().equalsIgnoreCase("SI"))?"checked='checked'":""%> />&nbsp;&nbsp;SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type='radio' name='<%=operazione.getCodiceOperazione()%>' value='NO' <%=(operazione.getValueSiNo()!=null&&operazione.getValueSiNo().equalsIgnoreCase("NO"))?"checked='checked'":""%> />&nbsp;&nbsp;NO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<%=operazione.getDescrizioneOperazione()%>
											</td>
										</tr>
									<%
								}
							} else { // fine check
								

								
								   if (operazione.isSelezionato()) {
									   %>
										  <tr>
										  <td width="6%"><input type='radio' name='<%=operazione.getRaggruppamentoCheck()%>' id="<%=operazione.getCodiceOperazione()%>" value='<%=operazione.getCodiceOperazione()%>'  checked="checked" />&nbsp;&nbsp;</td>
										  <td width="94%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label> 
										  <%if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
											    String link="normativa.jsp&index="+operazione.getCodRif();%> 
										  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
										  <%} %>		
										   </td></tr>								   
									   <%
								   }  else {// fine radiobutton selezionato
									   %>
									   	  <tr>
										  <td width="6%"><input type='radio' name='<%=operazione.getRaggruppamentoCheck()%>' id="<%=operazione.getCodiceOperazione()%>" value='<%=operazione.getCodiceOperazione()%>'   />&nbsp;&nbsp;</td>
										  <td width="94%" class="labelOperazioni"><label for="<%=operazione.getCodiceOperazione()%>"><%=operazione.getDescrizioneOperazione()%></label> 
										  <%if (operazione.getCodRif()!=null && !operazione.getCodRif().equalsIgnoreCase("")) { 
											    String link="normativa.jsp&index="+operazione.getCodRif();%> 
										  		<ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
										  <%}%>	
										  </td></tr>								   
									   <%
								   } // fine radiobutton NON selezioanto								
								
								
								
								
							}
						} // fine while interno
					} // fine while esterno
				%>
				
				
			
					<tr>
						<td colspan="2">
							<br/>
							<!-- PULSANTE INDIETRO -->
							<% ProcessData data2 = (ProcessData)pplProcess.getData(); 
							   if (data.getLivelloSceltaMinOp()==data2.getLivelloSceltaOp()) {
							%>
								<!-- non faccio niente (significa che avevo creato un bookmark a metà dell'albero delle operazioni e quindi non devo far vedere il tasto indietro -->
							<%
							   } else {
							%>
							<ppl:commandLoopback commandProperty="indietro" styleClass="button_AeC_operazioni" >
								&#60;&#60;&nbsp;<bean:message key="keyloopback.sceltaprecedente"/>
							</ppl:commandLoopback>
							<%
							   }
							%>
							<!-- PULSANTE AVANTI -->
							<ppl:commandLoopback commandProperty="avanti" validate="true" styleClass="button_AeC_operazioni" >
								<bean:message key="keyloopback.sceltaOperazionesuccessiva"/>&nbsp;&#62;&#62;
							</ppl:commandLoopback>
							<br/><br/>
						</td>
					</tr>
				</table>			
			</logic:notEqual>
	
	</logic:equal>
	

	<logic:equal value="true" name="pplProcess" property="data.fineSceltaOp">
		<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconAccept.gif" alt="scelta settore terminata" />
		<bean:message key="operazione.fineSceltaOperazioneInizio"/><br/>
		<br/>
		<ul>
		<logic:iterate id="rowsId" name="listaOperazioni" scope="request">
			<li><i><bean:write name="rowsId" filter="false" property="descrizioneOperazione"/>

                <%
	                PplUser peopleUserAdmin = PeopleContext.create(request).getUser();
                        if ( peopleUserAdmin.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK) != null ) {
                           if ((Boolean) peopleUserAdmin.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK)){
                %>
                &nbsp;(codice operazione <bean:write name="rowsId" filter="false" property="codice"/>)
			  <% } 
                        }%>
			
			</i></li>
		</logic:iterate>
		</ul>
		<br/>
		<bean:message key="operazione.fineSceltaOperazioneFine"/>
		<br/><br/>
		<!-- PULSANTE INDIETRO -->
		<% ProcessData data2 = (ProcessData)pplProcess.getData(); 
		   if (data.getLivelloSceltaMinOp()==data2.getLivelloSceltaOp()) {
		%>
			<!-- non faccio niente (significa che avevo creato un bookmark a metà dell'albero delle operazioni e quindi non devo far vedere il tasto indietro -->
		<%
		   } else {
		%>
		<ppl:commandLoopback commandProperty="indietro" styleClass="button_AeC_operazioni" >
			&#60;&#60;&nbsp;<bean:message key="keyloopback.sceltaprecedente"/>
		</ppl:commandLoopback>
		<%
			}
		%>
		<input type="submit" name="navigation.button.next" value='<bean:message key="keyloopback.stepSuccessivo"/>&nbsp;&#62;&#62;' class="button_AeC_operazioni" />
		<!-- 
		<ppl:commandLoopback commandProperty="nextStep"  styleClass="button_AeC_operazioni" >
			<bean:message key="keyloopback.stepSuccessivo"/>&nbsp;&#62;&#62;
		</ppl:commandLoopback>
		-->
	</logic:equal>
	<br/><br/>
	<%@include file="bookmark.jsp"%>
</logic:notEqual>
