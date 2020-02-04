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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="it.people.Activity,
		 it.people.core.CategoryManager,
		 it.people.core.PeopleContext,
		 it.people.error.*,
		 it.people.ActivityState,java.util.StringTokenizer"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="City" scope="session" type="it.people.City" />


<%
    //Label Comune
    comuneLabel = City.getLabel();
%>

<!-- firmaNonCompletata.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><bean:message key="label.windowTitle"/></title>
	<people:frameworkCss />
</head>
<body bgcolor="#c0c0c0">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	    <tr><td colspan="2" height="20px;" align="right" bgcolor="#B0A48D"><img src="/people/img/header/people.gif" alt="people" hspace="0" vspace="0" border="0"></td></tr>
	    <tr><td id="header" colspan="2"></td></tr>
		<tr>
			<td id="percorso" colspan="1" class="txtPercorso">
			<% 
	    		String backUrl= "/people/framework/comune/index.jsp?id=" + City.getKey(); 
	    		String peopleUrl = "/people/framework/index.jsp";
	    		
	    		%>
	    		<a href="<%=peopleUrl%>"><span class="txtPercorso">People &nbsp;&gt;&nbsp;</span></a>
	    		<%
	    		if(!City.getKey().equals("000000")){
	    		%><a href="<%=backUrl%>"><span class="txtPercorso"><%= comuneLabel %></span></a>
	    		<%}%>
	    		</td>
	    		</td><td id="percorso" class="txtPercorso" align="right">	
					<jsp:include page="/include/myProcessLink.jsp" />	    		
	    		</td>
		</tr>
        <tr>
			
			<td colspan="2" class="txtNormal" style="padding-left:23px;padding-top:23px;padding-right:23px;vertical-align:top;text-align:center">
<!-- Contenuti -------------------------------->
<h4>Pagina di Conferma</h4>
<p>
<h4>La pratica non è ancora pronta per essere inviata</h4>
</p>
Una terza persona deve completare uno o più step di firma
<p>
Per creare delle nuove pratiche cliccare <a href="<%= request.getContextPath() + "/framework/index.jsp %>">qui</a>.
</p>
<!-- fine contenuti -------------------------------->
			</td>
		</tr>

    </table>
</body>
</html:html>
