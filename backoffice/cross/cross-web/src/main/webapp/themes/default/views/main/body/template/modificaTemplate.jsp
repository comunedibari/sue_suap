<%-- 
    Document   : modificaù
    Created on : 18-Dec-2012, 11:30:32
    Author     : CS
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String path = request.getContextPath();
%>
<div>
    <h2 style="text-align: center"><spring:message code="templates.gestione.documento.title"/></h2>
    <tiles:insertAttribute name="body_error" />
    <form action="<%=path%>/gestione/template/salva.htm" enctype="multipart/form-data" method="post" class="uniForm inlineLabels comunicazione">
        <div class="inlineLabels">
            <input type="hidden" name="action" value="salva" />
            <input type="hidden" name="idTemplate" id="idTemplate" value="${template.idTemplate}" />
            <div class="ctrlHolder">
                <label for="descrizioneTemplate" class="required"><spring:message code="templates.gestione.documento.descrizione"/></label>
                <input type="text" name="descrizioneTemplate" id="descrizioneTemplate" value="${template.descrizioneTemplate}" class="textInput required"/>
            </div>
            <div class="ctrlHolder">
                <label for="nomeFile" class="required"><spring:message code="templates.gestione.documento.nomefile"/></label>
                <input type="text" name="nomeFile" id="descrizioneTemplate" value="${template.nomeFile}" class="textInput required"/>
                <p class="formHint"><spring:message code="templates.gestione.documento.nomefile"/></p>
            </div>
            <div class="ctrlHolder">
                <label for="file" class="required"><spring:message code="templates.gestione.documento.file"/></label>
                <input type="file" name="allegato.file" id="file" class="textInput required"/>
            </div>
            <div class="ctrlHolder">
                <label class="required"><spring:message code="templates.gestione.documento.tipologia"/></label>
                <select id="tipologiaTemplate" name="tipologiaTemplate" class="textInput required">
                    <option value="PDF" <c:if test="${template.tipologiaTemplate == 'PDF'}">selected="selected"</c:if> ><spring:message code="templates.gestione.documento.tipologia.pdf"/></option>
                    <option value="DOC" <c:if test="${template.tipologiaTemplate == 'DOC'}">selected="selected"</c:if> ><spring:message code="templates.gestione.documento.tipologia.doc"/></option>
                    <option value="RTF" <c:if test="${template.tipologiaTemplate == 'RTF'}">selected="selected"</c:if> ><spring:message code="templates.gestione.documento.tipologia.rtf"/></option>
                </select> 
            </div>
            <div class="buttonHolder">
                <a href="<%=path%>/gestione/template/lista.htm" class="secondaryAction">&larr; <spring:message code="templates.button.indietro"/></a>
                <button type="submit" class="primaryAction" name="submit" value="salva"><spring:message code="templates.button.salva"/></button>
            </div>
        </div> 
    </form>
</div>