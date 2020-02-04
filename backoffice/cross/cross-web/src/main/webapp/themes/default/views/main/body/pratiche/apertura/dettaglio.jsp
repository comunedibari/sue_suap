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
</script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/pratica.apertura.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/confronta.anagrafica.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/confronta.anagrafica.functions.js"/>"></script>

<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<tiles:insertAttribute name="operazioneRiuscita" />
<div class="uniForm ">
    <div class="content_sidebar">	
        <div class="sidebar_left">
            <h3><spring:message code="pratica.comunicazione.dettaglio.identificativo"/> <strong>${pratica.identificativoPratica}</strong></h3>
            <p><strong>In carico a </strong> ${pratica.idUtente.cognome} ${pratica.idUtente.nome}</p>
            <div class="sidebar_elemento">
                <c:choose>
                    <c:when test="${not empty praticaDettaglio.riepilogoPratica && not empty praticaDettaglio.riepilogoPratica.idAllegato}">
                        <p>
                            <a href="${path}/download.htm?id_file=${praticaDettaglio.riepilogoPratica.idAllegato}" class="scarica" target="_blank">
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
                    <li class="<c:if test="${empty currentTab || currentTab == 'frame0'}">active</c:if>"><a href="#frame0">Copertina</a></li>
                    <li class="<c:if test="${currentTab=='frame1'}">active</c:if>"><a href="#frame1">Anagrafiche</a></li>
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
                    <div class="frame copertina <c:if test="${empty currentTab || currentTab == 'frame0'}">active</c:if>" id="frame0">         
                            <div class="ctrlHolder">
                                <label for="pratica_identificativo" class="required"><spring:message code="pratica.copertina.identificativo.label"/></label>
                            <a href="#" id="pratica_identificativo" class="x-editable-text" name="pratica_identificativo">${pratica.identificativoPratica}</a>
                        </div>
                        <div class="ctrlHolder">
                            <label for="pratica_oggetto" class="required"><spring:message code="pratica.copertina.oggetto.label"/></label>
                            <a href="#" id="pratica_oggetto" class="x-editable-text" name="pratica_oggetto">${pratica.oggettoPratica}</a>
                        </div>
                        <div class="ctrlHolder">
                            <label for="pratica_procedimento" class="required"><spring:message code="pratica.copertina.procedimento.label"/></label>
                            <a href="#" id="pratica_procedimento" class="" name="pratica_procedimento">${pratica.procedimento.descrizione}</a>
                        </div>
                        <div class="ctrlHolder">
        					<label for="pratica_processo" class="required">Processo</label>
        					<a href="#" id="pratica_processo" class="" name="pratica_processo">${pratica.idProcesso.desProcesso}</a>
    					</div>
                        <div class="ctrlHolder">
                            <label for="pratica_ricezione_data" class="required"><spring:message code="pratica.copertina.ricezione.data.label"/></label>
                            <a href="#" id="pratica_ricezione_data" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="pratica_ricezione_data"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></a>
                        </div>
