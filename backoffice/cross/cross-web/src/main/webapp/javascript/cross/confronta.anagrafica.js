//Campi da abilitare per ogni tipo di persona
var personaFisicaFields = ['idTipoPersona', 'idTipoRuolo', 'flgIndividuale', 'codiceFiscale', 'nome', 'cognome', 'sesso', 'cittadinanza', 'nazionalita',
    'dataNascita', 'comuneNascita', 'comuneNascitaStato', 'comuneNascitaProvincia', 'localitaNascita'];
var personaDittaIndividuale = ['partitaIva', 'denominazione', 'provinciaCciaa', 'flgAttesaIscrizioneRea', 'nIscrizioneRea', 'dataIscrizioneRea',
    'flgAttesaIscrizioneRi', 'nIscrizioneRi', 'dataIscrizioneRi'];
var personaGiuridicaFields = ['idTipoPersona', 'idTipoRuolo', 'codiceFiscale', 'partitaIva', 'denominazione', 'desFormaGiuridica', 'provinciaCciaa',
    'flgAttesaIscrizioneRea', 'nIscrizioneRea', 'dataIscrizioneRea', 'flgAttesaIscrizioneRi', 'nIscrizioneRi', 'dataIscrizioneRi'];
var professionistaFields = ['partitaIva', 'tipoCollegio', 'numeroIscrizione', 'alboProvincia'];
var urlConfrontoAnagrafiche = path + '/pratiche/nuove/apertura/dettaglio/ajax.htm?idPratica=' + idPratica;

