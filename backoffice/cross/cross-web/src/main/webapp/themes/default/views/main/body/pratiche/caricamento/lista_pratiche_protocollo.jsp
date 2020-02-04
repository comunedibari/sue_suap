<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    var url = getUrl();
//    function eliminaPraticaProtocollo(idPraticaProtocollo){
//        console.log('Elimino '+idPraticaProtocollo);        
//        wgf.utils.askConfirm(
//                'Conferma eliminazione', 'Sei sicuro di voler eliminare la pratica?',
//                function(){
//                    console.log('Conferma eliminazione');
//                },function(){
//                    console.log('Annulla eliminazione');
//                });
//    }
    
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url + "?idEnte=${enteSelezionato.idEnte}",
                    datatype: "json",
                    colNames: ['ID',
                        '<spring:message code="protocollo.protocollo"/>',
                        '<spring:message code="protocollo.oggetto"/>',
                        '<spring:message code="protocollo.dataprotocollazione"/>',
                        '<spring:message code="protocollo.stato"/>',
                        'UO',
                        '<spring:message code="protocollo.mittente"/>',
                        '<spring:message code="protocollo.incarico"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idProtocollo',
                                    index: 'idProtocollo',
                                    hidden: true,
                                    sortable: false
                                },
                                {
                                    name: 'descPraticaProtocollo',
                                    index: 'descPraticaProtocollo',
                                    sortable: false,                                    
                                    formatter: function(cellvalue, options, rowObject) {
                                        return (rowObject['descPraticaProtocollo']==='null/null')?'':rowObject['descPraticaProtocollo'];
                                    }
                                },
                                {
                                    name: 'oggetto',
                                    index: 'oggetto'
                                },
                                {
                                    name: 'dataProtocollazione',
                                    index: 'dataProtocollazione',
                                    datefmt: 'dd/mm/yyyy'
                                },
                                {
                                    name: 'stato',
                                    index: 'stato'
                                },
                                {
                                    name: 'destinatario',
                                    index: 'destinatario',
                                    sortable: false
                                },
                                {
                                    name: 'mittente',
                                    index: 'mittente',
                                    sortable: false
                                },
                                {
                                    name: 'desUtentePresaInCarico',
                                    index: 'desUtentePresaInCarico'
                                },
                                {
                                    name: 'azione',
                                    index: 'idProtocollo',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div class="gridActionContainer"><a href="${path}/pratiche/nuove/caricamento.htm?idPraticaProtocollo=' + rowObject["idProtocollo"] + '" ><img src="${path}/themes/default/css/images/seleziona_azzurro.png"></a>';
                                        //link += '<a href="#" onclick="eliminaPraticaProtocollo('+rowObject["idProtocollo"]+')" class="elimina_pratica_protocollo"><img src="/cross/themes/default/css/images/basket.png"></a></div>';
                                        return link;
                                    }
                                }
                            ],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    /*
                     <c:set var="page" value="1"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                     <c:set var="page" value="${filtroRicerca.page}"/>
                     </c:if>
                     */
                    page: '${page}',
                    /*
                     <c:set var="orderColumn" value="idProtocollo"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                     */
                    sortname: '${orderColumn}',
                    viewrecords: true,
                    /*
                     <c:set var="orderDirection" value="asc"/>
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
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});


        $("#select")
                .button({
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    }
                })
                .click(function() {
                    var menu = $(this).parent().next().show().position({
                        my: "left top",
                        at: "left bottom",
                        of: this
                    });
                    $(document).one("click", function() {
                        menu.hide();
                    });
                    return false;
                });

        $(".split-button-list").menu();

        $(".split-button-href").click(function(e) {
            var href = e.currentTarget;
            location.href = href;
        });

    });
</script>

<div id="accettazione_pratiche"> <!-- apertura_pratiche -->

    <div class="split-button" style="text-align: right">
        <div class="split-button-label">
            <button id="select">Nuova pratica</button>
        </div>
        <ul class="split-button-list">
            <c:if test="${enablePraticheManuali }">
                <li>
                    <a class="split-button-href" title="<spring:message code="menu.nuovaGrafica.pratica.apertura.manuale"/>" href="${path}/pratiche/nuove/caricamento.htm"><spring:message code="menu.nuovaGrafica.pratica.apertura.manuale"/></a>
                </li>
            </c:if>
            <c:if test="${enablePraticheProtocolloRicerca }">
                <li>
                    <a class="split-button-href" title="<spring:message code="menu.gestionepratiche.protocollo.ricerca"/>" href="${path}/pratiche/caricamento/daNumeroProtocollo.htm"><spring:message code="menu.gestionepratiche.protocollo.ricerca"/></a>
                </li>
            </c:if>
            <c:if test="${enablePraticheComunica }">
                <li>
                    <a class="split-button-href" title="<spring:message code="menu.gestionepratichecomunica"/>" href="${path}/comunica/index.htm"><spring:message code="menu.gestionepratichecomunica"/></a>
                </li>
            </c:if>
            <c:if test="${enablePraticheSuapFvg }">
                <li>
                    <a class="split-button-href" title="<spring:message code="menu.gestionepratichesuapfvg"/>" href="${path}/suapfvg/index.htm"><spring:message code="menu.gestionepratichesuapfvg"/></a>
                </li>
            </c:if>
        </ul>
    </div>

    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>

</div>