<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script language="javascript">
    var path = '${path}';
</script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/notifiche.js"/>"></script>
<div id="headingnew">
    <div style="float:left;" class="header_logo_cross_container">
        <a href="${path}" title="${applicationName} ${version}" class="header_logo_cross_link"></a>
    </div>
    <div class="nav-bar">
        <div class="nav-bar-inner">
            <ul class="menu">
                <c:if test="${isSuperuser || enableGestionePratiche}">
                    <li data-role="dropdown">
                        <a class="accettazione" href="#">Accettazione</a>
                        <ul class="dropdown-menu">
                            <c:if test="${enablePraticheDaProtocollo}">
                                <li>
                                    <a title="<spring:message code="menu.nuovaGrafica.nuoveprotocollo"/>" href="${path}/pratiche/nuove/protocollo.htm"><spring:message code="menu.nuovaGrafica.nuoveprotocollo"/></a>
                                </li>
                            </c:if>
                            <c:if test="${enablePraticheDaProtocollo && enableComunicazioniIngresso}">
                                <li>
                                    <a title="<spring:message code="menu.nuovaGrafica.documentiprotocollo"/>" href="${path}/documenti/nuovi/protocollo.htm"><spring:message code="menu.nuovaGrafica.documentiprotocollo"/></a>
                                </li>
                            </c:if>
                             <c:if test="${abititaAssegnazionePratiche}">
                            	<li>
                                	<a title="<spring:message code="menu.nuovaGrafica.nuovepratiche"/>" href="${path}/pratiche/nuove.htm"><spring:message code="menu.nuovaGrafica.nuovepratiche"/></a>
                            	</li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <li data-role="dropdown">
                    <a class="gestione" href="#">Gestione</a>
                    <ul class="dropdown-menu">
                        <c:if test="${isSuperuser || enableGestionePratiche}">                        
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.aperturanuovepratiche"/>" href="${path}/pratiche/nuove/apertura.htm"><spring:message code="menu.nuovaGrafica.aperturanuovepratiche"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestionepratiche"/>" href="${path}/pratiche/gestisci.htm"><spring:message code="menu.nuovaGrafica.gestionepratiche"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.scadenziario"/>" href="${path}/pratiche/scadenzario.htm"><spring:message code="menu.nuovaGrafica.scadenziario"/></a>
                            </li>
                            <%-- <li><a title="<spring:message code="menu.area.alert.comunicazioni"/>" href="${path}/comunicazioni.htm"><spring:message code="menu.area.alert.comunicazioni"/></a></li> --%>
                        </c:if>
                        <li>
                            <a title="<spring:message code="menu.nuovaGrafica.gestioneanagrafiche"/>" href="${path}/gestione/anagrafiche/list.htm"><spring:message code="menu.nuovaGrafica.gestioneanagrafiche"/></a>
                        </li>
                        <c:if test="${isSuperuser || enableGestionePratiche}">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.gestionemail"/>" href="${path}/console/mail.htm"><spring:message code="menu.nuovaGrafica.gestionemail"/></a></li>
                            </c:if>
                            <c:if test="${enableCds}">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.cds"/>" href="${path}/form/view.htm?idForm=it.wego.cross.forms.common.GestioneOrganiCollegialiGrid"><spring:message code="menu.nuovaGrafica.cds"/></a>
                            </li>
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.cds.sedute"/>" href="${path}/form/view.htm?idForm=it.wego.cross.forms.common.GestioneOrganiCollegialiSeduteGrid"><spring:message code="menu.nuovaGrafica.cds.sedute"/></a>
                            </li>

                        </c:if>
                    </ul>
                </li>
                <c:if test="${isSuperuser}">
                    <li data-role="dropdown">
                        <a class="admin" href="#">Impostazioni</a>
                        <ul class="dropdown-menu">
                            <li><a title="<spring:message code="menu.nuovaGrafica.gestioneenti"/>" href="${path}/ente/index.htm"><spring:message code="menu.nuovaGrafica.gestioneenti"/></a></li>
                            <li><a title="<spring:message code="menu.nuovaGrafica.gestioneutenti"/>" href="${path}/utenti/index.htm"><spring:message code="menu.nuovaGrafica.gestioneutenti"/></a></li>
                            <li><a title="<spring:message code="menu.nuovaGrafica.gestionetemplate"/>" href="${path}/gestione/eventoTemplate/lista.htm"><spring:message code="menu.nuovaGrafica.gestionetemplate"/></a></li>
                            <li><a title="<spring:message code="menu.nuovaGrafica.gestioneprocessi"/>" href="${path}/processi/lista.htm"><spring:message code="menu.nuovaGrafica.gestioneprocessi"/></a></li>
                            <li><a title="<spring:message code="menu.nuovaGrafica.gestioneprocedimenti"/>" href="${path}/gestione/procedimenti/lista.htm"><spring:message code="menu.nuovaGrafica.gestioneprocedimenti"/></a></li>
                            <li><a title="<spring:message code="menu.nuovaGrafica.anagrafe.tributaria"/>" href="${path}/anagrafe_tributaria/export.htm"><spring:message code="menu.nuovaGrafica.anagrafe.tributaria"/></a></li>
                            <!--<li><a title="<spring:message code="menu.nuovaGrafica.gestionedatiestesi"/>" href="${path}/datiestesi/lista.htm"><spring:message code="menu.nuovaGrafica.gestionedatiestesi"/></a></li>-->
                            <li><a title="<spring:message code="menu.nuovaGrafica.caricamentopratiche"/>" href="${path}/caricamentopratiche/index.htm"><spring:message code="menu.nuovaGrafica.caricamentopratiche"/></a></li>
                            <li><a title="<spring:message code="menu.nuovaGrafica.console"/>" href="${path}/console/index.htm"><spring:message code="menu.nuovaGrafica.console"/></a></li>
                                <c:if test="${enableTasklist}">
                                <li><a title="<spring:message code="menu.nuovaGrafica.tasklist"/>" href="${path}/workflow/tasklist.htm"><spring:message code="menu.nuovaGrafica.tasklist"/></a></li>
                                </c:if>
                              <li><a title="<spring:message code="avvisi.gestione"/>" href="${path}/avvisi/listaAvvisi.htm"><spring:message code="avvisi.gestione"/></a></li>
                        </ul>
                    </li> 
               </c:if>
               <c:if test="${isEstrazioniUser || isEstrazioniCilaTodoUser}">
                    <li data-role="dropdown">
                        <a class="estrazioni" href="#">Estrazioni</a>
                         <ul class="dropdown-menu"> 
                         	<c:if test="${isEstrazioniUser}">
	                            <li><a title="<spring:message code="menu.nuovaGrafica.estrazioniCILA"/>" href="${path}/estrazioni/cila.htm"><spring:message code="menu.nuovaGrafica.estrazioniCILA"/></a></li>
	                            <li><a title="<spring:message code="menu.nuovaGrafica.estrazioniSCIA"/>" href="${path}/estrazioni/scia.htm"><spring:message code="menu.nuovaGrafica.estrazioniSCIA"/></a></li>
	                            <li><a title="<spring:message code="menu.nuovaGrafica.estrazioniPDC"/>" href="${path}/estrazioni/pdc.htm"><spring:message code="menu.nuovaGrafica.estrazioniPDC"/></a></li>
	                            <li><a title="<spring:message code="menu.nuovaGrafica.estrazioniAGIB"/>" href="${path}/estrazioni/agib.htm"><spring:message code="menu.nuovaGrafica.estrazioniAGIB"/></a></li>
                            </c:if>
                            <c:if test="${isEstrazioniCilaTodoUser}">
			                    <li><a href="${path}/estrazioni/cilaToDo.htm">Pratiche Cila 20%</a></li>
			                </c:if>
                        </ul>
                    </li>
                                                  
                </c:if>
                <%-- <c:if test="${isEstrazioniCilaTodoUser}">
                    <li data-role="dropdown">
                        <a class="estrazioni" href="${path}/estrazioni/cilaToDo.htm">Cila to do</a>                       
                    </li>
                                                  
                </c:if> --%>
                <li data-role="dropdown">
                    <a class="notifiche" href="#"> <span class="notificheDaLeggere">0</span></a>
                    <ul class="dropdown-menu dropdown-menu-notifiche">
                        <li>
                            <a title="<spring:message code="menu.nuovaGrafica.notifiche"/>" href="${path}/workflow/notifiche/list.htm"><spring:message code="menu.nuovaGrafica.notifiche.empty"/></a>
                        </li>
                    </ul>
                </li>
            </ul>




            <span class="divider"></span>
			<span class="element"><a class="exit" href="${path}/utenti/modificaPassword.htm">Cambia Password</a></span> 
            <span class="element"><a class="exit" href="${path}/esci.htm">Esci</a></span>
        </div>
    </div>

    <%--    
        Decommenta per riabilitare la chat
        <div class="apri"> 
    --%>

    <div class="apri"> 
        <div class="user-bar" id="user-bar">
            <a href="#"><img width="40" class="user_icon" src="${path}/themes/default/images/user_m.jpg" /><p><strong>${utenteConnesso.nome} ${utenteConnesso.cognome}</strong></p></a>
            <div class="clear"></div>
        </div>
    </div>

</div>

 