var validator = {
    email: /^[\w\-\.]*[\w\.]\@[\w\.]*[\w\-\.]+[\w\-]+[\w]\.+[\w]+[\w $]/,
    letters: /^[a-z\u00E0-\u00FC\-\*\(\)'= .]+$/i,
    alphanumeric: /^[a-zA-Z0-9\u00E0-\u00FC\-\*\(\)/ .]+$/i,
    date: /^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$/,
    numeric: /^[0-9.]+$/,
    year: /^[1-9]\d{3}$/
};

//Indirizzi da visualizzare per ogni tipo di persona (SEDE OPERATIVA sempre presente)
var indirizziPersonaFisica = ['RES', 'DOM'];
var indirizziPersonaGiuridica = ['SED'];

$(function() {

    var confrontoAnagraficheDialog = $('#confrontaAnagrafiche').dialog({
        autoOpen: false,
        modal: true,
        width: $(window).width() * 0.80,
        height: $(window).height() * 0.80,
        title: "Confronto anagrafiche:",
        buttons: {
            Ok: function() {
                console.log('Salva anagrafiche');
                if (isAnagraficaValid() && isRecapitiValid()) {
                    $(".sorgente input").removeClass("ui-state-error");
                    var div = $("<div>");
                    div.attr("id", 'salvaAnagrafica');
                    var checkCf = isValidCF();
                    if (checkCf) {
                        $(div).html(messaggioConferma).dialog({
                            buttons: {
                                SI: function() {
                                    $(this).dialog('close');
                                    salvaAnagrafica();
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
                console.log('Annulla');
                $(this).dialog('close');
            },
            Copia: function() {
                console.log('Copia anagrafiche');
                copiaTutto();
            }
        },
        close: function() {
            $('#destinazioneForm')[0].reset();
            $('#sorgenteForm')[0].reset();
        }
    });

    $(".anagrafica_confronta").on("click", function() {
        hideAllFields();
        var codiceFiscale = $(this).find("input[name='codiceFiscale']").val();
        var idPratica = $(this).find("input[name='idPratica']").val();
        var counterAnagrafica = $(this).find("input[name='counter']").val();
        var idAnagrafica = $(this).find("input[name='idAnagrafica']").val();
        initializeFormElements(idPratica, counterAnagrafica, idAnagrafica, codiceFiscale);
        $(confrontoAnagraficheDialog).css({
            overflow: "auto"}
        );
    });

    //Abilitazione dei campi in base al tipo di persona selezionato
    $('select[name="idTipoPersona"]').change(function() {
        var value = $(this).val();
        var codiceFiscale = $(".destinazione input[name='codiceFiscale']").val();
        var idAnagrafica = $(".destinazione input[name='idAnagrafica']").val();
        var originalValue = $(this).siblings('input[name="idTipoPersonaOriginalValue"]').val();
        $.ajax({
            url: urlConfrontoAnagrafiche,
            dataType: 'json',
            async: false,
            data: {
                idAnagrafica: idAnagrafica,
                codiceFiscale: codiceFiscale,
                idTipoPersona: value,
                action: 'isTipoPersonaModificabile'
            },
            success: function(data) {
                console.log(data);
                if (data.success !== undefined && data.success === false) {
                    $('select[name="idTipoPersona"]').val(originalValue);
                    var messages = [];
                    messages.push(data.message);
                    displayResponseDialog(messages, true);
                } else {
                    emptySelect('', 'idTipoPersona');
                    setValToSelect('', 'idTipoPersona', value);
                    if (value === 'F') {
                        hideFields(personaGiuridicaFields);
                        enableFields(personaFisicaFields);
                        calcolaTipiIndirizzo('F', true);
                    } else if (value === 'G') {
                        hideFields(personaFisicaFields);
                        hideFields(personaDittaIndividuale);
                        hideFields(professionistaFields);
                        enableFields(personaGiuridicaFields);
                        calcolaTipiIndirizzo('G', true);
                    }
                }
            }
        });
    });

    //Abilita i campi visibili per la ditta individuale
    $('select[name="flgIndividuale"]').change(function() {
        var value = $(this).val();
        emptySelect('', 'flgIndividuale');
        setValToSelect('', 'flgIndividuale', value);
        if (value === 'S') {
            enableFields(personaDittaIndividuale);
        } else if (value === 'N') {
            hideFields(personaDittaIndividuale);
        }
    });

    //Abilita i campi visibili per il Tecnico professionista
    $('select[name="idTipoRuolo"]').change(function() {
        var value = $(this).val();
        emptySelect('', 'idTipoRuolo');
        setValToSelect('', 'idTipoRuolo', value);
        var codTipoRuolo = $(this).find('option[value="' + value + '"]').attr('class');
        var tipoPersona = $(this).closest('ul').find('select[name="idTipoPersona"]').val();
        var flgIndividuale = $(this).closest('ul').find('select[name="flgIndividuale"]').val();
        if (codTipoRuolo === 'P' && tipoPersona === 'F') {
            hideFields(personaGiuridicaFields);
            hideFields(professionistaFields);
            enableFields(personaFisicaFields);
            enableFields(professionistaFields);
            calcolaTipiIndirizzo('F', false);
            if (flgIndividuale === 'S') {
                enableFields(personaDittaIndividuale);
            }
        } else {
            if (tipoPersona === 'F') {
                hideFields(personaGiuridicaFields);
                hideFields(professionistaFields);
                enableFields(personaFisicaFields);
                if (flgIndividuale === 'S') {
                    enableFields(personaDittaIndividuale);
                }
                calcolaTipiIndirizzo('F', false);
            } else if (tipoPersona === 'G') {
                hideFields(personaFisicaFields);
                hideFields(professionistaFields);
                hideFields(personaDittaIndividuale);
                enableFields(personaGiuridicaFields);
                calcolaTipiIndirizzo('G', false);
            }
        }
    });

    $(".dataPicker").datepicker({
        dateFormat: 'dd/mm/yy'
    });

    eventEliminaAnagrafica();
    manageSubmitPratica();
});

function manageSubmitPratica() {
    $("form.uniForm").submit(function() {
        var error = false;
        var errorMsg = new Array();
        var errVal = isPraticaValid();
        if (errVal !== null) {
            displayResponseDialog(errVal, true);
            return false;
        }

        $(".confermata").each(function(index) {
            var val = $(this).val();
            if (val === null || val === "") {
                var div = null;
                if ($(this).parent().parent().hasClass("showdettaglio")) {
                    div = $(this).parent().parent().parent().parent();
                } else {
                    div = $(this).parent().parent();
                }
                div.addClass("ui-state-error");
                error = true;
            }
        });
        if (error) {
            errorMsg.push("Non hai confermato tutti i componenti della pratica.");
        }
        if (errorMsg.length > 0) {
            displayResponseDialog(errorMsg, true);
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
}

//Inizializza gli elementi necessari alla popup di confronto anagrafiche
function initializeFormElements(idPratica, counterAnagrafica, idAnagrafica, codiceFiscale) {
    resetAnagrafica();
    $.ajax({
        url: urlConfrontoAnagrafiche,
        dataType: 'json',
        async: false,
        data: {
            idPratica: idPratica,
            idAnagrafica: idAnagrafica,
            codiceFiscale: codiceFiscale,
            counter: counterAnagrafica,
            action: 'getAnagrafica'
        },
        success: function(data) {
            console.log(data);
            if (data.success !== undefined && data.success === false) {
                var messages = [];
                messages.push(data.message);
                displayResponseDialog(messages, true);
            } else {
                initializeConfrontoAnagrafiche(data);
                initializeAutocompleteSections();
                initializeExternalIntegrations();
                initializeHighlight();
                $('#confrontaAnagrafiche').dialog('open');
            }
        }
    });
}

//Valorizza i dati di confronto anagrafiche
function initializeConfrontoAnagrafiche(json) {
    resetAnagrafica();
    var ricercaAnagrafe = false;
    var ricercaAnagrafePG = false;
    var anagraficaSorgente = json.rows[0];
    var anagraficaDestinazione = json.rows[1];
    var selTipoAnagrafica = anagraficaSorgente['tipoAnagrafica'];
    var varianteAnagrafica = anagraficaSorgente['varianteAnagrafica'];
    var ruolo = anagraficaSorgente['idTipoRuolo'];
    calcolaTipiIndirizzo(selTipoAnagrafica);
    if (anagraficaSorgente) {
        popolaDatiPersona(".sorgente", anagraficaSorgente);
    }
    if (anagraficaDestinazione) {
        popolaDatiPersona(".destinazione", anagraficaDestinazione);
        $(".destinazione input[name='idTipoRuoloOriginale']").val($.trim(anagraficaDestinazione['idTipoRuoloOriginale']));
        if (attivaRicercaAnagrafe && anagraficaDestinazione['tipoAnagrafica'] === 'F') {
            ricercaAnagrafe = true;
        }
        if (attivaRicercaAnagrafePG && anagraficaDestinazione['tipoAnagrafica'] === 'G') {
            ricercaAnagrafePG = true;
        }
    }
    //Popolamento recapiti
    if (anagraficaSorgente && anagraficaSorgente['recapiti']) {
        for (var i = 0; i < anagraficaSorgente['recapiti'].length; i++) {
            valorizzaRecapito('sorgente', i, anagraficaSorgente);
        }
    }
    if (anagraficaDestinazione && anagraficaDestinazione['recapiti']) {
        for (var i = 0; i < anagraficaDestinazione['recapiti'].length; i++) {
            valorizzaRecapito('destinazione', i, anagraficaDestinazione);
        }
    }

    if (selTipoAnagrafica === "F") {
        //PERSONA FISICA
        enableFields(personaFisicaFields);
        if (ricercaAnagrafe) {
            $(".sorgente .ricercaAnagrafe ").show();
        }
        //DITTA INDIVIDUALE
        if (varianteAnagrafica === "I") {
            enableFields(personaDittaIndividuale);
            if (ricercaAnagrafe) {
                $(".sorgente .ricercaAnagrafe ").hide();
                $(".sorgente .ricercaAnagrafe ").removeAttr('style');
            }
        }

        var tipoRuolo = $('.sorgente select[name="idTipoRuolo"] option[value="' + ruolo + '"]').attr('class');
        if (tipoRuolo === 'P') {
            enableFields(professionistaFields);
        }

    } else if (selTipoAnagrafica === "G") {
        //PERSONA GIURIDICA
        enableFields(personaGiuridicaFields);
    } else {
        //Non so che tipo di persona è. Potrebbe essere arrivata da protocollo. Visualizzo tutto e poi l'operatore sceglierà quello corretto
        enableAllFields();
    }

    $(".ui-dialog").append('<div id="blank"><br></div>');
    //TODO: verifica questo comportamento
    if ($("#abilitazione").val() !== "true") {
        $('.ui-dialog-buttonpane button:contains("Copia")').button().remove();
        $('.ui-dialog-buttonpane button:contains("Ok")').button().remove();
    }
}

//Crea le sezioni per i recapiti, valorizzandoli
function valorizzaRecapito(container, counter, data) {
    if ($('.' + container + ' .recapito' + counter).length === 0) {
        var recapito = $('.' + container + ' .recapito0').clone().removeClass("recapito0").addClass("recapito" + counter);
        recapito.find("input").each(function() {
            var name = $(this).attr("name");
            if (counter > 0) {
                name = name.replace('0', counter);
            }
            $(this).attr("name", name);
        });
        recapito.find("li").each(function() {
            var name = $(this).attr("class");
            if (counter > 0) {
                name = name.replace('0', counter);
            }
            $(this).attr("class", name);
        });
        recapito.find("select").each(function() {
            var name = $(this).attr("name");
            if (counter > 0) {
                name = name.replace('0', counter);
            }
            $(this).attr("name", name);
        });
        $('.' + container + ' .recapito' + (counter - 1)).after(recapito);
    }
    popolaRecapiti('.' + container, counter, data['recapiti'][counter]);
}

function calcolaTipiIndirizzo(tipoPersona, setDefault) {
    if (tipoPersona === 'F') {
        for (var i = 0; i < indirizziPersonaGiuridica.length; i++) {
            //Nascondo i recapiti delle persone giuridiche
            $('.recapiti\\.idTipoIndirizzo').children('option[class="' + indirizziPersonaGiuridica[i] + '"]').hide();
        }
        for (var i = 0; i < indirizziPersonaFisica.length; i++) {
            //Nascondo i recapiti delle persone giuridiche
            $('.recapiti\\.idTipoIndirizzo').children('option[class="' + indirizziPersonaFisica[i] + '"]').show();
        }
        if (setDefault) {
            emptySelect('', '.recapiti\\.idTipoIndirizzo');
            setValToSelect('', '.recapiti\\.idTipoIndirizzo', "RES");
//            var residenza = $('.recapiti\\.idTipoIndirizzo').children('option[class="RES"]').attr('selected', 'selected');
//            $('.recapiti\\.idTipoIndirizzo').val(residenza);
        }
    } else if (tipoPersona === 'G') {
        for (var i = 0; i < indirizziPersonaFisica.length; i++) {
            //Nascondo i recapiti delle persone giuridiche
            $('.recapiti\\.idTipoIndirizzo').children('option[class="' + indirizziPersonaFisica[i] + '"]').hide();
        }
        for (var i = 0; i < indirizziPersonaGiuridica.length; i++) {
            //Nascondo i recapiti delle persone giuridiche
            $('.recapiti\\.idTipoIndirizzo').children('option[class="' + indirizziPersonaGiuridica[i] + '"]').show();
        }
        if (setDefault) {
            emptySelect('', '.recapiti\\.idTipoIndirizzo');
            setValToSelect('', '.recapiti\\.idTipoIndirizzo', "SED");
//            var sede = $('.recapiti\\.idTipoIndirizzo').children('option[class="SED"]').val();
//            $('.recapiti\\.idTipoIndirizzo').val(sede);spos
        }
    }

}

function popolaRecapiti(sezione, counter, data) {
    $(sezione + " input[name='recapiti[" + counter + "]\\.counter']").val($.trim(data['counter']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.idRecapito']").val($.trim(data['idRecapito']));
    $(sezione + " select[name='recapiti[" + counter + "]\\.idTipoIndirizzo']").val("");
    $(sezione + " select[name='recapiti[" + counter + "]\\.idTipoIndirizzo'] option").removeAttr("selected");
    $(sezione + " select[name='recapiti[" + counter + "]\\.idTipoIndirizzo']").val($.trim(data['idTipoIndirizzo']));
    $(sezione + " select[name='recapiti[" + counter + "]\\.idTipoIndirizzo'] option[value='" + $.trim(data['idTipoIndirizzo']) + "']").attr('selected', 'selected');
    $(sezione + " input[name='recapiti[" + counter + "]\\.indirizzo']").val($.trim(data['indirizzo']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.nCivico']").val($.trim(data['nCivico']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.cap']").val($.trim(data['cap']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.descComune']").val($.trim(data['descComune']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.idComune']").val($.trim(data['idComune']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.descProvincia']").val($.trim(data['descProvincia']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.idProvincia']").val($.trim(data['idProvincia']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.descStato']").val($.trim(data['descStato']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.idStato']").val($.trim(data['idStato']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.telefono']").val($.trim(data['telefono']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.cellulare']").val($.trim(data['cellulare']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.pec']").val($.trim(data['pec']));
    $(sezione + " input[name='recapiti[" + counter + "]\\.email']").val($.trim(data['email']));
}
function emptySelect(sezione, name) {
    $(sezione + " select[name='" + name + "']").val('');
    $(sezione + " select[name='" + name + "'] option").removeAttr("selected");
}

function setValToSelect(sezione, name, value) {
    $(sezione + " select[name='" + name + "']").val(value);
    $(sezione + " select[name='" + name + "'] option[value='" + value + "']").attr('selected', 'selected');
}

function popolaDatiPersona(sezione, data) {

    emptySelect(sezione, 'idTipoPersona');
    setValToSelect(sezione, 'idTipoPersona', $.trim(data['tipoAnagrafica']));
    $(sezione + " input[name='idTipoPersonaOriginalValue']").val('');
    $(sezione + " input[name='idTipoPersonaOriginalValue']").val($.trim(data['tipoAnagrafica']));

    emptySelect(sezione, 'flgIndividuale');
    if (sezione === '.sorgente') {
        if (data['varianteAnagrafica'] === 'I') {
            setValToSelect(sezione, 'flgIndividuale', 'S');
        } else {
            setValToSelect(sezione, 'flgIndividuale', 'N');
        }
    } else {
        var flgIndividualeSorgente = $('.sorgente select[name="flgIndividuale"]').val();
        setValToSelect(sezione, 'flgIndividuale', flgIndividualeSorgente);
    }

    emptySelect(sezione, 'idTipoRuolo');
    setValToSelect(sezione, 'idTipoRuolo', $.trim(data['idTipoRuolo']));

    $(sezione + " input[name='idAnagrafica']").val($.trim(data['idAnagrafica']));
    $(sezione + " input[name='counter']").val($.trim(data['counter']));

    $(sezione + " input[name='codiceFiscale']").val($.trim(data['codiceFiscale']));
    $(sezione + " input[name='partitaIva']").val($.trim(data['partitaIva']));
    $(sezione + " input[name='nome']").val($.trim(data['nome']));
    $(sezione + " input[name='cognome']").val($.trim(data['cognome']));

    emptySelect(sezione, 'sesso');
    setValToSelect(sezione, 'sesso', $.trim(data['sesso']));

    $(sezione + " input[name='desCittadinanza']").val($.trim(data['desCittadinanza']));
    $(sezione + " input[name='idCittadinanza']").val($.trim(data['idCittadinanza']));
    $(sezione + " input[name='desNazionalita']").val($.trim(data['desNazionalita']));
    $(sezione + " input[name='idNazionalita']").val($.trim(data['idNazionalita']));
    $(sezione + " input[name='dataNascita']").val($.trim(data['dataNascita']));

    if (data['comuneNascita'] !== null && data['comuneNascita'] !== undefined) {
        $(sezione + " .dettaglio input[name='comuneNascita\\.descrizione']").val($.trim(data['comuneNascita']['descrizione']));
        $(sezione + " .dettaglio input[name='comuneNascita\\.idComune']").val($.trim(data['comuneNascita']['idComune']));
        $(sezione + " .dettaglio input[name='comuneNascita\\.codCatastale']").val($.trim(data['comuneNascita']['codCatastale']));
        if (data['comuneNascita']['stato'] !== null) {
            $(sezione + " .dettaglio input[name='comuneNascita\\.stato\\.descrizione']").val($.trim(data['comuneNascita']['stato']['descrizione']));
            $(sezione + " .dettaglio input[name='comuneNascita\\.stato\\.idStato']").val($.trim(data['comuneNascita']['stato']['idStato']));
        }
        if (data['comuneNascita']['provincia'] !== null) {
            $(sezione + " .dettaglio input[name='comuneNascita\\.provincia\\.descrizione']").val($.trim(data['comuneNascita']['provincia']['descrizione']));
            $(sezione + " .dettaglio input[name='comuneNascita\\.provincia\\.idProvincie']").val($.trim(data['comuneNascita']['provincia']['idProvincie']));
        }
    }

    $(sezione + " input[name='localitaNascita']").val($.trim(data['localitaNascita']));
    $(sezione + " input[name='denominazione']").val($.trim(data['denominazione']));
    $(sezione + " input[name='desFormaGiuridica']").val($.trim(data['desFormaGiuridica']));
    $(sezione + " input[name='idFormaGiuridica']").val($.trim(data['idFormaGiuridica']));

    if (data['provinciaCciaa'] !== null && data['provinciaCciaa'] !== undefined) {
        $(sezione + " input[name='provinciaCciaa\\.descrizione']").val($.trim(data['provinciaCciaa']['descrizione']));
        $(sezione + " input[name='provinciaCciaa\\.idProvincie']").val($.trim(data['provinciaCciaa']['idProvincie']));
    }

    emptySelect(sezione, 'flgAttesaIscrizioneRea');
    setValToSelect(sezione, 'flgAttesaIscrizioneRea', $.trim(data['flgAttesaIscrizioneRea']));

    $(sezione + " input[name='nIscrizioneRea']").val($.trim(data['nIscrizioneRea']));
    $(sezione + " input[name='dataIscrizioneRea']").val($.trim(data['dataIscrizioneRea']));

    emptySelect(sezione, 'flgAttesaIscrizioneRi');
    setValToSelect(sezione, 'flgAttesaIscrizioneRi', $.trim(data['flgAttesaIscrizioneRi']));

    $(sezione + " input[name='nIscrizioneRi']").val($.trim(data['nIscrizioneRi']));
    $(sezione + " input[name='dataIscrizioneRi']").val($.trim(data['dataIscrizioneRi']));
    $(sezione + " input[name='desTipoCollegio']").val($.trim(data['desTipoCollegio']));
    $(sezione + " input[name='idTipoCollegio']").val($.trim(data['idTipoCollegio']));
    $(sezione + " input[name='numeroIscrizione']").val($.trim(data['numeroIscrizione']));

    if (data['provinciaIscrizione'] !== null && data['provinciaIscrizione'] !== undefined) {
        $(sezione + " input[name='provinciaIscrizione\\.descrizione']").val($.trim(data['provinciaIscrizione']['descrizione']));
        $(sezione + " input[name='provinciaIscrizione\\.idProvincie']").val($.trim(data['provinciaIscrizione']['idProvincie']));
    }
}

function salvaAnagrafica() {

    var counter = $("#destinazioneForm input[name='counter']").val();
    var form = $("#destinazioneForm").clone(true);
    form.attr({
        name: 'anagrafica',
        method: 'post',
        action: urlConfrontoAnagrafiche
    });

    form.append($('<input>', {
        name: 'action',
        value: 'salvaAnagrafica',
        type: 'hidden'
    }));

    form.append($('<input>', {
        name: 'idPratica',
        value: idPratica,
        type: 'hidden'
    }));

    $.post(urlConfrontoAnagrafiche, form.serialize(),
            function(data) {
                if (data.success !== undefined) {
                    if (data.success) {
                        $("#salvaAnagrafica").dialog("close");
                        $("#confrontaAnagrafiche").dialog("close");
                        var messages = [];
                        messages.push(data.message);
                        displayResponseDialog(messages, false);
                        $("#anagrafica" + counter + " .confermata").val("1");
                        $("#anagrafica" + counter).addClass("confermataBG");
                        $("#anagrafica" + counter).removeClass("ui-state-error");
                    } else {
                        var messages = [];
                        messages.push(data.message);
                        displayResponseDialog(messages, true);
                    }
                } else {
                    displayResponseDialog(data.errors, true);
                }
            }, 'json');
}

//Funzionalità di copia
function copiaComuneDiNascita() {
    var idStato = $(".sorgente input[name='comuneNascita\\.stato\\.idStato']").val();
    //Se ho lo stato, allora la componente del comune di nascita è valorizzata correttamente e quindi posso copiarla
    if (idStato) {
        $(".destinazione input[name='comuneNascita\\.descrizione']").val($(".sorgente input[name='comuneNascita\\.descrizione']").val());
        $(".destinazione input[name='comuneNascita\\.codCatastale']").val($(".sorgente input[name='comuneNascita\\.codCatastale']").val());
        $(".destinazione input[name='comuneNascita\\.idComune']").val($(".sorgente input[name='comuneNascita\\.idComune']").val());
        $(".destinazione input[name='comuneNascita\\.stato\\.descrizione']").val($(".sorgente input[name='comuneNascita\\.stato\\.descrizione']").val());
        $(".destinazione input[name='comuneNascita\\.stato\\.idStato']").val(idStato);
        $(".destinazione input[name='comuneNascita\\.provincia\\.descrizione']").val($(".sorgente input[name='comuneNascita\\.provincia\\.descrizione']").val());
        $(".destinazione input[name='comuneNascita\\.provincia\\.idProvincie']").val($(".sorgente input[name='comuneNascita\\.provincia\\.idProvincie']").val());
    }
}

function copiaFormaGiuridica() {
    var idFormaGiuridica = $(".sorgente input[name='idFormaGiuridica']").val();
    if (idFormaGiuridica) {
        $(".destinazione input[name='desFormaGiuridica']").val($(".sorgente input[name='desFormaGiuridica']").val());
        $(".destinazione input[name='idFormaGiuridica']").val(idFormaGiuridica).val(idFormaGiuridica);
    }
}

function copiaProvinciaCciaa() {
    var idProvincia = $(".sorgente input[name='provinciaCciaa\\.idProvincie']").val();
    if (idProvincia) {
        $(".destinazione input[name='provinciaCciaa\\.descrizione']").val($(".sorgente input[name='provinciaCciaa\\.descrizione']").val());
        $(".destinazione input[name='provinciaCciaa\\.idProvincie']").val(idProvincia);
    }
}

function copiaTipoCollegio() {
    var idTipoCollegio = $(".sorgente input[name='idTipoCollegio']").val();
    $(".destinazione input[name='desTipoCollegio']").val($(".sorgente input[name='desTipoCollegio']").val());
    $(".destinazione input[name='idTipoCollegio']").val(idTipoCollegio);
}

function copiaProvinciaAlbo() {
    var idProvincia = $(".sorgente input[name='provinciaIscrizione\\.idProvincie']").val();
    if (idProvincia) {
        $(".destinazione input[name='provinciaIscrizione\\.descrizione']").val($(".sorgente input[name='provinciaIscrizione\\.descrizione']").val());
        $(".destinazione input[name='provinciaIscrizione\\.idProvincie']").val(idProvincia);
    }
}

function copiaCittadinanza() {
    var idCittadinanza = $(".sorgente input[name='idCittadinanza']").val();
    if (idCittadinanza) {
        $(".destinazione input[name='desCittadinanza']").val($(".sorgente input[name='desCittadinanza']").val());
        $(".destinazione input[name='idCittadinanza']").val(idCittadinanza);
    }
}

function copiaNazionalita() {
    var idNazionalita = $(".sorgente input[name='idNazionalita']").val();
    if (idNazionalita) {
        $(".destinazione input[name='desNazionalita']").val($(".sorgente input[name='desNazionalita']").val());
        $(".destinazione input[name='idNazionalita']").val(idNazionalita);
    }
}

function copiaRecapito(counter) {
    //Non sovrascrivo i campi con campi vuoti/non valorizzati
    var i = counter;
    copiaComuneRecapito(i);
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.counter']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.counter']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.counter']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.idTipoIndirizzo']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.idTipoIndirizzo']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.idTipoIndirizzo']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.indirizzo']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.indirizzo']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.indirizzo']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.nCivico']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.nCivico']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.nCivico']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.cap']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.cap']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.cap']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.telefono']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.telefono']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.telefono']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.cellulare']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.cellulare']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.cellulare']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.pec']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.pec']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.pec']").val());
    }
    if ($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.email']").val()) {
        $(".destinazione .recapito" + i + " input[name='recapiti[" + i + "]\\.email']").val($(".sorgente .recapito" + i + " input[name='recapiti[" + i + "]\\.email']").val());
    }
}

