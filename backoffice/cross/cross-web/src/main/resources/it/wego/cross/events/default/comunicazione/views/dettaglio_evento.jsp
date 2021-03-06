<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
%>
<script>
    var idPratica = '${id_pratica}';
    var idPraticaEvento = '${evento.idPraticaEvento}';
    var idEvento = '${evento.idEvento}';
    var path = '<%=path%>';
    var isEventoEditable = ${isEventoEditable};
    $(function() {

        if (isEventoEditable === false) {
            $('.x-editable-text').each(function(i, obj) {
                $(this).attr("class", "");
            });
        }

        $('.x-editable-text').editable({
            type: 'text',
            url: path + '/pratica/evento/attributi/aggiorna.htm',
            pk: 'praticaEvento',
            params: function(params) {
                var data = {};
                data['idPraticaEvento'] = "${evento.idPraticaEvento}";
                data['name'] = params.name;
                data['value'] = params.value;
                data['pk'] = params.pk;
                return data;
            },
            mode: 'inline',
            success: function(response, newValue) {
                if (response.success === "false") {
                    return response.msg;
                }
            }
        });

        $('#evento_protocollo').on('shown', function(e, editable) {
            $(".evento_protocollo_container input").maskfield('GPPPP/0000/099999999', {translation: {'P': {pattern: /[a-zA-Z]/, optional: true}, 'G': {pattern: /[a-zA-Z]/}}});
        });
    });
</script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/pratica.evento.dettaglio.js"/>"></script>
<%-- <tiles:insertAttribute name="body_error" /> --%>
<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<div class="content_sidebar">
    <c:url value="/download/pratica.htm" var="downloadPraticaUrl">
        <c:param name="id_pratica" value="${pratica.idPratica}"/>
    </c:url>
    <div class="sidebar_left">
        <h3><spring:message code="pratica.comunicazione.dettaglio.identificativo"/> <strong>${pratica.identificativoPratica}</strong></h3>
        <div class="sidebar_elemento">
            ${pratica.oggettoPratica}
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.dataricezione"/>:</strong> <fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></p>
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.stato"/>:</strong> ${pratica.idStatoPratica.descrizione}</p>
            <c:choose>
                <c:when test="${not empty pratica.idModello}">
                    <p style="margin-top:20px;">
                        <a href="${downloadPraticaUrl}" class="scarica" target="_blank">
                            <spring:message code="pratica.comunicazione.evento.pratica.download"/>
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
            <div class="menu-pull-bar"><spring:message code="pratica.comunicazione.evento.dettaglio"/></div>
            <ul>                   
                <li class="active"><a href="#frame1"><spring:message code="pratica.comunicazione.evento.dati"/></a></li>
            </ul>
            <div class="frames">
                <div class="frame active copertina" id="frame1">
                    <div>
                        <c:if test="${abilitaXMLSuri != null && abilitaXMLSuri == 'true'}">
                            <div class="ctrlHolder">
                                <legend><spring:message code="pratica.comunicazione.dettaglio.generaXMLSuri"/></legend>
                                <div>
                                    <form action="${path}/generaXMLSuri.htm" class="uniForm inlineLabels action" method="post">
                                        <div class="ctrlHolder dettaglio_liv_0">
                                            <label class="required">
                                                <spring:message code="pratica.comunicazione.dettaglio.generaXMLSuri.label"/>
                                            </label>
                                            <span>
                                                <input type="hidden" name="idPratica" value="${id_pratica}">
                                                <input type="hidden" name="idPraticaEvento" value="${evento.idPraticaEvento}">
                                                <c:if test="${evento.descrizione!=null}">      
                                                    <input type="text" name="descrizione" value="${evento.descrizione}" class="textInput" />
                                                </c:if>
                                                <c:if test="${evento.descrizione==null}">      
                                                    <input type="text" name="descrizione" value="L'evento non ha una descrizione, è stato probabilmente generato in modo errato." class="textInput" />
                                                </c:if>
                                                <input type="submit" name="submit" value="<spring:message code="pratica.comunicazione.dettaglio.generaXMLSuri.button"/>" class="button ui-state-default ui-corner-all"/>
                                            </span>
                                        </div>
                                    </form>
                                </div>

                            </div>
                        </c:if>
                        <div class="ctrlHolder dettaglio_liv_0">
                            <div class="ctrlHolder">
                                <label class="required"><spring:message code="pratica.comunicazione.evento.descrizione"/></label>
                                <c:if test="${evento.descrizione!=null}">   
                                    <span class="value">${evento.descrizione}</span>
                                </c:if>
                                <c:if test="${evento.descrizione==null}">   
                                    <span class="value">L'evento non ha una descrizione, è stato probabilmente generato in modo errato</span>
                                </c:if>
                            </div>
