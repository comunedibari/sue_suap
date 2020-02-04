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
<%@ page import="it.people.core.PeopleContext" %>
<%@ page import="it.people.util.PeopleProperties" %>
<%@ page import="it.people.City" %>
<%@ page import="it.people.PeopleConstants" %>
<%@ page import="it.people.util.IUserPanel" %>

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

	PeopleContext pplContext = PeopleContext.create(request);
	if (pplContext != null) {
		showLink = !pplContext.getUser().isAnonymous();			
		// Attenzione al fatto che di default hanno valori diversi
		logged = !pplContext.getUser().isAnonymous();		
	}

	// Definisce i valori per il pulsante di login/logoff	
	String loginLogoffMessageKey;
	if (logged) {
		loginLogoffMessageKey = ButtonKey.LOGOFF;
	} else {
		loginLogoffMessageKey = ButtonKey.LOGIN;
	}

	// Determina il codice comune
	String communeId = ((City) session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();

	boolean disabled = "true".equalsIgnoreCase(request.getParameter("disabled"));
	String homeUrl = PeopleProperties.HOMEPAGE_ADDRESS.getValueString(communeId);	
%>


<jsp:useBean id="navBarObject" scope="session" type="it.people.layout.NavigationBar" />

<html:xhtml />
<table class="box_nav" cellspacing="0">
    <tr>
   		<td class="box_nav_left_top"></td>    
		<td rowspan="2" class="path" >
			<% if (homeUrl != null) { %>
				<% if (!disabled) { %>
				    <html:submit property="<%=ButtonKey.HOME%>" styleClass="btnLink">
						<bean:message key="<%=ButtonKey.HOME%>"/>
				    </html:submit>
				<% } else { %>
					<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.HOME%>"/></span>
				<% } %>
				|
			<% } %>
			<% if (!disabled) { %>		
			  <!--  <html:submit property="<%=ButtonKey.SERVICE%>" styleClass="btnLink">
					<bean:message key="<%=ButtonKey.SERVICE%>"/>
			    </html:submit> -->
				<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.SERVICE%>"/></span>

			<% } else { %>
				<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.SERVICE%>"/></span>
			<% } %>
			|
			<%=navBarObject.getServiceDescription()%>
			&gt;
			<%=navBarObject.getCurrentActivityName()%>
			&gt;
			<%=navBarObject.getCurrentStepName()%>
		</td>
    		<td align="right" class="path" rowspan="2">
                        <ppl:userPanelLoopback processName="pplProcess" view="<%=IUserPanel.ViewTypes.bugSignalling.getViewName() %>" 
                            labelKey="label.userfunctionspanel.sendbug" altKey="" titleKey="" openinNewWindow="false" styleClass="btnLink" />
                        <ppl:userPanelLoopback processName="pplProcess" view="<%=IUserPanel.ViewTypes.sendSuggestion.getViewName() %>" 
                            labelKey="label.userfunctionspanel.sendsuggestion" altKey="" titleKey="" openinNewWindow="false" styleClass="btnLink" />
    			<% if (!disabled && showLink) { %>
				    <html:submit property="<%=ButtonKey.MY_PROCESS%>" styleClass="btnLink">
						<bean:message key="<%=ButtonKey.MY_PROCESS%>"/>
				    </html:submit>
				<% } else if (showLink){ %>
					<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.MY_PROCESS%>"/></span>
				<% } %>
    			<% if (!disabled) { %>
			  <!--  <html:submit property="<%=loginLogoffMessageKey%>" styleClass="btnLink">
				<bean:message key="<%=loginLogoffMessageKey%>"/>
			    </html:submit> -->
			<% } else { %>
				<!--<span class="btnLinkDisabled"><bean:message key="<%=loginLogoffMessageKey%>"/></span>-->
			<% } %>
			<!--
			<a href="changeLanguage.do">Test</a>
			    <html:submit property="header.button.language" styleClass="btnLink">
					Italiano
			    </html:submit>-->
			    <ppl:onLineHelpLoopback processName="pplProcess" openinNewWindow="false" 
			    	labelKey="label.onlinehelp" altKey="label.onlinehelp.alt" 
			    	titleKey="label.onlinehelp.title" 
			    	offLabelKey="label.onlinehelp.off" offAltKey="label.onlinehelp.off.alt" 
			    	offTitleKey="label.onlinehelp.off.title" 
			    	styleClass="btnLink" />
      		</td>
      		
  		<td class="box_nav_right_top"></td>      	
	</tr>	
	<tr>
   		<td class="box_nav_left_bottom"></td>    
  		<td class="box_nav_right_bottom"></td>      	
	</tr>
</table>
