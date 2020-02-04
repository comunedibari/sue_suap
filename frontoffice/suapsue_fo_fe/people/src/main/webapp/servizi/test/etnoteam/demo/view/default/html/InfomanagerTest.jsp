<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="it.people.util.infomanager.InfoManager"%>
<html>
    <head><title>JSP Page</title></head>
    <body>
<%
    InfoManager im = InfoManager.getInstance("it.people.fsl.servizi.test.etnoteam.demo");
    
    String prova = im.get("prova");
    
%>

    <p>
        Questo messaggio è stato reperito tramite Infomanager:
    </p>
    
    <p>
        <%= prova %>
    </p>

    </body>
</html>
