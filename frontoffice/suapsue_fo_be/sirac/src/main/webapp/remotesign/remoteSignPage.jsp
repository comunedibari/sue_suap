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
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

  <%
     //boolean isRemoteSignEnabled = new Boolean((String) application.getInitParameter("remoteSignEnabled")).booleanValue();
     //String remoteSignUserName = (String)session.getAttribute("it.people.sirac.authenticated_user");
     //String remoteSignMode = (String) application.getInitParameter("remoteSignMode");
     
     
     //if (isRemoteSignEnabled) {
       
  %>
    <c:if test="${param.remoteSignEnabled}">
      <script type="text/javascript" src="<c:out value='${param.pathPrefix}' />/remotesign.js">
      </script>
      <script type="text/javascript">
        init_pathPrefix("<c:out value='${param.pathPrefix}' />");
      </script>
        <input type="hidden" name="username" value="<c:out value='${param.userID}'/>" />
        <input type="hidden" name="remoteSignMode" value="<c:out value='${param.remoteSignMode}' />" />
        
        <a href="<c:out value="${param.pathPrefix}" />/FirmaRemotaHelp.pdf" target="_blank">
          Linee guida per l'utilizzo della firma remota
        </a>
    </c:if>
  <%
     //} 
  %>

<%--@ include file="/framework/debug/debug.jsp" --%>
<%--<jsp:include page="/framework/debug/debug.jsp" flush="true" />--%>
