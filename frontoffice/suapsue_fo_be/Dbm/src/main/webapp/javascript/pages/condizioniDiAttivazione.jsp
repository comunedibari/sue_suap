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
            Utente utente = (Utente) session.getAttribute("utente");
            String set_id = request.getParameter("set_id");
%>
<!--<script type="text/javascript">-->
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'condizioni_di_attivazione';
        var sort_field = 'cod_int';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var salva_tip_aggregazione='';
        var salva_des_aggregazione='';
        var dyn_fields = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_ope'},
            {name: 'des_ope'},
            {name: 'cod_sett'},
            {name: 'des_sett'}
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
            baseParams: {'tip_aggregazione':''},
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

        ds.load({params: {start:0, size:grid_row, tip_aggregazione:salva_tip_aggregazione}});

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
                id:'grid_cod_ope',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_ope")%>",
                width: 30,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'cod_ope'
            },
            {
                id:'grid_des_ope',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_ope")%>",
                width: 200,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'des_ope'
            },
            {
                id:'grid_cod_sett',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_sett")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_sett',
                renderer: renderTpl
            },
            {
                id:'grid_des_sett',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_sett")%>",
                width: 200,
                sortable: true,
                dataIndex: 'des_sett',
                renderer: renderTpl
            }
        ]);
        //Interventi
        var triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_int',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
            name: 'cod_int',
            window: winInterventi,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            lookupFields:['cod_int','tit_int'],
            windowLink:"<%=basePath%>protected/Interventi.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_int'],
            formName: formName,
            url: urlScriviSessione
        });

        //Operazioni

        var triggerOperazioni = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_ope',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_ope")%>",
            name: 'cod_ope',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            window: winOperazioni,
            lookupFields:['cod_ope','des_ope'],
            windowLink:"<%=basePath%>protected/Operazioni.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_ope'],
            formName: formName,
            url: urlScriviSessione
        });

        //SettoriAttivita
        var triggerSettoriAttivita = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_sett',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_sett")%>",
            name: 'cod_sett',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            window: winSettoriAttivita,
            lookupFields:['cod_sett','des_sett'],
            windowLink:"<%=basePath%>protected/SettoriAttivita.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_sett'],
            formName: formName,
            url: urlScriviSessione
        });
        //Aggregazioni
        var sort_field_aggregazioni = 'tip_aggregazione';
        var expand_field_aggregazioni = '';
        var table_name_aggregazioni = 'aggregazioni';
        var id_field_aggregazioni = '';
        var dyn_fields_aggregazioni = [
            {name: 'tip_aggregazione'},
            {name: 'des_aggregazione'}];
        var readerAggregazioni = new Ext.data.JsonReader({
            root:table_name_aggregazioni,
            fields: dyn_fields_aggregazioni,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_aggregazioni
        });

        var dsAggregazioni = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: "popUp?table_name="+table_name_aggregazioni }),
            sortInfo: {field:sort_field_aggregazioni,direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerAggregazioni
        });

        dsAggregazioni.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        });
        var searchSelectionModelAggregazioni = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    form.getForm().reset();
                    salva_tip_aggregazione=rec.data.tip_aggregazione;
                    salva_des_aggregazione='<b>'+rec.data.des_aggregazione+'</b>';
                    Ext.getCmp(winAggregazioni.lookupFields[0]).setValue(salva_tip_aggregazione);
                    Ext.getCmp(winAggregazioni.lookupFields[1]).setValue(salva_des_aggregazione);
                    Ext.getCmp(winAggregazioni.lookupFields[2]).setValue(salva_des_aggregazione);
                    ds.baseParams={tip_aggregazione:salva_tip_aggregazione},
                    ds.load({params: {start:0, size:grid_row, tip_aggregazione:salva_tip_aggregazione}});
                    winAggregazioni.hide();
                    Ext.getCmp('cancel').disable();
                }
            }
        });

        var colModelAggregazioni = new Ext.grid.ColumnModel([
            {
                id:'tip_aggregazione_search',
                header: "<%=testiPortale.get("header_tip_aggregazione")%>",
                width: 150,
                sortable: true,
                dataIndex: 'tip_aggregazione'
            },
            {
                id:'des_aggregazione_search',
                header: "<%=testiPortale.get("header_des_aggregazione")%>",
                width: 850,
                sortable: true,
                dataIndex: 'des_aggregazione'
            }
        ]);
        var searchAggregazioni = new Ext.ux.form.SearchField({
            store: dsAggregazioni,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var gridAggregazioni = new Ext.grid.GridPanel({
            id:'grid_aggregazioni',
            frame:true,
            ds: dsAggregazioni,
            cm: colModelAggregazioni,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelAggregazioni,
            oadMask: true,
            autoExpandColumn: expand_field_aggregazioni,
            height:430,
            bbar: new Ext.PagingToolbar({
                store: dsAggregazioni,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchAggregazioni]
            }),
            border: true
        });
        var winAggregazioni = new Ext.Window({
            title: "<%=testiPortale.get("titolo_window_aggregazioni")%>",
            closable:true,
            closeAction:'hide',
            width:900,
            height:500,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [gridAggregazioni],
            buttons: [{
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){
                        winAggregazioni.hide();
                    }
                }],
            display: function(){
                dsAggregazioni.reload({params:{start: 0, size:grid_row}});
                this.show();
            },
            lookupFields: ['tip_aggregazione','des_aggregazione','label_des_aggregazione']
        });

        var itemsForm = [{
                id: 'tip_aggregazione',
                //fieldLabel: 'tip_aggregazione',
                name: 'tip_aggregazione',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'des_aggregazione',
                //fieldLabel: 'des_aggregazione',
                hidden:true,
                name: 'des_aggregazione',
                readOnly: true,
                hideLabel: true
            },{
                id: 'label_des_aggregazione',
                //fieldLabel: '',
                xtype:'displayfield',
                name: 'label_des_aggregazione',
                readOnly: true,
                hideLabel: true
            },triggerInterventi,{
                id: 'tit_int',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                name: 'tit_int',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },triggerOperazioni,{
                id: 'des_ope',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ope")%>",
                name: 'des_ope',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },triggerSettoriAttivita,{
                id: 'des_sett',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_sett")%>",
                name: 'des_sett',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },{
                id: 'cod_int_old',
                fieldLabel: 'cod_int_old',
                name: 'cod_int_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'cod_sett_old',
                fieldLabel: 'cod_sett_old',
                name: 'cod_sett_old',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'cod_ope_old',
                fieldLabel: 'cod_ope_old',
                name: 'cod_ope_old',
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
                    Ext.getCmp("cancel").enable();
                    Ext.getCmp("save").enable();
                    Ext.getCmp("insert").disable();
                    //triggerOperazioni.setDisableButton1();
                    //triggerOperazioni.setDisableButton2();
                    //triggerOperazioni.setEnableButton3();
                    //triggerInterventi.setDisableButton1();
                    //triggerInterventi.setDisableButton2();
                    //triggerInterventi.setEnableButton3();
                    //triggerSettoriAttivita.setDisableButton1();
                    //triggerSettoriAttivita.setDisableButton2();
                    //triggerSettoriAttivita.setEnableButton3();
                    Ext.getCmp("cod_ope_old").setValue(rec.data.cod_ope);
                    Ext.getCmp("cod_sett_old").setValue(rec.data.cod_sett);
                    Ext.getCmp("cod_int_old").setValue(rec.data.cod_int+'');
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
                    Ext.getCmp("cod_int_old").setValue(Ext.getCmp("cod_int").getValue());
                    Ext.getCmp("cod_sett_old").setValue(Ext.getCmp("cod_sett").getValue());
                    Ext.getCmp("cod_ope_old").setValue(Ext.getCmp("cod_ope").getValue());
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
                    id: 'changeaggregazione',
                    text: "<%=testiPortale.get("bottone_cambia_aggregazione")%>",
                    handler: function(){
                        winAggregazioni.display();
                    }
                },
<% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) { %>
                  {
                        id: 'save',
                        text: "<%=testiPortale.get("bottone_save")%>",
                        disabled:true,
                        handler: function(){
                            Ext.getCmp("az_modifica").setValue('');
                            table_name_notifica=table_name;
                            msgForm.getForm().findField("azione").setValue("modifica");
                            if (Ext.getCmp("cod_int_old").getValue() != Ext.getCmp("cod_int").getValue()
                                || Ext.getCmp("cod_ope_old").getValue() != Ext.getCmp("cod_ope").getValue()
                                || Ext.getCmp("cod_sett_old").getValue() != Ext.getCmp("cod_sett").getValue()) {
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
                    handler:  function(){
                        Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                        function(e) {
                            if (e=='yes'){
                                form.getForm().submit({
                                    url:'cancella',
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                        ds.load({params: {start:0, size:grid_row, tip_aggregazione:salva_tip_aggregazione}});
                                        Ext.getCmp("insert").enable();
                                        Ext.getCmp("cancel").disable();
                                        triggerOperazioni.setEnabled();
                                        triggerInterventi.setEnabled();
                                        triggerSettoriAttivita.setEnabled();
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
                                ds.load({params: {start:0, size:grid_row, tip_aggregazione:salva_tip_aggregazione}});
                                Ext.getCmp('insert').disable();
                                Ext.getCmp('cancel').enable();
                                triggerOperazioni.setDisableButton1();
                                triggerOperazioni.setDisableButton2();
                                triggerOperazioni.setEnableButton3();
                                triggerInterventi.setDisableButton1();
                                triggerInterventi.setDisableButton2();
                                triggerInterventi.setEnableButton3();
                                triggerSettoriAttivita.setDisableButton1();
                                triggerSettoriAttivita.setDisableButton2();
                                triggerSettoriAttivita.setEnableButton3();
                                Ext.getCmp("azione_form").setValue('modifica');
                            },
                            failure:function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                },
    <% if (session.getAttribute("mappatura") != null) {%>
                    {
                        id: 'gerarchia_operazioni',
                        text: "<%=testiPortale.get("bottone_gerarchia_operazioni")%>",
                        handler: function(){
                            window.location="GerarchiaOperazioni.jsp";
                        }
                    },
                    {
                        id: 'gerarchia_settori',
                        text: "<%=testiPortale.get("bottone_gerarchia_settori")%>",
                        handler: function(){
                            window.location="GerarchiaSettori.jsp";
                        }
                    },
    <%}%>
    <% } %>
                    {
                        id: 'reset',
                        text: "<%=testiPortale.get("bottone_reset")%>",
                        handler: function(){
                            form.getForm().reset();
                            Ext.getCmp('insert').enable();
                            Ext.getCmp('cancel').disable();
                            triggerOperazioni.setEnabled();
                            triggerInterventi.setEnabled();
                            triggerSettoriAttivita.setEnabled();
                            Ext.getCmp("tip_aggregazione").setValue(salva_tip_aggregazione);
                            Ext.getCmp("des_aggregazione").setValue(salva_des_aggregazione);
                            Ext.getCmp("label_des_aggregazione").setValue(salva_des_aggregazione);
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

                    var conta=0;

                    for (ele in Ext.util.JSON.decode(response.responseText).success) {
                        if (Ext.getCmp(ele)) {
                            conta++;
                            if (Ext.util.JSON.decode(response.responseText).success[ele] != '') {
                                Ext.getCmp(ele).setValue(Ext.util.JSON.decode(response.responseText).success[ele]);
                            }
                        }
                    }
                    salva_des_aggregazione=Ext.getCmp("des_aggregazione").getValue();
                    salva_tip_aggregazione=Ext.getCmp("tip_aggregazione").getValue();
                    Ext.getCmp("label_des_aggregazione").setValue('<b>'+salva_des_aggregazione+'</b>');
                    if (conta == 0){
                        winAggregazioni.display();
                    } else {
                        ds.load({params: {start:0, size:grid_row, tip_aggregazione:salva_tip_aggregazione}});
                    }
                    if (Ext.getDom('azione_form').value == 'inserimento') {
                        Ext.getCmp('insert').enable();
                        Ext.getCmp('cancel').disable();
                        Ext.getCmp('save').disable();
                        triggerOperazioni.setEnabled();
                        triggerInterventi.setEnabled();
                        triggerSettoriAttivita.setEnabled();
                    }
                    if (Ext.getDom('azione_form').value == 'modifica') {
                        Ext.getCmp('insert').disable();
                        Ext.getCmp('cancel').enable();
                        Ext.getCmp('save').enable();
                        //triggerOperazioni.setDisableButton1();
                        //triggerOperazioni.setDisableButton2();
                        //triggerOperazioni.setEnableButton3();
                        //triggerInterventi.setDisableButton1();
                        //triggerInterventi.setDisableButton2();
                        //triggerInterventi.setEnableButton3();
                        //triggerSettoriAttivita.setDisableButton1();
                        //triggerSettoriAttivita.setDisableButton2();
                        //triggerSettoriAttivita.setEnableButton3();
                    }
                },
                failure: function(response,request) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
            });


            tree.render(menu_div);
            tree.selectPath("treepanel/source/<%=set_id%>",'id');
        });
<!--</script>-->
