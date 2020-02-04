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
            List<String> lingueTotali = (List<String>) session.getAttribute("lingueTotali");  
            String set_id = request.getParameter("set_id");
%>
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 25;
        var grid_row_upload = 10;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var formNameUpload = 'upload_form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'immagini_template';
        var table_name_upload = 'immagini';
        var sort_field = 'nome_immagine';
        var sort_field_upload = 'a.cod_sport';

        var id_field = '';
        var id_field_upload = '';
        var group_field = '';
        var group_field_upload = '';
        var expand_field = '';
        var expand_field_upload = '';

        var nome_immagine='';
        //Sportelli

        var triggerSportelli = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_sport',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_sport")%>",
            name: 'cod_sport',
            hideTrigger1:false,
            hideTrigger3:true,
            window: winSportelli,
            lookupFields:['cod_sport','des_sport'],
            windowLink:"<%=basePath%>protected/Sportelli.jsp?set_id=<%=set_id%>",
            formName: formNameUpload,
            url: urlScriviSessione
        });
        //Comuni
        var triggerComuni = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_com',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_com")%>",
            name: 'cod_com',
            window: winComuni,
            hideTrigger1:false,
            hideTrigger3:true,
            lookupFields:['cod_com','des_ente'],
            windowLink:"<%=basePath%>protected/EntiComuni.jsp?set_id=<%=set_id%>",
            formName: formNameUpload,
            url: urlScriviSessione
        });
        var comboLingue = new Ext.form.ComboBox({
            id: 'cod_lang',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_lang")%>",
            name: 'cod_lang',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'cod_lang_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_cod_lang_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['cod_lang','displayText'],
                data: [['it', "it"]
                       <% for (int i = 0; i < lingueTotali.size(); i++){ %>
                          ,["<%=lingueTotali.get(i)%>","<%=lingueTotali.get(i)%>"]
                       <%}%>
                      ]
            }),
            valueField: 'cod_lang',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true

        });  
        var dyn_fields = [
            {name: 'nome_immagine'},
            {name: 'des_immagine'}
        ];
        var dyn_fields_upload = [
            {name: 'nome_immagine'},
            {name: 'des_immagine'},
            {name: 'cod_sport'},
            {name: 'des_sport'},
            {name: 'nome_file'},
            {name: 'cod_com'},
            {name: 'cod_lang'},
            {name: 'des_ente'}
        ];

        var dyn_fields_protection = ['nome_immagine'];
        var dyn_fields_protection_upload = ['cod_sport','des_sport','cod_com','des_ente'];

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
                id:'grid_nome_immagine',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_nome_immagine")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'nome_immagine'
            },{
                id:'grid_des_immagine',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_immagine")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'des_immagine'
            }
        ]);


        var colModelUpload = new Ext.grid.ColumnModel([
            {
                id:'grid_des_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_sport")%>",
                renderer: renderTpl,
                width: 450,
                sortable: true,
                dataIndex: 'des_sport'
            },
            {
                id:'grid_des_ente',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_ente")%>",
                renderer: renderTpl,
                width: 450,
                sortable: true,
                dataIndex: 'des_ente'
            },
            {
                id:'grid_cod_lang',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_lang")%>",
                width: 50,
                sortable: true,
                dataIndex: 'cod_lang'
            },             
            {
                id:'grid_nome_file',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_nome_file")%>",
                width: 100,
                sortable: true,
                dataIndex: 'nome_file'
            }
        ]);
        var txtFld = new Ext.form.TextField({
            id: 'nome_immagine',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_immagine")%>",
            name: 'nome_immagine',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            maxLength:50,
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
                id: 'des_immagine',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_immagine")%>",
                name: 'des_immagine',
                allowBlank: false,
                maxLength:255,
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
                    Ext.getCmp('upload').enable();
                    Ext.getCmp("azione_form").setValue('modifica');
                    // nome_immagine=rec.data.nome_immagine;
                    dsUpload.baseParams={nome_immagine:rec.data.nome_immagine};
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
                                    clientValidation: false,
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                        ds.load({params: {start:0, size:grid_row}});
                                        Ext.getCmp("save").disable();
                                        Ext.getCmp("insert").enable();
                                        Ext.getCmp("cancel").disable();
                                        Ext.getCmp('upload').disable();
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
                                Ext.getCmp('upload').enable();
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
                },
<% } %>
                {
                    id: 'upload',
                    text: "<%=testiPortale.get("bottone_upload")%>",
                    disabled: true,
                    handler: function(){
                        uploadWindow.show();
                        dsUpload.load({params: {start:0, size:grid_row_upload, nome_immagine:Ext.getCmp("nome_immagine").getValue()}});
                    }
                },{
                    id: 'reset',
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        form.getForm().reset();
                        Ext.getCmp('insert').enable();
                        Ext.getCmp('save').disable();
                        Ext.getCmp('cancel').disable();
                        Ext.getCmp('upload').disable();
                        Ext.getCmp('azione_form').setValue('inserimento');
                        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                            Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                        }
                    }
                }],
            buttonAlign: 'center',
            renderTo: form_div
        });
        var readerUpload = new Ext.data.JsonReader({
            root:table_name_upload,
            fields: dyn_fields_upload,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_upload
        });
        var dsUpload = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_upload}),
            sortInfo: {field:sort_field_upload,direction:'ASC'},
            baseParams: {'nome_immagine':''},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerUpload,
            groupField: group_field_upload
        });

        dsUpload.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })

        var searchUpload = new Ext.ux.form.SearchField({
            store: dsUpload,
            width:220,
            paging: true,
            paging_size: grid_row_upload
        });
        var searchSelectionModelUpload = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp(formNameUpload).getForm().loadRecord(rec);
                    Ext.getCmp("save").enable();
                    Ext.getCmp("cancel").enable();
                    Ext.getCmp("insert").disable();
                    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                        Ext.getCmp(dyn_fields_protection_upload[ele]).getEl().dom.setAttribute('readOnly', true);
                    }
                }
            }
        });

        var gridUpload = new Ext.grid.GridPanel({
            id:'gridUpload',
            frame:true,
            ds: dsUpload,
            cm: colModelUpload,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelUpload,
            oadMask: true,
            autoExpandColumn: expand_field_upload,
            height:400,
            view: new Ext.grid.GroupingView({
                forceFit:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
            }),
            bbar: new Ext.PagingToolbar({
                store: dsUpload,
                pageSize:grid_row_upload,
                displayInfo:true,
                items:[searchUpload]
            }),
            border: true

        });
        var	itemsFormUpload = [triggerSportelli,{
                id: 'des_sport',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_sport")%>",
                name: 'des_sport',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },{
                id: 'window_table_name',
                fieldLabel: 'table name',
                name: 'window_table_name',
                xtype:'textarea',
                readOnly: true,
                hidden: true,
                hideLabel: true,
                value: table_name_upload
            },{
                id: 'window_nome_immagine',
                fieldLabel: 'azione form',
                name: 'window_nome_immagine',
                xtype:'textarea',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },triggerComuni,{
                id: 'des_ente',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ente")%>",
                name: 'des_ente',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },
            comboLingue,
            {
                id: 'nome_file',
                xtype:'displayfield',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_file")%>",
                name: 'nome_file'
            },{
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
            },gridUpload];

        var fp = new Ext.FormPanel({
            id: formNameUpload,
            forceLayout: true,
            fileUpload: true,
            frame: false,
            border:false,
            bodyBorder:false,
            bodyStyle: 'padding: 10px 10px 0 10px;',
            labelWidth: 120,
            defaults: {
                anchor: '95%'
            },
            items: [itemsFormUpload],
            buttons: [
<% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) {%>
                {
                    text: "<%=testiPortale.get("bottone_save")%>",
                    handler: function(){
                        if(fp.getForm().isValid()){
                            Ext.getCmp('window_nome_immagine').setValue(Ext.getCmp("nome_immagine").getValue());
                            fp.getForm().submit({
                                url: 'aggiornaMultipart',
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                success: function(response,request){
                                    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    Ext.getCmp("deleteFile").enable();
                                    dsUpload.load({params: {start:0, size:grid_row_upload, nome_immagine:Ext.getCmp("nome_immagine").getValue()}});
                                    Ext.getCmp("nome_file").setValue(Ext.util.JSON.decode(request.response.responseText).nome_file);
                                },
                                failure:function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            });
                        }
                    }
                },{
                    text: "<%=testiPortale.get("bottone_cancella")%>",
                    id: 'deleteFile',
                    handler: function(){
                        Ext.getCmp('window_nome_immagine').setValue(Ext.getCmp("nome_immagine").getValue());
                        fp.getForm().submit({
                            clientValidation: false,
                            url: 'cancellaMultipart',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(response,request){
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                dsUpload.load({params: {start:0, size:grid_row_upload, nome_immagine:Ext.getCmp("nome_immagine").getValue()}});
                            },
                            failure:function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                },
<% } %>
                {
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        fp.getForm().reset();
                    }
                },{
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){
                        uploadWindow.hide();
                    }
                }
            ],
            buttonAlign: 'center'
        });

        var uploadWindow = new Ext.Window({
            closeAction:'hide',
            width:900,
            height:600,
            forceLayout: true,
            plain: true,
            autoScroll: true,
            title: "<%=testiPortale.get("form_upload_title")%>",
            resizable:false,
            modal:true,
            items: [fp]
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

