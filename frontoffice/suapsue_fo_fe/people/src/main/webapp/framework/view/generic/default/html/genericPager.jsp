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
<%@ page import="it.people.core.Logger" %>
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<%
	Pager pager = new Pager(request, pplProcess, CurrentActivity, bottoniVisibili, bottoniNascosti);
%>
<html:xhtml/>
<table id="footer" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr><td>

	<%-- Pulsante Previous --%>
    <html:submit property="<%=pager.GetPreviousLabel()%>" styleClass="<%=pager.GetPreviousCssClass()%>">
      <bean:message key="<%=pager.GetPreviousLabel()%>"/>
    </html:submit>

	<%-- Elenco step --%>        
	<%
	int numberOfSteps = CurrentActivity.getStepCount();
    for (int index=0; numberOfSteps > 1 && index < numberOfSteps; index ++) {
        String currentClass = "txtStepEnable";
	
        if (index == CurrentActivity.getCurrentStepIndex())
            currentClass = "txtStepCurrent";
	%>
        <span class="<%=currentClass%>"> 
            <%= Integer.toString(index+1) %> 
        </span>
	<%
    }
    // se non ho STEPs (o ne ho uno solo) metto cmq degli spazi 
    // cosi' separo i bottoni NEXT e PREV
    
    if(numberOfSteps <= 1 ){%>
        &nbsp;&nbsp;&nbsp;&nbsp;
	<%}%>

	<%-- Pulsante Next --%>        
    <html:submit property="<%=pager.GetNextLabel()%>" styleClass="<%=pager.GetNextCssClass()%>">
      <bean:message key="<%=pager.GetNextLabel()%>"/>
    </html:submit>

    </td></tr>
</table>
