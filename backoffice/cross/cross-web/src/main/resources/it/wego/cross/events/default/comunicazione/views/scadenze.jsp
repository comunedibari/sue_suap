<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ctrlHolder dettaglio_liv_0">
    <table cellspacing="0" cellpadding="0" class="master"> 
        <tbody>
            <tr>
                <th><spring:message code="pratica.comunicazione.evento.scadenze.descrizionescadenza"/></th>
                <th><spring:message code="pratica.comunicazione.evento.scadenze.datainizioscadenza"/></th>
                <th><spring:message code="pratica.comunicazione.evento.scadenze.datafinescadenza"/></th>
                <th><spring:message code="pratica.comunicazione.evento.scadenze.entecoinvolto"/></th>
                <th><spring:message code="pratica.comunicazione.evento.scadenze.dachiudere"/></th>
            </tr>
            <c:set var="countScadenze" value="0" scope="page" />
            <c:forEach items="${scadenze_da_chiudere}" begin="0" var="scadenza">
                <tr>
                    <td>${scadenza.descrizione}</td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataInizioScadenza}" /></td>
                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataFineScadenza}" /></td>
                    <td>${scadenza.desEnte}</td>
                    <td>
                        <form:checkbox path="scadenzeDaChiudere[${countScadenze}].idScadenza" value="${scadenza.idScadenza}"/>
                        <!--<input type="checkbox" name="scadenzeDaChiudere[${countScadenze}].idScadenza" value="${scadenza.idScadenza}" />-->
                    </td>
                </tr>
                <c:set var="countScadenze" value="${countScadenze + 1}" scope="page"/>
            </c:forEach>
        </tbody>
    </table>
</div>