<%-- 
    Document   : list
    Created on : 29-ott-2012, 11.06.19
    Author     : CS
--%>
<%@ page pageEncoding="utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String pathRegistroImprese = request.getContextPath() + "/gestione/anagrafiche/personagiuridica.htm";
    String pathRicercaAnagrafe = request.getContextPath() + "/gestione/anagrafiche/personafisica.htm";
    String pathAggiornaAnagrafica = request.getContextPath() + "/gestione/anagrafiche/aggiornaAnagrafica.htm";
%>
<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/core.css"/>"  media="all"/>
<%@ page import="it.wego.cross.constants.CartiglioConstants" %>
<script>
    var messaggioClick = "<spring:message code="messaggio.click"/>";
    var messaggioConferma = "<spring:message code="messaggio.conferma"/>";
    var idPratica = ${idPratica};
    var errRuolo = "<spring:message code="error.idTipoRuolo"/>";
    var errNome = "<spring:message code="error.nome"/>";
    var errCognome = "<spring:message code="error.cognome"/>";
    var errCodiceFiscale = "<spring:message code="error.codiceFiscale"/>";
    var errDesCittadinanza = "<spring:message code="error.desCittadinanza"/>";
    var errIdCittadinanza = "<spring:message code="error.idCittadinanza"/>";
    var errDesNazionalita = "<spring:message code="error.desNazionalita"/>";
    var errIdNazionalita = "<spring:message code="error.idNazionalita"/>";
    var errDesComune = "<spring:message code="error.desComune"/>";
    var errIdComune = "<spring:message code="error.idComune"/>";
    var errIdStato = "<spring:message code="error.idStato"/>";
    var errIdProvincia = "<spring:message code="error.idProvincia"/>";
    var errIdProvinciaCciaa = "<spring:message code="error.idProvinciaCciaa"/>";
    var errDenominazionea = "<spring:message code="error.denominazione"/>";
    var errPartitaIva = "<spring:message code="error.partitaIva"/>";
    var errAlbo = "<spring:message code="error.albo"/>";
    var errFormaGiuridica = "<spring:message code="error.idFormaGiuridica"/>";
    var datiDaDB = "<spring:message code="datiDaDB"/>";
    var datiDaXML = "<spring:message code="datiDaXML"/>";
    var urlRegistroImprese = "<%=pathRegistroImprese%>";
    var urlRicercaAnagrafe = "<%=pathRicercaAnagrafe%>";
    var urlAggiornaAnagrafica = "<%=pathAggiornaAnagrafica%>";
    var messaggioIdPratica = '<spring:message code="pratica.id"/>';
    var messaggioPraticaStato = '<spring:message code="pratica.comunicazione.evento.pratica.stato"/>';
    var messaggioDataRicezione = '<spring:message code="pratica.data.ricezione"/>';
    var messaggioDescrizione = '<spring:message code="pratica.descrizione"/>';
    var messaggioProtocollo = '<spring:message code="pratiche.elenco.protocollo"/>';
    var messaggioEnte = '<spring:message code="pratiche.elenco.ente"/>';
    var messaggioComune = '<spring:message code="pratiche.elenco.comune"/>';
    var messaggioRichiedente = '<spring:message code="pratiche.elenco.richiedente"/>';
    var messaggioIncarico = '<spring:message code="pratiche.elenco.incarico"/>';
    var messaggioAzione = '<spring:message code="pratica.azione"/>';
    var attivaRegistroImprese =${attivaRegistroImprese};
    //var ricercaAnagrafe = ${gestioneAnagrafe};
    var attivaRicercaAnagrafe = ${gestioneAnagrafe};
    var attivaRicercaAnagrafePG = ${gestioneAnagrafePG};
    var urlPratiche = "${path}/pratiche/gestisci/ajax.htm";
    var praticaManuale = false;
    <c:set var="praticaManuale" value=""/>
    <c:if test="${(praticaDettaglio.tipoMessaggio!=null && praticaDettaglio.tipoMessaggio != 'AEC') || (message != null && message.error == true)}">
        <c:set var="praticaManuale" value="s"/>
    praticaManuale = true;
    </c:if>
</script>
<tiles:insertAttribute name="body_error" />

<script language="javascript">
    var path = '${path}';
    var isCoverEditable = ${isCoverEditable};
    var abilitazione = ${abilitato};
    var collegaProcedimentoDialogTitle = '<spring:message code="protocollo.dettaglio.endoprocedimento.title"/>';
    var statiPraticaList = [];
    <c:forEach items="${statiPratica}" var="statoPratica" varStatus="status">
    statiPraticaList.push({value: ${statoPratica.idStatoPratica}, text: '${statoPratica.descrizione}'});
    </c:forEach>
    $(document).ready(function(){
	    $("li a").click(function(){
			if($(this).attr("href") === "#frame1"){
	    		var newLength = $('#anagraficaContainer').width() - 2
	    		$("#jqTablePersonalData").jqGrid('setGridWidth',newLength);
			}
		})
    })
