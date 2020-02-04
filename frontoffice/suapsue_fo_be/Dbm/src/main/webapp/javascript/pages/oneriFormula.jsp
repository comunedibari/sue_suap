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
    boolean territorio = ((Boolean) session.getAttribute("territorialitaNew")).booleanValue();
%>

    function trim(str, chars) {
        return ltrim(rtrim(str, chars), chars);
    }

    function ltrim(str, chars) {
        chars = chars || "\\s";
        return str.replace(new RegExp("^[" + chars + "]+", "g"), "");
    }

    function rtrim(str, chars) {
        chars = chars || "\\s";
        return str.replace(new RegExp("[" + chars + "]+$", "g"), "");
    }
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 15;
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
        
        //OneriCampi
        var sort_field_oneri_campi = 'cod_onere_campo';
        var expand_field_oneri_campi = '';
        var table_name_oneri_campi = 'oneri_campi';
        var id_field_oneri_campi = '';

        var dyn_fields_oneri_campi = [
            {name: 'cod_onere_campo'},
            {name: 'testo_campo'}];
            
        var readerOneriCampi = new Ext.data.JsonReader({
            root:table_name_oneri_campi,
            fields: dyn_fields_oneri_campi,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_oneri_campi
        });

        var dsOneriCampi = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'popUp?table_name='+table_name_oneri_campi}),
            sortInfo: {field:sort_field_oneri_campi,direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerOneriCampi
        });

        dsOneriCampi.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        });
        var searchSelectionModelOneriCampi = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.Ajax.request({
                        url: 'gestisciCampoSelectSession',
                        method: 'POST',
                        params: {'cod_onere_campo':rec.data.cod_onere_campo, 'table_name':table_name_oneri_campi, 'operazione':'aggiungi'},
                        success: function(response,request) {
                            try {o = Ext.decode(response.responseText);}
                            catch(e) {
                                this.showError(response.responseText);
                                return;
                            }
                            if(!o.success) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                            }else {
                                dsCampi.load({params: {cod_onere_formula:Ext.getCmp('cod_onere_formula').value,session:'yes'}});
                            }
                        },
                        failure: function(response,request) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }
                    });
                    winOneriCampi.hide();
                }
            }
        });

        var colModelOneriCampi = new Ext.grid.ColumnModel([
            {
                id:'cod_onere_campo_search',
                header: "<%=testiPortale.get("header_cod_oneri_campo")%>",
                width: 150,
                sortable: true,
                dataIndex: 'cod_onere_campo'
            },
            {
                id:'testo_campo_search',
                header: "<%=testiPortale.get("header_testo_campo")%>",
                width: 850,
                sortable: true,
                dataIndex: 'testo_campo'
            }
        ]);
        var searchOneriCampi = new Ext.ux.form.SearchField({
            store: dsOneriCampi,
            width:320,
            paging: true,
            paging_size: grid_row_popup
        });
        var gridOneriCampi = new Ext.grid.GridPanel({
            id:'grid_oneri_campi',
            frame:true,
            ds: dsOneriCampi,
            cm: colModelOneriCampi,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelOneriCampi,
            oadMask: true,
            autoExpandColumn: expand_field_oneri_campi,
            height:400,
            bbar: new Ext.PagingToolbar({
                store: dsOneriCampi,
                pageSize:grid_row_popup,
                displayInfo:true,
                items:[searchOneriCampi]
            }),
            border: true
        });
        var winOneriCampi = new Ext.Window({
            title: "<%=testiPortale.get("titolo_window_oneri_campi")%>",
            closable:true,
            closeAction:'hide',
            width:900,
            height:500,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [gridOneriCampi],
            buttons: [{
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){winOneriCampi.hide();}
                }],
            display: function(){
                dsOneriCampi.reload({params:{start: 0, size:grid_row_popup}});
                this.show();
            },
            lookupFields: ['cod_onere_campo','testo_campo']
        });

        var table_name = 'oneri_formula';
        var sort_field = 'cod_onere_formula';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'cod_onere_formula'},
            {name: 'des_formula'},
            {name: 'tip_aggregazione'},
            {name: 'des_aggregazione'},             
            {name: 'formula'}
        ];
        var dyn_fields_protection = ['cod_onere_formula'];

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
                id:'grid_cod_onere_formula',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_onere_formula")%>",
                width: 50,
                sortable: true,
                dataIndex: 'cod_onere_formula'
            },
            {
                id:'grid_des_formula',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_formula")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'des_formula'
            }
    <% if (territorio) {%>
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
                                dsCampi.load({params: {cod_onere_formula:rec.data.cod_onere_formula}});
                                Ext.getCmp("insert").disable();
                                Ext.getCmp("azione_form").setValue('modifica');
                                if (tipoAggregazione == rec.data.tip_aggregazione || rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined || tipoAggregazione == null) {
                                    Ext.getCmp("aggiungiCampo").enable();
                                    Ext.getCmp("save").enable();
                                    Ext.getCmp("cancel").enable();
                                } else {
                                    Ext.getCmp("save").disable();
                                    Ext.getCmp("cancel").disable();
                                    Ext.getCmp("aggiungiCampo").disable();
                                    Ext.getCmp("cancellaCampo").disable();
                                    Ext.getCmp("modificaCampo").disable();                            
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
                        height:400,
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
                    var table_name_campi = 'oneri_campi_formula';

                    var dyn_fields_campi = [
                        {name: 'cod_onere_campo'},
                        {name: 'testo_campo'}
                    ];

                    var readerCampi = new Ext.data.JsonReader({
                        root:table_name_campi,
                        fields: dyn_fields_campi,
                        baseParams: {
                            lightWeight:true,
                            ext: 'js'
                        }
                    });

                    var dsCampi = new Ext.data.Store({
                        proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_campi}),
                        remoteSort: true,
                        reader: readerCampi
                    });
                    var gridCampiSelectionModel = new Ext.grid.RowSelectionModel({
                        singleSelect: true,
                        listeners: {
                            rowselect: function(sm, row, rec) {
                                if (tipoAggregazione == Ext.getCmp("tip_aggregazione").getValue() || Ext.getCmp("tip_aggregazione").getValue() == '' || Ext.getCmp("tip_aggregazione") == undefined || tipoAggregazione == null) {
                                    Ext.getCmp("cancellaCampo").enable();
                                    Ext.getCmp("modificaCampo").enable();
                                } else {
                                    Ext.getCmp("aggiungiCampo").disable();
                                    Ext.getCmp("cancellaCampo").disable();
                                    Ext.getCmp("modificaCampo").disable();                            
                                }                    
                            }
                        }

                    });

                    var colModelCampi = new Ext.grid.ColumnModel([
                        {
                            id:'grid_cod_onere_campo',
                            header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_onere_campo")%>",
                            width: 150,
                            sortable: true,
                            dataIndex: 'cod_onere_campo'
                        },
                        {
                            id:'grid_testo_campo',
                            header: "<%=testiPortale.get(nomePagina + "_header_grid_testo_campo")%>",
                            renderer: renderTpl,
                            width: 640,
                            sortable: true,
                            dataIndex: 'testo_campo'
                        }
                    ]);
                    var gridCampi = new Ext.grid.EditorGridPanel({
                        store: dsCampi,
                        cm: colModelCampi,
                        height: 100,
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_grid_campi")%>",
                        autoScroll:true,
                        sm:gridCampiSelectionModel,
                        border: true,
                        tbar: [{
                                text: "<%=testiPortale.get("button_grid_campi_aggiungi")%>",
                                id:'aggiungiCampo',
                                disabled:true,
                                listeners:{ 'click': function() {
                                        winOneriCampi.display();
                                    }
                                }
                            },{xtype: 'tbseparator'},{
                                text: "<%=testiPortale.get("button_grid_campi_elimina")%>",
                                id: 'cancellaCampo',
                                disabled: true,
                                listeners:{
                                    'click': function() {
                                        Ext.Ajax.request({
                                            url: 'gestisciCampoSelectSession',
                                            method: 'POST',
                                            params: {'cod_onere_campo':gridCampi.getSelectionModel().getSelected().get('cod_onere_campo'), 'table_name':table_name_oneri_campi, 'operazione':'cancella'},
                                            success: function(response,request) {
                                                try {o = Ext.decode(response.responseText);}
                                                catch(e) {
                                                    this.showError(response.responseText);
                                                    return;
                                                }
                                                if(!o.success) {
                                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                                }else {
                                                    dsCampi.load({params: {cod_onere_formula:Ext.getCmp('cod_onere_formula').value,session:'yes'}});
                                                    Ext.getCmp("cancellaCampo").disable();
                                                }
                                            },
                                            failure: function(response,request) {
                                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                            }
                                        });
                                    }
                                }
                            },{xtype: 'tbseparator'},{
                                text: "<%=testiPortale.get("button_grid_campi_gestisci")%>",
                                id: 'modificaCampo',
                                disabled: true,
                                listeners:{ 'click': function() {
                                        var items=Ext.getCmp(formName).getForm().getValues();
                                        var par={};
                                        for (i in items) {
                                            par[i]=items[i];
                                        }
                                        Ext.Ajax.request({
                                            url: urlScriviSessione,
                                            method: 'POST',
                                            params: par,
                                            success: function(response,request) {
                                                try {o = Ext.decode(response.responseText);}
                                                catch(e) {
                                                    this.showError(response.responseText);
                                                    return;
                                                }
                                                if(!o.success) {
                                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                                }else {
                                                    window.location="<%=basePath%>protected/OneriCampi.jsp?set_id=<%=set_id%>&cod_onere_campo="+gridCampi.getSelectionModel().getSelected().get('cod_onere_campo');
                                                }
                                            },
                                            failure: function(response,request) {
                                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                            }
                                        });
                                    }
                                }
                            }]
                    });
                    var txtFld = new Ext.form.TextField({
                        id: 'cod_onere_formula',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_onere_formula")%>",
                        name: 'cod_onere_formula',
                        allowBlank: false,
                        labelStyle: 'font-weight:bold;'	,
                        maxLength:10,
                        enableKeyEvents: true
                    });

                    txtFld.on('blur', function(a) {
                        if (Ext.getCmp("azione_form").getValue() != 'modifica') {
                            if (trim(this.getValue()) != "") {
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
                                        } else {
                                            Ext.getCmp("aggiungiCampo").enable();
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
                            id: 'des_formula',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_formula")%>",
                            name: 'des_formula',
                            allowBlank: false ,
                            maxLength:255,
                            labelStyle: 'font-weight:bold;'
                        },{
                            id: 'formula',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_formula")%>",
                            name: 'formula',
                            xtype:'textarea',
                            height: 75,
                            maxLength:64000,
                            allowBlank: false ,
                            labelStyle: 'font-weight:bold;'
                        },gridCampi
    <% if (territorio) {%>
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
                            dsCampi.load({params: {cod_onere_formula:''}});
                            dsCampi.removeAll();
                            Ext.getCmp('insert').enable();
                            Ext.getCmp('save').disable();
                            Ext.getCmp('cancel').disable();
                            Ext.getCmp('cancellaCampo').disable();
                            Ext.getCmp('modificaCampo').disable();
                            Ext.getCmp('aggiungiCampo').disable();
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
                        var conta=0;
                        for (ele in Ext.util.JSON.decode(response.responseText).success) {
                            conta++;
                            if (Ext.getCmp(ele)) {
                                if (Ext.util.JSON.decode(response.responseText).success[ele] != '') {
                                    Ext.getCmp(ele).setValue(Ext.util.JSON.decode(response.responseText).success[ele]);
                                }
                            }
                        }
                        if (conta == 0) {

                            Ext.Ajax.request({
                                url: 'gestisciCampoSelectSession',
                                method: 'POST',
                                params: { 'table_name':table_name_oneri_campi, 'operazione':'pulisci'},
                                success: function(response,request) {
                                    try {o = Ext.decode(response.responseText);}
                                    catch(e) {
                                        this.showError(response.responseText);
                                        return;
                                    }
                                    if(!o.success) {
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                    }
                                },
                                failure: function(response,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                }
                            });

                        }

                        if (Ext.getDom('azione_form').value == 'inserimento') {
                            Ext.getCmp("aggiungiCampo").enable();
                            Ext.getCmp('insert').enable();
                            Ext.getCmp('save').disable();
                            Ext.getCmp('cancel').disable();
                        }
                        if (Ext.getDom('azione_form').value == 'modifica') {
                            Ext.getCmp("aggiungiCampo").enable();
                            Ext.getCmp("save").enable();
                            Ext.getCmp("insert").disable();
                            Ext.getCmp("cancel").enable();
                        }
                        if (Ext.getDom('azione_form').value != ''){
                            dsCampi.load({params: {cod_onere_formula:Ext.getCmp('cod_onere_formula').value,session:'yes'}});
                        }
                    }
                },
                failure: function(response,request) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
            });
            cod_onere_formula=Ext.getUrlParam('cod_onere_formula');
            if (cod_onere_formula) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {'cod_onere_formula':cod_onere_formula, 'table_name':table_name},
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
                                dsCampi.load({params: {cod_onere_formula:cod_onere_formula}});
                                Ext.getCmp("modificaCampo").disable();
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
