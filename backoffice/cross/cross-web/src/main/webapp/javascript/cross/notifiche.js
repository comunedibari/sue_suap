function pollNotifiche() {
    setTimeout(function() {
        aggiornaNotifiche();
    }, 60000);
}

function aggiornaNotifiche() {
    $.ajax({
        url: path + '/workflow/notifiche/ajax/count.htm',
        type: "POST",
        success: function(data) {
            if (data.rows.length > 0) {
                $('.notificheDaLeggere').empty();
                $('.dropdown-menu-notifiche').empty();
                $('.notificheDaLeggere').text(data.total);
                for (var i = 0; i < data.rows.length; i++) {
                    var task = data.rows[i];
                    var row = $('<li />');
                    var item = $('<a >').attr({
                        href: path + '/workflow/notifiche/event.htm?taskId=' + task.taskId
                    });
                    var itemContainer = $('<span />').attr({
                        title: task.variables['testoMessaggio']
                    }).addClass('msg-body');

                    var itemTitle = $('<span />').addClass('msg-title');
                    itemTitle.text(task.variables['titoloNotifica']);

                    var itemText = $('<div />').addClass('msg-text');
                    $(itemText).text(task.variables['testoMessaggio']);

                    $(itemContainer).append(itemTitle).append(itemText);

                    $(item).append(itemContainer);
                    $(row).append(item);
                    $('.dropdown-menu-notifiche').append(row);
                }

                var showAll = $('<li />').addClass('msg-element-list');
                var showAllLink = $('<a >').attr({
                    href: path + '/workflow/notifiche/list.htm'
                }).html('Visualizza tutte le notifiche &rarr;');
//                $(showAllLink).append(showAllContent);
                $(showAll).append(showAllLink);
                $('.dropdown-menu-notifiche').append(showAll);
            }
        },
        dataType: "json",
        complete: pollNotifiche,
        timeout: 5000
    });
}

function markAsRead(taskId) {
    var parameters = {};
    parameters.taskId = taskId;
    $.ajax({
        type: 'POST',
        url: path + '/workflow/notifiche/markRead.htm',
        data: parameters,
        success: function(data) {
            var esito = data.success;
            var messaggio = data.msg;
            if (String(esito) === 'true') {
                mostraMessaggioAjax(messaggio, "success");
            } else {
                mostraMessaggioAjax(messaggio, "error");
            }
            aggiornaNotifiche();
            $("#notificheTable").trigger("reloadGrid");
        },
        dataType: 'json'
    });
}

function displayMessage(taskId) {
    var div = $('<div><div id="messageDialog"></div></div>');
    $(div).dialog({
        modal: true,
        width: 500,
        height: 300,
        title: 'Notifica',
        closeOnEscape: true,
        open: function(event, ui) {
//            $(".ui-dialog-titlebar-close", ui.dialog || ui).show(); 
            $('body').css('overflow', 'hidden');
            $('.ui-dialog-content').css('width', '100%');
            $('#messageDialog').load(path + '/workflow/notifiche/detail.htm?taskId=' + taskId, function() {

            });
        },
        close: function() {
            div.remove();
            $('body').css('overflow', 'auto');
        }
    });
}

$(function() {

    $("#notificheTable").jqGrid({
        url: path + "/workflow/notifiche/list/ajax.htm",
        datatype: "json",
        colNames: [
            'Id Task',
            'Pratica',
            'Richiedenti',
            'Attivita\'',
            'Testo',
            'Data',
            'Azioni'
        ],
        colModel:
                [{
                        name: 'taskId',
                        index: 'taskId',
                        sortable: false,
                        hidden: true,
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
                        name: 'title',
                        index: 'variables',
                        sortable: false,
                        formatter: function(cellvalue, options, rowObject) {
                            return rowObject["variables"]["titoloNotifica"];
                        }
                    },
                    {
                        name: 'content',
                        index: 'variables',
                        sortable: false,
                        formatter: function(cellvalue, options, rowObject) {
                            return rowObject["variables"]["testoMessaggio"];
                        }
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
                        width: '75px',
                        formatter: function(cellvalue, options, rowObject) {
                            var link = '<div class="taskItemContainer">' +
                                    '<a title="Visualizza dettaglio" class="taskItemButton" onclick="displayMessage(\'' + rowObject["taskId"] + '\')" href="#"><img src="' + path + '/themes/default/css/images/eye_grey.png"></a>' +
                                    '<a title="Visualizza evento" class="taskItemButton" href="' + path + '/workflow/notifiche/event.htm?taskId=' + rowObject["taskId"] + '"><img src="' + path + '/themes/default/css/images/ricollega_grey.png"></a>' +
                                    '<a title="Marca come letto" class="taskItemButton" onclick="markAsRead(\'' + rowObject["taskId"] + '\')" href="#"><img src="' + path + '/themes/default/css/images/check_grey.png"></a>' +
                                    '</div>';
                            return link;
                        }
                    }
                ],
        rowList: [10, 20, 30, 100],
        pager: '#notifichePager',
        sortname: 'date',
        viewrecords: true,
        sortorder: "DESC",
        rowNum: "10",
        jsonReader: {
            repeatitems: false,
            id: "0"
        },
        height: 'auto',
        width: $('.notificheTableContainer').width()
    }).navGrid('#notifichePager', {edit: false, add: false, del: false, search: false});

    aggiornaNotifiche();
});
