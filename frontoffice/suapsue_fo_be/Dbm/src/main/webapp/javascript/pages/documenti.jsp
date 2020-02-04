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

        var table_name = 'documenti';
        var sort_field = 'cod_doc';
        var table_name_documenti_documenti = 'documenti_documenti';
        var table_name_documenti_documenti_upload = 'documenti_documenti_upload';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

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
        
        var successNotificaPendente = function (response,request) {
                    try {o = Ext.decode(response.responseText);}
                    catch(e) {
                        this.showError(response.responseText);
                        return;
                    }
                    if(!o.success) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        if (Ext.getCmp("cancel")){Ext.getCmp("cancel").disable();}
                        if (Ext.getCmp("save")){Ext.getCmp("save").disable();}
                        if (Ext.getCmp("deleteFile")) {Ext.getCmp("deleteFile").disable();}
                        if (Ext.getCmp("upload")) {Ext.getCmp("upload").disable();}
                    }
                };
        var failureNotificaPendente = function (response,request) {
            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
        };

        function changeFlgDic(val){
            if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_dic_N")%>";
            }else if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_dic_S")%>";
            }
            return val;
        };
        var comboFlgDic = new Ext.form.ComboBox({
            id: 'flg_dic',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_dic")%>",
            name: 'flg_dic',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_dic_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_dic_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_dic','displayText'],
                data: [['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_dic_N")%>"],
                    ['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_dic_S")%>"]]
            }),
            valueField: 'flg_dic',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            editable: false,
            resizable:true

        });

        var dyn_fields = [
            {name: 'cod_doc'},
            {name: 'tit_doc_it'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'tit_doc_<%=lingueAggregazione.get(i)%>'},
             <%}%>            
            {name: 'href'},
            {name: 'tit_href'},
            {name: 'des_doc_it'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'des_doc_<%=lingueAggregazione.get(i)%>'},
             <%}%>             
            {name: 'nome_file'},
            {name: 'tip_doc'},
            {name: 'flg_dic'},
            {name: 'tip_aggregazione'},
            {name: 'des_aggregazione'}
        ];

        var fp = new Ext.FormPanel({
            id:'upload_form',
            forceLayout: true,
            fileUpload: true,
            frame: false,
            border:true,
            bodyBorder:false,
            bodyStyle: 'padding: 10px 10px 0 10px;',
            labelWidth: 120,
            defaults: {
                anchor: '98%'
            },
            items: [{
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
                },{
                    xtype: 'textfield',
                    id: 'window_cod_doc',
                    fieldLabel: '',
                    name: 'window_cod_doc',
                    readOnly: true,
                    hidden: true,
                    hideLabel: true
                }
            ],
            buttons: [
                {
                    id: 'saveUploadFile',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    handler: function(){
                        table_name_notifica=table_name_documenti_documenti_upload;
                        if(fp.getForm().isValid()){
                            fp.getForm().submit({
                                url: 'uploadDocument?table_name='+table_name,
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                success: function(response,request){
                                    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    Ext.getCmp("deleteFile").enable();
                                    ds.load({params: {start:0, size:grid_row}});
                                    Ext.getCmp("nome_file").setValue(Ext.util.JSON.decode(request.response.responseText).nome_file);
                                    uploadWindow.hide();
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
                    }
                },{
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
            width:800,
            height:200,
            forceLayout: true,
            plain: true,
            title: "<%=testiPortale.get("form_upload_title")%>",
            resizable:false,
            modal:true,
            items: [fp]
        });


        var triggerHref = new Ext.ux.form.SearchTripleFieldForm({
            id: 'href',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_href")%>",
            name: 'href',
            window: winHref,
            hideTrigger1:false,
    <% if (utente.getRuolo().equals("A")) {%>
                            hideTrigger3:true,
    <% }%>
            clearButton: true,
            lookupFields:['href','tit_href'],
            windowLink:"<%=basePath%>protected/Href.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['href'],
            formName: formName,
            url: urlScriviSessione
        });
        var dyn_fields_protection = ['cod_doc'];

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
            //		autoLoad:true
        });

        ds.load({params: {start:0, size:grid_row}});

        ds.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })

        var colModel = new Ext.grid.ColumnModel([
            {
                id:'grid_cod_doc',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_doc")%>",
                width: 100,
                sortable: true,
                dataIndex: 'cod_doc'
            },{
                id:'grid_tit_doc_it',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_doc")%>",
                renderer: renderTpl,
                width: 450,
                sortable: true,
                dataIndex: 'tit_doc_it'
            },{
                id:'grid_tit_href',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_href")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'tit_href'
            },{
                id:'grid_flg_dic',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_flg_dic")%>",
                renderer: changeFlgDic,
                width: 250,
                sortable: true,
                dataIndex: 'flg_dic'
            },{
                id:'grid_nome_file',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_nome_file")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'nome_file'
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
            id: 'cod_doc',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_doc")%>",
            name: 'cod_doc',
            allowBlank: false,
            maxLength: 20,
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
                id: 'tit_doc_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_doc")%>",
                name: 'tit_doc_it',
                xtype:'textarea',
                height: 37,
                maxLength: 64000,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
             <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
            {
                id: 'tit_doc_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_doc")%>(<%=lingueAggregazione.get(i)%>)",
                xtype:'textarea',
                height: 37,
                maxLength: 64000,
                allowBlank: false,
                name: 'tit_doc_<%=lingueAggregazione.get(i)%>',
                labelStyle: 'font-weight:bold;'
            },
            <%}%>
            {
                id: 'des_doc_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_doc")%>",
                maxLength: 64000,
                name: 'des_doc_it'
            },
             <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
            {
                id: 'des_doc_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_doc")%>(<%=lingueAggregazione.get(i)%>)",
                maxLength: 64000,
                name: 'des_doc_<%=lingueAggregazione.get(i)%>'
            },
            <%}%>            
            triggerHref,{
                id: 'tit_href',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_href")%>",
                name: 'tit_href',
                readOnly: true
            },comboFlgDic,{
                id: 'nome_file',
                xtype:'displayfield',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_file")%>",
                name: 'nome_file'
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
                    if (Ext.getCmp("insert")) {
                        Ext.getCmp("insert").disable();
                    }
                    if (tipoAggregazione == rec.data.tip_aggregazione || rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined || tipoAggregazione == null) {
                        if (Ext.getCmp("save")) {
                            Ext.getCmp("save").enable();
                        }
                        if (Ext.getCmp("cancel")) {
                            Ext.getCmp("cancel").enable();
                        }  
                        if (Ext.getCmp("deleteFile")) {
                            Ext.getCmp("deleteFile").disable();
                        }
                        if (Ext.getCmp("upload")) {
                            Ext.getCmp("upload").enable();
                        }
                        if (rec.data.nome_file != '') {                        
                           if (Ext.getCmp("deleteFile")) {
                               Ext.getCmp("deleteFile").enable();
                           }    
                        }                        
                    } else {
                        if (Ext.getCmp("save")) {
                           Ext.getCmp("save").disable();
                        } 
                        if (Ext.getCmp("cancel")) {
                           Ext.getCmp("cancel").disable();
                        } 
                        if (Ext.getCmp("deleteFile")) {
                           Ext.getCmp("deleteFile").disable();
                        }
                        if (Ext.getCmp("upload")) {
                           Ext.getCmp("upload").disable();
                        }
                        if (Ext.getCmp("deleteFile")) {
                            Ext.getCmp("deleteFile").disable();
                        }  
                        if (Ext.getCmp("saveUploadFile")) {
                            Ext.getCmp("saveUploadFile").disable();
                        } 
                    }
                    if (rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined) {
                        triggerAggregazioni.enable();
                    } else {
                        triggerAggregazioni.disable();
                    }
                    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                        Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                    }                    
                    Ext.getCmp("azione_form").setValue('modifica');
                    Ext.getCmp("window_cod_doc").setValue(Ext.getCmp("cod_doc").getValue());
                    triggerHref.setEnabled();
                    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                        Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                    }
                    Ext.Ajax.request({
                        url: 'leggiNotifica',
                        method: 'POST',
                        params: {'table_name':table_name,'cod_doc':rec.data.cod_doc},
                        success: successNotificaPendente,
                        failure: failureNotificaPendente
                    });

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
                        table_name_notifica=table_name;
                        form.getForm().submit({url:'aggiorna',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                ds.load({params: {start:0, size:grid_row}});
                                Ext.getCmp("azione_form").setValue('modifica');
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }
                                if (Ext.getCmp("insert")) {
                                    Ext.getCmp("insert").disable();
                                }
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").enable();
                                }
                                if (Ext.getCmp("upload")) {
                                    Ext.getCmp("upload").enable();
                                }
                                if (Ext.getCmp('nome_file').getValue() != '') {
                                    if (Ext.getCmp("deleteFile")) {
                                        Ext.getCmp("deleteFile").enable();
                                    }
                                }
                                if (Ext.getCmp("tip_aggregazione").getValue() != '') {
                                   triggerAggregazioni.disable();
                                } else {
                                   triggerAggregazioni.enable();
                                }
                                triggerHref.setEnabled();
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
                }
    <% if (!utente.getRuolo().equals("A")) {%>
                ,{
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
                                        if (Ext.getCmp("save")) {
                                            Ext.getCmp("save").disable();
                                        }
                                        if (Ext.getCmp("insert")) {
                                            Ext.getCmp("insert").enable();
                                        }
                                        if (Ext.getCmp("cancel")) {
                                            Ext.getCmp("cancel").disable();
                                        }
                                        if (Ext.getCmp("upload")) {
                                           Ext.getCmp("upload").disable();
                                        }
                                        if (Ext.getCmp("deleteFile")) {
                                            Ext.getCmp("deleteFile").disable();
                                        }
                                        Ext.getCmp("azione_form").setValue('inserimento');
                                        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                            Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                                        }
                                        if (Ext.getCmp('nome_file')) {
                                            Ext.getCmp("nome_file").setValue("");
                                        }
                                        triggerHref.setEnabled();
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
                                Ext.getCmp("window_cod_doc").setValue(Ext.getCmp("cod_doc").getValue());
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }
                                if (Ext.getCmp("insert")) {
                                    Ext.getCmp("insert").disable();
                                }
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").enable();
                                }
                                if (Ext.getCmp("upload")) {
                                    Ext.getCmp("upload").enable();
                                }
                                if (Ext.getCmp("deleteFile")) {
                                    Ext.getCmp("deleteFile").disable();
                                }
                                if (Ext.getCmp('nome_file').getValue() != '') {
                                    if (Ext.getCmp("deleteFile")) {
                                        Ext.getCmp("deleteFile").enable();
                                    }
                                }
                                triggerHref.setEnabled();
                                if (Ext.getCmp("tip_aggregazione").getValue() != '') {
                                   triggerAggregazioni.disable();
                                } else {
                                   triggerAggregazioni.enable();
                                }                                
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
                    id: 'upload',
                    text: "<%=testiPortale.get("bottone_upload")%>",
                    disabled: true,
                    handler: function(){
                        uploadWindow.show();
                    }
                },{
                    id: 'deleteFile',
                    text: "<%=testiPortale.get("bottone_delete_file")%>",
                    disabled: true,
                    handler:function(){
                        table_name_notifica=table_name_documenti_documenti;
                        Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                        function(e) {
                            if (e=='yes'){
                                form.getForm().submit({url:'cancella?file=S',
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                        ds.load({params: {start:0, size:grid_row}});
                                        if (Ext.getCmp("save")) {
                                            Ext.getCmp("save").enable();
                                        }
                                        if (Ext.getCmp("insert")) {
                                            Ext.getCmp("insert").disable();
                                        }
                                        if (Ext.getCmp("cancel")) {
                                            Ext.getCmp("cancel").enable();
                                        }
                                        if (Ext.getCmp('nome_file')) {
                                            Ext.getCmp("nome_file").setValue("");
                                        }
                                        triggerHref.setDisabled();
                                        Ext.getCmp("azione_form").setValue('modifica');
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
                                })
                            }
                        });
                    }
                },{
                    id: 'reset',
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        form.getForm().reset();
                        if (Ext.getCmp("save")) {
                            Ext.getCmp('save').disable();
                        }
                        if (Ext.getCmp("insert")) {
                            Ext.getCmp('insert').enable();
                        }
                        if (Ext.getCmp("cancel")) {
                            Ext.getCmp('cancel').disable();
                        }
                        if (Ext.getCmp("upload")) {
                            Ext.getCmp("upload").disable();
                        }
                        if (Ext.getCmp("deleteFile")) {
                            Ext.getCmp("deleteFile").disable();
                        }
                        Ext.getCmp("azione_form").setValue('inserimento');
                        triggerHref.setEnabled();
                        for (var ele=0; ele< dyn_fields_protection.length; ele++) {
                            Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                        }
                    }
                }
    <% }%>
            ],
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
                        if (Ext.getCmp("save")) {
                            Ext.getCmp('save').disable();
                        }
                        if (Ext.getCmp("insert")) {
                            Ext.getCmp('insert').enable();
                        }
                        if (Ext.getCmp("cancel")) {
                            Ext.getCmp('cancel').disable();
                        }
                        if (Ext.getCmp("upload")) {
                            Ext.getCmp("upload").enable();
                        }
                        triggerHref.setEnabled();
                    }
                    if (Ext.getDom('azione_form').value == 'modifica') {
                        if (Ext.getCmp("save")) {
                            Ext.getCmp("save").enable();
                        }
                        if (Ext.getCmp("insert")) {
                            Ext.getCmp("insert").disable();
                        }
                        if (Ext.getCmp("cancel")) {
                            Ext.getCmp("cancel").enable();
                        }
                        if (Ext.getCmp("upload")) {
                            Ext.getCmp("upload").enable();
                        }
                        if (Ext.getCmp("deleteFile")) {
                            Ext.getCmp("deleteFile").disable();
                        }
                        if (Ext.getCmp('nome_file').getValue() != '') {
                            if (Ext.getCmp("deleteFile")) {
                                Ext.getCmp("deleteFile").enable();
                            }
                        }
                        triggerHref.setDisabled();
                    }
                }
            },
            failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
            }
        });
        cod_doc=Ext.getUrlParam('cod_doc');
        if (cod_doc) {
            Ext.Ajax.request({
                url: 'leggiRecord',
                method: 'POST',
                params: {'cod_doc':cod_doc, 'table_name':table_name},
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
                            if (Ext.getCmp("insert")) {
                                Ext.getCmp("insert").disable();
                            }
                            for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                            }
                            triggerHref.setEnabled();
                            if (tipoAggregazione == dati['tip_aggregazione'] || dati['tip_aggregazione'] == '' || dati['tip_aggregazione'] == undefined || tipoAggregazione == null) {
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }                                
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").enable();
                                }
                                if (Ext.getCmp("upload")) {
                                   Ext.getCmp("upload").enable();
                                } 
                                if (Ext.getCmp('nome_file').getValue() != '') {
                                    if (Ext.getCmp("deleteFile")) {
                                       Ext.getCmp("deleteFile").enable();
                                    }
                                }                                
                            } else {
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }                                
                                if (Ext.getCmp("cancel")) {
                                   Ext.getCmp("cancel").disable();
                                }
                                if (Ext.getCmp("upload")) {
                                   Ext.getCmp("upload").disable();
                                }   
                                if (Ext.getCmp("deleteFile")) {
                                   Ext.getCmp("deleteFile").disable();
                                }                                
                            }
                            if (dati['tip_aggregazione'] == '' || dati['tip_aggregazione'] == 'undefined') {
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
