<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />
<div class="uniForm">
    <div class="inlineLabels" id="dettaglio_pratica">
        <h2 class="short" style="text-align:center"><spring:message code="pratica.comunicazione.evento.dettaglio"/></h2>
        <div class="ctrlHolder dettaglio_liv_0">
            <h4 class="readOnlyLabel"><spring:message code="pratica.comunicazione.evento.dati"/></h4>
            <div class="ctrlHolder">
                <label class="required"><spring:message code="pratica.comunicazione.evento.descrizione"/></label>
                <span class="value">${evento.descrizione}</span>
            </div>
            <div class="ctrlHolder">
                <label class="required"><spring:message code="pratica.comunicazione.dettaglio.protocollo"/></label>
                <span class="value">${evento.numProtocollo}</span>
            </div>
            <div class="ctrlHolder">
                <c:if test="${evento.verso!=null && evento.verso == 'I'}">
                    <label class="required"><spring:message code="pratica.comunicazione.evento.mittente"/></label>    
                </c:if>
                <c:if test="${evento.verso!=null && evento.verso == 'O'}">
                    <label class="required"><spring:message code="pratica.comunicazione.evento.destinatario"/></label>    
                </c:if>
                <span class="value">
                    <ul>
                        <c:if test="${!empty evento.destinatari.anagrafiche}">
                            <c:forEach items="${evento.destinatari.anagrafiche}" var="anagrafica" begin="0">
                                <li>
                                    ${anagrafica.anagrafica.codiceFiscale} - 
                                    <c:if test="${!empty anagrafica.anagrafica.denominazione}">
                                        <%-- è una azienda --%>
                                        ${anagrafica.anagrafica.denominazione}
                                    </c:if>
                                    <c:if test="${!empty anagrafica.anagrafica.cognome}">
                                        <%-- è una persona --%>
                                        ${anagrafica.anagrafica.nome} ${anagrafica.anagrafica.cognome}
                                    </c:if>
                                    <c:set var="email">
                                        <spring:message code="evento.posta.ordinaria"/>
                                    </c:set>
                                    <c:if test="${!empty anagrafica.recapito.email}">
                                        <c:set var="email" value="${anagrafica.recapito.email}"/>
                                    </c:if>
                                    <c:if test="${!empty anagrafica.recapito.pec}">
                                        <c:set var="email" value="${anagrafica.recapito.pec}"/>
                                    </c:if>
                                    (${email}) 
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${!empty evento.destinatari.enti}">
                            <c:forEach items="${evento.destinatari.enti}" var="ente" begin="0">
                                <li>${ente.descrizione}</li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${!empty evento.destinatari.notifica}">
                            <li>
                                ${evento.destinatari.notifica.presso}
                            </li>
                        </c:if>
                    </ul>
                </span>
            </div>
            <div class="ctrlHolder">
                <label class="required"><spring:message code="pratica.comunicazione.evento.dataevento"/></label>
                <span class="value">${evento.dataEvento}</span>
            </div>

            <div class="ctrlHolder">
                <label class="required"><spring:message code="pratica.comunicazione.evento.note"/></label>
                <span class="value">${evento.note}</span>
            </div>
        </div>
        <div class="ctrlHolder dettaglio_liv_0">
            <c:if test="${!empty evento.allegati}">
                <table cellspacing="0" cellpadding="0" class="master">
                    <tr>
                        <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                        <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                        <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                        <th></th>
                    </tr>
                    <c:forEach items="${evento.allegati}" var="allegato" begin="0">
                        <tr>
                            <td>${allegato.descrizione}</td>
                            <td>${allegato.nomeFile}</td>
                            <td>${allegato.tipoFile}</td>
                            <td>
                                <form action="<%=path%>/download.htm" class="uniForm inlineLabels" method="post">
                                    <input type="hidden" name="id_file" value="${allegato.idAllegato}"/>
                                    <input type="submit" name="subit" value="<spring:message code="pratica.comunicazione.evento.file.download"/>" class="ui-state-default ui-corner-all"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <div class="buttonHolder">
            <a href="<%=path%>/comunicazioni.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        </div>
    </div>
</div>