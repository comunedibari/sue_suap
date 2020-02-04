<%@page import="it.wego.cross.entity.Utente"%>
<%@page import="it.wego.cross.constants.SessionConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
%>
<%-- --%>
<c:if test="${utenteConnesso != null}">


    <div id="navigation">
        <div class="path"><a href="${infobar[0]}">HOME</a> / <tiles:insertAttribute name="infopath" /> </div>
        <div class="scadenziario_apri">
            <img class="icon_scadenziario" src="<%=path%>/themes/default/images/scadenza.gif" />
            <p>Alerts: ${praticheTotaliBar} pratiche</p>
        </div>
        <c:if test="${not empty selectedUG}">
            <div class="selected_ug">
                <a href="${path}/ug/select_current.htm?previous_url=${currentUriB64}">Unità di Gestione Selezionata: ${selectedUG.idEnte.descrizione}</a>
            </div>
        </c:if>
        <%--
        <div class="messaggi_apri">
            <img class="icon_messaggi" src="<%=path%>/themes/default/images/messaggio.png" />
            <p>${messaggiDaLeggere} messaggi da leggere</p>
        </div>
        --%>
        <div style="clear:both;"></div>
    </div>

    <div id="scadenziario_banner">
        <p class="scadenziario_nascondi"><img src="<%=path%>/themes/default/images/close.jpg" /></p>

        <div class="clear"></div>
        <h3>${inArrivoBarTotal} pratiche in arrivo</h3>

        <div id="scadenziario_banner_scroll" class="scadenziario_banner_scroll">
            <c:forEach items="${inArrivoBar}"  begin="0" var="inArrivoBar" >
                <div id="scadenziario_bar_inscadenza">

                    <div class="scadenziario_bar_dato_id">Pratica:</div>
                    <div class="scadenziario_bar_dato_numero_pratica">${inArrivoBar.protocollo}</div>
                    <div class="scadenziario_bar_dato_lente">

                        <form action="<%= path%>/pratiche/dettaglio.htm" method="post">
                            <input type="hidden" name="id_pratica" value="${inArrivoBar.idPratica}">
                            <input type="hidden" name="idPratica" value="${inArrivoBar.idPratica}">
                            <button type="submit" class="scadenziario_bar_button" title="Dettaglio pratica">
                                <div class="scadenziario_bar_bottone_dettaglio"></div> 
                            </button>
                        </form>

                    </div>

                    <div class="clear"></div>

                </div>
            </c:forEach>

            <div id="div_scadenze_data"></div>

        </div>
        <h3>${scadenzeBarTotal} pratiche in scadenza</h3>

        <div id="scadenziario_banner_scroll" class="scadenziario_banner_scroll">
            <c:forEach items="${scadenzeBar}"  begin="0" var="scadenzaBar" >
                <div id="scadenziario_bar_inscadenza">

                    <div class="scadenziario_bar_dato_id">Pratica:</div>
                    <div class="scadenziario_bar_dato_numero_pratica">${scadenzaBar.protocollo}</div>
                    <%-- <div class="scadenziario_bar_data_scadenza"><fmt:formatDate pattern="dd/MM/yyyy" value="${scadenzaBar.dataFineScadenza}" /></div> --%>
                    <div class="scadenziario_bar_dato_lente">

                        <form action="<%= path%>/pratiche/dettaglio.htm" method="post">
                            <input type="hidden" name="id_pratica" value="${scadenzaBar.idPratica}">
                            <input type="hidden" name="idPratica" value="${scadenzaBar.idPratica}">
                            <button type="submit" class="scadenziario_bar_button" title="Dettaglio pratica">
                                <div class="scadenziario_bar_bottone_dettaglio"></div> 
                            </button>
                        </form>

                    </div>

                    <div class="clear"></div>

                </div>
            </c:forEach>

            <div id="div_scadenze_data"></div>

        </div>
        <div class="clear"></div>
    </div>
</c:if>