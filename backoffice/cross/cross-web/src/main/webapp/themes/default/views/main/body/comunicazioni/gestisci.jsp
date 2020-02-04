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
            url:url, 
            datatype: "json", 
            colNames:[
                'ID',
                '<spring:message code="comunicazione.gestione.dataRicezione"/>',
                '<spring:message code="comunicazione.gestione.descrizione"/>',
                '<spring:message code="comunicazione.gestione.incarico"/>',
                '<spring:message code="comunicazione.gestione.oggetto"/>',
                '<spring:message code="comunicazione.gestione.richiedente"/>',
                '<spring:message code="comunicazione.gestione.comune"/>',
                '<spring:message code="comunicazione.gestione.azione"/>'
            ], 
            colModel:
                [{
                    name:'idPratica',
                    index:'idPratica',
                    hidden: true
                }, 
                {
                    name:'dataRicezione',
                    index:'dataRicezione'
                }, 
                {
                    name:'descPratica',
                    index:'descPratica',
                    sortable: false
                }, 
                {
                    name:'inCarico',
                    index:'inCarico',
                    sortable: false
                }, 
                {
                    name:'oggettoPratica',
                    index:'oggettoPratica',
                    sortable: false
                }, 
                {
                    name:'richiedente',
                    index:'richiedente',
                    sortable: false
                }, 
                {
                    name:'comune',
                    index:'comune',
                    sortable: false
                }, 
                {
                    name:'azione',
                    index:'id',
                    classes:"list_azioni",
                    sortable:false
                }
            ], 
                    
            rowList:[10,20,30,100], 
            pager: '#pager', 
            sortname: 'dataRicezione', 
            viewrecords: true, 
            sortorder: "asc", 
            jsonReader: 
                { 
                repeatitems : false, 
                id: "0" 
            }, 
            height: 'auto' ,
            width:$('.tableContainer').width(),
            gridComplete: function(){ 
                var ids = $(".list_azioni"); 
                for(var i=0;i<ids.length;i++){ 
                    var id=$(".list_azioni")[i].parentNode.id;
                    var html =$("#button").html().replace(/_ID_/g,id);
                    $($(".list_azioni")[i]).html(html);
                } 
            }
        }); 
    });
</script>
<div id="div_scadenziario">
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="hidden" id="button">
        <form action="<%=path%>/comunicazioni/dettaglio.htm" method="post" class="gridForm">
            <input type="hidden" name="idPratica" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="comunicazione.gestione.button.seleziona"/>
            </button>
        </form>
    </div>
    <div class="buttonHolder">
        <a href="<%=path%>/comunicazioni.htm" class="secondaryAction">&larr; <spring:message code="common.button.indietro"/></a>
    </div>
</div>