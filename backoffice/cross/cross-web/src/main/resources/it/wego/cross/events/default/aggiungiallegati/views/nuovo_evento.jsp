<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:url value="/download/pratica.htm" var="downloadPraticaUrl">
    <c:param name="id_pratica" value="${pratica.idPratica}"/>
</c:url>

<c:url value="/pratica/aggiungiallegati/salva.htm" var="submitEventoUrl">
</c:url>

<tiles:insertAttribute name="body_error" />

<script>
    $(document).bind('dragover', function(e) {
        var dropZoneall = $('#dropzoneall'),
                timeout = window.dropZoneTimeout;
        var dropZone = $('#dropzone');
        if (!timeout) {
            dropZoneall.addClass('in');
            dropZone.addClass('in');
        } else {
            clearTimeout(timeout);
        }
        var found = false,
                node = e.target;
        do {
            if (node === dropZone[0] || node === dropZoneall[0]) {
                found = true;
                break;
            }
            node = node.parentNode;
        } while (node != null);
        if (found) {
            dropZone.addClass('hover');
            dropZoneall.addClass('hover');
        } else {
            dropZone.removeClass('hover');
            dropZoneall.removeClass('hover');
        }
        window.dropZoneTimeout = setTimeout(function() {
            window.dropZoneTimeout = null;
            dropZone.removeClass('in hover');
            dropZoneall.removeClass('in hover');
        }, 100);
    });
</script>


<script>
    var idPratica = '${pratica.idPratica}';
    var idEvento = '${processo_evento.idEvento}';


    $(document).ready(function() {
        $(".collapsibleContent").hide();
        /*$(".collapsibleContentHeader").click(function() {
         $(this).next(".collapsibleContent").toggle('blind');
         });*/
        $("#creazioneEventoForm").submit(function() {
            var obbligatori = $('.obbligatorio').length;
            errorfile = false;
            for (var i = 0; i < obbligatori; i++) {
                var ob = $('.obbligatorio')[i];
                //^^CS AGGIUNTA
                //******************************************************************************************************
                //AGGIUNTO DA GAB MON 
                //******************************************************************************************************
                //e' stato necessario modificare sensibilmente questo messaggio di errore in modo che non controllasse che i file venissero caricati   
//                var file = $('.fileObbligatorio')[i].value;
                if ((ob.value == undefined || ob.value == ''))
                        // come era prima // || (file== undefined || file== ''))
                        {
                            errorfile = true;
                        }
            }
            if (errorfile)
            {
                //Per ogni file immattere una descrizione e caricare il file
                alert("Per ogni file caricato è obbligatorio immettere una descrizione.");//TODO: ^^CS message
                return false;
            }
            if ($('#giorniScadenzaCustom').length == 1) {
                if (!isGiorniScadenzaValid($('#giorniScadenzaCustom').val())) {
                    alert("Inserire un valore corretto per i giorni scadenza");
                    return false;


                }
            }
        });
    });



    function disableSubmit()
    {
        var submit = document.getElementById("creaEvento");
        submit.disabled = true;
        return false;
    }


    function enableSubmit()
    {
        var submit = document.getElementById("creaEvento");
        submit.disabled = false;
        return false;
    }
</script>
    
