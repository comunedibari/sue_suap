
$.fn.editable.defaults.emptytext = '[Nessun valore]';

function updateParam(location, key, value)
{
    key = encodeURI(key);
    value = encodeURI(value);

    var kvp = location.substr(1).split('&');

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
    return kvp.join('&');
}



function item_animate(conto, id, px) {

    var massimo = (conto - 1) * 277;
    var ultimo = 0 - ((conto - 2) * 277);
    var minimo = 0 - ((conto - 1) * 277);
    var attuale = parseInt($(id).css("marginLeft"));
    if (attuale === minimo) {
        $(id).animate({'marginLeft': 0});
    }
    else {
        if (attuale === massimo) {
            $(id).animate({'marginLeft': ultimo});
        } else {
            $(id).animate({'marginLeft': px});
        }
    }
}

function eventEliminaAnagrafica() {
    $(".elimina").unbind();
    $(".elimina").click(function() {
        var counter = $(this).find("input.counter").val();
        var iddiv = "";
//        if (graficaNuova())
//        {
        //^^CS siamo nella nuova grafica
        iddiv = $("#anagrafica_box #anagrafica" + counter);
//        }
//        else
//        {
//            //^^CS siamo nella vecchia grafica
//            iddiv = $(this).parent();
//        }
        var form = $("<form>").append($(this).find("input").clone());
        form.attr({
            name: 'anagrafica',
            method: 'post',
            action: urlConfrontoAnagrafiche
        });
        form.append($('<input>',
                {
                    'name': 'action',
                    'value': 'eliminaAnagraficaInXML',
                    'type': 'hidden'
                }));
        form.append($('<input>',
                {
                    'name': 'idPratica',
                    'value': idPratica,
                    'type': 'hidden'
                }));
        var div = $("<div>");
        div.html("Confermi di voler scollegare l'anagrafica? Se l'anagrafica non è stata mai confermata verrà perduta.");
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma eliminazione anagrafica",
                    buttons: {
                        Ok: function() {
                            $.post(urlConfrontoAnagrafiche, form.serialize(), function(data) {
                                $(div).dialog('close');
                                if (data.errors !== null) {
                                    displayResponseDialog(data.message, true);
                                } else {
                                    $(iddiv).remove();
                                }
                                //location.reload();
                                var frameAnagrafica = "frame1";
                                var paramurl = updateParam(document.location.search, "currentTab", "frame1");
                                paramurl = updateParam(paramurl, "a", "E");
                                document.location.search = paramurl;
                            }, 'json');
                        },
                        Annulla: function() {
                            $(this).dialog('close');
                        }
                    }
                });
    });
}



function deleteProcedimento(event) {
    var idEndoprocedimentoEnteToUnlink = $(event.target).attr('data-holder');
    var numeroEndoprocedimenti = $('#procedimenti_box td').length;
    if (numeroEndoprocedimenti > 1) {
        $('<div></div>').appendTo('body')
                .html('<div><h6 class="confirm-body-content">Sei sicuro di voler scollegare il procedimento?</h6></div>')
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
                                type: 'POST',
                                url: path + '/pratica/endoprocedimenti/gestisci.htm',
                                data: {
                                    idProcedimentoEnte: idEndoprocedimentoEnteToUnlink,
                                    idPratica: idPratica,
                                    operation: "DELETE"
                                },
                                success: function(data) {
                                    var esito = data.success;
                                    var messaggio = data.msg;
                                    if (esito === 'true') {
                                        $("#endoprocedimento_" + idEndoprocedimentoEnteToUnlink).remove();
                                        mostraMessaggioAjax(messaggio, "success");
                                    } else {
                                        mostraMessaggioAjax(messaggio, "error");
                                    }
                                },
                                dataType: 'json'
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
    } else {
        mostraMessaggioAjax("Impossibile cancellare. Deve essere presente almeno un endoprocedimento", "error");
    }
}

function setButton() {
    $(".showdetail").each(function() {
        $(this).click(function() {
            $("#" + $(this).attr("id") + "button").trigger("click");
        });
    });
}
setButton();


