<%
    String path = request.getContextPath();
    String url = path + "/documenti/protocollo/pratica/eventi.htm";
    String crea = path + "/pratica/evento/index.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {
        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['<spring:message code="pratica.id"/>',
                        '<spring:message code="pratica.data.ricezione"/>',
                        '<spring:message code="pratica.descrizione"/>',
                        '<spring:message code="pratiche.elenco.protocollo"/>',
                        '<spring:message code="pratiche.elenco.ente"/>',
                        '<spring:message code="pratiche.elenco.incarico"/>',
                        '<spring:message code="pratiche.elenco.comune"/>',
                        '<spring:message code="pratiche.elenco.mittente"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idPratica',
                                    index: 'idPratica',
                                    hidden: true
                                },
                                {
                                    name: 'dataRicezione',
                                    index: 'dataRicezione',
                                    datefmt: 'dd/mm/yyyy'

                                },
                                {
                                    name: 'oggettoPratica',
                                    index: 'oggettoPratica'
                                },
                                {
                                    name: 'protocollo',
                                    index: 'protocollo',
                                    sortable: false
                                },
                                {
                                    name: 'ente',
                                    index: 'ente',
                                    sortable: false
                                },
                                {
                                    name: 'inCarico',
                                    index: 'inCarico',
                                    sortable: false
                                },
                                {
                                    name: 'comune',
                                    index: 'comune',
                                    sortable: false
                                },
                                {
                                    name: 'richiedente',
                                    index: 'richiedente',
                                    sortable: false
                                },
                                {
                                    name: 'azione',
                                    index: 'idPratica',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var form = '<form action="<%=crea%>" method="post" ><input type="hidden" name="id_pratica" value="' + rowObject["idPratica"] + '"/><input type="hidden" name="verso" value="I" /><input type="hidden" name="id_protocollo" value="${id_protocollo}" /><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="protocollo.dettaglio.pratica.button.selezionaevento"/>"><div class="seleziona_pratica"></div></button></form>';
//                                        var form = '<form action="<%=url%>" method="post" ><input type="hidden" name="idPratica" value="' + rowObject["idPratica"] + '"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="protocollo.dettaglio.pratica.button.selezionaevento"/>"><div class="seleziona_pratica"></div></button></form>';
                                        return form;
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
                    hidegrid: false,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
    });
</script>
<div id="accettazione_pratiche">
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="uniForm">
        <div class="buttonHolder">
            <a href="<%= path%>/documenti/nuovi/protocollo.htm" class="secondaryAction">&larr; <spring:message code="protocollo.dettaglio.button.indietro"/></a>
        </div>
    </div>
</div>