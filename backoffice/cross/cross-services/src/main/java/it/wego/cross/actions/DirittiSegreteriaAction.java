/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheDirittiSegreteria;
import it.wego.cross.service.PraticheService;
import it.wego.utils.json.JsonBuilder;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gabriele
 */
@Component
public class DirittiSegreteriaAction {

    @Autowired
    private PraticheService praticheService;

    @Transactional
    public void salvaDirittiSegreteria(Map<String, Object> parametri) throws Exception {
        Map<String, String> cleanParametersMap = JsonBuilder.cleanParametersMap(parametri);
        
        Integer idPratica = Integer.valueOf(cleanParametersMap.get("id_pratica"));
        Pratica pratica = praticheService.getPratica(idPratica);
        PraticheDirittiSegreteria dirittiSegreteria = pratica.getDirittiSegreteria();
        if (dirittiSegreteria == null) {
            dirittiSegreteria = new PraticheDirittiSegreteria(idPratica);
            pratica.setDirittiSegreteria(dirittiSegreteria);
        }

        dirittiSegreteria.setUrbPagamentoUnicaSoluzione(cleanParametersMap.get("urb_pagamento_unica_soluzione"));
        dirittiSegreteria.setUrbTotale(cleanParametersMap.get("urb_totale"));
        dirittiSegreteria.setUrbBanca(cleanParametersMap.get("urb_banca"));
        dirittiSegreteria.setUrbData(cleanParametersMap.get("urb_data"));
        dirittiSegreteria.setUrbImporto(cleanParametersMap.get("urb_importo"));
        dirittiSegreteria.setUrbRataunoImporto(cleanParametersMap.get("urb_ratauno_importo"));
        dirittiSegreteria.setUrbRataunoDataPrevista(cleanParametersMap.get("urb_ratauno_data_prevista"));
        dirittiSegreteria.setUrbRataunoData(cleanParametersMap.get("urb_ratauno_data"));
        dirittiSegreteria.setUrbRatadueImporto(cleanParametersMap.get("urb_ratadue_importo"));
        dirittiSegreteria.setUrbRatadueDataPrevista(cleanParametersMap.get("urb_ratadue_data_prevista"));
        dirittiSegreteria.setUrbRatadueData(cleanParametersMap.get("urb_ratadue_data"));
        dirittiSegreteria.setUrbRatatreImporto(cleanParametersMap.get("urb_ratatre_importo"));
        dirittiSegreteria.setUrbRatatreDataPrevista(cleanParametersMap.get("urb_ratatre_data_prevista"));
        dirittiSegreteria.setUrbRatatreData(cleanParametersMap.get("urb_ratatre_data"));
        dirittiSegreteria.setConPagamentoUnicaSoluzione(cleanParametersMap.get("con_pagamento_unica_soluzione"));
        dirittiSegreteria.setConTotale(cleanParametersMap.get("con_totale"));
        dirittiSegreteria.setConBanca(cleanParametersMap.get("con_banca"));
        dirittiSegreteria.setConData(cleanParametersMap.get("con_data"));
        dirittiSegreteria.setConImporto(cleanParametersMap.get("con_importo"));
        dirittiSegreteria.setConRataunoImporto(cleanParametersMap.get("con_ratauno_importo"));
        dirittiSegreteria.setConRataunoDataPrevista(cleanParametersMap.get("con_ratauno_data_prevista"));
        dirittiSegreteria.setConRataunoData(cleanParametersMap.get("con_ratauno_data"));
        dirittiSegreteria.setConRatadueImporto(cleanParametersMap.get("con_ratadue_importo"));
        dirittiSegreteria.setConRatadueDataPrevista(cleanParametersMap.get("con_ratadue_data_prevista"));
        dirittiSegreteria.setConRatadueData(cleanParametersMap.get("con_ratadue_data"));
        dirittiSegreteria.setTarImportoDovuto(cleanParametersMap.get("tar_importo_dovuto"));
        dirittiSegreteria.setTarImportoPagato(cleanParametersMap.get("tar_importo_pagato"));
        dirittiSegreteria.setTarImportoConguaglio(cleanParametersMap.get("tar_importo_conguaglio"));
    }
}
