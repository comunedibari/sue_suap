$(function() {
    initializeAutocompleteFields();
    enableFlagReaRi();
    enableGestioneRecapiti();

    $(".date").datepicker();
    $('#cambiaAnagrafica').click(function() {
        var nuovaVariante = $('#nuovaVariante').val();
        var idAnagrafica = $('input[name="idAnagrafica"]').val();
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: path + '/gestione/anagrafiche/aggiornaVariante.htm',
            data: {
                idAnagrafica: idAnagrafica,
                nuovaVariante: nuovaVariante
            }
        }).done(function(data, textStatus) {
            var dialog = $('<div>', {
                title: 'Operazione eseguita correttamente',
                text: data
            }).appendTo('body');
            dialog.dialog({
                resizable: false,
                modal: true,
                //ho modificato la finestra di dialogo in modo che il submit si verifichi in ogni caso in modo che la pagina venga refreshata
                // subito sia che si chiuda cojn il bottone ok che nel caso in cui si chiuda con il bottone X ho quindi eliminato il submit quando si preme ok.
                close: function() {
                    var form = $('<form action="' + path + '/gestione/anagrafiche/modifica.htm" method="post">' +
                            '<input type="hidden" name="idAnagrafica" value="'+idAnagrafica+'" />' +
                            '</form>');
                    $('body').append(form);
                    $(form).submit();
                },
                buttons: {
                    Ok: function() {
                        $(this).dialog("close");
                    }
                }
            });
        }).fail(function(jqXHR, textStatus, errorThrown) {
            var dialog = $('<div>', {
                title: 'Si è verificato un errore',
                text: textStatus
            }).appendTo('body');
            dialog.dialog({
                resizable: false,
                modal: true,
                close: function() {
                    var form = $('<form action="' + path + '/gestione/anagrafiche/modifica.htm" method="post">' +
                            '<input type="hidden" name="idAnagrafica" value="'+idAnagrafica+'" />' +
                            '</form>');
                    $('body').append(form);
                    $(form).submit();
                },
                buttons: {
                    Ok: function() {
                        $(this).dialog("close");
                    }
                }
            });
        });
    });


    if ($('#flgAttesaIscrizioneRea').attr("checked") === null || $('#flgAttesaIscrizioneRea').attr("checked") === undefined) {
        $("#iscrizioneReaDiv").show();
        $("#iscrizioneReaDiv input").removeAttr('disabled');
    }

    if ($('#flgAttesaIscrizioneRi').attr("checked") === null || $('#flgAttesaIscrizioneRi').attr("checked") === undefined) {
        $("#iscrizioneRegistroImpreseDiv").show();
        $("#iscrizioneRegistroImpreseDiv input").removeAttr('disabled');
    }

    if ('I' === $('#varianteAnagrafica').val()) {
        $('#flgIndividuale').val('S');
    }
});

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

    $('.desComuneRecapito').autocomplete({
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
            $(this).siblings('.nComuneRecapito').val(ui.item.id);
        },
        minLength: 2
    });
}

function enableFlagReaRi() {
    $("#flgAttesaIscrizioneRea").click(function() {
        if ($(this).attr("checked") === null || $(this).attr("checked") === undefined) {
            $("#flgAttesaIscrizioneRea").attr('checked', 'checked');
            $("#iscrizioneReaDiv").hide();
            $("#iscrizioneReaDiv input").attr('disabled', 'disabled');
        } else {
            $("#flgAttesaIscrizioneRea").removeAttr('checked');
            $("#iscrizioneReaDiv").show();
            $("#iscrizioneReaDiv input").removeAttr('disabled');
        }
    });

    $("#flgAttesaIscrizioneRi").click(function() {
        if ($(this).attr("checked") === null || $(this).attr("checked") === undefined) {
            $("#flgAttesaIscrizioneRi").attr('checked', 'checked');
            $("#iscrizioneRegistroImpreseDiv").hide();
            $("#iscrizioneRegistroImpreseDiv input").attr('disabled', 'disabled');
        } else {
            $("#flgAttesaIscrizioneRi").removeAttr('checked');
            $("#iscrizioneRegistroImpreseDiv").show();
            $("#iscrizioneRegistroImpreseDiv input").removeAttr('disabled');
        }
    });
}

