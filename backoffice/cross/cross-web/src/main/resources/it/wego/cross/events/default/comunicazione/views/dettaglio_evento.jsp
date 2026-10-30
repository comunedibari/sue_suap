<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="it.wego.cross.constants.Constants" %>
<%@ page import="it.wego.cross.constants.CartiglioConstants" %>
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

<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<div class="content_sidebar">
    <c:url value="/download/pratica.htm" var="downloadPraticaUrl">
        <c:param name="id_pratica" value="${pratica.idPratica}"/>
    </c:url>
    <div class="sidebar_left">
       	<c:set var="cartiglio" value="${evento.cartiglio}" scope="request"/>
    	<c:set var="from" value="${CartiglioConstants.FROM_EVENT }" scope="request"/>
		<c:import url="/themes/default/views/main/body/pratiche/cartiglio.jsp"/>
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
                                <div style="text-align: left;">
                                    <a class="scarica"
                                        href="<%=path%>/download_attachments.htm?id_pratica=${pratica.idPratica}&amp;id_pratica_evento=${evento.idPraticaEvento}"
                                        target="_blank"> <spring:message code="pratica.comunicazione.dettaglio.allegati.downloadAllAttachments" /></a>
                                    <c:if test="${Constants.SUE_BARI.equals(ente.idEnte) && not empty evento.allegatoPrincipale}">
	                                    <a class="scarica" 
	                                        href="<%=path%>/download_from_register.htm?id_pratica=${pratica.idPratica}&amp;id_file=${evento.allegatoPrincipale.idAllegato}"
	                                        target="_blank"> <spring:message code="pratica.comunicazione.dettaglio.allegati.download.main.register" /></a>
                                     </c:if>
                                     <c:if test="${Constants.SUE_RUVO.equals(ente.idEnte)}">
	                                    <a class="scarica" 
	                                        href="<%=path%>/download_from_register_ruvo.htm?id_pratica=${pratica.idPratica}&amp;id_pratica_evento=${evento.idPraticaEvento}"
	                                        target="_blank"> <spring:message code="pratica.comunicazione.dettaglio.allegati.download.main.register_ruvo" /></a>
                                     </c:if>
                                </div>
                                <table cellspacing="0" cellpadding="0" class="master">
                                    <tr>
                                        <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                                        <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                                        <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                                        <th><spring:message code="pratica.comunicazione.dettaglio.allegati.download.file"/></th>
            							<th><spring:message code="pratica.comunicazione.dettaglio.allegati.preview"/></th>
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
                                            <td>
												<div style="text-align: center;">
												<c:if test="${allegato.nomeFile.toLowerCase().endsWith('.pdf') || allegato.nomeFile.toLowerCase().endsWith('.doc')
														|| allegato.nomeFile.toLowerCase().endsWith('.docx') || allegato.nomeFile.toLowerCase().endsWith('.xls')
														|| allegato.nomeFile.toLowerCase().endsWith('.xlsx') || allegato.nomeFile.toLowerCase().endsWith('.odt')
														|| allegato.nomeFile.toLowerCase().endsWith('.p7m')  || allegato.nomeFile.toLowerCase().endsWith('.ods')
														}">
													<a class="cerca_lente_rosso"
																href="<%=path%>/preview.htm?id_file=${allegato.idAllegato}"
																target="_blank"> </a>
												</c:if>
													
												</div>
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
                                                <th><spring:message code="pratica.comunicazione.evento.email.pec"/></th>
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
                                                                    <c:when test="${email.stato=='C' || email.stato=='I'}">
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
                                                    <td>
                                                    	<c:if test="${email.stato == 'C' || email.stato == 'I'}" >
															<a class="scarica cursor_pointer showPecEmailDetail" id="${email.idEmail}">
	                                                            <spring:message code="pratica.comunicazione.evento.email.visualizzaPEC"/>
	                                                        </a>
                                                        </c:if>
                                                    </td>
                                                    <td>${email.dataAggiornamento}</td>
                                                    <td>
                                                    	<c:if test="${email.stato == 'C' || email.stato == 'I'}" >
															<a class="scarica cursor_pointer showEmailRicevuta" id="${email.idEmail}">
	                                                            <spring:message code="pratica.comunicazione.evento.email.verifica"/>
	                                                        </a>
                                                        </c:if>
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
    
    <script id="template-email-pec" type="text/x-handlebars-template">
        <div>
        <form id="email_resend_form">
        <input type="hidden" name="idEmail" value="{{idEmail}}" />
        <input type="hidden" name="taskId" value="{{taskId}}" />
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
     <script type="text/javascript">
    $(function() {
        var emailDetailTemplateHtml = $("#template-email-ricevuta").html();
        var emailDetailTemplate = Handlebars.compile(emailDetailTemplateHtml);
        $('.showEmailRicevuta').click(function(event) {
            var emailId = $(this).attr('id');
            var parameters = {};
            parameters.mailId = emailId;
            parameters.institutionId = ${ente.idEnte};
            parameters.paperworkEventId = ${evento.idPraticaEvento};
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

<script type="text/javascript">
    $(document).ready(function(){
        var emailDetailTemplateHtml = $("#template-email-pec").html();
        var emailDetailTemplate = Handlebars.compile(emailDetailTemplateHtml);
        $('.showPecEmailDetail').click(function(event) {
        	var emailId = $(this).attr('id');
            var parameters = {};
            parameters.mailId = emailId;
            $.ajax({
                type: 'POST',
                url: '${path}/console/mail/pec_detail.htm',
                data: parameters,
                dataType: 'json',
                success: function(data) {
                    if (data.idEmail !== undefined && data.idEmail !== null && data.idEmail !== '') {
                        var emailDetailTemplateHtml = emailDetailTemplate(data);
                        var dialog = $('<div />').appendTo('body').dialog({
                            modal: true,
                            title: 'PEC',
                            buttons: [{
                                    text: "Chiudi",
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

