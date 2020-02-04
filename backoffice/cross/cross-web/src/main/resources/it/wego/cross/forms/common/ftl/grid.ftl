[#ftl]
[#include "grid.defaults.ftl"]

[#assign config=component.attributes.config]
[#assign ns = "fc-" + .now?long?c +"-" /]

[#macro manageAction actionItem]
    [#if standardRowActions[actionItem.name]?? && standardRowActions[actionItem.name].action??]
        [#assign standardAction=standardRowActions[actionItem.name].action]
    [/#if]
    [#assign confirmTitle=data.FtlUtils.getValue("confirmTitle", actionItem.action, standardAction, "Conferma")]
    [#assign confirmText=data.FtlUtils.getValue("confirmText", actionItem.action, standardAction, "Confermi di voler procedere?")]
    [#assign buttonOkClassIcon=data.FtlUtils.getValue("buttonOkClassIcon", actionItem.action, standardAction, "")]
    [#assign buttonOkClassColor=data.FtlUtils.getValue("buttonOkClassColor", actionItem.action, standardAction, "btn-danger")]
    [#assign actionType=data.FtlUtils.getValue("type", actionItem.action, standardAction, "")]
    [#assign actionUrl=data.FtlUtils.getValue("url", actionItem.action, standardAction, "")]

    [#if actionItem?? && actionItem.action?? && actionType??]
        var url = '[#if actionUrl??]${data.path}${actionUrl}[/#if]';
        var parameters = {};
        [#if actionItem.action.otherParameters??]
            [#list actionItem.action.otherParameters?keys as otherParameter]
                var otherParameterValue = '${actionItem.action.otherParameters[otherParameter]}';
                if(otherParameterValue==='%%grid-selector%%'){
                    otherParameterValue = '${ns}grid-table';
                }
                parameters['${otherParameter}'] = otherParameterValue;
            [/#list]
        [/#if]
        [#if actionItem.action.rowParameters??]
            var selectedRows = $(grid_selector).jqGrid('getGridParam','selarrrow');
            var selectionRowData = [];
            for (i = 0; i < selectedRows.length; i++) {
                var selectedRow = selectedRows[i];       
                var rowData = $(grid_selector).getRowData(selectedRow);
                [#list actionItem.action.rowParameters as rowParameter]
                    selectonRowData = parameters['${rowParameter}'];
                    if (! selectonRowData) {
                        selectionRowData = [];
                    }
                    selectionRowData.push(rowData['${rowParameter}']);
                    parameters['${rowParameter}']=JSON.stringify(selectionRowData);
                [/#list]
            }
        [/#if]
        [#if actionItem.action.requestParameters??]
            [#list actionItem.action.requestParameters as requestParameter]
                [#assign requestParameterValueString="data."+requestParameter]
                [#if requestParameterValueString?eval??]
                    [#assign requestParameterValueArray=requestParameterValueString?eval]
                    parameters['${requestParameter}']=${requestParameterValueArray};
                [/#if]
            [/#list]
        [/#if]
    [/#if]

    [#if actionType=='submit']
        e.preventDefault();
        wgf.utils.askConfirm({
            title: askConfirmTitleTemplate.replace("--TITLE--", "${confirmTitle}"), 
            text: askConfirmTextTemplate.replace("--TEXT--", "${confirmText}"), 
            buttonOk:{
                text: "<i class='ace-icon fa ${buttonOkClassIcon} bigger-110'></i>&nbsp; OK",
                cls: "btn ${buttonOkClassColor} btn-minier",
                callback: function(){
                    //var wgf.ajax.buildModalProgressResponseHanlder();
                    wgf.ajax.getJsonWithMask('body', url+'?'+$.param(parameters),function(response){
                        $(grid_selector).trigger("reloadGrid"); 
                        wgf.utils.log('SUCCESS');
                        $.gritter.add({
                                title: 'Notifica',
                                text: (response.message)?response.message:'Operazione completata.',
                                class_name: 'gritter-success'
                        });
                    });
                }
            },
            buttonCancel:{
                text: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; Annulla",
                cls: "btn btn-minier"
            }
        });
    [/#if]
    [#if actionType=='redirect']
        $('body').mask(wgf.messages.processingMessage);
        var encodedParams = $.param(parameters);
        window.location.href = url+'?'+encodedParams;
    [/#if]
    [#if actionType=='form']
        wgf.utils.log('FORM LOAD');
        var encodedParams = $.param(parameters);
        wgf.utils.showFormDialog(
            "<div class='widget-header'><h4 class='smaller'>${actionItem.action.title}</h4></div>",
            '${data.path}/form/ajax/view.htm?idForm=${actionItem.action.form}&'+encodedParams);
    [/#if]
    [#if actionType=='trigger']
        wgf.utils.log('FORM TRIGGER');
        [#assign actionTrigger=data.FtlUtils.getValue("trigger", actionItem.action, standardAction, "")]
        [#if actionTrigger??]
            $(grid_selector).trigger("${actionTrigger}"); 
        [/#if]
    [/#if]
[/#macro]

<div ${api.header}>

    <div class="cross-box widget-box transparent ">
        <div class="widget-header">
            <h5 class="widget-title" data-toggle="tooltip" data-placement="top" title="Tooltip on top">${config.title}</h5>

            
            [#list config.gridActions?reverse as gridAction]
                <div class="widget-toolbar">
                    [#list gridAction as gridActionItem]
                        [#assign gridActionColor=data.FtlUtils.getValue("color", gridActionItem, standardRowActions[gridActionItem.name], "grey")]
                        [#assign gridActionClass=data.FtlUtils.getValue("class", gridActionItem, standardRowActions[gridActionItem.name], "fa-gears")]
                        [#assign gridActionLegend=data.FtlUtils.getValue("legend", gridActionItem, standardRowActions[gridActionItem.name], "")]
                        [#if gridActionItem.items??]
                            <div class="widget-menu">
                                <a href="#" data-action="${gridActionItem.name}" data-toggle="dropdown" data-rel="tooltip" data-original-title="${gridActionLegend?trim}">
                                    <i class="ace-icon fa ${gridActionClass} ${gridActionColor} bigger-130"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">
                                    [#list gridActionItem.items as subItem]
                                        [#assign gridActionItemColor=data.FtlUtils.getValue("color", subItem, standardRowActions[gridActionItem.name], "grey")]
                                        [#assign gridActionItemClass=data.FtlUtils.getValue("class", subItem, standardRowActions[gridActionItem.name], "fa-gears")]
                                        [#assign gridActionItemLegend=data.FtlUtils.getValue("legend", subItem, standardRowActions[gridActionItem.name], "")]
                                        <li>
                                            <a data-toggle="tab" href="#dropdown1" data-rel="tooltip" data-original-title="${gridActionItemLegend?trim}" class="${ns}grid-action-item-${subItem.name}">
                                                <i class="ace-icon fa ${gridActionItemClass} ${gridActionItemColor} bigger-110"></i>
                                                ${gridActionItemLegend?trim}
                                            </a>
                                        </li>
                                    [/#list]
                                </ul>
                            </div>
                        [#else]
                            <a href="#" data-action="${gridActionItem.name}" data-rel="tooltip" data-original-title="${gridActionLegend?trim}" class="${ns}grid-action-item-${gridActionItem.name}">
                                <i class="ace-icon fa ${gridActionClass} ${gridActionColor} bigger-130"></i>
                            </a>
                        [/#if]
                    [/#list]
                </div>
            [/#list]
            
        </div>

        <div class="widget-body">
            <div class="widget-main" style="padding:0px">
													
                <table id="${ns}grid-table"></table>
                <div id="${ns}grid-pager"></div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" >
    //@ sourceURL=${ns}crossui-table.js;
    [#include "grid.formatters.js.ftl"]
    [#include "grid.js.ftl"]
</script>