<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">

    function disableIdSearchComune(type) {
        $("#search_id_comune_" + type).val('');
        $("#search_id_comune_" + type).attr('disabled', 'disabled');
    }

    function enableIdSearchComune(type) {
        $("#search_id_comune_" + type).val('');
        $("#search_id_comune_" + type).removeAttr('disabled');
    }


    function disableSearchComuneComponent(type) {
        $("#search_des_comune_" + type).val('');
        $("#search_des_comune_" + type).attr('disabled', 'disabled');
        $("#search_button_" + type).attr('disabled', 'disabled');
        disableIdSearchComune(type);
    }

    function enableSearchComuneComponent(type) {
        $("#search_des_comune_" + type).val('');
        $("#search_des_comune_" + type).removeAttr('disabled');
        $("#search_button_" + type).removeAttr('disabled');
        disableIdSearchComune(type);
    }

    function changeTipoCatasto() {
        var tipoCatasto = $("#search_tipoSistemaCatastale").val();
        if (tipoCatasto == "1") {
            if ($("#divSearch_tipoUnita").hasClass("hidden")) {
                $("#divSearch_tipoUnita").removeClass("hidden");
            }
            if ($("#divSearch_sezione").hasClass("hidden")) {
                $("#divSearch_sezione").removeClass("hidden");
            }
            if ($("#divSearch_foglio").hasClass("hidden")) {
                $("#divSearch_foglio").removeClass("hidden");
            }
            if (!$("#divSearch_tipoParticella").hasClass("hidden")) {
                $("#divSearch_tipoParticella").addClass("hidden");
            }
            if (!$("#divSearch_comuneCensuario").hasClass("hidden")) {
                $("#divSearch_comuneCensuario").addClass("hidden");
            }
            if (!$("#divSearch_estensioneParticella").hasClass("hidden")) {
                $("#divSearch_estensioneParticella").addClass("hidden");
            }
            $("#search_tipoParticella").val('');
            $("#search_comuneCensuario").val('');
            $("#search_estensioneParticella").val('');

        } else if (tipoCatasto == "2") {
            if (!$("#divSearch_tipoUnita").hasClass("hidden")) {
                $("#divSearch_tipoUnita").addClass("hidden");
            }
            if (!$("#divSearch_sezione").hasClass("hidden")) {
                $("#divSearch_sezione").addClass("hidden");
            }
            if (!$("#divSearch_foglio").hasClass("hidden")) {
                $("#divSearch_foglio").addClass("hidden");
            }
            if ($("#divSearch_tipoParticella").hasClass("hidden")) {
                $("#divSearch_tipoParticella").removeClass("hidden");
            }
            if ($("#divSearch_comuneCensuario").hasClass("hidden")) {
                $("#divSearch_comuneCensuario").removeClass("hidden");
            }
            if ($("#divSearch_estensioneParticella").hasClass("hidden")) {
                $("#divSearch_estensioneParticella").removeClass("hidden");
            }

            $("#search_tipoUnita").val('');
            $("#search_sezione").val('');
            $("#search_foglio").val('');
        } else {
            if (!$("#divSearch_tipoUnita").hasClass("hidden")) {
                $("#divSearch_tipoUnita").addClass("hidden");
            }
            if (!$("#divSearch_sezione").hasClass("hidden")) {
                $("#divSearch_sezione").addClass("hidden");
            }
            if (!$("#divSearch_foglio").hasClass("hidden")) {
                $("#divSearch_foglio").addClass("hidden");
            }
            if (!$("#divSearch_tipoParticella").hasClass("hidden")) {
                $("#divSearch_tipoParticella").addClass("hidden");
            }
            if (!$("#divSearch_comuneCensuario").hasClass("hidden")) {
                $("#divSearch_comuneCensuario").addClass("hidden");
            }
            if (!$("#divSearch_estensioneParticella").hasClass("hidden")) {
                $("#divSearch_estensioneParticella").addClass("hidden");
            }
            $("#search_tipoUnita").val('');
            $("#search_sezione").val('');
            $("#search_foglio").val('');
            $("#search_tipoParticella").val('');
            $("#search_comuneCensuario").val('');
            $("#search_estensioneParticella").val('');
        }
    }

    $(document).ready(function () {
        changeTipoCatasto();
        disableIdSearchComune('pratica');
        disableIdSearchComune('catasto');
        $("#search_des_comune_pratica").val('');
        $("#search_des_comune_catasto").val('');

        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });

        $('#search_button_catasto').click(function () {
            enableSearchComuneComponent('pratica');
            enableSearchComuneComponent('catasto');
        });
        $('#search_button_pratica').click(function () {
            enableSearchComuneComponent('pratica');
            enableSearchComuneComponent('catasto');
        });

        $('#search_des_comune_catasto').change(function () {
            var content = $("#search_des_comune_catasto").val();
            if (content.length > 0) {
                disableSearchComuneComponent('pratica');
                enableSearchComuneComponent('catasto');
            } else {
                enableSearchComuneComponent('pratica');
                enableSearchComuneComponent('catasto');
            }
        });

        $('#search_des_comune_pratica').change(function () {
            var content = $("#search_des_comune_pratica").val();
            if (content.length > 0) {
                enableSearchComuneComponent('pratica');
                disableSearchComuneComponent('catasto');
            } else {
                enableSearchComuneComponent('pratica');
                enableSearchComuneComponent('catasto');
            }
        });

        $("#ricerca_button").click(function () {
            //^^CS ELIMINA provoca questa stringa ..../dettaglio_search.htmsearch_id_pratica=asd&search_ente...
            //$('#list').setGridParam({url:url+$('#form_ricerca').serialize()});
            var urli = $('#list').getGridParam("url").split("?")[0];
            $('#list').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid", [{page: 1}]);
        });
        
        $("#azzera_button").click(function () {
        	$("#search_oggetto").val("");
        	$("#ricercaAnagraficaNome").val("");
        	$("#ricercaAnagraficaCF").val("");
        	$("#search_idpratica").val("");
        	$("#search_identificativo_pratica").val("");
        	$("#search_annoRiferimento").val("");
        	$("#search_id_pratica").val("");
        	$("#search_data_from").val("");
        	$("#search_data_to").val("");
        	$("#search_ente").val("");
        	$("#search_des_comune_pratica").val('');
        	$("#search_id_comune_pratica").val("");
        	$("#search_stato").val("ALL");
        	$("#search_all").val("");
        	$("#search_operatore").val("");
        	$("#search_tipoSistemaCatastale").val("");
        	changeTipoCatasto();
        	$("#search_tipoUnita").val("");
        	$("#search_tipoParticella").val("");
        	$("#search_sezione").val("");
        	$("#search_comuneCensuario").val("");
        	$("#search_foglio").val("");
        	$("#search_mappale").val("");
        	$("#search_estensioneParticella").val("");
        	$("#search_subalterno").val("");
        	$("#search_indirizzo").val("");
        	$("#search_subalterno").val("");
        	$("#search_des_comune_catasto").val("");
        	$("#search_id_comune_catasto").val("");
        	$("#search_civico").val("");
        	$("#search_protocollo_suap").val('');
        	$("#search_tipoProcedimentoSUAP").val("");
        	$("#search_tipoInterventoSUAP").val("");
        	$("#search_data_inizio_prot_suap").val("");
        	$("#search_data_fine_prot_suap").val("");
        	$("#search_tipoClassificazioneProcedimento").val("");
        	
        });

        $("#search_data_from").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $("#search_data_to").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        $("#search_data_inizio_prot_suap").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        $("#search_data_fine_prot_suap").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $('#search_des_comune_pratica').autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "<%= path%>/search/comune.htm",
                    dataType: "json",
                    data: {
                        description: $("#search_des_comune_pratica").val(),
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
                disableSearchComuneComponent('catasto');
                enableIdSearchComune('pratica');
                $('#search_id_comune_pratica').val(ui.item.id);
            },
            minLength: 2
        });

        $('#search_des_comune_catasto').autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "<%= path%>/search/comune.htm",
                    dataType: "json",
                    data: {
                        description: $("#search_des_comune_catasto").val(),
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
                disableSearchComuneComponent('pratica');
                enableIdSearchComune('catasto');
                $('#search_id_comune_catasto').val(ui.item.id);
            },
            minLength: 2
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
                            <legend><spring:message code="ricerca.label.richiedentiBeneficiari"/></legend>
                            <div class="ctrlHolder" id="divRicercaAnagraficaNome">
                                <label for="ricercaAnagraficaNome">
                                    <spring:message code="ricerca.cognomeRagSociale"/>
                                </label>
                                <input id="ricercaAnagraficaNome" name="ricercaAnagraficaNome" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.ricercaAnagraficaNome}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divRicercaAnagraficaCF">
                                <label for="ricercaAnagraficaCF">
                                    <spring:message code="ricerca.cfPartitaIva"/>
                                </label>
                                <input id="ricercaAnagraficaCF" name="ricercaAnagraficaCF" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.ricercaAnagraficaCF}">
                                <p class="formHint"></p>
                            </div>
                        </fieldset>
                        <fieldset id="praticaField">
                            <legend><spring:message code="ricerca.label.pratica"/></legend>
                            
                            <div class="ctrlHolder" id="divSearch_id_pratica">
                                <label for="search_id_pratica">
                                    <spring:message code="ricerca.idpratica"/>
                                </label>
                                <input id="search_idpratica" name="search_idpratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.idPratica}">
                                <p class="formHint"></p>
                            </div>
                             <div class="ctrlHolder" id="divSearch_identificativo_pratica">
                                <label for="search_id_pratica">
                                    <spring:message code="ricerca.identificativo_pratica"/>
                                </label>
                                <input id="search_identificativo_pratica" name="search_identificativo_pratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.identificativoPratica}">
                                <p class="formHint"></p>
                            </div>
                            
                            <div class="ctrlHolder" id="divSearch_annoRiferimento">
                                <label for="search_annoRiferimento"><spring:message code="ricerca.annoRiferimento"/></label>
                                <select id="search_annoRiferimento" name="search_annoRiferimento" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.inizio.anno"/></option>
                                    <c:forEach items="${anniRiferimento}" var="anno" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${anno != null &&  filtroRicerca.annoRiferimento == anno}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${anno}" ${selected}>${anno}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>
                            
                            <div class="ctrlHolder" id="divSearch_id_pratica"> 
                                <label for="search_id_pratica"><spring:message code="ricerca.identificativopratica"/></label>
                                <input id="search_id_pratica" name="search_id_pratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.numeroProtocollo}">
                            </div>
							<div class="ctrlHolder" id="divSearch_oggetto"
								<label for="search_id_pratica"><spring:message code="ricerca.oggetto_pratica"/></label>
