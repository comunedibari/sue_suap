var url = document.URL;
url = url.split("?");
var url0 = url[0].replace(".htm", "/ajax.htm");
if (url[1]) {
    url = url0 + "?" + url[1];
}
else {
    url = url0;
}

function updateParamAndReload(key, value)
{
    key = encodeURI(key);
    value = encodeURI(value);

    var kvp = document.location.search.substr(1).split('&');

    var i = kvp.length;
    var x;
    while (i--)
    {
        x = kvp[i].split('=');

        if (x[0] == key)
        {
            x[1] = value;
            kvp[i] = x.join('=');
            break;
        }
    }

    if (i < 0) {
        kvp[kvp.length] = [key, value].join('=');
    }

    //this will reload the page, it's likely better to store this until finished
    document.location.search = kvp.join('&');
}

var validator = {
    email: /^[\w\-\.]*[\w\.]\@[\w\.]*[\w\-\.]+[\w\-]+[\w]\.+[\w]+[\w $]/,
    letters: /^[a-z\u00E0-\u00FC\-\*\(\)'= .]+$/i,
    alphanumeric: /^[a-zA-Z0-9\u00E0-\u00FC\-\*\(\)/ .]+$/i,
    date: /^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$/,
    numeric: /^[0-9.]+$/,
    year: /^[1-9]\d{3}$/
};
$(document).ready(function() {

    $(".dataPicker").datepicker({
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "-120: + 0"
    });
    ////    $(".sorgente #dataNascita" ).datepicker();
    //            dateFormat: "dd/mm/yy" 
    //            changeMonth: true, 
    //            changeYear: true, 
    //            yearRange:"-120: + 0"
    //        });
    $(".containerAnagrafiche .sorgente input").val("");
    $(".containerAnagrafiche .destinazione").html($(".containerAnagrafiche .sorgente").html());
    $(".destinazione input:text").removeClass("ui-widget ui-widget-content ui-corner-all");
    $('.destinazione .dettaglio #counter').attr('id', 'counter1');
    $('.destinazione .dettaglio #tipoAnagrafica').attr('id', 'tipoAnagrafica1');
    $('.destinazione .dettaglio #flgIndividuale').attr('id', 'flgIndividuale1');
    $('.destinazione .dettaglio #flgIndividualeHidden').attr('id', 'flgIndividualeHidden1');
    $('.destinazione .dettaglio #varianteAnagrafica').attr('id', 'varianteAnagrafica1');
    $('.destinazione .dettaglio #idAnagrafica').attr('id', 'idAnagrafica1');
    $('.destinazione .dettaglio #desTipoRuolo').attr('id', 'desTipoRuolo1');
    $('.destinazione .dettaglio #codTipoRuolo').attr('id', 'codTipoRuolo1');
    $('.destinazione .dettaglio #idTipoRuolo').attr('id', 'idTipoRuolo1');
    $('.destinazione .dettaglio #codiceFiscale').attr('id', 'codiceFiscale1');
    $('.destinazione .dettaglio #partitaIva').attr('id', 'partitaIva1');
    $('.destinazione .dettaglio #nome').attr('id', 'nome1');
    $('.destinazione .dettaglio #cognome').attr('id', 'cognome1');
    $('.destinazione .dettaglio #sesso').attr('id', 'sesso1');
    $('.destinazione .dettaglio #desCittadinanza').attr('id', 'desCittadinanza1');
    $('.destinazione .dettaglio #idCittadinanza').attr('id', 'idCittadinanza1');
    $('.destinazione .dettaglio #desNazionalita').attr('id', 'desNazionalita1');
    $('.destinazione .dettaglio #idNazionalita').attr('id', 'idNazionalita1');
    $('.destinazione .dettaglio #dataNascita').attr('id', 'dataNascita1');
    $('.destinazione .dettaglio #comuneNascita\\.descrizione').attr('id', 'comuneNascita1.descrizione');
    $('.destinazione .dettaglio #comuneNascita\\.codCatastale').attr('id', 'comuneNascita1.codCatastale');
    $('.destinazione .dettaglio #comuneNascita\\.idComune').attr('id', 'comuneNascita1.idComune');
    $('.destinazione .dettaglio #comuneNascita\\.stato\\.idStato').attr('id', 'comuneNascita1.stato.idStato');
    $('.destinazione .dettaglio #comuneNascita\\.stato\\.descrizione').attr('id', 'comuneNascita1.stato.descrizione');
    $('.destinazione .dettaglio #comuneNascita\\.provincia\\.descrizione').attr('id', 'comuneNascita1.provincia.descrizione');
    $('.destinazione .dettaglio #comuneNascita\\.provincia\\.idProvincie').attr('id', 'comuneNascita1.provincia.idProvincie');
    $('.destinazione .dettaglio #localitaNascita').attr('id', 'localitaNascita1');
    $('.destinazione .dettaglio #denominazione').attr('id', 'denominazione1');
    $('.destinazione .dettaglio #desFormaGiuridica').attr('id', 'desFormaGiuridica1');
    $('.destinazione .dettaglio #idFormaGiuridica').attr('id', 'idFormaGiuridica1');
    $('.destinazione .dettaglio #ProvinciaCciaa\\.descrizione').attr('id', 'ProvinciaCciaa1.descrizione');
    $('.destinazione .dettaglio #ProvinciaCciaa\\.idProvincie').attr('id', 'ProvinciaCciaa1.idProvincie');
    $('.destinazione .dettaglio #nIscrizioneRea').attr('id', 'nIscrizioneRea1');
    $('.destinazione .dettaglio #flgAttesaIscrizioneReaHidden').attr('id', 'flgAttesaIscrizioneReaHidden1');
    $('.destinazione .dettaglio #flgAttesaIscrizioneRiHidden').attr('id', 'flgAttesaIscrizioneRiHidden1');
    $('.destinazione .dettaglio #dataIscrizioneRea').attr('id', 'dataIscrizioneRea1');
    $('.destinazione .dettaglio #nIscrizioneRi').attr('id', 'nIscrizioneRi1');
    $('.destinazione .dettaglio #dataIscrizioneRi').attr('id', 'dataIscrizioneRi1');
    $('.destinazione .dettaglio #numeroIscrizione').attr('id', 'numeroIscrizione1');
    $('.destinazione .dettaglio #provinciaIscrizione\\.descrizione').attr('id', 'provinciaIscrizione1.descrizione');
    $('.destinazione .dettaglio #provinciaIscrizione\\.idProvincia').attr('id', 'provinciaIscrizione1.idProvincia');
    eventConfronta();

    var tipoRuolo = $(".idTipoRuolo");
    if (tipoRuolo !== undefined && tipoRuolo.val() === '1') {
        //E' richiedente, non può essere ditta individuale
        $('.dittaIndividuale').addClass('hidden');
        $('.dittaIndividuale').hide();

    } else {
        $('.dittaIndividuale').removeClass('hidden');
        $('.dittaIndividuale').show();
    }

    $('.sorgente #flgIndividuale').change(function() {
        var isDittaIndividuale = this.value;
        $('.destinazione #flgIndividuale1').val(isDittaIndividuale);
        $('.destinazione #flgIndividualeHidden1').val(isDittaIndividuale);
        $('.sorgente #flgIndividuale').val(isDittaIndividuale);
        $('.sorgente #flgIndividualeHidden').val(isDittaIndividuale);
        if (tipoRuolo.val() === '1') {
            //E' richiedente: è sempre persona fisica
            $('.varianteAnagrafica').val('F');
            $('.dittaIndividuale').addClass('hidden');
            $('.dittaIndividuale').attr('style', 'display:none');
            $('.dittaIndividuale').hide();
        } else {
            $('.dittaIndividuale').removeClass('hidden');
            $('.dittaIndividuale').show();
            $('.I input').removeAttr("disabled");
            $('.I select').removeAttr("disabled");
            if (isDittaIndividuale === 'N') {
                $('.I').hide();
                $('.varianteAnagrafica').val('F');
            } else {
                $('.I').show();
                $('.varianteAnagrafica').val('I');
            }
        }
        if ($('.sorgente #tipoAnagrafica').val() == 'G') {
            $(".G").show();
            $(".G input").removeAttr("disabled");
            $(".sorgente .registroImprese ").show();
            $(".sorgente .registroImprese ").css({
                "font-size": "12px"
            });
            $(".sorgente .ricercaAnagrafe ").hide();
            $(".sorgente .ricercaAnagrafe ").removeAttr('style');
        }
    });

    $('.sorgente .flgAttesaIscrizioneRi select').change(function() {
        var selectedVal = $('.sorgente .flgAttesaIscrizioneRi select option:selected').val();
        $('.sorgente #flgAttesaIscrizioneRiHidden').val(selectedVal);
        //        $('.destinazione #flgAttesaIscrizioneRiHidden').val(selectedVal);
    });

});
function eventConfronta() {
    $("div.confronta").unbind();
    $("div.confronta").click(function() {
        resetAnagrafica();
        var $form = $("<form>").append($(this).find("input").clone());
        $form.attr({
            method: 'post',
            action: url
        });
        $.post(url, $form.serialize(),
                function(data) {
                    EsaminaAnagrafiche(data);
                }, 'json');

    });
    $(".containerAnagrafiche .sorgente .flgAttesaIscrizioneRea select").change(function() {
        var value = $(this).find(":selected").val();
        $(".sorgente .flgAttesaIscrizioneRea .input").val(value);
    });

    $(".containerAnagrafiche .sorgente .flgAttesaIscrizioneRi select").change(function() {
        var value = $(this).find(":selected").val();
        $(".sorgente .flgAttesaIscrizioneRi .input").val(value);
    });
}
function EsaminaAnagrafiche(json)
{

    if (json.errors != null && json.errors.length > 0)
    {
        dialogError(json.errors);
        return;
    }
    confrontaAnagrafiche(json);

}
var elementClickked;

function confrontaAnagrafiche(json)
{
    $(".containerAnagrafiche li >input").val("");
    //^^CS Popolo dati Persona
    var ricercaAnagrafe = false;
    var ricercaAnagrafePG = false;
    if (json.rows[0])
    {
        popolaDatiPersona(".sorgente", json.rows[0]);
        $("#idAnagrafica").val(json.rows[1]['idAnagrafica']);
        $(".sorgente .label").html(datiDaXML);
//        if (json.rows[0]['ricercaAnagrafica'] == "RICERCA_ANAGRAFICA_FISICA") {
//            ricercaAnagrafe = true;
//        }
//        if (json.rows[0]['ricercaAnagrafica'] == "RICERCA_ANAGRAFICA_GIURIDICA") {
//            ricercaAnagrafePG = true;
//        }        
    }
    if (json.rows[1])
    {
        popolaDatiPersona(".destinazione", json.rows[1]);
        $("#idAnagrafica").val(json.rows[1]['idAnagrafica']);
        $(".destinazione .label").html(datiDaDB);
        if (attivaRicercaAnagrafe && json.rows[1]['tipoAnagrafica'] == 'F') {
            ricercaAnagrafe = true;
        }
        if (attivaRicercaAnagrafePG && json.rows[1]['tipoAnagrafica'] == 'G') {
            ricercaAnagrafePG = true;
        }
    }
    if (json.rows[0] && json.rows[0]['recapiti'])
    {
        for (var i = 0; i < json.rows[0]['recapiti'].length; i++)
        {
            if ($(".sorgente .recapito" + i).length == 0)
            {
                var recapito = $(".sorgente .recapito0").clone().removeClass("recapito0").addClass("recapito" + i);
                //^^CS DA ELIMINARE IN VERSIONI FUTURE!!!!
                /**************************/
                recapito.find("input").each(function(index) {
                    var name = $(this).attr("name");
                    if (i > 0) {
                        name = name.replace('0', i);
                    }
                    $(this).attr("name", name);
                });
                /**************************/
                //^^CS DA ELIMINARE IN VERSIONI FUTURE!!!!
                recapito.find("li").each(function(index) {
                    var name = $(this).attr("class") + i;
                    $(this).attr("class", name);
                });

                $(".sorgente .recapito" + (i - 1)).after(recapito);
            }
            popolaRecapiti(".sorgente .recapito" + i, json.rows[0]['recapiti'][i], i);
        }
    }
    if (json.rows[1] && json.rows[1]['recapiti'])
    {
        for (var i = 0; i < json.rows[1]['recapiti'].length; i++)
        {
            if ($(".destinazione .recapito" + i).length == 0)
            {
                var recapito = $(".destinazione .recapito0").clone().removeClass("recapito0").addClass("recapito" + i);
                //^^CS DA ELIMINARE IN VERSIONI FUTURE!!!!
                /**************************/
                recapito.find("input").each(function(index) {
                    $(this).removeClass("ui-widget ui-widget-content ui-corner-all");
                    var name = $(this).attr("name");
                    if (i > 0)
                    {
                        name = name.replace('0', i);
                    }
                    $(this).attr("name", name);
                });
                /**************************/
                //^^CS DA ELIMINARE IN VERSIONI FUTURE!!!!
                recapito.find("li").each(function(index) {
                    var name = $(this).attr("class") + i;
                    $(this).attr("class", name);
                });
                $(".destinazione .recapito" + (i - 1)).after(recapito);
            }
            popolaRecapiti(".destinazione .recapito" + i, json.rows[1]['recapiti'][i], i);
            $(".recapito" + i + " .recapito\\.idRecapito").val(json.rows[1]['recapiti'][i]['idRecapito']);
        }
    }
    $(".confrontaAnagrafiche").removeClass("hidden");
    //var selTipoAnagrafica = getTipoAnagrafica(json.rows[0]['tipoAnagrafica'],json.rows[0]['varianteAnagrafica']);
    var selTipoAnagrafica = json.rows[0]['tipoAnagrafica'];
    var varianteAnagrafica = json.rows[0]['varianteAnagrafica'];
    $(".F").hide();
    $(".P").hide();
    $(".I").hide();
    $(".G").hide();
    $(".F input").attr("disabled", "disabled");
    $(".P input").attr("disabled", "disabled");
    $(".I input").attr("disabled", "disabled");
    $(".G input").attr("disabled", "disabled");
    //PERSONA FISICA
    if (selTipoAnagrafica == "F")
    {
        $(".F").show();
        $(".F input").removeAttr("disabled");
        //        $(".sorgente inputcodiceFiscale").removeAttr( 'style' );
        $(".sorgente .registroImprese ").hide();
        $(".sorgente .registroImprese ").removeAttr('style');

        if (ricercaAnagrafe) {
            $(".sorgente .ricercaAnagrafe ").show();
        }
    }
    if (varianteAnagrafica == 'P') {
        $(".P").show();
        $(".P input").removeAttr("disabled");
    }
    //PROFESSIONISTA
    //DISABILITO IL PROFESSIONISTA: è sempre persona fisica
    //    if (selTipoAnagrafica == "P")
    //    {
    //        $(".F").show();
    //        $(".P").show();
    //        $(".F input").removeAttr("disabled");
    //        $(".P input").removeAttr("disabled");
    //        $(".sorgente input.codiceFiscale").removeAttr( 'style' );
    //        $(".sorgente .registroImprese ").hide();
    //        $(".sorgente .registroImprese ").removeAttr( 'style' );
    //        if (ricercaAnagrafe) {
    //            $(".sorgente .ricercaAnagrafe ").show();
    //            $(".sorgente input.codiceFiscale").css({
    //                "float": "left", 
    //                "width": "40%"
    //            });
    //        }
    //    }
    //DITTA INDIVIDUALE
    //    if (selTipoAnagrafica == "I")
    if (varianteAnagrafica == "I")
    {
        $(".F").show();
        $(".I").show();
        $(".F input").removeAttr("disabled");
        $(".I input").removeAttr("disabled");
        $(".sorgente .registroImprese ").show();
        $(".sorgente .registroImprese ").css({
            "font-size": "12px"
        });
        if (ricercaAnagrafe) {
            $(".sorgente .ricercaAnagrafe ").hide();
            $(".sorgente .ricercaAnagrafe ").removeAttr('style');
        }
    }
    //PERSONA GIURIDICA
    if (selTipoAnagrafica == "G")
    {
        $(".G").show();
        $(".G input").removeAttr("disabled");
        $(".sorgente .registroImprese ").show();
        $(".sorgente .registroImprese ").css({
            "font-size": "12px"
        });
        $(".sorgente .ricercaAnagrafe ").hide();
        $(".sorgente .ricercaAnagrafe ").removeAttr('style');
    }

    var windowY = $(window).height();
    if (json.rows[0]['idTipoRuolo'] === 1) {
        $(".destinazione" + " .dettaglio #flgIndividuale").val('N').change();
        $(".destinazione" + " .dettaglio #flgIndividualeHidden").val('N');
        $(".dittaIndividuale").hide();
    }
    if (json.rows[0]['varianteAnagrafica'] === 'I') {
        $(".sorgente" + " .dettaglio #flgIndividuale").val("S").change();
        $(".sorgente" + " .dettaglio #flgIndividualeHidden").val('S');
    } else {
        $(".sorgente" + " .dettaglio #flgIndividuale").val("N").change();
        $(".sorgente" + " .dettaglio #flgIndividualeHidden").val('N');
    }
    $(".confrontaAnagrafiche").dialog(
            {
                modal: true,
                width: 1000,
                height: windowY * 0.95,
                title: "Confronto anagrafiche:",
                buttons: {
                    Ok: function() {

                        if (isValidAnagrafica() && isValidRecapiti())
                        {
                            $(".sorgente input").removeClass("ui-state-error");
                            var div = $("<div>");
                            div.attr("id", 'salvaAnagrafica');
                            var checkCf = isValidCF();
                            if (checkCf) {
                                $(div).html(messaggioConferma).dialog({
                                    buttons:
                                            {
                                                SI: function() {
                                                    salvaAnagrafica();
                                                    $(this).dialog('close');
                                                },
                                                NO: function() {
                                                    $(this).dialog('close');
                                                }
                                            }
                                });
                            }
                        }
                    },
                    Annulla: function() {
                        $(this).dialog('close');
                    },
                    Copia: function() {
                        copiaTutto();
                    }
                }

            });
    $(".ui-dialog").append('<div id="blank"><br></div>');
    if ($("#abilitazione").val() !== "true")
    {
        $('.ui-dialog-buttonpane button:contains("Copia")').button().remove();
        $('.ui-dialog-buttonpane button:contains("Ok")').button().remove();
    }
    highlight();
    var heightDe = $(".destinazione").height();
    var heightSo = $(".sorgente").height();
    if (heightDe > heightSo)
    {
        $(".azioni").height(heightDe);
        $(".sorgente").height(heightDe);
    } else {
        $(".azioni").height(heightSo);
        $(".destinazione").height(heightSo);
    }
    function copiaTutto() {
        $(".sorgente select").each(function(index) {
            var elementClickked = "";
            if ($(this).closest("li").attr("class") != null) {
                var elementClickked = $(this).closest("li").attr("class").split(" ")[0];
            }
            if ($(this).val() != null && $(this).val() != "" && $(this).attr("nonsicopia") == null) {
                var valore = $(this).val();
                var targetClass = $(this).attr("class");
                var target;
                if (targetClass != null && targetClass != "") {
                    target = ".destinazione ." + elementClickked + " select " + $(this).attr("class").replace(".", "\\.");
                } else {
                    target = ".destinazione ." + elementClickked + " select#" + $(this).attr("id").replace(/\./g, "\\.");
                }
                target = target.replace("ui-widget", "");
                target = target.replace("ui-widget-content", "");
                target = target.replace("ui-corner-all", "");
                target = target.replace("ui-state-highlight", "");
                target = target.replace("ui-autocomplete-loading", "");
                $(target).val(valore).change();
                if (target.indexOf('flgAttesaIscrizioneRi') != -1) {
                    $(".destinazione .dettaglio  .flgAttesaIscrizioneRi select").val(valore);
                    $(".destinazione .dettaglio  #flgAttesaIscrizioneRiHidden1").val(valore);
                } else if (target.indexOf('flgAttesaIscrizioneRea') != -1) {
                    $(".destinazione .dettaglio  .flgAttesaIscrizioneRea select").val(valore);
                    $(".destinazione .dettaglio  #flgAttesaIscrizioneReaHidden1").val(valore);
                } else if (target.indexOf('flgIndividuale') != -1) {
                    $('.destinazione .dettaglio #flgIndividuale1 select').val(valore);
                    $('.destinazione .dettaglio #flgAttesaIscrizioneReaHidden1').val(valore);
                }
            }
        });

        $(".sorgente input").each(function(index) {
            var elementClickked = "";
            if ($(this).closest("li").attr("class") != null) {
                var elementClickked = $(this).closest("li").attr("class").split(" ")[0];
            }
            if ($(this).val() != null && $(this).val() != "" && $(this).attr("nonsicopia") == null)
            {
                var targetClass = $(this).attr("class");
                var target;
                if (targetClass != null && targetClass != "") {
                    if (isRecapito(elementClickked)) {
                        target = ".destinazione ." + elementClickked + " input." + $(this).attr("class").replace(".", "\\.");
                    } else {
                        target = ".destinazione ." + elementClickked + " input " + $(this).attr("class").replace(".", "\\.");
                    }
                }
                else
                {

                    target = ".destinazione ." + elementClickked + " input#" + $(this).attr("id").replace(/\./g, "\\.");
                }
                target = target.replace("ui-widget", "");
                target = target.replace("ui-widget-content", "");
                target = target.replace("ui-corner-all", "");
                target = target.replace("hasDatepicker", "");
                target = target.replace("ui-state-highlight", "");
                target = target.replace("ui-autocomplete-input", "");
                target = target.replace("ui-autocomplete-loading", "");
                target = target.replace("dataPicker", "");
                if ($(this).attr("id") == "comuneNascita.descrizione")
                {
                    $(".destinazione .dettaglio #comuneNascita1\\.descrizione").val($(".sorgente .dettaglio #comuneNascita\\.descrizione").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.idComune").val($(".sorgente .dettaglio #comuneNascita\\.idComune").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.stato\\.descrizione").val($(".sorgente .dettaglio #comuneNascita\\.stato\\.descrizione").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.stato\\.idStato").val($(".sorgente .dettaglio #comuneNascita\\.stato\\.idStato").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.provincia\\.descrizione").val($(".sorgente .dettaglio #comuneNascita\\.provincia\\.descrizione").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.provincia\\.idProvincie").val($(".sorgente .dettaglio #comuneNascita\\.provincia\\.idProvincie").val());
                }
                else
                {
                    var elementClass = $(this).attr("class");
                    //Verifico se mi trovo nel caso di recapito del comune
                    if (elementClass != undefined && elementClass.indexOf("recapito-descComune") !== -1) {
                        var i = elementClickked[elementClickked.length - 1];
                        if (!$.isNumeric(i)) {
                            i = 0;
                        }
                        //                        if (i > 0){
                        //                            var desComune = $(".destinazione .recapitodescComune"+i+" input.recapito\\.descComune" ).val();
                        //                            $(".sorgente .recapitodescComune"+i+" input.recapito\\.descComune" ).val(desComune);
                        //                            var idComune = $(".destinazione .recapitodescComune"+i+" input.recapito\\.idComune" ).val();
                        //                            $(".sorgente .recapitodescComune"+i+" input.recapito\\.idComune" ).val(idComune);
                        //                            var desProvincia = $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.descProvincia" ).val();
                        //                            $(".sorgente .recapitodescProvincia"+i+" input.recapito\\.descProvincia" ).val(desProvincia);
                        //                            var idProvincia = $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.idProvincia" ).val();
                        //                            $(".sorgente .recapitodescProvincia"+i+" input.recapito\\.idProvincia" ).val(idProvincia);
                        //                            var desStato = $(".destinazione .recapitodescStato"+i+" input.recapito\\.descStato" ).val();
                        //                            $(".sorgente .recapitodescStato"+i+" input.recapito\\.descStato" ).val(desStato);
                        //                            var idStato = $(".destinazione .recapitodescStato"+i+" input.recapito\\.idStato" ).val();
                        //                            $(".sorgente .recapitodescStato"+i+" input.recapito\\.idStato" ).val(idStato);
                        //                        } else {
                        //                            $(".destinazione .recapitodescComune"+i+" input.recapito\\.descComune" ).val($(".sorgente .recapitodescComune input.recapito\\.descComune" ).val());
                        //                            $(".destinazione .recapitodescComune"+i+" input.recapito\\.idComune" ).val($(".sorgente .recapitodescComune input.recapito\\.idComune" ).val());
                        //                            $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.descProvincia" ).val($(".sorgente .recapitodescProvincia input.recapito\\.descProvincia" ).val());
                        //                            $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.idProvincia" ).val($(".sorgente .recapitodescProvincia input.recapito\\.idProvincia" ).val());
                        //                            $(".destinazione .recapitodescStato"+i+" input.recapito\\.descStato" ).val($(".sorgente .recapitodescStato input.recapito\\.descStato" ).val());
                        //                            $(".destinazione .recapitodescStato"+i+" input.recapito\\.idStato" ).val($(".sorgente .recapitodescStato input.recapito\\.idStato" ).val());    
                        $(".destinazione .recapito" + i + " input.recapito-descComune").val($(".sorgente .recapito" + i + " input.recapito-descComune").val());
                        $(".destinazione .recapito" + i + " input.recapito-idComune").val($(".sorgente .recapito" + i + " input.recapito-idComune").val());
                        $(".destinazione .recapito" + i + " input.recapito-descProvincia").val($(".sorgente .recapito" + i + " input.recapito-descProvincia").val());
                        $(".destinazione .recapito" + i + " input.recapito-idProvincia").val($(".sorgente .recapito" + i + " input.recapito-idProvincia").val());
                        $(".destinazione .recapito" + i + " input.recapito-descStato").val($(".sorgente .recapito" + i + " input.recapito-descStato").val());
                        $(".destinazione .recapito" + i + " input.recapito-idStato").val($(".sorgente .recapito" + i + " input.recapito-idStato").val());
                        //                        }
                    } else if ($(this).hasClass("descrizioneTipoAnagrafica")) {
                        if ($(target).val() != $(this).val()) {
                            //elimino la possibilità di cambiare il tipo di anagrafica in questo caso
                            //                            var div = $("<div>");
                            //                            div.html("ATTENZIONE: stai per cambiare il tipo di anagrafica da " + $(target).val() + " a " + $(this).val() + ". <br/><br/><b>Processo irreversibile</b>, confermi l'operazione?<br/>")
                            //                            $(div).dialog({
                            //                                modal: true,
                            //                                title: "Conferma cambio tipo anagrafica",
                            //                                buttons: {
                            //                                    Ok: function() {
                            //                                        $(target).val($(this).val());
                            //                                        $(".sorgente .tipoAnagrafica > input").val($(".destinazione .tipoAnagrafica > input").val());
                            //                                        $(".sorgente .varianteAnagrafica > input").val($(".destinazione .varianteAnagrafica > input").val());
                            //                                        aggiornaAnagraficaXML();
                            //                                    },
                            //                                    Annulla: function() {
                            //                                        $(div).dialog('close');
                            //                                    }
                            //                                }
                            //                            });
                            //                        }
                        }
                    } else if ($(this).attr('name') == 'provinciaIscrizione.descrizione' || $(this).attr('name') == 'provinciaIscrizione.idProvincia') {
                        $(".destinazione #provinciaIscrizione1\\.descrizione").val($(".sorgente #provinciaIscrizione\\.descrizione").val());
                        $(".destinazione #provinciaIscrizione1\\.idProvincia").val($(".sorgente #provinciaIscrizione\\.idProvincia").val());
                    } else if ($(this).attr('name') == 'desFormaGiuridica' || $(this).attr('name') == 'idFormaGiuridica') {
                        $(".destinazione #desFormaGiuridica1").val($(".sorgente #desFormaGiuridica").val());
                        $(".destinazione #idFormaGiuridica1").val($(".sorgente #idFormaGiuridica").val());
                    } else if ($(this).attr('name') == 'ProvinciaCciaa.descrizione' || $(this).attr('name') == 'ProvinciaCciaa.idProvincie') {
                        $(".destinazione #ProvinciaCciaa1\\.descrizione").val($(".sorgente #ProvinciaCciaa\\.descrizione").val());
                        $(".destinazione #ProvinciaCciaa1\\.idProvincie").val($(".sorgente #ProvinciaCciaa\\.idProvincie").val());
                    } else if ($(this).attr('name') == 'desCittadinanza' || $(this).attr('name') == 'idCittadinanza') {
                        $(".destinazione #desCittadinanza1").val($(".sorgente #desCittadinanza").val());
                        $(".destinazione #idCittadinanza1").val($(".sorgente #idCittadinanza").val());
                    } else if ($(this).attr('name') == 'desNazionalita' || $(this).attr('name') == 'idNazionalita') {
                        $(".destinazione #desNazionalita1").val($(".sorgente #desNazionalita").val());
                        $(".destinazione #idNazionalita1").val($(".sorgente #idNazionalita").val());
                    } else {
                        $(target).val($(this).val());
                    }
                }

            }
        });
        return false;
    }

    $("#spostaSingola").unbind();
    $("#spostaSingola").click(function() {
        if (!elementClickked)
        {
            $("<div>").html(messaggioClick).dialog();
            return;
        }
        $(".sorgente .ui-state-highlight select").each(function(index) {
            if ($(this).val() != null && $(this).val() != "" && $(this).attr("nonsicopia") == null) {
                var valore = $(this).val();
                var targetClass = $(this).attr("class");
                var target;
                if (targetClass != null && targetClass != "") {
                    target = ".destinazione ." + elementClickked + " select " + $(this).attr("class").replace(".", "\\.");
                } else {
                    target = ".destinazione ." + elementClickked + " select#" + $(this).attr("id").replace(/\./g, "\\.");
                }
                target = target.replace("ui-widget", "");
                target = target.replace("ui-widget-content", "");
                target = target.replace("ui-corner-all", "");
                target = target.replace("ui-state-highlight", "");
                target = target.replace("ui-autocomplete-loading", "");
                $(target).val(valore).change();
                if (target.indexOf('flgAttesaIscrizioneRi') != -1) {
                    $(".destinazione .dettaglio  .flgAttesaIscrizioneRi select").val(valore);
                    $(".destinazione .dettaglio  #flgAttesaIscrizioneRiHidden1").val(valore);
                } else if (target.indexOf('flgAttesaIscrizioneRea') != -1) {
                    $(".destinazione .dettaglio  .flgAttesaIscrizioneRea select").val(valore);
                    $(".destinazione .dettaglio  #flgAttesaIscrizioneReaHidden1").val(valore);
                } else if (target.indexOf('flgIndividuale') != -1) {
                    $('.destinazione .dettaglio #flgIndividuale1 select').val(valore);
                    $('.destinazione .dettaglio #flgAttesaIscrizioneReaHidden1').val(valore);
                }
            }
        });

        $(".sorgente .ui-state-highlight input").each(function(index) {
            if ($(this).val() != null && $(this).val() != "" && $(this).attr("nonsicopia") == null)
            {
                var targetClass = $(this).attr("class");
                var target;
                if (targetClass != null && targetClass != "") {
                    if (isRecapito(elementClickked)) {
                        target = ".destinazione ." + elementClickked + " input." + $(this).attr("class").replace(".", "\\.");
                    } else {
                        target = ".destinazione ." + elementClickked + " input " + $(this).attr("class").replace(".", "\\.");
                    }
                }
                else
                {

                    target = ".destinazione ." + elementClickked + " input#" + $(this).attr("id").replace(/\./g, "\\.");
                }
                target = target.replace("ui-widget", "");
                target = target.replace("ui-widget-content", "");
                target = target.replace("ui-corner-all", "");
                target = target.replace("hasDatepicker", "");
                target = target.replace("ui-state-highlight", "");
                target = target.replace("ui-autocomplete-input", "");
                target = target.replace("ui-autocomplete-loading", "");
                target = target.replace("dataPicker", "");
                if ($(this).attr("id") == "comuneNascita.descrizione")
                {
                    $(".destinazione .dettaglio #comuneNascita1\\.descrizione").val($(".sorgente .dettaglio #comuneNascita\\.descrizione").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.idComune").val($(".sorgente .dettaglio #comuneNascita\\.idComune").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.stato\\.descrizione").val($(".sorgente .dettaglio #comuneNascita\\.stato\\.descrizione").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.stato\\.idStato").val($(".sorgente .dettaglio #comuneNascita\\.stato\\.idStato").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.provincia\\.descrizione").val($(".sorgente .dettaglio #comuneNascita\\.provincia\\.descrizione").val());
                    $(".destinazione .dettaglio #comuneNascita1\\.provincia\\.idProvincie").val($(".sorgente .dettaglio #comuneNascita\\.provincia\\.idProvincie").val());
                }
                else
                {
                    var elementClass = $(this).attr("class");
                    //Verifico se mi trovo nel caso di recapito del comune
                    if (elementClass != undefined && elementClass.indexOf("recapito-descComune") !== -1) {
                        var i = elementClickked[elementClickked.length - 1];
                        if (!$.isNumeric(i)) {
                            i = 0;
                        }
                        //                        if (i > 0){
                        //                            var desComune = $(".destinazione .recapitodescComune"+i+" input.recapito\\.descComune" ).val();
                        //                            $(".sorgente .recapitodescComune"+i+" input.recapito\\.descComune" ).val(desComune);
                        //                            var idComune = $(".destinazione .recapitodescComune"+i+" input.recapito\\.idComune" ).val();
                        //                            $(".sorgente .recapitodescComune"+i+" input.recapito\\.idComune" ).val(idComune);
                        //                            var desProvincia = $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.descProvincia" ).val();
                        //                            $(".sorgente .recapitodescProvincia"+i+" input.recapito\\.descProvincia" ).val(desProvincia);
                        //                            var idProvincia = $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.idProvincia" ).val();
                        //                            $(".sorgente .recapitodescProvincia"+i+" input.recapito\\.idProvincia" ).val(idProvincia);
                        //                            var desStato = $(".destinazione .recapitodescStato"+i+" input.recapito\\.descStato" ).val();
                        //                            $(".sorgente .recapitodescStato"+i+" input.recapito\\.descStato" ).val(desStato);
                        //                            var idStato = $(".destinazione .recapitodescStato"+i+" input.recapito\\.idStato" ).val();
                        //                            $(".sorgente .recapitodescStato"+i+" input.recapito\\.idStato" ).val(idStato);
                        //                        } else {
                        //                            $(".destinazione .recapitodescComune"+i+" input.recapito\\.descComune" ).val($(".sorgente .recapitodescComune input.recapito\\.descComune" ).val());
                        //                            $(".destinazione .recapitodescComune"+i+" input.recapito\\.idComune" ).val($(".sorgente .recapitodescComune input.recapito\\.idComune" ).val());
                        //                            $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.descProvincia" ).val($(".sorgente .recapitodescProvincia input.recapito\\.descProvincia" ).val());
                        //                            $(".destinazione .recapitodescProvincia"+i+" input.recapito\\.idProvincia" ).val($(".sorgente .recapitodescProvincia input.recapito\\.idProvincia" ).val());
                        //                            $(".destinazione .recapitodescStato"+i+" input.recapito\\.descStato" ).val($(".sorgente .recapitodescStato input.recapito\\.descStato" ).val());
                        //                            $(".destinazione .recapitodescStato"+i+" input.recapito\\.idStato" ).val($(".sorgente .recapitodescStato input.recapito\\.idStato" ).val());    
                        $(".destinazione .recapito" + i + " input.recapito-descComune").val($(".sorgente .recapito" + i + " input.recapito-descComune").val());
                        $(".destinazione .recapito" + i + " input.recapito-idComune").val($(".sorgente .recapito" + i + " input.recapito-idComune").val());
                        $(".destinazione .recapito" + i + " input.recapito-descProvincia").val($(".sorgente .recapito" + i + " input.recapito-descProvincia").val());
                        $(".destinazione .recapito" + i + " input.recapito-idProvincia").val($(".sorgente .recapito" + i + " input.recapito-idProvincia").val());
                        $(".destinazione .recapito" + i + " input.recapito-descStato").val($(".sorgente .recapito" + i + " input.recapito-descStato").val());
                        $(".destinazione .recapito" + i + " input.recapito-idStato").val($(".sorgente .recapito" + i + " input.recapito-idStato").val());
                        //                        }
                    } else if ($(this).hasClass("descrizioneTipoAnagrafica")) {
                        if ($(target).val() != $(this).val()) {
                            var div = $("<div>");
                            div.html("ATTENZIONE: stai per cambiare il tipo di anagrafica da " + $(target).val() + " a " + $(this).val() + ". <br/><br/><b>Processo irreversibile</b>, confermi l'operazione?<br/>")
                            $(div).dialog({
                                modal: true,
                                title: "Conferma cambio tipo anagrafica",
                                buttons: {
                                    Ok: function() {
                                        $(target).val($(this).val());
                                        $(".sorgente .tipoAnagrafica > input").val($(".destinazione .tipoAnagrafica > input").val());
                                        $(".sorgente .varianteAnagrafica > input").val($(".destinazione .varianteAnagrafica > input").val());
                                        aggiornaAnagraficaXML();
                                    },
                                    Annulla: function() {
                                        $(div).dialog('close');
                                    }
                                }
                            });
                        }
                    } else if ($(this).attr('name') == 'provinciaIscrizione.descrizione' || $(this).attr('name') == 'provinciaIscrizione.idProvincia') {
                        $(".destinazione #provinciaIscrizione1\\.descrizione").val($(".sorgente #provinciaIscrizione\\.descrizione").val());
                        $(".destinazione #provinciaIscrizione1\\.idProvincia").val($(".sorgente #provinciaIscrizione\\.idProvincia").val());
                    } else if ($(this).attr('name') == 'desFormaGiuridica' || $(this).attr('name') == 'idFormaGiuridica') {
                        $(".destinazione #desFormaGiuridica1").val($(".sorgente #desFormaGiuridica").val());
                        $(".destinazione #idFormaGiuridica1").val($(".sorgente #idFormaGiuridica").val());
                    } else if ($(this).attr('name') == 'ProvinciaCciaa.descrizione' || $(this).attr('name') == 'ProvinciaCciaa.idProvincie') {
                        $(".destinazione #ProvinciaCciaa1\\.descrizione").val($(".sorgente #ProvinciaCciaa\\.descrizione").val());
                        $(".destinazione #ProvinciaCciaa1\\.idProvincie").val($(".sorgente #ProvinciaCciaa\\.idProvincie").val());
                    } else if ($(this).attr('name') == 'desCittadinanza' || $(this).attr('name') == 'idCittadinanza') {
                        $(".destinazione #desCittadinanza1").val($(".sorgente #desCittadinanza").val());
                        $(".destinazione #idCittadinanza1").val($(".sorgente #idCittadinanza").val());
                    } else if ($(this).attr('name') == 'desNazionalita' || $(this).attr('name') == 'idNazionalita') {
                        $(".destinazione #desNazionalita1").val($(".sorgente #desNazionalita").val());
                        $(".destinazione #idNazionalita1").val($(".sorgente #idNazionalita").val());
                    } else {
                        $(target).val($(this).val());
                    }
                }

            }
        });

    });

    $(".sorgente .registroImprese ").unbind();
    $(".sorgente .registroImprese ").click(function(e) {
        var codiceFiscale = $(".sorgente input#codiceFiscale").val();
        //trim della stringa
        codiceFiscale = codiceFiscale.replace(/^\s + |\s + $/g, '')

        $.ajax({
            type: 'POST',
            url: urlRegistroImprese,
            data: {
                codiceFiscale: codiceFiscale
            }
        }).done(function(data) {
            var wHeight = $(window).height() * 0.8;
            $('.dettaglioAziendaContainer').empty();
            var div = $('<div>');
            div.html(data);
            div.dialog({
                title: 'Dettaglio anagrafica azienda',
                modal: true,
                height: wHeight,
                width: '50%',
                //dialogClass:'cross_modal', //^^CS ELIMINA sposta la dialog
                buttons: {
                    'Accetta valori': function() {
                        if ($('.destinazione #denominazione1').val() != null && $.trim($('.dettaglioAziendaContainer #denominazioneRI').text()) != "")
                        {
                            $('.destinazione .denominazione span').text($('.destinazione #denominazione1').val());
                            $('.destinazione #denominazione1').val($('.dettaglioAziendaContainer #denominazioneRI').text());

                        }
                        //        if ($('.dettaglioAziendaContainer #codiceFiscaleRI').text()!= null&&$.trim($('.dettaglioAziendaContainer #codiceFiscaleRI').text())!= "")
                        //        {
                        //         $('.sorgente .codiceFiscale span').text($('.sorgente #codiceFiscale').val());
                        //         $('.sorgente #codiceFiscale').val($('.dettaglioAziendaContainer #codiceFiscaleRI').text());
                        //        }
                        //        if ($('.dettaglioAziendaContainer #partitaIvaRI').text()!= null&&$.trim($('.dettaglioAziendaContainer #partitaIvaRI').text())!= "")
                        //        {
                        //         $('.sorgente .partitaIva span').text($('.sorgente #partitaIva').val()); 
                        //         $('.sorgente #partitaIva').val($('.dettaglioAziendaContainer #partitaIvaRI').text());
                        //        }
                        if ($('.dettaglioAziendaContainer #desCciaaRI').text() != null && $.trim($('.dettaglioAziendaContainer #desCciaaRI').text()) != "")
                        {
                            $('.destinazione .ProvinciaCciaa .descrizione').val($('.destinazione #ProvinciaCciaa1\\.descrizione').val());
                            $('.destinazione #ProvinciaCciaa1\\.descrizione').val($('.dettaglioAziendaContainer #desCciaaRI').text());
                            $('.destinazione #ProvinciaCciaa1\\.idProvincie').val($('.dettaglioAziendaContainer #idCciaaRI').text());
                        }
                        $('.destinazione #nIscrizioneRea1').val($('.dettaglioAziendaContainer #numeroReaRI').text());
                        $(this).dialog("close");
                    },
                    'Annulla': function() {
                        $(this).dialog("close");
                    }
                }
            });
            return false;
        });
    });
    $(".sorgente .ricercaAnagrafe ").unbind();
    $(".sorgente .ricercaAnagrafe ").click(function(e) {
        var codiceFiscale = $(".sorgente input#codiceFiscale").val();
        codiceFiscale = codiceFiscale.replace(/^\s + |\s + $/g, '')

        $.ajax({
            type: 'POST',
            url: urlRicercaAnagrafe,
            data: {
                codiceFiscale: codiceFiscale,
                idPratica: idPratica
            }
        }).done(function(data) {
            var wHeight = $(window).height() * 0.8;
            $('.dettaglioAnagraficaContainer').empty();
            var div = $('<div>');
            div.html(data);
            div.dialog({
                title: 'Dettaglio anagrafica persona fisica',
                modal: true,
                height: wHeight,
                width: '50%',
                buttons: {
                    'Chiudi': function() {
                        $(this).dialog("close");
                    }
                }
            });
            return false;
        });
    });
}

function isValidCF()
{
    var tipoAnag = $(".destinazione .tipoAnagrafica > input").val();
    if (tipoAnag != '') {
        $(".sorgente .tipoAnagrafica > input").val($(".destinazione .tipoAnagrafica > input").val());
    }
    var varianteAnag = $(".destinazione .varianteAnagrafica > input").val();
    if (varianteAnag != '') {
        $(".sorgente .varianteAnagrafica > input").val($(".destinazione .varianteAnagrafica > input").val());
    }
    var check = false;
    $.ajax({
        url: url,
        dataType: "json",
        async: false,
        data: {
            codiceFiscale: $(".sorgente li.codiceFiscale input").val(),
            partitaIva: $(".sorgente .partitaIva > input").val(),
            action: "checkCF"
        },
        success: function(data) {
            if (data.errors != null) {
                dialogError(data.errors);
                check = false;
            } else {
                check = true;
            }
        }
    });
    return check;
}


function isValidRecapiti() {

    var valid = new Array();
    $(".sorgente .recapito\\.idTipoIndirizzo").each(function(index)
    {
        if (validator.letters.test($(this).val()))
        {
            if (!validator.numeric.test($(this).val()))
            {
                var target = "." + $(this).parent().attr("class").split(" ")[0];
                target = ".destinazione " + target + " .recapito\\.descTipoIndirizzo";
                $(target).addClass("ui-state-error");
                valid.push(""/**/);
            }
        }
    });
    $(".destinazione .recapito\\.indirizzo").each(function(index)
    {
        var tipoIndirizzo = $(".sorgente .recapito" + index + " .recapito\\.descTipoIndirizzo").val();
        if (!tipoIndirizzo != "NOTIFICA" && validator.letters.test(tipoIndirizzo))
        {
            if (!validator.alphanumeric.test($(this).val()))
            {
                var target = "." + $(this).parent().attr("class").split(" ")[0];
                target = ".sorgente " + target + " .recapito\\.indirizzo";
                $(target).addClass("ui-state-error");
                valid.push(""/**/);
            }
        }

    });
    $(".destinazione .recapito\\.idComune").each(function(index)
    {
        var tipoIndirizzo = $(".destinazione .recapito" + index + " .recapito\\.descTipoIndirizzo").val();
        if (!tipoIndirizzo != "NOTIFICA" && validator.letters.test(tipoIndirizzo))
        {
            if (!validator.numeric.test($(this).val()))
            {
                var target = "." + $(this).parent().attr("class").split(" ")[0];
                target = ".destinazione " + target + " .recapito\\.descComune";
                $(target).addClass("ui-state-error");
                valid.push(""/**/);
            }
        }
    });
    $(".destinazione .recapito\\.idStato").each(function(index)
    {
        var tipoIndirizzo = $(".destinazione .recapito" + index + " .recapito\\.descTipoIndirizzo").val();
        if (!tipoIndirizzo != "NOTIFICA" && validator.letters.test(tipoIndirizzo))
        {
            if (!validator.numeric.test($(this).val()))
            {
                var target = "." + $(this).parent().attr("class").split(" ")[0];
                target = ".destinazione " + target + " .recapito\\.descStato";
                $(target).addClass("ui-state-error");
                valid.push(""/**/);
            }
        }
    });
    $(".destinazione .recapito\\.nCivico").each(function(index)
    {
        var tipoIndirizzo = $(".destinazione .recapito" + index + " .recapito\\.descTipoIndirizzo").val();
        if (!tipoIndirizzo != "NOTIFICA" && validator.letters.test(tipoIndirizzo))
        {
            if (!validator.alphanumeric.test($(this).val()))
            {
                var target = "." + $(this).parent().attr("class").split(" ")[0];
                target = ".destinazione " + target + " .recapito\\.nCivico";
                $(target).addClass("ui-state-error");
                valid.push(""/**/);
            }
        }
    });
    if (valid.length > 0)
    {
        var valid = new Array();
        valid.push("Errore nei recapiti. Aggiornare i campi o correggerli e riprovare");
        dialogError(valid);
        return false;
    }
    return true;

}
function isValidAnagrafica()
{
    var valid = new Array();
    //    var selTipoAnagrafica = getTipoAnagrafica($(".sorgente .tipoAnagrafica > input").val(),$(".sorgente .varianteAnagrafica > input").val());
    var selTipoAnagrafica = $(".destinazione .tipoAnagrafica > input").val();

    if (selTipoAnagrafica == "F")
    {
        if ($(".destinazione .dettaglio input#nome1").val() == null || $(".destinazione .dettaglio input#nome").val() == "") {
            valid.push(errNome);
        }
        if ($(".destinazione .dettaglio input#cognome1").val() == null || $(".destinazione .dettaglio input#cognome").val() == "") {
            valid.push(errCognome);
        }
        if (!validator.alphanumeric.test($(".destinazione .dettaglio input#codiceFiscale1").val())) {
            valid.push(errCodiceFiscale);
        }
        //        Non obbligo la presenza della cittadinanza
        //        if (!validator.letters.test($(".sorgente .dettaglio input#desCittadinanza").val())){
        //            valid.push(errDesCittadinanza);
        //        }
        //        if (!validator.numeric.test($(".sorgente .dettaglio input#idCittadinanza").val())){
        //            valid.push(errIdCittadinanza);
        //        }

        //Verifico la presenza della denominazione
        if ($('select[name=flgDittaIndividuale]').val() === 'S') {
            if ($(".destinazione .dettaglio input#denominazione1").val() == null || $(".destinazione .dettaglio input#denominazione1").val() == "") {
                valid.push(errDenominazionea);
            }
        }

        if (!validator.letters.test($(".destinazione .dettaglio input#comuneNascita1\\.descrizione").val())) {
            valid.push(errDesComune);
        }
        if (!validator.numeric.test($(".destinazione .dettaglio input#comuneNascita1\\.idComune").val())) {
            valid.push(errIdComune);
        }
        if (!validator.numeric.test($(".destinazione .dettaglio input#comuneNascita1\\.stato\\.idStato").val())) {
            valid.push(errIdStato);
        }
        if (!validator.numeric.test($(".destinazione .dettaglio input#comuneNascita1\\.provincia\\.idProvincie").val())) {
            valid.push(errIdProvincia);
        }
    }

    if (selTipoAnagrafica == "G")
    {
        if ($(".destinazione .dettaglio input#denominazione1").val() == null || $(".destinazione .dettaglio input#denominazione1").val() == "") {
            valid.push(errDenominazionea);
        }
        if (!validator.numeric.test($(".destinazione .dettaglio input#partitaIva1").val())) {
            valid.push(errPartitaIva);
        }
        if (!validator.numeric.test($(".destinazione .dettaglio input#idFormaGiuridica1").val())) {
            valid.push(errFormaGiuridica);
        }

    }
    if (valid.length > 0)
    {
        dialogError(valid);
        return false;
    }
    return true;
}
function isValidPratica()
{
    var ruolianagrafiche = new Object;
    var errori = new Array();
    var elementi = null;
    if (graficaNuova())
    {
        elementi = $("#anagrafica_box > table  td > div");
    }
    else
    {
        elementi = $("#anagraficaContainer > div");
    }
    elementi.each(function(index) {
        var id = $(this).attr("id");
        var idruolo = $("#" + id + " .confronta #idTipoRuolo").val();
        var codruolo = $("#" + id + " .confronta #desTipoRuolo").val();
        var cf = $.trim($("#" + id + " .confronta #codiceFiscale").val());
        if (ruolianagrafiche[cf + idruolo] == null)
        {
            var anagrafica = new Object();
            anagrafica.cf = cf;
            anagrafica.codruolo = codruolo;
            anagrafica.idruolo = idruolo;
            anagrafica.counter = 0;
            ruolianagrafiche[cf + idruolo] = anagrafica;
        }
        else
        {
            ruolianagrafiche[cf + idruolo].counter = ruolianagrafiche[cf + idruolo].counter + 1;
        }
    });
    var trovato = false;
    for (ra in ruolianagrafiche)
    {
        if (ruolianagrafiche[ra].counter > 0)
        {
            errori.push("L'anagrafica " + ruolianagrafiche[ra].cf + " e' registrata piu di una volta con lo stesso ruolo " + ruolianagrafiche[ra].codruolo);
        }

        if (ruolianagrafiche[ra].codruolo.toUpperCase() == "RICHIEDENTE")
        {
            trovato = true;
        }
    }
    if (!trovato)
    {
        errori.push("La pratica deve contenere almeno una anagrafca di tipo Richiedente");
    }
    var protocollo = $("#protocollo").length;
    if (protocollo == 1) {
        //Ho il protocollo inserito manualmente
        var numeroProtocolloSplit = $("#protocollo").val().split("/");
        if (numeroProtocolloSplit != undefined && numeroProtocolloSplit.length != 3) {
            errori.push("Il numero di protocollo deve essere espresso nel formato Registro/Anno di riferimento/Numero");
        } else {
            var anno = numeroProtocolloSplit[1];
            if (!validator.year.test(anno)) {
                errori.push("L'anno inserito per il protocollo non è valido");
            }
        }
    }
    var datiCatastali = $('.DatiCatastali .confermato');
    var errCatastali = false;
    for (var i = 0; i < datiCatastali.length; i++) {
        if ($(datiCatastali[i]).val() == 'false') {
            errCatastali = true;
            break;
        }
    }
    if (errCatastali) {
        errori.push("Confermare tutti i dati catastali");
    }
    datiCatastali = $('.DatiTavolari .confermato');
    var errCatastali = false;
    for (var i = 0; i < datiCatastali.length; i++) {
        if ($(datiCatastali[i]).val() == 'false') {
            errCatastali = true;
            break;
        }
    }
    if (errCatastali) {
        errori.push("Confermare tutti i dati tavolari");
    }
    datiCatastali = $('.IndirizziIntervento .confermato');
    var errCatastali = false;
    for (var i = 0; i < datiCatastali.length; i++) {
        if ($(datiCatastali[i]).val() == 'false') {
            errCatastali = true;
            break;
        }
    }
    if (errCatastali) {
        errori.push("Confermare tutti gli indirizzi dell'intervento");
    }
    if (errori.length > 0)
    {
        return errori;
    }
    return null;
}

function isRecapito(elementClass) {
    return elementClass.indexOf("recapito") !== -1;
}

function popolaDatiPersona(sezione, data) {
    var destId = "";
    if (sezione == '.destinazione') {
        destId = "1";
    }
    $(sezione + " .dettaglio input#nome" + destId).val($.trim(data['nome']));
    $(sezione + " .dettaglio input#codTipoRuolo" + destId).val($.trim(data['codTipoRuolo']));
    //    var stri = stringaAnagrafica($.trim(data['tipoAnagrafica']),$.trim(data['varianteAnagrafica']));
    var tipologiaAnagrafica = $.trim(data['tipoAnagrafica']);
    $(sezione + " .dettaglio .tipoAnagrafica input").val(tipologiaAnagrafica);
    if (tipologiaAnagrafica === 'F') {
        $(sezione + " .dettaglio .descrizioneTipoAnagrafica ").val('Persona fisica');
        var tipoRuolo = $.trim(data['idTipoRuolo']);
        if (tipoRuolo === '1') {
            //E' richiedente, non può essere ditta individuale
            $('.dittaIndividuale').addClass('hidden');
            $('.dittaIndividuale').hide();
            $(sezione + ' .varianteAnagrafica').val('F');
        } else {
            var varianteAnagrafica = $.trim(data['varianteAnagrafica']);
            $('.dittaIndividuale').removeClass('hidden');
            $('.dittaIndividuale').show();
            $(sezione + ' .varianteAnagrafica').val(varianteAnagrafica);
            if (varianteAnagrafica === 'I') {
                //$(sezione + ' .dittaIndividuale select').val('S');
                $(".dittaIndividuale").find("select").find("option[value='S']").attr("selected", "selected");

            } else {
                //$(sezione + ' .dittaIndividuale select').val('N');
                $(".dittaIndividuale").find("select").find("option[value='N']").attr("selected", "selected");
            }
        }
    } else {
        $(sezione + " .dettaglio .descrizioneTipoAnagrafica ").val('Persona giuridica');
    }

    if (praticaManuale && sezione == ".sorgente")
    {
        $(".containerAnagrafiche .sorgente .tipoRuolo select").val($.trim(data['idTipoRuolo']));
        $(".containerAnagrafiche .sorgente .tipoRuolo #desTipoRuolo").val($.trim(data['desTipoRuolo']));
        $(sezione + " .dettaglio input#desTipoRuolo" + destId).val($.trim(data['desTipoRuolo']));
        $(".containerAnagrafiche .destinazione .tipoRuolo select").hide();
    }
    else
    {
        $(sezione + " .dettaglio input#desTipoRuolo" + destId).val($.trim(data['desTipoRuolo']));
        $(sezione + " .dettaglio input#idTipoRuolo" + destId).val($.trim(data['idTipoRuolo']));
    }
    $(sezione + " .dettaglio input#idTipoRuolo" + destId).val($.trim(data['idTipoRuolo']));
    $(sezione + " .dettaglio input#counter" + destId).val($.trim(data['counter']));
//
    $(sezione + " .dettaglio input#idAnagrafica" + destId).val($.trim(data['idAnagrafica']));
//
    $(sezione + " .dettaglio input#cognome" + destId).val($.trim(data['cognome']));
    $(sezione + " .dettaglio input#tipoAnagrafica" + destId).val($.trim(data['tipoAnagrafica']));
    $(sezione + " .dettaglio input#varianteAnagrafica" + destId).val($.trim(data['varianteAnagrafica']));
    $(sezione + " .dettaglio input#partitaIva" + destId).val($.trim(data['partitaIva']));
    $(sezione + " .dettaglio input#denominazione" + destId).val($.trim(data['denominazione']));
    $(sezione + " .dettaglio input#desCittadinanza" + destId).val($.trim(data['desCittadinanza']));
    $(sezione + " .dettaglio input#idCittadinanza" + destId).val($.trim(data['idCittadinanza']));
    $(sezione + " .dettaglio input#desNazionalita" + destId).val($.trim(data['desNazionalita']));
    $(sezione + " .dettaglio input#idNazionalita" + destId).val($.trim(data['idNazionalita']));
    $(sezione + " .dettaglio input#codiceFiscale" + destId).val($.trim(data['codiceFiscale']));
    $(sezione + " .dettaglio #sesso" + destId).val($.trim(data['sesso']));
    $(sezione + " .dettaglio input#cittadinanza" + destId).val($.trim(data['cittadinanza']));
    $(sezione + " .dettaglio input#nazionalita" + destId).val($.trim(data['nazionalita']));
    $(sezione + " .dettaglio input#dataNascita" + destId).val($.trim(data['dataNascita']));
    $(sezione + " .dettaglio input#localitaNascita" + destId).val($.trim(data['localitaNascita']));
    $(sezione + " .dettaglio input#desProvinciaIscrizione" + destId).val($.trim(data['desProvinciaIscrizione']));
    $(sezione + " .dettaglio input#desTipoCollegio" + destId).val($.trim(data['desTipoCollegio']));
    $(sezione + " .dettaglio input#idTipoCollegio" + destId).val($.trim(data['idTipoCollegio']));
    $(sezione + " .dettaglio input#idFormaGiuridica" + destId).val($.trim(data['idFormaGiuridica']));
    $(sezione + " .dettaglio input#desFormaGiuridica" + destId).val($.trim(data['desFormaGiuridica']));
    if (data['ProvinciaCciaa'] != null) {
        $(sezione + " .dettaglio input#ProvinciaCciaa" + destId + "\\.idProvincie").val($.trim(data['ProvinciaCciaa']['idProvincie']));
        $(sezione + " .dettaglio input#ProvinciaCciaa" + destId + "\\.descrizione").val($.trim(data['ProvinciaCciaa']['descrizione']));
    }
    if ($.trim(data['flgAttesaIscrizioneRea']) != '') {
        $(sezione + " .dettaglio  .flgAttesaIscrizioneRea select").val($.trim(data['flgAttesaIscrizioneRea']));
        $(sezione + " .dettaglio  #flgAttesaIscrizioneReaHidden" + destId).val($.trim(data['flgAttesaIscrizioneRea']));
    } else {
        $(sezione + " .dettaglio  .flgAttesaIscrizioneRea select").val('S');
        $(sezione + " .dettaglio  #flgAttesaIscrizioneReaHidden" + destId).val('S');
    }
    if ($.trim(data['flgAttesaIscrizioneRi']) != '') {
        $(sezione + " .dettaglio  .flgAttesaIscrizioneRi select").val($.trim(data['flgAttesaIscrizioneRi']));
        $(sezione + " .dettaglio  #flgAttesaIscrizioneRiHidden" + destId).val($.trim(data['flgAttesaIscrizioneRi']));
    } else {
        $(sezione + " .dettaglio  .flgAttesaIscrizioneRi select").val('S');
        $(sezione + " .dettaglio  #flgAttesaIscrizioneRiHidden" + destId).val('S');
    }

    //    $(sezione + " .flgAttesaIscrizioneRea .input").val($.trim(data['flgAttesaIscrizioneRea']));
    $(sezione + " .dettaglio  input#nIscrizioneRea" + destId).val($.trim(data['nIscrizioneRea']));
    $(sezione + " .dettaglio  input#dataIscrizioneRea" + destId).val($.trim(data['dataIscrizioneRea']));
    $(sezione + " .dettaglio  input#nIscrizioneRi" + destId).val($.trim(data['nIscrizioneRi']));
    $(sezione + " .dettaglio  input#dataIscrizioneRi" + destId).val($.trim(data['dataIscrizioneRi']));


    if (data['flgObbligoIscrizioneRea'] != null)
    {
        $(sezione + " .dettaglio input#ProvinciaCciaa" + destId + "\\.idProvincie").val($.trim(data['ProvinciaCciaa']['idProvincie']));
        $(sezione + " .dettaglio input#ProvinciaCciaa" + destId + "\\.descrizione").val($.trim(data['ProvinciaCciaa']['descrizione']));
    }

    $(sezione + " .dettaglio input#idProvinciaIscrizione" + destId).val($.trim(data['idProvinciaIscrizione']));
    $(sezione + " .dettaglio input#dataIscrizione" + destId).val($.trim(data['dataIscrizione']));
    $(sezione + " .dettaglio input#idFormaGiuridica" + destId).val($.trim(data['idFormaGiuridica']));
    $(sezione + " .dettaglio input#desFormaGiuridica" + destId).val($.trim(data['desFormaGiuridica']));
    $(sezione + " .dettaglio input#assensoUsoPec" + destId).val($.trim(data['assensoUsoPec']));

    $(sezione + " input.codiceFiscale").val($.trim(data['codiceFiscale']));
    if (data['provinciaIscrizione'] != null)
    {
        $(sezione + " .dettaglio input#provinciaIscrizione" + destId + "\\.descrizione").val($.trim(data['provinciaIscrizione']['descrizione']));
        $(sezione + " .dettaglio input#provinciaIscrizione" + destId + "\\.idProvincia").val($.trim(data['provinciaIscrizione']['idProvincie']));
    }
    $(sezione + " .dettaglio input#numeroIscrizione" + destId).val($.trim(data['numeroIscrizione']));
    if (data['comuneNascita'] != null)
    {
        $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.descrizione").val($.trim(data['comuneNascita']['descrizione']));
        $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.idComune").val($.trim(data['comuneNascita']['idComune']));
        $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.codCatastale").val($.trim(data['comuneNascita']['codCatastale']));
        if (data['comuneNascita']['stato'] != null)
        {

            $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.stato\\.descrizione").val($.trim(data['comuneNascita']['stato']['descrizione']));
            $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.stato\\.idStato").val($.trim(data['comuneNascita']['stato']['idStato']));
        }
        if (data['comuneNascita']['provincia'] != null)
        {

            $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.provincia\\.descrizione").val($.trim(data['comuneNascita']['provincia']['descrizione']));
            $(sezione + " .dettaglio input#comuneNascita" + destId + "\\.provincia\\.idProvincie").val($.trim(data['comuneNascita']['provincia']['idProvincie']));
        }
    }
    if (data['comuneNascita'] == null ||
            data['comuneNascita']['idComune'] == null ||
            data['comuneNascita']['stato'] == null ||
            data['comuneNascita']['stato']['idStato'] == null ||
            data['comuneNascita']['provincia'] == null ||
            data['comuneNascita']['provincia']['idProvincie'] == null) {
    }
//    if (data['ricercaAnagrafica'] && data['ricercaAnagrafica'] == "RICERCA_ANAGRAFICA_FISICA") {
//        $(sezione + " .dettaglio .ricercaAnagrafe.hidden").removeClass("hidden");
//    }
//    if (data['ricercaAnagrafica'] && data['ricercaAnagrafica'] == "RICERCA_ANAGRAFICA_GIURIDICA") {
//        
//    }
}
function popolaRecapiti(sezione, data, index)
{
    $(sezione + " input.recapito-descTipoIndirizzo").val($.trim(data['descTipoIndirizzo']));
    $(sezione + " input.recapito-counter").val($.trim(data['counter']));
    $(sezione + " input.recapito-idTipoIndirizzo").val($.trim(data['idTipoIndirizzo']));
    $(sezione + " input.recapito-indirizzo").val($.trim(data['indirizzo']));
    $(sezione + " input.recapito-idRecapito").val($.trim(data['idRecapito']));
    $(sezione + " input.recapito-nCivico").val($.trim(data['nCivico']));
    $(sezione + " input.recapito-cap").val($.trim(data['cap']));
    $(sezione + " input.recapito-idComune").val($.trim(data['idComune']));
    $(sezione + " input.recapito-descComune").val($.trim(data['descComune']));
    $(sezione + " input.recapito-descProvincia").val($.trim(data['descProvincia']));
    $(sezione + " input.recapito-idProvincia").val($.trim(data['idProvincia']));
    $(sezione + " input.recapito-descStato").val($.trim(data['descStato']));
    $(sezione + " input.recapito-idStato").val($.trim(data['idStato']));
    $(sezione + " input.recapito-telefono").val($.trim(data['telefono']));
    $(sezione + " input.recapito-cellulare").val($.trim(data['cellulare']));
    $(sezione + " input.recapito-pec").val($.trim(data['pec']));
    $(sezione + " input.recapito-email").val($.trim(data['email']));
    //    $(sezione + " input.recapito\\.descTipoIndirizzo").val($.trim(data['descTipoIndirizzo']));
    //    $(sezione + " input.recapito\\.counter").val($.trim(data['counter']));
    //    $(sezione + " input.recapito\\.idTipoIndirizzo").val($.trim(data['idTipoIndirizzo']));
    //    $(sezione + " input.recapito\\.indirizzo").val($.trim(data['indirizzo']));
    //    $(sezione + " input.recapito\\.idRecapito").val($.trim(data['idRecapito']));
    //    $(sezione + " input.recapito\\.counter").val($.trim(data['counter']));
    //    $(sezione + " input.recapito\\.nCivico").val($.trim(data['nCivico']));
    //    $(sezione + " input.recapito\\.cap").val($.trim(data['cap']));
    //    $(sezione + " input.recapito\\.idComune").val($.trim(data['idComune']));
    //    $(sezione + " input.recapito\\.descComune").val($.trim(data['descComune']));
    //    $(sezione + " input.recapito\\.descProvincia").val($.trim(data['descProvincia']));
    //    $(sezione + " input.recapito\\.idProvincia").val($.trim(data['idProvincia']));
    //    $(sezione + " input.recapito\\.descStato").val($.trim(data['descStato']));
    //    $(sezione + " input.recapito\\.idStato").val($.trim(data['idStato']));
    //    $(sezione + " input.recapito\\.telefono").val($.trim(data['telefono']));
    //    $(sezione + " input.recapito\\.cellulare").val($.trim(data['cellulare']));
    //    $(sezione + " input.recapito\\.pec").val($.trim(data['pec']));
    //    $(sezione + " input.recapito\\.email").val($.trim(data['email']));
    if (
            data['idStato'] == null ||
            data['idProvincia'] == null ||
            data['idComune'] == null
            )
    {
        //$(sezione + " input.recapito\\.descComune").addClass("ui-state-error");
    }
}
function salvaAnagrafica()
{
    var sesso = $("#sesso1").val();
    var form = $("<form>").append($(".destinazione").clone());
    $($(form).find(".sesso").children()[1]).val(sesso);

    form.attr({
        name: 'anagrafica',
        method: 'post',
        action: url
    });
    form.append($('<input>',
            {
                name: 'action',
                value: 'salvaAnagrafica',
                type: 'hidden'
            }));
    form.append($('<input>',
            {
                name: 'idPratica',
                value: idPratica,
                type: 'hidden'
            }));
    form.append($('<input>',
            {
                name: 'responsabileProcedimento',
                value: $('#responsabileProcedimento').val(),
                type: 'hidden'
            }));
    form.append($('<input>',
            {
                name: 'protocollo',
                value: $('#protocollo').val(),
                type: 'hidden'
            }));


    $.post(url, form.serialize(),
            function(data, textStatus, xhr) {
                if (data.errors != null) {
                    dialogError(data.errors);
                } else {
                    if (data.messages != null) {
                        $("#salvaAnagrafica").dialog("close");
                        $(".confrontaAnagrafiche").dialog("close");
                        dialogOK(data.messages);
                        var counter = $(".sorgente .counter input").val();
                        $("#anagrafica" + counter + " .confermata").val("1");
                        $("#anagrafica" + counter).addClass("confermataBG");
                        $("#anagrafica" + counter).removeClass("ui-state-error");
                    }
                }
            }, 'json');
}

function resetAnagrafica()
{
    $(".containerAnagrafiche input").val("");
    $(".containerAnagrafiche .ui-state-hover").removeClass("ui-state-hover");
    $(".containerAnagrafiche .ui-state-highlight").removeClass("ui-state-highlight");
    $(".containerAnagrafiche .ui-state-error").removeClass("ui-state-error");

    var count = $(".sorgente ul").length
    for (var i = 1; i < count; i++)
    {
        if ($(".recapito" + i).length > 0)
        {
            $(".recapito" + i).remove();
        }
    }
}
function dialogError(errors)
{
    var errore = "\n";
    for (var error in errors)
    {
        errore += "- " + errors[error] + "<br/>\n";
    }
    var $div = $("<div class = 'messaggioErrore'>");
    $div.html(errore);
    $div.dialog({
        modal: true,
        width: 400,
        buttons: {
            Ok: function() {
                $(this).dialog('close');
            }
        }
    }).parent().addClass("ui-state-error ui-corner-all");

}
function dialogOK(messages)
{
    var errore = "";
    for (var msg in messages)
    {
        errore += "[*] " + messages[msg] + "<br/>\n";
    }
    var $div = $("<div>");
    $div.html(errore);
    $div.dialog({
        modal: true,
        width: 400,
        buttons: {
            Ok: function() {
                $(this).dialog('close');
                //location.reload();
                updateParamAndReload("currentTab", "frame1");
            }
        }
    });
}

function highlight()
{
    $('.containerAnagrafiche li').unbind();
    $('.containerAnagrafiche li').mouseover(function()
    {
        if ($(this).attr("nonsicopia") == null) {
            var calss = ".containerAnagrafiche ." + $(this).attr("class").split(" ")[0];
            $(calss).addClass("ui-state-hover");
        }
    });
    $('.containerAnagrafiche li').mouseout(function() {
        $(".containerAnagrafiche .ui-state-hover").removeClass("ui-state-hover");
    });
    $(".containerAnagrafiche li").click(function() {
        if ($(this).attr("nonsicopia") == null) {
            $(".containerAnagrafiche .ui-state-highlight").removeClass("ui-state-highlight");
            $(".containerAnagrafiche .ui-state-hover").removeClass("ui-state-hover");
            elementClickked = $(this).attr("class").split(" ")[0];
            $(".containerAnagrafiche ." + elementClickked).addClass("ui-state-highlight");
            $(".containerAnagrafiche #spostaSingola").offset({
                top: $(".sorgente .ui-state-highlight").offset().top
            });
        } else
            return false;
    });
    $(".azioni>div").unbind();
    $(".azioni>div").mouseover(function() {
        $(this).addClass("ui-state-hover");
    });
    $(".azioni>div").mouseout(function() {
        $(this).removeClass("ui-state-hover")
    });
    $(".sorgente #comuneNascita\\.descrizione").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #comuneNascita\\.descrizione").val(),
                    date: $(".sorgente #dataNascita").val(),
                    action: "trovaComune"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione + " ( " + item.provincia.codCatastale + " ) ",
                            value: item.descrizione,
                            id: item.codCatastale,
                            idComune: item.idComune,
                            statoId: item.stato.idStato,
                            statoDesc: item.stato.descrizione,
                            provinciaId: item.provincia.idProvincie,
                            provinciaDesc: item.provincia.descrizione
                        }
                    }));
                }
            });
        },
        mustMatch: true,
        minLength: 2,
        select: function(event, ui) {
            //$(".sorgente #comuneNascita\\.descrizione").removeClass("ui-state-error");
            $(".sorgente #comuneNascita\\.descrizione").val(ui.item.value);
            $(".sorgente #comuneNascita\\.idComune").val(ui.item.idComune);
            $(".sorgente #comuneNascita\\.codCatastale").val(ui.item.id);
            $(".sorgente #comuneNascita\\.provincia\\.idProvincie").val(ui.item.provinciaId);
            $(".sorgente #comuneNascita\\.provincia\\.descrizione").val(ui.item.provinciaDesc);
            $(".sorgente #comuneNascita\\.stato\\.idStato").val(ui.item.statoId);
            $(".sorgente #comuneNascita\\.stato\\.descrizione").val(ui.item.statoDesc);
        }
    });
    listenerComune();
    $(".sorgente #desCittadinanza").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #desCittadinanza").val(),
                    action: "trovaCittadinanza"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            id: item.idCittadinanza
                        };
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            $(".sorgente #desCittadinanza").val(ui.item.value);
            $(".sorgente #idCittadinanza").val(ui.item.id);
        }
    });
    $(".sorgente #desNazionalita").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #desNazionalita").val(),
                    action: "trovaNazionalita"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            id: item.idNazionalita
                        };
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            $(".sorgente #desNazionalita").val(ui.item.value);
            $(".sorgente #idNazionalita").val(ui.item.id);
        }
    });
    $(".sorgente #provinciaIscrizione\\.descrizione").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #provinciaIscrizione\\.descrizione").val(),
                    action: "trovaProvincia"
                },
                success: function(data) {
                    response($.map(data.rows, function(item, id) {
                        return {
                            label: item,
                            value: item,
                            id: id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            $(".sorgente #provinciaIscrizione\\.descrizione").val(ui.item.value);
            $(".sorgente #provinciaIscrizione\\.idProvincia").val(ui.item.id);
        }
    });
    //    $(".sorgente #flgIndividuale").click(function(){
    //        if($(".sorgente #flgIndividuale").attr("selected") == "selected")
    //        {
    //            $(".sorgente #flgIndividuale").val('S');
    //        }
    //        else
    //        {
    //            $(".sorgente #flgIndividuale").val('N');
    //        }
    //    });

    $(".sorgente #desFormaGiuridica").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #desFormaGiuridica").val(),
                    action: "trovaFormaGiuridica"
                },
                success: function(data) {
                    if (count(data.rows) == 0) {
                        dialogError({
                            error: "Il valore immesso e' inesistente"
                        });
                        return;
                    }
                    response($.map(data.rows, function(item, id) {
                        return {
                            label: item,
                            value: item,
                            id: id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            $(".sorgente #desFormaGiuridica").val(ui.item.value);
            $(".sorgente #idFormaGiuridica").val(ui.item.id);
        }
    });
    $(".sorgente #ProvinciaCciaa\\.descrizione").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #ProvinciaCciaa\\.descrizione").val(),
                    action: "trovaProvincia"
                },
                success: function(data) {
                    response($.map(data.rows, function(item, id) {
                        return {
                            label: item,
                            value: item,
                            id: id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            $(".sorgente #ProvinciaCciaa\\.descrizione").val(ui.item.value);
            $(".sorgente #ProvinciaCciaa\\.idProvincie").val(ui.item.id);
        }
    })

    $(".sorgente #desTipoCollegio").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente #desTipoCollegio").val(),
                    action: "trovaTipoCollegio"
                },
                success: function(data) {
                    response($.map(data.rows, function(item, id) {
                        return {
                            label: item,
                            value: item,
                            id: id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            $(".sorgente #desTipoCollegio").val(ui.item.value);
            $(".sorgente #idTipoCollegio").val(ui.item.id);
        }
    });
//    $(".containerAnagrafiche .sorgente .tipoRuolo select").change(function(){
//        
//        var desTipoRuolo =  $(".containerAnagrafiche .sorgente .dettaglio input#desTipoRuolo").val();
//        var id = $.trim($(".containerAnagrafiche .sorgente .tipoRuolo select").val());
//        var des = $.trim($(".containerAnagrafiche .sorgente .tipoRuolo select :selected").text());
//        var div =$("<div>");
//        div.html("ATTENZIONE: stai per cambiare il ruolo dell'anagrafica da "+desTipoRuolo+ " a "+des+". <br/><br/><b>Processo irreversibile</b>, confermi l'operazione?<br/>")
//        $(div).dialog(
//        {
//            modal: true, 
//            title: "Conferma cambio ruolo anagrafica", 
//            buttons: {
//                Ok: function () {
//                    $(".containerAnagrafiche .sorgente .dettaglio input#idTipoRuolo").val(id);
//                    $(".containerAnagrafiche .sorgente .dettaglio input#desTipoRuolo").val(des);
//                    aggiornaAnagraficaXML();
//                }, 
//                Annulla:function (){
//                    $(".containerAnagrafiche .sorgente .tipoRuolo select option").each(function(index){
//                        var val = $(this).text();   
//                        var idRuolo = $(this).val();   
//                        if(val == desTipoRuolo)
//                        {
//                            $(".containerAnagrafiche .sorgente .tipoRuolo select").val(idRuolo);
//                        }
//                    });
//                    $(div).dialog('close');
//                }
//            }
//        });
//    });
}
;
//$(document).ready(function (){
//    $(".sorgente li.codiceFiscale input.codiceFiscale").autocomplete({
//        source: function ( request, response ) {
//            aggiornaAnagraficaXML();
//        }, 
//        minLength: 11, 
//        mustMatch:true
//    });
//});

function aggiornaAnagraficaXML()
{
    $(".sorgente .tipoAnagrafica > input").val($(".destinazione .tipoAnagrafica > input").val());
    $(".sorgente .varianteAnagrafica > input").val($(".destinazione .varianteAnagrafica > input").val());
    $.ajax({
        url: url,
        dataType: "json",
        delay: 500,
        data: {
            codiceFiscale: $(".sorgente li.codiceFiscale input.codiceFiscale").val(),
            varianteAnagrafica: $(".sorgente .varianteAnagrafica > input").val(),
            tipoAnagrafica: $(".sorgente .tipoAnagrafica > input").val(),
            desTipoRuolo: $(".sorgente .dettaglio .tipoRuolo input#desTipoRuolo").val(),
            counter: $(".sorgente .dettaglio .counter  input").val(),
            idPratica: idPratica,
            action: "salvaCFAnagraficaXML"
        },
        success: function(data) {
            if (data.errors != null)
            {
                dialogError(data.errors);
            }
            else
            {
                var form = $("<form>");
                form.append($('<input>',
                        {
                            'name': 'id_pratica',
                            'value': idPratica,
                            'type': 'hidden'
                        }));
                form.attr("action", document.URL);
                form.attr("method", "post");
                form.appendTo('body').submit();
            }
        }
    });
}


$(document).ready(function() {
    //RECAPITO NOTIFICA
    $(".recapitoNotifica .recapito\\.descComune").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".recapitoNotifica .recapito\\.descComune").val(),
                    date: "",
                    action: "trovaComune"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione + " ( " + item.provincia.descrizione + " ) ",
                            value: item.descrizione,
                            id: item.codCatastale,
                            idComune: item.idComune,
                            statoId: item.stato.idStato,
                            statoDesc: item.stato.descrizione,
                            provinciaId: item.provincia.idProvincie,
                            provinciaDesc: item.provincia.descrizione
                        };
                    }));
                }
            });
        },
        mustMatch: true,
        minLength: 2,
        select: function(event, ui)
        {
            $(".recapitoNotifica .recapito\\.descComune").val(ui.item.value);
            $(".recapitoNotifica .recapito\\.idComune").val(ui.item.idComune);
            $(".recapitoNotifica .recapito\\.codCatastale").val(ui.item.id);
            $(".recapitoNotifica .recapito\\.idProvincia").val(ui.item.provinciaId);
            $(".recapitoNotifica .recapito\\.descProvincia").val(ui.item.provinciaDesc);
            $(".recapitoNotifica .recapito\\.idStato").val(ui.item.statoId);
            $(".recapitoNotifica .recapito\\.descStato").val(ui.item.statoDesc);
        }
    });
    $("#recapitoNotificaAggiungi").click(function()
    {
        var $form = $("<form>").append($(".recapitoNotifica input").clone());
        $form.append($('<input>',
                {
                    'name': 'action',
                    'value': 'salvaRecapitoSingolo',
                    'type': 'hidden'
                }));
        $form.append($('<input>',
                {
                    'name': 'idPratica',
                    'value': idPratica,
                    'type': 'hidden'
                }));
        $form.attr({
            method: 'post',
            action: url
        });
        $.post(url, $form.serialize(),
                function(data) {
                    if (data.errors != null)
                    {
                        dialogError(data.errors);
                    }
                    else {
                        $("#recapitoNotifica").addClass("confermataBG");
                        $("#recapitoNotifica .confermata").val(1);
                        $("#recapitoNotifica .ui-state-error").removeClass("ui-state-error");
                    }
                }, 'json');
    });
});
$(document).ready(function() {
    autocompleteComuni();
    $("form.uniForm").submit(function() {
        var error = false;
        var errorMsg = new Array();
        var errVal = isValidPratica();
        if (errVal != null)
        {
            dialogError(errVal);
            return false;
        }
        $(".confermata").each(function(index) {
            var val = $(this).val();
            if (val == null || val == "")
            {
                var div = null;
                if (graficaNuova())
                {
                    if ($(this).parent().parent().hasClass("showdettaglio"))
                    {
                        div = $(this).parent().parent().parent().parent();
                    }
                    else
                    {
                        div = $(this).parent().parent();
                    }
                }
                else {
                    div = $(this).parent().parent();
                }
                div.addClass("ui-state-error");
                error = true;
            }
        });
        if (error)
        {

            errorMsg.push("Non hai confermato tutti i componenti della pratica.");
        }
        if (errorMsg.length > 0)
        {
            dialogError(errorMsg);
            return false;
        }

        $("form.uniForm").append($(".recapitoNotifica").clone().addClass("hidden"));
        $("form.uniForm").append($('<input>',
                {
                    'name': 'responsabileProcedimento',
                    'value': $("#responsabileProcedimento").val(),
                    'type': 'hidden'
                }));
        $("form.uniForm").append($('<input>',
                {
                    'name': 'protocollo',
                    'value': $("#protocollo").val(),
                    'type': 'hidden'
                }));
    });

});
function listenerComune()
{
    var className = ".sorgente ";
    $(className + " .recapito-descComune").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: $(".sorgente ." + this.element.parent().parent().attr("class") + " input.recapito-descComune").val(),
                    action: "trovaComune"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione + " ( " + item.provincia.descrizione + " ) ",
                            value: item.descrizione,
                            id: item.idComune,
                            idStato: item.stato.idStato,
                            descStato: item.stato.descrizione,
                            idProvincia: item.provincia.idProvincie,
                            descProvincia: item.provincia.descrizione
                        };
                    }));
                }
            });
        },
        mustMatch: true,
        minLength: 2,
        search: function(event, ui) {
            var className = ".sorgente ." + $(this).parent().parent().attr("class");
            var campo = $(className + " .recapitodescComune > .recapito-descComune").val();
            if (validator.letters.test(campo)) {
                //$(className + " .recapitodescComune > .recapito\\.descComune").removeClass("ui-state-error");
                return true;
            }
            //$(className + " .recapitodescComune >.recapito\\.descComune").addClass("ui-state-error");
            return false;
        },
        select: function(event, ui) {
            var className = ".sorgente ." + $(this).parent().parent().attr("class");
            //$(className + " .recapito\\.descComune").removeClass("ui-state-error");
            $(className + " .recapito-idComune").val(ui.item.id);
            $(className + " .recapito-idProvincia").val(ui.item.idProvincia);
            $(className + " .recapito-descProvincia").val(ui.item.descProvincia);
            $(className + " .recapito-idStato").val(ui.item.idStato);
            $(className + " .recapito-descStato").val(ui.item.descStato);
        }
    });
}
function autocompleteComuni() {
    //COMUNI
    $(".DatiCatastali .comune input").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: url,
                dataType: "json",
                data: {
                    descrizione: this.element.val(),
                    action: "trovaComune"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione + " ( " + item.stato.descrizione + " ) ",
                            value: item.descrizione,
                            id: item.idComune,
                            idStato: item.stato.idStato,
                            desStato: item.stato.descrizione,
                            idProvincia: item.provincia.idProvincie,
                            desProvincia: item.provincia.descrizione
                        }
                    }));
                }
            });
        },
        minLength: 2,
        mustMatch: true,
        select: function(event, ui) {
            var cllass = $(event.target).clone().removeClass("desComune ui-widget ui-widget-content ui-autocomplete-input textInput").attr("class");
            $(event.target).val(ui.item.value);
            $("div#" + cllass + " > input.desProvincia").val(ui.item.desProvincia);
            $("div#" + cllass + " > input.idStato").val(ui.item.idStato);
            $("div#" + cllass + " > input.desStato").val(ui.item.desStato);
            $("div#" + cllass + " > input.idProvincia").val(ui.item.idProvincia);
            $("div#" + cllass + " > input.idComune").val(ui.item.id);
        }
    });
}

