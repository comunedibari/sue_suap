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
                    url: path + '/workflow/task/completeProtocol.htm',
                    data: parameters,
                    success: function(data) {
                        var esito = data.success;
                        var messaggio = data.msg;
                        if (String(esito) === 'true') {
                            mostraMessaggioAjax(messaggio, "success");
                        } else {
                            mostraMessaggioAjax(messaggio, "error");
                        }
                        $("#tasklistTable").trigger("reloadGrid");
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
    var parameters = {};
    parameters.taskId = taskId;
    $.ajax({
        type: 'POST',
        url: path + '/workflow/task/getBrokenEmail.htm',
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


function inviaEmailConNuoviParametri(taskId, mailId, destinatario) {
    var parameters = {};
    parameters.taskId = taskId;
    parameters.mailId = mailId;
    parameters.destinatario = destinatario;
    $.ajax({
        type: 'POST',
        url: path + '/workflow/task/completeMail.htm',
        data: parameters,
        success: function(data) {
            var esito = data.success;
            var messaggio = data.msg;
            if (String(esito) === 'true') {
                mostraMessaggioAjax(messaggio, "success");
            } else {
                mostraMessaggioAjax(messaggio, "error");
            }
            $("#tasklistTable").trigger("reloadGrid");
        },
        dataType: 'json'
    });

}

function cancellaIstanza(instanceId) {
    var div = $("<div>");
    div.html("Confermi di voler cancellare l'istanza selezionata?");
    $(div).dialog(
            {
                modal: true,
                title: "Conferma cancellazione istanza",
                buttons: {
                    Ok: function() {
                        var parameters = {};
                        parameters.instanceId = instanceId;

                        $.ajax({
                            type: 'POST',
                            url: path + '/workflow/instance/delete.htm',
                            data: parameters,
                            success: function(data) {
                                var esito = data.success;
                                var messaggio = data.msg;
                                if (String(esito) === 'true') {
                                    mostraMessaggioAjax(messaggio, "success");
                                } else {
                                    mostraMessaggioAjax(messaggio, "error");
                                }
                                $("#tasklistTable").trigger("reloadGrid");
                            },
                            dataType: 'json'
                        });

                        $(div).dialog('close');
                    },
                    Annulla: function() {
                        $(div).dialog('close');
                    }
                }
            });
}
function showError(errorId) {
    var div = $('<div><div id="errorInternalDialog"></div></div>');
    $(div).dialog({
        modal: true,
        width: 1000,
        height: 300,
        title: 'ErrorDetail',
        closeOnEscape: true,
        open: function(event, ui) {
//            $(".ui-dialog-titlebar-close", ui.dialog || ui).show(); 
            $('body').css('overflow', 'hidden');
            $('.ui-dialog-content').css('width', '100%');
            $('#errorInternalDialog').load(path + '/error/view.htm?errorId=' + errorId, function() {

            });
        },
        close: function() {
            div.remove();
            $('body').css('overflow', 'auto');
        }
    });
}

$(function() {

    $("#tasklistTable").jqGrid({
        url: path + "/workflow/tasklist/ajax.htm",
        datatype: "json",
        colNames: [
            'Id Task',
            'Pratica',
            'Richiedenti',
            'Attivita\'',
            'Data',
            'Azioni'
        ],
        colModel:
                [{
                        name: 'taskId',
                        index: 'taskId',
                        sortable: false,
                        width: '25px'
                    },
                    {
                        name: 'pratica',
                        index: 'variables',
                        sortable: false,
                        formatter: function(cellvalue, options, rowObject) {
                            var idPratica = rowObject["variables"]["idPratica"];
                            var oggettoPratica = rowObject["variables"]["oggettoPratica"];
                            return '<a href="' + path + '/pratiche/dettaglio.htm?id_pratica=' + idPratica + '">' + oggettoPratica + '</a>';
                        }
                    },
                    {
                        name: 'richiedenti',
                        index: 'variables',
                        sortable: false,
                        formatter: function(cellvalue, options, rowObject) {
                            return rowObject["variables"]["richiedentiPratica"];
                        }
                    },
                    {
                        name: 'name',
                        index: 'name',
                        sortable: false
                    },
                    {
                        name: 'taskDate',
                        index: 'taskDate',
                        sortable: true,
                        width: '50px'
                    },
                    {
                        name: 'azione',
                        index: 'id',
                        classes: "list_azioni",
                        sortable: false,
                        formatter: function(cellvalue, options, rowObject) {
                            var completeFunction = '';
                            if (rowObject["taskKey"] === 'protocol_manual_management') {
                                completeFunction = "showManualProtocolDialog";
                            } else {
                                completeFunction = "showManualMailDialog";
                            }
                            var link = '<div class="gridActionContainer">\n\
                                                        <a onclick="showError(\'' + rowObject["variables"]["last_error_message"] + '\')" href="#"><img src="' + path + '/themes/default/css/images/eye_orange.png"></a>\n\
                                                        <a onclick="' + completeFunction + '(\'' + rowObject["taskId"] + '\')" href="#"><img src="' + path + '/themes/default/css/images/pencil_orange.png"></a>\n\
                                                        <a onClick = "cancellaIstanza(' + rowObject["instanceId"] + ')" href = "#">     <img src="' + path + '/themes/default/css/images/basket_orange.png"></a>\n\
                                                    </div>';
                            return link;
                        }
                    }
                ],
        rowList: [10, 20, 30, 100],
        pager: '#tasklistPager',
        sortname: 'date',
        viewrecords: true,
        sortorder: "DESC",
        rowNum: "10",
        jsonReader: {
            repeatitems: false,
            id: "0"
        },
        height: 'auto',
        width: $('.tasklistTableContainer').width()
    }).navGrid('#tasklistPager', {edit: false, add: false, del: false, search: false});


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
                inviaEmailConNuoviParametri(taskId, mailId, destinatario);
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
