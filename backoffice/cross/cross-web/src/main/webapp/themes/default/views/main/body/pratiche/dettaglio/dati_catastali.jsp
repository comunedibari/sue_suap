<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
    var urlDatiCatastali = "${path}/pratiche/dettaglio/formDatiCatastaliAjax.htm";
    var esistenzaRicercaCatasto = "${esistenzaRicercaCatasto}";
    var urlCercaCatasto = "${path}/pratica/ricercatocatasto.htm";
    var msgDelete = '<spring:message code="pratica.comunicazione.dettaglio.immobile.cancella"/>';
    $(document).ready(function () {
        $("#dialog-form").dialog({
            autoOpen: false,
            height: $(window).height() * 0.8,
            width: '50%',
            modal: true,
            close: function () {
                $("#dialog-form .datiCatastali").val("");
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
            $("#dialog-form").dialog('option', 'buttons', bottoni);
            $("#dialog-form").dialog("open");
        });
        $("#datoCatastale_idTipoSistemaCatastale").on("change", function (event) {
            var sistema = $('#datoCatastale_idTipoSistemaCatastale').find(":selected").val();
            cambiaSistemaCatastale(sistema);
        });

        $('.datoCatstale.box-unlink-datoCatastale').on('click', deleteDatoCatastale);
        $('.datoCatstale.cerca_lente_rosso.showdetail-datoCatastale').on('click', modificaDatoCatastale);
    });

    function eseguiClick(action) {
        var idPratica = $(".datiCatastali.idPratica").val();
        var idTipoSistemaCatastale = $(".datiCatastali.idTipoSistemaCatastale").val();
        var idTipoUnita = $(".datiCatastali.idTipoUnita").val();
        var sezione = $(".datiCatastali.sezione").val();
        var foglio = $(".datiCatastali.foglio").val();
        var mappale = "";
        var categoria = $(".datiCatastali.categoria").val();
        var subalterno = "";      
        if (idTipoSistemaCatastale == "1") {
            mappale = $(".datiCatastali.mappale").val();
            subalterno = $(".datiCatastali.subalterno").val();
        }
        if (idTipoSistemaCatastale == "2") {
            mappale = $(".datiCatastali.mappaleTavolare").val();
            subalterno = $(".datiCatastali.subalternoTavolare").val();
        }
        var idImmobile = $(".datiCatastali.idImmobile").val();
        var codImmobile = $(".datiCatastali.codImmobile").val();
        var idTipoParticella = $(".datiCatastali.idTipoParticella").val();
        var comuneCensuario = $(".datiCatastali.comuneCensuario").val();
        var estensioneParticella = $(".datiCatastali.estensioneParticella").val();
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
                    $("#dialog-form").dialog("close");
                    var datoCatastale = data.datoCatastale;
                    if (action === "inserisciDatoCatastale") {
                        var datiDatoCatastale = $("#datoCatastale_X").clone();
                        datiDatoCatastale.html(datiDatoCatastale.html().replace(/X/g, datoCatastale.idImmobile));
                        datiDatoCatastale.attr("id", "datoCatastale_" + datoCatastale.idImmobile);
                        var row = $('<td />').attr('id', 'TDdatoCatastale_' + datoCatastale.idImmobile).append(datiDatoCatastale);
                        row.addClass("DatiCatastaliElement");
                        $("tr.DatiCatastali").append(row);
                        $("#datoCatastale_" + datoCatastale.idImmobile + ' .datoCatstale.box-unlink-datoCatastale').on('click', deleteDatoCatastale);
                        $("#datoCatastale_" + datoCatastale.idImmobile + ' .showdetail-datoCatastale').on('click', modificaDatoCatastale);
                        $("#datoCatastale_" + datoCatastale.idImmobile + ' .showdetail-datoCatastale').attr("data-holder", datoCatastale.idImmobile);
                        if ($(".DatiCatastaliElement").length > 3) {
                            $(".DatiCatastali.imbox_frame").removeClass("margine_imbox_frame");
                            $(".DatiCatastali.imbox_frame").addClass("margine_imbox_frame");
                            $(".DatiCatastali.controllo_sinistra").removeClass("hidden");
                            $(".DatiCatastali.controllo_destra").removeClass("hidden");
                            $('#DatiCatastali-box-right').attr('onclick', '').unbind('click');
                            $("#DatiCatastali-box-right").on("click", function () {
                                item_animate($(".DatiCatastaliElement").length, '#datiCatastali_box', '-=277px');
                            });
                            $('#DatiCatastali-box-left').attr('onclick', '').unbind('click');
                            $("#DatiCatastali-box-left").on("click", function () {
                                item_animate($(".DatiCatastaliElement").length, '#datiCatastali_box', '+=277px');
                            });
                        }
                    }

                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_comuneCensuario").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_comuneCensuario").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_estensioneParticella").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_estensioneParticella").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_tipoParticella").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_tipoParticella").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_mappaleTavolare").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_mappaleTavolare").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_subalternoTavolare").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_subalternoTavolare").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_subalterno").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_subalterno").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_categoria").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_categoria").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_mappale").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_mappale").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_foglio").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_foglio").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_sezione").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_sezione").addClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_tipoUnita").removeClass("hidden");
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .box_tipoUnita").addClass("hidden");

                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.tipoCatasto").val(datoCatastale.desSistemaCatastale);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.tipoUnita").val(datoCatastale.desTipoUnita);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.sezione").val(datoCatastale.sezione);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.foglio").val(datoCatastale.foglio);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.mappale").val(datoCatastale.mappale);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.subalterno").val(datoCatastale.subalterno);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.categoria").val(datoCatastale.categoria);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.subalternoTavolare").val(datoCatastale.subalterno);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.mappaleTavolare").val(datoCatastale.mappale);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.tipoParticella").val(datoCatastale.desTipoParticella);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.estensioneParticella").val(datoCatastale.estensioneParticella);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.comuneCensuario").val(datoCatastale.comuneCensuario);
                    $("#datoCatastale_" + datoCatastale.idImmobile + " .input_anagrafica_disable.codImmobile").val(datoCatastale.codImmobile);

                    var catasto = datoCatastale.idTipoSistemaCatastale;
                    if (catasto == 1) {
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_tipoUnita").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_sezione").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_foglio").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_mappale").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_subalterno").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_categoria").removeClass("hidden");
                    }
                    if (catasto == 2) {
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_comuneCensuario").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_estensioneParticella").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_tipoParticella").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_mappaleTavolare").removeClass("hidden");
                        $("#datoCatastale_" + datoCatastale.idImmobile + " .box_subalternoTavolare").removeClass("hidden");
                    }
                }
            }
        });
    }
    function cambiaSistemaCatastale(sistema) {
        if (sistema == 1) {
            // ordinario
            $("#dialog-form .datoCatastale_tipoUnita_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_sezione_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_foglio_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_mappale_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_subalterno_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_categoria_div").removeClass("hidden");

            $("#dialog-form .datoCatastale_tipoParticella_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_tipoParticella_div").addClass("hidden");

            $("#dialog-form .datoCatastale_comunCensuario_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_comunCensuario_div").addClass("hidden");

            $("#dialog-form .datoCatastale_mappaleTavolare_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_mappaleTavolare_div").addClass("hidden");

            $("#dialog-form .datoCatastale_estensioneParticella_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_estensioneParticella_div").addClass("hidden");

            $("#dialog-form .datoCatastale_subalternoTavolare_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_subalternoTavolare_div").addClass("hidden");

        }
        if (sistema == 2) {
            // tavolare
            $("#dialog-form .datoCatastale_tipoParticella_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_comunCensuario_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_mappaleTavolare_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_estensioneParticella_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_subalternoTavolare_div").removeClass("hidden");

            $("#dialog-form .datoCatastale_tipoUnita_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_tipoUnita_div").addClass("hidden");

            $("#dialog-form .datoCatastale_sezione_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_sezione_div").addClass("hidden");

            $("#dialog-form .datoCatastale_foglio_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_foglio_div").addClass("hidden");

            $("#dialog-form .datoCatastale_mappale_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_mappale_div").addClass("hidden");

            $("#dialog-form .datoCatastale_subalterno_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_subalterno_div").addClass("hidden");
            
            $("#dialog-form .datoCatastale_categoria_div").removeClass("hidden");
            $("#dialog-form .datoCatastale_categoria_div").addClass("hidden");

        }

    }
    function modificaDatoCatastale(event) {
        var idImmobile = $(this).attr('data-holder');
        var idPratica = $("#datiCatastali_idPratica").val();
        $.ajax({
            url: urlDatiCatastali,
            dataType: "json",
            type: "POST",
            data: {
                action: "leggiDatoCatastale",
                idPratica: idPratica,
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
                    $("#dialog-form").dialog('option', 'buttons', bottoni);
                    $("#dialog-form").dialog('open');
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
        var idImmobile = $(event.target).attr('data-holder');
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
                                        $("#datoCatastale_" + idImmobile).remove();
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
        var categoria = $('#datoCatastale_categoria').val();
        if (mappaleTavolare && mappaleTavolare != "") {
            mappale = mappaleTavolare;
        }
        var estensioneParticella = $('#datoCatastale_estensioneParticella').val();
        var subalterno = $('#datoCatastale_subalterno').val();
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
<!-- Cont div -->
<c:set var="conto_div" value="0" scope="page" />
<c:set var="mostraFreccie" value="hidden" scope="page" />
<c:set var="margine_inbox_frame" value="" scope="page" />
<c:forEach items="${pratica.datiCatastaliList}" begin="0"  var="datoCatastale" varStatus="loop">
    <c:set var="conto_div" value="${conto_div + 1}" scope="page"/>
</c:forEach>

<!-- Cont div -->
<c:if test="${conto_div > 3}">
    <c:set var="mostraFreccie" value="" />
    <c:set var="margine_inbox_frame" value="margine_imbox_frame" scope="page" />
</c:if>
<spring:message code="datiCatastali.select" var="datiCatastaliSelect"/>
<div class="DatiCatastali imbox_frame ${margine_inbox_frame}">
    <input id="datiCatastali_idPratica" class="datiCatastali idPratica" type="hidden" value="${pratica.idPratica}" name="idPratica"/>    
    <div class="nascondiutente table-add-link" id="aggiungiDatoCatastale">
        <a href="#" id="add_datiCatastali_btn">
            <spring:message code="datiCatastali.button.aggiungi"/>
            <img src="${path}/themes/default/images/icons/add.png" alt="<spring:message code="datiCatastali.button.aggiungi"/>" title="<spring:message code="datiCatastali.button.aggiungi"/>">
        </a>
    </div>

    <div class="DatiCatastali controllo_sinistra ${mostraFreccie}">
        <div class="controller_box">
            <div id="DatiCatastali-box-left" class="box_left" onclick="item_animate('<c:out value="${conto_div}"></c:out>', '#datiCatastali_box', '-=277px')"></div>
            </div>
        </div>

        <div id="datiCatastali_box">
            <table cellpadding="0" cellspacing="0">
                <tr class="DatiCatastali">            
                <c:forEach items="${dettaglio.datiCatastali}" begin="0"  var="immobile" varStatus="loop">
                    <c:if test="${immobile.idTipoSistemaCatastale == 1}">
                        <c:set var="class_dato_catastale_tavolare" value="hidden"/>
                        <c:set var="class_dato_catastale" value=""/>
                    </c:if>
                    <c:if test="${immobile.idTipoSistemaCatastale == 2}">
                        <c:set var="class_dato_catastale_tavolare" value=""/>
                        <c:set var="class_dato_catastale" value="hidden"/>
                    </c:if>
                    <td id="TDdatoCatastale_${immobile.idImmobile}" class="DatiCatastaliElement">
                        <div id="datoCatastale_${immobile.idImmobile}">
                            <div class="circle <c:if test="${loop.last}">lastCircle</c:if>">
                                    <div class="ctrlHolder">
                                        <label class="required" style="display:inline">
                                        <spring:message code="datiCatastali.catasto.title"/>
                                    </label>
                                    <a class="nascondiutente datoCatstale box-unlink-datoCatastale" box-unlink-datoCatastale" data-holder="${immobile.idImmobile}">
                                       <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="datiCatastali.button.eliminaCatastale"/>" title="<spring:message code="datiCatastali.button.eliminaCatastale"/>">
                                        <spring:message code="datiCatastali.button.eliminaCatastale"/>
                                    </a>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.tipocatasto"/>
                                    </label>
                                    <input class="input_anagrafica_disable tipoCatasto" type="text" disabled="" value="${immobile.desSistemaCatastale}">
                                </div> 
                                <div class="box_tipoUnita ctrlHolder ${class_dato_catastale}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.tipoUnita"/>
                                    </label>
                                    <input class="input_anagrafica_disable tipoUnita" type="text" disabled="" value="${immobile.desTipoUnita}">
                                </div>
                                <div class="box_tipoParticella ctrlHolder ${class_dato_catastale_tavolare}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.tipoParticella"/>
                                    </label>
                                    <input class="input_anagrafica_disable tipoParticella" type="text" disabled="" value="${immobile.desTipoParticella}">
                                </div>                                    
                                <div class="box_sezione ctrlHolder ${class_dato_catastale}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.sezione"/>
                                    </label>
                                    <input class="input_anagrafica_disable sezione" type="text" disabled="" value="${immobile.sezione}">
                                </div>
                                <div class="box_comuneCensuario ctrlHolder ${class_dato_catastale_tavolare}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.comuneCensuario"/>
                                    </label>
                                    <input class="input_anagrafica_disable comuneCensuario" type="text" disabled="" value="${immobile.comuneCensuario}">
                                </div>
                                <div class="box_foglio ctrlHolder ${class_dato_catastale}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.foglio"/>
                                    </label>
                                    <input class="input_anagrafica_disable foglio" type="text" disabled="" value="${immobile.foglio}">
                                </div>
                                <div class="box_mappaleTavolare ctrlHolder ${class_dato_catastale_tavolare}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.particella"/>
                                    </label>
                                    <input class="input_anagrafica_disable mappaleTavolare" type="text" disabled="" value="${immobile.mappale}">
                                </div>                                    
                                <div class="box_mappale ctrlHolder ${class_dato_catastale}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.mappale"/>
                                    </label>
                                    <input class="input_anagrafica_disable mappale" type="text" disabled="" value="${immobile.mappale}">
                                </div>
                                <div class="box_estensioneParticella ctrlHolder ${class_dato_catastale_tavolare}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.estensioneParticella"/>
                                    </label>
                                    <input class="input_anagrafica_disable estensioneParticella" type="text" disabled="" value="${immobile.estensioneParticella}">
                                </div>                                    
                                <div class="box_subalterno ctrlHolder ${class_dato_catastale}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno"/>
                                    </label>
                                    <input class="input_anagrafica_disable subalterno" type="text" disabled="" value="${immobile.subalterno}">
                                </div>
                                <div class="box_subalternoTavolare ctrlHolder ${class_dato_catastale_tavolare}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno"/>
                                    </label>
                                    <input class="input_anagrafica_disable subalternoTavolare" type="text" disabled="" value="${immobile.subalterno}">
                                </div>
                                  <div class="box_categoria ctrlHolder ${class_dato_catastale_catastale}">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.categoria"/>
                                    </label>
                                    <input class="input_anagrafica_disable categoria" type="text" disabled="" value="${immobile.categoria}">
                                </div>
                                <c:if test="${! empty immobile.urlCatasto}">
                                    <a href="${immobile.urlCatasto}" target="_blank">
                                        <img src="${path}/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>">
                                        <spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>
                                    </a>
                                </c:if>
                                <div class="datoCatstale cerca_lente_rosso showdetail-datoCatastale" data-holder="${immobile.idImmobile}"><spring:message code="pratica.comunicazione.dettaglio.immobile.button"/></div>
                            </div>
                        </div>
                    </td>
                </c:forEach>
            </tr>
        </table>
    </div>

    <div class="DatiCatastali controllo_destra ${mostraFreccie}">
        <div class="controller_box">
            <div id="DatiCatastali-box-right" class="box_right" onclick="item_animate('<c:out value="${conto_div}"></c:out>', '#datiCatastali_box', '+=277px')"></div>
            </div>
        </div>

        <div class="clear"></div>
        <div id="dialog-form" title="Dato catastale" class="datiCatastaliDiv uniForm">
            <div class="modalError"></div>
            <div class="inlineLabels">
                <div class="ctrlHolder DatiCatastali">
                    <input name="idImmobile" value="" id="datoCatastale_idImmobile" type="text" class="datiCatastali idImmobile hidden" />
                    <input name="codImmobile" value="" id="datoCatastale_codImmobile" type="text" class="datiCatastali codImmobile hidden" />
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
<div class="hidden">
    <div id="datoCatastale_X">
        <div class="circle">
            <div class="ctrlHolder">
                <label class="required" style="display:inline">
                    <spring:message code="datiCatastali.catasto.title"/>
                </label>
                <a class="datoCatstale box-unlink-datoCatastale" data-holder="X">
                    <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="datiCatastali.button.eliminaCatastale"/>" title="<spring:message code="datiCatastali.button.eliminaCatastale"/>">
                    <spring:message code="datiCatastali.button.eliminaCatastale"/>
                </a>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.tipocatasto"/>
                </label>
                <input class="input_anagrafica_disable tipoCatasto" type="text" disabled="" value="">
            </div>                        
            <div class="box_tipoUnita ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.tipoUnita"/>
                </label>
                <input class="input_anagrafica_disable tipoUnita" type="text" disabled="" value="">
            </div>
            <div class="box_tipoParticella ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.tipoParticella"/>
                </label>
                <input class="input_anagrafica_disable tipoParticella" type="text" disabled="" value="">
            </div>                
            <div class="box_sezione ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.sezione"/>
                </label>
                <input class="input_anagrafica_disable sezione" type="text" disabled="" value="">
            </div>
            <div class="box_comuneCensuario ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.comuneCensuario"/>
                </label>
                <input class="input_anagrafica_disable comuneCensuario" type="text" disabled="" value="">
            </div>                
            <div class="box_foglio ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.foglio"/>
                </label>
                <input class="input_anagrafica_disable foglio" type="text" disabled="" value="">
            </div>
            <div class="box_mappaleTavolare ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.particella"/>
                </label>
                <input class="input_anagrafica_disable mappaleTavolare" type="text" disabled="" value="">
            </div>                
            <div class="box_mappale ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.mappale"/>
                </label>
                <input class="input_anagrafica_disable mappale" type="text" disabled="" value="">
            </div>

            <div class="box_estensioneParticella ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.estensioneParticella"/>
                </label>
                <input class="input_anagrafica_disable estensioneParticella" type="text" disabled="" value="">
            </div>  

            <div class="box_subalterno ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno"/>
                </label>
                <input class="input_anagrafica_disable subalterno" type="text" disabled="" value="">
            </div>

            <div class="box_subalternoTavolare ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno"/>
                </label>
                <input class="input_anagrafica_disable subalternoTavolare" type="text" disabled="" value="">
            </div>
            
            <div class="box_categoria ctrlHolder">
                <label class="required">
                   <spring:message code="pratica.comunicazione.dettaglio.immobile.categoria"/>
                </label>
                <input class="input_anagrafica_disable categoria" type="text" disabled="" value="">
            </div>  
                            
            <c:if test="${! empty immobile.urlCatasto}">
                <a href="${immobile.urlCatasto}" target="_blank">
                    <img src="${path}/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>
                </a>
            </c:if>
            <div class="datoCatstale cerca_lente_rosso showdetail-datoCatastale" data-holder=""><spring:message code="pratica.comunicazione.dettaglio.immobile.button"/></div>
        </div>
    </div>
</div>
<div id="windowCercaCatasto" class="modal-content">
    <div id="catastoTable">
    </div>
</div>        
