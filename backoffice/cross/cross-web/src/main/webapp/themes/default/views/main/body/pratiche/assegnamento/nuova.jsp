<%
    String path = request.getContextPath();
    String url = path + "/pratiche/nuove/assegna.htm";
    String download = path + "/download/pratica.htm";
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
                    colNames: ['<spring:message code="pratica.id"/>',
                        '<spring:message code="pratica.data.ricezione"/>',
                        '<spring:message code="pratica.descrizione"/>',
                        '<spring:message code="pratiche.elenco.protocollo"/>',
                        '<spring:message code="pratiche.elenco.ente"/>',
                        '<spring:message code="pratiche.elenco.comune"/>',
                        '<spring:message code="pratiche.elenco.richiedente"/>',
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
                                    sortable: true
                                },
                                {
                                    name: 'ente',
                                    index: 'ente',
                                    sortable: true
                                },
                                {
                                    name: 'comune',
                                    index: 'comune',
                                    sortable: true
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
                                        var link = '<div id="button"><div class="centra_impostazzioni_bottoni"><form action="<%=url%>" method="post" style="float:left"><input type="hidden" name="id_pratica" value="' + rowObject["idPratica"] + '"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><div class="bottone_assegna"></div> <!-- <spring:message code="pratica.button.assegna"/> --></button></form><form action="<%=download%>" method="post" style="float:left"><input type="hidden" name="id_pratica" value="' + rowObject["idPratica"] + '"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="pratica.button.assegna.download"/>"><div class="scarica4"></div></button></form></div></div>';
                                        return link;
                                    }
                                },
                           ],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'idPratica',
                    viewrecords: true,
                    sortorder: "desc",
                    hidegrid: false,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
//                            var id = $(".list_azioni")[i].parentNode.id;
//                            var html = $("#button").html().replace(/_ID_/g, id);
//                            $($(".list_azioni")[i]).html(html);
//                        }
//                    }
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
        // $("#list").jqGrid('navGrid','#pjmap',{edit:false,add:false,del:false}); 
    });
</script>

<div id="accettazione_pratiche">

    <%--    <h2 style="text-align: center"><spring:message code="pratiche.elenco"/>440</h2> --%>

    <tiles:insertAttribute name="ricerca" />

    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
<!--    <div class="hidden" id="button">
        <div class="centra_impostazzioni_bottoni">
            <form action="<%=url%>" method="post" style="float:left">
                <input type="hidden" name="id_pratica" value="_ID_">
                <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                    <div class="bottone_assegna"></div> 
                </button>
            </form>
            <form action="<%=download%>" method="post" style="float:left">
                <input type="hidden" name="id_pratica" value="_ID_">
                <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="pratica.button.assegna.download"/>">
                    <div class="scarica4"></div> 
                </button>
            </form>
        </div>
    </div>-->

</div>