<%
    String path = request.getContextPath();
    String url = path + "/pratica/comunicazionerea/submit.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />
<form:form modelAttribute="comunicazioneRea" action="<%=url%>"  id="creazioneEventoForm" method="post" cssClass="uniForm inlineLabels comunicazione">
    <h2 style="text-align: center">
        ${titoloPagina}
    </h2>
    <div class="inlineLabels">

        <fieldset class="fieldsetComunicazione fieldsetEvento">
            <legend>Evento partenza</legend>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="idEventoPartenza">
                    EventoPartenza
                </label>
                <form:select id="idEventoPartenza" path="idEventoPartenza">
                    <form:options items="${eventiPartenzaList}" itemLabel="itemLabel" itemValue="itemValue"/>
                </form:select>
            </div>

        </fieldset>

        <fieldset class="fieldsetComunicazione fieldsetEvento">
            <legend>Dati pratica</legend>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="tipologiaProcedimento">
                    Tipologia di procedimento
                </label>
                <form:select id="tipologiaProcedimento" path="tipologiaProcedimento">
                    <form:options items="${tipologieProcedimento}" itemLabel="itemLabel" itemValue="itemValue"/>
                </form:select>
            </div>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="tipologiaIntervento">
                    <%-- <spring:message code="pratica.comunicazione.evento.procedimento.riferimento"/> --%>
                    Tipologia di intervento
                </label>
                <form:select id="tipologiaIntervento" path="tipologiaIntervento">
                    <form:options items="${tipologieIntervento}" itemLabel="itemLabel" itemValue="itemValue"/>
                </form:select>
            </div>

        </fieldset>

        <fieldset class="fieldsetComunicazione fieldsetEvento">
            <legend>Dichiarante pratica</legend>
            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="dichiarantePratica">
                    <%-- <spring:message code="pratica.comunicazione.evento.procedimento.riferimento"/> --%>
                    Dichiarante pratica
                </label>
                <form:select id="dichiarantePratica" name="dichiarantePratica" path="dichiarantePratica" >
                    <form:options items="${anagraficheFisichePraticaList}" itemLabel="descrizioneAngrafica" itemValue="idAnagrafica"/>
                </form:select>
            </div>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="qualificaRappresentanteAzienda">
                    Qualifica dichiarante pratica
                </label>
                <form:select id="qualificaDichiarantePratica" path="qualificaDichiarantePratica">
                    <form:options items="${qualificheDichiarantePratica}" itemLabel="itemLabel" itemValue="itemValue"/>
                </form:select>
            </div>
        </fieldset>

        <fieldset class="fieldsetComunicazione fieldsetEvento">
            <legend>Azienda di riferimento</legend>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="aziendaRiferimento">
                    Azienda
                </label>
                <form:select id="aziendaRiferimento" name="aziendaRiferimento" path="aziendaRiferimento" >
                    <form:options items="${anagraficheGiuridichePraticaList}" itemLabel="descrizioneAngrafica" itemValue="idAnagrafica"/>
                </form:select>
            </div>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="formaGiuridicaAzienda">
                    Forma Giuridica
                </label>
                <form:select id="formaGiuridicaAzienda" path="formaGiuridicaAzienda">
                    <form:options items="${formeGiuridicaAzienda}" itemLabel="itemLabel" itemValue="itemValue"/>
                </form:select>
            </div>

        </fieldset>
        <fieldset class="fieldsetComunicazione fieldsetEvento">
            <legend>Rappresentante Azienda</legend>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="rappresentanteAzienda">
                    Rappresentante Azienda
                </label>
                <form:select id="rappresentanteAzienda" name="rappresentanteAzienda" path="rappresentanteAzienda" >
                    <form:options items="${anagraficheFisichePraticaList}" itemLabel="descrizioneAngrafica" itemValue="idAnagrafica"/>
                </form:select>
            </div>

            <div class="ctrlHolder dettaglio_liv_0">
                <label class="required" for="caricaRappresentanteAzienda">
                    Carica Rappresentante Azienda
                </label>
                <form:select id="caricaRappresentanteAzienda" path="caricaRappresentanteAzienda">
                    <form:options items="${caricheRappresentanteAzienda}" itemLabel="itemLabel" itemValue="itemValue"/>
                </form:select>
            </div>
        </fieldset>
    </div>
    <form:hidden path="idPratica"/>
    <form:hidden path="idEvento" />
    <form:hidden path="tipologiaComunicazioneRea" />
    <div class="buttonHolder">
        <a href="<%=path%>/pratica/evento/index.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        <button type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.crea"/></button>
    </div>
</form:form>