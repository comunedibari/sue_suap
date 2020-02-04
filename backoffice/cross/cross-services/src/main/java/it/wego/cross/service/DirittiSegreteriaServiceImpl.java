/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.wego.cross.actions.DirittiSegreteriaAction;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheDirittiSegreteria;
import it.wego.cross.exception.CrossException;
import it.wego.cross.utils.Log;
import it.wego.utils.json.JsonBuilder;
import it.wego.utils.json.JsonMapTransformer;
import static it.wego.utils.json.JsonMapTransformer.LABEL;
import static it.wego.utils.json.JsonMapTransformer.VALUE;
import it.wego.utils.json.JsonResponse;
import it.wego.utils.wegoforms.FormEngine;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author Gabriele
 */
@Service
public class DirittiSegreteriaServiceImpl implements DirittiSegreteriaService {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private DirittiSegreteriaAction dirittiSegreteriaAction;
    @Autowired
    private AnagraficheDao anagraficheDao;

    @Override
    public void initDirittiSegreteria(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        Integer idPratica = (Integer) model.asMap().get("idPatica");
        Pratica pratica = praticheService.getPratica(idPratica);
        PraticheDirittiSegreteria dirittiSegreteria = pratica.getDirittiSegreteria();

        if (dirittiSegreteria != null) {
            formEngine.putInstanceDataValue("id_pratica", idPratica.toString());
            formEngine.putInstanceDataValue("urb_pagamento_unica_soluzione", dirittiSegreteria.getUrbPagamentoUnicaSoluzione());
            formEngine.putInstanceDataValue("urb_totale", dirittiSegreteria.getUrbTotale());
            formEngine.putInstanceDataValue("urb_banca", dirittiSegreteria.getUrbBanca());
            formEngine.putInstanceDataValue("urb_data", dirittiSegreteria.getUrbData());
            formEngine.putInstanceDataValue("urb_importo", dirittiSegreteria.getUrbImporto());
            formEngine.putInstanceDataValue("urb_ratauno_importo", dirittiSegreteria.getUrbRataunoImporto());
            formEngine.putInstanceDataValue("urb_ratauno_data_prevista", dirittiSegreteria.getUrbRataunoDataPrevista());
            formEngine.putInstanceDataValue("urb_ratauno_data", dirittiSegreteria.getUrbRataunoData());
            formEngine.putInstanceDataValue("urb_ratadue_importo", dirittiSegreteria.getUrbRatadueImporto());
            formEngine.putInstanceDataValue("urb_ratadue_data_prevista", dirittiSegreteria.getUrbRatadueDataPrevista());
            formEngine.putInstanceDataValue("urb_ratadue_data", dirittiSegreteria.getUrbRatadueData());
            formEngine.putInstanceDataValue("urb_ratatre_importo", dirittiSegreteria.getUrbRatatreImporto());
            formEngine.putInstanceDataValue("urb_ratatre_data_prevista", dirittiSegreteria.getUrbRatatreDataPrevista());
            formEngine.putInstanceDataValue("urb_ratatre_data", dirittiSegreteria.getUrbRatatreData());
            formEngine.putInstanceDataValue("con_pagamento_unica_soluzione", dirittiSegreteria.getConPagamentoUnicaSoluzione());
            formEngine.putInstanceDataValue("con_totale", dirittiSegreteria.getConTotale());
            formEngine.putInstanceDataValue("con_banca", dirittiSegreteria.getConBanca());
            formEngine.putInstanceDataValue("con_data", dirittiSegreteria.getConData());
            formEngine.putInstanceDataValue("con_importo", dirittiSegreteria.getConImporto());
            formEngine.putInstanceDataValue("con_ratauno_importo", dirittiSegreteria.getConRataunoImporto());
            formEngine.putInstanceDataValue("con_ratauno_data_prevista", dirittiSegreteria.getConRataunoDataPrevista());
            formEngine.putInstanceDataValue("con_ratauno_data", dirittiSegreteria.getConRataunoData());
            formEngine.putInstanceDataValue("con_ratadue_importo", dirittiSegreteria.getConRatadueImporto());
            formEngine.putInstanceDataValue("con_ratadue_data_prevista", dirittiSegreteria.getConRatadueDataPrevista());
            formEngine.putInstanceDataValue("con_ratadue_data", dirittiSegreteria.getConRatadueData());
            formEngine.putInstanceDataValue("tar_importo_dovuto", dirittiSegreteria.getTarImportoDovuto());
            formEngine.putInstanceDataValue("tar_importo_pagato", dirittiSegreteria.getTarImportoPagato());
            formEngine.putInstanceDataValue("tar_importo_conguaglio", dirittiSegreteria.getTarImportoConguaglio());
        }
    }

    @Override
    public JsonResponse salvaDirittiSegreteria(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Salvataggio completato correttamente";
        try {
            if (request.getParameter("id_pratica") == null) {
                throw new CrossException("Impossibile trovare la pratica su cui salvare i diritti di segreteria");
            }
            dirittiSegreteriaAction.salvaDirittiSegreteria(request.getParameterMap());
        } catch (Exception e) {
            result = Boolean.FALSE;
            message = "Si è verificato un errore durante il salvataggio dei diritti di segreteria";
            Log.APP.error("Errore durante il salvataggio dei diritti segreteria", e);
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

}
