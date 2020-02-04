<%
    String path = request.getContextPath();
    String url = path + "/processi/salvaProcesso.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div>
    <h2 style="text-align: center"><spring:message code="processo.aggiungi.title"/></h2>
        <tiles:insertAttribute name="body_error" />
    <form action="<%=url%>" class="uniForm inlineLabels" method="post">
        <div class="inlineLabels">
            <div class="ctrlHolder">
                <label for="codProcesso" class="required"><spring:message code="processo.codice"/></label>
                <input name="codProcesso" id="codice" maxlength="255" type="text" class="textInput required" >
                <p class="formHint"><spring:message code="eventi.processo.obbligatorio"/></p>
            </div>
            <div class="ctrlHolder">
                <label for="desProcesso" class="required"><spring:message code="processo.descrizione"/></label>
                <input name="desProcesso" id="descrizione" maxlength="255" type="text" class="textInput required" >
                <p class="formHint"><spring:message code="eventi.processo.obbligatorio"/></p>
            </div>
        </div>

        <div class="buttonHolder">
            <a href="<%=path%>/processi/lista.htm" class="secondaryAction">&larr; <spring:message code="processo.button.indietro"/></a>
            <button type="submit" class="primaryAction"><spring:message code="processo.button.salva"/></button>
        </div>
    </form>
</div>

