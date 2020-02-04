[#ftl]

$(function() {

        [#list config.gridActions?reverse as gridAction]
            [#list gridAction as gridActionItem]        
                $('.${ns}grid-action-item-${gridActionItem.name}').on('click',function(e){
                    [@manageAction gridActionItem/]
                });
                [#if gridActionItem.items??]
                    [#list gridActionItem.items as subItem]
                        $('.${ns}grid-action-item-${subItem.name}').on('click',function(e){
                            [@manageAction subItem/]
                        });
                    [/#list]
                [/#if]
            [/#list]
        [/#list]
    
        var grid_selector = "#${ns}grid-table";
        var pager_selector = "#${ns}grid-pager";
        
        var askConfirmTitleTemplate="<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> --TITLE--</h4></div>";
        var askConfirmTextTemplate='<div class="space-6"></div> \
                                    <p class="bigger-110 bolder center grey"> \
                                            <i class="ace-icon fa fa-hand-o-right blue bigger-120"></i> \
                                            --TEXT-- \
                                    </p>';

        $.extend($.gritter.options, { 
            position: 'bottom-right'
        });

        /*$(window).on('resize.jqGrid', function() {
            $(grid_selector).jqGrid('setGridWidth', $(".page-content").width() - 5);
        });*/

        /*$(window).on('resize.jqGrid', function () {
            var parent_width = $(grid_selector).closest('.ui-dialog').children(":first").width();
            alert(parent_width);
            $(grid_selector).jqGrid( 'setGridWidth', parent_width );
            alert("POST"+parent_width);
	});*/
	
        //and also set width when tab pane becomes visible
        /*$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
          if($(e.target).attr('href') == '#mygrid') {
                var parent_width = $(grid_selector).closest('.tab-pane').width();
                $(grid_selector).jqGrid( 'setGridWidth', parent_width );
          }
        })*/


        jQuery(grid_selector).jqGrid({
            url: '${data.path}${config.loadData.url}',
            datatype: "json",
            postData: {
                [#if config.loadData.parameters??]
                    [#list config.loadData.parameters?keys as parameter]
                        ${parameter} :  "${config.loadData.parameters[parameter]}",
                    [/#list]
                [/#if]
                [#if config.loadData.requestParameters??]
                    [#list config.loadData.requestParameters as requestParameter]
                        [#assign requestParameterValueString="data."+requestParameter]
                        [#if requestParameterValueString?eval??]
                            [#assign requestParameterValueArray=requestParameterValueString?eval]
                            ${requestParameter} :  ${requestParameterValueArray},
                        [/#if]
                    [/#list]
                [/#if]
                },
            height: '100%',
            [#if config.sort.column??]
                sortname: '${config.sort.column}',
            [/#if]
            sortorder: '${config.sort.order!"asc"}',
            colModel: [
                [#list config.columnModel as column]
                    {
                        name: '${column.name}',
                        index: '${column.name}',
                        [#if column.key?? && column.key=="true"]
                            key: true,
                        [/#if]
                        [#if column.label??]
                            label: '${column.label}',
                        [/#if]
                        [#if column.width??]
                            width: '${column.width}',
                        [/#if]
                        [#if column.formatter??]
                            formatter: function(cellvalue, options, rowObject) {
                                if (typeof ${column.formatter.function} == 'function') {
                                    options.parameters={};
                                    [#list column.formatter.options?keys as option]
                                        options.parameters.${option} = "${column.formatter.options[option]}";
                                    [/#list]
                                    return ${column.formatter.function}(cellvalue, options, rowObject);
                                }else{
                                    return 'ciao';
                                }
                            }
                        [/#if]
                        [#if column.hidden?? && column.hidden=="true"]
                            hidden: true
                        [/#if]
                    },
                [/#list]
                [#if config.rowActions?? && config.rowActions?size>0]
                    {
                        name: 'myac',
                        classes: 'grid-row-action-cell',
                        index: '',
                        label: '&nbsp;',
                        width: ${config.rowActions?size}*30,
                        fixed: true,
                        sortable: false,
                        resize: false,
                        formatter:function(cellvalue, options, rowObject){
                            var rowActions='<div class="action-buttons">';
                            [#list config.rowActions as rowAction]
                                [#assign rowActionColor=data.FtlUtils.getValue("color", rowAction, standardRowActions[rowAction.name], "grey")]
                                [#assign rowActionClass=data.FtlUtils.getValue("class", rowAction, standardRowActions[rowAction.name], "fa-gears")]
                                [#assign rowActionLegend=data.FtlUtils.getValue("legend", rowAction, standardRowActions[rowAction.name], rowAction.name)]

                                rowActions += ' <a class="${rowActionColor?trim} ${ns}grid-row-action-item-${rowAction.name}" href="#" data-rel="tooltip" data-original-title="${rowActionLegend?trim}"><i class="ace-icon fa ${rowActionClass?trim} bigger-130"></i></a>';
                            [/#list]
                            rowActions += '</div> ';
                            return rowActions;
                        }
                    }
                [/#if]
            ],
            viewrecords: true,
            autowidth:true, 
            //shrinkToFit:true,
            rowNum: 10,
            rowList: [
                [#if config.pageSize??]
                    [#list config.pageSize as pageSize]
                        ${pageSize},
                    [/#list]
                [#else]
                    10, 20, 30
                [/#if]
                ],
            pager: pager_selector,
            altRows: true,
            //toppager: true,

            multiselect: true,
            //multikey: "ctrlKey",
            multiboxonly: true,
            loadComplete: function() {
                var table = this;

                [#list config.rowActions as rowAction]
                    $('.${ns}grid-row-action-item-${rowAction.name}').on('click',function(e){
                        $(grid_selector).jqGrid('resetSelection');
                        var currentRow = $(e.target).closest("tr.jqgrow");
			var currentId= currentRow[0].id;
                        $(grid_selector).jqGrid('setSelection',currentId);
                        
                        [#if rowAction.action?? && rowAction.action.type??]
                            [@manageAction rowAction/]
                        [/#if]
                    });
                [/#list]
                
                setTimeout(function() {
                    //styleCheckbox(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            }


        });
        $(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size


        //enable search/filter toolbar
        [#if config.filterToolbarEnable?? && config.filterToolbarEnable]
            jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
            jQuery(grid_selector).filterToolbar({});
        [/#if]

        jQuery(grid_selector).jqGrid('navGrid', pager_selector,
                {//navbar options
                    add: false,
                    edit: false,
                    del: false,
                    search: false,
                    refresh: false,
                }
        );


        function updatePagerIcons(table) {
            var replacement =
                    {
                        'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
                        'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
                        'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
                        'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
                    };
            $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function() {
                var icon = $(this);
                var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

                if ($class in replacement)
                    icon.attr('class', 'ui-icon ' + replacement[$class]);
            });
        }

        function enableTooltips(table) {
            //$('.navtable .ui-pg-button').tooltip({container: 'body'});
            //$(table).find('.ui-pg-div').tooltip({container: 'body'});
            $('[data-rel=tooltip]').tooltip({container: 'body'});
        }

    });
