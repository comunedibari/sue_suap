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
                        '<spring:message code="utenti.id"/>',
                        '<spring:message code="utenti.username"/>',
                        '<spring:message code="utenti.cognome"/> <spring:message code="utenti.nome"/>',
                                                '<spring:message code="utenti.codicefiscale"/>',
                                                '<spring:message code="utenti.dataattivazione"/>',
                                                '<spring:message code="utenti.azioni"/>',
                                                'Status'],
                                            colModel:
                                                    [{
                                                            name: 'idUtente',
                                                            index: 'idUtente',
                                                            hidden: true
                                                        },
                                                        {
                                                            name: 'username',
                                                            index: 'username'
                                                        },
                                                        {
                                                            name: 'nominativo',
                                                            index: 'nominativo',
                                                            sortable: true
                                                        },
                                                        {
                                                            name: 'codiceFiscale',
                                                            index: 'codiceFiscale'
                                                        },
                                                        {
                                                            name: 'dataAttivazione',
                                                            index: 'dataAttivazione',
                                                            datefmt: 'dd/mm/yyyy'
                                                        },
                                                        {
                                                            name: 'status',
                                                            index: 'id',
                                                            classes: "status",
                                                            hidden: true
                                                        },
                                                        {
                                                            name: 'azione',
                                                            index: 'id',
                                                            classes: "list_azioni",
                                                            sortable: false,
                                                            formatter: function(cellvalue, options, rowObject) {
                                                                var status = rowObject["status"];
                                                                if (status === "ATTIVO") {
                                                                    var link = '<div class="gridActionContainer" id=' + status + '>\
                                                        <a href="${path}/utenti/modifica.htm?idUtente=' + rowObject["idUtente"] + '" ><img src="${path}/themes/default/css/images/pencil.png"></a>\
                                                        <a href="#" onClick="eliminaUtente(' + rowObject["idUtente"] + ')" ><img src="${path}/themes/default/css/images/basket.png"></a>\
                                                    </div>';
                                                                    return link;
                                                                } else if (status === "NON_ATTIVO") {
                                                                    var link = '<div class="gridActionContainer" id=' + status + '>\
                                                        <a href="${path}/utenti/modifica.htm?idUtente=' + rowObject["idUtente"] + '" ><img src="${path}/themes/default/css/images/pencil.png"></a>\
                                                        <a href="#" onClick="riabilitaUtente(' + rowObject["idUtente"] + ')" ><img src="${path}/themes/default/css/images/ricollega.png"></a>\
                                                    </div>';
                                                                    return link;
                                                                }
                                                            }
                                                        }

                                                    ],
                                            rowList: [5, 10, 20, 30, 100],
                                            pager: '#pager',
                                            /*
                                             <c:set var="page" value="1"/>
                                             <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                                             <c:set var="page" value="${filtroRicerca.page}"/>
                                             </c:if> 
                                             */
                                            page: '${page}',
                                            /*
                                             <c:set var="orderColumn" value="idUtente"/>
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
<tiles:insertAttribute name="body_error" />
<div id="impostazioni_div">
    <%--    <h2 style="text-align: center"><spring:message code="utenti.title.elenco"/>160</h2>--%>
    <div class="table-add-link">


        <a href="<%=path%>/utenti/aggiungi.htm" class="inserisciAnagrafica" alt="<spring:message code="utenti.button.nuovo"/>" title="<spring:message code="utenti.button.nuovo"/>">
            <spring:message code="utenti.button.nuovo"/>
        </a>

    </div>
    <br />
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>

</div>

<script>
    function eliminaUtente(codUtente) {
        var div = $("<div>");
        div.html("<spring:message code="utente.disattiva.conferma"/>");
        $(div).dialog(
                {
                    modal: true,
                    title: "<spring:message code="utente.disattiva.dialogo"/>",
                    buttons: {
                        Ok: function() {
                            var form = $("<form>");
                            form.attr({
                                name: 'idUtente',
                                style: "display:none",
                                method: 'post',
                                action: "<%=path%>/utenti/elimina.htm",
                                class: "gridForm"
                            });
                            form.append($('<input>',
                                    {
                                        'name': 'codUtente',
                                        'value': codUtente,
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

<script>
    function riabilitaUtente(codUtente) {
        var form = $("<form>");
        form.attr({
            name: 'idUtente',
            method: 'post',
            action: "<%=path%>/utenti/attiva.htm",
            class: "gridForm"
        });
        form.append($('<input>',
                {
                    'name': 'codUtente',
                    'style': 'display:none',
                    'value': codUtente,
                    'type': 'hidden'
                }));
        $('body').append(form);
        var div = $("<div>");
        div.html("Confermi di voler riattivare l'utente selezionato?");
        $(div).dialog(
                {
                    modal: true,
                    title: "<spring:message code="utente.attiva.dialogo"/>",
                    buttons: {
                        Ok: function() {
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
