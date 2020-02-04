<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String url = path + "/gestione/anagrafiche/salva.htm";
%>

<script type="text/javascript">
    var path = '${path}';
    var legendPersonaFisica = "<spring:message code="anagrafica.personafisica"/>";
    var legendProfessionista = "<spring:message code="anagrafica.professionista"/>";
    var legendRecapitiPF = "<spring:message code="anagrafica.recapito.residenza.title"/>";
    var legendRecapitiPG = "<spring:message code="anagrafica.recapito.sede.title"/>";
    var mandatoryField = "<spring:message code="ente.campo.obbligatorio"/>";
</script>

<script  type="text/javascript" src="<c:url value="/javascript/cross/rubrica.nuovo.js"/>"></script>
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

<form:form action="<%=url%>" cssClass="uniForm inlineLabels comunicazione" method="post" commandName="anagrafica"> 
    <div id="anagraficaContent">
        <div class="inlineLabels">
            <fieldset class="fieldsetComunicazione">
                <legend><spring:message code="anagrafica.tipologia"/></legend>
                <div>
                    <div class="ctrlHolder">
                        <label for="tipoAnagrafica" class="required"><spring:message code="anagrafica.tipologia"/></label>
                        <form:select id="tipoAnagrafica" path="idTipoPersona">
                            <form:option value=""><spring:message code="anagrafica.tipologia.default"/></form:option>
                            <form:option value="F"><spring:message code="anagrafica.tipologia.fisica"/></form:option>
                            <form:option value="G"><spring:message code="anagrafica.tipologia.giuridica"/></form:option>
                        </form:select>
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder nome">
                        <label for="nome" class="required"><spring:message code="anagrafica.nome"/></label>
                        <form:input path="nome" id="nome" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder cognome">
                        <label for="cognome" class="required"><spring:message code="anagrafica.cognome"/></label>
                        <form:input path="cognome" id="cognome" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder denominazione">
                        <label for="denominazione" class="required"><spring:message code="anagrafica.denominazione"/></label>
                        <form:input path="denominazione" id="denominazione" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder desFormaGiuridica">
                        <label for="desFormaGiuridica" class="required"><spring:message code="anagrafica.formagiuridica"/></label>
                        <form:hidden path="idFormaGiuridica" id="idFormaGiuridica"/>
                        <form:input path="desFormaGiuridica" id="desFormaGiuridica" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder codiceFiscale">
                        <label for="codiceFiscale" class="required"><spring:message code="anagrafica.codicefiscale"/></label>
                        <form:input path="codiceFiscale" id="codiceFiscale" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder partitaIva">
                        <label for="partitaIva" class="required"><spring:message code="anagrafica.partitaiva"/></label>
                        <form:input path="partitaIva" id="partitaIva" maxlength="255" cssClass="textInput required" />
                        <p class="formHint partitaIvaMandatoryField hidden"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder sesso">
                        <label for="sesso" class="required"><spring:message code="anagrafica.sesso"/></label>
                        <form:select id="sesso" path="sesso">
                            <form:option value="F"><spring:message code="anagrafica.sesso.femmina"/></form:option>
                            <form:option value="M"><spring:message code="anagrafica.sesso.maschio"/></form:option>
                        </form:select>
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder dataNascita">
                        <label for="dataNascita" class="required"><spring:message code="anagrafica.datanascita"/></label>
                        <form:input path="dataNascita" id="dataNascita" maxlength="255" cssClass="textInput required date" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder cittadinanza">
                        <label for="desCittadinanza" class="required"><spring:message code="anagrafica.cittadinanza"/></label>
                        <form:hidden path="idCittadinanza" id="idCittadinanza"/>
                        <form:input path="desCittadinanza" id="desCittadinanza" maxlength="255" cssClass="textInput required" />
                    </div>

                    <div class="ctrlHolder nazionalita">
                        <label for="desNazionalita" class="required"><spring:message code="anagrafica.nazionalita"/></label>
                        <form:hidden path="idNazionalita" id="idNazionalita"/>
                        <form:input path="desNazionalita" id="desNazionalita" maxlength="255" cssClass="textInput required" />
                    </div>

                    <div class="ctrlHolder comuneNascita">
                        <label for="desComuneNascita" class="required"><spring:message code="anagrafica.comunenascita"/></label>
                        <form:hidden path="comuneNascita.idComune" id="nComuneNascita"/>
                        <form:input path="comuneNascita.descrizione" id="desComuneNascita" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder localitaNascita">
                        <label for="localitaNascita" class="required"><spring:message code="anagrafica.localitanascita"/></label>
                        <form:input path="localitaNascita" id="localitaNascita" maxlength="255" cssClass="textInput required" />
                    </div>

                    <div class="ctrlHolder impostaDittaIndividuale">
                        <label for="dittaIndividuale" class="required"><spring:message code="anagrafica.cambiaInPersonaDittaIndividuale"/></label>
                        <form:hidden path="varianteAnagrafica" id="varianteAnagrafica"/>
                        <form:hidden path="flgIndividuale" id="flgIndividuale"/>
                        <input name="dittaIndividuale" id="dittaIndividuale" type="checkbox" class="choice required" value="I"/>
                    </div>

                    <div class="ctrlHolder provinciaCciaa">
                        <label for="desProvinciaIscrizioneRi" class="required"><spring:message code="anagrafica.provinciacciaa"/></label>
                        <form:hidden path="provinciaCciaa.idProvincie" id="nProvinciaIscrizioneRi"/>
                        <form:input path="provinciaCciaa.descrizione" id="desProvinciaIscrizioneRi" maxlength="255" cssClass="textInput required" />
                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                    </div>

                    <div class="ctrlHolder flgAttesaIscrizioneRi">
                        <label for="flgAttesaIscrizioneRi" class="required"><spring:message code="anagrafica.attesascrizioneri"/></label>
                        <form:checkbox path="flgAttesaIscrizioneRi" id="flgAttesaIscrizioneRi" value="S" cssClass="choice required"/>
                    </div>
                    <div id="iscrizioneRegistroImpreseDiv">
                        <div class="ctrlHolder nIscrizioneRi">
                            <label for="nIscrizioneRi" class="required"><spring:message code="anagrafica.numeroiscrizioneri"/></label>
                            <form:input path="nIscrizioneRi" id="nIscrizioneRi" maxlength="255" cssClass="textInput required" />
                            <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                        </div>
                        <div class="ctrlHolder dataIscrizioneRi">
                            <label for="dataIscrizioneRi" class="required"><spring:message code="anagrafica.dataiscrizioneri"/></label>
                            <form:input path="dataIscrizioneRi" id="dataIscrizioneRi" maxlength="255" cssClass="textInput required date" />
                            <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                        </div>
                    </div>

                    <div class="ctrlHolder flgAttesaIscrizioneRea">
                        <label for="flgAttesaIscrizioneRea" class="required"><spring:message code="anagrafica.attesascrizionerea"/></label>
                        <form:checkbox path="flgAttesaIscrizioneRea" id="flgAttesaIscrizioneRea" value="S" cssClass="choice required"/>
                    </div>
                    <div  id="iscrizioneReaGDiv">
                        <div class="ctrlHolder nIscrizioneRea">
                            <label for="nIscrizioneRea" class="required"><spring:message code="anagrafica.numeroiscrizionerea"/></label>
                            <form:input path="nIscrizioneRea" id="nIscrizioneRea" maxlength="255" cssClass="textInput required" />
                            <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                        </div>
                        <div class="ctrlHolder dataIscrizioneRea">
                            <label for="dataIscrizioneRea" class="required"><spring:message code="anagrafica.dataiscrizionerea"/></label>
                            <form:input path="dataIscrizioneRea" id="dataIscrizioneRea" maxlength="255" cssClass="textInput required date" />
                            <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                        </div>
                    </div>
                </div>
            </fieldset>

            <fieldset class="fieldsetComunicazione professionistaSection">
                <legend><spring:message code="anagrafica.professionista"/></legend>
                <div>
                    <div class="ctrlHolder tipoCollegio">
                        <label for="desTipoCollegio" class="required"><spring:message code="anagrafica.tipocollegio"/></label>
                        <form:hidden path="codTipoCollegio" id="codTipoCollegio"/>
                        <form:input path="desTipoCollegio" id="desTipoCollegio" maxlength="255" cssClass="textInput required" />
                    </div>

                    <div class="ctrlHolder numeroIscrizione">
                        <label for="numeroIscrizione" class="required"><spring:message code="anagrafica.iscrizione"/></label>
                        <form:input path="numeroIscrizione" id="numeroIscrizione" maxlength="255" cssClass="textInput required" />
                    </div>

                    <div class="ctrlHolder dataIscrizione">
                        <label for="dataIscrizione" class="required"><spring:message code="anagrafica.dataiscrizione"/></label>
                        <form:input path="dataIscrizione" id="dataIscrizione" maxlength="255" cssClass="textInput required date" />
                    </div>

                    <div class="ctrlHolder alboProvincia">
                        <label for="desProvinciaIscrizione" class="required"><spring:message code="anagrafica.provinciaiscrizione"/></label>
                        <form:hidden path="provinciaIscrizione.idProvincie" id="nProvinciaIscrizione"/>
                        <form:input path="provinciaIscrizione.descrizione" id="desProvinciaIscrizione" maxlength="255" cssClass="textInput required" />
                    </div>
                </div>
            </fieldset>

            <fieldset class="fieldsetComunicazione recapitiSection" id="recapitiSection">
                <legend id="titoloRecapiti">
                    <spring:message code="anagrafica.recapito.sede.title"/>
                </legend>
                <div>
                    <div class="ctrlHolder " id="recapitoComuneDiv">
                        <form:hidden path="recapiti[0].IdTipoIndirizzo" id="idTipoIndirizzo" />
                        <form:hidden path="recapiti[0].descTipoIndirizzo" id="descTipoIndirizzo" />
                        <label for="desComuneRecapito" class="required"><spring:message code="anagrafica.recapito.comune" /></label>
                        <form:hidden path="recapiti[0].idComune" id="nComuneRecapito" />
                        <form:input path="recapiti[0].descComune" id="desComuneRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoLocalitaDiv">
                        <label for="localitaRecapito" class="required"><spring:message code="anagrafica.recapito.localita"/></label>
                        <form:input path="recapiti[0].localita" id="localitaRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoIndirizzoDiv">
                        <label for="indirizzoRecapito" class="required"><spring:message code="anagrafica.recapito.indirizzo"/></label>
                        <form:input path="recapiti[0].indirizzo" id="indirizzoRecapito" maxlength="255" cssClass="textInput required" />
                    </div>

                    <div class="ctrlHolder " id="recapitoCivicoDiv">
                        <label for="civicoRecapito" class="required"><spring:message code="anagrafica.recapito.civico"/></label>
                        <form:input path="recapiti[0].nCivico" id="civicoRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoCAPDiv">
                        <label for="capRecapito" class="required"><spring:message code="anagrafica.recapito.cap"/></label>
                        <form:input path="recapiti[0].cap" id="capRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder" id="recapitoAltreInfoIndirizzoDiv">
                        <label for="altreInfoIndirizzo" class="required"><spring:message code="anagrafica.recapito.altreInfoIndirizzo"/></label>
                        <form:input path="recapiti[0].altreInfoIndirizzo" id="altreInfoIndirizzo" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoPostaleDiv">
                        <label for="casellaPostaleRecapito" class="required"><spring:message code="anagrafica.recapito.casellapostale"/></label>
                        <form:input path="recapiti[0].casellaPostale" id="casellaPostaleRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoTelefonoDiv">
                        <label for="telefonoRecapito" class="required"><spring:message code="anagrafica.recapito.telefono"/></label>
                        <form:input path="recapiti[0].telefono" id="telefonoRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoCellulareDiv">
                        <label for="cellulareRecapito" class="required"><spring:message code="anagrafica.recapito.cellulare"/></label>
                        <form:input path="recapiti[0].cellulare" id="cellulareRecapito" maxlength="30" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoFAXDiv">
                        <label for="faxRecapito" class="required"><spring:message code="anagrafica.recapito.fax"/></label>
                        <form:input path="recapiti[0].fax" id="faxRecapito" maxlength="30" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoEmailDiv">
                        <label for="emailRecapito" class="required"><spring:message code="anagrafica.recapito.email"/></label>
                        <form:input path="recapiti[0].email" id="emailRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                    <div class="ctrlHolder " id="recapitoPECDiv">
                        <label for="pecRecapito" class="required"><spring:message code="anagrafica.recapito.pec"/></label>
                        <form:input path="recapiti[0].pec" id="pecRecapito" maxlength="255" cssClass="textInput required" />
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="buttonHolder_box">
            <button value="<spring:message code="anagrafica.button.salva"/>" class="primaryAction" name="submit" type="submit"><spring:message code="anagrafica.button.salva"/></button>
            <a href="<%=path%>/gestione/anagrafiche/list.htm?" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        </div>
    </div>
</form:form>