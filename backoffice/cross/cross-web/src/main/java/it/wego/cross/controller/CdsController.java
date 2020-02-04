/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.common.base.Strings;
import it.wego.cross.actions.CdsAction;
import it.wego.cross.beans.grid.GridOrganiCollegiali;
import it.wego.cross.beans.grid.GridOrganiCollegialiCommissione;
import it.wego.cross.beans.grid.GridOrganiCollegialiSedute;
import it.wego.cross.beans.grid.GridOrganiCollegialiSeduteCommissione;
import it.wego.cross.beans.grid.GridOrganiCollegialiSeduteCommissionePratiche;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.conferenzaservizi.xsd.Seduta;
import it.wego.cross.conferenzaservizi.xsd.Seduta.ComponentiCommissione;
import it.wego.cross.conferenzaservizi.xsd.Seduta.ComponentiCommissione.ComponenteCommissione;
import it.wego.cross.conferenzaservizi.xsd.Seduta.ComponentiCommissione.ComponenteCommissione.Ruolo;
import it.wego.cross.conferenzaservizi.xsd.Seduta.DataConclusione;
import it.wego.cross.conferenzaservizi.xsd.Seduta.DataConvocazione;
import it.wego.cross.conferenzaservizi.xsd.Seduta.Ente;
import it.wego.cross.conferenzaservizi.xsd.Seduta.Pratiche.Pratica;
import it.wego.cross.constants.CDSConstants;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.PermissionConstans;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.OrganiCollegialiDao;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.UtenteRuoloEnteDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiCommissioneDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiSeduteComissioneDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiSeduteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.OcPraticaCommissione;
import it.wego.cross.entity.OcSedutePratiche;
import it.wego.cross.entity.OrganiCollegiali;
import it.wego.cross.entity.OrganiCollegialiSedute;
import it.wego.cross.entity.OrganiCollegialiTemplate;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Templates;
import it.wego.cross.service.DocEngineService;
import it.wego.cross.service.OrganiCollegialiService;
import it.wego.cross.utils.Utils;
import it.wego.utils.json.JsonBuilder;
import it.wego.utils.json.JsonResponse;
import it.wego.utils.wegoforms.parsing.SimpleWgfValue;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author giuseppe
 */
@Controller
public class CdsController extends AbstractController {

    @Autowired
    private OrganiCollegialiService organiCollegialiService;
    @Autowired
    private CdsAction cdsAction;
    @Autowired
    private OrganiCollegialiDao organiCollegialiDao;
    @Autowired
    private DocEngineService docEngineService;

    private static final String AJAX = "ajax";

