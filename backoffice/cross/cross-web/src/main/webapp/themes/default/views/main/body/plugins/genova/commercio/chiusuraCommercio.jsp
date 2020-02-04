<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />
<div class="content_sidebar">
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
                        <label class="required"><spring:message code="pratica.comunicazione.evento.dataevento"/></label>
                        <span class="value">${evento.dataEvento}</span>
                    </div>

                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="pratica.comunicazione.evento.note"/></label>
                        <span class="value">${evento.note}</span>
                    </div>
                </div>
            </div>
            <div class="buttonHolder">
                <a href="${path}/pratiche/dettaglio.htm?id_pratica=${id_pratica}" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
            </div>
        </div>
    </div>    <div class="clear"></div>
</div>