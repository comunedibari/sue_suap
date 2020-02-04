<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/core.css"/>"  media="all"/>
<!--<script type="text/javascript" src="<c:url value="/javascript/core.js"/>"></script>-->
<script  type="text/javascript" src="<c:url value="/javascript/cross/sue_ws.js"/>"></script>
<tiles:insertAttribute name="body_error" />

<c:url value="/download/pratica.htm" var="downloadPraticaUrl">
    <c:param name="id_pratica" value="${pratica.idPratica}"/>
</c:url>

<c:set var="field_sue_manual_class" value=""/>
<c:if test="${sueInvioWsDTO.invioSueWs}">
    <c:set var="field_sue_manual_class" value="hidden"/>
</c:if>

<h2 class="short" style="text-align:left">Invio pratica a Backoffice</h2>

<div class="content_sidebar">
    <div class="sidebar_left">
        <h3><spring:message code="pratica.comunicazione.dettaglio.identificativo"/> <strong>${pratica.identificativoPratica}</strong></h3>
        <div class="sidebar_elemento">
            ${pratica.oggettoPratica}
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.dataricezione"/>:</strong> <fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></p>
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.stato"/>:</strong> ${pratica.idStatoPratica.descrizione}</p>
            <c:choose>
                <c:when test="${not empty pratica.idModello}">
                    <p style="margin-top:20px;">
                        <a href="${downloadPraticaUrl}" class="scarica" target="_blank">
                            <spring:message code="pratica.comunicazione.scaricaIstanza"/>
                        </a>
                    </p>
                </c:when>
                <c:otherwise>
                    <span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="sidebar_center">
        <form action="${path}/pratica/insertpraticabogenova/salva.htm" method="post" class="uniForm inlineLabels comunicazione">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>                   
                    <li class="active">
                        <a href="#frame1">Comunicazione</a>
                    </li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div id="frame1" class="frame active ordinariaDiv">

                        <input type="hidden" name="idPratica" value="${pratica.idPratica}" />
                        <input type="hidden" name="idEvento" value="${evento_selezionato}" />
                        <div class="ctrlHolder">
                            <label >Vuoi fare l'invio automatico della pratica al Backoffice dell'Edilizia?</label>
                            <input type="radio" name="invioSueWs" id="invio_sue_ws_si" value="TRUE" <c:if test="${sueInvioWsDTO.invioSueWs}">checked="checked"</c:if>/><label for="invio_sue_ws_si" class="scelta" >SI'</label><br/>
                            <input type="radio" name="invioSueWs" id="invio_sue_ws_no" value="FALSE" <c:if test="${not sueInvioWsDTO.invioSueWs}">checked="checked"</c:if>/><label for="invio_sue_ws_no" class="scelta" >NO</label>
                            </div>
                            <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual">
                            <label for="codice_registro">Codice Registro</label>
                            <input type="text" id="codice_registro" name="codiceRegistro" class="textInput required" value="${sueInvioWsDTO.codiceRegistro}" />
                        </div>
                        <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual">
                            <label for="codice_fascicolo">Codice Fascicolo</label>
                            <input type="text" id="codice_fascicolo" name="codiceFascicolo" class="textInput required" value="${sueInvioWsDTO.codiceFascicolo}"/>
                        </div>
<!--                        <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual">
                            <label for="anno_riferimento">Anno Riferimento</label>
                            <input type="text" id="codice_fascicolo" name="annoRiferimento" class="textInput required" value="${sueInvioWsDTO.annoRiferimento}"/>
                        </div>-->
                              <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual" >
                                    <label for="anno_riferimento">Anno Riferimento</label>
                        <select id="annoRiferimentoBoGenova" name="annoRiferimento" class="textInput required">
                            <option  value="" ><spring:message code="ricerca.inizio.anno"/></option>
                            <c:forEach items="${anniRiferimento}" var="anno" begin="0">
                                <c:set var="selected" value=""/> 
                                <c:if test="${sueInvioWsDTO.annoRiferimento != null &&  sueInvioWsDTO.annoRiferimento == anno}">
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="${anno}" ${selected}>${anno}</option>
                            </c:forEach>
                        </select>
                             </div>
                        <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual">
                            <label for="data_fascicolo">Data Fascicolo</label>
                            <input type="text" id="data_fascicolo" name="dataFascicolo" class="textInput required dataPicker" value="<fmt:formatDate value="${sueInvioWsDTO.dataFascicolo}" type="date" pattern="dd/MM/yyyy"/>"/>
                        </div>
                        <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual">
                            <label for="responsabile_procedimento">Responsabile Fascicolo</label>
                            <input type="text" id="responsabile_procedimento" name="responsabileProcedimento" class="textInput required" value="${sueInvioWsDTO.responsabileProcedimento}"/>
                        </div>
                        <div class="ctrlHolder ${field_sue_manual_class} field_sue_manual">
                            <label for="ufficio_procedimento">Ufficio Fascicolo</label>
                            <input type="text" id="ufficio_procedimento" name="ufficioProcedimento" class="textInput required" value="${sueInvioWsDTO.ufficioProcedimento}"/>
                        </div>
                    </div>
                </div>
                <div class="buttonHolder">
                    <a href="${path}/pratica/evento/index.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
                    <button id="submit_sue_ws" type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.procedi"/></button>
                </div>
            </div>
        </form>
    </div>
    <div class="clear"></div>
</div>

