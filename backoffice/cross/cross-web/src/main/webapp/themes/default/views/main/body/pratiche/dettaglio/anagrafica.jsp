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
	var associatedTechnicianAgentRoles = [];
	associatedTechnicianAgentRoles.push("Tecnico professionista");
	associatedTechnicianAgentRoles.push("Richiedente");
	var currentTechnicianAgentName = "";
	var people = {}
	
	function init(){
		<c:if test="${not empty associatedTechnicianAgentTaxCode}">
			associatedTechnicianAgents.add("${associatedTechnicianAgentTaxCode.toUpperCase()}")
		</c:if>
	}

	function callDetail(identificativoPratica, idAnagrafica, provenienza, conto_anagrafiche) {
		$.ajax({
			type: 'POST',
			url: '<%=path%>/pratica/comunicazione/dettaglio/anagrafica.htm',
			data: {
				identificativo_pratica: identificativoPratica, 
				id_anagrafica: idAnagrafica, 
				provenienza: provenienza,
				idx: conto_anagrafiche
			}
		}).done(function(data) {
			var wHeight = $(window).height() * 0.8;
			$('.dettaglioAnagraficaContainer').empty();
			$("#personal_data_detail_modal").html(data);
			$("#personal_data_detail_modal").dialog({
				title: 'Dettaglio anagrafica',
				modal: true,
				height: wHeight,
				width: '50%',
				dialogClass: 'cross_modal',
				buttons: {
					OK: function() {
						$(this).dialog("close");
					}
				}
			});
			return false;
		});
	}
	
	function personalDataIdentificationFormatterHander(cellvalue, options, rowObject){
		if (rowObject["provenienza"] === "DMS"){
            rowsToColorDms[rowsToColorDms.length] = options.rowId;
    	}
		if (rowObject["provenienza"] === "CIVILIA"){
            rowsToColorCivilia[rowsToColorCivilia.length] = options.rowId;
    	}
    	var nome = rowObject["nome"] || "";
        var cognome = rowObject["cognome"] || "";
        var ragioneSociale = rowObject["ragioneSociale"] || "";
        var cell= '<span>';
        cell+='<span><spring:message code="anagrafica.nome"/>:' + nome + "</span><br/>";
        cell+='<span><spring:message code="anagrafica.cognome"/>:' + cognome + "</span><br/>";
        cell+='<span><spring:message code="anagrafica.denominazione"/>:' + ragioneSociale + "</span><br/>";
        cell+="</span>";
        people[rowObject["codiceFiscale"]] = cognome + " " + nome;
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
				var cellulare = recapito["cellulare"] || ""
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
		var ruolo = rowObject["ruolo"] || "";
		var tipoAnagrafica = rowObject["tipoAnagrafica"] || "";
		var codiceFiscale = rowObject["codiceFiscale"] || "";
		var nome = rowObject["nome"] || "";
		var cognome = rowObject["cognome"] || "";
		var cell = ruolo;
		if(tipoAnagrafica == "F" && associatedTechnicianAgents.size > 0 && associatedTechnicianAgentRoles.includes(ruolo) &&  associatedTechnicianAgents.has(codiceFiscale.toUpperCase())){
			cell += "<br/><strong value=\""+ruolo+"\" class=\"hide\">PROCURATORE</strong>";
			rowsTechnicalAgent[rowsTechnicalAgent.length] = options.rowId;
        	currentTechnicianAgentName = cognome + " " + nome;

		}
		return cell;
	}
	function personalDataValidityDateFormatterHander(cellvalue, options, rowObject){
		var dataInizioValidita = rowObject["dataInizioValidita"]
		var options = { year: 'numeric', month: '2-digit', day: '2-digit' };
		var formattedDate = new Date(dataInizioValidita).toLocaleString('it-IT', options);
		return formattedDate;
	}
	function personalDataActionsFormatterHander(cellvalue, options, rowObject){
		var provenienza = rowObject["provenienza"] || "";
		var identificativoPratica = rowObject["identificativoPratica"] || "";
		var codiceFiscale = rowObject["codiceFiscale"] || "";
		var tipoAnagrafica = rowObject["tipoAnagrafica"] || "";
		var ruolo = rowObject["ruolo"] || "";
		var idRuolo = rowObject["idRuolo"] || "";
		var recapiti = rowObject["recapiti"] || "";
		var provenienza = rowObject["provenienza"] || "";
		var idAnagrafica = rowObject["idAnagrafica"] || "";
		var richiedenti = ${HtmlUtilities.toJson(richiedenti)}
		var beneficiari = ${HtmlUtilities.toJson(beneficiari)}
		var action = "";

		<c:if test="${!abilitazioneConsultazione}">
			if( !((ruolo == 'Richiedente' && richiedenti.length < 2) || (ruolo == 'Beneficiario' && beneficiari.length < 2)) && (provenienza != "DMS" && provenienza != "CIVILIA")
					&& !(tipoAnagrafica == "F" && associatedTechnicianAgents.size > 0 && associatedTechnicianAgentRoles.includes(ruolo) && associatedTechnicianAgents.has(codiceFiscale.toUpperCase()))){
				action = '<a href="<%=path%>/pratica/dettaglio/collegaAnagrafica/scollega.htm?idAnagrafica='+idAnagrafica+'&idRuolo='+idRuolo+'">'+ 
				'<img src="<%=path%>/themes/default/images/icons/link_break_big.png"'+
					'alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>"'+
					'title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">'+
				'</a>&nbsp;';
			}
		</c:if>
		action += '<a id="show_detail" title="<spring:message code="pratica.comunicazione.anagrafica.apridettaglio"/>" class="cerca_lente_dettaglio" href="javascript:callDetail(\'${dettaglio.identificativoPratica}\',\''+rowObject["idAnagrafica"]+'\',\''+provenienza+'\',\''+conto_anagrafiche_dms+'\')"> </a>'

		<c:if test="${!abilitazioneConsultazione}">
			if(tipoAnagrafica == "F" && associatedTechnicianAgents.size > 0 && associatedTechnicianAgentRoles.includes(ruolo) && !associatedTechnicianAgents.has(codiceFiscale.toUpperCase())){
				action += '&nbsp;<a title=\'<spring:message code="dettaglio.anagrafica.cambia.tecnico.procuratore" />\' class="tecnico_procuratore_dettaglio"'+
					'href="javascript:changeTechnicianAgent(\'${dettaglio.idPratica}\','+idAnagrafica+',\''+codiceFiscale+'\')"> </a>'
					
			} 
		</c:if>
		if (provenienza === "DMS")
			conto_anagrafiche_dms = conto_anagrafiche_dms+1;
		return action;
	}
	function personalDataSorting1(a,b,o){
		return o*(a==b? 0 : (a>b?1:-1));
	}
	
	function personalDataSorting2(a,b,o){
		return o*(a==b? 0 : (a>b?1:-1));
	}
	
	var rowsToColorCivilia = [];
	var rowsTechnicalAgent = [];
	var rowsToColorDms = [];
	var conto_anagrafiche_dms = 0;
	$(document).ready(function() {
		init();
		/*<c:set var="page" value="1"/>*/
	    /*<c:set var="orderColumn" value="idPraticaEvento"/>*/
	    /*<c:set var="orderDirection" value="desc"/>*/
	    
	    $("#jqTablePersonalData").jqGrid({
	        data: ${HtmlUtilities.toJson(dettaglio.getAnagrafiche())},
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
	        colModel:[{
	        	"sortable": true,
	        	"name" : "tipoAnagrafica",
	        	"formatter" : personalDataTypeFormatterHander,
	        	"sorttype" : "tipoAnagrafica",
	        	"sortfunc" : personalDataSorting1,
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
	        	"name" : "ruolo",
	        	"formatter" : personalDataRoleFormatterHander,
	        	"sorttype" : "Integer",
	        	"sortfunc" : personalDataSorting2,
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
                for (var i = 0; i < rowsToColorCivilia.length; i++) {
                	$("#jqTablePersonalData").find("tr#" + rowsToColorCivilia[i] + " td").css("background-color", "#FFF1EF");
                    $("#jqTablePersonalData").find("tr#" + rowsToColorCivilia[i] + " td").css("color", "black");
                }
                var found = false;
                for(role of associatedTechnicianAgentRoles){
	                for (var i = 0; i < rowsTechnicalAgent.length && !found; i++) {
	                	if($("#jqTablePersonalData").find("tr#" + rowsTechnicalAgent[i] + " td").find("strong[class=\"hide\"][value=\""+role+"\"]").length > 0){
		                	$("#jqTablePersonalData").find("tr#" + rowsTechnicalAgent[i] + " td").find("strong[class=\"hide\"][value=\""+role+"\"]").removeClass("hide");
		                    $("#jqTablePersonalData").find("tr#" + rowsTechnicalAgent[i] + " td").css("font-weight", " bold");
		                    found = true;
	                	}
	                }
	                	
                }
                for (var i = 0; i < rowsToColorDms.length; i++) {
                	$("#jqTablePersonalData").find("tr#" + rowsToColorDms[i] + " td").css("background-color", "#FF2400");
                    $("#jqTablePersonalData").find("tr#" + rowsToColorDms[i] + " td").css("color", "black");
                }
                rowsToColorCivilia = []
                rowsTechnicalAgent = []
                rowsToColorDms = []
                
             },
	        width: $('#anagrafica_table_div').width() - 2
	    }).navGrid('#pager', {edit: false, add: false, del: false, search: false
    });
    });
	
	function changeTechnicianAgent(identificativoPratica, idAnagrafica, codiceFiscale) {
		var newTechnicianAgentName = people[codiceFiscale];
		var title = '<div><h6 class="confirm-body-content"><spring:message code="anagrafica.tecnico.procuratore.cambia" /> '+currentTechnicianAgentName+' <spring:message code="anagrafica.a" /> '
					+ newTechnicianAgentName+'</h6></div>'
        $('<div></div>').appendTo('body')
                .html(title)
                .dialog({
                    modal: true,
                    title: '<spring:message code="anagrafica.tecnico.procuratore.confirm" />',
                    zIndex: 10000,
                    width: 300,
                    height: 150,
                    resizable: false,
                    buttons: {
                        'Conferma': function () {
                      		parent.$("#modAssociatedTechnicianAgentForm #idPratica").val(identificativoPratica);
                      		parent.$("#modAssociatedTechnicianAgentForm #technicianId").val(idAnagrafica);
                      		parent.$("#modAssociatedTechnicianAgentForm #technicianTaxCode").val(codiceFiscale);
                      		parent.$("#modAssociatedTechnicianAgentForm").submit();
                            $(this).dialog("close");
                        },
                        'Annulla': function () {
                            $(this).dialog("close");
                        }
                    },
                    close: function (event, ui) {
                        $(this).remove();
                    }
                });
    }
</script>

<div id="anagraficaContainer">


	<c:if test="${!abilitazioneConsultazione}">
		<div class="nascondiutente table-add-link">
		    <a href="<%=path%>/pratica/dettaglio/collegaAnagrafica.htm">
		        <spring:message code="pratica.comunicazione.collega.anagrafica"/>
		        <img src="<%=path%>/themes/default/images/icons/add.png" alt="<spring:message code="pratica.comunicazione.collega.anagrafica"/>" title="<spring:message code="pratica.comunicazione.collega.anagrafica"/>">
		    </a>
		</div>
	</c:if>
	<div id="anagrafica_table_div" class="row">
        <table id="jqTablePersonalData"></table> 
        <div id="pager"></div> 
    </div>
    <div id="timeLinePersonalDataDiv" class="hidden">
		<c:set var="identificativo_pratica" value="${dettaglio.identificativoPratica}" scope="request"/>
		
		<c:set var="anagraficheList" value="${dettaglio.getAnagrafiche().stream().filter(anag-> anag.getProvenienza() == null).toList()}" scope="request"/>
		<c:set var="status" value="${status}" scope="request"/>
		<c:set var="provenienza_idx" value="${1}" scope="request"/>
		<c:import url="/themes/default/views/main/body/pratiche/dettaglio/anagrafica_slides.jsp"/>
		<div class="clear"></div>
		
		<c:set var="anagraficheList" value="${dettaglio.getAnagrafiche().stream().filter(anag-> Costants.CIVILIA.equalsIgnoreCase(anag.getProvenienza())).toList()}" scope="request"/>
		<c:set var="status" value="${status}" scope="request"/>
		<c:set var="provenienza_idx" value="${2}" scope="request"/>
		<c:import url="/themes/default/views/main/body/pratiche/dettaglio/anagrafica_slides.jsp"/>
		<div class="clear"></div>
		
		<c:set var="anagraficheList" value="${dettaglio.getAnagrafiche().stream().filter(anag-> anag.getProvenienza() != null && !Costants.CIVILIA.equalsIgnoreCase(anag.getProvenienza())).toList()}" scope="request"/>
		<c:set var="status" value="${status}" scope="request"/>
		<c:set var="provenienza_idx" value="${3}" scope="request"/>
		<c:import url="/themes/default/views/main/body/pratiche/dettaglio/anagrafica_slides.jsp"/>
		<div class="clear"></div>
	</div>
	
</div> 

<div id="personal_data_detail_modal" class="hidden" class="modal-content"></div>

<form class="hidden" id="modAssociatedTechnicianAgentForm" action="<%=path%>/pratica/comunicazione/cambia_tecnico_procuratore.htm">
	<input type="hidden" name="id_pratica" id="idPratica" />
	<input type="hidden" name="technician_id" id="technicianId" />
	<input type="hidden" name="technician_tax_code" id="technicianTaxCode" />
</form>
