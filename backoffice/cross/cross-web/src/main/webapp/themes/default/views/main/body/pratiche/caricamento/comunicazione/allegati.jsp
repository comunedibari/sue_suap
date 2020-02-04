<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="ctrlHolder dettaglio_liv_0">
    <script type="text/javascript">
        
        var j=$('.nuovoAllegato').length;
        function aggiungiAllegato(){
            var description = '<td><input class="obbligatorio" type="text" name="allegatiManuali['+j+'].descrizione" /><br /><span class="formHint">Campo obbligatorio</span></td>';
            //^^CS MODIFICA
            var file = '<td><input type="file" name="allegatiManuali['+j+'].file" class="fileObbligatorio"/><span class="formHint">Campo obbligatorio</span></td>';
            var check = '<input class="allegatoEmail" type="checkbox" name="allegatiManuali['+j+'].allegaAllaMail" id="allegatoEmail" checked="checked"/>';
            var remove = '<a class="aggiungiAllegato" onclick="rimuoviAllegato(this)"><img src="<%=path%>/themes/default/images/icons/reject.png" alt="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>" title="<spring:message code="pratica.comunicazione.evento.eliminaallegato"/>"><spring:message code="pratica.comunicazione.evento.eliminaallegato"/></a>';
            var empty = '<td></td><td></td>';
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
    <c:if test="${comunicazione.evento.caricaDocumentoFirmato}">
        <h4 class="alertFileFirmati"><spring:message code="pratica.comunicazione.evento.filefirmato"/></h4>    
    </c:if>

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
                <th><spring:message code="pratica.comunicazione.evento.file.tipofile"/></th>
                <th><spring:message code="pratica.comunicazione.evento.file.download"/></th>
                <th><c:if test="${evento.allegatoEmail}"><spring:message code="pratica.comunicazione.evento.allega"/></c:if></th>
                </tr>

            <c:set var="countAllegatiPresenti" value="0" scope="page" />
            <c:forEach items="${comunicazione.allegatiPresenti}" begin="0" var="allegato">
                <tr>
                    <td>${allegato.descrizione}</td>
                    <td>${allegato.nomeFile}</td>
                    <td><span style="text-align: center;">${allegato.tipoFile}</span></td>
                    <td>
                        <span style="text-align: center;"> 
                            <a class="scarica" href="<%=path%>/download.htm?id_file=${allegato.idAllegato}" target="_blank">
                                <spring:message code="pratica.comunicazione.evento.file.download"/>
                            </a>
                        </span>
                    </td>
                    <td>
                        <c:if test="${comunicazione.evento.allegatoEmail}">
                            <span style="text-align: center;">
                                <input type="checkbox" name="allegatiPresenti[${countAllegatiPresenti}].idAllegato" id="allegatoEmail" value="${allegato.idAllegato}" />
                            </span>
                        </c:if>
                    </td>
                </tr>
                <c:set var="countAllegatiPresenti" value="${countAllegatiPresenti + 1}" scope="page"/>
            </c:forEach>

            <c:set var="countAllegatiDaProtocollo" value="0" scope="page" />
            <c:forEach items="${comunicazione.allegatiDaProtocollo}" begin="0"  var="allegatoDaProtocollo">
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
                        <c:if test="${comunicazione.evento.allegatoEmail}">
                            <span style="text-align: center;">
                                <input type="checkbox" name="allegatiDaProtocollo[${countAllegatiDaProtocollo}].allegaAllaMail" id="allegatoEmail" value="${allegatoDaProtocollo.idAllegato}" />
                            </span>
                        </c:if>
                    </td>
                </tr>
                <c:set var="countAllegatiDaProtocollo" value="${countAllegatiDaProtocollo + 1}" scope="page"/>
            </c:forEach>
        </tbody>
    </table>
</div>