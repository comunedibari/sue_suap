<%
    String path = request.getContextPath();
    String url = path + "/processi/salvaProcesso.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div>
    <tiles:insertAttribute name="body_error" />
<div  class="uniForm ">

    <!-- <h2 style="text-align: center"><spring:message code="processo.modifica.title"/>100</h2> -->
    <div class="sidebar_auto">
    	<div class="page-control" data-role="page-control">
            <span class="menu-pull"></span> 
            <div class="menu-pull-bar"></div>
            <!-- Etichette cartelle -->
            <ul>
                <li class="active"><a href="#frame1"><spring:message code="processo.modifica.title"/></a></li>
            </ul>
            <!-- Contenuto cartelle -->
            <div class="frames">
                <div class="frame active" id="frame1">
                <form id="modificaform" action="<%=url%>" class="uniForm inlineLabels" method="post">
                    <input name="idProcesso" id="idProcesso" type="hidden" value="${processo.idProcesso}">
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <label for="codProcesso" class="required"><spring:message code="processo.codice"/></label>
                            <input name="codProcesso" id="codProcesso" maxlength="255" type="text" class="textInput required" value="${processo.codProcesso}">
                            <p class="formHint"><spring:message code="eventi.processo.obbligatorio"/></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="desProcesso" class="required"><spring:message code="processo.descrizione"/></label>
                            <input name="desProcesso" id="desProcesso" maxlength="255" type="text" class="textInput required" value="${processo.desProcesso}">
                            <p class="formHint"><spring:message code="eventi.processo.obbligatorio"/></p>
                        </div>
                    </div>
            
                    <div class="buttonHolder">
                        <a href="<%=path%>/processi/lista.htm" class="secondaryAction">&larr; <spring:message code="processo.button.indietro"/></a>
                        <button onclick="eliminaProcesso(); return false;" class="cancella_generico"><spring:message code="processo.button.cancella"/></button>
                        <button type="submit" name="submitaction" value="<spring:message code="processo.button.salva"/>" class="primaryAction"><spring:message code="processo.button.salva"/></button>
                    </div>
                </form>
                
                </div>
                
            </div>
        <!-- fine cartelle -->
    	</div>
    <div class="clear"></div>    
	</div>
</div>
</div>

                    <script>
    function eliminaProcesso() {
        var div = $("<div>"); 
        div.html("Confermi di voler eliminare questo processo?")
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma eliminazione processo",
                    buttons: {
                        Ok: function() {
                            var form = $("#modificaform");
                             form.append($('<input>',
                                    {
                                        'name': 'submitaction',
                                        'value': "<spring:message code="processo.button.cancella"/>",
                                        'type': 'hidden'
                                    }));
                            form.submit();
                            $(div).dialog('close');
                        },
                        Annulla: function() {
                            $(this).dialog('close');
                        }
                    }
                });
    }

</script>