<div class="content_sidebar">
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
        <form:form id="creazioneEventoForm" action="${submitEventoUrl}" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data" commandName="comunicazione">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>           
                    <li class="active">
                        <a href="#frame5">${processo_evento.desEvento}</a>
                    </li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active"  id="frame5">
                        <div>

                            <div class="ctrlHolder dettaglio_liv_0">

                                <script type="text/javascript">

                                    var j = $('.nuovoAllegato').length;

                                    function rimuoviAllegato(row) {
                                        $(row.parentNode.parentNode).remove();
                                        var emptyrow = '<tr style="display:none" class="nuovoAllegato"></tr>'
                                        //***********************************************
                                        //AGGIUNTO DA GAB MON IN MODO CHE IL NUMERO DI OGGETTI "ALLEGATIMAILTABLE" NON DIMINUISCA QUANDO SE NE CANCELLA UNO, QUESTO SERVE PER ESSERE SICURI DI NON CREARE OGGETTI CON LO STESSO NOME DOPO UN ERRORE E LA DISTRUZIONE DI UNO
                                        $("#allegatiEmailTable > tbody").append(emptyrow);
                                        //***********************************************
                                        j = $('.allegatoEmail').length;
                                        for (var i = 0; i < j; i++) {
                                            $('.allegatoEmail')[i].value = i;
                                        }
                                    }
                                    //**********************************************************************************************************************
                                    //*******************************************************  AGGIUNTO DA GAB MON  ***************************************************************************************************
                                    //*********************************************************************************************************************************************************************************************************************************************
                                    $(function() {
                                        $('#uploadallegati').fileupload({
                                            dataType: 'json',
                                            done: function(e, data) {
                                                enableSubmit();
                                                $.each(data.result, function(index, file) {

                                                    if (file.name === "_ERRORE_ERRORE_") {

                                                        alert("Si è verificato un errore nel salvataggio temporaneo di uno o più files. Si prega di controllare nella tabella quali file sono stati caricati correttamente e provvedere a ricaricare quelli mancanti. Contattare l'amministratore qualora il problema dovesse ripresentarsi. ");
                                                        return false;
                                                    }
                                                    ;
                                                    var j = $('.nuovoAllegato').length;
                                                    var description = '<td><input class="obbligatorio" type="text" name="allegatiManuali[' + j + '].descrizione" /><br /><span class="formHint">Campo obbligatorio</span></td>';
                                                    var path = '<input style="display:none" type="text" name="allegatiManuali[' + j + '].pathFile" value="' + file.tempPath + '"/>';
                                                    var name = '<td>' + file.name + path;
                                                    var namehidden = '<input style="display:none" type="text" name="allegatiManuali[' + j + '].nomeFile" value="' + file.name + '"/></td>';
                                                    var type = '<td>' + '<div class="' + file.typeCode + '" style="text-align: center;"></div>';
                                                    var typehidden = '<input style="display:none" type="text" name="allegatiManuali[' + j + '].tipoFile" value="' + file.type + '"/></td>';
                                                    var check = '<td></td>';
                                                    var remove = '<td colspan="2"><a class="aggiungiAllegato" onclick="rimuoviAllegato(this)"><img src="${path}/themes/defaut/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a></td>';
                                                    var empty = '<td></td>';
                                                    var checkRemove = '<td>' + check + '&nbsp;' + remove + '</td>';
                                                    var row = '<tr class="nuovoAllegato">' + $('#allegatiEmailTable tr') + '">' + description + name + namehidden + type + typehidden + path + remove + '</tr>';
                                                    $("#allegatiEmailTable > tbody").append(row);
                                                    j++;
                                                });
                                            },
                                            progressall: function(e, data) {
                                                var progress = parseInt(data.loaded / data.total * 100, 10);
                                                $('#progress .bar').css(
                                                        'width',
                                                        progress + '%'
                                                        );
                                            },
                                            dropZone: $('#dropzoneall')
                                        });
                                    });
                                    //**********************************************************************************************************************************************************************
                                </script>
                                <script>
                                    function aggiungiAllegati() {
                                        $("#uploadallegati").trigger('click');
                                    }
                                </script>
                                <label>Rilasciare i file da caricare nella zona sottostante (drop zone) o cliccare il pulsante "Aggiungi allegato" per cercarli nel proprio computer, scrivere quindi una descrizione per ciascun file caricato. Possono essere caricati più files contemporaneamente in entrambe le modalità.</label>
                                <div class="dropzone_container" id="dropzoneall">DROP ZONE</div>

                                <div class="aggiungiAllegato">
                                    <a onclick="aggiungiAllegati()" class="crea_nuovo_evento">
                                        <spring:message code="pratica.comunicazione.evento.aggiungiallegato"/>
                                    </a>
                                </div>                
                                <input style="display:none" id="uploadallegati" type="file" name="file"  data-url="${path}/ajax_upload.htm" onchange="disableSubmit();
                                        return false;" multiple/> 

                                <table cellspacing="0" cellpadding="0" class="master" id="allegatiEmailTable">
                                    <tbody>
                                        <tr>
                                            <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                                            <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                                            <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                                            <th><spring:message code="pratica.comunicazione.evento.file.download"/></th>
                                            <th><spring:message code="pratica.comunicazione.evento.allega"/></th>
                                        </tr>

                                        <!--                AGGIUNTO DA GAB MON-->
                                        <!--    ***********************************************************************************************            -->

                                        <c:forEach items="${allegatiManuali}" var="allegato" varStatus="status">
                                            <tr class="nuovoAllegato">
                                                <td><input class="obbligatorio" type="text" name="allegatiManuali[${status.index}].descrizione" value="${allegato.descrizione}" /><br /><span class="formHint">Campo obbligatorio</span></td>
                                                <td>${allegato.nomeFile}
                                                    <input style="display:none" type="text" name="allegatiManuali[${status.index}].pathFile" value="${allegato.pathFile}"/><input style="display:none" type="text" name="allegatiManuali[${status.index}].nomeFile" value="${allegato.nomeFile}"/></td>
                                                <td style="background-image: url(images/bottone_gestione_flusso.png)">          
                                                    <div class="${allegato.tipoFileCode}" style="text-align: center;"></div>
                                                    <input style="display:none" type="text" name="allegatiManuali[${status.index}].tipoFile" value="${allegato.tipoFile}"/></td>
                                                </td>


                                                <td colspan="2">
                                                    <a class="aggiungiAllegato" onclick="rimuoviAllegato(this)"><img src="${path}/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <!--    ***********************************************************************************************            -->
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>  
                </div>
            </div>
            <div class="buttonHolder">
                <a href="${path}/pratica/evento/index.htm?id_pratica=${pratica.idPratica}" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
                <form:hidden path="activeTab" id="comunicazione_active_tab"/>
                <form:hidden path="idEvento"/>
                <form:hidden path="idPratica"/>
                <form:hidden path="initialized" value="true"/>
                <button tipoprotocollo="${tipoprotocollo}" value="<spring:message code="pratica.comunicazione.evento.procedi"/>" id="creaEvento" class="primaryAction" name="submitaction" onclick="triggersubmit();
                        return false;"><spring:message code="pratica.comunicazione.evento.procedi"/></button>
            </div>
        </form:form>
    </div>
    <div class="clear"></div>
