<%@ page pageEncoding="utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script  type="text/javascript" src="<c:url value="/javascript/cross/confronta.anagrafica.notifica.js"/>"></script>
<c:set var="classDiv" value="" />
<c:if test="${praticaDettaglio.notifica.counter != null && praticaDettaglio.notifica.counter > 0}">
    <c:set var="classDiv" value="confermataBG" />
</c:if>
<div id="recapitoNotifica" class="inlineLabels ${classDiv}" style="height: auto; width: 100%;">
    <div class="ctrlHolder recapitoNotifica">
        <span class="tabellaDettaglioTitolo">Recapito notifica della Pratica</span>
        <ul class="" style="width: 100%">
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.descTipoIndirizzo"/>:</strong></div>
                <input type="hidden" class="confermata" name="notifica.counter" value="${praticaDettaglio.notifica.counter}"/>
                <i>
                    <input type="text" class=" ui-widget ui-widget-content ui-corner-all" name="notifica.descTipoIndirizzo" value="NOTIFICA" readonly="readonly" style="background-color: transparent;border: medium none"/>
                </i>
                <input type="hidden" value="${praticaDettaglio.notifica.idRecapito}"/>
                <input type="hidden" name="notifica.counter" value="${praticaDettaglio.notifica.counter}"/>
                <input type="hidden" name="notifica.idTipoIndirizzo" value="${praticaDettaglio.notifica.idTipoIndirizzo}"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="pratiche.notifica.presso"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.presso" value="${praticaDettaglio.notifica.presso}" maxlength="255"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.indirizzo"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.indirizzo" value="${praticaDettaglio.notifica.indirizzo}"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.nCivico"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.nCivico" value="${praticaDettaglio.notifica.nCivico}" />
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.cap"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.cap" value="${praticaDettaglio.notifica.cap}"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.descComune"/>:</strong></div>
                <input type="text" class="recapito.descComune ui-widget ui-widget-content ui-corner-all" name="notifica.descComune" value="${praticaDettaglio.notifica.descComune}" />
                <input type="hidden" name="notifica.idComune" value="${praticaDettaglio.notifica.idComune}" class="recapito.idComune"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.descProvincia"/>:</strong></div>
                <input type="text" readonly="readonly" class="recapito.descProvincia ui-widget ui-widget-content ui-corner-all" name="notifica.descProvincia" value="${praticaDettaglio.notifica.descProvincia}" />
                <input type="hidden" class="" name="notifica.idProvincia" value="${praticaDettaglio.notifica.idProvincia}" class="recapito.idProvincia  hidden"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.descStato"/>:</strong></div>
                <input type="text" readonly="readonly" class="recapito.descStato ui-widget ui-widget-content ui-corner-all" name="notifica.descStato" value="${praticaDettaglio.notifica.descStato}"/>
                <input type="hidden" class="" name="notifica.idStato" value="${praticaDettaglio.notifica.idStato}" class="recapito.idStato hidden"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.telefono"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.telefono" value="${praticaDettaglio.notifica.telefono}" />
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.pec"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.pec" value="${praticaDettaglio.notifica.pec}"/>
            </li>
            <li class="">
                <div class="choiceLabel"><strong><spring:message code="utenti.email"/>:</strong></div>
                <input type="text" class="ui-widget ui-widget-content ui-corner-all" name="notifica.email" value="${praticaDettaglio.notifica.email}"/>
            </li>
            <li>
                <button class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false" name="recapitoNotificaConferma" id="recapitoNotificaConferma">
                    <span class="ui-button-text">Conferma</span>
                </button>
            </li>
        </ul>
    </div>
</div>