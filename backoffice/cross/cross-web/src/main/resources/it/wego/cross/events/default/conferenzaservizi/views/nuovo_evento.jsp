<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
    $(document).ready(function () {

        $(".datePicker").datepicker({
            dateFormat: 'dd/mm/yy'
        });

    });
    function triggersubmit() {
        var div = $("<div>");
        var messaggio = "";
        var idprot = $("#protocollo input").val();
        var tipoprotocollo = $("#creaEvento").attr("tipoprotocollo");
        messaggio = "<spring:message code="evento.crea.conferma"/>";
        if (tipoprotocollo === "S") {
            messaggio += "<spring:message code="evento.crea.conferma.protocollo"/>";
            if (idprot === "") {
                messaggio += " " + "<spring:message code="evento.crea.conferma.protocolloautomatico"/>";
            }
        }
        div.html(messaggio);
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma creazione nuovo evento",
                    buttons: {
                        Ok: function() {
                            $(div).dialog('close');
                            $('button[name="submitaction"]').attr('disabled','disabled');
                            $("#creazioneEventoForm").submit();
                            return true;
                        },
                        Annulla: function() {
                            $(this).dialog('close');
                            return false;
                        }
                    }
                });
    }    
</script>

<c:url value="/download/pratica.htm" var="downloadPraticaUrl">
    <c:param name="id_pratica" value="${pratica.idPratica}"/>
</c:url>

<c:url value="/pratica/attivaConferenzaServizi/azione/salva.htm" var="submitEventoUrl">
</c:url>


<tiles:insertAttribute name="body_error" />


<h2 class="short" style="text-align:left">${processo_evento.desEvento}</h2>

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
                        </a>
                    </p>
                </c:when>
                <c:otherwise>
                    <span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="inlineLabels">
        <fieldset class="fieldsetComunicazione">
            <div class="sidebar_center">
                <form:form id="creazioneEventoForm" action="${submitEventoUrl}" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data" commandName="praticaOrganoCollegiale">
                    <div class="page-control" data-role="page-control">
                        <!--<span class="menu-pull"></span> 
                        <div class="menu-pull-bar"></div>-->
                        <div class="ctrlHolder dettaglio_liv_0">
                            <form:label class="required" path="idOrganiCollegiali"><spring:message code="conferenzaservizi.evento.organocollegiale"/></form:label>
                            <form:select id="idOrganiCollegiali" path="idOrganiCollegiali">
                                <form:option value=""><spring:message code="conferenzaservizi.evento.organocollegiale.select"/></form:option>
                                <form:options items="${organiCollegialiList}" itemLabel="desOrganoCollegiale" itemValue="idOrganiCollegiali"/>
                            </form:select>
                        </div>
                        <div class="ctrlHolder dettaglio_liv_0">
                            <form:label cssClass="required" path="dataRichiesta"><spring:message code="conferenzaservizi.evento.dataattivazione"/></form:label> 
                            <form:input path="dataRichiesta" cssClass="textInput required datePicker" readonly="true"/>
                            <p class="formHint"></p>
                        </div>
                    </div>
                    <div class="buttonHolder">
                        <a href="${path}/pratica/evento/index.htm?id_pratica=${pratica.idPratica}" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>                        
                        <form:hidden path="idPratica"/>
                        <form:hidden path="idEvento"/>
                        <button tipoprotocollo="${tipoprotocollo}" value="<spring:message code="pratica.comunicazione.evento.procedi"/>" id="creaEvento" class="primaryAction" name="submitaction" onclick="triggersubmit();
                                return false;">
                            <spring:message code="pratica.comunicazione.evento.procedi"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </fieldset>
    </div>
    <div class="clear"></div>
</div>
