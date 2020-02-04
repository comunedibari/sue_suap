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
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';
        var formName = 'edit-form';
        var tree_div = Ext.get('grid');
        var form_div = Ext.get('form');
        var menu_div = Ext.get('tree_container');
        var table_name = 'gerarchia_settori';
        var id_field = '';
        var group_field = '';
        var expand_field = '';
        //SettoriAttivita
        var triggerSettoriAttivita = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_sett',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_sett")%>",
            name: 'cod_sett',
            window: winSettoriAttivita,
            hideTrigger1:false,
            clearButton: true,
            lookupFields:['cod_sett','des_sett'],
            windowLink:"<%=basePath%>protected/SettoriAttivita.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_sett'],
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
        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:'actionsGerarchia?table_name='+table_name,
            uiProviders:{
                'col': Ext.ux.tree.ColumnNodeUI
            }
        });

        function pulisci() {
            Ext.getDom('cod_ramo').value = '';
            Ext.getDom('cod_ramo_prec').value = '';
            Ext.getDom('it_des_ramo').value = '';
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
             Ext.getDom('<%=lingueTotali.get(i)%>_des_ramo').value = '';
             <%}%>            
            Ext.getDom('des_ramo_prec').value = '';
            Ext.getDom('cod_sett').value = '';
            Ext.getDom('des_sett').value = '';
            Ext.getDom('cod_rif').value = '';
            Ext.getDom('tit_rif').value = '';
        };

        function carica(node) {
            if (node.attributes.id == 'root') {
                pulisci();
            } else {
                Ext.getDom('cod_ramo').value = node.attributes.text;
                Ext.getDom('cod_ramo_prec').value = node.attributes.cod_ramo_prec;
                Ext.getDom('it_des_ramo').value = node.attributes.it_des_ramo;
                <% for (int i = 0; i < lingueTotali.size(); i++){ %>
                  if (node.attributes.<%=lingueTotali.get(i)%>_des_ramo){
                    Ext.getDom('<%=lingueTotali.get(i)%>_des_ramo').value = node.attributes.<%=lingueTotali.get(i)%>_des_ramo;
                  }
                <%}%>                 
                Ext.getDom('des_ramo_prec').value = node.attributes.des_ramo_prec;
                Ext.getDom('cod_sett').value = node.attributes.cod_sett;
                Ext.getDom('des_sett').value = node.attributes.des_sett;
                Ext.getDom('cod_rif').value = node.attributes.cod_rif;
                Ext.getDom('tit_rif').value = node.attributes.tit_rif;
            }
        };
        var gerarchia = new Ext.ux.tree.ColumnTree({
            renderTo:tree_div,
            rootVisible:true,
            autoScroll:true,
            singleClickExpand: true,
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
                            Ext.getCmp('save').enable();
                            Ext.getCmp('cancel').disable();
                            Ext.getCmp('aggiungiRamo').disable();
                            Ext.get('it_des_ramo').dom.readOnly = false;
                            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
                              Ext.get('<%=lingueTotali.get(i)%>_des_ramo').dom.readOnly = false;
                            <%}%>                              
                            triggerNormative.setEnabled();
                            Ext.Ajax.request({
                                url: 'leggiRamo',
                                method: 'POST',
                                params: {'table_name':table_name,'cod_ramo':node.attributes.text},
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
                                            triggerSettoriAttivita.setDisabled();
                                        } else {
                                            triggerSettoriAttivita.setEnabled();
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
                            var node = gerarchia.getSelectionModel() .getSelectedNode();
                            if (node.attributes.id == 'root') {
                                Ext.getDom('cod_ramo_prec').value = '';
                                Ext.getDom('des_ramo_prec').value = node.attributes.text;
                            } else {
                                Ext.getDom('cod_ramo_prec').value = node.attributes.text;
                                Ext.getDom('des_ramo_prec').value = node.attributes.it_des_ramo;
                            }
                            triggerNormative.setEnabled();
                            triggerSettoriAttivita.setEnabled();

                            Ext.get('cod_ramo').dom.readOnly = false;
                            Ext.get('it_des_ramo').dom.readOnly = false;
                            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
                              Ext.get('<%=lingueTotali.get(i)%>_des_ramo').dom.readOnly = false;
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

                }],

            columns:[
                {
                    header: "<%=testiPortale.get(nomePagina + "_header_gerarchia_cod_ramo")%>",
                    width:200,
                    dataIndex:'text',
                    id:'tree_cod_ramo'
                },{
                    header:"<%=testiPortale.get(nomePagina + "_header_gerarchia_des_ramo")%>",
                    width:850,
                    dataIndex:'it_des_ramo',
                    id:'tree_des_ramo'
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
                        if (node.attributes.cod_sett != '') {
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
                    <% for (int i = 0; i < lingueTotali.size(); i++){ %>
                      Ext.getCmp('<%=lingueTotali.get(i)%>_des_ramo').getEl().dom.setAttribute('readOnly', true);
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
            <% for (int i = 0; i < lingueTotali.size(); i++){ %>
            {
                id: '<%=lingueTotali.get(i)%>_des_ramo',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_ramo")%>(<%=lingueTotali.get(i)%>)",
                name: '<%=lingueTotali.get(i)%>_des_ramo',
                readOnly: true,
                xtype:'textarea',
                height: 37,
                maxLength: 64000,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
                    <%}%>            
            triggerSettoriAttivita
            ,
            {
                id: 'des_sett',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_sett")%>",
                name: 'des_sett',
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
                                triggerSettoriAttivita.setDisabled();
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
                                        triggerSettoriAttivita.setDisabled();
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
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                gerarchia.getRootNode().reload();
                                gerarchia.getRootNode().expand(false);

                                Ext.getCmp('save').disable();
                                Ext.getCmp('aggiungiRamo').disable();
                                Ext.getCmp('cancel').disable();
                                triggerSettoriAttivita.setDisabled();
                                triggerNormative.setDisabled();


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
        Ext.getCmp('modificaTree').disable();
        Ext.getCmp('cancellaTree').disable();
        Ext.getCmp('aggiungiTree').disable();

        tree.render(menu_div);

        tree.selectPath("treepanel/source/<%=set_id%>",'id');
    });
