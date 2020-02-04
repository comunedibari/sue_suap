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
<%@ page import="org.apache.ojb.broker.query.Criteria,
				 java.util.Collection,
				 it.people.process.*,
				 it.people.core.*"%>

<%
	String processID = request.getParameter("processID");

	SubmittedProcess sp = null;
	if (processID != null && processID.length() > 0) {
		Criteria crtr = new Criteria();
		crtr.addEqualTo("peopleProtocollId", processID);
		Collection spes = SubmittedProcessSearchManager.getInstance().get(PeopleContext.create(request), crtr);
		if (spes.size() > 0)
			sp = (SubmittedProcess)spes.iterator().next();
	}

	String newState = request.getParameter("newState");
	if (newState != null && newState.length() > 0) {
        if (sp != null) {
			sp.addHistoryStates(new SubmittedProcessHistory(SubmittedProcessState.get(new Long(newState))));
			if (new Long(newState).equals(new Long(3))) {
				sp.setCompleted(new Boolean(true));

			}
			SubmittedProcessManager.getInstance().set(PeopleContext.create(request), sp);
		}
	}
%>

<html>

<body>

<%
	String processIDText = "";
	if (sp != null) {
     	processIDText = sp.getPeopleProtocollId();
%>
<br><b><%= sp.getOid() %> <%= sp.getPeopleProtocollId() %></b>
	<% for (int i=0 ; i < sp.getHistoryState().length; i++)  { %>
        <br><%= sp.getHistoryState(i).getState().toString() %>
	<% } %>
<% } %>
<br>
<form method="post" action="processStatusMgr.jsp">
   	<br><input type="text" name="processID" value="<%=processIDText %>"/>
    <br><input type="text" name="newState" />
	<br><br><input type="submit" value="Imposta">
</form>

</body>
</html>




<!-- main.jsp -->