function copiaComuneRecapito(counter) {
    var i = counter;
    var idStato = $(".sorgente .recapito" + i + " input.recapito-idStato").val();
    //Se ho lo stato, allora la componente del recapito è valorizzata e quindi la copio
    if (idStato) {
        $(".destinazione .recapito" + i + " input.recapito-descComune").val($(".sorgente .recapito" + i + " input.recapito-descComune").val());
        $(".destinazione .recapito" + i + " input.recapito-idComune").val($(".sorgente .recapito" + i + " input.recapito-idComune").val());
        $(".destinazione .recapito" + i + " input.recapito-descProvincia").val($(".sorgente .recapito" + i + " input.recapito-descProvincia").val());
        $(".destinazione .recapito" + i + " input.recapito-idProvincia").val($(".sorgente .recapito" + i + " input.recapito-idProvincia").val());
        $(".destinazione .recapito" + i + " input.recapito-descStato").val($(".sorgente .recapito" + i + " input.recapito-descStato").val());
        $(".destinazione .recapito" + i + " input.recapito-idStato").val(idStato);
    }
}

function copiaTutto() {

    $(".sorgente select").each(function() {
        var value = $(this).val();
        var nameField = $(this).attr('name');
        //Copio solo se il valore della sorgente non è vuoto
        if (value) {
            emptySelect('', nameField);
            setValToSelect('', nameField, value);
        }
    });

    $(".sorgente input").each(function() {
        var value = $(this).val();
        var elementClicked = $(this).closest("li");
        if (elementClicked.length === 1) {
            elementClicked = elementClicked.attr("class").split(" ")[0];
        } else {
            elementClicked = undefined;
        }
        if (elementClicked !== undefined && elementClicked === 'comuneNascita') {
            copiaComuneDiNascita();
        } else if (elementClicked !== undefined && elementClicked === 'desFormaGiuridica') {
            copiaFormaGiuridica();
        } else if (elementClicked !== undefined && elementClicked === 'provinciaCciaa') {
            copiaProvinciaCciaa();
        } else if (elementClicked !== undefined && elementClicked === 'tipoCollegio') {
            copiaTipoCollegio();
        } else if (elementClicked !== undefined && elementClicked === 'alboProvincia') {
            copiaProvinciaAlbo();
        } else if (elementClicked !== undefined && elementClicked === 'cittadinanza') {
            copiaCittadinanza();
        } else if (elementClicked !== undefined && elementClicked === 'nazionalita') {
            copiaNazionalita();
        } else if (elementClicked !== undefined && startsWith(elementClicked, 'recapiti')) {
            var counter = $(this).closest('ul').attr('class').split('recapito')[1];
            copiaRecapito(counter);
        } else {
            //Copio solo se il valore della sorgente non è vuoto
            if (value) {
                $('input[name="' + elementClicked + '"]').val(value);
            }
        }
    });
    return false;
}

