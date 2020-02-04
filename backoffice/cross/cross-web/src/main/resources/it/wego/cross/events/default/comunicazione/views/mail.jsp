<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="comunicazione_destinatari" />
<div class="ctrlHolder">
    <form:label path="oggetto" class="required"><spring:message code="pratica.comunicazione.evento.oggetto"/></form:label>
    <form:input path="oggetto" id="oggetto" class="textInput required" />
    <p class="formHint"><spring:message code="pratica.comunicazione.evento.oggettonota"/></p>
</div>
<div class="ctrlHolder">
    <form:label path="contenuto" class="required"><spring:message code="pratica.comunicazione.evento.contenuto"/></form:label>
    <form:textarea cols="25" rows="5" path="contenuto" />
    <p class="formHint"><spring:message code="pratica.comunicazione.evento.contenutonota"/></p>
</div>
<c:if test="${processo_evento.flgProtocollazione == 'S'}">
    <tiles:insertAttribute name="comunicazione_protocollo_manuale" />
</c:if>
<%-- Non uso la taglib di spring perché voglio forzare sempre il default a Invia email--%>
<div class="ctrlHolder form_checkbox_div">
    <!--<label path="inviaEmail" style="font-size: 1em;"><spring:message code="pratica.comunicazione.evento.email"/></label>-->
    <form:label path="inviaEmail" style="font-size: 1em;"><spring:message code="pratica.comunicazione.evento.email"/></form:label>
    <form:checkbox path="inviaEmail" />
    <!--<input type="checkbox" name="inviaEmail" id="inviaEmail" value="ok" checked="checked">-->
    <p class="formHint">&nbsp;</p>
</div>
