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
<%@ include file="taglib.jsp"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
<%@ page import="java.util.Set"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<html:xhtml/>
<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>

<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
}
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

	<h4 align="center"><bean:message key="upload.allegati.confermaCancellazione"/></h4>
	<br>
	<table style="width:100%;" >
		<tr>
			<td width="100%" align="center">

				<a class="btn" href="/people/loopBack.do?propertyName=uploadFile.jsp&amp;confirm=SI&amp;cod=<%=(String)request.getSession().getAttribute("codiceDocumento")%>"  style="text-decoration: none;">&nbsp;&nbsp;<b>SI</b>&nbsp;&nbsp;</a>
				&nbsp;&nbsp;
				<a class="btn" href="/people/loopBack.do?propertyName=uploadFile.jsp&amp;confirm=NO"  style="text-decoration: none;">&nbsp;&nbsp;<b>NO</b>&nbsp;&nbsp;</a>

	 		</td>
	 	</tr>
	 </table>
	<br>
	<br>
	<input name="uploadName" type="hidden" readonly="readonly"  value="<%=(String)request.getSession().getAttribute("codiceDocumento")%>"/>
</logic:notEqual>
