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
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ page import="it.people.IStep" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.people.core.PplUserData"%>
<%@ page import="java.util.Iterator" %>

<%@ page import="it.people.Activity,
				 it.people.ActivityState,java.util.StringTokenizer,
                 it.people.content.CategoryImpl,
                 it.people.core.PeopleContext,
                 it.people.core.CategoryManager,
                 it.people.error.errorMessage,
                 it.people.error.MessagesFactory,
                 it.people.City,
                 it.people.util.debug.Debugger,
                 it.people.layout.*"%>

<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="City" scope="session" type="it.people.City" />
<jsp:useBean id="CurrentStep" scope="request" type="it.people.Step" />

<!-- main.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><bean:message key="label.windowTitle" /></title>
	<people:frameworkCss />
	<people:serviceCss processName="pplProcess" />

	<%@ include file="signHeader.jsp" %>
	
	<script type="text/javascript" >
		// Inizializza la pagina di firma 
		// WebSignInitialize("./framework/sign/generic/default/html/");
		function mainInit() {
			document.forms[0].stampa.disabled = false;
			document.forms[0].firma2.disabled = false;
			document.forms[0].firmaOffLineDownload.disabled = false;
			document.forms[0].firmaOffLineUpload.disabled = false;
		}		
	</script>
</head>

<body onload="mainInit()">
	<table cellspacing="0"  width="100%">
	    <tr><td colspan="2">
    		<people:include rootPath="/framework/view/generic" 
    			nestedPath="/html" 
    			elementName="header.jsp" />
	    </td></tr>	
		<tr><td colspan="2">
			<%@include file="navbar.jsp"%>
		</td></tr>
        <tr>
        	<td class="menu"><%@include file="menu.jsp"%></td>
        	<td valign="top"  class="main">       
        	<html:errors/>		
				<%
					try {
						pageContext.include(CurrentStep.getJspPath());
					}
					catch (Exception ex) {ex.printStackTrace(System.out);}
				%>

				<%@ include file="signFooter.jsp" %>
			</td>
		</tr>
    </table>
</body>
</html:html>
