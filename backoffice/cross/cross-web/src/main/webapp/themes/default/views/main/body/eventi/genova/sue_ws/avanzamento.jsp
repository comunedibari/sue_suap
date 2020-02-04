<%
    String path = request.getContextPath();
    String url = path + "/pratica/comunicazionebogenova/salva.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:insertAttribute name="body_error" />
<script>
    $(document).ready(function(){
                         
        $("#dataProtocolloGenerale").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        
        $("#dataInizio").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        
        $("#dataFine").datepicker({
            dateFormat: 'dd/mm/yy'
        });
    });
    
</script>
<form:form modelAttribute="comunicazione" action="<%=url%>"  id="comunicazione" method="post" enctype="multipart/form-data" cssClass="uniForm inlineLabels comunicazione">
    <div class="inlineLabels">
        <fieldset id="comunicazionebogenovaFieldSet" class="fieldsetComunicazione fieldsetEvento">
            <legend>Dati pratica edilizia</legend>
            <form:hidden path="codiceEvento" />
            <div class="ctrlHolder">
                <label for="numeroPraticaEdilizia">Numero pratica edilizia</label>
                <form:input path="numeroPraticaEdilizia" id="numeroPraticaEdilizia" maxlength="5" cssClass="textInput numeroPraticaEdilizia"/>
                <p class="formHint">Numero della pratica presente sul sistema di Back office</p>
            </div>
            <div class="ctrlHolder">
                <label for="annoPraticaEdilizia">Anno pratica edilizia</label>
                <form:input path="annoPraticaEdilizia" id="annoPraticaEdilizia" maxlength="4" cssClass="textInput annoPraticaEdilizia"/>
                <p class="formHint">Anno di riferimento della  pratica</p>
            </div>
            <div class="ctrlHolder">
                <label for="numeroProtocolloGenerale">Numero protocollo generale</label>
                <form:input path="numeroProtocolloGenerale" id="numeroProtocolloGenerale" maxlength="15" cssClass="textInput numeroProtocolloGenerale"/>
                <p class="formHint">Numero di protocollo generale a cui il documento fa riferimento</p>
            </div>
            <fmt:formatDate value="${comunicazione.dataProtocolloGenerale}" type="date" pattern="dd/MM/yyyy" var="dataProtocolloGenerale"/>
            <div class="ctrlHolder">
                <label for="dataProtocolloGenerale">Data protocollazione</label>
                <form:input path="dataProtocolloGenerale" id="dataProtocolloGenerale" value="${dataProtocolloGenerale}" maxlength="10" cssClass="textInput dataProtocolloGenerale"/>
                <p class="formHint">Data di protocollazione del documento</p>
            </div>
            <fmt:formatDate value="${comunicazione.dataInizio}" type="date" pattern="dd/MM/yyyy" var="dataInizio"/>
            <div class="ctrlHolder">
                <label for="dataInizio">Data inizio lavori</label>
                <form:input path="dataInizio" id="dataInizio" maxlength="10" value="${dataInizio}" cssClass="textInput dataInizio"/>
                <p class="formHint">Data inizio lavori</p>
            </div>
            <fmt:formatDate value="${comunicazione.dataFine}" type="date" pattern="dd/MM/yyyy" var="dataFine"/>
            <div class="ctrlHolder">
                <label for="dataFine">Data fine lavori</label>
                <form:input path="dataFine" id="dataFine" maxlength="10" value="${dataFine}" cssClass="textInput dataFine"/>
                <p class="formHint">Data fine lavori</p>
            </div>
            <div class="ctrlHolder">
                <label for="note">Note</label>
                <form:textarea path="note" id="note" cssClass="textInput note" rows="5" cols="30"/>
            </div>
        </fieldset>

        <fieldset id="allegatiFieldSet" class="fieldsetComunicazione fieldsetEvento">
            <legend>Allegati</legend>
            <script>
        
                var j=$('.nuovoAllegato').length;
                function aggiungiAllegato(){
                    var description = '<td><input class="obbligatorio" type="text" name="allegati['+j+'].descrizione" /><br /><span class="formHint">Campo obbligatorio</span></td>';
                    var file = '<td><input type="file" name="allegati['+j+'].file" class="fileObbligatorio"/><span class="formHint">Campo obbligatorio</span></td>';
                    var check = '<input class="allegatoEmail" type="checkbox" name="allegati['+j+'].idFile" value="0" checked="checked"/>';
                    var remove = '<a class="aggiungiAllegato" onclick="rimuoviAllegato(this)"><img src="<%=path%>/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a>';
                    var empty = '<td></td>';
                    var checkRemove = '<td>' + check + '&nbsp;' + remove + '</td>';
                    var row = '<tr class="nuovoAllegato">' + $('#allegatiEmailTable tr') + '">'+description + file + empty + checkRemove+'</tr>';
                    $("#allegatiEmailTable > tbody").append(row);
                    j++;
                }
        
                function rimuoviAllegato(row){
                    $(row.parentNode.parentNode).remove();
                    j = $('.allegatoEmail').length;
                    for (var i=0; i<j; i++) { 
                        $('.allegatoEmail')[i].value = i; 
                    }
                }
        
        
            </script>
            <div class="aggiungiAllegato">
                <a onclick="aggiungiAllegato()" class="crea_nuovo_evento">
                    <spring:message code="pratica.comunicazione.evento.aggiungiallegato"/>
                </a>
            </div>
            <table cellspacing="0" cellpadding="0" class="master" id="allegatiEmailTable">
                <tbody>
                    <tr>
                        <th style="width: 200px"><spring:message code="pratica.comunicazione.evento.file.descrizione"/></th>
                        <th><spring:message code="pratica.comunicazione.evento.file.nomefile"/></th>
                        <th><spring:message code="pratica.comunicazione.evento.file.download"/></th>
                        <th></th> 
                    </tr>

                    <c:set var="countAllegatiPresenti" value="0" scope="page" />
                    <c:forEach items="${comunicazione.allegati}" begin="0" var="allegato">
                        <tr>
                            <td>${allegato.descrizione}</td>
                            <td>${allegato.nomeFile}</td>
                            <td>
                                <span style="text-align: center;"> 
                                    <a class="scarica" href="<%=path%>/download.htm?id_file=${allegato.idFile}" target="_blank">
                                        <spring:message code="pratica.comunicazione.evento.file.download"/>
                                    </a>
                                </span>
                            </td>
                            <td>
                                <span style="text-align: center;">
                                    <input type="checkbox" name="allegati[${countAllegatiPresenti}].idFile" id="allegatoEmail" value="${allegato.idFile}" />
                                </span>
                            </td>
                        </tr>
                        <c:set var="countAllegatiPresenti" value="${countAllegatiPresenti + 1}" scope="page"/>
                    </c:forEach>
                </tbody>
            </table>
        </fieldset>

    </div>
    <div class="buttonHolder">
        <a href="<%=path%>/pratica/evento/index.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        <button type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.inoltra"/></button>
    </div>
</form:form>