</script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/pratica.apertura.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/confronta.anagrafica.ready.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/confronta.anagrafica.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/confronta.anagrafica.functions.js"/>"></script>

<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<tiles:insertAttribute name="operazioneRiuscita" />
<div class="uniForm ">
    <div class="content_sidebar">	
        <div class="sidebar_left">
            <c:set var="cartiglio" value="${pratica.cartiglio}" scope="request"/>
            <c:set var="from" value="${CartiglioConstants.FROM_OPENING }" scope="request"/>
			<c:import url="/themes/default/views/main/body/pratiche/cartiglio.jsp"/>
        </div>

        <div class="sidebar_center">

            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="<c:if test="${empty currentTab || currentTab=='frame1'}">active</c:if>"><a href="#frame1">Anagrafiche</a></li>
                        <c:if test="${ente_pratica.tipoEnte=='SUAP'}">
                        <li class="<c:if test="${currentTab=='frame2'}">active</c:if>"><a href="#frame2"><spring:message code="pratica.titolo.endoprocedimenti"/></a></li>
                        </c:if>
                    <li class="<c:if test="${currentTab=='frame3'}">active</c:if>"><a href="#frame3">Allegati</a></li>
                    <li class="<c:if test="${currentTab=='frame4'}">active</c:if>"><a href="#frame4">Dati catastali</a></li>
                    <li class="<c:if test="${currentTab=='frame5'}">active</c:if>"><a href="#frame5">Indirizzi intervento</a></li>
                        <c:if test="${praticaDettaglio.notifica != null}">
                        <li class="<c:if test="${currentTab=='frame6'}">active</c:if>"><a href="#frame6">Notifica</a></li>
                        </c:if>
                    <li class="<c:if test="${currentTab=='frame7'}">active</c:if>"><a href="#frame7"><spring:message code="pratica.comunicazione.dettaglio.evento.title"/></a></li>
                    </ul>
                    <!-- Contenuto cartelle -->
                    <div class="frames">
                        <input id="abilitazione" value="${abilitato}" style="display:none;">
                    
                    <div class="frame <c:if test="${empty currentTab || currentTab=='frame1'}">active</c:if>" id="frame1">
                        <tiles:insertAttribute name="anagrafica" />
                    </div>
                    <c:if test="${ente_pratica.tipoEnte=='SUAP'}">
                        <%-- Procedimenti --%>
                        <div class="frame <c:if test="${currentTab=='frame2'}">active</c:if>" id="frame2">
                                <div class="procedimenti_header">
                                    <a href="#" id="add_procedimenti_btn" class="nascondiutente">
                                    <spring:message code="pratica.comunicazione.collega.procedimento"/>
                                    <img src="${path}/themes/default/images/icons/add.png" alt="<spring:message code="pratica.comunicazione.collega.procedimento"/>" title="<spring:message code="pratica.comunicazione.collega.procedimento"/>">
                                </a>
                            </div>
                            <c:set var="procedimentiSize" value="0" scope="page" />
                            <c:if test="${not empty pratica.endoProcedimentiList}">
                                <c:set var="procedimentiSize" value="${fn:length(pratica.endoProcedimentiList)}" scope="page" />
                            </c:if>
                            <div class="imbox_frame <c:if test="${procedimentiSize > 1}">margine_imbox_frame</c:if>">
                                <c:if test="${procedimentiSize > 1}">
                                    <div class="controllo_sinistra">
                                        <div class="controller_box">
                                            <div class="box_left" onclick="item_animate('<c:out value="${procedimentiSize}"></c:out>', '#procedimenti_box', '-=277px')"></div>
                                            </div>
                                        </div>
                                </c:if>
                                <div id="procedimenti_box">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <c:forEach  items="${pratica.endoProcedimentiList}" var="endoprocedimento" >
                                                <td id="endoprocedimento_${endoprocedimento.idProcEnte}">
                                                    <div class="circle <c:if test="${loop.last}">lastCircle</c:if>">
                                                            <div class="ctrlHolder">
                                                                <label class="required" style="display:inline">
                                                                <spring:message code="pratica.titolo.procedimento"/>
                                                            </label>
                                                            <a class="nascondiutente box-unlink" data-holder="${endoprocedimento.idProcEnte}">
                                                                <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
                                                                <spring:message code="pratica.button.scollega.procedimento"/>
                                                            </a>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <div class="field big">${endoprocedimento.procedimento.descrizione}</div>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label class="required">
                                                                <spring:message code="pratica.titolo.termini"/>
                                                            </label>
                                                            <div class="field">${endoprocedimento.procedimento.termini}</div>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label class="required">
                                                                <spring:message code="pratica.titolo.ente"/>
                                                            </label>
                                                            <div class="field">${endoprocedimento.ente.descrizione}</div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </table>
                                </div>
                                <c:if test="${procedimentiSize > 1}">
                                    <div class="controllo_destra">
                                        <div class="controller_box">
                                            <div class="box_right" onclick="item_animate('<c:out value="${procedimentiSize}"></c:out>', '#procedimenti_box', '+=277px')"></div>
                                            </div>
                                        </div>
                                </c:if>
                                <div class="clear"></div>
                            </div>
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
                    <c:if test="${praticaDettaglio.notifica != null}">
                        <div class="frame <c:if test="${currentTab=='frame6'}">active</c:if>" id="frame6">
                            <tiles:insertAttribute name="notifica" />
                        </div>
                    </c:if>
                    <div class="frame timelineeventi <c:if test="${currentTab=='frame7'}">active</c:if>" id="frame7">
                        <tiles:insertAttribute name="eventi" />
                    </div>
                </div>
            </div>


            <form class="uniForm inlineLabels" method="post" action="${path}/pratiche/nuove/apertura/crea.htm">
                <div class="buttonHolder">
                    <div style="float:left;">
                    	 <c:choose>
							 <c:when test = "${fromGestioneEventi}">
								 <a class="secondaryAction" href="${path}/pratiche/gestisci.htm?back=true">&larr; Indietro</a>
							 </c:when>
							 <c:otherwise>
								 <a class="secondaryAction" href="${path}/pratiche/nuove/apertura.htm">&larr; Indietro</a>
							</c:otherwise>
						</c:choose>
                                        
                    </div>
                    <div style="float:right">
                        <input type="hidden" name="id_pratica" id="id_pratica" value="${pratica.idPratica}"/>
                        <input type="hidden" name="idPratica" id="idPratica" value="${pratica.idPratica}"/>
                        <input type="hidden" name="id_evento" id="id_evento" value="${idEvento}"/>
                        <input type="hidden" name="idEvento" id="id_evento" value="${idEvento}"/>

                        <button value="Conferma" class="nascondiutente primaryAction" name="submit" type="submit"><spring:message code="pratica.conferma"/></button>
                    </div>
                    <div class="clear"></div>

                </div>
            </form>

        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</div>

