[#ftl]
<script type="text/javascript" >
    [#include "ricercaPratiche.js.ftl"]
    $(function() {
        $('.ui-dialog-buttonset>button:first-child').after('<button type="button" id="ricerca_button" class=""><i class="ace-icon fa fa-search orange bigger-110"></i>&nbsp; Cerca</button>');
        $("#ricerca_button").on('click',function(e) {
            var urli = $('#${tableId}').getGridParam("url").split("?")[0];
            $('#${tableId}').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
            $(this).closest('.ui-dialog-content').dialog('close');
            $('#${tableId}').trigger("reloadGrid", [{page: 1}]);
        });
    });
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
                    <div class="row">
                        <div class="col-md-3">
                            <label for="ricercaAnagraficaNome">Cognome Nome / Ragione Sociale</label>
                            <input id="ricercaAnagraficaNome" name="ricercaAnagraficaNome" size="35" maxlength="50" type="text" class="form-control">
                        </div>

                        <div class="col-md-3">
                            <label for="ricercaAnagraficaCF">CF/Partita IVA</label>
                            <input id="ricercaAnagraficaCF" name="ricercaAnagraficaCF" size="35" maxlength="50" type="text" class="form-control">
                        </div>
                    </div>
                </fieldset>
                <fieldset id="praticaField">
                    <legend>Pratica</legend>
                    <div class="row">

                        <div class="col-md-3">
                            <label for="search_annoRiferimento">Anno riferimento</label>
                            <select id="search_annoRiferimento" name="search_annoRiferimento" class="form-control">
                                <option value="" >Non specificato</option>
                            [#list anniRiferimento as anno]
                                <option value="${anno}">${anno}</option>
                            [/#list]
                            </select>                            
                        </div>

                        <div class="col-md-4">
                            <label for="search_id_pratica">Numero di protocollo</label>
                            <input id="search_id_pratica" name="search_id_pratica"  maxlength="50" type="text" class="form-control">
                        </div>
                        
                        <div class="col-md-1">
                            <label for="search_data_from">Da</label>
                            <input type="text" class="input-sm form-control" name="search_data_from" id="search_data_from">
                        </div>

                        <div class="col-md-1">
                            <label for="search_data_to">A</label>
                            <input type="text" class="input-sm form-control" name="search_data_to" id="search_data_to">

                        </div>
                    </div>
                        
                    <div class="row">
                        <div class="col-md-3">
                            <label for="search_ente">Ente</label>
                            <select id="search_ente" name="search_ente" class="form-control">
                                <option  value="" >Qualsiasi ente</option>
                            [#list enti as ente]
                                <option  value="${ente.idEnte}">${ente.descrizione}</option>
                            [/#list]
                            </select>
                        </div>
                        
                        <div class="col-md-4">
                            <label for="search_des_comune_pratica">Comune</label>
                            <div class="input-group">
                                <input id="search_des_comune_pratica" name="search_des_comune" size="35" maxlength="255" type="text" class="form-control">
                                <input id="search_id_comune_pratica" name="search_id_comune" size="35" maxlength="35" type="hidden">
                                <span class="input-group-addon">
                                    <i id="search_button_pratica" class="fa fa-times bigger-110"></i>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <label for="search_stato">Stato della pratica</label>
                            <select id="search_stato" name="search_stato" class="form-control">
                                <option value="ATTIVE" >Pratiche attive</option>
                                <option value="CHIUSE" >Pratiche chiuse</option>
                                <option value="ALL" >Qualsiasi stato</option>
                            [#list statiPratica as stato]
                                <option value="${stato.idStatoPratica}">${stato.descrizione}</option>
                            [/#list]
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="search_all"></label>
                            <div>
                                <label>
                                    <input class="ace" type="checkbox" id="search_all" name="search_all" value="ok" >
                                    <span class="lbl"> Visualizza tutte le pratiche</span>
                                </label>
                            </div>
                        </div>
                        
                    </div>    

                </fieldset>

                <fieldset>
                    <legend>Dati catastali / Indirizzo intervento</legend>

                    <div class="row">
                    
                        <div class="col-md-3">
                            <label for="search_tipoSistemaCatastale">Tipo catasto</label>
                            <select id="search_tipoSistemaCatastale" name="search_tipoSistemaCatastale" class="form-control" onchange="changeTipoCatasto();">
                                <option  value="" >Non specificato</option>
                            [#list lkTipiCatasto as tipoCatasto]
                                <option  value="${tipoCatasto.idTipoSistemaCatastale}">${tipoCatasto.descrizione}</option>
                            [/#list]
                            </select>
                        </div>
                        
                        <div class="col-md-3 hidden" id="divSearch_tipoUnita">
                            <label for="search_tipoUnita">Tipo unita'</label>
                            <select id="search_tipoUnita" name="search_tipoUnita" class="form-control">
                                <option  value="" >Non specificato</option>
                            [#list lkTipiUnita as tipoUnita]
                                <option  value="${tipoUnita.idTipoUnita}">${tipoUnita.descrizione}</option>
                            [/#list]
                            </select>
                        </div>
                        
                        <div class="col-md-3 hidden" id="divSearch_tipoParticella">
                            <label for="search_tipoParticella">Tipo particella</label>
                            <select id="search_tipoParticella" name="search_tipoParticella" class="form-control">
                                <option  value="" >Non specificato</option>
                            [#list lkTipiParticella as tipoParticella]
                                <option  value="${tipoParticella.idTipoParticella}">${tipoParticella.descrizione}</option>
                            [/#list]
                            </select>
                        </div>

                    </div>

                    <div class="row">
                    
                        <div class="col-md-3 hidden" id="divSearch_sezione">
                            <label for="search_sezione">Sezione</label>
                            <input id="search_sezione" name="search_sezione" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3 hidden" id="divSearch_comuneCensuario">
                            <label for="search_comuneCensuario">Comune censuario</label>
                            <input id="search_comuneCensuario" name="search_comuneCensuario" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3 hidden" id="divSearch_foglio">
                            <label for="search_foglio">Foglio</label>
                            <input id="search_foglio" name="search_foglio" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3" id="divSearch_mappale">
                            <label for="search_mappale">Mappale/Particella</label>
                            <input id="search_mappale" name="search_mappale" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3 hidden" id="divSearch_estensioneParticella">
                            <label for="search_estensioneParticella">Estensione particella</label>
                            <input id="search_estensioneParticella" name="search_estensioneParticella" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3" id="divSearch_subalterno">
                            <label for="search_subalterno">Subalterno</label>
                            <input id="search_subalterno" name="search_subalterno" size="35" maxlength="30" type="text" class="form-control">
                        </div>
                    
                    </div>
                    <div class="row">
                    
                        <div class="col-md-3">
                            <label for="search_indirizzo">Indirizzo</label>
                            <input id="search_indirizzo" name="search_indirizzo" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3">
                            <label for="search_civico">Civico</label>
                            <input id="search_subalterno" name="search_civico" size="35" maxlength="30" type="text" class="form-control">
                        </div>                        
                    
                        <div class="col-md-3">
                            <label for="search_des_comune_catasto">Comune</label>
                            <div class="input-group">
                                <input id="search_des_comune_catasto" name="search_des_comune" size="35" maxlength="255" type="text" class="form-control">
                                <input id="search_id_comune_catasto" name="search_id_comune" size="35" maxlength="35" type="hidden">
                                <span class="input-group-addon">
                                    <i id="search_button_catasto" class="fa fa-times bigger-110"></i>
                                </span>
                            </div>
                        </div>
                
                    </div>                   
                </fieldset>
            </div>

        </div>
    </div>
</form>