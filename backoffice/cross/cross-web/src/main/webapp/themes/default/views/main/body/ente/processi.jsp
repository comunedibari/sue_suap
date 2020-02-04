<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
%>

<script type="text/javascript">

    var url = getUrl();
    $(document).ready(function ()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: [
                        'ID',
                        '<spring:message code="ente.procedimenti.processo.codProcesso"/>',
                        '<spring:message code="ente.procedimenti.processo.desProcesso"/>',
                        '<spring:message code="ente.procedimenti.processo.selezionato"/>',
                        '<spring:message code="ente.procedimenti.azioni"/>'],
                    colModel:
                            [{
                                    name: 'idProcesso',
                                    index: 'idProcesso',
                                    hidden: true
                                },
                                {
                                    name: 'codProcesso',
                                    index: 'codProcesso'
                                },
                                {
                                    name: 'desProcesso',
                                    index: 'desProcesso'
                                },
                                {
                                    name: 'selezionato',
                                    index: 'selezionato',
                                    formatter: function (cellvalue) {
                                        if (cellvalue == true) {
                                            cellvalue = 'SELEZIONATO';
                                        } else {
                                            cellvalue = '';
                                        }
                                        return cellvalue;
                                    }
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function (cellvalue, options, rowObject) {
                                        var link = '<div  class="collegaProcessoContainer gridActionContainer"><a href="${path}/ente/salvaProcesso.htm?idProcesso=' + rowObject["idProcesso"] + '" ><img src="${path}/themes/default/css/images/add_processo.png"></a></div>';
                                        return link;
                                    }
                                }
                           ],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'idProcesso',
                    viewrecords: true,
                    sortorder: "asc",
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

<div id="impostazioni_div">
    <%--<h2 style="text-align: center"><spring:message code="ente.procedimenti.processo.title"/>230</h2>--%>
    <tiles:insertAttribute name="ricerca" /> 
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="buttonHolder">
        <a href="<%=path%>/ente/procedimenti.htm" class="secondaryAction">&larr; Indietro</a>
    </div>
</div>


