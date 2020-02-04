<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="it.people.db.fedb.*, java.util.Collection" %>
<%@ page import="it.people.util.PeopleProperties" %>
<jsp:useBean id="City" scope="session" type="it.people.City" />
<% 
	String communeId = City.getOid();

	// Ridirezione alla pagina dei servizi specifica 
	// per il comune corrente se configurata.
	String servicePageUrl = PeopleProperties.SERVIZIPEOPLE_ADDRESS.getValueString(communeId);
	if (servicePageUrl != null && !"".equals(servicePageUrl)) {
		response.sendRedirect(servicePageUrl);
	}	
%>

<!-- index.jsp -->
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
	        <jsp:include page="/include/navbar.jsp" />
        </td></tr>
        <tr><td valign="top">
			<logic:notPresent parameter="selectedArea">
				<%Collection areas = (new AreaFactory()).getEnabledAreas(communeId);%>

				<% if (areas.isEmpty()) { %>
					<h2 class="index"><bean:message key="label.nessunServizioInstallato"/></h2>
				<% } else {%>
				    <h2 class="index"><bean:message key="label.areeServizi"/></h2>
					<ul class="indexListArea">
						<logic:iterate id="area" collection="<%=areas%>">
							<li>
								<html:link page="/framework/index.jsp" paramId="selectedArea" paramName="area" paramProperty="description">
									<bean:write name="area" property="description"/>
								</html:link>
							</li>
						</logic:iterate>
					</ul>
				<% } %>
			</logic:notPresent>		
			<logic:present parameter="selectedArea">		
				<%String area = request.getParameter("selectedArea");%>
				<%Collection services = (new ServiceFactory()).getEnabledServices(communeId, area);%>
				<% if (services.isEmpty()) { %>
					<h2 class="index"><bean:message key="label.nessunServizioInstallato"/></h2>
				<% } else {%>
					<h2 class="index"><bean:message key="label.serviziInstallati"/>&nbsp;<span style="font-style:italic"><%=area%></span></h2>
					<ul class="indexListService">
						<li class="levelup"><html:link href="./index.jsp">..</html:link></li>
						<logic:iterate id="service" collection="<%=services%>">
							<li>
								<html:link action="initProcess" paramId="processName" paramName="service" paramProperty="processName">
									<bean:write name="service" property="description"/>
								</html:link>
							</li>
						</logic:iterate>
					</ul>
				<% } %>
			</logic:present>
        </td></tr>
    </table>
</body>
</html:html>