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
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<% String action = request.getParameter("action"); %>
<% String argument = request.getParameter("argumentValue"); %>
<% String messageKey = request.getParameter("messageKey"); %>
<% String confirmKey = request.getParameter("confirmKey"); %>
<% String cancelKey = request.getParameter("cancelKey"); %>
<html:xhtml/>
<html:form action="<%=action%>">
	<div id="usrMsgContainer" class="infobox">
	    <table border="0" cellpadding="8" cellspacing="0" class="infoboxtable">
	        <tbody>
	            <tr>
	                <td>
		                <table border="0" cellspacing="1" class="innerinfoboxtable">
			                <tbody>
				                <tr>
					                <td>
					                	<html:img styleClass="runtimewarnmsg" page="/img/alert.png" alt="Attenzione!"></html:img>
					                </td>
					                <td><strong><span class="usrmsg">
					                	<bean:message key="<%=messageKey%>" />
					                </span></strong></td>
				                </tr>
				                <tr>
				                    <td colspan="2" class="runtimemsgcmdbar">
										<html:hidden property="argument" value="<%=argument%>"/>
										<html:submit property="method" styleClass="smallbtn">
											<bean:message key="<%=confirmKey%>"/>
										</html:submit>
										<html:submit property="method" styleClass="smallbtn">
											<bean:message key="<%=cancelKey%>"/>
										</html:submit>
				                   	</td>
				                </tr>
			                </tbody>
		                </table>
	                </td>
	            </tr>
	        </tbody>
	    </table>
	</div>        	
</html:form>	        		
