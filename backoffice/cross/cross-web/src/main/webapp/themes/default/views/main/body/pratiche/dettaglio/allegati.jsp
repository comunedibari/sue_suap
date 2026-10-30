
<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="it.wego.cross.utils.HtmlUtilities" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="it.wego.cross.session.MultiSubSessionManager" %>
<%@ page import="it.wego.cross.civilia.Costants" %>

<script type="text/javascript">
    var urlModificaDescrizioneAllegato = "${path}/pratiche/dettaglio/formAllegatiAjax.htm";
    var urlFirmaAllegato = "${path}/pratiche/dettaglio/formFirmaAllegatoAjax.htm";
    var urlCercaAllegatiProtocollo = "${path}/pratiche/dettaglio/formAllegatiProtocolloAjax.htm";
    var urlSalvaAllegatiProtocollo = "${path}/pratiche/dettaglio/formAllegatiProtocolloSalvaAjax.htm";
    var urlPraticaEvento = "${path}/pratica/comunicazione/dettaglio_evento.htm";
    
    function paperworkEventDetail(idPratica, idPraticaEvento) {
        var sessionId = "${MultiSubSessionManager.generateId()}";
        window.open(urlPraticaEvento+'?id_pratica='+idPratica+'&id_pratica_evento=' + idPraticaEvento+'&s_code='+sessionId+"&newTab=true", sessionId); 
        return false;
    }
    
    function modificaDescrizione(id, descrizione) {
        var wHeight = $(window).height() * 0.8;
        $("#windowModificaAllegato .descrizione").val($('<div/>').html(descrizione).text());
        $("#windowModificaAllegato .id_allegato").val(id);
        windowModificaAllegato(wHeight);
    }
    
    function modificaDescrizione2(id, row) {
        var wHeight = $(window).height() * 0.8;
        $("#windowModificaAllegato .descrizione").val($(row).closest('tr').find('td:eq(2)').text());
        $("#windowModificaAllegato .id_allegato").val(id);
        windowModificaAllegato(wHeight);
    }
    
    function firmaAllegato(id,row) {
        var wHeight = $(window).height() * 0.8;
       $("#windowFirmaAllegato .id_allegato").val(id);
        
        windowFirmaAllegato(wHeight,row);
    }
    function ricercaAllegati() {
        var wHeight = $(window).height() * 0.8;
        windowRicercaAllegati(wHeight);
        $(".tbal").find("tr:gt(0)").remove();
        $(".tbal").hide();
        $('.boxData').hide();
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
    function windowFirmaAllegato(wHeight,row) {
    	if ($(row).closest('tr').find('td:eq(3)').text().substring($(row).closest('tr').find('td:eq(3)').text().length - 3) == 'pdf' ||
    			 $(row).closest('tr').find('td:eq(3)').text().substring($(row).closest('tr').find('td:eq(3)').text().length - 3) == 'PDF')
    		$("#pades").prop('checked', true);
    	else{
    		$("#pades").prop('disabled', true);
    		$("#cades").prop('checked', true);
    	}
    		

        $('#windowFirmaAllegato').dialog({
            title: '<spring:message code="allegato.firma.descrizione.title"/>',
            modal: true,
            height: wHeight,
            width: '50%',
            close: function () {
                $("#windowFirmaAllegato .id_allegato").val("");
                $("#pades").prop('disabled', false);
            },
            buttons: {
                'Firma': function () {
                    firma();
                },
                'Annulla': function () {
                    $(this).dialog("close");
                }
            }
        });
    }

    function windowRicercaAllegati(wHeight) {
        $('#windowRicercaAllegati').dialog({
            title: '<spring:message code="allegato.protocollo.salva.title"/>',
            modal: true,
            height: wHeight,
            width: '50%',
            close: function () {
                $("#windowRicercaAllegati .id_allegato").val("");
                $("#windowRicercaAllegati .nuovaDescrizione").val("");
            },
            buttons: {
                'Cerca':{ 
                	id : 'cerca',
                	text: 'Cerca',
                	click: function () {
                    cerca();
                }},
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
                    var descrizione = $("<div>").text($("#windowModificaAllegato .nuovaDescrizione").val()).html();
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
  function firma() {
	 var idAllegato = $("#windowFirmaAllegato .id_allegato").val();
	 var cades = $("input[name='tipo_firma']:checked").val();
	 var username = $("#windowFirmaAllegato .signer").val();
	 var otp = $("#windowFirmaAllegato .otp").val();
	 	           $.ajax({
	                 url: urlFirmaAllegato,
	                 dataType: "json",
	                 type: "POST",
	                 data: {
	                     idAllegato: idAllegato,
	                     pratica: ${pratica.idPratica},
	                     cades: cades,
	                     username:username,
	                     otp:otp
	                 },
	                 success: function (data) {
	                     var messaggio = data.message;
	                     if (!data.success) {
	                         mostraMessaggioAjax(messaggio, "error");
	                     } else {
	                         $("#windowFirmaAllegato").dialog("close");
	                         mostraMessaggioAjax(messaggio, "success");
	                         $("table.master").append("<tr><td>Firma allegato</td><td>" + data.attributes.data + "</td><td>" + data.attributes.descrizione + "</td><td>" + data.attributes.nome + "</td>"
	                         + "<td><div style='text-align: center;'><a class='scarica2' href=/cross/download.htm?id_file=" + data.attributes.id + " target='_blank'> </a></div></td>"
	                         + "<td><div style='text-align: center;'>" 
					+ "<a class='icona_modifica' href='#'    onclick='modificaDescrizione2(" + data.attributes.id + ",this)'</a>"
			   		+ "</div></td><td style=background-color:#FFF>"
	 						+ "<div style='text-align: center;'>"
					+ "<a class='icona_firma' href='#' onclick='firmaAllegato(" + data.attributes.id + ",this)'></a>"
					+ "</div></td></tr>")
	                     }
	                 }
             });
        }
    
    function cerca() {
        var idAllegato = $("#windowRicercaAllegati .descrizione").val();
        var descrizione = $("#windowRicercaAllegati .nuovaDescrizione").val();
        $.ajax({
            url: urlCercaAllegatiProtocollo,
            dataType: "json",
            type: "POST",
            data: {
                action: "cercaAllegatiProtocollo",
                anno: idAllegato,
                idProtocollo: descrizione
            },
            success: function (data) {
                var messaggio = data.message;
                if (!data.success) {
                    mostraMessaggioAjax(messaggio, "error");
                } else {
                	all = data.attributes.allegati;
                	all = all.replaceAll('\\','');
                	all = all.replaceAll('"','');
                	all = all.replaceAll('[','');
                	all = all.replaceAll(']','');
                	all = all.split(',');
                	  $('.boxData').show();
                	  $('.dataProtocollo').val(data.attributes.dataProtocollo);
                	  $('.dataProtocolloH').val(data.attributes.dataProtocollo);
                	  $('.numeroProtocolloH').val(data.attributes.numeroProtocollo);
                	  $('.oggettoProtocollo').val(data.attributes.oggetto);
                	  $('.enteProtocollo').val(data.attributes.ente);
                	all.forEach(function(allegato){
                   //mostra lista allegati
                   $('.tbal').show();
                   $('.bt1').hide();
                   $('.bt2').show();
                   $('.tbal').append("<tr><td>" + allegato.split(';')[1].split('.')[0] + "</td>" +
   				"<td >"+ allegato.split(';')[1].split('.')[1] + "</td>" + 
				"<td >" + allegato.split(';')[2] + "</td>" +
				"<td>" +
					"<div style='text-align: center;'>" +
						"<a class='scarica2' " +
						"href=<%=path%>" + "/downloadA.htm?id_file=" + allegato.split(';')[0] + "/" + allegato.split(';')[1].replaceAll(' ','%20') + " " +
							
							"target='_blank'> </a>" +
					"</div>" +
				"</td>" +
					"<td >" +
						"<div style='text-align: center;'>" +
						"<input type='checkbox' value="+ allegato.split(';')[0] + "/" + allegato.split(';')[1].replaceAll(' ','%20') + ">" +
						"</div>" +
					"</td>" +
			"</tr>")
                   
                })
                $("#cerca").text('Salva');
                $("#cerca").unbind("click").click(function(){
                var res = {};
                res.dataProtocollo = data.attributes.dataProtocollo;
                res.numeroProtocollo = data.attributes.numeroProtocollo;
                var allegati = $(".tbal input:checkbox:checked").map(function(){
                    return $(this).val();
                  }).get();
                res.allegati = allegati;
                res = JSON.stringify(res);
                	$.ajax({
                        url: urlSalvaAllegatiProtocollo,
                        dataType: "json",
                        type: "POST",
                        data: {
                            action: "salvaAllegatiProtocollo",
                            pratica: ${pratica.idPratica},
                            res: res
                        },
                        success: function (data) {
                        	$("#windowRicercaAllegati").dialog("close");
                            mostraMessaggioAjax("salvataggio avvenuto con successo", "success");
                        	console.log("OK");
                        	location.reload();
                        }
                     })
                 });
                	
                }
            }
        });
    }
    
    $(document).ready(function(){
    	$("#downloadSelectedAttachments").click(function(e){
	    	e.preventDefault();
	    	if($('#attachments_detail_table [type="checkbox"]:checked').length > 0){
		    	$("#selected_attachments_div").empty()
		    	$('#attachments_detail_table [type="checkbox"]:checked').each(function(i,e){
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

<table cellspacing="0" cellpadding="0">

	<tr>
		<td>
			<div style="text-align: left;">
				<a class="scarica" 
					id="downloadSelectedAttachments" href="javascript:void()"
					> <spring:message code="pratica.comunicazione.dettaglio.allegati.download.selected" /></a>&nbsp;
				<a class="scarica"
					href="<%=path%>/downloadSR.htm?id_pratica=${pratica.idPratica}"
					target="_blank"> <spring:message code="pratica.dettaglio.allegati.registered.download" /></a>&nbsp;
				<a class="scarica"
					href="<%=path%>/downloadF.htm?id_pratica=${pratica.idPratica}"
					target="_blank"> <spring:message code="pratica.comunicazione.dettaglio.allegati.downloadAllAttachments" /></a>&nbsp;
					
				<a> <input type="checkbox" id="showRegistered"  /><spring:message code="pratica.dettaglio.allegati.registered.show" /></a>
			</div>
		</td>
	</tr>
</table>

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
    <c:if test="${!empty pratica.allegati}">
		<c:forEach items="${pratica.allegati}" var="allegato" begin="0">
			<%String colorIntegrazione = "#FFFFFF"; %>
			<%String colorCivilia = "#FFF1EF"; %>
			<%String colorDMS = "#E3F8E5"; %>

			<c:choose>
			   <c:when test="${allegato.provenienza eq 'CIVILIA'}"><% colorIntegrazione = "#FFF1EF"; %></c:when>
			   <c:when test="${allegato.provenienza eq 'DMS'}"><% colorIntegrazione = "#E3F8E5"; %></c:when>
			   <c:otherwise><% colorIntegrazione = "#FFFFFF"; %></c:otherwise>
			</c:choose>
							
			<c:if test="${allegato.descrizioneEvento == ('(proc) Invio Comunicazione / Integrazione')}">
				<%colorIntegrazione = "#82d676"; %>
			</c:if>
			<tr id="attachment_row_${allegato.id }" registered="${not empty allegato.protocollo}">
				<td style="background-color: <%=colorIntegrazione%>">
					<input type="checkbox" id="attachment_chk_${allegato.id }" value="${allegato.id }" />
				</td>
				<td style="background-color: <%=colorIntegrazione%>">
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
				</td>
				<td style="background-color: <%=colorIntegrazione%>"><span
					id="allegato_${allegato.id}">${allegato.descrizione}</span>
				</td>
				<td style="background-color: <%=colorIntegrazione%>">${allegato.nomeFile}</td>
				<td style="background-color: <%=colorIntegrazione%>"><fmt:formatNumber value="${allegato.dimensione/1024}" maxFractionDigits="0"/></td>

				
				<td style="background-color: <%= colorIntegrazione%>" class="td_attachments_options">
					<c:if test="${allegato.provenienza ne 'CIVILIA'}">
						<span>
							<a class="scarica2"
								href="<%=path%>/download.htm?id_file=${allegato.id}" title="<spring:message code="templates.gestione.documento.scarica"/>"
								target="_blank"> </a>
						</span>
					</c:if>
					<span>
						<c:if
							test="${allegato.nomeFile.toLowerCase().endsWith('.pdf') || allegato.nomeFile.toLowerCase().endsWith('.doc')
								|| allegato.nomeFile.toLowerCase().endsWith('.docx') || allegato.nomeFile.toLowerCase().endsWith('.xls')
								|| allegato.nomeFile.toLowerCase().endsWith('.xlsx') || allegato.nomeFile.toLowerCase().endsWith('.odt')
								|| allegato.nomeFile.toLowerCase().endsWith('.p7m')  || allegato.nomeFile.toLowerCase().endsWith('.ods')
								}">
								&nbsp;<a class="cerca_lente_dettaglio"
								href="<%=path%>/preview.htm?id_file=${allegato.id}"
								target="_blank" title="<spring:message code="pratica.comunicazione.dettaglio.allegati.preview" />"> </a>
							</c:if>
					</span>
					<c:if test="${!abilitazioneConsultazione && isAllegatoModificabile=='TRUE' && allegato.getProvenienza() == null}">
						&nbsp;<span>
								<a class="icona_modifica" href="#"
									onclick="modificaDescrizione('${allegato.id}', '${allegato.descrizione}')"
									 title="<spring:message code="allegato.modifica.descrizione.title" />">
								</a>
							</span>
						&nbsp;<span>
							<a class="icona_firma" href="#"
								onclick="firmaAllegato('${allegato.id}',this)"
								 title="<spring:message code="protocollo.dettaglio.allegati.firma" />">
							</a>
						</span>
					</c:if>
					<c:if test="${not empty allegato.idPraticaEvento}">
						&nbsp;<span>
								<a class="cerca_lente_dettaglio_2" href="#"
									onclick="paperworkEventDetail('${pratica.idPratica}','${allegato.idPraticaEvento}')" 
									title="<spring:message code="comunicazione.inarrivo.button.dettaglio" />">
								</a>
							</span>
						</span>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</c:if>

	<c:if test="${empty pratica.allegati}">
		<tr>
			<td colspan="5"><spring:message code="pratica.comunicazione.dettaglio.allegati.noresult" /></td>
		</tr>
	</c:if>
	</tbody>
</table>

<div id="windowModificaAllegato" class="modal-content">
	<input class="input_anagrafica_disable id_allegato hidden" type="text"
		value="">
	<div class="box_allegato ctrlHolder">
		<label class="required"> <spring:message
				code="allegato.modifica.descrizione.old" />
		</label> <input class="input_anagrafica_disable descrizione" type="text"
			disabled="" value="">
	</div>
	<div class="box_allegato ctrlHolder">
		<label class="required"> <spring:message
				code="allegato.modifica.descrizione.new" />
		</label> <input class="input_anagrafica_disable nuovaDescrizione" type="text"
			value="">
	</div>
</div>

<div id="windowFirmaAllegato" class="modal-content">
	<input class="input_anagrafica id_allegato hidden" type="text" value="">
	<div class="box_allegato ctrlHolder">
		<label class="required"> <spring:message
				code="allegato.firma.signer" />
		</label> <input class="input_anagrafica_disable signer" type="text" value="">
	</div>
	<div class="box_allegato ctrlHolder">
		<label class="required"> <spring:message
				code="allegato.firma.otp" />
		</label> <input class="input_anagrafica_disable otp" type="text" value="">
	</div>
	<p>
		<label class="required">Modalità firma:</label>
	</p>
	<input type="radio" name="tipo_firma" class="cades" id="cades" checked
		value="cades"><label><spring:message
			code="allegato.firma.cades" /></label><br> <input type="radio"
		name="tipo_firma" id="pades" value="pades"><label><spring:message
			code="allegato.firma.pades" /></label>
</div>

<div id="windowRicercaAllegati" class="modal-content">

	<input class="input_anagrafica_disable id_allegato hidden" type="text"
		value="">
	<div class="box_allegato ctrlHolder">
		<label class="required"> <spring:message
				code="ricerca.protocollo.anno" />
		</label> <select class="input_anagrafica_disable descrizione" type="text"
			value="">
			<option value="2018">2018</option>
			<option value="2019">2019</option>
			<option value="2020">2020</option>
		</select>
	</div>
	<div class="box_allegato ctrlHolder">
		<label class="required"> <spring:message
				code="ricerca.protocollo.numero" />
		</label> <input class="input_anagrafica_disable nuovaDescrizione" type="text"
			value="">
	</div>
	<div class="box_allegato ctrlHolder boxData" style="display: none;">
		<label class="required"> <spring:message
				code="ricerca.protocollo.ente" />
		</label> <input class="input_anagrafica_disable enteProtocollo" type="text"
			value="">
	</div>
	<div class="box_allegato ctrlHolder boxData" style="display: none;">
		<label class="required"> <spring:message
				code="ricerca.protocollo.oggetto" />
		</label> <input class="input_anagrafica_disable oggettoProtocollo" type="text"
			value="">
	</div>
	<div class="box_allegato ctrlHolder boxData" style="display: none;">
		<label class="required"> <spring:message
				code="ricerca.protocollo.data" />
		</label> <input class="input_anagrafica_disable dataProtocollo" type="text"
			value="">
	</div>
	<table cellspacing="0" cellpadding="0" class="master tbal"
		style="display: none;">
		<tr>
			<th><spring:message
					code="pratica.comunicazione.dettaglio.allegati.nomefile" /></th>
			<th><spring:message
					code="pratica.comunicazione.dettaglio.allegati.estensione" /></th>
			<th><spring:message
					code="pratica.comunicazione.dettaglio.allegati.dimensione" /></th>
			<th><spring:message
					code="pratica.comunicazione.dettaglio.allegati.download.file" /></th>
			<c:if test="${isAllegatoModificabile=='TRUE'}">
				<th><spring:message
						code="pratica.comunicazione.dettaglio.allegati.modifica" /></th>
			</c:if>
			<c:if test="${isAllegatoModificabile=='TRUE'}">
				<th><spring:message
						code="pratica.comunicazione.dettaglio.allegati.firma" /></th>
			</c:if>
		</tr>

	</table>
</div>

<form id="selected_attachments_form" class="hidden" action="<%=path%>/downloadS.htm" method="get" target="_blank">
	<input type="hidden" name="id_pratica" value="${pratica.idPratica}" />
	<div id="selected_attachments_div"></div>
</form>
