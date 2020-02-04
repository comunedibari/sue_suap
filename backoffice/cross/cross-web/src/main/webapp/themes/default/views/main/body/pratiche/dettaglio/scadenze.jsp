<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="timelineCard2 tl">

    <c:if test="${!empty pratica.scadenzeList}">
        <c:forEach items="${pratica.scadenzeList}" var="scadenza" begin="0">

            <div class="item" data-id="<fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataFineScadenza}" />" data-description="${scadenza.descrizioneScadenza}">
                <div style="padding:10px;">
                    <div class="ctrlHolder">
                        <label class="required">
                            Data scadenza:
                        </label>
                        <c:choose>
                            <c:when test="${scadenza.idStato.grpStatoScadenza == 'C'}">
                                <input class="input_anagrafica_disable" type="text" disabled="" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataFineScadenzaCalcolata}" />">          
                            </c:when>
                            <c:otherwise>
                                <input class="input_anagrafica_disable" type="text" disabled="" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataFineScadenza}" />">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="ctrlHolder">
                        <label class="required">
                            Descrizione:
                        </label>
                        <input class="input_anagrafica_disable" type="text" disabled="" value="${scadenza.descrizioneScadenza}">
                    </div>
                    <div class="ctrlHolder">
                        <label class="required">
                            Stato scadenza:
                        </label>
                        <input class="input_anagrafica_disable" type="text" disabled="" value="${scadenza.idStato.desStatoScadenza}">
                    </div>
                    <c:if test="${scadenza.idStato.grpStatoScadenza == 'C'}">
                        <div class="ctrlHolder">
                            <label class="required">
                                Data chiusura scadenza:
                            </label>
                            <input class="input_anagrafica_disable" type="text" disabled="" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataFineScadenza}" />">
                        </div>
                    </c:if>

                </div>

                <%-- <div class="read_more" data-id="${evento.dataEvento}"></div>--%>
            </div>

        </c:forEach>
    </c:if>

</div>