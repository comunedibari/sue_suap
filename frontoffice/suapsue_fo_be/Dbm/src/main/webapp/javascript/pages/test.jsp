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


var dyn_fields_protection = ['cod_sport', 'cod_oneri'];

var triggerSportelli = new Ext.ux.form.SearchTripleFieldForm({
id: 'cod_sport',
fieldLabel:"Codice sportello",
name: 'cod_sport',
window: winSportelli,
hideTrigger1:true,
hideTrigger3:true,
clearButton: false,
lookupFields:['cod_sport','des_sport'],
windowLink: false,
formName: formName,
labelStyle: 'font-weight:bold;',
url: false,
enableKeyEvents: true,
allowBlank: true,
setValueCustom: function(r) {
this.setValue(r);
}
});


                                       var fileUpload = new Ext.ux.form.FileUploadField ({
                                            xtype: 'fileuploadfield',
                                            id: 'form-file',
                                            fieldLabel: "<%=testiPortale.get("form_upload_file_name")%>",
                                            allowBlank: false,
                                            labelStyle: 'font-weight:bold;',
                                            name: 'document_path',
                                            buttonText: '',
                                            anchor:'95%',
                                            hideContainer: false,
                                            buttonCfg: {
                                                iconCls: 'upload-icon'
                                            }
                                        });




var triggerOneri = new Ext.ux.form.SearchTripleFieldForm({
id: 'cod_oneri',
fieldLabel:"Codice onere",
name: 'cod_oneri',
window: winOneri,
hideTrigger1:true,
hideTrigger3:true,
clearButton: false,
lookupFields:['cod_oneri','des_oneri'],
windowLink: false,
formName: formName,
labelStyle: 'font-weight:bold;',
url: false,
enableKeyEvents: true,
allowBlank: true
});



var triggerFae = new Ext.ux.form.SearchTripleFieldForm({
id: 'codice_ente',
fieldLabel:"Codice ente fae",
name: 'codice_ente',
window: winFae,
hideTrigger1:true,
hideTrigger3:true,
clearButton: false,
lookupFields:['codice_ente','tipologia_ufficio', 'codice_ufficio'],
windowLink: false,
formName: formName,
labelStyle: 'font-weight:bold;',
url: false,
enableKeyEvents: true,
allowBlank: true
});


var	itemsForm = [triggerSportelli,{
id: 'des_sport',
fieldLabel: "Descrizione sportello",
name: 'des-sport',
xtype:'textarea',
height: 37,
readOnly: true
},

triggerOneri,{
id: 'des_oneri',
fieldLabel: "Descrizione onere",
name: 'des-oneri',
xtype:'textarea',
height: 37,
readOnly: true
}, 


triggerFae,{
id: 'tipologia_ufficio',
fieldLabel: "Tipologia ufficio fae",
name: 'tipologia_ufficio',
xtype:'textarea',
height: 37,
readOnly: true
},
{
id: 'codice_ufficio',
fieldLabel: "Codice ufficio fae",
name: 'codice_ufficio',
xtype:'textarea',
height: 37,
readOnly: true
}, 


{
                    xtype: 'fileuploadfield',
                    id: 'form-file',
                    emptyText: "<%=testiPortale.get("form_upload_file_empty")%>",
                    fieldLabel: "<%=testiPortale.get("form_upload_file_name")%>",
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;',
                    name: 'document_path',
                    buttonText: '',
                    buttonCfg: {
                        iconCls: 'upload-icon'
                    }
                }



];






var form = new Ext.FormPanel({
id: formName,
fileUpload: true,
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
text: "Carica",
formBind:false,
handler: function(){

                            form.getForm().submit({
                                url: 'uploadDocumentccd',
                                waitMsg: "Attendere prego...",
                                success: function(response,request){
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
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

for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                            }
                            tree.render(menu_div);
                            tree.selectPath("treepanel/source/<%=set_id %>");
                            });