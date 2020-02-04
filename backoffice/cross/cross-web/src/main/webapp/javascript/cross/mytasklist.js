function cancellaIstanza(instanceId) {
    var div = $("<div>");
    div.html("Confermi di voler cancellare l'istanza selezionata?");
    $(div).dialog(
            {
                modal: true,
                title: "Conferma cancellazione istanza",
                buttons: {
                    Ok: function() {
                        var process = new Process(instanceId);
                        process.delete(function(esito, messaggio) {
                            if (String(esito) === 'true') {
                                mostraMessaggioAjax(messaggio, "success");
                            } else {
                                mostraMessaggioAjax(messaggio, "error");
                            }
                            $("#myTasklistTable").trigger("reloadGrid");
                        })
                        $(div).dialog('close');
                    },
                    Annulla: function() {
                        $(div).dialog('close');
                    }
                }
            });
}

$(function() {

    $("#myTasklistTable").jqGrid({
        url: path + "/workflow/mytask/ajax.htm",
        datatype: "json",
        colNames: [
            'Id Task',
            'Pratica',
            'Evento',
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
                    {name: 'descrizioneEvento', index: 'descrizioneEvento', sortable: false,
                        formatter: function(cellvalue, options, rowObject) {
                            var idEvento = rowObject["variables"]["idEvento"];
                            var descrizioneEvento = rowObject["variables"]["descrizioneEvento"];
                            return '<a href="' + path + '/console/mail/event.htm?evento=' + idEvento + '">' + descrizioneEvento + '</a>';
                        }},
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
                            var link = '<div class="gridActionContainer">' +
                                    '<a onclick="execute(\'' + rowObject["taskId"] + '\')" href="#"><img src="' + path + '/themes/default/css/images/pencil_orange.png"></a>' +
                                    '<a onClick = "cancellaIstanza(' + rowObject["instanceId"] + ')" href = "#">     <img src="' + path + '/themes/default/css/images/basket_orange.png"></a>' +
                                    '</div>';
                            return link;
                        }
                    }
                ],
        rowList: [10, 20, 30, 100],
        pager: '#myTasklistPager',
        sortname: 'date',
        viewrecords: true,
        sortorder: "DESC",
        rowNum: "10",
        jsonReader: {
            repeatitems: false,
            id: "0"
        },
        height: 'auto',
        width: $('.myTasklistTableContainer').width()
    }).navGrid('#myTasklistPager', {edit: false, add: false, del: false, search: false});

});
