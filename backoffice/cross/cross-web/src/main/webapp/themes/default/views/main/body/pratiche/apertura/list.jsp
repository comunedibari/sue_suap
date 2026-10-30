<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="dettaglioAperturaPraticaUrl" value="${path}/pratiche/nuove/apertura/dettaglio.htm"/>
<c:set var="riassegnaPraticaUrl" value="${path}/pratica/riassegna.htm"/>

<script type="text/javascript">
    function displayConfirmDialog(idPratica, isAdmin) {
        var urlToRedirect = '${riassegnaPraticaUrl}?from=nuove/apertura&idPratica=' + idPratica + "&s_code="+window.name;
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
                    Ok: function () {
                        $(this).dialog("close");
                        window.open(urlToRedirect, "_self");
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });
        }
    } 

    
</script>
<div id="apertura_pratiche">

    <tiles:insertAttribute name="ricerca" />
    <c:set var="researchTableDetailUrl" value="${dettaglioAperturaPraticaUrl}" scope="request" />
    <c:set var="columnForSorting" value="idPratica" scope="request" />
    <tiles:insertAttribute name="research_table" />
	<script type="text/javascript">
	function gestionePraticheActionsFormatter(cellvalue, options, rowObject) {
        var isAdmin = rowObject["adminOnProcEnte"];
        var technicialButton = '<a title="<spring:message code="ricerca.tecnico.operatore"/>" href="javascript:showTechnicianAgent(\''+rowObject["identificativoPratica"]+'\');" class="tecnico_procuratore" ></a>&nbsp';
        var isSameUser = rowObject["isSameUser"];
        var scaricaButton = '<a title="<spring:message code="ricerca.scarica"/>" href="${path}/download/pratica.htm?id_pratica='+rowObject["idPratica"]+'" class="scarica4" target="_blank"></a>&nbsp';
        
        var displayDialog = ' onclick="displayConfirmDialog(\'' + rowObject["idPratica"] + '\', ' + isAdmin + ')"';
        var riassegnaButton = '<a title="<spring:message code="gestione.button.riassegna"/>" ' + displayDialog + ' class="riassegna2"></a>';
        if (isSameUser && !isAdmin) {
            riassegnaButton = '';
        }
        return '<div class="gridActionContainerNoWidth">' +
	        riassegnaButton +
	        scaricaButton +
	        technicialButton + 
        '</div>';
    }
	function showTechnicianAgent(identificativoPratica) {
    	var parameters = {};
    	var templateHtml = $("#template-technicians-agent").html();
        var procuratoreTemplate = Handlebars.compile(templateHtml);
    	parameters.identificativoPratica = identificativoPratica;
        $.ajax({
            type: 'POST',
            url: '${path}/pratica/tecnico_procuratore.htm',
            data: parameters,
            dataType: 'json',
            success: function(data) {
            	if (!data) 
            		data = {}
           		var templateHtml = procuratoreTemplate(data);
            	
                
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
                }).html(templateHtml || '');
            	
            }
        });
    }
	</script>
</div>


