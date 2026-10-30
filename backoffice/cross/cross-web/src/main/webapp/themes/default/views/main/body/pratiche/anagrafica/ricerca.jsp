<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="${path}/javascript/generic_autocomplete.js"></script>

<script>
	function changePersonalDataType(){
		
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
	    } else if (tipoAnagrafica == 'F') {
	        $("#search_nome_container").show();
	        $("#search_cognome_container").show();
	        $("#search_denominazione_container").hide();
	        $("#search_partitaiva_container").hide();
	    } else if (tipoAnagrafica == 'P') {
	        $("#search_nome_container").show();
	        $("#search_cognome_container").show();
	        $("#search_denominazione_container").hide();
	        $("#search_partitaiva_container").show();
	    }else if (tipoAnagrafica == 'I') {
	        $("#search_nome_container").hide();
	        $("#search_cognome_container").hide();
	        $("#search_denominazione_container").show();
	        $("#search_partitaiva_container").show();
	    }else if (tipoAnagrafica == '') {
	        $("#search_nome_container").show();
	        $("#search_cognome_container").show();
	        $("#search_denominazione_container").show();
	        $("#search_partitaiva_container").show();
	    }
	    $("#ricerca").accordion("refresh");
	}
    $(document).ready(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            heightStyle: 'auto',
            active: false
        });

        $(document).on("keyup", "[id^='search_cognome']", function(e){
            cleanFunction = function(self){
                self.parent().parent().find("#search_cognome").val('');
            }

            dataJson = function(self, text){
            	var curName = self.parent().parent().find("#search_nome").val();
        		if(curName.length > 0)
        			return {surname: text, name: curName}
        		else
    		    	return {surname: text}
            }
            
            selectionFunction = function (self, event, ui) {
                self.val(ui.item.surname);
                return false;
            }
            
            successFunction = function (item) {
                return {
                    label: item.cognome,
                    surname: item.cognome,
                    name: item.nome,
                    id: item.cognome
                }
            }
            genericAutocomplete($(this), e, "<%= path%>/search/cognome.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });

        $(document).on("keyup", "[id^='search_nome']", function(e){
            cleanFunction = function(self){
                self.parent().parent().find("#search_nome").val('');
            }

            dataJson = function(self, text){
            	var curSurname = self.parent().parent().find("#search_cognome").val();
        		if(curSurname.length > 0)
        			return {name: text, surname: curSurname}
        		else
    		    	return {name: text}
            }
            
            selectionFunction = function (self, event, ui) {
                self.val(ui.item.name);
                return false;
            }
            
            successFunction = function (item) {
                return {
                    label: item.nome,
                    name: item.nome,
                    id: item.cognome
                }
            }
            genericAutocomplete($(this), e, "<%= path%>/search/nome.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });
        
        $(document).on("keyup", "[id^='search_denominazione']", function(e){
        	cleanFunction = function(self){}
        	
        	dataJson = function(self, text){
        		return {company_name: text}
            }
        	
        	selectionFunction = function (self, event, ui) {
            	return true;
            }
        	
        	successFunction = function (item) {
                return {
                    label: item.denominazione,
                    denominazione: item.denominazione,
                    id: item.idTipoPersona
                }
            }
        	genericAutocomplete($(this), e, "<%= path%>/search/ragione_sociale.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });


        $(document).on("keyup", "[id^='search_codice_fiscale']", function(e){
        	
        	cleanFunction = function(self){}

        	dataJson = function(self, text){
        		return {tax_code: text}
            }
        	
        	selectionFunction = function (self, event, ui) {
            	return true;
            }
        	
        	successFunction = function (item) {
                return {
                    label: item.codiceFiscale,
                    denominazione: item.codiceFiscale,
                    id: item.codiceFiscale
                }
            }
        	genericAutocomplete($(this), e, "<%= path%>/search/rubrica/codice_fiscale.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });

        $(document).on("keyup", "[id^='search_partita_iva']", function(e){
        	
        	cleanFunction = function(self){}

        	dataJson = function(self, text){
        		return {vat_number: text}
            }
        	
        	selectionFunction = function (self, event, ui) {
            	return true;
            }
        	
        	successFunction = function (item) {
                return {
                    label: item.partitaIva,
                    denominazione: item.partitaIva,
                    id: item.partitaIva
                }
            }
        	genericAutocomplete($(this), e, "<%= path%>/search/rubrica/partita_iva.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });

        $('#search_tipo_anagrafica').change(changePersonalDataType);

        $("#azzera_button").click(function (event) {
        	event.stopPropagation();
        	$("[id^='search_']").val("");	
        	changePersonalDataType();
        });
        $("#ricerca_button").click(function() {
            $('#list').setGridParam({url: url + "?" + $('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid", [{page: 1}]);
        });
    });
</script>
<div id="ricerca">
    <h3>
    	<a href="#"><spring:message code="ricerca.title"/></a>
    	<span class="buttonHolder operations">
		      <button id="ricerca_button" type="button" class="primaryAction fondo_destra"><spring:message code="ricerca.button.cerca"/></button>
		      <button id="azzera_button" type="button" class="primaryAction fondo_destra"><spring:message code="ricerca.button.azzera"/></button>
	   </span>
    	</h3>
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
            </div>
        </form>

    </div>
</div>