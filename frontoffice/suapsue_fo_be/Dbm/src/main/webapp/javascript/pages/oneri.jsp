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
<%@page import="java.util.List"%>
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
    List<String> lingueAggregazione = utente.getLingue();
    String set_id = request.getParameter("set_id");
    boolean territorio = ((Boolean) session.getAttribute("territorialitaNew")).booleanValue();
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
        var tipoAggregazione = null;
        <% if (utente.getTipAggregazione() != null){%>
            tipoAggregazione = '<%=utente.getTipAggregazione()%>';
        <%}%>
        var impAccNumberField = new Ext.form.NumberField({
            id:'imp_acc',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_imp_acc")%>",
            name: 'imp_acc',
            allowNegative: false,
            allowDecimals: true,
            decimalPrecision : 2,
            minValue:0
        });

        var triggerCud = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_cud',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_cud")%>",
            name: 'cod_cud',
            window: winCud,
            hideTrigger1:true,
            clearButton: true,
            lookupFields:['cod_cud','des_cud'],
            windowLink:"<%=basePath%>protected/Cud.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_cud'],
            formName: formName,
            url: urlScriviSessione,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'
        });
        var triggerDocOneri = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_doc_onere',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_doc_onere")%>",
            name: 'cod_doc_onere',
            window: winDocOneri,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_doc_onere','des_doc_onere'],
            windowLink:"<%=basePath%>protected/OneriDocumenti.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_doc_onere'],
            formName: formName,
            url: urlScriviSessione
        });
        var triggerOneriGerarchia = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_padre',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_padre")%>",
            name: 'cod_padre',
            window: winOneriGerarchia,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_padre','des_gerarchia'],
            windowLink:"<%=basePath%>protected/OneriGerarchia.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_padre'],
            formName: formName,
            url: urlScriviSessione
        });

        //Aggregazioni
        var triggerAggregazioni = new Ext.ux.form.SearchTripleFieldForm({
            id: 'tip_aggregazione',
            fieldLabel:"<%=testiPortale.get("Generic_field_form_tip_aggregazione")%>",
            name: 'tip_aggregazione',
            window: winAggregazioni,
            hideTrigger1:false,
            hideTrigger3:true,
            clearButton: true,
            lookupFields:['tip_aggregazione','des_aggregazione'],
            windowLink:"<%=basePath%>protected/Aggregazioni.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['tip_aggregazione'],
            formName: formName,
            url: urlScriviSessione
        });  
        var table_name = 'oneri';
        var sort_field = 'cod_oneri';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'cod_oneri'},
            {name: 'des_oneri_it'},
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {name: 'des_oneri_<%=lingueAggregazione.get(i)%>'},
    <%}%>   
                {name: 'cod_cud'},
                {name: 'des_cud'},
                {name: 'cod_doc_onere'},
                {name: 'des_doc_onere'},
                {name: 'cod_padre'},
                {name: 'des_gerarchia'},
                {name: 'imp_acc'},
                {name: 'tip_aggregazione'},
                {name: 'des_aggregazione'},  
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {name: 'note_<%=lingueAggregazione.get(i)%>'},
    <%}%>   
// PC - Oneri MIP - inizio
                {name: 'identificativo_contabile'},
// PC - Oneri MIP - fine
                {name: 'note_it'},
                {name: 'cumulabile'},
                {name: 'cumulabile_grid'}
            ];
            var dyn_fields_protection = ['cod_oneri'];

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
            });

            ds.load({params: {start:0, size:grid_row}});

            ds.on('loadexception', function(event, options, response, error) {
                var json = Ext.decode(response.responseText);
                Ext.Msg.alert('Errore', json.error);
            })

            var colModel = new Ext.grid.ColumnModel([
                {
                    id:'grid_cod_oneri',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_oneri")%>",
                    width: 50,
                    sortable: true,
                    dataIndex: 'cod_oneri'
                },
                {
                    id:'grid_des_oneri',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_des_oneri")%>",
                    renderer: renderTpl,
                    width: 250,
                    sortable: true,
                    dataIndex: 'des_oneri_it'
                },
                {
                    id:'grid_note',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_note")%>",
                    renderer: renderTpl,
                    width: 250,
                    sortable: true,
                    dataIndex: 'note_it'
                },{
                    id:'grid_des_cud',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_des_cud")%>",
                    renderer: renderTpl,
                    width: 250,
                    sortable: true,
                    dataIndex: 'des_cud'
                },{
                    id:'grid_imp_acc',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_imp_acc")%>",
                    width: 250,
                    sortable: true,
                    dataIndex: 'imp_acc'
                },{
                    id:'grid_cumulabile',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_cumulabile")%>",
                    width: 10,
                    sortable: true,
                    dataIndex: 'cumulabile_grid'
                }
                <% if (territorio) { %>
                 ,{
                  id:'aggregazione',
                  header: "<%=testiPortale.get("Generic_header_grid_aggregazione")%>",
                  renderer: renderTpl,
                  width: 20,
                  sortable: true,
                  dataIndex: 'tip_aggregazione'
                  }
                <%}%> 
            ]);

            var txtFld = new Ext.form.TextField({
                id: 'cod_oneri',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_oneri")%>",
                name: 'cod_oneri',
                allowBlank: false,
                maxLength: 8,
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
                    id: 'des_oneri_it',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_oneri")%>",
                    name: 'des_oneri_it',
                    allowBlank: false,
                    maxLength: 64000,
                    labelStyle: 'font-weight:bold;'
                },
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {
                    id: 'des_oneri_<%=lingueAggregazione.get(i)%>',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_oneri")%>(<%=lingueAggregazione.get(i)%>)",
                    name: 'des_oneri_<%=lingueAggregazione.get(i)%>',
                    allowBlank: false,
                    maxLength: 64000,
                    labelStyle: 'font-weight:bold;'
                },
    <%}%> 
                {
                    id: 'note_it',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_note")%>",
                    maxLength: 64000,
                    name: 'note_it'
                },
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {
                    id: 'note_<%=lingueAggregazione.get(i)%>',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_note")%>(<%=lingueAggregazione.get(i)%>)",
                    name: 'note_<%=lingueAggregazione.get(i)%>',
                    maxLength: 64000
                },
    <%}%> 
                triggerDocOneri,{
                    id: 'des_doc_onere',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_doc_onere")%>",
                    readOnly: true,
                    name: 'des_doc_onere'
                },triggerCud,{
                    id: 'des_cud',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_cud")%>",
                    readOnly: true,
                    name: 'des_cud'
                },triggerOneriGerarchia,{
                    id: 'des_gerarchia',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_gerarchia")%>",
                    readOnly: true,
                    name: 'des_gerarchia'
                },impAccNumberField
