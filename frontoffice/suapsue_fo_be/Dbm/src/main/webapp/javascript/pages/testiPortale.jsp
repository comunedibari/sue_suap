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
var formName = 'edit-form';
var form_div = Ext.get('form');
var grid_div = Ext.get('grid');
var menu_div = Ext.get('tree_container');

var table_name = 'html_testi';
var sort_field = 'cod_testo';

var id_field = '';
var group_field = '';
var expand_field = '';

var dyn_fields = [
{name: 'cod_testo'},
{name: 'des_testo'}
];
var dyn_fields_protection = ['cod_testo'];

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
});

var colModel = new Ext.grid.ColumnModel([
{
id:'grid_cod_testo',
header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_testo")%>",
width: 150,
renderer: renderTpl,
sortable: true,
dataIndex: 'cod_testo'
},
{
id:'grid_des_testo',
header: "<%=testiPortale.get(nomePagina + "_header_grid_des_testo")%>",
renderer: renderTpl,
width: 700,
sortable: true,
dataIndex: 'des_testo'
}
]);
var txtFld = new Ext.form.TextField({
id: 'cod_testo',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_testo")%>",
name: 'cod_testo',
allowBlank: false,
maxLength: 255,
labelStyle: 'font-weight:bold;',
enableKeyEvents: true
});
txtFld.on('blur', function(a) {
if (Ext.getCmp("azione_form").getValue() != 'modifica') {
if (this.getValue() != "") {
Ext.Ajax.request({
url: 'leggiEsistenza',
method: 'POST',
params: {table_name:table_name, tab_key:this.getValue()},
success: function(response,request) {
try {o = Ext.decode(response.responseText);}
catch(e) {
this.showError(response.responseText);
return;
}
if(!o.success) {
txtFld.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
} else {
txtFld.clearInvalid();
}
},
failure: function(response,request) {
Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
}
});
}
}
});

var	itemsForm = [txtFld,{
id: 'des_testo',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_testo")%>",
name: 'des_testo',
xtype:'textarea',
height: 75,
allowBlank: false,
labelStyle: 'font-weight:bold;'
},{
id: 'azione_form',
fieldLabel: 'azione form',
name: 'azione_form',
readOnly: true,
hidden: true,
hideLabel: true
},{
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
var searchSelectionModel = new Ext.grid.RowSelectionModel({
singleSelect: true,
listeners: {
rowselect: function(sm, row, rec) {
Ext.getCmp(formName).getForm().loadRecord(rec);
Ext.getCmp("save").enable();
Ext.getCmp("cancel").enable();
Ext.getCmp("insert").disable();
Ext.getCmp("azione_form").setValue('modifica');
for (var ele=0;ele < dyn_fields_protection.length;ele++) {
    Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
    }
    }
    }
    });


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
    formLayout: true,
    labelWidth: 120,
    title: "<%=testiPortale.get(nomePagina + "_header_form")%>",
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
    id: 'save',
    text: "<%=testiPortale.get("bottone_save")%>",
    disabled:true,
    handler: function(){
    form.getForm().submit({url:'aggiorna',
    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
    success: function(result,request) {
    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
    ds.load({params: {start:0, size:grid_row}});
    Ext.getCmp("azione_form").setValue('modifica');
    Ext.getCmp("save").enable();
    Ext.getCmp("insert").disable();
    Ext.getCmp("cancel").enable();
    },
    failure:function(result,request) {
    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
    }
    });
    }
    },{
    id: 'cancel',
    text: "<%=testiPortale.get("bottone_delete")%>",
    disabled:true,
    handler: function(){
    Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
    function(e) {
    if (e=='yes'){
    form.getForm().submit({url:'cancella',
    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
    success: function(result,request) {
    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
    ds.load({params: {start:0, size:grid_row}});
    Ext.getCmp("save").disable();
    Ext.getCmp("insert").enable();
    Ext.getCmp("cancel").disable();
    Ext.getCmp("azione_form").setValue('inserimento');
    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
        Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
        }
        },
        failure:function(result,request) {
        Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
        }
        })
        }
        }
        );
        }
        },{
        id: 'insert',
        text: "<%=testiPortale.get("bottone_insert")%>",
        handler: function(){
        form.getForm().submit({url:'inserisci',
        waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
        success: function(result,request) {
        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
        ds.load({params: {start:0, size:grid_row}});
        Ext.getCmp('save').enable();
        Ext.getCmp('insert').disable();
        Ext.getCmp('cancel').enable();
        Ext.getCmp("azione_form").setValue('modifica');
        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
            Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
            }
            },
            failure:function(result,request) {
            Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
            }
            });
            }
            },{
            id: 'reset',
            text: "<%=testiPortale.get("bottone_reset")%>",
            handler: function(){
            form.getForm().reset();
            Ext.getCmp('insert').enable();
            Ext.getCmp('save').disable();
            Ext.getCmp('cancel').disable();
            Ext.getCmp("azione_form").setValue('inserimento');
            for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                }
                }
                }],
                buttonAlign: 'center',
                renderTo: form_div
                });
                Ext.Ajax.request({
                url: 'leggiSessione',
                method: 'POST',
                params: {'table_name':table_name},
                success: function(response,request) {
                try {o = Ext.decode(response.responseText);}
                catch(e) {
                this.showError(response.responseText);
                return;
                }
                if(!o.success) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }else {
                for (ele in Ext.util.JSON.decode(response.responseText).success) {
                if (Ext.getCmp(ele)) {
                if (Ext.util.JSON.decode(response.responseText).success[ele] != '') {
                Ext.getCmp(ele).setValue(Ext.util.JSON.decode(response.responseText).success[ele]);
                }
                }
                }
                if (Ext.getDom('azione_form').value == 'inserimento') {
                Ext.getCmp('insert').enable();
                Ext.getCmp('save').disable();
                Ext.getCmp('cancel').disable();
                }
                if (Ext.getDom('azione_form').value == 'modifica') {
                Ext.getCmp("save").enable();
                Ext.getCmp("insert").disable();
                Ext.getCmp("cancel").enable();
                }
                }
                },
                failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
                });

                tree.render(menu_div);

	tree.selectPath("treepanel/source/<%=set_id%>",'id');

                });
