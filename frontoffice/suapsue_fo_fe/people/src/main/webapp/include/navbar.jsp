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
<%@ page import="it.people.layout.ButtonKey" %>
<%@ page import="it.people.util.PeopleProperties" %>
<%@ page import="it.people.core.PeopleContext" %>
<%@ page import="it.people.City" %>
<%@ page import="it.people.PeopleConstants" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
  
<%
	// se non trovo l'utente mostro comunque il link
	// la pagina delle pratiche on-line provvede poi
	// ad impedire l'accesso ai non autorizzati
	// (serve a far funzionare ad esempio la pagina index.jsp)
	boolean showLink = true;
	boolean logged = false;
	String myPage = request.getSession().getServletContext().getInitParameter("myPage");
	boolean myPageActive = (myPage!=null && myPage.equalsIgnoreCase("TRUE"));
	PeopleContext pplContext = PeopleContext.create(request);
	if (pplContext != null) {
		showLink = !pplContext.getUser().isAnonymous();			
		// Attenzione al fatto che di default hanno valori diversi
		logged = !pplContext.getUser().isAnonymous();
	}

	// Determina il codice comune
	String communeId = ((City) session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();
	
	boolean disabled = "true".equalsIgnoreCase(request.getParameter("disabled"));
	String homeUrl = PeopleProperties.HOMEPAGE_ADDRESS.getValueString(communeId);

	// Definisce i valori per il pulsante di login/logoff	
	String loginLogoffUrl;
	String loginLogoffMessageKey;
	if (logged) {
		loginLogoffUrl = "/people/logoffOutProcess.do";
		loginLogoffMessageKey = ButtonKey.LOGOFF;
	} else {
		loginLogoffUrl = "/people/framework/protected/login.jsp";
		loginLogoffMessageKey = ButtonKey.LOGIN;
	}
	
	String serviceUrl = PeopleProperties.SERVIZIPEOPLE_ADDRESS.getValueString(communeId);
	if (serviceUrl == null)
		serviceUrl = "/people";
%>

<html:xhtml />
<table class="box_nav" cellspacing="0">
    <tr>
   		<td class="box_nav_left_top"></td>    
		<td rowspan="2" class="path" > 
			<% if (homeUrl == null) { %>
				<% if (!disabled) { %>
					<a href="/people"><bean:message key="<%=ButtonKey.SERVICE%>"/></a>
				<% } else { %>
					<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.SERVICE%>"/></span>
				<% } %>						
			<% } else {%>
				<% if (!disabled) { %>
					<a href="<%=homeUrl%>"><bean:message key="<%=ButtonKey.HOME%>"/></a>&nbsp;|&nbsp;<a href="<%=serviceUrl%>"><bean:message key="<%=ButtonKey.SERVICE%>"/></a>
				<% } else { %>
					<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.HOME%>"/></span>|<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.SERVICE%>"/></span>
				<% } %>			
			<% } %>
		</td>
    		<td align="right" class="path" rowspan="2">
    		<% if (!disabled && showLink) { %>
    			<%if (myPageActive) { %>
	    		  <a href="/people/initProcess.do?processName=it.people.fsl.servizi.praticheOnLine.visura.myPage"><bean:message key="<%=ButtonKey.MY_PROCESS%>"/></a>&nbsp;<a href="<%=loginLogoffUrl%>"><bean:message key="<%=loginLogoffMessageKey%>"/></a>
	    		<% } else { %>      
	    		  <a href="/people/framework/protected/praticheOnLine.jsp"><bean:message key="<%=ButtonKey.MY_PROCESS%>"/></a>&nbsp;<a href="<%=loginLogoffUrl%>"><bean:message key="<%=loginLogoffMessageKey%>"/></a>	
	    		<% }%>
			<% } else if (!disabled && !showLink) { %>
				<a href="<%=loginLogoffUrl%>"><bean:message key="<%=loginLogoffMessageKey%>"/></a>
			<% } else { %>
				<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.MY_PROCESS%>"/></span>
				<span class="btnLinkDisabled"><bean:message key="<%=loginLogoffMessageKey%>"/></span>
			<% } %>
      		</td>
  		<td class="box_nav_right_top"></td>      	
	</tr>	
	<tr>
   		<td class="box_nav_left_bottom"></td>    
  		<td class="box_nav_right_bottom"></td>      	
	</tr>
</table>
