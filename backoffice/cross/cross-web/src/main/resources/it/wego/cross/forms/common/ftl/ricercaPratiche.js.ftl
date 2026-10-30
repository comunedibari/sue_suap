[#ftl]
	
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
		$(this).find("input, [id='delete_anagrafica_btn'], [id='add_anagrafica_btn']").attr("idx", $("#divRichiedentiBeneficiariField > #richiedentiBeneficiario").length);
	});
	handleButtonsVisibility("divRichiedentiBeneficiariField", "richiedentiBeneficiario","add_anagrafica_btn","delete_anagrafica_btn");

}
   
function addCadastralAddressDiv(){
	$("#divCatastoIndirizzoFields").append($("#CatastoIndirizzoFieldsTemplate").html());
	$("#divCatastoIndirizzoFields #CatastoIndirizzo").last().each(function( index ) {
		$(this).find("input, [id='delete_cadastral_address_btn'], [id='add_cadastral_address_btn'], #divSearch_flag_indirizzi").attr("idx", $("#divCatastoIndirizzoFields #CatastoIndirizzo").length);
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
		$(this).find("input, [id='delete_cadastral_data_btn'], [id='add_cadastral_data_btn'], #divSearch_flag_catasto, input").attr("idx", $("#divCatastoFields #CatastoField").length);
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

function search(){
	if(validateLinkedPaperworks()){
		var urli = "${data.path}/pratiche/gestisci/ajax.htm?"+$('#form_ricerca').serialize();
	    $('#${tableId}').setGridParam({url: urli});
	    $('#${tableId}').trigger("reloadGrid", [{page: 1}]);
    } else {
        showWarningMessage('Compilare almeno un campo di ricerca')
    }
}

function validateLinkedPaperworks(){
	valid = false;
	$("#form_ricerca").find("[id^='search_'][id!='hidden'][type!='checkbox']").each(function(i,v){
   		if($(v).attr("id") != "search_stato")	
    		valid = valid || $(v).val().length > 0
   	});
   	valid = valid || $("#search_stato").val() != "ALL";	
	return valid;
}

function subjectsAutocomplete(self, keyEvent, url, dataJson, cleanFunction, successFunction, selectionFunction){
	text = self.val();
	if(keyEvent.keyCode == 8) {
		cleanFunction(self)
	} else {
    	self.autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: url,
                    dataType: "json",
                    data: dataJson(self, self.val()),
                    success: function (data) {
                        response($.map(data, successFunction));
                    }
                });
            },
            select: function (event, ui) {
            	return selectionFunction(self, event, ui);
            },
            minLength: 2
        });
    }
}
$(document).ready(function () {
		if(parent.$("#selected_subjects option").length > 0){
			
			parent.$("#selected_subjects option").each(function( index ) {
				addBeneficiaryDiv();
			    $('[id="search_ricercaAnagraficaNome"][idx='+(index+1)+']').val($(this).attr("subject_name"));
			    $('[id="search_ricercaAnagraficaCognome"][idx='+(index+1)+']').val($(this).attr("subject_surname"));
			    $('[id="search_ricercaAnagraficaRagioneSociale"][idx='+(index+1)+']').val($(this).attr("company_name"));
			});
		}
       	else
       		addBeneficiaryDiv();
       	
       	if(parent.$("#selected_cadastral_data option").length > 0){
			
			parent.$("#selected_cadastral_data option").each(function( index ) {
				addCadastralDataDiv();
			    var unit_type = $(this).attr("unit_type")
			    $("#search_tipoUnita option").filter(function() {
				  
				  return $(this).text() == unit_type;
				}).prop('selected', true);
			    $('[id="search_sezione"][idx='+(index+1)+']').val($(this).attr("section"));
				$('[id="search_foglio"][idx='+(index+1)+']').val($(this).attr("sheet"));
				$('[id="search_mappale"][idx='+(index+1)+']').val($(this).attr("map"));
				$('[id="search_subalterno"][idx='+(index+1)+']').val($(this).attr("subordinate"));
			});
		}
       	else
       		addCadastralDataDiv();
		
		if(parent.$("#selected_cadastral_address option").length > 0){
			
			parent.$("#selected_cadastral_address option").each(function( index ) {
				addCadastralAddressDiv();
			    $('[id="search_indirizzo"][idx='+(index+1)+']').val($(this).attr("street"));
			    $('[id="search_civico"][idx='+(index+1)+']').val($(this).attr("number"));
				$('[id="search_des_comune_catasto"][idx='+(index+1)+']').val($(this).attr("town"));
				$('[id="search_id_comune_catasto"][idx='+(index+1)+']').val($(this).attr("id_town"));
			});
		}
       	else
       		addCadastralAddressDiv();
		
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
       
        $("#azzera_button").click(function () {
	        event.stopPropagation();
        	$("#ricerca").find("#delete_cadastral_data_btn:not(.hidden), #delete_anagrafica_btn:not(.hidden), #delete_cadastral_address_btn:not(.hidden)").each(function(){
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
	           	$(this).val('');
            } else {
	           	$(this).autocomplete({
                   source: function (request, response) {
                       $.ajax({
                           url: "${data.path}/search/comune.htm",
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
        		self.val('');
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
        	subjectsAutocomplete($(this), e, "${data.path}/search/cognome.htm", dataJson, cleanFunction, successFunction, selectionFunction);
       });

       $(document).on("keyup", "[id^='search_ricercaAnagraficaNome']", function(e){
       		cleanFunction = function(self){
        		self.val('');
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
        	subjectsAutocomplete($(this), e, "${data.path}/search/nome.htm", dataJson, cleanFunction, successFunction, selectionFunction);
       });
        
       $(document).on("keyup", "[id^='search_ricercaAnagraficaRagioneSociale']", function(e){
        	
        	cleanFunction = function(self){
        		self.val('');
        	}
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
        	subjectsAutocomplete($(this), e, "${data.path}/search/ragione_sociale.htm", dataJson, cleanFunction, successFunction, selectionFunction);
       });

       $(document).on("keyup", "[id^='search_ricercaAnagraficaCF']", function(e){
        	
        	cleanFunction = function(self){
        		self.val('');
        	}
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
        	subjectsAutocomplete($(this), e, "${data.path}/search/codice_fiscale.htm", dataJson, cleanFunction, successFunction, selectionFunction);
       });
        
      $(document).on("click", "#add_anagrafica_btn", addBeneficiaryDiv);
      $(document).on("click", "#delete_anagrafica_btn", removeBeneficiaryDiv);
      
      $(document).on("click", "#add_cadastral_address_btn", addCadastralAddressDiv);
      $(document).on("click", "#delete_cadastral_address_btn", removeCadastralAddressDiv);
      
      $(document).on("click", "#add_cadastral_data_btn", addCadastralDataDiv);
      $(document).on("click", "#delete_cadastral_data_btn", removeCadastralDataDiv);
      
      $("#ricerca_button").on('click',function() {
      		var urli = $('#${tableId}').getGridParam("url").split("?")[0];
      		$('#${tableId}').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
       		$('#${tableId}').trigger("reloadGrid", [{page: 1}]);
      });

	  $("#ricerca_button2").on('click',search);
});