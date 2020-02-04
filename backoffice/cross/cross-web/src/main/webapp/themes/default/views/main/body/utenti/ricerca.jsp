<%@page import="it.wego.cross.constants.SessionConstants"%>
<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
    $(document).ready(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });

        $("#ricerca_button").click(function() {
            $('#list').setGridParam({url: url + "?" + $('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid", [{page: 1}]);
        });

    });
</script>

<div id="ricerca">
    <h3><a href="#"><spring:message code="ente.comuni.ricerca.titolo"/></a></h3>
    <div>
        <form id="form_ricerca"  class="uniForm inlineLabels" method="post">
            <input name="tipoFiltro" type="hidden" value="utente" />
            <div style="width:1080px;">    
                <div class="ricerca_div_forms">  
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <label for="nome"><spring:message code="utenti.nome"/></label>
                            <input id="nome" name="nome" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.nome}" />
                        </div>
                        <div class="ctrlHolder">
                            <label for="cognome"><spring:message code="utenti.cognome" /></label>
                            <input id="cognome" name="cognome" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.cognome}"/>
                        </div>
                        <div class="ctrlHolder">
                            <label for="codicefiscale"><spring:message code="utenti.codicefiscale" /></label>
                            <input id="codiceFiscale" name="codiceFiscale" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.codiceFiscale}"/>
                        </div>
                        <div class="ctrlHolder">
                            <label for="username"><spring:message code="utenti.username" /></label>
                            <input id="username" name="username" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.username}"/>
                        </div>
                        <div class="ctrlHolder">
                            <label for="superuser">Grado di autorizzazione </label>
                            <select id="superuser" name="superuser"    class="textInput">
                                <c:set var="selected" value=""/> 
                                <c:if test="${(filtroRicerca.superuser != null) &&  (filtroRicerca.superuser == 'S')}" >
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="SUPERUSER" ${selected}>Solo superuser</option>
                                <c:set var="selected" value=""/> 
                                <c:if test="${(filtroRicerca.superuser != null) &&  (filtroRicerca.superuser == 'N')}" >
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="NON SUPERUSER" ${selected}>Solo non super user</option>
                                <c:set var="selected" value=""/> 
                                <c:if test="${(filtroRicerca.superuser == null) ||  ((filtroRicerca.superuser != 'S')&&(filtroRicerca.userStatus != 'N'))}" >
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="" ${selected}>Indifferente</option>
                            </select>
                        </div>
                        <div class="ctrlHolder">
                            <label for="userStatus"><spring:message code="utenti.stato" /></label>
                            <select id="userStatus" name="userStatus"  class="textInput">
                                <c:set var="selected" value=""/> 
                                <c:if test="${(filtroRicerca.userStatus != null) &&  (filtroRicerca.userStatus == 'ATTIVO')}" >
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="ATTIVO" ${selected}><spring:message code="utenti.stato.attivo" /></option>
                                <c:set var="selected" value=""/> 
                                <c:if test="${(filtroRicerca.userStatus != null) &&  (filtroRicerca.userStatus == 'NON_ATTIVO')}" >
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="NON_ATTIVO" ${selected}><spring:message code="utenti.stato.nonattivo" /></option>
                                <c:set var="selected" value=""/> 
                                <c:if test="${(filtroRicerca.userStatus == null) ||  ((filtroRicerca.userStatus != 'NON_ATTIVO')&&(filtroRicerca.userStatus != 'ATTIVO'))}" >
                                    <c:set var="selected" value="selected=\"selected\""/>     
                                </c:if>
                                <option  value="" ${selected}>Qualsiasi stato</option>
                            </select>
                        </div>
                    </div>

                    <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                        <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>
                    </div>
                </div>    
        </form>
    </div>
</div>
</div>

