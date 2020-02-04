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

        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'procedimenti';
        var sort_field = 'cod_proc';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var dyn_fields = [
            {name: 'cod_proc'},
            {name: 'tit_proc'},
            {name: 'cod_cud'},
            {name: 'des_cud'},
            {name: 'ter_eva'},
            {name: 'flg_tipo_proc'},
            {name: 'flg_bollo'}
        ];
        var terEvaNumberField = new Ext.form.NumberField({
            id:'ter_eva',
            fieldLabel: "<%=testiPortale.get("Procedimenti_field_form_ter_eva")%>",
            name: 'ter_eva',
            allowNegative: false,
            allowDecimals: false,
            minValue:0
        });
        var comboFlgTipoProc = new Ext.form.ComboBox({
            id: 'flg_tipo_proc',
            fieldLabel: "<%=testiPortale.get("Procedimenti_field_form_flg_tipo_proc")%>",
            name: 'flg_tipo_proc',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_tipo_proc_hidden',
            emptyText: "<%=testiPortale.get("Procedimenti_field_form_flg_tipo_proc_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_tipo_proc','displayText'],
                data: [['0', "<%=testiPortale.get("Procedimenti_field_form_combo_flg_tipo_proc_0")%>"],
                    ['1', "<%=testiPortale.get("Procedimenti_field_form_combo_flg_tipo_proc_1")%>"],
                    ['2', "<%=testiPortale.get("Procedimenti_field_form_combo_flg_tipo_proc_2")%>"]
                ]
            }),
            valueField: 'flg_tipo_proc',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true
        });
        var comboFlgBollo = new Ext.form.ComboBox({
            id: 'flg_bollo',
            fieldLabel: "<%=testiPortale.get("Procedimenti_field_form_flg_bollo")%>",
            name: 'flg_bollo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'flg_bollo_hidden',
            emptyText: "<%=testiPortale.get("Procedimenti_field_form_flg_bollo_select")%>",
            store: new Ext.data.ArrayStore({
                fields: ['flg_bollo','displayText'],
                data: [['S', "<%=testiPortale.get("Procedimenti_field_form_combo_flg_bollo_S")%>"],
                    ['N', "<%=testiPortale.get("Procedimenti_field_form_combo_flg_bollo_N")%>"]
                ]
            }),
            valueField: 'flg_bollo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            editable: false,
            resizable:true

        });

        var triggerProcedimenti = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_proc',
            fieldLabel:"<%=testiPortale.get("Procedimenti_field_form_cod_proc")%>",
            name: 'cod_proc',
            window: winProcedimenti,
            hideTrigger1:false,
            hideTrigger3:true,
            clearButton: false,
            lookupFields:['cod_proc','tit_proc_it'],
            formName: formName,
            labelStyle: 'font-weight:bold;',
            allowBlank: false,
            fieldsUnprotect:['cod_proc','tit_proc_it'],
            setValueCustom: function(r) {
                this.setValue(r);
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {table_name:"procedimenti", cod_proc:this.getValue()},
                    success: function(response,request) {
                        try {o = Ext.decode(response.responseText);}
                        catch(e) {
                            this.showError(response.responseText);
                            return;
                        }
                        if(!o.success) {
                            txtFld.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
                        }else{
                            for (i in itemsForm) {
                                var dati=Ext.util.JSON.decode(response.responseText).success["procedimenti"][0];
                                if (dati[itemsForm[i].name] != null) {
                                    Ext.getCmp(itemsForm[i].id).setValue(dati[itemsForm[i].name])
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
            },
            customFunctionClear: function() {
                for (index=0;index < this.lookupFields.length;index++) {
                    Ext.getCmp(this.fieldsUnprotect[index]).getEl().dom.readOnly = false;
                };
                form.getForm().reset();
                Ext.getCmp("save").disable();
                Ext.getCmp("insert").enable();
                Ext.getCmp("cancel").disable();
            }
        });

        //Cud
        var triggerCud = new Ext.ux.form.SearchTripleFieldForm({
            id: 'cod_cud',
            fieldLabel:"<%=testiPortale.get("Procedimenti_field_form_cod_cud")%>",
            name: 'cod_cud',
            window: winCud,
            hideTrigger1:true,
            clearButton: true,
            lookupFields:['cod_cud','des_cud'],
            windowLink:"<%=basePath%>protected/Cud.jsp?set_id=<%=set_id%>",
            windowLinkKeys:['cod_cud'],
            formName: formName,
            url: urlScriviSessione,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'
        });
        var dyn_fields_protection = ['cod_proc'];

        triggerProcedimenti.on('blur', function(a) {
            if (this.getValue() != "") {
                Ext.Ajax.request({
                    url: 'leggiEsistenza',
                    method: 'POST',
                    params: {table_name:"procedimenti", tab_key:this.getValue()},
                    success: function(response,request) {
                        try {o = Ext.decode(response.responseText);}
                        catch(e) {
                            this.showError(response.responseText);
                            return;
                        }
                        if(!o.success) {
                            triggerProcedimenti.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            }
        });
        var	itemsForm = [triggerProcedimenti,{
                id: 'tit_proc_it',
                fieldLabel: "<%=testiPortale.get("Procedimenti_field_form_tit_proc")%>",
                name: 'tit_proc_it',
                xtype:'textarea',
                height: 37,
                allowBlank: false,
                maxLength: 64000,
                labelStyle: 'font-weight:bold;'
            },
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
            {
                id: 'tit_proc_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get("Procedimenti_field_form_tit_proc")%>(<%=lingueAggregazione.get(i)%>)",
                name: 'tit_proc_<%=lingueAggregazione.get(i)%>',
                xtype:'textarea',
                height: 37,
                allowBlank: false,
                maxLength: 64000,
                labelStyle: 'font-weight:bold;'
            }, 
            <%}%>
            triggerCud,
            {
                id: 'des_cud',
                fieldLabel: "<%=testiPortale.get("Procedimenti_field_form_des_cud")%>",
                name: 'des_cud',
                xtype:'textarea',
                height: 37,
                readOnly: true
            },terEvaNumberField,comboFlgTipoProc,comboFlgBollo,{
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
            title: "<%=testiPortale.get("Procedimenti_header_form")%>",
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
                                Ext.getCmp("azione_form").setValue('modifica');
                                Ext.getCmp("save").enable();
                                Ext.getCmp("insert").disable();
                                Ext.getCmp("cancel").enable();
                                triggerCud.setDisabled();
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
                                Ext.getCmp('save').enable();
                                Ext.getCmp('insert').disable();
                                Ext.getCmp('cancel').enable();
                                triggerCud.setDisabled();
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
                },{
                    id: 'reset',
                    text: "<%=testiPortale.get("bottone_reset")%>",
                    handler: function(){
                        form.getForm().reset();
                        Ext.getCmp('insert').enable();
                        Ext.getCmp('save').disable();
                        Ext.getCmp('cancel').disable();
                        triggerCud.setEnabled();
                        Ext.getCmp("azione_form").setValue('inserimento');
                        for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                            Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                        }
                    }
                },{
                    id: 'avanti',
                    text: "<%=testiPortale.get("bottone_avanti")%>",
                    handler: function(){
                      window.location="RelazioniEnti.jsp?set_id=<%=set_id%>";
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
                        triggerCud.setEnabled();
                    }
                    if (Ext.getDom('azione_form').value == 'modifica') {
                        Ext.getCmp("save").enable();
                        Ext.getCmp("insert").disable();
                        Ext.getCmp("cancel").enable();
                        triggerCud.setEnabled();
                    }
                }
            },
            failure: function(response,request) {
                Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
            }
        });

        cod_proc=Ext.getUrlParam('cod_proc');
        if (cod_proc) {
            Ext.Ajax.request({
                url: 'leggiRecord',
                method: 'POST',
                params: {'cod_proc':cod_proc, 'table_name':table_name},
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

        tree.render(menu_div);

        tree.selectPath("treepanel/source/<%=set_id%>",'id');
    });
<!--</script>-->
