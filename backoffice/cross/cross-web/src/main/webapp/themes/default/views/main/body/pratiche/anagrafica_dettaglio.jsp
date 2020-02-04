<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="ctrlHolder dettaglioAnagraficaContainer">
    <c:choose>
        <c:when test="${!empty anagrafica}">
            <c:choose>
                <c:when test="${anagrafica.tipoAnagrafica == 'G'}">
                    <%-- Persona giuridica --%>
                    <fieldset class="fieldsetComunicazione">
                        <legend><spring:message code="dettaglio.anagrafica.personagiuridica"/></legend>
                        <div>
                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.denominazione"/>
                                </div>
                                <div class="detailValue">${anagrafica.denominazione}&nbsp;</div>
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.formagiuridica"/>
                                </div>
                                <div class="detailValue">${anagrafica.desFormaGiuridica}&nbsp;</div>
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.partitaiva"/>
                                </div>
                                <div class="detailValue">${anagrafica.partitaIva}&nbsp;</div>

                                <c:if test="${!empty anagrafica.nIscrizioneRea}">
                                </div>

                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.anagrafica.numeroiscrizionerea"/>
                                    </div>
                                    <div class="detailValue">${anagrafica.nIscrizioneRea}&nbsp;</div>
                                </div>

                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.anagrafica.partitaiva"/>
                                    </div>
                                    <div class="detailValue">${anagrafica.provinciaCciaa.descrizione}&nbsp;</div>
                                </div>

                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.anagrafica.dataiscrizionerea"/>
                                    </div>
                                    <div class="detailValue"><fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataIscrizioneRea}" />&nbsp;</div>
                                </c:if>
                            </div>
                    </fieldset>
                </c:when>
                <c:otherwise>
                    <%-- Persona fisica --%>
                    <fieldset class="fieldsetComunicazione">
                        <legend><spring:message code="dettaglio.anagrafica.personafisica"/></legend>
                        <div>

                            <div>
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.nome"/>
                                </div>
                                <div class="detailValue">${anagrafica.nome}&nbsp;</div>
                            </div>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.cognome"/>
                            </div>
                            <div class="detailValue">${anagrafica.cognome}&nbsp;</div>
                        </div>

                        <c:if test="${!empty anagrafica.denominazione}">
                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.denominazione"/>
                                </div>
                                <div class="detailValue">${anagrafica.denominazione}&nbsp;</div>
                            </div>
                        </c:if>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.codicefiscale"/>
                            </div>
                            <div class="detailValue">${anagrafica.codiceFiscale}&nbsp;</div>

                            <c:if test="${!empty anagrafica.partitaIva}">
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.partitaiva"/>
                                </div>
                                <div class="detailValue">${anagrafica.partitaIva}&nbsp;</div>
                            </c:if>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.datanascita"/>
                            </div>
                            <div class="detailValue"><fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataNascita}" />&nbsp;</div>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.cittadinanza"/>
                            </div>
                            <div class="detailValue">${anagrafica.desCittadinanza}&nbsp;</div>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.nazionalita"/>
                            </div>
                            <div class="detailValue">${anagrafica.desNazionalita}&nbsp;</div>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.comunenascita"/>
                            </div>
                            <div class="detailValue">${anagrafica.comuneNascita.descrizione}&nbsp;</div>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.localitanascita"/>
                            </div>
                            <div class="detailValue">${anagrafica.localitaNascita}&nbsp;</div>
                        </div>

                        <div style="clear:both">
                            <div class="detailKey">
                                <spring:message code="dettaglio.anagrafica.sesso"/>
                            </div>
                            <div class="detailValue">${anagrafica.sesso}</div>

                            <c:if test="${!empty anagrafica.desTipoCollegio}">
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.tipocollegio"/>
                                </div>
                                <div class="detailValue">${anagrafica.desTipoCollegio}&nbsp;</div>
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.iscrizione"/>
                                </div>
                                <div class="detailValue">${anagrafica.numeroIscrizione}&nbsp;</div>
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.dataiscrizione"/>
                                </div>
                                <div class="detailValue"><fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataIscrizione}" />&nbsp;</div>
                            </div>

                            <div style="clear:both">
                                <div class="detailKey">
                                    <spring:message code="dettaglio.anagrafica.provinciaiscrizione"/>
                                </div>
                                <div class="detailValue">${anagrafica.provinciaIscrizione.descrizione}&nbsp;</div>
                            </c:if>
                        </div>
                    </fieldset>
                </c:otherwise>
            </c:choose>
            <c:if test="${!empty recapiti}">
                <fieldset class="fieldsetComunicazione">
                    <legend><spring:message code="dettaglio.anagrafica.recapiti"/></legend>
                    <div>
                        <c:forEach items="${recapiti}" begin="0" var="recapito">
                            <div class="dettaglioRecapito">
                                <h4>${recapito.descTipoIndirizzo}</h4>

                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.comune"/>
                                    </div>
                                    <div class="detailValue">${recapito.descComune}&nbsp;</div>
                                </div>

                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.localita"/>
                                    </div>
                                    <div class="detailValue">${recapito.localita}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.indirizzo"/>
                                    </div>
                                    <div class="detailValue">${recapito.indirizzo}&nbsp;${recapito.nCivico}</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.cap"/>
                                    </div>
                                    <div class="detailValue">${recapito.cap}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.casellapostale"/>
                                    </div>
                                    <div class="detailValue">${recapito.casellaPostale}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.telefono"/>
                                    </div>
                                    <div class="detailValue">${recapito.telefono}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.cellulare"/>
                                    </div>
                                    <div class="detailValue">${recapito.cellulare}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.fax"/>
                                    </div>
                                    <div class="detailValue">${recapito.fax}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.email"/>
                                    </div>
                                    <div class="detailValue">${recapito.email}&nbsp;</div>
                                </div>
                                <div style="clear:both">
                                    <div class="detailKey">
                                        <spring:message code="dettaglio.recapito.pec"/>
                                    </div>
                                    <div class="detailValue">${recapito.pec}&nbsp;</div>
                                </div>
                            </c:forEach>
                        </div>
                </fieldset>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="uniForm failedSubmit">
                <div id="errorMsg">
                    <h3><spring:message code="dettaglio.anagrafica.empty"/></h3>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
