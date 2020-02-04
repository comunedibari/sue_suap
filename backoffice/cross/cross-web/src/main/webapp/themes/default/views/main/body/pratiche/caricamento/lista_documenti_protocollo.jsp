<%
    String path = request.getContextPath();
    String url = path + "/documenti/protocollo/pratica.htm";
    String download = path + "/download/protocollo.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script language="javascript">
    var path = '${path}';
</script>

<c:set var="page" value="1"/>
<c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
    <c:set var="page" value="${filtroRicerca.page}"/>
</c:if>

<c:set var="orderColumn" value="idProtocollo"/>
<c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
    <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
</c:if>

<c:set var="orderDirection" value="desc"/>
<c:if test="${filtroRicerca!=null && filtroRicerca.orderDirection!=null}">
    <c:set var="orderDirection" value="${filtroRicerca.orderDirection}"/>
</c:if>          

<c:set var="rowNum" value="10"/>
<c:if test="${filtroRicerca!=null && filtroRicerca.limit!=null}">
    <c:set var="rowNum" value="${filtroRicerca.limit}"/>
</c:if>
<script type="text/javascript">

    var url = getUrl();
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['ID',
                        '<spring:message code="protocollo.tipodocumento"/>',
                        '<spring:message code="protocollo.annofascicolo"/>',
                        '<spring:message code="protocollo.fascicolo"/>',
                        '<spring:message code="protocollo.annoriferimento"/>',
                        '<spring:message code="protocollo.protocollo"/>',
                        '<spring:message code="protocollo.oggetto"/>',
                        '<spring:message code="protocollo.dataprotocollazione"/>',
                        '<spring:message code="protocollo.stato"/>',
                        '<spring:message code="protocollo.mittente"/>',
                        '<spring:message code="protocollo.incarico"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idProtocollo',
                                    index: 'idProtocollo',
                                    hidden: true
                                },
                                {
                                    name: 'tipoDocumento',
                                    index: 'tipoDocumento',
                                    sortable: false
                                },
                                {
                                    name: 'annoFascicolo',
                                    index: 'annoFascicolo'
                                },
                                {
                                    name: 'nFascicolo',
                                    index: 'nFascicolo'

                                },
                                {
                                    name: 'annoRiferimento',
                                    index: 'annoRiferimento'
                                },
                                {
                                    name: 'nProtocollo',
                                    index: 'nProtocollo'
                                },
                                {
                                    name: 'oggetto',
                                    index: 'oggetto'
                                },
                                {
                                    name: 'dataRiferimento',
                                    index: 'dataRiferimento',
                                    datefmt: 'dd/mm/yyyy'
                                },
                                {
                                    name: 'stato',
                                    index: 'stato'
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
                                        var link = '<div class="gridActionContainer">\n\
                                                        <a href="<%=url%>?idProtocollo=' + rowObject["idProtocollo"] + '" ><img src="${path}/themes/default/css/images/seleziona_azzurro.png"></a>\n\
                                                        <a href="<%=download%>?idProtocollo=' + rowObject["idProtocollo"] + '" ><img src="${path}/themes/default/css/images/eye_azzurro.png"></a>\n\
                                                        <a onClick = "cancellaDocumento(' + rowObject["idProtocollo"] + ')" href = "#"><img src="' + path + '/themes/default/css/images/basket.png"></a>\n\
                                                    </div>';
                                        return link;
                                    }
                                },
                            ],
                    page: '${page}',
                    sortname: '${orderColumn}',
                    sortorder: '${orderDirection}',
                    rowNum: '${rowNum}',
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
    function cancellaDocumento(documentoId) {
        var div = $("<div>");
        div.html("Confermi di voler eliminare il documento dalla lista?");
        $(div).dialog(
                {
                    modal: true,
                    title: "Eliminazione documento",
                    buttons: {
                        Ok: function() {
                            var parameters = {};
                            parameters.documentoId = documentoId;

                            $.ajax({
                                type: 'POST',
                                url: path + '/documenti/nuovi/protocollo/cancella_documento.htm',
                                data: parameters,
                                success: function(data) {
                                    var esito = data.success;
                                    var messaggio = data.msg;
                                    if (String(esito) === 'true') {
                                        mostraMessaggioAjax(messaggio, "success");
                                    } else {
                                        mostraMessaggioAjax(messaggio, "error");
                                    }
                                    $("#list").trigger("reloadGrid");
                                },
                                dataType: 'json'
                            });

                            $(div).dialog('close');
                        },
                        Annulla: function() {
                            $(div).dialog('close');
                        }
                    }
                });
    }
</script>
<div id="accettazione_pratiche">
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>
