<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="listGridUrl" value="${path}/pratiche/nuove/apertura/ajax.htm"/>
<c:set var="dettaglioAperturaPraticaUrl" value="${path}/pratiche/nuove/apertura/dettaglio.htm"/>
<c:set var="riassegnaPraticaUrl" value="${path}/pratica/riassegna.htm"/>

<script type="text/javascript">
    function displayConfirmDialog(idPratica, isAdmin) {
        var urlToRedirect = '${riassegnaPraticaUrl}?from=nuove/apertura&idPratica=' + idPratica;
        if (isAdmin) {
            window.open(urlToRedirect, "_self");
        } else {
            var confirmDialog = $('<div />').attr({
                title: 'Conferma azione'
            }).html('Vuoi veramente prendere in carico la pratica?');

            $(confirmDialog).dialog({
                resizable: false,
                height: 180,
                modal: true,
                buttons: {
                    Ok: function () {
                        $(this).dialog("close");
                        window.open(urlToRedirect, "_self");
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });
        }
    }

    $(document).ready(function ()
    {

        $("#list").jqGrid
                ({
                    url: "${listGridUrl}",
                    datatype: "json",
                    colNames: ['<spring:message code="pratica.id"/>',
                    	'<spring:message code="pratica.identificativo.pratica"/>',
                        '<spring:message code="pratica.data.ricezione"/>',
                        '<spring:message code="pratica.descrizione"/>',
                        '<spring:message code="pratiche.elenco.protocollo"/>',
                        '<spring:message code="pratiche.elenco.ente"/>',
                        '<spring:message code="pratiche.elenco.comune"/>',
                        '<spring:message code="pratiche.elenco.richiedente"/>',
                        '<spring:message code="pratiche.elenco.incarico"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idPratica',
                                    index: 'idPratica',
                                    "width" : 55,
                                    sortable: true
                                    //hidden: true
                                },
                                {
                                    name: 'identificativoPratica',
                                    index: 'identificativoPratica',
                                    "width" : 160,
                                    sortable: true
                                },
                                {
                                    name: 'dataRicezione',
                                    index: 'dataRicezione',
                                    srcformat: "NNN dd, y H:mm:ss a",
                                    datefmt: 'dd/mm/y'

                                },
                                {
                                    name: 'oggettoPratica',
                                    index: 'oggettoPratica'
                                },
                                {
                                    name: 'protocollo',
                                    index: 'protocollo',
                                    sortable: true
                                },
                                {
                                    name: 'ente',
                                    index: 'ente',
                                    sortable: true
                                },
                                {
                                    name: 'comune',
                                    index: 'comune',
                                    sortable: true
                                },
                                {
                                    name: 'richiedenteDaXml',
                                    index: 'richiedenteDaXml',
                                    sortable: false
                                },
                                {
                                    name: 'inCarico',
                                    index: 'inCarico',
                                    sortable: false
                                },
                                {
                                    name: 'azione',
                                    index: 'idPratica',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function (cellvalue, options, rowObject) {
                                        var isAdmin = rowObject["adminOnProcEnte"];
                                        var isSameUser = rowObject["isSameUser"];
                                        var displayDialog = ' onclick="displayConfirmDialog(\'' + rowObject["idPratica"] + '\', ' + isAdmin + ')"';
                                        var riassegnaButton = '<a title="<spring:message code="gestione.button.riassegna"/>" ' + displayDialog + '><img src="${path}/themes/default/css/images/assegnare_verde.png" style="cursor: pointer"></a>';
                                        if (isSameUser && !isAdmin) {
                                            riassegnaButton = '';
                                        }
                                        var link = '<div class="gridActionContainer">' +
                                                '<a href="${dettaglioAperturaPraticaUrl}?idPratica=' + rowObject["idPratica"] + '" ><img src="${path}/themes/default/css/images/seleziona_verde.png"></a>' +
                                                riassegnaButton +
                                                '</div>';
                                        return link;
                                    }
                                }
                            ],
                    /*
                     <c:set var="page" value="1"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                     <c:set var="page" value="${filtroRicerca.page}"/>
                     </c:if>
                     */
                    page: '${page}',
                    /*
                     <c:set var="orderColumn" value="idPratica"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                     */
                    sortname: '${orderColumn}',
                    /*
                     <c:set var="orderDirection" value="desc"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderDirection!=null}">
                     <c:set var="orderDirection" value="${filtroRicerca.orderDirection}"/>
                     </c:if>
                     */
                    sortorder: "${orderDirection}",
                    /*
                     <c:set var="rowNum" value="10"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.limit!=null}">
                     <c:set var="rowNum" value="${filtroRicerca.limit}"/>
                     </c:if>
                     */
                    rowNum: "${rowNum}",
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
                    gridComplete: function () {
                    }
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
    });
</script>
<div id="apertura_pratiche">

    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>


