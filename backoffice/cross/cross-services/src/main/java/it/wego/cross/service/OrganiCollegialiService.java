/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.dozer.forms.OrganiCollegialiCommissioneDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiSeduteDTO;
import it.wego.cross.dto.dozer.PraticaOrganoCollegialeDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiSeduteComissioneDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.utils.json.JsonResponse;
import it.wego.utils.wegoforms.FormEngine;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author giuseppe
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface OrganiCollegialiService {

    public List<OrganiCollegialiDTO> findAllOragniCollegialiOrdered(Integer idEnte) throws Exception;

    public void salvaAvvioConferenzaServizi(PraticaOrganoCollegialeDTO pocDTO) throws Exception;

    public Long countOrganiCollegiali(Filter filter);

    public List<OrganiCollegialiDTO> getOrganiCollegiali(Filter filter);

    public void getOrganiCollegiali(HttpServletRequest request, Model model, FormEngine formEngin, Map<String, Object> formParameters) throws Exception;

    public JsonResponse insertOrganoCollegiale(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse salvaOrganoCollegiale(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public void initModificaOrganiCollegiali(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public Long countOrganiCollegialiSedute(Filter filter);

    public List<OrganiCollegialiSeduteDTO> getOrganiCollegialiSedute(Filter filter);

    public JsonResponse insertOrganoCollegialeSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public void initModificaOrganiCollegialiSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public void initInsertOrganiCollegialiSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse salvaOrganoCollegialeSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public Long countOrganiCollegialiCommissione(Filter filter);

    public List<OrganiCollegialiCommissioneDTO> getOrganiCollegialiCommissione(Filter filter);

    public void initInsertOrganiCollegialiCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public void initModificaOrganiCollegialiCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse insertOrganoCollegialeCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse salvaOrganoCollegialeCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public Long countOrganiCollegialiSedutePratiche(Filter filter);

    public List<OrganiCollegialiSeduteComissioneDTO> getOrganiCollegialiSedutePratiche(Filter filter);

    public Long countOrganiCollegialiSeduteCommissioneBase(Filter filter);

    public List<OrganiCollegialiSeduteComissioneDTO> getOrganiCollegialiSeduteCommissioneBase(Filter filter);

    public void initInsertOrganiCollegialiSedutePraticheCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse insertOrganoCollegialeSedutePraticheCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public void initModificaOrganiCollegialiSedutePratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse salvaOrganoCollegialeSedutePratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public Long countOrganiCollegialiSeduteCommissionePratiche(Filter filter);

    public List<OrganiCollegialiSeduteComissioneDTO> findOrganiCollegialiSeduteCommissionePratiche(Filter filter);

    public void initGestioneOrdinamentoPratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public JsonResponse salvaGestioneOrdinamentoPratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

    public void initDettaglioPratica(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception;

}
