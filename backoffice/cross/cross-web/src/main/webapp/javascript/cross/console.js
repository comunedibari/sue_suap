function poll() {
    setTimeout(function() {
        $('#mail').trigger('reloadGrid');
    }, 30000);
}

function showManualMailDialog(emailId, taskId) {
    $('body').addClass('stop-scrolling');
    var parameters = {};
    parameters.mailId = emailId;
    parameters.taskId = taskId;
    $.ajax({
        type: 'POST',
        url: path + '/console/mail/detail.htm',
        data: parameters,
        success: function(data) {
            if (data.idEmail !== undefined && data.idEmail !== null && data.idEmail !== '') {
                $('input[name="destinatario"]').val(data.emailDestinatario);
                $('input[name="mailId"]').val(data.idEmail);
                $('input[name="taskId"]').val(taskId);
                $('input[name="oggettoEmail"]').val(data.oggettoEmail);
                $('#corpoEmail').html(data.corpoEmail);
                $('#task_mail_manual_management').dialog("open");
            } else {
                var dialog = $("<div />").text(data.message);
                $(dialog).dialog({
                    modal: true,
                    title: "Esito operazione",
                    width: 350,
                    height: 300,
                    closeOnEscape: true,
                    buttons: {
                        Ok: function() {
                            $(dialog).dialog('close');
                        }
                    }
                });
            }
        },
        dataType: 'json'
    });
}

function markAsReceived(mailId, taskId) {
    var div = $('<div id="markAsReceivedDialog">Stai per marcare l&apos;email come correttamente ricevuta. Attenzione, l&apos;azione non &egrave; reversibile e marcher&agrave; l&apos;email come correttamente ricevuta, ignorando qualsiasi errore rilevato. Vuoi Procedere?</div>');
    $('body').addClass('stop-scrolling');
    $(div).dialog({
        modal: true,
        width: 1000,
        height: 300,
        title: 'Marca email come ricevuta',
        closeOnEscape: true,
        buttons: {
            Ok: function(event, ui) {
                var parameters = {};
                parameters.mailId = mailId;
                parameters.taskId = taskId;
                $.ajax({
                    type: 'POST',
                    url: path + '/console/mail/markAsReceived.htm',
                    data: parameters,
                    success: function(data) {
                        var esito = data.success;
                        var messaggio = data.message;
                        if (String(esito) === 'true') {
                            mostraMessaggioAjax(messaggio, "success");
                        } else {
                            mostraMessaggioAjax(messaggio, "error");
                        }
                        $("#mail").trigger("reloadGrid");
                    },
                    dataType: 'json'
                });
                $(this).dialog('close');
            },
            Annulla: function() {
                $(this).dialog("close");
            }
        }
    });
}

function inviaEmailConNuoviParametri(mailId, taskId, destinatario) {
    var parameters = {};
    parameters.mailId = mailId;
    parameters.taskId = taskId;
    parameters.destinatario = destinatario;
    $.ajax({
        type: 'POST',
        url: path + '/console/mail/resend.htm',
        data: parameters,
        success: function(data) {
            var esito = data.success;
            var messaggio = data.message;
            if (String(esito) === 'true') {
                mostraMessaggioAjax(messaggio, "success");
            } else {
                mostraMessaggioAjax(messaggio, "error");
            }
            $("#mail").trigger("reloadGrid");
        },
        dataType: 'json'
    });

}

function showError(mailId) {
    var div = $('<div><div id="errorInternalDialog"></div></div>');
    $('body').addClass('stop-scrolling');
    $(div).dialog({
        modal: true,
        width: 1000,
        height: 300,
        title: 'Dettaglio errore',
        closeOnEscape: true,
        open: function(event, ui) {
            var parameters = {};
            parameters.mailId = mailId;
            $.ajax({
                type: 'POST',
                url: path + '/console/mail/error.htm',
                data: parameters,
                success: function(data) {
                    var messaggio = data.message;
                    $('#errorInternalDialog').text(messaggio);
                },
                dataType: 'json'
            });
        },
        close: function() {
            div.remove();
            $('body').css('overflow', 'auto');
        }
    });
}

function invioMassivoEmailDialog(wHeight) {
    $('#windowInserisciIdMail').dialog({
        title: 'Lista mail da inviare',
        modal: true,
        height: wHeight,
        width: '50%',
        close: function() {
            $(this).html('');
        },
        buttons: {
            'Ok': function() {
                var lista_id_mail = $('#lista_id_mail').val();

                var parameters = {};

                var error = false;

                if ($.isBlank(lista_id_mail)) {
                    $(".formHint.lista_id_mail").addClass('errorHint');
                    error = true;
                } else {
                    parameters.lista_id_mail = lista_id_mail;
                    $(".formHint.lista_id_mail").removeClass('errorHint');
                }

                if (error) {
                    return;
                }

                $.ajax({
                    type: 'POST',
                    url: path + '/console/mail/invioMailMassiva.htm',
                    data: parameters,
                    success: function(data) {
                        var esito = data.success;
                        var messaggio = data.msg;
                        if (String(esito) === 'true') {
                            mostraMessaggioAjax(messaggio, "success");
                        } else {
                            mostraMessaggioAjax(messaggio, "error");
                        }
                        $("#mail").trigger("reloadGrid");
                    },
                    dataType: 'json'
                });

                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            },
            'Annulla': function() {
                $(this).dialog("close");
            }
        }
    });
}

function escapeHtml(string) {
    var entityMap = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': '&quot;',
        "'": '&#39;',
        "/": '&#x2F;'
    };
    return String(string).replace(/[&<>"'\/]/g, function(s) {
        return entityMap[s];
    });
}

