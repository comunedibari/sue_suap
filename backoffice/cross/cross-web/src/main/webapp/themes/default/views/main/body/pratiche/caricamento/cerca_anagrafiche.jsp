<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                '<spring:message code="anagrafica.ricerca.grid.tipologia"/>',
                '<spring:message code="anagrafica.ricerca.grid.descrizione"/>',
                '<spring:message code="anagrafica.ricerca.grid.partitaiva"/>',
                '<spring:message code="anagrafica.ricerca.grid.codicefiscale"/>',
                '<spring:message code="anagrafica.ricerca.grid.azioni"/>'], 
            colModel:
                [{
                    name:'idAnagrafica',
                    index:'idAnagrafica',
                    hidden: true
                }, 
                {
                    name:'tipoAnagrafica',
                    index:'tipoAnagrafica',
                    sortable:false,
                    formatter: function(cellvalue){
                        if (cellvalue == 'P') {
                            cellvalue = '<spring:message code="anagrafica.professionista"/>';
                        } else if (cellvalue == 'F') {
                            cellvalue = '<spring:message code="anagrafica.personafisica"/>';
                        } else if (cellvalue == 'G') {
                            cellvalue = '<spring:message code="anagrafica.personagiuridica"/>';
                        }
                        return cellvalue;
                    }
                }, 
                {
                    name:'denominazione',
                    index:'denominazione',
                    sortable:false
                }, 
                {
                    name:'partitaIva',
                    index:'partitaIva'
                }, 
                {
                    name:'codiceFiscale',
                    index:'codiceFiscale'
                },
                {
                    name:'azione',
                    index:'id',
                    classes:"list_azioni",
                    sortable:false
                }
            ], 
            /*
            <c:set var="page" value="1"/>
            <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                <c:set var="page" value="${filtroRicerca.page}"/>
            </c:if>
            */
            page: '${page}', 
            /*
            <c:set var="orderColumn" value="idAnagrafica"/>
            <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
            </c:if>
            */
            sortname: '${orderColumn}', 
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
            rowNum: "${rowNum}",             
            rowList:[10,20,30,100], 
            pager: '#pager', 
            viewrecords: true, 
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
<div>
    <h2 style="text-align: center"><spring:message code="anagrafica.ricerca.title"/></h2>
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="hidden" id="button">
        <form action="<%=path%>/pratiche/nuove/anagrafica/aggiungi/seleziona.htm" method="post" class="gridForm" style="margin-left: 30%">
            <input type="hidden" name="idAnagrafica" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="protocollo.dettaglio.anagrafica.button.seleziona"/>
            </button>
        </form>
    </div>
    <div class="hidden" id="button">
        <form action="<%=path%>/pratiche/nuove/anagrafica/aggiungi/dettaglio.htm" method="post" class="gridForm" style="margin-left: 30%">
            <input type="hidden" name="idAnagrafica" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="protocollo.dettaglio.anagrafica.button.dettaglio"/>
            </button>
        </form>
    </div>
    <div class="uniForm">
        <div class="buttonHolder">
            <a href="<%= path%>/pratiche/nuove/anagrafica/aggiungi/indietro.htm" class="secondaryAction">&larr; <spring:message code="protocollo.dettaglio.button.indietro"/></a>
        </div>
    </div>
</div>
