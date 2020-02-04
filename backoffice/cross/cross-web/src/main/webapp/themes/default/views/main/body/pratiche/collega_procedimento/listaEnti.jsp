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

        $('#list').jqGrid
                ({
                    url: url + "?" + $('#form_ricerca').serialize(),
                    datatype: 'json',
                    colNames: [
                        'ID',
                        '<spring:message code="anagrafica.ricerca.grid.descrizione"/>',
                        '<spring:message code="anagrafica.ricerca.grid.azioni"/>'],
                    colModel:
                            [{
                                    name: 'idEnte',
                                    index: 'idEnte',
                                    hidden: true
                                },
                                {
                                    name: 'descrizione',
                                    index: 'descrizione',
                                    width: 90,
                                    align: "left"
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    width: 10,
                                    align: "center",
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '        <form action="<%=path%>/pratiche/dettaglio/aggiungiProcedimento/inserisci.htm" method="post" class="gridForm" id="gridForm'+rowObject["idEnte"]+'"><input type="hidden" name="idEnte" value="'+rowObject["idEnte"]+'"><input type="hidden" name="idProcedimento" value="${ricerca.idProcedimento}"><button id="'+rowObject["idEnte"]+'" type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only collega-procedimento"><spring:message code="pratica.collegaAnagrafica.seleziona"/></button></form>';
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
                     <c:set var="orderColumn" value="idEnte"/>
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
                    rowNum: '${rowNum}',
                    jsonReader:
                            {
                                repeatitems: false,
                                id: '0'
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
                    gridComplete: function() {
                        var ids = $(".list_azioni");
                        for (var i = 0; i < ids.length; i++) {
                            var id = $(".list_azioni")[i].parentNode.childNodes[0].title;
                            var html = $("#button").html().replace(/_ID_/g, id);
                            $($(".list_azioni")[i]).html(html);
                        }
                    }
                });
    });
</script>
<div>
    <tiles:insertAttribute name="body_error" />
    <h2 style="text-align: center"><spring:message code="pratica.titolo.nuovoProcedimento.ente"/></h2>
    <h3 style="text-align: center">${procedimento.desProc}</h3>
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="hidden" id="button">
        <form action="<%=path%>/pratiche/dettaglio/aggiungiProcedimento/inserisci.htm" method="post" class="gridForm" id="gridForm__ID_">
            <input type="hidden" name="idEnte" value="_ID_">
            <input type="hidden" name="idProcedimento" value="${ricerca.idProcedimento}">
            <button id="button__ID_" type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only collega-procedimento">
                <spring:message code="pratica.collegaAnagrafica.seleziona"/>
            </button>
        </form>      
        <script type="text/javascript" charset="utf-8">
            $(function() {
                $("#gridForm__ID_").preventDoubleSubmission('Conferma inserimento', 'Proseguo ?', 'Attendi ...');
                $("#button__ID_").on('click', function(event) {
                    var form = $('#aggiungiProcedimentoForm');
                    form.data('tastoPremuto', 'submit');
                });
            });
        </script>
    </div>
    <form:form class="uniForm inlineLabels">
        <div class="buttonHolder">
            <a href="${path}/pratiche/dettaglio/aggiungiProcedimento.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        </div>
    </form:form>
</div>

