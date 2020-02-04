<%@ page pageEncoding="utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="confrontaAnagrafiche" class="hidden">
    <div class="containerAnagrafiche">
        <div class="destinazione ui-corner-all ">
            <form id="destinazioneForm">
                <input type="hidden" name="idAnagrafica"/>
                <input type="hidden" name="idTipoRuoloOriginale"/>
                <div class="label">Dati in database</div>
                <input type="hidden" name="counter"/>
                <ul class="dettaglio">
                    <li class="idTipoPersona">
                        <label for="destinazioneIdTipoPersona">
                            <strong><spring:message code="utenti.tipoAnagrafica"/>:</strong>
                        </label>
                        <select id="destinazioneIdTipoPersona" name="idTipoPersona" class="ui-widget ui-widget-content ui-corner-all">
                            <option value="F">Persona fisica</option>
                            <option value="G">Persona giuridica</option>
                        </select>
                        <input type="hidden" name="idTipoPersonaOriginalValue" />
                    </li>
                    <li class="flgIndividuale">
                        <label for="destinazioneFlgIndividuale">
                            <strong><spring:message code="utenti.flgDittaIndividuale" />:</strong>
                        </label>
                        <select name="flgIndividuale" id="destinazioneFlgIndividuale" class="ui-widget ui-widget-content ui-corner-all">
                            <option value="N">No</option>
                            <option value="S">Si</option>
                        </select>
                    </li>      
                    <li class="idTipoRuolo">
                        <label for="destinazioneIdTipoRuolo">
                            <strong><spring:message code="utenti.tipoRuolo"/>:</strong>
                        </label>
                        <select id="destinazioneIdTipoRuolo" name="idTipoRuolo" class="ui-widget ui-widget-content ui-corner-all">
                            <option value=""></option>
                            <c:forEach items="${tipoRuoloList}" var="tipoRuolo" begin="0">
                                <option class="${tipoRuolo.codRuolo}" value="${tipoRuolo.idTipoRuolo}">${tipoRuolo.descrizione}</option>    
                            </c:forEach>
                        </select>
                    </li>
                    <li class="codiceFiscale">
                        <label for="destinazioneCodiceFiscale">
                            <strong><spring:message code="utenti.codicefiscale"/>:</strong>
                        </label>
                        <div>
                            <input id="destinazioneCodiceFiscale" type="text" name="codiceFiscale" class="ui-widget ui-widget-content ui-corner-all"/>
                        </div>
                    </li>
                    <li class="partitaIva">
                        <label for="destinazionePartitaIva">
                            <strong><spring:message code="utenti.piva"/>:</strong>
                        </label>
                        <input id="destinazionePartitaIva" type="text" name="partitaIva" value="" class="ui-widget ui-widget-content ui-corner-all"/>
                        <span class="partitaIva"></span>
                    </li>
                    <li class="nome">
                        <label for="destinazioneNome">
                            <strong><spring:message code="utenti.nome"/>:</strong>
                        </label>
                        <input id="destinazioneNome" type="text" name="nome" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="cognome">
                        <label for="destinazioneCognome">
                            <strong><spring:message code="utenti.cognome"/>:</strong>
                        </label>
                        <input id="destinazioneCognome" type="text" name="cognome" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="sesso">
                        <label for="destinazioneSesso">
                            <strong><spring:message code="utenti.sesso"/>:</strong>
                        </label>
                        <select id="destinazioneSesso" name="sesso" class="ui-widget ui-widget-content ui-corner-all">
                            <option value="M"><spring:message code="utenti.sessoM"/></option>
                            <option value="F"><spring:message code="utenti.sessoF"/></option>
                        </select>
                    </li>
                    <li class="cittadinanza">
                        <label for="destinazioneCittadinanza">
                            <strong><spring:message code="utenti.cittadinanza"/>:</strong>
                        </label>
                        <input type="text" id="destinazioneCittadinanza" name="desCittadinanza" value="" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idCittadinanza" value="" />
                    </li>
                    <li class="nazionalita">
                        <label for="destinazioneNazionalita">
                            <strong><spring:message code="utenti.nazionalita"/>:</strong>
                        </label>
                        <input id="destinazioneNazionalita" type="text" name="desNazionalita" value="" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idNazionalita" value="" />
                    </li>
                    <li class="dataNascita">
                        <label for="destinazioneDataNascita">
                            <strong><spring:message code="utenti.dataNascita"/>:</strong>
                        </label>
                        <input type="text" id="destinazioneDataNascita" name="dataNascita" class="ui-widget ui-widget-content ui-corner-all dataPicker"/>
                    </li>
                    <li class="comuneNascita">
                        <label for="destinazioneComuneNascita">
                            <strong><spring:message code="utenti.comuneNascita"/>:</strong>
                        </label>
                        <input id="destinazioneComuneNascita" type="text" tabindex="-1" name="comuneNascita.descrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="comuneNascita.codCatastale" />
                        <input type="hidden" name="comuneNascita.idComune" />
                    </li>
                    <li class="comuneNascitaStato" nonsicopia="true">
                        <label for="destinazioneComuneNascitaStato">
                            <strong><spring:message code="utenti.stato"/>:</strong>
                        </label>
                        <input id="destinazioneComuneNascitaStato" type="text" readonly="readonly" name="comuneNascita.stato.descrizione" class="ui-widget ui-widget-content ui-corner-all" />
                        <input type="hidden" name="comuneNascita.stato.idStato"/>
                    </li>
                    <li class="comuneNascitaProvincia" nonsicopia="true">
                        <label for="destinazioneComuneNascitaProvincia">
                            <strong><spring:message code="utenti.provincia"/>:</strong>
                        </label>
                        <input id="destinazioneComuneNascitaProvincia" type="text" readonly="readonly"  name="comuneNascita.provincia.descrizione" class="ui-widget ui-widget-content ui-corner-all" nonsicopia="true"/>
                        <input type="hidden" name="comuneNascita.provincia.idProvincie"/>
                    </li>
                    <li class="localitaNascita">
                        <label for="destinazioneLocalitaNascita">
                            <strong><spring:message code="utenti.localitaNascita"/>:</strong>
                        </label>
                        <input id="destinazioneLocalitaNascita" type="text" name="localitaNascita" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="denominazione">
                        <label for="destinazioneDenominazione">
                            <strong><spring:message code="utenti.ragionesociale"/>:</strong>
                        </label>
                        <input id="destinazioneDenominazione" type="text" name="denominazione" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="desFormaGiuridica">
                        <label for="destinazioneFormaGiuridica">
                            <strong><spring:message code="utenti.formagiuridica"/>:</strong>
                        </label>
                        <input id="destinazioneFormaGiuridica" type="text" name="desFormaGiuridica" class="desFormaGiuridica ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idFormaGiuridica" class="idFormaGiuridica ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="provinciaCciaa">
                        <label for="destinazioneProvinciaCciaa">
                            <strong><spring:message code="utenti.idProvinciaCciaa"/>:</strong>
                        </label>
                        <input id="destinazioneProvinciaCciaa" type="text" name="provinciaCciaa.descrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="provinciaCciaa.idProvincie" class=""/>
                    </li>
                    <li class="flgAttesaIscrizioneRea">
                        <label for="destinazioneFlgAttesaIscrizioneRea">
                            <strong><spring:message code="utenti.flgAttesaIscrizioneRea"/>:</strong>
                        </label>
                        <select id="destinazioneFlgAttesaIscrizioneRea" class="flgAttesaIscrizioneRea ui-widget ui-widget-content ui-corner-all" name="flgAttesaIscrizioneRea">
                            <option class="S" value="S">Si</option>
                            <option class="N" value="N">No</option>
                        </select>
                    </li>
                    <li class="nIscrizioneRea">
                        <label for="destinazioneNIscrizioneRea">
                            <strong><spring:message code="utenti.nIscrizioneRea"/>:</strong>
                        </label>
                        <input id="destinazioneNIscrizioneRea" type="text" name="nIscrizioneRea" class="ui-widget ui-widget-content ui-corner-all" />
                    </li>
                    <li class="dataIscrizioneRea">
                        <label for="destinazioneDataIscrizioneRea">
                            <strong><spring:message code="utenti.dataIscrizioneRea"/>:</strong>
                        </label>
                        <input id="destinazioneDataIscrizioneRea" type="text" name="dataIscrizioneRea" class="ui-widget ui-widget-content ui-corner-all dataPicker" />
                    </li>
                    <li class="flgAttesaIscrizioneRi">
                        <label for="destinazioneFlgAttesaIscrizioneRi">
                            <strong><spring:message code="utenti.flgAttesaIscrizioneRi"/>:</strong>
                        </label>
                        <select id="destinazioneFlgAttesaIscrizioneRi" class="ui-widget ui-widget-content ui-corner-all flgAttesaIscrizioneRi" name="flgAttesaIscrizioneRi">
                            <option value="S">Si</option>
                            <option value="N">No</option>
                        </select>
                    </li>
                    <li class="nIscrizioneRi">
                        <label for="destinazioneNIscrizioneRi">
                            <strong><spring:message code="utenti.nIscrizioneRi"/>:</strong>
                        </label>
                        <input id="destinazioneNIscrizioneRi" type="text" name="nIscrizioneRi" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="dataIscrizioneRi">
                        <label for="destinazioneDataIscrizioneRi">
                            <strong><spring:message code="utenti.dataIscrizioneRi"/>:</strong>
                        </label>
                        <input id="destinazioneDataIscrizioneRi" type="text" name="dataIscrizioneRi" class="ui-widget ui-widget-content ui-corner-all dataPicker"/>
                    </li>
                    <li class="tipoCollegio">
                        <label for="destinazioneTipoCollegio">
                            <strong><spring:message code="utenti.descTipoCollegio"/>:</strong>
                        </label>
                        <input id="destinazioneTipoCollegio" type="text" name="desTipoCollegio" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idTipoCollegio" />
                    </li>
                    <li class="numeroIscrizione">
                        <label for="destinazioneAlboNum">
                            <strong><spring:message code="utenti.alboNumero"/>:</strong>
                        </label>
                        <input id="destinazioneAlboNum" type="text" name="numeroIscrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="alboProvincia">
                        <label for="destinazioneAlboProvincia">
                            <strong><spring:message code="utenti.provinciaAlbo"/>:</strong>
                        </label>
                        <input id="destinazioneAlboProvincia" type="text" name="provinciaIscrizione.descrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="provinciaIscrizione.idProvincie" />
                    </li>
                </ul>
                <ul class="recapito0"> 
                    <input type="hidden" name="recapiti[0].idRecapito" />
                    <li class="recapiti_idTipoIndirizzo_0">
                        <label>
                            <strong><spring:message code="utenti.descTipoIndirizzo"/>:</strong>
                        </label>
                        <select class="ui-widget ui-widget-content ui-corner-all recapiti.idTipoIndirizzo" name="recapiti[0].idTipoIndirizzo">
                            <c:forEach items="${tipoIndirizzoList}" var="tipoIndirizzo" begin="0">
                                <option class="${tipoIndirizzo.codTipoIndirizzo}" value="${tipoIndirizzo.idTipoIndirizzo}">${tipoIndirizzo.descTipoIndirizzo}</option>    
                            </c:forEach>
                        </select>
                    </li>
                    <li class="recapiti_indirizzo_0" >
                        <label>
                            <strong><spring:message code="utenti.indirizzo"/> :</strong>
                        </label>
                        <input type="text" class="recapito-indirizzo ui-widget ui-widget-content ui-corner-all" name="recapiti[0].indirizzo" />
                    </li>
                    <li class="recapiti_nCivico_0">
                        <label>
                            <strong><spring:message code="utenti.nCivico"/>:</strong>
                        </label>
                        <input type="text" class="recapito-nCivico ui-widget ui-widget-content ui-corner-all" name="recapiti[0].nCivico" value="" />
                    </li>
                    <li class="recapiti_cap_0">
                        <label>
                            <strong><spring:message code="utenti.cap"/>:</strong>
                        </label>
                        <input type="text" class="recapito-cap ui-widget ui-widget-content ui-corner-all" name="recapiti[0].cap" value=""/>
                    </li>
                    <li class="recapiti_descComune_0">
                        <label >
                            <strong><spring:message code="utenti.descComune"/>:</strong>
                        </label>
                        <input type="text" class="recapito-descComune ui-widget ui-widget-content ui-corner-all" name="recapiti[0].descComune" value="" />
                        <input type="hidden" class="recapito-idComune recapito.idComune" name="recapiti[0].idComune" value="" class="hidden"/>
                    </li>
                    <li class="recapiti_descProvincia_0" nonsicopia="true">
                        <label >
                            <strong><spring:message code="utenti.descProvincia"/>:</strong>
                        </label>
                        <input type="text" readonly="readonly" class="recapito-descProvincia ui-widget ui-widget-content ui-corner-all" name="recapiti[0].descProvincia" value="" nonsicopia="true"/>
                        <input type="hidden" class="recapito-idProvincia recapito.idProvincia" name="recapiti[0].idProvincia" value="" class="hidden" nonsicopia="true"/>
                    </li>

                    <li class="recapiti_descStato_0" nonsicopia="true">
                        <label >
                            <strong><spring:message code="utenti.descStato"/>:</strong>
                        </label>
                        <input type="text" readonly="readonly" class="recapito-descStato ui-widget ui-widget-content ui-corner-all" name="recapiti[0].descStato" value="" nonsicopia="true"/>
                        <input type="hidden" class="recapito-idStato recapito.idStato" name="recapiti[0].idStato" value="" class="hidden" nonsicopia="true"/>
                    </li>

                    <li class="recapiti_telefono_0">
                        <label >
                            <strong><spring:message code="utenti.telefono"/>:</strong>
                        </label>
                        <input type="text" class="recapito-telefono ui-widget ui-widget-content ui-corner-all" name="recapiti[0].telefono" value="" />
                    </li>

                    <li class="recapiti_cellulare_0">
                        <label >
                            <strong><spring:message code="utenti.cellulare"/>:</strong>
                        </label>
                        <input type="text" class="recapito-cellulare ui-widget ui-widget-content ui-corner-all" name="recapiti[0].cellulare" value="" />
                    </li>
                    <li class="recapiti_pec_0">
                        <label >
                            <strong><spring:message code="utenti.pec"/>:</strong>
                        </label>
                        <input type="text" class="recapito-pec ui-widget ui-widget-content ui-corner-all" name="recapiti[0].pec" value=""/>
                    </li>

                    <li class="recapiti_email_0">
                        <label >
                            <strong><spring:message code="utenti.email"/>:</strong>
                        </label>
                        <input type="text" class="recapito-email ui-widget ui-widget-content ui-corner-all" name="recapiti[0].email" value=""/>
                    </li>
                </ul>
            </form>
        </div>

        <div class="azioni ui-widget-content">
            <div class="ui-widget-header ui-helper-clearfix ui-corner-all" id="spostaSingola">
                <span class="ui-icon ui-icon-circle-arrow-w"></span>
            </div>
        </div>

        <div class="sorgente ui-corner-all ">
            <form id="sorgenteForm">
                <div class="label">Dati pervenuti</div>
                <input type="hidden" name="idAnagrafica"/>
                <input type="hidden" name="counter"/>
                <ul class="dettaglio">
                    <li class="idTipoPersona">
                        <label for="sorgenteIdTipoPersona">
                            <strong><spring:message code="utenti.tipoAnagrafica"/>:</strong>
                        </label>
                        <select id="sorgenteIdTipoPersona" name="idTipoPersona" class="ui-widget ui-widget-content ui-corner-all">
                            <option value="F">Persona fisica</option>
                            <option value="G">Persona giuridica</option>
                        </select>
                        <input type="hidden" name="idTipoPersonaOriginalValue" />
                    </li>
                    <li class="flgIndividuale">
                        <label for="sorgenteFlgIndividuale">
                            <strong><spring:message code="utenti.flgDittaIndividuale" />:</strong>
                        </label>
                        <select id="sorgenteFlgIndividuale" name="flgIndividuale" class="ui-widget ui-widget-content ui-corner-all">
                            <option value="N">No</option>
                            <option value="S">Si</option>
                        </select>
                    </li>      
                    <li class="idTipoRuolo">
                        <label for="sorgenteIdTipoRuolo">
                            <strong><spring:message code="utenti.tipoRuolo"/>:</strong>
                        </label>
                        <select id="sorgenteIdTipoRuolo" name="idTipoRuolo" class="ui-widget ui-widget-content ui-corner-all">
                            <option value=""></option>
                            <c:forEach items="${tipoRuoloList}" var="tipoRuolo" begin="0">
                                <option class="${tipoRuolo.codRuolo}" value="${tipoRuolo.idTipoRuolo}">${tipoRuolo.descrizione}</option>    
                            </c:forEach>
                        </select>
                    </li>
                    <li class="codiceFiscale" data-field="codiceFiscale">
                        <label for="sorgenteCodiceFicale">
                            <strong><spring:message code="utenti.codicefiscale"/>:</strong>
                        </label>
                        <div>
                            <input id="sorgenteCodiceFicale" type="text" name="codiceFiscale" class="ui-widget ui-widget-content ui-corner-all"/>
                            <c:if test="${attivaRegistroImprese}">
                                <button class="registroImprese" type="button">Reg. Imp.</button>
                            </c:if>
                            <button class="ricercaAnagrafe" type="button">Verifica</button>
                        </div>
                    </li>
                    <li class="partitaIva" data-field="partitaIva">
                        <label for="sorgentePartitaIva">
                            <strong><spring:message code="utenti.piva"/>:</strong>
                        </label>
                        <input id="sorgentePartitaIva" type="text" name="partitaIva" value="" class="ui-widget ui-widget-content ui-corner-all"/>
                        <span class="partitaIva"></span>
                    </li>
                    <li class="nome">
                        <label for="sorgenteNome">
                            <strong><spring:message code="utenti.nome"/>:</strong>
                        </label>
                        <input id="sorgenteNome" type="text" name="nome" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="cognome">
                        <label for="sorgenteCognome">
                            <strong><spring:message code="utenti.cognome"/>:</strong>
                        </label>
                        <input id="sorgenteCognome" type="text" name="cognome" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="sesso">
                        <label for="sorgenteSesso">
                            <strong><spring:message code="utenti.sesso"/>:</strong>
                        </label>
                        <select id="sorgenteSesso" name="sesso" class="ui-widget ui-widget-content ui-corner-all">
                            <option value="M"><spring:message code="utenti.sessoM"/></option>
                            <option value="F"><spring:message code="utenti.sessoF"/></option>
                        </select>
                    </li>
                    <li class="cittadinanza">
                        <label for="sorgenteCittadinanza">
                            <strong><spring:message code="utenti.cittadinanza"/>:</strong>
                        </label>
                        <input id="sorgenteCittadinanza" type="text" name="desCittadinanza" value="" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idCittadinanza" value="" />
                    </li>
                    <li class="nazionalita">
                        <label for="sorgenteNazionalita">
                            <strong><spring:message code="utenti.nazionalita"/>:</strong>
                        </label>
                        <input id="sorgenteNazionalita" type="text" name="desNazionalita" value="" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idNazionalita" value="" />
                    </li>
                    <li class="dataNascita">
                        <label for="sorgenteDataNascita">
                            <strong><spring:message code="utenti.dataNascita"/>:</strong>
                        </label>
                        <input id="sorgenteDataNascita" type="text" name="dataNascita" class="ui-widget ui-widget-content ui-corner-all dataPicker"/>
                    </li>
                    <li class="comuneNascita" data-type="address-comune">
                        <label for="sorgenteComuneNascita">
                            <strong><spring:message code="utenti.comuneNascita"/>:</strong>
                        </label>
                        <input id="sorgenteComuneNascita" type="text" tabindex="-1" name="comuneNascita.descrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="comuneNascita.codCatastale" />
                        <input type="hidden" name="comuneNascita.idComune" />
                    </li>
                    <li class="comuneNascitaStato" nonsicopia="true" data-type="address-stato">
                        <label for="sorgenteStatoNascita">
                            <strong><spring:message code="utenti.stato"/>:</strong>
                        </label>
                        <input id="sorgenteStatoNascita" type="text" readonly="readonly" name="comuneNascita.stato.descrizione" class="ui-widget ui-widget-content ui-corner-all" />
                        <input type="hidden" name="comuneNascita.stato.idStato"/>
                    </li>
                    <li class="comuneNascitaProvincia" nonsicopia="true" data-type="address-provincia">
                        <label for="sorgenteProvinciaNascita">
                            <strong><spring:message code="utenti.provincia"/>:</strong>
                        </label>
                        <input id="sorgenteProvinciaNascita" type="text" readonly="readonly"  name="comuneNascita.provincia.descrizione" class="ui-widget ui-widget-content ui-corner-all" nonsicopia="true"/>
                        <input type="hidden" name="comuneNascita.provincia.idProvincie"/>
                    </li>
                    <li class="localitaNascita">
                        <label for="sorgenteLocalitaNascita">
                            <strong><spring:message code="utenti.localitaNascita"/>:</strong>
                        </label>
                        <input id="sorgenteLocalitaNascita" type="text" name="localitaNascita" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="denominazione">
                        <label for="sorgenteDenominazione">
                            <strong><spring:message code="utenti.ragionesociale"/>:</strong>
                        </label>
                        <input id="sorgenteDenominazione" type="text" name="denominazione" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="desFormaGiuridica">
                        <label for="sorgenteFormaGiuridica">
                            <strong><spring:message code="utenti.formagiuridica"/>:</strong>
                        </label>
                        <input id="sorgenteFormaGiuridica" type="text" name="desFormaGiuridica" class="desFormaGiuridica ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idFormaGiuridica" class="idFormaGiuridica ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="provinciaCciaa">
                        <label for="sorgenteProvinciaCciaa">
                            <strong><spring:message code="utenti.idProvinciaCciaa"/>:</strong>
                        </label>
                        <input id="sorgenteProvinciaCciaa" type="text" name="provinciaCciaa.descrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="provinciaCciaa.idProvincie" class=""/>
                    </li>
                    <li class="flgAttesaIscrizioneRea">
                        <label for="sorgenteFlgAttesaIscrizioneRea">
                            <strong><spring:message code="utenti.flgAttesaIscrizioneRea"/>:</strong>
                        </label>
                        <select id="sorgenteFlgAttesaIscrizioneRea" class="flgAttesaIscrizioneRea ui-widget ui-widget-content ui-corner-all" name="flgAttesaIscrizioneRea">
                            <option class="S" value="S">Si</option>
                            <option class="N" value="N">No</option>
                        </select>
                    </li>
                    <li class="nIscrizioneRea">
                        <label for="sorgenteNIscrizioneRea">
                            <strong><spring:message code="utenti.nIscrizioneRea"/>:</strong>
                        </label>
                        <input id="sorgenteNIscrizioneRea" type="text" name="nIscrizioneRea" class="ui-widget ui-widget-content ui-corner-all" />
                    </li>
                    <li class="dataIscrizioneRea">
                        <label for="sorgenteDataIscrizioneRea">
                            <strong><spring:message code="utenti.dataIscrizioneRea"/>:</strong>
                        </label>
                        <input id="sorgenteDataIscrizioneRea" type="text" name="dataIscrizioneRea" class="ui-widget ui-widget-content ui-corner-all dataPicker" />
                    </li>
                    <li class="flgAttesaIscrizioneRi">
                        <label for="sorgenteFlgAttesaIscrizioneRi">
                            <strong><spring:message code="utenti.flgAttesaIscrizioneRi"/>:</strong>
                        </label>
                        <select id="sorgenteFlgAttesaIscrizioneRi" class="ui-widget ui-widget-content ui-corner-all flgAttesaIscrizioneRi" name="flgAttesaIscrizioneRi">
                            <option value="S">Si</option>
                            <option value="N">No</option>
                        </select>
                    </li>
                    <li class="nIscrizioneRi">
                        <label for="sorgenteNIscrizioneRi">
                            <strong><spring:message code="utenti.nIscrizioneRi"/>:</strong>
                        </label>
                        <input id="sorgenteNIscrizioneRi" type="text" name="nIscrizioneRi" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="dataIscrizioneRi">
                        <label for="sorgenteDataIscrizioneRi">
                            <strong><spring:message code="utenti.dataIscrizioneRi"/>:</strong>
                        </label>
                        <input id="sorgenteDataIscrizioneRi" type="text" name="dataIscrizioneRi" class="ui-widget ui-widget-content ui-corner-all dataPicker"/>
                    </li>
                    <li class="tipoCollegio">
                        <label for="sorgenteTipoCollegio">
                            <strong><spring:message code="utenti.descTipoCollegio"/>:</strong>
                        </label>
                        <input id="sorgenteTipoCollegio" type="text" name="desTipoCollegio" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="idTipoCollegio" />
                    </li>
                    <li class="numeroIscrizione">
                        <label for="sorgenteAlboNum">
                            <strong><spring:message code="utenti.alboNumero"/>:</strong>
                        </label>
                        <input id="sorgenteAlboNum" type="text" name="numeroIscrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                    </li>
                    <li class="alboProvincia">
                        <label for="sorgenteAlboProvincia">
                            <strong><spring:message code="utenti.provinciaAlbo"/>:</strong>
                        </label>
                        <input id="sorgenteAlboProvincia" type="text" name="provinciaIscrizione.descrizione" class="ui-widget ui-widget-content ui-corner-all"/>
                        <input type="hidden" name="provinciaIscrizione.idProvincie" />
                    </li>
                </ul>
                <ul class="recapito0"> 
                    <input type="hidden" name="recapiti[0].counter" />
                    <input type="hidden" name="recapiti[0].idRecapito" />
                    <li class="recapiti_idTipoIndirizzo_0">
                        <label>
                            <strong><spring:message code="utenti.descTipoIndirizzo"/>:</strong>
                        </label>
                        <select class="ui-widget ui-widget-content ui-corner-all recapiti.idTipoIndirizzo"  name="recapiti[0].idTipoIndirizzo">
                            <c:forEach items="${tipoIndirizzoList}" var="tipoIndirizzo" begin="0">
                                <option class="${tipoIndirizzo.codTipoIndirizzo}" value="${tipoIndirizzo.idTipoIndirizzo}">${tipoIndirizzo.descTipoIndirizzo}</option>    
                            </c:forEach>
                        </select>
                    </li>
                    <li class="recapiti_indirizzo_0" >
                        <label>
                            <strong><spring:message code="utenti.indirizzo"/> :</strong>
                        </label>
                        <input type="text" class="recapito-indirizzo ui-widget ui-widget-content ui-corner-all" name="recapiti[0].indirizzo" />
                    </li>
                    <li class="recapiti_nCivico_0">
                        <label>
                            <strong><spring:message code="utenti.nCivico"/>:</strong>
                        </label>
                        <input type="text" class="recapito-nCivico ui-widget ui-widget-content ui-corner-all" name="recapiti[0].nCivico" value="" />
                    </li>
                    <li class="recapiti_cap_0">
                        <label>
                            <strong><spring:message code="utenti.cap"/>:</strong>
                        </label>
                        <input type="text" class="recapito-cap ui-widget ui-widget-content ui-corner-all" name="recapiti[0].cap" value=""/>
                    </li>
                    <li class="recapiti_descComune_0">
                        <label >
                            <strong><spring:message code="utenti.descComune"/>:</strong>
                        </label>
                        <input type="text" class="recapito-descComune ui-widget ui-widget-content ui-corner-all" name="recapiti[0].descComune" value="" />
                        <input type="hidden" class="recapito-idComune recapito.idComune" name="recapiti[0].idComune" value="" class="hidden"/>
                    </li>
                    <li class="recapiti_descProvincia_0" nonsicopia="true">
                        <label >
                            <strong><spring:message code="utenti.descProvincia"/>:</strong>
                        </label>
                        <input type="text" readonly="readonly" class="recapito-descProvincia ui-widget ui-widget-content ui-corner-all" name="recapiti[0].descProvincia" value="" nonsicopia="true"/>
                        <input type="hidden" class="recapito-idProvincia recapito.idProvincia" name="recapiti[0].idProvincia" value="" class="hidden" nonsicopia="true"/>
                    </li>

                    <li class="recapiti_descStato_0" nonsicopia="true">
                        <label >
                            <strong><spring:message code="utenti.descStato"/>:</strong>
                        </label>
                        <input type="text" readonly="readonly" class="recapito-descStato ui-widget ui-widget-content ui-corner-all" name="recapiti[0].descStato" value="" nonsicopia="true"/>
                        <input type="hidden" class="recapito-idStato recapito.idStato" name="recapiti[0].idStato" value="" class="hidden" nonsicopia="true"/>
                    </li>

                    <li class="recapiti_telefono_0">
                        <label >
                            <strong><spring:message code="utenti.telefono"/>:</strong>
                        </label>
                        <input type="text" class="recapito-telefono ui-widget ui-widget-content ui-corner-all" name="recapiti[0].telefono" value="" />
                    </li>

                    <li class="recapiti_cellulare_0">
                        <label >
                            <strong><spring:message code="utenti.cellulare"/>:</strong>
                        </label>
                        <input type="text" class="recapito-cellulare ui-widget ui-widget-content ui-corner-all" name="recapiti[0].cellulare" value="" />
                    </li>
                    <li class="recapiti_pec_0">
                        <label >
                            <strong><spring:message code="utenti.pec"/>:</strong>
                        </label>
                        <input type="text" class="recapito-pec ui-widget ui-widget-content ui-corner-all" name="recapiti[0].pec" value=""/>
                    </li>

                    <li class="recapiti_email_0">
                        <label >
                            <strong><spring:message code="utenti.email"/>:</strong>
                        </label>
                        <input type="text" class="recapito-email ui-widget ui-widget-content ui-corner-all" name="recapiti[0].email" value=""/>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>