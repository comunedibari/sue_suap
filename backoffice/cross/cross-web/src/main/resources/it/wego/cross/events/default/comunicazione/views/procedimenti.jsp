<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="procedimenti">
    <label class="required" for="procedimentoRiferimento">
        <spring:message code="pratica.comunicazione.evento.procedimento.riferimento"/>
    </label>

    <c:choose>
        <c:when test="${not empty processo_evento.idProcedimentoRiferimento}">
            <input type="radio" name="procedimentoRiferimento" id="procedimentoRiferimento[0]" value="${processo_evento.idProcedimentoRiferimento.idProc}">${procedimento.desProcedimento}
        </c:when>
        <c:otherwise>
            <c:set var="count" value="0" scope="page" />
            <c:forEach items="${pratica.endoProcedimentiList}" begin="0"  var="procedimenti">
                <c:set var="contains" value="false" />
                <fmt:parseNumber var="parsedNumber" integerOnly="true" type="number" value="${procedimenti.procedimento.idProc}" />
                <c:forEach var="item" items="${comunicazione.procedimentiIds}">
                    <c:if test="${item eq parsedNumber}">
                        <c:set var="contains" value="true" />
                    </c:if>
                </c:forEach>
                <c:set var="email" value="${procedimenti.ente.email}" scope="page" />
                <c:choose>
                    <c:when test="${!empty procedimenti.ente.pec}">
                        <c:set var="email" value="${procedimenti.ente.pec}" scope="page" />
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${processo_evento.maxDestinatari != null && processo_evento.maxDestinatari > 0}"> <%-- numero limitato di procedimenti - forzo a 1 - quindi radiobutton--%>
                        <input type="radio" name="procedimentiIds" id="procedimentiIds[${count}]" value="${procedimenti.procedimento.idProc}" <c:if test="${fn:length(pratica.endoProcedimentiList) == 1 || contains}">checked="checked"</c:if> />
                        &nbsp;${procedimenti.procedimento.descrizione}(${procedimenti.ente.descrizione} - ${email})<br/>
                    </c:when>
                    <c:otherwise> <%-- numero illimitato di procedimenti, quindi checkbox --%>
                        <c:choose>
                            <c:when test="${fn:length(pratica.endoProcedimentiList) == 1}">
                                <input type="radio" name="procedimentiIds" id="procedimentiIds[${count}]" value="${procedimenti.procedimento.idProc}" checked="checked" />
                                &nbsp;${procedimenti.procedimento.descrizione}(${procedimenti.ente.descrizione} - ${email})
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="procedimentiIds" class="choice required" id="procedimentiIds[${count}]" value="${procedimenti.procedimento.idProc}" <c:if test="${contains}">checked="checked"</c:if> />
                                &nbsp;${procedimenti.procedimento.descrizione}(${procedimenti.ente.descrizione} - ${email})<br/>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>    
                </c:choose>
                <c:set var="count" value="${count}+1" scope="page" />
            </c:forEach>
        </c:otherwise>
    </c:choose>    
</div>
