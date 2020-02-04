<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy

Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://www.osor.eu/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@page import="it.people.dbm.model.Utente"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.people.dbm.dao.DbmDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            ServletContext context = getServletContext();
            String basePath = context.getInitParameter("baseUrl");
            HashMap testiPortale = (HashMap) session.getAttribute("testiPortale");
            String nomePagina = request.getParameter("nomePagina");
            Utente utente = (Utente) session.getAttribute("utente");
            String set_id = request.getParameter("set_id");
%>
Ext.onReady(function(){

Ext.QuickTips.init();


var grid_row = 25;
var urlScriviSessione = 'registraSessione';

var bd = Ext.getBody();
var formName = 'edit-form';
var form_div = Ext.get('form');
var grid_div = Ext.get('grid');
var menu_div = Ext.get('tree_container');

var table_name = 'formulario_ae';
var sort_field = 'codice_ente';

var id_field = '';
var group_field = '';
var expand_field = '';

var dyn_fields = [
		{name: 'data_validita'},
		{name: 'codice_ente'},
		{name: 'tipologia_ufficio'},
		{name: 'codice_ufficio'},
		{name: 'tipologia_ente'},
		{name: 'denominazione'},
		{name: 'codice_catastale_comune'},
		{name: 'data_decorrenza'},
		{name: 'ufficio_statale'}
	];


var reader = new Ext.data.JsonReader({
	root:table_name,
	fields: dyn_fields,
	baseParams: {
			lightWeight:true,
			ext: 'js'
		},
	totalProperty: 'totalCount',
	idProperty: id_field
});

var ds = new Ext.data.GroupingStore({
	proxy: new Ext.data.HttpProxy({url: 'actions_CCD?table_name='+table_name}),
	sortInfo: {field:sort_field,direction:'DESC'},
	defaultParamNames: {
			start: 'start',
			limit: 'size',
			sort: 'sort',
			dir: 'dir'
		},
	remoteSort: true,
	reader: reader,
	groupField: group_field
	//		autoLoad:true
});

ds.load({params: {start:0, size:grid_row}});

ds.on('loadexception', function(event, options, response, error) {
var json = Ext.decode(response.responseText);
Ext.Msg.alert('Errore', json.error);
})

var colModel = new Ext.grid.ColumnModel([
{
id:'grid_data_validita',
header: "<%=testiPortale.get(nomePagina + "_header_grid_data_validita")%>",
width: 100,
sortable: true,
dataIndex: 'data_validita'
},
{
id:'grid_codice_ente',
header: "<%=testiPortale.get(nomePagina + "_header_grid_codice_ente")%>",
renderer: renderTpl,
width: 350,
sortable: true,
dataIndex: 'codice_ente'
},
{
id:'grid_tipologia_ufficio',
header: "<%=testiPortale.get(nomePagina + "_header_grid_tipologia_ufficio")%>",
width: 100,
sortable: true,
dataIndex: 'tipologia_ufficio'
},
{
id:'grid_codice_ufficio',
header: "<%=testiPortale.get(nomePagina + "_header_grid_codice_ufficio")%>",
renderer: renderTpl,
width: 350,
sortable: true,
dataIndex: 'codice_ufficio'
},
{
id:'grid_tipologia_ente',
header: "<%=testiPortale.get(nomePagina + "_header_grid_tipologia_ente")%>",
width: 100,
sortable: true,
dataIndex: 'tipologia_ente'
},
{
id:'grid_denominazione',
header: "<%=testiPortale.get(nomePagina + "_header_grid_denominazione")%>",
renderer: renderTpl,
width: 350,
sortable: true,
dataIndex: 'denominazione'
},
{
id:'grid_codice_catastale_comune',
header: "<%=testiPortale.get(nomePagina + "_header_grid_codice_catastale_comune")%>",
width: 100,
sortable: true,
dataIndex: 'codice_catastale_comune'
},
{
id:'grid_data_decorrenza',
header: "<%=testiPortale.get(nomePagina + "_header_grid_data_decorrenza")%>",
renderer: renderTpl,
width: 350,
sortable: true,
dataIndex: 'data_decorrenza'
},
{
id:'grid_ufficio_statale',
header: "<%=testiPortale.get(nomePagina + "_header_grid_ufficio_statale")%>",
width: 100,
sortable: true,
dataIndex: 'ufficio_statale'
}
]);


                                       var fileUpload = new Ext.ux.form.FileUploadField ({
                    xtype: 'fileuploadfield',
                    id: 'form-file',
                    emptyText: "<%=testiPortale.get("form_upload_file_empty")%>",
                    fieldLabel: "<%=testiPortale.get("form_upload_file_name")%> Excel",
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;',
                    name: 'document_path',
                    buttonText: '',
                    buttonCfg: {
                        iconCls: 'upload-icon'
                    }
                });



var	itemsForm = [fileUpload, {
id: 'table_name',
fieldLabel: 'table name',
name: 'table_name',
readOnly: true,
hidden: true,
hideLabel: true,
value: table_name
}];

var search = new Ext.ux.form.SearchField({
store: ds,
width:320,
paging: true,
paging_size: grid_row
});


    var grid = new Ext.grid.GridPanel({
    id:'gridStandard',
    frame:true,
    ds: ds,
    cm: colModel,
    buttonAlign: 'center',
    autoScroll:true,
    oadMask: true,
    autoExpandColumn: expand_field,
    height:500,
    view: new Ext.grid.GroupingView({
    forceFit:true,
    groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
    }),
    bbar: new Ext.PagingToolbar({
    store: ds,
    pageSize:grid_row,
    displayInfo:true,
    items:[search]
    }),
    border: true,
    renderTo: grid_div
    });
    
    
    
    
    
    var form = new Ext.FormPanel({
    id: formName,
    monitorValid: true,
	fileUpload: true,
    formLayout: true,
    labelWidth: 120,
    title:"<%=testiPortale.get(nomePagina + "_header_form")%>",
    defaults: {anchor:'98%', border:false},
    height:200,			// Default config options for child items
    defaultType: 'textfield',
    frame: true,
    autoHeight: true,
    autoWidth: true,
    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
    border: true,
    style: {
    "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
    },
    items: itemsForm,

buttons: [{
id: 'update_formulary',
text: "Carica",
formBind:true,
handler: function(){

                            form.getForm().submit({
                                url: 'uploadDocumentccd',
                                waitMsg: "Attendere prego...",
                                success: function(response,request){
										ds.removeAll();
										ds.load({params: {start:0, size:grid_row}});
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                		Ext.getCmp("form-file").reset();
                                },
                                failure:function(result,request) {
                                        Ext.Msg.alert('Status', 'Failed'+request.msg);
                                }
                            });

}
}],
    
                buttonAlign: 'center',
                renderTo: form_div
                });
                
                
                


                    tree.render(menu_div);

                    tree.selectPath("treepanel/source/<%=set_id%>",'id');

                    });
