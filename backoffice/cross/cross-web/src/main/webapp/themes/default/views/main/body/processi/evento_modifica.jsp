<%
    String path = request.getContextPath();
    String url = path + "/processi/eventi/aggiungi/salvaEvento.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
    function elimina() {
        var div = $("<div>");
        div.html("Confermi di voler eliminare questo evento?")
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma eliminazione evento",
                    buttons: {
                        Ok: function () {
                            var form = $("#modificaEventoForm");
                            form.append($('<input>',
                                    {
                                        'name': 'submitaction',
                                        'value': "<spring:message code="processo.button.cancella"/>",
                                        'type': 'hidden'
                                    }));
                            form.submit();
                            $(div).dialog('close');
                        },
                        Annulla: function () {
                            $(this).dialog('close');
                        }
                    }
                });
    }

</script>
<div>
    <tiles:insertAttribute name="body_error" />
    <div class="uniForm ">
            <!--    <h2 style="text-align: center"><spring:message code="evento.aggiungi.title"/>555</h2> -->
        <form:form modelAttribute="evento" action="<%=url%>"  id="modificaEventoForm" method="post" cssClass="uniForm inlineLabels comunicazione">

            <div class="sidebar_auto">
                <div class="page-control" data-role="page-control">
                    <span class="menu-pull"></span> 
                    <div class="menu-pull-bar"></div>
                    <!-- Etichette cartelle -->
                    <ul>
                        <li class="active"><a href="#frame1">Generale</a></li>
                        <li><a href="#frame2">Email</a></li>
                        <li><a href="#frame3">Protocollo</a></li>
                        <li><a href="#frame4"><spring:message code="evento.scadenza.title"/></a></li>
                            <c:if test="${isGestioneAnagraficheEventi}">
                            <li><a href="#frame5"><spring:message code="evento.anagraficheEnti.title"/></a></li>
                            </c:if>
                    </ul>
                    <!-- Contenuto cartelle -->
                    <div class="frames" style="margin-top: 34px !important;">
                        <div class="frame active" id="frame1">

                            <div class="inlineLabels">

                                <form:hidden path="idProcesso" />
                                <form:hidden path="idEvento" />
                                <div class="ctrlHolder">
                                    <label for="codice" class="required"><spring:message code="evento.codice"/></label>
                                    <form:input path="codiceEvento" id="codice" maxlength="255" cssClass="textInput required"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="descrizione" class="required"><spring:message code="evento.descrizione"/></label>
                                    <form:input path="descrizioneEvento" id="codice" maxlength="255" cssClass="textInput required"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="statoPost" class="required"><spring:message code="evento.statoPost"/></label>
                                    <form:select id="statoPost" name="statoPost" path="statoPost" cssClass="select required">
                                        <form:options items="${statoPratica}" itemLabel="descrizione" itemValue="idStatoPratica"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="tipoMittente" class="required"><spring:message code="evento.tipoMittente"/></label>
                                    <form:select id="tipoMittente" name="tipoMittente" path="tipoMittente" cssClass="select required">
                                        <form:option value="" label="" />
                                        <form:options items="${mittenti}" itemLabel="desTipoAttore" itemValue="idTipoAttore"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="tipoDestinatario" class="required"><spring:message code="evento.tipoDestinatario"/></label>
                                    <form:select id="tipoDestinatario" name="tipoDestinatario" path="tipoDestinatario" >
                                        <form:option value="" label="" />
                                        <form:options items="${destinatari}" itemLabel="desTipoAttore" itemValue="idTipoAttore"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="scriptScadenzaEvento" class="required"><spring:message code="evento.scriptScadenzaEvento"/></label>
                                    <form:input path="scriptScadenzaEvento" id="scriptScadenzaEvento" maxlength="255" cssClass="textInput required"/>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="flgVisualizzaProcedimenti" class="required"><spring:message code="evento.visualizza.procedimenti"/></label>
                                    <form:radiobutton path="flgVisualizzaProcedimenti" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgVisualizzaProcedimenti" value="N" /><spring:message code="evento.no"/>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="verso" class="required"><spring:message code="evento.verso"/></label>
                                    <form:select path="verso">
                                        <form:option value="I" label="Ingresso" />
                                        <form:option value="O" label="Uscita"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgPortale"/></label>
                                    <form:radiobutton path="flgPortale" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgPortale" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="forzaChiusuraScadenze" class="required"><spring:message code="evento.forzaChiusuraScadenze"/></label>
                                    <form:radiobutton path="forzaChiusuraScadenze" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="forzaChiusuraScadenze" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>
                            </div>
                        </div>
                        <div class="frame" id="frame2">

                            <div class="inlineLabels">

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgMail"/></label>
                                    <form:radiobutton path="flgMail" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgMail" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgAllegatiEmail"/></label>
                                    <form:radiobutton path="flgAllegatiEmail" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgAllegatiEmail" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="oggettoMail" class="required"><spring:message code="evento.oggettoMail"/></label>
                                    <form:textarea path="oggettoMail" id="oggettoMail" rows="5" cols="40" />
                                </div>

                                <div class="ctrlHolder">
                                    <label for="corpoMail" class="required"><spring:message code="evento.corpoMail"/></label>
                                    <form:textarea path="corpoMail" id="corpoMail" rows="5" cols="50" />
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgDestinatari"/></label>
                                    <form:radiobutton path="flgDestinatari" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgDestinatari" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="flgDestinatariSoloEnti" class="required"><spring:message code="evento.flgDestinatariSoloEnti"/></label>
                                    <form:radiobutton path="flgDestinatariSoloEnti" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgDestinatariSoloEnti" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="maxDestinatari" class="required"><spring:message code="evento.maxDestinatari"/></label>
                                    <form:input path="maxDestinatari" id="maxDestinatari" maxlength="10" cssClass="textInput required"/>
                                </div>

                            </div>
                        </div>
                        <div class="frame" id="frame3">

                            <div class="inlineLabels">

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgProtocollazione"/></label>
                                    <form:radiobutton path="flgProtocollazione" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgProtocollazione" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgRicevuta"/></label>
                                    <form:radiobutton path="flgRicevuta" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgRicevuta" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgFirmato"/></label>
                                    <form:radiobutton path="flgFirmato" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgFirmato" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgApriSottoPratica"/></label>
                                    <form:radiobutton path="flgApriSottoPratica" value="S" /><spring:message code="evento.si"/>&nbsp;&nbsp;&nbsp;
                                    <form:radiobutton path="flgApriSottoPratica" value="N" /><spring:message code="evento.no"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label for="scriptEvento" class="required"><spring:message code="evento.scriptEvento"/></label>
                                    <form:input path="scriptEvento" id="scriptEvento" maxlength="20" cssClass="textInput required"/>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="scriptProtocollo" class="required"><spring:message code="evento.scriptProtocollo"/></label>
                                    <form:input path="scriptProtocollo" id="scriptProtocollo" maxlength="20" cssClass="textInput required"/>
                                </div>

                                <div class="ctrlHolder">
                                    <label for="funzioneApplicativa" class="required"><spring:message code="evento.funzioneApplicativa"/></label>
                                    <form:input path="funzioneApplicativa" id="funzioneApplicativa" maxlength="255" cssClass="textInput required"/>
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>

                                <div class="ctrlHolder">
                                    <label class="required"><spring:message code="evento.flgAutomatico"/></label>
                                    <form:radiobutton path="flgAutomatico" value="S" /><spring:message code="evento.si"/>
                                    <form:radiobutton path="flgAutomatico" value="N" /><spring:message code="evento.no"/>&nbsp;&nbsp;&nbsp;
                                    <p class="formHint"><spring:message code="evento.campo.obbligatorio"/></p>
                                </div>
                            </div>
                        </div>
                        <div class="frame" id="frame4">

                            <div class="inlineLabels">

                                <div class="table-add-link">
                                    <a class="addgenerico" onclick="aggiungiScadenza()" alt="<spring:message code="evento.scadenza.add"/>" title="<spring:message code="evento.scadenza.add"/>">
                                        <spring:message code="evento.scadenza.add"/>

                                    </a>
                                </div>

                                <script>
                                    var tipologiaCombo = '';
                                    <c:forEach items="${tipologiaScadenza}" var="tipoScadenza" begin="0">
                                    tipologiaCombo += '<option value="${tipoScadenza.idAnaScadenza}">${tipoScadenza.desAnaScadenza}</option>';
                                    </c:forEach>

                                    function aggiungiScadenza() {
                                        var labelTipologia = '<spring:message code="evento.scadenza.tipologia"/>';
                                        var labelStatoScadenza = '<spring:message code="evento.scadenza.stato"/>';
                                        var labelTermini = '<spring:message code="evento.scadenza.termini"/>';
                                        var labelScript = '<spring:message code="evento.scadenza.scriptscadenza"/>';
                                        var labelVisualizza = '<spring:message code="evento.scadenza.visualizza"/>';
                                        var labelRimuoviScadenza = '<spring:message code="evento.scadenza.del"/>';
                                        var counter = $('#scadenzeFieldset > div').length;
                                        if (counter == 0) {
                                            $('#scadenzeFieldset').show();
                                        }
                                        var newsection = '<div class="scadenza div_add_dati" id="scadenza_' + counter + '">';
                                        if (counter > 0) {
                                            newsection += '<hr />';
                                        }
                                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="tipologiaScadenza_' + counter + '">' + labelTipologia + '</label>';
                                        newsection += '<select name="scadenze[' + counter + '].idAnaScadenza" id="tipologiaScadenza_' + counter + '">' + tipologiaCombo + '</select></div>';
                                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="statoScadenza_' + counter + '">' + labelStatoScadenza + '</label>';
                                        newsection += '<select name="scadenze[' + counter + '].idStatoScadenza" id="statoScadenza_' + counter + '"><option value="A">Aperta</option><option value="C">Chiusa</option></select></div>';
                                        newsection += '<div class="ctrlHolder"><label for="termini_' + counter + '" class="required">' + labelTermini + '</label>';
                                        newsection += '<input type="text" name="scadenze[' + counter + '].terminiScadenza" id="termini_' + counter + '" maxlength="10" /></div>';
                                        newsection += '<div class="ctrlHolder"><label for="script_' + counter + '" class="required">' + labelScript + '</label>';
                                        newsection += '<input type="text" name="scadenze[' + counter + '].scriptScadenza" id="termini_' + counter + '" maxlength="255" /></div>';
                                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="visualizzaScadenza_' + counter + '">' + labelVisualizza + '</label>';
                                        newsection += '<select name="scadenze[' + counter + '].flgVisualizzaScadenza" id="visualizzaScadenza_' + counter + '"><option value="S">Si</option><option value="N">No</option></select></div>';
                                        newsection += '<div class="table-add-link"><a class="menogenerico" onclick="rimuoviScadenza(\'#scadenza_' + counter + '\')">' + labelRimuoviScadenza;
                                        newsection += '</a></div>';
                                        newsection += '</div>';
                                        $('#scadenzeFieldset').append(newsection);
                                    }

                                    function rimuoviScadenza(sezione) {
                                        $(sezione).remove();
                                        var scadenzeTotali = $('#scadenzeFieldset > div').length;
                                        if (scadenzeTotali == 0) {
                                            $('#scadenzeFieldset').hide();
                                        }
                                    }
                                </script>
                                <fieldset id="scadenzeFieldset"  class="fieldsetzero">
                                    <c:set var="count" value="0" scope="page" />
                                    <c:forEach items="${evento.scadenze}" begin="0" var="scadenze" varStatus="status">
                                        <div class="scadenza" id="scadenza_${count}">
                                            <c:if test="${count > 0}">
                                                <hr />
                                            </c:if>

                                            <div class="ctrlHolder dettaglio_liv_0">
                                                <label class="required" for="tipologiaScadenza"><spring:message code="evento.scadenza.tipologia"/></label>
                                                <form:select id="tipologiaScadenza" path="scadenze[${count}].idAnaScadenza">
                                                    <form:options items="${tipologiaScadenza}" itemLabel="desAnaScadenza" itemValue="idAnaScadenza"/>
                                                </form:select>
                                            </div>

                                            <div class="ctrlHolder dettaglio_liv_0">
                                                <label class="required" for="statoScadenza"><spring:message code="evento.scadenza.stato"/></label>
                                                <form:select path="scadenze[${count}].idStatoScadenza">
                                                    <form:option value="A" label="Aperta" />
                                                    <form:option value="C" label="Chiusa"/>
                                                </form:select>
                                            </div>

                                            <div class="ctrlHolder">
                                                <label for="termini" class="required"><spring:message code="evento.scadenza.termini"/></label>
                                                <form:input path="scadenze[${count}].terminiScadenza" id="termini" maxlength="10" cssClass="textInput required"/>
                                            </div> 

                                            <div class="ctrlHolder">
                                                <label for="script" class="required"><spring:message code="evento.scadenza.scriptscadenza"/></label>
                                                <form:input path="scadenze[${count}].scriptScadenza" id="script" maxlength="255" cssClass="textInput required"/>
                                            </div> 

                                            <div class="ctrlHolder dettaglio_liv_0">
                                                <label class="required" for="visualizzaScadenza"><spring:message code="evento.scadenza.visualizza"/></label>
                                                <form:select path="scadenze[${count}].flgVisualizzaScadenza" id="visualizzaScadenza">
                                                    <form:option value="S" label="Si"/>
                                                    <form:option value="N" label="No"/>
                                                </form:select>
                                            </div>

                                            <div class="table-add-link">
                                                <a class="menogenerico" onclick="rimuoviScadenza('#scadenza_${count}')" alt="<spring:message code="evento.scadenza.del"/>" title="<spring:message code="evento.scadenza.del"/>">
                                                    <spring:message code="evento.scadenza.del"/>

                                                </a>
                                            </div>
                                        </div>
                                        <c:set var="count" value="${count + 1}" scope="page"/>
                                    </c:forEach>
                                </fieldset>
                            </div>
                        </div>
                        <c:if test="${isGestioneAnagraficheEventi}">
                            <div class="frame" id="frame5">
                                <tiles:insertAttribute name="evento_gestisci_anagrafiche" />
                            </div>
                        </c:if>
                    </div>
                    <!-- fine cartelle -->
                    <div class="buttonHolder">
                        <a href="<%=path%>/processi/eventi/lista.htm" class="secondaryAction">&larr; <spring:message code="processo.button.indietro"/></a>
                        <button type="submit" class="primaryAction" name="submitaction" value="<spring:message code="processo.button.salva"/>"><spring:message code="processo.button.salva"/></button>
                        <button onclick="elimina();
                                return false;"class="cancella_generico"><spring:message code="processo.button.cancella"/></button>
                    </div>
                </div>
                <div class="clear"></div>    
            </div>

        </form:form>
    </div>
</div>