function enableGestioneRecapiti() {
    $(".aggiungiRecapito").click(function() {
        var recapito;
        var provarecapito = $("#recapitiSection").size();
        if (provarecapito > 0) {
            recapito = $("#recapitiSection").clone().addClass("inlineLabels nuovoRecapito");
        } else {
            recapito = $("#recapitiSection").clone().addClass("inlineLabels nuovoRecapito");
        }
        recapito.dialog({
            modal: true,
            width: 1024,
            windowY: $(window).height(),
            //MODIFICATO DA GAB MON
            title: "Nuovo recapito",
            dialogClass: "uniForm inlineLabels comunicazione",
            close: function() {
                $(".nuovoRecapito").remove();
            },
            buttons: {
                Ok: function() {
                    var form;
                    form = $("<form>").append($(".nuovoRecapito").clone());
                    form.attr({
                        name: 'recapito',
                        method: 'post',
                        action: path + "/gestione/anagrafiche/modifica/ajax.htm"
                    });
                    form.append($('<input>', {
                        'name': 'action',
                        'value': 'salvaRecapito',
                        'type': 'hidden'
                    }));
                    form.append($('<input>', {
                        'name': 'idAnagrafica',
                        'value': $("#idAnagrafica").val(),
                        'type': 'hidden'
                    }));
                    $.post(path + "/gestione/anagrafiche/modifica/ajax.htm", form.serialize(),
                            function(data) {
                                if (data.errors !== undefined) {
                                    alert(data.errors.join("\n"));
                                    return false;
                                } else {
                                    var idAnagrafica = $("#idAnagrafica").val();
                                    var form = $('<form action="' + path + '/gestione/anagrafiche/modifica.htm" method="post">' +
                                            '<input type="hidden" name="idAnagrafica" value="' + idAnagrafica + '" />' +
                                            '</form>');
                                    $(form).submit();
                                }
                            }, 'json');
                    //AGGIUNTO DA GAB MON 
                    return false;
                },
                Annulla: function() {
                    $(this).dialog('close');
                }
            }
        });

        $(".nuovoRecapito input").each(function(index) {
            $(this).val("");
        });

        var idTipo = $(this).attr("id");
        var descTipo = $(this).attr("value");
        $(".nuovoRecapito #titoloRecapiti").html(descTipo);
        $(".nuovoRecapito .idTipoIndirizzo ").val(idTipo);
        $(".nuovoRecapito .descTipoIndirizzo ").val(descTipo);
        $('.nuovoRecapito #desComuneRecapito').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: path + "/search/comune.htm",
                    dataType: "json",
                    cache: false,
                    data: {
                        description: $(".nuovoRecapito #desComuneRecapito").val(),
                        dataValidita: ''
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                value: item.descrizione,
                                id: item.idComune
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $(this).siblings('.nComuneRecapito').val(ui.item.id);
            },
            minLength: 2
        });
    });

    $(".eliminaRecapito").click(function() {
        var idTipoRecapito = $(this).attr("name");
        $.ajax({
            url: path + "/gestione/anagrafiche/modifica/ajax.htm",
            dataType: "json",
            cache: false,
            data: {
                action: "eliminaRecapito",
                idTipoRecapito: idTipoRecapito,
                idAnagrafica: $("#idAnagrafica").val()
            },
            success: function(data) {
                if (data.errors !== undefined) {
                    alert(data.errors.join("\n"));
                } else {
                    var idAnagrafica = $("#idAnagrafica").val();
                    var form = $('<form action="' + path + '/gestione/anagrafiche/modifica.htm" method="post">' +
                            '<input type="hidden" name="idAnagrafica" value="' + idAnagrafica + '" />' +
                            '</form>');
                    $(form).submit();
                }
            }});
    });
}