<!--                             <div class="ctrlHolder evento_protocollo_container"> -->
							  <div class="ctrlHolder ">
                                <label for="evento_protocollo" class="required"><spring:message code="pratica.comunicazione.dettaglio.protocollo"/></label>
                                <c:if test="${evento.numProtocollo!=null}">   
                                    <a href="#" id="evento_protocollo" class="x-editable-text" name="evento_protocollo">${evento.numProtocollo}</a>
                                </c:if>
                                <c:if test="${evento.numProtocollo==null}">   
                                    <a href="#" id="evento_protocollo" class="x-editable-text" name="evento_protocollo"></a>
                                </c:if>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required"><spring:message code="pratica.comunicazione.dettaglio.protocollo.data"/></label>
                                <a href="#" id="evento_data_protocollo" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="evento_data_protocollo"><fmt:formatDate pattern="dd/MM/yyyy" value="${evento.dataProtocollo}" /></a>
                            </div>
                            <div class="ctrlHolder">
                                <c:if test="${evento.verso!=null && evento.verso == 'I'}">
                                    <label class="required"><spring:message code="pratica.comunicazione.evento.mittente"/></label>    
                                </c:if>
                                <c:if test="${evento.verso!=null && evento.verso == 'O'}">
                                    <label class="required"><spring:message code="pratica.comunicazione.evento.destinatario"/></label>    
                                </c:if>
                                <c:if test="${evento.verso==null || evento.verso == ''}">
                                    <label class="required"><spring:message code="pratica.comunicazione.evento.nuova"/></label>    
                                </c:if>
                                <span class="value">
                                    <ul>
                                        <c:if test="${!empty evento.destinatari}">
                                            <c:if test="${!empty evento.destinatari.anagrafiche}">
                                                <c:forEach items="${evento.destinatari.anagrafiche}" var="anagrafica" begin="0">
                                                    <li>
                                                        ${anagrafica.anagrafica.codiceFiscale} - 
                                                        <c:if test="${!empty anagrafica.anagrafica.denominazione}">
                                                            <%-- è una azienda --%>
                                                            ${anagrafica.anagrafica.denominazione}
                                                        </c:if>
                                                        <c:if test="${!empty anagrafica.anagrafica.cognome}">
                                                            <%-- è una persona --%>
                                                            ${anagrafica.anagrafica.nome} ${anagrafica.anagrafica.cognome}
                                                        </c:if>
                                                        <c:set var="email">
                                                            <spring:message code="evento.posta.ordinaria"/>
                                                        </c:set>
                                                        <c:if test="${!empty anagrafica.recapito.email}">
                                                            <c:set var="email" value="${anagrafica.recapito.email}"/>
                                                        </c:if>
                                                        <c:if test="${!empty anagrafica.recapito.pec}">
                                                            <c:set var="email" value="${anagrafica.recapito.pec}"/>
                                                        </c:if>
                                                        (${email}) 
                                                    </li>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${!empty evento.destinatari.enti}">
                                                <c:forEach items="${evento.destinatari.enti}" var="ente" begin="0">
                                                    <li>${ente.descrizione}</li>
                                                    </c:forEach>
                                                </c:if>
                                                <c:if test="${!empty evento.destinatari.notifica}">
                                                <li>
                                                    ${evento.destinatari.notifica.presso}
                                                </li>
                                            </c:if>
                                        </c:if>
                                    </ul>
                                </span>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required"><spring:message code="pratica.comunicazione.evento.dataevento"/></label>
                                <span class="value">${evento.dataEvento}</span>
                            </div>

                            <div class="ctrlHolder">
                                <label class="required"><spring:message code="pratica.comunicazione.evento.note"/></label>
                                <a href="#" id="evento_note" class="x-editable-text" name="evento_note">${evento.note}</a>
                            </div>
                            <div class="ctrlHolder">
                                <label class="required"><spring:message code="pratica.comunicazione.evento.visibilitaFront"/></label>
                                <span class="value">
                                    <c:if test="${! empty evento.pubblicazionePortale}">
                                        <input type="checkbox" name="evento.pubblicazionePortale" id="pubblicazionePortale" value="S" <c:if test="${evento.pubblicazionePortale == 'S'}">checked="checked"</c:if> />
                                    </c:if>
                                    <c:if test="${ empty evento.pubblicazionePortale}">
                                        <input type="checkbox" name="evento.pubblicazionePortale" id="pubblicazionePortale" value="S" />
                                    </c:if>
                                </span>
                            </div>
                        </div>
                        <div class="ctrlHolder dettaglio_liv_0">
                            <c:if test="${!empty evento.allegati}">
                                <table cellspacing="0" cellpadding="0" class="master">
                                    <tr>
                                        <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                                        <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                                        <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                                        <th></th>
                                    </tr>
                                    <c:forEach items="${evento.allegati}" var="allegato" begin="0">
                                        <tr>
                                            <td>${allegato.descrizione}</td>
                                            <td>${allegato.nomeFile}</td>
                                            <td style="background-image: url(images/bottone_gestione_flusso.png)">          
                                                <div class="${allegato.tipoFileCode}" style="text-align: center;"></div>

                                            </td>
                                            <td>
                                                <span style="text-align: center;"> 
                                                    <a class="scarica" href="${path}/download.htm?id_file=${allegato.idAllegato}" target="_blank">
                                                        <spring:message code="pratica.comunicazione.evento.file.download"/>
                                                    </a>
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </div>
                        <c:if test="${!empty evento.email}">
                            <div class="inlineLabels" id="dettaglio_email">
                                <div class="ctrlHolder dettaglio_liv_0">

                                    <h4 class="readOnlyLabel">Email</h4>     
                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <table cellspacing="0" cellpadding="0" class="master"> 
                                            <tr>
                                                <th style="" colspan="2"><spring:message code="pratica.comunicazione.evento.email.stato"/></th>
                                                <th style=""><spring:message code="pratica.comunicazione.evento.email.dataInvio"/></th>
                                                <th><spring:message code="pratica.comunicazione.evento.email.destinatario"/></th>
                                                <th><spring:message code="pratica.comunicazione.evento.email.oggetto"/></th>
                                                <th><spring:message code="pratica.comunicazione.evento.email.ultimoAggiornamento"/></th>
                                                <th><spring:message code="pratica.comunicazione.evento.email.esitoSpedizione"/></th>
                                                <th><spring:message code="pratica.comunicazione.evento.email.azione"/></th>
                                            </tr>
                                            <c:forEach items="${evento.email}" var="email" begin="0">
                                                <tr>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${email.stato=='E' || email.stato=='M'}">
                                                                <img title="${email.stato}" alt="${email.stato}" src="<%=path%>/themes/default/images/icons/exclamation.png" width="18">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${email.stato=='C'}">
                                                                        <img title="${email.stato}" alt="${email.stato}" src="<%=path%>/themes/default/images/icons/accept.png" width="18">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img title="${email.stato}" alt="${email.stato}" src="<%=path%>/themes/default/images/icons/icon_alert.gif" width="18">
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>  
                                                    </td>
                                                    <td>${email.statoDescrizione}</td>
                                                    <td>${email.dataInserimento}</td>
                                                    <td>${email.emailDestinatario}</td>
                                                    <td>${email.oggettoEmail}</td>
                                                    <td>${email.dataAggiornamento}</td>
                                                    <td>
