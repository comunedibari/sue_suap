<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	var path = "${path}";
	var linkgeolocale = '<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>';
	var immobileButton = '<spring:message code="pratica.comunicazione.dettaglio.immobile.button"/>';
	var eliminaCatastale = '<spring:message code="datiCatastali.button.eliminaCatastale"/>';
	var urlDatiCatastali = "${path}/pratiche/nuove/apertura/dettaglio/formDatiCatastaliAjax.htm";
    var esistenzaRicercaCatasto = "${esistenzaRicercaCatasto}";
    var urlCercaCatasto = "${path}/pratica/ricercatocatasto.htm";
    var msgDelete = '<spring:message code="pratica.comunicazione.dettaglio.immobile.cancella"/>';
    $(document).ready(function () {
        
        $("#dialog-form-datoCatastale").dialog({
            autoOpen: false,
            height: $(window).height() * 0.8,
            width: '50%',
            modal: true,
            close: function () {
                $("#dialog-form-datoCatastale .datiCatastali").val("");
                $(".modalError").html("");
                $(".modalError").removeClass("message");
                $(".modalError").removeClass("error");
            }
        });
        $("#aggiungiDatoCatastale").click(function () {
            var bottoni = [];
            if (esistenzaRicercaCatasto && esistenzaRicercaCatasto == 'true') {
                bottoni.push(bottoneRicercaCatasto);
            }
            bottoni.push(bottoneInserisci);
            bottoni.push(bottoneChiudi);
            $("#dialog-form-datoCatastale").dialog('option', 'buttons', bottoni);
            $("#dialog-form-datoCatastale").dialog("open");
        });

        $("#datoCatastale_idTipoSistemaCatastale").on("change", function (event) {
            var sistema = $('#datoCatastale_idTipoSistemaCatastale').find(":selected").val();
            cambiaSistemaCatastale(sistema);
        });
        $('.datoCatastale.box-unlink-datoCatastale').on('click', deleteDatoCatastale);
        $('.datiCatastali.cerca_lente_dettaglio.showdetail-datoCatastale').on('click', modificaDatoCatastale);
    });

    function eseguiClick(action) {
        var idPratica = $(".datiCatastali.idPratica").val();
        var idTipoSistemaCatastale = $(".datiCatastali.idTipoSistemaCatastale").val();
        var idTipoUnita = $(".datiCatastali.idTipoUnita").val();
        var sezione = $(".datiCatastali.sezione").val();
        var foglio = $(".datiCatastali.foglio").val();
        var mappale = "";
        var categoria = $(".datiCatastali.categoria").val();
        var subalterno="";
        if (idTipoSistemaCatastale == "1") {
            mappale = $(".datiCatastali.mappale").val();
            subalterno = $(".datiCatastali.subalterno").val();
        }
        if (idTipoSistemaCatastale == "2") {
            mappale = $(".datiCatastali.mappaleTavolare").val();
            subalterno = $(".datiCatastali.subalternoTavolare").val();
        }
        var idImmobile = $(".datiCatastali.idImmobile").val();
        var counter = $(".datiCatastali.counter").val();
        var idTipoParticella = $(".datiCatastali.idTipoParticella").val();
        var comuneCensuario = $(".datiCatastali.comuneCensuario").val();
        var estensioneParticella = $(".datiCatastali.estensioneParticella").val();
        var codImmobile = $(".datiCatastali.codImmobile").val();

        $.ajax({
            url: urlDatiCatastali,
            dataType: "json",
            data: {
                action: action,
                idImmobile: idImmobile,
                idPratica: idPratica,
                idTipoUnita: idTipoUnita,
                idTipoSistemaCatastale: idTipoSistemaCatastale,
                sezione: sezione,
                foglio: foglio,
                mappale: mappale,
                counter: counter,
                subalterno: subalterno,
                categoria:categoria,
                idTipoParticella: idTipoParticella,
                comuneCensuario: comuneCensuario,
                codImmobile: codImmobile,
                estensioneParticella: estensioneParticella
            },
            success: function (data) {
                if (data.errors) {
                    var messaggio = data.errors;
                    var msg = $('<h3 />').text('Si è verificato un errore:');
                    $(".modalError").empty();
                    $(".modalError").append(msg);
                    $(".modalError").append(messaggio);
                    $(".modalError").addClass("message");
                    $(".modalError").addClass("error");
                } else {
                    var messaggio = data.messages;
                    mostraMessaggioAjax(messaggio, "success");
                    $("#dialog-form-datoCatastale").dialog("close");
                    var datoCatastale = data.datoCatastale;
                    var indexDatoCatastale;
                    var indexDatoCatastaleIdImmobile;
                    var indexDatoCatastaleCounter;
                    if (datoCatastale.counter || datoCatastale.counter == 0) {
                        indexDatoCatastale = "CNT-" + datoCatastale.counter;
                        indexDatoCatastaleCounter = indexDatoCatastale;
                    }
                    if (!!datoCatastale.idImmobile) {
                        indexDatoCatastale = "IM-" + datoCatastale.idImmobile;
                        indexDatoCatastaleIdImmobile = indexDatoCatastale;
                    }
                    if (action === "salvaDatoCatastale") {
                    	$("#datoCatastale_"+indexDatoCatastale).remove();
                    }

                    addRowToTableToNewCadastralData(datoCatastale);
                    $("#cadastralDataEmptyRow").remove();
                }
            }
        });
    }
    
    function addRowToTableToNewCadastralData(datoCatastale){
    	var tableRow = '<td class="confermataBG">'+datoCatastale.desSistemaCatastale+'</td>';
        tableRow += '<td class="confermataBG">'+datoCatastale.desTipoUnita+'</td>';
        tableRow += '<td class="confermataBG">'+datoCatastale.sezione+'</td>';
        tableRow += '<td class="confermataBG">'+datoCatastale.foglio+'</td>';
        tableRow += '<td class="confermataBG">'+datoCatastale.mappale+'</td>';
        tableRow += '<td class="confermataBG">'+datoCatastale.subalterno+'</td>';
        var indexDatoCatastale = datoCatastale.idImmobile;
        var indexDatoCatastaleIdImmobile;
        var indexDatoCatastaleCounter;
        if (datoCatastale.counter || datoCatastale.counter == 0) {
            indexDatoCatastale = "CNT-" + datoCatastale.counter;
            indexDatoCatastaleCounter = indexDatoCatastale;
        }
        if (datoCatastale.idImmobile) {
            indexDatoCatastale = "IM-" + datoCatastale.idImmobile;
            indexDatoCatastaleIdImmobile = indexDatoCatastale;
        }
        tableRow += '<td class="confermataBG">'+createNewCadastralDataCellAction(datoCatastale, indexDatoCatastale)+'</td>';
        $("#tableCadastralDataTable tbody").append('<tr id="datoCatastale_'+indexDatoCatastale+'">'+tableRow+'</tr>');
        $('#datoCatastale_'+indexDatoCatastale+' .datoCatastale.box-unlink-datoCatastale').on('click', deleteDatoCatastale);
        $('#datoCatastale_'+indexDatoCatastale+' .datiCatastali.cerca_lente_dettaglio.showdetail-datoCatastale').on('click', modificaDatoCatastale);
    }

    function createNewCadastralDataCellAction(datoCatastale, indexDatoCatastale){
    	var action = "";
    	if(!!datoCatastale.urlCatasto){
	        action += '<a href="'+datoCatastale.urlCatasto+'" target="_blank" title="'+linkgeolocale+'">'+
	            	  '<img src="'+path+'/themes/default/images/icons/magnifier.png" alt="'+linkgeolocale+'" title="'+linkgeolocale+'">'+
	        		  '</a>'
    	}
    
	    action += '<a class="datiCatastali cerca_lente_dettaglio showdetail-datoCatastale" data-holder="'+indexDatoCatastale+'" data-counter="'+datoCatastale.counter+'" title="'+immobileButton+'"></a>&nbsp;'

    	action += '<a class="nascondiutente datoCatastale box-unlink-datoCatastale" data-holder="'+indexDatoCatastale+'" data-counter="'+datoCatastale.counter+'" title="'+eliminaCatastale+'">'+
            '<img src="${path}/themes/default/images/icons/link_break_big.png" alt="'+eliminaCatastale+'" title="'+eliminaCatastale+'"></a>'
		return action;    
    }
    
    function modificaDatoCatastale(event) {
        var idImmobileString = $(this).attr('data-holder') || event.currentTarget.getAttribute('data-holder');
        var idImmobile;
        if (idImmobileString) {
            var res = idImmobileString.split("IM-");
            if (res.length > 1) {
                idImmobile = res[1];
            }
        }
        var counter = $(this).attr('data-counter');
        var idPratica = $("#datiCatastali_idPratica").val();
        $.ajax({
            url: urlDatiCatastali,
            dataType: "json",
            data: {
                action: "leggiDatoCatastale",
                idPratica: idPratica,
                counter: counter,
                idImmobile: idImmobile
            },
            success: function (data) {
                var errore = data.errors;
                if (errore) {
                    var messaggio = data.errors;
                    mostraMessaggioAjax(messaggio, "error");
                } else {
                	var datoCatastale = data.datoCatastale;
                    $('#datoCatastale_sezione').val(datoCatastale.sezione);
                    $('#datoCatastale_mappale').val(datoCatastale.mappale);
                    $('#datoCatastale_mappaleTavolare').val(datoCatastale.mappale);
                    $('#datoCatastale_foglio').val(datoCatastale.foglio);
                    $('#datoCatastale_subalterno').val(datoCatastale.subalterno);
                    $('#datoCatastale_categoria').val(datoCatastale.categoria);
                    $('#datoCatastale_subalternoTavolare').val(datoCatastale.subalterno);
                    $('#datoCatastale_estensioneParticella').val(datoCatastale.estensioneParticella);
                    $('#datoCatastale_comuneCensuario').val(datoCatastale.comuneCensuario);
                    $('#datoCatastale_idTipoSistemaCatastale').val(datoCatastale.idTipoSistemaCatastale);
                    $('#datoCatastale_idTipoUnita').val(datoCatastale.idTipoUnita);
                    $('#datoCatastale_idTipoParticella').val(datoCatastale.idTipoParticella);
                    $('#datoCatastale_idImmobile').val(datoCatastale.idImmobile);
                    $('#datoCatastale_counter').val(datoCatastale.counter);
                    $('#datoCatastale_codImmobile').val(datoCatastale.codImmobile);

                    cambiaSistemaCatastale(datoCatastale.idTipoSistemaCatastale);

                    var bottoni = [];
                    if (esistenzaRicercaCatasto && esistenzaRicercaCatasto == 'true') {
                        bottoni.push(bottoneRicercaCatasto);
                    }
                    if ($("#abilitazione").val() === "true")
                    {
                        bottoni.push(bottoneModifica);
                    }
                    bottoni.push(bottoneChiudi);
                    $("#dialog-form-datoCatastale").dialog('option', 'buttons', bottoni);
                    $("#dialog-form-datoCatastale").dialog('open');
                }
            }
        });
    }

    var bottoneInserisci = {
        text: "Inserisci",
        click: function () {
            eseguiClick("inserisciDatoCatastale");
        }
    };
    var bottoneModifica = {
        text: "Modifica",
        click: function () {
            eseguiClick("salvaDatoCatastale");
        }
    };
    var bottoneChiudi = {
        text: "Chiudi",
        click: function () {
            $(this).dialog("close");
        }
    };
    var bottoneRicercaCatasto = {
        text: "Cerca",
        click: function () {
            catastoCerca();
        }
    };
    function deleteDatoCatastale(event) {
    	var idImmobileString = $(this).attr('data-holder') || event.currentTarget.getAttribute('data-holder');
        var idImmobile;
        if (idImmobileString) {
            var res = idImmobileString.split("IM-");
            if (res.length > 1) {
                idImmobile = res[1];
            }
        }
        var counter = $(this).attr('data-counter');
        var idPratica = $(".datiCatastali.idPratica").val();
        $('<div></div>').appendTo('body')
                .html('<div><h6 class="confirm-body-content">' + msgDelete + '</h6></div>')
                .dialog({
                    modal: true,
                    title: 'Conferma cancellazione',
                    zIndex: 10000,
                    width: 300,
                    height: 150,
                    resizable: false,
                    buttons: {
                        'Conferma': function () {
                            $.ajax({
                                url: urlDatiCatastali,
                                dataType: "json",
                                data: {
                                    action: "eliminaDatoCatastale",
                                    idPratica: idPratica,
                                    counter: counter,
                                    idImmobile: idImmobile
                                },
                                success: function (data) {
                                    var errore = data.errors;
                                    if (errore) {
                                        var messaggio = data.errors;
                                        mostraMessaggioAjax(messaggio, "error");
                                    } else {
                                        var messaggio = data.messages;
                                        mostraMessaggioAjax(messaggio, "success");
                                        $("#datoCatastale_" + idImmobileString).remove();
                                    }
                                }
                            });
                            $(this).dialog("close");
                        },
                        'Annulla': function () {
                            $(this).dialog("close");
                        }
                    },
                    close: function (event, ui) {
                        $(this).remove();
                    }
                });
    }
    function cambiaSistemaCatastale(sistema) {
        if (sistema == 1) {
            // ordinario
            $("#dialog-form-datoCatastale .datoCatastale_tipoUnita_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_sezione_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_foglio_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_mappale_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_subalterno_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_categoria_div").removeClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_tipoParticella_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_tipoParticella_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_comunCensuario_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_comunCensuario_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_mappaleTavolare_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_mappaleTavolare_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_estensioneParticella_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_estensioneParticella_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_subalternoTavolare_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_subalternoTavolare_div").addClass("hidden");
            
           

        }
        if (sistema == 2) {
            // tavolare
            $("#dialog-form-datoCatastale .datoCatastale_tipoParticella_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_comunCensuario_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_mappaleTavolare_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_estensioneParticella_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_subalternoTavolare_div").removeClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_tipoUnita_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_tipoUnita_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_sezione_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_sezione_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_foglio_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_foglio_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_mappale_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_mappale_div").addClass("hidden");

            $("#dialog-form-datoCatastale .datoCatastale_subalterno_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_subalterno_div").addClass("hidden");
            
            $("#dialog-form-datoCatastale .datoCatastale_categoria_div").removeClass("hidden");
            $("#dialog-form-datoCatastale .datoCatastale_categoria_div").addClass("hidden");

        }

    }
    function catastoCerca() {
        var wHeight = $(window).height() * 0.8;
        var idTipoSistemaCatastale = $('#datoCatastale_idTipoSistemaCatastale').val();
        var idImmobile = $('#datoCatastale_idImmobile').val();
        var idTipoUnita = $('#datoCatastale_idTipoUnita').val();
        var idPratica = $('#datiCatastali_idPratica').val();
        var idTipoParticella = $('#datoCatastale_idTipoParticella').val();
        var sezione = $('#datoCatastale_sezione').val();
        var comuneCensuario = $('#datoCatastale_comuneCensuario').val();
        var foglio = $('#datoCatastale_foglio').val();
        var mappaleTavolare = $('#datoCatastale_mappaleTavolare').val();
        var mappale = $('#datoCatastale_mappale').val();
        if (mappaleTavolare && mappaleTavolare != "") {
            mappale = mappaleTavolare;
        }
        var estensioneParticella = $('#datoCatastale_estensioneParticella').val();
        var subalterno = $('#datoCatastale_subalterno').val();
        var categoria = $('#datoCatastale_categoria').val();
        var subalternoTavolare = $('#datoCatastale_subalternoTavolare').val();
        if (subalternoTavolare && subalternoTavolare != "") {
            subalterno = subalternoTavolare;
        }
        $.post(urlCercaCatasto, {idImmobile: idImmobile,
            idTipoSistemaCatastale: idTipoSistemaCatastale,
            idTipoUnita: idTipoUnita,
            idPratica: idPratica,
            idTipoParticella: idTipoParticella,
            sezione: sezione,
            comuneCensuario: comuneCensuario,
            foglio: foglio,
            mappale: mappale,
            estensioneParticella: estensioneParticella,
            subalterno: subalterno,
            categoria:categoria
        }, function (result) {
            //var data = $.parseJSON(result);
            var data = result;
            if (data.error !== null && data.error !== undefined) {
                alert(data.error);
            } else {
                if (data.rows != null) {
                    $('#catastoTable').remove();
                    var ul = $('#windowCercaCatasto');
                    var tipoCatasto = data.rows[0].idTipoSistemaCatastale;
                    var table = "";
                    if (tipoCatasto == "1") {
                        table = table + "<table cellspacing='0' cellpadding='0' class='master datiCatastaliSearchResult' id='datiCatastaliTable'>" +
                                "<tbody>" +
                                "<tr>" +
                                "<th class='hidden'>id Tipo Catasto</th>" +
                                "<th class='hidden'>id Tipo Unita</th>" +
                                "<th>Tipo Unita'</th>" +
                                "<th>Sezione</th>" +
                                "<th>Foglio</th> " +
                                "<th>N.Mappale</th>" +
                                "<th>Subalterno</th>" +
                                "<th>Categoria</th>" +
                                "<th></th>" +
                                "</tr>";
                    }
                    if (tipoCatasto == "2") {
                        table = table + "<table cellspacing='0' cellpadding='0' class='master datiCatastaliSearchResult' id='datiCatastaliTable'>" +
                                "<tbody>" +
                                "<tr>" +
                                "<th class='hidden'>id Tipo Catasto</th>" +
                                "<th class='hidden'>id Tipo Particella</th>" +
                                "<th>Tipo Particella</th>" +
                                "<th>Comune Censuario</th>" +
                                "<th>N.Mappale/Particella<th> " +
                                "<th>Estensione</th>" +
                                "<th>Subalterno</th>" +
                                "<th>Categoria</th>" +
                                "<th></th>" +
                                "</tr>";
                    }
                    for (var i = 0; i < data.total; i++) {
                        var button = "<button onclick='selezionaDatoCatastale(" + i + ")' class='datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all' role='button' aria-disabled='false'>" +
                                "<span class='ui-button-text'>Seleziona</span>" +
                                "</button>";
                        var tRow = "";
                        if (tipoCatasto == "1") {
                            tRow = tRow + "<tr id='row_" + i + "'>" +
                                    "<td class='hidden'>" + e(data.rows[i].idTipoSistemaCatastale) + "</td>" +
                                    "<td class='hidden'>" + e(data.rows[i].idTipoUnita) + "</td>" +
                                    "<td>" + e(data.rows[i].desTipoUnita) + "</td>" +
                                    "<td>" + e(data.rows[i].sezione) + "</td>" +
                                    "<td>" + e(data.rows[i].foglio) + "</td>" +
                                    "<td>" + e(data.rows[i].mappale) + "</td>" +
                                    "<td>" + e(data.rows[i].subalterno) + "</td>" +
                                    "<td>" + e(data.rows[i].categoria) + "</td>" +
                                    "<td><input type='hidden' name='codImmobile' id='row_codImmobile_" + i + "' value='" + data.rows[i].codImmobile + "' />" + button + "</td>" +
                                    "</tr>";
                        }
                        if (tipoCatasto == "2") {
                            tRow = tRow + "<tr id='row_" + i + "'>" +
                                    "<td class='hidden'>" + e(data.rows[i].idTipoSistemaCatastale) + "</td>" +
                                    "<td class='hidden'>" + e(data.rows[i].idTipoParticella) + "</td>" +
                                    "<td>" + e(data.rows[i].desTipoParticella) + "</td>" +
                                    "<td>" + e(data.rows[i].comuneCensuario) + "</td>" +
                                    "<td>" + e(data.rows[i].mappale) + "</td>" +
                                    "<td>" + e(data.rows[i].estensioneParticella) + "</td>" +
                                    "<td>" + e(data.rows[i].subalterno) + "</td>" +
                                    "<td>" + e(data.rows[i].categoria) + "</td>" +
                                    "<td><input type='hidden' name='codImmobile' id='row_codImmobile_" + i + "' value='" + data.rows[i].codImmobile + "' />" + button + "</td>" +
                                    "</tr>";
                        }
                        table = table + tRow;
                    }
                    table = table + "</tbody></table>";
                    ul.append(table);
                    windowsCercaCatasto(wHeight);
                }
            }
        });

    }
    function windowsCercaCatasto(wHeight) {
        $('#windowCercaCatasto').dialog({
            title: '<spring:message code="datiCatastali.windowsCerca.title"/>',
            modal: true,
            height: wHeight,
            width: '90%',
            close: function () {
                $(this).html('');
            },
            buttons: {
                'Annulla': function () {
                    $(this).dialog("close");
                }
            }
        });
    }
    function selezionaDatoCatastale(tableIndex, index) {
        var tr = $('#row_' + tableIndex);
        var idTipoSistemaCatastale = $(tr).find("td").eq(0).html();
        $('#datoCatastale_idTipoSistemaCatastale').val(idTipoSistemaCatastale);

        var codImmobile = $('#row_codImmobile_' + tableIndex).val();
        $('#datoCatastale_codImmobile').val(codImmobile);

        if (idTipoSistemaCatastale == "1") {
            $('#datoCatastale_idTipoUnita').val($(tr).find("td").eq(1).html());
            $('#datoCatastale_sezione').val($(tr).find("td").eq(3).html());
            $('#datoCatastale_foglio').val($(tr).find("td").eq(4).html());
            $('#datoCatastale_mappale').val($(tr).find("td").eq(5).html());
            $('#datoCatastale_subalterno').val($(tr).find("td").eq(6).html());
            $('#datoCatastale_categoria').val($(tr).find("td").eq(7).html());
        }
        if (idTipoSistemaCatastale == "2") {
            $('#datoCatastale_idTipoParticella').val($(tr).find("td").eq(1).html());
            $('#datoCatastale_comuneCensuario').val($(tr).find("td").eq(3).html());
            $('#datoCatastale_mappaleTavolare').val($(tr).find("td").eq(4).html());
            $('#datoCatastale_estensioneParticella').val($(tr).find("td").eq(5).html());
            $('#datoCatastale_subalternoTavolare').val($(tr).find("td").eq(6).html());
        }

        //Pulizia tabella e visualizzazione pulsante
        $('#catastoTable').remove();
        $('#windowCercaCatasto').dialog("close");
    }
    function e(val) {
        if (val && val !== 'undefined') {
            return val;
        }
        return '';
    }


