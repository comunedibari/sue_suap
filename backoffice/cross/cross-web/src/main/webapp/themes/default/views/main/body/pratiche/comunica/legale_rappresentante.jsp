<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div>
    <div class="ctrlHolder">
        <label for="legaleRappresentante.nome" class="required"><spring:message code="comunica.legrap.nome"/></label>
        <span class="value">${comunica.legaleRappresentante.nome}</span>
        <form:hidden path="legaleRappresentante.nome" />
    </div>
    <div class="ctrlHolder">
        <label for="legaleRappresentante.cognome" class="required"><spring:message code="comunica.legrap.cognome"/></label>
        <span class="value">${comunica.legaleRappresentante.cognome}</span>
        <form:hidden path="legaleRappresentante.cognome" />
    </div>
    <div class="ctrlHolder">
        <label for="legaleRappresentante.codiceFiscale" class="required"><spring:message code="comunica.legrap.codicefiscale"/></label>
        <span class="value">${comunica.legaleRappresentante.codiceFiscale}</span>
        <form:hidden path="legaleRappresentante.codiceFiscale" />
    </div>
    <div class="ctrlHolder">
        <label for="legaleRappresentante.partitaIva" class="required"><spring:message code="comunica.legrap.partitaiva"/></label>
        <span class="value">${comunica.legaleRappresentante.partitaIva}</span>
        <form:hidden path="legaleRappresentante.partitaIva" />
    </div>
    <div class="ctrlHolder">
        <label for="legaleRappresentante.pec" class="required"><spring:message code="comunica.legrap.pec"/></label>
        <span class="value">${comunica.legaleRappresentante.pec}</span>
        <form:hidden path="legaleRappresentante.pec" />
    </div>
    <div class="ctrlHolder">
        <label for="legaleRappresentante.nazionalita" class="required"><spring:message code="comunica.legrap.nazionalita"/></label>
        <form:select id="legaleRappresentante.nazionalita" name="legaleRappresentante.nazionalita" path="legaleRappresentante.nazionalita" cssClass="select required">
            <form:option value="" label="" />
            <form:options items="${nazionalita}" itemLabel="description" itemValue="id"/>
        </form:select>
        <p class="formHint"><spring:message code="comunica.note"/> <c:out value="${comunica.legaleRappresentante.nazionalitaOrigine}" /></p>
        <form:hidden path="legaleRappresentante.nazionalitaOrigine" />
    </div>                            
</div>