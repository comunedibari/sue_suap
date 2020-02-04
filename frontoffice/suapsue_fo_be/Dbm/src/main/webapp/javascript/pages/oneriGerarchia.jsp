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
        var formName = 'edit-form';
        var tree_div = Ext.get('grid');
        var form_div = Ext.get('form');
        var menu_div = Ext.get('tree_container');
        var table_name = 'oneri_gerarchia';
        var id_field = '';
        var group_field = '';
        var expand_field = '';
        var tipoAggregazione = null;
        <% if (utente.getTipAggregazione() != null){%>
            tipoAggregazione = '<%=utente.getTipAggregazione()%>';
        <%}%>

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
        //SettoriAttivita
        var triggerOneriFormula = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_onere_formula',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_onere_formula")%>",
            name: 'cod_onere_formula',
            window: winOneriFormula,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_onere_formula','des_formula'],
            windowLink:"<%=basePath%>protected/OneriFormula.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_onere_formula'],
            formName: formName,
            url: urlScriviSessione
        });

        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:'actionsGerarchia?table_name='+table_name,
            baseParams: {'tip_aggregazione': ''},
            uiProviders:{
                'col': Ext.ux.tree.ColumnNodeUI
            }
        });

        function pulisci() {
            Ext.getDom('cod_figlio').value = '';
            Ext.getDom('cod_padre').value = '';
            Ext.getDom('des_gerarchia_it').value = '';
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                Ext.getDom('des_gerarchia_<%=lingueAggregazione.get(i)%>').value = '';
    <%}%>  
                Ext.getDom('des_padre').value = '';
                Ext.getDom('cod_onere_formula').value = '';
                Ext.getDom('des_formula').value = '';
                if (Ext.getDom('tip_aggregazione')) {
                    Ext.getDom('tip_aggregazione').value = '';
                }
                if (Ext.getDom('des_aggregazione')) {
                    Ext.getDom('des_aggregazione').value = '';
                }
            };

            function carica(node) {
                if (node.attributes.id == 'root') {
                    pulisci();
                } else {
                    Ext.getDom('cod_figlio').value = node.attributes.text;
                    Ext.getDom('cod_padre').value = node.attributes.cod_padre;
                    Ext.getDom('des_gerarchia_it').value = node.attributes.des_gerarchia_it;
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                    var valNodo = node.attributes.des_gerarchia_<%=lingueAggregazione.get(i)%>;
                    if (!node.attributes.des_gerarchia_<%=lingueAggregazione.get(i)%>) {
                        valNodo="";
                    }
                    Ext.getDom('des_gerarchia_<%=lingueAggregazione.get(i)%>').value = valNodo;
    <%}%>                 
                    Ext.getDom('des_padre').value = node.attributes.des_padre;
                    Ext.getDom('cod_onere_formula').value = node.attributes.cod_onere_formula;
                    Ext.getDom('des_formula').value = node.attributes.des_formula;
                    if (Ext.getDom('tip_aggregazione')) {
                        Ext.getDom('tip_aggregazione').value = node.attributes.tip_aggregazione;
                    }
                    if (Ext.getDom('des_aggregazione')) {
                        Ext.getDom('des_aggregazione').value = node.attributes.des_aggregazione;
                    }
                }
            };
            var gerarchia = new Ext.ux.tree.ColumnTree({
                renderTo:tree_div,
                rootVisible:true,
                autoScroll:true,
                forceLayout: true,
                title: ' ',
                root: new Ext.tree.AsyncTreeNode({
                    text:"<%=testiPortale.get(nomePagina + "_radice_gerarchia")%>",
                    id:'root'
                }),
                tbar : [{
                        text: "<%=testiPortale.get("bottone_modifica")%>",

                        id:'modificaTree',
                        listeners:
                            { 'click': function() {
                                Ext.getDom('azione_form').value = 'modifica';

                                var node = gerarchia.getSelectionModel().getSelectedNode();
                                carica(node);
                                Ext.getCmp('aggiungiRamo').disable();
                                Ext.get('des_gerarchia_it').dom.readOnly = false;
                                if (tipoAggregazione == node.attributes.tip_aggregazione || node.attributes.tip_aggregazione == '' || node.attributes.tip_aggregazione == undefined || tipoAggregazione == null) {
                                    Ext.getCmp("save").enable();
                                    Ext.getCmp('cancel').disable();
                                } else {
                                    Ext.getCmp("save").disable();
                                    Ext.getCmp("cancel").disable();
                                    Ext.getCmp("aggiungiTree").disable();
                                    Ext.getCmp("cancellaTree").disable();
                                }
                                if (node.attributes.tip_aggregazione == '' || node.attributes.tip_aggregazione == undefined) {
                                    triggerAggregazioni.enable();
                                } else {
                                    triggerAggregazioni.disable();
                                }                            
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                                Ext.get('des_gerarchia_<%=lingueAggregazione.get(i)%>').dom.readOnly = false;
    <%}%> 
                                Ext.Ajax.request({
                                    url: 'leggiRamo',
                                    method: 'POST',
                                    params: {'table_name':table_name,'cod_figlio':node.attributes.text},
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
                                                triggerOneriFormula.setDisabled();
                                            } else {
                                                triggerOneriFormula.setEnabled();
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
                                if (node.attributes.id == 'root') {
                                    Ext.getDom('cod_padre').value = '';
                                    Ext.getDom('des_padre').value = node.attributes.text;
                                } else {
                                    Ext.getDom('des_padre').value = node.attributes.des_gerarchia_it;
                                    Ext.getDom('cod_padre').value = node.attributes.text;
                                }
                                triggerOneriFormula.setEnabled();
                                Ext.get('cod_figlio').dom.readOnly = false;

                                Ext.get('des_gerarchia_it').dom.readOnly = false;
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                                Ext.get('des_gerarchia_<%=lingueAggregazione.get(i)%>').dom.readOnly = false;
    <%}%> 
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
                                var node = gerarchia.getSelectionModel() .getSelectedNode();
                                carica(node);
                                Ext.getCmp('save').disable();
                                Ext.getCmp('cancel').enable();
                                Ext.getCmp('aggiungiRamo').disable();
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
                    }
                ],

                columns:[
                    {
                        header: "<%=testiPortale.get(nomePagina + "_header_cod_figlio")%>",
                        width:200,
                        dataIndex:'text',
                        id:'tree_cod_figlio'
                    },{
                        header: "<%=testiPortale.get(nomePagina + "_header_des_gerarchia")%>",
                        width:850,
                        dataIndex:'des_gerarchia_it',
                        id:'tree_des_gerarchia_it'
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
                            if (tipoAggregazione == node.attributes.tip_aggregazione || node.attributes.tip_aggregazione == '' || node.attributes.tip_aggregazione == undefined || tipoAggregazione == null) {
                                Ext.getCmp('modificaTree').enable();
                                Ext.getCmp('cancellaTree').enable();
                                Ext.getCmp('aggiungiTree').enable();
                                if (node.attributes.cod_onere_formula != '') {
                                    Ext.getCmp('aggiungiTree').disable();
                                } else {
                                    Ext.getCmp('aggiungiTree').enable();
                                }
                            } else {
                                Ext.getCmp('modificaTree').enable();
                                Ext.getCmp('cancellaTree').disable();
                                Ext.getCmp('aggiungiTree').disable();
                            }
                        }
                        Ext.getCmp('save').disable();
                        Ext.getCmp('cancel').disable();
                        Ext.getCmp('aggiungiRamo').disable();
                        Ext.getCmp('cod_figlio').getEl().dom.setAttribute('readOnly', true);
                        Ext.getCmp('des_gerarchia_it').getEl().dom.setAttribute('readOnly', true);
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                        Ext.getCmp('des_gerarchia_<%=lingueAggregazione.get(i)%>').getEl().dom.setAttribute('readOnly', true);
    <%}%> 
                    }
                }
            });
            var txtFld = new Ext.form.TextField({
                id:'cod_figlio',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_figlio")%>",
                name: 'cod_figlio',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'	,
                allowNegative: false,
                allowDecimals: false,
                minValue:1,
                maxValue:999999999,
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

            var itemsForm = [{
                    id: 'cod_padre',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_padre")%>",
                    name: 'cod_padre',
                    readOnly: true
                },
                {
                    id: 'des_padre',
                    xtype:'textarea',
                    height: 37,
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_padre")%>",
                    name: 'des_padre',
                    readOnly: true
                },txtFld,{
                    id: 'des_gerarchia_it',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_gerarchia")%>",
                    maxLength: 64000,
                    name: 'des_gerarchia_it',
                    xtype:'textarea',
                    height: 37,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },
    <% for (int i = 0; i < lingueAggregazione.size(); i++) {%>
                {
                    id: 'des_gerarchia_<%=lingueAggregazione.get(i)%>',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_gerarchia")%>(<%=lingueAggregazione.get(i)%>)",
                    maxLength: 64000,
                    name: 'des_gerarchia_<%=lingueAggregazione.get(i)%>',
                    xtype:'textarea',
                    height: 37,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },
    <%}%>
                triggerOneriFormula,{
                    id: 'des_formula',
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_formula")%>",
                    name: 'des_formula',
                    readOnly: true,
                    xtype:'textarea',
                    height: 37
                }
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
                    {
                        id: 'save',
                        text: "<%=testiPortale.get("bottone_save")%>",
                        disabled:true,
                        handler: function(){
                            form.getForm().submit({
                                url:'aggiorna',
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",

                                success: function(result,request){
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    gerarchia.getRootNode().reload();
                                    gerarchia.getRootNode().expand(false);
                                    Ext.getCmp("save").disable();
                                    Ext.getCmp("aggiungiRamo").disable();
                                    Ext.getCmp("cancel").disable();
                                    triggerOneriFormula.setDisabled();
                                },
                                failure: function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            });
                        }
                    },
                    {
                        id: 'cancel',
                        text: "<%=testiPortale.get("bottone_delete")%>",
                        disabled:true,
                        handler: function(){
                            Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione_ramo")%>",
                            function(e) {
                                if (e=='yes'){
                                    form.getForm().submit({
                                        url:'cancella',
                                        waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",

                                        success: function(result,request) {
                                            Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                            gerarchia.getRootNode().reload();
                                            gerarchia.getRootNode().expand(false);
                                            Ext.getCmp("save").disable();
                                            Ext.getCmp("aggiungiRamo").disable();
                                            Ext.getCmp("cancel").disable();
                                            triggerOneriFormula.setDisabled();
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
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    gerarchia.getRootNode().reload();
                                    gerarchia.getRootNode().expand(false);

                                    Ext.getCmp('save').disable();
                                    Ext.getCmp('aggiungiRamo').disable();
                                    Ext.getCmp('cancel').disable();
                                    triggerOneriFormula.setDisabled();
                                },
                                failure: function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            });
                        }
                    }
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
                },
                failure: function(response,request) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
            });

            Ext.getCmp('save').disable();
            Ext.getCmp('cancel').disable();
            Ext.getCmp('aggiungiRamo').disable();
            cod_padre=Ext.getUrlParam('cod_padre');
            if (cod_padre) {
                Ext.Ajax.request({
                    url: 'leggiGerarchia',
                    method: 'POST',
                    params: {'cod_padre':cod_padre},
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
                                var dati=Ext.util.JSON.decode(response.responseText).success['ramo'][0];
                                if (dati[itemsForm[i].name] != null) {
                                    Ext.getCmp(itemsForm[i].id).setValue(dati[itemsForm[i].name])
                                    modifica++;
                                }
                            }
                            if (modifica > 0) {
                                Ext.getCmp('azione_form').setValue('modifica');
                                Ext.getCmp("save").enable();
                                Ext.getCmp("cancel").enable();
                            }
                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            }
            Ext.getCmp('save').disable();
            Ext.getCmp('cancel').disable();
            Ext.getCmp('aggiungiRamo').disable();
            Ext.getCmp('modificaTree').disable();
            Ext.getCmp('cancellaTree').disable();
            Ext.getCmp('aggiungiTree').disable();
            tree.render(menu_div);

            tree.selectPath("treepanel/source/<%=set_id%>",'id');
        });
