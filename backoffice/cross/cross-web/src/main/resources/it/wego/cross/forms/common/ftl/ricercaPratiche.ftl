[#ftl]
<script type="text/javascript">
    [#include "ricercaPratiche.js.ftl"]
</script>
[#assign anniRiferimento=data.anniRiferimento] 
[#assign enti=data.enti]
[#assign statiPratica=data.statiPratica] 
[#assign lkTipiCatasto=data.lkTipiCatasto] 
[#assign lkTipiUnita=data.lkTipiUnita]
[#assign lkTipiParticella=data.lkTipiParticella] 
[#assign
lkTipoPratica=data.lkTipoPratica] 
[#assign operatori=data.operatori]


<form id="form_ricerca"
	action="${data.path}/pratiche/comunicazione/dettaglio_search.htm"
	class="uniForm inlineLabels" method="post">
	<input name="tipoFiltro" type="hidden" value="utente" />
	<div>
		
		<div>
			<fieldset id="divRichiedentiBeneficiariField" class="research-fieldset">
				<legend class="legend">Richiedenti / Beneficiari <label
						for="search_flag_catasto">OR <input
							id="search_flag_anagrafica" name="search_flag_anagrafica"
							type="checkbox" value="1" />
					</label></legend>
			</fieldset>
			<fieldset class="research-fieldset" id="ResearchFieldset">
				<legend class="legend">Pratica</legend>
				<div class="row">
					<div class="width15">
						<label for="search_id_pratica"> Id pratica</label> <input
							id="search_idpratica" name="search_idpratica" size="35"
							maxlength="50" type="text" class="textInput">
					</div>
					<div class="width15">
						<label>Identificativo Pratica</label> <input
							id="search_identificativo_pratica"
							name="search_identificativo_pratica" size="35" maxlength="50"
							type="text" class="textInput" />
					</div>

					<div class="width15">
						<label for="search_annoRiferimento">Anno fascicolo</label> <select
							id="search_annoRiferimento" name="search_annoRiferimento"
							class="textInput">
							<option value="">Non specificato</option> [#list anniRiferimento as anno]
							<option value="${anno}">${anno}</option> [/#list]
						</select>
					</div>

					<div class="width20">
						<label for="search_id_pratica">Numero protocollo</label> <input
							id="search_protocollo_evento" name="search_protocollo_evento"
							size="35" maxlength="50" type="text" class="textInput" />
					</div>

					<div class="width20">
						<label for="search_id_pratica">Numero di fascicolo</label> <input
							id="search_id_pratica" name="search_id_pratica" size="35"
							maxlength="50" type="text" class="textInput" />
					</div>
				</div>
				<div class="row">
					<div class="width30">
						<label>Tipo pratica</label> <select id="search_tipo_pratica"
							name="search_tipo_pratica"
							class="textInput select chosen-select"
							data-placeholder="Seleziona un tipo Pratica">
							<option value=""></option> [#list lkTipoPratica as tipoPratica]
							[#if tipoPratica.descrizione?has_content]
							<option value="${tipoPratica.idTipoPratica}">${tipoPratica.tipoPratica} - ${tipoPratica.descrizione}</option>.
							[/#if] [/#list]
						</select>
					</div>
					<div class="width20">
						<label>Numero pratica</label> <input id="search_numero_pratica"
							name="search_numero_pratica" size="35" maxlength="50"
							type="text" class="textInput">
					</div>

					<div class="width20">
						<label>Anno</label> <select id="search_anno_pratica"
							name="search_anno_pratica" class="textInput select">
							<option value=""><spring:message
									code="ricerca.inizio.anno" /></option> [#list anniRiferimento as anno]
							<option value="${anno}">${anno}</option> [/#list]
						</select>
					</div>
				</div>
				<div class="row">
					<div class="width10">
						<label for="search_data_from">Da</label> <input
							id="search_data_from" name="search_data_from" size="35"
							maxlength="50" type="text" class="textInput" />
					</div>
					<div class="width10 ">
						<label for="search_data_to">A</label> <input id="search_data_to"
							name="search_data_to" size="35" maxlength="50" type="text"
							class="textInput" />
					</div>
					<div class="width25">
	                       <input type="hidden" />
	                   </div>
					<div class="width50">
                        <label for="search_descrizione_intervento">Descrizione intervento</label>
                        <input id="search_descrizione_intervento" name="search_descrizione_intervento" size="35" maxlength="50" type="text" class="textInput" />
                    </div>
				</div>
				<div class="row">
                    <div class="width50">
                        <label>Oggetto</label>
                        <input id="search_oggetto" name="search_oggetto" size="35" maxlength="50" type="text" class="textInput" />
                        <p class="formHint"></p>
                    </div>
                </div>
				<div class="row">
					<div class="width30">
						<label for="search_ente">Ente</label> <select id="search_ente"
							name="search_ente" class="textInput">
							<option value="">Qualsiasi ente</option> [#list enti as ente]
							<option value="${ente.idEnte}">${ente.descrizione}</option>
							[/#list]
						</select>
					</div>

					<div class="width30">
						<label for="search_stato">Stato della pratica</label> <select
							id="search_stato" name="search_stato" class="textInput">
							<option value="ALL">Qualsiasi stato</option> 
							<option value="ATTIVE">Pratiche attive</option>
							<option value="CHIUSE">Pratiche chiuse</option>
							[#list statiPratica
							as stato]
							<option value="${stato.idStatoPratica}">${stato.descrizione}</option>
							[/#list]
						</select>
					</div>

					<div class="width30">
						<label>Operatore</label> <select id="search_operatore"
							name="search_operatore" class="select textInput">
							<option value="">Tutti gli operatori</option> [#list operatori
							as operatore]
							<option value="${operatore.idUtente}">${operatore.cognome}
								${operatore.nome}</option> [/#list]
						</select>
					</div>
				</div>
			</fieldset>

			<fieldset id="divCatastoFields" class="research-fieldset">
				<legend class="legend">
					Dati Catastali <span id="divSearch_flag_catasto"> <label
						for="search_flag_catasto">OR <input
							id="search_flag_catasto" name="search_flag_catasto"
							type="checkbox" value="1" />
					</label>
					</span>
				</legend>
			</fieldset>

			<fieldset id="divCatastoIndirizzoFields" class="research-fieldset">
				<legend class="legend">
					Indirizzi intervento <span id="divSearch_flag_indirizzi"> <label
						for="search_flag_indirizzi">OR <input
							id="search_flag_indirizzi" name="search_flag_indirizzi"
							type="checkbox" value="1" />
					</label>
					</span>
				</legend>

				<p class="formHint"></p>

			</fieldset>
		</div>

		
		<div class="buttonHolder"
			style="background-color: #E6E6E6; text-align: center">
			<button id="ricerca_button2" type="button"
				class="primaryAction fondo_destra" style="background-color: #0067A9">Cerca</button>
		</div>
	</div>
</form>

<div id="richiedentiBeneficiariTemplate" class="hidden">[#include
	"research_identification_fragment.ftl"]</div>

<div id="CatastoIndirizzoFieldsTemplate" class="hidden">[#include
	"research_cadastral_address_fragment.ftl"]</div>

<div id="CatastoFieldsTemplate" class="hidden">[#include
	"research_cadastral_data_fragment.ftl"]</div>

<script type="text/javascript">
	$(".chosen-select").chosen({allow_single_deselect: true});    
</script>

