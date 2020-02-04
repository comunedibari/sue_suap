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

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />

<html:xhtml/>
<br/><br/>

<logic:present name="bookmarkNonDisponibile" scope="request">
	<table style="border:2px dotted red; padding: 3px; width:96%;">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center"><br/><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			<b>&nbsp;&nbsp;&nbsp;Questo servizio non è al momento disponibile. Il servizio è aperto dal <%=(String)request.getAttribute("dataInvioValidita")%> al <%=(String)request.getAttribute("dataFineValidita")%></b>
			&nbsp;&nbsp;&nbsp;<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>
	<br/>
</logic:present>

<logic:present name="bookmarkAccessListLock" scope="request">
	<table style="border:2px dotted red; padding: 3px; width:96%;">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center"><br/><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			<b>&nbsp;&nbsp;&nbsp;<bean:message key="mu.label.accesslistKO" /></b>
			&nbsp;&nbsp;&nbsp;<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
	</table>
	<br/>
</logic:present>
