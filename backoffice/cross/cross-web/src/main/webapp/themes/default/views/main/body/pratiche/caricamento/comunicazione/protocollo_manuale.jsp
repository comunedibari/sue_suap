<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="ctrlHolder dettaglio_liv_0" id="protocollo">
    <div class="ctrlHolder">
        <label for="numeroDiProtocollo" class="required">
            <spring:message code="pratica.comunicazione.evento.protocollo.numero"/>    
        </label> 
        <input type="text" class="textInput dimensione_box_comunicazione" name="numeroDiProtocollo"  id="numeroDiProtocollo" value="">
        <p class="formHint">
            <spring:message code="pratica.comunicazione.evento.protocollo.numeronota"/>
        </p>
    </div>
</div>