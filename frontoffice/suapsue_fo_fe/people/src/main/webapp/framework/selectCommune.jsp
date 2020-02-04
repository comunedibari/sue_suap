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
<%@ page import="it.people.core.*, it.people.content.*, it.people.filters.*, java.util.*"%>

<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String destinationURL = (String) request.getAttribute(CommuneFilter.INVOKED_URL_ATTRIBUTE_NAME);	
	if (destinationURL.indexOf("?") != -1)
		destinationURL += "&";
	else
		destinationURL += "?";
	List communi = (List) request.getAttribute("communi");
%>
<html:html xhtml="true">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><bean:message key="label.windowTitle"/></title>
    <people:frameworkCss/>
</head>
<body>
    <table class="selectCommune" width="99%">
        <tr><td valign="top">
            <h1><bean:message key="label.selectCommune.descrizione"/></h1>
            
            <br/><br/><br/>
            <a href="changeLanguage.do">It</a>
            <br/><br/><br/>
            
			<logic:iterate id="commune" name="communi">
				<a href="<%=destinationURL%>communeCode=<bean:write name="commune" property="key"/>&amp;selectingCommune=true">
					<p><bean:write name="commune" property="label"/></p>
				</a>
			</logic:iterate>		
            
            <% if (communi.isEmpty()) { %>
            	<bean:message key="label.selectCommune.nessunEnteConfigurato"/>
            <% } %>
        </td></tr>
    </table>
</body>    
</html:html>
