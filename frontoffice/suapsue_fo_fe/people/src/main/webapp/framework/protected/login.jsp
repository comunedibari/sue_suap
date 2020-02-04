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
<%@ page language="java" import="java.util.*" %>
<%@ page import="it.people.core.PeopleContext" %>
<%@ page import="it.people.PeopleConstants" %>
<%@ page import="it.people.PeopleConstants" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	// Se non è richiesta esplicitamente la sua creazione
	// non viene creato lo UserPrincipal
	PeopleContext pplContext = PeopleContext.create(request);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
  </head>
  
  <body>
  	<%
  		String processName = (String) session.getAttribute(PeopleConstants.REQUEST_PROCESS_NAME);
  		String parametriBookmark = (String) session.getAttribute("parametriBookmark");
  		if (parametriBookmark==null) {parametriBookmark="";}
        session.removeAttribute(PeopleConstants.REQUEST_PROCESS_NAME);  	
		String destinationUrl = "/";
  		if (processName != null) {	
			destinationUrl += "initProcess.do?processName=" + processName+parametriBookmark;
		}
	%>
	<jsp:forward page="<%=destinationUrl%>" /> 
  </body>
</html>
