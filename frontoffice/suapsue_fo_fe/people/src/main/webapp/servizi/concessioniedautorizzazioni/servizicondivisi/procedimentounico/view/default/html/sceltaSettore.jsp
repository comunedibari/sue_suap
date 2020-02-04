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
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />

			<%
			ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
			ProcessData data = (ProcessData)pplProcess.getData();
			ArrayList listaFigli = (ArrayList)request.getAttribute("listaFigli");
			if (bottoniNascosti!=null){
				if (data.getSettoreScelto()==null || (listaFigli!=null && listaFigli.size()>0)) {
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
	<br/><h3><bean:message key="settore.titolo"/></h3>

	<logic:empty name="pplProcess" property="data.settoreScelto"> <!-- sono sulla radice dell'albero della scelta dei settori : non ho ancora selezionato niente   -->
	
		<logic:iterate  name="pplProcess" property="data.alberoSettori" id="var" type="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SettoreBean">
			<logic:empty name="var" property="codiceRamoPadre">
			<bean:define id="codRamo" name="var" property="codiceRamo" type="java.lang.String" ></bean:define>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="data.tmp"  styleId="<%=codRamo %>" idName="var" value="codiceRamo"  /><label for="<%=codRamo %>"><bean:write name="var" filter="false" property="descrizioneRamo"/></label>
			<logic:notEmpty name="var" property="cod_rif_normativa">
				<bean:define id="cod_rif" name="var" property="cod_rif_normativa" type="java.lang.String" ></bean:define>
				<ppl:linkLoopback propertyIndex="<%=cod_rif%>" property="normativa.jsp" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
			</logic:notEmpty>
			<br/>
			</logic:empty>
		</logic:iterate>
		<br/>
		
		<ppl:commandLoopback commandProperty="avanti" validate="true" styleClass="button_AeC" >
			<bean:message key="keyloopback.sceltasuccessiva"/>&nbsp;&#62;&#62;
		</ppl:commandLoopback>
		<br/><br/>
	</logic:empty> <!-- fine scelta radice albero settori -->
	
	
	
	<logic:notEmpty name="pplProcess" property="data.settoreScelto"> <!-- sto esplorando un ramo dell'albero dei settori -->
	
		<logic:notEmpty name="listaFigli"> <!-- NON sono arrivato ad un nodo foglia -->
			
		
			&nbsp;&nbsp;<b><bean:message key="settore.sottotitolo"/></b>
			<logic:iterate name="pplProcess" property="data.settoreScelto.stringPath" id="row">
				<font color="black"><i><bean:write name="row" filter="false"/></i></font><font color="blue"><b>&nbsp;&#62;</b></font>
			</logic:iterate>
			<font color="black"><i><bean:write name="pplProcess" filter="false" property="data.settoreScelto.descrizioneRamo"/></i></font>
			<br/><br/>
			<logic:iterate  name="listaFigli" id="var">
				<bean:define id="codRamo" name="var" property="codiceRamo" type="java.lang.String" ></bean:define>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio property="data.tmp" styleId="<%=codRamo %>" idName="var" value="codiceRamo"  /><label for="<%=codRamo %>"><bean:write name="var" filter="false" property="descrizioneRamo"/></label>
					<logic:notEmpty name="var" property="cod_rif_normativa">
						<bean:define id="cod_rif" name="var" property="cod_rif_normativa" type="java.lang.String" ></bean:define>
						<ppl:linkLoopback propertyIndex="<%=cod_rif%>" property="normativa.jsp" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
					</logic:notEmpty>
					<br/>
			</logic:iterate>
			<br/>
			<%if (data.getLivelloSceltaSettore()>data.getLivelloSceltaMinSettore()) { %>
			<ppl:commandLoopback commandProperty="indietro" styleClass="button_AeC" >
				&#60;&#60;&nbsp;<bean:message key="keyloopback.sceltaprecedente"/>
			</ppl:commandLoopback>
			<%} %>

			<ppl:commandLoopback commandProperty="avanti" validate="true" styleClass="button_AeC" >
				<bean:message key="keyloopback.sceltasuccessiva"/>&nbsp;&#62;&#62;
			</ppl:commandLoopback>
		</logic:notEmpty>
		
		
		<logic:empty name="listaFigli"> <!-- sono arrivato ad un nodo foglia -->
			&nbsp;&nbsp;<b><bean:message key="settore.sottotitolo"/></b>
			<logic:iterate name="pplProcess" property="data.settoreScelto.stringPath" id="row">
				<font color="black"><i><bean:write name="row" filter="false"/></i></font><font color="blue"><b>&nbsp;&#62;</b></font>
			</logic:iterate>
			<font color="black"><i><bean:write name="pplProcess" filter="false" property="data.settoreScelto.descrizione"/></i></font>
			<br/><br/>
			<table>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconAccept.gif" alt="scelta settore terminata" /></td>
					<td valign="bottom">&nbsp;<bean:message key="settore.fineSceltaSettoreInizio"/>&nbsp;<b><bean:write name="pplProcess" filter="false" property="data.settoreScelto.descrizione"/></b><bean:message key="settore.fineSceltaSettoreFine"/></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="settore.endBottom"/></td>
				</tr>
			</table>
			<br/><br/>
			<%if (data.getLivelloSceltaSettore()>data.getLivelloSceltaMinSettore()) { %>
			<ppl:commandLoopback commandProperty="indietro" styleClass="button_AeC"  >
				&#60;&#60;&nbsp;<bean:message key="keyloopback.sceltaprecedente"/>
			</ppl:commandLoopback>
			<%} %>
			<ppl:commandLoopback commandProperty="nextStep" validate="true" styleClass="button_AeC" >
				<bean:message key="keyloopback.stepSuccessivo"/>&nbsp;&#62;&#62;
			</ppl:commandLoopback>
			<br/>
		</logic:empty>
	</logic:notEmpty>
	<br/><br/><br/><br/><br/>
	<%@include file="bookmark.jsp"%>
</logic:notEqual>
