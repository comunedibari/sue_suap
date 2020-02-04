<%-- 
    Document   : stampa
    Created on : 3-feb-2012, 10.44.31
    Author     : CS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="buttonHolder">
        <a class="secondaryAction" href="<%=request.getHeader("referer")%>">← Indietro</a>
        <button class="primaryAction" id="stampa_dettaglio_pratica" type="submit">Stampa</button>
</div>