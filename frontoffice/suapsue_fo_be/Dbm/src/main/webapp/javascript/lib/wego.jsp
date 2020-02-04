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
<%@page import="java.util.Properties"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.HashMap"%>
<%@page import="it.people.dbm.dao.DbmDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            ServletContext context = getServletContext();
            String basePath = context.getInitParameter("baseUrl");
            HashMap testiPortale = (HashMap) session.getAttribute("testiPortale");
            String nomePagina = request.getParameter("nomePagina");
            DbmDao dbmDao = new DbmDao();
            URL myURL = application.getResource("/WEB-INF/release.properties");
            InputStream in = myURL.openStream();
            Properties p = new Properties();
            p.load(in);
            String release = p.getProperty("release");
            String releaseDate = p.getProperty("release_date");
%>

    Ext.wego = function(){
       
        var msgCt;

        function createBox(t, s){
            return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
        }
        return {
            msg : function(title, format){
                if(!msgCt){
                    msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
                }
                msgCt.alignTo(document, 't-t');
                var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
                var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
                m.slideIn('t').pause(1).ghost("t", {remove:true});
            },

            init : function(){
                /*
            var t = Ext.get('exttheme');
            if(!t){ // run locally?
                return;
            }
            var theme = Cookies.get('exttheme') || 'aero';
            if(theme){
                t.dom.value = theme;
                Ext.getBody().addClass('x-'+theme);
            }
            t.on('change', function(){
                Cookies.set('exttheme', t.getValue());
                setTimeout(function(){
                    window.location.reload();
                }, 250);
            });*/

                var lb = Ext.get('lib-bar');
                if(lb){
                    lb.show();
                }
            }
        };
    }();

   function trim(str) {
	return (str+'').replace(/^\s+|\s+$/g,"");
    }
    
    function renderTpl(value, p, record){
        p.attr = 'ext:qtip="'+value.replace( /\"/g, "'" ).replace(/\n/g,"<br/>")+'" ext:qtitle="'+this.header+'"';
        return value;
    }
    Ext.getUrlParam = function(param) {
        var params = Ext.urlDecode(location.search.substring(1));
        return param ? params[param] : params;
    };
    var table_name_notifica="";

    var msgForm = new Ext.FormPanel({
        id: 'msgForm',
        monitorValid: true,
        formLayout: true,
        labelWidth: 120,
        defaults: {anchor:'98%', border:false},
        defaultType: 'textarea',
        frame: true,
        bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
        border: false,
        style: {
            "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
        },
        monitorValid: true,
        items: [{
                id: 'textMsg',
                fieldLabel: "<%=testiPortale.get("testo_per_notifica")%>",
                name: 'textMsg',
                xtype:'textarea',
                maxLength: 64000,
                height: 100,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{xtype: 'textfield', name: 'azione', value: '', hidden: true}],
        buttons: [{
                id: 'confNotifica',
                text: "<%=testiPortale.get("button_invia_notifica")%>",
                disabled:false,
                formBind:true,
                handler: function(){
                    msgForm.getForm().submit({url:'scriviNotifica?table_name='+table_name_notifica,
                        waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                        success: function(result,request) {
                            Ext.wego.msg('Status', Ext.util.JSON.decode(request.response.responseText).success);
                            msgForm.getForm().reset();
                            winDisplayNotifica.hide();
                        },
                        failure:function(result,request) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                        }
                    })
                }
            },
            {
                id: 'annNotifica',
                text: "<%=testiPortale.get("button_abbandona")%>",
                disabled:false,
                handler: function(){
                    msgForm.getForm().reset();
                    winDisplayNotifica.hide();
                }
            }],
        buttonAlign: 'center'
    });
    var winDisplayNotifica = new Ext.Window({
        title:"<%=testiPortale.get("header_form_invio_notifica")%>",
        closable:false,
        closeAction:'hide',
        width:500,
        height:200,
        border:false,
        maximizable: false,
        plain:true,
        modal:true,
        items: [msgForm]
    });

    var masker=null;

    Ext.onReady(function(){
        Ext.Ajax.timeout = 90000;  
        masker= new Ext.LoadMask(Ext.getBody(), {msg:'Attendere...'});

        Ext.Ajax.on('requestcomplete', function(conn, response, options) {
            if (response.status && response.status != 200) {
                Ext.Msg.show({
                    title:'<div align="center">Attenzione!</div>',
                    msg:"La sessione è scaduta. Effettua nuovamente il login.",
                    height:200,
                    width:200,
                    closable:false,
                    buttons:Ext.Msg.OK,
                    fn: function(btn){
                        var redirect = '<%= basePath%>' + 'index.jsp';
                        window.location = redirect;
                    }
                });
            } else {
                try {
                    Ext.util.JSON.decode(response.responseText);
                } catch (err) {
                    Ext.Msg.show({
                        title:'<div align="center">Attenzione!</div>',
                        msg:"La sessione è scaduta. Effettua nuovamente il login.",
                        height:200,
                        width:200,
                        closable:false,
                        buttons:Ext.Msg.OK,
                        fn: function(btn){
                            var redirect = '<%= basePath%>' + 'index.jsp';
                            window.location = redirect;
                        }
                    });
                }
            }
        });
        Ext.form.Field.prototype.msgTarget = 'under';

        var winDisplayHelp = new Ext.Window({
            title: '<%=testiPortale.get("titolo_window_display_help")%>',
            closable:true,
            closeAction:'hide',
            width:900,
            height:500,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [{
                    id: 'des_help',
                    fieldLabel: '',
                    name: 'des_help',
                    xtype:'textarea',
                    height: 450,
                    width: 850
                }],
            buttons: [{
                    text: '<%=testiPortale.get("bottone_close")%>',
                    handler: function(){winDisplayHelp.hide();}
                }],
            display: function(){
                this.show();
            }
        });
        var help= new Ext.Toolbar({
            width: '95%',
            renderTo: 'header-dx',
            items: [{
                    xtype: 'button',
                    text: 'Help',
                    listeners: {
                        'click': function() {
                            Ext.Ajax.request({
                                url: 'leggiHelpOnLine',
                                method: 'POST',
                                params: {'help':'<%=nomePagina%>'},
                                success: function(response,request) {
                                    try {o = Ext.decode(response.responseText);}
                                    catch(e) {
                                        this.showError(response.responseText);
                                        return;
                                    }
                                    if(!o.success) {
                                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                    }else {
                                        winDisplayHelp.display();
                                        winDisplayHelp.items.items[0].setValue(Ext.util.JSON.decode(response.responseText).success.help);
                                    }
                                },
                                failure: function(response,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                                }
                            });
                        }
                    }
                },
                {
                    xtype: 'button',
                    text: 'Info',
                    listeners: {
                        'click': function() {
                            var msg = 'Versione: <%= release%> <br/>Data di rilascio: <%= releaseDate%>';
                            Ext.wego.msg('Info', msg);
                        }
                    }
                }]
        });
    });
    var grid_row_popup = 25;
    //Comuni
    var sort_field_comuni = 'cod_com';
    var expand_field_comuni = '';
    var table_name_comuni = 'comuni';
    var id_field_comuni = '';
    var dyn_fields_comuni = [
        {name: 'cod_com'},
        {name: 'des_ente'}];
    var readerComuni = new Ext.data.JsonReader({
        root:table_name_comuni,
        fields: dyn_fields_comuni,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_comuni
    });

    var dsComuni = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_comuni}),
        sortInfo: {field:sort_field_comuni,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerComuni
    });

    dsComuni.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelComuni = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winComuni.lookupFields[0]).setValueCustom(rec.data.cod_com);
                Ext.getCmp(winComuni.lookupFields[1]).setValue(rec.data.des_ente);
                winComuni.hide();
            }
        }
    });

    var colModelComuni = new Ext.grid.ColumnModel([
        {
            id:'cod_com_search',
            header: '<%=testiPortale.get("header_cod_com")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_com'
        },
        {
            id:'des_ente_search',
            header: '<%=testiPortale.get("header_des_ente")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_ente'
        }
    ]);
    var searchComuni = new Ext.ux.form.SearchField({
        store: dsComuni,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });

    var gridComuni = new Ext.grid.GridPanel({
        id:'grid_comuni',
        frame:true,
        ds: dsComuni,
        cm: colModelComuni,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelComuni,
        oadMask: true,
        autoExpandColumn: expand_field_comuni,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsComuni,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchComuni]
        }),
        border: true
    });
    var winComuni = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_comuni")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridComuni],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winComuni.hide();}
            }],
        display: function(){
            dsComuni.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_com','des_ente']
    });
    //Interventi
    var sort_field_interventi = 'cod_int';
    var expand_field_interventi = '';
    var table_name_interventi = 'interventi';
    var id_field_interventi = '';
    var dyn_fields_interventi = [
        {name: 'cod_int'},
        {name: 'tit_int'}];
    var readerInterventi = new Ext.data.JsonReader({
        root:table_name_interventi,
        fields: dyn_fields_interventi,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_interventi
    });


    var dsInterventi = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: 'popUp?table_name='+table_name_interventi}),
        sortInfo: {field:sort_field_interventi,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerInterventi
    });

    dsInterventi.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelInterventi = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winInterventi.lookupFields[0]).setValueCustom(rec.data.cod_int);
                Ext.getCmp(winInterventi.lookupFields[1]).setValue(rec.data.tit_int);
                winInterventi.hide();
            }
        }
    });

    var colModelInterventi = new Ext.grid.ColumnModel([
        {
            id:'cod_int_search',
            header: '<%=testiPortale.get("header_cod_int")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_int'
        },
        {
            id:'tit_int_search',
            header: '<%=testiPortale.get("header_tit_int")%>',
            width: 850,
            sortable: true,
            dataIndex: 'tit_int'
        }
    ]);
    var searchInterventi = new Ext.ux.form.SearchField({
        store: dsInterventi,
        width:320,
        paging: true,
        paging_size: grid_row_popup

    });
    var gridInterventi = new Ext.grid.GridPanel({
        id:'grid_interventi',
        frame:true,
        ds: dsInterventi,
        cm: colModelInterventi,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelInterventi,
        oadMask: true,
        autoExpandColumn: expand_field_interventi,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsInterventi,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchInterventi]
        }),
        border: true
    });
    var winInterventi = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_interventi")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridInterventi],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winInterventi.hide();}
            }],
        display: function(){
            dsInterventi.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_int','tit_int']
    });
    
    //Interventi Totali
    var sort_field_interventi_totali = 'cod_int';
    var expand_field_interventi_totali = '';
    var table_name_interventi_totali = 'interventi_totali';
    var id_field_interventi_totali = '';
    var dyn_fields_interventi_totali = [
        {name: 'cod_int'},
        {name: 'tit_int'}];
    var readerInterventi_totali = new Ext.data.JsonReader({
        root:table_name_interventi_totali,
        fields: dyn_fields_interventi_totali,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_interventi_totali
    });


    var dsInterventi_totali = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: 'popUp?table_name='+table_name_interventi_totali}),
        sortInfo: {field:sort_field_interventi_totali,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerInterventi_totali
    });

    dsInterventi_totali.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelInterventi_totali = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winInterventi_totali.lookupFields[0]).setValueCustom(rec.data.cod_int);
                Ext.getCmp(winInterventi_totali.lookupFields[1]).setValue(rec.data.tit_int);
                winInterventi_totali.hide();
            }
        }
    });

    var colModelInterventi_totali = new Ext.grid.ColumnModel([
        {
            id:'cod_int_search',
            header: '<%=testiPortale.get("header_cod_int")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_int'
        },
        {
            id:'tit_int_search',
            header: '<%=testiPortale.get("header_tit_int")%>',
            width: 850,
            sortable: true,
            dataIndex: 'tit_int'
        }
    ]);
    var searchInterventi_totali = new Ext.ux.form.SearchField({
        store: dsInterventi_totali,
        width:320,
        paging: true,
        paging_size: grid_row_popup

    });
    var gridInterventi_totali = new Ext.grid.GridPanel({
        id:'grid_interventi_totali',
        frame:true,
        ds: dsInterventi_totali,
        cm: colModelInterventi_totali,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelInterventi_totali,
        oadMask: true,
        autoExpandColumn: expand_field_interventi_totali,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsInterventi_totali,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchInterventi_totali]
        }),
        border: true
    });
    var winInterventi_totali = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_interventi")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridInterventi_totali],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winInterventi_totali.hide();}
            }],
        display: function(){
            dsInterventi_totali.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_int','tit_int']
    });
    
    
    //InterventiCollegati
    var sort_field_interventi_collegati = 'cod_int';
    var expand_field_interventi_collegati = '';
    var table_name_interventi_collegati = 'interventi_collegati';
    var id_field_interventi_collegati = '';
    var dyn_fields_interventi_collegati = [
        {name: 'cod_int'},
        {name: 'tit_int'}];
    var readerInterventiCollegati = new Ext.data.JsonReader({
        root:table_name_interventi_collegati,
        fields: dyn_fields_interventi_collegati,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_interventi_collegati
    });


    var dsInterventiCollegati = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_interventi_collegati}),
        sortInfo: {field:sort_field_interventi_collegati,direction:'DESC'},
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
    var searchSelectionModelInterventiCollegati = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winInterventiCollegati.lookupFields[0]).setValue(rec.data.cod_int);
                Ext.getCmp(winInterventiCollegati.lookupFields[1]).setValue(rec.data.tit_int);
                winInterventiCollegati.hide();
            }
        }
    });

    var colModelInterventiCollegati = new Ext.grid.ColumnModel([
        {
            id:'cod_int_collegati_search',
            header: '<%=testiPortale.get("header_cod_int")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_int'
        },
        {
            id:'tit_int_collegati_search',
            header: '<%=testiPortale.get("header_tit_int")%>',
            width: 850,
            sortable: true,
            dataIndex: 'tit_int'
        }
    ]);
    var searchInterventiCollegati = new Ext.ux.form.SearchField({
        store: dsInterventiCollegati,
        width:320,
        paging: true,
        paging_size: grid_row_popup

    });
    var gridInterventiCollegati = new Ext.grid.GridPanel({
        id:'grid_interventi_collegati',
        frame:true,
        ds: dsInterventiCollegati,
        cm: colModelInterventiCollegati,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelInterventiCollegati,
        oadMask: true,
        autoExpandColumn: expand_field_interventi_collegati,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsInterventiCollegati,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchInterventiCollegati]
        }),
        border: true
    });
    var winInterventiCollegati = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_interventi")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridInterventiCollegati],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winInterventiCollegati.hide();}
            }],
        display: function(){
            dsInterventiCollegati.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_int','tit_int']
    });
    //Testo condizioni
    var sort_field_condizioni = 'cod_cond';
    var expand_field_condizioni = '';
    var table_name_condizioni = 'testo_condizioni';
    var id_field_condizioni = '';
    var dyn_fields_condizioni = [
        {name: 'cod_cond'},
        {name: 'testo_cond'}];
    var readerCondizioni = new Ext.data.JsonReader({
        root:table_name_condizioni,
        fields: dyn_fields_condizioni,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_condizioni
    });


    var dsCondizioni = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_condizioni}),
        sortInfo: {field:sort_field_condizioni,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerCondizioni
    });

    dsCondizioni.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelCondizioni = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winCondizioni.lookupFields[0]).setValue(rec.data.cod_cond);
                Ext.getCmp(winCondizioni.lookupFields[1]).setValue(rec.data.testo_cond);
                winCondizioni.hide();
            }
        }
    });

    var colModelCondizioni = new Ext.grid.ColumnModel([
        {
            id:'cod_cond_search',
            header: '<%=testiPortale.get("header_cod_cond")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_cond'
        },
        {
            id:'testo_cond_search',
            header: '<%=testiPortale.get("header_testo_cond")%>',
            width: 850,
            sortable: true,
            dataIndex: 'testo_cond'
        }
    ]);
    var searchCondizioni = new Ext.ux.form.SearchField({
        store: dsCondizioni,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });

    var gridCondizioni = new Ext.grid.GridPanel({
        id:'grid_condizioni',
        frame:true,
        ds: dsCondizioni,
        cm: colModelCondizioni,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelCondizioni,
        oadMask: true,
        autoExpandColumn: expand_field_condizioni,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsCondizioni,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchCondizioni]
        }),
        border: true
    });
    var winCondizioni = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_testi_condizione")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridCondizioni],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winCondizioni.hide();}
            }],
        display: function(){
            dsCondizioni.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_cond','testo_cond']
    });
    //Documenti
    var sort_field_documenti = 'cod_doc';
    var expand_field_documenti = '';
    var table_name_documenti = 'documenti';
    var id_field_documenti = '';
    var dyn_fields_documenti = [
        {name: 'cod_doc'},
        {name: 'tit_doc'},
        {name: 'href'},
        {name: 'tit_href'}];
    var readerDocumenti = new Ext.data.JsonReader({
        root:table_name_documenti,
        fields: dyn_fields_documenti,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_documenti
    });
    var dsDocumenti = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_documenti}),
        sortInfo: {field:sort_field_documenti,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerDocumenti
    });

    dsDocumenti.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelDocumenti = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winDocumenti.lookupFields[0]).setValue(rec.data.cod_doc);
                Ext.getCmp(winDocumenti.lookupFields[1]).setValue(rec.data.tit_doc);
                winDocumenti.hide();
            }
        }
    });

    var colModelDocumenti = new Ext.grid.ColumnModel([
        {
            id:'cod_doc_search',
            header: '<%=testiPortale.get("header_cod_doc")%>',
            width: 80,
            sortable: true,
            dataIndex: 'cod_doc'
        },
        {
            id:'tit_doc_search',
            header: '<%=testiPortale.get("header_tit_doc")%>',
            width: 400,
            sortable: true,
            dataIndex: 'tit_doc'
        },
        {
            id:'href_search',
            header: '<%=testiPortale.get("header_href")%>',
            width: 80,
            sortable: true,
            dataIndex: 'href'
        },
        {
            id:'tit_href_search',
            header: '<%=testiPortale.get("header_tit_href")%>',
            width: 400,
            sortable: true,
            dataIndex: 'tit_href'
        }
    ]);
    var searchDocumenti = new Ext.ux.form.SearchField({
        store: dsDocumenti,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridDocumenti = new Ext.grid.GridPanel({
        id:'grid_documenti',
        frame:true,
        ds: dsDocumenti,
        cm: colModelDocumenti,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelDocumenti,
        oadMask: true,
        autoExpandColumn: expand_field_documenti,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsDocumenti,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchDocumenti]
        }),
        border: true
    });
    var winDocumenti = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_documenti")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridDocumenti],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winDocumenti.hide();}
            }],
        display: function(){
            dsDocumenti.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_doc','tit_doc']
    });
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
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_aggregazioni}),
        sortInfo: {field:sort_field_aggregazioni,direction:'DESC'},
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
    })
    var searchSelectionModelAggregazioni = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winAggregazioni.lookupFields[0]).setValue(rec.data.tip_aggregazione);
                Ext.getCmp(winAggregazioni.lookupFields[1]).setValue(rec.data.des_aggregazione);
                winAggregazioni.hide();
            }
        }
    });

    var colModelAggregazioni = new Ext.grid.ColumnModel([
        {
            id:'tip_aggregazione_search',
            header: '<%=testiPortale.get("header_tip_aggregazione")%>',
            width: 150,
            sortable: true,
            dataIndex: 'tip_aggregazione'
        },
        {
            id:'des_aggregazione_search',
            header: '<%=testiPortale.get("header_des_aggregazione")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_aggregazione'
        }
    ]);
    var searchAggregazioni = new Ext.ux.form.SearchField({
        store: dsAggregazioni,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridAggregazioni = new Ext.grid.GridPanel({
        id:'grid_aggregazioni',
        frame:true,
        ds: dsAggregazioni,
        cm: colModelAggregazioni,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelAggregazioni,
        oadMask: true,
        autoExpandColumn: expand_field_aggregazioni,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsAggregazioni,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchAggregazioni]
        }),
        border: true
    });
    var winAggregazioni = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_aggregazioni")%>',
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
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winAggregazioni.hide();}
            }],
        display: function(){
            dsAggregazioni.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['tip_aggregazione','des_aggregazione']
    });
    //TipiRif
    var sort_field_tipi_rif = 'cod_tipo_rif';
    var expand_field_tipi_rif = '';
    var table_name_tipi_rif = 'tipi_rif';
    var id_field_tipi_rif = '';
    var dyn_fields_tipi_rif = [
        {name: 'cod_tipo_rif'},
        {name: 'tipo_rif'}];
    var readerTipiRif = new Ext.data.JsonReader({
        root:table_name_tipi_rif,
        fields: dyn_fields_tipi_rif,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_tipi_rif
    });

    var dsTipiRif = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_tipi_rif}),
        sortInfo: {field:sort_field_tipi_rif,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerTipiRif
    });

    dsTipiRif.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelTipiRif = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winTipiRif.lookupFields[0]).setValue(rec.data.cod_tipo_rif);
                Ext.getCmp(winTipiRif.lookupFields[1]).setValue(rec.data.tipo_rif);
                winTipiRif.hide();
            }
        }
    });

    var colModelTipiRif = new Ext.grid.ColumnModel([
        {
            id:'cod_tipo_rif_search',
            header: '<%=testiPortale.get("header_cod_tipo_rif")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_tipo_rif'
        },
        {
            id:'tipo_rif_search',
            header: '<%=testiPortale.get("header_tipo_rif")%>',
            width: 850,
            sortable: true,
            dataIndex: 'tipo_rif'
        }
    ]);
    var searchTipiRif = new Ext.ux.form.SearchField({
        store: dsTipiRif,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridTipiRif = new Ext.grid.GridPanel({
        id:'grid_tipi_rif',
        frame:true,
        ds: dsTipiRif,
        cm: colModelTipiRif,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelTipiRif,
        oadMask: true,
        autoExpandColumn: expand_field_tipi_rif,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsTipiRif,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchTipiRif]
        }),
        border: true
    });
    var winTipiRif = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_tipi_rif")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridTipiRif],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winTipiRif.hide();}
            }],
        display: function(){
            dsTipiRif.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_tipo_rif','tipo_rif']
    });
    //Normative
    var sort_field_normative = 'cod_rif';
    var expand_field_normative = '';
    var table_name_normative = 'normative';
    var id_field_normative = '';
    var dyn_fields_normative = [
        {name: 'cod_rif'},
        {name: 'tit_rif'}];
    var readerNormative = new Ext.data.JsonReader({
        root:table_name_normative,
        fields: dyn_fields_normative,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_normative
    });


    var dsNormative = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_normative}),
        sortInfo: {field:sort_field_normative,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerNormative
    });

    dsNormative.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelNormative = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winNormative.lookupFields[0]).setValue(rec.data.cod_rif);
                Ext.getCmp(winNormative.lookupFields[1]).setValue(rec.data.tit_rif);
                winNormative.hide();
            }
        }
    });

    var colModelNormative = new Ext.grid.ColumnModel([
        {
            id:'cod_rif_search',
            header: '<%=testiPortale.get("header_cod_rif")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_rif'
        },
        {
            id:'tit_rif_search',
            header: '<%=testiPortale.get("header_tit_rif")%>',
            width: 850,
            sortable: true,
            dataIndex: 'tit_rif'
        }
    ]);
    var searchNormative = new Ext.ux.form.SearchField({
        store: dsNormative,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });

    var gridNormative = new Ext.grid.GridPanel({
        id:'grid_normative',
        frame:true,
        ds: dsNormative,
        cm: colModelNormative,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelNormative,
        oadMask: true,
        autoExpandColumn: expand_field_normative,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsNormative,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchNormative]
        }),
        border: true
    });
    var winNormative = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_normative")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridNormative],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winNormative.hide();}
            }],
        display: function(){
            dsNormative.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_rif','tit_rif']
    });
    //Cud
    var sort_field_cud = 'cod_cud';
    var expand_field_cud = '';
    var table_name_cud = 'cud';
    var id_field_cud = '';

    var dyn_fields_cud = [
        {name: 'cod_cud'},
        {name: 'des_cud'}];
    var readerCud = new Ext.data.JsonReader({
        root:table_name_cud,
        fields: dyn_fields_cud,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_cud
    });

    var dsCud = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_cud}),
        sortInfo: {field:sort_field_cud,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerCud
    });

    dsCud.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelCud = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winCud.lookupFields[0]).setValue(rec.data.cod_cud);
                Ext.getCmp(winCud.lookupFields[1]).setValue(rec.data.des_cud);
                winCud.hide();
            }
        }
    });

    var colModelCud = new Ext.grid.ColumnModel([
        {
            id:'cod_cud_search',
            header: '<%=testiPortale.get("header_cod_cud")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_cud'
        },
        {
            id:'des_cud_search',
            header: '<%=testiPortale.get("header_des_cud")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_cud'
        }
    ]);
    var searchCud = new Ext.ux.form.SearchField({
        store: dsCud,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridCud = new Ext.grid.GridPanel({
        id:'grid_cud',
        frame:true,
        ds: dsCud,
        cm: colModelCud,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelCud,
        oadMask: true,
        autoExpandColumn: expand_field_cud,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsCud,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchCud]
        }),
        border: true
    });
    var winCud = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_cud")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridCud],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winCud.hide();}
            }],
        display: function(){
            dsCud.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_cud','des_cud']
    });
    //DocOneri
    var sort_field_doc_oneri = 'cod_doc_onere';
    var expand_field_doc_oneri = '';
    var table_name_doc_oneri = 'oneri_documenti';
    var id_field_doc_oneri = '';

    var dyn_fields_doc_oneri = [
        {name: 'cod_doc_onere'},
        {name: 'des_doc_onere'}];
    var readerDocOneri = new Ext.data.JsonReader({
        root:table_name_doc_oneri,
        fields: dyn_fields_doc_oneri,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_doc_oneri
    });

    var dsDocOneri = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_doc_oneri}),
        sortInfo: {field:sort_field_doc_oneri,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerDocOneri
    });

    dsDocOneri.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelDocOneri = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winDocOneri.lookupFields[0]).setValue(rec.data.cod_doc_onere);
                Ext.getCmp(winDocOneri.lookupFields[1]).setValue(rec.data.des_doc_onere);
                winDocOneri.hide();
            }
        }
    });

    var colModelDocOneri = new Ext.grid.ColumnModel([
        {
            id:'cod_doc_onere_search',
            header: '<%=testiPortale.get("header_cod_doc_onere")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_doc_onere'
        },
        {
            id:'des_cod_onere_search',
            header: '<%=testiPortale.get("header_des_doc_onere")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_doc_onere'
        }
    ]);
    var searchDocOneri = new Ext.ux.form.SearchField({
        store: dsDocOneri,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridDocOneri = new Ext.grid.GridPanel({
        id:'grid_doc_oneri',
        frame:true,
        ds: dsDocOneri,
        cm: colModelDocOneri,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelDocOneri,
        oadMask: true,
        autoExpandColumn: expand_field_doc_oneri,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsDocOneri,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchDocOneri]
        }),
        border: true
    });
    var winDocOneri = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_doc_oneri")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridDocOneri],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winDocOneri.hide();}
            }],
        display: function(){
            dsDocOneri.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_doc_onere','des_doc_onere']
    });
    //OneriGerarchia
    var sort_field_oneri_gerarchia = 'cod_padre';
    var expand_field_oneri_gerarchia = '';
    var table_name_oneri_gerarchia = 'oneri_gerarchia';
    var id_field_oneri_gerarchia = '';

    var dyn_fields_oneri_gerarchia = [
        {name: 'cod_padre'},
        {name: 'des_gerarchia'}];
    var readerOneriGerarchia = new Ext.data.JsonReader({
        root:table_name_oneri_gerarchia,
        fields: dyn_fields_oneri_gerarchia,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_oneri_gerarchia
    });

    var dsOneriGerarchia = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_oneri_gerarchia}),
        sortInfo: {field:sort_field_oneri_gerarchia,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerOneriGerarchia
    });

    dsOneriGerarchia.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelOneriGerarchia = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winOneriGerarchia.lookupFields[0]).setValue(rec.data.cod_padre);
                Ext.getCmp(winOneriGerarchia.lookupFields[1]).setValue(rec.data.des_gerarchia);
                winOneriGerarchia.hide();
            }
        }
    });

    var colModelOneriGerarchia = new Ext.grid.ColumnModel([
        {
            id:'cod_padre_search',
            header: '<%=testiPortale.get("header_cod_padre")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_padre'
        },
        {
            id:'des_gerarchia_search',
            header: '<%=testiPortale.get("header_des_gerarchia")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_gerarchia'
        }
    ]);
    var searchOneriGerarchia = new Ext.ux.form.SearchField({
        store: dsOneriGerarchia,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridOneriGerarchia = new Ext.grid.GridPanel({
        id:'grid_oneri_gerarchia',
        frame:true,
        ds: dsOneriGerarchia,
        cm: colModelOneriGerarchia,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelOneriGerarchia,
        oadMask: true,
        autoExpandColumn: expand_field_oneri_gerarchia,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsOneriGerarchia,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchOneriGerarchia]
        }),
        border: true
    });
    var winOneriGerarchia = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_oneri_gerarchia")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridOneriGerarchia],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winOneriGerarchia.hide();}
            }],
        display: function(){
            dsOneriGerarchia.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_padre','des_gerarchia']
    });
    var sort_field_oneri = 'cod_oneri';
    var expand_field_oneri = '';
    var table_name_oneri = 'oneri';
    var id_field_oneri = '';
    var dyn_fields_oneri = [
        {name: 'cod_oneri'},
        {name: 'des_oneri'}];
    var readerOneri = new Ext.data.JsonReader({
        root:table_name_oneri,
        fields: dyn_fields_oneri,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_oneri
    });


    var dsOneri = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_oneri}),
        sortInfo: {field:sort_field_oneri,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerOneri
    });

    dsOneri.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelOneri = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winOneri.lookupFields[0]).setValue(rec.data.cod_oneri);
                Ext.getCmp(winOneri.lookupFields[1]).setValue(rec.data.des_oneri);
                winOneri.hide();
            }
        }
    });

    var colModelOneri = new Ext.grid.ColumnModel([
        {
            id:'cod_oneri_search',
            header: '<%=testiPortale.get("header_cod_oneri")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_oneri'
        },
        {
            id:'des_oneri_search',
            header: '<%=testiPortale.get("header_des_oneri")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_oneri'
        }
    ]);
    var searchOneri = new Ext.ux.form.SearchField({
        store: dsOneri,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridOneri = new Ext.grid.GridPanel({
        id:'grid_oneri',
        frame:true,
        ds: dsOneri,
        cm: colModelOneri,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelOneri,
        oadMask: true,
        autoExpandColumn: expand_field_oneri,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsOneri,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchOneri]
        }),
        border: true
    });
    var winOneri = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_oneri")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridOneri],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winOneri.hide();}
            }],
        display: function(){
            dsOneri.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_oneri','des_oneri']
    });
    var sort_field_settori_attivita = 'cod_sett';
    var expand_field_settori_attivita = '';
    var table_name_settori_attivita = 'settori_attivita';
    var id_field_settori_attivita = '';
    var dyn_fields_settori_attivita = [
        {name: 'cod_sett'},
        {name: 'des_sett'}];
    var readerSettoriAttivita = new Ext.data.JsonReader({
        root:table_name_settori_attivita,
        fields: dyn_fields_settori_attivita,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_settori_attivita
    });


    var dsSettoriAttivita = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_settori_attivita}),
        sortInfo: {field:sort_field_settori_attivita,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerSettoriAttivita
    });

    dsSettoriAttivita.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelSettoriAttivita = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winSettoriAttivita.lookupFields[0]).setValue(rec.data.cod_sett);
                Ext.getCmp(winSettoriAttivita.lookupFields[1]).setValue(rec.data.des_sett);
                winSettoriAttivita.hide();
            }
        }
    });

    var colModelSettoriAttivita = new Ext.grid.ColumnModel([
        {
            id:'cod_sett_search',
            header: '<%=testiPortale.get("header_cod_sett")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_sett'
        },
        {
            id:'des_sett_search',
            header: '<%=testiPortale.get("header_des_sett")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_sett'
        }
    ]);
    var searchSettoriAttivita = new Ext.ux.form.SearchField({
        store: dsSettoriAttivita,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridSettoriAttivita = new Ext.grid.GridPanel({
        id:'grid_settori_attivita',
        frame:true,
        ds: dsSettoriAttivita,
        cm: colModelSettoriAttivita,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelSettoriAttivita,
        oadMask: true,
        autoExpandColumn: expand_field_settori_attivita,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsSettoriAttivita,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchSettoriAttivita]
        }),
        border: true
    });
    var winSettoriAttivita = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_settori_attivita")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridSettoriAttivita],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winSettoriAttivita.hide();}
            }],
        display: function(){
            dsSettoriAttivita.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_sett','des_sett']
    });
    //Procedimenti
    var sort_field_procedimenti = 'cod_proc';
    var expand_field_procedimenti = '';
    var table_name_procedimenti = 'procedimenti';
    var id_field_procedimenti = '';
    var dyn_fields_procedimenti = [
        {name: 'cod_proc'},
        {name: 'tit_proc'}];
    var readerProcedimenti = new Ext.data.JsonReader({
        root:table_name_procedimenti,
        fields: dyn_fields_procedimenti,
        baseParams: {'cod_proc':''},
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_procedimenti
    });


    var dsProcedimenti = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_procedimenti}),
        sortInfo: {field:sort_field_procedimenti,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerProcedimenti
    });

    dsProcedimenti.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelProcedimenti = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winProcedimenti.lookupFields[0]).setValueCustom(rec.data.cod_proc);
                Ext.getCmp(winProcedimenti.lookupFields[1]).setValue(rec.data.tit_proc);
                winProcedimenti.hide();
            }
        }
    });

    var colModelProcedimenti = new Ext.grid.ColumnModel([
        {
            id:'cod_proc_search',
            header: '<%=testiPortale.get("header_cod_proc")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_proc'
        },
        {
            id:'tit_proc_search',
            header: '<%=testiPortale.get("header_tit_proc")%>',
            width: 850,
            sortable: true,
            dataIndex: 'tit_proc'
        }
    ]);
    var searchProcedimenti = new Ext.ux.form.SearchField({
        store: dsProcedimenti,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });

    var gridProcedimenti = new Ext.grid.GridPanel({
        id:'grid_procedimenti',
        frame:true,
        ds: dsProcedimenti,
        cm: colModelProcedimenti,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelProcedimenti,
        oadMask: true,
        autoExpandColumn: expand_field_procedimenti,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsProcedimenti,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchProcedimenti]
        }),
        border: true
    });
    var winProcedimenti = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_procedimenti")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridProcedimenti],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winProcedimenti.hide();}
            }],
        display: function(){
            dsProcedimenti.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_proc','tit_proc']
    });
    //Operazioni
    var sort_field_operazioni = 'cod_ope';
    var expand_field_operazioni = '';
    var table_name_operazioni = 'operazioni';
    var id_field_operazioni = '';

    var dyn_fields_operazioni = [
        {name: 'cod_ope'},
        {name: 'des_ope'}];
    var readerOperazioni = new Ext.data.JsonReader({
        root:table_name_operazioni,
        fields: dyn_fields_operazioni,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_operazioni
    });

    var dsOperazioni = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_operazioni}),
        sortInfo: {field:sort_field_operazioni,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerOperazioni
    });

    dsOperazioni.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelOperazioni = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winOperazioni.lookupFields[0]).setValue(rec.data.cod_ope);
                Ext.getCmp(winOperazioni.lookupFields[1]).setValue(rec.data.des_ope);
                winOperazioni.hide();
            }
        }
    });

    var colModelOperazioni = new Ext.grid.ColumnModel([
        {
            id:'cod_ope_search',
            header: '<%=testiPortale.get("header_cod_ope")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_ope'
        },
        {
            id:'des_ope_search',
            header: '<%=testiPortale.get("header_des_ope")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_ope'
        }
    ]);
    var searchOperazioni = new Ext.ux.form.SearchField({
        store: dsOperazioni,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridOperazioni = new Ext.grid.GridPanel({
        id:'grid_operazioni',
        frame:true,
        ds: dsOperazioni,
        cm: colModelOperazioni,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelOperazioni,
        oadMask: true,
        autoExpandColumn: expand_field_operazioni,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsOperazioni,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchOperazioni]
        }),
        border: true
    });
    var winOperazioni = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_operazioni")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridOperazioni],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winOperazioni.hide();}
            }],
        display: function(){
            dsOperazioni.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_ope','des_ope']
    });
    //Href
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
        proxy: new Ext.data.HttpProxy({url: 'popUp?table_name='+table_name_href}),
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
                Ext.getCmp(winHref.lookupFields[0]).setValue(rec.data.href);
                Ext.getCmp(winHref.lookupFields[1]).setValue(rec.data.tit_href);
                winHref.hide();
            }
        }
    });

    var colModelHref = new Ext.grid.ColumnModel([
        {
            id:'href_search',
            header: '<%=testiPortale.get("header_href")%>',
            width: 150,
            sortable: true,
            dataIndex: 'href'
        },
        {
            id:'tit_href_search',
            header: '<%=testiPortale.get("header_tit_href")%>',
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
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsHref,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchHref]
        }),
        border: true
    });
    var winHref = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_href")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridHref],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winHref.hide();}
            }],
        display: function(){
            dsHref.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['href','tit_href']
    });
    var sort_field_classi_enti = 'cod_classe_ente';
    var expand_field_classi_enti = '';
    var table_name_classi_enti = 'classi_enti';
    var id_field_classi_enti = '';

    var dyn_fields_classi_enti = [
        {name: 'cod_classe_ente'},
        {name: 'des_classe_ente'}];
    var readerClassiEnti = new Ext.data.JsonReader({
        root:table_name_classi_enti,
        fields: dyn_fields_classi_enti,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_classi_enti
    });

    var dsClassiEnti = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: 'popUp?table_name='+table_name_classi_enti}),
        sortInfo: {field:sort_field_classi_enti,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerClassiEnti
    });

    dsClassiEnti.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelClassiEnti = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winClassiEnti.lookupFields[0]).setValue(rec.data.cod_classe_ente);
                Ext.getCmp(winClassiEnti.lookupFields[1]).setValue(rec.data.des_classe_ente);
                winClassiEnti.hide();
            }
        }
    });

    var colModelClassiEnti = new Ext.grid.ColumnModel([
        {
            id:'cod_classe_ente_search',
            header: '<%=testiPortale.get("header_cod_classe_ente")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_classe_ente'
        },
        {
            id:'des_classe_ente_search',
            header: '<%=testiPortale.get("header_des_classe_ente")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_classe_ente'
        }
    ]);
    var searchClassiEnti = new Ext.ux.form.SearchField({
        store: dsClassiEnti,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridClassiEnti = new Ext.grid.GridPanel({
        id:'grid_classi_enti',
        frame:true,
        ds: dsClassiEnti,
        cm: colModelClassiEnti,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelClassiEnti,
        oadMask: true,
        autoExpandColumn: expand_field_classi_enti,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsClassiEnti,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchClassiEnti]
        }),
        border: true
    });
    var winClassiEnti = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_classi_enti")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridClassiEnti],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winClassiEnti.hide();}
            }],
        display: function(){
            dsClassiEnti.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_classe_ente','des_classe_ente']
    });
    var sort_field_enti_comuni = 'cod_ente';
    var expand_field_enti_comuni = '';
    var table_name_enti_comuni = 'enti_comuni';
    var id_field_enti_comuni = '';

    var dyn_fields_enti_comuni = [
        {name: 'cod_ente'},
        {name: 'des_ente'}];
    var readerEntiComuni = new Ext.data.JsonReader({
        root:table_name_enti_comuni,
        fields: dyn_fields_enti_comuni,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_enti_comuni
    });

    var dsEntiComuni = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_enti_comuni}),
        sortInfo: {field:sort_field_enti_comuni,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerEntiComuni
    });

    dsEntiComuni.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelEntiComuni = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winEntiComuni.lookupFields[0]).setValue(rec.data.cod_ente);
                Ext.getCmp(winEntiComuni.lookupFields[1]).setValue(rec.data.des_ente);
                winEntiComuni.hide();
            }
        }
    });

    var colModelEntiComuni = new Ext.grid.ColumnModel([
        {
            id:'cod_ente_search',
            header: '<%=testiPortale.get("header_cod_ente")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_ente'
        },
        {
            id:'des_ente_search',
            header: '<%=testiPortale.get("header_des_ente")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_ente'
        }
    ]);
    var searchEntiComuni = new Ext.ux.form.SearchField({
        store: dsEntiComuni,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridEntiComuni = new Ext.grid.GridPanel({
        id:'grid_enti_comuni',
        frame:true,
        ds: dsEntiComuni,
        cm: colModelEntiComuni,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelEntiComuni,
        oadMask: true,
        autoExpandColumn: expand_field_enti_comuni,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsEntiComuni,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchEntiComuni]
        }),
        border: true
    });
    var winEntiComuni = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_enti_comuni")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridEntiComuni],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winEntiComuni.hide();}
            }],
        display: function(){
            dsEntiComuni.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_ente','des_ente']
    });
    var sort_field_oneri_formula = 'cod_onere_formula';
    var expand_field_oneri_formula = '';
    var table_name_oneri_formula = 'oneri_formula';
    var id_field_oneri_formula = '';

    var dyn_fields_oneri_formula = [
        {name: 'cod_onere_formula'},
        {name: 'des_formula'}];
    var readerOneriFormula = new Ext.data.JsonReader({
        root:table_name_oneri_formula,
        fields: dyn_fields_oneri_formula,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_oneri_formula
    });

    var dsOneriFormula = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:  'popUp?table_name='+table_name_oneri_formula}),
        sortInfo: {field:sort_field_oneri_formula,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerOneriFormula
    });

    dsOneriFormula.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    });
    var searchSelectionModelOneriFormula = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winOneriFormula.lookupFields[0]).setValue(rec.data.cod_onere_formula);
                Ext.getCmp(winOneriFormula.lookupFields[1]).setValue(rec.data.des_formula);
                winOneriFormula.hide();
            }
        }
    });

    var colModelOneriFormula = new Ext.grid.ColumnModel([
        {
            id:'cod_onere_formula_search',
            header: '<%=testiPortale.get("header_cod_onere_formula")%>',

            width: 150,
            sortable: true,
            dataIndex: 'cod_onere_formula'
        },
        {
            id:'des_formula_search',
            header: '<%=testiPortale.get("header_des_formula")%>',

            width: 850,
            sortable: true,
            dataIndex: 'des_formula'
        }
    ]);
    var searchOneriFormula = new Ext.ux.form.SearchField({
        store: dsOneriFormula,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridOneriFormula = new Ext.grid.GridPanel({
        id:'grid_oneri_formula',
        frame:true,
        ds: dsOneriFormula,
        cm: colModelOneriFormula,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelOneriFormula,
        oadMask: true,
        autoExpandColumn: expand_field_oneri_formula,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsOneriFormula,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchOneriFormula]
        }),
        border: true
    });
    var winOneriFormula = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_oneri_formula")%>',

        closable:true,
        closeAction:'hide',
        width:900,
        height:500,


        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridOneriFormula],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winOneriFormula.hide();}
            }],
        display: function(){
            dsOneriFormula.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_onere_formula','des_formula']
    });
    var sort_field_destinatari = 'cod_dest';
    var expand_field_destinatari = '';
    var table_name_destinatari = 'destinatari';
    var id_field_destinatari = '';
    var dyn_fields_destinatari = [
        {name: 'cod_dest'},
        {name: 'intestazione'}];
    var readerDestinatari = new Ext.data.JsonReader({
        root:table_name_destinatari,
        fields: dyn_fields_destinatari,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_destinatari
    });


    var dsDestinatari = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_destinatari}),
        sortInfo: {field:sort_field_destinatari,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerDestinatari
    });

    dsDestinatari.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelDestinatari = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winDestinatari.lookupFields[0]).setValue(rec.data.cod_dest);
                Ext.getCmp(winDestinatari.lookupFields[1]).setValue(rec.data.intestazione);
                winDestinatari.hide();
            }
        }
    });

    var colModelDestinatari = new Ext.grid.ColumnModel([
        {
            id:'cod_dest_search',
            header: '<%=testiPortale.get("header_cod_dest")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_dest'
        },
        {
            id:'intestazione_search',
            header: '<%=testiPortale.get("header_intestazione")%>',
            width: 850,
            sortable: true,
            dataIndex: 'intestazione'
        }
    ]);
    var searchDestinatari = new Ext.ux.form.SearchField({
        store: dsDestinatari,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridDestinatari = new Ext.grid.GridPanel({
        id:'grid_destinatari',
        frame:true,
        ds: dsDestinatari,
        cm: colModelDestinatari,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelDestinatari,
        oadMask: true,
        autoExpandColumn: expand_field_destinatari,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsDestinatari,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchDestinatari]
        }),
        border: true
    });
    var winDestinatari = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_destinatari")%>',
        closable:true,
        closeAction:'hide',
        width: 900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridDestinatari],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winDestinatari.hide();}
            }],
        display: function(){
            dsDestinatari.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_dest','intestazione']
    });
    var sort_field_sportelli = 'cod_sport';
    var expand_field_sportelli = '';
    var table_name_sportelli = 'sportelli';
    var id_field_sportelli = '';
    var dyn_fields_sportelli = [
        {name: 'cod_sport'},
        {name: 'des_sport'}];
    var readerSportelli = new Ext.data.JsonReader({
        root:table_name_sportelli,
        fields: dyn_fields_sportelli,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_sportelli
    });


    var dsSportelli = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_sportelli}),
        sortInfo: {field:sort_field_sportelli,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerSportelli
    });

    dsSportelli.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelSportelli = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winSportelli.lookupFields[0]).setValue(rec.data.cod_sport);
                Ext.getCmp(winSportelli.lookupFields[1]).setValue(rec.data.des_sport);
                winSportelli.hide();
            }
        }
    });

    var colModelSportelli = new Ext.grid.ColumnModel([
        {
            id:'cod_sport_search',
            header: '<%=testiPortale.get("header_cod_sport")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_sport'
        },
        {
            id:'des_sport_search',
            header: '<%=testiPortale.get("header_des_sport")%>',
            width: 850,
            sortable: true,
            dataIndex: 'des_sport'
        }
    ]);
    var searchSportelli = new Ext.ux.form.SearchField({
        store: dsSportelli,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridSportelli = new Ext.grid.GridPanel({
        id:'grid_sportelli',
        frame:true,
        ds: dsSportelli,
        cm: colModelSportelli,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelSportelli,
        oadMask: true,
        autoExpandColumn: expand_field_sportelli,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsSportelli,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchSportelli]
        }),
        border: true
    });
    var winSportelli = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_sportelli")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridSportelli],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winSportelli.hide();}
            }],
        display: function(){
            dsSportelli.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_sport','des_sport']
    });

    var sort_field_servizi = 'cod_servizio';
    var expand_field_servizi = '';
    var table_name_servizi = 'servizi';
    var id_field_servizi = '';
    var dyn_fields_servizi = [
        {name: 'cod_servizio'},
        {name: 'nome_servizio'}];
    var readerServizi = new Ext.data.JsonReader({
        root:table_name_servizi,
        fields: dyn_fields_servizi,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_servizi
    });


    var dsServizi = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_servizi}),
        sortInfo: {field:sort_field_servizi,direction:'DESC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerServizi
    });

    dsServizi.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelServizi = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winServizi.lookupFields[0]).setValue(rec.data.cod_servizio);
                Ext.getCmp(winServizi.lookupFields[1]).setValue(rec.data.nome_servizio);
                winServizi.hide();
            }
        }
    });

    var colModelServizi = new Ext.grid.ColumnModel([
        {
            id:'cod_servizio_search',
            header: '<%=testiPortale.get("header_cod_servizio")%>',
            width: 150,
            sortable: true,
            dataIndex: 'cod_servizio'
        },
        {
            id:'nome_servizio_search',
            header: '<%=testiPortale.get("header_nome_servizio")%>',
            width: 850,
            sortable: true,
            dataIndex: 'nome_servizio'
        }
    ]);
    var searchServizi = new Ext.ux.form.SearchField({
        store: dsServizi,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });
    var gridServizi = new Ext.grid.GridPanel({
        id:'grid_servizi',
        frame:true,
        ds: dsServizi,
        cm: colModelServizi,
        buttonAlign: 'center',
        autoScroll:true,
        forceLayout:true,
        sm:searchSelectionModelServizi,
        oadMask: true,
        autoExpandColumn: expand_field_servizi,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsServizi,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchServizi]
        }),
        border: true
    });
    var winServizi = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_servizi")%>',
        closable:true,
        closeAction:'hide',
        width:900,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridServizi],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winServizi.hide();}
            }],
        display: function(){
            dsServizi.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['cod_servizo','nome_servizio']
    });
    //Custom Component
    Ext.form.TextFieldRemoteVal = function(config){
        Ext.form.TextFieldRemoteVal.superclass.constructor.call(this, config);
        if( this.urlRemoteVal ) {
            if( this.remoteValidation == 'onValidate' ) {
                this.on('valid', this.startRemoteVal.createDelegate(this));
            }else if( this.remoteValidation == 'onBlur' ) {
                this.on('blur', this.startRemoteVal.createDelegate(this));
            }
        }
    };

    Ext.extend(Ext.form.TextFieldRemoteVal, Ext.form.TextField, {
        remoteValidation: null, /* 'onValidate' or 'onBlur' */
        urlRemoteVal: null,
        timeout: 30,
        method: 'POST',
        badServerRespText: 'Error: bad server response during validation',
        badComText: 'Error: validation unavailable',

        // redefinition
        onRender : function(ct){
            Ext.form.TextFieldRemoteVal.superclass.onRender.call(this, ct);

            this.remoteCheckIcon = ct.createChild({tav:'div', cls:'x-form-remote-wait'});
            this.remoteCheckIcon.hide();
        },

        // private
        alignRemoteCheckIcon : function(){
            this.remoteCheckIcon.alignTo(this.el, 'tl-tr', [2, 2]);
        },

        // private
        getParams: function() {
            var tfp = (this.name ||this.id)+'='+this.getValue();
            var p = (this.paramsRemoteVal?this.paramsRemoteVal:'');
            if(p){
                if(typeof p == "object")
                    tfp += '&' + Ext.urlEncode(p);
                else if(typeof p == 'string' && p.length)
                    tfp += '&' + p;
            }
            return tfp;
        },

        // public
        startRemoteVal: function() {
            var v = this.getValue();
            // don't start a remote validation if the value doesn't change
            // (getFocus/lostFocus for example)
            if( this.lastValue != v ) {
                this.lastValue = v;
                if( this.transaction ) {
                    this.abort();
                }
                this.alignRemoteCheckIcon();
                this.remoteCheckIcon.show();
                var params = this.getParams();
                this.transaction = Ext.lib.Ajax.request(
                this.method,
                this.urlRemoteVal + (this.method=='GET' ? '?' + params : ''),
                {success: this.successRemoteVal, failure:
                        this.failureRemoteVal, scope: this, timeout: (this.timeout*1000)},
                params);
            }
            // but if remote validation error, show it! (because validateValue reset it)
            else if( !this.isValid ) {
                this.markInvalid(this.currentErrorTxt);
            }
        },

        // public
        abort : function(){
            if(this.transaction){
                Ext.lib.Ajax.abort(this.transaction);
            }
        },

        // private
        successRemoteVal: function(response) {
            this.transaction = null;
            this.remoteCheckIcon.hide();
            var result = this.processResponse(response);
            if(result) {
                if(result.errors) {
                    this.currentErrorTxt = result.errors;
                    this.markInvalid(this.currentErrorTxt);
                    this.isValid = false;
                } else {
                    this.isValid = true;
                }
            }else{
                this.currentErrorTxt = this.badServerRespText;
                this.markInvalid(this.currentErrorTxt);
                this.isValid = false;
            }
        },

        // private
        failureRemoteVal: function(response) {
            this.transaction = null;
            this.remoteCheckIcon.hide();
            this.currentErrorTxt = this.badComText;
            this.markInvalid(this.currentErrorTxt);
            this.isValid = false;
        },

        // private
        processResponse: function(response) {
            return (!response.responseText ? false :
                Ext.decode(response.responseText));
        }

    });

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        //Formulario agenzia entrate
    var sort_field_fae = 'codice_catastale_comune';
    var expand_field_fae = '';
    var table_name_fae = 'formulario_ae';
    var id_field_fae = '';
    var dyn_fields_fae = [
		{name: 'id'},
		{name: 'data_validita'},
		{name: 'codice_ente'},
		{name: 'tipologia_ufficio'},
		{name: 'codice_ufficio'},
		{name: 'tipologia_ente'},
		{name: 'denominazione'},
		{name: 'codice_catastale_comune'},
		{name: 'data_decorrenza'},
		{name: 'ufficio_statale'}
        ];
    var readerFae = new Ext.data.JsonReader({
        root:table_name_fae,
        fields: dyn_fields_fae,
        baseParams: {
            lightWeight:true,
            ext: 'js'
        },
        totalProperty: 'totalCount',
        idProperty: id_field_fae
    });

    var dsFae = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({	url:  'popUp?table_name='+table_name_fae}),
        sortInfo: {field:sort_field_fae,direction:'ASC'},
        defaultParamNames: {
            start: 'start',
            limit: 'size',
            sort: 'sort',
            dir: 'dir'
        },
        remoteSort: true,
        reader: readerFae
    });

    dsFae.on('loadexception', function(event, options, response, error) {
        var json = Ext.decode(response.responseText);
        Ext.Msg.alert('Errore', json.error);
    })
    var searchSelectionModelFae = new Ext.grid.RowSelectionModel({
        singleSelect: true,
        listeners: {
            rowselect: function(sm, row, rec) {
                Ext.getCmp(winFae.lookupFields[0]).setValue(rec.data.codice_ente);
                Ext.getCmp(winFae.lookupFields[1]).setValue(rec.data.tipologia_ufficio);
                Ext.getCmp(winFae.lookupFields[2]).setValue(rec.data.codice_ufficio);
                winFae.hide();
            }
        }
    });

    var colModelFae = new Ext.grid.ColumnModel([
		{
		id:'grid_codice_ente',
		header: "<%=testiPortale.get("header_fae_codice_ente")%>",
		renderer: renderTpl,
		width: 50,
		sortable: true,
		dataIndex: 'codice_ente'
		},
		{
		id:'grid_tipologia_ufficio',
		header: "<%=testiPortale.get("header_fae_tipologia_ufficio")%>",
		width: 50,
		sortable: true,
		dataIndex: 'tipologia_ufficio'
		},
		{
		id:'grid_codice_ufficio',
		header: "<%=testiPortale.get("header_fae_codice_ufficio")%>",
		renderer: renderTpl,
		width: 50,
		sortable: true,
		dataIndex: 'codice_ufficio'
		},
		{
		id:'grid_tipologia_ente',
		header: "<%=testiPortale.get("header_fae_tipologia_ente")%>",
		width: 50,
		sortable: true,
		dataIndex: 'tipologia_ente'
		},
		{
		id:'grid_denominazione',
		header: "<%=testiPortale.get("header_fae_denominazione")%>",
		renderer: renderTpl,
		width: 350,
		sortable: true,
		dataIndex: 'denominazione'
		},
		{
		id:'grid_codice_catastale_comune',
		header: "<%=testiPortale.get("header_fae_codice_catastale_comune")%>",
		width: 50,
		sortable: true,
		dataIndex: 'codice_catastale_comune'
		},
		{
		id:'grid_ufficio_statale',
		header: "<%=testiPortale.get("header_fae_ufficio_statale")%>",
		width: 50,
		sortable: true,
		dataIndex: 'ufficio_statale'
		}
    ]);
    var searchFae = new Ext.ux.form.SearchField({
        store: dsFae,
        width:320,
        paging: true,
        paging_size: grid_row_popup
    });

    var gridFae = new Ext.grid.GridPanel({
        id:'grid_fae',
        frame:true,
        ds: dsFae,
        cm: colModelFae,
        buttonAlign: 'center',
        autoScroll:true,
        sm:searchSelectionModelFae,
        oadMask: true,
        autoExpandColumn: expand_field_fae,
        height:430,
        bbar: new Ext.PagingToolbar({
            store: dsFae,
            pageSize:grid_row_popup,
            displayInfo:true,
            items:[searchFae]
        }),
        border: true
    });
    var winFae = new Ext.Window({
        title: '<%=testiPortale.get("titolo_window_formulario_agenzia_entrate")%>',
        closable:true,
        closeAction:'hide',
        width:700,
        height:500,
        border:false,
        maximizable: true,
        plain:true,
        modal:true,
        items: [gridFae],
        buttons: [{
                text: '<%=testiPortale.get("bottone_close")%>',
                handler: function(){winFae.hide();}
            }],
        display: function(){
            dsFae.reload({params:{start: 0, size:grid_row_popup}});
            this.show();
        },
        lookupFields: ['codice_ente', 'tipologia_ufficio', 'codice_ufficio']
    });
    