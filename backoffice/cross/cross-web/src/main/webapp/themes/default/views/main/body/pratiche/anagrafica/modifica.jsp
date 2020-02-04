<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String url = path + "/gestione/anagrafiche/aggiorna.htm";
%>
<style>
    .ui-button {
        font-size: 11px;
        margin-left: 2px;
        padding: 2px;
    }
</style>

<script type="text/javascript">
    var path = '${path}';
</script>

<script  type="text/javascript" src="<c:url value="/javascript/cross/rubrica.modifica.js"/>"></script>
<tiles:insertAttribute name="body_error" />
<tiles:insertAttribute name="ricerca_ri" />
<c:if test="${!empty errore}">
    <div class="uniForm failedSubmit">
        <h3><spring:message code="error.default.message"/></h3>
        <ol>
            <li>${errore}</li>
        </ol>
    </div>
</c:if>
<form:form action="<%=url%>" class="uniForm inlineLabels comunicazione" method="post" commandName="anagrafica">
    <div class="inlineLabels">
        <div class="ctrlHolder">
            <div id="anagraficaContent">
                <form:hidden path="tipoAnagrafica" id="tipoAnagrafica" />
                <form:hidden path="idAnagrafica" id="idAnagrafica" />
                <form:hidden path="idTipoPersona" id="idTipoPersona" />
                <form:hidden path="flgIndividuale" id="flgIndividuale" />
                <form:hidden path="varianteAnagrafica" id="varianteAnagrafica"/>
                <fieldset class="fieldsetComunicazione">
                    <legend><spring:message code="anagrafica.personafisica.datigenerali"/></legend>
                    <div>
                        <div>
                            <c:if test="${anagrafica.tipoAnagrafica == 'F'}">
                                <div class="ctrlHolder">
                                    <label for="nome" class="required"><spring:message code="anagrafica.nome"/></label>
                                    <form:input path="nome" id="nome" cssClass="textInput required" maxlength="255" />
                                    <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label for="cognome" class="required"><spring:message code="anagrafica.cognome"/></label>
                                    <form:input path="cognome" id="cognome" cssClass="textInput required" maxlength="255" />
                                    <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                </div>
                                <!--</div>-->
                            </c:if>
                            <c:if test="${(anagrafica.tipoAnagrafica == 'G') || (anagrafica.varianteAnagrafica == 'I')}">
                                <div class="ctrlHolder" >
                                    <label for="denominazione" class="required"><spring:message code="anagrafica.denominazione"/></label>
                                    <form:input path="denominazione" id="denominazione" cssClass="textInput required" maxlength="255" />
                                    <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                </div>
                            </c:if>

                            <c:if test="${anagrafica.tipoAnagrafica == 'G'}">
                                <div class="ctrlHolder" >
                                    <label for="desFormaGiuridica" class="required"><spring:message code="anagrafica.formagiuridica"/></label>
                                    <form:hidden path="idFormaGiuridica" id="idFormaGiuridica" />
                                    <form:input path="desFormaGiuridica" id="desFormaGiuridica" cssClass="textInput required" maxlength="255" />
                                </div>
                            </c:if>
                            <div class="ctrlHolder">
                                <label for="codiceFiscale" class="required"><spring:message code="anagrafica.codicefiscale"/></label>
                                <form:input path="codiceFiscale" id="codiceFiscale" cssClass="textInput required" maxlength="255" />
                                <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                            </div>
                            <c:if test="${(anagrafica.tipoAnagrafica == 'G') || (anagrafica.varianteAnagrafica == 'I')}">
                                <div class="ctrlHolder" >
                                    <label for="partitaIva" class="required"><spring:message code="anagrafica.partitaiva"/></label>
                                    <form:input path="partitaIva" id="partitaIva" cssClass="textInput required" maxlength="255" />
                                    <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                </div>
                            </c:if>
                            <c:if test="${anagrafica.tipoAnagrafica == 'F'}">
                                <div class="ctrlHolder" >
                                    <label for="sesso" class="required"><spring:message code="anagrafica.sesso"/></label>
                                    <form:select id="sesso" path="sesso">
                                        <form:option value="F"><spring:message code="anagrafica.sesso.femmina"/></form:option>
                                        <form:option value="M"><spring:message code="anagrafica.sesso.maschio"/></form:option>
                                    </form:select>
                                </div>
                                <div class="ctrlHolder" >
                                    <label for="dataNascita" class="required"><spring:message code="anagrafica.datanascita"/></label>
                                    <%--
                                    <fmt:formatDate value="dataNascita"  
                                                    type="date" 
                                                    pattern="dd/MM/yyyy"
                                                    var="dataNascita" />
                                    --%>
                                    <form:input path="dataNascita" id="dataNascita" cssClass="textInput required date"/>
                                </div>
                                <div class="ctrlHolder" >
                                    <label for="desCittadinanza" class="required"><spring:message code="anagrafica.cittadinanza"/></label>
                                    <form:hidden path="idCittadinanza" id="idCittadinanza"/>
                                    <form:input path="desCittadinanza" id="desCittadinanza" cssClass="textInput required" maxlength="255" />
                                    <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                </div>
                                <div class="ctrlHolder" >
                                    <label for="desNazionalita" class="required"><spring:message code="anagrafica.nazionalita"/></label>
                                    <form:hidden path="idNazionalita" id="idNazionalita"/>
                                    <form:input path="desNazionalita" id="desNazionalita" cssClass="textInput required" maxlength="255" />
                                </div>
                                <div class="ctrlHolder">
                                    <label for="desComuneNascita" class="required"><spring:message code="anagrafica.comunenascita"/></label>
                                    <form:hidden path="comuneNascita.idComune" id="nComuneNascita"/>
                                    <form:input path="comuneNascita.descrizione" id="desComuneNascita" maxlength="255" cssClass="textInput required" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label for="localitaNascita" class="required"><spring:message code="anagrafica.localitanascita"/></label>
                                    <form:input path="localitaNascita" id="localitaNascita" maxlength="255" cssClass="textInput required" />
                                </div>
                                <c:if test="${(anagrafica.varianteAnagrafica== null) || (anagrafica.varianteAnagrafica== '') || (anagrafica.varianteAnagrafica== 'F')}">
                                    <div class="ctrlHolder" >
                                        <label for="cambiaAnagrafica" class="required">Imposta tipo anagrafica a Ditta Individuale</label>
                                        <button type="button" id="cambiaAnagrafica" class="ui-button ui-widget ui-state-default ui-corner-all">Cambia anagrafica</button>
                                        <input type="hidden" id="nuovaVariante" name="nuovaVariante" value="I"/>
                                    </div>
                                </c:if>
                            </c:if>
                            <c:if test="${(anagrafica.tipoAnagrafica == 'G') || (anagrafica.varianteAnagrafica == 'I')}">
                                <div class="ctrlHolder" >
                                    <label for="desProvinciaIscrizioneRi" class="required"><spring:message code="anagrafica.provinciacciaa"/></label>
                                    <form:hidden path="provinciaCciaa.idProvincie" id="nProvinciaIscrizioneRi"/>
                                    <form:input path="provinciaCciaa.descrizione" id="desProvinciaIscrizioneRi" maxlength="255" cssClass="textInput required" />
                                </div>
                                <div class="ctrlHolder">
                                    <label for="flgAttesaIscrizioneRi" class="required"><spring:message code="anagrafica.attesascrizioneri"/></label>
                                    <form:checkbox path="flgAttesaIscrizioneRi" id="flgAttesaIscrizioneRi" value="S" cssClass="choice required"/>
                                </div>
                                <div id="iscrizioneRegistroImpreseDiv" class="hidden">
                                    <div class="ctrlHolder">
                                        <label for="nIscrizioneRi" class="required"><spring:message code="anagrafica.numeroiscrizioneri"/></label>
                                        <form:input path="nIscrizioneRi" id="nIscrizioneRi" maxlength="255" cssClass="textInput required" />
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="dataIscrizioneRi" class="required"><spring:message code="anagrafica.dataiscrizioneri"/></label>
                                        <%--
                                        <fmt:formatDate value="dataIscrizioneRi"  
                                                        type="date" 
                                                        pattern="dd/MM/yyyy"
                                                        var="dataIscrizioneRi" />
                                        --%>
                                        <form:input path="dataIscrizioneRi" id="dataIscrizioneRi" maxlength="255" cssClass="textInput required date" />
                                    </div>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="flgAttesaIscrizioneRea" class="required"><spring:message code="anagrafica.attesascrizionerea"/></label>
                                    <form:checkbox path="flgAttesaIscrizioneRea" id="flgAttesaIscrizioneRea" value="S" cssClass="choice required"/>
                                </div>
                                <div id="iscrizioneReaDiv" class="hidden">
                                    <div class="ctrlHolder">
                                        <label for="nIscrizioneReaG" class="required">
                                            <spring:message code="anagrafica.numeroiscrizionerea" />
                                        </label>
                                        <form:input path="nIscrizioneRea" id="nIscrizioneRea" maxlength="255" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="dataIscrizioneReaG" class="required"><spring:message code="anagrafica.dataiscrizionerea"/></label>
                                        <%--
                                        <fmt:formatDate value="dataIscrizioneRea"  
                                                        type="date" 
                                                        pattern="dd/MM/yyyy"
                                                        var="dataIscrizioneRea" />
                                        --%>
                                        <form:input path="dataIscrizioneRea" id="dataIscrizioneRea" maxlength="255" cssClass="textInput required date" />
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>
                <c:if test="${anagrafica.tipoAnagrafica == 'F' && anagrafica.varianteAnagrafica != 'I'}">
                    <fieldset class="fieldsetComunicazione">
                        <legend><spring:message code="anagrafica.professionista"/></legend>
                        <div>
                            <div class="ctrlHolder">
                                <label for="desTipoCollegio" class="required"><spring:message code="anagrafica.tipocollegio"/></label>
                                <form:hidden path="codTipoCollegio" id="codTipoCollegio"/>
                                <form:input path="desTipoCollegio" id="desTipoCollegio" maxlength="255" cssClass="textInput required" />
                            </div>

                            <div class="ctrlHolder">
                                <label for="numeroIscrizione" class="required"><spring:message code="anagrafica.iscrizione"/></label>
                                <form:input path="numeroIscrizione" id="numeroIscrizione" maxlength="255" cssClass="textInput required" />
                            </div>

                            <div class="ctrlHolder">
                                <label for="dataIscrizione" class="required"><spring:message code="anagrafica.dataiscrizione"/></label>
                                <%--
                                <fmt:formatDate value="dataIscrizione"  
                                                type="date" 
                                                pattern="dd/MM/yyyy"
                                                var="dataIscrizione" />
                                --%>
                                <form:input path="dataIscrizione" id="dataIscrizione" maxlength="255" cssClass="textInput required date" />
                            </div>

                            <div class="ctrlHolder">
                                <label for="desProvinciaIscrizione" class="required"><spring:message code="anagrafica.provinciaiscrizione"/></label>
                                <form:hidden path="provinciaIscrizione.idProvincie" id="nProvinciaIscrizione"/>
                                <form:input path="provinciaIscrizione.descrizione" id="desProvinciaIscrizione" maxlength="255" cssClass="textInput required" />
                            </div>
                        </div>
                    </fieldset>
                </c:if>
                <%-- Recapiti --%>
                <c:if test="${empty anagrafica.recapiti}">
                    <fieldset style="display:none" class="fieldsetComunicazione" id="recapitiSection">
                        <div class="dati">
                            <form:hidden path="recapiti[${count}].idRecapito" cssClass="idRecapito"/>
                            <form:hidden path="recapiti[${count}].idTipoIndirizzo" cssClass="idTipoIndirizzo"/>
                            <form:hidden path="recapiti[${count}].descTipoIndirizzo" cssClass="descTipoIndirizzo"/>s
                            <div class="ctrlHolder" id="recapitoComuneDiv">
                                <label for="desComuneRecapito" class="required"><spring:message code="anagrafica.recapito.comune"/></label>
                                <form:hidden path="recapiti[0].idComune" id="nComuneRecapito" cssClass="nComuneRecapito"/>
                                <form:input path="recapiti[0].descComune" id="desComuneRecapito" maxlength="255" cssClass="textInput required desComuneRecapito" />
                            </div>
                            <div class="ctrlHolder" id="recapitoLocalitaDiv">
                                <label for="localitaRecapito" class="required"><spring:message code="anagrafica.recapito.localita"/></label>
                                <form:input path="recapiti[0].localita" id="localitaRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoIndirizzoDiv">
                                <label for="indirizzoRecapito" class="required"><spring:message code="anagrafica.recapito.indirizzo"/></label>
                                <form:input path="recapiti[0].indirizzo" id="indirizzoRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoAltreInfoIndirizzoDiv">
                                <label for="altreInfoIndirizzo" class="required"><spring:message code="anagrafica.recapito.altreInfoIndirizzo"/></label>
                                <form:input path="recapiti[0].altreInfoIndirizzo" id="altreInfoIndirizzo" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoCivicoDiv">
                                <label for="civicoRecapito" class="required"><spring:message code="anagrafica.recapito.civico"/></label>
                                <form:input path="recapiti[0].nCivico" id="civicoRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoCAPDiv">
                                <label for="capRecapito" class="required"><spring:message code="anagrafica.recapito.cap"/></label>
                                <form:input path="recapiti[0].cap" id="capRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoPostaleDiv">
                                <label for="casellaPostaleRecapito" class="required"><spring:message code="anagrafica.recapito.casellapostale"/></label>
                                <form:input path="recapiti[0].casellaPostale" id="casellaPostaleRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoTelefonoDiv">
                                <label for="telefonoRecapito" class="required"><spring:message code="anagrafica.recapito.telefono"/></label>
                                <form:input path="recapiti[0].telefono" id="telefonoRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoCellulareDiv">
                                <label for="cellulareRecapito" class="required"><spring:message code="anagrafica.recapito.cellulare"/></label>
                                <form:input path="recapiti[0].cellulare" id="cellulareRecapito" maxlength="30" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoFAXDiv">
                                <label for="faxRecapito" class="required"><spring:message code="anagrafica.recapito.fax"/></label>
                                <form:input path="recapiti[0].fax" id="faxRecapito" maxlength="30" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoEmailDiv">
                                <label for="emailRecapito" class="required"><spring:message code="anagrafica.recapito.email"/></label>
                                <form:input path="recapiti[0].email" id="emailRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                            <div class="ctrlHolder" id="recapitoPECDiv">
                                <label for="pecRecapito" class="required"><spring:message code="anagrafica.recapito.pec"/></label>
                                <form:input path="recapiti[0].pec" id="pecRecapito" maxlength="255" cssClass="textInput required" />
                            </div>
                        </div>
                    </fieldset>
                </c:if>
                <c:if test="${!empty anagrafica.recapiti}">
                    <c:set var="count" value="0" scope="page" /> 
                    <c:forEach items="${anagrafica.recapiti}"  begin="0" var="recapito" varStatus="status">
                        <c:if test="${recapito.descTipoIndirizzo != null &&recapito.descTipoIndirizzo !='NOTIFICA' && recapito.descTipoIndirizzo!=''}">
                            <fieldset class="fieldsetComunicazione" id="recapitiSection">
                                <legend id="titoloRecapiti">${recapito.descTipoIndirizzo}
                                    <c:if test="${recapito.descTipoIndirizzo!='RESIDENZA' && recapito.descTipoIndirizzo!='SEDE'}">
                                        <input  type="button" name="${recapito.idTipoIndirizzo}" value="Elimina" class="eliminaRecapito ui-button ui-widget ui-state-default ui-corner-all" style="margin-left: 2px;padding:2px;"/>
                                    </c:if>
                                </legend>
                                <div class="dati">
                                    <form:hidden path="recapiti[${count}].idRecapito" cssClass="idRecapito"/>
                                    <form:hidden path="recapiti[${count}].idTipoIndirizzo" cssClass="idTipoIndirizzo"/>
                                    <form:hidden path="recapiti[${count}].descTipoIndirizzo" cssClass="descTipoIndirizzo"/>
                                    <div class="ctrlHolder"  id="recapitoComuneDiv">
                                        <label for="desComuneRecapito" class="required"><spring:message code="anagrafica.recapito.comune"/></label>
                                        <form:hidden path="recapiti[${count}].idComune" cssClass="nComuneRecapito"/>
                                        <form:input path="recapiti[${count}].descComune" id="desComuneRecapito" maxlength="255" cssClass="textInput required desComuneRecapito" />
                                    </div>
                                    <div class="ctrlHolder" id="recapitoLocalitaDiv">
                                        <label for="localitaRecapito" class="required"><spring:message code="anagrafica.recapito.localita"/></label>
                                        <form:input path="recapiti[${count}].localita" id="localitaRecapito" maxlength="255" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder" id="recapitoIndirizzoDiv">
                                        <label for="indirizzoRecapito" class="required"><spring:message code="anagrafica.recapito.indirizzo"/></label>
                                        <form:input path="recapiti[${count}].indirizzo" id="indirizzoRecapito" maxlength="255" cssClass="textInput required" />
                                    </div>

                                    <div class="ctrlHolder" id="recapitoCivicoDiv">
                                        <label for="civicoRecapito" class="required"><spring:message code="anagrafica.recapito.civico"/></label>
                                        <form:input path="recapiti[${count}].nCivico" id="civicoRecapito" maxlength="45" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoCAPDiv">
                                        <label for="capRecapito" class="required"><spring:message code="anagrafica.recapito.cap"/></label>
                                        <form:input path="recapiti[${count}].cap" id="capRecapito" maxlength="20" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder" id="recapitoAltreInfoIndirizzoDiv">
                                        <label for="indirizzoRecapito" class="required"><spring:message code="anagrafica.recapito.altreInfoIndirizzo"/></label>
                                        <form:input path="recapiti[${count}].altreInfoIndirizzo" id="altreInfoIndirizzo" maxlength="255" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoPostaleDiv">
                                        <label for="casellaPostaleRecapito" class="required"><spring:message code="anagrafica.recapito.casellapostale"/></label>
                                        <form:input path="recapiti[${count}].casellaPostale" id="casellaPostaleRecapito" maxlength="255" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoTelefonoDiv">
                                        <label for="telefonoRecapito" class="required"><spring:message code="anagrafica.recapito.telefono"/></label>
                                        <form:input path="recapiti[${count}].telefono" id="telefonoRecapito" maxlength="255" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoCellulareDiv">
                                        <label for="cellulareRecapito" class="required"><spring:message code="anagrafica.recapito.cellulare"/></label>
                                        <form:input path="recapiti[${count}].cellulare" id="cellulareRecapito" maxlength="30" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoFAXDiv">
                                        <label for="faxRecapito" class="required"><spring:message code="anagrafica.recapito.fax"/></label>
                                        <form:input path="recapiti[${count}].fax" id="faxRecapito" maxlength="30" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoEmailDiv">
                                        <label for="emailRecapito" class="required"><spring:message code="anagrafica.recapito.email"/></label>
                                        <form:input path="recapiti[${count}].email" id="emailRecapito" maxlength="255" cssClass="textInput required" />
                                    </div>
                                    <div class="ctrlHolder"  id="recapitoPECDiv">
                                        <label for="pecRecapito" class="required"><spring:message code="anagrafica.recapito.pec"/></label>
                                        <form:input path="recapiti[${count}].pec" id="pecRecapito" maxlength="255" cssClass="textInput required" />
                                    </div>
                                </div>
                            </fieldset>
                            <c:set var="count" value="${count + 1}" scope="page"/>
                        </c:if>
                    </c:forEach>
                </c:if> 
                <div id="aggiungiRecapito">                    
                    <c:forEach begin="0" items="${ListaTipoIndirizzo}" var="ruolo" step="1">
                        <button type="button" id="${ruolo.key}"  value="${ruolo.value}" class="aggiungiRecapito ui-button ui-widget ui-state-default ui-corner-all">Aggiungi Recapito per ${ruolo.value}</button>
                    </c:forEach>
                </div>
                <div class="buttonHolder_box">
                    <button value="<spring:message code="anagrafica.button.salva"/>" class="primaryAction" name="submit" type="submit"><spring:message code="anagrafica.button.salva"/></button>
                    <a href="<%=path%>/gestione/anagrafiche/list.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
                </div>
            </div>
        </div>
    </div>
</form:form>
