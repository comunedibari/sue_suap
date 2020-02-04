/**
 * @author CS
 */
    $(".impostaVarianteAnagrafica input").click(function(){
        var tipoAnagrafica = $(".personaFisica .dettaglio > select").val();
        var selTipoAnagrafica = $(this).attr("id");
        if($(this).attr("checked")!=null)
        {
            $(".impostaVarianteAnagrafica input:checked").removeAttr("checked");
            $(this).attr("checked","checked");
            var select = "personaFisica";
            if(selTipoAnagrafica=='P')
            {
                select = "anagraficaProfessionista";
            }
            if(selTipoAnagrafica=='I')
            {
                 select = "anagraficaDittaIndividuale";
            }
            $(".personaFisica ."+select).show();
            $(".personaFisica ."+select).removeClass("hidden");
            $(".personaFisica ."+select+" input").removeAttr("disabled");
            $("#anagraficaContent #varianteAnagrafica").val(selTipoAnagrafica);
        }
        else
        {
            if(selTipoAnagrafica=='P')
            {
                select = "anagraficaProfessionista";
            }
            if(selTipoAnagrafica=='I')
            {
                 select = "anagraficaDittaIndividuale";
            }
            $(".personaFisica ."+select).hide();
            $("#anagraficaContent #varianteAnagrafica").val("");
        }
    });