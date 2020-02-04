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

        var table_name = 'utenti';
        var sort_field = 'cod_utente';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        function changeRuolo(val){
            if(val == 'A'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_A")%>";
            }else if(val == 'B'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_B")%>";
            }else if(val == 'C'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_C")%>";
            }else if(val == 'D'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_D")%>";
            }
            return val;
        };
        var comboRuolo = new Ext.form.ComboBox({
            id: 'ruolo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ruolo")%>",
            name: 'ruolo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'ruolo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_ruolo_select")%>",
            editable: false,
            store: new Ext.data.ArrayStore({
                fields: ['ruolo','displayText'],
                data: [['A', "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_A")%>"],
                    ['B', "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_B")%>"],
                    ['C', "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_C")%>"],
                    ['D', "<%=testiPortale.get(nomePagina + "_field_form_combo_ruolo_D")%>"]]
            }),
            valueField: 'ruolo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });

        var dyn_fields = [
            {name: 'cod_utente'},
            {name: 'cognome_nome'},
            {name: 'ruolo'},
            {name: 'email'},
            {name: 'cod_utente_padre'},
            {name: 'cognome_nome_padre'},
            {name: 'tip_aggregazione'},
            {name: 'des_aggregazione'}
        ];

        var dyn_fields_protection = ['cod_utente'];

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
        //Utenti
        var sort_field_utenti_padre = 'cod_utente_padre';
        var expand_field_utenti_padre = '';
        var table_name_utenti_padre = 'utenti_padre';
        var id_field_utenti_padre = '';
        var dyn_fields_utenti_padre = [
            {name: 'cod_utente_padre'},
            {name: 'cognome_nome_padre'}];
        var readerUtentiPadre = new Ext.data.JsonReader({
            root:table_name_utenti_padre,
            fields: dyn_fields_utenti_padre,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_utenti_padre
        });


        var dsUtentiPadre = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_utenti_padre}),
            sortInfo: {field:sort_field_utenti_padre,direction:'DESC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerUtentiPadre
        });

        dsUtentiPadre.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })
        var searchSelectionModelUtentiPadre = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp(winUtentiPadre.lookupFields[0]).setValue(rec.data.cod_utente_padre);
                    Ext.getCmp(winUtentiPadre.lookupFields[1]).setValue(rec.data.cognome_nome_padre);
                    winUtentiPadre.hide();
                }
            }
        });

        var colModelUtentiPadre = new Ext.grid.ColumnModel([
            {
                id:'cod_utente_padre_search',
                header: "<%=testiPortale.get(nomePagina + "_header_cod_utente_padre")%>",
                width: 150,
                sortable: true,
                dataIndex: 'cod_utente_padre'
            },
            {
                id:'cognome_nome_padre_search',
                header: "<%=testiPortale.get(nomePagina + "_header_cognome_nome_padre")%>",
                width: 850,
                sortable: true,
                dataIndex: 'cognome_nome_padre'
            }
        ]);
        var searchUtentiPadre = new Ext.ux.form.SearchField({
            store: dsUtentiPadre,
            width:320,
            paging: true,
            paging_size: grid_row
        });

        var gridUtentiPadre = new Ext.grid.GridPanel({
            id:'grid_utenti_padre',
            frame:true,
            ds: dsUtentiPadre,
            cm: colModelUtentiPadre,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelUtentiPadre,
            oadMask: true,
            autoExpandColumn: expand_field_utenti_padre,
            height:450,
            bbar: new Ext.PagingToolbar({
                store: dsUtentiPadre,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchUtentiPadre]
            }),
            border: true
        });
        var winUtentiPadre = new Ext.Window({
            title: "<%=testiPortale.get(nomePagina + "_titolo_window_utenti_padre")%>",
            closable:true,
            closeAction:'hide',
            width:900,
            height:550,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [gridUtentiPadre],
            buttons: [{
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){winUtentiPadre.hide();}
                }],
            display: function(){
                dsUtentiPadre.reload({params:{start: 0, size:grid_row}});
                this.show();
            },
            lookupFields: ['cod_utente_padre','cognome_nome_padre']
        });
        var triggerUtentiPadre = new Ext.ux.form.SearchFieldForm({
            id: 'cod_utente_padre',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_utente_padre")%>",
            name: 'cod_utente_padre',
            window: winUtentiPadre,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_utente_padre','cognome_nome_padre'],
            windowLink:"<%=basePath%>protected/UtentiPadre.jsp?set_id=<%=set_id%>",
            formName: formName,
            url: urlScriviSessione
        });
        //Aggregazioni
        var triggerAggregazioni = new Ext.ux.form.SearchTripleFieldForm({
            id: 'tip_aggregazione',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_tip_aggregazione")%>",
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
        var colModel = new Ext.grid.ColumnModel([
            {
                id:'grid_cod_utente',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_utente")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_utente'
            },
            {
                id:'grid_cognome_nome',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cognome_nome")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'cognome_nome'
            },
            {
                id:'grid_email',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_email")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'email'
            },
            {
                id:'cod_utente_padre',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_utente_padre")%>",
                renderer: renderTpl,
                width: 50,
                sortable: true,
                dataIndex: 'cod_utente_padre'
            },
            {
                id:'cognome_nome_padre',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cognome_nome_padre")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'cognome_nome_padre'
            },{
                id:'ruolo',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_ruolo")%>",
                renderer: renderTpl,
                width: 50,
                sortable: true,
                dataIndex: 'ruolo',
                renderer: changeRuolo
            },{
                id:'aggregazione',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_aggregazione")%>",
                renderer: renderTpl,
                width: 50,
                sortable: true,
                dataIndex: 'des_aggregazione'
            }
        ]);
        var txtFld = new Ext.form.TextField({
            id: 'cod_utente',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_utente")%>",
            name: 'cod_utente',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            maxLength:16,
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
                id: 'cognome_nome',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cognome_nome")%>",
                name: 'cognome_nome',
                allowBlank: false,
                maxLength:45,
                labelStyle: "font-weight:bold;"
            },{
                id: 'email',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_email")%>",
                name: 'email',
                vtype:'email',
                allowBlank: false,
                maxLength:100,
                labelStyle: 'font-weight:bold;'
            },comboRuolo,triggerUtentiPadre,{
                id: 'cognome_nome_padre',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cognome_nome_padre")%>",
                name: 'cognome_nome_padre',
                readOnly: true
            },triggerAggregazioni,{
                id: 'des_aggregazione',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_aggregazione")%>",
                name: 'des_aggregazione',
                readOnly: true
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
                    id: 'save',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    disabled:true,
                    handler: function(){
                        if(form.getForm().isValid()){
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
                        if(form.getForm().isValid()){
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
            },
            failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
            }
        });
        cod_utente=Ext.getUrlParam('cod_utente');
        if (cod_utente) {
            Ext.Ajax.request({
                url: 'leggiRecord',
                method: 'POST',
                params: {'cod_utente':cod_utente,'table_name':table_name},
                success: function(response,request) {
                    try {o = Ext.decode(response.responseText);}
                    catch(e) {
                        this.showError(response.responseText);
                        return;
                    }
                    if(!o.success) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }else {
                        var modifica=0
                        for (i in itemsForm) {
                            var dati=Ext.util.JSON.decode(response.responseText).success[table_name][0];
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
                            for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
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
