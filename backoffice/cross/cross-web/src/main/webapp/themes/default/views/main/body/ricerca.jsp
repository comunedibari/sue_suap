<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${path}/javascript/generic_autocomplete.js"></script>
<script type="text/javascript">
    function disableIdSearchComune(type) {
        $("#search_id_comune_" + type).val('');
        $("#search_id_comune_" + type).attr('disabled', 'disabled');
    }

    function enableIdSearchComune(type) {
        $("#search_id_comune_" + type).removeAttr('disabled');
    }

    function disableSearchComuneComponent(type) {
        $("#search_des_comune_" + type).attr('disabled', 'disabled');
        $("#search_button_" + type).attr('disabled', 'disabled');
        disableIdSearchComune(type);
    }

    function enableSearchComuneComponent(type) {
        $("#search_des_comune_" + type).removeAttr('disabled');
        $("#search_button_" + type).removeAttr('disabled');
        enableIdSearchComune(type);
    }

    function addBeneficiaryDiv(){
		$("#divRichiedentiBeneficiariField").append($("#richiedentiBeneficiariTemplate").html());
		$("#divRichiedentiBeneficiariField > #richiedentiBeneficiario").last().each(function( index ) {
			$(this).find("[id='delete_anagrafica_btn'], [id='add_anagrafica_btn']").attr("idx", $("#divRichiedentiBeneficiariField > #richiedentiBeneficiario").length);
		});
		handleButtonsVisibility("divRichiedentiBeneficiariField", "richiedentiBeneficiario","add_anagrafica_btn","delete_anagrafica_btn");
	}
    
    function addCadastralAddressDiv(){
		$("#divCatastoIndirizzoFields").append($("#CatastoIndirizzoFieldsTemplate").html());
		$("#divCatastoIndirizzoFields #CatastoIndirizzo").last().each(function( index ) {
			$(this).find("[id='delete_cadastral_address_btn'], [id='add_cadastral_address_btn'], #divSearch_flag_indirizzi").attr("idx", $("#divCatastoIndirizzoFields #CatastoIndirizzo").length);
		});
		handleButtonsVisibility("divCatastoIndirizzoFields", "CatastoIndirizzo","add_cadastral_address_btn","delete_cadastral_address_btn");
	}
    
    function addCadastralDataDiv(){
    	var value1 = "";
    	var value2= "";
    	if($("#divCatastoFields #CatastoField").length > 0){
	    	$("#divCatastoFields #CatastoField").last().each(function(){
	    		value1 = $(this).find("[id='search_sezione']").val()
	    		value2 = $(this).find("[id='search_foglio']").val()
	    	});
    	}
		$("#divCatastoFields").append($("#CatastoFieldsTemplate").html());
		$("#divCatastoFields #CatastoField").last().each(function( index ) {
			$(this).find("[id='delete_cadastral_data_btn'], [id='add_cadastral_data_btn'], #divSearch_flag_catasto, input").attr("idx", $("#divCatastoFields #CatastoField").length);
			$(this).find("[id='search_sezione']").val(value1)
    		$(this).find("[id='search_foglio']").val(value2)
    	});
		handleButtonsVisibility("divCatastoFields", "CatastoField","add_cadastral_data_btn","delete_cadastral_data_btn");
	}
    
    function handleButtonsVisibility(container, singleGroup, addButton, deleteButton){
    	lastIndex = $("#"+container +" #"+singleGroup).length;
    	if(lastIndex == 1){
			$("#"+container).find("[id='"+addButton+"']").removeClass("hidden");
    	}
    	else {
    		lastIndex =""+ $("#"+container +" #"+singleGroup).length;
   			$("#"+container).find("[id='"+addButton+"']").addClass("hidden");
   			$("#"+container).find("[id='"+deleteButton+"']").removeClass("hidden");
   			
   			$("#"+container +" #"+singleGroup).last().each(function( index ) {
   				$(this).find("[id='"+addButton+"']").removeClass("hidden");
   			})
    	}
    }

	function removeBeneficiaryDiv(){
		if($("#divRichiedentiBeneficiariField #richiedentiBeneficiario").length == 1){
			$("#divRichiedentiBeneficiariField #richiedentiBeneficiario input").val("");
		}
		else{
	    	$(this).closest("#richiedentiBeneficiario").remove();
    		handleButtonsVisibility("divRichiedentiBeneficiariField", "richiedentiBeneficiario","add_anagrafica_btn","delete_anagrafica_btn");
		}
    }
    
    function removeCadastralAddressDiv(){
    	if($("#divCatastoIndirizzoFields #CatastoIndirizzo").length == 1){
			$("#divCatastoIndirizzoFields #CatastoIndirizzo input").val("");
		}
		else{
			$(this).closest("#CatastoIndirizzo").remove();
	    	handleButtonsVisibility("divCatastoIndirizzoFields", "CatastoIndirizzo","add_cadastral_address_btn","delete_cadastral_address_btn");
		}
    }
    
    function removeCadastralDataDiv(){
    	if($("#divCatastoFields #CatastoField").length == 1){
			$("#divCatastoFields #CatastoField input,#divCatastoFields #CatastoField select").val("");
		}
		else{
			$(this).closest("#CatastoField").remove();  
	    	handleButtonsVisibility("divCatastoFields", "CatastoField","add_cadastral_data_btn","delete_cadastral_data_btn");
		}
    }

    
    
    function validate(){
    	valid = false
    	$("[id^='search_'][type!='checkbox']").each(function(i,v){
       		if($(v).attr("id") != "search_stato")	
	       		valid = valid || $(v).val().length > 0
       	});
       	valid = valid || $("#search_stato").val() != "ALL";	
       	valid = valid || $("[name='ricerca_albero']").find("[type='checkbox']:checked").length > 0;	
    	return valid;
    }
    
    $(document).ready(function () {
        <c:if test="${empty filtroRicerca.identifications}">
 			addBeneficiaryDiv();
        </c:if>
        <c:if test="${empty filtroRicerca.cadastralAddresses}">
 			addCadastralAddressDiv();
 		</c:if>
 		 <c:if test="${empty filtroRicerca.cadastralDataList}">
 			addCadastralDataDiv();
 		</c:if>
 		
        $("#search_des_comune_catasto").val('');
        $("#search_protocollo_suap").val('');
        $("#search_tipoProcedimentoSUAP").val("");
    	$("#search_tipoInterventoSUAP").val("");
    	$("#search_data_inizio_prot_suap").val("");
    	$("#search_data_fine_prot_suap").val("");
    	$("#search_tipoClassificazioneProcedimento").val("");
        
    	$("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });

        $("#ricerca_button").click(function (event) {
        	event.stopPropagation();
        	if(validate()){
	            var yourArray = [];
	            $("input:checkbox[name=type]:checked").each(function(){
				    yourArray.push(this.id);
				});
	            $("#search_tree").val(yourArray);
	            var urli = $('#list').getGridParam("url").split("?")[0];
	            $('#list').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
	            $('#list').trigger("reloadGrid", [{page: 1}]);
        	} else {
        		showWarningMessage('<spring:message code="ricerca.validation.error" />')
        	}
        });
        
        $("#azzera_button").click(function (event) {
        	event.stopPropagation();
        	$("#ricerca").find("#delete_cadastral_data_btn, #delete_anagrafica_btn, #delete_cadastral_address_btn").each(function(){
        		$(this).trigger("click");
        	})
        	$('#search_tipo_pratica').val('').trigger('chosen:updated');
        	$("[id^='search_'][type!='checkbox']").val("");
        	$("#search_stato").val("ALL");	
        });

        $("#search_data_from, #search_data_to, #search_data_inizio_prot_suap, #search_data_fine_prot_suap").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $(document).on("keyup", "[id^='search_des_comune_catasto']", function(e){
        	text = $(this).val();
        	if(e.keyCode == 8) {
        		$(this).siblings('#search_id_comune_catasto').val('');
            } else {
            	$(this).autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "<%= path%>/search/comune.htm",
                            dataType: "json",
                            data: {
                                description: text,
                                dataValidita: ''
                            },
                            success: function (data) {
                                response($.map(data, function (item) {
                                    return {
                                        label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                        value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                        id: item.idComune
                                    }
                                }));
                            }
                        });
                    },
                    select: function (event, ui) {
                        $(this).siblings('#search_id_comune_catasto').val(ui.item.id);
                    },
                    minLength: 2
                });
            }
        });
        
        $(document).on("keyup", "[id^='search_ricercaAnagraficaCognome']", function(e){
        	cleanFunction = function(self){
        		self.parent().parent().find("#search_ricercaAnagraficaCognome").val('');
        	}

        	dataJson = function(self, text){
        		var curName = self.parent().parent().find("#search_ricercaAnagraficaNome").val();
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
                    id: item.cognome
                }
            }
        	genericAutocomplete($(this), e, "<%= path%>/search/cognome.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });

        $(document).on("keyup", "[id^='search_ricercaAnagraficaNome']", function(e){
        	cleanFunction = function(self){
        		self.parent().parent().find("#search_ricercaAnagraficaNome").val('');
        	}

        	dataJson = function(self, text){
        		var curSurname = self.parent().parent().find("#search_ricercaAnagraficaCognome").val();
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
        
        $(document).on("keyup", "[id^='search_ricercaAnagraficaRagioneSociale']", function(e){
        	
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

        $(document).on("keyup", "[id^='search_ricercaAnagraficaCF']", function(e){
        	
        	cleanFunction = function(self){}

        	dataJson = function(self, text){
        		return {tax_code: text}
            }
        	
        	selectionFunction = function (self, event, ui) {
            	return true;
            }
        	
        	successFunction = function (item) {
                return {
                    label: item.codiceFiscale || item.partitaIva,
                    denominazione: item.codiceFiscale || item.partitaIva,
                    id: item.codiceFiscale || item.partitaIva
                }
            }
        	genericAutocomplete($(this), e, "<%= path%>/search/codice_fiscale.htm", 2, dataJson, cleanFunction, successFunction, selectionFunction);
        });
        
        $(document).on("keyup", "[id^='search_indirizzo']", function(e){
        	
        	minLength = $(this).val().search("via") > -1 ? 6 : 4;
            minLength = $(this).val().search("(?:viale |corso |largo )") > -1 ? 8 : minLength;
        	
            cleanFunction = function(self){}

        	dataJson = function(self, text){
        		return {
        			steet_name: text,
        			is_start_from:(minLength != 4)
        		}
            }
        	
        	selectionFunction = function (self, event, ui) {
            	$(self).val(ui.item.indirizzo)
            	self.parent().parent().find("#search_civico").val(ui.item.civico);
        		return false;
            }
        	
        	successFunction = function (item) {
                return {
                    label: item.indirizzo + " " + item.civico,
                    indirizzo:  item.indirizzo,
                    civico: item.civico,
                    id: item.indirizzo + " " + item.civico
                }
            }

        	genericAutocomplete($(this), e, '<%= path%>/search/addressesByStreetName.htm', minLength, dataJson, cleanFunction, successFunction, selectionFunction);
        });

        $(document).on("click", "#add_anagrafica_btn", addBeneficiaryDiv);
        $(document).on("click", "#delete_anagrafica_btn", removeBeneficiaryDiv);
        
        $(document).on("click", "#add_cadastral_address_btn", addCadastralAddressDiv);
        $(document).on("click", "#delete_cadastral_address_btn", removeCadastralAddressDiv);
        
        $(document).on("click", "#add_cadastral_data_btn", addCadastralDataDiv);
        $(document).on("click", "#delete_cadastral_data_btn", removeCadastralDataDiv);
    });
</script>
<c:set var="infopath" scope="page"><tiles:getAsString name="infopath"/></c:set>

<div id="ricerca">
    <h3>
	    <a href="#"><spring:message code="ricerca.title"/></a>
	    <span class="buttonHolder operations">
		      <button id="ricerca_button" type="button" class="primaryAction fondo_destra"><spring:message code="ricerca.button.cerca"/></button>
		      <button id="azzera_button" type="button" class="primaryAction fondo_destra"><spring:message code="ricerca.button.azzera"/></button>
	   </span>
   </h3>
    <div>
        <form id="form_ricerca" action="<%= path%>/pratiche/comunicazione/dettaglio_search.htm" class="uniForm" method="post">
            <input name="tipoFiltro" type="hidden" value="utente" />
            <input name="back" type="hidden" value="${back }" />

            <div style="width:1080px;">    
				
                   
                    <div >
                    	<fieldset id="divRichiedentiBeneficiariField" class="research-fieldset" > 
                            <legend class="legend"><spring:message code="ricerca.label.soggettiCoinvolti"/>
                            	<span id="divSearch_flag_anagrafica">
						           <label for="search_flag_anagrafica"><spring:message code="ricerca.or" />	
							           <input id="search_flag_anagrafica" name="search_flag_anagrafica" type="checkbox" value="1" ${filtroRicerca.getIdentifications().getLogicalOperator().getValue()== '1'?'checked':''} />
						           </label>
						       </span> 
						    </legend>
                            <c:forEach items="${filtroRicerca.getIdentifications().getList()}" var="identification" varStatus="anagraficaLoop">
	                           	<c:set var="identification" value="${identification}" scope="request"/>
								<c:set var="anagraficaLoop" value="${anagraficaLoop}" scope="request"/>
								<c:import url="/themes/default/views/main/body/research_identification_fragment.jsp"/>
							</c:forEach>
                        </fieldset>
						<fieldset class="research-fieldset">
							<legend class="legend"><spring:message code="ricerca.label.pratica"/></legend>
							<div class="row">
	                            <div class="width15">
	                                <label for="search_id_pratica">
	                                    <spring:message code="ricerca.idpratica"/>
	                                </label>
	                                <input id="search_idpratica" name="search_idpratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.idPratica}" />
	                                <p class="formHint"></p>
	                            </div>
	                            <div class="width15">
	                                <label>
	                                    <spring:message code="ricerca.identificativo_pratica"/>
	                                </label>
	                                <input id="search_identificativo_pratica" name="search_identificativo_pratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.identificativoPratica}" />
	                            </div>
	                            <div class="width20">
	                                <label><spring:message code="ricerca.annoFascicolo"/></label>
	                                <select id="search_annoRiferimento" name="search_annoRiferimento" class="textInput select">
	                                    <option  value="" ><spring:message code="ricerca.inizio.anno"/></option>
	                                    <c:forEach items="${anniRiferimento}" var="anno" begin="0">
	                                        <c:set var="selected" value=""/> 
	                                        <c:if test="${anno != null &&  filtroRicerca.annoRiferimento == anno}">
	                                            <c:set var="selected" value="selected=\"selected\""/>     
	                                        </c:if>
	                                        <option  value="${anno}" ${selected}>${anno}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
	                            <div class="width20"> 
	                                <label for="search_id_pratica"><spring:message code="ricerca.identificativopratica"/></label>
	                                <input id="search_id_pratica" name="search_id_pratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.numeroProtocollo}" />
	                            </div>
	                            <div class="width20"> 
	                                <label for="search_protocollo_evento"><spring:message code="ricerca.protocollo.numero_evento"/></label>
	                                <input id="search_protocollo_evento" name="search_protocollo_evento" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.registerNumberByEvent}" />
	                            </div>
                            </div>
							<div class="row">
								<div class="width50" >
			                    	<label><spring:message code="ricerca.tipo_pratica"/></label>
		                            
                                    <select id="search_tipo_pratica" name="search_tipo_pratica" class="chosen-select" data-placeholder="<spring:message code="ricerca.seleziona_tipo_pratica"/>">
                                        <option  value=""></option>
                                        <c:forEach items="${lkTipoPratica}" var="tipoPratica" begin="0">
                                    	    <c:set var="selected" value=""/> 
                                        		<c:if test="${filtroRicerca.idTipoPratica != null && filtroRicerca.idTipoPratica.idTipoPratica == tipoPratica.idTipoPratica}">
	                                            	<c:set var="selected" value="selected=\"selected\""/>     
		                                        </c:if>
                                        	<option  value="${tipoPratica.idTipoPratica}" ${selected}>${tipoPratica.tipoPratica} - ${tipoPratica.descrizione}</option>
    	                                </c:forEach>
                                    </select>
				                </div>
				                <div class="width20">
	                                <label><spring:message code="ricerca.label.numeroPratica"/></label>
	                                <input id="search_numero_pratica" name="search_numero_pratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.numeroPratica}">
	                            </div>
                            
	                            <div class="width20">
	                                <label><spring:message code="ricerca.label.anno"/></label>
	                                <select id="search_anno_pratica" name="search_anno_pratica" class="textInput select">
	                                    <option  value="" ><spring:message code="ricerca.inizio.anno"/></option>
	                                    <c:forEach items="${anniRiferimento}" var="anno" begin="0">
	                                        <c:set var="selected" value=""/> 
	                                        <c:if test="${anno != null &&  filtroRicerca.annoPratica == anno}">
	                                            <c:set var="selected" value="selected=\"selected\""/>     
	                                        </c:if>
	                                        <option  value="${anno}" ${selected}>${anno}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                
                            <div class="row">
	                            <div class="width20">
	                                <label><spring:message code="ricerca.datainizio"/></label>
	                                <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataInizio}"/>" />
	                            </div>
	                            
	                            <div class="width20">
	                                <label><spring:message code="ricerca.datafine"/></label>
	                                <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataFine}"/>" />
	                            </div>
	                            <div class="width50">
	                                <label for="search_descrizione_intervento">
	                                    <spring:message code="ricerca.descrizioneIntervento"/>
	                                </label>
	                                <input id="search_descrizione_intervento" name="search_descrizione_intervento" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.interventionDescription}" />
	                                <p class="formHint"></p>
	                            </div>
				            </div>
	                        <div class="row">
	                            <div class="width50">
	                                <label">
	                                    <spring:message code="ricerca.oggetto"/>
	                                </label>
	                                <input id="search_oggetto" name="search_oggetto" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.oggetto}" />
	                                <p class="formHint"></p>
	                            </div>
	                        </div>
	                        <div class="row">
	                            <div class="width30">
	                                <label><spring:message code="ricerca.ente"/></label>
	                                <select id="search_ente" name="search_ente" class="select textInput">
	                                    <option  value="" ><spring:message code="ricerca.entestart"/></option>
	                                    <c:forEach items="${entiRicerca}" var="ente" begin="0">
	                                        <c:set var="selected" value=""/> 
	                                        <c:if test="${filtroRicerca.enteSelezionato != null &&  filtroRicerca.enteSelezionato.idEnte == ente.idEnte}">
	                                            <c:set var="selected" value="selected=\"selected\""/>     
	                                        </c:if>
	                                        <option  value="${ente.idEnte}" ${selected}>${ente.descrizione}</option>
	                                    </c:forEach>
	                                </select>
	                            </div> 

                            	<div class="width30">
	                                <label for="search_stato"><spring:message code="ricerca.statopratica"/></label>
	                                <select id="search_stato" name="search_stato" class="select textInput">
   		                                <c:if test="${abilitazioneConsultazione}">
		                                    <option value="CHIUSE" ><spring:message code="ricerca.statopraticaclose"/></option>
		                                </c:if>

	                                	<c:if test="${!abilitazioneConsultazione}">
		                                	<option value="ALL" ><spring:message code="ricerca.statopraticastart"/></option>
		                                    <option value="ATTIVE" ><spring:message code="ricerca.statopraticaactive"/></option>
		                                    <option value="CHIUSE" ><spring:message code="ricerca.statopraticaclose"/></option>
		                                    <c:forEach items="${statoPraticaRicerca}" var="stato" begin="0">
		                                        <c:set var="selected" value=""/> 
		                                        <c:if test="${filtroRicerca.idStatoPratica != null &&  filtroRicerca.idStatoPratica.idStatoPratica == stato.idStatoPratica}">
		                                            <c:set var="selected" value="selected=\"selected\""/>     
		                                        </c:if>
		                                        <c:if test='${stato.idStatoPratica ne "8"}'>
		                                            <option value="${stato.idStatoPratica}" ${selected}>${stato.descrizione}</option>
		                                        </c:if>
		                                    </c:forEach>
	                                    </c:if>
	                                </select>
	                            </div>   

	                            <div class="width30">
	                                <label><spring:message code="ricerca.operatore"/></label>
	                                <select id="search_operatore" name="search_operatore" class="select textInput">
	                                    <option value="" ><spring:message code="ricerca.operatorestart"/></option>
	                                    <c:forEach items="${operatoriRicerca}" var="operatore" begin="0">
	                                        <c:set var="selected" value=""/> 
	                                        
									         
								             <c:if test="${filtroRicerca.idOperatoreSelezionato != null && filtroRicerca.idOperatoreSelezionato == operatore.idUtente}">
                                           		<c:set var="selected" value="selected=\"selected\""/>  
                                       		</c:if>
	                                        <option  value="${operatore.idUtente}" ${selected}>${operatore.cognome} ${operatore.nome}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                        </fieldset>

                        <fieldset class="research-fieldset" id="divCatastoFields">
                            <legend class="legend"><spring:message code="ricerca.label.datiCatastali"/>
	                            <span id="divSearch_flag_catasto">
							        <label for="search_flag_catasto"><spring:message code="ricerca.or" />
							        	<input id="search_flag_catasto" name="search_flag_catasto" type="checkbox" value="1" ${filtroRicerca.getCadastralDataList().getLogicalOperator().getValue()== '1'?'checked':''} />
							        </label>
							    </span> 
						    </legend>
						    
							 <c:forEach items="${filtroRicerca.getCadastralDataList().getList()}" var="cadastralData" varStatus="dataLoop">
							 	<c:set var="lkTipiUnita" value="${lkTipiUnita}" scope="request"/>
								<c:set var="cadastralData" value="${cadastralData}" scope="request"/>
								<c:set var="dataLoop" value="${dataLoop}" scope="request"/>
								<c:import url="/themes/default/views/main/body/research_cadastral_data_fragment.jsp"/>
							 </c:forEach>
                        </fieldset>
                        
                        <fieldset class="research-fieldset" id="divCatastoIndirizzoFields" >
                            <legend class="legend"><spring:message code="ricerca.label.indirizzoIntervento"/>
                            	<span id="divSearch_flag_indirizzi">
						           <label for="search_flag_indirizzi"><spring:message code="ricerca.or" />	
							           <input id="search_flag_indirizzi" name="search_flag_indirizzi" type="checkbox" value="1" ${filtroRicerca.getCadastralAddresses().getLogicalOperator().getValue()== '1'?'checked':''} />
						           </label>
						       </span> 
							</legend>

	                       	<c:forEach items="${filtroRicerca.getCadastralAddresses().getAddresses()}" var="cadastralAddress" varStatus="addressLoop">
	                           	<c:set var="cadastralAddress" value="${cadastralAddress}" scope="request"/>
	                           	<c:set var="addressLoop" value="${addressLoop}" scope="request"/>
								<c:import url="/themes/default/views/main/body/research_cadastral_address_fragment.jsp"/>
							</c:forEach>
	                    </fieldset>                         
                    </div>

                
                
              	<c:if test='${infopath.equals("Gestione pratiche") && utenteConnesso.utenteRuoloEnte.stream().filter(ruolo->ruolo.desEnte.contains("SUAP")).count() gt 0}' >  
	                <div class="sidebar_auto" style="width:1080px;">
	               		<div class="page-control" data-role="page-control">
		                	<span class="menu-pull"></span> 
		                	<div class="menu-pull-bar"></div>
		                     <ul>
								<li class="active"><a href="#frame1">Dati Suap</a></li>
								<li><a href="#frame2">Albero Ricerca</a></li>
							</ul>
							<!-- Contenuto cartelle -->
							<div class="frames">
								<div class="frame active" id="frame1">
								
								
			                        <fieldset id="suapField" >
			                            
			                            <div class="ctrlHolder" id="divSearch_protocollo_suap">
			                                <label for="search_protocollo_suap">
			                                    Protocollo Suap
			                                </label>
			                               <input id="search_protocollo_suap" name="search_protocollo_suap" size="35" maxlength="50"  type="text" class="textInput" value="${filtroRicerca.protocolloSuap}" size="35">
			                                <p class="formHint"></p>
			                            </div>
			                            <div class="ctrlHolder" id="divSearch_data_inizio_prot_suap">
											<label for="search_data_inizio_prot_suap"><spring:message code="ricerca.dataInizioprotsuap"/></label>
			                                <input id="search_data_inizio_prot_suap" name="search_data_inizio_prot_suap" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataInizioProtSuap}"/>">
			                                <p class="formHint"></p>
			                            </div>
			                            <div class="ctrlHolder" id="divSearch_data_fine_prot_suap">
			                                <label for="search_data_fine_prot_suap"><spring:message code="ricerca.dataFineprotsuap"/></label>
			                                <input id="search_data_fine_prot_suap" name="search_data_fine_prot_suap" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataFineProtSuap}"/>">
			                                <p class="formHint"></p>
			                            </div>
			                            <div class="ctrlHolder" id="divSearch_tipo_classificazione_suap">
											 <label for="search_tipoClassificazioneProcedimento">
				                                    Tipo classificazione procedimento
				                             </label>
				                             
				                             <select id="search_tipoClassificazioneProcedimento" name="search_tipoClassificazioneProcedimento" class="select textInput" >
			                                    <option  value="" ><spring:message code="ricerca.inizio.tipoClassificazioneProcedimento"/></option>
			                                    <c:forEach items="${lkTipoClassificazioneProcedimento}" var="tipoClassificazioneProcedimento" begin="0">
			                                        <c:set var="selected" value=""/> 
			                                        <c:if test="${tipoClassificazioneProcedimento.idClassificazioneProcedimento != null &&  filtroRicerca.idClassificazioneProcedimento == tipoClassificazioneProcedimento.idClassificazioneProcedimento}">
			                                            <c:set var="selected" value="selected=\"selected\""/>     
			                                        </c:if>
			                                        <option  value="${tipoClassificazioneProcedimento.idClassificazioneProcedimento}" ${selected}>${tipoClassificazioneProcedimento.descrizioneClassificazioneProcedimento}</option>
			                                    </c:forEach>
			                                </select>
				                             <p class="formHint"></p>
				                             <p class="formHint"></p>
										</div>
										<div class="ctrlHolder" id="divSearch_tipo_procedimento_suap">
											 <label for="search_tipoProcedimentoSUAP">
				                                    Tipo procedimento SUAP
				                             </label>
				                             
				                             <select id="search_tipoProcedimentoSUAP" name="search_tipoProcedimentoSUAP" class="select textInput"  type="text" >
			                                    <option  value="" ><spring:message code="ricerca.inizio.tipoProcedimento"/></option>
			                                    <c:forEach items="${lkTipoProcedimentoSUAP}" var="tipoProcedimentoSUAP" begin="0">
			                                        <c:set var="selected" value=""/> 
			                                        <c:if test="${tipoProcedimentoSUAP.idTipoProcedimentoSuap != null &&  filtroRicerca.idTipoProcedimentoSuap == tipoProcedimentoSUAP.idTipoProcedimentoSuap}">
			                                            <c:set var="selected" value="selected=\"selected\""/>     
			                                        </c:if>
			                                        <option  value="${tipoProcedimentoSUAP.idTipoProcedimentoSuap}" ${selected}>${tipoProcedimentoSUAP.descrizioneCrossSuap}</option>
			                                    </c:forEach>
			                                </select>
				                             <p class="formHint"></p>
										</div>
										<div class="ctrlHolder" id="divSearch_tipo_intervento_suap">
											 <label for="search_tipoInterventoSUAP">
				                                    Tipo intervento SUAP
				                             </label>
				                             
				                             <select id="search_tipoInterventoSUAP" name="search_tipoInterventoSUAP" class="select textInput" >
			                                    <option  value="" ><spring:message code="ricerca.inizio.tipoIntervento"/></option>
			                                    <c:forEach items="${lkTipoInterventoSUAP}" var="tipoInterventoSUAP" begin="0">
			                                        <c:set var="selected" value=""/> 
			                                        <c:if test="${tipoInterventoSUAP.idTipoInterventoSuap != null &&  filtroRicerca.idTipoInterventoSuap == tipoInterventoSUAP.idTipoInterventoSuap}">
			                                            <c:set var="selected" value="selected=\"selected\""/>     
			                                        </c:if>
			                                        <option  value="${tipoInterventoSUAP.idTipoInterventoSuap}" ${selected}>${tipoInterventoSUAP.descrizioneTipoInterventoSuap}</option>
			                                    </c:forEach>
			                                </select>
				                            <p class="formHint"></p>
			                                <p class="formHint"></p>
										</div>
								</div>
								<div class="frame" id="frame2">
									<div id="divsearch_tree" class="ctrlHolder" >
										<input type="hidden" id= "search_tree" name= "search_tree"/>
										<tiles:insertAttribute name="ricerca_albero" />
									</div>
								</div>
							</div>
						</div>
					</div>				
	            </c:if>
            </div>
        </form>
    </div>
</div>

<c:set var="dataLoop" value="${null}" scope="request"/>
<c:set var="anagraficaLoop" value="${null}" scope="request"/>
<c:set var="addressLoop" value="${null}" scope="request"/>
<c:set var="cadastralData" value="${null}" scope="request"/>
<c:set var="cadastralAddress" value="${null}" scope="request"/>
<c:set var="identification" value="${null}" scope="request"/>	
<div id="richiedentiBeneficiariTemplate" class="hidden">
	<c:import url="/themes/default/views/main/body/research_identification_fragment.jsp"/>
</div>

<div id="CatastoIndirizzoFieldsTemplate"  class="hidden">
	<c:import url="/themes/default/views/main/body/research_cadastral_address_fragment.jsp"/>
</div>

<div id="CatastoFieldsTemplate"  class="hidden">
	<c:import url="/themes/default/views/main/body/research_cadastral_data_fragment.jsp"/>
</div>

<script type="text/javascript">
	$(".chosen-select").chosen({allow_single_deselect: true});    
</script>













