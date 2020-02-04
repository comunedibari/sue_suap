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
            Utente utente = (Utente) session.getAttribute("utente");

%>

    var Tree = Ext.tree;
    var tree = new Tree.TreePanel({
        useArrows: true,
        cls: 'albero',
        autoScroll: true,
        animate: true,
        enableDD: false,
        containerScroll: false,
        border: false,
        loader: new Tree.TreeLoader(),
        lines: true,
        margins: '0 0 0 0',
        listeners: {
            "click": function (a,b,c) {
                if (a.leaf) {
                    masker.show();
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
                            } else {
                                if (a.attributes.itemHref) {
                                    window.location=a.attributes.itemHref;
                                }
                            }
                        },
                        failure: function(response,request) {
                            masker.hide();
                            Ext.Msg.alert('Status', Ext.util.JSON.decode(response.responseText).failure);
                        }
                    });
                }
            }
        }
    });

    var root = new Tree.TreeNode({
        text: '<%=testiPortale.get("menu_azioni")%>',
        draggable:false,
        id:'source',
        href: "#",
        iconCls :'no-icon',
        cls :'no-icon-root'
    });

    tree.setRootNode(root);

    var json = [
    <%if (utente.getRuolo().equals("D")) {%>
            {id:'id1',
                iconCls:'no-icon',
                leaf:false,
                text:'<%=testiPortale.get("menu_1")%>',
                itemHref:false,
                singleClickExpand: true,
                children:[
                    {id:'id1-1',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Utenti.jsp?set_id=id1/id1-1',
                        text:'<%=testiPortale.get("menu_1_1")%>'},
                    {id:'id1-2',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/UtenteInterventi.jsp?set_id=id1/id1-2',
                        text:'<%=testiPortale.get("menu_1_2")%>'},
                    {id:'id1-3',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/TestiPortale.jsp?set_id=id1/id1-3',
                        text:'<%=testiPortale.get("menu_1_3")%>'},
                    {id:'id1-4',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Help.jsp?set_id=id1/id1-4',
                        text:'<%=testiPortale.get("menu_1_4")%>'},
                    {id:'id1-5',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/DatiIntegrati.jsp?set_id=id1/id1-5',
                        text:'<%=testiPortale.get("menu_1_5")%>'},
                    {id:'id1-6',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Anagrafica.jsp?set_id=id1/id1-6',
                        text:'<%=testiPortale.get("menu_1_6")%>'},
                    {id:'id1-7',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Parametri.jsp?set_id=id1/id1-7',
                        text:'<%=testiPortale.get("menu_1_7")%>'},
                    {id:'id1-8',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/LingueAggregazioni.jsp?set_id=id1/id1-8',
                        text:'<%=testiPortale.get("menu_1_8")%>'}]
            },
    <%}%>
    <%if (utente.getRuolo().equals("D") || utente.getRuolo().equals("C")) {%>
            {id:'id2',
                iconCls:'no-icon',
                leaf:false,
                text: '<%=testiPortale.get("menu_2")%>',
                itemHref:false,
                singleClickExpand: true,
                children:[
                    {id:'id2-1',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/TipiAggregazione.jsp?set_id=id2/id2-1',
                        text:'<%=testiPortale.get("menu_2_1")%>'},
                    {id:'id2-2',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Cud.jsp?set_id=id2/id2-2',
                        text:'<%=testiPortale.get("menu_2_2")%>'},
                    {id:'id2-3',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/ClassiEnti.jsp?set_id=id2/id2-3',
                        text:'<%=testiPortale.get("menu_2_3")%>'},
                    {id:'id2-4',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/EntiComuni.jsp?set_id=id2/id2-4',
                        text:'<%=testiPortale.get("menu_2_4")%>'},
                    {id:'id2-5',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Destinatari.jsp?set_id=id2/id2-5',
                        text:'<%=testiPortale.get("menu_2_5")%>'},
                    {id:'id2-6',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Sportelli.jsp?set_id=id2/id2-6',
                        text:'<%=testiPortale.get("menu_2_6")%>'}
    <%if (utente.getRuolo().equals("D")) {%>
                        ,
                    {id:'id2-7',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/SettoriAttivita.jsp?set_id=id2/id2-7',
                        text:'<%=testiPortale.get("menu_2_7")%>'}
                 <% } %>
                        ,
                    {id:'id2-8',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Operazioni.jsp?set_id=id2/id2-8',
                        text:'<%=testiPortale.get("menu_2_8")%>'}
    <%if (utente.getRuolo().equals("D")) {%>
                        ,
                    {id:'id2-9',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/GerarchiaSettori.jsp?set_id=id2/id2-9',
                        text:'<%=testiPortale.get("menu_2_9")%>'}
                 <% } %>
                        ,
                    {id:'id2-10',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/GerarchiaOperazioni.jsp?set_id=id2/id2-10',
                        text:'<%=testiPortale.get("menu_2_10")%>'},
                    {id:'id2-11',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Interventi.jsp?set_id=id2/id2-11',
                        text:'<%=testiPortale.get("menu_2_11")%>'},
                    {id:'id2-12',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Procedimenti.jsp?set_id=id2/id2-12',
                        text:'<%=testiPortale.get("menu_2_12")%>'},
                    {id:'id2-13',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/TipiRif.jsp?set_id=id2/id2-13',
                        text:'<%=testiPortale.get("menu_2_13")%>'},
                    {id:'id2-14',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Oneri.jsp?set_id=id2/id2-14',
                        text:'<%=testiPortale.get("menu_2_14")%>'},
                    {id:'id2-15',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/ComuniAggregazione.jsp?set_id=id2/id2-15',
                        text:'<%=testiPortale.get("menu_2_15")%>'},
                    {id:'id2-16',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/OneriGerarchia.jsp?set_id=id2/id2-16',
                        text:'<%=testiPortale.get("menu_2_16")%>'},
                    {id:'id2-17',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/OneriFormula.jsp?set_id=id2/id2-17',
                        text:'<%=testiPortale.get("menu_2_17")%>'},
                    {id:'id2-18',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/OneriCampi.jsp?set_id=id2/id2-18',
                        text:'<%=testiPortale.get("menu_2_18")%>'},
                    {id:'id2-19',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/OneriDocumenti.jsp?set_id=id2/id2-19',
                        text:'<%=testiPortale.get("menu_2_19")%>'},
                    {id:'id2-20',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/OneriInterventi.jsp?set_id=id2/id2-20',
                        text:'<%=testiPortale.get("menu_2_20")%>'},
                    {id:'id2-21',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/AllegatiComuni.jsp?set_id=id2/id2-21',
                        text:'<%=testiPortale.get("menu_2_21")%>'},
                    {id:'id2-22',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/InterventiComuni.jsp?set_id=id2/id2-22',
                        text:'<%=testiPortale.get("menu_2_22")%>'},
                    {id:'id2-23',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/OneriComuni.jsp?set_id=id2/id2-23',
                        text:'<%=testiPortale.get("menu_2_23")%>'},
                    {id:'id2-24',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/SettoriAttivitaComuni.jsp?set_id=id2/id2-24',
                        text:'<%=testiPortale.get("menu_2_24")%>'},
                    {id:'id2-25',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/RelazioniEnti.jsp?set_id=id2/id2-25',
                        text:'<%=testiPortale.get("menu_2_25")%>'},
                    {id:'id2-26',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/NormeComuni.jsp?set_id=id2/id2-26',
                        text:'<%=testiPortale.get("menu_2_26")%>'},
                    {id:'id2-27',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/CondizioniDiAttivazione.jsp?set_id=id2/id2-27',
                        text:'<%=testiPortale.get("menu_2_27")%>'},
                    {id:'id2-28',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/SequenzaInterventi.jsp?set_id=id2/id2-28',
                        text:'<%=testiPortale.get("menu_2_28")%>'}
    <%if (utente.getRuolo().equals("D")) {%>
                        ,
                    {id:'id2-29',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Template.jsp?set_id=id2/id2-29',
                        text:'<%=testiPortale.get("menu_2_29")%>'}
    <% } %>
                        ,
                    {id:'id2-30',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/ImmaginiTemplate.jsp?set_id=id2/id2-30',
                        text:'<%=testiPortale.get("menu_2_30")%>'},
                    {id:'id2-31',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/TemplatesVari.jsp?set_id=id2/id2-31',
                        text:'<%=testiPortale.get("menu_2_31")%>'},
                    {id:'id2-32',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/DuplicazioneInterventi.jsp?set_id=id2/id2-32',
                        text:'<%=testiPortale.get("menu_2_32")%>'},
                    {id:'id2-33',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/ProceduraGuidata.jsp?set_id=id2/id2-33',
                        text:'<%=testiPortale.get("menu_2_33")%>',
                        children:[]},
                    {id:'id2-34',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Pubblicazione.jsp?set_id=id2/id2-34',
                        text:'<%=testiPortale.get("menu_2_34")%>',
                        children:[]
                 }]
            },
    <%}%>
            {id:'id3',
                iconCls:'no-icon',
                leaf:false,
                text:'<%=testiPortale.get("menu_3")%>',
                itemHref:false,
                singleClickExpand: true,
                children:[
    <% if (!utente.getRuolo().equals("A")) {%>
                    {id:'id3-1',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Allegati.jsp?set_id=id3/id3-1',
                        text:'<%=testiPortale.get("menu_3_1")%>'},
    <%}%>
                    {id:'id3-2',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/TestoCondizioni.jsp?set_id=id3/id3-2',
                        text:'<%=testiPortale.get("menu_3_2")%>'},
                    {id:'id3-3',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Normative.jsp?set_id=id3/id3-3',
                        text:'<%=testiPortale.get("menu_3_3")%>'},
                    {id:'id3-4',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Documenti.jsp?set_id=id3/id3-4',
                        text:'<%=testiPortale.get("menu_3_4")%>'},
                    {id:'id3-5',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/Href.jsp?set_id=id3/id3-5',
                        text:'<%=testiPortale.get("menu_3_5")%>'},
    <% if (!utente.getRuolo().equals("A")) {%>
                    {id:'id3-6',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/NormeInterventi.jsp?set_id=id3/id3-6',
                        text:'<%=testiPortale.get("menu_3_6")%>'},
    <%}%>
                    {id:'id3-7',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/StampaScheda.jsp?set_id=id3/id3-7',
                        text:'<%=testiPortale.get("menu_3_7")%>'}
    <% if (!utente.getRuolo().equals("A")) {%>
                    ,{id:'id3-8',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/InterventiCollegati.jsp?set_id=id3/id3-8',
                        text:'<%=testiPortale.get("menu_3_8")%>'}
                    ,{id:'id3-9',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/CondizioniNormative.jsp?set_id=id3/id3-9',
                        text:'<%=testiPortale.get("menu_3_9")%>'}
    <%}%>
                    ,{id:'id3-10',
                        iconCls:'no-icon',
                        leaf:true,
                        itemHref:'<%=basePath%>protected/FormularioAgenziaEntrate.jsp?set_id=id3/id3-10',
                        text:'<%=testiPortale.get("menu_3_10")%>'}
                ]
            },
            {id:'id4',
                iconCls:'no-icon',
                leaf:true,
                itemHref:'<%=basePath%>protected/Notifiche.jsp?set_id=id4',
                text:'<%=testiPortale.get("menu_4")%>',
                children:[]
            }
        ];

        for(var i = 0, len = json.length; i < len; i++) {
            root.appendChild(tree.getLoader().createNode(json[i]));
        };

