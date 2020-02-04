<%-- 
    Document   : lista_processi
    Created on : 21-Jan-2013, 12:15:51
    Author     : CS
--%>
<%
    String path = request.getContextPath();
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['id',
                        '<spring:message code="processo.codice"/>',
                        '<spring:message code="processo.descrizione"/>',
                        'Processo',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idEvento',
                                    index: 'idEvento',
                                    hidden: true
                                },
                                {
                                    name: 'codEvento',
                                    index: 'codEvento'
                                },
                                {
                                    name: 'descrizione',
                                    index: 'descrizione',
                                    sortable: false
                                },
                                {
                                    name: 'desProcesso',
                                    index: 'desProcesso',
                                    sortable: false
                                },
                                {
                                    name: 'azione',
                                    index: 'codEvento',
                                    classes: "list_azioni",
                                    sortable: false,
                                     formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div class=" gridActionContainer"><form action="${path}/processi/eventi/flusso.htm" method="post" style="float: left"><input type="hidden" name="idEvento" value="'+rowObject["idEvento"]+'" ><button type="submit" class="gestione_eventi ui-button ui-widget ui-corner-all ui-button-text-only" title="<spring:message code="eventi.processo.gestioneeventi"/>"></button></form> <form action="${path}/processi/eventi/modifica.htm" method="post" style="float: left"><input type="hidden" name="idEvento" value="'+rowObject["idEvento"]+'"><button type="submit" class="modifica_ente ui-button ui-widget ui-corner-all ui-button-text-only " title="<spring:message code="eventi.processo.modifica"/>"></button></form></div>';
                                        return link;
                                    }
                                },
                           ],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'idEvento',
                    viewrecords: true,
                    sortorder: "desc",
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
//                            var id = $(".list_azioni")[i].parentNode.id;
//                            var html = $("#button").html().replace(/_ID_/g, id);
//                            $($(".list_azioni")[i]).html(html);
//                        }
//                    }
                });
    });
</script>
<div>
    <tiles:insertAttribute name="operazioneRiuscita" /> 
</div>
<div id="impostazioni_div">
    <%--    <h2 style="text-align: center"><spring:message code="eventi.processo.gestioneeventi"/></h2> --%>
    <div class="table-add-link">
        <a href="<%=path%>/processi/eventi/aggiungi.htm" class="addgenerico"  alt="<spring:message code="eventi.add"/>" title="<spring:message code="eventi.add"/>">
            <spring:message code="eventi.add"/>

        </a>
    </div>
    <br />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
<!--    <div class="hidden" id="button">
        <div class="centra_impostazzioni_bottoni">
            <form action="<%=path%>/processi/eventi/modifica.htm" method="post" style="float: left">
                <input type="hidden" name="idEvento" value="_ID_">
                <button type="submit" class="modifica_ente  ui-button ui-widget ui-corner-all ui-button-text-only" title="<spring:message code="eventi.processo.modifica"/>">

                </button>
            </form>
            <form action="<%=path%>/processi/eventi/flusso.htm" method="post" style="float: left">
                <input type="hidden" name="idEvento" value="_ID_">
                <button type="submit" class="gestione_flusso ui-button ui-widget ui-corner-all ui-button-text-only" title="<spring:message code="eventi.processo.flusso"/>">

                </button>
            </form>
        </div>
    </div>-->
    <div class="uniForm inlineLabels">
        <div class="buttonHolder">
            <a href="<%=path%>/processi/lista.htm" class="secondaryAction">&larr; <spring:message code="processo.button.indietro"/></a>
        </div>
    </div> 
</div>