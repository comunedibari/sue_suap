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
            String nomePagina = "AttributiProcedimento";
            String set_id = request.getParameter("set_id");
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/master.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/user.css" />
<%--
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-all.js"></script>
--%>
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-base-debug.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/ext-lang-it.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/SearchField.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/SearchFieldForm.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/TripleTriggerField.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/SearchTripleFieldForm.js"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/menu.jsp"></script>
<script type="text/javascript" src="<%=basePath%>javascript/lib/wego.jsp?nomePagina=<%=nomePagina%>"></script>
<script type="text/javascript" src="<%=basePath%>javascript/pages/attributiProcedimento.jsp?nomePagina=<%=nomePagina%>&set_id=<%=set_id%>"></script>
<title>Attributi Procedimento</title>
<script type="text/javascript">
    Ext.onReady(function(){
        var x = Ext.get('module_wrapper');
        x.setHeight(0);
    });
</script>
</head>
<body>
    <div id="wrapper">

        <div id="header">
            <div id="header-sx"><h1><%=testiPortale.get("header")%></h1></div><div id="header-dx"></div>
        </div><!-- fine header -->

        <div id="module_wrapper" style="height:'1%';">

            <div id="module_container">
                <div id="module_sx">
                    <div class="block_sx">
                        <div id="tree_container"></div>

                    </div>
                </div><!-- fine module_sx -->

                <div id="module_main">
                    <div id="form"></div>
                </div><!-- fine module_main -->
            </div>

        </div><!-- fine module_wrapper -->

    </div><!-- fine wrapper -->

</body>
</html>
