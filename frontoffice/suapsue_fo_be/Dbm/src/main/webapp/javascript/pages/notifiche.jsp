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
            DbmDao dbmDao = new DbmDao();
            Utente utente = (Utente) session.getAttribute("utente");
            String set_id = request.getParameter("set_id");
%>
Ext.onReady(function(){

Ext.QuickTips.init();

var grid_row = 25;
var urlScriviSessione = 'registraSessione';

var bd = Ext.getBody();

var grid_div = Ext.get('grid');
var menu_div = Ext.get('tree_container');

var formName = 'formNotifiche';
var table_name = 'notifiche';
var sort_field = 'data_notifica';

var id_field = '';
var group_field = '';
var expand_field = '';

var table_interventi = 'interventi';

var dyn_fields_interventi = [
{name: 'cod_int'},
{name: 'tit_int'}
];

var readerInterventi = new Ext.data.JsonReader({
root:table_interventi,
fields: dyn_fields_interventi,
baseParams: {
lightWeight:true,
ext: 'js'
}
});

var dsInterventi = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name+'&interventi=yes'}),
remoteSort: true,
reader: readerInterventi
});

dsInterventi.on('loadexception', function(event, options, response, error) {
var json = Ext.decode(response.responseText);
Ext.Msg.alert('Errore', json.error);
})

var colModelInterventi = new Ext.grid.ColumnModel([
{
id:'grid_cod_int',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int")%>",
width: 100,
sortable: true,
dataIndex: 'cod_int'
},
{
id:'grid_tit_int',
header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int")%>",
renderer: renderTpl,
width: 540,
sortable: true,
dataIndex: 'tit_int'
}
]);

var gridInterventi = new Ext.grid.EditorGridPanel({
store: dsInterventi,
cm: colModelInterventi,
height: 160,
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_grid_interventi")%>",
autoScroll:true,
border: false
});



var comboStatoNotifica =  new Ext.form.ComboBox({
id: 'stato_notifica',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica")%>",
name: 'stato_notifica',
typeAhead: true,
triggerAction: 'all',
lazyRender:true,
mode: 'local',
hiddenName: 'stato_notifica_hidden',
emptyText: "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_select")%>",
store: new Ext.data.SimpleStore({
fields: ['stato_notifica','displayText'],
data: [['N', "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_N")%>"],
['A', "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_A")%>"],
['C', "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_C")%>"]]
}),
valueField: 'stato_notifica',
displayField: 'displayText',
selectOnFocus:true,
editable: false,
allowBlank: false,
labelStyle: 'font-weight:bold;'	,
resizable:true
});

function changeStatoNotifica(val){
if(val == 'N'){
return "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_N")%>";
}else if(val == 'A'){
return "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_A")%>";
}else if(val == 'C'){
return "<%=testiPortale.get(nomePagina + "_field_form_stato_notifica_C")%>";
}
return val;
};

var dyn_fields = [
{name: 'cnt'},
{name: 'cod_utente_origine'},
{name: 'cognome_nome_origine'},
{name: 'cognome_nome_carico'},
{name: 'cod_utente_carico'},
{name: 'testo_notifica'},
{name: 'data_notifica'},
{name: 'stato_notifica'},
{name: 'nome_file'},
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
proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name}),
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


var search = new Ext.ux.form.SearchField({
store: ds,
width:320,
paging: true,
paging_size: grid_row
});
var searchSelectionModel = new Ext.grid.RowSelectionModel({
singleSelect: true,
listeners: {
rowselect: function(sm, row, rec) {
winNotifica.display();
Ext.getCmp(formName).getForm().loadRecord(rec);
dsInterventi.load({params:{cnt:rec.data.cnt}});
}
}
});
var colModel = new Ext.grid.ColumnModel([
{
id:'grid_cnt',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cnt")%>",
width: 20,
sortable: true,
dataIndex: 'cnt'
},
{
id:'grid_cod_utente_carico',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_utente_carico")%>",
renderer: renderTpl,
width: 50,
sortable: true,
dataIndex: 'cod_utente_carico'
},
{
id:'grid_cognome_nome_carico',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cognome_nome_carico")%>",
renderer: renderTpl,
width: 100,
sortable: true,
dataIndex: 'cognome_nome_carico'
},
{
id:'grid_cod_utente_origine',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_utente_origine")%>",
renderer: renderTpl,
width: 50,
sortable: true,
dataIndex: 'cod_utente_origine'
},
{
id:'grid_cognome_nome_origine',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cognome_nome_origine")%>",
renderer: renderTpl,
width: 100,
sortable: true,
dataIndex: 'cognome_nome_origine'
},
{
id:'grid_testo_notifica',
header: "<%=testiPortale.get(nomePagina + "_header_grid_testo_notifica")%>",
renderer: renderTpl,
width: 200,
sortable: true,
dataIndex: 'testo_notifica'
},
{
id:'grid_nome_file',
header: "<%=testiPortale.get(nomePagina + "_header_grid_nome_file")%>",
renderer: renderTpl,
width: 100,
sortable: true,
dataIndex: 'nome_file'
},{
id:'grid_data_notifica',
header: "<%=testiPortale.get(nomePagina + "_header_grid_data_notifica")%>",
renderer: renderTpl,
width: 50,
sortable: true,
dataIndex: 'data_notifica'
},{
id:'grid_stato_notifica',
header: "<%=testiPortale.get(nomePagina + "_header_grid_stato_notifica")%>",
renderer: renderTpl,
width: 50,
sortable: true,
dataIndex: 'stato_notifica',
renderer: changeStatoNotifica
}
]);


