<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String path = request.getContextPath();
    String url = path + "/ente/aggiungi.htm";
%>
<tiles:insertAttribute name="body_error" />

<div>
    <div  class="uniForm ">
        <div class="sidebar_auto">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="ente.aggiungi"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <form:form action="<%=url%>" class="uniForm inlineLabels" method="post" commandName="ente">
                            <div style="float:left; width:520px;">
                                <div class="inlineLabels">
                                    <input type="hidden" name="action" value="add">
                                    <div class="ctrlHolder">
                                        <form:label path="tipoEnte" class="required">Tipologia</form:label>
                                        <form:select path="tipoEnte">
                                            <form:option value="ENTE ESTERNO" label="Ente esterno"/>
                                            <form:option value="ENTE INTERNO" label="Ente interno"/>
                                            <form:option value="SUAP" label="Sportello"/>
                                        </form:select>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>   
                                    <div class="ctrlHolder">
                                        <form:label path="descrizione" class="required"><spring:message code="ente.descrizione"/></form:label>
                                        <form:input path="descrizione" class="textInput required" maxlength="255"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="codiceFiscale" class="required"><spring:message code="ente.codiceFiscale"/></form:label>
                                        <form:input path="codiceFiscale" class="textInput required" maxlength="20"/>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="partitaIva" class="required"><spring:message code="ente.partitaIva"/></form:label>
                                        <form:input path="partitaIva" class="textInput required" maxlength="255"/>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="indirizzo" class="required"><spring:message code="ente.indirizzo"/></form:label>
                                        <form:input path="indirizzo" class="textInput required" maxlength="255"/>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="cap" class="required"><spring:message code="ente.cap"/></form:label>
                                        <form:input path="cap" maxlength="5" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="citta" class="required"><spring:message code="ente.citta"/></form:label>
                                        <form:input path="citta" maxlength="255" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="provincia" class="required"><spring:message code="ente.provincia"/></form:label>
                                        <form:input path="provincia" maxlength="4" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="unitaOrganizzativa" class="required"><spring:message code="ente.unitaOrganizzativa"/></label>
                                        <input name="unitaOrganizzativa" id="unitaOrganizzativa" maxlength="5" type="text" class="textInput" value="${ente.unitaOrganizzativa}"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/> (se lasciato vuoto il codice ente sarà usato)</p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="codiceAmministrazione" class="required"><spring:message code="ente.codiceAmministrazione"/></label>
                                        <input name="codiceAmministrazione" id="codiceAmministrazione" maxlength="5" type="text" class="textInput" value="${ente.codiceAmministrazione}"/>
                                    </div>
                                </div>
                            </div>
                            <div style="float:left; width:520px; margin-left:10px;">    
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <form:label path="codEnte" class="required"><spring:message code="ente.codice"/></form:label>
                                        <form:input path="codEnte" maxlength="255" class="textInput required" />
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="codiceAoo" class="required"><spring:message code="ente.codiceaoo"/></form:label>
                                        <form:input path="codiceAoo" maxlength="255" class="textInput required"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="telefono" class="required"><spring:message code="ente.telefono"/></form:label>
                                        <form:input path="telefono" maxlength="30" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="fax" class="required"><spring:message code="ente.fax"/></form:label>
                                        <form:input path="fax" maxlength="30" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="email" class="required"><spring:message code="ente.email"/></form:label>
                                        <form:input path="email" maxlength="255" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="pec" class="required"><spring:message code="ente.pec"/></form:label>
                                        <form:input path="pec" maxlength="255" class="textInput required" />
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="codiceIstat" class="required"><spring:message code="ente.codiceistat"/></form:label>
                                        <form:input path="codiceIstat" maxlength="5" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="codiceCatastale" class="required"><spring:message code="ente.codicebelfiore"/></form:label>
                                        <form:input path="codiceCatastale" maxlength="5" class="textInput required" />
                                    </div>
                                    <div class="ctrlHolder">
                                        <form:label path="identificativoSuap" class="required"><spring:message code="ente.identificativosuap"/></form:label>
                                        <form:input path="identificativoSuap" maxlength="5" class="textInput required" />
                                    </div>
                                </div>
                            </div>
                            <div class="buttonHolder">
                                <a href="<%=path%>/ente/index.htm" class="secondaryAction">&larr; Indietro</a>
                                <button type="submit" class="primaryAction"><spring:message code="ente.button.nuovo"/></button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
