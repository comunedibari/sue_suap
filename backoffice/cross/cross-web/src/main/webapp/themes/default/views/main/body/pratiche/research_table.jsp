<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="riassegnaPraticaUrl" value="${path}/pratica/riassegna.htm"/>

<script type="text/javascript">
	var maxCadastralData = 8;
	var maxApplicant = 8;
	var maxAdresses = 5;
	var maxOperations = 5;

	function paperworkIdentificationFormatterHandler(cellvalue, options, rowObject) {
    	var identificativoPratica = rowObject["complexId"]["identificativoPratica"] || "vuoto";
        var protocollo = rowObject["complexId"]["fascicolo"] || "vuoto";
        var idPratica = rowObject["complexId"]["idPratica"] || "vuoto";
        var cell= '<span class="mini_table">';
        cell+="<span>" + idPratica + "</span><br/>";
        cell+="<span>" + identificativoPratica + "</span><br/>";
        cell+="<span>" + protocollo + "</span><br/>";
        cell+="</span>";
		return cell;
    }

	function paperworkIdentificationCellattrHandler(cellvalue, options, rowObject) {
		var identificativoPratica = rowObject["complexId"]["identificativoPratica"] || "vuoto";
        var protocollo = rowObject["complexId"]["fascicolo"] || "vuoto";
        var idPratica = rowObject["complexId"]["idPratica"] || "vuoto";
        return ' title="'+idPratica+'\n'+identificativoPratica+'\n'+protocollo+'" ';
    }
    
    function paperworkReceivingFormatterHandler(cellvalue, options, rowObject) {
    	if (rowObject["integrazione"] === "S"){
            rowsToColor[rowsToColor.length] = options.rowId;
    	}

    	var receivingDate = rowObject["receiving"]["receivingDate"] || "vuoto";
    	var protocolNumber = rowObject["receiving"]["protocolNumber"] || "vuoto";
    	var protocolDate = rowObject["receiving"]["protocolDate"] || "vuoto";
        var cell= '<span class="mini_table">';
        cell+="<span>" + receivingDate + "</span><br/>";
        cell+="<span>" + protocolNumber + "</span><br/>";
        cell+="<span>" + protocolDate + "</span><br/>";
        cell+="</span>";
		return cell;
    }
    function paperworkReceivingCellattrHandler(cellvalue, options, rowObject) {
    	if (rowObject["integrazione"] === "S"){
            rowsToColor[rowsToColor.length] = options.rowId;
    	}

    	var receivingDate = rowObject["receiving"]["receivingDate"] || "vuoto";
    	var protocolNumber = rowObject["receiving"]["protocolNumber"] || "vuoto";
    	var protocolDate = rowObject["receiving"]["protocolDate"] || "vuoto";
        var cell= '';
        cell+= receivingDate + "\n";
        cell+= protocolNumber + "\n";
        cell+= protocolDate + "\n";
        return ' title="'+cell+'" ';
    }
     
    function paperworkOperationsFormatterHandler(cellvalue, options, rowObject) {
    	var operations = rowObject["operations"];
       	var cell= '<span class="mini_table">';
        if(!!operations){
            for (var i = 0; i < operations.length && i < maxOperations; i++ ){
            	operation = operations[i]
	        	cell+="<span>" + operation + "</span><br/>";
            }
        }
        cell+="</span>";
        
		return cell;
    }    
    function paperworkOperationsCellattrHandler(cellvalue, options, rowObject) {
    	var operations = rowObject["operations"];
       	var cell= '';
        if(!!operations){
            for (operation of operations)
	        	cell+=operation + "\n";
        }
        
        return ' title="'+cell+'" ';
    }    
    
    function paperworkAddressesFormatterHandler(cellvalue, options, rowObject) {
    	var addresses = rowObject["addresses"];
       	var cell= '<span class="mini_table">';
        if(!!addresses){
            for(var i = 0; i < addresses.length && i < maxAdresses; i++){
	        	address = addresses[i]
            	cell+="<span>" + address["streetName"] + "," +address["streetNumber"]+ "</span><br/>";
            }
        }
        cell+="</span>";
		return cell;
    }
    function paperworkAddressesCellattrHandler(cellvalue, options, rowObject) {
    	var addresses = rowObject["addresses"];
       	var cell= '';
        if(!!addresses){
            for (address of addresses)
	        	cell+=address["streetName"] + "," +address["streetNumber"]+ "\n";
        }
        return ' title="'+cell+'" ';
    }

    function paperworkCadastralDataFormatterHandler(cellvalue, options, rowObject) {
		var cadastralDataList = rowObject["cadastralDataList"];
	    var cell= '<span class="mini_table">';
	    if(!!cadastralDataList){
	        for (var i = 0 ; i < cadastralDataList.length && i < maxCadastralData; i++){
	        	cadastralData = cadastralDataList[i];
	        	section = cadastralData["section"] || "vuoto"
	        	sheet =cadastralData["sheet"] || "vuoto"
	        	map =cadastralData["map"] || "vuoto"
	        	subordinate =cadastralData["subordinate"] || "vuoto"
	        	cell+="<span>" + section + "-" +sheet+"-" +map+"-" +subordinate+ "</span><br/>";
	        }
	    }
        cell+="</span>";
		return cell;
	}

    function paperworkCadastralDataCellattrHandler(cellvalue, options, rowObject) {
		var cadastralDataList = rowObject["cadastralDataList"];
	    var cell= '';
	    if(!!cadastralDataList){
	        for (cadastralData of cadastralDataList){
	        	section = cadastralData["section"] || "vuoto"
	        	sheet =cadastralData["sheet"] || "vuoto"
	        	map =cadastralData["map"] || "vuoto"
	        	subordinate =cadastralData["subordinate"] || "vuoto"
	        	cell+=section + "-" +sheet+"-" +map+"-" +subordinate+ "\n";
	        }
	    }
	    return ' title="'+cell+'" ';
	}

    function paperworkApplicantsFormatterHandler(cellvalue, options, rowObject) {
		var applicant = rowObject["richiedente"];
		applicants = applicant.split("<br />")
		var cell= '<span class="mini_table">';
	    if(!!applicants){
	        for (var i = 0 ; i < applicants.length && i < maxApplicant; i++){
	        	cell+="<span>" + applicants[i].trim()+ "</span><br/>";
	        }
	    }
        cell+="</span>"; 
		return cell;
	}

    function paperworkApplicantsCellattrHandler(cellvalue, options, rowObject) {
    	var applicant = rowObject["richiedente"];
    	applicants = applicant.split("<br />")
		var cell= '';
	    if(!!applicants){
	        for (var i = 0 ; i < applicants.length; i++){
	        	cell+= applicants[i].trim() + "\n";
	        }
	    } 
	    return ' title="'+cell+'" ';
	}
    
    function paperworkInternalEventFormatterHandler(cellvalue, options, rowObject) {
        var cell= '<span class="mini_table">';
    	if(!!rowObject["lastInternalEvent"]){
	    	var type = rowObject["lastInternalEvent"]["type"] || "vuoto";
	    	var date = rowObject["lastInternalEvent"]["date"] || "vuoto";
	    	var protocolNumber = rowObject["lastInternalEvent"]["protocolNumber"] || "vuoto";
	    	var protocolDate = rowObject["lastInternalEvent"]["protocolDate"] || "vuoto";
	    	var operator = rowObject["lastInternalEvent"]["operator"] || "vuoto";
	        cell+="<span>" + type + "</span><br/>";
	        cell+="<span>" + date + "</span><br/>";
	        cell+="<span>" + protocolNumber + "</span><br/>";
	        cell+="<span>" + protocolDate + "</span><br/>";
	        cell+="<span>" + operator + "</span><br/>";
    	}
        cell+="</span>";
		return cell;
    }

    function paperworkInternalEventCellattrHandler(cellvalue, options, rowObject) {
        var cell= '';
    	if(!!rowObject["lastInternalEvent"]){
	    	var type = rowObject["lastInternalEvent"]["type"] || "vuoto";
	    	var date = rowObject["lastInternalEvent"]["date"] || "vuoto";
	    	var protocolNumber = rowObject["lastInternalEvent"]["protocolNumber"] || "vuoto";
	    	var protocolDate = rowObject["lastInternalEvent"]["protocolDate"] || "vuoto";
	    	var operator = rowObject["lastInternalEvent"]["operator"] || "vuoto";
	        cell+=type + "\n";
	        cell+=date + "\n";
	        cell+=protocolNumber + "\n";
	        cell+=protocolDate + "\n";
	        cell+=operator + "\n";
    	}
    	return ' title="'+cell+'" ';
    }
    
    function paperworkExternalEventFormatterHandler(cellvalue, options, rowObject) {
        var cell= '<span class="mini_table">';
    	if(!!rowObject["lastExternalEvent"]){
	    	var type = rowObject["lastExternalEvent"]["type"] || "vuoto";
	    	var date = rowObject["lastExternalEvent"]["date"] || "vuoto";
	    	var protocolNumber = rowObject["lastExternalEvent"]["protocolNumber"] || "vuoto";
	    	var protocolDate = rowObject["lastExternalEvent"]["protocolDate"] || "vuoto";
	    	cell+="<span>" + type + "</span><br/>";
	        cell+="<span>" + date + "</span><br/>";
	        cell+="<span>" + protocolNumber + "</span><br/>";
	        cell+="<span>" + protocolDate + "</span><br/>";
    	}
        cell+="</span>";
		return cell;
    }
    
    function paperworkExternalEventCellattrHandler(cellvalue, options, rowObject) {
        var cell= '';
    	if(!!rowObject["lastExternalEvent"]){
	    	var type = rowObject["lastExternalEvent"]["type"] || "vuoto";
	    	var date = rowObject["lastExternalEvent"]["date"] || "vuoto";
	    	var protocolNumber = rowObject["lastExternalEvent"]["protocolNumber"] || "vuoto";
	    	var protocolDate = rowObject["lastExternalEvent"]["protocolDate"] || "vuoto";
	    	cell+=type + "\n";
	        cell+=date + "\n";
	        cell+=protocolNumber + "\n";
	        cell+=protocolDate + "\n";
    	}
    	return ' title="'+cell+'" ';
    }
    
    function paperworkNotesFormatterHandler(cellvalue, options, rowObject) {
        var cell= '<span class="mini_table">';
    	if(!!rowObject["lastNote"]){
	    	var date = rowObject["lastNote"]["date"] || "vuoto";
	    	var user = rowObject["lastNote"]["user"] || "vuoto";
	    	var content = rowObject["lastNote"]["content"] || "vuoto";
	        cell+="<span>" + date + "</span><br/>";
	        cell+="<span>" + user + "</span><br/>";
	        cell+="<span>" + content + "</span><br/>";
    	}
        cell+="</span>";
		return cell;
    }
    
    function paperworkNotesCellattrHandler(cellvalue, options, rowObject) {
        var cell= '';
    	if(!!rowObject["lastNote"]){
	    	var date = rowObject["lastNote"]["date"] || "vuoto";
	    	var user = rowObject["lastNote"]["user"] || "vuoto";
	    	var content = rowObject["lastNote"]["content"] || "vuoto";
	        cell+= date + "\n";
	        cell+= user + "\n";
	        cell+= content + "\n";
    	}
    	return ' title="'+cell+'" ';
    }
    
    function paperworkDaysFormatterHandler(cellvalue, options, rowObject) {
        var cell = "";
        if(!!rowObject["dataUltimoEventoEsterno"] && rowObject["dataUltimoEventoEsterno"] != "01/01/1900"){
        	var dateStr = rowObject["dataUltimoEventoEsterno"];
	        var eventDay = new Date(dateStr.substring(3,5)+"/"+dateStr.substring(0,2)+"/"+dateStr.substring(6,10));
	        dateNow = new Date();
	        dateNow.setHours(0,0,0,0);
	        cell = Math.round((dateNow.getTime() - eventDay.getTime()) / (1000 * 3600 * 24));
        }
		return cell;
    }

    var rowsToColor = [];
    var url = getUrl();
    $(document).ready(function() {
        $("#list").jqGrid
                ({
                    url: url <c:if test="${back == true}">+"?" + $('#form_ricerca').serialize()</c:if>,
                    datatype: "json",
                    colModel:${gestionePraticheColumnModel},
                    /*
                     <c:set var="page" value="1"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                     	<c:set var="page" value="${filtroRicerca.page}"/>
                     </c:if>
                     */
                    page: '${page}',
                    /*
                     <c:set var="orderColumn" value="${columnForSorting}"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     	<c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                     */
                    sortname: '${orderColumn}',
                    /*
                     <c:set var="orderDirection" value="desc"/>
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
                    /*<c:if test="${not empty researchTableDetailUrl}">*/
                    ondblClickRow : function(rowid, status, e){ 
                    	var myGrid = $('#list'),
                    	selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
                    	idPratica = myGrid.jqGrid ('getCell', selRowId, 'idPratica');
                    	$("#dettaglio_pratica_form #idPratica").val(idPratica)
                        $("#dettaglio_pratica_form").trigger('submit');

                    },
                    /*</c:if>*/
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    hidegrid: false,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            }
                    ,
                    caption: '',
                    height: 'auto',
                   	/*<c:if test="${not empty showIntegrations}">*/
                    gridComplete: function () {
                        for (var i = 0; i < rowsToColor.length; i++) {
                        	$("#" + rowsToColor[i]).find("td").css("background-color", "#82d676");
                            $("#" + rowsToColor[i]).find("td").css("color", "black");
                        }
                        rowsToColor = []
                     },
                    /*</c:if>*/
                    width: $('.tableContainer').width()
                }).navGrid('#pager', {edit: false, add: false, del: false, search: false});
    });