var grid = new Ext.grid.GridPanel({
id:'gridStandard',
frame:true,
ds: ds,
cm: colModel,
buttonAlign: 'center',
autoScroll:true,
sm:searchSelectionModel,
oadMask: true,
autoExpandColumn: expand_field,
height:700,
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
var itemsForm = [{
id: 'cnt',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cnt")%>",
name: 'cnt',
readOnly: true
},{
id: 'cod_utente_carico',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_utente_carico")%>",
name: 'cod_utente_carico',
readOnly: true
},{
id: 'cognome_nome_carico',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cognome_nome_carico")%>",
name: 'cognome_nome_carico',
readOnly: true
},{
id: 'cod_utente_origine',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_utente_origine")%>",
name: 'cod_utente_origine',
readOnly: true
},{
id: 'cognome_nome_origine',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cognome_nome_origine")%>",
name: 'cognome_nome_origine',
readOnly: true
},{
id: 'testo_notifica',
xtype:'textarea',
height: 75,
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_testo_notifica")%>",
name: 'testo_notifica',
readOnly: true
},{
id: 'nome_file',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_file")%>",
name: 'nome_file',
readOnly: true
},{
id: 'data_notifica',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_data_notifica")%>",
name: 'data_notifica',
readOnly: true
},comboStatoNotifica,
gridInterventi,
{
id: 'table_name',
fieldLabel: 'table_name',
name: 'table_name',
readOnly: true,
hidden: true,
hideLabel: true,
value: table_name
},
{
id: 'azione_form',
fieldLabel: 'azione form',
name: 'azione_form',
readOnly: true,
hidden: true,
hideLabel: true
}
];

var form = new Ext.FormPanel({
id: formName,
monitorValid: true,
formLayout: true,
labelWidth: 120,
defaults: {anchor:'98%', border:false, xtype:'textfield'},
//height:200,			// Default config options for child items
frame: true,
//autoHeight: true,
autoWidth: true,
bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
border: true,
autoScroll:true,
style: {
"margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
},
items: itemsForm,
<% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) {%>
buttons: [{
id: 'save',
text: "<%=testiPortale.get("bottone_save")%>",
handler: function(){
Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cambio_stato")%>",
function(e) {
if (e=='yes'){
form.getForm().submit({url:'aggiorna',
waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
success: function(result,request) {
Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
ds.load({params: {start:0, size:grid_row}});
Ext.getCmp("azione_form").setValue('modifica');
Ext.getCmp("save").enable();
ds.load({params: {start:0, size:grid_row}});
winNotifica.hide();
},
failure:function(result,request) {
if (Ext.util.JSON.decode(request.response.responseText).failure.errore){
Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure.errore);
}else{
Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
}
}
});
}
});
}
},
{
id: 'aggiorna',
text: "<%=testiPortale.get("bottone_applica_notifica")%>",
handler: function(){
Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_applica_notifica")%>",
function(e) {
if (e=='yes'){
form.getForm().submit({url:'aggiorna?azione=applica',
waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
success: function(result,request) {
Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
ds.load({params: {start:0, size:grid_row}});
Ext.getCmp("azione_form").setValue('modifica');
Ext.getCmp("save").enable();
ds.load({params: {start:0, size:grid_row}});
winNotifica.hide();
},
failure:function(result,request) {
if (Ext.util.JSON.decode(request.response.responseText).failure.errore){
Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure.errore);
}else{
Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
}
}
});
}
});
}
}],
<% }%>
buttonAlign: 'center'
});
var winNotifica = new Ext.Window({
layout: 'fit',
closable:true,
closeAction:'hide',
width:900,
title:"<%=testiPortale.get(nomePagina + "_header_form")%>",
height:450,
border:false,
maximizable: true,
autoScroll: true,
plain:true,
modal:true,
items: [form],
buttons: [{
text: "<%=testiPortale.get("bottone_close")%>",
handler: function(){winNotifica.hide();}
}],
display: function(){
this.show();
	                }
});


tree.render(menu_div);
tree.selectPath("treepanel/source/<%=set_id%>",'id');

});
