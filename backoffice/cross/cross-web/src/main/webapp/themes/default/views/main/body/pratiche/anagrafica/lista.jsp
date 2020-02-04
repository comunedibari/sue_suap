<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {
        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: [
                        'ID',
                        '<spring:message code="anagrafica.ricerca.grid.tipologia"/>',
                        '<spring:message code="anagrafica.ricerca.grid.descrizione"/>',
                        '<spring:message code="anagrafica.ricerca.grid.partitaiva"/>',
                        '<spring:message code="anagrafica.ricerca.grid.codicefiscale"/>',
                        '<spring:message code="anagrafica.ricerca.grid.azioni"/>'],
                    colModel:
                            [{
                                    name: 'idAnagrafica',
                                    index: 'idAnagrafica',
                                    hidden: true
                                },
                                {
                                    name: 'tipoAnagrafica',
                                    index: 'tipoAnagrafica',
                                    sortable: false
                                },
                                {
                                    name: 'denominazione',
                                    index: 'denominazione',
                                    sortable: false
                                },
                                {
                                    name: 'partitaIva',
                                    index: 'partitaIva'
                                },
                                {
                                    name: 'codiceFiscale',
                                    index: 'codiceFiscale'
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div  id="button"><form action="${path}/gestione/anagrafiche/modifica.htm" method="post" class="gridForm" style="margin-left: 30%"><input type="hidden" name="idAnagrafica" value="' + rowObject["idAnagrafica"]+'"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="anagrafica.button.modifica"/>"><div class="bottone_gestisci"></div></button></form></div>';
                                        return link;
                                    }
                                }
                            ],
                     <c:set var="page" value="1"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                     <c:set var="page" value="${filtroRicerca.page}"/>
                     </c:if>
                    page: '${page}',
                     <c:set var="orderColumn" value="idAnagrafica"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                    sortname: '${orderColumn}',
                     <c:set var="orderDirection" value="desc"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderDirection!=null}">
                     <c:set var="orderDirection" value="${filtroRicerca.orderDirection}"/>
                     </c:if>
                    sortorder: "${orderDirection}",
                     <c:set var="rowNum" value="10"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.limit!=null}">
                     <c:set var="rowNum" value="${filtroRicerca.limit}"/>
                     </c:if>
                    rowNum: "${rowNum}",
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    jsonReader: {
                        repeatitems: false,
                        id: "0"
                    },
                    height: 'auto',
                    width: $('.tableContainer').width()
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
    });
</script>
<div>
    <tiles:insertAttribute name="operazioneRiuscita" /> 
</div>
<div id="apertura_pratiche">
    <div class="table-add-link">
        <a href="<%=path%>/gestione/anagrafiche/nuova.htm" class="inserisciAnagrafica" alt="<spring:message code="anagrafica.ricerca.add"/>" title="<spring:message code="anagrafica.ricerca.add"/>">
            <spring:message code="anagrafica.ricerca.add"/>
        </a>
    </div>
    <br />
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>