</script>
<div class="tableContainer jqtable">
    <table id="list"><tr><td/></tr></table> 
    <div id="pager"></div> 
</div>

<form id="dettaglio_pratica_form" class="hidden" action="${researchTableDetailUrl}">
	<input type="hidden" id="idPratica" name="id_pratica" />
</form>
 
<script id="template-technicians-agent" type="text/x-handlebars-template">
	<div class="ctrlHolder">
	    {{#if nome}}
	        <label>Nome: {{nome}} </label><br/>
	        <label>Cognome: {{cognome}}</label><br/>
	        <label>Codice Fiscale: {{codiceFiscale}}</label><br/>
	        {{#each recapiti}}
  				  <label>Cellulare: {{cellulare}}</label><br/>
  				  <label>Email: {{email}}</label><br/>
  				  <label>PEC: {{pec}}</label><br/>
			{{/each}}
		{{else}}
    	    <span>Procuratore non presente</span>
		{{/if}}
    </div>
</script>
<script id="template-external-operator-agent" type="text/x-handlebars-template">
	{{#if nome}}
	    <div class="ctrlHolder">
			<strong>procuratore che ha inviato l'ultima integrazione:</strong><br/>
	    	<label>Nome: {{nome}} </label><br/>
	    	<label>Cognome: {{cognome}}</label><br/>
	    	<label>Codice Fiscale: {{codiceFiscale}}</label>
		</div>
	{{/if}}
</script>