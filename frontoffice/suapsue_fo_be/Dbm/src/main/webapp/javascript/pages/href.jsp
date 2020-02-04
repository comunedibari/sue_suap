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
    function trim(str, chars) {
        return ltrim(rtrim(str, chars), chars);
    }

    function ltrim(str, chars) {
        chars = chars || "\\s";
        return str.replace(new RegExp("^[" + chars + "]+", "g"), "");
    }

    function caricaArray(store) {
        dati=[];
        for(i=0;i < store.data.items.length;i++){
            dati.push(store.data.items[i].data);
        }
        return dati;
    }
    function rtrim(str, chars) {
        chars = chars || "\\s";
        return str.replace(new RegExp("[" + chars + "]+$", "g"), "");
    }

    function isGood(x){
       try {
              new RegExp(x);
              return true;
           } catch(e) {
              return "Stringa non formalmente corretta";
           }
    }
    Ext.onReady(function(){

        Ext.QuickTips.init();

        var grid_row = 25;
        var urlScriviSessione = 'registraSessione';

        var bd = Ext.getBody();
        var formName = 'edit-form';
        var form_div = Ext.get('form');
        var grid_div = Ext.get('grid');
        var menu_div = Ext.get('tree_container');
        var table_name = 'href';
        var tipoAggregazione = null;
        <% if (utente.getTipAggregazione() != null){%>
            tipoAggregazione = '<%=utente.getTipAggregazione()%>';
        <%}%>
        var operazione = null;
        var rigaModifica = null;

        var formNameCampo='formNameCampo';
        //HrefCampi
        var sort_field_href_campi = 'href';
        var expand_field_href_campi = 'des_campo_search';
        var table_name_href_campi = 'href_campi';
        var id_field_href_campi = '';
        var grid_row_href_campi = 25;
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

        var successNotificaPendente = function (response,request) {
                    try {o = Ext.decode(response.responseText);}
                    catch(e) {
                        this.showError(response.responseText);
                        return;
                    }
                    if(!o.success) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        if (Ext.getCmp("save")) {Ext.getCmp("save").disable();}
                        if (Ext.getCmp("cancel")) {Ext.getCmp("cancel").disable();}
                        if (Ext.getCmp("duplicaHref")) {Ext.getCmp("duplicaHref").disable();}
                    }
                };
        var failureNotificaPendente = function (response,request) {
            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
        };

        function changeTpRiga(val){
            if(val == '0'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_riga_0")%>";
            }else if(val == '1'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_riga_1")%>";
            }
            return val;
        };
        var comboTpRiga = new Ext.form.ComboBox({
            id: 'tp_riga',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tp_riga")%>",
            name: 'tp_riga',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'tp_riga_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tp_riga_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['tp_riga','displayText'],
                data: [['0', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_riga_0")%>"],
                    ['1', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_riga_1")%>"]]
            }),
            valueField: 'tp_riga',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });

        function changeTipo(val){
            if(val == 'I'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_I")%>";
            }else if(val == 'L'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_L")%>";
            }else if(val == 'C'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_C")%>";
            }else if(val == 'R'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_R")%>";
            }else if(val == 'T'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_T")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_N")%>";
            }else if(val == 'A'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_A")%>";
            }
            return val;
        };
        var comboTipo = new Ext.form.ComboBox({
            id: 'tipo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tipo")%>",
            name: 'tipo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'tipo_hidden',
            editable: false,
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tipo_select")%>",
            store: new Ext.data.SimpleStore({
                fields: ['tipo','displayText'],
                data: [['I', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_I")%>"],
                    ['L', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_L")%>"],
                    ['C', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_C")%>"],
                    ['R', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_R")%>"],
                    ['T', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_T")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_N")%>"],
                    ['A', "<%=testiPortale.get(nomePagina + "_field_form_combo_tipo_A")%>"]
                ]
            }),
            listeners :{select: function(sm, row, rec) {
                    if (row.data.tipo == 'L') {
                        Ext.getCmp("aggiungiCampoSelect").enable();
                    }else{
                        Ext.getCmp("cancellaCampoSelect").disable();
                        Ext.getCmp("aggiungiCampoSelect").disable();
                    }
                }},
            valueField: 'tipo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });

        function changeControllo(val){
            if(val == ''){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_")%>";
            }else if(val == 'O'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_O")%>";
            }
            return val;
        };
        var comboControllo = new Ext.form.ComboBox({
            id: 'controllo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_controllo")%>",
            name: 'controllo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'controllo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_controllo_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['controllo','displayText'],
                data: [['', "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_")%>"],
                    ['O', "<%=testiPortale.get(nomePagina + "_field_form_combo_controllo_O")%>"]
                ]
            }),
            valueField: 'controllo',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });
        function changeTpControllo(val){
            if(val == 'I'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_I")%>";
            }else if(val == 'F'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_F")%>";
            }else if(val == 'D'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_D")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_N")%>";
            }else if(val == 'T'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_T")%>";
            }else if(val == 'C'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_C")%>";
            }else if(val == 'X'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_X")%>";
            }
            return val;
        };
        var comboTpControllo = new Ext.form.ComboBox({
            id: 'tp_controllo',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tp_controllo")%>",
            name: 'tp_controllo',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'tp_controllo_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_tp_controllo_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['tp_controllo','displayText'],
                data: [['I', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_I")%>"],
                    ['F', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_F")%>"],
                    ['D', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_D")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_N")%>"],
                    ['T', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_T")%>"],
                    ['C', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_C")%>"],
                    ['X', "<%=testiPortale.get(nomePagina + "_field_form_combo_tp_controllo_X")%>"]
                ]
            }),
            listeners :{select: function(sm, row, rec) {
                    if (row.data.tp_controllo == 'X') {
                        Ext.getCmp("pattern").enable();
                        Ext.getCmp("pattern").markInvalid("Campo obbligatorio");
                    }else{
                        Ext.getCmp("pattern").disable();
                        Ext.getCmp("pattern").setValue("");
                        Ext.getCmp("pattern").valid=true;
                    }
                }},
            valueField: 'tp_controllo',
            displayField: 'displayText',
            selectOnFocus:true,
            resizable:true
        });

        function changeEdit(val){
            if(val == 'S'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_S")%>";
            }else if(val == 'N'){
                return "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_N")%>";
            }
            return val;
        };
        var comboEdit = new Ext.form.ComboBox({
            id: 'edit',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_edit")%>",
            name: 'edit',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender:true,
            mode: 'local',
            hiddenName: 'edit_hidden',
            emptyText: "<%=testiPortale.get(nomePagina + "_field_form_edit_select")%>",
            editable: false,
            store: new Ext.data.SimpleStore({
                fields: ['edit','displayText'],
                data: [['S', "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_S")%>"],
                    ['N', "<%=testiPortale.get(nomePagina + "_field_form_combo_edit_N")%>"]
                ]
            }),
            valueField: 'edit',
            displayField: 'displayText',
            selectOnFocus:true,
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            resizable:true
        });
        var rigaNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_riga")%>",
            name: 'riga',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            allowNegative: false,
            allowDecimals: false,
            minValue:1
        });
        var posizioneNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_posizione")%>",
            name: 'posizione',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            allowNegative: false,
            allowDecimals: false,
            minValue:1
        });
        var lunghezzaNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_lunghezza")%>",
            name: 'lunghezza',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            allowNegative: false,
            allowDecimals: false,
            minValue:0
        });
        var decimaliNumberField = new Ext.form.NumberField({
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_decimali")%>",
            name: 'decimali',
            allowNegative: false,
            allowDecimals: false,
            minValue:0
        });

        var dyn_fields_href_campi = [
            {name: 'contatore'},
            {name: 'href'},
            {name: 'nome'},
            {name: 'des_campo_it'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'des_campo_<%=lingueAggregazione.get(i)%>'},
             <%}%>              
            {name: 'riga'},
            {name: 'posizione'},
            {name: 'tp_riga'},
            {name: 'tipo'},
            {name: 'valore'},
            {name: 'controllo'},
            {name: 'tp_controllo'},
            {name: 'lunghezza'},
            {name: 'decimali'},
            {name: 'edit'},
            {name: 'raggruppamento_check'},
            {name: 'campo_collegato'},
            {name: 'val_campo_collegato'},
            {name: 'combobox'},
            {name: 'web_serv'},
            {name: 'nome_xsd'},
            {name: 'campo_key'},
            {name: 'campo_dati'},
            {name: 'campo_xml_mod'},
            {name: 'marcatore_incrociato'},
            {name: 'precompilazione'},
            {name: 'err_msg_it'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'err_msg_<%=lingueAggregazione.get(i)%>'},
             <%}%>              
            {name: 'pattern'}
        ];
        var readerHrefCampi = new Ext.data.JsonReader({
            root:table_name_href_campi,
            fields: dyn_fields_href_campi,
            baseParams: {
                lightWeight:true,
                ext: 'js'
            },
            totalProperty: 'totalCount',
            idProperty: id_field_href_campi
        });

        var expander = new Ext.ux.grid.RowExpander({
            tpl : new Ext.Template(
            '<div class="detailData">', '', '</div>'),
            listeners : {
                expand : function(ex, record, body, rowIndex) {
                    var dataDetail = record.json["combobox"];
                    var smDetail = new Ext.grid.RowSelectionModel({
                    });
                    var storeDetail = new Ext.data.JsonStore( {
                        fields : [ "val_select", "des_valore_it" ],
                        data :dataDetail
                    });
                    var cmDetail = new Ext.grid.ColumnModel( [{
                            header :"<%=testiPortale.get(nomePagina + "_header_href_campi_val_select")%>",
                            dataIndex :'val_select',
                            width :50,
                            hideable :false,
                            sortable :false,
                            resizable :true
                        }, {
                            header :"<%=testiPortale.get(nomePagina + "_header_href_campi_des_valore")%>",
                            dataIndex :'des_valore_it',
                            width :400,
                            hideable :false,
                            sortable :false,
                            resizable :true
                        }]);
                    var gridDetail = new Ext.grid.EditorGridPanel( {
                        store :storeDetail,
                        cm :cmDetail,
                        sm: smDetail,
                        renderTo :Ext.DomQuery.select("div.detailData", body)[0],
                        enableHdMenu :false,
                        stripeRows :false,
                        autoWidth :true,
                        autoHeight :true
                    });
                }
            }
        });

        var dsHrefCampi = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: 'actions?table_name='+table_name_href_campi}),
            reader: readerHrefCampi
        });

        dsHrefCampi.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        });

        var searchSelectionModelHrefCampi = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    dsCampiSelect.removeAll();
                    for (i=0;i < dsHrefCampi.data.items[row].data.combobox.length;i++) {
                        records = [];
                        obj = new Object();
                        obj.des_valore_it = dsHrefCampi.data.items[row].data.combobox[i].des_valore_it;
                        obj.val_select = dsHrefCampi.data.items[row].data.combobox[i].val_select;
                        <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                           obj.des_valore_<%=lingueAggregazione.get(i)%> = dsHrefCampi.data.items[row].data.combobox[i].des_valore_<%=lingueAggregazione.get(i)%>;
                        <%}%>                         
                        records.push(new Ext.data.Record(obj));
                        dsCampiSelect.add(records);
                    }
                    if (rec.data.tp_controllo != 'X') {
                        Ext.getCmp("pattern").disable();
                        Ext.getCmp("pattern").clearInvalid();
                    } else {
                        Ext.getCmp("pattern").enable();
                    }
                    if (tipoAggregazione == Ext.getCmp("tip_aggregazione").getValue() || Ext.getCmp("tip_aggregazione").getValue() == '' || Ext.getCmp("tip_aggregazione").getValue() == undefined || tipoAggregazione == null) {
                        Ext.getCmp("cancellaCampo").enable();
                        Ext.getCmp("modificaCampo").enable();
                        Ext.getCmp("aggiungiCampo").enable();
                    } else {
                        Ext.getCmp("cancellaCampo").disable();
                        Ext.getCmp("modificaCampo").enable();
                        Ext.getCmp("aggiungiCampo").disable();
                    }
                }
            }
        });

        var colModelHrefCampi = new Ext.grid.ColumnModel({
            columns:[expander,
                {
                    id:'nome_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_nome")%>",
                    width: 50,
                    sortable: true,
                    dataIndex: 'nome'
                },{
                    id:'des_campo_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_des_campo")%>",
                    width: 180,
                    sortable: true,
                    dataIndex: 'des_campo_it',
                    renderer: renderTpl
                },{
                    id:'riga_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_riga")%>",
                    width: 25,
                    sortable: true,
                    dataIndex: 'riga'
                },{
                    id:'posizione_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_posizione")%>",
                    width: 25,
                    sortable: true,
                    dataIndex: 'posizione'
                },{
                    id:'tp_riga_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_tp_riga")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'tp_riga',
                    renderer: changeTpRiga
                },{
                    id:'tipo_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_tipo")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'tipo',
                    renderer: changeTipo
                },{
                    id:'controllo_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_controllo")%>",
                    width: 50,
                    sortable: true,
                    dataIndex: 'controllo',
                    renderer: changeControllo
                },{
                    id:'tp_controllo_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_tp_controllo")%>",
                    width: 50,
                    sortable: true,
                    dataIndex: 'tp_controllo',
                    renderer: changeTpControllo
                },{
                    id:'lunghezza_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_lunghezza")%>",
                    width: 25,
                    sortable: true,
                    dataIndex: 'lunghezza'
                },{
                    id:'decimali_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_decimali")%>",
                    width: 30,
                    sortable: true,
                    dataIndex: 'decimali'
                },{
                    id:'edit_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_edit")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'edit',
                    renderer: changeEdit
                },{
                    id:'valore_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_valore")%>",
                    width: 35,
                    sortable: true,
                    dataIndex: 'valore',
                    renderer: renderTpl
                },{
                    id:'raggruppamento_check_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_raggruppamento_check")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'raggruppamento_check',
                    renderer: renderTpl
                },{
                    id:'campo_collegato_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_campo_collegato")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'campo_collegato',
                    renderer: renderTpl
                },{
                    id:'val_campo_collegato_search',
                    header: "<%=testiPortale.get(nomePagina + "_header_href_campi_val_campo_collegato")%>",
                    width: 40,
                    sortable: true,
                    dataIndex: 'val_campo_collegato',
                    renderer: renderTpl
                }
            ]});

        var gridHrefCampi = new Ext.grid.GridPanel({
            id:'gridHrefCampi',
            frame:true,
            ds: dsHrefCampi,
            cm: colModelHrefCampi,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelHrefCampi,
            oadMask: true,
            autoExpandColumn: expand_field_href,
            height:420,
            border: true,
            plugins: expander,
            viewConfig: {forceFit:true},
            renderTo: grid_div,
            tbar: [{
                    text: "<%=testiPortale.get("bottone_add")%>",
                    id:'aggiungiCampo',
                    disabled: true,
                    listeners:{ 'click': function() {
                            operazione='inserimento';
                            rigaModifica = null;
                            winCampo.display();
                            Ext.getCmp(formName).getForm().findField('href').getEl().dom.setAttribute('readOnly', true);
                            if (Ext.getCmp("saveCampo")) {
                                Ext.getCmp("saveCampo").enable();
                            }
                            Ext.getCmp(formNameCampo).getForm().findField('contatore').setValue('nuovo');
                            Ext.getCmp(formNameCampo).getForm().findField('href').setValue(Ext.getCmp(formName).getForm().findField('href').getValue());
                        }
                    }
                },{xtype: 'tbseparator'},{
                    text: "<%=testiPortale.get("bottone_modify")%>",
                    id:'modificaCampo',
                    disabled: true,
                    listeners:{ 'click': function() {
                            winCampo.display();
                            Ext.getCmp(formNameCampo).getForm().loadRecord(gridHrefCampi.getSelectionModel().getSelected());
                            operazione='modifica';
                            rigaModifica = gridHrefCampi.selModel.last;
                            if (gridHrefCampi.getSelectionModel().getSelected().data.tipo == 'L') {
                                Ext.getCmp('aggiungiCampoSelect').enable();
                            }
                            Ext.getCmp("saveCampo").enable();
                        }
                    }
                },{xtype: 'tbseparator'},{
                    text: "<%=testiPortale.get("bottone_delete")%>",
                    id:'cancellaCampo',
                    disabled: true,
                    listeners:{ 'click': function(a,b,c) {
                            Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                            function(e) {
                                if (e=='yes'){
                                    ind = gridHrefCampi.selModel.last;
                                    dsHrefCampi.removeAt(ind);
                                }
                            })
                        }
                    }
                }]
        });

        var table_name_href = 'href';
        var sort_field_href = 'href';

        var id_field_href = '';
        var group_field_href = '';
        var expand_field_href = '';

        var dyn_fields_href = [
            {name: 'href'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'tit_href_<%=lingueAggregazione.get(i)%>'},
             <%}%>              
            {name: 'tit_href_it'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
             {name: 'piede_href_<%=lingueAggregazione.get(i)%>'},
             <%}%>              
            {name: 'piede_href_it'},
            {name: 'tip_aggregazione'},
            {name: 'des_aggregazione'}            
        ];
        var dyn_fields_protection_href = ['href'];

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
        var dsHref = new Ext.data.GroupingStore({
            proxy: new Ext.data.HttpProxy({url:'actions?table_name=href_totali'}),
            sortInfo: {field:sort_field_href,direction:'ASC'},
            defaultParamNames: {
                start: 'start',
                limit: 'size',
                sort: 'sort',
                dir: 'dir'
            },
            remoteSort: true,
            reader: readerHref,
            groupField: group_field_href
        });

        dsHref.on('loadexception', function(event, options, response, error) {
            var json = Ext.decode(response.responseText);
            Ext.Msg.alert('Errore', json.error);
        });

        var colModelHref = new Ext.grid.ColumnModel([
            {
                id:'grid_href',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_href")%>",
                width: 50,
                sortable: true,
                dataIndex: 'href'
            },{
                id:'grid_tit_href',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_tit_href")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'tit_href_it'
            },{
                id:'grid_piede_href',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_piede_href")%>",
                renderer: renderTpl,
                width: 250,
                sortable: true,
                dataIndex: 'piede_href_it'
            }
        ]);

        var searchHref = new Ext.ux.form.SearchField({
            store: dsHref,
            width:320,
            paging: true,
            paging_size: grid_row
        });
        var searchSelectionModelHref = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp(formName).getForm().loadRecord(rec);
                    dsHrefCampi.load({params: {href:rec.data.href}});
                    if (tipoAggregazione == rec.data.tip_aggregazione || rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined || tipoAggregazione == null) {                    
                       if (Ext.getCmp("aggiungiCampo")) {
                           Ext.getCmp("aggiungiCampo").enable();
                       }
                       if (Ext.getCmp("cancellaCampo")) {
                           Ext.getCmp("cancellaCampo").disable();
                       }
                       if (Ext.getCmp("modificaCampo")) {
                           Ext.getCmp("modificaCampo").disable();
                       }
                       if (Ext.getCmp("insert")) {
                           Ext.getCmp("insert").disable();
                       }
                       if (Ext.getCmp("save")) {
                           Ext.getCmp("save").enable();
                       }
                       if (Ext.getCmp("cancel")) {
                           Ext.getCmp("cancel").enable();
                       }
                       if (Ext.getCmp("duplicaHref")) {
                           Ext.getCmp("duplicaHref").enable();
                       }
                    } else {
                       if (Ext.getCmp("aggiungiCampo")) {
                           Ext.getCmp("aggiungiCampo").disable();
                       }
                       if (Ext.getCmp("cancellaCampo")) {
                           Ext.getCmp("cancellaCampo").disable();
                       }
                       if (Ext.getCmp("modificaCampo")) {
                           Ext.getCmp("modificaCampo").disable();
                       }
                       if (Ext.getCmp("insert")) {
                           Ext.getCmp("insert").disable();
                       }
                       if (Ext.getCmp("save")) {
                           Ext.getCmp("save").disable();
                       }
                       if (Ext.getCmp("cancel")) {
                           Ext.getCmp("cancel").disable();
                       }
                       if (Ext.getCmp("duplicaHref")) {
                           Ext.getCmp("duplicaHref").disable();
                       }
                    }
                    if (rec.data.tip_aggregazione == '' || rec.data.tip_aggregazione == undefined) {
                        triggerAggregazioni.enable();
                    } else {
                        triggerAggregazioni.disable();
                    }
                    Ext.getCmp("azione_form").setValue('modifica');
                    for (var ele=0;ele < dyn_fields_protection_href.length;ele++) {
                        Ext.getCmp(formName).getForm().findField(dyn_fields_protection_href[ele]).getEl().dom.setAttribute('readOnly', true);
                    }
                    winHref.hide();
                    Ext.Ajax.request({
                        url: 'leggiNotifica',
                        method: 'POST',
                        params: {'table_name':table_name,'href':rec.data.href},
                        success: successNotificaPendente,
                        failure: failureNotificaPendente
                    });
                }
            }
        });

        var gridHref = new Ext.grid.GridPanel({
            id:'gridHref',
            frame:true,
            ds: dsHref,
            cm: colModelHref,
            buttonAlign: 'center',
            autoScroll:true,
            sm:searchSelectionModelHref,
            oadMask: true,
            autoExpandColumn: expand_field_href,
            height:480,
            view: new Ext.grid.GroupingView({
                forceFit:true,
                groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Elementi" : "Elemento"]})'
            }),
            bbar: new Ext.PagingToolbar({
                store: dsHref,
                pageSize:grid_row,
                displayInfo:true,
                items:[searchHref]
            }),
            border: true
        });
        var winHref = new Ext.Window({
            title: "<%=testiPortale.get(nomePagina + "_titolo_window_href")%>",
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
                dsHref.reload({params:{start: 0, size:grid_row}});
                this.show();
            },
            lookupFields: ['href','tit_href','piede_href']
        });

        var formNameWindow = 'edit-form-window';
        var	itemsFormWindow = [{
                id: 'contatore_select',
                fieldLabel: 'contatore_select',
                name: 'contatore',
                readOnly: true,
                hidden: true,
                hideLabel: true,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'href_select',
                fieldLabel: 'href_select',
                name: 'href',
                readOnly: true,
                hidden: true,
                hideLabel: true,
                allowBlank: false ,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'nome_select',
                fieldLabel: 'nome_select',
                name: 'nome',
                readOnly: true,
                hidden: true,
                hideLabel: true,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'val_select',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_val_select")%>",
                name: 'val_select',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },{
                id: 'des_valore_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_valore")%>",
                name: 'des_valore_it',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            }
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
              ,{
                id: 'des_valore_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_valore")%>(<%=lingueAggregazione.get(i)%>)",
                name: 'des_valore_<%=lingueAggregazione.get(i)%>',
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
               }
             <%}%>,{
                id: 'azioneSelect',
                fieldLabel: 'azioneSelect',
                name: 'azioneSelect',
                readOnly: true,
                hidden: true,
                hideLabel: true
            },{
                id: 'idModificaSelect',
                fieldLabel: 'idModificaSelect',
                name: 'idModificaSelect',
                readOnly: true,
                hidden: true,
                hideLabel: true
            }];

        var formHrefCampiSelect = new Ext.FormPanel({
            id: formNameWindow,
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
            title:"<%=testiPortale.get(nomePagina + "_href_campi_select_header_form")%>",
            defaults: {width: 600, border:false},
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
            items: itemsFormWindow,
            buttons: [{
                    id: 'saveWindow',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    formBind:true,
                    handler: function(){
                        var records = [];
                        if (Ext.getCmp(formNameWindow).getForm().findField('azioneSelect').getValue()=='inserimento') {
                            obj = new Object();
                            obj.val_select = Ext.getCmp(formNameWindow).getForm().findField('val_select').getValue();
                            obj.des_valore_it = Ext.getCmp(formNameWindow).getForm().findField('des_valore_it').getValue();
                            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                               obj.des_valore_<%=lingueAggregazione.get(i)%> = Ext.getCmp(formNameWindow).getForm().findField('des_valore_<%=lingueAggregazione.get(i)%>').getValue();
                            <%}%>
                            records.push(new Ext.data.Record(obj));
                            dsCampiSelect.add(records);
                        }
                        if (Ext.getCmp(formNameWindow).getForm().findField('azioneSelect').getValue()=='modifica') {
                            var p = Ext.getCmp(formNameWindow).getForm().findField('idModificaSelect').getValue();
                            dsCampiSelect.data.items[p].data.des_valore_it=Ext.getCmp(formNameWindow).getForm().findField('des_valore_it').getValue();
                            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                               dsCampiSelect.data.items[p].data.des_valore_<%=lingueAggregazione.get(i)%> = Ext.getCmp(formNameWindow).getForm().findField('des_valore_<%=lingueAggregazione.get(i)%>').getValue();
                            <%}%> 
                            gridCampiSelect.getView().refresh();
                        }
                        formHrefCampiSelect.getForm().reset();
                        winHrefCampiSelect.hide();
                        Ext.getCmp("saveWindow").disable();
                    }
                },{
                    id: 'closeWindow',
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){
                        winHrefCampiSelect.hide();
                        Ext.getCmp("saveWindow").disable();
                    }
                }],
            buttonAlign: 'center'
        });

        var winHrefCampiSelect = new Ext.Window({
            closable:true,
            closeAction:'hide',
            width:900,
            height:500,
            border:false,
            maximizable: true,
            plain:true,
            modal:true,
            items: [formHrefCampiSelect],
            display: function(){
                this.show();
            }
        });

        /*---------window duplica href----------------*/
        var table_name_new_href = 'new_href';
        var txtFldNH = new Ext.form.TextField({
            id: 'new_href',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_duplica_href_new_href")%>",
            name: 'new_href',
            allowBlank: false,
            labelStyle: 'font-weight:bold;',
            maxLength:50,
            enableKeyEvents: true
        });
        txtFldNH.on('blur', function(a) {
            if (this.getValue() != "") {
                Ext.Ajax.request({
                    url: 'leggiEsistenza',
                    method: 'POST',
                    params: {table_name:table_name_new_href, tab_key:this.getValue()},
                    success: function(response,request) {
                        try {o = Ext.decode(response.responseText);}
                        catch(e) {
                            this.showError(response.responseText);
                            return;
                        }
                        if(!o.success) {
                            txtFldNH.markInvalid(Ext.util.JSON.decode(response.responseText).failure);
                        } else {
                            Ext.getCmp("saveDHWindow").enable();
                        }
                    },
                    failure: function(response,request) {
                        Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                    }
                });
            }
        });
        var	itemsDuplicaHref = [{
                id: 'old_href',
                fieldLabel: 'old_href',
                name: 'old_href',
                readOnly: true,
                hidden: true,
                hideLabel: true,
                maxLength:50,
                allowBlank: false
            },txtFldNH];

        var formDuplicaHref = new Ext.FormPanel({
            id: 'formDuplicaHref',
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
            title:"<%=testiPortale.get(nomePagina + "_href_duplica_href_header_form")%>",
            defaults: {width: 200, border:false},
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
            items: itemsDuplicaHref,
            buttons: [{
                    id: 'saveDHWindow',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    handler: function(){
                        formDuplicaHref.getForm().submit({url:'inserisci?table_name='+table_name_new_href,
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                codice=Ext.getCmp('formDuplicaHref').getForm().findField('new_href').getValue();
                                formDuplicaHref.getForm().reset();
                                winDuplicaHref.hide();
                                Ext.getCmp(formName).getForm().findField('href').setValue(codice);
                                dsHrefCampi.load({params: {href:codice}});
                            },
                            failure:function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                            }
                        });
                    }
                },{
                    id: 'closeDHWindow',
                    text: "<%=testiPortale.get("bottone_close")%>",
                    handler: function(){winDuplicaHref.hide();}
                }],
            buttonAlign: 'center'
        });

        var winDuplicaHref = new Ext.Window({
            closable:true,
            closeAction:'hide',
            width:500,
            height:200,
            border:false,
            maximizable: false,
            plain:true,
            modal:true,
            items: [formDuplicaHref],
            display: function(){
                this.show();
            }
        });

        /*--------------------------------------------*/

        var table_name_campi_select = 'href_campi_select';

        var dyn_fields_campi_select = [
            {name: 'val_select'},
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
               {name: 'des_valore_<%=lingueAggregazione.get(i)%>'},
            <%}%>            
            {name: 'des_valore_it'}
        ];

        var dsCampiSelect = new Ext.data.ArrayStore({
            fields : dyn_fields_campi_select
        });
        var colModelCampiSelect = new Ext.grid.ColumnModel([
            {
                id:'grid_val_select',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_val_select")%>",
                width: 90,
                sortable: true,
                dataIndex: 'val_select'
            },{
                id:'grid_des_valore',
                header: "<%=testiPortale.get(nomePagina + "_header_grid_des_valore")%>",
                renderer: renderTpl,
                width: 570,
                sortable: true,
                dataIndex: 'des_valore_it'
            }
        ]);
        var gridCampiSelectSelectionModel = new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    Ext.getCmp("cancellaCampoSelect").enable();
                    Ext.getCmp("modificaCampoSelect").enable();
                }
            }
        });

        var gridCampiSelect = new Ext.grid.EditorGridPanel({
            id: 'gridCampiSelect',
            store: dsCampiSelect,
            cm: colModelCampiSelect,
            height: 160,
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_grid_campi_select")%>",
            autoScroll:true,
            sm:gridCampiSelectSelectionModel,
            border: true,
            tbar: [{
                    text: "<%=testiPortale.get("bottone_add")%>",
                    id:'aggiungiCampoSelect',
                    disabled:true,
                    listeners:{ 'click': function() {
                            winHrefCampiSelect.display();
                            formHrefCampiSelect.getForm().reset();
                            Ext.getCmp("closeCampo").enable();
                            Ext.getCmp(formNameWindow).getForm().findField('href').setValue(Ext.getCmp(formNameCampo).getForm().findField('href').getValue());
                            Ext.getCmp(formNameWindow).getForm().findField('contatore').setValue(Ext.getCmp(formNameCampo).getForm().findField('contatore').getValue());
                            Ext.getCmp(formNameWindow).getForm().findField('nome').setValue(Ext.getCmp(formNameCampo).getForm().findField('nome').getValue());
                            Ext.getCmp(formNameWindow).getForm().findField('val_select').enable();
                            Ext.getCmp(formNameWindow).getForm().findField('azioneSelect').setValue('inserimento');
                        }
                    }
                },{xtype: 'tbseparator'},{
                    text: "<%=testiPortale.get("bottone_modify")%>",
                    id: 'modificaCampoSelect',
                    disabled:true,
                    listeners:{
                        'click': function() {
                            var ind = gridCampiSelect.selModel.last;
                            formHrefCampiSelect.getForm().reset();
                            winHrefCampiSelect.display();
                            Ext.getCmp("closeCampo").enable();
                            Ext.getCmp(formNameWindow).getForm().findField('href').setValue(Ext.getCmp(formNameCampo).getForm().findField('href').getValue());
                            Ext.getCmp(formNameWindow).getForm().findField('contatore').setValue(Ext.getCmp(formNameCampo).getForm().findField('contatore').getValue());
                            Ext.getCmp(formNameWindow).getForm().findField('nome').setValue(Ext.getCmp(formNameCampo).getForm().findField('nome').getValue());
                            Ext.getCmp(formNameWindow).getForm().findField('val_select').setValue(dsCampiSelect.getAt(ind).data.val_select);
                            Ext.getCmp(formNameWindow).getForm().findField('val_select').disable();
                            Ext.getCmp(formNameWindow).getForm().findField('des_valore_it').setValue(dsCampiSelect.getAt(ind).data.des_valore_it);
                            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                                 Ext.getCmp(formNameWindow).getForm().findField('des_valore_<%=lingueAggregazione.get(i)%>').setValue(dsCampiSelect.getAt(ind).data.des_valore_<%=lingueAggregazione.get(i)%>);
                            <%}%>
                            Ext.getCmp(formNameWindow).getForm().findField('azioneSelect').setValue('modifica');
                            Ext.getCmp(formNameWindow).getForm().findField('idModificaSelect').setValue(ind);
                        }
                    }
                },{xtype: 'tbseparator'},{
                    text: "<%=testiPortale.get("bottone_cancella")%>",
                    id: 'cancellaCampoSelect',
                    disabled:true,
                    listeners:{
                        'click': function() {
                            Ext.MessageBox.confirm('Confirm', "<%=testiPortale.get("msg_conferma_cancellazione")%>",
                            function(e) {
                                if (e=='yes'){
                                    var ind = gridCampiSelect.selModel.last;
                                    dsCampiSelect.removeAt(ind);
                                }
                            }
                        )
                        }
                    }
                }]
        });

        var txtFld = new Ext.form.TextField({
            id: 'href',
            fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_href")%>",
            name: 'href',
            allowBlank: false,
            labelStyle: 'font-weight:bold;'	,
            maxLength:50,
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
                            } else {
                                Ext.getCmp("aggiungiCampo").enable();
                            }
                        },
                        failure: function(response,request) {
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }
                    });
                }
            }
        });
        var	itemsForm = [txtFld,{
                id: 'tit_href_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_href")%>",
                name: 'tit_href_it',
                xtype:'textarea',
                maxLength:255,
                height: 37,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
            },
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
               {
               id: 'tit_href_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_tit_href")%>(<%=lingueAggregazione.get(i)%>)",
                name: 'tit_href_<%=lingueAggregazione.get(i)%>',
                xtype:'textarea',
                maxLength:255,
                height: 37,
                allowBlank: false,
                labelStyle: 'font-weight:bold;'
               },
            <%}%> 
            {
                id: 'piede_href_it',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_piede_href")%>",
                name: 'piede_href_it',
                maxLength:64000,
                xtype:'textarea',
                height: 37
            },
            <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
               {
               id: 'piede_href_<%=lingueAggregazione.get(i)%>',
                fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_piede_href")%>(<%=lingueAggregazione.get(i)%>)",
                name: 'piede_href_<%=lingueAggregazione.get(i)%>',
                maxLength:64000,
                xtype:'textarea',
                height: 37
               },
            <%}%> 
            <% if (territorio) { %>
                triggerAggregazioni,{
                id: 'des_aggregazione',
                fieldLabel: "<%=testiPortale.get("Generic_field_form_des_aggregazione")%>",
                name: 'des_aggregazione',
                readOnly: true
            }, 
            <%}%>   
            {
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

        var submitForm = function () {
                        form.getForm().submit({url:'aggiorna',
                            params: {'hrefCampi':Ext.util.JSON.encode(caricaArray(dsHrefCampi))},
                            waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                            success: function(result,request) {
                                Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                Ext.getCmp("azione_form").setValue('modifica');
                                if (Ext.getCmp("insert")) {
                                    Ext.getCmp("insert").disable();
                                }
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").enable();
                                }
                            },
                            failure:function(result,request) {
                                if (Ext.util.JSON.decode(request.response.responseText).failure.notifica){
                                    Ext.MessageBox.confirm('Confirm',Ext.util.JSON.decode(request.response.responseText).failure.notifica,
                                    function(e) {
                                        if (e=='yes'){
                                            winDisplayNotifica.show();
                                        }
                                    });
                                }else if (Ext.util.JSON.decode(request.response.responseText).failure.errore){
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure.errore);
                                }else{
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            }
                        });

        }
        var form = new Ext.FormPanel({
            id: formName,
            monitorValid: true,
            formLayout: true,
            labelWidth: 120,
            title:"<%=testiPortale.get(nomePagina + "_href_header_form")%>",
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
            
            buttons: [{id: 'selezionaHref',
                    text: "<%=testiPortale.get("bottone_seleziona_href")%>",
                    handler: function(){
                        winHref.display();
                    }
                },{
                    id: 'save',
                    text: "<%=testiPortale.get("bottone_save")%>",
                    disabled:true,
                    handler: function(){
                        table_name_notifica=table_name;
                        submitForm();
                    }
                }
           
    <% if (!utente.getRuolo().equals("A")) {%>
                    ,{
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
                                            Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                            if (Ext.getCmp("insert")) {
                                                Ext.getCmp("insert").enable();
                                            }
                                            if (Ext.getCmp("save")) {
                                                Ext.getCmp("save").disable();
                                            }
                                            if (Ext.getCmp("cancel")) {
                                                Ext.getCmp("cancel").disable();
                                            }
                                            Ext.getCmp("azione_form").setValue('inserimento');
                                            for (var ele=0;ele < dyn_fields_protection_href.length;ele++) {
                                                Ext.getCmp(formName).getForm().findField(dyn_fields_protection_href[ele]).getEl().dom.readOnly = false;
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
                                params: {'hrefCampi':Ext.util.JSON.encode(caricaArray(dsHrefCampi))},
                                waitMsg: "<%=testiPortale.get("attesa_salvataggio")%>",
                                success: function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).success);
                                    if (Ext.getCmp("insert")) {
                                        Ext.getCmp("insert").disable();
                                    }
                                    if (Ext.getCmp("save")) {
                                        Ext.getCmp("save").enable();
                                    }
                                    if (Ext.getCmp("cancel")) {
                                        Ext.getCmp("cancel").enable();
                                    }
                                    Ext.getCmp("azione_form").setValue('modifica');
                                    for (var ele=0;ele < dyn_fields_protection_href.length;ele++) {
                                        Ext.getCmp(formName).getForm().findField(dyn_fields_protection_href[ele]).getEl().dom.setAttribute('readOnly', true);
                                    }
                                },
                                failure:function(result,request) {
                                    Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                                }
                            });
                        }
                    },{
                        id: 'duplicaHref',
                        text: "<%=testiPortale.get("bottone_duplica_href")%>",
                        disabled:true,
                        handler: function(){
                            winDuplicaHref.display();
                            Ext.getCmp("saveDHWindow").disable();
                            Ext.getCmp('formDuplicaHref').getForm().findField('old_href').setValue(Ext.getCmp(formName).getForm().findField('href').getValue());
                        }
                    },{
                        id: 'reset',
                        text: "<%=testiPortale.get("bottone_reset")%>",
                        handler: function(){
                            form.getForm().reset();
                            dsHrefCampi.removeAll();
                            if (Ext.getCmp("insert")) {
                                Ext.getCmp("insert").enable();
                            }
                            if (Ext.getCmp("save")) {
                                Ext.getCmp("save").disable();
                            }
                            if (Ext.getCmp("cancel")) {
                                Ext.getCmp("cancel").disable();
                            }
                            if (Ext.getCmp("duplicaHref")) {
                                Ext.getCmp("duplicaHref").disable();
                            }
                            Ext.getCmp('aggiungiCampo').disable();
                            Ext.getCmp("azione_form").setValue('inserimento');
                            for (var ele=0;ele < dyn_fields_protection_href.length;ele++) {
                                Ext.getCmp(formName).getForm().findField(dyn_fields_protection_href[ele]).getEl().dom.readOnly = false;
                            }

                        }
                    }
    <% }%>
                ],
                buttonAlign: 'center',
                renderTo: form_div
            });
            var dyn_fields_protection_href_campi = [];
            var table_name_href_campi='href_campi';
            var	itemsFormCampo = [{
                    fieldLabel: 'contatore',
                    name: 'contatore',
                    hidden: true,
                    hideLabel: true,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },{
                    fieldLabel: 'href',
                    name: 'href',
                    hidden: true, 
                    hideLabel: true,
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_nome")%>",
                    name: 'nome',
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_campo")%>",
                    name: 'des_campo_it',
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
                },
             <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
               {
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_des_campo")%>(<%=lingueAggregazione.get(i)%>)",
                    name: 'des_campo_<%=lingueAggregazione.get(i)%>',
                    allowBlank: false,
                    labelStyle: 'font-weight:bold;'
               },
            <%}%>  
                rigaNumberField,posizioneNumberField,comboTpRiga,comboTipo,comboControllo,
                comboTpControllo,
                {
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_pattern")%>",
                    name: 'pattern',
                    id: 'pattern',
                    //allowBlank: true,
                    maxLength: 255,
                    validator: function(x) {
                        if (x == ''){
                           return "Campo obbligatorio";
                        }
                        return isGood(x);
                    }
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_err_msg")%>",
                    name: 'err_msg_it',
                    allowBlank: true,
                    maxLength: 255
                },
             <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
               {
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_err_msg")%>(<%=lingueAggregazione.get(i)%>)",
                    name: 'err_msg_<%=lingueAggregazione.get(i)%>',
                    allowBlank: true,
                    maxLength: 255
               },
            <%}%>                  
                comboEdit,lunghezzaNumberField,decimaliNumberField,{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_valore")%>",
                    name: 'valore'
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_raggruppamento_check")%>",
                    name: 'raggruppamento_check'
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_campo_collegato")%>",
                    name: 'campo_collegato'
                },{
                    fieldLabel: "<%=testiPortale.get(nomePagina + "_field_form_val_campo_collegato")%>",
                    name: 'val_campo_collegato'
                },gridCampiSelect,{
                    fieldLabel: 'table name',
                    name: 'table_name',
                    readOnly: true,
                    hidden: true,
                    hideLabel: true,
                    value: table_name_href_campi
                }];

            var formCampo = new Ext.FormPanel({
                id: formNameCampo,
                monitorValid: true,
                formLayout: true,
                labelWidth: 120,
                defaults: {anchor:'95%', border:false, xtype:'textfield'},
                frame: true,
                autoWidth: true,
                autoScroll: true,
                bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                border: true,
                style: {
                    "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                },
                items: itemsFormCampo
            });
            var winCampo = new Ext.Window({
                layout: 'fit',
                closable:true,
                closeAction:'hide',
                width:900,
                height:600,
                border:false,
                maximizable: true,
                plain:true,
                modal:true,
                items: [formCampo],
                display: function(){
                    this.show();
                },
                buttons: [{
                        id: 'saveCampo',
                        text: "<%=testiPortale.get("bottone_save")%>",
                        disabled:true,
                        handler: function(){
                            if ( formCampo.getForm().isValid() ) {
                                var records = [];
                                obj = new Object();
                                obj.contatore = Ext.getCmp(formNameCampo).getForm().findField('contatore').getValue();
                                obj.href = Ext.getCmp(formNameCampo).getForm().findField('href').getValue();
                                obj.nome = Ext.getCmp(formNameCampo).getForm().findField('nome').getValue();
                                obj.des_campo_it = Ext.getCmp(formNameCampo).getForm().findField('des_campo_it').getValue();
                                <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                                   obj.des_campo_<%=lingueAggregazione.get(i)%> = Ext.getCmp(formNameCampo).getForm().findField('des_campo_<%=lingueAggregazione.get(i)%>').getValue();
                                <%}%> 
                                obj.riga = Ext.getCmp(formNameCampo).getForm().findField('riga').getValue();
                                obj.posizione = Ext.getCmp(formNameCampo).getForm().findField('posizione').getValue();
                                obj.tp_riga = Ext.getCmp(formNameCampo).getForm().findField('tp_riga_hidden').getValue();
                                obj.tipo = Ext.getCmp(formNameCampo).getForm().findField('tipo_hidden').getValue();
                                obj.valore = Ext.getCmp(formNameCampo).getForm().findField('valore').getValue();
                                obj.controllo = Ext.getCmp(formNameCampo).getForm().findField('controllo_hidden').getValue();
                                obj.tp_controllo = Ext.getCmp(formNameCampo).getForm().findField('tp_controllo_hidden').getValue();
                                obj.pattern = Ext.getCmp(formNameCampo).getForm().findField('pattern').getValue();
                                obj.err_msg_it = Ext.getCmp(formNameCampo).getForm().findField('err_msg_it').getValue();
                                <% for (int i = 0; i < lingueAggregazione.size(); i++){ %>
                                   obj.err_msg_<%=lingueAggregazione.get(i)%> = Ext.getCmp(formNameCampo).getForm().findField('err_msg_<%=lingueAggregazione.get(i)%>').getValue();
                                <%}%>                                 
                                obj.lunghezza = Ext.getCmp(formNameCampo).getForm().findField('lunghezza').getValue();
                                obj.decimali = Ext.getCmp(formNameCampo).getForm().findField('decimali').getValue();
                                obj.edit = Ext.getCmp(formNameCampo).getForm().findField('edit_hidden').getValue();
                                obj.raggruppamento_check = Ext.getCmp(formNameCampo).getForm().findField('raggruppamento_check').getValue();
                                obj.campo_collegato = Ext.getCmp(formNameCampo).getForm().findField('campo_collegato').getValue();
                                obj.val_campo_collegato = Ext.getCmp(formNameCampo).getForm().findField('val_campo_collegato').getValue();
                                obj.combobox =  [];
                                comboBoxValue = gridCampiSelect.getStore();
                                for(i=0;i < comboBoxValue.data.length;i++){
                                   obj.combobox.push(comboBoxValue.data.items[i].data);
                                }
                                if (operazione == 'inserimento'){
                                   obj.web_serv = "";
                                   obj.nome_xsd = "";
                                   obj.campo_key = "";
                                   obj.campo_dati = "";
                                   obj.campo_xml_mod = "";
                                   obj.marcatore_incrociato = "";
                                   obj.precompilazione = "";
                                }
                                if (operazione == 'modifica'){
                                   obj.web_serv = dsHrefCampi.data.items[rigaModifica].data.web_serv;
                                   obj.nome_xsd = dsHrefCampi.data.items[rigaModifica].data.nome_xsd;
                                   obj.campo_key = dsHrefCampi.data.items[rigaModifica].data.campo_key;
                                   obj.campo_dati = dsHrefCampi.data.items[rigaModifica].data.campo_dati;
                                   obj.campo_xml_mod = dsHrefCampi.data.items[rigaModifica].data.campo_xml_mod;
                                   obj.marcatore_incrociato = dsHrefCampi.data.items[rigaModifica].data.marcatore_incrociato;
                                   obj.precompilazione = dsHrefCampi.data.items[rigaModifica].data.precompilazione;
                                }
                                record = new Ext.data.Record(obj);
                                record.json=new Object();
                                record.json.combobox=obj.combobox;
                                records.push(record);
                                if (operazione == 'inserimento'){
                                   dsHrefCampi.add(records);
                                }
                                if (operazione == 'modifica'){
                                   dsHrefCampi.removeAt(rigaModifica);
                                   dsHrefCampi.insert(rigaModifica,records);
                                }
                                formCampo.getForm().reset();
                                gridCampiSelect.getStore().removeAll();
                                winCampo.hide();
                                operazione=null;
                                rigaModifica = null;
                            }
                        }
                    },{
                        id: 'closeCampo',
                        text: "<%=testiPortale.get("bottone_close")%>",
                        handler: function(){
                            formCampo.getForm().reset();
                            winCampo.hide();
                        }
                    }],
                buttonAlign: 'center'
            });

            href_url=Ext.getUrlParam('href');
            if (href_url) {
                Ext.Ajax.request({
                    url: 'leggiRecord',
                    method: 'POST',
                    params: {'href':href_url,'table_name':table_name},
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
                                dsHrefCampi.load({params: {href:href_url}});
                                if (Ext.getCmp("insert")) {
                                    Ext.getCmp("insert").disable();
                                }
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").enable();
                                }
                                if (Ext.getCmp("duplicaHref")) {
                                    Ext.getCmp("duplicaHref").enable();
                                }
                                if (tipoAggregazione == dati['tip_aggregazione'] || dati['tip_aggregazione'] == '' || dati['tip_aggregazione'] == undefined || tipoAggregazione == null) {
                                    if (Ext.getCmp("aggiungiCampo")) {
                                        Ext.getCmp("aggiungiCampo").enable();
                                    }
                                    if (Ext.getCmp("cancellaCampo")) {
                                        Ext.getCmp("cancellaCampo").disable();
                                    }
                                    if (Ext.getCmp("modificaCampo")) {
                                        Ext.getCmp("modificaCampo").disable();
                                    }
                                    if (Ext.getCmp("insert")) {
                                        Ext.getCmp("insert").disable();
                                    }
                                    if (Ext.getCmp("save")) {
                                        Ext.getCmp("save").enable();
                                    }
                                    if (Ext.getCmp("cancel")) {
                                        Ext.getCmp("cancel").enable();
                                    }
                                    if (Ext.getCmp("duplicaHref")) {
                                        Ext.getCmp("duplicaHref").enable();
                                    }
                                } else {
                                    if (Ext.getCmp("aggiungiCampo")) {
                                        Ext.getCmp("aggiungiCampo").disable();
                                    }
                                    if (Ext.getCmp("cancellaCampo")) {
                                        Ext.getCmp("cancellaCampo").disable();
                                    }
                                    if (Ext.getCmp("modificaCampo")) {
                                        Ext.getCmp("modificaCampo").disable();
                                    }
                                    if (Ext.getCmp("insert")) {
                                        Ext.getCmp("insert").disable();
                                    }
                                    if (Ext.getCmp("save")) {
                                        Ext.getCmp("save").disable();
                                    }
                                    if (Ext.getCmp("cancel")) {
                                        Ext.getCmp("cancel").disable();
                                    }
                                    if (Ext.getCmp("duplicaHref")) {
                                        Ext.getCmp("duplicaHref").disable();
                                    }
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
            } else {
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

                            if (conta==0) {
                                winHref.display();
                            }
                            if (Ext.getDom('azione_form').value == 'inserimento') {
                                if (Ext.getCmp("insert")) {
                                    Ext.getCmp("insert").enable();
                                }
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").disable();
                                }
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").disable();
                                }
                                if (Ext.getCmp("duplicaHref")) {
                                    Ext.getCmp("duplicaHref").disable();
                                }
                            }
                            if (Ext.getDom('azione_form').value == 'modifica') {
                                if (Ext.getCmp("insert")) {
                                    Ext.getCmp("insert").disable();
                                }
                                if (Ext.getCmp("save")) {
                                    Ext.getCmp("save").enable();
                                }
                                if (Ext.getCmp("cancel")) {
                                    Ext.getCmp("cancel").enable();
                                }
                                if (Ext.getCmp("duplicaHref")) {
                                    Ext.getCmp("duplicaHref").enable();
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