</script>    


<spring:message code="datiCatastali.select" var="datiCatastaliSelect"/>
<div class="DatiCatastali imbox_frame ${margine_inbox_frame}">
    <input id="datiCatastali_idPratica" class="datiCatastali idPratica" type="hidden" value="${pratica.idPratica}" name="idPratica"/>    
    
    <div class="nascondiutente table-add-link" id="aggiungiDatoCatastale">
        <a href="#" id="add_datiCatastali_btn">
            <spring:message code="datiCatastali.button.aggiungi"/>
            <img src="${path}/themes/default/images/icons/add.png" alt="<spring:message code="datiCatastali.button.aggiungi"/>" title="<spring:message code="datiCatastali.button.aggiungi"/>">
        </a>
    </div>
	<div id="tableCadastralDataDiv">
	
		<table id="tableCadastralDataTable" cellspacing="0" cellpadding="0" class="master">
		
			<tr>
				<th><spring:message	code="pratica.comunicazione.dettaglio.immobile.tipocatasto" /></th>
				<th><spring:message	code="pratica.comunicazione.dettaglio.immobile.tipoUnita" /></th>
				<th><spring:message code="pratica.comunicazione.dettaglio.immobile.sezione" /></th>
				<th><spring:message code="pratica.comunicazione.dettaglio.immobile.foglio" /></th>
				<th><spring:message code="pratica.comunicazione.dettaglio.immobile.particella" /></th>
				<th><spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno" /></th>
				<th class="width10"><spring:message code="anagrafica.ricerca.grid.azioni"/></th>
			</tr>
		
		
		    	
		    <c:if test="${not empty praticaDettaglio.datiCatastali}">
				<c:forEach items="${praticaDettaglio.datiCatastali}" var="immobile" begin="0">
                    <c:set var="class_dato_catastale" value=""/>
                    <c:if test="${immobile.confermato == 'true'}">
                        <c:set var="class_dato_catastale" value="confermataBG"/>
                    </c:if> 
                    <c:if test="${not empty immobile.counter}">
                        <c:set var="indexDatoCatastale" value="CNT-${immobile.counter}"/>
                    </c:if>
                    <c:if test="${not empty immobile.idImmobile}">
                        <c:set var="indexDatoCatastale" value="IM-${immobile.idImmobile}"/>
                    </c:if>

					<tr id="datoCatastale_${indexDatoCatastale}">
						<td class="${class_dato_catastale}">${immobile.desSistemaCatastale}</td>
						<td class="${class_dato_catastale}">${immobile.desTipoUnita }</td>
						<td class="${class_dato_catastale}">${immobile.sezione }</td>
						<td class="${class_dato_catastale}">${immobile.foglio }</td>
						<td class="${class_dato_catastale}">${immobile.mappale }</td>						
						<td class="${class_dato_catastale}">${immobile.subalterno }</td>	
						<td class="${class_dato_catastale}">
							<c:if test="${not empty immobile.urlCatasto}">
				                <a href="${immobile.urlCatasto}" target="_blank" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>">
				                    <img src="${path}/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>">
				                </a>
				            </c:if>
				         	<a class="datiCatastali cerca_lente_dettaglio showdetail-datoCatastale" data-holder="${indexDatoCatastale}" data-counter="${immobile.counter}" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.button"/>"></a>
					     	&nbsp;
							<a class="nascondiutente datoCatastale box-unlink-datoCatastale" data-holder="${indexDatoCatastale}" data-counter="${immobile.counter}" title="<spring:message code="datiCatastali.button.eliminaCatastale"/>">
                                <img src="${path}/themes/default/images/icons/link_break_big.png" alt="<spring:message code="datiCatastali.button.eliminaCatastale"/>" title="<spring:message code="datiCatastali.button.eliminaCatastale"/>">
                            </a>
			                </a>
						</td>				
					</tr>
				</c:forEach>
			</c:if>
		
			<c:if test="${empty praticaDettaglio.datiCatastali}">
				<tr id="cadastralDataEmptyRow">
					<td colspan="7"><spring:message code="pratica.comunicazione.dettaglio.dati.catastali.noresult" /></td>
				</tr>
			</c:if>
		</table>
	</div>
	<div id="timeLineCadastralDataDiv" class="hidden">

        <div class="clear"></div>
        <div id="dialog-form-datoCatastale" title="Dato catastale" class="datiCatastaliDiv uniForm">
            <div class="modalError"></div>
            <div class="inlineLabels">
                <div class="ctrlHolder DatiCatastali">
                    <input name="idImmobile" value="" id="datoCatastale_idImmobile" type="text" class="datiCatastali idImmobile hidden" />
                    <input name="codImmobile" value="" id="datoCatastale_codImmobile" type="text" class="datiCatastali codImmobile hidden" />                    
                    <input name="counter" value="" id="datoCatastale_counter" type="text" class="datiCatastali counter hidden" />
                    <ul id="datiCatastali" class="DatiCatastaliSingle">
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.desTipoSistemaCatastale"/></label>
                            <select id="datoCatastale_idTipoSistemaCatastale" class="datiCatastali idTipoSistemaCatastale">
                                <option value="">${datiCatastaliSelect}</option>
                                <c:forEach items="${tipoSistemaCatastaleDtoList}" begin="0"  var="tipoSistemaCatastaleDto" varStatus="loop">
                                    <option value="${tipoSistemaCatastaleDto.idTipoSistemaCatastale}">${tipoSistemaCatastaleDto.descrizione}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="datoCatastale_tipoUnita_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.desTipoUnita"/></label>
                            <select id="datoCatastale_idTipoUnita" class="datiCatastali idTipoUnita">
                                <option value="">${datiCatastaliSelect}</option>
                                <c:forEach items="${tipoUnitaDtoList}" begin="0"  var="tipoUnitaDto" varStatus="loop">
                                    <option value="${tipoUnitaDto.idTipoUnita}">${tipoUnitaDto.descrizione}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="datoCatastale_tipoParticella_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.desTipoParticella"/></label>
                            <select id="datoCatastale_idTipoParticella" class="datiCatastali idTipoParticella">
                                <option value="">${datiCatastaliSelect}</option>
                                <c:forEach items="${tipoParticellaDtoList}" begin="0"  var="tipoParticellaDto" varStatus="loop">
                                    <option value="${tipoParticellaDto.idTipoParticella}">${tipoParticellaDto.descrizione}</option>
                                </c:forEach>
                            </select>
                        </div>                             
                    </li>
                    <li>
                        <div class="datoCatastale_sezione_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.sezione"/></label>
                            <input name="sezione" value="" id="datoCatastale_sezione" type="text" class="datiCatastali sezione" />
                        </div>
                        <div class="datoCatastale_comunCensuario_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.ComuneCensuario"/></label>
                            <input name="comuneCensuario" value="" id="datoCatastale_comuneCensuario" type="text" class="datiCatastali comuneCensuario" />
                        </div>                            
                    </li>
                    <li>
                        <div class="datoCatastale_foglio_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.foglio"/></label>
                            <input name="foglio" value="" id="datoCatastale_foglio" type="text" class="datiCatastali foglio" />
                        </div>
                        <div class="datoCatastale_mappaleTavolare_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.particella"/></label>
                            <input name="mappaleTavolare" value="" id="datoCatastale_mappaleTavolare" type="text" class="datiCatastali mappaleTavolare" />
                        </div>                            
                    </li> 
                    <li>
                        <div class="datoCatastale_mappale_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.mappale"/></label>
                            <input name="mappale" value="" id="datoCatastale_mappale" type="text" class="datiCatastali mappale" />
                        </div>

                        <div class="datoCatastale_estensioneParticella_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.estensioneParticella"/></label>
                            <input name="estensioneParticella" value="" id="datoCatastale_estensioneParticella" type="text" class="datiCatastali estensioneParticella" />
                        </div>                             
                    </li>  
                    <li>
                        <div class="datoCatastale_subalterno_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.subalterno"/></label>
                            <input name="subalterno" value="" id="datoCatastale_subalterno" type="text" class="datiCatastali subalterno" />
                        </div>
                        <div class="datoCatastale_subalternoTavolare_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.subalterno"/></label>
                            <input name="subalternoTavolare" value="" id="datoCatastale_subalternoTavolare" type="text" class="datiCatastali subalternoTavolare" />
                        </div>                                                  
                    </li>
                    <li>
                    	 <div class="datoCatastale_categoria_div ctrlHolder distanziatore hidden">
                            <label class="required"><spring:message code="datiCatastali.categoria"/></label>
                            <input name="categoria" value="" id="datoCatastale_categoria" type="text" class="datiCatastali categoria" />
                        </div>   
                    </li>                     
                </ul>
            </div>
        </div>
    	</div>
	</div>
</div>
<div id="windowCercaCatasto" class="modal-content">
    <div id="catastoTable">
    </div>
</div>   