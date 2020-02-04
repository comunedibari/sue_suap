function showManualProtocolDialog(taskId) {
    $('body').addClass('stop-scrolling');

    $('.task_protocol_manual_management').dialog({
        title: "Protocollo Task Manuale",
        modal: true,
        resizable: false,
        width: 600,
        dialogClass: 'collega-procedimento-dialog',
        buttons: {
            'Ok': function() {
                var protocolloAutomatico = $('#protocollo_automatico').val();
                var protocolloSegnatura = $('#protocollo_segnatura').val();
                var protocolloData = $('#protocollo_data').val();

                var parameters = {};
                parameters.taskId = taskId;
                parameters.protocolloAutomatico = protocolloAutomatico;

                if (String(protocolloAutomatico) === 'false') {
                    var error = false;

                    if ($.isBlank(protocolloSegnatura)) {
                        $(".formHint.protocolloSegnatura").addClass('errorHint');
                        error = true;
                    } else {
                        parameters.protocolloSegnatura = protocolloSegnatura;
                        $(".formHint.protocolloSegnatura").removeClass('errorHint');
                    }
                    if ($.isBlank(protocolloData)) {
                        $(".formHint.protocolloData").addClass('errorHint');
                        error = true;
                    } else {
                        parameters.protocolloData = protocolloData;
                        $(".formHint.protocolloData").removeClass('errorHint');
                    }

                    if (error) {
                        return;
                    }
                }

                $.ajax({
                    type: 'POST',
                    url: path + '/processi/processo/task/completeProtocol.htm',
                    data: parameters,
                    success: function(data) {
                        var esito = data.success;
                        var messaggio = data.msg;
                        if (String(esito) === 'true') {
                            mostraMessaggioAjax(messaggio, "success");
                        } else {
                            mostraMessaggioAjax(messaggio, "error");
                        }
                    },
                    dataType: 'json'
                });

                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            },
            'Annulla': function() {
                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            }
        }
    });
}

function showManualMailDialog(taskId) {
    $('body').addClass('stop-scrolling');

    $('.task_mail_manual_management').dialog({
        title: "Mail Task Manuale",
        modal: true,
        resizable: false,
        width: 600,
        dialogClass: 'collega-procedimento-dialog',
        buttons: {
            'Ok': function() {
                var parameters = {};
                parameters.taskId = taskId;

                $.ajax({
                    type: 'POST',
                    url: path + '/processi/processo/task/completeMail.htm',
                    data: parameters,
                    success: function(data) {
                        var esito = data.success;
                        var messaggio = data.msg;
                        if (String(esito) === 'true') {
                            mostraMessaggioAjax(messaggio, "success");
                        } else {
                            mostraMessaggioAjax(messaggio, "error");
                        }
                    },
                    dataType: 'json'
                });

                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            },
            'Annulla': function() {
                $('body').removeClass('stop-scrolling');
                $(this).dialog('close');
            }
        }
    });
}

$(function() {
    $('.complete_task_protocol_manual_management').on('click', function(event) {
        var taskId = $(event.target).attr('task-id');
        var taskKey = $(event.target).attr('task-key');

        if (taskKey === 'protocol_manual_management') {
            showManualProtocolDialog(taskId);
        }
        if (taskKey === 'mail_manual_management') {
            showManualMailDialog(taskId);
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