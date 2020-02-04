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
<%@ page isErrorPage="true" %>
<%@ include file="./include/siracTop.jsp" %>
<html lang="it" dir="ltr">
<head>
	<c:out value="${it_css}" escapeXml="false" />
	<c:out value="${it_meta}" escapeXml="false" />
	<title><c:out value="${it_pageTitle}" /></title>
</head>

<body>
	<div style="top: 0;left: 0;     
					 font-weight: bold; font-size: 18pt;
					 background-color: #E5E8EC;">
	    <%@ include file="./include/siracHeader.jsp" %>
	</div>
<%
	String errorMessage = (String)request.getAttribute("errorMessage");
 %>

<div id="content">

<h1>Errore</h1>

<p>
  <c:if test="${errorMessage != null}">
    <b><c:out value='${errorMessage}'/></b><br>
  </c:if>
</p>

 <%
   boolean showDetails = new Boolean((String)application.getInitParameter("showDetailInErrorPage")).booleanValue();   
   //out.(showDetails);
   if (showDetails && request.getAttribute("javax.servlet.error.exception") != null) {
%>
	<p>Si è verificato un errore di tipo
	    <b><%= exception.getClass().getName() %></b> durante l'elaborazione di 
	    <b><%= request.getAttribute("javax.servlet.error.request_uri") %></b>
	</p>

	<h2>Dettagli</h2>
	
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
