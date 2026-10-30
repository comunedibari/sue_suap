function confrontaAnagraficaReady(){
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

    $(".anagrafica_confronta,.anagrafica_confronta_2").on("click", function() {
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
}