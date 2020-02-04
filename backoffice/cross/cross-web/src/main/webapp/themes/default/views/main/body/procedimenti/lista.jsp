<%-- 
    Document   : lista
    Created on : 18-Dec-2012, 11:29:52
    Author     : CS
--%>
<%
    String path = request.getContextPath();
    String url = path + "/gestione/procedimenti/modifica.htm";
    String elimina = path + "/gestione/procedimenti/elimina.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>--%>

<style>
    select{
        width:250px;
    }
</style>
<script type="text/javascript">
    var url = document.URL;
    url = url.replace(".htm", "/ajax.htm");
    $(document).ready(function()
    {
//        $.jgrid.no_legacy_api = true;
//        $.jgrid.useJSON = true;
//        $.jgrid.defaults = $.extend($.jgrid.defaults, {loadui: "enable"});

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    recreateForum: true,
                    colNames:
                            [
                                'idProcedimento',
                                'Descrizione',
                                'Tipo procedimento',
                                'Termini',
                                'Azioni'
                            ],
                    colModel:
                            [
                                {
                                    name: 'idProcedimento',
                                    index: 'idProcedimento',
                                    hidden: true
                                },
                                {
                                    name: 'desProcedimento',
                                    index: 'desProcedimento',
                                    classes: "desProcedimento",
                                    width: 550,
                                    sortable: true
                                },
                                {
                                    name: 'tipoProc',
                                    index: 'tipoProc',
                                    classes: "tipoProc",
                                    sortable: false
                                },
                                {
                                    name: 'termini',
                                    index: 'termini',
                                    classes: 'termini',
                                    sortable: false
                                },
                                {
                                    name: 'azioni',
                                    index: 'azioni',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div class="gridActionContainer"><form action="<%=url%>" method="post" style="float:left" class="gridForm"><input type="hidden" name="idProcedimento" value="' + rowObject["idProcedimento"] + '"><button type="submit" class="modifica_ente ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only modifica_ente " name="submitaction" value="modifica" title="<spring:message code="procedimento.modifica"/>"></button></form><form class="gridForm"><button onclick="eliminaProcedimento(' + rowObject["idProcedimento"] + ');return false;" class="cancella_ente ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only cancella_ente" name="submitaction" value="elimina" title="<spring:message code="procedimento.elimina"/>"></button></form></div>';
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
                     <c:set var="orderColumn" value="desProcedimento"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                     */
                    sortname: '${orderColumn}',
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
                    rowList: [5, 10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
//                    ondblClickRow: function(id)
//                    {
//                        modificaRiga(id);
//                    },
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
//                            var id = $(".list_azioni")[i].parentNode.childNodes[0].title;
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
<div>
    <tiles:insertAttribute name="body_error" />
</div>
<div id="impostazioni_div">
    <h2 style="text-align: center"><spring:message code="procedimento.lista.descrizione"/></h2>

    <div class="table-add-link">
        <a href="<%=url%>" >
            <spring:message code="procedimento.ricerca.aggiungi"/>
            <img title="<spring:message code="procedimento.ricerca.aggiungi"/>" alt="<spring:message code="procedimento.ricerca.aggiungi"/>" src="<%=path%>/themes/default/images/icons/add.png">
        </a>
    </div>
    <br/>
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <!--    <div class="hidden" id="button">
            <div class="centra_impostazzioni_bottoni">
                <form action="<%=url%>" method="post" style="float:left" class="gridForm">
                    <input type="hidden" name="idProcedimento" value="_ID_">
                    <button type="submit" class="modifica_ente ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only modifica_ente " name="submitaction" value="modifica" title="<spring:message code="procedimento.modifica"/>">
                    </button>
                </form>
                <form class="gridForm">
                    <button onclick="elimina(_ID_);
            return false;" class="cancella_ente ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only cancella_ente" name="submitaction" value="elimina" title="<spring:message code="procedimento.elimina"/>">
                    </button>
                </form>
            </div>
        </div>-->

</div>

<script>
    function eliminaProcedimento(idProcedimento) {
        var div = $("<div>");
        div.html("Confermi di voler eliminare il procedimento selezionato?")
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma eliminazione procedimento",
                    buttons: {
                        Ok: function() {
                            var form = $("<form>");
                            form.attr({
                                name: 'idProcedimento',
                                style: "display:none",
                                method: 'post',
                                action: "<%=elimina%>",
                                class: "gridForm"
                            });
                            form.append($('<input>',
                                    {
                                        'name': 'idProcedimento',
                                        'value': idProcedimento,
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