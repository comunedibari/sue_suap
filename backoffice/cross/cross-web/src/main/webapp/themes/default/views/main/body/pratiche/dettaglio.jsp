<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    td > .action {
        text-align: center;
        margin-right:15px;
    }    
    .ui-tabs-panel{
        margin-top: 34px;
    }
    .ui-tabs-panel>.wgf-container-title:first-of-type{
        display:none;
    }
</style>
<script src="${path}/assets/js/jquery.gritter.js"></script>
<link rel="stylesheet" href="${path}/assets/css/jquery.gritter.css" />
<script type="text/javascript">
    var path = '${path}';
    var idPraticaX = '${pratica.idPratica}';
	var fromImportXml = '${fromImportXml}';									   
    var EventManager = {};
    $.extend($.gritter.options, {
        position: 'bottom-right'
    });
</script>

<script  type="text/javascript" src="<c:url value="/javascript/cross/pratica.gestione.js"/>"></script>
<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<tiles:insertAttribute name="operazioneRiuscita" />
<div class="uniForm uniform2">
    <div class="content_sidebar">	
        <div class="sidebar_left">

            <h3><spring:message code="pratica.comunicazione.dettaglio.identificativo"/> <strong>${pratica.identificativoPratica}</strong></h3>

            <div class="sidebar_elemento">

                ${pratica.oggettoPratica}
                <p><strong><spring:message code="pratica.comunicazione.dettaglio.protocollo"/></strong> ${pratica.identificatoreProtocolloIstanza}</p>
                <p><strong><spring:message code="pratica.comunicazione.dettaglio.dataricezione"/></strong> <fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></p>
                <p><strong><spring:message code="pratica.comunicazione.dettaglio.statopratica"/></strong> ${pratica.idStatoPratica.descrizione}</p>
                <p><strong>In carico a </strong> ${pratica.idUtente.cognome} ${pratica.idUtente.nome}</p>

                <c:choose>
                    <c:when test="${not empty pratica.idModello}">
                        <p style="margin-top:20px;">
                            <a href="${path}/download/pratica.htm" class="scarica" target="_blank">
                                <spring:message code="pratica.comunicazione.scaricaIstanza"/>
                            </a>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="sidebar_center">

            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="<c:if test="${empty currentTab || currentTab == 'frame0'}">active</c:if>"><a href="#frame0"><spring:message code="pratica.comunicazione.copertina.title"/></a></li>
                    <li class="<c:if test="${currentTab=='frame1'}">active</c:if>"><a href="#frame1"><spring:message code="pratica.comunicazione.anagrafica.title"/></a></li>
                        <c:if test="${pratica.ente.tipoEnte=='SUAP'}">
                        <li class="<c:if test="${currentTab=='frame2'}">active</c:if>"><a href="#frame2"><spring:message code="pratica.comunicazione.dettaglio.procedimenti.title"/></a></li>
                        </c:if>
                    <li class="<c:if test="${currentTab=='frame3'}">active</c:if>"><a href="#frame3"><spring:message code="pratica.comunicazione.dettaglio.allegati.title"/></a></li>
                    <li class="<c:if test="${currentTab=='frame4'}">active</c:if>"><a href="#frame4"><spring:message code="pratica.comunicazione.dettaglio.immobile.title"/></a></li>
                    <li class="<c:if test="${currentTab=='frame5'}">active</c:if>"><a href="#frame5"><spring:message code="pratica.comunicazione.dettaglio.indirizzo.title"/></a></li>
                    <li class="<c:if test="${currentTab=='frame6'}">active</c:if>"><a href="#frame6"><spring:message code="pratica.comunicazione.dettaglio.scadenze.title"/></a></li>
                    <li class="<c:if test="${currentTab=='frame7'}">active</c:if>"><a href="#frame7"><spring:message code="pratica.comunicazione.dettaglio.evento.title"/></a></li>
                    <li class="<c:if test="${currentTab=='frame8'}">active</c:if>"><a href="#frame8"><spring:message code="pratica.comunicazione.dettaglio.note.title"/></a></li>
                        <c:if test="${enablePraticaMessaggi}">
                        <li class="<c:if test="${currentTab=='frame9'}">active</c:if>"><a href="#frame9"><spring:message code="pratica.comunicazione.dettaglio.messaggi.title"/></a></li>
                        </c:if>

                    <li class="<c:if test="${currentTab=='frame10'}">active</c:if>"><a href="#frame10"><spring:message code="pratica.comunicazione.dettaglio.praticheCollegate.title"/></a></li>
                    <c:if test="${enableDirittiSegreteria}">
                        <li class="<c:if test="${currentTab=='frame11'}">active</c:if>"><a href="#frame11"><spring:message code="pratica.comunicazione.dettaglio.dirittiSegreteria.title"/></a></li>
                    </c:if>

                    </ul>
                    <!-- Contenuto cartelle -->
                    <div class="frames">
                        <div class="frame copertina <c:if test="${empty currentTab || currentTab == 'frame0'}">active</c:if>" id="frame0">
                        <tiles:insertAttribute name="copertina" />
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame1'}">active</c:if>" id="frame1">
                        <tiles:insertAttribute name="anagrafica" />
                    </div>
                    <c:if test="${pratica.ente.tipoEnte=='SUAP'}">
                        <div class="frame <c:if test="${currentTab=='frame2'}">active</c:if>" id="frame2">
                            <tiles:insertAttribute name="procedimenti" />
                        </div>
                    </c:if>
                    <div class="frame <c:if test="${currentTab=='frame3'}">active</c:if>" id="frame3">
                        <tiles:insertAttribute name="allegati" />
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame4'}">active</c:if>" id="frame4">
                        <tiles:insertAttribute name="dati_catastali" />
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame5'}">active</c:if>" id="frame5">
                        <tiles:insertAttribute name="indirizzi_intervento" />
                    </div>                    
                    <div class="frame timelineeventi <c:if test="${currentTab=='frame6'}">active</c:if>" id="frame6">
                        <tiles:insertAttribute name="scadenze" />
                    </div>
                    <div class="frame timelineeventi <c:if test="${currentTab=='frame7'}">active</c:if>" id="frame7">
                        <tiles:insertAttribute name="eventi" />
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame8'}">active</c:if>" id="frame8">
                        <tiles:insertAttribute name="note" />
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame9'}">active</c:if>" id="frame9">
                        <tiles:insertAttribute name="messaggi" />
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame10'}">active</c:if>" id="frame10">
                        <tiles:insertAttribute name="pratiche_collegate" />
                    </div>
                    <c:if test="${enableDirittiSegreteria}">                            
                        <div class="frame <c:if test="${currentTab=='frame11'}">active</c:if>" id="frame11">
                            ${gestioneDirittiSegreteriaForm}
                        </div>
                    </c:if>
                </div>
                <div style="float:left;">
				<c:choose>
    				<c:when test="${fromImportXml}">
                
                    	<a class="secondaryAction" href="${path}/caricamentopratiche/index.htm">&larr; Indietro</a>
                    	
                    </c:when>
                    <c:otherwise>
                    	<a class="secondaryAction" href="${path}/pratiche/gestisci.htm">&larr; Indietro</a>
                    </c:otherwise>
                </c:choose>		    
                </div>      
            </div>

        </div>
        <div class="clear"></div>
    </div>


</div>

<script>
    $(function() {
        var currentTab = '${currentTab}';
        if (currentTab === 'frame7') {
            setTimeout(function() {
                $('a[href="#frame7"]').trigger('click');
            }, 1);
        }
    });
</script>