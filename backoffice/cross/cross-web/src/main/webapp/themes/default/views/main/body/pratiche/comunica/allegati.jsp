<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ctrlHolder dettaglio_liv_0">
    <script>
        
        var j=$('.nuovoAllegato').length;
        function aggiungiAllegato(){
            var description = '<td><input class="obbligatorio" type="text" name="allegati['+j+'].descrizione" /><br /><span class="formHint">Campo obbligatorio</span></td>';
            var file = '<td><input type="file" name="allegati['+j+'].file" class="fileObbligatorio"/><br /><span class="formHint">Campo obbligatorio</span></td>';
            var checkModello = '<td><input type="hidden" name="allegati['+j+'].idAllegato" value="'+j+'" /><input type="radio" name="idAllegatoPratica" value="'+j+'"/></td>';
            var remove = '<a class="aggiungiAllegato" onclick="rimuoviAllegato(this)"><img src="<%= request.getContextPath()%>/themes/default/images/icons/reject.png" alt="<spring:message code="comunica.allegati.elimina"/>" title="<spring:message code="comunica.allegati.elimina"/>"><spring:message code="comunica.allegati.elimina"/></a>';
            var checkRemove = '<td>' + remove + '</td>';
            var row = '<tr class="nuovoAllegato">' + $('#allegatiEmailTable tr') + '">'+description + file + checkModello + checkRemove+'</tr>';
            $("#allegatiEmailTable > tbody").append(row);
            j++;
        }
        
        function rimuoviAllegato(row){
            $(row.parentNode.parentNode).remove();
            j = $('.nuovoAllegato').length;
            for (var i=0; i<j; i++) { 
                $('.nuovoAllegato')[i].value = i; 
            }
        }
        
        
    </script>

    <div class="aggiungiAllegato">
        <a onclick="aggiungiAllegato()" class="crea_nuovo_evento">
            <spring:message code="comunica.allegati.aggiungi"/>
        </a>
    </div>
    <table cellspacing="0" cellpadding="0" class="master" id="allegatiEmailTable">
        <tbody>
            <tr>
                <th><spring:message code="comunica.allegati.descrizione"/></th>
                <th><spring:message code="comunica.allegati.nomefile"/></th>
                <th><spring:message code="comunica.allegati.ismodello"/></th>
                <th><spring:message code="comunica.allegati.azione"/></th>
            </tr>
        </tbody>
    </table>
</div>