//Nasconde tutti i campi all'inizializzazione della popup di confronto anagrafiche
function hideAllFields() {
    hideFields(personaFisicaFields);
    hideFields(professionistaFields);
    hideFields(personaDittaIndividuale);
    hideFields(personaGiuridicaFields);
}
//Mostra tutti i campi
function enableAllFields() {
    enableFields(personaFisicaFields);
    enableFields(professionistaFields);
    enableFields(personaDittaIndividuale);
    enableFields(personaGiuridicaFields);
}

function hideFields(fields) {
    for (var i = 0; i < fields.length; i++) {
        $('.' + fields[i]).hide();
    }
}

function enableFields(fields) {
    for (var i = 0; i < fields.length; i++) {
        $('.' + fields[i]).show();
    }
}

function resetAnagrafica() {
    $(".containerAnagrafiche input").val("");
    $(".containerAnagrafiche select").val("");
    $(".containerAnagrafiche option:selected").attr('selected', '');
    $(".containerAnagrafiche .ui-state-hover").removeClass("ui-state-hover");
    $(".containerAnagrafiche .ui-state-highlight").removeClass("ui-state-highlight");
    $(".containerAnagrafiche .ui-state-error").removeClass("ui-state-error");

    var count = $(".sorgente ul").length;
    for (var i = 1; i < count; i++) {
        if ($(".recapito" + i).length > 0) {
            $(".recapito" + i).remove();
        }
    }

}

