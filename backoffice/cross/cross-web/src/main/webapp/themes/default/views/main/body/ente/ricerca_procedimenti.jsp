<%@page import="it.wego.cross.constants.SessionConstants"%>
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
        
    });
</script>

<div id="ricerca">
    <h3><a href="#"><spring:message code="ente.comuni.ricerca.titolo"/></a></h3>
    <div>
        <form id="form_ricerca" action="<%=path%>/ente/procedimenti.htm" class="uniForm inlineLabels" method="post">
            <div style="width:1080px;">    
                <div class="ricerca_div_forms">  
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <label for="procedimento"><spring:message code="ente.procedimenti.nome"/></label>
                            <input id="procedimento" name="procedimento" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.procedimento}"/>
                        </div>
                        <div class="ctrlHolder">
                            <label for="statoProcedimento"><spring:message code="ente.procedimenti.stato"/></label>
                            <select name="statoProcedimento">
                                <option value="" selected="selected"><spring:message code="ente.procedimenti.stato.all"/></option>
                                <option value="abilitato"><spring:message code="ente.procedimenti.stato.abilitato"/></option>
                                <option value="nonabilitato"><spring:message code="ente.procedimenti.stato.nonabilitato"/></option>
                            </select>
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