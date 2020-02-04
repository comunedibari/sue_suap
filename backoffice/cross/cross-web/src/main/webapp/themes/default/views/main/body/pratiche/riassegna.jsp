<%
    String path = request.getContextPath();
    String url = path + "/pratiche/riassegna/selezione.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    $(function() {
        $("#list").jqGrid
                ({
                    url: getUrl(),
                    datatype: "json",
                    colNames: ['<spring:message code="pratica.utente.nominativo"/>',
                        '<spring:message code="pratica.utente.username"/>',
                        '<spring:message code="pratica.utente.codicefiscale"/>',
                        '<spring:message code="pratica.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'nominativo',
                                    index: 'nominativo',
                                    width: 55
                                },
                                {
                                    name: 'username',
                                    index: 'username'

                                },
                                {
                                    name: 'codiceFiscale',
                                    index: 'codiceFiscale'
                                },
                                {
                                    name: 'codiceUtente',
                                    index: 'codiceUtente',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div id="button">' +
                                                '<form action="<%=url%>" method="post" >' +
                                                '<input type="hidden" id="id_utente" name="idUtenteAssegnatario" value="' + rowObject["codiceUtente"] + '">' +
                                                '<input type="hidden" id="adminOnProcEnte" name="adminOnProcEnte" value="' + rowObject["adminOnProcEnte"] + '">' +
                                                '<input type="hidden" name="idPratica" value="${idPratica}">' +
                                                '<input type="hidden" name="from" value="${from}">' +
                                                '<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="pratica.button.assegna"/>">' +
                                                '<div class="bottone_assegna_utente">' +
                                                '</div></button></form></div> ';
                                        return link;
                                    }
                                }],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'nominativo',
                    viewrecords: true,
                    sortorder: "desc",
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});;
    });
</script>
<div id="apertura_pratiche">
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>
<div style="float:left;">
    <a class="secondaryAction" href="<%=path%>/pratiche/${from}.htm">&larr; Indietro</a>
</div>    
