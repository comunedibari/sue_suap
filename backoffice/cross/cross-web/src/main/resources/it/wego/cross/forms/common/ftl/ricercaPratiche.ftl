[#ftl]
<script type="text/javascript" >
    [#include "ricercaPratiche.js.ftl"]
</script>
[#assign anniRiferimento=data.anniRiferimento]
[#assign enti=data.enti]
[#assign statiPratica=data.statiPratica]
[#assign lkTipiCatasto=data.lkTipiCatasto]
[#assign lkTipiUnita=data.lkTipiUnita]
[#assign lkTipiParticella=data.lkTipiParticella]

<form id="form_ricerca" action="${data.path}/pratiche/comunicazione/dettaglio_search.htm" class="uniForm inlineLabels" method="post">
    <input name="tipoFiltro" type="hidden" value="utente" />
    <div>    
        <div class="ricerca_div_forms">    
            <div class="inlineLabels">
                <fieldset id="richiedentiBeneficiariField"> 
                    <legend>Richiedenti / Beneficiari</legend>
                    <div class="ctrlHolder" id="divRicercaAnagraficaNome">
                        <label for="ricercaAnagraficaNome">Cognome Nome / Ragione Sociale</label>
                        <input id="ricercaAnagraficaNome" name="ricercaAnagraficaNome" size="35" maxlength="50" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder" id="divRicercaAnagraficaCF">
                        <label for="ricercaAnagraficaCF">CF/Partita IVA</label>
                        <input id="ricercaAnagraficaCF" name="ricercaAnagraficaCF" size="35" maxlength="50" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                </fieldset>
                <fieldset id="praticaField">
                    <legend>Pratica</legend>
                    <div class="ctrlHolder" id="divSearch_annoRiferimento">
                        <label for="search_annoRiferimento">Anno riferimento</label>
                        <select id="search_annoRiferimento" name="search_annoRiferimento" class="textInput">
                            <option value="" >Non specificato</option>
                            [#list anniRiferimento as anno]
                                <option value="${anno}">${anno}</option>
                            [/#list]
                        </select>
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder" id="divSearch_id_pratica"> 
                        <label for="search_id_pratica">Numero di protocollo</label>
                        <input id="search_id_pratica" name="search_id_pratica" size="35" maxlength="50" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>  
                    <div class="ctrlHolder" id="divSearch_data_from">
                        <label for="search_data_from">Da</label>
                        <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder" id="divSearch_data_to">
                        <label for="search_data_to">A</label>
                        <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder" id="divSearch_ente">
                        <label for="search_ente">Ente</label>
                        <select id="search_ente" name="search_ente" class="textInput">
                            <option  value="" >Qualsiasi ente</option>
                            [#list enti as ente]
                                <option  value="${ente.idEnte}">${ente.descrizione}</option>
                            [/#list]
                        </select>
                        <p class="formHint"></p>
                    </div> 
                    <div class="ctrlHolder" id="divSearch_des_comune_pratica">
                    <label for="search_des_comune_pratica">Comune</label>
                    <input id="search_des_comune_pratica" name="search_des_comune" size="35" maxlength="255" type="text" style="width: 60%;" class="textInput">
                    <input id="search_id_comune_pratica" name="search_id_comune" size="35" maxlength="35" type="hidden" class="textInput">
                    <button type="button" id="search_button_pratica" style="
                            margin-left: 10px;
                            margin-bottom: 0px;
                            margin-top: 0px;
                            float: left;
                            ">X</button>
                    <p class="formHint"></p>
                </div>          
                    <div class="ctrlHolder" id="divSearch_stato">
                        <label for="search_stato">Stato della pratica</label>
                        <select id="search_stato" name="search_stato" class="textInput">
                            <option value="ATTIVE" >Pratiche attive</option>
                            <option value="CHIUSE" >Pratiche chiuse</option>
                            <option value="ALL" >Qualsiasi stato</option>
                            [#list statiPratica as stato]
                                <option value="${stato.idStatoPratica}">${stato.descrizione}</option>
                            [/#list]
                        </select>
                        <p class="formHint"></p>
                    </div>   
                    <div class="ctrlHolder" id="divSearch_all">
                        <label for="search_all">Visualizza tutte le pratiche</label>
                        <input id="search_all" name="search_all" type="checkbox" class="textInput checkbox_width" value="ok"  /></label>
                        <p class="formHint"></p>
                    </div>
                </fieldset>

                <fieldset id="CatastoField" class="fieldsettop">
                    <legend>Dati catastali / Indirizzo intervento</legend>

                    <div class="ctrlHolder" id="divSearch_tipoSistemaCatastale">
                        <label for="search_tipoSistemaCatastale">Tipo catasto</label>
                        <select id="search_tipoSistemaCatastale" name="search_tipoSistemaCatastale" class="textInput" onchange="changeTipoCatasto();">
                            <option  value="" >Non specificato</option>
                            [#list lkTipiCatasto as tipoCatasto]
                                    <option  value="${tipoCatasto.idTipoSistemaCatastale}">${tipoCatasto.descrizione}</option>
                            [/#list]
                        </select>
                        <p class="formHint"></p>
                    </div>   
                    <div class="ctrlHolder hidden" id="divSearch_tipoUnita">
                        <label for="search_tipoUnita">Tipo unita'</label>
                        <select id="search_tipoUnita" name="search_tipoUnita" class="textInput">
                            <option  value="" >Non specificato</option>
                            [#list lkTipiUnita as tipoUnita]
                                <option  value="${tipoUnita.idTipoUnita}">${tipoUnita.descrizione}</option>
                            [/#list]
                        </select>
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder hidden" id="divSearch_tipoParticella">
                        <label for="search_tipoParticella">Tipo particella</label>
                        <select id="search_tipoParticella" name="search_tipoParticella" class="textInput">
                            <option  value="" >Non specificato</option>
                            [#list lkTipiParticella as tipoParticella]
                                <option  value="${tipoParticella.idTipoParticella}">${tipoParticella.descrizione}</option>
                            [/#list]
                        </select>
                        <p class="formHint"></p>
                    </div> 
                </fieldset>

                <fieldset id="CatastoFields" class="fieldsetleftright"> 
                    <div class="ctrlHolder hidden" id="divSearch_sezione">
                        <label for="search_sezione">Sezione</label>
                        <input id="search_sezione" name="search_sezione" size="35" maxlength="30" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder hidden" id="divSearch_comuneCensuario">
                        <label for="search_comuneCensuario">Comune censuario</label>
                        <input id="search_comuneCensuario" name="search_comuneCensuario" size="35" maxlength="30" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>                                
                    <div class="ctrlHolder hidden" id="divSearch_foglio">
                        <label for="search_foglio">Foglio</label>
                        <input id="search_foglio" name="search_foglio" size="35" maxlength="30" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder" id="divSearch_mappale">
                        <label for="search_mappale">Mappale/Particella</label>
                        <input id="search_mappale" name="search_mappale" size="35" maxlength="30" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                    <div class="ctrlHolder hidden" id="divSearch_estensioneParticella">
                        <label for="search_estensioneParticella">Estensione particella</label>
                        <input id="search_estensioneParticella" name="search_estensioneParticella" size="35" maxlength="30" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>                                 
                    <div class="ctrlHolder" id="divSearch_subalterno">
                        <label for="search_subalterno">Subalterno</label>
                        <input id="search_subalterno" name="search_subalterno" size="35" maxlength="30" type="text" class="textInput">
                        <p class="formHint"></p>
                    </div>
                </fieldset>
                <fieldset id="CatastoIndirizzoFields" class="fieldsetbottom">
                    <div class="ctrlHolder" id="divSearch_indirizzo">
                    <label for="search_indirizzo">Indirizzo</label>
                    <input id="search_indirizzo" name="search_indirizzo" size="35" maxlength="30" type="text" class="textInput">
                    <p class="formHint"></p>
                </div>
                    <div class="ctrlHolder" id="divSearch_civico">
                    <label for="search_civico">Civico</label>
                    <input id="search_subalterno" name="search_civico" size="35" maxlength="30" type="text" class="textInput">
                    <p class="formHint"></p>
                </div>
                    <div class="ctrlHolder" id="divSearch_des_comune_catasto">
                    <label for="search_des_comune_catasto">Comune</label>
                    <input id="search_des_comune_catasto" name="search_des_comune" size="35" maxlength="255" type="text" style="width: 60%;" class="textInput">
                    <input id="search_id_comune_catasto" name="search_id_comune" size="35" maxlength="35" type="hidden" class="textInput">
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
            <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9">Cerca</button>				
        </div>
    </div>
</form>