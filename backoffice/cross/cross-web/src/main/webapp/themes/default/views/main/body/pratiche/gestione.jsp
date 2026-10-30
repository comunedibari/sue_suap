<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="riassegnaPraticaUrl" value="${path}/pratica/riassegna.htm"/>

<%@ page import="it.wego.cross.constants.Constants" %>

<c:set var="notTargetSuap" value='${utenteConnesso.utenteRuoloEnte.stream().filter(ruolo->ruolo.idEnte == Constants.SUAP_BARI || ruolo.idEnte == Constants.SUAP_GIOIA_DEL_COLLE).count() == 0}' />

<script type="text/javascript">
	
    function displayConfirmDialog(idPratica, isAdmin) {
        var urlToRedirect = '${riassegnaPraticaUrl}?from=gestisci&idPratica=' + idPratica + "&s_code="+window.name;
        if (isAdmin) {
            window.open(urlToRedirect, "_self");
        } else {
            var confirmDialog = $('<div />').attr({
                title: 'Conferma azione'
            }).html('Vuoi veramente prendere in carico la pratica?');

            $(confirmDialog).dialog({
                resizable: false,
                height: 180,
                modal: true,
                buttons: {
                    Ok: function() {
                        $(this).dialog("close");
                        window.open(urlToRedirect, "_self");
                    },
                    Cancel: function() {
                        $(this).dialog("close");
                    }
                }
            });
        }
    }
</script>


<div id="apertura_pratiche">
    <tiles:insertAttribute name="ricerca" />
    
    <c:set var="showIntegrations" value="true" scope="request" />
    <c:set var="columnForSorting" value="dataUltimoEventoEsterno" scope="request" />
    <c:set var="researchTableDetailUrl" value="${path}/pratiche/dettaglio.htm" scope="request" />
    <tiles:insertAttribute name="research_table" />
    <script type="text/javascript">
	    function gestionePraticheActionsFormatter(cellvalue, options, rowObject) {
	    	var isAdmin = rowObject["adminOnProcEnte"];
	    	var lastExternalEventId = '-1';
	        if(!!rowObject["lastExternalEvent"] && ${notTargetSuap}){
	        	lastExternalEventId = rowObject["lastExternalEvent"]["id"];
	        }                                              
	        var isSameUser = rowObject["isSameUser"];
	        var displayDialog = ' onclick="displayConfirmDialog(\'' + rowObject["idPratica"] + '\', ' + isAdmin + ')"';
	        var technicialButton = '<a title="<spring:message code="ricerca.tecnico.operatore"/>" href="javascript:showTechnicianAgent(\''+rowObject["identificativoPratica"]+'\',\''+rowObject["idPratica"]+'\',\''+lastExternalEventId+'\');" class="tecnico_procuratore" ></a>&nbsp';
	        var scaricaButton = '<a title="<spring:message code="ricerca.scarica"/>" href="${path}/download/pratica.htm?id_pratica='+rowObject["idPratica"]+'" class="scarica4" target="_blank"></a>&nbsp';
	        var riassegnaButton = '';
	        <c:if test="${!abilitazioneConsultazione}">
	        	riassegnaButton='<a href="javascript:void(0)" title="<spring:message code="gestione.button.riassegna"/>" ' + displayDialog + ' class="riassegna2"></a>&nbsp';
	        </c:if>
	        if (isSameUser && !isAdmin) {
	            riassegnaButton = '';
	        }
	        var link = '<div class="gridActionContainerNoWidth">' +
                riassegnaButton +
		        scaricaButton +
		        technicialButton +
            '</div>';
	        return link;
	    }
	    
	    function showTechnicianAgent(identificativoPratica, idPratica, idPraticaEvento) {
	    	var parameters = {};
	    	var templateHtml = $("#template-technicians-agent").html();
	        var procuratoreTemplate = Handlebars.compile(templateHtml);
	    	parameters.identificativoPratica = identificativoPratica;

			$.ajax({
	            type: 'POST',
	            url: '${path}/pratica/tecnico_procuratore.htm',
	            data: parameters,
	            dataType: 'json',
	            success: function(agentData) {
	            	if (!agentData) 
	            		agentData = {}
	           		var templateHtmlTechnicalAgent = procuratoreTemplate(agentData);
	            	
	            	var parameters = {};
	    	    	var templateHtml = $("#template-external-operator-agent").html();
	    	        var procuratoreEsternoTemplate = Handlebars.compile(templateHtml);
	    	    	parameters.idPratica = idPratica;
	    	    	parameters.idPraticaEvento = idPraticaEvento;
	    	        $.ajax({
	    	            type: 'POST',
	    	            url: '${path}/pratica/procuratore_per_evento_esterno.htm',
	    	            data: parameters,
	    	            dataType: 'json',
	    	            success: function(externalAgentData) {
	    	            	if (!externalAgentData) 
	    	            		externalAgentData = {}
	    	            	var templateHtml = procuratoreEsternoTemplate(externalAgentData);
	    		            	
	    		                
	    	            	var dialog = $('<div />').appendTo('body').dialog({
	    	                    modal: true,
	    	                    title: 'Tecnico procuratore',
	    	                    buttons: [{
	    	                            text: "Chiudi",
	    	                            click: function() {
	    	                                $(this).dialog("close");
	    	                            }
	    	                        }],
	    	                    width: 800,
	    	                    position: { my: 'top', at: 'top+20' },
	    	                    close: function() {
	    	                        dialog.remove();
	    	                    }
	    	                }).html(templateHtmlTechnicalAgent + templateHtml);
	    	            	
	    	            }
	    	        });
	            }
	        });
	    }
	    
	</script>
</div>