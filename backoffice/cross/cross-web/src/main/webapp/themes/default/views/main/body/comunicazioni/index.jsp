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
                '<spring:message code="comunicazione.inarrivo.descrizione"/>',
                '<spring:message code="comunicazione.inarrivo.ente"/>',
                '<spring:message code="comunicazione.inarrivo.pratica"/>',
                '<spring:message code="comunicazione.inarrivo.incarico"/>',
                '<spring:message code="comunicazione.inarrivo.data"/>',
                '<spring:message code="comunicazione.inarrivo.azione"/>'
            ], 
            colModel:
                [{
                    name:'idComunicazione',
                    index:'idComunicazione',
                    hidden: true
                }, 
                {
                    name:'descrizione',
                    index:'descrizione',
                    sortable: false
                }, 
                {
                    name:'ente',
                    index:'ente',
                    sortable: false
                }, 
                {
                    name:'pratica',
                    index:'pratica',
                    sortable: false
                }, 
                {
                    name:'utente',
                    index:'utente',
                    sortable: false
                }, 
                {
                    name:'dataComunicazione',
                    index:'dataComunicazione'
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
            sortname: 'idMessaggio', 
            viewrecords: true, 
            sortorder: "desc", 
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
            <input type="hidden" name="idComunicazione" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="comunicazione.inarrivo.button.dettaglio"/>
            </button>
        </form>
        <form action="<%=path%>/comunicazioni/gestisci.htm" method="post" class="gridForm">
            <input type="hidden" name="idComunicazione" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="comunicazione.inarrivo.button.gestisci"/>
            </button>
        </form>
    </div>
    <div class="buttonHolder">
        <a href="<%=path%>/index.htm" class="secondaryAction">&larr; <spring:message code="common.button.indietro"/></a>
    </div>
</div>