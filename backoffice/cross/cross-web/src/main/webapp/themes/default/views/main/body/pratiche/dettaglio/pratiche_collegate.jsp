<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="w" uri="/WEB-INF/wegoTag.tld"  %>
<%@ page import="it.wego.cross.session.MultiSubSessionManager" %>
<%@ page import="it.wego.cross.utils.Utilities" %>

<script type="text/javascript">

    function detailPraticaCollegata(idRecord) {
        console.log("DETAIL: " + idRecord);
        var sessionId = "${MultiSubSessionManager.generateId()}";
        window.open('${path}/pratiche/dettaglio.htm?id_pratica=' + idRecord+'&s_code='+sessionId, sessionId); 
    }

    function selectPraticaDaCollegare(idPratica) {
        console.log("Pratica: " + idPratica);

        $.ajax({
            url: '${path}/pratiche/pratiche_collegate/add.htm',
            dataType: "json",
            data: {
                idPratica: ${pratica.idPratica},
                idPraticaCollegata: idPratica
            },
            async: false,
            success: function(data) {
                $('.ui-dialog-content').dialog('close');
                if (data.success) {
                    var praticaCollegataTemplateHtml = $("#template-pratica-collegata").html();
                    var praticaCollegataTemplate = Handlebars.compile(praticaCollegataTemplateHtml);
                    var praticaCollegataNewBox = praticaCollegataTemplate({
                        idPratica: data.attributes.idPraticaCollegata,
                        oggettoPraticaCollegata: data.attributes.oggettoPraticaCollegata,
                        dataRicezionePraticaCollegata: data.attributes.dataRicezionePraticaCollegata,
                        protocolloPraticaCollegata: data.attributes.protocolloPraticaCollegata,
                        statoPraticaCollegata: data.attributes.statoPraticaCollegata
                    });

                    $('.container-praticacollegata table tr').append(praticaCollegataNewBox);

                    $('#summary_block-praticacollegata-' + idPratica + ' .remove').on('click', function(event) {
                        return confirmDeletePraticacollegata(event);
                    });

                    $('#summary_block-praticacollegata-' + idPratica + ' .detail').on('click', function(event) {
                    	event.preventDefault();
                        detailPraticaCollegata(data.attributes.idPraticaCollegata);
                    });
                    addRowToTableToLinkedPaperworks(JSON.parse(data.attributes.praticaCollegata));
                    $("#linkedPaperworksEmptyRow").remove();
                } else {
                    mostraMessaggioAjax(data.message, "error");
                }
            }
        });
    }

    function addRowToTableToLinkedPaperworks(praticaCollegata){
    	var tableRow = '<td>'+praticaCollegata.oggettoPratica+'</td>';
        tableRow += '<td>'+praticaCollegata.identificativoPratica+'</td>';
        tableRow += '<td>'+praticaCollegata.numeroProtocollo+'</td>';
        tableRow += '<td>'+praticaCollegata.tipoPratica+'</td>';
        tableRow += '<td>'+praticaCollegata.numeroPratica+'</td>';
        tableRow += '<td>'+praticaCollegata.anno+'</td>';
        tableRow += '<td>'+praticaCollegata.descrizioneStato+'</td>';
        tableRow += '<td>'+praticaCollegata.inCarico+'</td>';
        tableRow += '<td></td>';
        tableRow += '<td></td>';
        tableRow += '<td>'+createLinkedPaperworksCellAction(praticaCollegata)+'</td>';
        $("#linkedPaperworksTable tbody").append('<tr id="associated_'+praticaCollegata.idPratica+'">'+tableRow+'</tr>');
    }

    function createLinkedPaperworksCellAction(praticaCollegata){
    	action = '<a data-holder="'+praticaCollegata.idPratica+'" href="javascript:void(0)" onclick="confirmDeletePraticacollegata(event)">'+
				 '<img data-holder="'+praticaCollegata.idPratica+'" src="<%=path%>/themes/default/images/icons/link_break_big.png"'+
				 'alt="<spring:message code="pratica.comunicazione.dettaglio.scollega.pratiche.collegate.title"/>"'+
				 'title="<spring:message code="pratica.comunicazione.dettaglio.scollega.pratiche.collegate.title"/>">'+
				 '</a>&nbsp;'+
				 '<a class="cerca_lente_dettaglio" href="javascript:detailPraticaCollegata('+praticaCollegata.idPratica+')"> </a>'
		return action;    
    }

    function deletePraticaCollegata(idRecord, uiRemoveCallback) {
        console.log("DELETE: " + idRecord);

        $.get(
                '${path}/pratiche/pratiche_collegate/delete.htm',
                {
                    idPratica: "${pratica.idPratica}",
                    idPraticaCollegata: idRecord
                },
        wgf.ajax.responseHandler(function() {
        	$("#associated_"+idRecord).remove();
            uiRemoveCallback(idRecord);
        }));

    }

    function addPraticaCollegata() {
        console.log("ADD ");
        wgf.utils.showFormDialog(
                'Collega pratica',
                '${path}/form/ajax/view.htm?idForm=it.wego.cross.forms.common.PraticheCollegateGrid');
    }
    $(document).ready(function () {
		$("#summaryTable").addClass("hidden");    	
    })

