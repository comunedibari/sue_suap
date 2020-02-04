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
            heightStyle: 'auto',
            active: false
        });
                
        $("#ricerca_button").click(function() {
            $('#list').setGridParam({url:url+"?"+$('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid",[{page:1}]);
        });
    });
</script>
<div id="ricerca">
    <h3><a href="#"><spring:message code="ricerca.title"/></a></h3>
    <div>
        <form id="form_ricerca" class="uniForm inlineLabels" method="post">
            <input name="tipoFiltro" type="hidden" value="procedimento" />
            <div class="inlineLabels">
                <input type="hidden" name="dontsearch" value="">
                <div class="ctrlHolder" id="search_desEnte_container" >
                    <label for="procedimento"><spring:message code="ricerca.procedimento"/></label>
                    <input id="search_desEnte" name="procedimento" size="35" maxlength="255" type="text" class="textInput" value="${ricerca.procedimento}">
                    <p class="formHint"></p>
                </div>
            </div>
            <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                <button id="ricerca_button" type="button" class="primaryAction" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>
            </div>
        </form>
    </div>
</div>