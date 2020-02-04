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
<%@ page import="it.people.City" %>
<%@ page import="it.people.PeopleConstants" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<% 	
	// Determina il codice comune
	String communeIdNavBar = ((City) session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();	
	String homeUrl = PeopleProperties.HOMEPAGE_ADDRESS.getValueString(communeIdNavBar); 
%>
<jsp:useBean id="navBarObject" scope="session" type="it.people.layout.NavigationBar" />

<html:xhtml />
<table class="box_nav" cellspacing="0">
    <tr>
   		<td class="box_nav_left_top"></td>    
		<td rowspan="2" class="path" > 
			<% if (homeUrl != null) { %>
			<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.HOME%>"/></span>
			|
			<%}%>
			<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.SERVICE%>"/></span>
			|
			<%=navBarObject.getServiceDescription()%>
			&gt;
			<%=navBarObject.getCurrentActivityName()%>
			&gt;
			<%=navBarObject.getCurrentStepName()%>
		</td>
		<td align="right" class="path" rowspan="2">
			<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.MY_PROCESS%>"/></span>
			<span class="btnLinkDisabled"><bean:message key="<%=ButtonKey.LOGIN%>"/></span>
  		</td>
  		<td class="box_nav_right_top"></td>      	
	</tr>	
	<tr>
   		<td class="box_nav_left_bottom"></td>    
  		<td class="box_nav_right_bottom"></td>      	
	</tr>
</table>