<%-- 								<input id="search_oggetto" name="search_oggetto" size="70" maxlength="50" type="text" class="textInput" value="${filtroRicerca.oggetto}"> --%>
								<select id="search_oggetto" name="search_oggetto" class="textInput">
                                	<option value="" ></option>
                                    <option value="SCIA" ><spring:message code="ricerca.scia"/></option>
                                    <option value="CILA" ><spring:message code="ricerca.cila"/></option>
                                    <option value="permesso di costruire" ><spring:message code="ricerca.pdc"/></option>
                                    <option value="agibilit" ><spring:message code="ricerca.agibilita"/></option>
                                    <option value="fine lavori" ><spring:message code="ricerca.fine.lavori"/></option>
                                    <option value="Autorizzazione Paesaggistica" ><spring:message code="ricerca.autorizzazione.paesagg"/></option>
                                    <option value="gestionale generico" ><spring:message code="ricerca.gestionale.generico"/></option>
                                    <option value="scia alternativa" ><spring:message code="ricerca.scia.alternativa"/></option>
                                    <option value="scia pubblicit" >Scia pubblicita'</option>
                                    <option value="PAS" >PAS</option>
                                </select>
                                <p class="formHint"></p>
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_data_from">
                                <label for="search_data_from"><spring:message code="ricerca.datainizio"/></label>
                                <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataInizio}"/>">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_data_to">
                                <label for="search_data_to"><spring:message code="ricerca.datafine"/></label>
                                <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataFine}"/>">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_ente">
                                <label for="search_ente"><spring:message code="ricerca.ente"/></label>
                                <select id="search_ente" name="search_ente" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.entestart"/></option>
                                    <c:forEach items="${entiRicerca}" var="ente" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${filtroRicerca.enteSelezionato != null &&  filtroRicerca.enteSelezionato.idEnte == ente.idEnte}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${ente.idEnte}" ${selected}>${ente.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div> 
                            <div class="ctrlHolder" id="divSearch_des_comune_pratica">
                                <label for="search_des_comune_pratica"><spring:message code="ricerca.comune"/></label>
                                <input id="search_des_comune_pratica" name="search_des_comune" size="35" maxlength="255" type="text" style="width: 60%;" class="textInput" value="${filtroRicerca.desComune}">
                                <input id="search_id_comune_pratica" name="search_id_comune" size="35" maxlength="35" type="hidden" class="textInput" value="${filtroRicerca.idComune}">
                                <button type="button" id="search_button_pratica" style="
                                        margin-left: 10px;
                                        margin-bottom: 0px;
                                        margin-top: 0px;
                                        float: left;
                                        ">X</button>
                                <p class="formHint"></p>
                            </div>          
                            <div class="ctrlHolder" id="divSearch_stato">
                                <label for="search_stato"><spring:message code="ricerca.statopratica"/></label>
                                <select id="search_stato" name="search_stato" class="textInput">
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
                                </select>
                                <p class="formHint"></p>
                            </div>   

                            
                            <div class="ctrlHolder" id="divSearch_operatore">
                                <label for="search_operatore"><spring:message code="ricerca.operatore"/></label>
                                <select id="search_operatore" name="search_operatore" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.operatorestart"/></option>
                                    <c:forEach items="${operatoriRicerca}" var="operatore" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <%String selected =""; %>
                                        
                                        <c:choose>
									         <c:when test = "${filtroRicerca.idOperatoreSelezionato == null}">
									            <c:if test="${operatore.idUtente eq utenteConnesso.idUtente} ">
                                           			<%selected = "selected"; %>    
                                        		</c:if>
									         </c:when>
									         <c:otherwise>
									             <c:if test="${filtroRicerca.idOperatoreSelezionato == operatore.idUtente} ">
                                            		<%selected = "selected"; %>    
                                        		</c:if>
									         </c:otherwise>
									    </c:choose>
                                        
                                        <option  value="${operatore.idUtente}" <%=selected %>>${operatore.cognome} ${operatore.nome}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>
                            
                        </fieldset>
                        
                        <fieldset id="suapField">
                            <legend>Dati Suap</legend>
                            
                            <div class="ctrlHolder" id="divSearch_protocollo_suap">
                                <label for="search_id_pratica">
                                    Protocollo Suap
                                </label>
                                <p class="formHint"></p>
                                <input id="search_protocollo_suap" name="search_protocollo_suap" type="text" class="textInput" value="${filtroRicerca.protocolloSuap}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_tipo_procedimento_suap">
								 <label for="search_id_pratica">
	                                    Tipo classificazione procedimento
	                             </label>
	                             
	                             <select id="search_tipoClassificazioneProcedimento" name="search_tipoClassificazioneProcedimento" class="textInput" >
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
							</div>
							<div class="ctrlHolder" id="divSearch_tipo_procedimento_suap">
								 <label for="search_id_pratica">
	                                    Tipo procedimento SUAP
	                             </label>
	                             
	                             <select id="search_tipoProcedimentoSUAP" name="search_tipoProcedimentoSUAP" class="textInput"  type="text" >
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
								 <label for="search_id_pratica">
	                                    Tipo intervento SUAP
	                             </label>
	                             
	                             <select id="search_tipoInterventoSUAP" name="search_tipoInterventoSUAP" class="textInput" >
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
                        </fieldset>

                        <fieldset id="CatastoField" class="fieldsettop">
                            <legend><spring:message code="ricerca.label.datiCatastali"/></legend>

                            <div class="ctrlHolder" id="divSearch_tipoSistemaCatastale">
                                <label for="search_tipoSistemaCatastale"><spring:message code="ricerca.tipoSistemaCatastale"/></label>
                                <select id="search_tipoSistemaCatastale" name="search_tipoSistemaCatastale" class="textInput" onchange="changeTipoCatasto();">
                                    <option  value="" ><spring:message code="ricerca.inizio.tipoCatasto"/></option>
                                    <c:forEach items="${lkTipiCatasto}" var="tipoCatasto" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${tipoCatasto.idTipoSistemaCatastale != null &&  filtroRicerca.idTipoSistemaCatastale == tipoCatasto.idTipoSistemaCatastale}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${tipoCatasto.idTipoSistemaCatastale}" ${selected}>${tipoCatasto.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>  
                            <div class="ctrlHolder hidden" id="divSearch_tipoUnita">
                                <label for="search_tipoUnita"><spring:message code="ricerca.tipoUnita"/></label>
                                <select id="search_tipoUnita" name="search_tipoUnita" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.inizio.tipoCatasto"/></option>
                                    <c:forEach items="${lkTipiUnita}" var="tipoUnita" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${tipoUnita.idTipoUnita != null &&  filtroRicerca.idTipoUnita == tipoUnita.idTipoUnita}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${tipoUnita.idTipoUnita}" ${selected}>${tipoUnita.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div> 
                            <div class="ctrlHolder hidden" id="divSearch_tipoParticella">
                                <label for="search_tipoParticella"><spring:message code="ricerca.tipoParticella"/></label>
                                <select id="search_tipoParticella" name="search_tipoParticella" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.inizio.tipoCatasto"/></option>
                                    <c:forEach items="${lkTipiParticella}" var="tipoParticella" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${tipoParticella.idTipoParticella != null &&  filtroRicerca.idTipoParticella == tipoParticella.idTipoParticella}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${tipoParticella.idTipoParticella}" ${selected}>${tipoParticella.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div> 
                        </fieldset>

                        <fieldset id="CatastoFields" class="fieldsetleftright">  
                            <div class="ctrlHolder hidden" id="divSearch_sezione">
                                <label for="search_sezione"><spring:message code="ricerca.sezione"/></label>
                                <input id="search_sezione" name="search_sezione" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.sezione}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder hidden" id="divSearch_comuneCensuario">
                                <label for="search_comuneCensuario"><spring:message code="ricerca.comuneCensuario"/></label>
                                <input id="search_comuneCensuario" name="search_comuneCensuario" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.comuneCensuario}">
                                <p class="formHint"></p>
                            </div>                                
                            <div class="ctrlHolder hidden" id="divSearch_foglio">
                                <label for="search_foglio"><spring:message code="ricerca.foglio"/></label>
                                <input id="search_foglio" name="search_foglio" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.foglio}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_mappale">
                                <label for="search_mappale"><spring:message code="ricerca.mappale"/></label>
                                <input id="search_mappale" name="search_mappale" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.mappale}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder hidden" id="divSearch_estensioneParticella">
                                <label for="search_estensioneParticella"><spring:message code="ricerca.estensioneParticella"/></label>
                                <input id="search_estensioneParticella" name="search_estensioneParticella" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.estensioneParticella}">
                                <p class="formHint"></p>
                            </div>                                 
                            <div class="ctrlHolder" id="divSearch_subalterno">
                                <label for="search_subalterno"><spring:message code="ricerca.subalterno"/></label>
                                <input id="search_subalterno" name="search_subalterno" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.subalterno}">
                                <p class="formHint"></p>
                            </div>
                        </fieldset>
                        <fieldset id="CatastoIndirizzoFields" class="fieldsetbottom">
                            <div class="ctrlHolder" id="divSearch_indirizzo">
                                <label for="search_indirizzo"><spring:message code="ricerca.indirizzo"/></label>
                                <input id="search_indirizzo" name="search_indirizzo" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.indirizzo}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_civico">
                                <label for="search_civico"><spring:message code="ricerca.civico"/></label>
                                <input id="search_civico" name="search_civico" size="35" maxlength="30" type="text" class="textInput" value="${filtroRicerca.civico}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_des_comune_catasto">
                                <label for="search_des_comune_catasto"><spring:message code="ricerca.comune"/></label>
                                <input id="search_des_comune_catasto" name="search_des_comune" size="35" maxlength="255" type="text" style="width: 60%;" class="textInput" value="${filtroRicerca.desComune}">
                                <input id="search_id_comune_catasto" name="search_id_comune" size="35" maxlength="35" type="hidden" class="textInput" value="${filtroRicerca.idComune}">
                                <button type="button" id="search_button_catasto" style="
                                        margin-left: 10px;
                                        margin-bottom: 0px;
                                        margin-top: 0px;
                                        float: left;
                                        ">X</button>
                                <p class="formHint"></p>
                            </div>
                        </fieldset>                         
                    </div>

                </div>
                <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                    <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>		
                    <button id="azzera_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.azzera"/></button>		
                </div>
            </div>
        </form>
    </div>
</div>