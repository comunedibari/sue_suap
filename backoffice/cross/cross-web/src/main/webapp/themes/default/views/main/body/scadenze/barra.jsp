<%
    String path = request.getContextPath();
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
    var url = '<%= path%>/pratiche/scadenzario_cerca/ajax_search.htm';
    //alert(url);
	$(document).ready(function()
    {
                  
        $("#list").jqGrid
        ({ 
            url:url, 
            datatype: "json", 
            colNames:['<spring:message code="scadenzario.idpratica"/>',
                '<spring:message code="scadenzario.identificativo"/>',
                '<spring:message code="scadenzario.datascadenza"/>',
                '<spring:message code="scadenzario.azioni"/>'], 
            colModel:
                [{
                    name:'pratica',
                    index:'pratica',
                    sortable:false,
                    hidden: true
                }, 
				{
                    name:'identificativo',
                    index:'identificativo',
                    sortable:false
                },
				{
                    name:'dataFineScadenza',
                    index:'dataFineScadenza',
                    datefmt:'dd/mm/yyyy'         
                }, 
                {
                    name:'azione',
                    /*index:'idPratica',*/
                    classes:"list_azioni",
                    sortable:false
                },
            ],
            rowList:[10,20,30,100],
            pager: '#pager',
            sortname: 'dataFineScadenza',
            viewrecords: true,
            sortorder: 'asc',
            hidegrid: false,
            jsonReader:
                {
                repeatitems : false,
                id: "0"
            }, 
            height: 'auto' ,
            width:$('.tableContainer').width(),
            gridComplete: function(){
                var ids = $(".list_azioni");
                for(var i=0;i<ids.length;i++)
                {
                    var id=$(".list_azioni")[i].parentNode.id;
                    var html =$("#button").html().replace("_ID_",id);
                    $($(".list_azioni")[i]).html(html);
                    if($.trim($("#"+id+" td.statoPratica").text())=="Ricevuta")
                    {
                       $(ids[0]).find("form").attr("action","<%= path%>/pratiche/nuove/apertura/dettaglio.htm");
                    }
                } 
            }
        }); 
        // $("#list").jqGrid('navGrid','#pjmap',{edit:false,add:false,del:false}); 
    });
</script>

<div id="div_scadenziario">
    
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="hidden" id="button">
        <form action="<%= path%>/pratiche/dettaglio.htm" method="post" >
            <input type="hidden" name="id_pratica" value="_ID_">
            <input type="hidden" name="idPratica" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="scadenzario.button.dettaglio"/>">
                <div class="bottone_dettaglio"></div>
            </button>
        </form>
    </div>

</div>

