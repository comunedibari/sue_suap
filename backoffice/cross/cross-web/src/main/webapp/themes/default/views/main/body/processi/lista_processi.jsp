<%-- 
    Document   : lista_processi
    Created on : 21-Jan-2013, 12:15:51
    Author     : CS
--%>
<%//    String path = request.getContextPath();
//    String url = path + "/pratiche/nuove/apertura/dettaglio.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function () {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['id',
                        '<spring:message code="processo.codice"/>',
                        '<spring:message code="processo.descrizione"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idProcesso',
                                    index: 'idProcesso',
                                    hidden: true
                                },
                                {
                                    name: 'codProcesso',
                                    index: 'codProcesso'
                                },
                                {
                                    name: 'desProcesso',
                                    index: 'desProcesso'
                                },
                                {
                                    name: 'azione',
                                    index: 'idProcesso',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function (cellvalue, options, rowObject) {
                                        var link = '<div class=" gridActionContainer"><form action="${path}/processi/eventi/lista.htm" method="post" style="float: left"><input type="hidden" name="idProcesso" value="' + rowObject["idProcesso"] + '" ><button type="submit" class="gestione_eventi ui-button ui-widget ui-corner-all ui-button-text-only" title="<spring:message code="eventi.processo.gestioneeventi"/>"></button></form> <form action="${path}/processi/modifica.htm" method="post" style="float: left"><input type="hidden" name="idProcesso" value="' + rowObject["idProcesso"] + '"><button type="submit" class="modifica_ente ui-button ui-widget ui-corner-all ui-button-text-only " title="<spring:message code="eventi.processo.modifica"/>"></button></form></div>';
                                        return link;
                                    }
                                }
                            ],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'idProcesso',
                    viewrecords: true,
                    sortorder: "desc",
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
                });
    });
</script>
<div>
    <tiles:insertAttribute name="operazioneRiuscita" /> 
</div>
<div id="impostazioni_div">
    <%-- <h2 style="text-align: center"><spring:message code="eventi.processo.gestioneprocessi"/></h2> --%>
    <div class="table-add-link">

        <a href="${path}/processi/aggiungi.htm" class="addgenerico" alt="<spring:message code="eventi.processo.add"/>" title="<spring:message code="eventi.processo.add"/>">
            <spring:message code="eventi.processo.add"/>
        </a>
    </div>
    <br />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>
