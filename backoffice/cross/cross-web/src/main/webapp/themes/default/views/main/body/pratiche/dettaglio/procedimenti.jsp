<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Cont div -->
<c:set var="conto_div" value="0" scope="page" />
<c:forEach items="${pratica.endoProcedimentiList}" begin="0"  var="procedimento" varStatus="loop">
    <c:set var="conto_div" value="${conto_div + 1}" scope="page"/>
</c:forEach>
<!-- Cont div -->

<div class="imbox_frame <c:if test="${conto_div > 3}">margine_imbox_frame</c:if>">
        <div class="nascondiutente table-add-link">
            <a href="<%=path%>/pratiche/dettaglio/aggiungiProcedimento.htm">
            <spring:message code="pratica.comunicazione.dettaglio.procedimenti.aggiungi.procedimento"/>
            <img src="<%=path%>/themes/default/images/icons/add.png" alt="<spring:message code="pratica.comunicazione.dettaglio.procedimenti.aggiungi.procedimento"/>" title="<spring:message code="pratica.comunicazione.dettaglio.procedimenti.aggiungi.procedimento"/>">
        </a>
    </div>
    <c:if test="${conto_div > 3}">
        <div class="controllo_sinistra">
            <div class="controller_box">
                <div class="box_left" onclick="item_animate('<c:out value="${conto_div}"></c:out>', '#procedimenti_box', '-=277px')"></div>
                </div>
            </div>
    </c:if>
    <div id="procedimenti_box">
        <table cellpadding="0" cellspacing="0">
            <tr>
                <c:forEach items="${pratica.endoProcedimentiList}" begin="0"  var="endoprocedimento" varStatus="loop">
                    <td id="endoprocedimento_${endoprocedimento.idProcEnte}">
                        <div class="circle <c:if test="${loop.last}">lastCircle</c:if>">
                                <div class="ctrlHolder">
                                    <label class="required" style="display:inline">
                                    <spring:message code="pratica.titolo.procedimento"/>
                                </label>
                                <a class="nascondiutente box-unlink" data-holder="${endoprocedimento.idProcEnte}">
                                    <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.button.scollega.procedimento"/>" title="<spring:message code="pratica.button.scollega.procedimento"/>">
                                    <spring:message code="pratica.button.scollega.procedimento"/>
                                </a> 
                            </div>

                            <div class="ctrlHolder">
                                <div class="field big">${endoprocedimento.procedimento.descrizione}</div>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required">
                                    <spring:message code="pratica.comunicazione.dettaglio.procedimenti.termini"/>
                                </label>
                                <input class="input_anagrafica_disable" type="text" disabled="" value="${endoprocedimento.procedimento.termini}">
                            </div>
                            <div class="ctrlHolder">
                                <label class="required">
                                    <spring:message code="pratica.comunicazione.dettaglio.procedimenti.entedestinatario"/>
                                </label>
                                <input class="input_anagrafica_disable" type="text" disabled="" value="${endoprocedimento.ente.descrizione}">
                            </div>
                            <c:if test="${! empty endoprocedimento.pratica}">
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.procedimenti.Stato"/>
                                    </label>
                                    <input class="input_anagrafica_disable" type="text" disabled="" value="${endoprocedimento.pratica.idStatoPratica.descrizione}">
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.procedimenti.Utente"/>
                                    </label>
                                    <input class="input_anagrafica_disable" type="text" disabled="" value="${endoprocedimento.pratica.idUtente.cognome} ${endoprocedimento.pratica.idUtente.nome}">
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.dettaglio.procedimenti.GiorniScadenza"/>
                                    </label>
                                    <input class="input_anagrafica_disable" type="text" disabled="" value="${endoprocedimento.pratica.numGiorniPrimaScadenza}">
                                </div> 
                            </c:if>
                        </div>
                    </td>
                </c:forEach>
            </tr>
        </table>
    </div>
    <c:if test="${conto_div > 3}">
        <div class="controllo_destra">
            <div class="controller_box">
                <div class="box_right" onclick="item_animate('<c:out value="${conto_div}"></c:out>', '#procedimenti_box', '+=277px')"></div>
                </div>
            </div>
    </c:if>
    <div class="clear"></div>
</div>