function initializeHighlight() {

    var elementClicked;

    $(".containerAnagrafiche li").click(function() {
        $(".containerAnagrafiche .ui-state-highlight").removeClass("ui-state-highlight");
        $(".containerAnagrafiche .ui-state-hover").removeClass("ui-state-hover");
        elementClicked = $(this).attr("class").split(" ")[0];
        $(".containerAnagrafiche ." + elementClicked).addClass("ui-state-highlight");
        $(".containerAnagrafiche #spostaSingola").offset({
            top: $(this).offset().top
        });
    });

    $('#spostaSingola').click(function() {
        var valoreSorgente;
        if (elementClicked === 'comuneNascita') {
            copiaComuneDiNascita();
        } else if (elementClicked === 'desFormaGiuridica') {
            copiaFormaGiuridica();
        } else if (elementClicked === 'provinciaCciaa') {
            copiaProvinciaCciaa();
        } else if (elementClicked === 'tipoCollegio') {
            copiaTipoCollegio();
        } else if (elementClicked === 'alboProvincia') {
            copiaProvinciaAlbo();
        } else if (elementClicked === 'cittadinanza') {
            copiaCittadinanza();
        } else if (elementClicked === 'nazionalita') {
            copiaNazionalita();
        } else if (startsWith(elementClicked, 'recapiti')) {
            var split = elementClicked.split("_");
            var counter = split[2];
            if (split[1] === 'descComune') {
                copiaComuneRecapito(counter);
            } else if (split[1] === 'idTipoIndirizzo') {
                var valore = $('.sorgente select[name="recapiti[' + split[2] + '].idTipoIndirizzo"').val();
                emptySelect('.destinazione', 'recapiti[' + split[2] + '].idTipoIndirizzo');
                setValToSelect('.destinazione', 'recapiti[' + split[2] + '].idTipoIndirizzo', valore);
            } else {
                var valore = $('.sorgente input[name="recapiti[' + split[2] + '].' + split[1] + '"]').val();
                $('.destinazione input[name="recapiti[' + split[2] + '].' + split[1] + '"]').val(valore);
            }
        } else if ($('.sorgente input[name="' + elementClicked + '"]').length === 1) {
            valoreSorgente = $('.sorgente input[name="' + elementClicked + '"]').val();
            $('.destinazione input[name="' + elementClicked + '"]').val(valoreSorgente);
        } else if ($('.sorgente select[name="' + elementClicked + '"]').length === 1) {
            valoreSorgente = $('.sorgente select[name="' + elementClicked + '"]').val();
            $('.destinazione select[name="' + elementClicked + '"]').val(valoreSorgente);
        }

        $('.destinazione input[name="' + elementClicked + '"]').closest('li').removeClass("ui-state-error");
        $('.destinazione select[name="' + elementClicked + '"]').closest('li').removeClass("ui-state-error");
    });

    $('select').change(function() {
        //Copia il valore della select su qualsiasi campo con lo stesso nome di tipo select
        var nameField = $(this).attr('name');
        var value = $(this).val();
        $('.sorgente select[name="' + nameField + '"] option').removeAttr("selected");
        $('.destinazione select[name="' + nameField + '"] option').removeAttr("selected");
        $('.sorgente select[name="' + nameField + '"]').val(value);
        $('.sorgente select[name="' + nameField + '"] option[value="' + value + '"]').attr('selected', 'selected');
        $('.destinazione select[name="' + nameField + '"]').val(value);
        $('.destinazione select[name="' + nameField + '"] option[value="' + value + '"]').attr('selected', 'selected');

    });
}

