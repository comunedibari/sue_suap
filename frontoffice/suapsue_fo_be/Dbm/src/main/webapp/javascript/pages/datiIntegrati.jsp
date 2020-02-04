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
        var href='';
        var tit_href='';

        var sort_field_href = 'href';
        var expand_field_href = '';
        var table_name_href = 'href';
        var id_field_href = '';

        var dyn_fields_href = [
            {name: 'href'},
            {name: 'tit_href'}];
        var readerHref = new Ext.data.JsonReader({
            root:table_name_href,
            fields: dyn_fields_href,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_href
        });

        var dsHref = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_href}),
            sortInfo: {field:sort_field_href,direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerHref
        });

        dsHref.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        });
        var searchSelectionModelHref = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    form.getForm().reset();
                    grid.getStore().removeAll();
                    href=rec.data.href;
                    tit_href=rec.data.tit_href;
                    Ext.getCmp("href").setValue(href);
                    Ext.getCmp("tit_href").setValue(tit_href);
                    ds.baseParams={href:href},
                    ds.load({params: {start:0, size:grid_row}});
                    winHref.hide();
                }
            }
        });

        var colModelHref = new Ext.grid.ColumnModel([
            {
                id:'href_search',
                header: "<%=testiPortale.get("header_href")%>",
                width: 150,
                sortable: true,
                dataIndex: 'href'
            },
            {
                id:'tit_href_search',
                header: "<%=testiPortale.get("header_tit_href")%>",
                width: 850,
                sortable: true,
                dataIndex: 'tit_href'
            }
        ]);
        var searchHref = new Ext.ux.form.SearchField({
            store: dsHref,
            width:320,
            paging: true,
            paging_size: grid_row_popup
        });
        var gridHref = new Ext.grid.GridPanel({
            id:'grid_href',
            frame:true,
            ds: dsHref,
            cm: colModelHref,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelHref,
            oadMask: true,
            autoExpandColumn: expand_field_href,
            height:450,
            bbar: new Ext.PagingToolbar({
                store: dsHref,
                pageSize:grid_row_popup,
                displayInfo:true,
                items:[searchHref]
            }),
            border: true
        });
        var winHref = new Ext.Window({
            title: "<%=testiPortale.get("titolo_window_href")%>",
            closable:true,
            closeAction:'hide',
            width:900,
            height:550,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [gridHref],
            buttons: [{
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){winHref.hide();}
                }],
            display: function(){
                dsHref.reload({params:{start: 0, size:grid_row_popup}});
                this.show();
            },
            lookupFields: ['href','tit_href']
        });

        var table_name = 'dati_integrati';
        var sort_field = 'a.nome';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'nome'},
            {name: 'href'},
            {name: 'tit_href'},
            {name: 'contatore'},
            {name: 'des_campo'},
            {name: 'web_serv'},
            {name: 'nome_xsd'},
            {name: 'campo_key'},
            {name: 'campo_dati'},
            {name: 'campo_xml_mod'},
            {name: 'marcatore_incrociato'},
            {name: 'precompilazione'},
        ];

        var dyn_fields_protection = ['nome','contatore','href','tit_href'];

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
            baseParams: {'href':'123'},
            remoteSort: true,
            reader: reader,
            groupField: group_field
            //		autoLoad:true
        });

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
                width: 30,
                sortable: true,
                dataIndex: 'nome'
            },
            {
                id:'grid_des_campo',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_campo")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'des_campo'
            }
        ]);

        var	itemsForm = [{
                id: 'contatore',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_contatore")%>",
                name: 'contatore',
                readOnly: true,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'href',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_href")%>",
                name: 'href',
                readOnly: true,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'tit_href',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_href")%>",
                name: 'tit_href',
                readOnly: true,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'nome',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome")%>",
                name: 'nome',
                readOnly: true,
                allowBlank: false ,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'web_serv',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_web_serv")%>",
                name: 'web_serv'
            },{
                id: 'nome_xsd',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome_xsd")%>",
                name: 'nome_xsd'
            },{
                id: 'campo_key',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_key")%>",
                name: 'campo_key'
            },{
                id: 'campo_dati',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_dati")%>",
                name: 'campo_dati'
            },{
                id: 'campo_xml_mod',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_xml_mod")%>",
                name: 'campo_xml_mod'
            },{
                id: 'marcatore_incrociato',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_marcatore_incrociato")%>",
                name: 'marcatore_incrociato'
            },{
                id: 'precompilazione',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_precompilazione")%>",
                name: 'precompilazione'
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
                    Ext.getCmp("azione_form").setValue('modifica');
                    for (var ele=0;ele<dyn_fields_protection.length;ele++) {
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
                    id: 'cambiaDichiarazione',
                    text: "<%=testiPortale.get("bottone_cambia_dichiarazione")%>",
                    handler: function(){
                        winHref.display();
                    }
                },{
                    id: 'save',
                    formBind:true,
                    text: "<%=testiPortale.get("bottone_save")%>",
                    disabled:true,
                    handler: function(){
                        form.getForm().submit({url:'aggiorna',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                ds.load({params: {start:0, size:grid_row, href:href}});
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
                        winHref.display();
                    }
                    if (Ext.getDom('azione_form').value == 'modifica') {
                        Ext.getCmp("save").enable();
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