// PC - Oneri MIP - inizio
                ,{
                    id: 'identificativo_contabile',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_identificativo_contabile")%>",
                    name: 'identificativo_contabile',
                    maxLength: 45
                }
// PC - Oneri MIP - fine				
				,{
                    id: 'cumulabile',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cumulabile")%>",
                    xtype: 'checkbox',
                    readOnly: false,
                    name: 'cumulabile',
                    checked: false
                }
                <% if (territorio) { %>
                   ,triggerAggregazioni,{
                    id: 'des_aggregazione',
                    fieldLabel: "<%=testiPortale.get("Generic_field_form_des_aggregazione")%>",
                    name: 'des_aggregazione',
                    readOnly: true
                   } 
                <%}%>
                ,{
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
                        Ext.getCmp("insert").disable();
                        Ext.getCmp("azione_form").setValue('modifica');
                        if (tipoAggregazione == rec.data.tip_aggregazione || rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined || tipoAggregazione == null) {
                            Ext.getCmp("save").enable();
                            Ext.getCmp("cancel").enable();
                        } else {
                            Ext.getCmp("save").disable();
                            Ext.getCmp("cancel").disable();
                        }
                        if (rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined) {
                            triggerAggregazioni.enable();
                        } else {
                            triggerAggregazioni.disable();
                        }                        
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
                buttons: [
    <% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) {%>
                    {
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
                                    if (Ext.getCmp("tip_aggregazione").getValue() != '') {
                                        triggerAggregazioni.disable();
                                    } else {
                                        triggerAggregazioni.enable();
                                    } 
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
                        handler:  function(){
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
                                    if (Ext.getCmp("tip_aggregazione").getValue() != '') {
                                       triggerAggregazioni.disable();
                                    } else {
                                       triggerAggregazioni.enable();
                                    } 
                                    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                        Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                                    }
                                },
                                failure:function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            });
                        }
                    },
    <% }%>
                    {
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
            cod_oneri=Ext.getUrlParam('cod_oneri');
            if (cod_oneri) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {'cod_oneri':cod_oneri, 'table_name':table_name},
                    success: function(response,request) {
                        try {o = Ext.decode(response.responseText);}
                        catch(e) {
                            this.showError(response.responseText);
                            return;
                        }
                        if(!o.success) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }else {
                            var modifica=0;
                            var dati=Ext.util.JSON.decode(response.responseText).success[table_name][0];
                            for (i in itemsForm) {
                                if (dati[itemsForm[i].name] != null) {
                                    Ext.getCmp(itemsForm[i].id).setValue(dati[itemsForm[i].name])
                                    modifica++;
                                }
                            }
                            if (modifica > 0) {
                                Ext.getCmp('azione_form').setValue('modifica');
                                Ext.getCmp("insert").disable();
                                for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                    Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                                }
                                if (tipoAggregazione == dati['tip_aggregazione'] || dati['tip_aggregazione'] == '' || dati['tip_aggregazione'] == undefined || tipoAggregazione == null) {
                                    Ext.getCmp("save").enable();
                                    Ext.getCmp("cancel").enable();
                                } else {
                                    Ext.getCmp("save").disable();
                                    Ext.getCmp("cancel").disable();
                                }
                                if (dati['tip_aggregazione'] == '' || dati['tip_aggregazione'] == undefined) {
                                   triggerAggregazioni.enable();
                                } else {
                                   triggerAggregazioni.disable();
                                }
                            }
                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            }

            tree.render(menu_div);

            tree.selectPath("treepanel/source/<%=set_id%>",'id');
        });
