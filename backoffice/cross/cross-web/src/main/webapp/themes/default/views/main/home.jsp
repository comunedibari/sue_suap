<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- Inizio Menu home -->
<tiles:insertAttribute name="body_error" />
<div class="menu-home">
    <c:if test="${isSuperuser || enableGestionePratiche}">
        <div class="pratiche-assegnare">    
            <div class="icona"></div>

            <div class="box-testo">
                <h3><spring:message code="menu.area.accettazione.title"/></h3>
            </div>

            <div class="clear">

                <div id="menu_accettazione">

                    <ul class="menu_sotto">
                        <c:if test="${enablePraticheDaProtocollo}">
                            <li>
                                <a title="<spring:message code="menu.nuovaGrafica.nuoveprotocollo"/>" href="${path}/pratiche/nuove/protocollo.htm"><spring:message code="menu.nuovaGrafica.nuoveprotocollo"/></a>
                            </li>
                        </c:if>
                        <c:if test="${enablePraticheDaProtocollo && enableComunicazioniIngresso}">
                            <li class="">
                                <a title="<spring:message code="menu.nuovaGrafica.documentiprotocollo"/>" href="${path}/documenti/nuovi/protocollo.htm"><spring:message code="menu.nuovaGrafica.documentiprotocollo"/></a>
                            </li>
                        </c:if>
                       <c:if test="${abititaAssegnazionePratiche}">
                        	<li>
                            	<a title="<spring:message code="menu.nuovaGrafica.nuovepratiche"/>" href="${path}/pratiche/nuove.htm"><spring:message code="menu.nuovaGrafica.nuovepratiche"/></a>
                        	</li>
                        </c:if>
                    </ul>

                </div>

            </div>
        </div>   
    </c:if>
    <div class="apertura-pratiche">
        <div class="icona"></div>

        <div class="box-testo">
            <h3><spring:message code="menu.area.gestione.title"/></h3>
        </div>

        <div class="clear">

            <div id="menu_apertura">

                <ul class="menu_sotto">
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
                    </c:if>
                    <li>
                        <a title="<spring:message code="menu.nuovaGrafica.gestioneanagrafiche"/>" href="${path}/gestione/anagrafiche/list.htm"><spring:message code="menu.nuovaGrafica.gestioneanagrafiche"/></a>
                    </li>
                    <%-- <li><a title="<spring:message code="menu.nuovaGrafica.tasklist"/>" href="${path}/workflow/mytask.htm"><spring:message code="menu.nuovaGrafica.tasklist"/></a></li> --%>
                    <c:if test="${isSuperuser || enableGestionePratiche}">
                        <li>
                            <a title="<spring:message code="menu.nuovaGrafica.gestionemail"/>" href="${path}/console/mail.htm"><spring:message code="menu.nuovaGrafica.gestionemail"/></a>
                        </li>
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

            </div>

        </div>
    </div>

    <c:if test="${isSuperuser}">
        <div class="impostazioni_home">    
            <div class="icona"></div>

            <div class="box-testo">
                <h3><spring:message code="menu.area.impostazioni.title"/></h3>
            </div>

            <div class="clear">

                <div id="menu_impostazioni">
                    <ul class="menu_sotto">
                        <li><a title="<spring:message code="menu.nuovaGrafica.gestioneenti"/>" href="${path}/ente/index.htm"><spring:message code="menu.nuovaGrafica.gestioneenti"/></a></li>
                        <li><a title="<spring:message code="menu.nuovaGrafica.gestioneutenti"/>" href="${path}/utenti/index.htm"><spring:message code="menu.nuovaGrafica.gestioneutenti"/></a></li>
                        <li><a title="<spring:message code="menu.nuovaGrafica.gestionetemplate"/>" href="${path}/gestione/eventoTemplate/lista.htm"><spring:message code="menu.nuovaGrafica.gestionetemplate"/></a></li>
                        <li><a title="<spring:message code="menu.nuovaGrafica.gestioneprocessi"/>" href="${path}/processi/lista.htm"><spring:message code="menu.nuovaGrafica.gestioneprocessi"/></a></li>
                        <li><a title="<spring:message code="menu.nuovaGrafica.gestioneprocedimenti"/>" href="${path}/gestione/procedimenti/lista.htm"><spring:message code="menu.nuovaGrafica.gestioneprocedimenti"/></a></li>
                        <li><a title="<spring:message code="menu.nuovaGrafica.anagrafe.tributaria"/>" href="${path}/anagrafe_tributaria/export.htm"><spring:message code="menu.nuovaGrafica.anagrafe.tributaria"/></a></li>
                        <!--<li><a title="<spring:message code="menu.nuovaGrafica.gestionedatiestesi"/>" href="${path}/datiestesi/lista.htm"><spring:message code="menu.nuovaGrafica.gestionedatiestesi"/></a></li>-->
                        <li><a title="<spring:message code="menu.nuovaGrafica.console"/>" href="${path}/console/index.htm"><spring:message code="menu.nuovaGrafica.console"/></a></li>
                            <c:if test="${enableTasklist}">
                            <li><a title="<spring:message code="menu.nuovaGrafica.tasklist"/>" href="${path}/workflow/tasklist.htm"><spring:message code="menu.nuovaGrafica.tasklist"/></a></li>
                             <li><a title="<spring:message code="avvisi.gestione"/>" href="${path}/avvisi/listaAvvisi.htm"><spring:message code="avvisi.gestione"/></a></li>
                            </c:if>
                    </ul>
                </div>

            </div>
        </div>
    </c:if>
    <c:if test="${isSuperuser || enableGestionePratiche}">
        <div class="scadenziario">
            <div class="icona"></div>

            <div class="box-testo">
                <h3><spring:message code="menu.area.alert.title"/></h3>
            </div>

            <div class="clear">

                <div id="menu_scadenziario">

                    <ul class="menu_sotto">
                        <li><a title="<spring:message code="menu.area.alert.pratiche"/>" href="pratiche/scadenzario.htm"><spring:message code="menu.area.alert.pratiche"/></a></li>
                            <%-- <li><a title="<spring:message code="menu.area.alert.comunicazioni"/>" href="comunicazioni.htm"><spring:message code="menu.area.alert.comunicazioni"/></a></li> --%>
                    </ul>

                </div>

            </div>
        </div>
    </c:if>
    <div class="clear"></div>
	<!-- <div style="background: #b6d6ea;color: #001e31;text-align: center;margin: 2%;border: 5px solid #cceafd;padding: 10px;">
  		<strong>Attenzione!</strong> Stiamo aggiornando il sistema. Ti preghiamo di scaricare i moduli ai link seguenti e compilarli.
	  	<ul style="list-style-type: none;padding:10px;">
  			<li style="display:inline">
  				<a style="color: inherit;margin: 1px;background-color: #01253d;padding: 3px 10px;color: #fff;border-radius: 2px;" href="#" >Modulo unificato Permesso di costruire edilizia.pdf</a>
  			</li>
  			<li style="display:inline">
  				<a style="color: inherit;margin: 1px;background-color: #01253d;padding: 3px 10px;color: #fff;border-radius: 2px;" href="#" >Allegato_A_modulistica_edilizia.doc</a>
  			</li>
  			<li style="display:inline">
  				<a style="color: inherit;margin: 1px;background-color: #01253d;padding: 3px 10px;color: #fff;border-radius: 2px;" href="#" >Modulo unificato Permesso di costruire edilizia.pdf</a>
  			</li>
  		</ul>  		
	</div> -->
</div>

<!-- Fine Menu home -->