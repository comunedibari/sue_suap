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
<!--<script type="text/javascript">-->
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';
        var formName = 'edit-form';
        var tree_div = Ext.get('grid');
        var form_div = Ext.get('form');
        var menu_div = Ext.get('tree_container');
        var table_name = 'gerarchia_operazioni';
        var id_field = '';
        var group_field = '';
        var expand_field = '';
        var tipoAggregazione = null;
        <% if (utente.getTipAggregazione() != null){%>
            tipoAggregazione = '<%=utente.getTipAggregazione()%>';
        <%}%>
      
        Ext.apply(Ext.form.VTypes, {
            controllo: function(v,e,f){
                if (Ext.getCmp('raggruppamento_check').getValue() == '') {
                    Ext.getCmp('flg_sino').clearInvalid();
                    Ext.getCmp('raggruppamento_check').clearInvalid();
                    return true;
                } else {
                    if (Ext.getCmp('flg_sino').hiddenField.value == 'N') {
                        Ext.getCmp('flg_sino').clearInvalid();
                        Ext.getCmp('raggruppamento_check').clearInvalid();
                        return true;
                    } else {
                        //                 Ext.getCmp('flg_sino').markInvalid();
                        //                 Ext.getCmp('raggruppamento_check').markInvalid();
                        return false;
                    }
                }
            },
            controlloText: 'Il campo raggruppamento check non può essere attivo se è attivo il campo di selezione check'
        });

        function change(val){
            if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_sino_S")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_sino_N")%>";                    }
            return val;
        };
        var comboFlgSiNo = new Ext.form.ComboBox({
            id: 'flg_sino',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_sino")%>",
            name: 'flg_sino',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            vtype: 'controllo',
            hiddenName: 'flg_sino_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_sino_select")%>",
            store: new Ext.data.SimpleStore({
                fields: ['flg_sino','displayText'],
                data: [['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_sino_N")%>"],
                    ['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_sino_S")%>"]]
            }),
            valueField: 'flg_sino',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            editable: false,
            resizable:true
        });

        var triggerOperazioni = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_ope',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_ope")%>",
            name: 'cod_ope',
            window: winOperazioni,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_ope','des_ope'],
            windowLink:"<%=basePath%>protected/Operazioni.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_ope'],
            formName: formName,
            url: urlScriviSessione
        });

        //Normative
        var triggerNormative = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_rif',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_rif")%>",
            name: 'cod_rif',
            window: winNormative,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_rif','tit_rif'],
            windowLink:"<%=basePath%>protected/Normative.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_rif'],
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
                    Ext.getCmp(winAggregazioni.lookupFields[0]).setValue(rec.data.tip_aggregazione);
                    Ext.getCmp(winAggregazioni.lookupFields[1]).setValue(rec.data.des_aggregazione);
                    winAggregazioni.hide();
                    pulisci();
                    Ext.getCmp('save').disable();
                    Ext.getCmp('cancel').disable();
                    Ext.getCmp('aggiungiRamo').disable();
                    gerarchia.enable();
                    treeLoader.baseParams.tip_aggregazione=rec.data.tip_aggregazione;
                    gerarchia.getNodeById('root').setText(rec.data.des_aggregazione);
                    gerarchia.getRootNode().reload();
                    gerarchia.getRootNode().expand(false);
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
                    handler: function(){winAggregazioni.hide();}
                }],
            display: function(){
                dsAggregazioni.reload({params:{start: 0, size:grid_row}});
                this.show();
            },
            lookupFields: ['tip_aggregazione','des_aggregazione']
        });

        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:'actionsGerarchia?table_name='+table_name,
            baseParams: {'tip_aggregazione': ''},
            uiProviders:{
                'col': Ext.ux.tree.ColumnNodeUI
            }
        });
        function pulisci() {
            Ext.getDom('cod_ramo').value = '';
            Ext.getDom('cod_ramo_prec').value = '';
            Ext.getDom('it_des_ramo').value = '';
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             Ext.getDom('<%=lingueAggregazione.get(i)%>_des_ramo').value = '';
             <%}%>             
            Ext.getDom('des_ramo_prec').value = '';
            Ext.getDom('cod_ope').value = '';
            Ext.getDom('des_ope').value = '';
            Ext.getDom('cod_rif').value = '';
            Ext.getDom('tit_rif').value = '';
            Ext.getDom('raggruppamento_check').value = '';
            Ext.getCmp('flg_sino').setValue('N');
        };

        function carica(node) {
            if (node.attributes.id == 'root') {
                pulisci();
            } else {
                //          Ext.getDom('cod_ramo').value = node.attributes.cod_ramo;
                Ext.getDom('cod_ramo').value = node.attributes.text;
                Ext.getDom('cod_ramo_prec').value = node.attributes.cod_ramo_prec;
                Ext.getDom('it_des_ramo').value = node.attributes.it_des_ramo;
                <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                  if (node.attributes.<%=lingueAggregazione.get(i)%>_des_ramo){
                    Ext.getDom('<%=lingueAggregazione.get(i)%>_des_ramo').value = node.attributes.<%=lingueAggregazione.get(i)%>_des_ramo;
                  }
                <%}%>                 
                Ext.getDom('des_ramo_prec').value = node.attributes.des_ramo_prec;
                Ext.getDom('cod_ope').value = node.attributes.cod_ope;
                Ext.getDom('des_ope').value = node.attributes.des_ope;
                Ext.getDom('cod_rif').value = node.attributes.cod_rif;
                Ext.getDom('tit_rif').value = node.attributes.tit_rif;
                Ext.getDom('raggruppamento_check').value = node.attributes.raggruppamento_check;
                Ext.getCmp('flg_sino').setValue(node.attributes.flg_sino);
            }
        };

        var gerarchia = new Ext.tree.ColumnTree({
            renderTo:tree_div,
            rootVisible:true,
            autoScroll:true,
            forceLayout: true,
            title: ' ',
            root: new Ext.tree.AsyncTreeNode({
                text:"<%=testiPortale.get(nomePagina + "_radice_gerarchia")%>",
                id:'root'
            }),
<% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) { %>
            tbar : [{
                    text: "<%=testiPortale.get("bottone_modifica")%>",
                    id:'modificaTree',
                    listeners:
                        { 'click': function() {
                            Ext.getDom('azione_form').value = 'modifica';

                            var node = gerarchia.getSelectionModel().getSelectedNode();
                            carica(node);
                            Ext.getCmp('cancel').disable();
                            Ext.getCmp('aggiungiRamo').disable();
                            Ext.get('it_des_ramo').dom.readOnly = false;
                            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                              Ext.get('<%=lingueAggregazione.get(i)%>_des_ramo').dom.readOnly = false;
                            <%}%>  
                            triggerNormative.setEnabled();
                            if (treeLoader.baseParams.tip_aggregazione != tipoAggregazione && tipoAggregazione != null) { 
                                Ext.getCmp('save').disable();
                            } else {
                                Ext.getCmp('save').enable();
                            }
                            Ext.Ajax.request({
                                url: 'leggiRamo',
                                method: 'POST',
                                params: {'table_name':table_name,'cod_ramo':node.attributes.text, 'tip_aggregazione':Ext.getDom('tip_aggregazione').value},
                                success: function(response,request) {
                                    try {o = Ext.decode(response.responseText);}
                                    catch(e) {
                                        this.showError(response.responseText);
                                        return;
                                    }
                                    if(!o.success) {
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                    }else {
                                        if (Ext.util.JSON.decode(response.responseText).success['figli'] > 0) {
                                            triggerOperazioni.setDisabled();
                                        } else {
                                            triggerOperazioni.setEnabled();
                                        }
                                    }
                                },
                                failure: function(response,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                }
                            });
                        }
                    }
                },{
                    xtype: 'tbseparator'
                },{
                    text: "<%=testiPortale.get("bottone_aggiungi_ramo")%>",
                    id: 'aggiungiTree',
                    listeners:
                        { 'click': function() {
                            Ext.getDom('azione_form').value = 'aggiungiRamo';
                            var node = gerarchia.getSelectionModel().getSelectedNode();
                            pulisci();
                            if (node.attributes.id == 'root') {
                                Ext.getDom('cod_ramo_prec').value = '';
                                Ext.getDom('des_ramo_prec').value = node.attributes.text;
                            } else {
                                Ext.getDom('cod_ramo_prec').value = node.attributes.text;
                                Ext.getDom('des_ramo_prec').value = node.attributes.it_des_ramo;
                            }
                            Ext.get('cod_ramo').dom.readOnly = false;
                            Ext.get('it_des_ramo').dom.readOnly = false;
                            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                              Ext.get('<%=lingueAggregazione.get(i)%>_des_ramo').dom.readOnly = false;
                            <%}%>                               
                            triggerNormative.setEnabled();
                            triggerOperazioni.setEnabled();

                            Ext.getCmp('save').disable();
                            Ext.getCmp('cancel').disable();
                            Ext.getCmp('aggiungiRamo').enable();
                        }
                    }
                },{
                    xtype: 'tbseparator'
                },{
                    text: "<%=testiPortale.get("bottone_delete")%>",
                    id: 'cancellaTree',
                    listeners:
                        { 'click': function() {
                            Ext.getDom('azione_form').value = 'cancella';

                            var node = gerarchia.getSelectionModel().getSelectedNode();
                            carica(node);
                            Ext.getCmp('save').disable();
                            Ext.getCmp('cancel').enable();
                            Ext.getCmp('aggiungiRamo').disable();
                        }
                    }
                },{
                    xtype: 'tbseparator'
                },{
                    text: "<%=testiPortale.get("bottone_cambia_aggregazione")%>",
                    id: 'cambiaAggregazioneTree',
                    listeners:
                        { 'click': function() {
                            winAggregazioni.display();
                        }
                    }
                },{
                    xtype: 'tbseparator'
                },{
                    text: "<%=testiPortale.get("bottone_espandi")%>",
                    id: 'espandiTree',
                    listeners:
                        { 'click': function() {
                            gerarchia.expandAll();
                        }
                    }
                },{
                    xtype: 'tbseparator'
                },{
                    text: "<%=testiPortale.get("bottone_collassa")%>",
                    id: 'collassaTree',
                    listeners:
                        { 'click': function() {
                            gerarchia.collapseAll();
                        }
                    }
                },{
                    xtype: 'tbseparator'
                },{
                    text: "<%=testiPortale.get("bottone_stampa")%>",
                    id: 'stampaTree',
                    listeners:
                        { 'click': function() {
                            Ext.ux.Printer.print(gerarchia);
                        }
                    }
                }],
