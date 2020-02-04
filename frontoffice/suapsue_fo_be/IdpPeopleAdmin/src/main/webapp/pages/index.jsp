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
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
	<head>
		<title></title>
	</head>
	<body>
		<% 
			it.idp.people.admin.faces.Manager manager = (it.idp.people.admin.faces.Manager)session.getAttribute("tableManager");
			if (request.getParameter("view") != null) 
			{
				String view = (String)request.getParameter("view");
		%>
				<jsp:forward page="<%="/faces/pages/list.jsp?view=" + view %>" />
		<% 
			}
			else
			{				
				if (manager != null)
				{
					manager.setView(0);
				}
			}			
		%>
		<f:view>
		<h:form>
			<div class="verticalMenu">
					<div>
						<p>
							Menu
						</p>
					</div>
					<ul>
						<li>
							<t:commandLink styleClass="active" value="Home page" action="index"/>							
						</li>
						<li>
							<t:commandLink styleClass="" value="Configurazione" action="config"/>							
						</li>
					</ul>
				</div>
		</h:form>
		</f:view>
	</body>	
</html>  
