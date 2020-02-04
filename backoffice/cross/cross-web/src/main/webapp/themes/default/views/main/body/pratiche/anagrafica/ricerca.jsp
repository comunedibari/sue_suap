<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
    $(document).ready(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            heightStyle: 'auto',
            active: false
        });

        $('#search_tipo_anagrafica').change(function() {
            var tipoAnagrafica = $('#search_tipo_anagrafica').val();
            $('#search_nome').val('');
            $('#search_cognome').val('');
            $('#search_partita_iva').val('');
            $('#search_denominazione').val('');
            if (tipoAnagrafica == 'G') {
                $("#search_nome_container").hide();
                $("#search_cognome_container").hide();
                $("#search_denominazione_container").show();
                $("#search_partitaiva_container").show();
                $("#ricerca").accordion("resize");
            } else if (tipoAnagrafica == 'F') {
                $("#search_nome_container").show();
                $("#search_cognome_container").show();
                $("#search_denominazione_container").hide();
                $("#search_partitaiva_container").hide();
                $("#ricerca").accordion("resize");
            } else if (tipoAnagrafica == 'P') {
                $("#search_nome_container").show();
                $("#search_cognome_container").show();
                $("#search_denominazione_container").hide();
                $("#search_partitaiva_container").show();
                $("#ricerca").accordion("resize");
            }else if (tipoAnagrafica == '') {
                $("#search_nome_container").show();
                $("#search_cognome_container").show();
                $("#search_denominazione_container").show();
                $("#search_partitaiva_container").show();
                $("#ricerca").accordion("resize");
            }
        });

        $("#ricerca_button").click(function() {
            $('#list').setGridParam({url: url + "?" + $('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid", [{page: 1}]);
        });
    });
</script>
<div id="ricerca">
    <h3><a href="#"><spring:message code="ricerca.title"/></a></h3>
    <div>
        <form id="form_ricerca" action="<%= path%>/pratiche/comunicazione/dettaglio_search.htm" class="uniForm inlineLabels" method="post">
            <input name="tipoFiltro" type="hidden" value="utente" />
            <div style="width:1080px;">             
                <div class="ricerca_div_forms">    
                    <div class="inlineLabels">
                        <fieldset id="richiedentiBeneficiariField">
                              <legend><spring:message code="ricerca.title.anagrafica"/></legend>
                            <input type="hidden" name="dontsearch" value="">
                            <div class="ctrlHolder" id="search_tipoAnagrafica_container">
                                <label for="search_tipo_anagrafica"><spring:message code="ricerca.tipologiaanagrafica"/></label>
                                <select name="search_tipo_anagrafica" id="search_tipo_anagrafica">
                                     <option value="" <c:if test="${anagrafica.tipoAnagrafica == ''}">selected="selected"</c:if> ><spring:message code="ricerca.tipologiaanagrafica.tutti"/></option>
                                    <option value="F" <c:if test="${anagrafica.tipoAnagrafica == 'F'}">selected="selected"</c:if> ><spring:message code="ricerca.tipologiaanagrafic.pf"/></option>
                                    <option value="G" <c:if test="${anagrafica.tipoAnagrafica == 'G'}">selected="selected"</c:if> ><spring:message code="ricerca.tipologiaanagrafic.pg"/></option>
<!--                                   Constants.PERSONA_DITTAINDIVIDUALE="I"-->
                                        <option value="I" <c:if test="${anagrafica.tipoAnagrafica == 'P'}">selected="selected"</c:if> ><spring:message code="ricerca.tipologiaanagrafic.di"/></option>
                                     
                                    </select>
                                </div>
                                <div class="ctrlHolder" id="search_cognome_container" >
                                    <label for="search_cognome"><spring:message code="ricerca.cognome"/></label>
                                <input id="search_cognome" name="search_cognome" size="35" maxlength="255" type="text" class="textInput" value="${filtroRicerca.cognome}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="search_nome_container" >
                                <label for="search_nome"><spring:message code="ricerca.nome"/></label>
                                <input id="search_nome" name="search_nome" size="35" maxlength="255" type="text" class="textInput" value="${filtroRicerca.nome}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="search_denominazione_container" >
                                <label for="search_denominazione"><spring:message code="ricerca.denominazione"/></label>
                                <input id="search_denominazione" name="search_denominazione" size="35" maxlength="255" type="text" class="textInput" value="${filtroRicerca.denominazione}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="search_codice_fiscale_container">
                                <label for="search_codice_fiscale"><spring:message code="ricerca.codicefiscale"/></label>
                                <input id="search_codice_fiscale" name="search_codice_fiscale" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.codiceFiscale}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="search_partitaiva_container" >
                                <label for="search_partita_iva"><spring:message code="ricerca.partitaiva"/></label>
                                <input id="search_partita_iva" name="search_partita_iva" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.partitaIva}">
                                <p class="formHint"></p>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                    <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>				
                </div>
            </div>
        </form>

    </div>
</div>