<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<input type="submit" name="submit" class="nascondiutente insersisciNota crea_nuovo_evento" value="<spring:message code="pratica.comunicazione.dettaglio.note.inserisci"/>"/>
<table cellspacing="0" cellpadding="0" class="master elencoNote" >
    <tr>
        <th><spring:message code="pratica.comunicazione.dettaglio.note.Data"/></th>
        <th><spring:message code="pratica.comunicazione.dettaglio.note.Utente"/></th>
        <th><spring:message code="pratica.comunicazione.dettaglio.note.Testo"/></th>
        <th>&nbsp;</th>
    </tr>
    <c:if test="${!empty pratica.notePratica}">
        <c:forEach items="${pratica.notePratica}" var="nota" begin="0">
            <tr>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${nota.dataInserimento}" /></td>       
                <td>${nota.idUtente.cognome} ${nota.idUtente.nome}</td>
                <c:set var="testoNota" value="${nota.testo}"/>
                <c:set var="testoNotaBreve" value="${fn:substring(testoNota, 1, 80)}" />
                <td>${testoNotaBreve}</td>
                <td>
                    <div class="action">
                        <button id="${nota.idNotePratica}" class="cerca_lente dettaglioNota crea_nuovo_evento">Dettaglio</button>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty pratica.notePratica}">
        <tr>
            <td colspan="4"><spring:message code="pratica.comunicazione.dettaglio.note.noresult"/></td>
        </tr>
    </c:if>
</table>
<div class="hidden" id="notaContainer">
    <div class="inputsNota">
        <div>
            <lable><spring:message code="pratica.nota.scrivi"/></lable>
        </div>
        <div>
            <textarea name="testo" id="testoNota" value="" style="width: 97%; height: 250px;"></textarea>
        </div>
    </div>
</div>

<script id="template-dettaglio-nota-pratica" type="text/x-handlebars-template">
    <div>
    <div class="ui-dialog-content ui-widget-content">
    <div class="ctrlHolder dettaglioAnagraficaContainer ui-dialog-content ui-widget-content">
    <fieldset class="fieldsetComunicazione">
    <legend>Dettaglio nota</legend>
    <div>
    <div class="detailKey" style="display: block;float: left;width: 45%;">
    Data
    </div>
    <div class="detailValue" style="background: none repeat scroll 0 0 #EEEEEE; display: block;float: left; width: 45%;">
    <span class="value">{{dataInserimento}}</span>
    </div>

    <div class="detailKey" style="display: block;float: left;width: 45%;">
    Utente
    </div>
    <div class="detailValue"  style="background: none repeat scroll 0 0 #EEEEEE; display: block;float: left; width: 45%;"><span class="value">{{desUtente}}</span></div>

    <div class="">
    Dettaglio:
    </div>
    <div >
    {{testo}}
    </div>
    </div>
    </fieldset>
    </div>
    </div>
    </div>
</script>

<script id="template-dettaglio-nota-pratica-summary" type="text/x-handlebars-template">
    <tr>
        <td>{{dataInserimento}}</td>       
        <td>{{desUtente}}</td>
        <td>{{testo}}</td>
        <td>
            <div class="action">
                <button id="nota-pratica-{{idNota}}" class="cerca_lente dettaglioNota crea_nuovo_evento ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-focus">Dettaglio</button>
            </div>
        </td>
    </tr>
</script>

<script>
    $(document).ready(function() {


        var notaPraticaSummaryTemplateHtml = $("#template-dettaglio-nota-pratica-summary").html();
        var notaPraticaSummaryTemplate = Handlebars.compile(notaPraticaSummaryTemplateHtml);

        $(".insersisciNota").click(function() {
            $('#testoNota').val();
            $('#notaContainer').dialog({
                modal: true,
                title: "Aggiungi nota:",
                width: 500,
                dialogClass: "",
                buttons: {
                    Ok: function() {
                        $.ajax({
                            url: '${path}/pratica/note/aggiungi/ajax.htm',
                            dataType: "json",
                            data: {
                                idPratica: "${pratica.idPratica}",
                                testo: $('#testoNota').val()
                            },
                            success: function(data) {
                                $("#notaContainer").dialog("close");
                                if (data.success) {
                                    var notaPraticaSummaryHtml = notaPraticaSummaryTemplate(data.attributes);
                                    $('.elencoNote tr:last').after(notaPraticaSummaryHtml);
                                    $('#nota-pratica-' + data.attributes.idNota).click(function() {
                                        showNotaDettaglio(data.attributes.idNota);
                                    });
                                } else {
                                    var messaggio = data.messages;
                                    mostraMessaggioAjax(messaggio, "error");
                                }
                            }
                        });
                    },
                    Annulla: function() {
                        $(this).dialog('close');
                    }
                }
            });
        });

        $(".dettaglioNota").click(function() {
            var id = $(this).attr("id");
            showNotaDettaglio(id);
        });
    });

    function showNotaDettaglio(id) {
        var notaPraticaTemplateHtml = $("#template-dettaglio-nota-pratica").html();
        var notaPraticaTemplate = Handlebars.compile(notaPraticaTemplateHtml);
        $.ajax({
            url: '${path}/pratica/note/get/ajax.htm',
            dataType: "json",
            data: {
                idNota: id
            },
            success: function(data) {
                var notaPraticaDettaglioHtml = notaPraticaTemplate(data);
                wgf.utils.showHtmlMessageDialog('Dettaglio nota', notaPraticaDettaglioHtml);
            }
        });
    }

</script>