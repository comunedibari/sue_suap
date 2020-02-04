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
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />

<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
ProcessData data = (ProcessData)pplProcess.getData();
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
} 
%>

<!-- ...  -->
 
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
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" id="ttt">
		<logic:notEqual property="data.nomeBookmark" name="pplProcess" value="">
			<tr>
				<td>
				<center><b><bean:write filter="yes" name="pplProcess" property="data.nomeBookmark" /></b></center>
				<br />
				</td>
			</tr>
		</logic:notEqual>
		
		
		<logic:notEqual property="data.descBookmark" name="pplProcess" value="">
			<tr>
				<td>
					<p align="left"><bean:write filter="yes" name="pplProcess" property="data.descBookmark" /></p>
				</td>
			</tr>
			<logic:notEmpty name="dataScadenzaBandi" scope="request">
			<tr>
				<td>
					<p align="center">
					 <b><bean:message key="DEF.servizioScadenza"/>&nbsp;<%=(String)request.getAttribute("dataInizioBandi")%>&nbsp;al&nbsp;<%=(String)request.getAttribute("dataScadenzaBandi")%></b>
					</p>
				</td>
			</tr>
			</logic:notEmpty>
		</logic:notEqual>
		<logic:equal property="data.nomeBookmark" name="pplProcess" value="">
		<tr>
			<td>
				<p align="center"><b><i><bean:message key="DEF.titolo1"/></i></b></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><br/><bean:message key="DEF.desc1" /></p>
			</td>
		</tr>
		<!--  <tr>
			<td>
				<p align="center"><br/><b><bean:message key="DEF.titolo2" /></b></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><bean:message key="DEF.desc2" /></p>
			</td>
		</tr> -->
		</logic:equal>
		<tr>
			<td>
				<br/><div class="informativa"><bean:message key="testo.informativa" /></div>
			</td>
		</tr>
		<tr>
			<td align="center">
				<html:checkbox property="data.infomativaPrivacy" styleId="infomativaPrivacy" name="pplProcess" />
				<label for="infomativaPrivacy"><bean:message key="label.accettatecondizioniprivacy" /></label>
				<br/>
			</td>
		</tr>
		
	</table>
	
	<!-- INIZIO sezione inserita per permettere l'importazione dei bookmark da Ravenna -->
	<logic:notEmpty name="importRA">	
		<table width="100%"><tr><td align="center">
			<ppl:commandLoopback commandProperty="importRavenna" styleClass="btn"  >
				Import bookmark Ravenna
			</ppl:commandLoopback>
		</td></tr></table>
	</logic:notEmpty>
	<!-- FINE sezione inserita per permettere l'importazione dei bookmark da Ravenna -->
	
	<script type="text/javascript">
		try {
			document.getElementById('ttt').focus();
		} catch(e) {
			alert("");
		}		
	</script>	
	<noscript>
		<INPUT TYPE="hidden" NAME="javascript" VALUE="">
	</noscript>
	<br/>
	<!--  
	<%=System.getProperty("file.encoding")%><br/><%=request.getCharacterEncoding()%>
	  -->
</logic:notEqual>
	
