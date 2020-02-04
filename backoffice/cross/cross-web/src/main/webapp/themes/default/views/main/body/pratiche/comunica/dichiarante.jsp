<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
    <div class="ctrlHolder">
        <label for="dichiarante.nome" class="required"><spring:message code="comunica.dichiarante.nome"/></label>
        <span class="value">${comunica.dichiarante.nome}</span>
        <form:hidden path="dichiarante.nome" />
    </div>
    <div class="ctrlHolder">
        <label for="dichiarante.cognome" class="required"><spring:message code="comunica.dichiarante.cognome"/></label>
        <span class="value">${comunica.dichiarante.cognome}</span>
        <form:hidden path="dichiarante.cognome" />
    </div>
    <div class="ctrlHolder">
        <label for="dichiarante.codiceFiscale" class="required"><spring:message code="comunica.dichiarante.codicefiscale"/></label>
        <span class="value">${comunica.dichiarante.codiceFiscale}</span>
        <form:hidden path="dichiarante.codiceFiscale" />
    </div>
    <div class="ctrlHolder">
        <label for="dichiarante.partitaIva" class="required"><spring:message code="comunica.dichiarante.partitaiva"/></label>
        <span class="value">${comunica.dichiarante.partitaIva}</span>
        <form:hidden path="dichiarante.partitaIva" />
    </div>
    <div class="ctrlHolder">
        <label for="dichiarante.pec" class="required"><spring:message code="comunica.dichiarante.pec"/></label>
        <span class="value">${comunica.dichiarante.pec}</span>
        <form:hidden path="dichiarante.pec" />
    </div>
    <div class="ctrlHolder">
        <label for="dichiarante.telefono" class="required"><spring:message code="comunica.dichiarante.telefono"/></label>
        <span class="value">${comunica.dichiarante.telefono}</span>
        <form:hidden path="dichiarante.telefono" />
    </div>
    <div class="ctrlHolder">
        <label for="dichiarante.nazionalita" class="required"><spring:message code="comunica.dichiarante.nazionalita"/></label>
        <form:select id="dichiarante.nazionalita" name="dichiarante.nazionalita" path="dichiarante.nazionalita" cssClass="select required">
            <form:option value="" label="" />
            <form:options items="${nazionalita}" itemLabel="description" itemValue="id"/>
        </form:select>
        <p class="formHint"><spring:message code="comunica.note"/> <c:out value="${comunica.dichiarante.nazionalitaOrigine}" /></p>
        <form:hidden path="dichiarante.nazionalitaOrigine" />
    </div>
</div>
