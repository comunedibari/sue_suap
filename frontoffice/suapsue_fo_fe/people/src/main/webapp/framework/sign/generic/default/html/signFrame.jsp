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
<%@ page language="java" import="java.util.*" %>
<%@ page import="it.people.Activity" %>
<%@ page import="it.people.ActivityState" %>
<%@ page import="it.people.core.PeopleContext" %>
<%@ page import="it.people.error.errorMessage" %>
<%@ page import="it.people.error.MessagesFactory" %>
<%@ page import="it.people.City" %>
<%@ page import="it.people.util.debug.Debugger"%>

<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="City" scope="session" type="it.people.City" />
<jsp:useBean id="CurrentStep" scope="request" type="it.people.Step" />

<!-- signFrame.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><bean:message key="label.windowTitle"/></title>
	<people:frameworkCss/>
	<style type="text/css">
		@media print {
			input.screen {display:none;}
			hr.screen {display:none;}
		}
	</style>
	<script type="text/javascript" >
		function executeSubmit(newAction) {
		if (newAction != "") {
		   	document.forms[0].action = newAction;
		   }
			document.forms[0].submit();
		}
		
		function openHelpUrl(url){
			var v = window.open(url,'_frame','width=550,height=300,toolbar=no,scrollbars=yes,resizable=yes');
		}	
	</script>
</head>

<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
	    <tr>
	    	<td colspan="2" class="anteprima_header">
	    		<img class="anteprima_header" src="/people/img/people_logo.jpg" alt="Logo People" />
	    	</td>
	    </tr>
        <tr><td valign="top">
			<html:form action="/signProcess.do" enctype="multipart/form-data">
				<div class="anteprima_body">
					<%
						try {
							pageContext.include(CurrentStep.getJspPath());
						}
						catch (Exception ex) {ex.printStackTrace(System.out);}
					%>
				</div>
				<div class="anteprima_button_bar">
					<br />
					<hr />
					<input type="submit" name="back" value="Indietro" class="btn" />
				</div>
			</html:form>
		</td></tr>
    </table>
</body>
</html:html>
