
<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO"%>


<c:choose>
	<c:when test="${anagrafica.tipoAnagrafica == 'G'}">
		<td>
			<div class=" anagraficacircle circle <c:if test="${status.last}">lastCircle</c:if>  
                      		<c:choose>
								<c:when test="${anagrafica.provenienza eq 'CIVILIA'}">sfondorosso</c:when>
								<c:when test="${anagrafica.provenienza eq 'DMS'}">sfondoverde</c:when>
							  	<c:otherwise>sfondobianco</c:otherwise>
							</c:choose>"
                         		id="<c:out value="anagrafica_${provenienza_idx}_${conto_anagrafiche}" />" >
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.ruolo" />
					</label>
					<c:if
						test="${!(((anagrafica.ruolo eq 'Richiedente') && (fn:length(richiedenti) lt 2) ) ||  ( (anagrafica.ruolo eq 'Beneficiario') && (fn:length(beneficiari) lt 2))) && !(anagrafica.provenienza eq 'CIVILIA' || anagrafica.provenienza eq 'DMS' )}">
						<a style="padding: 10px; width: 75%;"
							href="<%=path%>/pratica/dettaglio/collegaAnagrafica/scollega.htm?idAnagrafica=${anagrafica.idAnagrafica}&idRuolo=${anagrafica.idRuolo}"
							class='nascondiutente showdetail'> <img
							src="<%=path%>/themes/default/images/icons/link_break.png"
							alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>"
							title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
							&nbsp;&nbsp;<spring:message
								code="pratica.comunicazione.anagrafica.scollega.click" />
						</a>
					</c:if>
					<input class="input_anagrafica_disable" type="text" disabled=""
						value="${anagrafica.ruolo}">
				</div>
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.denominazione" />
					</label>
					<textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.ragioneSociale}</textarea>
				</div>
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.partitaiva" />
					</label> <input class="input_anagrafica_disable" type="text" disabled=""
						value="${anagrafica.partitaIVA}">
				</div>
