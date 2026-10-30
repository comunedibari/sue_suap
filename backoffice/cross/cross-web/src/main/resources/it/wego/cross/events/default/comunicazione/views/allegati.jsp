<%
    String path = request.getContextPath();
%>
<%String colorIntegrazione = "#FFFFFF"; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.text.NumberFormat" %>

<div id="esitoPanel" style="display:none">
    <div class="uniForm">
        <div>
            <div>
                <h3></h3>
                <span id="esitoPanelMessage">  </span>
            </div>
        </div>
    </div>
</div>

<div class="ctrlHolder dettaglio_liv_0">

	<script type="text/javascript">

		var j = $('.nuovoAllegato').length;
        var urlFirmaAllegato = "${path}/pratica/comunicazione/index/formFirmaAllegatoAjax.htm";
        var urlFirmaAllegato2 = "${path}/pratica/comunicazione/index/formFirmaAllegatoAjax2.htm";

        function firmaAllegato(id,row) {
            var wHeight = $(window).height() * 0.8;
           $("#windowFirmaAllegato .id_allegato").val(id);
            
            windowFirmaAllegato(wHeight,row);
        }
        
        function firmaAllegato2(id,row) {
            var wHeight = $(window).height() * 0.8;
           $("#windowFirmaAllegato2 .id_allegato").val(id);
            
            windowFirmaAllegato2(wHeight,id,row);
        }
        
        function windowFirmaAllegato(wHeight,row) {
        	if ($(row).closest('tr').find('td:eq(1)').text().substring($(row).closest('tr').find('td:eq(1)').text().length - 3) == 'pdf' ||
       			 $(row).closest('tr').find('td:eq(1)').text().substring($(row).closest('tr').find('td:eq(1)').text().length - 3) == 'PDF')
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
        
        function windowFirmaAllegato2(wHeight,id,row) {
        	if ($(row).closest('tr').find('td:eq(1)').text().substring($(row).closest('tr').find('td:eq(1)').text().length - 3) == 'pdf' ||
       			 $(row).closest('tr').find('td:eq(1)').text().substring($(row).closest('tr').find('td:eq(1)').text().length - 3) == 'PDF')
       		$("#pades2").prop('checked', true);
       	else{
       		$("#pades2").prop('disabled', true);
       		$("#cades2").prop('checked', true);
       	}
            $('#windowFirmaAllegato2').dialog({
                title: '<spring:message code="allegato.firma.descrizione.title"/>',
                modal: true,
                height: wHeight,
                width: '50%',
                close: function () {
                    $("#windowFirmaAllegato2 .id_allegato").val("");
                    $("#pades2").prop('disabled', false);
                },
                buttons: {
                    'Firma': function () {
                        firma2(id);
                    },
                    'Annulla': function () {
                        $(this).dialog("close");
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
                                	 var j = $('.documenti_allegati').length;
                                    $("#windowFirmaAllegato").dialog("close");
                                    mostraMessaggioAjax(messaggio, "success");
                                    $("#allegatiEmailTable").append("<tr><td>" + data.attributes.descrizione + "</td><td>" + data.attributes.nome + "</td>"
                                    + "<td style='background-image: url(images/bottone_gestione_flusso.png)'>"     
                                    + "<div class=" + data.attributes.tipoFileCode + " style='text-align: center;'></div></td>"
                                    + "<td><span style='text-align: center;'><a class='scarica' href=/cross/download.htm?id_file=" + data.attributes.id + " target='_blank'>Scarica file</a></span></td>"
                                    + "<td><input type='checkbox' name='allegatiPresenti[" + j + "].idAllegato' id='allegatiPresenti " + j + ".idAllegato1' class='documenti_allegati' value=" + data.attributes.id + ">"
                                    +  "<input type='hidden' name='_allegatiPresenti[" + j + "].idAllegato' value='on'></td>"
                                    + "<td><input id='allegatiPresenti" + j + ".principale1' name='allegatiPresenti[" + j + "].principale' class='documenti_allegati_principale' type='checkbox' value='true' >"
                                    + "<input type='hidden' name='_allegatiPresenti[" + j + "].principale' value='on'></td><td style=background-color:#FFF>"
            						+ "<div style='text-align: center;'>"
        							+ "<a class='icona_modifica' href='#' onclick='firmaAllegato(" + data.attributes.id + ",this)'></a>"
        							+ "</div></td></tr>")
                                }
                            }
                        });
        }
        
        
        function firma2(id) {
        	
            var idAllegato = $("#windowFirmaAllegato2 .id_allegato").val();
            var cades = $("input[name='tipo_firma2']:checked").val();
            var username = $("#windowFirmaAllegato2 .signer").val();
            var nomeFile = $("input[name='allegatiManuali[" + id + "].nomeFile']").val(); 
        	var tipoFile = $("input[name='allegatiManuali[" + id + "].tipoFile']").val();
        	var pathFileI = $("input[name='allegatiManuali[" + id + "].pathFile']").val();
        	var descrizione = $("input[name='allegatiManuali[" + id + "].descrizione']").val();
            var otp = $("#windowFirmaAllegato2 .otp").val();
            	           $.ajax({
                            url: urlFirmaAllegato2,
                            dataType: "json",
                            type: "POST",
                            data: {
                                 nomeFile : nomeFile,
                        		 tipoFile : tipoFile,
                        		pathFileI : pathFileI,
                        		 descrizione : descrizione,
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
                                	 var j = $('.documenti_allegati').length;
                                    $("#windowFirmaAllegato2").dialog("close");
                                    mostraMessaggioAjax(messaggio, "success");
                                    $("#allegatiEmailTable").append("<tr><td>" + data.attributes.descrizione + "</td><td>" + data.attributes.nome + "</td>"
                                    + "<td style='background-image: url(images/bottone_gestione_flusso.png)'>"     
                                    + "<div class=" + data.attributes.tipoFileCode + " style='text-align: center;'></div></td>"
                                    + "<td><span style='text-align: center;'><a class='scarica' href=/cross/download.htm?id_file=" + data.attributes.id + " target='_blank'>Scarica file</a></span></td>"
                                    + "<td><input type='checkbox' name='allegatiPresenti[" + j + "].idAllegato' id='allegatiPresenti " + j + ".idAllegato1' class='documenti_allegati' value=" + data.attributes.id + ">"
                                    +  "<input type='hidden' name='_allegatiPresenti[" + j + "].idAllegato' value='on'></td>"
                                    + "<td><input id='allegatiPresenti" + j + ".principale1' name='allegatiPresenti[" + j + "].principale' class='documenti_allegati_principale' type='checkbox' value='true' >"
                                    + "<input type='hidden' name='_allegatiPresenti[" + j + "].principale' value='on'></td><td style=background-color:#FFF>"
            						+ "<div style='text-align: center;'>"
        							+ "<a class='icona_modifica' href='#' onclick='firmaAllegato(" + data.attributes.id + ",this)'></a>"
        							+ "</div></td></tr>")
                                }
                            }
                        });
        }
        
        
        
        
        function mostraMessaggioAjax(messaggio, result) {
            if (!result) {
                result = 'success';
            }
            if(result==='success'){$("#esitoPanel h3").text('Operazione eseguita con successo:');}
            if(result==='warning'){$("#esitoPanel h3").text('Attenzione:');}
            if(result==='error'){$("#esitoPanel h3").text('Si è verificato un errore:');}
            
            $("#esitoPanelMessage").text(messaggio);
            
            
            $("#esitoPanel").show();
            
            $("#esitoPanel>div>div").removeClass();
            $("#esitoPanel>div>div").addClass("message");
            $("#esitoPanel>div>div").addClass(result);
            
            setTimeout(function() {
                var selectedEffect = 'blind';
                var options = {};
                $("#esitoPanel").hide(selectedEffect, options, 500);
            }, 5000);
        }
        
        
        
        function rimuoviAllegato(row) {
        	attachmentsManager.setAttachment($(row).attr("att_id"), null, false, true);
            $(row.parentNode.parentNode).remove();
            var emptyrow = '<tr style="display:none" class="nuovoAllegato"></tr>';
            //***********************************************
            //fa IN MODO CHE IL NUMERO DI OGGETTI "ALLEGATIMAILTABLE" NON DIMINUISCA QUANDO SE NE CANCELLA UNO, QUESTO SERVE PER ESSERE SICURI DI NON CREARE OGGETTI CON LO STESSO NOME DOPO UN ERRORE E LA DISTRUZIONE DI UNO
            $("#allegatiEmailTable > tbody").append(emptyrow);
            //***********************************************
            j = $('.allegatoEmail').length;
            for (var i = 0; i < j; i++) {
                $('.allegatoEmail')[i].value = i;
            }
        }
        $(document).ready(function(){
            $('#uploadallegati').fileupload({
                dataType: 'json',
                done: function(e, data) {
                    enableSubmit();
                    $.each(data.result, function(index, file) {
						if (file.name === "_ERRORE_ERRORE_") {
							alert("Si è verificato un errore nel salvataggio temporaneo di uno o più files. Si prega di controllare nella tabella quali file sono stati caricati correttamente e provvedere a ricaricare quelli mancanti. Contattare l'amministratore qualora il problema dovesse ripresentarsi. ");
                            return false;
                        }

                        var j = $('.nuovoAllegato').length;
                       	loadedAttachmentsCounter++;

                        var hiddenFields = '';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].pathFile" value="' + file.tempPath + '"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].nomeFile" value="' + file.name + '"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].tipoFile" value="' + file.type + '"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].allegaAllaMail" value="true"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].dimensione" value="'+file.size+'"/>';

                        var description = '<td>' + hiddenFields + '<input class="obbligatorio" type="text" name="allegatiManuali[' + j + '].descrizione" style="width: 100%"/><br /><span class="formHint">Campo obbligatorio</span></td>';
                        var name = '<td>' + file.name + '</td>';
                        var type = '<td><div class="' + file.typeCode + '" style="text-align: center;"></div></td>';
                        var preview = '<td><span style="text-align: center;"><a class="scarica2" href=/cross/downloadO.htm?id_file=' + file.tempPath + '/' + file.name.replace(" ","_") + ' target="_blank"> </a>'
                        		+ '  <a class="cerca_lente_rosso" href="/cross/previewO.htm?id_file=' + file.tempPath + '/' + file.name.replace(" ","_") + '" target="_blank"> </a>'
                        		+'</span></td>'
                        var remove = '<td><a class="aggiungiAllegato" att_id="new_attachment_'+loadedAttachmentsCounter+'" onclick="rimuoviAllegato(this);"><img src="${path}/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a></td>';
						var firm = '<td><div style="text-align: center;"><a class="icona_modifica" href="#" onclick="firmaAllegato2(' + j +  ',this)"></a></div></td>';
                        var principale = '<td></td>';
                        var prot = '${processo_evento.flgProtocollazione}';
                        if (prot == 'S')
                        	principale = '<td><input type="checkbox" name="allegatiManuali[' + j + '].principale" value="true" class="documenti_allegati_principale" /></td>';
                        var dimension = '<td id=new_attachment_'+loadedAttachmentsCounter+'">' + Math.round((1*file.size) /1024)+ '</td>';
                        var row = '<tr class="nuovoAllegato">' + hiddenFields + description + name + type + dimension+preview +remove + principale + firm +'</tr>'; 
                        $("#allegatiEmailTable > tbody").append(row);
                        j++;
                        attachmentsManager.setAttachment("new_attachment_"+loadedAttachmentsCounter, file.size, true, true);
                    });
                },
                progressall: function(e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $('#progress .bar').css(
                            'width',
                            progress + '%'
                            );
                },
                dropZone: $('#dropzoneall')
            });
            
            $("#seleziona_tutti_allegati").on("change", function(){
				var isChecked = $(this).is(":checked");            	

                $('.documenti_allegati').not(this).each(function(i,e){
                	if(isChecked ^ $(e).is(":checked")){
                		attachmentsManager.setAttachment($(e).attr("id"), $(e).attr("size"), isChecked, false);
                	}
                	$(e).prop('checked', isChecked);
                })
                if(!attachmentsManager.isValid()){
            		attachmentsManager.printErrorMessage();
            	}
            });
            
            $(".documenti_allegati").on("change", function(){
            	attachmentsManager.setAttachment($(this).attr("id"), $(this).attr("size"), $(this).is(":checked"), true);
            });
            
        });
        
    </script>

    <c:if test="${processo_evento.flgFirmato}">
        <h4 class="alertFileFirmati"><spring:message code="pratica.comunicazione.evento.filefirmato"/></h4>    
    </c:if>

   <script type="text/javascript">
        function aggiungiAllegati() {
            $("#uploadallegati").trigger('click');
        }
    </script>
    <label>Rilasciare i file da caricare nella zona sottostante (drop zone) o cliccare il pulsante "Aggiungi allegato" per cercarli nel proprio computer, scrivere quindi una descrizione per ciascun file caricato. Possono essere caricati più files contemporaneamente in entrambe le modalità.</label>
    <div class="dropzone_container" id="dropzoneall">DROP ZONE</div>

    <div class="aggiungiAllegato">
        <a onclick="aggiungiAllegati()" class="crea_nuovo_evento">
            <spring:message code="pratica.comunicazione.evento.aggiungiallegato"/>
        </a>
    </div>     
 
    <div id="windowFirmaAllegato" class="modal-content">
	    <input class="input_anagrafica id_allegato hidden" type="text" value="" />
	    <div class="box_allegato ctrlHolder">
	        <label class="required">
	            <spring:message code="allegato.firma.signer"/>
	        </label>
	        <input class="input_anagrafica_disable signer" type="text"  value="">
	    </div>
	    <div class="box_allegato ctrlHolder">
	         <label class="required">
	            <spring:message code="allegato.firma.otp"/>
	        </label>
	        <input class="input_anagrafica_disable otp" type="text"  value="">  
	    </div>
	    <p><label class="required">Modalità firma:</label></p>
	    <input type="radio" name="tipo_firma" class="cades" id="cades" checked value="cades"><label><spring:message code="allegato.firma.cades"/></label><br>
		<input type="radio" name="tipo_firma" id="pades" value="pades"><label><spring:message code="allegato.firma.pades"/></label>
	</div>  
	          
	<div id="windowFirmaAllegato2" class="modal-content">
	    <input class="input_anagrafica id_allegato hidden" type="text" value="">
	         <div class="box_allegato ctrlHolder">
	      <label class="required">
	            <spring:message code="allegato.firma.signer"/>
	        </label>
	        <input class="input_anagrafica_disable signer" type="text"  value="">
	        </div>
	         <div class="box_allegato ctrlHolder">
	         <label class="required">
	            <spring:message code="allegato.firma.otp"/>
	        </label>
	        <input class="input_anagrafica_disable otp" type="text"  value="">  
	        </div>
	        <p><label class="required">Modalità firma:</label></p>
	      <input type="radio" name="tipo_firma2" class="cades" id="cades2" checked value="cades"><label><spring:message code="allegato.firma.cades"/></label><br>
	  <input type="radio" name="tipo_firma2" id="pades2" value="pades"><label><spring:message code="allegato.firma.pades"/></label>
	</div>    
	
    <input style="display:none" id="uploadallegati" type="file" name="file"  data-url="${path}/ajax_upload.htm" onchange="disableSubmit(); return false;" multiple/> 

    <table cellspacing="0" cellpadding="0" class="master" id="allegatiEmailTable">
        <tbody>
            <tr>
                <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.allegati.dimensione.kb"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.download"/></th>
                <th><input type="checkbox" id="seleziona_tutti_allegati"/>&nbsp;<spring:message code="pratica.comunicazione.evento.allega"/></th>
                <th><spring:message code="pratica.comunicazione.evento.allega.principale"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.allegati.firma"/></th>
            </tr>

            <c:set var="countAllegatiPresenti" value="0" scope="page" />
            <c:forEach items="${allegatiPratica}" begin="0" var="allegato">
                <tr id="tr_${allegato.idAllegato}">
                    <td>${allegato.descrizione}</td>
                    <td>${allegato.nomeFile}</td>
                    <td style="background-image: url(images/bottone_gestione_flusso.png)">          
                        <div class="${allegato.tipoFileCode}" style="text-align: center;"></div>
                    </td>
                    <td><fmt:formatNumber value="${allegato.dimensione/1024}" maxFractionDigits="0"/></td> 
                    <td>
                        <span style="text-align: center;"> 
                            <a class="scarica" href="${path}/download.htm?id_file=${allegato.idAllegato}" target="_blank">
                                <spring:message code="pratica.comunicazione.evento.file.download"/>
                            </a>
                        </span>
                    </td>
                    <td>
                        <c:if test="${processo_evento.flgAllMail == 'S'}">
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiPresenti[${countAllegatiPresenti}].idAllegato" value="${allegato.idAllegato}" class="documenti_allegati" size="${allegato.dimensione}" />
                            	<script type="text/javascript">
                            		var isChecked = $("[name='allegatiPresenti[${countAllegatiPresenti}].idAllegato']").is(":checked")
									if(isChecked){
										var elem = $("[name='allegatiPresenti[${countAllegatiPresenti}].idAllegato']")
										attachmentsManager.setAttachment(elem.attr("id"), elem.attr("size"), elem.is(":checked"), true);
									}
                            	</script>
                            </span>
                        </c:if>
                    </td>
                    <td>
                    	<c:if test="${processo_evento.flgProtocollazione=='S'}">
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiPresenti[${countAllegatiPresenti}].principale" value="true" class="documenti_allegati_principale" />
                            </span>
                   		</c:if> 
                    </td>
                    <td style="background-color: <%= colorIntegrazione%>">
						<div style="text-align: center;">
							<a class="icona_modifica" href="#"
								onclick="firmaAllegato('${allegato.idAllegato}',this)">
							</a>
						</div>
					</td>
                </tr>
                <c:set var="countAllegatiPresenti" value="${countAllegatiPresenti + 1}" scope="page"/>
            </c:forEach>

            <c:set var="countAllegatiDaProtocollo" value="0" scope="page" />
            <c:forEach items="${allegatiDaProtocollo}" begin="0"  var="allegatoDaProtocollo">
                <tr id="tr_${allegatoDaProtocollo.idAllegato}" class="nuovoAllegato">
                    <td>
                        <input class="obbligatorio" readonly="readonly" type="text" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].descrizione" value="${allegatoDaProtocollo.descrizione}" />
                        <input class="obbligatorio" readonly="readonly" type="hidden" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].idFileEsterno" value="${allegatoDaProtocollo.idFileEsterno}" />
                    </td>
                    <td><input class="obbligatorio" readonly="readonly" type="text" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].nomeFile" value="${allegatoDaProtocollo.nomeFile}"/>adfadfadf</td>
                    <td><input class="obbligatorio" readonly="readonly" type="text" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].tipoFile" value="${allegatoDaProtocollo.tipoFile}"/></td>
                    <td>
                        <span style="text-align: center;">

                            <a class="scarica" href="<%=path%>/download.htm?id_file=${allegatoDaProtocollo.idAllegato}" target="_blank">
                                <spring:message code="pratica.comunicazione.evento.file.download"/>
                            </a>
                        </span>
                    </td>
                    <td>
                        <c:if test="${processo_evento.flgAllMail == 'S'}">
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiDaProtocollo[${countAllegatiPresenti}].idAllegato" value="${allegatoDaProtocollo.idAllegato}" class="documenti_allegati" />
                            </span>
                        </c:if>
                    </td>
                    <td>
                         <c:if test="${processo_evento.flgProtocollazione=='S' && processo_evento.flgAllMail == 'S'}"> 
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiDaProtocollo[${countAllegatiPresenti}].principale" value="true" class="documenti_allegati_principale"/>
                            </span>
                         </c:if> 
                    </td>
                </tr>
                <c:set var="countAllegatiDaProtocollo" value="${countAllegatiDaProtocollo + 1}" scope="page"/>
            </c:forEach>

			<c:set var="countAllegatiInseriti" value="0" scope="page" />
            <c:forEach items="${allegatiManuali}" var="allegato" varStatus="status">
                <c:set var="countAllegatiInseriti" value="${countAllegatiInseriti + 1}" scope="page"/>
                <tr id="tr_${allegato.idAllegato}" class="nuovoAllegato">
                    <td>
                        <input style="display:none" type="text" name="allegatiManuali[${status.index}].allegaAllaMail" value="${allegato.allegaAllaMail}"/>
                        <input class="obbligatorio" type="text" name="allegatiManuali[${status.index}].descrizione" value="${allegato.descrizione}" /><br /><span class="formHint">Campo obbligatorio</span>
                    </td>
                    <td>${allegato.nomeFile}
                        <input style="display:none" type="text" name="allegatiManuali[${status.index}].pathFile" value="${allegato.pathFile}"/><input style="display:none" type="text" name="allegatiManuali[${status.index}].nomeFile" value="${allegato.nomeFile}"/>
                    </td>
                    <td style="background-image: url(images/bottone_gestione_flusso.png)">          
                        <div class="${allegato.tipoFileCode}" style="text-align: center;"></div>
                        <input style="display:none" type="text" name="allegatiManuali[${status.index}].tipoFile" value="${allegato.tipoFile}"/>
                    </td>

					<td><fmt:formatNumber value="${allegato.dimensione/1024}" maxFractionDigits="0"/>
	                    <script type="text/javascript">
	                    	loadedAttachmentsCounter++;
	                    	attachmentsManager.setAttachment("new_attachment_"+loadedAttachmentsCounter, 1*${allegato.dimensione}, true, true);
	                    </script>
                    </td>
                    <td colspan="2">
                        <a class="aggiungiAllegato" onclick="rimuoviAllegato(this)" att_id="new_attachment_${countAllegatiInseriti}"><img src="${path}/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a>
                    </td>
                    <td>
                         <c:if test="${processo_evento.flgProtocollazione=='S'}"> 
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiDaProtocollo[${countAllegatiPresenti}].principale" value="true" />
                            </span>
                         </c:if>
                    </td>
					<td></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>