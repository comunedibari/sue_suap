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
    
        Ext.override(Ext.grid.EditorGridPanel, {
            onEditComplete : function(ed, value, startValue){
                this.editing = false;
                this.activeEditor = null;
                ed.un("specialkey", this.selModel.onEditorKey, this.selModel);
                var r = ed.record;
                var field = this.colModel.getDataIndex(ed.col);
                value = this.postEditValue(value, startValue, r, field);
                if(this.forceValidation === true || String(value) !== String(startValue)){
                    var e = {
                        grid: this,
                        record: r,
                        field: field,
                        originalValue: startValue,
                        value: value,
                        row: ed.row,
                        column: ed.col,
                        cancel:false
                    };
                    if(this.fireEvent("validateedit", e) !== false && !e.cancel) { // && String(value) !== String(startValue)){
                        r.set(field, e.value);
                        delete e.cancel;
                        this.fireEvent("afteredit", e);
                    }
                }
                this.view.focusCell(ed.row, ed.col);
            }
        });
        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var interventiCodici=[];
        var interventi;
        var decodeCodeName = {
        	'interventi' : 'codExt',
        	'procedimenti' : 'procExt',
        	'cud' : 'cudExt',
        	'documenti' : 'docExt',
        	'dichiarazioni' : 'dicExt',
        	'testo_condizioni' : 'condExt',
        	'normative' : 'rifExt',
        	'tipi_rif' : 'tipExt'
        };

        var dyn_fields_protection = [];
        var legenda = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice accettato: <span class="ok-col">&nbsp;&nbsp;&nbsp;&nbsp;</span>, Codice presente in banca dati : <span class="db-col">&nbsp;&nbsp;&nbsp;&nbsp;</span>, Codice già utilizzato: <span class="ko-col">&nbsp;&nbsp;&nbsp;&nbsp;</span>, Codice di altra aggregazione: <span class="stop-col">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        Ext.layout.FormLayout.prototype.trackLabels = true;

        function renderEsistenzaIcon(value, p, record){ 
            var icon;
            if (value == 'S') {
                icon = '<div class="ko-col"></div>';
            } else if (value == 'N') {
                icon = '<div class="ok-col"></div>';
            } else if (value == 'D'){
                icon = '<div class="db-col"></div>';
            } else if (value == 'X'){
                icon = '<div class="stop-col"></div>';
            } else {
                icon = '<div></div>';
            }
            return icon;
        }

        function checkCodice(gridPanel, typeRemoto, typeGrid, fieldName, tabPanel){
            gridPanel.on('afteredit', function(e) {
                if (e.record.data.new_code != "") {
                    var new_code = e.record.data.new_code;
                    var fieldNameCmpl = 'var cod_orig = e.record.data.'+fieldName;
                    eval(fieldNameCmpl);
                    Ext.Ajax.request({
                        url: 'salvaInSessione',
                        method: 'POST',
                        params: {
                            type: typeRemoto,
                            cod_orig: cod_orig,
                            new_code: new_code
                        },
                        success: function(response,request) {
                            var esito = Ext.decode(response.responseText);
                            for ( chiave in interventi) {
                              for ( var i=0;i < interventi[chiave][typeGrid].length; i++){
                                 if (interventi[chiave][typeGrid][i][fieldName]==cod_orig){
                                     interventi[chiave][typeGrid][i].new_code=new_code;
                                     interventi[chiave][typeGrid][i].esistenza=esito.esistenza;
                                     tabPanel.getItem(chiave).items.get(typeGrid+"_"+chiave).store.loadData(interventi[chiave]);
                                 }
                              }
                            }
                        },
                        failure: function(response,request) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }
                    });
                }
            });
        }

        var caricaButton = new Ext.Button({
            id: 'carica',
            text: "<%=testiPortale.get("bottone_carica")%>",
            handler: function(){
                tabPanel.show();
                Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_caricamento")%>",
                function(e) {
                    if (e=='yes'){
                        form.getForm().submit({url:'duplicazioneInterventi?carica=yes',
                            waitMsg: "<%=testiPortale.get("attesa_caricamento")%>",
                            success: function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                    Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                                };
                                //cleanAllStore();
                                tabPanel.hide();
                                caricaButton.hide();
                            },
                            failure:function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        })
                    }
                });
            }
        });

        var eseguiButton =  new Ext.Button({
            id: 'esegui',
            text: "<%=testiPortale.get("bottone_esegui")%>",
            formBind:true,
            handler: function(){
                Ext.Ajax.request({
                    url: 'pulisciSession',
                    method: 'POST',
                    params: {},
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
                valoreRadio = form.getForm().getValues()['rb'];
                if (valoreRadio == "2") {
                    Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_scarico")%>",
                    function(e) {
                        if (e=='yes'){
                            cod_int = form.getForm().getValues()['cod_int'];
                            cod_com = form.getForm().getValues()['cod_com'];
                            int_coll = form.getForm().getValues()['int_coll'];
                            window.open("duplicazioneInterventi?cod_int="+cod_int+"&rb="+valoreRadio+"&cod_com="+cod_com+"&int_coll="+int_coll);
                            caricaButton.hide();
                        }
                    }
                );
                }
                if (valoreRadio == "1") {
                    Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_copia")%>",
                    function(e) {
                        if (e=='yes'){
                            form.getForm().submit({url:'duplicazioneInterventi',
                                waitMsg: "<%=testiPortale.get("attesa_copia")%>",
                                success: function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                        Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                                    };
                                    caricaButton.hide();
                                },
                                failure:function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            })
                        }
                    }
                );
                }
                if (valoreRadio == "3") {
                    Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_verifica_import")%>",
                    function(e) {
                        if (e=='yes'){
                            form.getForm().submit({url:'duplicazioneInterventi',
                                waitMsg: "<%=testiPortale.get("attesa_verifica_import")%>",
                                success: function(result,request) {
                                    interventiCodici=Ext.util.JSON.decode(request.response.responseText).success.interventiCodici;
                                    interventi=Ext.util.JSON.decode(request.response.responseText).success.interventi;
                                    if (interventiCodici) {
                                        tabPanel = new Ext.TabPanel({
                                            region:'center',
                                            id:'tabPanel',
                                            activeTab:0,
                                            defaults:{autoScroll:true}
                                        }); 
                                        for (var i=0; i < interventiCodici.length; i++) {
                                            intervento=interventi[interventiCodici[i]];
                                            var chkCol = new Ext.grid.CheckColumn({
                                            					header: 'Escludere',
                                            					dataIndex: 'exclude',
                                            					width: 65,
                                           					    processEvent : function(name, e, grid, rowIndex, colIndex){
                                           					    	if (name == 'mousedown') {
                                           					    		var entityToSet = interventi[grid.findParentByType('panel').id][grid.type][rowIndex];
                                           					    		entityToSet.exclude = (entityToSet.exclude == "") ? "on" : "";
                                           					    		var codeToSend = (entityToSet.new_code === undefined) ? entityToSet[decodeCodeName[grid.type]] : entityToSet.new_code;
                                           					    		grid.store.loadData(interventi[grid.findParentByType('panel').id]);
													                    Ext.Ajax.request({
													                        url: 'actions_CCD',
													                        method: 'POST',
																			params: {
																				'type':grid.type, 
                                        										'table_name':'import_intervento',
										                                        'esistenza':entityToSet.esistenza,
										                                        'code':codeToSend,
										                                        'status':entityToSet[this.dataIndex],
                                        										'action':'exclude',
                                        										session:'yes'
                                        									},
													                        success: function(response,request) {
													                            var esito = Ext.decode(response.responseText);
													                            var propagation = esito.propagation;
													                            if (propagation) {
													                            	for (var i = 0; i < interventiCodici.length; i++) {
													                            	    var codeInt = interventiCodici[i];
													                            		for (var key in propagation) {
													                            			for (var j = 0; j < interventi[codeInt][key].length; j++) {
													                            				var entity = interventi[codeInt][key][j];
													                            				var codeToCheck = (entity.new_code === undefined) ? entity[decodeCodeName[key]] : entity.new_code;
													                            				if (propagation[key].indexOf(codeToCheck) != -1) {
													                            					entity.exclude = "on";
													                            				}
													                            			}
													                            			tabPanel.getItem(codeInt).items.get(key+"_"+codeInt).store.loadData(interventi[codeInt]);
													                            		}
													                            	}
													                            }
													                        },
													                        failure: function(response,request) {
													                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
													                        }
													                    });           																         																
           																return false; // Cancel row selection.
       																} else {
           																return Ext.grid.ActionColumn.superclass.processEvent.apply(this, arguments);
       																}
   																}               
                                            				});
                                            var tab = new Ext.Panel({
                                                defaults:{autoHeight: true, autoScroll:true},
                                                id:interventiCodici[i],
                                                title:interventiCodici[i],
                                                items:[{xtype: 'editorgrid',
														id: 'interventi_'+interventiCodici[i],
														type: 'interventi', 
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'exclude'},
                                                            	{name: 'codExt'},
                                                                {name: 'desExt'},
                                                                {name: 'codInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data:   {"interventi":{}},
                                                            root   : 'interventi'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'codExt'},
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.NumberField({
                                                                     labelStyle: 'font-weight:bold;',
                                                                     maxLength:9,
                                                                     enableKeyEvents: true
                                                                    })},
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon},
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'},
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'}
                                                        ],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_intervento")%>" + legenda,
                                                        stateful: true,
                                                        autoScroll:true  
                                                    },{xtype: 'editorgrid',
                                                        id: 'procedimenti_'+interventiCodici[i],
														type: 'procedimenti', 
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'procExt'},
                                                                {name: 'exclude'},
                                                                {name: 'desExt'},
                                                                {name: 'procInt'},
                                                                {name: 'desInt'}, 
                                                                {name: 'esistenza'}, 
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"procedimenti":{}},
                                                            root   : 'procedimenti'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol, {header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'procExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:8,
                                                                        enableKeyEvents: true
                                                                    })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_procedimento")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    },{xtype: 'editorgrid',
                                                        id: 'cud_'+interventiCodici[i],
														type: 'cud', 
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'cudExt'},
                                                                {name: 'desExt'},
                                                                {name: 'exclude'},
                                                                {name: 'cudInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"cud":{}},
                                                            root   : 'cud'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'cudExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:8,
                                                                        enableKeyEvents: true
                                                                    })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon

                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }
                                                        ],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_cud")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    },{xtype: 'editorgrid',
                                                        id: 'documenti_'+interventiCodici[i],
														type: 'documenti', 
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'docExt'},
                                                                {name: 'desExt'},
                                                                {name: 'exclude'},
                                                                {name: 'docInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"documenti":{}},
                                                            root   : 'documenti'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,{header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'docExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:20,
                                                                        enableKeyEvents: true
                                                                })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon

                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_documento")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    },{xtype: 'editorgrid',
                                                        id: 'dichiarazioni_'+interventiCodici[i],
														type: 'dichiarazioni', 
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'dicExt'},
                                                                {name: 'desExt'},
                                                                {name: 'exclude'},
                                                                {name: 'dicInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"dichiarazioni":{}},
                                                            root   : 'dichiarazioni'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,{header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'dicExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:50,
                                                                        enableKeyEvents: true
                                                                    })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon

                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_dichiarazioni")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    },{xtype: 'editorgrid',
                                                        id: 'testo_condizioni_'+interventiCodici[i],  
														type: 'testo_condizioni', 
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'condExt'},
                                                                {name: 'desExt'},
                                                                {name: 'exclude'},
                                                                {name: 'condInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"testo_condizioni":{}},
                                                            root   : 'testo_condizioni'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,{header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'condExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:10,
                                                                        enableKeyEvents: true
                                                                    })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon

                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_condizioni")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    },{xtype: 'editorgrid',
                                                        id: 'normative_'+interventiCodici[i],
                                                        type: 'normative',
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'rifExt'},
                                                                {name: 'desExt'},
                                                                {name: 'exclude'},
                                                                {name: 'rifInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"normative":{}},
                                                            root   : 'normative'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,{header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'rifExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:8,
                                                                        enableKeyEvents: true
                                                                    })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon

                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_normative")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    },{xtype: 'editorgrid',
                                                        id: 'tipi_rif_'+interventiCodici[i],
                                                        type: 'tipi_rif',
                                                        store: new Ext.data.JsonStore({
                                                            fields: [{name: 'tipExt'},
                                                                {name: 'desExt'},
                                                                {name: 'exclude'},
                                                                {name: 'tipInt'},
                                                                {name: 'desInt'},
                                                                {name: 'esistenza'},
                                                                {name: 'change'},
                                                                {name: 'new_code'}],
                                                            data: {"tipi_rif":{}},
                                                            root   : 'tipi_rif'
                                                        }),
                                                        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
                                                        columns: [chkCol,{header   : "<%=testiPortale.get(nomePagina + "_grid_codice")%>",
                                                                width    : 100,
                                                                sortable : true,
                                                                dataIndex: 'tipExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_new_code")%>",
                                                                width    : 100,
                                                                sortable : false,
                                                                renderer: renderTpl,
                                                                dataIndex: 'new_code',
                                                                editor: new Ext.form.TextField({
                                                                        allowBlank: false,
                                                                        labelStyle: 'font-weight:bold;',
                                                                        maxLength:4,
                                                                        enableKeyEvents: true
                                                                    })
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_esiste")%>",
                                                                width    : 50,
                                                                sortable : false,
                                                                dataIndex: 'esistenza',
                                                                renderer: renderEsistenzaIcon

                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_xml")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desExt'
                                                            },
                                                            {header   : "<%=testiPortale.get(nomePagina + "_grid_descrizione_locale")%>",
                                                                width    : 400,
                                                                sortable : true,
                                                                renderer: renderTpl,
                                                                dataIndex: 'desInt'
                                                            }],
                                                        stripeRows: true,
                                                        height: 70,
                                                        clicksToEdit: 1,
                                                        viewConfig: {
                                                        	stripRows: false,
	                                                        getRowClass: function (record) {
	                                                        	return (record.get('exclude') == "") ? 'normal-Import' : 'excluded-Import';
	                                                        }
                                                        },
                                                        title: "<%=testiPortale.get(nomePagina + "_grid_title_tipi_normativa")%>",
                                                        stateful: true,
                                                        autoScroll:true
                                                    }
                                                ]
                                            });
                                            tab.items.get(0).store.loadData(intervento);
                                            tab.items.get(1).store.loadData(intervento);
                                            tab.items.get(2).store.loadData(intervento);
                                            tab.items.get(3).store.loadData(intervento);
                                            tab.items.get(4).store.loadData(intervento);
                                            tab.items.get(5).store.loadData(intervento);
                                            tab.items.get(6).store.loadData(intervento);
                                            tab.items.get(7).store.loadData(intervento);											
                                            tabPanel.add(tab);  
                                            checkCodice(tab.items.get(0), 'interventi', 'interventi', 'codExt', tabPanel);	
                                            checkCodice(tab.items.get(1), 'procedimenti', 'procedimenti', 'procExt', tabPanel);
                                            checkCodice(tab.items.get(2), 'cud', 'cud', 'cudExt', tabPanel);
                                            checkCodice(tab.items.get(3), 'documenti', 'documenti', 'docExt', tabPanel);
                                            checkCodice(tab.items.get(4), 'dichiarazioni','dichiarazioni', 'dicExt', tabPanel);
                                            checkCodice(tab.items.get(5), 'testo_condizioni', 'testo_condizioni', 'condExt', tabPanel);
                                            checkCodice(tab.items.get(6), 'normative', 'normative', 'rifExt', tabPanel);
                                            checkCodice(tab.items.get(7), 'tipi_rif', 'tipi_rif', 'tipExt', tabPanel);
                                        }
                                    }
                                    tabPanel.render(grid_div);
                                    tabPanel.show();
                                    Ext.getCmp('form-file').readOnly;
                                    caricaButton.show();
                                    eseguiButton.hide();
                                },
                                failure:function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            })
                        }
                    }
                );
                }
            }
        });

        var radioGroup = {
            id:'radioGroup',
            xtype: 'fieldset',
            title: "<%=testiPortale.get(nomePagina + "_operazioni")%>",
            autoHeight: true,
            items: [{
                    xtype: 'radiogroup',
                    //                    fieldLabel: 'Single Column',
                    itemCls: 'x-check-group-alt',
                    columns: 1,
                    items: [
                        {boxLabel: "<%=testiPortale.get(nomePagina + "_duplicazione_locale")%>", name: 'rb', inputValue: '1'},
                        {boxLabel: "<%=testiPortale.get(nomePagina + "_export_file")%>", name: 'rb', inputValue: '2'},
                        {boxLabel: "<%=testiPortale.get(nomePagina + "_import_file")%>", name: 'rb', inputValue: '3'},
                        {boxLabel: "<%=testiPortale.get(nomePagina + "_nessuna_attivita")%>", name: 'rb', inputValue: '4' ,checked: true}
                    ],
                    listeners: {
                        'change': {
                            fn: function(radioGroup, selectedRadio) {
                                switch (selectedRadio.getGroupValue())
                                {
                                    case '1':
                                        triggerComuni = new Ext.ux.form.SearchTripleFieldForm({
                                            id: 'cod_com',
                                            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_com")%>",
                                            name: 'cod_com',
                                            window: winComuni,
                                            hideTrigger1:true,
                                            hideTrigger3:true,
                                            clearButton: false,
                                            lookupFields:['cod_com','des_com'],
                                            windowLink: false,
                                            formName: formName,
                                            labelStyle: 'font-weight:bold;',
                                            allowBlank: false,
                                            url: false,
                                            anchor:'95%',
                                            hideContainer: false,
                                            setValueCustom: function(r) {
                                                this.setValue(r);
                                                triggerInterventi.setEnabled();
                                                triggerInterventi.setDisableButton1();
                                                triggerInterventi.setEnableButton2();
                                                triggerInterventi.setDisableButton3();
                                                Ext.getCmp('cod_int').setValue("");
                                                Ext.getCmp('tit_int').setValue("");
                                                dsInterventi.baseParams.cod_com = r;
                                            }
                                        });
                                        triggerComuniDesc =  new Ext.form.TextArea({
                                            id: 'des_com',
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_com")%>",
                                            name: 'des_com',
                                            height: 37,
                                            readOnly: true,
                                            anchor:'95%',
                                            hideContainer: false
                                        });

                                        triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
                                            id: 'cod_int',
                                            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
                                            name: 'cod_int',
                                            window: winInterventi_totali,
                                            hideTrigger1:true,
                                            hideTrigger3:true,
                                            clearButton: false,
                                            lookupFields:['cod_int','tit_int'],
                                            windowLink: false,
                                            formName: formName,
                                            labelStyle: 'font-weight:bold;',
                                            allowBlank: false,
                                            url: false,
                                            anchor:'95%',
                                            hideContainer: false
                                        });

                                        triggerInterventiDesc =  new Ext.form.TextArea({
                                            id: 'tit_int',
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                                            name: 'tit_int',
                                            height: 37,
                                            readOnly: true,
                                            anchor:'95%',
                                            hideContainer: false
                                        });
                                        txtFld = new Ext.form.NumberField({
                                            id:'cod_int_new',
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_cod_int_new")%>",
                                            name: 'cod_int_new',
                                            allowBlank: false,
                                            labelStyle: 'font-weight:bold;'	,
                                            allowNegative: false,
                                            allowDecimals: false,
                                            anchor:'95%',
                                            minValue:1,
                                            maxValue:999999999,
                                            enableKeyEvents: true
                                        });
                                        txtFld.on('blur', function(a) {
                                            if (this.getValue() != "") {
                                                Ext.Ajax.request({
                                                    url: 'leggiEsistenza',
                                                    method: 'POST',
                                                    params: {table_name:'interventi', tab_key:this.getValue()},
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
                                        });
                                        var interventiCollegatiCombo = new Ext.form.Checkbox ({
                                            id: 'int_coll', 
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_interventi_collegati")%>",
                                            name: 'int_coll',
                                            inputValue: 'collegati',
                                            allowBlank: false
                                        });
                                        if (Ext.getCmp('cod_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuni);
                                        }
                                        if (Ext.getCmp('des_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuniDesc);
                                        }
                                        if (Ext.getCmp('cod_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventi);
                                        }
                                        if (Ext.getCmp('tit_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventiDesc);
                                        }
                                        if (Ext.getCmp('int_coll')) {
                                            radioGroup.findParentByType('fieldset').remove(interventiCollegatiCombo);
                                        }   
                                        
                                        radioGroup.findParentByType('fieldset').add(triggerComuni);
                                        radioGroup.findParentByType('fieldset').add(triggerComuniDesc);
                                        radioGroup.findParentByType('fieldset').add(triggerInterventi);
                                        radioGroup.findParentByType('fieldset').add(triggerInterventiDesc);
                                        radioGroup.findParentByType('fieldset').add(txtFld);
                                        radioGroup.findParentByType('fieldset').add(interventiCollegatiCombo);
                                        radioGroup.findParentByType('fieldset').doLayout();
                                        triggerInterventi.setDisabled();

                                        if (Ext.getCmp('form-file')) {
                                            radioGroup.findParentByType('fieldset').remove(fileUpload);
                                        }
                                        if (Ext.getCmp('tabPanel')){
                                            Ext.getCmp('tabPanel').destroy()
                                        }
                                        caricaButton.hide();
                                        eseguiButton.show();
                                        break;
                                    case '2':
                                        triggerComuni = new Ext.ux.form.SearchTripleFieldForm({
                                            id: 'cod_com',
                                            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_com")%>",
                                            name: 'cod_com',
                                            window: winComuni,
                                            hideTrigger1:true,
                                            hideTrigger3:true,
                                            clearButton: false,
                                            lookupFields:['cod_com','des_com'],
                                            windowLink: false,
                                            formName: formName,
                                            labelStyle: 'font-weight:bold;',
                                            allowBlank: false,
                                            url: false,
                                            anchor:'95%',
                                            hideContainer: false,
                                            setValueCustom: function(r) {
                                                this.setValue(r);
                                                triggerInterventi.setEnabled();
                                                triggerInterventi.setDisableButton1();
                                                triggerInterventi.setEnableButton2();
                                                triggerInterventi.setDisableButton3();
                                                Ext.getCmp('cod_int').setValue("");
                                                Ext.getCmp('tit_int').setValue("");
                                                dsInterventi.baseParams.cod_com = r;
                                            }
                                        });
                                        triggerComuniDesc =  new Ext.form.TextArea({
                                            id: 'des_com',
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_com")%>",
                                            name: 'des_com',
                                            height: 37,
                                            readOnly: true,
                                            anchor:'95%',
                                            hideContainer: false
                                        });
                                        triggerInterventi = new Ext.ux.form.SearchTripleFieldForm({
                                            id: 'cod_int',
                                            fieldLabel:"<%=testiPortale.get(nomePagina + "_field_form_cod_int")%>",
                                            name: 'cod_int',
                                            window: winInterventi_totali,
                                            hideTrigger1:true,
                                            hideTrigger3:true,
                                            clearButton: false,
                                            lookupFields:['cod_int','tit_int'],
                                            windowLink: false,
                                            formName: formName,
                                            labelStyle: 'font-weight:bold;',
                                            allowBlank: false,
                                            url: false,
                                            anchor:'95%',
                                            hideContainer: false
                                        });

                                        triggerInterventiDesc =  new Ext.form.TextArea({
                                            id: 'tit_int',
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_int")%>",
                                            name: 'tit_int',
                                            height: 37,
                                            readOnly: true,
                                            anchor:'95%',
                                            hideContainer: false
                                        });
                                        var interventiCollegatiCombo = new Ext.form.Checkbox ({
                                            id: 'int_coll', 
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_interventi_collegati")%>",
                                            name: 'int_coll',
                                            inputValue: 'collegati',                                            
                                            allowBlank: false
                                        });  
                                        if (Ext.getCmp('cod_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuni);
                                        }
                                        if (Ext.getCmp('des_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuniDesc);
                                        }
                                        if (Ext.getCmp('cod_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventi);
                                        }
                                        if (Ext.getCmp('tit_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventiDesc);
                                        }
                                        if (Ext.getCmp('int_coll')) {
                                            radioGroup.findParentByType('fieldset').remove(interventiCollegatiCombo);
                                        }                                        
                                        radioGroup.findParentByType('fieldset').add(triggerComuni);
                                        radioGroup.findParentByType('fieldset').add(triggerComuniDesc);
                                        radioGroup.findParentByType('fieldset').add(triggerInterventi);
                                        radioGroup.findParentByType('fieldset').add(triggerInterventiDesc);
                                        radioGroup.findParentByType('fieldset').add(interventiCollegatiCombo);
                                        radioGroup.findParentByType('fieldset').doLayout();
                                        triggerInterventi.setDisabled();

                                        if (Ext.getCmp('form-file')) {
                                            radioGroup.findParentByType('fieldset').remove(fileUpload);
                                        }
                                        if (Ext.getCmp('cod_int_new')) {
                                            radioGroup.findParentByType('fieldset').remove(txtFld);
                                        }
                                        if (Ext.getCmp('tabPanel')){
                                            Ext.getCmp('tabPanel').destroy()
                                        }
                                        caricaButton.hide();
                                        eseguiButton.show();
                                        break;

                                    case '3':
                                        fileUpload = new Ext.ux.form.FileUploadField ({
                                            xtype: 'fileuploadfield',
                                            id: 'form-file',
                                            emptyText: "<%=testiPortale.get("form_upload_file_empty")%>",
                                            fieldLabel: "<%=testiPortale.get("form_upload_file_name")%>",
                                            allowBlank: false,
                                            labelStyle: 'font-weight:bold;',
                                            name: 'document_path',
                                            buttonText: '',
                                            anchor:'95%',
                                            hideContainer: false,
                                            buttonCfg: {
                                                iconCls: 'upload-icon'
                                            }

                                        });
                                        var interventiCollegatiCombo = new Ext.form.Checkbox ({
                                            id: 'int_coll', 
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_interventi_collegati")%>",
                                            name: 'int_coll',
                                            inputValue: 'collegati',                                            
                                            allowBlank: false
                                        });                                       
                                        if (Ext.getCmp('cod_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuni);
                                        }
                                        if (Ext.getCmp('des_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuniDesc);
                                        }
                                        if (Ext.getCmp('cod_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventi);
                                        }
                                        if (Ext.getCmp('tit_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventiDesc);
                                        }
                                        if (Ext.getCmp('cod_int_new')) {
                                            radioGroup.findParentByType('fieldset').remove(txtFld);
                                        }
                                        if (Ext.getCmp('int_coll')) {
                                            radioGroup.findParentByType('fieldset').remove(interventiCollegatiCombo);
                                        }                                        
                                        radioGroup.findParentByType('fieldset').add(interventiCollegatiCombo);                                        
                                        radioGroup.findParentByType('fieldset').add(fileUpload);
                                        caricaButton.hide();
                                        eseguiButton.show();
                                        break;

                                    case '4':
                                        //cleanAllStore();
                                        var interventiCollegatiCombo = new Ext.form.Checkbox ({
                                            id: 'int_coll', 
                                            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_interventi_collegati")%>",
                                            name: 'int_coll',
                                            inputValue: 'collegati',                                            
                                            allowBlank: false
                                        });                                         
                                        if (Ext.getCmp('cod_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuni);
                                        }
                                        if (Ext.getCmp('des_com')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerComuniDesc);
                                        }
                                        if (Ext.getCmp('cod_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventi);
                                        }
                                        if (Ext.getCmp('tit_int')) {
                                            radioGroup.findParentByType('fieldset').remove(triggerInterventiDesc);
                                        }
                                        if (Ext.getCmp('form-file')) {
                                            radioGroup.findParentByType('fieldset').remove(fileUpload);
                                        }
                                        if (Ext.getCmp('cod_int_new')) {
                                            radioGroup.findParentByType('fieldset').remove(txtFld);
                                        }
                                        if (Ext.getCmp('int_coll')) {
                                            radioGroup.findParentByType('fieldset').remove(interventiCollegatiCombo);
                                        } 
                                        if (Ext.getCmp('tabPanel')){
                                            Ext.getCmp('tabPanel').destroy()
                                        }
                                        caricaButton.hide();
                                        eseguiButton.hide();
                                        break;
                                    }
                                    radioGroup.findParentByType('fieldset').doLayout();

                                }
                            }
                        }
                    }]
            };
            var	itemsForm = [radioGroup]

            var form = new Ext.FormPanel({
                id: formName,
                monitorValid: true,
                fileUpload: true,
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
                buttons: [
    <% if (!utente.getRuolo().equals("A") && !utente.getRuolo().equals("B")) {%>
                        caricaButton,eseguiButton,
    <% }%>
                        {
                            id: 'reset',
                            text: "<%=testiPortale.get("bottone_reset")%>",
                            handler: function(){
                                form.getForm().reset();
                                if (Ext.getCmp('tabPanel')){
                                   Ext.getCmp('tabPanel').destroy()
                                }
                                caricaButton.hide();
                                eseguiButton.show();
                                for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                                    Ext.get(dyn_fields_protection[ele]).dom.readOnly = false;
                                }
                                Ext.Ajax.request({
                                    url: 'pulisciSession',
                                    method: 'POST',
                                    params: {},
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
                        }],
                    buttonAlign: 'center',
                    renderTo: form_div
                });

                for (var ele=0;ele < dyn_fields_protection.length;ele++) {
                    Ext.getCmp(dyn_fields_protection[ele]).getEl().dom.setAttribute('readOnly', true);
                }
                
            
                caricaButton.hide();
                eseguiButton.hide();
                tree.render(menu_div);
                tree.selectPath("treepanel/source/<%=set_id%>",'id');

            });
