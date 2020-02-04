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
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!-- deleteProcess.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <title><bean:message key="label.windowTitle"/></title>
    <people:frameworkCss />
</head>
<body>
    <table cellspacing="0" width="100%">
        <tr><td colspan="2">
    		<people:include rootPath="/framework/view/generic" 
    			nestedPath="/html" 
    			elementName="header.jsp" />	        			
        </td></tr>
        <tr><td>
			<jsp:include page="/include/navbar.jsp">
				<jsp:param name="disabled" value="true" />
			</jsp:include>
        </td></tr>
		<tr><td>&nbsp;</td></tr>	        
        <tr><td>
			<jsp:include page="confirmControl.jsp">
				<jsp:param name="action" value="/framework/protected/deleteProcess.do"/>
				<jsp:param name="argumentValue" value='<%=request.getParameter("processId")%>'/>
				<jsp:param name="messageKey" value="label.deleteProcess.message"/>
				<jsp:param name="confirmKey" value="label.deleteProcess.delete"/>
				<jsp:param name="cancelKey" value="label.deleteProcess.cancel"/>
			</jsp:include>
        </td></tr>
    </table>
</body>
</html:html>
        	
