<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript">

	function identificationFormatterHandler(cellvalue, options, rowObject) {
        var cell= "<span>";
        cell+='<span><spring:message code="anagrafica.nome"/>: </span><span>' + rowObject['nome'] + '</span><br/>';
        cell+='<span><spring:message code="anagrafica.cognome"/>: </span><span>' + rowObject['cognome'] + '</span><br/>';
        cell+='<span><spring:message code="anagrafica.denominazione"/>: </span><span>' + rowObject['denominazione'] + '</span><br/>';
        cell+="</span>";
		return cell;
    }

	function identificationCodesFormatterHandler(cellvalue, options, rowObject) {
        var codiceFiscale = rowObject['codiceFiscale'] || "";
        var partitaIva = rowObject['partitaIva'] || "";
        
        var cell= "<span>";
        cell+='<span><spring:message code="anagrafica.codicefiscale"/>: </span><span>' + codiceFiscale + '</span><br/>';
        cell+='<span><spring:message code="anagrafica.partitaiva"/>: </span><span>' + partitaIva + '</span><br/>';
        cell+="</span>";
		return cell;
    }

	function infoDataFormatterHandler(cellvalue, options, rowObject) {
		var infoData = rowObject['recapiti']
		var length = infoData.length;
		
        var cell= "<span>";
		if(length == 1){
			cell+=buildInfoData(infoData[0])
		}
		else if (length > 1){
			var currentCell = null;
			for (currentInfo of infoData){
				var idTipoIndirizzo = currentInfo["idTipoIndirizzo"] || 0;
				if(idTipoIndirizzo == 1)
					currentCell=buildInfoData(currentInfo)
			}
			if (currentCell == null)
				currentCell=buildInfoData(infoData[0])
			cell+=currentCell;
		}
		cell+="</span>";
		return cell;
    }
	
	function infoDataCounterFormatterHandler(cellvalue, options, rowObject) {
		var infoData = rowObject['recapiti']
		return !!infoData && infoData.length >1 ?'<spring:message code="anagrafica.impresaindividuale.si"/>': '<spring:message code="anagrafica.impresaindividuale.no"/>';
	}
	
	function buildInfoData(info){
		var telefono = info['telefono'] || "";
		var indirizzo = info['indirizzo'] || "";
		if(indirizzo.length > 0 && !!info['nCivico'])
			indirizzo += ", "+info['nCivico'];
		var email = info['email'] || "";
		var pec = info['pec'] || "";
		
        var currentCell='<span><spring:message code="anagrafica.recapito.telefono"/>: </span><span>' + telefono + '</span><br/>';
        currentCell+='<span><spring:message code="anagrafica.recapito.indirizzo"/>: </span><span>' + indirizzo + '</span><br/>';
        currentCell+='<span><spring:message code="anagrafica.recapito.email"/>: </span><span>' + email + '</span><br/>';
        currentCell+='<span><spring:message code="anagrafica.recapito.pec"/>: </span><span>' + pec + '</span><br/>';
		return currentCell;
	}
	
	function actionsFormatterHandler(cellvalue, options, rowObject) {
        var link = '<div  id="button"><form action="${path}/gestione/anagrafiche/modifica.htm" method="post" class="gridForm" style="margin-left: 30%"><input type="hidden" name="idAnagrafica" value="' + rowObject["idAnagrafica"]+'"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="anagrafica.button.modifica"/>"><div class="bottone_gestisci"></div></button></form></div>';
        return link;
    }
	var url = getUrl();
    $(document).ready(function()
    {
        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: [
                    	'',
                        '<spring:message code="anagrafica.ricerca.grid.tipologia"/>',
                        '<spring:message code="anagrafica.ricerca.grid.descrizione"/>',
                        '<spring:message code="anagrafica.ricerca.grid.partitaiva"/>/<spring:message code="anagrafica.ricerca.grid.codicefiscale"/>',
                        '<spring:message code="anagrafica.recapiti"/>',
                        '<spring:message code="anagrafica.recapiti.altri"/>',
                        '<spring:message code="anagrafica.ricerca.grid.azioni"/>'],
                    colModel:
                        [
                        	{
                                name: 'idAnagrafica',
                                index: 'idAnagrafica',
                                hidden: true
                            },
                        	{
                                name: 'tipoAnagrafica',
                                index: 'tipoAnagrafica',
                                sortable: false
                            },
                            {
                            	sortable : false,
                            	hidden : false,
                            	width : 150,
                            	formatter : identificationFormatterHandler
                            },
                            {
                                sortable: false,
                                formatter: identificationCodesFormatterHandler
                            },
                            {
                                sortable: false,
                                formatter: infoDataFormatterHandler
                            },
                            {
                                sortable: false,
                                formatter: infoDataCounterFormatterHandler,
                                align: "center",
                                width: 40
                            },
                            {
                                name: 'azione',
                                index: 'id',
                                classes: "list_azioni",
                                sortable: false,
                                width: 40,
                                formatter: actionsFormatterHandler
                            },
                            
                        ],
                    <c:set var="page" value="1"/>
                    <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                    	<c:set var="page" value="${filtroRicerca.page}"/>
                    </c:if>
                    page: '${page}',
                    <c:set var="orderColumn" value="idAnagrafica"/>
                    <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                    	<c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                    </c:if>
                    sortname: '${orderColumn}',
                    <c:set var="orderDirection" value="desc"/>
                    <c:if test="${filtroRicerca!=null && filtroRicerca.orderDirection!=null}">
                    	<c:set var="orderDirection" value="${filtroRicerca.orderDirection}"/>
                    </c:if>
                    sortorder: "${orderDirection}",
                    <c:set var="rowNum" value="10"/>
                    <c:if test="${filtroRicerca!=null && filtroRicerca.limit!=null}">
                     	<c:set var="rowNum" value="${filtroRicerca.limit}"/>
                    </c:if>
                    rowNum: "${rowNum}",
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    jsonReader: {
                        repeatitems: false,
                        id: "0"
                    },
                    height: 'auto',
                    width: $('.tableContainer').width(),
                    ondblClickRow : function(rowid, status, e){ 
                    	var myGrid = $('#list'),
                    	selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
                    	idAnagrafica = myGrid.jqGrid ('getCell', selRowId, 'idAnagrafica');
                    	$("#dettaglio_anagrafica_form #idAnagrafica").val(idAnagrafica)
                        $("#dettaglio_anagrafica_form").trigger('submit');

                    }
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
    });
</script>
<div>
    <tiles:insertAttribute name="operazioneRiuscita" /> 
</div>
<div id="apertura_pratiche">
    <div class="table-add-link">
        <a href="<%=path%>/gestione/anagrafiche/nuova.htm" class="inserisciAnagrafica" alt="<spring:message code="anagrafica.ricerca.add"/>" title="<spring:message code="anagrafica.ricerca.add"/>">
            <spring:message code="anagrafica.ricerca.add"/>
        </a>
    </div>
    <br />
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
</div>
<form id="dettaglio_anagrafica_form" class="hide" action="${path}/gestione/anagrafiche/modifica.htm">
	<input type="hidden" id="idAnagrafica" name="idAnagrafica" />
	<input type="hidden" name="readonly" value="true" /> 
</form>