</script>
<c:if test="${!abilitazioneConsultazione}">
	<span>
		<w:form formId="it.wego.cross.forms.common.SummaryBlockPraticheCollegate" />
	</span>
</c:if>
<table id="linkedPaperworksTable" cellspacing="0" cellpadding="0" class="master">
		
		<thead> 
			<tr>
				<th><spring:message code="pratica.comunicazione.dettaglio.identificativo" /></th>
				<th><spring:message code="pratica.dettaglio.collegate.identificativo" /></th>
				<th><spring:message code="pratica.copertina.protocollo.segnatura.label" /></th>
				<th><spring:message code="pratica.dettaglio.collegate.tipo"/></th>
				<th><spring:message code="pratica.dettaglio.collegate.numero"/></th>
				<th><spring:message code="pratica.dettaglio.collegate.anno"/></th>
				<th><spring:message code="pratica.dettaglio.collegate.stato"/></th>
				<th><spring:message code="pratiche.elenco.incarico"/></th>
				<th><spring:message code="pratiche.elenco.dataPratica"/></th>
				<th class="width10"><spring:message code="pratica.dettaglio.collegate.a"/></th>
				<th><spring:message code="pratica.comunicazione.dettaglio.allegati.azioni"/></th>
			</tr>

		</thead>   
		<tbody>
			<c:if test="${not empty Utilities.getAllOfLinkedPaperworks(pratica)}">
				<c:forEach items="${Utilities.sortPaperworksByRIC(Utilities.getAllOfLinkedPaperworks(pratica))}" var="linked" begin="0">
					<tr id="associated_${linked.idPratica }">
						<td>${linked.oggettoPratica }</td>
						<td>${linked.identificativoPratica }</td>
						<td>${linked.protocollo.segnatura }</td>
						<td>${linked.tipoPratica }</td>
						<td>${linked.numeroPratica }</td>
						<td>${linked.anno }</td>
						<td>${linked.idStatoPratica.descrizione }</td>
						<td>
							<c:if test="${not empty linked.idUtente}">
								${linked.idUtente.cognome } 
								<c:if test="${linked.idUtente.nome.toLowerCase() != 'archivio'}">${linked.idUtente.nome}</c:if>
							</c:if>
						</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${linked.dataRicezione}" /></td>
						<td>
							<c:forEach items="${linked.linkedToList}" var="parent" begin="0">
								${parent.tipoPratica }-${parent.numeroPratica }-${parent.anno }<br/>
							</c:forEach>
						</td>
						<td>
							<c:if test="${!abilitazioneConsultazione}">
								<a data-holder="${linked.idPratica}" href="javascript:void(0)" onclick="confirmDeletePraticacollegata(event)" /> 
								<img data-holder="${linked.idPratica}" 
									src="<%=path%>/themes/default/images/icons/link_break_big.png"
									alt="<spring:message code="pratica.comunicazione.dettaglio.scollega.pratiche.collegate.title"/>"
									title="<spring:message code="pratica.comunicazione.dettaglio.scollega.pratiche.collegate.title"/>">
								</a>&nbsp;
							</c:if>
							<c:if test="${!abilitazioneConsultazione || (linked.idStatoPratica.codice=='C' || linked.idStatoPratica.codice == 'X')}">
								<a <a class="<c:if test="${abilitazioneConsultazione}">clickable</c:if> cerca_lente_dettaglio" title="<spring:message code="pratica.comunicazione.dettaglio"/>" href="javascript:detailPraticaCollegata(${linked.idPratica })"> </a>
							</c:if>
								
						</td>
					
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty pratica.praticheAssociate}">
				<tr id="linkedPaperworksEmptyRow">
					<td colspan="10"><spring:message code="pratica.comunicazione.dettaglio.pratiche.associate.noresult" /></td>
				</tr>
			</c:if>
		</tbody>
    </table>