/**
 * Gestione Modifica e aggiunta anagrafica
 */

$(document).ready(function() {
    eventEliminaAnagrafica()
    $(".nuovaAnagraficaDialog .dettaglio select").change(function() {
        var tipoAnagrafica = getTipoAnagrafica($(".nuovaAnagraficaDialog .dettaglio > select").val(), $(".nuovaAnagraficaDialog .varianteAnagrafica > select").val());
        $(".nuovaAnagraficaDialog .F").addClass("hidden");
        $(".nuovaAnagraficaDialog .F").hide();
        $(".nuovaAnagraficaDialog .P").addClass("hidden");
        $(".nuovaAnagraficaDialog .P").hide();
        $(".nuovaAnagraficaDialog .G").addClass("hidden");
        $(".nuovaAnagraficaDialog .G").hide();
        $(".nuovaAnagraficaDialog .F input").attr("disabled", "disabled");
        $(".nuovaAnagraficaDialog .P input").attr("disabled", "disabled");
        $(".nuovaAnagraficaDialog .G input").attr("disabled", "disabled");
        $(".nuovaAnagraficaDialog ." + tipoAnagrafica).removeClass("hidden");
        $(".nuovaAnagraficaDialog ." + tipoAnagrafica).show("hidden");
        $(".nuovaAnagraficaDialog ." + tipoAnagrafica + " input").show();
        $(".nuovaAnagraficaDialog ." + tipoAnagrafica).removeAttr("disabled");
        $(".nuovaAnagraficaDialog ." + tipoAnagrafica + " input").removeAttr("disabled");
        $(".nuovaAnagraficaDialog .dettaglio input.tipoAnagrafica").val($(".nuovaAnagraficaDialog  .dettaglio select").val());
        $(".nuovaAnagraficaDialog .dettaglio input.varianteAnagrafica").val(tipoAnagrafica);
    });
    /*$(".aggiungiAnagrafica ").click(function() {
     $(".nuovaAnagraficaDialog").removeClass("hidden");
     $(".nuovaAnagraficaDialog .F input").val("");
     $(".nuovaAnagraficaDialog .G input").val("");
     $(".nuovaAnagraficaDialog .P input").val("");
     $(".nuovaAnagraficaDialog .F").addClass("hidden");
     $(".nuovaAnagraficaDialog .P").addClass("hidden");
     $(".nuovaAnagraficaDialog .G").addClass("hidden");
     $(".nuovaAnagraficaDialog .F").hide();
     $(".nuovaAnagraficaDialog .P").hide();
     $(".nuovaAnagraficaDialog .G").hide();
     $(".nuovaAnagraficaDialog .dettaglio > select option:selected").removeAttr("selected");
     $(".nuovaAnagraficaDialog input").removeAttr("checked");
     $(".nuovaAnagraficaDialog .tipoRuolo > select option:selected").removeAttr("selected");
     $(".nuovaAnagraficaDialog").dialog({
     modal: true,
     width: 500,
     minHeight: 300,
     title: "Aggiungi anagrafica:",
     buttons: {
     Ok: function()
     {
     loccato = false;
     salvaAnagraficaInXML();
     },
     Annulla: function()
     {
     $(this).dialog("close");
     }
     }
     });
     });*/
    $(".impostaVarianteAnagrafica input").click(function() {
        var tipoAnagrafica = $(".nuovaAnagraficaDialog .dettaglio > select").val();
        if ($(this).attr("checked") != null)
        {
            $(".impostaVarianteAnagrafica input:checked").removeAttr("checked");
            $(this).attr("checked", "checked");
            var selTipoAnagrafica = $(".impostaVarianteAnagrafica input:checked").attr("id");
            $(".nuovaAnagraficaDialog ." + selTipoAnagrafica).show();
            $(".nuovaAnagraficaDialog ." + selTipoAnagrafica).removeClass("hidden");
            $(".nuovaAnagraficaDialog ." + selTipoAnagrafica + " input").removeAttr("disabled");
            $(".nuovaAnagraficaDialog .dettaglio input.varianteAnagrafica").val(selTipoAnagrafica);
            $(".nuovaAnagraficaDialog .dettaglio input.tipoAnagrafica  ").val(tipoAnagrafica);
        }
        else
        {
            var selTipoAnagrafica = $(this).attr("id");
            $(".nuovaAnagraficaDialog ." + selTipoAnagrafica).hide();
            $(".nuovaAnagraficaDialog .dettaglio input.varianteAnagrafica").val("");
            $(".nuovaAnagraficaDialog .dettaglioinput").val(tipoAnagrafica);
        }
    });
});
var loccato = true;
function salvaAnagraficaInXML()
{
    if (!loccato)
    {
        var tipoRuolo = $.trim($(".nuovaAnagraficaDialog .tipoRuolo > select").val());
        var tipoAnagrafica = $.trim($(".nuovaAnagraficaDialog .dettaglio > select").val());
        var selTipoAnagrafica = getTipoAnagrafica($(".nuovaAnagraficaDialog .dettaglio > select").val(), $(".nuovaAnagraficaDialog .dettaglio > select.varianteAnagrafica").val());
        var cf = $.trim($(".nuovaAnagraficaDialog .codiceFiscale input").val());
        var err = new Array();
        if (tipoRuolo == "" || tipoAnagrafica == "")
        {
            err.push("Selezionare tipo anagrafica e Tipo Ruolo");
        }
        if (cf == "")
        {
            err.push("Immettere il Codice Fiscale");
        }
        if (err.length > 0)
        {
            dialogError(err);
        }
        else
        {


            var form = $("<form>").append($(".nuovaAnagraficaDialog input").clone());
            form.attr({
                name: 'anagrafica',
                method: 'post',
                action: url
            });
            form.append($('<input>',
                    {
                        'name': 'action',
                        'value': 'salvaAnagraficaInXML',
                        'type': 'hidden'
                    }));
            form.append($('<input>',
                    {
                        'name': 'idPratica',
                        'value': idPratica,
                        'type': 'hidden'
                    }));
            /*
             form.append($('<input>', 
             {
             'name': 'tipoAnagrafica', 
             'value': $(".nuovaAnagraficaDialog .tipoAnagrafica select").val(), 
             'type': 'hidden'
             }));
             */
            var recapitiIndex = 0;
            if (selTipoAnagrafica == "G")
            {
                form.append($('<input>',
                        {
                            'name': 'recapiti[' + recapitiIndex + '].descTipoIndirizzo',
                            'value': "SEDE",
                            'type': 'hidden'
                        }));
            } else {
                form.append($('<input>',
                        {
                            'name': 'recapiti[' + recapitiIndex + '].descTipoIndirizzo',
                            'value': "RESIDENZA",
                            'type': 'hidden'
                        }));
            }
            form.append($('<input>',
                    {
                        'name': 'recapiti[' + recapitiIndex + '].counter',
                        'value': recapitiIndex,
                        'type': 'hidden'
                    }));
            recapitiIndex++;
            $(".recapiti input:checked").each(function(index) {
                form.append($('<input>',
                        {
                            'name': 'recapiti[' + recapitiIndex + '].descTipoIndirizzo',
                            'value': $(this).attr("name"),
                            'type': 'hidden'
                        }));
                form.append($('<input>',
                        {
                            'name': 'recapiti[' + recapitiIndex + '].counter',
                            'value': recapitiIndex,
                            'type': 'hidden'
                        }));
                recapitiIndex++;
            });
            form.append($('<input>',
                    {
                        'name': 'idTipoRuolo',
                        'value': $(".nuovaAnagraficaDialog .tipoRuolo select").val(),
                        'type': 'hidden'
                    }));
            form.append($('<input>',
                    {
                        'name': 'codTipoRuolo',
                        'value': $(".nuovaAnagraficaDialog .tipoRuolo select option:selected").attr("title"),
                        'type': 'hidden'
                    }));
            form.append($('<input>',
                    {
                        'name': 'desTipoRuolo',
                        'value': $(".nuovaAnagraficaDialog .tipoRuolo select option:selected").text(),
                        'type': 'hidden'
                    }));
            $.post(url, form.serialize(), function(data)
            {
                var ana = $.trim($(".nuovaAnagraficaDialog .codiceFiscale > input").val());
                if (data.errors != null)
                {
                    dialogError(data.errors);
                }
                else
                {
                    var form = $("<form>");
                    form.append($('<input>',
                            {
                                'name': 'id_pratica',
                                'value': idPratica,
                                'type': 'hidden'
                            }));
                    form.attr("action", document.URL);
                    form.attr("method", "post");
                    form.appendTo('body').submit();
                }
            }, 'json');
        }
    }

}

function count(obj)
{
    var count = 0;
    for (i in obj) {
        count++;
    }
    return count;
}
function graficaNuova()
{
    if ($("#anagrafica_box").length > 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}
function empty(valore) {
    if (valore != null && valore != "") {
        return false;
    }
    return true;
}

function disactiveConfermato(obj) {
    var classe = $($(obj).attr('currentTarget')).attr('class').split(' ')[0];
    $("#" + classe + " .confermato").val('false');
    $("#" + classe).removeClass("confermataBG");
    obj.stopPropagation();
}
function vaiCatasto(url) {
    window.open(url);
    return false;
}
