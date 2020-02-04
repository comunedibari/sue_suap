[#ftl]
<script type="text/javascript" >
    [#include "ricercaProcedimenti.js.ftl"]
</script>
<script type="text/javascript" >
    $(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });
    });
</script>
        <form id="form_ricerca" action="${data.path}/utenti/procedimenti/select/ajax.htm" class="uniForm inlineLabels" method="post">
            <input type="hidden" id="idUtenteRuoloEnte" name="idUtenteRuoloEnte" value="${data.idUtenteRuoloEnte!}" />
            <div style="width:1080px;">    
                <div class="ricerca_div_forms">  
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <label for="procedimento">Descrizione</label>
                            <input id="procedimento" name="procedimento" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.procedimento!}"/>
                        </div>
                        <div class="ctrlHolder">
                            <label for="search_stato_procedimento_utente">Stato</label>
                            <select name="search_stato_procedimento_utente">
                                <option value="" selected="selected">Tutti gli stati</option>
                                <option value="ABILITATO">Abilitato</option>
                                <option value="NON ABILITATO">Non abilitato</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                    <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9">Cerca</button>
                </div>
            </div>    
        </form>
