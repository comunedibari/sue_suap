
<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="it.wego.cross.utils.Utilities" %>
<%@ page import="it.wego.cross.constants.Constants" %>

<script type="text/javascript">
    var statiPraticaList = [];
    <c:forEach items="${statiPratica}" var="statoPratica" varStatus="status">
    <c:if test="${statoPratica.idStatoPratica != 3}">
	    statiPraticaList.push({value: ${statoPratica.idStatoPratica}, text: '${statoPratica.descrizione}'});
    </c:if>
    </c:forEach>
    var path = '${path}';
    $.fn.editable.defaults.emptytext = '[Nessun valore]';
    $(document).ready(function(){

       	$('#pratica_stato').editable({
            source: statiPraticaList,
            url: path + '/pratica/attributi/aggiorna.htm',
            pk: 'pratica',
            params: function(params) {
                var data = {};
                data['idPratica'] = "${pratica.idPratica}";
                data['name'] = params.name;
                data['value'] = params.value;
                data['pk'] = params.pk;
                return data;
            },
            mode: 'inline',
            success: function(response, newValue) {
            	if (response.success === "false") 
                    return response.msg;
                else
                	location.reload();
            }
        });
    });
</script>  

<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/cartiglio.css"/>"  media="all"/>
<h3>
	<spring:message code="cartiglio.dettaglio.CodiceIdentificativo" /> <strong>${cartiglio.codiceFrontOffice}</strong>
	<br/>
	<spring:message code="cartiglio.dettaglio.CodiceInterno" /> <strong>${cartiglio.codiceInterno}</strong>
	<br/>
	<spring:message code="cartiglio.dettaglio.pratica" /> <strong>${cartiglio.codTipoPratica }-${cartiglio.numeroPratica }-${cartiglio.annoPratica }</strong>
	<br/>
	<spring:message code="cartiglio.dettaglio.NumeroFascicolo" /> <strong>${cartiglio.numeroFascicolo }</strong>
</h3>

<div class="sidebar_elemento">
	<c:if test="${!abilitazioneConsultazione}">
		<c:choose>
	        <c:when test="${not empty cartiglio.idModello}">
	             <p style="margin-top:20px;">
	                 <a href="${path}/download/pratica.htm" class="scarica" target="_blank">
	                     <spring:message code="pratica.comunicazione.scaricaIstanza"/>
	                 </a>
	             </p>
	        </c:when>
	        <c:otherwise>
	         	 <br/>
	         	 <p><span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span><p>
	         	 <br/>
	        </c:otherwise>
	    </c:choose>
	    <c:if test="${not empty cartiglio.idUtente}">
		     <form action="${path }/pratica/riassegna.htm" method="post" style="float:left">
		     	<input type="hidden" name="idPratica" value="${cartiglio.idPratica }">
		     	<input type="hidden" name="idPraticaEvento" value="${cartiglio.idPraticaEvento }">
		     	<input type="hidden" name="from" value="${from }">
		     	<button type="submit" class="ui-button ui-corner-all riassegna" >  Riassegna pratica</button>
		     	<div class="bottone_assegna"></div> 
		     </form>
	    </c:if>
     
<%--      <c:if test="${utenteConnesso.idUtente == 1 && isProd && pratica.idStatoPratica.idStatoPratica != 3}"> --%>
<%-- 	     <form action="${path }/extra/return_to_approval.htm" method="post" style="float:left"> --%>
<%-- 	     	<input type="hidden" name="id_pratica" value="${cartiglio.idPratica }"> --%>
	     	
<!-- 	     	<button type="submit" class="ui-button ui-corner-all" >  -->
<%-- 	     		<img title="Stato indefinito" alt="Stato indefinito" src="<%=path%>/themes/default/images/icons/icon_alert.gif" width="18" /> rimanda in accettazione --%>
<!-- 	     	</button> -->
<!-- 	     	<div class="bottone_assegna"></div>  -->
<!-- 	     </form> -->
<%--      </c:if> --%>
	    <c:choose>
	   		<c:when test="${pratica.integrazione eq 'S' and pratica.idComune.idComune eq 5440}">
	   			<p style="margin-top:20px;">
	   				<a href="${path}/pratiche/integrazione/lavorata.htm" class="deletegenerico" title="Rimuovi flag integrazione">
	   					<spring:message code="pratica.comunicazione.integrazione.lavorata"/>
                    </a>
                </p>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </c:if>
	<table class="row">
		<thead>
			<tr>
				<th class="width50"></th>
				<th class="width50"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.TipoPratica" /></a></strong> </td>
				<td>${cartiglio.tipoPratica}</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.NumeroPratica" /></a></strong> </td>
				<td>${cartiglio.numeroPratica}</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.AnnoPratica" /></a></strong> </td>
				<td>${cartiglio.annoPratica}</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.NumeroFascicolo" /></a></strong> </td>
				<td>${cartiglio.numeroFascicolo }</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.AnnoFascicolo" /></a></strong> </td>
				<td>${cartiglio.annoFascicolo }</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.NumeroProtocolloPratica" /></a></strong> </td>
				<td>${cartiglio.numeroProtocollo }</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.DataProtocolloPratica" /></a></strong> </td>
				<td>${Utilities.dateToString(cartiglio.dataProtocollo)}</td>
			</tr>
			
			<c:if test="${cartiglio.showSuapData()}">
				<tr>
					<td><strong><spring:message code="pratica.prot_suap.label" /></a></strong> </td>
					<td>${cartiglio.numeroProtocolloSuap }</td>
				</tr>
				<tr>
					<td><strong><spring:message code="pratica.data_prot_suap.label" /></a></strong> </td>
					<td>${Utilities.dateToString(cartiglio.dataProtocolloSuap)}</td>
				</tr>
			</c:if>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.DataRicezione" /></a></strong> </td>
				<td>${Utilities.dateToString(cartiglio.dataRicezione)}</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.Procuratore" /></a></strong> </td>
				<td>${cartiglio.procuratore.cognome } ${cartiglio.procuratore.nome} </td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.Assegnatario" /></a></strong> </td>
				<td>${cartiglio.assegnatario.cognome } ${cartiglio.assegnatario.nome }</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.Stato" /></a></strong> </td>
				<td>
					<c:if test="${abilitazioneConsultazione}">
						<span>${cartiglio.stato}</span>
					</c:if>
					<c:if test="${!abilitazioneConsultazione}">
						<a href="#" id="pratica_stato" class="" name="pratica_stato" data-type="select">${cartiglio.stato}</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.TipologiaIntervento" /></a></strong> </td>
				<td>
					<c:if test="${not empty cartiglio.tipologiaIntervento}">
					<ul>
					<c:forEach items="${cartiglio.tipologiaIntervento}" begin="0" var="tipologiaIntervento">
						<li>${tipologiaIntervento}</li>	
						</c:forEach>
					</c:if>
					</ul>
				</td>
			</tr>
			<tr>
				<td><strong><spring:message code="cartiglio.dettaglio.OggettoIntervento" /></a></strong> </td>
				<td>${cartiglio.oggettoIntervento }</td>
			</tr>
		</tbody>
	</table>
</div>