//Inizializzazione componenti popup anagrafica
function initializeAutocompleteSections() {

    $(".sorgente input[name='desCittadinanza']").autocomplete({
        source: function(request, response) {
            var descrizione = $(".sorgente input[name='desCittadinanza']").val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
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
            $(".sorgente input[name='desCittadinanza']").val(ui.item.value);
            $(".sorgente input[name='idCittadinanza']").val(ui.item.id);
        }
    });

    $(".sorgente input[name='desNazionalita']").autocomplete({
        source: function(request, response) {
            var descrizione = $(".sorgente input[name='desNazionalita']").val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
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
            $(".sorgente input[name='desNazionalita']").val(ui.item.value);
            $(".sorgente input[name='idNazionalita']").val(ui.item.id);
        }
    });

    $('.sorgente input[name="comuneNascita\\.descrizione"]').autocomplete({
        source: function(request, response) {
            var descrizione = $('.sorgente input[name="comuneNascita\\.descrizione"]').val();
            var dataNascita = $('.sorgente input[name="dataNascita"]').val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
                    date: dataNascita,
                    action: 'trovaComune'
                },
                success: function(data) {
                    var result = $.map(data.rows, function(item) {
                        return {
                            label: item.descrizione + " ( " + item.provincia.codCatastale + " ) ",
                            value: item.descrizione,
                            id: item.codCatastale,
                            idComune: item.idComune,
                            statoId: item.stato.idStato,
                            statoDesc: item.stato.descrizione,
                            provinciaId: item.provincia.idProvincie,
                            provinciaDesc: item.provincia.descrizione
                        };
                    });
                    response(result);
                }
            });
        },
        mustMatch: true,
        minLength: 2,
        select: function(event, ui) {
            $('.sorgente input[name="comuneNascita\\.descrizione"]').val(ui.item.value);
            $('.sorgente input[name="comuneNascita\\.idComune"]').val(ui.item.idComune);
            $('.sorgente input[name="comuneNascita\\.codCatastale"]').val(ui.item.id);
            $('.sorgente input[name="comuneNascita\\.provincia\\.idProvincie"]').val(ui.item.provinciaId);
            $('.sorgente input[name="comuneNascita\\.provincia\\.descrizione"]').val(ui.item.provinciaDesc);
            $('.sorgente input[name="comuneNascita\\.stato\\.idStato"]').val(ui.item.statoId);
            $('.sorgente input[name="comuneNascita\\.stato\\.descrizione"]').val(ui.item.statoDesc);
        }
    });


    $(".sorgente .recapito-descComune").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: urlConfrontoAnagrafiche,
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
        select: function(event, ui) {
            var className = ".sorgente ." + $(this).parent().parent().attr("class");
            $(className + " .recapito-idComune").val(ui.item.id);
            $(className + " .recapito-idProvincia").val(ui.item.idProvincia);
            $(className + " .recapito-descProvincia").val(ui.item.descProvincia);
            $(className + " .recapito-idStato").val(ui.item.idStato);
            $(className + " .recapito-descStato").val(ui.item.descStato);
        }
    });


    $(".sorgente input[name='provinciaIscrizione\\.descrizione']").autocomplete({
        source: function(request, response) {
            var descrizione = $(".sorgente input[name='provinciaIscrizione\\.descrizione']").val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
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
            $(".sorgente input[name='provinciaIscrizione\\.descrizione']").val(ui.item.value);
            $(".sorgente input[name='provinciaIscrizione\\.idProvincie']").val(ui.item.id);
        }
    });

    $(".sorgente input[name='desFormaGiuridica']").autocomplete({
        source: function(request, response) {
            var descrizione = $(".sorgente input[name='desFormaGiuridica']").val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
                    action: "trovaFormaGiuridica"
                },
                success: function(data) {
                    if (data.rows.length === 0) {
                        var messages = [];
                        messages.push("Il valore immesso e' inesistente");
                        displayResponseDialog(messages, true);
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
            $(".sorgente input[name='desFormaGiuridica']").val(ui.item.value);
            $(".sorgente input[name='idFormaGiuridica']").val(ui.item.id);
        }
    });

    $(".sorgente input[name='provinciaCciaa\\.descrizione']").autocomplete({
        source: function(request, response) {
            var descrizione = $(".sorgente input[name='provinciaCciaa\\.descrizione']").val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
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
            $(".sorgente input[name='provinciaCciaa\\.descrizione']").val(ui.item.value);
            $(".sorgente input[name='provinciaCciaa\\.idProvincie']").val(ui.item.id);
        }
    });

    $(".sorgente input[name='desTipoCollegio']").autocomplete({
        source: function(request, response) {
            var descrizione = $(".sorgente input[name='desTipoCollegio']").val();
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: descrizione,
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
            $(".sorgente input[name='desTipoCollegio']").val(ui.item.value);
            $(".sorgente input[name='idTipoCollegio']").val(ui.item.id);
        }
    });

}

