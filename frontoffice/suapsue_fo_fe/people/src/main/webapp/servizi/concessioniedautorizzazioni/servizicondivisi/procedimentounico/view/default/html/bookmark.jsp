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
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.Set"%>
<%@ page import="it.people.core.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>


<%
PplUser peopleUser = PeopleContext.create(request).getUser();
if ( peopleUser.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK) != null ) {
   if ((Boolean) peopleUser.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK)){
%>

<div id="creaBookmark" title="Creazione bookmark">
<center>
<table>
	<tr>
		<td><input type="button" class="btn"
			alt="Accessibilità: la chiave di accesso da tastiera è B"
			accesskey="B"
			onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp');"
			onkeypress="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp');"
			value="Crea Bookmark" />
		</td>
	</tr>
</table>
</center>
<br/>
</div>
<%
   }
}
%>
