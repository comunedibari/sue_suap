/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.cross.service;

import it.wego.utils.json.JsonResponse;
import it.wego.utils.wegoforms.FormEngine;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author Gabriele
 */
@Service
public interface DirittiSegreteriaService {
    
    public void initDirittiSegreteria(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;
    
    public JsonResponse salvaDirittiSegreteria(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;
}
