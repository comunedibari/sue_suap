<%
    String path = request.getContextPath();
    String url = path + "/documenti/protocollo/pratica/eventi/visualizza.htm";
    String creazioneEvento = path + "/pratica/evento/crea.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    //^^CS AGGIUNTA
//    var url = document.URL;
//    url= url.split("?");
//    if(url.length>0)
//    {
//        url0=url[0].replace(".htm","/ajax.htm");
//        url=url0+"?"+url[1];
//    }
//    else
//    {
//        url=url0;
//    } 
    var url = getUrl();
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['ID',
                        '<spring:message code="protocollo.dettaglio.eventi.descrizione"/>',
                        '<spring:message code="protocollo.dettaglio.eventi.verso"/>',
                        '<spring:message code="protocollo.dettaglio.eventi.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idEvento',
                                    index: 'idEvento',
                                    hidden: true
                                },
                                {
                                    name: 'descrizione',
                                    index: 'descrizione',
                                    sortable: false

                                },
                                {
                                    name: 'verso',
                                    index: 'verso',
                                    sortable: false
                                },
                                {
                                    name: 'azione',
                                    index: 'idEvento',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var form = '<form action="<%=creazioneEvento%>" method="post" ><input type="hidden" name="id_evento" value="' + rowObject["idEvento"] + '"><input type="hidden" name="idPratica" value="${idPratica}"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="protocollo.dettaglio.eventi.seleziona"/>"><div class="seleziona_pratica"></div></button></form>';
                                        return form;
                                    }
                                }
                            ],
                    rowNum: -1,
                    pager: '#pager',
                    sortname: 'idEvento',
                    viewrecords: true,
                    sortorder: "desc",
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
    <tiles:insertAttribute name="body_error" />
    <%--    <h2 style="text-align: center"><spring:message code="protocollo.dettaglio.eventi"/>111</h2> --%>
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="uniForm">
        <div class="buttonHolder">
            <a href="<%= path%>/documenti/protocollo/pratica.htm" class="secondaryAction">&larr; <spring:message code="protocollo.dettaglio.button.indietro"/></a>
        </div>
    </div>
</div>