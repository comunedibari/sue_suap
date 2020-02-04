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

        var table_name = 'interventi_collegati';
        var sort_field = 'a.cod_int_padre';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'cod_int_padre'},
            {name: 'tit_int_padre'},
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_cond'},
            {name: 'testo_cond'}
        ];
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
                    }else {
                        for (ele in Ext.util.JSON.decode(response.responseText).success) {
                            if (Ext.getCmp(ele)) {
                                if (Ext.util.JSON.decode(response.responseText).success[ele] != '') {
                                    Ext.getCmp(ele).setValue(Ext.util.JSON.decode(response.responseText).success[ele]);
                                }
                            }
                        }
                    }
                };
        var failureNotificaPendente = function (response,request) {
            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
        };
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
                id:'grid_cod_int_padre',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int_padre")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_int_padre'
            },
            {
                id:'grid_tit_int_padre',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int_padre")%>",
                renderer: renderTpl,
                width: 200,
                sortable: true,
                dataIndex: 'tit_int_padre'
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
                width: 60,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'tit_int'
            },
            {
                id:'grid_cod_cond',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_cond")%>",
                width: 60,
                sortable: true,
                dataIndex: 'cod_cond',
                renderer: renderTpl
            },
            {
                id:'grid_testo_cond',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_testo_cond")%>",
                width: 60,
                sortable: true,
                dataIndex: 'testo_cond',
                renderer: renderTpl
            }
        ]);
        //Interventi
        var triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_int',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
            name: 'cod_int',
    <% if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {%>
                hideTrigger3:true,
    <% }%>
                window: winInterventiCollegati,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'	,
                lookupFields:['cod_int','tit_int'],
                windowLink:"<%=basePath%>protected/Interventi.jsp?set_id=<%=set_id%>",
                windowLinkKeys:['cod_int'],
                formName: formName,
                url: urlScriviSessione
            });
            //InterventiPadre
            var triggerInterventiPadre = new Ext.ux.form.SearchTripleFieldForm({
                id: 'cod_int_padre',
                fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int_padre")%>",
                name: 'cod_int_padre',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'	,
    <% if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {%>
                hideTrigger3:true,
    <% }%>
                window: winInterventi,
                lookupFields:['cod_int_padre','tit_int_padre'],
                windowLink:"<%=basePath%>protected/Interventi.jsp?set_id=<%=set_id%>",
                windowLinkKeys:['cod_int_padre'],
                formName: formName,
                url: urlScriviSessione
            });

            //Testo condizioni
            var triggerCondizioni = new Ext.ux.form.SearchTripleFieldForm({
                id: 'cod_cond',
                fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_cond")%>",
                name: 'cod_cond',
                window: winCondizioni,
                hideTrigger1:false,
    <% if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {%>
                hideTrigger3:true,
    <% }%>
                clearButton: true,
                lookupFields:['cod_cond','testo_cond'],
                windowLink:"<%=basePath%>protected/TestoCondizioni.jsp?set_id=<%=set_id%>",
                windowLinkKeys:['cod_cond'],
                formName: formName,
                url: urlScriviSessione
            });

            var itemsForm = [triggerInterventiPadre,{
                    id: 'tit_int_padre',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int_padre")%>",
                    name: 'tit_int_padre',
                    xtype:'textarea',
                    height: 37,
                    readOnly: true
                },triggerInterventi,{
                    id: 'tit_int',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                    name: 'tit_int',
                    xtype:'textarea',
                    height: 37,
                    readOnly: true
                },triggerCondizioni,{
                    id: 'testo_cond',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_testo_cond")%>",
                    name: 'testo_cond',
                    xtype:'textarea',
                    height: 37,
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
                        triggerInterventi.setDisableButton1();
                        triggerInterventi.setDisableButton2();
                        triggerInterventi.setEnableButton3();
                        triggerInterventiPadre.setDisableButton1();
                        triggerInterventiPadre.setDisableButton2();
                        triggerInterventiPadre.setEnableButton3();
                        Ext.getCmp("azione_form").setValue('modifica');
                        Ext.Ajax.request({
                            url: 'leggiNotifica',
                            method: 'POST',
                            params: {'table_name':table_name,'cod_int':rec.data.cod_int, 'cod_int_padre':rec.data.cod_int_padre},
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
                            msgForm.getForm().findField("azione").setValue("modifica");
                            form.getForm().submit({url:'aggiorna',
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                success: function(result,request) {
                                    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    ds.load({params: {start:0, size:grid_row}});
                                    Ext.getCmp("azione_form").setValue('modifica');
                                    Ext.getCmp("save").enable();
                                    Ext.getCmp("insert").disable();
                                    Ext.getCmp("cancel").enable();
                                    triggerInterventi.setDisableButton1();
                                    triggerInterventi.setDisableButton2();
                                    triggerInterventi.setEnableButton3();
                                    triggerInterventiPadre.setDisableButton1();
                                    triggerInterventiPadre.setDisableButton2();
                                    triggerInterventiPadre.setEnableButton3();
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
                    },{
                        id: 'cancel',
                        text: "<%=testiPortale.get("bottone_delete")%>",
                        disabled:true,
                        handler: function() {
                            table_name_notifica=table_name;
                            msgForm.getForm().findField("azione").setValue("cancellazione");
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
                                            triggerInterventiPadre.setEnabled();
                                            triggerInterventi.setEnabled();
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
                            });
                        }
                    },{
                        id: 'insert',
                        text: "<%=testiPortale.get("bottone_insert")%>",
                        handler: function(){
                            table_name_notifica=table_name;
                            msgForm.getForm().findField("azione").setValue("inserimento");
                            form.getForm().submit({url:'inserisci',
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                success: function(result,request) {
                                    Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    ds.load({params: {start:0, size:grid_row}});
                                    Ext.getCmp('save').enable();
                                    Ext.getCmp('insert').disable();
                                    Ext.getCmp('cancel').enable();
                                    triggerInterventi.setDisableButton1();
                                    triggerInterventi.setDisableButton2();
                                    triggerInterventi.setEnableButton3();
                                    triggerInterventiPadre.setDisableButton1();
                                    triggerInterventiPadre.setDisableButton2();
                                    triggerInterventiPadre.setEnableButton3();
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
                            triggerInterventiPadre.setEnabled();
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
                    try {o = Ext.decode(response.responseText);}
                    catch(e) {
                        this.showError(response.responseText);
                        return;
                    }
                    if(!o.success) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    } else {
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
                            triggerInterventiPadre.setEnabled();
                            triggerInterventi.setEnabled();
                        }
                        if (Ext.getDom('azione_form').value == 'modifica') {
                            Ext.getCmp("save").enable();
                            Ext.getCmp("insert").disable();
                            Ext.getCmp("cancel").enable();
                            triggerInterventiPadre.setDisabled();
                            triggerInterventi.setDisabled();
                            triggerInterventi.setDisableButton1();
                            triggerInterventi.setDisableButton2();
                            triggerInterventi.setEnableButton3();
                            triggerInterventiPadre.setDisableButton1();
                            triggerInterventiPadre.setDisableButton2();
                            triggerInterventiPadre.setEnableButton3();
                        }
                    }
                },
                failure: function(response,request) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
            });
            cod_int_padre=Ext.getUrlParam('cod_int_padre');
            cod_int=Ext.getUrlParam('cod_int');
            if (cod_int_padre && cod_int) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {cod_int:cod_int, cod_int_padre: cod_int_padre, table_name:table_name},
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
                                //                            for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                //                                Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                                //                            }
                            }
                            triggerInterventi.setDisabled();
                            triggerInterventiPadre.setDisabled();

                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            } else {
                if (cod_int_padre) {
                    Ext.Ajax.request({
                        url: 'leggiRecord',
                        method: 'POST',
                        params: {cod_int:cod_int_padre, table_name:"interventi"},
                        success: function(response,request) {
                            try {o = Ext.decode(response.responseText);}
                            catch(e) {
                                this.showError(response.responseText);
                                return;
                            }
                            if(!o.success) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                            }else {
                                var dati=Ext.util.JSON.decode(response.responseText).success['interventi'][0];
                                form.getForm().findField('cod_int_padre').setValue(dati['cod_int']);
                                form.getForm().findField('tit_int_padre').setValue(dati['tit_int']);
                                triggerInterventiPadre.setDisabled();
                            }
                        },
                        failure: function(response,request) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }
                    });

                }
            }

            tree.render(menu_div);
            tree.selectPath("treepanel/source/<%=set_id%>",'id');

        });