<div class="hidden">
    <div id="listaPraticaOrigine">
        <h2 style="text-align: center"><spring:message code="pratiche.elenco.gestione"/></h2>
        <tiles:insertAttribute name="ricerca" />
        <div class="tableContainer">
            <table id="list"><tr><td/></tr></table> 
            <div id="pager"></div> 

        </div>
        <div class="hidden" id="button">
            <form action="${path}/pratiche/dettaglio.htm" method="post" class="selezionaPratica">
                <input type="hidden" name="id_pratica" value="_ID_">
                <button type="submit" class="selezionaPratica ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                    <spring:message code="gestione.button.gestisci"/>
                </button>
            </form>
        </div>
    </div>
</div>

<div class="hidden collega-procedimento">
    <div class="ctrlHolder">
        <select id="endoprocedimentiSelezionati" name="endoprocedimentiSelezionati" maxlength="255" data-placeholder="<spring:message code="protocollo.dettaglio.endoprocedimento"/>" class="textInput required chosen-select" >
            <option value=""></option>
            <c:forEach items="${pratica.ente.endoProcedimentiEntiAttivi}" var="endoprocedimentoEnte" begin="0">
                <option value="${endoprocedimentoEnte.idProcEnte}" >${endoprocedimentoEnte.procedimento.descrizione} (${endoprocedimentoEnte.ente.descrizione})</option>
            </c:forEach>
        </select>
    </div>
</div>


<script id="template-procedimento" type="text/x-handlebars-template">
    <td id="endoprocedimento_{{idProcedimentoEnte}}">
    <div class="circle ">
    <div class="ctrlHolder">
    <label class="required" style="display:inline">
    <spring:message code="pratica.titolo.procedimento"/>
    </label>
    <a class="box-unlink" data-holder="{{idProcedimentoEnte}}">
    <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
    Scollega
    </a>
    </div>
    <div class="ctrlHolder">
    <div class="field big">{{titoloProcedimento}}</div>
    </div>
    <div class="ctrlHolder">
    <label class="required">Termini</label>
    <div class="field">{{terminiProcedimento}}</div>
    </div>
    <div class="ctrlHolder">
    <label class="required">Ente</label>
    <div class="field">{{enteProcedimento}}</div>
    </div>
    </div>
    </td>
</script>

<script>
    $(function () {
        if (abilitazione === false) {
            $('.nascondiutente').each(function (i, obj) {
                $(this).detach();
            });
        }
    });
</script>

<tiles:insertAttribute name="confrontaAnagrafiche" />