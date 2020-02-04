<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
    String path = request.getContextPath();
%>
<tiles:insertAttribute name="body_error" />
<script>
    $(function(){
        $("button[value='Modifica ente']").on("click", function(){
            if( $("input[name='codEnteHidden']").val()!==$("input[name='codEnte']").val()){
              c=  confirm("Si sta cercando di modificare il codice di questo ente. Controllare che la modifica risulti allineata rispetto alle pratiche ricevute dal FO.");
            if(c){return true; }else{return false; };
        }else{ 
            return true;
        }
        return false;
        });
    });
    </script>
                                        <div id="mess"></div>
<div>
    <div  class="uniForm ">
        <div>
            <tiles:insertAttribute name="operazioneRiuscita" /> 
        </div>
        <div class="sidebar_auto">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="ente.modifica.title"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <form action="<%=path%>/ente/modifica.htm" class="uniForm inlineLabels" method="post">

                            <div style="float:left; width:520px;">
                                <div class="inlineLabels">
                                    <input type="hidden" name="action" value="modify"/>
                                    <input name="idEnte" id="idEnte" maxlength="255" type="hidden" class="textInput required" value="${ente.idEnte}"/>
                                    <div class="ctrlHolder">
                                        <label for="descrizione" class="required">Tipologia</label>
                                        <select name="tipoEnte">
                                            <option value="ENTE ESTERNO" <c:if test="${ente.tipoEnte == 'ENTE ESTERNO'}"> selected="selected"</c:if>>Ente esterno</option>
                                            <option value="ENTE INTERNO" <c:if test="${ente.tipoEnte == 'ENTE INTERNO'}"> selected="selected"</c:if>>Ente interno</option>
                                            <option value="SUAP" <c:if test="${ente.tipoEnte == 'SUAP'}"> selected="selected"</c:if>>Sportello</option>
                                            </select>
                                            <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>              
                                    <div class="ctrlHolder">
                                        <label for="descrizione" class="required"><spring:message code="ente.descrizione"/></label>
                                        <input name="descrizione" id="descrizione" maxlength="255" type="text" class="textInput required" value="${ente.descrizione}"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="codiceFiscale" class="required"><spring:message code="ente.codiceFiscale"/></label>
                                        <input name="codiceFiscale" id="codiceFiscale" maxlength="255" type="text" class="textInput required" value="${ente.codiceFiscale}"/>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="partitaIva" class="required"><spring:message code="ente.partitaIva"/></label>
                                        <input name="partitaIva" id="partitaIva" maxlength="255" type="text" class="textInput required" value="${ente.partitaIva}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="indirizzo" class="required"><spring:message code="ente.indirizzo"/></label>
                                        <input name="indirizzo" id="indirizzo" maxlength="255" type="text" class="textInput" value="${ente.indirizzo}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="cap" class="required"><spring:message code="ente.cap"/></label>
                                        <input name="cap" id="cap" maxlength="5" type="text" class="textInput required" value="${ente.cap}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="citta" class="required"><spring:message code="ente.citta"/></label>
                                        <input name="citta" id="citta" maxlength="255" type="text" class="textInput" value="${ente.citta}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="provincia" class="required"><spring:message code="ente.provincia"/></label>
                                        <input name="provincia" id="provincia" maxlength="4" type="text" class="textInput" value="${ente.provincia}"/>
                                    </div>

                                </div>
                            </div>
                            <div style="float:left; width:520px;">
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <label for="codEnte" class="required"><spring:message code="ente.codice"/></label>
                                        <input name="codEnte" maxlength="255" id="descrizione" maxlength="255" type="text" class="textInput required" value="${ente.codEnte}"/>
                                        <input name="codEnteHidden" maxlength="255" id="descrizione" maxlength="255" type="text" class="textInput required" value="${ente.codEnte}" style="display:none;"/>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="codiceAoo" class="required"><spring:message code="ente.codiceaoo"/></label>
                                        <input name="codiceAoo" maxlength="255" id="descrizione" maxlength="255" type="text" class="textInput required" value="${ente.codiceAoo}"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="telefono" class="required"><spring:message code="ente.telefono"/></label>
                                        <input name="telefono" id="telefono" maxlength="30" type="text" class="textInput" value="${ente.telefono}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="fax" class="required"><spring:message code="ente.fax"/></label>
                                        <input name="fax" id="fax" maxlength="30" type="fax" class="textInput required" value="${ente.fax}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="email" class="required"><spring:message code="ente.email"/></label>
                                        <input name="email" id="email" maxlength="255" type="text" class="textInput" value="${ente.email}"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/> (in caso la pec non sia presente)</p>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="pec" class="required"><spring:message code="ente.pec"/></label>
                                        <input name="pec" id="pec" maxlength="255" type="text" class="textInput" value="${ente.pec}"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/></p>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="codiceIstat" class="required"><spring:message code="ente.codiceistat"/></label>
                                        <input name="codiceIstat" id="codiceIstat" maxlength="5" type="text" class="textInput" value="${ente.codiceIstat}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="codiceCatastale" class="required"><spring:message code="ente.codicebelfiore"/></label>
                                        <input name="codiceCatastale" id="codiceCatastale" maxlength="5" type="text" class="textInput" value="${ente.codiceCatastale}"/>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label for="identificativoSuap" class="required"><spring:message code="ente.identificativosuap"/></label>
                                        <input name="identificativoSuap" id="codiceCatastale" type="text" class="textInput" value="${ente.identificativoSuap}"/>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="unitaOrganizzativa" class="required"><spring:message code="ente.unitaOrganizzativa"/></label>
                                        <input name="unitaOrganizzativa" id="unitaOrganizzativa"  type="text" class="textInput" value="${ente.unitaOrganizzativa}"/>
                                        <p class="formHint"><spring:message code="ente.campo.obbligatorio"/> (di default il codice ente)</p>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label for="codiceAmministrazione" class="required"><spring:message code="ente.codiceAmministrazione"/></label>
                                        <input name="codiceAmministrazione" id="codiceAmministrazione"  type="text" class="textInput" value="${ente.codiceAmministrazione}"/>
                                    </div>
                                </div>
                            </div>

                            <div class="buttonHolder">

                                <a href="<%=path%>/ente/index.htm" class="secondaryAction">&larr; Indietro</a>
                                <button type="submit" name="submit" class="primaryAction" value="<spring:message code="ente.button.modifica"/>"><spring:message code="ente.button.modifica"/></button>
                                <button type="submit" name="submit" class="primaryAction" value="<spring:message code="ente.button.procedimenti.collega"/>"><spring:message code="ente.button.procedimenti.collega"/></button>
                                <button type="submit" name="submit" class="primaryAction" value="<spring:message code="ente.button.comuni.collega"/>"><spring:message code="ente.button.comuni.collega"/></button>
                                <c:if test='${isSuap == "SUAP"}'>
                                    <button type="submit" name="submit" class="primaryAction" value="<spring:message code="ente.button.endoprocedimenti"/>"><spring:message code="ente.button.endoprocedimenti"/></button>
                                </c:if>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



