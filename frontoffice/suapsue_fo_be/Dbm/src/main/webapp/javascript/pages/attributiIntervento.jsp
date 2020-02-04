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
%>
<!--<script type="text/javascript">-->
    Ext.onReady(function(){
    var codiceIntervento=null;

    var leggiRecord = function(key) {
    Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {table_name:"interventi", cod_int:key},
                    success: function(response,request) {
                        try {o = Ext.decode(response.responseText);}
                        catch(e) {
                            this.showError(response.responseText);
                            return;
                        }
                        if(!o.success) {
                            txtFld.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
                        }else{
                            for (i in itemsFormIntervento) {
                                var dati=Ext.util.JSON.decode(response.responseText).success["interventi"][0];
                                if (dati[itemsFormIntervento[i].name]) {
                                    Ext.getCmp(itemsFormIntervento[i].id).setValue(dati[itemsFormIntervento[i].name])
                                }
                            }
                            Ext.getCmp("azione_form").setValue('modifica');
                            Ext.getCmp("save").enable();
                            Ext.getCmp("insert").disable();
                            Ext.getCmp("cancel").enable();

                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
                codiceIntervento=key;
                dsAllegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                dsNormeIntervento.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                dsInterventiCollegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                dsOneriIntervento.load({params: {start:0, size:grid_row , cod_int:codiceIntervento}});
                searchAllegati.enable();
                searchNormeIntervento.enable();
                searchInterventiCollegati.enable();
                searchOneriIntervento.enable();
                bottoneInterventiCollegati.enable();
                bottoneAllegati.enable();
                bottoneNormeIntervento.enable();
                bottoneOneriIntervento.enable();
    };
    var scriviSessione = function(key) {
        Ext.Ajax.request({
                    url: urlScriviSessione,
                    method: 'POST',
                    params: {table_name:"mappatura", cod_int:key},
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
        };
      var leggiSessione = function() {
        Ext.Ajax.request({
            url: 'leggiSessione',
            method: 'POST',
            params: {'table_name':'mappatura'},
            success: function(response,request) {
                try {o = Ext.decode(response.responseText);}
                catch(e) {
                    this.showError(response.responseText);
                    return;
                }
                if(!o.success) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }else {
                    var dati=Ext.util.JSON.decode(response.responseText).success.table_name;
                    if (dati) {
                       if (dati=='mappatura') {
                          var codice=Ext.util.JSON.decode(response.responseText).success.cod_int;
                          if ( codice ) {
                             codiceIntervento=codice;
                             leggiRecord(codiceIntervento);
                             dsAllegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                             dsNormeIntervento.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                             dsInterventiCollegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                             dsOneriIntervento.load({params: {start:0, size:grid_row , cod_int:codiceIntervento}});
                             searchAllegati.enable();
                             searchNormeIntervento.enable();
                             searchInterventiCollegati.enable();
                             searchOneriIntervento.enable();
                             bottoneInterventiCollegati.enable();
                             bottoneAllegati.enable();
                             bottoneNormeIntervento.enable();
                             bottoneOneriIntervento.enable();
                          }
                       }
                    }
                }
            },
            failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
            }
        });

        };
        Ext.QuickTips.init();
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        leggiSessione();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var menu_div = Ext.get('tree_container');
        // form intervento
        var dyn_fields_protection_intervento=['cod_int'];

        var comboFlgObb = new Ext.form.ComboBox({
            id: 'flg_obb',
            fieldLabel: "<%=testiPortale.get("Interventi_field_form_flg_obb")%>",
            name: 'flg_obb',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_obb_hidden',
            emptyText: "<%=testiPortale.get("Interventi_field_form_flg_obb_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_obb','displayText'],
                data: [['O', "<%=testiPortale.get("Interventi_field_form_combo_flg_obb_O")%>"],
                    ['F', "<%=testiPortale.get("Interventi_field_form_combo_flg_obb_F")%>"],
                    ['N', "<%=testiPortale.get("Interventi_field_form_combo_flg_obb_N")%>"]]
            }),
            valueField: 'flg_obb',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true

        });
        var triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_int',
            fieldLabel:"<%=testiPortale.get("Interventi_field_form_cod_int")%>",
            name: 'cod_int',
            window: winInterventi,
            hideTrigger1:false,
            hideTrigger3:true,
            clearButton: false,
            lookupFields:['cod_int','tit_int_it'],
            formName: formName,
            labelStyle: 'font-weight:bold;',
            allowBlank: true,
            enableKeyEvents: true,
            allowNegative: false,
            allowDecimals: false,
            minValue:1,
            maxValue:999999999,
            fieldsUnprotect:['cod_int','tit_int_it'],
            setValueCustom: function(r) {
                this.setValue(r);
                leggiRecord(r);
                scriviSessione(r);

            },
            customFunctionClear: function() {
                for (index=0;index < this.lookupFields.length;index++) {
                    Ext.getCmp(this.fieldsUnprotect[index]).getEl().dom.readOnly = false;
                };
                codiceIntervento=null;
                dsAllegati.removeAll();
                dsNormeIntervento.removeAll();
                dsInterventiCollegati.removeAll();
                dsOneriIntervento.removeAll();
                searchAllegati.disable();
                searchNormeIntervento.disable();
                searchInterventiCollegati.disable();
                searchOneriIntervento.disable();
                formIntervento.getForm().reset();
                Ext.getCmp("save").disable();
                Ext.getCmp("insert").enable();
                Ext.getCmp("cancel").disable();
                bottoneInterventiCollegati.disable();
                bottoneAllegati.disable();
                bottoneNormeIntervento.disable();
                bottoneOneriIntervento.disable();
            }
        });

        var triggerProcedimenti = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_proc',
            fieldLabel:"<%=testiPortale.get("Interventi_field_form_cod_proc")%>",
            name: 'cod_proc',
            window: winProcedimenti,
            hideTrigger1:false,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            clearButton: true,
            lookupFields:['cod_proc','tit_proc'],
            windowLink:"<%=basePath%>protected/Procedimenti.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_proc'],
            formName: formName,
            url: urlScriviSessione
        });

        triggerInterventi.on('blur', function(a) {
            if (this.getValue() != "") {
                Ext.Ajax.request({
                    url: 'leggiEsistenza',
                    method: 'POST',
                    params: {table_name:"interventi", tab_key:this.getValue()},
                    success: function(response,request) {
                        try {o = Ext.decode(response.responseText);}
                        catch(e) {
                            this.showError(response.responseText);
                            return;
                        }
                        if(!o.success) {
                            triggerInterventi.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            }
        });
        var ordinamentoNumberField = new Ext.form.NumberField({
            id:'ordinamento',
            fieldLabel: "<%=testiPortale.get("Interventi_field_form_ordinamento")%>",
            name: 'ordinamento',
            labelStyle: 'font-weight:bold;',
            allowNegative: false,
            allowDecimals: false
        });
        var	itemsFormIntervento = [triggerInterventi,{
                id: 'tit_int_it',
                fieldLabel: "<%=testiPortale.get("Interventi_field_form_tit_int")%>",
                maxLength: 64000,
                name: 'tit_int_it',
                labelStyle: 'font-weight:bold;'
            },
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
            {
                id: 'tit_int_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get("Interventi_field_form_tit_int")%>(<%=lingueAggregazione.get(i)%>)",
                maxLength: 64000,
                allowBlank: false,
                name: 'tit_int_<%=lingueAggregazione.get(i)%>',
                labelStyle: 'font-weight:bold;'
            },
            <%}%>            
            triggerProcedimenti,{
                id: 'tit_proc',
                fieldLabel: "<%=testiPortale.get("Interventi_field_form_tit_proc")%>",
                name: 'tit_proc',
                readOnly: true
            },comboFlgObb,ordinamentoNumberField,{
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
                value: "interventi"
            }];

        var formIntervento = new Ext.FormPanel({
            id: formName,
            title: "Intervento",
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
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
            items: itemsFormIntervento,
            buttons: [{
                    id: 'save',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    disabled:true,
                    handler: function(){
                        if (trim(Ext.getCmp("ordinamento").getValue()) == ""){
                            Ext.getCmp("ordinamento").setValue(Ext.getCmp("cod_int").getValue());
                        }
                        formIntervento.getForm().submit({url:'aggiorna',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
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
                    handler:  function() {
                        Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                        function(e) {
                            if (e=='yes'){
                                formIntervento.getForm().submit({url:'cancella',
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                        Ext.getCmp("save").disable();
                                        Ext.getCmp("insert").enable();
                                        Ext.getCmp("cancel").disable();
                                        Ext.getCmp("azione_form").setValue('inserimento');
                                        for (var ele=0;ele < dyn_fields_protection_intervento.length;ele++) {
                                            formIntervento.getForm().findField(dyn_fields_protection_intervento[ele]).getEl().dom.readOnly = false;
                                        }
                                        codiceIntervento=null;
                                    },
                                    failure:function(result,request) {
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
                        if (trim(Ext.getCmp("ordinamento").getValue()) == ""){
                            Ext.getCmp("ordinamento").setValue(Ext.getCmp("cod_int").getValue());
                        }
                        formIntervento.getForm().submit({url:'inserisci',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                Ext.getCmp('save').enable();
                                Ext.getCmp('insert').disable();
                                Ext.getCmp('cancel').enable();
                                for (var ele=0;ele < dyn_fields_protection_intervento.length;ele++) {
                                    formIntervento.getForm().findField(dyn_fields_protection_intervento[ele]).getEl().dom.readOnly = true;
                                }
                                Ext.getCmp("azione_form").setValue('modifica');
                                codiceIntervento=formIntervento.getForm().findField("cod_int").getValue();
                                dsAllegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                                dsNormeIntervento.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                                dsInterventiCollegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                                dsOneriIntervento.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
                                searchAllegati.enable();
                                searchNormeIntervento.enable();
                                searchInterventiCollegati.enable();
                                searchOneriIntervento.enable();
                                bottoneInterventiCollegati.enable();
                                bottoneAllegati.enable();
                                bottoneNormeIntervento.enable();
                                bottoneOneriIntervento.enable();
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
                        formIntervento.getForm().reset();
                        Ext.getCmp('insert').enable();
                        Ext.getCmp('save').disable();
                        Ext.getCmp('cancel').disable();
                        Ext.getCmp("azione_form").setValue('inserimento');
                        for (var ele=0;ele < dyn_fields_protection_intervento.length;ele++) {
                            formIntervento.getForm().findField(dyn_fields_protection_intervento[ele]).getEl().dom.readOnly = false;
                        }
                        codiceIntervento=null;
                        dsAllegati.removeAll();
                        dsNormeIntervento.removeAll();
                        dsInterventiCollegati.removeAll();
                        searchAllegati.disable();
                        searchNormeIntervento.disable();
                        searchInterventiCollegati.disable();
                        bottoneInterventiCollegati.disable();
                        bottoneAllegati.disable();
                        bottoneNormeIntervento.disable();
                    }
                },{
                    id: 'avanti',
                    text: "<%=testiPortale.get("bottone_avanti")%>",
                    handler: function(){
                      window.location="InterventiComuni.jsp?set_id=<%=set_id%>";
                    }
                }],
            buttonAlign: 'center'
        });
        // allegati
        var sort_field_allegati="cod_doc";
        function changeAllegati(val){
            if(val == 0){
                return "<%=testiPortale.get("Allegati_field_form_combo_flg_autocert_0")%>";
            }else if(val == 1){
                return "<%=testiPortale.get("Allegati_field_form_combo_flg_autocert_1")%>";                    }
            return val;
        };
        function changeObbAllegati(val){
            if(val == 0){
                return "<%=testiPortale.get("Allegati_field_form_combo_flg_obb_N")%>";
            }else if(val == 1){
                return "<%=testiPortale.get("Allegati_field_form_combo_flg_obb_S")%>";                    }
            return val;
        };
        var searchComboDSAllegati = new Ext.data.ArrayStore({
            fields: ['search_cod', 'search_des'],
            data : [
                ['default', 'Selezionare'],
                ['href', 'Dichirazioni'],
                ['condizione', 'Condizione'],
                ['documento', 'Documento']
            ]
        });
        var searchComboAllegati = new Ext.form.ComboBox({
            store: searchComboDSAllegati,
            width:100,
            mode: 'local',
            forceSelection: true,
            triggerAction: 'all',
            valueField: 'search_cod',
            displayField:'search_des',
            selectOnFocus:true,
            allowBlank: false,
            editable: false,
            value:'default'
        });
        var dyn_fields_allegati = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_doc'},
            {name: 'tit_doc'},
            {name: 'cod_cond'},
            {name: 'testo_cond'},
            {name: 'flg_autocert'},
            {name: 'copie'},
            {name: 'flg_obb'},
            {name: 'tipologie'},
            {name: 'num_max_pag'},
            {name: 'dimensione_max'},
            {name: 'href'},
            {name: 'tit_href'},
            {name: 'ordinamento'}
        ];
        var readerAllegati = new Ext.data.JsonReader({
            root:"allegati",
            fields: dyn_fields_allegati,
            totalProperty: 'totalCount',
            idProperty: ""
        });
        var dsAllegati = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name=allegati'}),
            sortInfo: {field:sort_field_allegati,direction:'ASC'},
            baseParams: {search_cod:'default'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerAllegati,
            groupField: ""
        });

        dsAllegati.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.failure);
        });
        dsAllegati.on('beforeload', function(store){
            store.baseParams.cod_int= codiceIntervento;
        });
        var searchAllegati = new Ext.ux.form.SearchField({
            store: dsAllegati,
            searchCombo:searchComboAllegati,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var bottoneAllegati=new Ext.Button({
            id: 'inserisciAllegato',
            iconCls: "insertButton",
            handler: function(){
                window.location="Allegati.jsp?set_id=<%=set_id%>&cod_int="+codiceIntervento;
            }
        });
        var gridAllegati = new Ext.grid.GridPanel({
            id:'gridStandardAllegati',
            title:"Allegati",
            frame:true,
            ds: dsAllegati,
            columns: [
                {
                    width: 2,
                    sortable: false,
                    dataIndex: 'cod_int',
                    hidden: true,
                    hideable: false
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_cod_doc")%>",
                    width: 60,
                    sortable: true,
                    dataIndex: 'cod_doc'
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_tit_doc")%>",
                    width: 250,
                    sortable: true,
                    renderer : renderTpl,
                    dataIndex: 'tit_doc'
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_cod_cond")%>",
                    width: 60,
                    sortable: true,
                    dataIndex: 'cod_cond'
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_testo_cond")%>",
                    width: 150,
                    sortable: true,
                    renderer : renderTpl,
                    dataIndex: 'testo_cond'
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_flg_autocert")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'flg_autocert',
                    renderer: changeAllegati,
                    hidden: true
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_copie")%>",
                    width: 20,
                    sortable: true,
                    dataIndex: 'copie',
                    hidden: true
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_flg_obb")%>",
                    width: 20,
                    sortable: true,
                    dataIndex: 'flg_obb',
                    renderer: changeObbAllegati,
                    hidden: true
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_href")%>",
                    width: 60,
                    sortable: true,
                    dataIndex: 'href'
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_tit_href")%>",
                    width: 150,
                    renderer : renderTpl,
                    sortable: true,
                    dataIndex: 'tit_href'
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_tipologie")%>",
                    width: 80,
                    sortable: true,
                    dataIndex: 'tipologie',
                    hidden: true
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_num_max_pag")%>",
                    width: 30,
                    sortable: true,
                    dataIndex: 'num_max_pag',
                    hidden: true
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_dimensione_max")%>",
                    width: 30,
                    sortable: true,
                    dataIndex: 'dimensione_max',
                    hidden: true
                },
                {
                    header: "<%=testiPortale.get("Allegati_header_grid_ordinamento")%>",
                    width: 30,
                    sortable: true,
                    dataIndex: 'ordinamento',
                    hidden: true
                },
                {
                    xtype: 'actioncolumn',
                    header: "Azione",
                    width: 30,
                    items: [{
                            tooltip: 'Modifica',
                            icon   : '../images/default/shared/edit.png',
                            handler: function(grid, rowIndex, colIndex) {
                                var rec = dsAllegati.getAt(rowIndex);
                                window.location="Allegati.jsp?set_id=<%=set_id%>&cod_doc="+rec.get('cod_doc')+"&cod_int="+rec.get('cod_int');
                            }
                        }]
                }
            ],
            buttonAlign: 'center',
            autoScroll:true,
            oadMask: true,
            autoExpandColumn: "cod_doc",
            height:300,
            view: new Ext.grid.GroupingView({
                forceFit:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
            }),
            bbar: new Ext.PagingToolbar({
                store: dsAllegati,
                pageSize:grid_row,
                displayInfo:true,
                items:["",searchComboAllegati,"",searchAllegati,"",bottoneAllegati]
            }),
            border: true,
            stripeRows: true,
            stateful: true,
            stateId: 'grid'
        });
        // normative
        var dyn_fields_norme_interventi = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_rif'},
            {name: 'tit_rif'},
            {name: 'art_rif'},
            {name: 'nome_rif'},
            {name: 'cod_tipo_rif'},
            {name: 'tipo_rif'},
            {name: 'nome_file'}
        ];
        var readerNormeIntervento = new Ext.data.JsonReader({
            root:"norme_interventi",
            fields: dyn_fields_norme_interventi,
            totalProperty: 'totalCount',
            idProperty: ""
        });
        var dsNormeIntervento = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name=norme_interventi'}),
            sortInfo: {field:"cod_rif",direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerNormeIntervento,
            groupField: ""
        });

        dsNormeIntervento.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })
        dsNormeIntervento.on('beforeload', function(store){
            store.baseParams.cod_int= codiceIntervento;
        });
        var bottoneNormeIntervento=new Ext.Button({
            id: 'inserisciNormeIntervento',
            iconCls: "insertButton",
            handler: function(){
                window.location="NormeInterventi.jsp?set_id=<%=set_id%>&cod_int="+codiceIntervento;
            }
        });
        var colModelNormeIntervento = new Ext.grid.ColumnModel([
            {
                id:'gridNormeIntervento_cod_int',
                width: 78,
                sortable: false,
                dataIndex: 'cod_int',
                hidden: true,
                hideable: false
            },
            {
                id:'grid_cod_rif',
                header: "<%=testiPortale.get("NormeInterventi_header_grid_cod_rif")%>",
                width: 78,
                sortable: true,
                dataIndex: 'cod_rif'
            },
            {
                id:'grid_tit_rif',
                header: "<%=testiPortale.get("NormeInterventi_header_grid_tit_rif")%>",
                renderer: renderTpl,
                width: 150,
                sortable: true,
                dataIndex: 'tit_rif'
            },
            {
                id:'grid_nome_rif',
                header: "<%=testiPortale.get("Normative_header_grid_nome_rif")%>",
                renderer: renderTpl,
                width: 150,
                sortable: true,
                dataIndex: 'nome_rif'
            },{
                id:'grid_cod_tipo_rif',
                header: "<%=testiPortale.get("Normative_header_grid_cod_tipo_rif")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_tipo_rif'
            },{
                id:'grid_tipo_rif',
                header: "<%=testiPortale.get("Normative_header_grid_tipo_rif")%>",
                renderer: renderTpl,
                width: 150,
                sortable: true,
                dataIndex: 'tipo_rif'
            },{
                id:'grid_art_rif',
                header: "<%=testiPortale.get("NormeInterventi_header_grid_art_rif")%>",
                renderer: renderTpl,
                width: 150,
                sortable: true,
                hidden: true,
                dataIndex: 'art_rif'
            },
            {
                id:'grid_nome_file',
                header: "<%=testiPortale.get("Normative_header_grid_nome_file")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'nome_file'
            },{
                xtype: 'actioncolumn',
                header: "Azione",
                width: 50,
                items: [{
                        tooltip: 'Modifica',
                        icon   : '../images/default/shared/edit.png',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = dsNormeIntervento.getAt(rowIndex);
                            window.location="NormeInterventi.jsp?set_id=<%=set_id%>&cod_rif="+rec.get('cod_rif')+"&cod_int="+rec.get('cod_int');
                        }
                    }]
            }
        ]);
        var searchNormeIntervento = new Ext.ux.form.SearchField({
            store: dsNormeIntervento,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var gridNormeIntervento = new Ext.grid.GridPanel({
            id:'gridStandardNormeIntervento',
            title: "Normative",
            frame:true,
            ds: dsNormeIntervento,
            cm: colModelNormeIntervento,
            buttonAlign: 'center',
            autoScroll:true,
            oadMask: true,
            autoExpandColumn: "cod_rif",
            height:300,
            view: new Ext.grid.GroupingView({
                forceFit:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
            }),
            bbar: new Ext.PagingToolbar({
                store: dsNormeIntervento,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchNormeIntervento,"",bottoneNormeIntervento]
            }),
            border: true,
            stripeRows: true,
            stateful: true,
            stateId: 'gridNormeIntervento'
        });
        //interventi collegati
        var dyn_fields_interventi_collegati = [
            {name: 'cod_int_padre'},
            {name: 'tit_int_padre'},
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_cond'},
            {name: 'testo_cond'}
        ];
        var readerInterventiCollegati = new Ext.data.JsonReader({
            root:"interventi_collegati",
            fields: dyn_fields_interventi_collegati,
            totalProperty: 'totalCount',
            idProperty: ""
        });
        var dsInterventiCollegati = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name=interventi_collegati'}),
            sortInfo: {field:"cod_int",direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerInterventiCollegati
        });
        dsInterventiCollegati.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })
        dsInterventiCollegati.on('beforeload', function(store){
            store.baseParams.cod_int= codiceIntervento;
        });
        var bottoneInterventiCollegati=new Ext.Button({
            id: 'inserisciInterventiCollegati',
            iconCls: "insertButton",
            handler: function(){
                window.location="InterventiCollegati.jsp?set_id=<%=set_id%>&cod_int_padre="+codiceIntervento;
            }
        });
        var colModelInterventiCollegati = new Ext.grid.ColumnModel([
            {
                width: 30,
                sortable: false,
                dataIndex: 'cod_int_padre',
                hidden: true,
                hideable: false
            },
            {
                header: "<%=testiPortale.get("InterventiCollegati_header_grid_cod_int")%>",
                width: 70,
                sortable: true,
                dataIndex: 'cod_int'
            },
            {
                header: "<%=testiPortale.get("InterventiCollegati_header_grid_tit_int")%>",
                width: 150,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'tit_int'
            },
            {
                id:'grid_cod_cond',
                header: "<%=testiPortale.get("InterventiCollegati_header_grid_cod_cond")%>",
                width: 70,
                sortable: true,
                dataIndex: 'cod_cond',
                renderer: renderTpl
            },
            {
                header: "<%=testiPortale.get("InterventiCollegati_header_grid_testo_cond")%>",
                width: 150,
                sortable: true,
                dataIndex: 'testo_cond',
                renderer: renderTpl
            },{
                xtype: 'actioncolumn',
                header: "Azione",
                width: 50,
                items: [{
                        tooltip: 'Modifica',
                        icon   : '../images/default/shared/edit.png',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = dsInterventiCollegati.getAt(rowIndex);
                            window.location="InterventiCollegati.jsp?set_id=<%=set_id%>&cod_int_padre="+rec.get('cod_int_padre')+"&cod_int="+rec.get('cod_int');
                        }
                    }]
            }
        ]);
        var searchInterventiCollegati = new Ext.ux.form.SearchField({
            store: dsInterventiCollegati,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var gridInterventiCollegati = new Ext.grid.GridPanel({
            id:'gridStandardInterventiCollegati',
            title: "Interventi Collegati",
            frame:true,
            ds: dsInterventiCollegati,
            cm: colModelInterventiCollegati,
            buttonAlign: 'center',
            autoScroll:true,
            oadMask: true,
            autoExpandColumn: "cod_int",
            height:300,
            view: new Ext.grid.GroupingView({
                forceFit:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
            }),
            bbar: new Ext.PagingToolbar({
                store: dsInterventiCollegati,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchInterventiCollegati,"",bottoneInterventiCollegati]
            }),
            border: true,
            stripeRows: true,
            stateful: true,
            stateId: 'gridInterventiCollegati'
        });
// oneri interventi
        var dyn_fields_oneri_intervento = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_oneri'},
            {name: 'des_oneri'}
        ];
        var readerOneriIntervento = new Ext.data.JsonReader({
            root:"oneri_interventi",
            fields: dyn_fields_oneri_intervento,
            alProperty: 'totalCount',
            idProperty: ""
        });
        var dsOneriIntervento = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name=oneri_interventi'}),
            sortInfo: {field:"cod_oneri",direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerOneriIntervento,
            groupField: ""
        });

        dsOneriIntervento.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        });
        dsOneriIntervento.on('beforeload', function(store){
            store.baseParams.cod_int= codiceIntervento;
        });
        var searchOneriIntervento = new Ext.ux.form.SearchField({
            store: dsOneriIntervento,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var bottoneOneriIntervento=new Ext.Button({
            id: 'inserisciOnereIntervento',
            iconCls: "insertButton",
            handler: function(){
                window.location="OneriInterventi.jsp?set_id=<%=set_id%>&cod_int="+codiceIntervento;
            }
        });
        var colModelOneriIntervento = new Ext.grid.ColumnModel([
            {
                width: 30,
                sortable: false,
                dataIndex: 'cod_int',
                hidden: true,
                hideable: false
            },
            {
                header: "<%=testiPortale.get("OneriInterventi_header_grid_cod_oneri")%>",
                width: 30,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'cod_oneri'
            },
            {
                header: "<%=testiPortale.get("OneriInterventi_header_grid_des_oneri")%>",
                width: 60,
                renderer: renderTpl,
                sortable: true,
                dataIndex: 'des_oneri'
            },{
                xtype: 'actioncolumn',
                header: "Azione",
                width: 50,
                items: [{
                        tooltip: 'Modifica',
                        icon   : '../images/default/shared/edit.png',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = dsOneriIntervento.getAt(rowIndex);
                            window.location="OneriInterventi.jsp?set_id=<%=set_id%>&cod_int="+rec.get('cod_int')+"&cod_oneri="+rec.get('cod_oneri');
                        }
                    }]
            }
        ]);
        var gridOneriIntervento = new Ext.grid.GridPanel({
            id:'gridStandardOneriIntervento',
            title: "Oneri intervento",
            frame:true,
            ds: dsOneriIntervento,
            cm: colModelOneriIntervento,
            buttonAlign: 'center',
            autoScroll:true,
            oadMask: true,
            autoExpandColumn: "cod_oneri",
            height:300,
            view: new Ext.grid.GroupingView({
                forceFit:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
            }),
            bbar: new Ext.PagingToolbar({
                store: dsOneriIntervento,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchOneriIntervento,"",bottoneOneriIntervento]
            }),
            border: true,
            stripeRows: true,
            stateful: true,
            stateId: 'gridOneriIntervento'
        });

        var panel = new Ext.Panel({ renderTo:form_div,
            items: [formIntervento,gridAllegati,gridNormeIntervento,gridInterventiCollegati,gridOneriIntervento]

        });
        for (var ele=0;ele < dyn_fields_protection_intervento.length;ele++) {
            formIntervento.getForm().findField(dyn_fields_protection_intervento[ele]).getEl().dom.readOnly = false;
        }
        searchAllegati.disable();
        searchNormeIntervento.disable();
        searchInterventiCollegati.disable();
        searchOneriIntervento.disable();
        bottoneInterventiCollegati.disable();
        bottoneAllegati.disable();
        bottoneNormeIntervento.disable();
        bottoneOneriIntervento.disable();
        if (codiceIntervento) {
            leggiRecord(codiceIntervento);
            searchAllegati.enable();
            searchNormeIntervento.enable();
            searchInterventiCollegati.enable();
            searchOneriIntervento.enable();
            dsAllegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
            dsNormeIntervento.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
            dsInterventiCollegati.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
            dsOneriIntervento.load({params: {start:0, size:grid_row, cod_int:codiceIntervento}});
        }
        cod_int=Ext.getUrlParam('cod_int');
        if (cod_int) {
            leggiRecord(cod_int);
        }
        tree.render(menu_div);

        tree.selectPath("treepanel/source/<%=set_id%>",'id');

    });
<!--</script>-->
