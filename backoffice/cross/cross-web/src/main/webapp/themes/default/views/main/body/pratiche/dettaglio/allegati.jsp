<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    var urlModificaDescrizioneAllegato = "${path}/pratiche/dettaglio/formAllegatiAjax.htm";
    function modificaDescrizione(id, descrizione) {
        var wHeight = $(window).height() * 0.8;
        $("#windowModificaAllegato .descrizione").val(descrizione);
        $("#windowModificaAllegato .id_allegato").val(id);
        windowModificaAllegato(wHeight);
    }
    function windowModificaAllegato(wHeight) {
        $('#windowModificaAllegato').dialog({
            title: '<spring:message code="allegato.modifica.descrizione.title"/>',
            modal: true,
            height: wHeight,
            width: '50%',
            close: function () {
                $("#windowModificaAllegato .id_allegato").val("");
                $("#windowModificaAllegato .nuovaDescrizione").val("");
            },
            buttons: {
                'Modifica': function () {
                    aggiorna();
                },
                'Annulla': function () {
                    $(this).dialog("close");
                }
            }
        });
    }
    function aggiorna() {
        var div = $("<div>");
        div.html("Confermi ?");
        $(div).dialog({
            modal: true,
            title: "Conferma modifica descrizione",
            buttons: {
                Ok: function () {
                    var idAllegato = $("#windowModificaAllegato .id_allegato").val();
                    var descrizione = $("#windowModificaAllegato .nuovaDescrizione").val();
                    $.ajax({
                        url: urlModificaDescrizioneAllegato,
                        dataType: "json",
                        type: "POST",
                        data: {
                            action: "modificaDescrizione",
                            idAllegato: idAllegato,
                            descrizione: descrizione
                        },
                        success: function (data) {
                            var messaggio = data.message;
                            if (!data.success) {
                                mostraMessaggioAjax(messaggio, "error");
                            } else {
                                $("#windowModificaAllegato").dialog("close");
                                $("#allegato_" + idAllegato).html(descrizione);
                                mostraMessaggioAjax(messaggio, "success");
                            }
                        }
                    });
                    $(div).dialog('close');
                },
                Annulla: function () {
                    $(div).dialog('close');
                }
            }
        });
    }
</script>  
<table cellspacing="0" cellpadding="0" class="master">
    <tr>
    	<th><spring:message code="pratica.comunicazione.dettaglio.allegati.descrizione.evento"/></th>
        <th><spring:message code="pratica.comunicazione.dettaglio.allegati.dataevento"/></th>
        <th><spring:message code="pratica.comunicazione.dettaglio.allegati.descrizione"/></th>
        <th><spring:message code="pratica.comunicazione.dettaglio.allegati.nomefile"/></th>
        <th><spring:message code="pratica.comunicazione.dettaglio.allegati.download"/></th>
            <c:if test="${isAllegatoModificabile=='TRUE'}">
            <th>&nbsp;</th>
            </c:if>
    </tr>
    
    <c:if test="${!empty pratica.allegati}">
		<c:forEach items="${pratica.allegati}" var="allegato" begin="0">
			<%String colorIntegrazione = "#FFFFFF"; %>
			<c:if test="${allegato.descrizioneEvento == ('(proc) Invio Comunicazione / Integrazione')}">
				<%colorIntegrazione = "#82d676"; %>
			</c:if>
			<tr>
				<td style="background-color: <%= colorIntegrazione%>">${allegato.descrizioneEvento}</td>
				<td style="background-color: <%= colorIntegrazione%>"><fmt:formatDate pattern="dd/MM/yyyy" value="${allegato.dataEvento}" /></td>
				<td style="background-color: <%= colorIntegrazione%>"><span	id="allegato_${allegato.id}">${allegato.descrizione}</span></td>
				<td style="background-color: <%= colorIntegrazione%>">${allegato.nomeFile}</td>
				<td style="background-color: <%= colorIntegrazione%>">
					<div style="text-align: center;">
						<a class="scarica2"
							href="<%=path%>/download.htm?id_file=${allegato.id}"
							target="_blank"> </a>
					</div>
				</td>
				<c:if test="${isAllegatoModificabile=='TRUE'}">
					<td style="background-color: <%= colorIntegrazione%>">
						<div style="text-align: center;">
							<a class="icona_modifica" href="#"
								onclick="modificaDescrizione('${allegato.id}', '${allegato.descrizione}')">
							</a>
						</div>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</c:if>
    <c:if test="${empty pratica.allegati}">
        <tr>
            <td colspan="4"><spring:message code="pratica.comunicazione.dettaglio.allegati.noresult"/></td>
        </tr>
    </c:if>
</table>

<div id="windowModificaAllegato" class="modal-content">
    <input class="input_anagrafica_disable id_allegato hidden" type="text" value="">
    <div class="box_allegato ctrlHolder">
        <label class="required">
            <spring:message code="allegato.modifica.descrizione.old"/>
        </label>
        <input class="input_anagrafica_disable descrizione" type="text" disabled="" value="">
    </div>
    <div class="box_allegato ctrlHolder">
        <label class="required">
            <spring:message code="allegato.modifica.descrizione.new"/>
        </label>
        <input class="input_anagrafica_disable nuovaDescrizione" type="text" value="">
    </div>        
</div>  