</div>



<!--   INSERITO DA GAB MON-->
<!--****************************************************************************************************************************************************************************************************************************-->
<!--            attiva il bottone di load di un file-->
<script>
    function uploadOriginal() {
        $("#originalupload").trigger('click');
    }
</script>
<script>
    function triggersubmit() {
        var div = $("<div>");
        var messaggio = "";
        var idprot = $("#protocollo input").val();
        var tipoprotocollo = $("#creaEvento").attr("tipoprotocollo");
        messaggio = "<spring:message code="evento.crea.conferma"/>";
        if (tipoprotocollo === "S") {
            messaggio += "<spring:message code="evento.crea.conferma.protocollo"/>";
            if (idprot === "") {
                messaggio += " " + "<spring:message code="evento.crea.conferma.protocolloautomatico"/>";
            }
        }
        div.html(messaggio);
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma creazione nuovo evento",
                    buttons: {
                        Ok: function() {
                            $(div).dialog('close');
                            $("#creazioneEventoForm").submit();
                            return true;
                        },
                        Annulla: function() {
                            $(this).dialog('close');
                            return false;
                        }
                    }
                });
    }
</script>
<!--****************************************************************************************************************************************************************************************************************************-->



<!--   INSERITO DA GAB MON-->
<!--****************************************************************************************************************************************************************************************************************************-->
<!--  SERVE CHE VENGA ESEGUITO ANCHE LO SCRIPT DI JQUERY MA e' GIA' INCLUSO ALTROVE NELLA PAGINA E VENGONO ERRORI SE SI INCLUDE ANCHE QUI
-->


<script src="/cross/javascript/jquery.iframe-transport.js"></script>
<script src="/cross/javascript/jquery.fileupload.js"></script>
<script src="/cross/javascript/myuploadfunction.js"></script>  
<!--****************************************************************************************************************************************************************************************************************************-->