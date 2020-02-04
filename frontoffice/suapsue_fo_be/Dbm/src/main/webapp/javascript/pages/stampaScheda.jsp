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


var dyn_fields_protection = ['cod_com','cod_int'];
var triggerComuni = new Ext.ux.form.SearchTripleFieldForm({
id: 'cod_com',
fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_com")%>",
name: 'cod_com',
window: winComuni,
hideTrigger1:true,
hideTrigger3:true,
clearButton: false,
lookupFields:['cod_com','des_ente'],
windowLink: false,
formName: formName,
labelStyle: 'font-weight:bold;',
url: false,
enableKeyEvents: true,
allowBlank: false,
setValueCustom: function(r) {
this.setValue(r);
triggerInterventi_totali.setEnabled();
triggerInterventi_totali.setDisableButton1();
triggerInterventi_totali.setEnableButton2();
triggerInterventi_totali.setDisableButton3();
Ext.getCmp('cod_int').setValue("");
Ext.getCmp('tit_int').setValue("");
dsInterventi_totali.baseParams.cod_com = r;
}
});

var triggerInterventi_totali = new Ext.ux.form.SearchTripleFieldForm({
id: 'cod_int',
fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
name: 'cod_int',
window: winInterventi_totali,
hideTrigger1:true,
hideTrigger3:true,
clearButton: false,
lookupFields:['cod_int','tit_int'],
windowLink: false,
formName: formName,
labelStyle: 'font-weight:bold;',
allowBlank: false,
url: false
});

var	itemsForm = [triggerComuni,{
id: 'des_ente',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ente")%>",
name: 'des-ente',
xtype:'textarea',
height: 37,
readOnly: true
},triggerInterventi_totali,{
id: 'tit_int',
fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
name: 'tit_int',
xtype:'textarea',
height: 37,
readOnly: true
}];

var form = new Ext.FormPanel({
id: formName,
monitorValid: true,
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
id: 'stampa',
text: "<%=testiPortale.get("bottone_stampa")%>",
formBind:true,
handler: function(){
var url='StampaScheda/creaRTF?cod_int='+Ext.getCmp("cod_int").getValue()+'&cod_com='+Ext.getCmp("cod_com").getValue();
myWindow=window.open(url, "_blank", "height=300, width=500,toolbar=no,scrollbars=no,menubar=no");
}
},{
id: "reset",
text: "<%=testiPortale.get("bottone_reset")%>",
handler: function(){
form.getForm().reset();
for (var ele=0;ele < dyn_fields_protection.length;ele++) {
    Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
    }
    }
    }],
    buttonAlign: "center",
    renderTo: form_div
    });

    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
        Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute("readOnly", true);
        }
        triggerInterventi_totali.setDisabled();

        tree.render(menu_div);

        tree.selectPath("treepanel/source/<%=set_id%>","id");

        });
