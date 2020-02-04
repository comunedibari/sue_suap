<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
%>
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
            url:url,
                    datatype: "json",
                    colNames:[
                    'ID',
                    '<spring:message code="ente.comuni.descrizione"/>',
                    '<spring:message code="ente.comuni.provincia"/>',
                    '<spring:message code="ente.comuni.codicecatastale"/>',
                    '<spring:message code="ente.comuni.azione"/>'],
                    colModel:
                    [{
                    name:'idComune',
                            index:'idComune',
                            hidden: true
                    },
                    {
                    name:'descrizione',
                            index:'descrizione',
                            sortable: false
                    },
                    {
                    name:'provincia.descrizione',
                            index:'provincia.descrizione',
                            sortable: false
                    },
                    {
                    name:'codCatastale',
                            index:'codCatastale',
                            sortable: false
                    },
                    {
                    name:'azione',
                            index:'id',
                            classes:"list_azioni",
                            sortable:false,
                            formatter: function(cellvalue, options, rowObject){
                    var link = '<div class="collegaComuneContainer gridActionContainer"><a href="${path}/ente/comuni/seleziona.htm?idComune=' + rowObject["idComune"] + '" ><img class="collegaEnteComuneImmagine" src="${path}/themes/default/css/images/plus.png"></a></div>';
                            return link;
                    }

                    }
                   ],
                    rowList:[10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'idComune',
                    viewrecords: true,
                    sortorder: "asc",
                    jsonReader:
            {
            repeatitems : false,
                    id: "0"
            },
                    height: 'auto',
                    width:$('.tableContainer').width(),
//            gridComplete: function(){ 
//                var ids = $(".list_azioni"); 
//                for(var i=0;i<ids.length;i++){ 
//                    var id=$(".list_azioni")[i].parentNode.id;
//                    var html =$("#button").html().replace(/_ID_/g,id);
//                    $($(".list_azioni")[i]).html(html);
//                } 
//            }
            });
    });
</script>

<div>
    <h2 style="text-align: center"><spring:message code="ente.comuni.titolo"/></h2>
    <div id="impostazioni_div">
    <tiles:insertAttribute name="ricerca" />
    
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
<!--    <div class="hidden" id="button">
        <form action="<%=path%>/ente/comuni/seleziona.htm" method="post" class="gridForm">
            <input type="hidden" name="idComune" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">

            </button>
        </form>
    </div>-->
    </div>
    <div class="buttonHolder">
        <a href="<%=path%>/ente/modifica.htm" class="secondaryAction">&larr; Indietro</a>
    </div>
</div>


