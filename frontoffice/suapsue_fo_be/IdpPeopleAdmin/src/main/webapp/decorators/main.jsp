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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:directive.page import="it.idp.people.admin.faces.PortalConfig"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="it.idp.people.admin.common.PageProperties"/>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Idp People Admin - <decorator:title default="Benvenuto"/></title>
    <link rel="stylesheet" href="<c:url value="/css/classic/tables.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/css/classic/main.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/css/classic/mainnav.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/css/classic/messages.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/css/classic/tooltip.css"/>" type="text/css"/>
    <decorator:head/>
</head>

<body>

<%
	PortalConfig portalConfig = (PortalConfig)request.getAttribute("portalConfig");
	it.idp.people.admin.faces.Manager manager = (it.idp.people.admin.faces.Manager)session.getAttribute("tableManager");
	if(portalConfig == null)
	{
		portalConfig = new PortalConfig();
		request.setAttribute("portalConfig", portalConfig);
	}
%>

<div id="caption">
    <ul id="top">
        <li id="title">
        	<decorator:title default="Idp People Admin"/>
        </li>
    </ul>
</div>

<div id="navcontainer">
    <ul id="tabnav">
    	<li>
    	<%
	    	if(manager == null || manager.getView() == 0)
	    	{
	    %>
	    	<a class="active" href="<c:url value="/pages/index.jsf"/>">
        		Home
        	</a>
        <%
	    	}
	    	else
	    	{
	    %>
	    	<a href="<c:url value="/pages/index.jsf"/>">
        		Home
        	</a>
	    <%
	    	}
    	%>
        </li>
        
        <%
	        HashMap pageMap = portalConfig.getPageMap();        	
        	Iterator it = pageMap.values().iterator();
    		while(it.hasNext())
    		{
    			PageProperties pageProperties = (PageProperties)it.next();
    			if(manager != null && manager.getView() != 0 && manager.getView() == pageProperties.getIndex())
    			{
    			%>
    				<li class="active"><%= pageProperties.getName()%></li>	
    			<%
    			}
    			else
    			{
    			%>
    				<li>
	    				<a href="<c:url value='<%= "/pages/index.jsf?view=" + pageProperties.getIndex() %>'/>">
		        		<%= pageProperties.getName()%>
						</a>
					</li>	
    			<%
    			}
	   		}
    	%>
	</ul>
</div>

<div id="mainBody">
    <decorator:body/>	
</div>

 
<div id="footer">
    <%--
    <ul>
        <li><a href="<c:url value="/pages/index.jsf"/>">Home</a></li>
        <li><a href="<c:url value="/pages/index.jsf?view=1"/>">Comuni</a></li>
        <li><a href="<c:url value="/pages/index.jsf?view=2"/>">Utenti</a></li>
        <li><a href="<c:url value="/pages/index.jsf?view=3"/>">Qualifiche</a></li>
        <li><a href="<c:url value="/pages/index.jsf?view=4"/>">Accreditamenti</a></li>
    </ul>
    
    <div style="margin: 20px 0 0 0;">
		<p><small>(<a href="?printable=true">printable version</a>)</small></p>
    </div>
    --%>
</div>


</body>
</html>
