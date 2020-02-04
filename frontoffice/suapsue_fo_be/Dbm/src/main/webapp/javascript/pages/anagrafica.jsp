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

    function isGood(x){
        try {
            new RegExp(x);
            return true;
        } catch(e) {
            return "Stringa non formalmente corretta";
        }
    }
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'anagrafica';
        var sort_field = 'nome';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var comboFlgPrecompilazione = new Ext.form.ComboBox({
            id: 'flg_precompilazione',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_precompilazione")%>",
            name: 'flg_precompilazione',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_precompilazione_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_precompilazione_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['flg_precompilazione','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_precompilazione_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_precompilazione_N")%>"]]
            }),
            valueField: 'flg_precompilazione',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });

        var comboTpRiga = new Ext.form.ComboBox({
            id: 'tp_riga',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tp_riga")%>",
            name: 'tp_riga',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'tp_riga_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tp_riga_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['tp_riga','displayText'],
                data: [['0', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_riga_0")%>"],
                    ['1', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_riga_1")%>"]]
            }),
            valueField: 'tp_riga',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });



        var table_name_tipi_campo_titolare = 'tipi_campo_titolare';
    
        var dyn_fields_campiTipiCampoTitolare = [
            {name: 'tipo'},
            {name: 'descrizione'}
        ];


        var readerTipiCampoTitolare = new Ext.data.JsonReader({
            root:'tipi_campo_titolare',
            fields: dyn_fields_campiTipiCampoTitolare,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            }
        });

        var dsTipiCampoTitolare = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions_CCD?table_name='+table_name_tipi_campo_titolare}),
            remoteSort: true,
            reader: readerTipiCampoTitolare
        });
        
        dsTipiCampoTitolare.load();

        var comboCampoTitolare = new Ext.form.ComboBox({
            id: 'campo_titolare',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_titolare")%>",
            name: 'campo_titolare',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'campo_titolare_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_campo_titolare_select")%>",
            editable: false,
            store: dsTipiCampoTitolare,
            valueField: 'tipo',
            displayField: 'descrizione',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });










        function changeTipo(val){
            if(val == 'I'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_I")%>";
            }else if(val == 'L'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_L")%>";
            }else if(val == 'C'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_C")%>";
            }else if(val == 'R'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_R")%>";
            }else if(val == 'T'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_T")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_N")%>";
            }else if(val == 'A'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_A")%>";
            }
            return val;
        };
        var comboTipo = new Ext.form.ComboBox({
            id: 'tipo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tipo")%>",
            name: 'tipo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'tipo_hidden',
            editable: false,
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tipo_select")%>",
            store: new Ext.data.SimpleStore({
                fields: ['tipo','displayText'],
                data: [['I', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_I")%>"],
                    ['L', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_L")%>"],
                    ['C', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_C")%>"],
                    ['R', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_R")%>"],
                    ['T', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_T")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_N")%>"],
                    ['A', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_A")%>"]
                ]
            }),
            listeners :{select: function(sm, row, rec) {
                    if (row.data.tipo == 'R') {
                        txtFld.clearInvalid();
                    }             
                    if (row.data.tipo == 'L') {
                        Ext.getCmp("aggiungiCampoSelect").enable();
                    } else {
                        Ext.Ajax.request({
                            url: 'gestisciCampoSelectSession',
                            method: 'POST',
                            params: {'table_name':table_name_campi_select, 'operazione':'pulisci'},
                            success: function(response,request) {
                                try {o = Ext.decode(response.responseText);}
                                catch(e) {
                                    this.showError(response.responseText);
                                    return;
                                }
                                if(!o.success) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                }else {
                                    dsCampiSelect.load({params:{session:'yes'}});
                                }
                            },
                            failure: function(response,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                            }
                        });

                        Ext.getCmp("cancellaCampoSelect").disable();
                        Ext.getCmp("aggiungiCampoSelect").disable();
                    }
                }},
            valueField: 'tipo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            resizable:true
        });

        function changeControllo(val){
            if(val == ''){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_")%>";
            }else if(val == 'O'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_O")%>";
            }
            return val;
        };
        var comboControllo = new Ext.form.ComboBox({
            id: 'controllo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_controllo")%>",
            name: 'controllo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'controllo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_controllo_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['controllo','displayText'],
                data: [['', "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_")%>"],
                    ['O', "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_O")%>"]
                ]
            }),
            valueField: 'controllo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            resizable:true
        });
        function changeTpControllo(val){
            if(val == 'I'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_I")%>";
            }else if(val == 'F'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_F")%>";
            }else if(val == 'D'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_D")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_N")%>";
            }else if(val == 'T'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_T")%>";
            }else if(val == 'C'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_C")%>";
            }else if(val == 'X'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_X")%>";
            }
            return val;
        };
        var comboTpControllo = new Ext.form.ComboBox({
            id: 'tp_controllo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tp_controllo")%>",
            name: 'tp_controllo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'tp_controllo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tp_controllo_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['tp_controllo','displayText'],
                data: [['I', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_I")%>"],
                    ['F', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_F")%>"],
                    ['D', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_D")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_N")%>"],
                    ['T', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_T")%>"],
                    ['C', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_C")%>"],
                    ['X', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_X")%>"]
                ]
            }),
            listeners :{select: function(sm, row, rec) {
                    if (row.data.tp_controllo == 'X') {
                        Ext.getCmp("pattern").enable();
                        Ext.getCmp("pattern").markInvalid("Campo obbligatorio");

                    }else{
                        Ext.getCmp("pattern").disable();
                        Ext.getCmp("pattern").setValue("");
                        Ext.getCmp("pattern").valid=true;
                    }
                }},
            valueField: 'tp_controllo',
            displayField: 'displayText',
            selectOnFocus:true,
            resizable:true
        });

        function changeEdit(val){
            if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_S")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_N")%>";
            }
            return val;
        };
        var comboEdit = new Ext.form.ComboBox({
            id: 'edit',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_edit")%>",
            name: 'edit',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'edit_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_edit_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['edit','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_N")%>"]
                ]
            }),
            valueField: 'edit',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            resizable:true
        });
        var rigaNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_riga")%>",
            name: 'riga',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            allowNegative: false,
            allowDecimals: false,
            minValue:1
        });
        var posizioneNumberField = new Ext.form.NumberField({
            //                    id:'posizione',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_posizione")%>",
            name: 'posizione',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            allowNegative: false,
            allowDecimals: false,
            minValue:1
        });
        var lunghezzaNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_lunghezza")%>",
            name: 'lunghezza',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            allowNegative: false,
            allowDecimals: false,
            minValue:0
        });
        var livelloNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_livello")%>",
            name: 'livello',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            allowNegative: false,
            allowDecimals: false,
            minValue:1
        });
        var decimaliNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_decimali")%>",
            name: 'decimali',
            allowNegative: false,
            allowDecimals: false,
            minValue:0
        });
        var formNameWindow = 'edit-form-window';
        var table_name_campi_select = 'anagrafica_select';

        var dyn_fields_campi_select = [
            {name: 'val_select'},
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
             {name: 'des_valore_<%=lingueTotali.get(i)%>'},
             <%}%>            
            {name: 'des_valore_it'}
        ];
        var	itemsFormWindow = [{
                id: 'val_select',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_val_select")%>",
                name: 'val_select',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'des_valore_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_valore")%>",
                name: 'des_valore_it',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
            {
                id: 'des_valore_<%=lingueTotali.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_valore")%>(<%=lingueTotali.get(i)%>)",
                allowBlank: false,
                name: 'des_valore_<%=lingueTotali.get(i)%>',
                labelStyle: 'font-weight:bold;'
            },
                    <%}%>            
            {
                id: 'table_name_select',
                fieldLabel: 'table name',
                name: 'table_name',
                readOnly: true,
                hidden: true,
                hideLabel: true,
                value: table_name_campi_select}];

        var formHrefCampiSelect = new Ext.FormPanel({
            id: formNameWindow,
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
            title:"<%=testiPortale.get(nomePagina + "_campi_select_header_form")%>",
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
                        formHrefCampiSelect.getForm().submit({url:'gestisciCampoSelectSession?operazione=aggiungi',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                dsCampiSelect.load({params:{session:'yes'}});
                                formHrefCampiSelect.getForm().reset();
                                winHrefCampiSelect.hide();
                            },
                            failure:function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                },{
                    id: 'closeWindow',
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){winHrefCampiSelect.hide();}
                }],
            buttonAlign: 'center'
        });

        var winHrefCampiSelect = new Ext.Window({
            closable:true,
            closeAction:'hide',
            width:'80%',
            height:'80%',
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [formHrefCampiSelect],
            display: function(){
                this.show();
            }
        });

        var readerCampiSelect = new Ext.data.JsonReader({
            root:table_name_campi_select,
            fields: dyn_fields_campi_select,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount'
        });

        var dsCampiSelect = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_campi_select}),
            remoteSort: true,
            reader: readerCampiSelect
        });
        dsCampiSelect.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
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
                id:'grid_des_valore_it',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_valore")%>",
                renderer: renderTpl,
                width: 640,
                sortable: true,
                dataIndex: 'des_valore_it'
            }
        ]);
        var gridCampiSelectSelectionModel = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp("cancellaCampoSelect").enable();
                }
            }
        });

        var gridCampiSelect = new Ext.grid.EditorGridPanel({
            id: 'gridCampiSelect',
            store: dsCampiSelect,
            cm: colModelCampiSelect,
            height: 160,
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_grid_campi_select")%>",
            autoScroll:true,
            sm:gridCampiSelectSelectionModel,
            border: true,
            tbar: [{
                    text: "<%=testiPortale.get("bottone_add")%>",
                    id:'aggiungiCampoSelect',
                    disabled:true,
                    listeners:{ 'click': function() {
                            winHrefCampiSelect.display();
                        }
                    }
                },{xtype: 'tbseparator'},{
                    text: "<%=testiPortale.get("bottone_delete")%>",
                    id: 'cancellaCampoSelect',
                    disabled:true,
                    listeners:{
                        'click': function() {
                            Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                            function(e) {
                                if (e=='yes'){
                                    Ext.Ajax.request({
                                        url: 'gestisciCampoSelectSession?operazione=cancella',
                                        method: 'POST',
                                        params: {'val_select':gridCampiSelect.getSelectionModel().getSelected().get('val_select'),
                                            'table_name':table_name_campi_select},
                                        success: function(response,request) {
                                            try {o = Ext.decode(response.responseText);}
                                            catch(e) {
                                                this.showError(response.responseText);
                                                return;
                                            }
                                            if(!o.success) {
                                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                            }else {
                                                dsCampiSelect.load({params: {'session':'yes'}});
                                                Ext.getCmp("cancellaCampoSelect").disable();
                                            }
                                        },
                                        failure: function(response,request) {
                                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                        }
                                    });
                                }
                            }
                        )
                        }
                    }
                }]
        });


        var dyn_fields = [
            {name: 'contatore'},
            {name: 'nome'},
            {name: 'des_campo_it'},
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
             {name: 'des_campo_<%=lingueTotali.get(i)%>'},
             <%}%>            
            {name: 'riga'},
            {name: 'posizione'},
            {name: 'tp_riga'},
            {name: 'tipo'},
            {name: 'controllo'},
            {name: 'tp_controllo'},
            {name: 'valore'},
            {name: 'edit'},
            {name: 'web_serv'},
            {name: 'campo_dati'},
            {name: 'campo_key'},
            {name: 'campo_xml_mod'},
            {name: 'lunghezza'},
            {name: 'decimali'},
            {name: 'nome_xsd'},
            {name: 'precompilazione'},
            {name: 'raggruppamento_check'},
            {name: 'campo_collegato'},
            {name: 'val_campo_collegato'},
            {name: 'livello'},
            {name: 'flg_precompilazione'},
            {name: 'azione'},
            {name: 'err_msg_it'},
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
             {name: 'err_msg_<%=lingueTotali.get(i)%>'},
             <%}%>            
            {name: 'pattern'},
            {name: 'marcatore_incrociato'},
            {name: 'campo_titolare'}
        ];

        var dyn_fields_protection = ['nome'];

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
                id:'grid_contatore',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_contatore")%>",
                width: 30,
                sortable: true,
                dataIndex: 'contatore'
            },
            {
                id:'grid_nome',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_nome")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'nome'
            },
            {
                id:'grid_des_campo_it',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_campo")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'des_campo_it'
            },
            {
                id:'grid_livello',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_livello")%>",
                width: 100,
                sortable: true,
                dataIndex: 'livello'
            }
        ]);

        var txtFld = new Ext.form.TextField({
            id: 'nome',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome")%>",
            name: 'nome',
            maxLength: 10,
            allowBlank: false ,
            labelStyle: 'font-weight:bold;',
            enableKeyEvents: true
        });
        txtFld.on('blur', function(a) {
            if (Ext.getCmp("azione_form").getValue() != 'modifica') {
                if (this.getValue() != "") {
                    Ext.Ajax.request({
                        url: 'leggiEsistenza',
                        method: 'POST',
                        params: {table_name:table_name, tab_key:this.getValue(), tab_key1:Ext.getCmp("tipo").getValue()},
                        success: function(response,request) {
                            try {o = Ext.decode(response.responseText);}
                            catch(e) {
                                this.showError(response.responseText);
                                return;
                            }
                            if(!o.success) {
                                txtFld.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
                                comboTipo.markInvalid(Ext.util.JSON.decode(response.responseText).failure);

                            } else {
                                txtFld.clearInvalid();
                                comboTipo.clearInvalid();
                            }
                        },
                        failure: function(response,request) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }
                    });
                }
            }
        });


        var	itemsForm = [{
                id: 'contatore',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_contatore")%>",
                name: 'contatore',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },txtFld,rigaNumberField,posizioneNumberField,comboTpRiga,comboTipo,comboControllo,comboTpControllo,
            {
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_pattern")%>",
                    name: 'pattern',
                    id: 'pattern',
                    //allowBlank: true,
                    maxLength: 255,
                    validator: function(x) {
                        if (x == ''){
                           return "Campo obbligatorio";
                        }
                        return isGood(x);
                    }
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_err_msg")%>",
                    name: 'err_msg_it',
                    allowBlank: true,
                    maxLength: 255
                },
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
            {
                id: 'err_msg_<%=lingueTotali.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_err_msg")%>(<%=lingueTotali.get(i)%>)",
                maxLength: 255,
                allowBlank: true,
                name: 'err_msg_<%=lingueTotali.get(i)%>'
            },
            <%}%>                
            lunghezzaNumberField,decimaliNumberField,
            {
                id: 'des_campo_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_campo")%>",
                name: 'des_campo_it',
                maxLength: 65000,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
                        <% for (int i = 0; i < lingueTotali.size(); i++){ %>
            {
                id: 'des_campo_<%=lingueTotali.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_campo")%>(<%=lingueTotali.get(i)%>)",
                maxLength: 64000,
                allowBlank: false,
                name: 'des_campo_<%=lingueTotali.get(i)%>',
                labelStyle: 'font-weight:bold;'
            },
                    <%}%>
            comboEdit,livelloNumberField,{
                id: 'valore',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_valore")%>",
                name: 'valore',
                maxLength: 20
            },{
                id: 'precompilazione',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_precompilazione")%>",
                name: 'precompilazione',
                maxLength: 100
            },{
                id: 'campo_xml_mod',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_xml_mod")%>",
                name: 'campo_xml_mod',
                maxLength: 255
            },{
                id: 'campo_key',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_key")%>",
                name: 'campo_key',
                maxLength: 10
            },{
                id: 'campo_dati',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_dati")%>",
                name: 'campo_dati',
                maxLength: 255
            },{
                id: 'web_serv',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_web_serv")%>",
                name: 'web_serv',
                maxLength: 255
            },{
                id: 'nome_xsd',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_xsd")%>",
                name: 'nome_xsd',
                maxLength: 255
            },{
                id: 'raggruppamento_check',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_raggruppamento_check")%>",
                name: 'raggruppamento_check',
                maxLength: 10
            },{
                id: 'campo_collegato',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_collegato")%>",
                name: 'campo_collegato',
                maxLength: 10
            },{
                id: 'val_campo_collegato',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_val_campo_collegato")%>",
                name: 'val_campo_collegato',
                maxLength: 20
            },comboFlgPrecompilazione,{
                id: 'azione',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_azione")%>",
                name: 'azione',
                maxLength: 50
            },{
                id: 'marcatore_incrociato',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_marcatore_incrociato")%>",
                name: 'marcatore_incrociato',
                maxLength: 45
            },comboCampoTitolare,gridCampiSelect,{
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
                    dsCampiSelect.load({params: {'nome':rec.data.nome}});
                    if (rec.data.tipo == 'L') {
                        Ext.getCmp("aggiungiCampoSelect").enable();
                    }
                    if (rec.data.tp_controllo != 'X') {
                        Ext.getCmp("pattern").disable();
                        Ext.getCmp("pattern").clearInvalid();
                    } else {
                        Ext.getCmp("pattern").enable();
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
            height:300,
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
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                        Ext.Ajax.request({
                                            url: 'gestisciCampoSelectSession',
                                            method: 'POST',
                                            params: {'table_name':table_name_campi_select,'operazione':'pulisci'},
                                            success: function(response,request) {
                                                try {o = Ext.decode(response.responseText);}
                                                catch(e) {
                                                    this.showError(response.responseText);
                                                    return;
                                                }
                                                if(!o.success) {
                                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                                } else {
                                                    dsCampiSelect.load({params:{'session':'yes'}});
                                                }
                                            },
                                            failure: function(response,request) {
                                                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                            }
                                        });
                                        ds.load({params: {start:0, size:grid_row}});
                                        form.getForm().reset();
                                        Ext.getCmp("save").disable();
                                        Ext.getCmp("insert").enable();
                                        Ext.getCmp("cancel").disable();
                                        Ext.getCmp("azione_form").setValue('inserimento');
                                        Ext.getCmp("cancellaCampoSelect").disable();
                                        Ext.getCmp("aggiungiCampoSelect").disable();
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
                    text:"<%=testiPortale.get("bottone_insert")%>",
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
                                dsCampiSelect.load({params:{'session':'yes'}});
                            },
                            failure:function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                },{
                    id: 'reset',
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        form.getForm().reset();
                        Ext.Ajax.request({
                            url: 'gestisciCampoSelectSession',
                            method: 'POST',
                            params: {'table_name':table_name_campi_select , 'operazione':'pulisci'},
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
                        dsCampiSelect.load({params:{'session':'yes'}});
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
                    var conta=0;
                    for (ele in Ext.util.JSON.decode(response.responseText).success) {
                        conta++;
                        if (Ext.getCmp(ele)) {
                            if (Ext.util.JSON.decode(response.responseText).success[ele] != '') {
                                Ext.getCmp(ele).setValue(Ext.util.JSON.decode(response.responseText).success[ele]);
                            }
                        }
                    }
                    if (conta==0) {
                        Ext.Ajax.request({
                            url: 'gestisciCampoSelectSession',
                            method: 'POST',
                            params: {'table_name':table_name_campi_select , 'operazione':'pulisci'},
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

