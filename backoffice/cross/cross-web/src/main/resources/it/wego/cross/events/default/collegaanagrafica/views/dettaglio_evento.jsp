<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />
<div class="content_sidebar">
    <c:url value="/download/pratica.htm" var="downloadPraticaUrl">
        <c:param name="id_pratica" value="${pratica.idPratica}"/>
    </c:url>
    <div class="sidebar_left">
        <h3><spring:message code="pratica.comunicazione.dettaglio.identificativo"/> <strong>${pratica.identificativoPratica}</strong></h3>
        <div class="sidebar_elemento">
            ${pratica.oggettoPratica}
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.dataricezione"/>:</strong> <fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></p>
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.stato"/>:</strong> ${pratica.idStatoPratica.descrizione}</p>
            <c:choose>
                <c:when test="${not empty pratica.idModello}">
                    <p style="margin-top:20px;">
                        <a href="${downloadPraticaUrl}" class="scarica" target="_blank">
                            <spring:message code="pratica.comunicazione.evento.pratica.download"/>
                        </a>
                    </p>
                </c:when>
                <c:otherwise>
                    <span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="sidebar_center">
        <div class="page-control" data-role="page-control">
            <span class="menu-pull"></span>
            <div class="menu-pull-bar"><spring:message code="pratica.comunicazione.evento.dettaglio"/></div>
            <ul>                   
                <li class="active"><a href="#frame1">${evento.descrizione}</a></li>
            </ul>
            <div class="frames">
                <div class="frame active copertina" id="frame1">
                    <div>
                        <div class="ctrlHolder">
                            <label class="required"><spring:message code="pratica.collegaAnagrafica.dettaglio.utente"/></label>
                            <span class="value">
                                <ul>
                                    <li>${evento.operatore}</li>
                                </ul>
                            </span>
                        </div>
                        <div class="ctrlHolder">
                            <label class="required"><spring:message code="pratica.collegaAnagrafica.dettaglio.anagrafica"/></label>    
                            <span class="value">
                                <ul>
                                    <c:if test="${!empty evento.destinatari.anagrafiche}">
                                        <c:forEach items="${evento.destinatari.anagrafiche}" var="anagrafica" begin="0">
                                            <li>
                                                <c:if test="${!empty anagrafica.anagrafica.denominazione}">
                                                    <%-- è una azienda --%>
                                                    ${anagrafica.anagrafica.denominazione}
                                                </c:if>
                                                <c:if test="${!empty anagrafica.anagrafica.cognome}">
                                                    <%-- è una persona --%>
                                                    ${anagrafica.anagrafica.nome} ${anagrafica.anagrafica.cognome}
                                                </c:if>
                                                (${anagrafica.anagrafica.codiceFiscale})
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                </ul>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buttonHolder">
                <a href="<%=path%>/pratiche/dettaglio.htm?id_pratica=${id_pratica}&currentTab=frame7" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
            </div>
        </div>
    </div>

    <div class="clear"></div>

</div>