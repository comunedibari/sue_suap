<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" charset="utf-8">
    var urlIndirizziIntervento = "${path}/pratiche/nuove/apertura/dettaglio/formIndirizziInterventoAjax.htm";
    var urlCercaStradario = "${path}/pratica/ricercatoponomastica.htm";
    var esistenzaStradario = "${esistenzaStradario}";
    var msgDelete = "<spring:message code='pratica.comunicazione.dettaglio.indirizzo.intervento.cancella'/>";
    $(document).ready(function() {
        $("#dialog-form-indirizzoIntervento").dialog({
            autoOpen: false,
            height: $(window).height() * 0.8,
            width: '50%',
            modal: true,
            close: function() {
                $("#dialog-form-indirizzoIntervento .indirizziIntervento").val("");
                $(".modalError").html("");
                $(".modalError").removeClass("message");
                $(".modalError").removeClass("error");
            }
        });

        $("#aggiungiIndirizzoIntervento").click(function() {
            var bottoni = [];
            if (esistenzaStradario && esistenzaStradario == 'true') {
                bottoni.push(bottoneStradarioIndirizzo);
            }
            bottoni.push(bottoneInserisciIndirizzo);
            bottoni.push(bottoneChiudiIndirizzo);
            $("#dialog-form-indirizzoIntervento").dialog('option', 'buttons', bottoni);
            $("#dialog-form-indirizzoIntervento").dialog("open");
        });
        $('.indirizzoIntervento.box-unlink-indirizzoIntervento').on('click', deleteIndirizzoIntervento);
        $('.indirizzoIntervento.cerca_lente_dettaglio.showdetail-indirizzoIntervento').on('click', modificaIndirizzo);
    });
    var bottoneInserisciIndirizzo = {
        text: "Inserisci",
        click: function() {
            eseguiClickIndirizzo("inserisciIndirizzoIntervento");
        }
    }
    var bottoneModificaIndirizzo = {
        text: "Modifica",
        click: function() {
            eseguiClickIndirizzo("salvaIndirizzoIntervento");
        }
    };
    var bottoneChiudiIndirizzo = {
        text: "Chiudi",
        click: function() {
            $(this).dialog("close");
        }
    };
    var bottoneStradarioIndirizzo = {
        text: "Cerca",
        click: function() {
            indirizziCerca();
        }
    };
    function eseguiClickIndirizzo(action) {
        var idPratica = $("#indirizzoIntervento_idPratica").val();
        var idIndirizzoIntervento = $(".indirizziIntervento.idIndirizzoIntervento").val();
        var counter = $(".indirizziIntervento.counter").val();
        var localita = $(".indirizziIntervento.localita").val();
        var indirizzo = $(".indirizziIntervento.indirizzo").val();
        var civico = $(".indirizziIntervento.civico").val();
        var cap = $(".indirizziIntervento.cap").val();
        var lettera = $(".indirizziIntervento.lettera").val();
        var colore = $(".indirizziIntervento.colore").val();
        var altreInformazioniIndirizzo = $(".indirizziIntervento.altreInformazioniIndirizzo").val();
        var internoNumero = $(".indirizziIntervento.internoNumero").val();
        var internoScala = $(".indirizziIntervento.internoScala").val();
        var internoLettera = $(".indirizziIntervento.internoLettera").val();
        var latitudine = $(".indirizziIntervento.latitudine").val();
        var longitudine = $(".indirizziIntervento.longitudine").val();
        var codVia = $(".indirizziIntervento.codVia").val();
        var codCivico = $(".indirizziIntervento.codCivico").val();
        var piano = $(".indirizziIntervento.piano").val();
        $.ajax({
            url: urlIndirizziIntervento,
            dataType: "json",
            data: {
                action: action,
                idPratica: idPratica,
                idIndirizzoIntervento: idIndirizzoIntervento,
                counter: counter,
                indirizzo: indirizzo,
                localita: localita,
                civico: civico,
                cap: cap,
                lettera: lettera,
                piano: piano,
                colore: colore,
                altreInformazioniIndirizzo: altreInformazioniIndirizzo,
                internoLettera: internoLettera,
                internoNumero: internoNumero,
                internoScala: internoScala,
                latitudine: latitudine,
                longitudine: longitudine,
                codVia: codVia,
                codCivico: codCivico
            },
            success: function(data) {
                if (data.errors) {
                    var messaggio = data.errors;
                    var msg = $('<h3 />').text('Si è verificato un errore:');
                    $("#dialog-form-indirizzoIntervento .modalError").empty();
                    $("#dialog-form-indirizzoIntervento .modalError").append(msg);
                    $("#dialog-form-indirizzoIntervento .modalError").append(messaggio);
                    $("#dialog-form-indirizzoIntervento .modalError").addClass("message");
                    $("#dialog-form-indirizzoIntervento .modalError").addClass("error");
                } else {
                    var messaggio = data.messages;
                    mostraMessaggioAjax(messaggio, "success");
                    $("#dialog-form-indirizzoIntervento").dialog("close");
                    var indirizzoIntervento = data.indirizzo;
                    var indexIndirizzo;
                    var indexIndirizzoIdIndirizzo;
                    var indexIndirizzoCounter;
                    if (indirizzoIntervento.counter) {
                        indexIndirizzo = "CNT-" + indirizzoIntervento.counter;
                        indexIndirizzoCounter = indexIndirizzo;
                    }
                    if (indirizzoIntervento.idIndirizzoIntervento) {
                        indexIndirizzo = "ID-" + indirizzoIntervento.idIndirizzoIntervento;
                        indexIndirizzoIdIndirizzo = indexIndirizzo;
                    }
 
                    var indexDato = indexIndirizzo;
                    if ($("#indirizzoIntervento_" + indexIndirizzoCounter).length > 0) {
                        indexDato = indexIndirizzoCounter;
                    }
                    if ($("#indirizzoIntervento_" + indexIndirizzoIdIndirizzo).length > 0) {
                        indexDato = indexIndirizzoIdIndirizzo;
                    }
					//table operation
                    if (action == "salvaIndirizzoIntervento") {
                    	$("#TRindirizzoIntervento_"+indexIndirizzo).remove();
                    }
                    addRowToTableToStreetTable(indexDato, indirizzoIntervento);
                    $("#cadastralStreetEmptyRow").remove()
                }
            }
        });
    }

    function addRowToTableToStreetTable(indexDato, indirizzoIntervento){
    	var tableRow = '<td class="confermataBG">'+indirizzoIntervento.localita+'</td>';
        tableRow += '<td class="confermataBG">'+indirizzoIntervento.indirizzo + ' '+ indirizzoIntervento.civico + '</td>';
        tableRow += '<td class="confermataBG">'+indirizzoIntervento.cap + '</td>';
        tableRow += '<td class="confermataBG">'+indirizzoIntervento.lettera+'/'+indirizzoIntervento.colore+'</td>';
        tableRow += '<td class="confermataBG">'+indirizzoIntervento.piano+'/'+indirizzoIntervento.internoNumero+'</td>';
        tableRow += '<td class="confermataBG">'+indirizzoIntervento.latitudine+'/'+indirizzoIntervento.longitudine+'</td>';
        tableRow += '<td class="confermataBG">'+createStreetTableCellAction(indirizzoIntervento)+'</td>';
        
		$("#tableStreetsDivTable tbody").append('<tr id="TRindirizzoIntervento_'+indexDato+'">'+tableRow+'</tr>');
        $('#TRindirizzoIntervento_'+indexDato+' .indirizzoIntervento.box-unlink-indirizzoIntervento').on('click', deleteIndirizzoIntervento);
        $('#TRindirizzoIntervento_'+indexDato+' .indirizzoIntervento.cerca_lente_dettaglio.showdetail-indirizzoIntervento').on('click', modificaIndirizzo);
    }

    function createStreetTableCellAction(indirizzo){
    	if(indirizzo.confermato == 'true')
	        class_indirizzo="confermataBG"
		var indexIndirizzo = "";
	    if(!!indirizzo.counter)
	        indexIndirizzo="CNT-"+indirizzo.counter

	    if(!!indirizzo.idIndirizzoIntervento)
	        indexIndirizzo="ID-"+indirizzo.idIndirizzoIntervento

	    var action = "";
	    if(indirizzo.urlCatasto){
	    	action += '<a href="'+indirizzo.urlCatasto+'" target="_blank" class="input_anagrafica_disable urlCatasto">'+
                	  	'<img src="${path}/themes/default/images/icons/magnifier.png"'+ 
                    	'alt="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.link"/>" '+
                    	'title="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.link"/>" />'+
            '</a>'
	    }
        action += '<a class="indirizzoIntervento cerca_lente_dettaglio showdetail-indirizzoIntervento" data-holder="'+indexIndirizzo+'" data-counter="'+indirizzo.counter+'" title="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.button"/>"></a>'
        action +='<a class="nascondiutente indirizzoIntervento box-unlink-indirizzoIntervento" data-holder="'+indexIndirizzo+'" data-counter="'+indirizzo.counter+'">'+
            '<img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="datiCatastali.button.eliminaIndirizzo"/>" title="<spring:message code="datiCatastali.button.eliminaIndirizzo"/>" title="<spring:message code="datiCatastali.button.eliminaIndirizzo"/>"></a>'
    	return action;    
    }
    
    function modificaIndirizzo(event) {
        var idIndirizzoInterventoString = $(this).attr('data-holder');
        var idIndirizzoIntervento;
        if (idIndirizzoInterventoString) {
            var res = idIndirizzoInterventoString.split("ID-");
            if (res.length > 1) {
                idIndirizzoIntervento = res[1];
            } else {
                idIndirizzoInterventoString = $(this).attr('data-holder');
            }
        }
        var counter = $(this).attr('data-counter');
        var idPratica = $("#indirizzoIntervento_idPratica").val();
        $.ajax({
            url: urlIndirizziIntervento,
            dataType: "json",
            data: {
                action: "leggiIndirizzoIntervento",
                idPratica: idPratica,
                counter: counter,
                idIndirizzoIntervento: idIndirizzoIntervento
            },
            success: function(data) {
                var errore = data.errors;
                if (errore) {
                    var messaggio = data.errors;
                    mostraMessaggioAjax(messaggio, "error");
                } else {
                    var messaggio = data.indirizzo;
                    $('#indirizzoIntervento_localita').val(messaggio.localita);
                    $('#indirizzoIntervento_indirizzo').val(messaggio.indirizzo);
                    $('#indirizzoIntervento_codVia').val(messaggio.codVia);
                    $('#indirizzoIntervento_civico').val(messaggio.civico);
                    $('#indirizzoIntervento_codCivico').val(messaggio.codCivico);
                    $('#indirizzoIntervento_cap').val(messaggio.cap);
                    $('#indirizzoIntervento_lettera').val(messaggio.lettera);
                    $('#indirizzoIntervento_colore').val(messaggio.colore);
                    $('#indirizzoIntervento_piano').val(messaggio.piano);
                    $('#indirizzoIntervento_internoNumero').val(messaggio.internoNumero);
                    $('#indirizzoIntervento_internoLettera').val(messaggio.internoLettera);
                    $('#indirizzoIntervento_internoScala').val(messaggio.internoScala);
                    $('#indirizzoIntervento_longitudine').val(messaggio.longitudine);
                    $('#indirizzoIntervento_latitudine').val(messaggio.latitudine);
                    $('#indirizzoIntervento_idIndirizzoIntervento').val(messaggio.idIndirizzoIntervento);
                    $('#indirizzoIntervento_altreInformazioniIndirizzo').val(messaggio.altreInformazioniIndirizzo);
                    $('#indirizzoIntervento_counter').val(messaggio.counter);
                    var bottoni = [];
                    if (esistenzaStradario && esistenzaStradario == 'true') {
                        bottoni.push(bottoneStradarioIndirizzo);
                    }
                    if ($("#abilitazione").val() == "true"){
                        bottoni.push(bottoneModificaIndirizzo);
                    }
                    bottoni.push(bottoneChiudiIndirizzo);
                    $("#dialog-form-indirizzoIntervento").dialog('option', 'buttons', bottoni);
                    $("#dialog-form-indirizzoIntervento").dialog('open');
                }
            }
        });
    }
    function deleteIndirizzoIntervento(event) {
    	var idIndirizzoInterventoString = $(this).attr('data-holder');
        var idIndirizzoIntervento;
        if (idIndirizzoInterventoString) {
            var res = idIndirizzoInterventoString.split("ID-");
            if (res.length > 1) {
                idIndirizzoIntervento = res[1];
            }
        }
        var counter = $(event.target).attr('data-counter');
        var idPratica = $("#indirizzoIntervento_idPratica").val();
        $('<div></div>').appendTo('body')
                .html("<div><h6 class='confirm-body-content'>" + msgDelete + "</h6></div>")
                .dialog({
                    modal: true,
                    title: 'Conferma cancellazione',
                    zIndex: 10000,
                    width: 300,
                    height: 150,
                    resizable: false,
                    buttons: {
                        'Conferma': function() {
                            $.ajax({
                                url: urlIndirizziIntervento,
                                dataType: "json",
                                data: {
                                    action: "eliminaIndirizzoIntervento",
                                    idPratica: idPratica,
                                    counter: counter,
                                    idIndirizzoIntervento: idIndirizzoIntervento
                                },
                                success: function(data) {
                                    var errore = data.errors;
                                    if (errore) {
                                        var messaggio = data.errors;
                                        mostraMessaggioAjax(messaggio, "error");
                                    } else {
                                        var messaggio = data.messages;
                                        mostraMessaggioAjax(messaggio, "success");
                                        $("#indirizzoIntervento_" + idIndirizzoInterventoString).remove();
                                        $("#TRindirizzoIntervento_" + idIndirizzoInterventoString).remove();
                                    }
                                }
                            });
                            $(this).dialog("close");
                        },
                        'Annulla': function() {
                            $(this).dialog("close");
                        }
                    },
                    close: function(event, ui) {
                        $(this).remove();
                    }
                });
    }
    function indirizziCerca() {
        var wHeight = $(window).height() * 0.9;
        var localita = $('#indirizzoIntervento_localita').val();
        var indirizzo = $('#indirizzoIntervento_indirizzo').val();
        var idPratica = $('#indirizzoIntervento_idPratica').val();
        var civico = $('#indirizzoIntervento_civico').val();
        var cap = $('#indirizzoIntervento_cap').val();
        var lettera = $('#indirizzoIntervento_lettera').val();
        var colore = $('#indirizzoIntervento_colore').val();
        var altreInformazioniIndirizzo = $('#indirizzoIntervento_altreInformazioniIndirizzo').val();
        var internoNumero = $('#indirizzoIntervento_internoNumero').val();
        var internoLettera = $('#indirizzoIntervento_internoLettera').val();
        var internoScala = $('#indirizzoIntervento_internoScala').val();
        var latitudine = $('#indirizzoIntervento_latitudine').val();
        var longitudine = $('#indirizzoIntervento_longitudine').val();
        var foglio = $('#indirizzoIntervento_foglio').val();
        var mappale = $('#indirizzoIntervento_mappale').val();
        var piano = $('#indirizzoIntervento_piano').val();
        $.post(urlCercaStradario, {localita: localita,
            indirizzo: indirizzo,
            idPratica: idPratica,
            civico: civico,
            cap: cap,
            lettera: lettera,
            colore: colore,
            altreInformazioniIndirizzo: altreInformazioniIndirizzo,
            internoNumero: internoNumero,
            internoLettera: internoLettera,
            internoScala: internoScala,
            latitudine: latitudine,
            longitudine: longitudine,
            foglio: foglio,
            mappale: mappale,
            piano: piano
        }, function(result) {
            //var data = $.parseJSON(result);
            var data = result;
            if (data.error !== null && data.error !== undefined) {
                alert(data.error);
            } else {
                if (data.rows != null) {
                    $('#indirizziInterventoTable').remove();
                    var ul = $('#windowCercaIndirizzi');
                    var table = "<table cellspacing='0' cellpadding='0' class='master datiCatastaliSearchResult' id='datiCatastaliTable'>" +
                            "<tbody>" +
                            "<tr>" +
                            "<th>Indirizzo</th>" +
                            "<th>Civico</th>" +
                            "<th>Lettera</th> " +
                            "<th>Colore</th>" +
                            "<th>Interno</th>" +
                            "<th>Lettera interno</th>" +
                            "<th>Scala</th>" +
                            "<th>Piano</th>" +
                            "<th class='hidden'>CAP</th>" +
                            "<th>Foglio</th>" +
                            "<th>Mappale</th>" +
                            "<th></th>" +
                            "</tr>";
                    for (var i = 0; i < data.total; i++) {
                        var button = "<button onclick='selezionaIndirizzo(" + i + ")' class='datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all' role='button' aria-disabled='false'>" +
                                "<span class='ui-button-text'>Seleziona</span>" +
                                "</button>";

                        var tRow = "<tr id='row_" + i + "'>" +
                                "<td>" + e(data.rows[i].indirizzo) + "</td>" +
                                "<td>" + e(data.rows[i].codCivico) + "</td>" +
                                "<td>" + e(data.rows[i].lettera) + "</td>" +
                                "<td>" + e(data.rows[i].colore) + "</td>" +
                                "<td>" + e(data.rows[i].internoNumero) + "</td>" +
                                "<td>" + e(data.rows[i].internoLettera) + "</td>" +
                                "<td>" + e(data.rows[i].internoScala) + "</td>" +
                                "<td>" + e(data.rows[i].piano) + "</td>" +
                                "<td class='hidden'>" + e(data.rows[i].cap) + "</td>" +
                                "<td>" + e(data.rows[i].foglio) + "</td>" +
                                "<td>" + e(data.rows[i].mappale) + "</td>" +
                                "<td><input type='hidden' name='codVia' id='row_codVia_" + i + "' value='" + data.rows[i].codVia + "' />" + button + "</td>" +
                                "</tr>";
                        table = table + tRow;
                    }
                    table = table + "</tbody></table>";
                    ul.append(table);
                    windows(wHeight);
                }
            }
        });

    }
    function windows(wHeight) {
        $('#windowCercaIndirizzi').dialog({
            title: '<spring:message code="datiCatastali.windowsCerca.title"/>',
            modal: true,
            height: wHeight,
            width: '90%',
            close: function() {
                $(this).html('');
            },
            buttons: {
                'Annulla': function() {
                    $(this).dialog("close");
                }
            }
        });
    }
    function selezionaIndirizzo(tableIndex, index) {
        var tr = $('#row_' + tableIndex);
        var desVia = $(tr).find("td").eq(0).html();
        $('#indirizzoIntervento_indirizzo').val(desVia);

        var codVia = $('#row_codVia_' + tableIndex).val();
        $('#indirizzoIntervento_codVia').val(codVia);

        var codCivico = $(tr).find("td").eq(1).html();
        $('#indirizzoIntervento_civico').val(codCivico);
        $('#indirizzoIntervento_codCivico').val(codCivico);

        var lettera = $(tr).find("td").eq(2).html();
        $('#indirizzoIntervento_lettera').val(lettera);

        var colore = $(tr).find("td").eq(3).html();
        $('#indirizzoIntervento_colore').val(colore);

        var interno = $(tr).find("td").eq(4).html();
        $('#indirizzoIntervento_internoNumero').val(interno);

        var letteraInterno = $(tr).find("td").eq(5).html();
        $('#indirizzoIntervento_internoLettera').val(letteraInterno);

        var scala = $(tr).find("td").eq(6).html();
        $('#indirizzoIntervento_internoScala').val(scala);
        
        var piano = $(tr).find("td").eq(7).html();
        $('#indirizzoIntervento_piano').val(piano);        
        
        var cap = $(tr).find("td").eq(8).html();
        $('#indirizzoIntervento_cap').val(cap);
        //Pulizia tabella e visualizzazione pulsante
        $('#indirizzoInterventoTable').remove();
        $('#windowCercaIndirizzi').dialog("close");
    }
    function e(val) {
        if (val && val !== 'undefined') {
            return val;
        }
        return '';
    }
