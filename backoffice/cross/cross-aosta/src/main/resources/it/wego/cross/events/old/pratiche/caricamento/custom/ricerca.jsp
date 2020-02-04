<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function(){
        $( "#ricerca" ).accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });
        
        $("#ricerca_button").click(function() {
            $('#list').setGridParam({url:url+"?"+$('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid",[{page:1}]);
        });
        
        $("#search_data_from").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        
        $("#search_data_to").datepicker({
            dateFormat: 'dd/mm/yy'
        });
    });
</script>
<div id="ricerca">
    <h3><a href="#"><spring:message code="ricerca.title"/></a></h3>
    <div>
        <form id="form_ricerca" action="<%= path%>/pratiche/comunicazione/dettaglio_search.htm" class="uniForm inlineLabels" method="post">
            <input name="tipoFiltro" type="hidden" value="utente" />
            <div class="inlineLabels">  
                <div class="ctrlHolder">
                    <label for="search_id_pratica">Numero di protocollo</label>
                    <input id="search_id_pratica" name="search_id_pratica" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.numeroProtocollo}">
                    <p class="formHint"></p>
                </div>
                <div class="ctrlHolder">
                    <label for="search_fascicolo">Numero fascicolo</label>
                    <input id="search_fascicolo" name="search_fascicolo" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.numeroFascicolo}">
                    <p class="formHint"></p>
                </div>
                <div class="ctrlHolder">
                    <label for="search_oggetto">Oggetto</label>
                    <input id="search_oggetto" name="search_oggetto" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.oggetto}">
                    <p class="formHint"></p>
                </div>
                <div class="ctrlHolder">
                    <label for="search_data_from">Da</label>
                    <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${filtroRicerca.dataInizio}"/>">
                    <p class="formHint"></p>
                </div>
                <div class="ctrlHolder">
                    <label for="search_data_to">A</label>
                    <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${filtroRicerca.dataFine}"/>">
                    <p class="formHint"></p>
                </div>
                <div class="ctrlHolder">
                    <label for="search_all">Visualizza tutte le pratiche</label>
                    <input id="search_all" name="search_all" type="checkbox" class="textInput" value="ok" />
                    <p class="formHint"></p>
                </div>
            </div>
            <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                <button id="ricerca_button" type="button" class="primaryAction" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>
            </div>
        </form>
    </div>
</div>