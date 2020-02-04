<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String path = request.getContextPath();
    String url = path + "/gestione/procedimenti/salva.htm";
%>
<script>
    $(function () {
        $("#allineaClear").on("click", function () {
            var div = $("<div>");
            div.html("Confermi di voler sincronizzare il procedimento su clear (la funzionalita' va fatta se il procedimento è gia' stato collegato a qualche ente) ?")
            $(div).dialog(
                    {
                        modal: true,
                        title: "Conferma sincronizzazione",
                        buttons: {
                            Ok: function () {
                                var form = $("#submitForm");
                                form.append($('<input>',
                                    {
                                        'name': 'allineaClear',
                                        'value': 'allineaClear',
                                        'type': 'hidden'
                                    }));
                                form.submit();
                                $(div).dialog('close');
                            },
                            Annulla: function () {
                                $(this).dialog('close');
                            }
                        }
                    });
                    return false;
        });
    });

</script>
<tiles:insertAttribute name="body_error" />
<div>
    <div  class="uniForm ">
        <div class="sidebar_auto">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="procedimento.modifica"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <form:form id="submitForm" action="<%=url%>" class="uniForm inlineLabels" method="post" commandName="procedimento">
                            <form:hidden path="idProcedimento"/>
                            <div style="float:left; width:520px;">
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <form:label path="desProcedimento" class="required"><spring:message code="procedimento.descrizione"/></form:label>
                                        <form:input path="desProcedimento" class="textInput required" maxlength="255"/>
                                        <form:hidden path="codLang" />
                                        <p class="formHint"><spring:message code="common.field.mandatory"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="codProcedimento" class="required"><spring:message code="procedimento.codProcedimento"/></form:label>
                                        <form:input path="codProcedimento" class="textInput required" maxlength="50"/>
                                        <p class="formHint"><spring:message code="common.field.mandatory"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="tipoProc" class="required"><spring:message code="procedimento.tipoProc"/></form:label>
                                        <form:input path="tipoProc" class="textInput required" maxlength="10"/>
                                        <p class="formHint"><spring:message code="common.field.mandatory"/></p>
                                    </div>
                                </div>
                            </div>
                            <div style="float:left; width:520px;">
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <form:label path="termini" class="required"><spring:message code="procedimento.termini"/></form:label>
                                        <form:input path="termini" class="textInput required" maxlength="10"/>
                                        <p class="formHint"><spring:message code="common.field.mandatory"/></p>
                                    </div>
                                </div>
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <form:label path="classifica" class="required"><spring:message code="procedimento.classifica"/></form:label>
                                        <form:input path="classifica" class="textInput required" maxlength="255"/>
                                        <p class="formHint">&nbsp;</p>
                                    </div>
                                </div>
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <form:label path="peso" class="required"><spring:message code="procedimento.peso"/></form:label>
                                        <form:input path="peso" class="textInput required" maxlength="10"/>
                                        <p class="formHint">&nbsp;</p>
                                    </div>
                                </div>
                            </div>
                            <div class="buttonHolder">
                                <a href="<%=path%>/gestione/procedimenti/lista.htm" class="secondaryAction">&larr; <spring:message code="common.button.indietro"/></a>
                                <button type="submit" name="aggiorna" id="aggiorna" class="primaryAction"><spring:message code="procedimento.salva"/></button>
                                <c:if test='${clearActive}'>
                                    <button type="submit" name="allineaClear" id="allineaClear" class="primaryAction"><spring:message code="procedimento.clear.update"/></button>
                                </c:if>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>