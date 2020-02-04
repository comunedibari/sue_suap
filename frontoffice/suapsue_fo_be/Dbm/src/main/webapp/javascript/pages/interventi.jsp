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
<%@page import="java.util.List"%>
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

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'interventi';
        var sort_field = 'cod_int';

        var id_field = '';
        var group_field = '';
        var expand_field = '';
        
        var tipoAggregazione = null;
        <% if (utente.getTipAggregazione() != null){%>
            tipoAggregazione = '<%=utente.getTipAggregazione()%>';
        <%}%>

        function changeFlgObb(val){
            if(val == 'O'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_obb_O")%>";
            }else if(val == 'F'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_obb_F")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_obb_N")%>";
            }
            return val;
        };
        var comboFlgObb = new Ext.form.ComboBox({
            id: 'flg_obb',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_flg_obb")%>",
            name: 'flg_obb',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_obb_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_flg_obb_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_obb','displayText'],
                data: [['O', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_obb_O")%>"],
                    ['F', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_obb_F")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_flg_obb_N")%>"]]
            }),
            valueField: 'flg_obb',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true

        });

        var dyn_fields = [
            {name: 'cod_int'},
            {name: 'tit_int_it'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'tit_int_<%=lingueAggregazione.get(i)%>'},
             <%}%>
            {name: 'cod_proc'},
            {name: 'tit_proc'},
            {name: 'flg_obb'},
            {name: 'tip_aggregazione'},
            {name: 'des_aggregazione'},
            {name: 'ordinamento'}
        ];


        var dyn_fields_protection = ['cod_int'];

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

        //Procedimenti
        var triggerProcedimenti = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_proc',
            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_proc")%>",
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

        var colModel = new Ext.grid.ColumnModel([
            {
                id:'grid_cod_int',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_int'
            },
            {
                id:'grid_tit_int_it',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'tit_int_it'
            },
            {
                id:'grid_cod_proc',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_proc")%>",
                width: 30,
                sortable: true,
                dataIndex: 'cod_proc'
            },
            {
                id:'grid_tit_proc',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_proc")%>",
                renderer: renderTpl,
                width: 100,
                sortable: true,
                dataIndex: 'tit_proc'
            },
            {
                id:'grid_flg_obb',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_flg_obb")%>",
                renderer: changeFlgObb,
                width: 30,
                sortable: true,
                dataIndex: 'flg_obb'
            },
            {
                id:'grid_ordinamento',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_ordinamento")%>",
                width: 30,
                sortable: true,
                dataIndex: 'ordinamento'
            }
            <% if (territorio) { %>
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
        var txtFld = new Ext.form.TextField({
            id:'cod_int',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
            name: 'cod_int',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
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
        var ordinamentoNumberField = new Ext.form.NumberField({
            id:'ordinamento',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_ordinamento")%>",
            name: 'ordinamento',
            labelStyle: 'font-weight:bold;',
            allowNegative: false,
            allowDecimals: false
        });
        
        var	itemsForm = [txtFld,{
                id: 'tit_int_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                maxLength: 64000,
                allowBlank: false,
                name: 'tit_int_it',
                labelStyle: 'font-weight:bold;'
            },
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
            {
                id: 'tit_int_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>(<%=lingueAggregazione.get(i)%>)",
                maxLength: 64000,
                allowBlank: false,
                name: 'tit_int_<%=lingueAggregazione.get(i)%>',
                labelStyle: 'font-weight:bold;'
            },
           <%}%>
            triggerProcedimenti,{
                id: 'tit_proc',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_proc")%>",
                name: 'tit_proc',
                readOnly: true
            },comboFlgObb,ordinamentoNumberField
            <% if (territorio) { %>
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
                    Ext.getCmp("insert").disable();
                    Ext.getCmp("azione_form").setValue('modifica');
                    if (tipoAggregazione == rec.data.tip_aggregazione || rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined || tipoAggregazione == null) {
                        Ext.getCmp("save").enable();
                        Ext.getCmp("cancel").enable();
                    } else {
                        Ext.getCmp("save").disable();
                        Ext.getCmp("cancel").disable();
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
<% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) { %>
                   {
                    id: 'save',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    disabled:true,
                    handler: function(){
                        if (trim(Ext.getCmp("ordinamento").getValue()) == ""){
                            Ext.getCmp("ordinamento").setValue(Ext.getCmp("cod_int").getValue());
                        }
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
                    handler:  function() {
                        Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                        function(e) {
                            if (e=='yes'){
                                form.getForm().submit({url:"cancella",
                                    waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                    success: function(result,request) {
                                        Ext.wego.msg("Status", Ext.util.JSON.decode(request.response.responseText).success);
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
                        form.getForm().submit({url:'inserisci',
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                ds.load({params: {start:0, size:grid_row}});
                                Ext.getCmp('save').enable();
                                Ext.getCmp('insert').disable();
                                Ext.getCmp('cancel').enable();
                                for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                    Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                                }
                                Ext.getCmp("azione_form").setValue('modifica');
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
                },
<% } %>
                {
                    id: 'reset',
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        form.getForm().reset();
                        Ext.getCmp('insert').enable();
                        Ext.getCmp('save').disable();
                        Ext.getCmp('cancel').disable();
                        Ext.getCmp("azione_form").setValue('inserimento');
                        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                            Ext.getCmp(formName).getForm().findField(dyn_fields_protection[ele]).getEl().dom.readOnly = false;
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
        cod_int=Ext.getUrlParam('cod_int');
        if (!cod_int) {
            cod_int=Ext.getUrlParam('cod_int_padre');
        }
        if (!cod_int) {
            cod_int=Ext.getUrlParam('cod_int_sel');
        }
        if (!cod_int) {
            cod_int=Ext.getUrlParam('cod_int_prec');
        }
        if (cod_int) {
            Ext.Ajax.request({
                url: 'leggiRecord',
                method: 'POST',
                params: {'cod_int':cod_int, 'table_name':table_name},
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
                            for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                            }
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
