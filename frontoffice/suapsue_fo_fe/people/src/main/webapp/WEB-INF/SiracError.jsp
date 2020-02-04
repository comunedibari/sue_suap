<%@ page isErrorPage="true" %>
<%@ include file="./include/siracTop.jsp" %>
<html lang="it" dir="ltr">
<head>
	<c:out value="${it_people_css}" escapeXml="false" />
	<c:out value="${it_people_meta}" escapeXml="false" />
	<title><c:out value="${it_people_pageTitle}" /></title>
</head>

<body>
<%@ include file="./include/siracHeader.jsp" %>
<%--@ include file="/WEB-INF/jsp/include/menu.jsp" --%>
<%
	String errorMessage = (String)request.getAttribute("errorMessage");
 %>
<%-- <jsp:useBean id="errorMessage"   scope="request" type="java.lang.String" /> --%>

<div id="content">

<h1>Errore</h1>

<%--
<p>Si è verificato un errore di tipo
    <b><%= exception.getClass().getName() %></b> durante l'elaborazione di 
    <b><%= request.getAttribute("javax.servlet.error.request_uri") %></b>
</p>
--%>    

<%-- SPOSTATO NEI DETTAGLI
<p>Si è verificato un errore durante l'elaborazione di 
    <b><%= request.getAttribute("javax.servlet.error.request_uri") %></b>
</p>
--%>
<p>
  <%--<b><%= exception.getMessage() %></b><br>--%>
  <c:if test="${errorMessage != null}">
    <b><c:out value='${errorMessage}'/></b><br>
  </c:if>
</p>

 <%
   boolean showDetails = new Boolean((String)application.getInitParameter("showDetailInErrorPage")).booleanValue();   
   //out.(showDetails);
   if (showDetails && request.getAttribute("javax.servlet.error.exception") != null) {
%>
	<h2>Dettagli</h2>
	
	<p>Si è verificato un errore durante l'elaborazione di 
    	<b><%= request.getAttribute("javax.servlet.error.request_uri") %></b>
	</p>
	<p><b><%= exception.getClass().getName() + ": " + exception.getMessage()%></b></p>
	<p>
	<%
	
		Throwable e = (Throwable)request.getAttribute("javax.servlet.error.exception");
		StackTraceElement[] stack = e.getStackTrace();
		
		for (int n = 0; n < Math.min(5, stack.length); n++) { 
		    out.write(stack[n].toString());
		    out.write("<br/>");
		}
		
		out.write("<hr />"); 
		
		e = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e.getCause();
		
		if (e != null) { 
		    out.write("Causa: <b>" + e.getClass().getName() + "</b><p> [ " + e.getMessage() + " ] </p>");
		    stack = e.getStackTrace();
		    for (int n = 0; n < Math.min(5, stack.length); n++) { 
		        out.write(stack[n].toString());
		        out.write("<br/>");
		    }
		} 
	%>	
  
<%   
   }
   
   String returnURL = (String)application.getInitParameter("people.sirac.error.returnURL");   
   if(returnURL != null)   {
 %>
 
 </p>

</div> 

 	<br><br>
	<div align="center">
		<input type="button" style="width:100px" name="return" value="OK" onclick="location='<%=returnURL%>'"/>
    </div>
 <%
   }
 %>

   
</body>
</html>
