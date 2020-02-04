<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
    var codiceEmail = rowObject['esitoCaricamento'];
    if (codiceEmail === "Positivo") {
    	return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Ok" alt="Ok" src="${path}/themes/default/images/icons/accept.png" width="18" /></div>';
        
    } else {
    	return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Errore" alt="Errori" src="${path}/themes/default/images/icons/exclamation.png" width="18" /></div>';
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
    var idPratica = rowObject["idPratica"];
    var link = '';
    if(idPratica!=null){
    	link =  '<div class="gridActionContainer">' +
        '<a title="<spring:message code="gestione.button.gestisci"/>" href="${path}/pratiche/dettaglio.htm?id_pratica=' + rowObject["idPratica"] + '&fromImportXml=true" ><img src="${path}/themes/default/css/images/seleziona_verde.png"></a>' +
        '</div>'; 
    }
   
    return link;
}

function gestionePraticheIdentificativoPraticaOrProtocolloFormatter(cellvalue, options, rowObject) {
    var identificativoPratica = rowObject["identificativoPratica"];
    var protocollo = rowObject["protocollo"];
    return protocollo ? protocollo : identificativoPratica;
}


var url = getUrl();
debugger;

function setLastUpdate() {
    var now = getTodayDateString();
    $('.last_update').text(now);
}

var identificativi = new String(${identificativiPraticaXML});
    $(document).ready(function() {


        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colModel:${gestioneCaricamentoPraticheColumnModel},
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
                    caption: 'Risultato Caricamento Pratiche',
                    height: 'auto',
                    width: $('.tableContainer').width()
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
        
        setLastUpdate();
    });

    </script>
    
<div id="impostazioni_div">
   
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>
<spring:message code="console.lastupdate"/>: <span class="last_update"></span>