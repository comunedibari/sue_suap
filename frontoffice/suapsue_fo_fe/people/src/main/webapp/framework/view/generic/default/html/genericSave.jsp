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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<%
	PplUser peopleUser = PeopleContext.create(request).getUser();
	Object requiredFieldsPresentsObj = pageContext.getAttribute("requiredFieldsPresents", PageContext.REQUEST_SCOPE);
	boolean showRequiredFieldsChar = false;
	if (requiredFieldsPresentsObj != null) {
		showRequiredFieldsChar = ((Boolean)requiredFieldsPresentsObj).booleanValue();
	}
%>
<html:xhtml/>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td class="spaziatoreSmall" colspan="2"></td>
	</tr>
	<tr>
		<% if (CurrentActivity.getCurrentStep() instanceof SummaryStep) {%>
            <td></td>
		<%} else if (showRequiredFieldsChar) {%>
            <td class="didascalia">Il simbolo (*) indica un campo obbligatorio.</td>
		<%}%>
            <td align="right">
            <%if (!bottoniNascosti.contains(NavigatorHelper.BOTTONE_SALVA)
            	  && !peopleUser.isAnonymous() && pplProcess.getView().isBottomSaveBarEnabled()){%>
				<html:submit property="<%=ButtonKey.SAVE%>" styleClass="btn">
				  <bean:message key="<%=ButtonKey.SAVE%>"/>
				</html:submit>
            <%}%>
            </td>
	</tr>
	<tr>
		<td class="spaziatore" colspan="2"></td>
	</tr>
</table>
