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



