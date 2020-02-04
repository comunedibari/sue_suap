<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {
                  
        $("#list").jqGrid
        ({ 
            url:url, 
            datatype: "json", 
            colNames:['<spring:message code="scadenzario.idpratica"/>',
                '<spring:message code="scadenzario.identificativo"/>', 
                '<spring:message code="scadenzario.oggetto"/>',
                '<spring:message code="scadenzario.protocollo"/>',
                '<spring:message code="scadenzario.statoPratica"/>',
                '<spring:message code="scadenzario.scadenza"/>',
                '<spring:message code="scadenzario.dataricezione"/>',
                '<spring:message code="scadenzario.datascadenza"/>',                 
                '<spring:message code="scadenzario.giornirestanti"/>',
                '<spring:message code="scadenzario.utenteincarico"/>',
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
                    name:'oggetto',
                    index:'oggetto',
                    sortable:false
                }, 
                {
                    name:'protocollo',
                    index:'protocollo',
                    sortable:false
                }, 
                {
                    name:'statoPratica',
                    index:'statoPratica',
                    classes:"statoPratica",
                    sortable:false
                }, 
                {
                    name:'descrizione',
                    index:'descrizione',
                    sortable:false
                }, {
                    name:'dataRicezione',
                    index:'dataRicezione',
                    datefmt:'dd/mm/yyyy',
                    sortable:false
                }, {
                    name:'dataFineScadenza',
                    index:'dataFineScadenza',
                    datefmt:'dd/mm/yyyy'         
                }, {
                    name:'giornirestanti',
                    index:'giornirestanti',
                    sortable: false,
                    formatter: function(cellvalue){
                        var classe;
                        var val = Number(cellvalue);
                        if (val > 5) {
                            classe= "class='scadenzanormale'";
                        } else if (val > 0) {
                            classe= "class='scadenzaWarn'";
                        } else {
                            classe= "class='scadenzaSuperata'";
                        }
                        return '<span ' + classe + ' >' + cellvalue + '</span>';
                    }
                }, {
                    name:'utenteInCarico',
                    index:'utenteInCarico',
                    sortable:false
                }, 
                //                {
                //                    name:'azione',
                //                    classes:"list_azioni",
                //                    sortable:false
                //                },
                {
                    name: 'azione',
                    index: 'idPratica',
                    classes: "list_azioni",
                    sortable: false,
                    formatter: function(cellvalue, options, rowObject) {
                        var link = '<div class="gridActionContainer"><a href="<%= path%>/pratiche/dettaglio.htm?id_pratica='+rowObject.pratica +'&&backto=pratiche_scadenziario" ><img src="${path}/themes/default/css/images/bottone_dettaglio.png"></a></div>';
                        return link;
                    }
                }                
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
            }
        }).navGrid('#pager', {edit: false, add: false, del: false, search: false}); 
    });
</script>

<div id="div_scadenziario">
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>

