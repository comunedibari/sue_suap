<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ctrlHolder dettaglio_liv_0" id="ricevuta">
    <h4 class="readOnlyLabel">
        <spring:message code="pratica.comunicazione.evento.documento"/>    
    </h4>
    <div class="ctrlHolder">
        <label class="required">
            <spring:message code="pratica.comunicazione.evento.template"/>
        </label>
        <span class="value">
            <spring:message code="pratica.comunicazione.evento.documentodownload"/>
        </span>
    </div>
</div>