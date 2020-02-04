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
<%@ page contentType="text/html" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page import="it.idp.people.admin.faces.PortalConfig"/>
<jsp:directive.page import="it.idp.people.admin.common.PageProperties"/>
<html>
	<head>
	<%
		it.idp.people.admin.faces.Manager manager = (it.idp.people.admin.faces.Manager)session.getAttribute("tableManager");
		if (manager == null)
		{
			manager = new it.idp.people.admin.faces.Manager();
			session.setAttribute("tableManager", manager);	
		}
		manager.setCurrent(null);
	
		PortalConfig portalConfig = (PortalConfig)request.getAttribute("portalConfig");
		PageProperties pageProperties = null;
		if(portalConfig == null)
		{
			portalConfig = new PortalConfig();
			request.setAttribute("portalConfig", portalConfig);
			String view = (String)request.getParameter("view");
			if(view == null)
			{
				view = new Integer(manager.getView()).toString();
			}
			pageProperties = (PageProperties)portalConfig.getPageMap().get(new Integer(view));
		}
		if(pageProperties != null)
		{
	%>
		<title>Gestione <%= pageProperties.getName()%></title>
	<%
		} else {
	%>
		<title>Lista</title>
	<%
		}
	%>
	</head>
	<body>
		<f:view>		
		<h:form id="form1">
		<div width="100%">
			<table width="100%">
				<tr>
					<td>
						<t:commandLink value="Inserisci nuovo" action="#{tableManager.setInsert}"/>
					</td>
					<td width="50%"align="right">
						<t:commandLink value="Filtra" action="#{tableManager.switchFilter}"/>						
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td width="50%"align="right">
					<t:panelGrid rendered="#{tableManager.enableFilter}">
						<c:import url="<%= manager.getFilterPage() %>" /> 					     			
					</t:panelGrid>
					</td>
				</tr>
			</table>			
			<p></p>
			<%
				if(manager.getList().size() > 0)
				{
			%>
			<t:dataTable rowClasses="odd,even" cellspacing="0" styleClass="genericTbl"
			    		 value="#{tableManager.tableDataModel}" var="Item"
			    		 binding="#{tableManager.dynamicDataTable}"
			    		 sortable="true" renderedIfEmpty="false" 
			    		 sortAscending="#{tableManager.ascending}" 
			    		 sortColumn="#{tableManager.sortColumn}">
			</t:dataTable>
			<div align="center">			
				<br/>
				<t:commandButton value="<<" action="#{tableManager.paging.scrollFirst}" disabled="#{tableManager.paging.scrollFirstDisabled}"/>
				<t:commandButton value="<" action="#{tableManager.paging.scrollPrevious}" disabled="#{tableManager.paging.scrollFirstDisabled}"/>
				<t:commandButton value=">" action="#{tableManager.paging.scrollNext}" disabled="#{tableManager.paging.scrollLastDisabled}"/>
				<t:commandButton value=">>" action="#{tableManager.paging.scrollLast}" disabled="#{tableManager.paging.scrollLastDisabled}"/>
				 Righe per pagina: 
				<t:inputText value="#{tableManager.paging.rowsPerPage}" size="3" />
				 Pagina numero: 
				<t:inputText value="#{tableManager.paging.pageIndex}" size="5" />
				 di <t:outputText value="#{tableManager.paging.pageCount}"/>
				<t:commandButton value="Refresh" />
				<br/>
			</div>
			<%
				}
				else
				{
			%>
			<div align="center">
				<p>Nessun risultato</p>
			</div>
			<%
				}
			%>	
		</div>
		</h:form>
		</f:view>
	</body>
</html>

