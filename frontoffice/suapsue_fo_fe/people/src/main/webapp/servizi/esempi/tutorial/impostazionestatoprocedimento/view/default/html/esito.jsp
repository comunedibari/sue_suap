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
<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	// Naconde il pulsante avanti/invia/firma
	ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
	bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);	
%>


<html:xhtml/>
<div class="title1">
	<h1> <bean:message key="label.esito.titolo" /></h1>
</div>
	
<div class="text_block">
	<bean:message key="label.esito.info" /><br />
	<ppl:errors/><br />
	<bean:message key="label.esito.risposta" /><br />
	<bean:write name="pplProcess" property="data.rispostaWebService" />
</div>
