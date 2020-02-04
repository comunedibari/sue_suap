<%
    String path = request.getContextPath();
    String url = path + "/pratica/anagrafe_tributaria_commercio/submit.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />
<form modelAttribute="anagrafeCommercio" action="<%=url%>"  id="anagrafeTributaria" method="post" cssClass="uniForm inlineLabels comunicazione">
    <h2 style="text-align: center">Non &egrave; possibile creare manualmente un evento di aggiunta anagrafica</h2>
    <p>Per aggiungere una anagrafica utilizzare la apposita funzionalità nella pagina di gestione della pratica.</p>
    <div class="buttonHolder">
        <a href="<%=path%>/pratica/evento/index.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
    </div>
</form>