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
        var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"<%=testiPortale.get("attesa_salvataggio")%>"});

        Ext.QuickTips.init();

        var grid_row = 25;

        var bd = Ext.getBody();

        var formName = 'edit-form';
        
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');

        var table_name = 'notifiche';
        var sort_field = 'data_modifica';

        var id_field = '';
        var group_field = '';
        var expand_field = '';

        var table_name = 'pubblicazione';

        var dyn_fields = [
            {name: 'cod_int'},
            {name: 'tit_int'},
            {name: 'data_modifica'},
            {name: 'note_pubblicazione'}
        ];

        var reader = new Ext.data.JsonReader({
            root:table_name,
            fields: dyn_fields,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount'
        });

var writer = new Ext.data.JsonWriter({
returnJson: true,
writeAllFields: false,
encode: true,
listful: true
});

        var ds = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name}),
            remoteSort: true,
            reader: reader,
            writer: writer,
            sortInfo: {field:sort_field,direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
        });

        var checkboxGrid = new Ext.grid.CheckboxSelectionModel({
        	singleSelect:false,
            listeners: {
                selectionchange: function(sm) {
                    if (sm.getCount()) {
                        if (sm.getCount() > 0) {
                            Ext.getCmp("compare-button").enable();
                        } else {
                            Ext.getCmp("compare-button").disable();
                        }
                    } else {
                        Ext.getCmp("compare-button").disable();
                    }
                }
            }
        });

	    var fm = Ext.form;

var chkColumn = new Ext.grid.CheckColumn({
header: "<%=testiPortale.get(nomePagina + "_header_grid_pubblica")%>",
dataIndex: 'pubblicazione',
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            var record = grid.store.getAt(rowIndex);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
            
        	var rowSelected = false;
        	for(var i = 0, len = grid.getStore().getTotalCount(); i < len; i++) {
	            var buffer = grid.store.getAt(i);
	            if (buffer.get(this.dataIndex)) {
	            	rowSelected = true;
	            	break;
	            }
        	}

            if (rowSelected) {
                Ext.getCmp("compare-button").enable();
            } else {
                Ext.getCmp("compare-button").disable();
            }
            
            return false; // Cancel row selection.
        } else {
            return Ext.grid.ActionColumn.superclass.processEvent.apply(this, arguments);
        }
    }});


        var colModel = new Ext.grid.ColumnModel([
            {
                header: "<%=testiPortale.get(nomePagina + "_header_grid_cod_int")%>",
                width: 90,
                sortable: true,
                dataIndex: 'cod_int'
            },
            {
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_int")%>",
                renderer: renderTpl,
                width: 480,
                sortable: true,
                dataIndex: 'tit_int'
            },
            {
                header: "<%=testiPortale.get(nomePagina + "_header_grid_data_modifica")%>",
                width: 150,
                sortable: true,
                renderer: renderTpl,
                dataIndex: 'data_modifica'
            },
            {
            	id:'note_pubblicazione',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_note_pubblicazione")%>",
                width: 150,
                editor: new fm.TextArea({
                    allowBlank: true
                }),
                dataIndex: 'note_pubblicazione'
            },chkColumn
        ]);

        ds.load({params: {start:0, size:grid_row}});

        ds.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        })


        var search = new Ext.ux.form.SearchField({
            store: ds,
            width:320,
            paging: true,
            paging_size: grid_row
        });

        var submitForm = function (p) {
             myMask.show();
             Ext.Ajax.request({
                url: 'aggiorna',
                method: 'POST',
                params: {'table_name':table_name,'dati':Ext.util.JSON.encode(p)},
                success: function(response,request) {
                    try {o = Ext.decode(response.responseText);}
                    catch(e) {
                        this.showError(response.responseText);
                        return;
                    }
                    if(!o.success) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }else {
                        Ext.wego.msg('Status', Ext.util.JSON.decode(response.responseText).success);
                        ds.load({params: {start:0, size:grid_row}});
                        Ext.getCmp("compare-button").disable();
                    }
                },
                failure: function(response,request) {
                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                }
            });
            myMask.hide();
        }



        var txtFldNoteGenerali = new Ext.form.TextArea({
                id: 'note_generali_pubblicazione',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_note_generali_pubblicazione")%>",
                name: 'note_generali_pubblicazione',
                maxLength: 500,
                allowBlank: true,
                labelStyle: 'font-weight:bold;',
                enableKeyEvents: true
        });
        
        var buttonClearNoteGenerali = new Ext.Button({
                id: 'reset-note-generali-button',
                text: "<%=testiPortale.get("bottone_reset")%>",
                disabled:false,
                border: true,
                handler: function(){
                	if (txtFldNoteGenerali.getValue()) {
					Ext.MessageBox.confirm("<%=testiPortale.get(nomePagina + "_field_form_note_generali_pubblicazione_conferma_reset_titolo")%>", "<%=testiPortale.get(nomePagina + "_field_form_note_generali_pubblicazione_conferma_reset_msg")%>",
						function(e) {
							if (e=='yes'){
			                	txtFldNoteGenerali.setValue('');
			                }
			            });
			        }
                }
        });

        var	itemsForm = [txtFldNoteGenerali];
        var	buttonsForm = [buttonClearNoteGenerali];


        var form = new Ext.FormPanel({
            id: formName,
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
            title:"<%=testiPortale.get(nomePagina + "_header_grid_pubblicazione")%>",
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
            buttons: buttonsForm,
            buttonAlign: 'center',
                renderTo: form_div
            });









        var grid = new Ext.grid.EditorGridPanel({
            id:'gridStandard',
            title: "",
            frame:true,
            ds: ds,
            cm: colModel,
            sm: checkboxGrid,
            buttonAlign: 'center',
            clicksToEdit: 1,
            autoScroll:true,
            autoExpandColumn: expand_field,
            height:600,
            singleSelect:false,
            viewConfig: {
                    forceFit:true
            },
            bbar: new Ext.PagingToolbar({
                store: ds,
                pageSize:grid_row,
                displayInfo:true,
                items:[search]
            }),
            buttons: [{
                id: 'compare-button',
                text: "<%=testiPortale.get("bottone_save")%>",
                disabled:true,
                handler: function(){
                
                	var noteGenerali = txtFldNoteGenerali.getValue();
                
                	grid.getStore().save();
                	
                   //var selections = grid.getSelectionModel().getSelections();

                   var interventi = new Array();
                   
                   /*
                   for (i=0;i < selections.length; i++) {
                     var myObj = new Object();
                     myObj.codInt=selections[i].data.cod_int;
                     myObj.notePublicazione=noteGenerali.concat('\n\n').concat(selections[i].data.note_pubblicazione);
                     interventi[i]=myObj;
                   }
                   */

					var index = 0;
		        	for(var i = 0, len = grid.getStore().getTotalCount(); i < len; i++) {
			            var buffer = grid.store.getAt(i);
			            if (buffer.get('pubblicazione')) {
		                     var myObj = new Object();
		                     myObj.codInt=buffer.get('cod_int');
		                     myObj.notePubblicazione=buffer.get('note_pubblicazione');
		                     interventi[index]=myObj;
		                     index++;
			            }
		        	}
                   
                   var objSubmit=new Object();
                   objSubmit.noteGenerali=noteGenerali;
                   objSubmit.interventi=interventi;
                   submitForm(objSubmit);
                }
            }],
            border: true,
            renderTo: grid_div
        });

            tree.render(menu_div);
            tree.selectPath("treepanel/source/<%=set_id%>",'id');

        });