</script>

<spring:message code="datiCatastali.select" var="datiCatastaliSelect"/>
<div class="IndirizziIntervento imbox_frame ${margine_inbox_frame}">
    <input id="indirizzoIntervento_idPratica" class="indirizzoIntervento idPratica" type="hidden" value="${pratica.idPratica}" name="idPratica"/>
    <div class="nascondiutente table-add-link" id="aggiungiIndirizzoIntervento">
        <a href="#" id="add_indirizziIntervento_btn">
            <spring:message code="datiCatastali.button.aggiungiIndirizzo"/>
            <img src="${path}/themes/default/images/icons/add.png" alt="<spring:message code="datiCatastali.button.aggiungiIndirizzo"/>" title="<spring:message code="datiCatastali.button.aggiungiIndirizzo"/>">
        </a>
    </div>
	
	<div id="tableStreetsDiv">
		<table id="tableStreetsDivTable" cellspacing="0" cellpadding="0" class="master">
			<thead>
				<tr>
					<th><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.localita" /></th>
					<th><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.indirizzo" /></th>
					<th><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.cap" /></th>
					<th><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.lettera" /><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.colore" /></th>
					<th><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.piano" /><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.interno" /></th>
					<th><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.latitudine" /><spring:message	code="pratica.comunicazione.dettaglio.indirizzo.intervento.longitudine" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${not empty praticaDettaglio.indirizziIntervento}">
				<c:forEach items="${praticaDettaglio.indirizziIntervento}" begin="0"  var="indirizzo" varStatus="loop">
					<c:if test="${indirizzo.confermato == 'true'}">
                       <c:set var="class_indirizzo" value="confermataBG"/>
                    </c:if>
                    <c:if test="${not empty indirizzo.counter}">
                       <c:set var="indexIndirizzo" value="CNT-${indirizzo.counter}"/>
                    </c:if>
                    <c:if test="${not empty indirizzo.idIndirizzoIntervento}">
                       <c:set var="indexIndirizzo" value="ID-${indirizzo.idIndirizzoIntervento}"/>
                    </c:if>   
									
					
					<tr id="TRindirizzoIntervento_${indexIndirizzo}">
						<td class="IndirizziInterventoElement ${class_indirizzo}">${indirizzo.localita}</td>
						<td class="IndirizziInterventoElement ${class_indirizzo}">${indirizzo.indirizzo}&nbsp;${indirizzo.civico}</td>
						<td class="IndirizziInterventoElement ${class_indirizzo}">${indirizzo.cap }</td>
						<td class="IndirizziInterventoElement ${class_indirizzo}">${indirizzo.lettera }/${indirizzo.colore }</td>
						<td class="IndirizziInterventoElement ${class_indirizzo}">${indirizzo.piano }/${indirizzo.internoNumero }</td>						
						<td class="IndirizziInterventoElement ${class_indirizzo}">${indirizzo.latitudine }/${indirizzo.longitudine }</td>	
						<td class="IndirizziInterventoElement ${class_indirizzo}">
							<c:if test="${not empty indirizzo.urlCatasto}">
								<a href="${indirizzo.urlCatasto}" target="_blank" class="input_anagrafica_disable urlCatasto">
	                                <img src="${path}/themes/default/images/icons/magnifier.png" 
		                                alt="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.link"/>" 
		                                title="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.link"/>" />
	                            </a>
                            </c:if>
                            <a class="indirizzoIntervento cerca_lente_dettaglio showdetail-indirizzoIntervento" data-holder="${indexIndirizzo}" data-counter="${indirizzo.counter}" title="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.button"/>"></a>
							
							<a class="nascondiutente indirizzoIntervento box-unlink-indirizzoIntervento" data-holder="${indexIndirizzo}" data-counter="${indirizzo.counter}">
                                <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="datiCatastali.button.eliminaIndirizzo"/>" title="<spring:message code="datiCatastali.button.eliminaIndirizzo"/>" title="<spring:message code="datiCatastali.button.eliminaIndirizzo"/>">
                            </a>
	                    </td>			
					</tr>
				</c:forEach>
			</c:if>
		
			<c:if test="${empty praticaDettaglio.indirizziIntervento}">
				<tr id="cadastralStreetEmptyRow">
					<td colspan="10"><spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.noresult" /></td>
				</tr>
			</c:if>
			</tbody>
		
		</table>
	</div>

   <div class="clear"></div>
   <div id="dialog-form-indirizzoIntervento" title="<spring:message code="pratica.comunicazione.dettaglio.indirizzo.intervento.gestione.title"/>" class="indirizziInterventoDiv uniForm">
        <div class="modalError"></div>
        <div class="inlineLabels">
            <div class="ctrlHolder IndirizziIntervento">
                <ul id="indirizziIntervento" class="IndirizziInterventoSingle">
                    <li>
                        <input name="idIndirizzoIntervento" value="" id="indirizzoIntervento_idIndirizzoIntervento" type="text" class="indirizziIntervento idIndirizzoIntervento hidden" />
                        <input name="counter" value="" id="indirizzoIntervento_counter" type="text" class="indirizziIntervento counter hidden" />
                        <input name="codVia" value="" id="indirizzoIntervento_codVia" type="text" class="indirizziIntervento codVia hidden" />
                        <input name="codCivico" value="" id="indirizzoIntervento_codCivico" type="text" class="indirizziIntervento codCivico hidden" />
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.indirizzo"/></label>
                            <input name="indirizzo" value="" id="indirizzoIntervento_indirizzo" type="text" class="indirizziIntervento indirizzo" />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.civico"/></label>
                            <input name="civico" value="" id="indirizzoIntervento_civico" type="text" class="indirizziIntervento civico" />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.lettera"/></label>
                            <input name="lettera" value="" id="indirizzoIntervento_lettera" type="text" class="indirizziIntervento lettera" />
                        </div>
                    </li>  
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.colore"/></label>
                            <input name="colore" value="" id="indirizzoIntervento_colore" type="text" class="indirizziIntervento colore" />
                        </div>
                    </li>                    
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.piano"/></label>
                            <input name="piano" value="" id="indirizzoIntervento_piano" type="text" class="indirizziIntervento piano" />
                        </div>
                    </li>                     
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.scala"/></label>
                            <input name="internoScala" value="" id="indirizzoIntervento_internoScala" type="text" class="indirizziIntervento internoScala" />
                        </div> 
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.interno"/></label>
                            <input name="internoNumero" value="" id="indirizzoIntervento_internoNumero" type="text" class="indirizziIntervento internoNumero" />
                        </div> 
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.letterainterno"/></label>
                            <input name="internoLettera" value="" id="indirizzoIntervento_internoLettera" type="text" class="indirizziIntervento internoLettera" />
                        </div> 
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.localita"/></label>
                            <input name="localita" value="" id="indirizzoIntervento_localita" type="text" class="indirizziIntervento localita" />
                        </div>
                    </li> 
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.cap"/></label>
                            <input name="cap" value="" id="indirizzoIntervento_cap" type="text" class="indirizziIntervento cap" />
                        </div>
                    </li> 
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.latitudine"/></label>
                            <input name="latitudine" value="" id="indirizzoIntervento_latitudine" type="text" class="indirizziIntervento latitudine" />
                        </div> 
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.longitudine"/></label>
                            <input name="longitudine" value="" id="indirizzoIntervento_longitudine" type="text" class="indirizziIntervento longitudine" />
                        </div> 
                    </li>   
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.altreInformazioniIndirizzo"/></label>
                            <input name="altreInformazioniIndirizzo" value="" id="indirizzoIntervento_altreInformazioniIndirizzo" type="text" class="indirizziIntervento altreInformazioniIndirizzo" />
                        </div> 
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="windowCercaIndirizzi" class="modal-content">
    <div id="indirizziInterventoTable">
    </div>
</div>
