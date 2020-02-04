<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
    td > .action {
        text-align: center;
        margin-right:15px;
    }    
</style>
<c:if test="${ritornaAidPratica!=null}">    
    <script>
        $(document).ready(function() {
            //^^CS AGGIUNTA gestione redirect pratica origine
            $("#indietroAidPratica").click(function() {
                $("form.page").attr("action", "<%=path%>/pratiche/comunicazione/dettaglio.htm");
            });
        });
    </script>
</c:if>
<div class="uniForm uniform2">
    <h2 style="text-align: center">
        <spring:message code="pratica.comunicazione.dettaglio"/>
    </h2>

    <!-- Dettaglio pratica -->
    <div class="inlineLabels">
        <fieldset class="fieldsetComunicazione">
            <legend><spring:message code="pratica.comunicazione.dettaglio.datipratica"/></legend>
            <div>
                <div class="ctrlHolder dettaglio_liv_0">
                    <label class="required">
                        <spring:message code="pratica.comunicazione.dettaglio.oggetto"/>
                    </label>
                    <span class="value">${dettaglio.oggetto}</span>
                </div>
                <div class="ctrlHolder">
                    <label class="required">
                        <spring:message code="pratica.comunicazione.dettaglio.identificativo"/> 
                    </label>
                    <span class="value">${dettaglio.identificativoPratica}</span>
                </div>
                <div class="ctrlHolder">
                    <label class="required">
                        <spring:message code="pratica.comunicazione.dettaglio.protocollo"/>
                    </label>
                    <span class="value">${dettaglio.numeroProtocollo}</span>
                </div>
                <div class="ctrlHolder">
                    <label class="required">
                        <spring:message code="pratica.comunicazione.dettaglio.dataricezione"/>
                    </label>
                    <span class="value"><fmt:formatDate pattern="dd/MM/yyyy" value="${dettaglio.dataRicezione}" /></span>
                </div>
                <div class="ctrlHolder">
                    <label class="required">
                        <spring:message code="pratica.comunicazione.dettaglio.statopratica"/>
                    </label>
                    <span class="value">${dettaglio.statoPratica}</span>
                </div>
                <div class="ctrlHolder">
                    <label class="required">
                        <spring:message code="pratica.comunicazione.evento.pratica.download"/>
                    </label>
                    <c:choose>
                        <c:when test="${not empty pratica.idModello}">
                            <span class="value">
                                <img src="<%=path%>/themes/default/images/icons/apri.png" alt="<spring:message code="pratica.comunicazione.evento.pratica.download"/>" title="<spring:message code="pratica.comunicazione.evento.pratica.download"/>">
                                <a href="<%=path%>/download/pratica.htm" target="_blank">
                                    <spring:message code="pratica.comunicazione.evento.pratica.download"/>
                                </a>
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </fieldset>

        <!-- Anagrafiche -->
        <fieldset class="fieldsetComunicazione">
            <legend><spring:message code="pratica.comunicazione.anagrafica.title"/></legend>
            <!-- preload the images -->
            <div style='display:none'>
                <img src="<%=path%>/themes/default/images/icons/x.png" alt='' />
            </div>
            <div>
                <c:forEach items="${dettaglio.anagrafiche}" begin="0" var="anagrafica" varStatus="status">
                    <c:choose>
                        <c:when test="${anagrafica.tipoAnagrafica == 'G'}">
                            <script>
                                $(document).ready(function() {
                                    // Load dialog on page load
                                    //$('#basic-modal-content').modal();

                                    // Load dialog on click
                                    $('#<c:out value="anagrafica_${anagrafica.idAnagrafica}"/> .showdetail').click(function(e) {
                                        $.ajax({
                                            type: 'POST',
                                            url: '<%=path%>/pratica/comunicazione/dettaglio/anagrafica.htm',
                                            data: {id_anagrafica: '${anagrafica.idAnagrafica}'}
                                        }).done(function(data) {
                                            var wHeight = $(window).height() * 0.8;
                                            $('.dettaglioAnagraficaContainer').empty();
                                            $('#anagrafica_detail_${anagrafica.idAnagrafica}').html(data);
                                            $('#anagrafica_detail_${anagrafica.idAnagrafica}').dialog({
                                                title: 'Dettaglio anagrafica',
                                                modal: true,
                                                height: wHeight,
                                                width: '50%',
                                                dialogClass: 'cross_modal'/*,
                                                 create: function(event, ui) {
                                                 },
                                                 open: function(event, ui) {
                                                 $('html').css('overflow', 'hidden');
                                                 },
                                                 close: function(event, ui) {
                                                 $('html').css('overflow', 'scroll');
                                                 }*/
                                            });
                                            return false;
                                        });
                                    });
                                });
                            </script>

                            <div class="circle <c:if test="${status.last}">lastCircle</c:if>" id="<c:out value="anagrafica_${anagrafica.idAnagrafica}" />" >
                                    <div class="ctrlHolder">
                                        <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.ruolo"/>
                                    </label>
                                    <span class="value">${anagrafica.ruolo}</span>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.denominazione"/>
                                    </label>
                                    <span class="value">${anagrafica.ragioneSociale}</span>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.partitaiva"/>
                                    </label>
                                    <span class="value">${anagrafica.partitaIVA}</span>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.apridettaglio"/>
                                    </label>
                                    <a href="#" class='showdetail'>
                                        <img src="<%=path%>/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>">
                                        &nbsp;&nbsp;<spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>
                                    </a>
                                </div>
                                <div id="<c:out value="anagrafica_detail_${anagrafica.idAnagrafica}" />" class="modal-content">
                                </div>   
                            </div>

                        </c:when>
                        <c:otherwise>
                            <script>
                                $(document).ready(function() {
                                    // Load dialog on page load
                                    //$('#basic-modal-content').modal();

                                    // Load dialog on click
                                    $('#<c:out value="anagrafica_${anagrafica.idAnagrafica}"/> .showdetail').click(function(e) {
                                        $.ajax({
                                            type: 'POST',
                                            url: '<%=path%>/pratica/comunicazione/dettaglio/anagrafica.htm',
                                            data: {id_anagrafica: '${anagrafica.idAnagrafica}'}
                                        }).done(function(data) {
                                            var wHeight = $(window).height() * 0.8;
                                            $('.dettaglioAnagraficaContainer').empty();
                                            $('#anagrafica_detail_${anagrafica.idAnagrafica}').html(data);
                                            $('#anagrafica_detail_${anagrafica.idAnagrafica}').dialog({
                                                title: 'Dettaglio anagrafica',
                                                modal: true,
                                                height: wHeight,
                                                width: '50%',
                                                dialogClass: 'cross_modal'/*,
                                                 create: function(event, ui) {
                                                 },
                                                 open: function(event, ui) {
                                                 $('html').css('overflow', 'hidden');
                                                 },
                                                 close: function(event, ui) {
                                                 $('html').css('overflow', 'scroll');
                                                 }*/
                                            });
                                            return false;
                                        });
                                    });
                                });
                            </script>
                            <div class="circle <c:if test="${status.last}">lastCircle</c:if>" id="<c:out value="anagrafica_${anagrafica.idAnagrafica}" />" >
                                    <div class="ctrlHolder">
                                        <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.ruolo"/>
                                    </label>
                                    <span class="value">${anagrafica.ruolo}</span>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.nome"/>
                                    </label>
                                    <span class="value">${anagrafica.nome}</span>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.cognome"/>
                                    </label>
                                    <span class="value">${anagrafica.cognome}</span>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.codicefiscale"/>
                                    </label>
                                    <span class="value">${anagrafica.codiceFiscale}</span>
                                </div>
                                <c:if test="${anagrafica.varianteAnagrafica == 'P' || anagrafica.varianteAnagrafica == 'I' }">
                                    <div class="ctrlHolder">
                                        <label class="required">
                                            <spring:message code="pratica.comunicazione.anagrafica.partitaiva"/>
                                        </label>
                                        <span class="value">${anagrafica.partitaIVA}</span>
                                    </div>
                                </c:if>
                                <div class="ctrlHolder">
                                    <label class="required">
                                        <spring:message code="pratica.comunicazione.anagrafica.apridettaglio"/>
                                    </label>
                                    <a href="#" class='showdetail'>
                                        <img src="<%=path%>/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>">
                                        &nbsp;&nbsp;<spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>
                                    </a>
                                </div>
                                <div id="<c:out value="anagrafica_detail_${anagrafica.idAnagrafica}" />" class="modal-content">
                                </div>     
                            </div>

                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </fieldset>




        <!-- Procedimenti -->
        <fieldset class="fieldsetComunicazione">
            <legend><spring:message code="pratica.comunicazione.dettaglio.procedimenti.title"/></legend>
            <div>
                <c:forEach items="${dettaglio.procedimenti}" begin="0"  var="procedimento" varStatus="loop">
                    <div class="circle <c:if test="${loop.last}">lastCircle</c:if>">
                            <h4 class=".titoloSecondoLivello">
                            ${procedimento.desProcedimento}
                        </h4>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.dettaglio.procedimenti.termini"/>
                            </label>
                            <span class="value">${procedimento.termini}</span>
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.dettaglio.procedimenti.entedestinatario"/>
                            </label>
                            <span class="value">${procedimento.desEnteDestinatario}</span>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </fieldset>

        <!-- Dati catastali -->
        <c:if test="${!empty dettaglio.datiCatastali}">
            <fieldset class="fieldsetComunicazione">
                <legend><spring:message code="pratica.comunicazione.dettaglio.immobile.title"/></legend>
                <div>
                    <c:forEach items="${dettaglio.datiCatastali}" begin="0"  var="immobile" varStatus="loop">
                        <div class="circle <c:if test="${loop.last}">lastCircle</c:if>">
                            <h4 class=".titoloSecondoLivello"><spring:message code="pratica.comunicazione.dettaglio.immobile.tipocatasto"/>: ${immobile.desSistemaCatastale}
                            </h4>
                            <div class="ctrlHolder">
                                <label class="required">
                                    <spring:message code="pratica.comunicazione.dettaglio.immobile.comune"/>
                                </label>
                                <span class="value">${immobile.desComune}</span>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required">
                                    <spring:message code="pratica.comunicazione.dettaglio.immobile.indirizzo"/>
                                </label>
                                <span class="value">${immobile.indirizzo}&nbsp;${immobile.civico}</span>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required">
                                    <spring:message code="pratica.comunicazione.dettaglio.immobile.foglio"/>
                                </label>
                                <span class="value">${immobile.foglio}</span>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required">
                                    <spring:message code="pratica.comunicazione.dettaglio.immobile.mappale"/>
                                </label>
                                <span class="value">${immobile.mappale}</span>
                            </div>
                            <c:if test="${! empty immobile.latitudine}"> 
                                <a href="http://geonavsct.partout.it/pub/geonavsct/index.html?funzione=ZOOMPUNTO&cache=CTRN&scala=9&sistema=UTM&datum=ED50&puntox=${immobile.latitudine}&puntoy=${immobile.longitudine}" target="_blank">
                                    <img src="<%=path%>/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>">
                                    <spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>
                                </a>
                            </c:if> 
                        </div>
                    </c:forEach>
                </div>
            </fieldset>
        </c:if>
    </div>

    <!-- Scadenze -->
    <div class="inlineLabels">
        <h4 class="tabellaDettaglioTitolo">
            <spring:message code="pratica.comunicazione.dettaglio.scadenze.title"/>
        </h4>
        <table cellspacing="0" cellpadding="0" class="master">
            <tr>
                <th><spring:message code="pratica.comunicazione.dettaglio.scadenze.descrizione"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.scadenze.stato"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.scadenze.datascadenza"/></th>
            </tr>
            <c:if test="${!empty dettaglio.scadenze}">
                <c:forEach items="${dettaglio.scadenze}" var="scadenza" begin="0">
                    <tr>
                        <td>${scadenza.descrizione}</td>
                        <td>${scadenza.statoScadenza}</td>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${scadenza.dataFineScadenza}" /></td>       
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty dettaglio.scadenze}">
                <tr>
                    <td colspan="3"><spring:message code="pratica.comunicazione.dettaglio.scadenze.noresult"/></td>
                </tr>
            </c:if>
        </table>
    </div>

    <!-- Allegati -->
    <div class="inlineLabels">
        <h4 class="tabellaDettaglioTitolo">
            <spring:message code="pratica.comunicazione.dettaglio.allegati.title"/>
        </h4>
        <table cellspacing="0" cellpadding="0" class="master">
            <tr>
                <th><spring:message code="pratica.comunicazione.dettaglio.allegati.descrizione"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.allegati.nomefile"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.allegati.download"/></th>
            </tr>
            <c:if test="${!empty dettaglio.allegati}">
                <c:forEach items="${dettaglio.allegati}" var="allegato" begin="0">
                    <tr>
                        <td>${allegato.descrizione}</td>
                        <td>${allegato.nomeFile}</td>
                        <td>
                            <span style="text-align: center;">
                                <img src="<%=path%>/themes/default/images/icons/apri.png" alt="<spring:message code="pratica.comunicazione.evento.file.download"/>" title="<spring:message code="pratica.comunicazione.evento.file.download"/>">
                                <a href="<%=path%>/download.htm?id_file=${allegato.idAllegato}" target="_blank">
                                    <spring:message code="pratica.comunicazione.evento.file.download"/>
                                </a>
                            </span>
                        </td>             
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty dettaglio.allegati}">
                <tr>
                    <td colspan="4"><spring:message code="pratica.comunicazione.dettaglio.allegati.noresult"/></td>
                </tr>
            </c:if>
        </table>
    </div>

    <!-- Eventi -->
    <div class="inlineLabels">
        <h4 class="tabellaDettaglioTitolo">
            <spring:message code="pratica.comunicazione.dettaglio.evento.title"/>
        </h4>
        <table cellspacing="0" cellpadding="0" class="master">
            <tr>
                <th><spring:message code="pratica.comunicazione.evento.descrizione"/></th>
                <th><spring:message code="pratica.comunicazione.evento.dataevento"/></th>
                <th><spring:message code="pratica.comunicazione.evento.operatore"/></th>
                <th><spring:message code="pratica.comunicazione.evento.visibilitaFront"/></th>
                <th></th>
            </tr>
            <c:if test="${!empty dettaglio.eventi}">
                <c:forEach items="${dettaglio.eventi}" var="evento" begin="0">
                    <tr>
                        <td>${evento.descrizione}</td>
                        <td>${evento.dataEvento}</td>
                        <td>${evento.operatore}</td>
                        <td>
                            <c:choose>
                                <c:when test="${evento.pubblicazionePortale==null||evento.pubblicazionePortale=='N'}">
                                    NO
                                </c:when>
                                <c:otherwise>
                                    SI
                                </c:otherwise>                            
                            </c:choose>
                        </td>

                        <td>
                            <form action="<%=path%>/pratica/comunicazione/dettaglio_evento.htm" class="uniForm inlineLabels action" method="post">
                                <input type="hidden" name="id_pratica_evento" value="${evento.idPraticaEvento}">
                                <input type="submit" name="submit" value="<spring:message code="pratica.comunicazione.evento.dettaglio"/>" class="button ui-state-default ui-corner-all"/>
                            </form>
                        </td>             
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty dettaglio.eventi}">
                <tr>
                    <td colspan="3"><spring:message code="pratica.comunicazione.evento.noresult"/></td>
                </tr>
            </c:if>
        </table>
    </div>

    <script>
        $("document").ready(function() {
            $(".dettaglioNota").click(function() {
                var id = $(this).attr("id");
                id = "testoNota" + id;
                $("#" + id).dialog(
                        {
                            width: 500,
                            modal: true,
                            title: "Dettaglio nota:",
                            dialogClass: "uniForm portlet"/*,
                             beforeClose: function( event, ui ) {
                             $('html').css('overflow', 'scroll');
                             },
                             open: function(event, ui) {
                             $('html').css('overflow', 'hidden');
                             }*/,
                            buttons: {
                                Ok: function() {
                                    $("#" + id).dialog('close');
                                    //$('html').css('overflow', 'scroll');
                                }
                            }
                        });
            });
            $(".insersisciNota").click(function() {
                var form = $("<form>");
                form.attr({
                    name: 'nota',
                    method: 'post',
                    action: '<%=path%>/pratica/note/aggiungi/ajax.htm'
                });
                form.append($('<input>',
                        {
                            'name': 'idPratica',
                            'value': ${dettaglio.idPratica},
                            'type': 'hidden'
                        }));
                form.append($('<input>',
                        {
                            'name': 'id_pratica',
                            'value': ${dettaglio.idPratica},
                            'type': 'hidden'
                        }));
                form.append($('.inputsNota'));
                form.dialog(
                        {
                            modal: true,
                            title: "Aggiungi nota:",
                            width: 500,
                            dialogClass: "",
                            buttons: {
                                Ok: function() {
                                    $.post('<%=path%>/pratica/note/aggiungi/ajax.htm', form.serialize(),
                                            function(data) {

                                                if (data.errors != null)
                                                {
                                                    alert(data.errors);
                                                }
                                                else
                                                {
                                                    form.dialog("destroy");
                                                    form.attr("action", document.URL);
                                                    form.appendTo('body').submit();
                                                }
                                            }, 'json');
                                },
                                Annulla: function() {
                                    $(this).dialog('close');
                                }
                            }
                        });
            });
        });
    </script>
    <!-- Note -->
    <fieldset class="fieldsetComunicazione">
        <legend><spring:message code="pratica.comunicazione.dettaglio.note.title"/></legend>
        <h4 class="button insersisciNota dettaglioNota" style="width:90px">
            <img title="Scarica file" alt="Scarica file" src="<%=path%>/themes/default/images/icons/add.png">
            <spring:message code="pratica.comunicazione.dettaglio.note.inserisci"/>
        </h4>
        <table cellspacing="0" cellpadding="0" class="master">
            <tr>
                <th><spring:message code="pratica.comunicazione.dettaglio.note.Data"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.note.Utente"/></th>
                <th><spring:message code="pratica.comunicazione.dettaglio.note.Testo"/></th>
                <th>&nbsp;</th>
            </tr>
            <c:if test="${!empty dettaglio.notePratica}">
                <c:forEach items="${dettaglio.notePratica}" var="nota" begin="0">
                    <tr>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${nota.dataInserimento}" /></td>       
                        <td>${nota.desUtente}</td>
                        <td>${nota.testoBreve}</td>
                        <td>
                            <div class="action">
                                <input type="button" value="Dettaglio" class="button dettaglioNota ui-state-default ui-corner-all" id="${nota.idNota}"/>
                                <div class="hidden">
                                    <div class="ctrlHolder dettaglioAnagraficaContainer">
                                        <fieldset id="testoNota${nota.idNota}" class="fieldsetComunicazione">
                                            <legend>Email</legend>
                                            <div>
                                                <div class="detailKey">
                                                    Data
                                                </div>
                                                <div class="detailValue">
                                                    <span class="value"><fmt:formatDate pattern="dd/MM/yyyy" value="${nota.dataInserimento}" /></span>
                                                </div>

                                                <div class="detailKey">
                                                    Utente
                                                </div>
                                                <div class="detailValue"><span class="value">${nota.desUtente}</span></div>

                                                <div class="detailKey">
                                                    Dettaglio:
                                                </div>
                                                <div >
                                                    ${nota.testo}  
                                                </div>
                                                <div class="detailValue">01425360938&nbsp;</div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>

                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty dettaglio.notePratica}">
                <tr>
                    <td colspan="4"><spring:message code="pratica.comunicazione.dettaglio.note.noresult"/></td>
                </tr>
            </c:if>
        </table>
        <div class="hidden">
            <div class="inputsNota">
                <div>
                    <lable>Scrivere di seguito la nota</lable>
                </div>
                <div>
                    <textarea name="testo" value="" style="width: 97%; height: 250px;"></textarea>
                </div>
            </div>

        </div>
    </fieldset>
    <form action="<%=path%>/pratica/comunicazione/index.htm" class="uniForm inlineLabels page" method="post">
        <div class="buttonHolder">
            <%-- ^^CS AGGIUNTA dopo aver premuto vai a pratica origine, do il permesso di rtornare indietro --%>
            <c:if test="${ritornaAidPratica!=null}">
                <input type="hidden" id="ritornaAidPratica" name="id_pratica" class="primaryAction" value="${ritornaAidPratica}"/>
                <input type="submit" id="indietroAidPratica" name="submit" class="primaryAction" value="Indietro" style="margin-right:73%"/>
            </c:if>
            <input type="submit" name="submit" class="primaryAction" value="<spring:message code="pratica.button.evento"/>"/>
        </div>
    </form>

</div>
