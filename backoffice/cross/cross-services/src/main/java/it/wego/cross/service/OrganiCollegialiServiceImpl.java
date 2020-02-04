/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.wego.cross.actions.CdsAction;
import it.wego.cross.constants.CDSConstants;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.PermissionConstans;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.OrganiCollegialiDao;
import it.wego.cross.dao.PraticaOrganiCollegialiDao;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.UtenteRuoloEnteDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiCommissioneDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiSeduteDTO;
import it.wego.cross.dto.dozer.PraticaOrganoCollegialeDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiSeduteComissioneDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkRuoliCommissione;
import it.wego.cross.entity.LkStatiPraticaOrganiCollegiali;
import it.wego.cross.entity.LkStatiSedute;
import it.wego.cross.entity.OcPraticaCommissione;
import it.wego.cross.entity.OcSedutePratiche;
import it.wego.cross.entity.OrganiCollegiali;
import it.wego.cross.entity.OrganiCollegialiCommissione;
import it.wego.cross.entity.OrganiCollegialiSedute;
import it.wego.cross.entity.OrganiCollegialiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaOrganiCollegiali;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.Templates;
import it.wego.utils.json.JsonBuilder;
import it.wego.utils.json.JsonMapTransformer;
import static it.wego.utils.json.JsonMapTransformer.LABEL;
import static it.wego.utils.json.JsonMapTransformer.VALUE;
import it.wego.utils.json.JsonResponse;
import it.wego.utils.wegoforms.FormEngine;
import it.wego.utils.wegoforms.base.FileUploadHandler;
import it.wego.utils.wegoforms.base.FileUploadHandler.FileData;
import it.wego.utils.wegoforms.parsing.SimpleWgfValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.dozer.Mapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author giuseppe
 */
@Service
public class OrganiCollegialiServiceImpl implements OrganiCollegialiService {

    @Autowired
    private OrganiCollegialiDao organiCollegialiDao;
    @Autowired
    private Mapper mapper;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    public UtentiService utentiService;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private CdsAction cdsAction;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private PraticaOrganiCollegialiDao praticaOrganiCollegialiDao;
    @Autowired
    private AllegatiDao allegatiDao;

    @Override
    public List<OrganiCollegialiDTO> findAllOragniCollegialiOrdered(Integer idEnte) throws Exception {
        List<OrganiCollegiali> organiCollegiali = organiCollegialiDao.findAllOrdered(idEnte);
        List<OrganiCollegialiDTO> organiCollegialiResult = new ArrayList<OrganiCollegialiDTO>();
        if (organiCollegiali != null && !organiCollegiali.isEmpty()) {
            for (OrganiCollegiali o : organiCollegiali) {
                OrganiCollegialiDTO ocDTO = mapper.map(o, OrganiCollegialiDTO.class);
                ocDTO.setIdEnte(o.getIdEnte().getIdEnte());
                ocDTO.setDesEnte(o.getIdEnte().getDescrizione());
                organiCollegialiResult.add(ocDTO);
            }

        }
        return organiCollegialiResult;
    }

    @Override
    public void salvaAvvioConferenzaServizi(PraticaOrganoCollegialeDTO pocDTO) throws Exception {

    }

    @Override
    public Long countOrganiCollegiali(Filter filter) {
        Long count = organiCollegialiDao.countOrganiCollegiali(filter);
        return count;
    }

