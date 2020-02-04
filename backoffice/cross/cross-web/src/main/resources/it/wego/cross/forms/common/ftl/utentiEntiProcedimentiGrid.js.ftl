[#ftl]

$(function () {
    
    var grid = $('#${tableId}'), pager = $('#${pagerId}');

    var loadInto = grid.closest('.wgf-container').find('.wfl-flowConfigurationContainer > .wgf-container-childs');
    loadInto.on('wgf-afterSubmitSuccess', function () {
        grid.trigger("reloadGrid");
    });

    grid.jqGrid({
        url: "${data.path}/utenti/procedimenti/select/ajax.htm",
        datatype: 'json',
        autowidth: true,
        height: "100%",
        shrinkToFit: true,
        viewrecords: true,
        postData: {
            action: 'search',
            idUtenteRuoloEnte: $('#idUtenteRuoloEnte').val()
        },
        colModel: [
            {
                name: 'codProcedimento',
                label: 'Codice procedimento',
                sortable: false,
                width: 60
            }, {
                name: 'nome',
                label: 'Descrizione',
                width: 250
            }, {
                name: 'abilitazione',
                label: 'Stato',
                width: 60
            }, {
                name: 'azione',
                label: 'Azione',
                width: 60,
                formatter: function(cellvalue, options, rowObject) {
                      var link = '<div class="gridActionContainer">\
                      <a href="#" onClick="cambiaStato(' + rowObject["codUtente"] + ',' + rowObject["codEnte"] + ',' + rowObject["codProcedimento"] + ',\''+ rowObject["codPermesso"] +'\')" ><img src="${data.path}/themes/default/css/images/ricollega.png"></a>\
                      </div>';
                      return link
                      }
            }],
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: '#${pagerId}',
        sortname: 'dataRicezione',
        sortorder: 'asc'
    });
    grid.navGrid('#${pagerId}', {
        edit: false,
        add: false,
        del: false,
        search: false
    });
    jQuery("#grid").jqGrid('setGroupHeaders', {
        groupHeaders: [
            {startColumnName: 'active', numberOfColumns: 3, titleText: 'Instances'}
        ]
    });

});

function cambiaStato(idUtente,idEnte,idProcedimento,codPermesso) {
    $.ajax({
        url: "${data.path}/utenti/procedimenti/cambiostato/ajax.htm",
        dataType: "json",
        data: {
            idUtente: idUtente,
            idEnte: idEnte,
            idProcedimento: idProcedimento,
            codPermesso: codPermesso
        },
        success: function (data) {
            if (data.success) {
                var grid = $('#${tableId}');
                grid.trigger("reloadGrid");
                mostraMessaggioAjax(data.message, "success");
            } else {
                mostraMessaggioAjax(data.message, "error");
            }
        }
    });
}