<% } %>
            columns:[
                {
                    header: "<%=testiPortale.get(nomePagina + "_header_gerarchia_cod_ramo")%>",
                    width:190,
                    dataIndex:'text',
                    id:'text'
                },{
                    header:"<%=testiPortale.get(nomePagina + "_header_gerarchia_des_ramo")%>",
                    width:700,
                    dataIndex:'it_des_ramo',
                    id:'tree_des_ramo'
                },{
                    header:"<%=testiPortale.get(nomePagina + "_header_gerarchia_raggruppamento_check")%>",
                    width:70,
                    dataIndex:'raggruppamento_check',
                    id:'tree_raggruppamnto_check'
                },{
                    header:"<%=testiPortale.get(nomePagina + "_header_gerarchia_flg_sino")%>",
                    width:60,
                    dataIndex:'flg_sino',
                    id:'tree_flg_sino',
                    renderer: change
                }],

            loader: treeLoader,

            listeners: {
                'click': function(node) {
                    pulisci();
                    if (node.attributes.id == 'root') {
                        Ext.getCmp('modificaTree').disable();
                        Ext.getCmp('cancellaTree').disable();
                        Ext.getCmp('aggiungiTree').enable();
                    } else {
                        Ext.getCmp('modificaTree').enable();
                        Ext.getCmp('cancellaTree').enable();
                        if (node.attributes.cod_ope != '') {
                            Ext.getCmp('aggiungiTree').disable();
                        } else {
                            Ext.getCmp('aggiungiTree').enable();
                        }
                    }
                    Ext.getCmp('save').disable();
                    Ext.getCmp('cancel').disable();
                    Ext.getCmp('aggiungiRamo').disable();
                    Ext.getCmp('cod_ramo').getEl().dom.setAttribute('readOnly', true);
                    Ext.getCmp('it_des_ramo').getEl().dom.setAttribute('readOnly', true);
                    if (treeLoader.baseParams.tip_aggregazione != tipoAggregazione && tipoAggregazione != null) {
                        Ext.getCmp('modificaTree').enable();
                        Ext.getCmp('cancellaTree').disable();
                        Ext.getCmp('aggiungiTree').disable();  
                        Ext.getCmp('save').disable();
                        Ext.getCmp('cancel').disable();
                    }                    
                    <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                      Ext.getCmp('<%=lingueAggregazione.get(i)%>_des_ramo').getEl().dom.setAttribute('readOnly', true);
                    <%}%>                     
                }
            }
        });
        var txtFld = new Ext.form.TextField({
            id: 'cod_ramo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_ramo")%>",
            name: 'cod_ramo',
            readOnly: true,
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
                        params: {table_name:table_name, tab_key:this.getValue(), tip_aggregazione:Ext.getCmp('tip_aggregazione').value},
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
        var txtFldRaggruppamentoCheck = new Ext.form.TextField({
            id: 'raggruppamento_check',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_raggruppamento_check")%>",
            name: 'raggruppamento_check',
            vtype: 'controllo',
            enableKeyEvents: true
        });
        txtFldRaggruppamentoCheck.on('blur', function(a) {
            if (Ext.getCmp('raggruppamento_check').getValue() == '') {
                Ext.getCmp('flg_sino').clearInvalid();
                Ext.getCmp('raggruppamento_check').clearInvalid();
            }
        });

        var itemsForm = [{
                id: 'tip_aggregazione',
                fieldLabel: 'tip_aggregazione',
                name: 'tip_aggregazione',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'des_aggregazione',
                fieldLabel: 'des_aggregazione',
                name: 'des_aggregazione',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'cod_ramo_prec',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_ramo_prec")%>",
                name: 'cod_ramo_prec',
                readOnly: true
            },
            {
                id: 'des_ramo_prec',
                xtype:'textarea',
                height: 37,
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ramo_prec")%>",
                name: 'des_ramo_prec',
                readOnly: true
            },txtFld,{
                id: 'it_des_ramo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ramo")%>",
                name: 'it_des_ramo',
                readOnly: true,
                xtype:'textarea',
                height: 37,
                maxLength: 64000,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
            {
                id: '<%=lingueAggregazione.get(i)%>_des_ramo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ramo")%>(<%=lingueAggregazione.get(i)%>)",
                name: '<%=lingueAggregazione.get(i)%>_des_ramo',
                readOnly: true,
                xtype:'textarea',
                height: 37,
                maxLength: 64000,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
                    <%}%>               
            triggerOperazioni,
            {
                id: 'des_ope',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ope")%>",
                name: 'des_ope',
                readOnly: true,
                xtype:'textarea',
                height: 37
            },
            triggerNormative
            ,
            {
                id: 'tit_rif',
                xtype:'textarea',
                height: 37,
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_rif")%>",
                name: 'tit_rif',
                readOnly: true
            },txtFldRaggruppamentoCheck,comboFlgSiNo,{
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
            }
        ];


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
<% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) { %>
                {
                    id: 'save',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    disabled:true,
                    handler: function(){
                        form.getForm().submit({
                            url:'aggiorna',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request){
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                gerarchia.getRootNode().reload();
                                gerarchia.getRootNode().expand(false);

                                Ext.getCmp("save").disable();
                                Ext.getCmp("aggiungiRamo").disable();
                                Ext.getCmp("cancel").disable();
                                triggerOperazioni.setDisabled();
                                triggerNormative.setDisabled();
                            },
                            failure: function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                },
                {
                    id: 'cancel',
                    text:"<%=testiPortale.get("bottone_delete")%>",
                    disabled:true,
                    handler: function(){
                        Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione_ramo")%>",
                        function(e) {
                            if (e=='yes'){
                                form.getForm().submit({
                                    url:'cancella',
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                        gerarchia.getRootNode().reload();
                                        gerarchia.getRootNode().expand(false);

                                        Ext.getCmp("save").disable();
                                        Ext.getCmp("aggiungiRamo").disable();
                                        Ext.getCmp("cancel").disable();
                                        triggerOperazioni.setDisabled();
                                        triggerNormative.setDisabled();
                                    },
                                    failure: function(result,request) {
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                    }
                                });
                            }
                        }
                    );
                    }
                },
                {
                    id: 'aggiungiRamo',
                    text: "<%=testiPortale.get("bottone_aggiungi_ramo")%>",
                    handler: function(){
                        form.getForm().submit({
                            url:'inserisci',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                gerarchia.getRootNode().reload();
                                gerarchia.getRootNode().expand(false);

                                Ext.getCmp('save').disable();
                                Ext.getCmp('aggiungiRamo').disable();
                                Ext.getCmp('cancel').disable();
                                triggerOperazioni.setDisabled();
                                triggerNormative.setDisabled();
                            },
                            failure: function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                }
<% } %>
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
                        winAggregazioni.display();
                    } else {
                        treeLoader.baseParams.tip_aggregazione=Ext.getCmp('tip_aggregazione').value;
                        gerarchia.getNodeById('root').setText(Ext.getCmp('des_aggregazione').value);
                        gerarchia.getRootNode().reload();
                        gerarchia.getRootNode().expand(false);
                        if (Ext.getCmp('azione_form').value == 'aggiungiRamo') {
                            Ext.getCmp('aggiungiRamo').enable();
                        }
                        if (Ext.getCmp('azione_form').value == 'modifica') {
                            Ext.getCmp('save').enable();
                        }
                        if (Ext.getCmp('azione_form').value == 'cancella') {
                            Ext.getCmp('cancel').enable();
                        }
                    }
                }
            },
            failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
            }
        });

        Ext.getCmp('save').disable();
        Ext.getCmp('cancel').disable();
        Ext.getCmp('aggiungiRamo').disable();
        Ext.getCmp('modificaTree').disable();
        Ext.getCmp('cancellaTree').disable();
        Ext.getCmp('aggiungiTree').disable();
        gerarchia.disable();
        tree.render(menu_div);

        tree.selectPath("treepanel/source/<%=set_id%>",'id');
    });
<!--</script>-->
