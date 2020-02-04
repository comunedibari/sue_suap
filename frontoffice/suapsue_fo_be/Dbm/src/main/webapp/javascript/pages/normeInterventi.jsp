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

        var table_name = 'norme_interventi';
        var sort_field = 'a.cod_int';

        var id_field = '';
        var group_field = '';
        var expand_field = '';
        
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
        var dyn_fields = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_rif'},
            {name: 'tit_rif'},
            {name: 'art_rif'}
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
                id:'grid_cod_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_int'
            },
            {
                id:'grid_tit_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int")%>",
                renderer: renderTpl,
                width: 200,
                sortable: true,
                dataIndex: 'tit_int'
            },
            {
                id:'grid_cod_rif',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_rif")%>",
                width: 30,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'cod_rif'
            },
            {
                id:'grid_tit_rif',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_rif")%>",
                width: 60,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'tit_rif'
            },
            {
                id:'grid_art_rif',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_art_rif")%>",
                width: 60,
                sortable: true,
                dataIndex: 'art_rif',
                renderer: renderTpl
            }
        ]);
        var triggerNormative = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_rif',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_rif")%>",
            name: 'cod_rif',
            window: winNormative,
    <% if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {%>
                hideTrigger3:true,
    <% }%>
                allowBlank: false,
                labelStyle: 'font-weight:bold;',
                lookupFields:['cod_rif','tit_rif'],
                windowLink:"<%=basePath%>protected/Normative.jsp?set_id=<%=set_id%>",
                windowLinkKeys:['cod_rif'],
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
                window: winInterventi,
    <% if (utente.getRuolo().equals("A") || utente.getRuolo().equals("B")) {%>
                hideTrigger3:true,
    <% }%>
                lookupFields:['cod_int','tit_int'],
                windowLink:"<%=basePath%>protected/Interventi.jsp?set_id=<%=set_id%>",
                windowLinkKeys:['cod_int'],
                formName: formName,
                url: urlScriviSessione
            });

            var itemsForm = [triggerInterventi,{
                    id: 'tit_int',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                    name: 'tit_int',
                    readOnly: true
                },triggerNormative,{
                    id: 'tit_rif',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_rif")%>",
                    name: 'tit_rif',
                    readOnly: true
                },{
                    id: 'art_rif',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_art_rif")%>",
                    name: 'art_rif'
                },{
                    id: 'azione_form',
                    fieldLabel: 'azione form',
                    name: 'azione_form',
                    readOnly: true,
                    hidden: true,
                    hideLabel: true
                },{
                    id: 'cod_rif_old',
                    fieldLabel: 'cod_rif_old',
                    name: 'cod_rif_old',
                    readOnly: true,
                    hidden: true,
                    hideLabel: true
                },{
                    id: 'cod_int_old',
                    fieldLabel: 'cod_int_old',
                    name: 'cod_int_old',
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
                        Ext.getCmp("cod_rif_old").setValue(rec.data.cod_rif);
                        Ext.getCmp("cod_int_old").setValue(rec.data.cod_int+'');
                        Ext.getCmp("save").enable();
                        Ext.getCmp("cancel").enable();
                        Ext.getCmp("insert").disable();
                        //triggerInterventi.setDisabled();
                        //triggerNormative.setDisabled();
                        Ext.getCmp("azione_form").setValue('modifica');
                        Ext.Ajax.request({
                            url: 'leggiNotifica',
                            method: 'POST',
                            params: {'table_name':table_name,'cod_int':rec.data.cod_int, 'cod_rif':rec.data.cod_rif},
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
                                    //triggerInterventi.setDisabled();
                                    //triggerNormative.setDisabled();
                                    Ext.getCmp("cod_int_old").setValue(Ext.getCmp("cod_int").getValue());
                                    Ext.getCmp("cod_rif_old").setValue(Ext.getCmp("cod_rif").getValue());
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
                buttons: [{
                        id: 'save',
                        text: "<%=testiPortale.get("bottone_save")%>",
                        disabled:true,
                        handler: function(){
                            Ext.getCmp("az_modifica").setValue('');
                            table_name_notifica=table_name;
                            msgForm.getForm().findField("azione").setValue("modifica");
                            if (Ext.getCmp("cod_int_old").getValue() != Ext.getCmp("cod_int").getValue()
                                  || Ext.getCmp("cod_rif_old").getValue() != Ext.getCmp("cod_rif").getValue()) {
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
                    },{
                        id: 'cancel',
                        text: "<%=testiPortale.get("bottone_delete")%>",
                        disabled:true,
                        handler: function(){
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
                        );
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
                                    Ext.getCmp('cod_int_old').setValue(Ext.getCmp('cod_int').getValue());
                                    Ext.getCmp('cod_rif_old').setValue(Ext.getCmp('cod_rif').getValue());
                                    //triggerInterventi.setDisabled();
                                    //triggerNormative.setDisabled();
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
                            triggerInterventi.setEnabled();
                            triggerNormative.setEnabled();
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
                            Ext.getCmp('save').disable();
                            Ext.getCmp('cancel').disable();
                            triggerInterventi.setEnabled();
                            triggerNormative.setEnabled();
                        }
                        if (Ext.getDom('azione_form').value == 'modifica') {
                            Ext.getCmp("save").enable();
                            Ext.getCmp("insert").disable();
                            Ext.getCmp("cancel").enable();
                            //triggerInterventi.setDisabled();
                            //triggerNormative.setDisabled();
                        }
                    }
                },
                failure: function(response,request) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
            });
            cod_int=Ext.getUrlParam('cod_int');
            cod_rif=Ext.getUrlParam('cod_rif');
            if (cod_rif && cod_int) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {cod_int:cod_int, cod_rif: cod_rif, table_name:table_name},
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
                                triggerNormative.setEnabled();
                            }
                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            } else {
                if (cod_int) {
                    Ext.Ajax.request({
                        url: 'leggiRecord',
                        method: 'POST',
                        params: {cod_int:cod_int, table_name:"interventi"},
                        success: function(response,request) {
                            try {o = Ext.decode(response.responseText);}
                            catch(e) {
                                this.showError(response.responseText);
                                return;
                            }
                            if(!o.success) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                            }else {
                                for (i in itemsForm) {
                                    var dati=Ext.util.JSON.decode(response.responseText).success["interventi"][0];
                                    if (dati[itemsForm[i].name] != null) {
                                        Ext.getCmp(itemsForm[i].id).setValue(dati[itemsForm[i].name])
                                    }
                                }
                                triggerInterventi.setDisabled();
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