$(function() {

    $("#invioMailMassiveButton")
            .button()
            .click(function(event) {
                event.preventDefault();
                var wHeight = $(window).height() * 0.8;
                invioMassivoEmailDialog(wHeight);
            });

    $("#mail").jqGrid({
        url: path + '/console/mail/ajax.htm',
        datatype: "json",
        colNames: ['Id email', 'Pratica', 'Evento', 'Corpo email', 'Data inserimento', 'Destinatario', 'Oggetto', 'Errore', 'Stato', 'Azione'],
        colModel: [
            {name: 'idEmail', index: 'idEmail', width: 50},
            {name: 'oggettoPratica', index: 'oggettoPratica', sortable: false,
                formatter: function(cellvalue, options, rowObject) {
                    var idPratica = rowObject["idPratica"];
                    var oggettoPratica = rowObject["oggettoPratica"];
                    return '<a href="' + path + '/pratiche/dettaglio.htm?id_pratica=' + idPratica + '">' + oggettoPratica + '</a>';
                }},
            {name: 'descrizioneEvento', index: 'descrizioneEvento', sortable: false,
                formatter: function(cellvalue, options, rowObject) {
                    var idEvento = rowObject["idEvento"];
                    var descrizioneEvento = rowObject["descrizioneEvento"];
                    return '<a href="' + path + '/console/mail/event.htm?evento=' + idEvento + '">' + descrizioneEvento + '</a>';
                }},
            {name: 'corpoEmail', index: 'corpoEmail'},
            {name: 'dataInserimento', index: 'dataInserimento'},
            {name: 'emailDestinatario', index: 'emailDestinatario'},
            {name: 'oggettoEmail', index: 'oggettoEmail'},
            {name: 'corpoRisposta', index: 'corpoRisposta', hidden: true},
            {name: 'statoDescrizione', index: 'statoDescrizione', sortable: false,
                formatter: function(cellvalue, options, rowObject) {
                    var risposta = rowObject['corpoRisposta'];
                    if (risposta !== '' && risposta !== undefined && risposta !== null) {
                        var detail = escapeHtml(risposta);
                        var titoloErrore = rowObject['oggettoRisposta'] !== undefined && rowObject['oggettoRisposta'] !== '' ? rowObject['oggettoRisposta'] : rowObject['corpoRisposta'];
                        var dettaglio = '<span title="' + detail + '">' + titoloErrore + '</span>';
                        return dettaglio;
                    } else {
                        return cellvalue;
                    }
                }},
            {name: 'azione', index: 'azione', sortable: false,
                formatter: function(cellvalue, options, rowObject) {
                    var taskId = rowObject["taskId"];
                    taskId = taskId !== undefined && taskId !== null ? taskId : '';
                    var link = '<div class="gridActionContainer">' +
                            '<a onclick="showError(\'' + rowObject["idEmail"] + '\')" href="#"><img src="' + path + '/themes/default/css/images/eye.png" alt="Visualizza errore" title="Visualizza errore"></a>' +
                            '<a onclick="showManualMailDialog(\'' + rowObject["idEmail"] + '\', \''+ taskId +'\')" href="#"><img src="' + path + '/themes/default/css/images/pencil.png" alt="Riprova invio della email" title="Riprova invio della email"></a>' +
                            '<a onclick="markAsReceived(\'' + rowObject["idEmail"] + '\', \'' + taskId +'\')" href="#"><img src="' + path + '/themes/default/css/images/basket-grey.png" title="Marca la mail come correttamente consegnata" alt="Marca la mail come correttamente consegnata"></a>' +
                            '</div>';
                    //'<input class="button ui-state-default ui-corner-all" type="button" value="Rispedisci email" onclick="showManualMailDialog(' + rowObject["idEmail"] + ');"  /></div>';
                    return link;
                }}
        ],
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: '#pagerMail',
        sortname: 'idEmail',
        viewrecords: true,
        jsonReader: {repeatitems: false},
        sortorder: "desc",
        caption: "Email non inviate",
        height: 'auto',
        width: $('.tableContainer').width()
    });
    $("#mail").jqGrid('navGrid', '#pagerMail', {
        add: false,
        edit: false,
        del: false,
        search: false,
        refresh: true
    });
    poll();


    $('#task_mail_manual_management').dialog({
        autoOpen: false,
        height: 300,
        width: 650,
        modal: true,
        buttons: {
            Ok: function() {
                var destinatario = $('input[name="destinatario"]').val();
                var mailId = $('input[name="mailId"]').val();
                var taskId = $('input[name="taskId"]').val();
                inviaEmailConNuoviParametri(mailId, taskId, destinatario);
                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            },
            Annulla: function() {
                $('#task_mail_manual_form')[0].reset();
                $('#corpoEmail').text("");
                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            }
        },
        close: function() {
            $('#task_mail_manual_form')[0].reset();
            $('#corpoEmail').text("");
            $('body').removeClass('stop-scrolling');
            $(this).dialog('close');
        }
    });

    $('#protocollo_automatico').on('change', function() {
        if (String(this.value) === 'false') {
            $($('#protocollo_segnatura').parent()).removeClass('hidden');
            $($('#protocollo_data').parent()).removeClass('hidden');
        } else {
            $($('#protocollo_segnatura').parent()).addClass('hidden');
            $($('#protocollo_data').parent()).addClass('hidden');
        }
    });

    $("#protocollo_segnatura").maskfield('GPPPP/0000/099999999', {translation: {'P': {pattern: /[a-zA-Z]/, optional: true}, 'G': {pattern: /[a-zA-Z]/}}});
    $("#protocollo_data").datepicker({
        dateFormat: 'dd/mm/yy'
    });

});
