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
<%@ page isErrorPage="true"%>
<%@ page import="it.people.error.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ include file="./include/top.jsp"%>
<html lang="it" dir="ltr">
  <head>
  <c:out value="${it_people_css}" escapeXml="false" />
  <c:out value="${it_people_meta}" escapeXml="false" />
  <title><c:out value="${it_people_pageTitle}" /></title>
  </head>
  
  <body>
  <!-- 
       <span style="top: 0;left: 0;     
         font-weight: bold; font-size: 24pt;
         color: light-gray;background-color: #E5E8EC;">
  -->
          <%@ include file="./include/header.jsp"%>
       </span>
  <%--@ include file="/WEB-INF/jsp/include/menu.jsp" --%>
  
  
    <p>
      <h4><%
             errorMessage em = (errorMessage) request
              .getAttribute("SIRAC_AUTHORIZATION_ERROR_MESSAGE");
            if (em != null) {
              ArrayList al = em.getQueue();
              for (int i = 0; i < al.size(); i++) {
                out.println((String) al.get(i));
          %>
               <br>
          <%
              }
              em.cleanQueue();
            }
          %> 
          <br>
      </h4>
    </p>
  </body>
</html>