    @Override
    public List<OrganiCollegialiDTO> getOrganiCollegiali(Filter filter) {
        List<OrganiCollegiali> organiCollegiali = organiCollegialiDao.findAll(filter);
        List<OrganiCollegialiDTO> dtos = new ArrayList<OrganiCollegialiDTO>();
        if (organiCollegiali != null && !organiCollegiali.isEmpty()) {
            for (OrganiCollegiali organoCollegiale : organiCollegiali) {
                OrganiCollegialiDTO dto = mapper.map(organoCollegiale, OrganiCollegialiDTO.class);
                dto.setIdEnte(organoCollegiale.getIdEnte().getIdEnte());
                dto.setDesEnte(organoCollegiale.getIdEnte().getDescrizione());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public void getOrganiCollegiali(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {

    }

    @Override
    public JsonResponse insertOrganoCollegiale(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        SimpleWgfValue desOrganoCollegiale = (SimpleWgfValue) formParameters.get("desOrganoCollegiale");
        if (desOrganoCollegiale == null || Strings.isNullOrEmpty((String) desOrganoCollegiale.getValue())) {
            result = Boolean.FALSE;
            message = "Manca la descrizione Organo Collegiale";
        } else {
            mappa.put("descrizione", (String) desOrganoCollegiale.getValue());
        }
        SimpleWgfValue ente = (SimpleWgfValue) formParameters.get("enti");
        if (ente == null || Strings.isNullOrEmpty((String) ente.getValue())) {
            result = Boolean.FALSE;
            message = "Manca la scelta ente";
        } else {
            mappa.put("ente", (String) ente.getValue());
        }
        SimpleWgfValue odg = ((SimpleWgfValue) formParameters.get("odg"));
        boolean errore = true;
        if (odg != null) {
            FileData fileData = (FileData) odg.value();
            if (fileData != null && !Strings.isNullOrEmpty(fileData.getFileName())) {
                String nomeFile = fileData.getFileName();
                byte[] binario = fileData.getData();
                Templates t = new Templates();
                t.setDescrizione("Ordine del giorno");
                t.setNomeFile("odg.doc");
                t.setNomeFileOriginale(nomeFile);
                t.setMimeType("application/msword");
                t.setFileOutput("DOC");
                Base64 base64 = new Base64();
                t.setTemplate(base64.encodeAsString(binario));
                mappa.put(CDSConstants.TEMPLATE_ODG, t);
                errore = false;
            }
        }
        if (errore) {
            result = Boolean.FALSE;
            message = "Manca il documento per OdG";
        }
        errore = true;
        SimpleWgfValue convocazione = (SimpleWgfValue) formParameters.get("convocazione");
        if (convocazione != null) {
            FileData fileData = (FileData) convocazione.value();
            if (fileData != null && !Strings.isNullOrEmpty(fileData.getFileName())) {
                String nomeFile = fileData.getFileName();
                byte[] binario = fileData.getData();
                Templates t = new Templates();
                t.setDescrizione("Convocazione conferanza servizi");
                t.setNomeFile("convocazione.doc");
                t.setNomeFileOriginale(nomeFile);
                t.setMimeType("application/msword");
                t.setFileOutput("DOC");
                Base64 base64 = new Base64();
                t.setTemplate(base64.encodeAsString(binario));
                mappa.put(CDSConstants.TEMPLATE_CONVOCAZIONE, t);
                errore = false;
            }
        }
        if (errore) {
            result = Boolean.FALSE;
            message = "Manca il documento per Convocazione";
        }
        errore = true;
        SimpleWgfValue verbale = (SimpleWgfValue) formParameters.get("verbale");
        if (verbale != null) {
            FileData fileData = (FileData) verbale.value();
            if (fileData != null && !Strings.isNullOrEmpty(fileData.getFileName())) {
                String nomeFile = fileData.getFileName();
                byte[] binario = fileData.getData();
                Templates t = new Templates();
                t.setDescrizione("Verbale conferenza servizi");
                t.setNomeFile("verbale.doc");
                t.setNomeFileOriginale(nomeFile);
                t.setMimeType("application/msword");
                t.setFileOutput("DOC");
                Base64 base64 = new Base64();
                t.setTemplate(base64.encodeAsString(binario));
                mappa.put(CDSConstants.TEMPLATE_VERBALE, t);
                errore = false;
            }
        }
        if (errore) {
            result = Boolean.FALSE;
            message = "Manca il documento per verbale";
        }
        if (result) {
            cdsAction.insertOrganoCollegiale(mappa);
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public JsonResponse salvaOrganoCollegiale(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        SimpleWgfValue idOrganoCollegiale = (SimpleWgfValue) formParameters.get("idOrganiCollegiali");
        mappa.put("idOrganoCollegiale", (String) idOrganoCollegiale.getValue());

        SimpleWgfValue desOrganoCollegiale = (SimpleWgfValue) formParameters.get("desOrganoCollegiale");
        mappa.put("descrizione", (String) desOrganoCollegiale.getValue());

        SimpleWgfValue odg = ((SimpleWgfValue) formParameters.get("odg"));
        if (odg != null) {
            FileData fileData = (FileData) odg.value();
            if (fileData != null && !Strings.isNullOrEmpty(fileData.getFileName())) {
                String nomeFile = fileData.getFileName();
                byte[] binario = fileData.getData();
                Templates t = new Templates();
                t.setDescrizione("Ordine del giorno");
                t.setNomeFile("odg.doc");
                t.setNomeFileOriginale(nomeFile);
                t.setMimeType("application/msword");
                t.setFileOutput("DOC");
                Base64 base64 = new Base64();
                t.setTemplate(base64.encodeAsString(binario));
                mappa.put(CDSConstants.TEMPLATE_ODG, t);

            }
        }
        SimpleWgfValue convocazione = (SimpleWgfValue) formParameters.get("convocazione");
        if (convocazione != null) {
            FileData fileData = (FileData) convocazione.value();
            if (fileData != null && !Strings.isNullOrEmpty(fileData.getFileName())) {
                String nomeFile = fileData.getFileName();
                byte[] binario = fileData.getData();
                Templates t = new Templates();
                t.setDescrizione("Convocazione conferanza servizi");
                t.setNomeFile("convocazione.doc");
                t.setNomeFileOriginale(nomeFile);
                t.setMimeType("application/msword");
                t.setFileOutput("DOC");
                Base64 base64 = new Base64();
                t.setTemplate(base64.encodeAsString(binario));
                mappa.put(CDSConstants.TEMPLATE_CONVOCAZIONE, t);
            }
        }
        SimpleWgfValue verbale = (SimpleWgfValue) formParameters.get("verbale");
        if (verbale != null) {
            FileData fileData = (FileData) verbale.value();
            if (fileData != null && !Strings.isNullOrEmpty(fileData.getFileName())) {
                String nomeFile = fileData.getFileName();
                byte[] binario = fileData.getData();
                Templates t = new Templates();
                t.setDescrizione("Verbale conferenza servizi");
                t.setNomeFile("verbale.doc");
                t.setNomeFileOriginale(nomeFile);
                t.setMimeType("application/msword");
                t.setFileOutput("DOC");
                Base64 base64 = new Base64();
                t.setTemplate(base64.encodeAsString(binario));
                mappa.put(CDSConstants.TEMPLATE_VERBALE, t);
            }
        }
        if (result) {
            cdsAction.salvaOrganoCollegiale(mappa);
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public void initModificaOrganiCollegiali(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        final Filter filter = new Filter();
        for (UtenteRuoloEnteDTO ure : authUser.getUtenteRuoloEnte()) {
            if (ure.getCodPermesso().equals(PermissionConstans.SEGRETERIA)) {
                filter.getListaEnti().add(ure.getIdEnte());
            }
        }
        OrganiCollegialiDTO dto = new OrganiCollegialiDTO();
        if (request.getParameter("idOrganiCollegiali") != null) {
            JSONArray j = new JSONArray(request.getParameter("idOrganiCollegiali"));
            Integer idOrganiCollegiali = Integer.valueOf((String) j.get(0));
            OrganiCollegiali organiCollegiali = organiCollegialiDao.findById(idOrganiCollegiali);

            if (organiCollegiali != null) {
                dto = mapper.map(organiCollegiali, OrganiCollegialiDTO.class);
                dto.setIdEnte(organiCollegiali.getIdEnte().getIdEnte());
                dto.setDesEnte(organiCollegiali.getIdEnte().getDescrizione());

                for (OrganiCollegialiTemplate template : organiCollegiali.getOrganiCollegialiTemplateList()) {
                    formEngine.putInstanceDataValue(template.getTipologiaTemplate().toLowerCase(), new FileUploadHandler.FileDataImpl(template.getIdTemplate().getNomeFileOriginale(), Base64.decodeBase64(template.getIdTemplate().getTemplate())));
                }
            }

        }
        formEngine.putInstanceDataValue("idOrganiCollegiali", dto.getIdOrganiCollegiali());
        formEngine.putInstanceDataValue("desOrganoCollegiale", dto.getDesOrganoCollegiale());
        formEngine.putInstanceDataValue("idEnte", dto.getIdEnte());
        formEngine.putInstanceDataValue("desEnte", dto.getDesEnte());
        formEngine.putInstanceDataValue("enti_data", new Supplier() {
            @Override
            public Object get() {
                List<Enti> listaEnti = entiService.findAll(filter);
                return (List) Lists.newArrayList(Lists.transform(listaEnti, new JsonMapTransformer<Enti>() {
                    @Override
                    public void transformToMap(Enti ente) {
                        put(LABEL, ente.getDescrizione());
                        put(VALUE, ente.getIdEnte().toString());

                    }
                }));
            }
        }.get());
    }

    @Override
    public Long countOrganiCollegialiSedute(Filter filter) {
        Long count = organiCollegialiDao.countOrganiCollegialiSedute(filter);
        return count;
    }

    @Override
    public List<OrganiCollegialiSeduteDTO> getOrganiCollegialiSedute(Filter filter) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<OrganiCollegialiSedute> organiCollegialiSedute = organiCollegialiDao.findAllSeduteFiltered(filter);
        List<OrganiCollegialiSeduteDTO> dtos = new ArrayList<OrganiCollegialiSeduteDTO>();
        if (organiCollegialiSedute != null && !organiCollegialiSedute.isEmpty()) {
            for (OrganiCollegialiSedute organoCollegialeSeduta : organiCollegialiSedute) {
                OrganiCollegialiSeduteDTO dto = mapper.map(organoCollegialeSeduta, OrganiCollegialiSeduteDTO.class);
                dto.setIdOrganniCollegiali(organoCollegialeSeduta.getIdOrganoCollegiale().getIdOrganiCollegiali());
                dto.setDesOrganoCollegiale(organoCollegialeSeduta.getIdOrganoCollegiale().getDesOrganoCollegiale());
                dto.setIdEnte(organoCollegialeSeduta.getIdOrganoCollegiale().getIdEnte().getIdEnte());
                dto.setDesEnte(organoCollegialeSeduta.getIdOrganoCollegiale().getIdEnte().getDescrizione());
                if (organoCollegialeSeduta.getDataConclusione() != null) {
                    dto.setDataConclusioneGrid(sdf.format(organoCollegialeSeduta.getDataConclusione()) + " " + (organoCollegialeSeduta.getOraConclusione() == null ? "" : organoCollegialeSeduta.getOraConclusione()));
                }
                if (organoCollegialeSeduta.getDataInizio() != null) {
                    dto.setDataInizioGrid(sdf.format(organoCollegialeSeduta.getDataInizio()) + " " + (organoCollegialeSeduta.getOraInizio() == null ? "" : organoCollegialeSeduta.getOraInizio()));
                }
                if (organoCollegialeSeduta.getDataConvocazione() != null) {
                    dto.setDataConvocazioneGrid(sdf.format(organoCollegialeSeduta.getDataConvocazione()) + " " + (organoCollegialeSeduta.getOraConvocazione() == null ? "" : organoCollegialeSeduta.getOraConvocazione()));
                }
                if (organoCollegialeSeduta.getIdStatoSeduta() != null) {
                    dto.setIdStatoSeduta(organoCollegialeSeduta.getIdStatoSeduta().getIdStatoSeduta());
                    dto.setDesStatoSeduta(organoCollegialeSeduta.getIdStatoSeduta().getDescrizione());
                }
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public JsonResponse insertOrganoCollegialeSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        SimpleWgfValue idOrganoCollegiale = (SimpleWgfValue) formParameters.get("organiCollegiali");
        mappa.put("idOrganoCollegiale", (String) idOrganoCollegiale.getValue());
        SimpleWgfValue dataPrevista = (SimpleWgfValue) formParameters.get("dataPrevista");
        mappa.put("dataPrevista", dataPrevista.getValue());
        if (result) {
            cdsAction.inserisciOrganoCollegialeSeduta(mappa);
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public void initInsertOrganiCollegialiSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        final Filter filter = new Filter();
        for (UtenteRuoloEnteDTO ure : authUser.getUtenteRuoloEnte()) {
            if (ure.getCodPermesso().equals(PermissionConstans.SEGRETERIA)) {
                filter.getListaEnti().add(ure.getIdEnte());
            }
        }
        filter.setOrderDirection("asc");
        filter.setOrderColumn("desOrganoCollegiale");
        formEngine.putInstanceDataValue("organiCollegiali_data", new Supplier() {
            @Override
            public Object get() {
                List<OrganiCollegiali> lista = organiCollegialiDao.findAll(filter);
                return (List) Lists.newArrayList(Lists.transform(lista, new JsonMapTransformer<OrganiCollegiali>() {
                    @Override
                    public void transformToMap(OrganiCollegiali oc) {
                        put(LABEL, oc.getDesOrganoCollegiale() + "(" + oc.getIdEnte().getDescrizione() + ")");
                        put(VALUE, oc.getIdOrganiCollegiali().toString());
                    }
                }));
            }
        }.get());
    }

    @Override
    public void initModificaOrganiCollegialiSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        JSONArray j = new JSONArray(request.getParameter("idSeduta"));
        Integer idSeduta = Integer.valueOf((String) j.get(0));
        OrganiCollegialiSedute ocs = organiCollegialiDao.findByIdSeduta(idSeduta);

        formEngine.putInstanceDataValue("idSeduta", ocs.getIdSeduta());
        formEngine.putInstanceDataValue("idOrganiCollegiali", ocs.getIdOrganoCollegiale().getIdOrganiCollegiali());
        formEngine.putInstanceDataValue("desOrganoCollegiale", ocs.getIdOrganoCollegiale().getDesOrganoCollegiale() + "(" + ocs.getIdOrganoCollegiale().getIdEnte().getDescrizione() + ")");
        formEngine.putInstanceDataValue("dataPrevista", ocs.getDataPrevista());
        formEngine.putInstanceDataValue("dataConvocazione", ocs.getDataConvocazione());
        formEngine.putInstanceDataValue("dataInizio", ocs.getDataInizio());
        formEngine.putInstanceDataValue("dataConclusione", ocs.getDataConclusione());
        formEngine.putInstanceDataValue("oraConvocazione", ocs.getOraConvocazione());
        formEngine.putInstanceDataValue("oraInizio", ocs.getOraInizio());
        formEngine.putInstanceDataValue("oraConclusione", ocs.getOraConclusione());
        formEngine.putInstanceDataValue("statoSeduta", ocs.getIdStatoSeduta().getIdStatoSeduta().toString());
        formEngine.putInstanceDataValue("statoSeduta_data", new Supplier() {
            @Override
            public Object get() {
                List<LkStatiSedute> lista = lookupDao.findAllStatiSedute();
                return (List) Lists.newArrayList(Lists.transform(lista, new JsonMapTransformer<LkStatiSedute>() {
                    @Override
                    public void transformToMap(LkStatiSedute oc) {
                        put(LABEL, oc.getDescrizione());
                        put(VALUE, oc.getIdStatoSeduta().toString());
                    }
                }));
            }
        }.get());

    }

    @Override
    public JsonResponse salvaOrganoCollegialeSedute(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";

        SimpleWgfValue idSeduta = (SimpleWgfValue) formParameters.get("idSeduta");
        mappa.put("idSeduta", (String) idSeduta.getValue());

        SimpleWgfValue dataPrevista = (SimpleWgfValue) formParameters.get("dataPrevista");
        mappa.put("dataPrevista", dataPrevista.getValue());

        SimpleWgfValue dataConvocazione = (SimpleWgfValue) formParameters.get("dataConvocazione");
        mappa.put("dataConvocazione", dataConvocazione.getValue());
        SimpleWgfValue oraConvocazione = (SimpleWgfValue) formParameters.get("oraConvocazione");
        mappa.put("oraConvocazione", oraConvocazione.getValue());
        SimpleWgfValue dataInizio = (SimpleWgfValue) formParameters.get("dataInizio");
        mappa.put("dataInizio", dataInizio.getValue());
        SimpleWgfValue oraInizio = (SimpleWgfValue) formParameters.get("oraInizio");
        mappa.put("oraInizio", oraInizio.getValue());
        SimpleWgfValue dataConclusione = (SimpleWgfValue) formParameters.get("dataConclusione");
        mappa.put("dataConclusione", dataConclusione.getValue());
        SimpleWgfValue oraConclusione = (SimpleWgfValue) formParameters.get("oraConclusione");
        mappa.put("oraConclusione", oraConclusione.getValue());
        SimpleWgfValue idStatoSeduta = (SimpleWgfValue) formParameters.get("statoSeduta");
        mappa.put("idStatoSeduta", idStatoSeduta.getValue());

        if (result) {
            cdsAction.modificaOrganoCollegialeSeduta(mappa);
        }

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public Long countOrganiCollegialiCommissione(Filter filter) {
        Long count = organiCollegialiDao.countOrganiCollegialiCommissione(filter);
        return count;
    }

    @Override
    public List<OrganiCollegialiCommissioneDTO> getOrganiCollegialiCommissione(Filter filter) {
        List<OrganiCollegialiCommissione> organiCollegialiCommissione = organiCollegialiDao.findAllOrganiCollegialiFiltered(filter);
        List<OrganiCollegialiCommissioneDTO> dtos = new ArrayList<OrganiCollegialiCommissioneDTO>();
        if (organiCollegialiCommissione != null && !organiCollegialiCommissione.isEmpty()) {
            for (OrganiCollegialiCommissione organoCollegialiCommissione : organiCollegialiCommissione) {
                OrganiCollegialiCommissioneDTO dto = new OrganiCollegialiCommissioneDTO();
                dto.setCognome(organoCollegialiCommissione.getAnagrafica().getCognome());
                dto.setDesEnte(organoCollegialiCommissione.getOrganiCollegiali().getIdEnte().getDescrizione());
                dto.setDesOrganoCollegiale(organoCollegialiCommissione.getOrganiCollegiali().getDesOrganoCollegiale());
                dto.setDesRuoloCommissione(organoCollegialiCommissione.getIdRuoloCommissione().getDescrizione());
                dto.setIdAnagrafica(organoCollegialiCommissione.getAnagrafica().getIdAnagrafica());
                dto.setIdEnte(organoCollegialiCommissione.getOrganiCollegiali().getIdEnte().getIdEnte());
                dto.setIdOrganiCollegiali(organoCollegialiCommissione.getOrganiCollegiali().getIdOrganiCollegiali());
                dto.setIdRuoloComissione(organoCollegialiCommissione.getIdRuoloCommissione().getIdRuoloCommissione());
                dto.setNome(organoCollegialiCommissione.getAnagrafica().getNome());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public void initInsertOrganiCollegialiCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        Integer idOrganiCollegiali = Integer.valueOf(request.getParameter("idOrganiCollegiali[]"));
        OrganiCollegiali oc = organiCollegialiDao.findById(idOrganiCollegiali);
        formEngine.putInstanceDataValue("idOC", idOrganiCollegiali.toString());
        formEngine.putInstanceDataValue("ruoli_data", new Supplier() {
            @Override
            public Object get() {
                List<LkRuoliCommissione> lista = lookupDao.findAllRuoloCommissione();
                return (List) Lists.newArrayList(Lists.transform(lista, new JsonMapTransformer<LkRuoliCommissione>() {
                    @Override
                    public void transformToMap(LkRuoliCommissione ruolo) {
                        put(LABEL, ruolo.getDescrizione());
                        put(VALUE, ruolo.getIdRuoloCommissione().toString());

                    }
                }));
            }
        }.get());
        formEngine.putInstanceDataValue("idAnagrafica_data", new Supplier() {
            @Override
            public Object get() {
                return Lists.newArrayList(Iterables.transform(anagraficheDao.findAllFisiche(), new JsonMapTransformer<Anagrafica>() {
                    @Override
                    public void transformToMap(Anagrafica anagrafica) {
                        put(VALUE, anagrafica.getIdAnagrafica().toString());
                        put(LABEL, anagrafica.getCognome() + " " + anagrafica.getNome());
                    }
                }));
            }
        }.get());

    }

    @Override
    public void initModificaOrganiCollegialiCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        JSONArray j = new JSONArray(request.getParameter("idOrganiCollegiali"));
        Integer idOrganiCollegiali = Integer.valueOf((String) j.get(0));
        j = new JSONArray(request.getParameter("idAnagrafica"));
        Integer idAnagrafica = Integer.valueOf((String) j.get(0));
        OrganiCollegialiCommissione ocs = organiCollegialiDao.findByIdOrganoIdAnagrafica(idOrganiCollegiali, idAnagrafica);

        formEngine.putInstanceDataValue("ruoli_data", new Supplier() {
            @Override
            public Object get() {
                List<LkRuoliCommissione> lista = lookupDao.findAllRuoloCommissione();
                return (List) Lists.newArrayList(Lists.transform(lista, new JsonMapTransformer<LkRuoliCommissione>() {
                    @Override
                    public void transformToMap(LkRuoliCommissione ruolo) {
                        put(LABEL, ruolo.getDescrizione());
                        put(VALUE, ruolo.getIdRuoloCommissione().toString());

                    }
                }));
            }
        }.get());
        formEngine.putInstanceDataValue("idAnagrafica", idAnagrafica);
        formEngine.putInstanceDataValue("idOrganiCollegiali", idOrganiCollegiali);
        formEngine.putInstanceDataValue("ruoli", ocs.getIdRuoloCommissione().getIdRuoloCommissione());
        formEngine.putInstanceDataValue("cognome", ocs.getAnagrafica().getCognome());
        formEngine.putInstanceDataValue("nome", ocs.getAnagrafica().getNome());

    }

    @Override
    public JsonResponse insertOrganoCollegialeCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        SimpleWgfValue idOrganoCollegiale = (SimpleWgfValue) formParameters.get("idOC");
        mappa.put("idOrganiCollegiali", (String) idOrganoCollegiale.getValue());
        SimpleWgfValue idAnagrafica = (SimpleWgfValue) formParameters.get("idAnagrafica");
        mappa.put("idAnagrafica", (String) idAnagrafica.getValue());
        SimpleWgfValue ruoli = (SimpleWgfValue) formParameters.get("ruoli");
        mappa.put("idRuoloCommissione", (String) ruoli.getValue());
        OrganiCollegialiCommissione o = organiCollegialiDao.findByIdOrganoIdAnagrafica(Integer.valueOf((String) idOrganoCollegiale.getValue()), Integer.valueOf((String) idAnagrafica.getValue()));
        if (o != null) {
            result = Boolean.FALSE;
            message = "Anagrafica già utilizzata";
        }

        if (result) {
            cdsAction.inserisciOrganoCollegialeCommissione(mappa);
        }
        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public JsonResponse salvaOrganoCollegialeCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";

        SimpleWgfValue idOrganiCollegiali = (SimpleWgfValue) formParameters.get("idOrganiCollegiali");
        mappa.put("idOrganiCollegiali", (String) idOrganiCollegiali.getValue());

        SimpleWgfValue idAnagrafica = (SimpleWgfValue) formParameters.get("idAnagrafica");
        mappa.put("idAnagrafica", (String) idAnagrafica.getValue());

        SimpleWgfValue ruoli = (SimpleWgfValue) formParameters.get("ruoli");
        mappa.put("idRuoloCommissione", (String) ruoli.getValue());

        if (result) {
            cdsAction.modificaOrganoCollegialeCommissione(mappa);
        }

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public Long countOrganiCollegialiSedutePratiche(Filter filter) {
        Long count = organiCollegialiDao.countOrganiCollegialiSedutePratiche(filter);
        return count;
    }

    @Override
    public List<OrganiCollegialiSeduteComissioneDTO> getOrganiCollegialiSedutePratiche(Filter filter) {
        List<OcPraticaCommissione> organiCollegialiSedute = organiCollegialiDao.findAllSedutePraticheFiltered(filter);
        List<OrganiCollegialiSeduteComissioneDTO> dtos = new ArrayList<OrganiCollegialiSeduteComissioneDTO>();
        int index = 0;
        if (organiCollegialiSedute != null && !organiCollegialiSedute.isEmpty()) {
            for (OcPraticaCommissione organoCollegialeSeduta : organiCollegialiSedute) {
                OrganiCollegialiSeduteComissioneDTO dto = mapper.map(organiCollegialiSedute, OrganiCollegialiSeduteComissioneDTO.class);
                Anagrafica a = organoCollegialeSeduta.getAnagrafica();
                LkRuoliCommissione lk = organoCollegialeSeduta.getIdRuoloCommissione();
                dto.setCognome(a.getCognome());
                dto.setDesRuoloCommissione(lk.getDescrizione());
                dto.setIdAnagrafica(a.getIdAnagrafica());
                dto.setIdOrganiCollegiali(organoCollegialeSeduta.getOcSedutePratiche().getIdSeduta().getIdOrganoCollegiale().getIdOrganiCollegiali());
                if (organoCollegialeSeduta.getOcSedutePratiche().getIdPraticaOrganiCollegiali() != null) {
                    dto.setIdPraticaOrganiCollegiali(organoCollegialeSeduta.getOcSedutePratiche().getIdPraticaOrganiCollegiali().getIdPraticaOrganiCollegiali());
                }
                dto.setIdRuoloCommissione(lk.getIdRuoloCommissione());
                dto.setIdSeduta(organoCollegialeSeduta.getOcSedutePratiche().getIdSeduta().getIdSeduta());
                dto.setIdSedutaPratica(organoCollegialeSeduta.getOcSedutePratiche().getIdSedutaPratica());
                dto.setNome(a.getNome());
                dto.setIdKey(index);
                dtos.add(dto);
                index++;
            }
        }
        return dtos;
    }

    @Override
    public Long countOrganiCollegialiSeduteCommissioneBase(Filter filter) {
        Long count = organiCollegialiDao.countOrganiCollegialiSeduteCommissioneBase(filter);
        return count;
    }

    @Override
    public List<OrganiCollegialiSeduteComissioneDTO> getOrganiCollegialiSeduteCommissioneBase(Filter filter) {
        List<OcPraticaCommissione> organiCollegialiSedute = organiCollegialiDao.findAllSedutePraticheCommissioneBaseFiltered(filter);
        List<OrganiCollegialiSeduteComissioneDTO> dtos = new ArrayList<OrganiCollegialiSeduteComissioneDTO>();
        if (organiCollegialiSedute != null && !organiCollegialiSedute.isEmpty()) {
            for (OcPraticaCommissione organoCollegialeSeduta : organiCollegialiSedute) {
                OrganiCollegialiSeduteComissioneDTO dto = mapper.map(organiCollegialiSedute, OrganiCollegialiSeduteComissioneDTO.class);
                Anagrafica a = organoCollegialeSeduta.getAnagrafica();
                LkRuoliCommissione lk = organoCollegialeSeduta.getIdRuoloCommissione();
                dto.setCognome(a.getCognome());
                dto.setDesRuoloCommissione(lk.getDescrizione());
                dto.setIdAnagrafica(a.getIdAnagrafica());
                dto.setIdOrganiCollegiali(organoCollegialeSeduta.getOcSedutePratiche().getIdSeduta().getIdOrganoCollegiale().getIdOrganiCollegiali());
                if (organoCollegialeSeduta.getOcSedutePratiche().getIdPraticaOrganiCollegiali() != null) {
                    dto.setIdPraticaOrganiCollegiali(organoCollegialeSeduta.getOcSedutePratiche().getIdPraticaOrganiCollegiali().getIdPraticaOrganiCollegiali());
                }
                dto.setIdRuoloCommissione(lk.getIdRuoloCommissione());
                dto.setIdSeduta(organoCollegialeSeduta.getOcSedutePratiche().getIdSeduta().getIdSeduta());
                dto.setIdSedutaPratica(organoCollegialeSeduta.getOcSedutePratiche().getIdSedutaPratica());
                dto.setNome(a.getNome());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public void initInsertOrganiCollegialiSedutePraticheCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        Integer idSeduta = Integer.valueOf(request.getParameter("idSeduta[]"));
        Integer idSedutaPratica = null;
        if (!Strings.isNullOrEmpty(request.getParameter("idSedutaPratica[]"))) {
            idSedutaPratica = Integer.valueOf(request.getParameter("idSedutaPratica[]"));
        }
        Integer idPraticaOrganiCollegiali = null;
        if (!Strings.isNullOrEmpty(request.getParameter("idPraticaOrganiCollegiali[]"))) {
            idPraticaOrganiCollegiali = Integer.valueOf(request.getParameter("idPraticaOrganiCollegiali[]"));
        }
        formEngine.putInstanceDataValue("idSeduta", idSeduta.toString());
        formEngine.putInstanceDataValue("idSedutaPratica", idSedutaPratica);
        formEngine.putInstanceDataValue("idPraticaOrganiCollegiali", idPraticaOrganiCollegiali);
        formEngine.putInstanceDataValue("commissioneBase", request.getParameter("commissioneBase"));
        formEngine.putInstanceDataValue("ruoli_data", new Supplier() {
            @Override
            public Object get() {
                List<LkRuoliCommissione> lista = lookupDao.findAllRuoloCommissione();
                return (List) Lists.newArrayList(Lists.transform(lista, new JsonMapTransformer<LkRuoliCommissione>() {
                    @Override
                    public void transformToMap(LkRuoliCommissione ruolo) {
                        put(LABEL, ruolo.getDescrizione());
                        put(VALUE, ruolo.getIdRuoloCommissione().toString());

                    }
                }));
            }
        }.get());
        formEngine.putInstanceDataValue("idAnagrafica_data", new Supplier() {
            @Override
            public Object get() {
                return Lists.newArrayList(Iterables.transform(anagraficheDao.findAllFisiche(), new JsonMapTransformer<Anagrafica>() {
                    @Override
                    public void transformToMap(Anagrafica anagrafica) {
                        put(VALUE, anagrafica.getIdAnagrafica().toString());
                        put(LABEL, anagrafica.getCognome() + " " + anagrafica.getNome());
                    }
                }));
            }
        }.get());
    }

    @Override
    public JsonResponse insertOrganoCollegialeSedutePraticheCommissione(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        SimpleWgfValue commissioneBase = (SimpleWgfValue) formParameters.get("commissioneBase");
        SimpleWgfValue idSeduta = (SimpleWgfValue) formParameters.get("idSeduta");
        mappa.put("idSeduta", (String) idSeduta.getValue());
        SimpleWgfValue idAnagrafica = (SimpleWgfValue) formParameters.get("idAnagrafica");
        mappa.put("idAnagrafica", (String) idAnagrafica.getValue());
        SimpleWgfValue ruoli = (SimpleWgfValue) formParameters.get("ruoli");
        mappa.put("idRuoloCommissione", (String) ruoli.getValue());
        SimpleWgfValue idSedutaPratica = (SimpleWgfValue) formParameters.get("idSedutaPratica");
        if (idSedutaPratica != null && !Strings.isNullOrEmpty((String) idSedutaPratica.getValue())) {
            mappa.put("idSedutaPratica", (String) idSedutaPratica.getValue());
            OcPraticaCommissione o = organiCollegialiDao.findByIdSedutaPraticheAnagrafica(Integer.valueOf((String) idSedutaPratica.getValue()), Integer.valueOf((String) idAnagrafica.getValue()));
            if (o != null) {
                result = Boolean.FALSE;
                message = "Anagrafica già utilizzata";
            }
        } else if (commissioneBase != null && !Strings.isNullOrEmpty((String) commissioneBase.getValue()) && "si".equals((String) commissioneBase.getValue())) {
            OcPraticaCommissione o = organiCollegialiDao.findOrganiCollegialiSeduteCommissioneAnagraficaBase(Integer.valueOf((String) idSeduta.getValue()), Integer.valueOf((String) idAnagrafica.getValue()));
            if (o != null) {
                result = Boolean.FALSE;
                message = "Anagrafica già utilizzata";
            }
        } else {

            result = Boolean.FALSE;
            message = "La pratica non è stata asseganata a questa seduta";
        }

        if (result) {
            cdsAction.inserisciOrganoCollegialeSedutePratiche(mappa);
        }

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    public void initModificaOrganiCollegialiSedutePratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        JSONArray j = new JSONArray(request.getParameter("idSedutaPratica"));
        Integer idSedutaPratica = Integer.valueOf((String) j.get(0));
        j = new JSONArray(request.getParameter("idAnagrafica"));
        Integer idAnagrafica = Integer.valueOf((String) j.get(0));
        OcPraticaCommissione ocs = organiCollegialiDao.findByIdSedutaPraticheAnagrafica(idSedutaPratica, idAnagrafica);

        formEngine.putInstanceDataValue("ruoli_data", new Supplier() {
            @Override
            public Object get() {
                List<LkRuoliCommissione> lista = lookupDao.findAllRuoloCommissione();
                return (List) Lists.newArrayList(Lists.transform(lista, new JsonMapTransformer<LkRuoliCommissione>() {
                    @Override
                    public void transformToMap(LkRuoliCommissione ruolo) {
                        put(LABEL, ruolo.getDescrizione());
                        put(VALUE, ruolo.getIdRuoloCommissione().toString());

                    }
                }));
            }
        }.get());
        formEngine.putInstanceDataValue("idSedutaPratica", idSedutaPratica.toString());
        formEngine.putInstanceDataValue("idAnagrafica", ocs.getAnagrafica().getIdAnagrafica().toString());
        formEngine.putInstanceDataValue("ruoli", ocs.getIdRuoloCommissione().getIdRuoloCommissione());
        formEngine.putInstanceDataValue("cognome", ocs.getAnagrafica().getCognome());
        formEngine.putInstanceDataValue("nome", ocs.getAnagrafica().getNome());
        formEngine.putInstanceDataValue("idSeduta", ocs.getOcSedutePratiche().getIdSeduta().getIdSeduta().toString());
    }

    @Override
    public JsonResponse salvaOrganoCollegialeSedutePratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        HashMap<String, Object> mappa = new HashMap<String, Object>();
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";

        SimpleWgfValue idSedutaPratica = (SimpleWgfValue) formParameters.get("idSedutaPratica");
        mappa.put("idSedutaPratica", (String) idSedutaPratica.getValue());

        SimpleWgfValue idAnagrafica = (SimpleWgfValue) formParameters.get("idAnagrafica");
        mappa.put("idAnagrafica", (String) idAnagrafica.getValue());

        SimpleWgfValue ruoli = (SimpleWgfValue) formParameters.get("ruoli");
        mappa.put("idRuoloCommissione", (String) ruoli.getValue());

        if (result) {
            cdsAction.modificaOrganoCollegialeSedutePratiche(mappa);
        }

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public Long countOrganiCollegialiSeduteCommissionePratiche(Filter filter) {
        Long count = organiCollegialiDao.countOrganiCollegialiSeduteCommissionePratiche(filter);
        return count;
    }

    @Override
    public List<OrganiCollegialiSeduteComissioneDTO> findOrganiCollegialiSeduteCommissionePratiche(Filter filter) {
        List<PraticaOrganiCollegiali> lista = organiCollegialiDao.findOrganiCollegialiSeduteCommissionePratiche(filter);
        List<OrganiCollegialiSeduteComissioneDTO> ret = new ArrayList<OrganiCollegialiSeduteComissioneDTO>();
        int idKey = 0;
        for (PraticaOrganiCollegiali p : lista) {
            OrganiCollegialiSeduteComissioneDTO o = new OrganiCollegialiSeduteComissioneDTO();
            o.setIdSeduta(filter.getIdSeduta());
            o.setIdOrganiCollegiali(p.getIdOrganiCollegiali().getIdOrganiCollegiali());
            o.setIdPraticaOrganiCollegiali(p.getIdPraticaOrganiCollegiali());
            o.setDataRicezione(p.getIdPratica().getDataRicezione());
            o.setDataRichiestaCommissione(p.getDataRichiesta());
            o.setDesEnte(p.getIdOrganiCollegiali().getIdEnte().getDescrizione());
            o.setDesOrganoCollegiale(p.getIdOrganiCollegiali().getDesOrganoCollegiale());
            o.setIdEnte(p.getIdOrganiCollegiali().getIdEnte().getIdEnte());
            o.setIdPratica(p.getIdPratica().getIdPratica());
            o.setOggetto(p.getIdPratica().getOggettoPratica());
            o.setProtocollo(p.getIdPratica().getProtocollo());
            if (p.getIdSeduta() != null) {
                for (OcSedutePratiche oc : p.getIdSeduta().getOcSedutePraticheList()) {
                    if (oc.getIdPraticaOrganiCollegiali() != null && oc.getIdPraticaOrganiCollegiali().getIdPraticaOrganiCollegiali().equals(p.getIdPraticaOrganiCollegiali())) {
                        o.setSequenza(oc.getSequenza());
                        o.setIdSedutaPratica(oc.getIdSedutaPratica());
                        break;
                    }
                }
            }
            o.setIdKey(idKey);
            ret.add(o);
            idKey++;
        }
        return ret;
    }

    public void initGestioneOrdinamentoPratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        JSONArray j = new JSONArray(request.getParameter("idSeduta"));
        Integer idSeduta = Integer.valueOf((String) j.get(0));
        Integer idSedutaPratica = null;
        j = new JSONArray(request.getParameter("idSedutaPratica"));
        if (!Strings.isNullOrEmpty((String) j.get(0))) {
            idSedutaPratica = Integer.valueOf((String) j.get(0));
        }
        j = new JSONArray(request.getParameter("idPraticaOrganiCollegiali"));
        Integer idPraticaOrganoCollegiale = Integer.valueOf((String) j.get(0));

        PraticaOrganiCollegiali poc = praticaOrganiCollegialiDao.findById(idPraticaOrganoCollegiale);
        OcSedutePratiche ocp = null;
        if (idSedutaPratica != null) {
            ocp = organiCollegialiDao.findByIdSedutaPratica(idSedutaPratica);
        }
        formEngine.putInstanceDataValue("idPraticaOrganoCollegiale", idPraticaOrganoCollegiale.toString());
        formEngine.putInstanceDataValue("idSeduta", idSeduta.toString());
        if (ocp != null) {
            formEngine.putInstanceDataValue("idSedutaPratica", idSedutaPratica.toString());
            formEngine.putInstanceDataValue("sequenza", ocp.getSequenza().toString());
            formEngine.putInstanceDataValue("note", ocp.getNote());
        } else {
            formEngine.putInstanceDataValue("idSedutaPratica", null);
            formEngine.putInstanceDataValue("sequenza", null);
            formEngine.putInstanceDataValue("note", null);
        }
        formEngine.putInstanceDataValue("idPratica", poc.getIdPratica().getIdPratica().toString());
        formEngine.putInstanceDataValue("protocollo", poc.getIdPratica().getProtocollo());
        formEngine.putInstanceDataValue("oggetto", poc.getIdPratica().getOggettoPratica());
        formEngine.putInstanceDataValue("dataRicezione", poc.getIdPratica().getDataRicezione());
        formEngine.putInstanceDataValue("dataRichiestaCommissione", poc.getDataRichiesta());

    }

    public JsonResponse salvaGestioneOrdinamentoPratiche(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        Boolean result = Boolean.TRUE;
        String message = "Aggiornamento effettuato";
        Map<String, String> cleanParametersMap = JsonBuilder.cleanParametersMap(request.getParameterMap());
        try {
            if (Strings.isNullOrEmpty(cleanParametersMap.get("sequenza")) || Integer.valueOf(cleanParametersMap.get("sequenza")).compareTo(0) < 1) {
                result = Boolean.FALSE;
                message = "Inserire una sequenza";
            }
        } catch (Exception e) {
            result = Boolean.FALSE;
            message = "Inserire una sequenza intera";
        }

        if (result) {
            cdsAction.inserisciSedutaPraticaSequenza(cleanParametersMap);
        }

        return JsonBuilder.newInstance().withSuccess(result).withMessage(message).buildResponse();
    }

    @Override
    public void initDettaglioPratica(HttpServletRequest request, Model model, FormEngine formEngine, Map<String, Object> formParameters) throws Exception {
        UtenteDTO authUser = (UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO);
        JSONArray j = new JSONArray(request.getParameter("idSeduta"));
        Integer idSeduta = Integer.valueOf((String) j.get(0));
        j = new JSONArray(request.getParameter("idPraticaOrganiCollegiali"));
        Integer idPraticaOrganoCollegiale = Integer.valueOf((String) j.get(0));

        OcSedutePratiche ocs = organiCollegialiDao.findByIdSedutaIdPraticaOC(idSeduta, idPraticaOrganoCollegiale);
        Pratica p = ocs.getIdPraticaOrganiCollegiali().getIdPratica();
        List<Integer> allegati = new ArrayList<Integer>();
        List<PraticheEventi> le = p.getPraticheEventiList();
        for (PraticheEventi pe : le) {
            for (PraticheEventiAllegati pea : pe.getPraticheEventiAllegatiList()) {
                if (!allegati.contains(pea.getAllegati().getId())) {
                    allegati.add(pea.getAllegati().getId());
                }
            }
        }
        request.getSession().setAttribute(SessionConstants.ID_PRATICA_SELEZIONATA, p.getIdPratica());
        String listAllegati = "<div class=\"wgf-fieldWrapper wgf-component\"><label class=\"wgf-label\">Documenti : </label>";
        for (Integer idAllegato : allegati) {
            Allegati a = allegatiDao.getAllegato(idAllegato);
            listAllegati = listAllegati + "<div class=\"row\">&nbsp;&nbsp;&nbsp;<a href=\"" + formEngine.getInstanceData().get("path") + "/download.htm?id_file=" + idAllegato + "\">" + a.getNomeFile() + "</a></div>";
        }
        listAllegati=listAllegati+"</div>";
        
        formEngine.putInstanceDataValue("procedimento", p.getIdProcEnte().getIdProc().getProcedimentiTestiByLang("it"));
        formEngine.putInstanceDataValue("ente", p.getIdProcEnte().getIdEnte().getDescrizione());
        
        formEngine.putInstanceDataValue("oggetto",p.getOggettoPratica());
        String procedimenti = "";
        for (PraticaProcedimenti pp : p.getPraticaProcedimentiList()) {
            procedimenti = procedimenti + "<div class=\"wgf-fieldWrapper wgf-component\"><label class=\"wgf-label\">Endoprocedimento : </label><span class=\"wgf-fieldIcons\"></span><input class=\"wgf-textField\"  disabled=\"\" name=\"procedimento\" value=\""+pp.getProcedimenti().getProcedimentiTestiByLang("it")+"\" type=\"text\"></div>";
        }
        formEngine.putInstanceDataValue("endoProcedimenti", procedimenti);

        formEngine.putInstanceDataValue("allegati", listAllegati);
        formEngine.putInstanceDataValue("oggetto", p.getOggettoPratica());
        formEngine.putInstanceDataValue("protocollo", p.getProtocollo());
        formEngine.putInstanceDataValue("dataRicezione", p.getDataRicezione());
        String richiedenti = "";
        for (PraticaAnagrafica a : p.getPraticaAnagraficaList()) {
            String r = "";
            if (Constants.RUOLO_COD_RICHIEDENTE.equals(a.getLkTipoRuolo().getCodRuolo())){
                r = a.getAnagrafica().getCodiceFiscale() + "&nbsp;" +( a.getAnagrafica().getCognome() != null ? a.getAnagrafica().getCognome() : "" )+ "&nbsp;" + (a.getAnagrafica().getNome() != null ? a.getAnagrafica().getNome() : "") + "&nbsp;" + (a.getAnagrafica().getDenominazione() != null ? a.getAnagrafica().getDenominazione() : "");
                richiedenti = richiedenti + "<div class=\"wgf-fieldWrapper wgf-component\"><label class=\"wgf-label\">Richiedente : </label><span class=\"wgf-fieldIcons\"></span><input class=\"wgf-textField\"  disabled=\"\" name=\"richiedente\" value=\""+r+"\" type=\"text\"></div>";
            }
        }
        formEngine.putInstanceDataValue("richiedenti", richiedenti);

    }

}
