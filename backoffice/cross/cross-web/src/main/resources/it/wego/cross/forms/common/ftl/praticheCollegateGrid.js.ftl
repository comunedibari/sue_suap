[#ftl]

$(function () {
    
    var grid = $('#${tableId}'), pager = $('#${pagerId}');

    var loadInto = grid.closest('.wgf-container').find('.wfl-flowConfigurationContainer > .wgf-container-childs');
    loadInto.on('wgf-afterSubmitSuccess', function () {
        grid.trigger("reloadGrid");
    });

    grid.jqGrid({
//        url: "${api.buildActionUrl('getFlowDefinitionGridData')}",
        url: "${data.path}/pratiche/gestisci/ajax.htm?search_all=OK",
        datatype: 'json',
        autowidth: true,
        height: "100%",
        shrinkToFit: true,
        viewrecords: true,
        postData: {
            action: 'search'
        },
        colModel: [
            {
                name: 'idPratica',
                label: 'Id',
                width: 40,
                classes: 'ui-ellipsis'
            }, {
                name: 'statoPratica',
                label: 'Stato pratica',
                sortable: false,
                width: 60
            }, {
                name: 'dataRicezione',
                label: 'Data Ricezione',
                width: 60
            }, {
                name: 'oggettoPratica',
                label: 'Oggetto',
                width: 250
            }, {
                name: 'protocollo',
                label: 'Protocollo',
                width: 60
            }, {
                name: 'richiedente',
                label: 'Richiedenti',
                sortable: false,
                width: 120
            }, {
                name: 'ente',
                label: 'Ente',
                sortable: false,
                width: 120
            },{
                name: 'inCarico',
                label: 'Operatore',
                sortable: false,
                width: 120
            }],
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: '#${pagerId}',
//        sortname: 'flowDescription',
        sortname: 'dataRicezione',
        sortorder: 'asc',
        onSelectRow: function (id) {
            var record = grid.jqGrid("getRowData", id), idPraticaId = record.idPratica;
            selectPraticaDaCollegare(idPraticaId);
        }
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