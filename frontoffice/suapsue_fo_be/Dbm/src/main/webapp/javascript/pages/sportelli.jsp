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
<%--
    Document   : sportelli
    Modified on : August - September 2011
    Author     : CCD - Riccardo Forafo'
    Modified rows: from 150 to 164
    Modified rows: from 320 to 514
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
    List<String> lingueTotali = (List<String>) session.getAttribute("lingueTotali");
    String set_id = request.getParameter("set_id");
%>

    Ext.onReady(function(){

        Ext.QuickTips.init();

        var codSportSelected = null;
        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'sportelli';
        var sort_field = 'cod_sport';

        var id_field = '';
        var group_field = '';
        var expand_field = '';
        function changeAttivo(val){
            if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_attivo_S")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_attivo_N")%>";
            }
            return val;
        };
        var comboAttivo = new Ext.form.ComboBox({
            id: 'flg_attivo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_attivo")%>",
            name: 'flg_attivo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_attivo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_attivo_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_attivo','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_attivo_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_attivo_N")%>"]]
            }),
            valueField: 'flg_attivo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true
        });
        function changePu(val){
            if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_pu_S")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_pu_N")%>";
            }
            return val;
        };
        var comboPu = new Ext.form.ComboBox({
            id: 'flg_pu',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_pu")%>",
            name: 'flg_pu',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_pu_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_pu_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_pu','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_pu_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_pu_N")%>"]]
            }),
            valueField: 'flg_pu',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true
        });
        function changeSu(val){
            if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_su_S")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_su_N")%>";
            }
            return val;
        };
        var comboSu = new Ext.form.ComboBox({
            id: 'flg_su',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_su")%>",
            name: 'flg_su',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_su_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_su_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_su','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_su_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_su_N")%>"]]
            }),
            valueField: 'flg_su',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            editable: false,
            resizable:true
        });
        var comboOptionAllegati = new Ext.form.ComboBox({
            id: 'flg_option_allegati',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_option_allegati")%>",
            name: 'flg_option_allegati',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_option_allegati_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_option_allegati_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_option_allegati','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_option_allegati_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_option_allegati_N")%>"]]
            }),
            valueField: 'flg_option_allegati',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            editable: false,
            resizable:true
        });
        var comboOggettoRicevuta = new Ext.form.ComboBox({
            id: 'flg_oggetto_ricevuta',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_oggetto_ricevuta")%>",
            name: 'flg_oggetto_ricevuta',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_oggetto_ricevuta_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_oggetto_ricevuta_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_oggetto_ricevuta','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_oggetto_ricevuta_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_oggetto_ricevuta_N")%>"]]
            }),
            valueField: 'flg_oggetto_ricevuta',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            width: 300,
            labelStyle: 'font-weight:bold;',
            editable: false,
            resizable:true
        });
        var comboOggettoRiepilogo = new Ext.form.ComboBox({
            id: 'flg_oggetto_riepilogo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_oggetto_riepilogo")%>",
            name: 'flg_oggetto_riepilogo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_oggetto_riepilogo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_oggetto_riepilogo_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_oggetto_riepilogo','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_oggetto_riepilogo_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_oggetto_riepilogo_N")%>"]]
            }),
            valueField: 'flg_oggetto_riepilogo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            width: 300,
            labelStyle: 'font-weight:bold;',
            editable: false,
            resizable:true
        });        
    <%--
    CCD
    --%>
        var comboDimMaxAllUM = new Ext.form.ComboBox({
            id: 'allegati_dimensione_max_um',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_dim_max_all_um")%>",
            name: 'allegati_dimensione_max_um',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'allegati_dimensione_max_um_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_dim_max_all_um_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['allegati_dimensione_max_um','displayText'],
                data: [['B', "<%=testiPortale.get(nomePagina + "_field_form_combo_dim_max_all_um_B_label")%>"],
                    ['KB', "<%=testiPortale.get(nomePagina + "_field_form_combo_dim_max_all_um_KB_label")%>"],
                    ['MB', "<%=testiPortale.get(nomePagina + "_field_form_combo_dim_max_all_um_MB_label")%>"]]
            }),
            valueField: 'allegati_dimensione_max_um',
            value: 'MB',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:normal;',
            editable: false,
            resizable:false
        });

		//href
		var triggerHref = new Ext.ux.form.SearchTripleFieldForm({
			id: 'href',
			fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_href")%>",
			name: 'href',
			window: winHref,
			hideTrigger1:false,
			hideTrigger3:true,
			clearButton: true,
			lookupFields:['href','tit_href'],
			windowLink:"<%=basePath%>protected/Href.jsp?set_id=<%=set_id%>",
			windowLinkKeys:['href'],
			formName: formName,
			width: 300,
			url: urlScriviSessione
		});
        
    <%--
    CCD
    --%>


        var dyn_fields = [
            {name: 'cod_sport'},
            {name: 'nome_rup'},
            {name: 'flg_attivo'},
            {name: 'flg_pu'},
            {name: 'indirizzo'},
            {name: 'cap'},
            {name: 'citta'},
            {name: 'prov'},
            {name: 'tel'},
            {name: 'fax'},
            {name: 'email'},
            {name: 'email_cert'},
            {name: 'flg_su'},
            {name: 'riversamento_automatico'},
            {name: 'des_sport_it'},
    <% for (int i = 0; i < lingueTotali.size(); i++) {%>
                        {name: 'des_sport_<%=lingueTotali.get(i)%>'},
    <%}%>              
                         {name: 'id_mail_server'},
                         {name: 'id_protocollo'},
                         {name: 'id_backoffice'},
                         {name: 'template_oggetto_ricevuta'},
                         {name: 'template_corpo_ricevuta'},
                         {name: 'template_nome_file_zip'},
                         {name: 'send_zip_file'},
                         {name: 'send_single_files'},
                         {name: 'send_xml'},
                         {name: 'send_signature'},
                         {name: 'send_ricevuta_dopo_protocollazione'},
                         {name: 'send_ricevuta_dopo_invio_bo'},
                         {name: 'template_oggetto_mail_suap'},
                         {name: 'ae_codice_utente'},
                         {name: 'ae_codice_ente'},
                         {name: 'ae_tipo_ufficio'},
                         {name: 'ae_codice_ufficio'},
                         {name: 'ae_tipologia_servizio'},
                         {name: 'allegati_dimensione_max'},
                         {name: 'allegati_dimensione_max_um'},
                         {name: 'visualizza_stampa_modello_in_bianco'},
                         {name: 'visualizza_versione_pdf'},
                         {name: 'firma_on_line'},
                         {name: 'firma_off_line'},
                         {name: 'send_protocollo_param'},
                         {name: 'flg_option_allegati'},
                         {name: 'flg_oggetto_ricevuta'},
                         {name: 'flg_oggetto_riepilogo'},
						 {name: 'href'},,
						 {name: 'tit_href'}
                     ];
                     var dyn_fields_protection = ['cod_sport'];

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

                     var colModel = new Ext.grid.ColumnModel([
                         {
                             id:'grid_cod_sport',
                             header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_sport")%>",
                             width: 50,
                             sortable: true,
                             dataIndex: 'cod_sport'
                         },
                         {
                             id:'grid_des_sport_it',
                             header: "<%=testiPortale.get(nomePagina + "_header_grid_des_sport")%>",
                             renderer: renderTpl,
                             width: 350,
                             sortable: true,
                             dataIndex: 'des_sport_it'
                         },
                         {
                             id:'grid_nome_rup',
                             header: "<%=testiPortale.get(nomePagina + "_header_grid_nome_rup")%>",
                             renderer: renderTpl,
                             width: 350,
                             sortable: true,
                             dataIndex: 'nome_rup'
                         },{
                             id:'grid_flg_attivo',
                             header: "<%=testiPortale.get(nomePagina + "_header_grid_flg_attivo")%>",
                             renderer: changeAttivo,
                             width: 50,
                             sortable: true,
                             dataIndex: 'flg_attivo'
                         }
                     ]);
                     var txtFld = new Ext.form.TextField({
                         id: 'cod_sport',
                         fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_sport")%>",
                         name: 'cod_sport',
                         allowBlank: false,
                         maxLength: 10,
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
                                         } else {
                                             Ext.getCmp("aggiungiCampo").enable();                    
		                             Ext.getCmp("aggiungiCampoWs").enable();
                                         }    
                                     },
                                     failure: function(response,request) {
                                         Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                     }
                                 });
                             }
                         }
                     });




    <%--
    CCD
    --%>


        var table_name_protocollo_segnatura = 'sportelli_param_prot_pec';
    
        var dyn_fields_campiProtocolloSegnatura = [
            {name: 'name'},
            {name: 'value'}
        ];


        var readerCampiProtocolloSegnatura = new Ext.data.JsonReader({
            root:'dati_protocollo_segnatura',
            fields: dyn_fields_campiProtocolloSegnatura,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            }
        });

        var dsProtocolloSegnatura = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions_CCD?table_name='+table_name_protocollo_segnatura}),
            remoteSort: true,
            reader: readerCampiProtocolloSegnatura
        });

        var gridProtocolloSegnaturaSelectionModel = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp("rimuoviCampo").enable();
                }
            }
        });

        var editor = new Ext.ux.grid.RowEditor({
            saveText: 'Aggiorna',
            cancelText: 'Annulla'
        });
    
        var colModelProtocolloSegnatura = new Ext.grid.ColumnModel([
            {
                id:'grid_name',
                header: "Nome parametro",
                width: 150,
                sortable: true,
                editable: true,
                dataIndex: 'name',
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            },
            {
                id:'grid_value',
                header: "Valore parametro",
                width: 640,
                sortable: true,
                editable: true,
                dataIndex: 'value',
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }
        ]);

        var gridProtocolloSegnatura = new Ext.grid.EditorGridPanel({
            store: dsProtocolloSegnatura,
            plugins: [editor],
            cm: colModelProtocolloSegnatura,
            height: 150,
            fieldLabel: "Protocollo segnatura",
            autoScroll:true,
            sm:gridProtocolloSegnaturaSelectionModel,
            border: true,
            tbar:
                [
                 {
                    text: "<%=testiPortale.get("button_grid_campi_aggiungi")%>",
                    id:'aggiungiCampo',
                    disabled:true,
                    handler: function(){
                        var newRecord =  new (gridProtocolloSegnatura.getStore().recordType) ({
                            name: 'nome', value: 'valore'});
                        editor.stopEditing();
                        gridProtocolloSegnatura.getStore().insert(0, newRecord);
                        gridProtocolloSegnatura.getView().refresh();
                        gridProtocolloSegnatura.getSelectionModel().selectRow(0);
                        editor.startEditing(0);
                    }
    		},{xtype: 'tbseparator'},
                {
                    text: "<%=testiPortale.get("button_grid_campi_elimina")%>",
                    id:'rimuoviCampo',
                    disabled:true,
                    handler: function(){
                        Ext.MessageBox.confirm('Conferma eliminazione', 'Eliminare il parametro?', function(btn) {
                            if (btn == 'yes') {
                                editor.stopEditing();
                                var s = gridProtocolloSegnatura.getSelectionModel().getSelections();
                                for(var i = 0, r; r = s[i]; i++){
                                    gridProtocolloSegnatura.getStore().remove(r);
                                }
                            }
                        }
                    );
                    }
                }
            ]
        });


        var table_name_protocollo_ws = 'sportelli_param_prot_ws';
    
        var dyn_fields_campiProtocolloWs = [
            {name: 'name'},
            {name: 'value'}
        ];


        var readerCampiProtocolloWs = new Ext.data.JsonReader({
            root:'dati_protocollo_ws',
            fields: dyn_fields_campiProtocolloWs,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            }
        });

        var dsProtocolloWs = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions_CCD?table_name='+table_name_protocollo_ws}),
            remoteSort: true,
            reader: readerCampiProtocolloWs
        });

        var gridProtocolloWsSelectionModel = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp("rimuoviCampoWs").enable();
                }
            }
        });

        var editorWs = new Ext.ux.grid.RowEditor({
            saveText: 'Aggiorna',
            cancelText: 'Annulla'
        });
    
        var colModelProtocolloWs = new Ext.grid.ColumnModel([
            {
                id:'grid_name_ws',
                header: "Nome parametro",
                width: 150,
                sortable: true,
                editable: true,
                dataIndex: 'name',
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            },
            {
                id:'grid_value_ws',
                header: "Valore parametro",
                width: 640,
                sortable: true,
                editable: true,
                dataIndex: 'value',
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }
        ]);

        var gridProtocolloWs = new Ext.grid.EditorGridPanel({
            store: dsProtocolloWs,
            plugins: [editorWs],
            cm: colModelProtocolloWs,
            height: 150,
            fieldLabel: "Protocollo",
            autoScroll:true,
            sm:gridProtocolloWsSelectionModel,
            border: true,
            tbar:
                [
                 {
                    text: "<%=testiPortale.get("button_grid_campi_aggiungi")%>",
                    id:'aggiungiCampoWs',
                    disabled:true,
                    handler: function(){
                        var newRecordWs =  new (gridProtocolloWs.getStore().recordType) ({
                            name: 'nome', value: 'valore'});
                        editorWs.stopEditing();
                        gridProtocolloWs.getStore().insert(0, newRecordWs);
                        gridProtocolloWs.getView().refresh();
                        gridProtocolloWs.getSelectionModel().selectRow(0);
                        editorWs.startEditing(0);
                    }
    		},{xtype: 'tbseparator'},
                {
                    text: "<%=testiPortale.get("button_grid_campi_elimina")%>",
                    id:'rimuoviCampoWs',
                    disabled:true,
                    handler: function(){
                        Ext.MessageBox.confirm('Conferma eliminazione', 'Eliminare il parametro?', function(btn) {
                            if (btn == 'yes') {
                                editorWs.stopEditing();
                                var s = gridProtocolloWs.getSelectionModel().getSelections();
                                for(var i = 0, r; r = s[i]; i++){
                                    gridProtocolloWs.getStore().remove(r);
                                }
                            }
                        }
                    );
                    }
                }
            ]
        });
		

        var triggerFae = new Ext.ux.form.SearchTripleFieldForm({
            id: 'ae_codice_ente',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_ae_codice_ente")%>",
            name: 'ae_codice_ente',
            window: winFae,
            hideTrigger1:false,
            hideTrigger3:true,
            clearButton: false,
            lookupFields:['ae_codice_ente','ae_tipo_ufficio', 'ae_codice_ufficio'],
            windowLink: false,
            formName: formName,
            labelStyle: 'font-weight:bold;',
            url: false,
            enableKeyEvents: true,
            allowBlank: true
        });

    <%--
    CCD
    --%>

        var	itemsForm = [txtFld,{
                id: 'des_sport_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_sport")%>",
                name: 'des_sport_it',
                allowBlank: false,
                maxLength: 255,
                labelStyle: 'font-weight:bold;'
            },
    <% for (int i = 0; i < lingueTotali.size(); i++) {%>
                         {
                             id: 'des_sport_<%=lingueTotali.get(i)%>',
                             fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_sport")%>(<%=lingueTotali.get(i)%>)",
                             allowBlank: false,
                             maxLength: 255,
                             name: 'des_sport_<%=lingueTotali.get(i)%>',
                             labelStyle: 'font-weight:bold;'
                         },
    <%}%>               
                        {
                            id: 'nome_rup',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_rup")%>",
                            name: 'nome_rup',
                            allowBlank: false,
                            maxLength: 255,
                            labelStyle: 'font-weight:bold;'
                        },{
                            id: 'indirizzo',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_indirizzo")%>",
                            maxLength: 255,
                            name: 'indirizzo'
                        },{
                            id: 'citta',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_citta")%>",
                            maxLength: 255,
                            name: 'citta'
                        },{
                            id: 'cap',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cap")%>",
                            maxLength: 5,
                            name: 'cap'
                        },{
                            id: 'prov',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_prov")%>",
                            maxLength: 4,
                            name: 'prov'
                        },{
                            id: 'tel',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tel")%>",
                            maxLength: 20,
                            name: 'tel'
                        },{
                            id: 'fax',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_fax")%>",
                            maxLength: 20,
                            name: 'fax'
                        },{
                            id: 'email',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_email")%>",
                            maxLength: 255,
                            name: 'email'
                        },{
                            id: 'email_cert',
                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_email_cert")%>",
                            maxLength: 255,
                            name: 'email_cert'
                        },
            
    <%--
    CCD
    --%>
            {
                xtype: 'fieldset', 
                title: "<%=testiPortale.get(nomePagina + "_field_form_dim_max_all_settings")%>", 
                border: '1',
                items: [
                    {
                        xtype       : 'container',
                        border      : false,
                        layout      : 'column',
                        defaultType : 'field',
                        items       : [
                            {
                                xtype  : 'container',
                                layout : 'form', 
                                width  : 270,
                                items  : [
                                    {
                                        xtype			: 'numberfield',
                                        value			: 0,
                                        blankText		: 'Inserire un valore maggiore od uguale a zero.',
                                        allowDecimals	: false,
                                        allowBlank	: false,
                                        allowNegative	: true,
                                        minValue		: -1,
                                        id		 	: 'allegati_dimensione_max',
                                        fieldLabel 	: "<%=testiPortale.get(nomePagina + "_field_form_dim_max_all")%>",
                                        name       	: 'allegati_dimensione_max',
                                        anchor     	: '-20'
                                    }
                                ]
                            },
                            {
                                xtype       : 'container',
                                layout      : 'form',
                                columnWidth : 1,
                                labelWidth  : 40,
                                items       : [
                                    comboDimMaxAllUM
                                ]
                            }
                        ]
                    }
                ]
            }
    <%--
    CCD
    --%>
            
            
            ,comboPu,comboSu,comboAttivo,comboOptionAllegati,
            
            
            {
                items: [
            
            comboOggettoRicevuta,comboOggettoRiepilogo,
    <%--
    CCD
    --%>
			triggerHref,{
				id: 'tit_href',
				fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_href")%>",
				name: 'tit_href',
				width: '90%',
				xtype: 'textfield',
				readOnly: true
			}
                ],
                xtype: 'fieldset',
                title: "<%=testiPortale.get(nomePagina + "_field_form_impostazioni_oggetto_title")%>",
                width: '100%',
                border: '1' 
            },			
			
			
			
    <%--
    CCD
    --%>
            {
                xtype: 'fieldset', 
                title: "<%=testiPortale.get(nomePagina + "_field_form_id_mail_server_title")%>", 
                border: '1',
                items: [
                    {
                        id: 'id_mail_server',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_id_mail_server")%>",
                        maxLength: 250,
                        width: '90%',
                        xtype: 'textfield',
                        name: 'id_mail_server'
                    },
                    {
                        xtype: 'checkboxgroup',
                        columns: 4,
                        vertical: false,
                        items: [
                            {
                                id: 'send_zip_file',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_send_zip_file")%>",
                                xtype: 'checkbox',
                                name: 'send_zip_file'
                            },
                            {
                                id: 'send_single_files',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_send_single_files")%>",
                                xtype: 'checkbox',
                                name: 'send_single_files'
                            },
                            {
                                id: 'send_xml',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_send_xml")%>",
                                xtype: 'checkbox',
                                name: 'send_xml'
                            }
                        ]
                    },
                    {
                        xtype: 'fieldset',
                        title: "Segnatura cittadino", 
                        width: '100%',
                        collapsible: true,
                        border: '1', 
                        items: [
                            {
                                id: 'send_signature',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_send_signature")%>",
                                xtype: 'checkbox',
                                name: 'send_signature',
                                hideLabel: true
                            }, gridProtocolloSegnatura
                        ]
                    },             
                    {
                        id: 'template_nome_file_zip',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_template_nome_file_zip")%>",
                        maxLength: 500,
                        width: '90%',
                        name: 'template_nome_file_zip',
                        allowBlank: false,
                        xtype: 'textfield',
                        labelStyle: 'font-weight:bold;'
                    },
                    {
                        id: 'template_oggetto_mail_suap',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_template_oggetto_mail_suap")%>",
                        maxLength: 500,
                        width: '90%',
                        name: 'template_oggetto_mail_suap',
                        allowBlank: false,
                        xtype: 'textfield',
                        labelStyle: 'font-weight:bold;'
                    }
                ]
            },
                    {
                        xtype: 'fieldset',
                        title: "Protocollo", 
                        width: '100%',
                        collapsible: true,
                        border: '1', 
                        items: [
                            {
                                id: 'send_protocollo_param',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_protocollo")%>",
                                xtype: 'checkbox',
                                name: 'send_protocollo_param',
                                hideLabel: true
                            }, gridProtocolloWs
                        ]
                    },              
            {
                items: [
                    {
                        id: 'template_oggetto_ricevuta',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_template_oggetto_ricevuta")%>",
                        xtype:'textarea',
                        width: '90%',
                        height: 75,
                        name: 'template_oggetto_ricevuta',
                        allowBlank: false,
                        labelStyle: 'font-weight:bold;'
                    },
                    {
                        id: 'template_corpo_ricevuta',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_template_corpo_ricevuta")%>",
                        xtype:'textarea',
                        width: '90%',
                        height: 75,
                        name: 'template_corpo_ricevuta',
                        allowBlank: false,
                        labelStyle: 'font-weight:bold;'
                    },
                    {
                        items: [
                            {
                                xtype: 'checkboxgroup',
                                columns: 2,
                                vertical: false,
                                items: [
                                    {
                                        id: 'send_ricevuta_dopo_protocollazione',
                                        boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_send_ricevuta_dopo_protocollazione")%>",
                                        xtype: 'checkbox',
                                        name: 'send_ricevuta_dopo_protocollazione'
                                    },
                                    {
                                        id: 'send_ricevuta_dopo_invio_bo',
                                        boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_send_ricevuta_dopo_invio_bo")%>",
                                        xtype: 'checkbox',
                                        name: 'send_ricevuta_dopo_invio_bo'
                                    }
                                ]
                            }
                        ]
                    }
                ],
                xtype: 'fieldset',
                title: "<%=testiPortale.get(nomePagina + "_field_form_ricevuta_utente_title")%>",
                width: '100%',
                border: '1' 
            },
            {
                id: 'id_protocollo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_id_protocollo")%>",
                maxLength: 250,
                name: 'id_protocollo'
            },
            {
                id: 'id_backoffice',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_id_backoffice")%>",
                maxLength: 250,
                name: 'id_backoffice'
            },
            {
                items: [
                    {
                        id: 'ae_codice_utente',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ae_codice_utente")%>",
                        xtype:'textfield',
                        width: '90%',
                        name: 'ae_codice_utente',
                        allowBlank: true,
                        maxLength: 5,
                        labelStyle: 'font-weight:bold;'
                    },
                    triggerFae,

                    /*                    
                    {
                        id: 'ae_codice_ente',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ae_codice_ente")%>",
                        xtype:'textfield',
                        width: '90%',
                        name: 'ae_codice_ente',
                        allowBlank: true,
                        maxLength: 5,
                        labelStyle: 'font-weight:bold;'
                    },
                     */
                    
                    
                    {
                        id: 'ae_tipo_ufficio',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ae_tipo_ufficio")%>",
                        xtype:'textfield',
                        width: '90%',
                        name: 'ae_tipo_ufficio',
                        allowBlank: true,
                        maxLength: 1,
                        readOnly: false,
                        labelStyle: 'font-weight:bold;'
                    }
                    ,
                    {
                        id: 'ae_codice_ufficio',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ae_codice_ufficio")%>",
                        xtype:'textfield',
                        width: '90%',
                        name: 'ae_codice_ufficio',
                        allowBlank: true,
                        maxLength: 6,
                        readOnly: false,
                        labelStyle: 'font-weight:bold;'
                    }
                    ,
                    {
                        id: 'ae_tipologia_servizio',
                        fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ae_tipologia_servizio")%>",
                        xtype:'textfield',
                        width: '90%',
                        name: 'ae_tipologia_servizio',
                        allowBlank: true,
                        maxLength: 3,
                        labelStyle: 'font-weight:bold;'
                    },
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
                title: "<%=testiPortale.get(nomePagina + "_field_form_ae_title")%>",
                width: '100%',
                border: '1' 
            },
            {
                items: [
                    {
                        xtype: 'checkboxgroup',
                        columns: 2,
                        vertical: false,
                        items: [
                            {
                                id: 'visualizza_stampa_modello_in_bianco',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_visualizza_stampa_modello_in_bianco")%>",
                                xtype: 'checkbox',
                                name: 'visualizza_stampa_modello_in_bianco'
                            },                    
                            {
                                id: 'visualizza_versione_pdf',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_visualizza_versione_pdf")%>",
                                xtype: 'checkbox',
                                name: 'visualizza_versione_pdf'
                            }                    
                        ]
                    },
                    {
                        xtype: 'checkboxgroup',
                        columns: 2,
                        vertical: false,
                        items: [
                            {
                                id: 'firma_on_line',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_firma_on_line")%>",
                                xtype: 'checkbox',
                                name: 'firma_on_line'
                            },                    
                            {
                                id: 'firma_off_line',
                                boxLabel: "<%=testiPortale.get(nomePagina + "_field_form_firma_off_line")%>",
                                xtype: 'checkbox',
                                name: 'firma_off_line'
                            }                    
                        ]
                    }
                ],
                xtype: 'fieldset',
                title: "<%=testiPortale.get(nomePagina + "_field_form_pagina_firma_title")%>",
                width: '100%',
                border: '1' 
            },
            {
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
                    dsProtocolloSegnatura.removeAll();
                    dsProtocolloSegnatura.load({params: {ref_sport:grid.getSelectionModel().getSelected().get('cod_sport')}});
		    dsProtocolloWs.removeAll();
                    dsProtocolloWs.load({params: {ref_sport:grid.getSelectionModel().getSelected().get('cod_sport')}});
                    codSportSelected = grid.getSelectionModel().getSelected().get('cod_sport');
                    Ext.getCmp("aggiungiCampo").enable();                    
		    Ext.getCmp("aggiungiCampoWs").enable();
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

    <%--
    CCD
    --%>
                                var gridData = new Array();
                                gridProtocolloSegnatura.getStore().each(function (record) {
                                    gridData.push(record.data);
                                });
    
                                Ext.Ajax.request({
                                    url: 'actions_CCD',
                                    method: 'POST',
                                    params: {'ref_sport':codSportSelected, 
                                        'store':Ext.util.JSON.encode(gridData), 
                                        'table_name':table_name_protocollo_segnatura, 'action':'update', session:'yes'}
                                });
    <%--
    CCD
    --%>
                                var gridDataProtocolloWS = new Array();
                                gridProtocolloWs.getStore().each(function (record) {
                                    gridDataProtocolloWS.push(record.data);
                                });
    
                                Ext.Ajax.request({
                                    url: 'actions_CCD',
                                    method: 'POST',
                                    params: {'ref_sport':codSportSelected, 
                                        'store':Ext.util.JSON.encode(gridDataProtocolloWS), 
                                        'table_name':table_name_protocollo_ws, 'action':'update', session:'yes'}
                                });
    
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
                                codSportSelected=Ext.getCmp(formName).getForm().findField('cod_sport').getValue();
    <%--
    CCD
    --%>
                                var gridData = new Array();
                                gridProtocolloSegnatura.getStore().each(function (record) {
                                    gridData.push(record.data);
                                });
    
                                Ext.Ajax.request({
                                    url: 'actions_CCD',
                                    method: 'POST',
                                    params: {'ref_sport':codSportSelected, 
                                        'store':Ext.util.JSON.encode(gridData), 
                                        'table_name':table_name_protocollo_segnatura, 'action':'update', session:'yes'}
                                });
    <%--
    CCD
    --%>
                                var gridDataWs = new Array();
                                gridProtocolloWs.getStore().each(function (record) {
                                    gridDataWs.push(record.data);
                                });
    
                                Ext.Ajax.request({
                                    url: 'actions_CCD',
                                    method: 'POST',
                                    params: {'ref_sport':codSportSelected, 
                                        'store':Ext.util.JSON.encode(gridDataWs), 
                                        'table_name':table_name_protocollo_ws, 'action':'update', session:'yes'}
                                });
                                
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
                },
    <% }%>
                    {
                        id: 'reset',
                        text: "<%=testiPortale.get("bottone_reset")%>",
                        handler: function(){
                            form.getForm().reset();
                            codSportSelected=null;
                            Ext.getCmp("aggiungiCampo").disable();                    
		            Ext.getCmp("aggiungiCampoWs").disable();                            
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
                        for (ele in Ext.util.JSON.decode(response.responseText).success) {
                            conta++;
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
            cod_sport=Ext.getUrlParam('cod_sport');
            if (cod_sport) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {'cod_sport':cod_sport, 'table_name':table_name},
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

            Ext.getCmp("ae_codice_ente").getEl().dom.removeAttribute('readOnly');

            tree.render(menu_div);
            tree.selectPath("treepanel/source/<%=set_id%>",'id');
        });
