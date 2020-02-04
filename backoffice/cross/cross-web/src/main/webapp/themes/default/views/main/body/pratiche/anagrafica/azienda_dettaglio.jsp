<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="ctrlHolder dettaglioAziendaContainer">
    <c:choose>
        <c:when test="${!empty datiIdentificativi.errore}">
            <div class="errore">
                <div class="erroreContent">${datiIdentificativi.errore.valore} (codice ${datiIdentificativi.errore.codice})</div>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${!empty datiIdentificativi.warning}">
                <div class="warning">
                    <ul>
                        <c:forEach var="warn" begin="0" items="${datiIdentificativi.warning}">
                            <li>${warn.valore} (codice ${warn.codice})</li>    
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <fieldset class="fieldsetComunicazione">
                <legend><spring:message code="anagrafica.ri.title"/></legend>
                <div>
                    <div class="detailKey">
                        <spring:message code="anagrafica.ri.denominazione"/>
                    </div>
                    <div class="detailValue" id="denominazioneRI">${datiIdentificativi.denominazione}<c:if test="${empty datiIdentificativi.denominazione}">&nbsp;</c:if></div>

                    <div class="detailKey">
                        <spring:message code="anagrafica.ri.statoimpresa"/>
                    </div>
                    <div class="detailValue" id="statoImpresaRI">${datiIdentificativi.statoImpresa}<c:if test="${empty datiIdentificativi.statoImpresa}">&nbsp;</c:if></div>

                    <div class="detailKey">
                        <spring:message code="anagrafica.ri.codicefiscale"/>
                    </div>
                    <div class="detailValue" id="codiceFiscaleRI">${datiIdentificativi.codiceFiscale}<c:if test="${empty datiIdentificativi.codiceFiscale}">&nbsp;</c:if></div>

                    <div class="detailKey">
                        <spring:message code="anagrafica.ri.partitaiva"/>
                    </div>
                    <div class="detailValue" id="partitaIvaRI">${datiIdentificativi.partitaIva}<c:if test="${empty datiIdentificativi.partitaIva}">&nbsp;</c:if></div>

                    <div class="detailKey">
                        <spring:message code="anagrafica.ri.cciaa"/>
                    </div>
                    <div class="detailValue" id="desCciaaRI">${datiIdentificativi.desCciaa}<c:if test="${empty datiIdentificativi.desCciaa}">&nbsp;</c:if></div>
                    <input type="hidden" name="idCciaaRI" id="idCciaaRI" value="${datiIdentificativi.idCciaa}" />

                    <div class="detailKey">
                        <spring:message code="anagrafica.ri.nrea"/>
                    </div>
                    <div class="detailValue" id="numeroReaRI">${datiIdentificativi.nrea}<c:if test="${empty datiIdentificativi.nrea}">&nbsp;</c:if></div>
                </div>
            </fieldset>
            <c:if test="${!empty datiIdentificativi.sedeLegale}">
                <fieldset class="fieldsetComunicazione">
                    <legend><spring:message code="anagrafica.ri.sede.title"/></legend>
                    <div>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.email}">
                            <div class="detailKey">
                                <spring:message code="anagrafica.ri.email"/>
                            </div>
                            <div class="detailValue">${datiIdentificativi.sedeLegale.email}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.descComune}">
                            <div class="detailKey">
                                <spring:message code="utenti.descComune"/>
                            </div>
                            <div class="detailValue" id="descComune">${datiIdentificativi.sedeLegale.descComune}</div>
                            <div class="hidden" id="idComune">${datiIdentificativi.sedeLegale.idComune}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.descProvincia}">
                            <div class="detailKey">
                                <spring:message code="utenti.descProvincia"/>
                            </div>
                            <div class="detailValue" id="descProvincia">${datiIdentificativi.sedeLegale.descProvincia}</div>
                            <div class="hidden" id="idProvincia">${datiIdentificativi.sedeLegale.idProvincia}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.descStato}">
                            <div class="detailKey">
                                <spring:message code="utenti.descStato"/>
                            </div>
                            <div class="detailValue" id="descStato">${datiIdentificativi.sedeLegale.descStato}</div>
                            <div class="hidden" id="idStato">${datiIdentificativi.sedeLegale.descStato}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.cap}">
                            <div class="detailKey">
                                <spring:message code="utenti.cap"/>
                            </div>
                            <div class="detailValue" id="cap">${datiIdentificativi.sedeLegale.cap}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.nCivico}">
                            <div class="detailKey">
                                <spring:message code="utenti.nCivico"/>
                            </div>
                            <div class="detailValue" id="nCivico">${datiIdentificativi.sedeLegale.nCivico}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.indirizzo}">
                            <div class="detailKey">
                                <spring:message code="utenti.indirizzo"/>
                            </div>
                            <div class="detailValue" id="indirizzo">${datiIdentificativi.sedeLegale.indirizzo}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.localita}">
                            <div class="detailKey">
                                <spring:message code="utenti.localita"/>
                            </div>
                            <div class="detailValue" id="localita">${datiIdentificativi.sedeLegale.localita}</div>
                        </c:if>
                        <c:if test="${!empty datiIdentificativi.sedeLegale.telefono}">
                            <div class="detailKey">
                                <spring:message code="utenti.telefono"/>
                            </div>
                            <div class="detailValue" id="telefono">${datiIdentificativi.sedeLegale.telefono}</div>
                        </c:if>
                    </div>
                </fieldset>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>