<!--                         <div class="ctrlHolder pratica_protocollo_segnatura_container"> -->
						 <div class="ctrlHolder">
                            <label for="pratica_protocollo_segnatura" class="required"><spring:message code="pratica.copertina.protocollo.segnatura.label"/></label>
                            <a href="#" id="pratica_protocollo_segnatura" class="x-editable-text" name="pratica_protocollo_segnatura">${pratica.protocollo.segnatura}</a>
                        </div>
                        <div class="ctrlHolder">
                            <label for="pratica_protocollo_data" class="required"><spring:message code="pratica.copertina.protocollo.data.label"/></label>
                            <a href="#" id="pratica_protocollo_data" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="pratica_protocollo_data"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.protocollo.dataProtocollazione}" /></a>
                        </div>
                        <div class="ctrlHolder">
                            <label for="pratica_stato" class="required"><spring:message code="pratica.copertina.stato.label"/></label>
                            <a href="#" id="pratica_stato" class="" name="pratica_stato" data-type="select">${pratica.idStatoPratica.descrizione}</a>
                        </div>
                        <div class="ctrlHolder <c:if test="${pratica.idStatoPratica.grpStatoPratica != 'C'}">hidden</c:if>" id="pratica_chiusura_data_container">
                            <label for="pratica_chiusura_data" class="required"><spring:message code="pratica.copertina.chiusura.data.label"/></label>
                            <a href="#" id="pratica_chiusura_data" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="pratica_chiusura_data"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataChiusura}" /></a>
                        </div>
                        <%--
                                                <div class="ctrlHolder">
                                                    <label for="pratica_responsabile_istruttoria" class="required"><spring:message code="pratica.copertina.responsabile.istruttoria.label"/></label>
                                                    <a href="#" id="pratica_responsabile_istruttoria" class="x-editable-text" name="pratica_responsabile_istruttoria">${praticaDettaglio.responsabileProcedimento}</a>
                                                </div>
                        --%>
                        <div class="ctrlHolder">
                            <label for="pratica_responsabile_procedimento" class="required"><spring:message code="pratica.copertina.responsabile.procedimento.label"/></label>
                            <a href="#" id="pratica_responsabile_procedimento" class="x-editable-text" name="pratica_responsabile_procedimento">${pratica.responsabileProcedimento}</a>
                        </div>
                        <div class="ctrlHolder">
                            <label for="pratica_comune" class="required"><spring:message code="pratica.copertina.comune.label"/></label>
                            <a href="#" id="pratica_comune" name="pratica_comune">${pratica.idComune.descrizione}</a>
                        </div>
                    </div>
                    <div class="frame <c:if test="${currentTab=='frame1'}">active</c:if>" id="frame1">
                            <div class="nascondiutente anagrafiche_header">
                                <a href="${path}/pratica/dettaglio/collegaAnagrafica.htm?daPraticaNuova=si">
                                <spring:message code="pratica.comunicazione.collega.anagrafica"/>
                                <img src="${path}/themes/default/images/icons/add.png" alt="<spring:message code="pratica.comunicazione.collega.anagrafica"/>" title="<spring:message code="pratica.comunicazione.collega.anagrafica"/>">
                            </a>
                        </div>
                        <!--<button  id="ricercaPraticaOrigine" class="cerca_lente ui-button">Ricerca pratica origine</button>-->
                        <div id="anagraficaContainer">
                            <!-- Cont div -->
                            <c:set var="anagraficheSize" value="${fn:length(praticaDettaglio.anagraficaList)}" scope="page" />
                            <!-- fine cont div --> 
                            <div class="imbox_frame <c:if test="${anagraficheSize > 1}">margine_imbox_frame</c:if>">
                                <c:if test="${anagraficheSize > 1}">
                                    <div class="controllo_sinistra">
                                        <div class="controller_box">
                                            <div class="box_left" onclick="item_animate('<c:out value="${anagraficheSize}"></c:out>', '#anagrafica_box', '-=277px')"></div>
                                            </div>
                                        </div>
                                </c:if>
                                <div id="anagrafica_box">   
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <c:forEach  items="${praticaDettaglio.anagraficaList}" var="anagrafica" >
                                                <td>
                                                    <c:set var="classDiv" value="" />
                                                    <c:set var="confermata" value="" />
                                                    <c:if test="${anagrafica.confermata != null && anagrafica.confermata!=''}">
                                                        <c:set var="classDiv" value="${classDiv} confermataBG" />
                                                        <c:set var="confermata" value="1" />    
                                                    </c:if>
                                                    <div class="circle ${classDiv}" id="anagrafica${anagrafica.counter}" title="${classDiv}">
                                                        <c:set var="classDiv" value="" />
                                                        <div class="ctrlHolder">
                                                            <label class="required">
                                                                <spring:message code="utenti.tipoRuolo"/>
                                                            </label>                                                     
                                                            <c:if test="${!(((anagrafica.desTipoRuolo eq 'Richiedente') && (fn:length(richiedenti) lt 2) ) ||  ( (anagrafica.desTipoRuolo eq 'Beneficiario') && (fn:length(beneficiari) lt 2)))}"> 
                                                                <a style="padding: 10px; width: 75%;" class='nascondiutente scollegalink showdetail' id="scollega${anagrafica.counter}" count="${anagrafica.counter}">
                                                                    <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
                                                                    &nbsp;&nbsp;<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>
                                                                </a>
                                                            </c:if>
                                                            <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.desTipoRuolo}">
                                                        </div>
                                                        <c:if test="${anagrafica.tipoAnagrafica == 'F'}">
                                                            <div class="ctrlHolder">
                                                                <label class="required">
                                                                    <spring:message code="utenti.nome"/>
                                                                </label>
                                                                <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.nome}">
                                                            </div>
                                                            <div class="ctrlHolder">
                                                                <label class="required">
                                                                    <spring:message code="utenti.cognome"/>
                                                                </label>
                                                                <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.cognome}">
                                                            </div>
                                                            <div class="ctrlHolder">
                                                                <label class="required">
                                                                    <spring:message code="utenti.codicefiscale"/>
                                                                </label>
                                                                <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.codiceFiscale}">
                                                            </div>
                                                            <c:if test="${anagrafica.flgIndividuale == 'S'}">
                                                                <div class="ctrlHolder">
                                                                    <label class="required">
                                                                        <spring:message code="utenti.denominazione"/>
                                                                    </label>
                                                                    <textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.denominazione}</textarea>
                                                                </div>
                                                                <div class="ctrlHolder">
                                                                    <label class="required">
                                                                        <spring:message code="utenti.piva"/>
                                                                    </label>
                                                                    <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.partitaIva}">
                                                                </div>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${anagrafica.tipoAnagrafica == 'G'}">
                                                            <div class="ctrlHolder">
                                                                <label class="required">
                                                                    <spring:message code="utenti.denominazione"/>
                                                                </label>
                                                                <textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.denominazione}</textarea>
                                                            </div>
                                                            <div class="ctrlHolder">
                                                                <label class="required">
                                                                    <spring:message code="utenti.piva"/>
                                                                </label>
                                                                <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.partitaIva}">
                                                            </div>
                                                        </c:if>
                                                        <div class="showdettaglio">
                                                            <c:if test='${anagrafica.daRubrica != "S"}'>
                                                                <div class="anagrafica_confronta confronta ui-button">
                                                                    &nbsp;&nbsp;<spring:message code="pratica.anagrafica.conferma"/>
                                                                    <input type="hidden" class="confermata" value="${confermata}" name="confermata"/>
                                                                    <input type="hidden" class="counter" value="${anagrafica.counter}" name="counter"/>
                                                                    <input type="hidden" name="idTipoRuolo" value="${anagrafica.idTipoRuolo}"/>
                                                                    <input type="hidden" name="idAnagrafica" value="${anagrafica.idAnagrafica}"/>
                                                                    <input type="hidden" name="desTipoRuolo" value="${anagrafica.desTipoRuolo}"/>
                                                                    <input type="hidden" name="idPratica" value="${pratica.idPratica}"/>
                                                                    <input type="hidden" name="partitaIva" value="${anagrafica.partitaIva}"/>
                                                                    <input type="hidden" name="codiceFiscale" value="${anagrafica.codiceFiscale}"/>
                                                                    <input type="hidden" name="tipoAnagrafica" value="${anagrafica.tipoAnagrafica}"/>
                                                                    <input type="hidden" name="varianteAnagrafica" value="${anagrafica.varianteAnagrafica}"/>  
                                                                </div>                                        
                                                                <div style='display:none' id="scollega${anagrafica.counter}button"  class="cancella elimina ui-button" title="<spring:message code="pratica.anagrafica.elimina"/>">
                                                                    &nbsp;&nbsp;<spring:message code="pratica.anagrafica.scollega"/>
                                                                    <input type="hidden" class="counter" value="${anagrafica.counter}" name="counter"/>
                                                                    <input type="hidden" value="${anagrafica.idAnagrafica}" name="id"/>
                                                                    <input type="hidden" value="${anagrafica.idTipoRuolo}" name="ruolo"/>
                                                                    <input type="hidden" value="${anagrafica.collegata }" name="collegata"/>
                                                                </div>
                                                            </c:if>
                                                            <c:if test='${anagrafica.daRubrica == "S"}'>
                                                                <script>
                                                                    $(function () {
                                                                        $('#dettaglio_${anagrafica.counter}').click(function (e) {
                                                                            $.ajax({
                                                                                type: 'POST',
                                                                                url: '${path}/pratica/comunicazione/dettaglio/anagrafica.htm',
                                                                                data: {id_anagrafica: '${anagrafica.idAnagrafica}'}
                                                                            }).done(function (data) {
                                                                                var wHeight = $(window).height() * 0.8;
                                                                                $('.dettaglioAnagraficaContainer').empty();
                                                                                $('#anagrafica_detail_${anagrafica.counter}').html(data);
                                                                                $('#anagrafica_detail_${anagrafica.counter}').dialog({
                                                                                    title: 'Dettaglio anagrafica',
                                                                                    modal: true,
                                                                                    height: wHeight,
                                                                                    width: '50%',
                                                                                    dialogClass: 'cross_modal',
                                                                                    buttons: {
                                                                                        OK: function () {
                                                                                            $(this).dialog("close");
                                                                                        }
                                                                                    }
                                                                                });
                                                                                return false;
                                                                            });
                                                                        });
                                                                    });
                                                                </script>
                                                                <div id="<c:out value="anagrafica_detail_${anagrafica.counter}" />" class="modal-content">
                                                                </div>    
                                                                <div class="anagrafica_dettaglio_apertura ui-button"   id="dettaglio_${anagrafica.counter}">
                                                                    <spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>
                                                                </div>
                                                                <div style='display:none' id="scollega${anagrafica.counter}button"  class="cancella elimina ui-button" title="<spring:message code="pratica.anagrafica.elimina"/>">
                                                                    &nbsp;&nbsp;<spring:message code="pratica.anagrafica.scollega"/>
                                                                    <input type="hidden" class="counter" value="${anagrafica.counter}" name="counter"/>
                                                                    <input type="hidden" value="${anagrafica.idTipoRuolo}" name="ruolo"/>
                                                                    <input type="hidden" value="${anagrafica.idAnagrafica}" name="id"/>
                                                                    <input type="hidden" value="${anagrafica.idTipoRuolo}" name="ruolo"/>
                                                                    <input type="hidden" name="idTipoRuolo" value="${anagrafica.idTipoRuolo}"/>
                                                                    <input type="hidden" name="desTipoRuolo" value="${anagrafica.desTipoRuolo}"/>
                                                                    <input type="hidden" name="codiceFiscale" value="${anagrafica.codiceFiscale}"/>
                                                                    <input type="hidden" value="${anagrafica.collegata }" name="collegata"/>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </table>
                                </div>
                                <c:if test="${anagraficheSize > 1}">
                                    <div class="controllo_destra">
                                        <div class="controller_box">
                                            <div class="box_right" onclick="item_animate('<c:out value="${anagraficheSize}"></c:out>', '#anagrafica_box', '+=277px')"></div>
                                            </div>
                                        </div>
                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </div>
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
								 <a class="secondaryAction" href="${path}/pratiche/gestisci.htm">&larr; Indietro</a>
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
<script>
    $(function addButton() {
        $('.scollegalink').each(function () {
            var count = $(this).attr("count");
            $(this).click(function () {
                $('#scollega' + count + 'button').click();
            });
        });
    })
</script>

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