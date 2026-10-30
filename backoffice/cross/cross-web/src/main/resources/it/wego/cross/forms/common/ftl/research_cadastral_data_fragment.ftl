[#ftl]
[#assign lkTipiUnita=data.lkTipiUnita]

<span id="CatastoField">
	<div class="row">
		<div class="width20" id="divSearch_tipoUnita">
	         <label for="search_tipoUnita">Tipo unita'</label>
	         <select id="search_tipoUnita" name="search_tipoUnita" class="textInput">
	             <option  value="" >Non specificato</option>
	             [#list lkTipiUnita as tipoUnita]
		              [#if tipoUnita.idTipoUnita == 1 || tipoUnita.idTipoUnita == 2]
	                 		<option  value="${tipoUnita.idTipoUnita}">${tipoUnita.descrizione}</option>
	                 [/#if]
	             [/#list]
	         </select>
	         <p class="formHint"></p>
	     </div>
	
		<div class="width5" id="divSearch_sezione">
		    <label for="search_sezione">Sezione</label>
		    <input id="search_sezione" name="search_sezione" size="35" maxlength="30" type="text" class="textInput" />
		    <p class="formHint"></p>
		</div>
		                              
		<div class="width5" id="divSearch_foglio">
		    <label for="search_foglio">Foglio</label>
		    <input id="search_foglio" name="search_foglio" size="35" maxlength="30" type="text" class="textInput" />
		    <p class="formHint"></p>
		</div>
		
		<div class="width10" id="divSearch_mappale">
		    <label for="search_mappale">Mappale</label>
		    <input id="search_mappale" name="search_mappale" size="35" maxlength="30" type="text" class="textInput" />
		    <p class="formHint"></p>
		</div>
		                           
		<div class="width20" id="divSearch_subalterno">
		    <label for="search_subalterno">subalterno</label>
		    <input id="search_subalterno" name="search_subalterno" size="35" maxlength="30" type="text" class="textInput" />
			<input size="35" maxlength="35" type="hidden" class="textInput" />
		</div>
	
		<div class="width5">	
			<span id="delete_cadastral_data_btn" class="button_operation" type="button" idx="0">
				<img src="/cross/themes/default/images/icons/reject.png" />
			</span>
			<span id="add_cadastral_data_btn" class="button_operation" type="button" idx="0" >
				<img src="/cross/themes/default/images/icons/add.png" />
			</span>
		</div>
	</div>
	<p class="formHint"></p>
</span>