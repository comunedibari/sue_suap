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
        var table_mappatura_interventi = 'table_mappatura_interventi';
        var table_mappatura_procedimenti = 'table_mappatura_procedimenti';
        var sort_field_procedimenti = 'cod_int';
        var sort_field_interventi = 'cod_proc';
        var table_name = 'mappatura';

        var dyn_fields_protection = ['cod_int','cod_proc'];

        var scelta = "";

        var triggerProcedimenti = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_proc',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_proc")%>",
            name: 'cod_proc',
            window: winProcedimenti,
            hideTrigger1:false,
            hideTrigger3:false,
            clearButton: false,
            lookupFields:['cod_proc','tit_proc'],
            formName: formName,
            labelStyle: 'font-weight:bold;',
            url: urlScriviSessione,
            enableKeyEvents: true,
            windowLink:"<%=basePath%>protected/Procedimenti.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_proc'],
            allowBlank: true,
            setValueCustom: function(r) {
                this.setValue(r);
                triggerInterventi.setDisabled();
                triggerProcedimenti.setDisableButton1();
                triggerProcedimenti.setEnableButton2();
                triggerProcedimenti.setDisableButton3();
                Ext.getCmp('cod_int').setValue("");
                Ext.getCmp('tit_int').setValue("");
                dsProcedimenti.baseParams.cod_proc = r;
                dsProcedimenti.load({params: {start:0, size:grid_row}});
                gridProcedimenti.setHeight(Ext.getBody().getHeight()-Ext.get('header').getHeight()-Ext.get('form').getHeight());
                gridProcedimenti.render(grid_div);
                gridProcedimenti.show();
                scelta="procedimento";
            }
        });

        var triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_int',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
            name: 'cod_int',
            window: winInterventi,
            hideTrigger1:false,
            hideTrigger3:false,
            clearButton: false,
            lookupFields:['cod_int','tit_int'],
            formName: formName,
            labelStyle: 'font-weight:bold;',
            allowBlank: true,
            windowLink:"<%=basePath%>protected/Interventi.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_int'],
            url: urlScriviSessione,
            enableKeyEvents: true,
            setValueCustom: function(r) {
                this.setValue(r);
                triggerProcedimenti.setDisabled();
                triggerInterventi.setDisableButton1();
                triggerInterventi.setEnableButton2();
                triggerInterventi.setDisableButton3();
                Ext.getCmp('cod_proc').setValue("");
                Ext.getCmp('tit_proc').setValue("");
                dsInterventi.baseParams.cod_int = r;
                gridInterventi.setHeight(Ext.getBody().getHeight()-Ext.get('header').getHeight()-Ext.get('form').getHeight());
                dsInterventi.load({params: {start:0, size:grid_row}});
                gridInterventi.render(grid_div);
                gridInterventi.show();
                scelta="intervento";
            }
        });
        var dyn_fields_intervento = [
            {name: 'cod_proc'},
            {name: 'tit_proc'},
            {name: 'cod_dest'},
            {name: 'intestazione'},
            {name: 'cod_com'},
            {name: 'des_ente'},
            {name: 'cod_sport'},
            {name: 'des_sport'}
        ];
        var dyn_fields_procedimento = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'cod_dest'},
            {name: 'intestazione'},
            {name: 'cod_com'},
            {name: 'des_ente'},
            {name: 'cod_sport'},
            {name: 'des_sport'}
        ];
        var readerInterventi = new Ext.data.JsonReader({
            root:table_mappatura_interventi,
            fields: dyn_fields_intervento
        });

        var dsInterventi = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_mappatura_interventi}),
            remoteSort: true,
            reader: readerInterventi,
            sortInfo: {field:sort_field_interventi,direction:'ASC'}
        });

        dsInterventi.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })

        var colModelInterventi = new Ext.grid.ColumnModel([
            {
                id:'grid_cod_proc',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_proc")%>",
                width: 78,
                sortable: true,
                dataIndex: 'cod_proc'
            },
            {
                id:'grid_tit_proc',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_proc")%>",
                renderer: renderTpl,
                width: 320,
                sortable: true,
                dataIndex: 'tit_proc'
            },
            {
                id:'grid_cod_com',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_com")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_com',
                hidden: true
            },
            {
                id:'grid_des_ente',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_ente")%>",
                renderer: renderTpl,
                width: 150,
                sortable: true,
                dataIndex: 'des_ente'
            },
            {
                id:'grid_cod_dest',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_dest")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_dest',
                hidden: true
            },
            {
                id:'grid_intestazione',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_intestazione")%>",
                renderer: renderTpl,
                width: 245,
                sortable: true,
                dataIndex: 'intestazione'
            },
            {
                id:'grid_cod_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_sport")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_sport',
                hidden: true
            },
            {
                id:'grid_des_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_sport")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'des_sport'
            }
        ]);

        var gridInterventi = new Ext.grid.EditorGridPanel({
            title: "<%=testiPortale.get(nomePagina + "_header_grid_interventi")%>",
            store: dsInterventi,
            cm: colModelInterventi,
            viewConfig: {
                forceFit: true
            },
            autoScroll:true,
            border: false
        });

        var readerProcedimenti = new Ext.data.JsonReader({
            root:table_mappatura_procedimenti,
            fields: dyn_fields_procedimento
        });

        var dsProcedimenti = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_mappatura_procedimenti}),
            remoteSort: true,
            reader: readerProcedimenti,
            sortInfo: {field:sort_field_procedimenti,direction:'ASC'}
        });

        dsProcedimenti.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })

        var colModelProcedimenti = new Ext.grid.ColumnModel([
            {
                id:'grid_cod_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int")%>",
                width: 78,
                sortable: true,
                dataIndex: 'cod_int'
            },
            {
                id:'grid_tit_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int")%>",
                renderer: renderTpl,
                width: 330,
                sortable: true,
                dataIndex: 'tit_int'
            },
            {
                id:'grid_cod_com',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_com")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_com',
                hidden: true
            },
            {
                id:'grid_des_ente',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_ente")%>",
                renderer: renderTpl,
                width: 150,
                sortable: true,
                dataIndex: 'des_ente'
            },
            {
                id:'grid_cod_dest',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_dest")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_dest',
                hidden: true
            },
            {
                id:'grid_intestazione',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_intestazione")%>",
                renderer: renderTpl,
                width: 245,
                sortable: true,
                dataIndex: 'intestazione'
            },
            {
                id:'grid_cod_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_sport")%>",
                renderer: renderTpl,
                width: 78,
                sortable: true,
                dataIndex: 'cod_sport',
                hidden: true
            },
            {
                id:'grid_des_sport',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_sport")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'des_sport'
            }
        ]);

        var gridProcedimenti = new Ext.grid.EditorGridPanel({
            store: dsProcedimenti,
            cm: colModelProcedimenti,
            title: "<%=testiPortale.get(nomePagina + "_header_grid_procedimenti")%>",
            autoScroll:true,
            border: false,
            viewConfig: {
                forceFit: true
            }
        });

        var	itemsForm = [triggerInterventi,{
                id: 'tit_int',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                name: 'tit_int',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },triggerProcedimenti,{
                id: 'tit_proc',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_proc")%>",
                name: 'tit_proc',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },{
                id: 'set_id',
                name: 'set_id',
                hidden: true,
                readOnly: true,
                value: '<%=set_id%>'
            },{
                id: 'table_name',
                name: 'table_name',
                hidden: true,
                readOnly: true,
                value: table_name
            }];

        var form = new Ext.FormPanel({
            id: formName,
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
            ajaxSubmit:false,
            standardSubmit: true,
            url: 'mappatura',
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
                    id: 'avanti',
                    text: "<%=testiPortale.get("bottone_avanti")%>",
                    formBind:true,
                    handler: function(){
                       form.getForm().submit({method: 'POST'});
//                        if (scelta == "intervento") {
//                            window.location="AttributiIntervento.jsp?cod_int="+form.getForm().findField("cod_int").getValue();
//                        }
//                        if (scelta == "procedimento") {
//                            window.location="AttributiProcedimento.jsp?cod_proc="+form.getForm().findField("cod_proc").getValue();
//                        }
                    }
                },{
                    id: 'reset',
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        form.getForm().reset();
                        triggerInterventi.setEnabled();
                        triggerProcedimenti.setEnabled();
                        gridInterventi.hide();
                        gridProcedimenti.hide();
                        scelta="";
                        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                            Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                        }
                    }
                }],
            buttonAlign: 'center',
            renderTo: form_div
        });

        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
            Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
        }
        tree.render(menu_div);

        tree.selectPath("treepanel/source/<%=set_id%>",'id');

    });
<!--</script>-->
