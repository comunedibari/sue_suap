//Campi da abilitare per ogni tipo di persona
var personaFisicaFields = ['idTipoPersona', 'idTipoRuolo', 'flgIndividuale', 'codiceFiscale', 'nome', 'cognome', 'sesso', 'cittadinanza', 'nazionalita',
    'dataNascita', 'comuneNascita', 'comuneNascitaStato', 'comuneNascitaProvincia', 'localitaNascita', 'impostaDittaIndividuale', 'professionistaSection'];
var personaDittaIndividuale = ['partitaIva', 'denominazione', 'provinciaCciaa', 'flgAttesaIscrizioneRea', 'nIscrizioneRea', 'dataIscrizioneRea',
    'flgAttesaIscrizioneRi', 'nIscrizioneRi', 'dataIscrizioneRi', 'buttonRicercaRi'];
var personaGiuridicaFields = ['idTipoPersona', 'idTipoRuolo', 'codiceFiscale', 'partitaIva', 'denominazione', 'desFormaGiuridica', 'provinciaCciaa',
    'flgAttesaIscrizioneRea', 'nIscrizioneRea', 'dataIscrizioneRea', 'flgAttesaIscrizioneRi', 'nIscrizioneRi', 'dataIscrizioneRi', 'buttonRicercaRi'];
var professionistaFields = ['partitaIva', 'tipoCollegio', 'numeroIscrizione', 'dataIscrizione', 'alboProvincia', 'professionistaSection'];
var datiRea = ['nIscrizioneRea', 'dataIscrizioneRea'];
var datiRi = ['nIscrizioneRi', 'dataIscrizioneRi'];

$(function() {
    initializeAutocompleteFields();

    hideFields(personaFisicaFields);
    hideFields(personaDittaIndividuale);
    hideFields(personaGiuridicaFields);
    hideFields(professionistaFields);
    hideFields(['recapitiSection']);
    
    var varianteAnagrafica = $('#varianteAnagrafica').val();

    $('#tipoAnagrafica').change(function() {
        abilitaAnagrafiche($(this).val());
        clearForm();
    });

    $("#dittaIndividuale").click(function() {
        if (this.checked) {
            hideFields(professionistaFields);
            enableFields(personaDittaIndividuale);
            $('#varianteAnagrafica').val('I');
            $('#flgIndividuale').val('S');
            $('.partitaIvaMandatoryField').show();
        } else {
            hideFields(personaDittaIndividuale);
            enableFields(professionistaFields);
            $('#varianteAnagrafica').val('F');
            $('#flgIndividuale').val('N');
            $('.partitaIvaMandatoryField').hide();
        }
    });

    $('#flgAttesaIscrizioneRi').click(function() {
        if (this.checked) {
            hideFields(datiRi);
        } else {
            enableFields(datiRi);
        }
    });

    $('#flgAttesaIscrizioneRea').click(function() {
        if (this.checked) {
            hideFields(datiRea);
        } else {
            enableFields(datiRea);
        }
    });

    $.datepicker.setDefaults($.datepicker.regional["it"]);
    $.datepicker.setDefaults({
        dateFormat: 'dd/mm/yy',
        changeMonth: true,
        changeYear: true,
        yearRange: "-120:+0"
    });
    $(".date").datepicker();

    if ($('#tipoAnagrafica').val() !== undefined && $('#tipoAnagrafica').val() !== '') {
        $('#tipoAnagrafica').trigger('change');
    }

    if ('I' === varianteAnagrafica) {
        $('#dittaIndividuale').trigger('click');
    }

    if ('checked' === $('#flgAttesaIscrizioneRea').attr('checked')) {
        $('#flgAttesaIscrizioneRea').trigger('change');
        hideFields(datiRea);
    }

    if ('checked' === $('#flgAttesaIscrizioneRi').attr('checked')) {
        $('#flgAttesaIscrizioneRi').trigger('change');
        hideFields(datiRi);
    }
});


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

function abilitaAnagrafiche(anagraficaToDisplay) {
    if (anagraficaToDisplay === 'G') {
        hideFields(personaFisicaFields);
        hideFields(personaDittaIndividuale);
        hideFields(professionistaFields);
        enableFields(personaGiuridicaFields);
        $('#idTipoIndirizzo').val(3);
        $('#descTipoIndirizzo').val('SEDE');
        $('#titoloRecapiti').html('SEDE');
        $('#varianteAnagrafica').val('G');
        enableFields(['recapitiSection']);
        $('.partitaIvaMandatoryField').show();
    } else if (anagraficaToDisplay === 'F') {
        hideFields(personaDittaIndividuale);
        hideFields(personaGiuridicaFields);
        enableFields(personaFisicaFields);
        enableFields(professionistaFields);
        $('#idTipoIndirizzo').val(1);
        $('#descTipoIndirizzo').val('RESIDENZA');
        $("#titoloRecapiti").html('RESIDENZA');
        enableFields(['recapitiSection']);
        $('#varianteAnagrafica').val('F');
        $('.partitaIvaMandatoryField').hide();
    } else {
        hideFields(personaFisicaFields);
        hideFields(personaDittaIndividuale);
        hideFields(personaGiuridicaFields);
        hideFields(professionistaFields);
        hideFields(['recapitiSection']);
        $('#varianteAnagrafica').val('');
        $('.partitaIvaMandatoryField').hide();
    }
}

function clearForm() {
    var tipoAnagrafica = $('#tipoAnagrafica').val();
    $('#anagraficaContent').closest('form')[0].reset();
    $('#tipoAnagrafica').val(tipoAnagrafica);
}

function initializeAutocompleteFields() {
    $('#desFormaGiuridica').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/formagiuridica.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $("#desFormaGiuridica").val()
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            id: item.idFormaGiuridica
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#idFormaGiuridica').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desCittadinanza').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/cittadinanza.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $("#desCittadinanza").val()
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            id: item.idCittadinanza
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#idCittadinanza').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desNazionalita').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/nazionalita.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $("#desNazionalita").val()
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            id: item.idNazionalita
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#idNazionalita').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desProvinciaIscrizioneRi').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/province.htm",
                dataType: "json",
                data: {
                    description: $("#desProvinciaIscrizioneRi").val(),
                    flgInfocamere: 'S'
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            codCatastale: item.codCatastale,
                            id: item.idProvincie
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#nProvinciaIscrizioneRi').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desTipoCollegio').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/tipocollegio.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $("#desTipoCollegio").val()
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            id: item.idTipoCollegio
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#codTipoCollegio').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desProvinciaIscrizione').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/province.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $("#desProvinciaIscrizione").val(),
                    flgInfocamere: 'N'
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione,
                            value: item.descrizione,
                            codCatastale: item.codCatastale,
                            id: item.idProvincie
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#nProvinciaIscrizione').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desComuneNascita').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/comune.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $(this.element).val(),
                    dataValidita: ''
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                            value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                            id: item.idComune
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#nComuneNascita').val(ui.item.id);
        },
        minLength: 2
    });

    $('#desComuneRecapito').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: path + "/search/comune.htm",
                dataType: "json",
                cache: false,
                data: {
                    description: $(this.element).val(),
                    dataValidita: ''
                },
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                            value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                            id: item.idComune
                        };
                    }));
                }
            });
        },
        select: function(event, ui) {
            $('#nComuneRecapito').val(ui.item.id);
        },
        minLength: 2
    });
}