<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {
        $("#list").jqGrid
                ({
                    url: url,
//                    id: 'idEnte',
                    datatype: "json",
                    colNames: [
                        '<spring:message code="ente.id"/>',
                        '<spring:message code="ente.descrizione"/>',
                        '<spring:message code="ente.citta"/>',
                        '<spring:message code="ente.provincia"/>',
                        '<spring:message code="ente.email"/>',
                        '<spring:message code="ente.pec"/>',
                        '<spring:message code="ente.telefono"/>',
                        '<spring:message code="ente.azioni"/>'],
                    colModel:
                            [{
                                    name: 'idEnte',
                                    index: 'idEnte',
                                    hidden: true
                                },
                                {
                                    name: 'descrizione',
                                    index: 'descrizione'
                                },
                                {
                                    name: 'citta',
                                    index: 'citta'
                                },
                                {
                                    name: 'provincia',
                                    index: 'provincia'
                                },
                                {
                                    name: 'email',
                                    index: 'email'
                                },
                                {
                                    name: 'pec',
                                    index: 'pec'
                                },
                                {
                                    name: 'telefono',
                                    index: 'telefono'
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div class="gridActionContainer">\n\
                                                        <a href="${path}/ente/modifica.htm?idEnte=' + rowObject["idEnte"] + '" ><img src="${path}/themes/default/css/images/pencil.png"></a>\n\
                                                        <a href="#" onClick="eliminaEnte('+rowObject["idEnte"]+')" class="elimina_ente"><img src="${path}/themes/default/css/images/basket.png"></a>\n\
                                                    </div>';
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
                    rowNum: "${rowNum}",
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
//            ,
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
////                            var id = $(".list_azioni")[i].parentNode.id;
//                            var id = $($(".list_azioni")[i]);
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
    <tiles:insertAttribute name="body_error" />
    <div class="table-add-link">

        <a href="${path}/ente/aggiungi.htm" class="addgenerico" alt="<spring:message code="ente.aggiungi"/>" title="<spring:message code="ente.aggiungi"/>">
            <spring:message code="ente.aggiungi"/>
        </a>
    </div>
    <br />
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <%--<div class="hidden" id="button">
        <div class="centra_impostazzioni_bottoni">
            <form action="${path}/ente/modifica.htm" method="post" class="gridForm">
                <input type="hidden" name="idEnte" value="_ID_">
                <button type="submit" class="modifica_ente ui-button ui-widget ui-corner-all ui-button-text-only " title="<spring:message code="ente.alt.modifica"/>"> <!-- ui-state-default  -->

                </button>
            </form>
            <form class="gridForm">  
                <button onclick="eliminaEnte(_ID_);
        return false;" class="cancella_ente ui-button ui-widget ui-corner-all ui-button-text-only cancella_ente" title="<spring:message code="ente.alt.cancella"/>"><!-- ui-state-default -->
                </button>
            </form>
            <script type="text/javascript" charset="utf-8">
                $(function() {
                    //$("#gridForm__ID_").preventDoubleSubmission('Conferma cancellazione', 'Proseguo ?', 'Attendi ...');
                });
            </script>
        </div>
    </div>--%>
</div>

<script>
    function eliminaEnte(codEnte) {
        var div = $("<div>");
        div.html("Confermi di voler eliminare l'ente selezionato?");
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma eliminazione ente",
                    buttons: {
                        Ok: function() {
                            var form = $("<form>");
                            form.attr({
                                name: 'idEnte',
                                style: "display:none",
                                method: 'post',
                                action: "${path}/ente/elimina.htm",
                                class: "gridForm"
                            });
                            form.append($('<input>',
                                    {
                                        'name': 'codEnte',
                                        'value': codEnte,
                                        'type': 'hidden'
                                    }));
                            $('body').append(form);
                            form.submit();
                            $(div).dialog('close');
                        },
                        Annulla: function() {
                            $(this).dialog('close');
                        }
                    }
                });
    }

</script>
