<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        <form id="form_ricerca" action="<%= path%>/gestione/eventoTemplate/lista/ajax.htm" class="uniForm inlineLabels" method="post">
           <input type="hidden" name="tipoFiltro" value="utente"/>
            <div style="width:1080px;">    
            <div class="ricerca_div_forms">   
                <div class="inlineLabels">
                    <div class="ctrlHolder">
                    <label for="search_stato">Descrizione Template</label>
                        <input type="text" name="descrizioneTemplate" class="textInput required" value="${filtroRicerca.descrizioneTemplate}"/>
                    <p class="formHint"></p>
                </div>
            	</div>
                
            </div>
            <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>				
            </div>
        </div>
        </form>
    </div>
</div>