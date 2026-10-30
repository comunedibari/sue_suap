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
    $(document).ready(function(){
    	$("#downloadSelectedAttachments").click(function(e){
	    	e.preventDefault();
	    	if($('#attachments_detail_table [type="checkbox"]:checked').length > 0){
		    	$("#selected_attachments_div").empty()
		    	$('#attachments_detail_table [type="checkbox"]').each(function(i,e){
		    		if($(e).is(":checked"))
		    			$("#selected_attachments_div").append('<input type="hidden" name="id_files" value="'+$(e).val()+'" />');
		    	});
				    	
		    	$("#selected_attachments_form").trigger('submit');
	    	} else
	    		alert('<spring:message code="pratica.dettaglio.allegati.errore.selezione" />')
	    });

    	$("#showRegistered").change(function(){
    		$('#attachments_detail_table tbody input:checked').prop("checked", false).removeAttr("checked");
			if($(this).is(":checked"))
    			$('#attachments_detail_table tbody tr[registered="false"]').addClass("hidden");
	    	else
				$('#attachments_detail_table tbody tr').removeClass("hidden");
    	})
    });
</script>  

<div class="inlineLabels">
	<div style="text-align: left;">
    	<a class="scarica" 
			id="downloadSelectedAttachments" href="javascript:void()"
			> <spring:message code="pratica.comunicazione.dettaglio.allegati.download.selected" /></a>&nbsp;
		<a class="scarica"
			href="<%=path%>/downloadSR.htm?id_pratica=${pratica.idPratica}"
			target="_blank"> <spring:message code="pratica.dettaglio.allegati.registered.download" /></a>&nbsp;
	    <a class="scarica"
            href="<%=path%>/downloadF.htm?id_pratica=${pratica.idPratica}"
            target="_blank"> <spring:message code="pratica.comunicazione.dettaglio.allegati.downloadAllAttachments" /></a>
			
		<a> <input type="checkbox" id="showRegistered"  /><spring:message code="pratica.dettaglio.allegati.registered.show" /></a>
    </div>
    <table id="attachments_detail_table" cellspacing="0" cellpadding="0" class="master">
    	<thead>
	     	<tr>
				<th class="width1"></th>
				<th class="width25"><spring:message
						code="pratica.comunicazione.dettaglio.allegati.descrizione.evento" /></th>
				<th class="width25"><spring:message
						code="pratica.comunicazione.dettaglio.allegati.descrizione.allegato" /></th>
				<th class="width15"><spring:message
						code="pratica.comunicazione.dettaglio.allegati.nomefile" /></th>
				<th class="width5"><spring:message
					code="pratica.comunicazione.dettaglio.allegati.dimensione.kb" /></th>
				<th class="width20"><spring:message
						code="pratica.comunicazione.dettaglio.allegati.azioni" /></th>
		    </tr>
        </thead>
        <tbody>
	        <c:if test="${not empty pratica.allegati}">
	            <c:forEach items="${pratica.allegati}" var="allegato" begin="0">
	            <%String colorIntegrazione = "#FFFFFF"; %>
				<c:if test="${allegato.descrizioneEvento == ('(proc) Invio Comunicazione / Integrazione')}">
					<%colorIntegrazione = "#82d676"; %>
				</c:if>
	                <tr id="attachment_row_${allegato.id }" registered="${not empty allegato.protocollo}">
						<td style="background-color: <%=colorIntegrazione%>">
							<input type="checkbox" id="attachment_chk_${allegato.id }" value="${allegato.id }" />
						</td>
	                	<td style="background-color: <%= colorIntegrazione%>">
		                	<span id="allegato_${allegato.id}">
		                		<span>
									<span><spring:message code="pratica.comunicazione.dettaglio.allegati.tipo" />:</span>
									<span>${allegato.descrizioneEvento}</span><br/>
									<span><spring:message code="pratica.comunicazione.dettaglio.note.Data" />:</span>
									<span><fmt:formatDate pattern="dd/MM/yyyy" value="${allegato.dataEvento}" /></span><br/>
									<span><spring:message code="pratica.comunicazione.dettaglio.protocollo" />:</span>
									<span>${allegato.protocollo}</span><br/>
									<span><spring:message code="pratica.comunicazione.dettaglio.protocollo.data" />:</span>
									<span><fmt:formatDate pattern="dd/MM/yyyy" value="${allegato.dataProtocollo}" /></span><br/>
									<span><spring:message code="pratica.comunicazione.dettaglio.allegati.operatore" />:</span>
									<c:if test="${not empty allegato.operatore}">
										<span>${allegato.operatore.cognome} ${allegato.operatore.nome}</span><br/>
									</c:if>
								</span>
		                	</span>
	                	</td>
	                	<td style="background-color: <%= colorIntegrazione%>"><span id="allegato_${allegato.id}">${allegato.descrizione}</span></td>
	                    <td style="background-color: <%= colorIntegrazione%>">${allegato.nomeFile}</td>
	                    <td style="background-color: <%= colorIntegrazione%>"><fmt:formatNumber value="${allegato.dimensione/1024}" maxFractionDigits="0"/></td>
	                    <td style="background-color: <%= colorIntegrazione%>" class="td_attachments_options">
	                        <span style="text-align: center;">
	                            <a class="scarica2" href="${path}/download.htm?id_file=${allegato.id}" target="_blank" title="<spring:message code="templates.gestione.documento.scarica"/>"></a>
	                        </span>
	                    	<span style="text-align: center;">
							<c:if test="${allegato.nomeFile.toLowerCase().endsWith('.pdf') || allegato.nomeFile.toLowerCase().endsWith('.doc')
									|| allegato.nomeFile.toLowerCase().endsWith('.docx') || allegato.nomeFile.toLowerCase().endsWith('.xls')
									|| allegato.nomeFile.toLowerCase().endsWith('.xlsx') || allegato.nomeFile.toLowerCase().endsWith('.odt')
									|| allegato.nomeFile.toLowerCase().endsWith('.p7m')  || allegato.nomeFile.toLowerCase().endsWith('.ods')
									}">&nbsp;<span><a class="cerca_lente_dettaglio"
											href="<%=path%>/preview.htm?id_file=${allegato.id}"
											target="_blank" title="<spring:message code="pratica.comunicazione.dettaglio.allegati.preview" />"> </a>
											</span>
							</c:if>
		                    <c:if test="${isAllegatoModificabile=='TRUE'}">
		                        &nbsp;&nbsp;<span style="text-align: center;">
		                            <a class="icona_modifica" href="#" onclick="modificaDescrizione('${allegato.id}', '${allegato.descrizione}')"
		                            title="<spring:message code="allegato.modifica.descrizione.title" />"> </a>
		                        </span>
		                    </c:if>
						</td>
	                </tr>
	            </c:forEach>
	        </c:if>
	        <c:if test="${empty pratica.allegati}">
	            <tr>
	                <td colspan="5"><spring:message code="pratica.comunicazione.dettaglio.allegati.noresult"/></td>
	            </tr>
	        </c:if>
        </tbody>
    </table>
</div>            

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
<form id="selected_attachments_form" class="hidden" action="<%=path%>/downloadS.htm" method="get" target="_blank">
	<input type="hidden" name="id_pratica" value="${pratica.idPratica}" />
	<div id="selected_attachments_div"></div>
</form> 