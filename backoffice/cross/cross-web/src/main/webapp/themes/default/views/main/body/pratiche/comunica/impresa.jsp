<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ctrlHolder">
    <label for="impresa.ragioneSociale" class="required"><spring:message code="comunica.impresa.ragionesociale"/></label>
    <span class="value">${comunica.impresa.ragioneSociale}</span>
    <form:hidden path="impresa.ragioneSociale" />
</div>
<div class="ctrlHolder">
    <div class="ctrlHolder dettaglio_liv_0">
        <label for="impresa.codFormaGiuridica" class="required"><spring:message code="comunica.impresa.formagiuridica"/></label>
        <form:select id="impresa.codFormaGiuridica" name="impresa.codFormaGiuridica" path="impresa.codFormaGiuridica" cssClass="select required">
            <form:option value="" label="" />
            <form:options items="${formaGiuridica}" itemLabel="description" itemValue="id"/>
        </form:select>
    </div>
    <p class="formHint"><spring:message code="comunica.note"/> <c:out value="${comunica.impresa.formaGiuridicaOrigine}" /></p>
    <form:hidden path="impresa.formaGiuridicaOrigine" />
</div>     
<div class="ctrlHolder">
    <label for="impresa.codiceFiscale" class="required"><spring:message code="comunica.impresa.codicefiscale"/></label>
    <span class="value">${comunica.impresa.codiceFiscale}</span>
    <form:hidden path="impresa.codiceFiscale" />
</div>
<div class="ctrlHolder">
    <label for="impresa.partitaIva" class="required"><spring:message code="comunica.impresa.partitaiva"/></label>
    <span class="value">${comunica.impresa.partitaIva}</span>
    <form:hidden path="impresa.partitaIva" />
</div>
<div class="ctrlHolder">
    <label for="impresa.desComuneSede" class="required"><spring:message code="comunica.impresa.comunesede"/></label>
    <span class="value">${comunica.impresa.desComuneSede}</span>
    <form:hidden path="impresa.desComuneSede" />
    <form:hidden path="impresa.codCatastaleComuneSede" />
</div>
<div class="ctrlHolder">
    <label for="impresa.indirizzoSede" class="required"><spring:message code="comunica.impresa.indirizzo"/></label>
    <span class="value">${comunica.impresa.indirizzoSede}</span>
    <form:hidden path="impresa.indirizzoSede" />
</div>
<div class="ctrlHolder">
    <label for="impresa.civicoSede" class="required"><spring:message code="comunica.impresa.civico"/></label>
    <span class="value">${comunica.impresa.civicoSede}</span>
    <form:hidden path="impresa.civicoSede" />
</div>
<div class="ctrlHolder">
    <label for="impresa.telefono" class="required"><spring:message code="comunica.impresa.telefono"/></label>
    <span class="value">${comunica.impresa.telefono}</span>
    <form:hidden path="impresa.telefono" />
</div>

<div class="ctrlHolder">
    <div class="ctrlHolder dettaglio_liv_0">
        <label for="impresa.provinciaRea" class="required"><spring:message code="comunica.impresa.provrea"/></label>
        <form:select id="impresa.provinciaRea" name="impresa.provinciaRea" path="impresa.provinciaRea" cssClass="select required">
            <form:option value="" label="" />
            <form:options items="${provincie}" itemLabel="description" itemValue="id"/>
        </form:select>
    </div>
    <p class="formHint"><spring:message code="comunica.note"/> <c:out value="${comunica.impresa.provinciaReaOrigine}" /></p>
    <form:hidden path="impresa.provinciaReaOrigine" />
</div>
<div class="ctrlHolder">
    <label for="impresa.dataIscrizione" class="required"><spring:message code="comunica.impresa.datarea"/></label>
    <span class="value">${comunica.impresa.dataIscrizione}</span>
    <form:hidden path="impresa.dataIscrizione" />
</div>
<div class="ctrlHolder">
    <label for="impresa.numeroRea" class="required"><spring:message code="comunica.impresa.numrea"/></label>
    <span class="value">${comunica.impresa.numeroRea}</span>
    <form:hidden path="impresa.numeroRea" />
</div>