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
        var tipoAggregazione = null;
        <% if (utente.getTipAggregazione() != null){%>
            tipoAggregazione = '<%=utente.getTipAggregazione()%>';
        <%}%>

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

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
        var formNameWindow = 'edit-form-window';
        var	itemsFormWindow = [{
                id: 'val_select',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_val_select")%>",
                name: 'val_select',
                maxLength:255,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
            {
                    id: 'des_val_select_it',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_val_select")%>",
                    name: 'des_val_select_it',
                    maxLength:255,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
            }
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                ,{
                    id: 'des_val_select_<%=lingueAggregazione.get(i)%>',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_val_select")%>(<%=lingueAggregazione.get(i)%>)",
                    name: 'des_val_select_<%=lingueAggregazione.get(i)%>',
                    maxLength:255,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                }
    <%}%> 
            ];

            var formOneriCampiSelect = new Ext.FormPanel({
                id: formNameWindow,
                monitorValid: true,
                title: "<%=testiPortale.get(nomePagina + "_header_form_campi_select")%>",
                formLayout: true,
                labelWidth: 120,
                defaults: {width: 600, border:false},
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
                items: itemsFormWindow,
                buttons: [{
                        id: 'saveWindow',
                        text: "<%=testiPortale.get("bottone_save")%>",
                        handler: function(){
                            formOneriCampiSelect.getForm().submit({url:'gestisciCampoSelectSession?operazione=aggiungi&table_name='+table_name_campi_select,
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                success: function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    dsCampiSelect.load({params: {cod_onere_campo:Ext.getCmp("cod_onere_campo"),session:'yes'}});
                                    formOneriCampiSelect.getForm().reset();
                                    winOneriCampiSelect.hide();
                                },
                                failure:function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            });
                        }
                    },{
                        id: 'closeWindow',
                        text: "<%=testiPortale.get("bottone_close")%>",
                        handler: function(){winOneriCampiSelect.hide();}
                    }],
                buttonAlign: 'center'
            });

            var winOneriCampiSelect = new Ext.Window({
                closable:true,
                closeAction:'hide',
                width:900,
                height:180,
                border:false,
                maximizable: true,
                plain:true,
                modal:true,
                items: [formOneriCampiSelect],
                display: function(){
                    this.show();
                }
            });

            var table_name_campi_select = 'oneri_campi_select_testi';

            var dyn_fields_campi_select = [
                {name: 'val_select'},
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {name: 'des_val_select_<%=lingueAggregazione.get(i)%>'},
    <%}%> 
                {name: 'des_val_select_it'}
            ];

            var readerCampiSelect = new Ext.data.JsonReader({
                root:table_name_campi_select,
                fields: dyn_fields_campi_select,
                baseParams: {
                    lightWeight:true,
                    ext: 'js'
                }
            });

            var dsCampiSelect = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_campi_select}),
                remoteSort: true,
                reader: readerCampiSelect
            });
            var colModelCampiSelect = new Ext.grid.ColumnModel([
                {
                    id:'grid_val_select',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_val_select")%>",
                    width: 150,
                    sortable: true,
                    dataIndex: 'val_select'
                },
                {
                    id:'grid_des_val_select',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_des_val_select")%>",
                    renderer: renderTpl,
                    width: 640,
                    sortable: true,
                    dataIndex: 'des_val_select_it'
                }
            ]);
            var gridCampiSelectSelectionModel = new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                         if (tipoAggregazione == Ext.getCmp("tip_aggregazione").getValue() || Ext.getCmp("tip_aggregazione").getValue() == '' || Ext.getCmp("tip_aggregazione") == undefined || tipoAggregazione == null) {
                              Ext.getCmp("cancellaCampo").enable();
                         } else {
                              Ext.getCmp("aggiungiCampo").disable();
                              Ext.getCmp("cancellaCampo").disable();
                         }                     
                    }
                }
            });

            var gridCampiSelect = new Ext.grid.EditorGridPanel({
                store: dsCampiSelect,
                cm: colModelCampiSelect,
                height: 160,
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_grid_campi_select")%>",
                autoScroll:true,
                sm:gridCampiSelectSelectionModel,
                border: true,
                tbar: [{
                        text: "<%=testiPortale.get("bottone_grid_campi_select_aggiungi")%>",
                        id:'aggiungiCampo',
                        disabled:true,
                        listeners:{ 'click': function() {
                                winOneriCampiSelect.display();
                            }
                        }
                    },{xtype: 'tbseparator'},{
                        text: "<%=testiPortale.get("bottone_grid_campi_select_elimina")%>",
                        id: 'cancellaCampo',
                        disabled:true,
                        listeners:{
                            'click': function() {
                                Ext.Ajax.request({
                                    url: 'gestisciCampoSelectSession',
                                    method: 'POST',
                                    params: {'val_select':gridCampiSelect.getSelectionModel().getSelected().get('val_select'), 'table_name':table_name_campi_select ,'operazione':'cancella'},
                                    success: function(response,request) {
                                        try {o = Ext.decode(response.responseText);}
                                        catch(e) {
                                            this.showError(response.responseText);
                                            return;
                                        }
                                        if(!o.success) {
                                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                        }else {
                                            dsCampiSelect.load({params: {cod_onere_campo:Ext.getCmp("cod_onere_campo").value,session:'yes'}});
                                            Ext.getCmp("cancellaCampo").disable();
                                        }
                                    },
                                    failure: function(response,request) {
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                    }
                                });
                            }
                        }
                    }]
            });

            var lngCampoNumberField = new Ext.form.NumberField({
                id:'lng_campo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_lng_campo")%>",
                name: 'lng_campo',
                allowNegative: false,
                allowDecimals: false,
                minValue:0,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            });
            var lngDecNumberField = new Ext.form.NumberField({
                id:'lng_dec',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_lng_dec")%>",
                name: 'lng_dec',
                allowNegative: false,
                allowDecimals: false,
                minValue:0,
                allowBlank: true
            });
            function change(val){
                if(val == 'I'){
                    return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_campo_I")%>";
                }else if(val == 'L'){
                    return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_campo_L")%>";
                }else if(val == 'D'){
                    return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_campo_D")%>";
                }
                return val;
            };
            var comboTpCampo = new Ext.form.ComboBox({
                id: 'tp_campo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tp_campo")%>",
                name: 'tp_campo',
                typeAhead: true,
                triggerAction: 'all',
                lazyRender:true,
                mode: 'local',
                hiddenName: 'tp_campo_hidden',
                emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tp_campo_select")%>",
                store: new Ext.data.ArrayStore({
                    fields: ['tp_campo','displayText'],
                    data: [['I', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_campo_I")%>"],
                        ['L', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_campo_L")%>"],
                        ['D', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_campo_D")%>"]]
                }),
                listeners :{select: function(sm, row, rec) {
                        if (row.data.tp_campo == 'L') {
                            dsCampiSelect.load({params: {cod_onere_campo:Ext.getCmp("cod_onere_campo").value}});
                            Ext.getCmp("aggiungiCampo").enable();
                        } else {
                            dsCampiSelect.load({params: {cod_onere_campo:''}});
                            gridCampiSelect.removeAll();
                            Ext.getCmp("cancellaCampo").disable();
                            Ext.getCmp("aggiungiCampo").disable();
                        }
                        Ext.getCmp("accetta_valore_zero").setVisible(row.data.tp_campo != 'L');
                    }},
                
                valueField: 'tp_campo',
                displayField: 'displayText',
                selectOnFocus:true,
                editable: false,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'	,
                resizable:true
            });


            var table_name = 'oneri_campi';
            var sort_field = 'cod_onere_campo';

            var id_field = '';
            var group_field = '';
            var expand_field = '';

            var dyn_fields = [
                {name: 'cod_onere_campo'},
                {name: 'testo_campo_it'},
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {name: 'testo_campo_<%=lingueAggregazione.get(i)%>'},
    <%}%>   
                {name: 'tp_campo'},
                {name: 'lng_dec'},
                {name: 'tip_aggregazione'},
                {name: 'des_aggregazione'},                
                {name: 'lng_campo'},
                {name: 'accetta_valore_zero'}
            ];
            var dyn_fields_protection = ['cod_onere_campo'];

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
                    id:'grid_cod_onere_campo',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_onere_campo")%>",
                    width: 110,
                    sortable: true,
                    dataIndex: 'cod_onere_campo'
                },
                {
                    id:'grid_testo_campo',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_testo_campo")%>",
                    renderer: renderTpl,
                    width: 600,
                    sortable: true,
                    dataIndex: 'testo_campo_it'
                },
                {
                    id:'tp_campo',
                    header: "<%=testiPortale.get(nomePagina + "_header_grid_tp_campo")%>",
                    renderer: change,
                    width: 250,
                    sortable: true,
                    dataIndex: 'tp_campo'
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
                id: 'cod_onere_campo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_onere_campo")%>",
                name: 'cod_onere_campo',
                allowBlank: false,
                maxLength:10,
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
                    id: 'testo_campo_it',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_testo_campo")%>",
                    name: 'testo_campo_it',
                    maxLength:255,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {
                    id: 'testo_campo_<%=lingueAggregazione.get(i)%>',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_testo_campo")%>(<%=lingueAggregazione.get(i)%>)",
                    name: 'testo_campo_<%=lingueAggregazione.get(i)%>',
                    maxLength:255,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },
    <%}%> 
                comboTpCampo,

                 
                            {
                                id: 'accetta_valore_zero',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_accetta_valore_zero")%>",
                                xtype: 'checkbox',
                                name: 'accetta_valore_zero',
                                checked: true,
                                hidden: true
                            },                    

                
                lngCampoNumberField,lngDecNumberField,gridCampiSelect
             <% if (territorio) { %>
                ,triggerAggregazioni,
               {
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
                        dsCampiSelect.load({params: {cod_onere_campo:rec.data.cod_onere_campo}});
                        Ext.getCmp("insert").disable();
                        Ext.getCmp("azione_form").setValue('modifica');
                        if (tipoAggregazione == rec.data.tip_aggregazione || rec.data.tip_aggregazione == '' || tipoAggregazione == null) {
                            Ext.getCmp("save").enable();
                            Ext.getCmp("cancel").enable();
                            if (rec.data.tp_campo == 'L') {
                                Ext.getCmp("aggiungiCampo").enable();
                                Ext.getCmp("cancellaCampo").disable();
                            } else {
                                Ext.getCmp("aggiungiCampo").disable();
                                Ext.getCmp("cancellaCampo").disable();
                            }                            
                            Ext.getCmp("accetta_valore_zero").setVisible(rec.data.tp_campo != 'L');
                        } else {
                            Ext.getCmp("save").disable();
                            Ext.getCmp("cancel").disable();
                            Ext.getCmp("aggiungiCampo").disable();
                            Ext.getCmp("cancellaCampo").disable();                            
                        }
                        if (rec.data.tip_aggregazione == '' ||  rec.data.tip_aggregazione == undefined) {
                            triggerAggregazioni.enable();
                        } else {
                            triggerAggregazioni.disable();
                        }                        
                        for (var ele=0;ele  < dyn_fields_protection.length;ele++) {
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
                                    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                        Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                                    }
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
            cod_onere_campo=Ext.getUrlParam('cod_onere_campo');
            if (cod_onere_campo) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {'cod_onere_campo':cod_onere_campo, 'table_name':table_name},
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
                                Ext.getCmp("save").enable();
                                Ext.getCmp("insert").disable();
                                Ext.getCmp("cancel").enable();
                                dsCampiSelect.load({params: {cod_onere_campo:cod_onere_campo}});
                                if (tipoAggregazione == dati['tip_aggregazione'] || dati['tip_aggregazione'] == '' || dati['tip_aggregazione'] == undefined || tipoAggregazione == null) {
                                    Ext.getCmp("save").enable();
                                    Ext.getCmp("cancel").enable();
                                    if (Ext.getCmp("tp_campo").getValue() == 'L') {
                                        Ext.getCmp("aggiungiCampo").enable();
                                        Ext.getCmp("cancellaCampo").disable();
                                    } else {
                                        Ext.getCmp("aggiungiCampo").disable();
                                        Ext.getCmp("cancellaCampo").disable();
                                    }                                    
                                    Ext.getCmp("accetta_valore_zero").setVisible(Ext.getCmp("tp_campo").getValue() != 'L');
                                } else {
                                    Ext.getCmp("save").disable();
                                    Ext.getCmp("cancel").disable();
                                    Ext.getCmp("aggiungiCampo").disable();
                                    Ext.getCmp("cancellaCampo").disable();                                    
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
