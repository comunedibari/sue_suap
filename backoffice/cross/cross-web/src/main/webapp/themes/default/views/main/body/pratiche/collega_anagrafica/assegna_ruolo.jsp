<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String url = path + "/pratica/dettaglio/collegaAnagrafica/conferma.htm";
%>
<script type="text/javascript">
    $(document).ready(function(){
        var tipoAnagrafica = '${anagrafica.tipologia}';
        $('#ruoli').change(function(){
            $("#ruoli option:selected").each(function () {
                var param = $(this).text();
                if (tipoAnagrafica == 'F' && param.toUpperCase().indexOf('RICHIEDENTE') != -1){
                    $('.qualificaRichiedente').show();
                } else {
                    $('#qualificaRichiedente').val(0);
                    $('.qualificaRichiedente').hide();
                }
                if (tipoAnagrafica == 'F' && param.toUpperCase().indexOf('TECNICO') != -1){
                    $('.qualificaTecnico').show();
                } else {
                    $('#qualificaTecnico').val(0);
                    $('.qualificaTecnico').hide();
                }
            });
        });
    });
</script>
<tiles:insertAttribute name="body_error" />
<form:form action="<%=url%>" class="uniForm inlineLabels comunicazione" method="post" commandName="anagrafica">
    <form:hidden path="idAnagrafica" />
    <div class="inlineLabels">
        <div class="personaFisica">
            <fieldset class="fieldsetComunicazione">
                <legend id="legendPersonaFisica"><spring:message code="pratica.collegaAnagrafica.riepilogo"/></legend>
                <div>
                    <c:if test="${anagrafica.tipologia == 'F'}">
                        <div class="ctrlHolder">
                            <label for="nome"><spring:message code="pratica.collegaAnagrafica.nome"/></label>
                            <span id="nome" class="value">${anagrafica.nome}</span>
                        </div>
                        <div class="ctrlHolder">
                            <label for="cognome"><spring:message code="pratica.collegaAnagrafica.cognome"/></label>
                            <span id="cognome" class="value">${anagrafica.cognome}</span>
                        </div>
                    </c:if>
                    <c:if test="${anagrafica.tipologia == 'G' or anagrafica.variante == 'I'}">
                        <div class="ctrlHolder">
                            <label for="denominazione"><spring:message code="pratica.collegaAnagrafica.denominazione"/></label>
                            <span id="denominazione" class="value">${anagrafica.denominazione}</span>
                        </div>
                    </c:if>
                    <div class="ctrlHolder">
                        <label for="codiceFiscale"><spring:message code="pratica.collegaAnagrafica.codicefiscale"/></label>
                        <span id="codiceFiscale" class="value">${anagrafica.codiceFiscale}</span>
                    </div>
                    <c:if test="${anagrafica.tipologia == 'G' or anagrafica.variante == 'I'}">
                        <div class="ctrlHolder">
                            <label for="partitaIva"><spring:message code="pratica.collegaAnagrafica.partitaiva"/></label>
                            <span id="partitaIva" class="value">${anagrafica.partitaIva}</span>
                        </div>
                    </c:if>
                    <c:if test="${anagrafica.variante == 'I'}">
                        <div class="ctrlHolder">
                            <label for="dittaIndividuale"><spring:message code="pratica.collegaAnagrafica.dittaIndividuale"/></label>
                            <form:select path="dittaIndividuale" id="dittaIndividuale">
                                <form:option value="S" label="Si"/>
                                <form:option value="N" label="No"/>
                            </form:select>
                        </div>
                    </c:if>
                    <div class="ctrlHolder">
                        <label for="ruoli"><spring:message code="pratica.collegaAnagrafica.ruolo"/></label>
                        <form:select path="ruolo" id="ruoli">
                            <form:options items="${ruoli}" />
                        </form:select>
                    </div>
                    <div class="ctrlHolder qualificaRichiedente" style="display:none">
                        <label for="qualificaRichiedente"><spring:message code="pratica.collegaAnagrafica.qualifica"/></label>
                        <form:select path="qualificaRichiedente" id="qualificaRichiedente">
                            <form:options items="${qualificheRichiedente}" />
                        </form:select>
                    </div>
                    <div class="ctrlHolder qualificaTecnico" style="display:none">
                        <label for="qualificaTecnico"><spring:message code="pratica.collegaAnagrafica.qualifica"/></label>
                        <form:select path="qualificaTecnico" id="qualificaTecnico">
                            <form:options items="${qualificheTecnico}" />
                        </form:select>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>
    <div class="buttonHolder">
        <button type="submit" class="primaryAction"><spring:message code="pratica.collegaAnagrafica.salva"/></button>
 <a href="<%=path%>/pratica/dettaglio/collegaAnagrafica.htm?daPraticaNuova=${daPraticaNuova}" class="secondaryAction"/>&larr; <spring:message code="pratica.button.indietro"/>
    </div>
<input type="hidden" name ="daPraticaNuova" value="${daPraticaNuova}">
</form:form>