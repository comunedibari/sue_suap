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
<%@include file="../include/stdHeader.jsp" %>
<%
            String nomePagina = "PrtoceduraGuidata";
            String set_id = request.getParameter("set_id");
            session.setAttribute("mappatura", new Boolean(true));
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/master.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/user.css" />
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-all.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-lang-it.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/SearchField.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/SearchFieldForm.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/TripleTriggerField.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/SearchTripleFieldForm.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/menu.jsp"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/wego.jsp?nomePagina=<%=nomePagina%>"></script>
<title>Procedura guidata</title>
<script type="text/javascript">

    Ext.onReady(function() {
        var menu_div = Ext.get('tree_container');
        tree.render(menu_div);
        tree.selectPath("treepanel/source/<%=set_id%>",'id');
    });
</script>
</head>
<body>
    <div id="wrapper">

        <div id="header">
            <div id="header-sx"><h1><%=testiPortale.get("header")%></h1></div><div id="header-dx"></div>
        </div><!-- fine header -->

        <div id="module_wrapper">

            <div id="module_container">
                <div id="module_sx">
                    <div class="block_sx">
                        <div id="tree_container"></div>

                    </div>
                </div><!-- fine module_sx -->

                <div id="module_main">
                    <div id="form">
                        <% if (utente.getRuolo().equals("D") || utente.getRuolo().equals("C")) { %>
                        <img border="0" src="<%=basePath%>images/sistemaintegrato.jpg" alt="sistemaintegrato" usemap="#home">
                        <map name="home">
                            <area accesskey="m" shape="rect" coords="24,136,288,211" href="Mappatura.jsp?set_id=<%=set_id%>" alt="Mappatura" title="Mappatura" />
                            <area accesskey="i" shape="rect" coords="364,1,628,152" href="AttributiIntervento.jsp?set_id=<%=set_id%>" alt="Attributi Interventi" title="Attributi Interventi" />
                            <area accesskey="p" shape="rect" coords="364,172,628,294" href="AttributiProcedimento.jsp?set_id=<%=set_id%>" alt="Attributi Procedimenti" title="Attributi Procedimenti" />
                            <area accesskey="c" shape="rect" coords="674,39,938,114" href="InterventiComuni.jsp?set_id=<%=set_id%>" alt="Interventi Comuni" title="Interventi Comuni" />
                            <area accesskey="t" shape="rect" coords="674,172,938,297" href="RelazioniEnti.jsp?set_id=<%=set_id%>" alt="Relazioni Enti" title="Relazioni Enti" />
                            <area accesskey="a" shape="rect" coords="24,356,288,468" href="CondizioniDiAttivazione.jsp?set_id=<%=set_id%>" alt="Condizioni Di Attivazione" title="Condizioni Di Attivazione" />
                            <area accesskey="s" shape="rect" coords="364,323,628,398" href="GerarchiaSettori.jsp?set_id=<%=set_id%>" alt="Gerarchia Settori" title="Gerarchia Settori" />
                            <area accesskey="o" shape="rect" coords="364,424,628,500" href="GerarchiaOperazioni.jsp?set_id=<%=set_id%>" alt="Gerarchia Operazioni" title="Gerarchia Operazioni" />
                        </map>
                        <% } %>
                        <% if (utente.getRuolo().equals("B") || utente.getRuolo().equals("A")) { %>
                          <img border="0" src="<%=basePath%>images//sistemaintegratoutente.jpg" alt="sistemaintegrato utente" usemap="#home1">
                            <map name="home1">
                            <area accesskey="m" shape="rect" coords="184,253,447,314" href="Mappatura.jsp?set_id=<%=set_id%>" alt="Mappatura" title="Mappatura" />
                            <area accesskey="i" shape="rect" coords="525,106,787,254" href="AttributiIntervento.jsp?set_id=<%=set_id%>" alt="Attributi Interventi" title="Attributi Interventi" />
                            <area accesskey="p" shape="rect" coords="525,278,787,398" href="AttributiProcedimento.jsp?set_id=<%=set_id%>" alt="Attributi Procedimenti" title="Attributi Procedimenti" />
                         </map>
                        <% } %>
                    </div>
                </div><!-- fine module_main -->
            </div>

        </div><!-- fine module_wrapper -->

    </div><!-- fine wrapper -->

</body>
</html>
