<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="ctrlHolder dettaglio_liv_0">

    <script type="text/javascript">

        var j = $('.nuovoAllegato').length;

        function rimuoviAllegato(row) {
            $(row.parentNode.parentNode).remove();
            var emptyrow = '<tr style="display:none" class="nuovoAllegato"></tr>';
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

                        var hiddenFields = '';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].pathFile" value="' + file.tempPath + '"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].nomeFile" value="' + file.name + '"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].tipoFile" value="' + file.type + '"/>';
                        hiddenFields += '<input style="display:none" type="text" name="allegatiManuali[' + j + '].allegaAllaMail" value="true"/>';

                        var description = '<td>' + hiddenFields + '<input class="obbligatorio" type="text" name="allegatiManuali[' + j + '].descrizione" style="width: 100%"/><br /><span class="formHint">Campo obbligatorio</span></td>';
                        var name = '<td>' + file.name + '</td>';
                        var type = '<td><div class="' + file.typeCode + '" style="text-align: center;"></div></td>';
                        var remove = '<td colspan="2"><a class="aggiungiAllegato" onclick="rimuoviAllegato(this);"><img src="${path}/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a></td>';

                        var principale = '';
                        var prot = '${processo_evento.flgProtocollazione}';
                        if (prot == 'S')
                        	principale = '<td><input type="checkbox" name="allegatiManuali[' + j + '].principale" value="true" class="documenti_allegati_principale" /></td>';
                        	
                        var row = '<tr class="nuovoAllegato">' + hiddenFields + description + name + type + remove + principale +'</tr>';
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
            
            $("#seleziona_tutti_allegati").on("click", function(){
                $('.documenti_allegati').not(this).prop('checked', this.checked);
            });
            
        });
        
//**********************************************************************************************************************************************************************
    </script>



    <c:if test="${processo_evento.flgFirmato}">
        <h4 class="alertFileFirmati"><spring:message code="pratica.comunicazione.evento.filefirmato"/></h4>    
    </c:if>

    <!--        <div class="aggiungiAllegato">
                <a onclick="aggiungiAllegato()" class="crea_nuovo_evento">-->
    <!-- spring:message code="pratica.comunicazione.evento.aggiungiallegato"/>-->
    <!--</a>
    </div>-->


    <!--        AGGIUNTO DA GAB MON-->
    <!--*****************************************************************************************************************************************************************        -->
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
    <!--*****************************************************************************************************************************************************************        -->



    <table cellspacing="0" cellpadding="0" class="master" id="allegatiEmailTable">
        <tbody>
            <tr>
                <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.download"/></th>
                <th><input type="checkbox" id="seleziona_tutti_allegati"/>&nbsp;<spring:message code="pratica.comunicazione.evento.allega"/></th>
                <th><spring:message code="pratica.comunicazione.evento.allega.principale"/></th>
            </tr>

            <c:set var="countAllegatiPresenti" value="0" scope="page" />
            <c:forEach items="${allegatiPratica}" begin="0" var="allegato">
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
                        <c:if test="${processo_evento.flgAllMail == 'S'}">
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiPresenti[${countAllegatiPresenti}].idAllegato" value="${allegato.idAllegato}" class="documenti_allegati" />
                            </span>
                        </c:if>
                    </td>
                    <td>
                    	<c:if test="${processo_evento.flgProtocollazione=='S'}">
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiPresenti[${countAllegatiPresenti}].principale" value="true" class="documenti_allegati_principale" />
                            </span>
                   		</c:if> 
                    </td>
                </tr>
                <c:set var="countAllegatiPresenti" value="${countAllegatiPresenti + 1}" scope="page"/>
            </c:forEach>

            <c:set var="countAllegatiDaProtocollo" value="0" scope="page" />
            <c:forEach items="${allegatiDaProtocollo}" begin="0"  var="allegatoDaProtocollo">
                <tr class="nuovoAllegato">
                    <td>
                        <input class="obbligatorio" readonly="readonly" type="text" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].descrizione" value="${allegatoDaProtocollo.descrizione}">
                        <input class="obbligatorio" readonly="readonly" type="hidden" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].idFileEsterno" value="${allegatoDaProtocollo.idFileEsterno}">
                    </td>
                    <td><input class="obbligatorio" readonly="readonly" type="text" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].nomeFile" value="${allegatoDaProtocollo.nomeFile}"></td>
                    <td><input class="obbligatorio" readonly="readonly" type="text" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].tipoFile" value="${allegatoDaProtocollo.tipoFile}"></td>
                    <td>
                        <span style="text-align: center;">

                            <a class="scarica" href="<%=path%>/download.htm?id_file=${allegatoDaProtocollo.idAllegato}" target="_blank">
                                <spring:message code="pratica.comunicazione.evento.file.download"/>
                            </a>
                        </span>
                    </td>
                    <td>
                        <c:if test="${processo_evento.flgAllMail == 'S'}">
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiDaProtocollo[${countAllegatiPresenti}].idAllegato" value="${allegatoDaProtocollo.idAllegato}" class="documenti_allegati" />
                            </span>
                        </c:if>
                    </td>
                    <td>
                         <c:if test="${processo_evento.flgProtocollazione=='S' && processo_evento.flgAllMail == 'S'}"> 
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiDaProtocollo[${countAllegatiPresenti}].principale" value="true" class="documenti_allegati_principale"/>
                            </span>
                         </c:if> 
                    </td>
                </tr>
                <c:set var="countAllegatiDaProtocollo" value="${countAllegatiDaProtocollo + 1}" scope="page"/>
            </c:forEach>


            <!--                AGGIUNTO DA GAB MON-->
            <!--    ***********************************************************************************************            -->

            <c:forEach items="${allegatiManuali}" var="allegato" varStatus="status">
                <tr class="nuovoAllegato">
                    <td>
                        <input style="display:none" type="text" name="allegatiManuali[${status.index}].allegaAllaMail" value="${allegato.allegaAllaMail}"/>
                        <input class="obbligatorio" type="text" name="allegatiManuali[${status.index}].descrizione" value="${allegato.descrizione}" /><br /><span class="formHint">Campo obbligatorio</span></td>
                    <td>${allegato.nomeFile}
                        <input style="display:none" type="text" name="allegatiManuali[${status.index}].pathFile" value="${allegato.pathFile}"/><input style="display:none" type="text" name="allegatiManuali[${status.index}].nomeFile" value="${allegato.nomeFile}"/></td>
                    <td style="background-image: url(images/bottone_gestione_flusso.png)">          
                        <div class="${allegato.tipoFileCode}" style="text-align: center;"></div>
                        <input style="display:none" type="text" name="allegatiManuali[${status.index}].tipoFile" value="${allegato.tipoFile}"/>
                    </td>


                    <td colspan="2">
                        <a class="aggiungiAllegato" onclick="rimuoviAllegato(this)"><img src="${path}/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a>
                    </td>
                    <td>
                         <c:if test="${processo_evento.flgProtocollazione=='S'}"> 
                            <span style="text-align: center;">
                                <form:checkbox path="allegatiDaProtocollo[${countAllegatiPresenti}].principale" value="true" />
                            </span>
                         </c:if>
                    </td>

                </tr>
            </c:forEach>
            <!--    ***********************************************************************************************            -->
        </tbody>
    </table>
</div>