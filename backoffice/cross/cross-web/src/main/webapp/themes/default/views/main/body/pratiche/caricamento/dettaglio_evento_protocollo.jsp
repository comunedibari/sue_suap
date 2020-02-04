<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="body_error" />
<form id="creazioneEventoForm" action="<%= path%>/documenti/protocollo/pratica/eventi/salva.htm" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data">
    <div class="inlineLabels"> 
        <h2 class="short" style="text-align:center">${comunicazione.evento.descrizione}</h2>
        <script type="text/javascript">
            $(document).ready(function() {
                //                $(".collapsibleContent").hide();
                //                $(".collapsibleContentHeader").click(function() {
                //                    $(this).next(".collapsibleContent").toggle('blind');
                //                });
                //                
                $("#creazioneEventoForm").submit(function() {
                    var obbligatori = $('.obbligatorio').length;
                    errorfile = false;
                    for (var i = 0; i < obbligatori; i++) {
                        var ob = $('.obbligatorio')[i];
                        //^^CS AGGIUNTA
                        var file = $('.fileObbligatorio')[i].value;
                        if ((ob.value == undefined || ob.value == '') ||
                                (file == undefined || file == ''))
                        {
                            errorfile = true;
                        }
                    }
                    if (errorfile)
                    {
                        alert("Per ogni file immettere descrizione e caricare file.");//TODO: ^^CS message
                        return false;
                    }
                });
            });
        </script>
        <div class="ctrlHolder dettaglio_liv_0">
            <fieldset class="fieldsetComunicazione">
                <legend>
                    <spring:message code="pratica.comunicazione.evento.pratica.title"/>
                </legend>
                <div>
                    <div class="ctrlHolder">
                        <label class="required">
                            <spring:message code="pratica.comunicazione.evento.pratica.numero"/>
                        </label>
                        <span class="value">${comunicazione.protocollo}</span>
                    </div>
                    <div class="ctrlHolder">
                        <label class="required"> <spring:message code="pratica.comunicazione.evento.pratica.dataricezione"/></label>
                        <span class="value"><fmt:formatDate pattern="dd/MM/yyyy" value="${comunicazione.dataRicezione}" /></span>
                    </div>
                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="pratica.comunicazione.evento.pratica.stato"/></label>
                        <span class="value">${comunicazione.stato}</span>
                    </div>
                    <c:if test="${(!empty comunicazione.scadenzeDaChiudere)}">
                        <div class="ctrlHolder">
                            <label for="giorniScadenzaCustom" class="required"><spring:message code="pratica.comunicazione.evento.pratica.scadenza"/></label>
                            <input name="giorniScadenzaCustom" id="giorniScadenzaCustom" type="text" class="textInput required">
                        </div>
                    </c:if>
                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="pratica.comunicazione.evento.pratica.download"/></label>
                        <c:choose>
                            <c:when test="${comunicazione.downloadAllegatoPratica}">
                                <span class="value">
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
        </div>

        <c:if test="${comunicazione.evento.mostraDestinatari == 'S'}">
            <div class="ctrlHolder dettaglio_liv_0">
                <fieldset class="fieldsetComunicazione">
                    <legend>
                        <spring:message code="pratica.comunicazione.evento.title"/>
                    </legend>
                    <div>
                        <%-- ^^CS se l'evento gli e' settato mostra destinatari --%>
                        <div class="ctrlHolder">
                            <label for="destinatari_a" class="required">
                                <spring:message code="pratica.comunicazione.evento.mittenti"/>
                            </label>        
                            <div class="destinatari">
                                <script type="text/javascript">
                                    $(document).ready(function() {
                                        var url = '<%=path%>/search/destinatari.htm?id_pratica=${comunicazione.idPratica}&id_evento=${idEvento}';
                                        $("#mittenti_a").tokenInput(url, {
                                            theme: 'facebook',
                                            queryParam: 'query',
                                            prePopulate: [
                                            ],
                                            propertyToSearch: "description",
                                            resultsFormatter: function(item) {
                                                return "<li>" +
                                                        "<div style='display: inline-block; padding-left: 10px;'>\n\
                                                        <div class='full_name'>" + item.description + " (" + item.type + ")</div>\n\
                                                        <div class='email'>" + item.email + "</div></div></li>";
                                            }
                                        });
                                    });
                                </script>

                                <div class="mittenti">
                                    <input class="textInput" id="mittenti_a" name="mittentiIds" />
                                </div>
                            </div>
                        </div>

                        <c:if test="${comunicazione.evento.protocollo == 'S'}">
                            <tiles:insertAttribute name="comunicazione_protocollo_manuale" />
                        </c:if>
                        <c:if test="${comunicazione.evento.visualizzaProcedimentiRiferimento == 'S'}">
                            <tiles:insertAttribute name="comunicazione_procedimenti" />
                        </c:if>

                    </div>
                </fieldset>
            </div>    
        </c:if>

        <div class="ctrlHolder dettaglio_liv_0">
            <fieldset class="fieldsetComunicazione">
                <legend>
                    <spring:message code="pratica.comunicazione.evento.documento"/> 
                </legend>
                <div>
                    <tiles:insertAttribute name="comunicazione_file_upload" />
                </div>
            </fieldset>
        </div>

        <c:if test="${comunicazione.evento.visualizzaScadenzeDaChiudere == 'S'}">
            <div class="ctrlHolder dettaglio_liv_0">
                <fieldset class="fieldsetComunicazione">
                    <legend>
                        <spring:message code="pratica.comunicazione.evento.scadenze"/> 
                    </legend>
                    <div>
                        <tiles:insertAttribute name="comunicazione_scadenze" />
                    </div>
                </fieldset>
            </div>
        </c:if>

        <div class="ctrlHolder dettaglio_liv_0">
            <fieldset class="fieldsetComunicazione">
                <legend class="collapsibleContentHeader"><spring:message code="pratica.comunicazione.evento.altreazioni"/></legend>
                <div class="collapsibleContent">
                    <div class="ctrlHolder form_checkbox_div">
                        <label for="visualizzaEvento" style="font-size: 1em;">
                            <spring:message code="pratica.comunicazione.evento.visualizza"/>
                        </label>
                        <input type="checkbox" name="visualizzaEventoSuCross" id="visualizzaEvento" value="ok" checked="checked" />
                    </div>
                    <c:if test="${comunicazione.evento.pubblicazionePortale == 'S'}">
                        <div class="ctrlHolder form_checkbox_div">
                            <label for="visualizzaPortale" style="font-size: 1em;">
                                <spring:message code="pratica.comunicazione.evento.pubblica"/>
                            </label>
                            <input type="checkbox" name="visualizzaEventoPortale" id="visualizzaPortale" value="ok" checked="checked" />
                        </div>
                    </c:if>
                    <div class="ctrlHolder dettaglio_liv_0" style="display:block;">
                        <h4 class="readOnlyLabel"><spring:message code="pratica.comunicazione.evento.note"/></h4>
                        <div class="readOnly">
                            <textarea cols="25" rows="25" id="note" name="note" style="width:100%;"></textarea>
                        </div>
                    </div>           
                </div>
            </fieldset>
        </div>
    </div>

    <div class="buttonHolder">
        <a href="<%=path%>/documenti/protocollo/pratica/eventi.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        <input type="hidden" name="idEvento" value="${idEvento}" />
        <button type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.crea"/></button>
    </div>
</form>