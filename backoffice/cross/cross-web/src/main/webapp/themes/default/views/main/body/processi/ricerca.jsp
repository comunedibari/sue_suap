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
            //^^CS ELIMINA provoca questa stringa ..../dettaglio_search.htmsearch_id_pratica=asd&search_ente...
            //$('#list').setGridParam({url:url+$('#form_ricerca').serialize()});
            var urli = $('#list').getGridParam("url").split("?")[0];
            $('#list').setGridParam({url:urli+"?"+$('#form_ricerca').serialize()});
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
            <div style="width:1080px;">    
            <div class="ricerca_div_forms">   
                <div class="inlineLabels">
                    <div class="ctrlHolder">
                        <label for="search_id_pratica"><spring:message code="ricerca.identificativopratica"/></label>
                        <input id="search_id_pratica" name="search_id_pratica" size="35" maxlength="50" type="text" class="textInput" value="">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder">
                        <label for="search_ente"><spring:message code="ricerca.ente"/></label>
                        <select id="search_ente" name="search_ente" class="textInput">
                            <option  value="" ><spring:message code="ricerca.entestart"/></option>
                            <c:forEach items="${entiRicerca}" var="ente" begin="0">
                                <option  value="${ente.idEnte}">${ente.descrizione}</option>
                            </c:forEach>
                        </select>
                        <p class="formHint"></p>
                    </div>
                </div>    
            </div>
            <div class="ricerca_div_forms">    
                <div class="inlineLabels">
                    <div class="ctrlHolder">
                        <label for="search_stato"><spring:message code="ricerca.statopratica"/></label>
                        <select id="search_stato" name="search_stato" class="textInput">
                            <option  value="" ><spring:message code="ricerca.statopraticastart"/></option>
                            <c:forEach items="${statoPraticaRicerca}" var="stato" begin="0">
                                <option value="${stato.idStatoPratica}">${stato.descrizione}</option>
                            </c:forEach>
                        </select>
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder">
                        <label for="search_data_from"><spring:message code="ricerca.datainizio"/></label>
                        <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput" value="">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder">
                        <label for="search_data_to"><spring:message code="ricerca.datafine"/></label>
                        <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput" value="">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder">
                        <label for="search_all"><spring:message code="ricerca.tuttiutenti"/><input id="search_all" name="search_all" type="checkbox" class="textInput checkbox_width" value="ok" /></label>
                        
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