$(function() {
    var procedimentoTemplateHtml = $("#template-procedimento").html();
    var procedimentoTemplate = Handlebars.compile(procedimentoTemplateHtml);
    if (isCoverEditable === false) {
        $('.x-editable-text').each(function(i, obj) {
            $(this).attr("class", "");
        });
    }
    $('.x-editable-text').editable({
        type: 'text',
        url: path + '/pratica/attributi/aggiorna.htm',
        pk: 'pratica',
        params: function(params) {
            var data = {};
            data['idPratica'] = idPratica;
            data['name'] = params.name;
            data['value'] = params.value;
            data['pk'] = params.pk;
            return data;
        },
        mode: 'inline',
        success: function(response, newValue) {
            if (response.success === "false") {
                return response.msg;
            }
        }
    });
    if (isCoverEditable === true) {
        $('#pratica_stato').editable({
            source: statiPraticaList,
            url: path + '/pratica/attributi/aggiorna.htm',
            pk: 'pratica',
            params: function(params) {
                var data = {};
                data['idPratica'] = idPratica;
                data['name'] = params.name;
                data['value'] = params.value;
                data['pk'] = params.pk;
                return data;
            },
            mode: 'inline',
            success: function(response, newValue) {
                if (response.success === "false") {
                    return response.msg;
                }
                if (response.bloccaChiusura === "true") {
                    $("#pratica_chiusura_data_container").hide();
                } else {
                    $("#pratica_chiusura_data_container").show();
                }
            }
        });
    }
    $('#pratica_protocollo_segnatura').on('shown', function(e, editable) {
        $(".pratica_protocollo_segnatura_container input").maskfield('GPPPP/0000/099999999', {translation: {'P': {pattern: /[a-zA-Z]/, optional: true}, 'G': {pattern: /[a-zA-Z]/}}});
    });

    $("#endoprocedimentiSelezionati").chosen({allow_single_deselect: true, width: "498px"});
    $('#add_procedimenti_btn').on('click', function() {
        $('.collega-procedimento').dialog({
            title: collegaProcedimentoDialogTitle,
            modal: true,
            resizable: false,
            width: 500,
            dialogClass: 'collega-procedimento-dialog',
            buttons: {
                'Ok': function() {
                    var idEndoprocedimentoEnteToAdd = $('#endoprocedimentiSelezionati').val();

                    if (idEndoprocedimentoEnteToAdd && !($("#endoprocedimento_" + idEndoprocedimentoEnteToAdd).length)) {

                        $.ajax({
                            type: 'POST',
                            url: path + '/pratica/endoprocedimenti/gestisci.htm',
                            data: {
                                idProcedimentoEnte: idEndoprocedimentoEnteToAdd,
                                idPratica: idPratica,
                                operation: "ADD"
                            },
                            success: function(data) {
                                var esito = data.success;
                                var messaggio = data.msg;
                                if (esito === 'true') {

                                    var procedimentoNewBox = procedimentoTemplate({
                                        idProcedimentoEnte: idEndoprocedimentoEnteToAdd,
                                        titoloProcedimento: data.procedimento,
                                        terminiProcedimento: data.termini,
                                        enteProcedimento: data.ente
                                    });

                                    $('#procedimenti_box table tr').append(procedimentoNewBox);

                                    $("#endoprocedimento_" + idEndoprocedimentoEnteToAdd + " .box-unlink").on('click', deleteProcedimento);

                                    $('#endoprocedimentiSelezionati').val('');
                                    $('#endoprocedimentiSelezionati').trigger("chosen:updated");
                                    mostraMessaggioAjax(messaggio, "success");
                                } else {
                                    mostraMessaggioAjax(messaggio, "error");
                                }
                            },
                            dataType: 'json'
                        });

                    } else {
                        mostraMessaggioAjax("Impossibile aggiungere un endoprocedimento già presente", "warning");
                    }

                    $(this).dialog('close');
                },
                'Annulla': function() {
                    $(this).dialog('close');
                }
            }
        });


    });
    $('.box-unlink').on('click', deleteProcedimento);
});