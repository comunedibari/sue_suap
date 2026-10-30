<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="it.wego.cross.constants.CartiglioConstants" %>
<tiles:insertAttribute name="body_error" />

<div class="content_sidebar">
    <c:url value="/download/pratica.htm" var="downloadPraticaUrl">
        <c:param name="id_pratica" value="${pratica.idPratica}"/>
    </c:url>
    <div class="sidebar_left"> 
    	<c:set var="cartiglio" value="${evento.cartiglio}" scope="request"/>
    	<c:set var="from" value="${CartiglioConstants.FROM_EVENT }" scope="request"/>
		<c:import url="/themes/default/views/main/body/pratiche/cartiglio.jsp"/>
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
                            <label class="required"><spring:message code="pratica.collegaAnagrafica.dettaglio.dataevento"/></label>
                            <span class="value">
                                <ul>
                                    <li>${evento.dataEvento}</li>
                                </ul>
                            </span>
                        </div>
                        <c:if test="${!empty evento.destinatari.anagrafiche}">
                            <div class="ctrlHolder">
                                <label class="required"><spring:message code="pratica.collegaAnagrafica.dettaglio.anagrafica"/></label>    
                                <span class="value">
                                    <ul>
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
                                    </ul>
                                </span>
                            </div>      
                        </c:if>
                        <div class="ctrlHolder">
                            <label class="required"><spring:message code="pratica.collegaAnagrafica.dettaglio.note"/></label>
                            <span class="value">
                                <ul>
                                    <li>${evento.note}</li>
                                </ul>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buttonHolder">
                <a href="<%=path%>/pratiche/dettaglio.htm?id_pratica=${id_pratica}" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>
