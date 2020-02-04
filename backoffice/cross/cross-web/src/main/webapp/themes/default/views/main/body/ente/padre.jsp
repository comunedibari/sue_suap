<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>

<table cellspacing="0" cellpadding="0" class="master">
    <tr>
        <th><spring:message code="ente.descrizione"/></th>
        <th><spring:message code="utenti.enti.citta"/></th>
        <th><spring:message code="ente.provincia"/></th>
        <th><spring:message code="ente.email"/></th>
        <th><spring:message code="ente.pec"/></th>
        <th><spring:message code="ente.telefono"/></th>
        <th></th>
    </tr>
    <c:if test="${!empty enti}">
        <c:forEach items="${enti}" var="ente" begin="0">
            <c:if test="${ente.idEnte != codEnteOrigine}">
                <tr>
                    <td>${ente.descrizione}</td>
                    <td>${ente.citta}</td>
                    <td>${ente.provincia}</td>
                    <td>${ente.email}</td>
                    <td>${ente.pec}</td>
                    <td>${ente.telefono}</td>
                    <td>
                        <form action="<%=path%>/ente/collegaPadre.htm" method="post">
                            <input type="hidden" name="codEnte" value="${ente.idEnte}"/>
                            <input type="hidden" name="codEntePadre" value="${codEnteOrigine}"/>
                            <input type="submit" name="submit" value="<spring:message code="ente.seleziona"/>"/>
                        </form>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </c:if>
    <c:if test="${empty enti}">
        <tr>
            <td colspan="7"><spring:message code="ente.noresult"/></td>
        </tr>
    </c:if>
</table>
<div class="buttonHolder">
    <a href="<%=path%>/ente/modifica.htm?codEnte=${codEnteOrigine}" class="primaryAction">&larr; Indietro</a>
</div>