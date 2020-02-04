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

        var table_name = 'relazioni_enti';
        var sort_field = 'cod_com';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'cod_com'},
            {name: 'des_ente'},
            {name: 'cod_dest'},
            {name: 'intestazione'},
            {name: 'cod_cud'},
            {name: 'des_cud'},
            {name: 'cod_sport'},
            {name: 'des_sport'},
            {name: 'riversamento_automatico'}
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

        var colModel = new Ext.grid.ColumnModel([
            {
                id:'grid_cod_com',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_com")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_com'
            },
            {
                id:'grid_des_ente',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_ente")%>",
                renderer: renderTpl,
                width: 200,
                sortable: true,
                dataIndex: 'des_ente'
            },
            {
                id:'grid_cod_dest',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_dest")%>",
                width: 30,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'cod_dest'
            },
            {
                id:'grid_intestazione',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_intestazione")%>",
                width: 200,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'intestazione'
            },
            {
                id:'grid_cod_cud',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_cud")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_cud',
                renderer: renderTpl
            },
            {
                id:'grid_des_cud',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_cud")%>",
                width: 200,
                sortable: true,
                dataIndex: 'des_cud',
                renderer: renderTpl
            },
            {
                id:'grid_cod_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_sport")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_sport',
                renderer: renderTpl
            },
            {
                id:'grid_des_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_sport")%>",
                width: 200,
                sortable: true,
                dataIndex: 'des_sport',
                renderer: renderTpl
            }
        ]);
        //Comuni
        var triggerComuni = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_com',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_com")%>",
            name: 'cod_com',
            window: winComuni,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            lookupFields:['cod_com','des_ente'],
            windowLink:"<%=basePath%>protected/EntiComuni.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_com'],
            formName: formName,
            url: urlScriviSessione
        });

        //Destinatari

        var triggerDestinatari = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_dest',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_dest")%>",
            name: 'cod_dest',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            window: winDestinatari,
            lookupFields:['cod_dest','intestazione'],
            windowLink:"<%=basePath%>protected/Destinatari.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_dest'],
            formName: formName,
            url: urlScriviSessione
        });

        //Sportelli

        var triggerSportelli = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_sport',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_sport")%>",
            name: 'cod_sport',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            window: winSportelli,
            lookupFields:['cod_sport','des_sport'],
            windowLink:"<%=basePath%>protected/Sportelli.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_sport'],
            formName: formName,
            url: urlScriviSessione
        });

        //Cud
        var triggerCud = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_cud',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_cud")%>",
            name: 'cod_cud',
            window: winCud,
            lookupFields:['cod_cud','des_cud'],
            labelStyle: 'font-weight:bold;',
            windowLink:"<%=basePath%>protected/Cud.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_cud'],
            formName: formName,
            url: urlScriviSessione
        });

        var itemsForm = [triggerComuni,{
                id: 'des_ente',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ente")%>",
                name: 'des_ente',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },triggerDestinatari,{
                id: 'intestazione',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_intestazione")%>",
                name: 'intestazione',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },triggerCud,{
                id: 'des_cud',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_cud")%>",
                name: 'des_cud',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },triggerSportelli,{
                id: 'des_sport',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_sport")%>",
                name: 'des_sport',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },{
                items: [
                    {
                        items: [
                            {
                                xtype: 'checkboxgroup',
                                columns: 1,
                                vertical: false,
                                items: [
									{
										id: 'riversamento_automatico',
										boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_riversamento_automatico")%>",
										xtype: 'checkbox',
										name: 'riversamento_automatico'
									}                    
                                ]
                            }
                        ]
                    }
                ],
                xtype: 'fieldset',
                title: "<%=testiPortale.get(nomePagina + "_field_form_riversamento_oneri")%>",
                width: '100%',
				border: '1' 
            },{
                id: 'cod_dest_old',
                fieldLabel: 'cod_dest_old',
                name: 'cod_dest_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'cod_com_old',
                fieldLabel: 'cod_com_old',
                name: 'cod_com_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'cod_cud_old',
                fieldLabel: 'cod_cud_old',
                name: 'cod_cud_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'cod_sport_old',
                fieldLabel: 'cod_sport_old',
                name: 'cod_sport_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'riversamento_automatico_old',
                fieldLabel: 'riversamento_automatico_old',
                name: 'riversamento_automatico_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'az_modifica',
                fieldLabel: 'az_modifica',
                name: 'az_modifica',
                readOnly: true,
                hidden: true,
                hideLabel: true
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
                    Ext.getCmp("cod_com_old").setValue(rec.data.cod_com);
                    Ext.getCmp("cod_cud_old").setValue(rec.data.cod_cud);
                    Ext.getCmp("cod_dest_old").setValue(rec.data.cod_dest);
                    Ext.getCmp("cod_sport_old").setValue(rec.data.cod_sport);
                    Ext.getCmp("riversamento_automatico_old").setValue(rec.data.riversamento_automatico);
                    Ext.getCmp("cancel").enable();
                    Ext.getCmp("insert").disable();
                    Ext.getCmp("save").enable();
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

        var submitForm = function () {
            form.getForm().submit({url:'aggiorna',
                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                success: function(result,request) {
                    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                    ds.load({params: {start:0, size:grid_row}});
                    Ext.getCmp("azione_form").setValue('modifica');
                    Ext.getCmp("save").enable();
                    Ext.getCmp("insert").disable();
                    Ext.getCmp("cancel").enable();
                    Ext.getCmp("cod_com_old").setValue(Ext.getCmp("cod_com").getValue());
                    Ext.getCmp("cod_cud_old").setValue(Ext.getCmp("cod_cud").getValue());
                },
                failure:function(result,request) {
                    if (Ext.util.JSON.decode(request.response.responseText).failure.notifica){
                        Ext.MessageBox.confirm('Confirm',Ext.util.JSON.decode(request.response.responseText).failure.notifica,
                        function(e) {
                            if (e=='yes'){
                                winDisplayNotifica.show();
                            }
                        });
                    }else if (Ext.util.JSON.decode(request.response.responseText).failure.errore){
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure.errore);
                    }else{
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                    }
                }
            });
        }

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
                            Ext.getCmp("az_modifica").setValue('');
                            table_name_notifica=table_name;
                            msgForm.getForm().findField("azione").setValue("modifica");
                            if (Ext.getCmp("cod_com_old").getValue() != Ext.getCmp("cod_com").getValue()
                                || Ext.getCmp("cod_cud_old").getValue() != Ext.getCmp("cod_cud").getValue()) {
                                Ext.MessageBox.confirm('Confirm','Cancello la relazione precedente ?' ,function(e) {
                                    if (e=='yes'){
                                        Ext.getCmp("az_modifica").setValue('cancella');
                                    }
                                    if (e=='no'){
                                        Ext.getCmp("az_modifica").setValue('mantieni');
                                    }
                                    submitForm();
                                });

                            } else {
                                submitForm();
                            }
                        }
                    },
                    {
                        id: 'cancel',
                        text: "<%=testiPortale.get("bottone_delete")%>",
                        disabled:true,
                        handler: function(){
                            Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                            function(e) {
                                if (e=='yes'){
                                    form.getForm().submit({
                                        url:'cancella',
                                        waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                        success: function(result,request) {
                                            Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                            ds.load({params: {start:0, size:grid_row}});
                                            Ext.getCmp("insert").enable();
                                            Ext.getCmp("cancel").disable();
                                            triggerDestinatari.setEnabled();
                                            triggerComuni.setEnabled();
                                            triggerCud.setEnabled();
                                            triggerSportelli.setEnabled();
                                            Ext.getCmp("azione_form").setValue('inserimento');
                                        },
                                        failure: function(result,request) {
                                            Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                        }
                                    });
                                }
                            });
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
                                    Ext.getCmp('insert').disable();
                                    Ext.getCmp('cancel').enable();
                                    Ext.getCmp('save').enable();
                                    Ext.getCmp("azione_form").setValue('modifica');
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
                            triggerDestinatari.setEnabled();
                            triggerComuni.setEnabled();
                            triggerCud.setEnabled();
                            triggerSportelli.setEnabled();
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
                            Ext.getCmp('cancel').disable();
                            triggerDestinatari.setEnabled();
                            triggerComuni.setEnabled();
                            triggerCud.setEnabled();
                            triggerSportelli.setEnabled();
                        }
                        if (Ext.getDom('azione_form').value == 'modifica') {
                            Ext.getCmp('insert').disable();
                            Ext.getCmp('cancel').enable();
                            Ext.getCmp('save').enable();
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
