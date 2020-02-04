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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="taglib.jsp"%>
<script src="<%=htmlPath%>/tree/mktree.js" type="text/javascript"></script>
<link href="<%=htmlPath%>/tree/mktree.css" rel="stylesheet" type="text/css" />
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

<html:hidden property="data.datiTemporanei.checkBoxValueSelected" name="pplProcess" styleId="chkSelected" />
<html:hidden property="data.datiTemporanei.initAlberoOneri" name="pplProcess" value="true" />

<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">

	<tr>
		<td valign="top">
		<p><b><bean:message key="label.sceltaoneri.title" /> </b></p>
		</td>
	</tr>


	<tr>
		<td>

		<table border="0" cellspacing="1" cellpadding="2" width="100%">



			<tr>
				<td valign="middle"><%= (String) request.getAttribute("alberoOneri")%>
				</td>
			</tr>
			<tr>
				<td valign="middle"></td>
			</tr>

		</table>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>


</table>
<br/>
<%@include file="bookmark.jsp"%>
</logic:notEqual>
