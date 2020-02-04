<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<h1><bean:message key="testUserAdmin.titolo"/></h1>
<% if (((IRequestWrapper)request).getPplUser().isPeopleAdmin()) {%>
	<bean:message key="testUserAdmin.benvenuto.utenteNormale"/>
<% } else { %>
	<bean:message key="testUserAdmin.benvenuto.utenteAdmin"/>
<% } %>
