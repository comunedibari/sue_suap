<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="it.wego.cross.civilia.Costants" %> 
<%@ page import="it.wego.cross.utils.HtmlUtilities" %>

<script type="text/javascript">
	var associatedTechnicianAgents = new Set();
	var rowsConfermata = [];
	var rowsErrors = [];
	var rowsTechnicianAgent = [];

	var associatedTechnicianAgentRoles = [];
	associatedTechnicianAgentRoles.push("Tecnico professionista");
	associatedTechnicianAgentRoles.push("Richiedente");
	
	function init(){
		<c:if test="${not empty associatedTechnicianAgentTaxCode}">
			associatedTechnicianAgents.add("${associatedTechnicianAgentTaxCode.toUpperCase()}")
		</c:if>
	}
	
	function personalDataSorting(a,b,o){
		return o*(a==b? 0 : (a>b?1:-1));
	}
	
	function personalDataIdentificationFormatterHander(cellvalue, options, rowObject){
		if (rowObject["confermata"] != null && rowObject["confermata"].length > 0){
			rowsConfermata[rowsConfermata.length] = options.rowId;
    	}
		else{
			if(!!globalCompared){
				rowsErrors[rowsErrors.length] = options.rowId;
			}
		}
    	var nome = rowObject["nome"] || "";
        var cognome = rowObject["cognome"] || "";
        var ragioneSociale = rowObject["ragioneSociale"] || "";
        var cell= '<span>';
        cell+='<span><spring:message code="anagrafica.nome"/>:' + nome + "</span><br/>";
        cell+='<span><spring:message code="anagrafica.cognome"/>:' + cognome + "</span><br/>";
        cell+='<span><spring:message code="anagrafica.denominazione"/>:' + ragioneSociale + "</span><br/>";
        cell+="</span>";
		return cell;
    }
	
	function personalDataIdentificationCellattrHandler(cellvalue, options, rowObject){
		var nome = rowObject["nome"] || "";
        var cognome = rowObject["cognome"] || "";
        var ragioneSociale = rowObject["ragioneSociale"] || "";
        var cell= '';
        cell+='<spring:message code="anagrafica.nome"/>:' + nome + "\n";
        cell+='<spring:message code="anagrafica.cognome"/>:' + cognome + "\n";
        cell+='<spring:message code="anagrafica.denominazione"/>:' + ragioneSociale + "\n";
        
		return ' title="'+cell+'" ';
	}

	function personalDataTypeFormatterHander(cellvalue, options, rowObject){
		var tipoAnagrafica = rowObject["tipoAnagrafica"] || "";
		var cell= '';
		if(tipoAnagrafica == 'F')
			cell = '<spring:message code="anagrafica.tipologia.fisica" />'
		else if(tipoAnagrafica == 'G')
			cell = '<spring:message code="anagrafica.tipologia.giuridica" />'
		else
			cell = "NO"
		return cell;
	}

	function personalDataCodesFormatterHander(cellvalue, options, rowObject){
    	var codiceFiscale = rowObject["codiceFiscale"] || "";
        var partitaIVA = rowObject["partitaIVA"] || "";
        var cell= '<span>';
        cell+='<span><spring:message code="anagrafica.codicefiscale"/>:' + codiceFiscale + "</span><br/>";
        cell+='<span><spring:message code="anagrafica.partitaiva"/>:' + partitaIVA + "</span><br/>";
        cell+="</span>";
		return cell;
    }
	function personalDataConctactsFormatterHander(cellvalue, options, rowObject){
		var recapiti = rowObject["recapiti"]
		var recapito = null;
		var cell = "";
		if(!!recapiti){
			if(recapiti.length ==1)
				recapito = recapiti[0]
			else{
				for(i=0; i < recapiti.length;i++){
					if(recapiti[i].idTipoIndirizzo == 1)
						recapito = recapiti[i]
				}
			}
			if(recapito != null){
				var cellulare = recapito["cellulare"]
				cell= '<span>';
				cell += '<span><spring:message code="anagrafica.recapito.telefono"/>: '+recapito["telefono"]+'</span><br/>';
				cell += '<span><spring:message code="anagrafica.recapito.cellulare"/>: '+cellulare+'</span><br/>';
				cell += '<span><spring:message code="anagrafica.recapito.indirizzo"/>: '+recapito["indirizzo"]+'</span><br/>';
				cell += '<span><spring:message code="anagrafica.recapito.email"/>: '+recapito["email"]+'</span><br/>';
				cell += '<span><spring:message code="anagrafica.recapito.pec"/>: '+recapito["pec"]+'</span><br/>';
				cell +="</span>";
			}
		}
		return cell;
	}
	function personalDataNumberOfConctactsFormatterHander(cellvalue, options, rowObject){
		var recapiti = rowObject["recapiti"]
		return (!!recapiti && recapiti.length>1)?"SI":"NO";
	}
	
	function personalDataRoleFormatterHander(cellvalue, options, rowObject){
		var ruolo = rowObject["desTipoRuolo"] || "";
		var tipoAnagrafica = rowObject["tipoAnagrafica"] || "";
		var codiceFiscale = rowObject["codiceFiscale"] || "";
		var cell = ruolo;
		if(tipoAnagrafica == "F" && associatedTechnicianAgents.size > 0 && associatedTechnicianAgentRoles.includes(ruolo) &&  associatedTechnicianAgents.has(codiceFiscale.toUpperCase())){
			cell += "<br/><strong>PROCURATORE</strong>";
			rowsTechnicianAgent[rowsTechnicianAgent.length] = options.rowId;
		}
		return cell;
	}
	function personalDataValidityDateFormatterHander(cellvalue, options, rowObject){
		var dataInizioValidita = rowObject["dataInizioValidita"] || ""
		var options = { year: 'numeric', month: '2-digit', day: '2-digit' };
		var formattedDate = ""
		if(dataInizioValidita.length > 0)
			formattedDate = new Date(dataInizioValidita).toLocaleString('it-IT', options);
		return formattedDate;
	}
	
	function personalDataActionsFormatterHander(cellvalue, options, rowObject){
		var ruolo = rowObject["desTipoRuolo"] || "";
		var daRubrica = rowObject["daRubrica"] || "";
		var idTipoRuolo = rowObject["idTipoRuolo"] || "";
		var idAnagrafica = rowObject["idAnagrafica"] || "";
		var partitaIva = rowObject["partitaIva"] || "";
		var codiceFiscale = rowObject["codiceFiscale"] || "";
		var desTipoRuolo = rowObject["desTipoRuolo"] || "";
		var tipoAnagrafica = rowObject["tipoAnagrafica"] || "";
		var varianteAnagrafica = rowObject["varianteAnagrafica"] || "";
		var counter = rowObject["counter"];
		var collegata = rowObject["collegata"] || "";
		var confermata = rowObject["confermata"] || "";
		var richiedenti = ${HtmlUtilities.toJson(richiedenti)}
		var beneficiari = ${HtmlUtilities.toJson(beneficiari)}
		var action = "";
		if( !((ruolo == 'Richiedente' && richiedenti.length < 2) || (ruolo == 'Beneficiario' && beneficiari.length < 2))){
			action = '<a class="nascondiutente scollegalink showdetail" id="'+counter+'" count="'+counter+'">'+
			'<img src="${path}/themes/default/images/icons/link_break.png"/></a>';
		}
		if (daRubrica != "S") {
			action +='<div class="anagrafica_confronta_2 confronta ui-button">'+
                '&nbsp;'+
                '<input type="hidden" class="confermata" value="'+confermata+'" name="confermata"/>'+
                '<input type="hidden" class="counter" value="'+counter+'" name="counter"/>'+
                '<input type="hidden" name="idTipoRuolo" value="'+idTipoRuolo+'"/>'+
                '<input type="hidden" name="idAnagrafica" value="'+idAnagrafica+'"/>'+
                '<input type="hidden" name="desTipoRuolo" value="'+desTipoRuolo+'"/>'+
                '<input type="hidden" name="idPratica" value="${pratica.idPratica}"/>'+
                '<input type="hidden" name="partitaIva" value="'+partitaIva+'"/>'+
                '<input type="hidden" name="codiceFiscale" value="'+codiceFiscale+'"/>'+
                '<input type="hidden" name="tipoAnagrafica" value="'+tipoAnagrafica+'"/>'+
                '<input type="hidden" name="varianteAnagrafica" value="'+varianteAnagrafica+'"/> '+ 
            '</div>'+
            '<div style="display:none" id="scollega'+counter+'button"  class="cancella elimina ui-button" title="<spring:message code="pratica.anagrafica.elimina"/>">'+
                '<input type="hidden" class="counter" value="'+counter+'" name="counter"/>'+
                '<input type="hidden" value="'+idAnagrafica+'" name="id"/>'+
                '<input type="hidden" value="'+idTipoRuolo+'" name="ruolo"/>'+
                '<input type="hidden" value="'+collegata +'" name="collegata"/>'+
            '</div>'
		}
		if(daRubrica == 'S'){
			$('#dettaglio_'+counter).click(function (e) {
                   $.ajax({
                       type: 'POST',
                       url: '${path}/pratica/comunicazione/dettaglio/anagrafica.htm',
                       data: {id_anagrafica: idAnagrafica}
                   }).done(function (data) {
                       var wHeight = $(window).height() * 0.8;
                       $('.dettaglioAnagraficaContainer').empty();
                       $('#anagrafica_detail_${anagrafica.counter}').html(data);
                       $('#anagrafica_detail_${anagrafica.counter}').dialog({
                           title: 'Dettaglio anagrafica',
                           modal: true,
                           height: wHeight,
                           width: '50%',
                           dialogClass: 'cross_modal',
                           buttons: {
                               OK: function () {
                                   $(this).dialog("close");
                               }
                           }
                       });
                       return false;
                   });
               });
			action += '<div id="<c:out value="anagrafica_detail_'+counter+'" />" title="<spring:message code="pratica.comunicazione.anagrafica.apridettaglio"/>" class="modal-content"></div>'+ 
                   
                   '<div class="anagrafica_dettaglio_apertura_2 ui-button"   id="dettaglio_'+counter+'">&nbsp;</div>'+
                              
                   '<div style="display:none" id="scollega'+counter+'button"  class="cancella elimina ui-button" title="<spring:message code="pratica.anagrafica.elimina"/>">'+
                       '<input type="hidden" class="counter" value="'+counter+'" name="counter"/>'+
                       '<input type="hidden" value="'+idTipoRuolo+'" name="ruolo"/>'+
                       '<input type="hidden" value="'+idAnagrafica+'" name="id"/>'+
                       '<input type="hidden" value="'+idTipoRuolo+'" name="ruolo"/>'+
                       '<input type="hidden" name="idTipoRuolo" value="'+idTipoRuolo+'"/>'+
                       '<input type="hidden" name="desTipoRuolo" value="'+desTipoRuolo+'"/>'+
                       '<input type="hidden" name="codiceFiscale" value="'+codiceFiscale+'"/>'+
                       '<input type="hidden" value="'+collegata +'" name="collegata"/>'+
                   '</div>'
		}
		

		return action;
	}
	$(document).ready(function() {
		
		$("#jqTablePersonalData").jqGrid({
	        data: ${HtmlUtilities.toJson(praticaDettaglio.anagraficaList)},
	        datatype: "local",
	        colNames: [
	        	'<spring:message code="anagrafica.ricerca.grid.tipologia"/>',
                '<spring:message code="anagrafica.ricerca.grid.descrizione"/>',
                '<spring:message code="anagrafica.ricerca.grid.partitaiva"/>/<spring:message code="anagrafica.ricerca.grid.codicefiscale"/>',
                '<spring:message code="anagrafica.recapiti"/>',
                '<spring:message code="anagrafica.recapiti.altri"/>',
                '<spring:message code="pratica.comunicazione.anagrafica.ruolo" />',
                '<spring:message code="dettaglio.anagrafica.data.inizio.validita" />',
                '<spring:message code="anagrafica.ricerca.grid.azioni"/>'
	            ],
	        colModel:[
	        	{
	        		"sortable": true,
		        	"name" : "tipoAnagrafica",
		        	"formatter" : personalDataTypeFormatterHander,
		        	"sorttype" : "tipoAnagrafica",
		        	"sortfunc" : personalDataSorting,
		        	"align" : "center",
		        	"width": 100
	        	},
	        	{
		        	"sortable" : false,
		        	"name" : "descrizioneAnagrafica",
		        	"autowidth": true,
		        	"title" : false,
		        	"formatter" : personalDataIdentificationFormatterHander,
		        	"cellattr" : personalDataIdentificationCellattrHandler,
		        	"width": 100
		        	},
		        {
		        	"sortable" : false,
		        	"autowidth": true,
		        	"formatter" : personalDataCodesFormatterHander
		        },
		        {
		        	"sortable" : false,
		        	"autowidth": true,
		        	"formatter" : personalDataConctactsFormatterHander
		        },
		        {
		        	"width": 40,
		        	"sortable" : false,
		        	"autowidth": true,
		        	"formatter" : personalDataNumberOfConctactsFormatterHander,
		        	"align" : "center",
		        },
		        {
		        	"sortable" : true,
		        	"name" : "desTipoRuolo",
		        	"formatter" : personalDataRoleFormatterHander,
		        	"sorttype" : "Integer",
		        	"sortfunc" : personalDataSorting,
		        	"width": 100
		        },
		        {
		        	"sortable" : false,
		        	"formatter" : personalDataValidityDateFormatterHander,
		        	"width" : 80
		        },
		        {
		        	"sortable" : false,
		        	"formatter" : personalDataActionsFormatterHander,
		        	"width" : 75
		        }
	        ],
            viewrecords: true,
            hidegrid: false,
            rowNum: 100,
	        jsonReader:{
	            repeatitems: false,
	            id: "0"
	        },
	        height: 'auto',
	        gridComplete: function () {  
                for (var i = 0; i < rowsConfermata.length; i++) {
                	$("#jqTablePersonalData").find("tr#" + rowsConfermata[i] +" td").css("background-color", "#C5DBEC");
                	$("#jqTablePersonalData").find("tr#" + rowsConfermata[i] +" td").css("color", "black");
                }
                for (var i = 0; i < rowsTechnicianAgent.length; i++) {
                	$("#jqTablePersonalData").find("tr#" + rowsTechnicianAgent[i] +" td").css("font-weight", "bold");
                	$("#jqTablePersonalData").find("tr#" + rowsTechnicianAgent[i] +" td").css("color", "black");
                }
                for (var i = 0; i < rowsErrors.length; i++) {
                	$("#jqTablePersonalData").find("tr#" + rowsErrors[i] +" td").css("background-color", "#FF2400");
                	$("#jqTablePersonalData").find("tr#" + rowsErrors[i] +" td").css("color", "black");
                }
                rowsConfermata = []
                rowsTechnicianAgent= []
                rowsErrors= []
                confrontaAnagraficaReady();
                eventEliminaAnagrafica();
             },
	        width: $('#anagrafiche_pratica').width() - 2
	    }).navGrid('#pager', {edit: false, add: false, del: false, search: false});

		$('.scollegalink').click(function () {
            $('#scollega' + $(this).attr("count") + 'button').click();
	    });
	});
	