    @RequestMapping("/cds/organicollegiali/index/ajax")
    public String getOrganiCollegiali(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        for (UtenteRuoloEnteDTO ure : authUser.getUtenteRuoloEnte()) {
            if (ure.getCodPermesso().equals(PermissionConstans.SEGRETERIA)) {
                filter.getListaEnti().add(ure.getIdEnte());
            }
        }

        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        GridOrganiCollegiali json = new GridOrganiCollegiali();
        //Visualizza solo errori attivi
        filter.setTipoFiltro("T");
        Long count = organiCollegialiService.countOrganiCollegiali(filter);
        List<OrganiCollegialiDTO> dtos = organiCollegialiService.getOrganiCollegiali(filter);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/cds/organicollegiali/sedute/index/ajax")
    public String getOrganiCollegialiSedute(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        for (UtenteRuoloEnteDTO ure : authUser.getUtenteRuoloEnte()) {
            if (ure.getCodPermesso().equals(PermissionConstans.SEGRETERIA)) {
                filter.getListaEnti().add(ure.getIdEnte());
            }
        }

        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        GridOrganiCollegialiSedute json = new GridOrganiCollegialiSedute();
        //Visualizza solo errori attivi
        filter.setTipoFiltro("T");
        Long count = organiCollegialiService.countOrganiCollegialiSedute(filter);
        List<OrganiCollegialiSeduteDTO> dtos = organiCollegialiService.getOrganiCollegialiSedute(filter);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/cds/organicollegiali/commissione/index/ajax")
    public String getOrganiCollegialiCommissione(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        filter.setIdOrganiCollegiali(Integer.valueOf(request.getParameter("idOrganiCollegiali[]")));

        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        GridOrganiCollegialiCommissione json = new GridOrganiCollegialiCommissione();
        //Visualizza solo errori attivi
        filter.setTipoFiltro("T");
        Long count = organiCollegialiService.countOrganiCollegialiCommissione(filter);
        List<OrganiCollegialiCommissioneDTO> dtos = organiCollegialiService.getOrganiCollegialiCommissione(filter);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/cds/organicollegiali/commissione/delete")
    public @ResponseBody
    Object organiCollegialiCommissioneDelete(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        JSONArray j = new JSONArray(request.getParameter("idOrganiCollegiali"));
        Integer idOrganiCollegiali = Integer.valueOf((String) j.get(0));
        j = new JSONArray(request.getParameter("idAnagrafica"));
        Integer idAnagrafica = Integer.valueOf((String) j.get(0));

        cdsAction.eliminaOrganoCollegialeCommissione(idOrganiCollegiali, idAnagrafica);

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @RequestMapping("/cds/organicollegiali/sedute/delete")
    public @ResponseBody
    Object organiCollegialiSeduteDelete(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        JSONArray j = new JSONArray(request.getParameter("idSeduta"));
        Integer idSeduta = Integer.valueOf((String) j.get(0));

        cdsAction.eliminaOrganoCollegialeSeduta(idSeduta);

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @RequestMapping("/cds/organicollegiali/sedute/commissionebase/index/ajax")
    public String getOrganiCollegialiSeduteCommissioneBase(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        for (UtenteRuoloEnteDTO ure : authUser.getUtenteRuoloEnte()) {
            if (ure.getCodPermesso().equals(PermissionConstans.SEGRETERIA)) {
                filter.getListaEnti().add(ure.getIdEnte());
            }
        }
        Integer idSeduta = Integer.valueOf(request.getParameter("idSeduta[]"));
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        GridOrganiCollegialiSeduteCommissione json = new GridOrganiCollegialiSeduteCommissione();
        //Visualizza solo errori attivi
        filter.setTipoFiltro("T");
        filter.setIdSeduta(idSeduta);
        filter.setIdPraticaOrganiCollegiali(null);
        filter.setIdSedutaPratica(null);
        Long count = organiCollegialiService.countOrganiCollegialiSeduteCommissioneBase(filter);
        List<OrganiCollegialiSeduteComissioneDTO> dtos = organiCollegialiService.getOrganiCollegialiSeduteCommissioneBase(filter);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/cds/organicollegiali/sedute/commissione/delete")
    public @ResponseBody
    Object organiCollegialiSeduteCommissioneDelete(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        JSONArray j = new JSONArray(request.getParameter("idSedutaPratica"));
        Integer idSedutaPratica = Integer.valueOf((String) j.get(0));
        j = new JSONArray(request.getParameter("idAnagrafica"));
        Integer idAnagrafica = Integer.valueOf((String) j.get(0));

        cdsAction.eliminaOrganoCollegialeSedutaPratiche(idSedutaPratica, idAnagrafica);

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @RequestMapping("/cds/organicollegiali/commissione/pratiche/index/ajax")
    public String getOrganiCollegialiSeduteCommissionePratiche(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);

        Integer idSeduta = Integer.valueOf(request.getParameter("idSeduta[]"));
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        OrganiCollegialiSedute ocs = organiCollegialiDao.findByIdSeduta(idSeduta);
        GridOrganiCollegialiSeduteCommissionePratiche json = new GridOrganiCollegialiSeduteCommissionePratiche();
        //Visualizza solo errori attivi
        filter.setTipoFiltro("T");
        filter.setIdSeduta(idSeduta);
        filter.setIdOrganiCollegiali(ocs.getIdOrganoCollegiale().getIdOrganiCollegiali());
        Long count = organiCollegialiService.countOrganiCollegialiSeduteCommissionePratiche(filter);
        List<OrganiCollegialiSeduteComissioneDTO> dtos = organiCollegialiService.findOrganiCollegialiSeduteCommissionePratiche(filter);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/cds/organicollegiali/commissione/pratiche/delete")
    public @ResponseBody
    Object organiCollegialiCommissionePraticheSeduteDelete(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        JSONArray j = new JSONArray(request.getParameter("idSedutaPratica"));
        if (!Strings.isNullOrEmpty((String) j.get(0))) {
            Integer idSedutaPratica = Integer.valueOf((String) j.get(0));
            cdsAction.eliminaOrganoCollegialeSeduteCommissionePratiche(idSedutaPratica);
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @RequestMapping("/cds/organicollegiali/sedute/commissionepratica/index/ajax")
    public String getOrganiCollegialiSeduteCommissionePratica(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        for (UtenteRuoloEnteDTO ure : authUser.getUtenteRuoloEnte()) {
            if (ure.getCodPermesso().equals(PermissionConstans.SEGRETERIA)) {
                filter.getListaEnti().add(ure.getIdEnte());
            }
        }
        Integer idSeduta = Integer.valueOf(request.getParameter("idSeduta[]"));
        if (!Strings.isNullOrEmpty(request.getParameter("idSedutaPratica[]"))) {
            Integer idSedutaPratica = Integer.valueOf(request.getParameter("idSedutaPratica[]"));
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            GridOrganiCollegialiSeduteCommissione json = new GridOrganiCollegialiSeduteCommissione();
            //Visualizza solo errori attivi
            filter.setTipoFiltro("T");
            filter.setIdSeduta(idSeduta);
            filter.setIdPraticaOrganiCollegiali(null);
            filter.setIdSedutaPratica(idSedutaPratica);
            Long count = organiCollegialiService.countOrganiCollegialiSedutePratiche(filter);
            List<OrganiCollegialiSeduteComissioneDTO> dtos = organiCollegialiService.getOrganiCollegialiSedutePratiche(filter);
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords(count.intValue());
            json.setTotal(totalRecords);
            json.setRows(dtos);
            model.addAttribute("json", json.toString());
        }
        return AJAX;
    }

    @RequestMapping("/cds/organicollegiali/delete")
    public @ResponseBody
    Object organiCollegialiDelete(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        JSONArray j = new JSONArray(request.getParameter("idOrganiCollegiali"));
        if (!Strings.isNullOrEmpty((String) j.get(0))) {
            Integer idOrganiCollegiali = Integer.valueOf((String) j.get(0));
            Long count = organiCollegialiDao.countUseOrganoCollegiale(idOrganiCollegiali);
            if (count.equals(0L)) {
                cdsAction.eliminaOrganoCollegiale(idOrganiCollegiali);
            } else {
                result = Boolean.FALSE;
                message = "Organo collegiale in uso - non posso eliminarlo";
            }
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @RequestMapping("/cds/organicollegiali/sedute/stampadocumenti")
    public void downloadDocumenti(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Integer idSeduta = Integer.valueOf(request.getParameter("idSeduta"));
        String tipo = request.getParameter("type");
        OrganiCollegialiSedute ocs = organiCollegialiDao.findByIdSeduta(idSeduta);
        Seduta seduta = new Seduta();
        seduta.setIdSeduta(ocs.getIdSeduta());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DataConvocazione dc = new DataConvocazione();
        if (ocs.getDataConvocazione() != null) {
            dc.setData(sdf.format(ocs.getDataConvocazione()));
            dc.setOra(ocs.getOraConvocazione());
        }
        seduta.setDataConvocazione(dc);
        DataConclusione dcon = new DataConclusione();
        if (ocs.getDataConclusione() != null) {
            dcon.setData(sdf.format(ocs.getDataConclusione()));
            dcon.setOra(ocs.getOraConclusione());
        }
        seduta.setDataConclusione(dcon);
        Seduta.DataInizio din = new Seduta.DataInizio();
        if (ocs.getDataInizio() != null) {
            din.setData(sdf.format(ocs.getDataInizio()));
            din.setOra(ocs.getOraInizio());
        }
        seduta.setDataInizio(din);

        List<OcSedutePratiche> ocspList = ocs.getOcSedutePraticheList();
        List<OcPraticaCommissione> lista = null;
        List<OcSedutePratiche> listaPratiche = new ArrayList<OcSedutePratiche>();
        for (OcSedutePratiche o : ocspList) {
            lista = new ArrayList<OcPraticaCommissione>();
            if (o.getIdPraticaOrganiCollegiali() == null) {
                for (OcPraticaCommissione op : o.getOcPraticaCommissioneList()) {
                    if (lista.isEmpty()) {
                        lista.add(op);
                    } else {
                        int index = 0;
                        boolean insert = false;
                        for (OcPraticaCommissione ol : lista) {
                            if (ol.getIdRuoloCommissione().getPeso() >= op.getIdRuoloCommissione().getPeso()) {
                                lista.add(index, op);
                                insert = true;
                                break;
                            }
                            index++;
                        }
                        if (!insert) {
                            lista.add(op);
                        }

                    }
                }
                ComponentiCommissione cc = new ComponentiCommissione();
                for (OcPraticaCommissione op : lista) {
                    ComponenteCommissione c = new ComponenteCommissione();
                    c.setCognome(op.getAnagrafica().getCognome());
                    c.setNome(op.getAnagrafica().getNome());
                    c.setIdAnagrafica(op.getAnagrafica().getIdAnagrafica());
                    Ruolo r = new Ruolo();
                    r.setIdRuolo(op.getIdRuoloCommissione().getIdRuoloCommissione());
                    r.setDescrizione(op.getIdRuoloCommissione().getDescrizione());
                    r.setPeso(op.getIdRuoloCommissione().getPeso());
                    c.setRuolo(r);
                    cc.getComponenteCommissione().add(c);
                }
                seduta.setComponentiCommissione(cc);
            } else {
                if (listaPratiche.isEmpty()) {
                    listaPratiche.add(o);
                } else {
                    int index = 0;
                    boolean insert = false;
                    for (OcSedutePratiche ol : listaPratiche) {
                        if (ol.getSequenza() >= o.getSequenza()) {
                            listaPratiche.add(index, o);
                            insert = true;
                            break;
                        }
                        index++;
                    }
                    if (!insert) {
                        listaPratiche.add(o);
                    }
                }
            }
        }
        serializzaPratica(listaPratiche, seduta);

        Ente e = new Ente();

        e.setIdEnte(ocs.getIdOrganoCollegiale().getIdEnte().getIdEnte());
        e.setDescrizione(ocs.getIdOrganoCollegiale().getIdEnte().getDescrizione());
        seduta.setEnte(e);
        Seduta.Organo o = new Seduta.Organo();

        o.setIdOrgano(ocs.getIdOrganoCollegiale().getIdOrganiCollegiali());
        o.setDescrizione(ocs.getIdOrganoCollegiale().getDesOrganoCollegiale());
        seduta.setOrgano(o);
        String xml = Utils.marshall(seduta);

        ocs.getIdOrganoCollegiale().getOrganiCollegialiTemplateList();
        byte[] templateByteArray = null;
        String nomeFile = "";
        String tipoFile = "";
        for (OrganiCollegialiTemplate t : ocs.getIdOrganoCollegiale().getOrganiCollegialiTemplateList()) {
            if (t.getTipologiaTemplate().equals(CDSConstants.TEMPLATE_CONVOCAZIONE) && tipo.equals(CDSConstants.TEMPLATE_CONVOCAZIONE)) {
                templateByteArray = t.getIdTemplate().getTemplate().getBytes();
                nomeFile = t.getIdTemplate().getNomeFile();
                tipoFile = t.getIdTemplate().getMimeType();
            }
            if (t.getTipologiaTemplate().equals(CDSConstants.TEMPLATE_ODG) && tipo.equals(CDSConstants.TEMPLATE_ODG)) {
                templateByteArray = t.getIdTemplate().getTemplate().getBytes();
                nomeFile = t.getIdTemplate().getNomeFile();
                tipoFile = t.getIdTemplate().getMimeType();
            }
            if (t.getTipologiaTemplate().equals(CDSConstants.TEMPLATE_VERBALE) && tipo.equals(CDSConstants.TEMPLATE_VERBALE)) {
                templateByteArray = t.getIdTemplate().getTemplate().getBytes();
                nomeFile = t.getIdTemplate().getNomeFile();
                tipoFile = t.getIdTemplate().getMimeType();
            }
        }
        byte[] documento = docEngineService.createDocument(xml, templateByteArray, "DOC");

        response.setContentType(tipoFile);

        response.setContentLength(documento.length);

        response.addHeader("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
        OutputStream out = response.getOutputStream();

        out.write(documento);

        out.flush();

        out.close();
    }

    private Filter getFilter(JqgridPaginator paginator) throws NumberFormatException {
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = new Filter();
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        return filter;
    }

    private void serializzaPratica(List<OcSedutePratiche> listaPratiche, Seduta seduta) {
        List<OcPraticaCommissione> lista = new ArrayList<OcPraticaCommissione>();
        Seduta.Pratiche pratiche = new Seduta.Pratiche();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (OcSedutePratiche o : listaPratiche) {
            Pratica p = new Pratica();
            p.setIdPratica(o.getIdPraticaOrganiCollegiali().getIdPratica().getIdPratica());
            p.setOggetto(o.getIdPraticaOrganiCollegiali().getIdPratica().getOggettoPratica());
            p.setProtocollo(o.getIdPraticaOrganiCollegiali().getIdPratica().getProtocollo());
            p.setSequenza(o.getSequenza());
            p.setTesto(o.getNote());
            if (o.getIdPraticaOrganiCollegiali().getIdPratica().getDataProtocollazione() != null) {
                p.setDataProtocollo(sdf.format(o.getIdPraticaOrganiCollegiali().getIdPratica().getDataProtocollazione()));
            }
            Pratica.Localizzazioni ll = new Pratica.Localizzazioni();
            for (IndirizziIntervento i : o.getIdPraticaOrganiCollegiali().getIdPratica().getIndirizziInterventoList()) {
                Pratica.Localizzazioni.Localizzazione l = new Pratica.Localizzazioni.Localizzazione();
                l.setCap(i.getCap());
                l.setCivico(i.getCivico());
                l.setLocalita(i.getLocalita());
                l.setVia(i.getIndirizzo());
                l.setComune(o.getIdPraticaOrganiCollegiali().getIdPratica().getIdComune().getDescrizione());
                l.setColore(i.getColore());
                l.setInternoLettera(l.getInternoLettera());
                l.setInternoNumero(l.getInternoNumero());
                l.setInternoScala(l.getInternoScala());
                l.setLettera(l.getLettera());
                l.setPiano(l.getPiano());
                ll.getLocalizzazione().add(l);
            }
            p.setLocalizzazioni(ll);
            Pratica.Daticatastali ldd = new Pratica.Daticatastali();
            for (DatiCatastali dd : o.getIdPraticaOrganiCollegiali().getIdPratica().getDatiCatastaliList()) {
                Pratica.Daticatastali.Datocatastale d = new Pratica.Daticatastali.Datocatastale();
                d.setEstensioneparticella(dd.getEstensioneParticella());
                d.setFoglio(dd.getFoglio());
                d.setMappale(dd.getMappale());
                d.setSubalterno(dd.getSubalterno());
                if (dd.getIdTipoSistemaCatastale() != null) {
                    Pratica.Daticatastali.Datocatastale.Tipocatasto tc = new Pratica.Daticatastali.Datocatastale.Tipocatasto();
                    tc.setIdtipo(dd.getIdTipoSistemaCatastale().getIdTipoSistemaCatastale());
                    tc.setDescrizione(dd.getIdTipoSistemaCatastale().getDescrizione());
                    d.setTipocatasto(tc);
                }
                if (dd.getIdTipoParticella() != null) {
                    Pratica.Daticatastali.Datocatastale.Tipoparticella tp = new Pratica.Daticatastali.Datocatastale.Tipoparticella();
                    tp.setIdtipo(dd.getIdTipoParticella().getIdTipoParticella());
                    tp.setDescrizione(dd.getIdTipoParticella().getDescrizione());
                    d.setTipoparticella(tp);
                }
                if (dd.getIdTipoUnita() != null) {
                    Pratica.Daticatastali.Datocatastale.Tipounita tu = new Pratica.Daticatastali.Datocatastale.Tipounita();
                    tu.setIdtipo(dd.getIdTipoUnita().getIdTipoUnita());
                    tu.setDescrizione(dd.getIdTipoUnita().getDescrizione());
                    d.setTipounita(tu);
                }
                ldd.getDatocatastale().add(d);
            }
            p.setDaticatastali(ldd);
            for (OcPraticaCommissione op : o.getOcPraticaCommissioneList()) {
                if (lista.isEmpty()) {
                    lista.add(op);
                } else {
                    int index = 0;
                    boolean insert = false;
                    for (OcPraticaCommissione ol : lista) {
                        if (ol.getIdRuoloCommissione().getPeso() >= op.getIdRuoloCommissione().getPeso()) {
                            lista.add(index, op);
                            insert = true;
                            break;
                        }
                        index++;
                    }
                    if (!insert) {
                        lista.add(op);
                    }

                }
            }
            Pratica.ComponentiCommissione cc = new Pratica.ComponentiCommissione();
            for (OcPraticaCommissione op : lista) {
                Pratica.ComponentiCommissione.ComponenteCommissione c = new Pratica.ComponentiCommissione.ComponenteCommissione();
                c.setCognome(op.getAnagrafica().getCognome());
                c.setNome(op.getAnagrafica().getNome());
                c.setIdAnagrafica(op.getAnagrafica().getIdAnagrafica());
                Pratica.ComponentiCommissione.ComponenteCommissione.Ruolo r = new Pratica.ComponentiCommissione.ComponenteCommissione.Ruolo();
                r.setIdRuolo(op.getIdRuoloCommissione().getIdRuoloCommissione());
                r.setDescrizione(op.getIdRuoloCommissione().getDescrizione());
                r.setPeso(op.getIdRuoloCommissione().getPeso());
                c.setRuolo(r);
                cc.getComponenteCommissione().add(c);
            }
            p.setComponentiCommissione(cc);
            Pratica.Procedimento procedimento = new Pratica.Procedimento();
            procedimento.setIdProcedimento(o.getIdPraticaOrganiCollegiali().getIdPratica().getIdProcEnte().getIdProc().getIdProc());
            procedimento.setDescrizione(o.getIdPraticaOrganiCollegiali().getIdPratica().getIdProcEnte().getIdProc().getProcedimentiTestiByLang("it"));
            Pratica.Procedimento.Endoprocedimenti listaEndo = new Pratica.Procedimento.Endoprocedimenti();
            for (PraticaProcedimenti proc : o.getIdPraticaOrganiCollegiali().getIdPratica().getPraticaProcedimentiList()) {
                Pratica.Procedimento.Endoprocedimenti.Endoprocedimento endo = new Pratica.Procedimento.Endoprocedimenti.Endoprocedimento();
                endo.setDescrizione(proc.getProcedimenti().getProcedimentiTestiByLang("it"));
                endo.setIdEndoprocedimento(proc.getProcedimenti().getIdProc());
                Pratica.Procedimento.Endoprocedimenti.Endoprocedimento.Ente e = new Pratica.Procedimento.Endoprocedimenti.Endoprocedimento.Ente();
                e.setDescrizione(proc.getEnti().getDescrizione());
                e.setIdEnte(proc.getEnti().getIdEnte());
                endo.setEnte(e);
                listaEndo.getEndoprocedimento().add(endo);
            }
            procedimento.setEndoprocedimenti(listaEndo);
            Pratica.Richiedenti rich = new Pratica.Richiedenti();
            for (PraticaAnagrafica pa : o.getIdPraticaOrganiCollegiali().getIdPratica().getPraticaAnagraficaList()) {
                if (pa.getLkTipoRuolo().getCodRuolo().equals(Constants.RUOLO_COD_RICHIEDENTE)) {
                    Pratica.Richiedenti.Richiedente r = new Pratica.Richiedenti.Richiedente();
                    r.setIdAnagrafica(pa.getAnagrafica().getIdAnagrafica());
                    r.setCodiceFiscale(pa.getAnagrafica().getCodiceFiscale());
                    r.setPartitaIva(pa.getAnagrafica().getPartitaIva());
                    r.setTipoAnagrafica(pa.getAnagrafica().getTipoAnagrafica().toString());
                    r.setCognome(pa.getAnagrafica().getCognome());
                    r.setNome(pa.getAnagrafica().getNome());
                    r.setDenominazione(pa.getAnagrafica().getDenominazione());
                    rich.getRichiedente().add(r);
                }
            }
            p.setRichiedenti(rich);
            Pratica.Beneficiari benef = new Pratica.Beneficiari();
            for (PraticaAnagrafica pa : o.getIdPraticaOrganiCollegiali().getIdPratica().getPraticaAnagraficaList()) {
                if (pa.getLkTipoRuolo().getCodRuolo().equals(Constants.RUOLO_COD_BENEFICIARIO)) {
                    Pratica.Beneficiari.Beneficiario r = new Pratica.Beneficiari.Beneficiario();
                    r.setIdAnagrafica(pa.getAnagrafica().getIdAnagrafica());
                    r.setCodiceFiscale(pa.getAnagrafica().getCodiceFiscale());
                    r.setPartitaIva(pa.getAnagrafica().getPartitaIva());
                    r.setTipoAnagrafica(pa.getAnagrafica().getTipoAnagrafica().toString());
                    r.setCognome(pa.getAnagrafica().getCognome());
                    r.setNome(pa.getAnagrafica().getNome());
                    r.setDenominazione(pa.getAnagrafica().getDenominazione());
                    benef.getBeneficiario().add(r);
                }
            }
            p.setBeneficiari(benef);
            p.setProcedimento(procedimento);
            pratiche.getPratica().add(p);
        }
        seduta.setPratiche(pratiche);
    }
}
