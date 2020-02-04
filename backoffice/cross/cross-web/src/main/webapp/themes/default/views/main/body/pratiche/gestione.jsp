<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="riassegnaPraticaUrl" value="${path}/pratica/riassegna.htm"/>
<script type="text/javascript">

    function displayConfirmDialog(idPratica, isAdmin) {
        var urlToRedirect = '${riassegnaPraticaUrl}?from=gestisci&idPratica=' + idPratica;
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
                    Ok: function() {
                        $(this).dialog("close");
                        window.open(urlToRedirect, "_self");
                    },
                    Cancel: function() {
                        $(this).dialog("close");
                    }
                }
            });
        }
    }

    function gestionePraticheStatoMailFormatter(cellvalue, options, rowObject) {
        var codiceEmail = rowObject['codStatoEmail'];
        if (codiceEmail === "E") {
            return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Errore" alt="Errori" src="${path}/themes/default/images/icons/exclamation.png" width="18" /></div>';
        } else if (codiceEmail === "X") {
            return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Stato indefinito" alt="Stato indefinito" src="${path}/themes/default/images/icons/icon_alert.gif" width="18" /></div>';
        } else {
            return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Ok" alt="Ok" src="${path}/themes/default/images/icons/accept.png" width="18" /></div>';
        }
    }

    function gestionePraticheActionsFormatter(cellvalue, options, rowObject) {
        var isAdmin = rowObject["adminOnProcEnte"];
        var isSameUser = rowObject["isSameUser"];
        var displayDialog = ' onclick="displayConfirmDialog(\'' + rowObject["idPratica"] + '\', ' + isAdmin + ')"';
        var riassegnaButton = '<a title="<spring:message code="gestione.button.riassegna"/>" ' + displayDialog + '><img src="${path}/themes/default/css/images/assegnare_verde.png" style="cursor: pointer"></a>';
        if (isSameUser && !isAdmin) {
            riassegnaButton = '';
        }
        var link = '<div class="gridActionContainer">' +
                '<a title="<spring:message code="gestione.button.gestisci"/>" href="${path}/pratiche/dettaglio.htm?id_pratica=' + rowObject["idPratica"] + '" ><img src="${path}/themes/default/css/images/seleziona_verde.png"></a>' +
                riassegnaButton +
                '</div>';
        return link;
    }

    function gestionePraticheIdentificativoPraticaOrProtocolloFormatter(cellvalue, options, rowObject) {
        var identificativoPratica = rowObject["identificativoPratica"];
        var protocollo = rowObject["protocollo"];
        return protocollo ? protocollo : identificativoPratica;
    }
    
    function gestionePraticheIntegrazione(cellvalue, options, rowObject) {
//         var integra = rowObject['integrazione'];
//         if (integra === "S") {
//             return '<div style="text-align: center; width: 100%; height: 100%; background-color:lightgreen">Si</div>';
//         } else {
//         	return '<div style="text-align: center; width: 100%; height: 100%;">No</div>';
//         }
        if (cellvalue === "S"){
            rowsToColor[rowsToColor.length] = options.rowId;
            cellvalue = "SI";
        }
        else
        	cellvalue = "NO";
        return cellvalue;
    }

    var rowsToColor = [];
    var url = getUrl();
    $(document).ready(function() {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colModel:${gestionePraticheColumnModel},
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
                    hidegrid: false,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            }
                    ,
                    caption: '',
                    height: 'auto',
                    gridComplete: function () {
                        for (var i = 0; i < rowsToColor.length; i++) {
                        	debugger;
                            var status = $("#" + rowsToColor[i]).find("td").eq(12).html();
                            if (status === "SI") {
                                $("#" + rowsToColor[i]).find("td").css("background-color", "#82d676");
                                $("#" + rowsToColor[i]).find("td").css("color", "black");
                            }
                        }
                     },
                    width: $('.tableContainer').width()
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