</script>

<div id="anagraficaContainer">
	<div class="nascondiutente table-add-link">
	    <a href="<%=path%>/pratica/dettaglio/collegaAnagrafica.htm">
	        <spring:message code="pratica.comunicazione.collega.anagrafica"/>
	        <img src="<%=path%>/themes/default/images/icons/add.png" alt="<spring:message code="pratica.comunicazione.collega.anagrafica"/>" title="<spring:message code="pratica.comunicazione.collega.anagrafica"/>">
	    </a>
	</div>
	<div id="anagrafiche_pratica" class="row">
        <table id="jqTablePersonalData"></table> 
        <div id="pager"></div> 
</div>
    <div id="timeLinePersonalDataDiv" class="hidden">
	    <c:set var="anagraficheSize" value="${fn:length(praticaDettaglio.anagraficaList)}" scope="page" />
	    <div class="imbox_frame <c:if test="${anagraficheSize > 1}">margine_imbox_frame</c:if>">
	        <c:if test="${anagraficheSize > 1}">
	            <div class="controllo_sinistra">
	                <div class="controller_box">
	                    <div class="box_left" onclick="item_animate('<c:out value="${anagraficheSize}"></c:out>', '#anagrafica_box', '-=277px')"></div>
	                    </div>
	                </div>
	        </c:if>
	        <div id="anagrafica_box">   
	            <table cellpadding="0" cellspacing="0">
	                <tr>
	                    <c:forEach  items="${praticaDettaglio.anagraficaList}" var="anagrafica" >
	                        <td>
	                            <c:set var="classDiv" value="" />
	                            <c:set var="confermata" value="" />
	                            <c:if test="${anagrafica.confermata != null && anagrafica.confermata!=''}">
	                                <c:set var="classDiv" value="${classDiv} confermataBG" />
	                                <c:set var="confermata" value="1" />    
	                            </c:if>
	                            <div class="circle ${classDiv}" id="anagrafica${anagrafica.counter}" title="${classDiv}">
	                                <c:set var="classDiv" value="" />
	                                <div class="ctrlHolder">
	                                    <label class="required">
	                                        <spring:message code="utenti.tipoRuolo"/>
	                                    </label>                                                     
	                                    <c:if test="${!(((anagrafica.desTipoRuolo eq 'Richiedente') && (fn:length(richiedenti) lt 2) ) ||  ( (anagrafica.desTipoRuolo eq 'Beneficiario') && (fn:length(beneficiari) lt 2)))}"> 
	                                        <a style="padding: 10px; width: 75%;" class='nascondiutente scollegalink showdetail' id="scollega${anagrafica.counter}" count="${anagrafica.counter}">
	                                            <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
	                                            &nbsp;&nbsp;<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>
	                                        </a>
	                                    </c:if>
	                                    <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.desTipoRuolo}">
	                                </div>
	                                <c:if test="${anagrafica.tipoAnagrafica == 'F'}">
	                                    <div class="ctrlHolder">
	                                        <label class="required">
	                                            <spring:message code="utenti.nome"/>
	                                        </label>
	                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.nome}">
	                                    </div>
	                                    <div class="ctrlHolder">
	                                        <label class="required">
	                                            <spring:message code="utenti.cognome"/>
	                                        </label>
	                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.cognome}">
	                                    </div>
	                                    <div class="ctrlHolder">
	                                        <label class="required">
	                                            <spring:message code="utenti.codicefiscale"/>
	                                        </label>
	                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.codiceFiscale}">
	                                    </div>
	                                    <c:if test="${anagrafica.flgIndividuale == 'S'}">
	                                        <div class="ctrlHolder">
	                                            <label class="required">
	                                                <spring:message code="utenti.denominazione"/>
	                                            </label>
	                                            <textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.denominazione}</textarea>
	                                        </div>
	                                        <div class="ctrlHolder">
	                                            <label class="required">
	                                                <spring:message code="utenti.piva"/>
	                                            </label>
	                                            <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.partitaIva}">
	                                        </div>
	                                    </c:if>
	                                </c:if>
	                                <c:if test="${anagrafica.tipoAnagrafica == 'G'}">
	                                    <div class="ctrlHolder">
	                                        <label class="required">
	                                            <spring:message code="utenti.denominazione"/>
	                                        </label>
	                                        <textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.denominazione}</textarea>
	                                    </div>
	                                    <div class="ctrlHolder">
	                                        <label class="required">
	                                            <spring:message code="utenti.piva"/>
	                                        </label>
	                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.partitaIva}">
	                                    </div>
	                                </c:if>
	                                <div class="showdettaglio">
	                                    <c:if test='${anagrafica.daRubrica != "S"}'>
	                                        <div class="anagrafica_confronta confronta ui-button">
	                                            &nbsp;&nbsp;<spring:message code="pratica.anagrafica.conferma"/>
	                                            <input type="hidden" class="confermata" value="${confermata}" name="confermata"/>
	                                            <input type="hidden" class="counter" value="${anagrafica.counter}" name="counter"/>
	                                            <input type="hidden" name="idTipoRuolo" value="${anagrafica.idTipoRuolo}"/>
	                                            <input type="hidden" name="idAnagrafica" value="${anagrafica.idAnagrafica}"/>
	                                            <input type="hidden" name="desTipoRuolo" value="${anagrafica.desTipoRuolo}"/>
	                                            <input type="hidden" name="idPratica" value="${pratica.idPratica}"/>
	                                            <input type="hidden" name="partitaIva" value="${anagrafica.partitaIva}"/>
	                                            <input type="hidden" name="codiceFiscale" value="${anagrafica.codiceFiscale}"/>
	                                            <input type="hidden" name="tipoAnagrafica" value="${anagrafica.tipoAnagrafica}"/>
	                                            <input type="hidden" name="varianteAnagrafica" value="${anagrafica.varianteAnagrafica}"/>  
	                                        </div>                                        
	                                        <div style='display:none' id="scollega${anagrafica.counter}button"  class="cancella elimina ui-button" title="<spring:message code="pratica.anagrafica.elimina"/>">
	                                            &nbsp;&nbsp;<spring:message code="pratica.anagrafica.scollega"/>
	                                            <input type="hidden" class="counter" value="${anagrafica.counter}" name="counter"/>
	                                            <input type="hidden" value="${anagrafica.idAnagrafica}" name="id"/>
	                                            <input type="hidden" value="${anagrafica.idTipoRuolo}" name="ruolo"/>
	                                            <input type="hidden" value="${anagrafica.collegata }" name="collegata"/>
	                                        </div>
	                                    </c:if>
	                                    <c:if test='${anagrafica.daRubrica == "S"}'>
	                                        <script>
	                                            $(function () {
	                                                $('#dettaglio_${anagrafica.counter}').click(function (e) {
	                                                    $.ajax({
	                                                        type: 'POST',
	                                                        url: '${path}/pratica/comunicazione/dettaglio/anagrafica.htm',
	                                                        data: {id_anagrafica: '${anagrafica.idAnagrafica}'}
	                                                    }).done(function (data) {
	                                                        var wHeight = $(window).height() * 0.8;
	                                                        $('.dettaglioAnagraficaContainer').empty();
	                                                        $('#anagrafica_detail_${anagrafica.counter}').html(data);
	                                                        $('#anagrafica_detail_${anagrafica.counter}').dialog({
	                                                            title: 'Dettaglio anagrafica',
	                                                            modal: true,
	                                                            height: wHeight,
	                                                            width: '50%',
	                                                            dialogClass: 'cross_modal',
	                                                            buttons: {
	                                                                OK: function () {
	                                                                    $(this).dialog("close");
	                                                                }
	                                                            }
	                                                        });
	                                                        return false;
	                                                    });
	                                                });
	                                            });
	                                        </script>
	                                        <div id="<c:out value="anagrafica_detail_${anagrafica.counter}" />" class="modal-content">
	                                        </div>    
	                                        <div class="anagrafica_dettaglio_apertura ui-button"   id="dettaglio_${anagrafica.counter}">
	                                            <spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>
	                                        </div>
	                                        <div style='display:none' id="scollega${anagrafica.counter}button"  class="cancella elimina ui-button" title="<spring:message code="pratica.anagrafica.elimina"/>">
	                                            &nbsp;&nbsp;<spring:message code="pratica.anagrafica.scollega"/>
	                                            <input type="hidden" class="counter" value="${anagrafica.counter}" name="counter"/>
	                                            <input type="hidden" value="${anagrafica.idTipoRuolo}" name="ruolo"/>
	                                            <input type="hidden" value="${anagrafica.idAnagrafica}" name="id"/>
	                                            <input type="hidden" value="${anagrafica.idTipoRuolo}" name="ruolo"/>
	                                            <input type="hidden" name="idTipoRuolo" value="${anagrafica.idTipoRuolo}"/>
	                                            <input type="hidden" name="desTipoRuolo" value="${anagrafica.desTipoRuolo}"/>
	                                            <input type="hidden" name="codiceFiscale" value="${anagrafica.codiceFiscale}"/>
	                                            <input type="hidden" value="${anagrafica.collegata }" name="collegata"/>
	                                        </div>
	                                    </c:if>
	                                </div>
	                            </div>
	                        </td>
	                    </c:forEach>
	                </tr>
	            </table>
	        </div>
	        <c:if test="${anagraficheSize > 1}">
	            <div class="controllo_destra">
	                <div class="controller_box">
	                    <div class="box_right" onclick="item_animate('<c:out value="${anagraficheSize}"></c:out>', '#anagrafica_box', '+=277px')"></div>
	                    </div>
	                </div>
	        </c:if>
	    </div>
	    <div class="clear"></div>
    </div>
</div>