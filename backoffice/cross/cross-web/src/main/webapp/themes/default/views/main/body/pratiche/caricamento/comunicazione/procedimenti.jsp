<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="ctrlHolder dettaglio_liv_0">
    <label class="required" for="procedimentoRiferimento">
        <spring:message code="pratica.comunicazione.evento.procedimento.riferimento"/>
    </label>
    <select id="procedimentoRiferimento" name="procedimentoRiferimento[0]">
        <c:forEach items="${comunicazione.procedimentiRiferimento}" begin="0"  var="procedimento">
            <option value="${procedimento.idProcedimento}">${procedimento.desProcedimento}</option>
        </c:forEach>
    </select>
</div>