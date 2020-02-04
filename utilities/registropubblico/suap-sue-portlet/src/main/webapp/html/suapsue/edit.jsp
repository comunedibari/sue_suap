<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/html/common/init.jsp"%>
<liferay-ui:error key="errore.generico" message="errore.generico" />
<liferay-ui:error key="errore.dettaglio.pratica" message="errore.dettaglio.pratica" />
<liferay-ui:error key="errore.end.point" message="errore.end.point" />
<liferay-ui:error key="errore.pratica.protocollo" message="errore.pratica.protocollo" />

<liferay-ui:header title="Dettaglio Pratica" backURL="${backLink}" />
	
<c:if test="${pratica.segnaturaProtocollo.protocollo!=null}">
	<fmt:parseDate value="${pratica.dataRicezione}" pattern="yyyy-MM-dd" var="dataRicezione" />
	<fmt:formatDate value="${dataRicezione}" var="data_ricezione" pattern="dd MMMM yyyy" />	
	<label><strong>Data Ricezione: </strong></label><span><c:out value="${data_ricezione}"/></span>
	<br/>
	
	<fmt:parseDate value="${pratica.dataInizioLavori}" pattern="dd/MM/yyyy" var="dataInizioLavori"/>
    <fmt:formatDate value="${dataInizioLavori}" var="data_inizio_lavori" pattern="dd MMMM yyyy"/>	
	<label><strong>Data Inizio Lavori: </strong></label><span><c:out value="${data_inizio_lavori}"/></span>
	<br/>
	
	<fmt:parseDate value="${pratica.dataFineLavori}" pattern="dd/MM/yyyy" var="dataFineLavori"/>
    <fmt:formatDate value="${dataFineLavori}" var="data_fine_lavori" pattern="dd MMMM yyyy"/>	
	<label><strong>Data Fine Lavori: </strong></label><span><c:out value="${data_fine_lavori}"></c:out></span>
	<br/>
	
	<fmt:parseDate value="${pratica.dataFineLavoriPresunta}" pattern="dd/MM/yyyy" var="dataFineLavoriPresunta"/>
    <fmt:formatDate value="${dataFineLavoriPresunta}" var="data_fine_lavori_presunta" pattern="dd MMMM yyyy"/>	
	<label><strong>Data Fine Lavori Presunta: </strong></label><span><c:out value="${data_fine_lavori_presunta}"></c:out></span>
	<br/>
	
	<fmt:parseDate value="${pratica.dataChiusura}" pattern="yyyy-MM-dd" var="dataChiusura"/>
    <fmt:formatDate value="${dataChiusura}" var="data_chiusura" pattern="dd MMMM yyyy"/>	
	<label><strong>Data Chiusura: </strong></label><span><c:out value="${data_chiusura}"></c:out></span>
	<br/>
	
	<label><strong>Identificativo Pratica: </strong></label><span><c:out value="${pratica.identificativoPratica}"></c:out></span>
	<br/>
	
	<label><strong>Sportello: </strong></label><span><c:out value="${pratica.desSportello}"></c:out></span>
	<br/>
	
	<label><strong>Descrizione Procedimento: </strong></label><span><c:out value="${pratica.desProcedimentoSuap}"></c:out></span>
	<br/>
	
	<label><strong>Istruttore: </strong></label><span><c:out value="${pratica.istruttore}"></c:out></span>
	<br/>
	
	<label><strong>Comune: </strong></label><span><c:out value="${pratica.desComune}"></c:out></span>
	<br/>
	<br/>
<c:forEach items="${pratica.datiCatastali.datoCatastaleSIT}" var="datoCatastale">
	<div class="descrizioneServizioDiv">
		<div class="nomeServizio"><span>Dati Catastali</span></div>
		<span class="dato-catastale"><label><strong>Foglio:</strong></label>&nbsp;<c:out value="${datoCatastale.foglio}"></c:out></span>
		<span class="dato-catastale"><label><strong>Mappale:</strong></label>&nbsp;<c:out value="${datoCatastale.mappale}"></c:out></span>
		<span class="dato-catastale"><label><strong>Subalterno:</strong></label>&nbsp;<c:out value="${datoCatastale.subalterno}"></c:out></span>
	</div>
	<br />
</c:forEach>

<c:forEach items="${pratica.indirizziInterventoSIT.indirizzoInterventoSIT}" var="indirizzo">
	<div class="descrizioneServizioDiv">
		<div class="nomeServizio"><span>Indirizzo Intervento</span></div>
		<span><label><strong>Indirizzo:</strong></label> <c:out value="${indirizzo.localita} ${indirizzo.indirizzo} ${indirizzo.civico} ${indirizzo.cap}" /></span>
		<br/>
		<span><label><strong>Scala:</strong></label> <c:out value="${indirizzo.internoScala}"></c:out></span>
		<br/>
		<span><label><strong>Piano:</strong></label> <c:out value="${indirizzo.piano}"></c:out></span>
		<br/>
		<span><label><strong>Interno:</strong></label> <c:out value="${indirizzo.internoNumero}"></c:out></span>
	</div>
	<br/>
</c:forEach>

<c:forEach items="${pratica.anagraficheSIT.anagraficaSIT}" var="anagrafica">
	<span><label><strong>${anagrafica.desTipoRuolo}:</strong></label> <c:out value="${anagrafica.cognome} ${anagrafica.nome}"></c:out></span>
	<br/>
</c:forEach>
	
	<span><label><strong>Oggetto:</strong></label> <c:out value="${pratica.oggetto}"></c:out></span>
	<br />
	
	<span><label><strong>Numero Protocollo Pratica:</strong></label> <c:out value="${pratica.segnaturaProtocollo.protocollo}"></c:out></span>
	<br />
	
	<span><label><strong>Anno Protocollo Pratica: </strong></label> <c:out value="${pratica.segnaturaProtocollo.anno}"></c:out></span>
	<br/>
	
	<fmt:parseDate value="${pratica.dataProtocollo}" pattern="yyyy-MM-dd" var="dataProtocollo"/>
    <fmt:formatDate value="${dataProtocollo}" var="data_protocollo" pattern="dd MMMM yyyy"/>	
	<span><label><strong>Data Protocollo Pratica:</strong></label> <c:out value="${data_protocollo}"></c:out></span>
	<br/>
	
<c:forEach items="${pratica.procedimentiSIT.procedimentoSIT}" var="procedimento">
	<span><label><strong>Procedimento: </strong></label> <c:out value="${procedimento.desProcedimento}"></c:out></span>
	<br />
</c:forEach>
	
	<span><label><strong>Sanatoria:</strong></label> 
		<c:choose><c:when test="${pratica.flgSanatoria eq 'N'}">NO</c:when><c:when test="${pratica.flgSanatoria eq 'S'}">SI</c:when></c:choose>
	</span>
	<br/>
	
	<span><label><strong>Deroga:</strong></label>
		<c:choose><c:when test="${pratica.flgDeroga eq 'N'}">NO</c:when><c:when test="${pratica.flgDeroga eq 'S'}">SI</c:when></c:choose>
	</span>
	<br/>
	
	<span><label><strong>Stato Pratica:</strong></label> <c:out value="${pratica.desStatoPratica}"></c:out></span>
	<br/>
	
	<span><label><strong>Responsabile Procedimento:</strong></label> <c:out value="${pratica.responsabileProcedimento}"></c:out></span>
</c:if>