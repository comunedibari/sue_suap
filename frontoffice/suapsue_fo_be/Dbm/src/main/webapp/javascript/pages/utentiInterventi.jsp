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

        var table_name = 'utenti_interventi';
        var sort_field = 'a.cod_utente';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'cod_utente'},
            {name: 'cognome_nome'},
            {name: 'cod_int'},
            {name: 'tit_int'}
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
            sortInfo: {field:sort_field,direction:'ASC'},
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
                id:'grid_cod_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_int'
            },
            {
                id:'grid_tit_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int")%>",
                width: 100,
                sortable: true,
                dataIndex: 'tit_int'
            }
        ]);
        //Utenti
        var sort_field_utenti = 'a.cod_utente';
        var expand_field_utenti = '';
        var table_name_utenti = 'utenti';
        var id_field_utenti = '';
        var dyn_fields_utenti = [
            {name: 'cod_utente'},
            {name: 'cognome_nome'}];
        var readerUtenti = new Ext.data.JsonReader({
            root:table_name_utenti,
            fields: dyn_fields_utenti,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_utenti
        });


        var dsUtenti = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_utenti}),
            sortInfo: {field:sort_field_utenti,direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerUtenti
        });

        dsUtenti.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })
        var searchSelectionModelUtenti = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp(winUtenti.lookupFields[0]).setValue(rec.data.cod_utente);
                    Ext.getCmp(winUtenti.lookupFields[1]).setValue(rec.data.cognome_nome);
                    winUtenti.hide();
                }
            }
        });

        var colModelUtenti = new Ext.grid.ColumnModel([
            {
                id:'cod_utente_search',
                header: "<%=testiPortale.get(nomePagina + "_header_cod_utente")%>",
                width: 150,
                sortable: true,
                dataIndex: 'cod_utente'
            },
            {
                id:'cognome_nome_search',
                header: "<%=testiPortale.get(nomePagina + "_header_cognome_nome")%>",
                width: 850,
                sortable: true,
                dataIndex: 'cognome_nome'
            }
        ]);
        var searchUtenti = new Ext.ux.form.SearchField({
            store: dsUtenti,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var gridUtenti = new Ext.grid.GridPanel({
            id:'grid_utenti',
            frame:true,
            ds: dsUtenti,
            cm: colModelUtenti,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelUtenti,
            oadMask: true,
            autoExpandColumn: expand_field_utenti,
            height:500,
            bbar: new Ext.PagingToolbar({
                store: dsUtenti,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchUtenti]
            }),
            border: true
        });
        var winUtenti = new Ext.Window({
            title: "<%=testiPortale.get(nomePagina + "_titolo_window_utenti")%>",
            closable:true,
            closeAction:'hide',
            width:900,
            height:500,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [gridUtenti],
            buttons: [{
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){winUtenti.hide();}
                }],
            display: function(){
                dsUtenti.reload({params:{start: 0, size:grid_row}});
                this.show();
            },
            lookupFields: ['cod_utente','cognome_nome']
        });
        var triggerUtenti = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_utente',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_utente")%>",
            name: 'cod_utente',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            enableButtons: true,
            window:winUtenti,
            lookupFields:['cod_utente','cognome_nome'],
            windowLink:"<%=basePath%>protected/Utenti.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_utente'],
            formName: formName,
            url: urlScriviSessione
        });

        //Interventi
        var triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_int',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
            name: 'cod_int',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            enableButtons: true,
            window:winInterventi,
            lookupFields:['cod_int','tit_int'],
            windowLink:"<%=basePath%>protected/Interventi.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_int'],
            formName: formName,
            url: urlScriviSessione
        });
        //
        var itemsForm = [triggerUtenti,{
                id: 'cognome_nome',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cognome_nome")%>",
                name: 'cognome_nome',
                readOnly: true
            },triggerInterventi,{
                id: 'tit_int',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                name: 'tit_int',
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
                    Ext.getCmp("cancel").enable();
                    Ext.getCmp("insert").disable();
                    triggerUtenti.setDisabled();
                    Ext.getCmp("azione_form").setValue('modifica');
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
                                        Ext.getCmp("insert").enable();
                                        Ext.getCmp("cancel").enable();
                                        Ext.getCmp("azione_form").setValue('inserimento');
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
                                    Ext.getCmp('insert').enable();
                                    Ext.getCmp('cancel').enable();
                                    triggerUtenti.setDisabled();
                                    Ext.getCmp("azione_form").setValue('modifica');

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
                        Ext.getCmp('cancel').disable();
                        triggerUtenti.setEnabled();
                        triggerInterventi.setEnabled();
                        Ext.getCmp("azione_form").setValue('inserimento');
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
                    Ext.getCmp('cancel').enable();
                    triggerUtenti.setEnabled();
                }
                if (Ext.getDom('azione_form').value == 'modifica') {
                    Ext.getCmp("insert").enable();
                    Ext.getCmp("cancel").enable();
                    if (Ext.getDom("cod_int").value != '') {
                        Ext.getCmp('insert').disable();
                    }
                    triggerUtenti.setDisabled();
                }
            },
            failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
            }
        });


        tree.render(menu_div);
        tree.selectPath("treepanel/source/<%=set_id%>",'id');

    });