<%--                                                         <p title="${email.corpoRisposta}">${email.oggettoRisposta}</p> --%>
														<a class="scarica cursor_pointer showEmailRicevuta" id="${email.idEmail}">
                                                            Verifica Ricevuta
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a class="scarica cursor_pointer showEmailDetail" id="${email.idEmail}">
                                                            <spring:message code="pratica.comunicazione.evento.email.rispedisci"/>
                                                        </a>
                                                    </td>
                                                    
                                                </tr>
                                            </c:forEach >
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="buttonHolder">
                <a href="${path}/pratiche/dettaglio.htm?id_pratica=${id_pratica}&currentTab=frame7" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
            </div>
        </div>
    </div>

    <script id="template-email-da-rispedire" type="text/x-handlebars-template">
        <div>
        <form id="email_resend_form">
        <input type="hidden" name="idEmail" value="{{idEmail}}" />
        <input type="hidden" name="taskId" value="{{taskId}}" />
        <div class="ctrlHolder">
        <label class="required" for="destinatario">Destinatario</label>
        <input type="text" name="destinatario" class="textInput required value" style="width: 100%;" value="{{emailDestinatario}}"/>
        </div>
        <div class="ctrlHolder">
        <label class="required" for="oggettoEmail">Oggetto email</label>
        <input type="text" name="oggettoEmail" class="textInput required value" style="width: 100%;" value="{{oggettoEmail}}"/>
        </div>
        <div class="ctrlHolder">    
        <label class="required">Corpo email</label>
        <div class="value text-area">{{corpoEmail}}</div>
        </div>
        </form>
        </div>
    </script>
    
    <script id="template-email-ricevuta" type="text/x-handlebars-template">
        <div>
        <form id="email_resend_form">
        <input type="hidden" name="idEmail" value="{{idEmail}}" />
        <input type="hidden" name="taskId" value="{{taskId}}" />
        <div class="ctrlHolder">
        <label class="required" for="oggettoRisposta">Oggetto risposta</label>
        <input type="text" name="oggettoRisposta" class="textInput required value" style="width: 100%;" value="{{oggettoRisposta}}"/>
        </div>
        <div class="ctrlHolder">    
        <label class="required">Corpo risposta</label>
        <div class="value text-area">{{corpoRisposta}}</div>
        </div>
        </form>
        </div>
    </script>

    <script>
    $(function() {
        var emailDetailTemplateHtml = $("#template-email-da-rispedire").html();
        var emailDetailTemplate = Handlebars.compile(emailDetailTemplateHtml);

        $('.showEmailDetail').click(function(event) {
            var emailId = $(this).attr('id');
            var parameters = {};
            parameters.mailId = emailId;
            $.ajax({
                type: 'POST',
                url: '${path}/console/mail/detail.htm',
                data: parameters,
                dataType: 'json',
                success: function(data) {
                    if (data.idEmail !== undefined && data.idEmail !== null && data.idEmail !== '') {
                        var emailDetailTemplateHtml = emailDetailTemplate(data);
                        var dialog = $('<div />').appendTo('body').dialog({
                            modal: true,
                            title: 'Vuoi rispedire l\'email?',
                            buttons: [{
                                    text: "Ok",
                                    click: function() {
                                        resendEmail(this);
                                    }
                                }, {
                                    text: "Annulla",
                                    click: function() {
                                        $(this).dialog("close");
                                    }
                                }],
                            width: 800,
                            position: { my: 'top', at: 'top+20' },
                            close: function() {
                                dialog.remove();
                            }
                        }).html(emailDetailTemplateHtml || '');
                    } else {
                        var dialog = $("<div />").text(data.message);
                        $(dialog).dialog({
                            modal: true,
                            title: "Esito operazione",
                            width: 350,
                            height: 300,
                            closeOnEscape: true,
                            buttons: {
                                Ok: function() {
                                    $(dialog).dialog('close');
                                }
                            }
                        });
                    }
                }
            });
        });
    });

    </script>
     <script>
    $(function() {
        var emailDetailTemplateHtml = $("#template-email-ricevuta").html();
        var emailDetailTemplate = Handlebars.compile(emailDetailTemplateHtml);

        $('.showEmailRicevuta').click(function(event) {
            var emailId = $(this).attr('id');
            var parameters = {};
            parameters.mailId = emailId;
            $.ajax({
                type: 'POST',
                url: '${path}/console/mail/detail.htm',
                data: parameters,
                dataType: 'json',
                success: function(data) {
                    if (data.idEmail !== undefined && data.idEmail !== null && data.idEmail !== '') {
                        var emailDetailTemplateHtml = emailDetailTemplate(data);
                        var dialog = $('<div />').appendTo('body').dialog({
                            modal: true,
                            title: 'Ricevuta',
                            buttons: [{
                                    text: "Ok",
                                    click: function() {
                                    	$(this).dialog("close");
                                    }
                                }],
                            width: 800,
                            position: { my: 'top', at: 'top+20' },
                            close: function() {
                                dialog.remove();
                            }
                        }).html(emailDetailTemplateHtml || '');
                    } else {
                        var dialog = $("<div />").text(data.message);
                        $(dialog).dialog({
                            modal: true,
                            title: "Esito operazione",
                            width: 350,
                            height: 300,
                            closeOnEscape: true,
                            buttons: {
                                Ok: function() {
                                    $(dialog).dialog('close');
                                }
                            }
                        });
                    }
                }
            });
        });
    });

    </script>
    <div class="clear"></div>
</div>