<!--  LOR 
				<c:if test="${anagrafica.provenienza ne 'CIVILIA' && anagrafica.provenienza ne 'DMS' && not empty anagrafica.ruolo_civilia}">
					<div class="ctrlHolder">
						<label class="required"> <spring:message
								code="pratica.collegaAnagrafica.etichetta" />
						</label> <input class="input_anagrafica_disable" type="text" disabled=""
							value="${anagrafica.ruolo_civilia}">
					</div>
				</c:if>
 -->				
				<div id="<c:out value="anagrafica_detail_${provenienza_idx}_${conto_anagrafiche}" />"
					class="modal-content"></div>

				<div class="cerca_lente_rosso  showdetail">
					<spring:message
						code="pratica.comunicazione.anagrafica.apridettaglio.click" />
				</div>
			</div>
		</td>
	</c:when>
	<c:otherwise>
		<td class="anagraficadettaglio">
		
			<div
				class=" anagraficacircle circle <c:if test="${status.last}">lastCircle</c:if> 
                    <c:choose>
						<c:when test="${anagrafica.provenienza eq 'CIVILIA'}">sfondorosso</c:when>
						<c:when test="${anagrafica.provenienza eq 'DMS'}">sfondoverde</c:when>
					   	<c:otherwise>sfondobianco</c:otherwise>
					</c:choose>"
				id="<c:out value="anagrafica_${provenienza_idx}_${conto_anagrafiche}" />">
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.ruolo" />
					</label>
					<c:if
						test="${!(((anagrafica.ruolo eq 'Richiedente') && (fn:length(richiedenti) lt 2) ) ||  ( (anagrafica.ruolo eq 'Beneficiario') && (fn:length(beneficiari) lt 2))) && !(anagrafica.provenienza eq 'CIVILIA' || anagrafica.provenienza eq 'DMS' )}">
						<a style="padding: 10px; width: 75%;"
							href="<%=path%>/pratica/dettaglio/collegaAnagrafica/scollega.htm?idAnagrafica=${anagrafica.idAnagrafica}&idRuolo=${anagrafica.idRuolo}"
							class='nascondiutente showdetail'> <img
							src="<%=path%>/themes/default/images/icons/link_break.png"
							alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>"
							title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
							&nbsp;&nbsp;<spring:message
								code="pratica.comunicazione.anagrafica.scollega.click" />
						</a>
					</c:if>
					<input class="input_anagrafica_disable" type="text" disabled=""
						value="${anagrafica.ruolo}">
				</div>
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.nome" />
					</label>
					<textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.nome}</textarea>
				</div>
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.cognome" />
					</label> <input class="input_anagrafica_disable" type="text" disabled=""
						value="${anagrafica.cognome}">
				</div>
				<div class="ctrlHolder">
					<label class="required"> <spring:message
							code="pratica.comunicazione.anagrafica.codicefiscale" />
					</label> <input class="input_anagrafica_disable" type="text" disabled=""
						value="${anagrafica.codiceFiscale}">
				</div>
				<c:if test="${anagrafica.isDittaIndividuale}">
					<div class="ctrlHolder">
						<label class="required"> <spring:message
								code="pratica.comunicazione.anagrafica.denominazione" />
						</label> <span class="value">${anagrafica.ragioneSociale}</span>
					</div>
					<div class="ctrlHolder">
						<label class="required"> <spring:message
								code="pratica.comunicazione.anagrafica.partitaiva" />
						</label> <input class="input_anagrafica_disable" type="text" disabled=""
							value="${anagrafica.partitaIVA}">
					</div>
					<div class="ctrlHolder">
						<label class="required"> <spring:message
								code="pratica.comunicazione.anagrafica.dittaindividuale" />
						</label> <input class="input_anagrafica_disable" type="text" disabled=""
							value="<spring:message code="pratica.comunicazione.anagrafica.dittaindividuale.text"/>">
					</div>
				</c:if>


				<div id="<c:out value="anagrafica_detail_${provenienza_idx}_${conto_anagrafiche}" />"
					class="modal-content">
				</div>
				<c:if test='${!associatedTechnicianAgents.isEmpty() && anagrafica.ruolo != null && anagrafica.ruolo.equals("Tecnico professionista")}'>
					<c:choose>
                        <c:when test='${!associatedTechnicianAgents.contains(anagrafica.codiceFiscale.toUpperCase())}'>
	                        <div id="changeTechnicanBtn" class="cerca_button showdetailchange">
	                            <spring:message code="pratica.comunicazione.dettaglio.anagrafica.assegnaTecnicoOperatore"/>
	                        </div>
	                        <form class="hide" id="changeAssociatedTechnicianAgentForm" action="<%=path%>/pratica/comunicazione/cambia_tecnico_procuratore.htm">
	                        	<input type="hidden" name="id_pratica" value="${dettaglio.idPratica}" />
	                        	<input type="hidden" name="technician_tax_code" value="${anagrafica.codiceFiscale}" />
	                        	<input type="hidden" name="technician_id" value="${anagrafica.idAnagrafica}" />
	                        </form>
						</c:when>
					    <c:when test='${associatedTechnicianAgents.contains(anagrafica.codiceFiscale)}'>
					    	<div class="showlabel">
					    		<span><spring:message code="pratica.comunicazione.dettaglio.anagrafica.procuratore"></spring:message></span>
					    	</div>
					 	</c:when>
					</c:choose>
				</c:if>
				<div class="cerca_lente_rosso  showdetail">
					<spring:message
						code="pratica.comunicazione.anagrafica.apridettaglio.click" />
				</div>
			</div>
		</td>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	$(document).ready(function() {
		// Load dialog on page load
		//$('#basic-modal-content').modal();

		// Load dialog on click
		$('#<c:out value="anagrafica_${provenienza_idx}_${conto_anagrafiche}"/> .showdetail').click(function(e) {
			$.ajax({
				type: 'POST',
				url: '<%=path%>/pratica/comunicazione/dettaglio/anagrafica.htm',
				data: {
					identificativo_pratica: '${identificativo_pratica}', 
					id_anagrafica: '${anagrafica.idAnagrafica}', 
					provenienza: '${anagrafica.provenienza}',
					idx: '${conto_anagrafiche}'
				}
			}).done(function(data) {
				var wHeight = $(window).height() * 0.8;
				$('.dettaglioAnagraficaContainer').empty();
				$('#anagrafica_detail_${provenienza_idx}_${conto_anagrafiche}').html(data);
				$('#anagrafica_detail_${provenienza_idx}_${conto_anagrafiche}').dialog({
					title: 'Dettaglio anagrafica',
					modal: true,
					height: wHeight,
					width: '50%',
					dialogClass: 'cross_modal', /*,
					 create: function(event, ui) {
					 },
					 open: function(event, ui) {
					 $('html').css('overflow', 'hidden');
					 },
					 close: function(event, ui) {
					 $('html').css('overflow', 'scroll');
					 }*/
					buttons: {
						OK: function() {
							$(this).dialog("close");
						}
					}
				});
				return false;
			});
		});
        $('#<c:out value="anagrafica_${provenienza_idx}_${conto_anagrafiche}"/> #changeTechnicanBtn').click(function(e) {
        	e.stopPropagation();
     	    $('#<c:out value="anagrafica_${provenienza_idx}_${conto_anagrafiche}"/>').find("#changeAssociatedTechnicianAgentForm").submit()
        });
	});
</script>