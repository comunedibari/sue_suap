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
<%@ page import="it.people.util.IUserPanel" %>
<%@ page import="it.people.core.Logger" %>
<%@ page import="it.people.core.*" %>
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<div style="text-align: center; margin-bottom: 10px; margin-top: 20px;">
	<h1><bean:message key="label.userfunctionspanel.sendsuggestion.title"/></h1>
</div>

<div style="text-align: center; margin-bottom: 10px; padding: 10px;">
	<bean:message key="label.userfunctionspanel.sendsuggestion.info"/>
</div>

<div style="margin-left: 20px; border:1px solid #EAEAEA; padding: 20px;">
	<%
	    String email="";	
 	    PplUser peopleUser = PeopleContext.create(request).getUser();
 	    email=peopleUser.getUserData().getEmailaddress();
					 
	%>
	<div style="margin-bottom: 10px;">
		<label style="font-size: 1.2em;">Email Segnalatore:</label><input name="emailSegnalatore" type="text" style="font-size: 1.2em; font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;"  size="94"  value="<%=email%>">
	</div>
	
	<div style="margin-bottom: 10px;">
		<label style="font-size: 1.2em;" for="suggestionSubject"><bean:message key="label.userfunctionspanel.sendsuggestion.subject"/></label><input style="font-size: 1.2em; font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;" type="text" id="suggestionSubject" size="100" name="suggestionSubject" value="" />
	</div>
	<div style="margin-bottom: 10px;">
		<label style="display: inline-block; vertical-align: top; font-size: 1.2em;" for="suggestionContent"><bean:message key="label.userfunctionspanel.sendsuggestion.description"/></label><textarea style="display: inline-block; vertical-align: baseline; font-size: 1.2em; font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;" id="suggestionContent" rows="10" cols="100" name="suggestionContent"></textarea>
	</div>
	<div style="margin-bottom: 10px;">
		<input style="font-size: 1.2em; font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;" type="checkbox" id="requestReceipt" name="requestReceipt" /><label style="font-size: 1.2em;" for="requestReceipt"><bean:message key="label.userfunctionspanel.sendsuggestion.sendUserNotify"/></label>
	</div>
	<div style="text-align: right;">
		<input type="submit" name="<%=ButtonKey.USER_PANEL_LOOP_BACK%>$<%=IUserPanel.ActionTypes.submit.getActionName()%>" value="<bean:message key="label.userfunctionspanel.sendsuggestion.submit"/>" class="btn" />
		<input type="submit" name="<%=ButtonKey.USER_PANEL_LOOP_BACK%>$<%=IUserPanel.ActionTypes.cancel.getActionName()%>" value="<bean:message key="label.userfunctionspanel.sendsuggestion.cancel"/>" class="btn" />
	</div>
</div>