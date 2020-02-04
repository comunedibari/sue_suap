<%-- 
    Document   : gestioneEndoprocedimenti
    Created on : 28-feb-2014, 17.00.08
    Author     : GabrieleM
--%>
<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<script type="text/javascript" src="<c:url value="/javascript/jquery.dynatree.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/ui.dynatree/ui.dynatree.css" />" media="all"/>

<script type="text/javascript">

    var treeData = ${tree};
    $(function() {
        $("#alberoProcedimentiEnti").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function(select, node) {
//                // Get a list of all selected nodes, and convert to a key array:
//                var selKeys = $.map(node.tree.getSelectedNodes(), function(node) {
//                    return node.data.key;
//                });
//
//                // Get a list of all selected TOP nodes
//                var selRootNodes = node.tree.getSelectedNodes(true);
//                // ... and convert to a key array:
//                var selRootKeys = $.map(selRootNodes, function(node) {
//                    return node.data.key;
//                });
                $("#selected").attr("value", $.map($("#alberoProcedimentiEnti").dynatree("getSelectedNodes"), function(node) {
                    return node.data.key;
                }).join(","));
            },
            onDblClick: function(node, event) {
                node.toggleSelect();
            },
            onKeydown: function(node, event) {
                if (event.which === 32) {
                    node.toggleSelect();
                    return false;
                }
            }
        });
    });
</script>

<h1>Gestione endoprocedimenti</h1>

<div>
    <tiles:insertAttribute name="body_error" />
</div>
<p class="description">
    Si può selezionare un singolo procedimento oppure tutti i procedimenti propri di un ente spuntando la checkbox relativa all'ente.
</p>
<div id="alberoProcedimentiEnti"></div>


<div class="buttonHolder">
    <form id="submitForm" action="<%=path%>/ente/endoprocedimenti/salva.htm" class="uniForm inlineLabels" method="post" >
        <button>Salva modifiche</button>
        <input id="selected" name ="selected" type="hidden" value="nessunaModifica" >
        <input type="hidden" id="idEnte" name ="idEnte" type="hidden" value=${idEnte} >
    </form>
    <a href="<%=path%>/ente/modifica.htm" class="secondaryAction">&larr; Indietro</a>
</div>



