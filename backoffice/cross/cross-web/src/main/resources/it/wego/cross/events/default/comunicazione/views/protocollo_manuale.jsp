<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
    $(document).ready(function() {

        $(".datePicker").datepicker({
            dateFormat: 'dd/mm/yy'
        });

    });
</script>

<div class="ctrlHolder dettaglio_liv_0" id="protocollo">
    <!--<div class="ctrlHolder">-->
    <form:label path="numeroDiProtocollo" cssClass="required"><spring:message code="pratica.comunicazione.evento.protocollo.numero"/></form:label> 
    <form:input cssClass="textInput required" path="numeroDiProtocollo" />
    <p class="formHint"><spring:message code="pratica.comunicazione.evento.protocollo.numeronota"/></p>
    <!--</div>-->
</div>
<div class="ctrlHolder dettaglio_liv_0">
    <form:label cssClass="required" path="dataDiProtocollo">Data Protocollazione</form:label> 
    <form:input path="dataDiProtocollo" cssClass="textInput required datePicker" readonly="true"/>
    <p class="formHint"></p>
</div>