function initializeExternalIntegrations() {

    $(".registroImprese ").click(function() {
        var codiceFiscale = $('.sorgente input[name="codiceFiscale"]').val();
        //trim della stringa
        codiceFiscale = codiceFiscale.replace(/^\s + |\s + $/g, '');

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
                buttons: {
                    'Accetta valori': function() {
                        if ($('.destinazione input[name="denominazione"]').val() !== null &&
                                $.trim($('.dettaglioAziendaContainer #denominazioneRI').text()) !== "") {
                            var denominazioneRegistroImprese = $('.dettaglioAziendaContainer #denominazioneRI').text();
                            $('.destinazione input[name="denominazione"]').val(denominazioneRegistroImprese);
                        }
                        if ($('.dettaglioAziendaContainer #desCciaaRI').text() !== null
                                && $.trim($('.dettaglioAziendaContainer #desCciaaRI').text()) !== "") {
                            var descrizioneProvinciaCciaaRegistroImprese = $('.dettaglioAziendaContainer #desCciaaRI').text();
                            var idProvinciaCciaaRegistroImprese = $('.dettaglioAziendaContainer #idCciaaRI').text();
                            $('.destinazione input[name="provinciaCciaa\\.descrizione"]').val(descrizioneProvinciaCciaaRegistroImprese);
                            $('.destinazione input[name="provinciaCciaa\\.idProvincie"]').val(idProvinciaCciaaRegistroImprese);
                        }
                        var numeroReaRegistroImprese = $('.dettaglioAziendaContainer #numeroReaRI').text();
                        $('.destinazione input[name="nIscrizioneRea"]').val(numeroReaRegistroImprese);
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

    $(".ricercaAnagrafe ").click(function(e) {
        var codiceFiscale = $('.sorgente input[name="codiceFiscale"]').val();
        codiceFiscale = codiceFiscale.replace(/^\s + |\s + $/g, '');

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

//Validatore
function isAnagraficaValid() {
//    var valid = new Array();
//    var selTipoAnagrafica = $("#destinazioneIdTipoPersona").val();
//    if ($('#destinazioneIdTipoRuolo').val() === '') {
//        valid.push(errRuolo);
//    }
//    if (selTipoAnagrafica === "F") {
//        if ($(".destinazione .dettaglio input[name='nome']").val() === null
//                || $(".destinazione .dettaglio input[name='nome']").val() === "") {
//            valid.push(errNome);
//        }
//        if ($(".destinazione .dettaglio input[name='cognome']").val() === null
//                || $(".destinazione .dettaglio input[name='cognome']").val() === "") {
//            valid.push(errCognome);
//        }
//        if (!validator.alphanumeric.test($(".destinazione .dettaglio input[name='codiceFiscale']").val())) {
//            valid.push(errCodiceFiscale);
//        }
//        if (!validator.letters.test($(".destinazione .dettaglio input[name='comuneNascita\\.descrizione']").val())) {
//            valid.push(errDesComune);
//        }
//        if (!validator.numeric.test($(".destinazione .dettaglio input[name='comuneNascita\\.idComune']").val())) {
//            valid.push(errIdComune);
//        }
//        if (!validator.numeric.test($(".destinazione .dettaglio input[name='comuneNascita\\.stato\\.idStato']").val())) {
//            valid.push(errIdStato);
//        }
//        if (!validator.numeric.test($(".destinazione .dettaglio input[name='comuneNascita\\.provincia\\.idProvincie']").val())) {
//            valid.push(errIdProvincia);
//        }
//        if ($('#destinazioneIdTipoRuolo').val() === '3') {
//            //E' un tecnico professionista. Deve aver compilato i seguenti campi: 
//            var tipoCollegio = $('#destinazioneTipoCollegio').val();
//            var numeroAlbo = $('#destinazioneAlboNum').val();
//            var provinciaIscrizione = $('.destinazione input[name="provinciaIscrizione\\.idProvincie"]').val();
//            if (tipoCollegio === '' || tipoCollegio === undefined) {
//                valid.push('Non è stato valorizzato il collegio di appartenenza');
//            }
//            if (numeroAlbo === '' || numeroAlbo === undefined) {
//                valid.push('Non è stato valorizzato il numero di iscrizione all\'albo');
//            }
//            if (provinciaIscrizione === '' || provinciaIscrizione === undefined) {
//                valid.push('Non è stata indicata la provincia di iscrizione');
//            }
//        }
//        //Verifico la presenza della denominazione per la ditta individuale
//        if ($('.destinazione select[name=flgDittaIndividuale]').val() === 'S') {
//            if ($(".destinazione .dettaglio input[name='denominazione']").val() === null
//                    || $(".destinazione .dettaglio input[name='denominazione']").val() === "") {
//                valid.push(errDenominazionea);
//            }
//        }
//    }
//
//    if (selTipoAnagrafica === "G") {
//        if ($(".destinazione .dettaglio input[name='denominazione']").val() === null
//                || $(".destinazione .dettaglio input[name='denominazione']").val() === "") {
//            valid.push(errDenominazionea);
//        }
//        if (!validator.numeric.test($(".destinazione .dettaglio input[name='idFormaGiuridica']").val())) {
//            valid.push(errFormaGiuridica);
//        }
//        if (!validator.numeric.test($(".destinazione .dettaglio input[name='partitaIva']").val())) {
//            valid.push(errPartitaIva);
//        }
//        if (!validator.numeric.test($(".destinazione .dettaglio input[name='codiceFiscale']").val())) {
//            valid.push(errCodiceFiscale);
//        }
//        //Provincia obbligatoria solo se non sono in attesa di iscrizione al RI o al REA 
//        if ($(".destinazione .dettaglio select[name='flgAttesaIscrizioneRi']").val() === 'N' ||
//                $(".destinazione .dettaglio select[name='flgAttesaIscrizioneRea']").val() === 'N') {
//            if (!validator.numeric.test($(".destinazione .dettaglio input[name='provinciaCciaa\\.idProvincie']").val())) {
//                valid.push(errIdProvinciaCciaa);
//            }
//        }
//    }
//
//    if (valid.length > 0) {
//        displayResponseDialog(valid, true);
//        return false;
//    }
    return true;
}

function isValidCF() {
    var codiceFiscale = $(".destinazione input[name='codiceFiscale']").val();
    var partitaIva = $(".destinazione input[name='partitaIva']").val();
    var idAnagrafica = $(".destinazione input[name='idAnagrafica']").val();
    var tipoAnagrafica = $(".destinazione select[name='idTipoPersona']").val();
    var flgIndividuale = $(".destinazione select[name='flgIndividuale']").val();
    var check = false;
    $.ajax({
        url: urlConfrontoAnagrafiche,
        dataType: "json",
        async: false,
        data: {
            codiceFiscale: codiceFiscale,
            partitaIva: partitaIva,
            idAnagrafica: idAnagrafica,
            tipoAnagrafica: tipoAnagrafica,
            flgIndividuale: flgIndividuale,
            action: 'checkCF'
        },
        success: function(data) {
            if (!data.success) {
                var messages = [];
                messages.push(data.message);
                displayResponseDialog(messages, true);
                check = false;
            } else {
                check = true;
            }
        }
    });
    return check;
}

function isRecapitiValid() {
//    var valid = new Array();
//
//    $(".destinazione .recapiti\\.idTipoIndirizzo").each(function(index) {
//        if (!validator.numeric.test($(this).val())) {
//            var fieldContainer = $(this).closest('li');
//            $(fieldContainer).addClass("ui-state-error");
//            valid.push("");
//        }
//    });
//    if (valid.length > 0) {
//        var valid = new Array();
//        valid.push("Errore nei recapiti. Aggiornare i campi o correggerli e riprovare");
//        displayResponseDialog(valid, true);
//        return false;
//    }
    return true;
}

function isPraticaValid()
{
    var ruolianagrafiche = new Object;
    var errori = new Array();
    var elementi = $("#anagrafica_box > table  td > div");

    elementi.each(function(index) {
        var id = $(this).attr("id");
        var idruolo = $("#" + id + "  input[name='idTipoRuolo']").val();
        var codruolo = $("#" + id + "  input[name='desTipoRuolo']").val();
        var cf = $.trim($("#" + id + "  input[name='codiceFiscale']").val());
        if (ruolianagrafiche[cf + idruolo] === undefined) {
            var anagrafica = new Object();
            anagrafica.cf = cf;
            anagrafica.codruolo = codruolo;
            anagrafica.idruolo = idruolo;
            anagrafica.counter = 0;
            ruolianagrafiche[cf + idruolo] = anagrafica;
        } else {
            ruolianagrafiche[cf + idruolo].counter = ruolianagrafiche[cf + idruolo].counter + 1;
        }
    });

    var trovatoRichiedente = false;
    var trovatoBeneficiario = false;
    for (var ra in ruolianagrafiche) {
        if (ruolianagrafiche[ra].counter > 0) {
            errori.push("L'anagrafica " + ruolianagrafiche[ra].cf + " e' registrata piu di una volta con lo stesso ruolo " + ruolianagrafiche[ra].codruolo);
        }
        if (ruolianagrafiche[ra].codruolo.toUpperCase() === "RICHIEDENTE") {
            trovatoRichiedente = true;
        }
        if (ruolianagrafiche[ra].codruolo.toUpperCase() === "BENEFICIARIO") {
            trovatoBeneficiario = true;
        }
    }
    if (!trovatoRichiedente) {
        errori.push("La pratica deve contenere almeno una anagrafica di tipo Richiedente");
    }
    if (!trovatoBeneficiario) {
        errori.push("La pratica deve contenere almeno una anagrafica di tipo Beneficiario");
    }
    var protocollo = $("#protocollo").length;
    if (protocollo === 1) {
        //Ho il protocollo inserito manualmente
        var numeroProtocolloSplit = $("#protocollo").val().split("/");
        if (numeroProtocolloSplit !== undefined && numeroProtocolloSplit.length !== 3) {
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
        if ($(datiCatastali[i]).val() === 'false') {
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
        if ($(datiCatastali[i]).val() === 'false') {
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
        if ($(datiCatastali[i]).val() === 'false') {
            errCatastali = true;
            break;
        }
    }
    if (errCatastali) {
        errori.push("Confermare tutti gli indirizzi dell'intervento");
    }
    if (errori.length > 0) {
        return errori;
    }
    return null;
}

//Altre funzioni

function startsWith(string, value) {
    return string.indexOf(value) === 0;
}

function displayResponseDialog(messages, error) {
    var message = "";
    for (var msg in messages) {
        message += messages[msg] + "<br/>\n";
    }
    var dialog = $("<div>");
    dialog.html(message);
    var reloadPage = true;
    if (error) {
        dialog.addClass('messaggioErrore');
        dialog.parent().addClass("ui-state-error ui-corner-all");
        reloadPage = false;
    }

    dialog.dialog({
        modal: true,
        width: 400,
        buttons: {
            Ok: function() {
                $(this).dialog('close');
                if (reloadPage) {
                    var url = path + '/pratiche/nuove/apertura/dettaglio.htm?idPratica=' + idPratica + '&currentTab=frame1';
                    window.open(url, '_self');
//                    updateParamAndReload("currentTab", "frame1");
                }
            }
        }
    });
}
