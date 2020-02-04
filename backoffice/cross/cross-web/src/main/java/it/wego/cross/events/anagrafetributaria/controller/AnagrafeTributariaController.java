/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.controller;

import com.google.common.base.Strings;
import it.wego.cross.actions.AnagrafeTributariaAction;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.beans.layout.Select;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.controller.EventoController;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaCommercioDTO;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaCommercioListDTO;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaDTO;
import it.wego.cross.events.anagrafetributaria.bean.DatiCatastaliDTO;
import it.wego.cross.events.anagrafetributaria.bean.AnagraficaDTO;
import it.wego.cross.events.anagrafetributaria.bean.ImpresaDTO;
import it.wego.cross.events.anagrafetributaria.bean.IndirizzoRichiestaDTO;
import it.wego.cross.events.anagrafetributaria.bean.QualificaLookupDTO;
import it.wego.cross.events.anagrafetributaria.entity.AtRecordDettaglio;
import it.wego.cross.events.anagrafetributaria.serializer.AnagraficaTributariaSerializer;
import it.wego.cross.events.anagrafetributaria.service.AnagrafeTributariaService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.anagrafetributaria.commercio.AnagrafeTributariaCommercio;
import it.wego.cross.xml.anagrafetributaria.edilizia.AnagrafeTributariaEdilizia;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Beppe
 */
@Controller
public class AnagrafeTributariaController extends AbstractController {

    private static final String ANAGRAFE_TRIBUTARIA_EDILIZIA = "anagrafe_tributaria_edilizia_view";
    private static final String ANAGRAFE_TRIBUTARIA_COMMERCIO = "anagrafe_tributaria_commercio_view";
    private static final String ANAGRAFE_TRIBUTARIA_EXPORT = "anagrafe_tributaria_export";
    private static final String PERSONA_FISICA = "F";
    private static final String PERSONA_GIURIDICA = "G";
    private static final String COD_FORNITURA_TRIBUTARIA_EDILIZIA = "DIAXX";
    private static final String COD_FORNITURA_TRIBUTARIA_COMMERCIO = "CC";
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagrafeTributariaAction anagrafeTributariaAction;
    @Autowired
    private AnagrafeTributariaService anagrafeTributariaService;
    @Autowired
    private AnagraficaTributariaSerializer serializer;
    @Autowired
    private LookupService lookupService;

    @RequestMapping("/pratica/anagrafe_tributaria_edilizia/index")
    public String index(
            @ModelAttribute("id_pratica_selezionata") Integer idPratica,
            @ModelAttribute("id_evento") Integer idEvento,
            BindingResult result, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            AnagrafeTributariaDTO anagrafeTributaria = new AnagrafeTributariaDTO();
            String codFornitura = COD_FORNITURA_TRIBUTARIA_EDILIZIA;
            Pratica pratica = praticheService.getPratica(idPratica);
            List<AtRecordDettaglio> records = anagrafeTributariaService.findRecordDettaglio(pratica.getIdPratica(), codFornitura);
            if (records != null && !records.isEmpty()) {
                valorizzaDatiLookupTributariaEdilizia(model);
                List<IndirizzoRichiestaDTO> indirizziRichiesta = anagrafeTributariaService.findIndirizzoRichiesta(pratica);
                model.addAttribute("indirizziRichiesta", indirizziRichiesta);
                //Questo evento lo posso fare N volte. Se il record esiste già in DB, devo estrarre l'XML e caricare AnagrafeTributariaDTO 
                it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio recordDettaglio = JAXB.unmarshal(new StringReader(new String(records.get(0).getRecordDettaglio())), it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.class);
                anagrafeTributaria = serializer.serializeAnagrafeTributaria(recordDettaglio, pratica);
                anagrafeTributaria.setIdDettaglio(records.get(0).getIdAtRecordDettaglio());
            } else {
                valorizzaCampiTributariaEdilizia(model, anagrafeTributaria, pratica);
            }
            anagrafeTributaria.setIdPratica(idPratica);
            anagrafeTributaria.setIdEvento(idEvento);
            anagrafeTributaria.setCodFornitura(codFornitura);

            model.addAttribute("anagrafeTributaria", anagrafeTributaria);
            return ANAGRAFE_TRIBUTARIA_EDILIZIA;

        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare l'evento di anagrafeTributaria", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("mes sage", msg);
            return EventoController.EVENTO_SCELTA;
        }
    }

    private void valorizzaCampiTributariaEdilizia(Model model, AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) throws Exception {
        //Lookup/Tendine indipendenti dalla pratica
        valorizzaDatiLookupTributariaEdilizia(model);

        List<DatiCatastaliDTO> datiCatastali = anagrafeTributariaService.findDatiCatastali(pratica);
        model.addAttribute("datiCatastali", datiCatastali);
        anagrafeTributaria.setDatiCatastali(datiCatastali);
        //Valori derivanti dalla pratica
        List<AnagraficaDTO> richiedenti = anagrafeTributariaService.findRichiedentiPratica(pratica);
        model.addAttribute("richiedenti", richiedenti);
        anagrafeTributaria.setRichiedenti(richiedenti);
        List<AnagraficaDTO> beneficiari = anagrafeTributariaService.findBeneficiariPratica(pratica);
        model.addAttribute("beneficiario", beneficiari);
        anagrafeTributaria.setBeneficiari(beneficiari);
        List<AnagraficaDTO> professionisti = anagrafeTributariaService.findProfessionistiPratica(pratica);
        model.addAttribute("professionista", professionisti);
        anagrafeTributaria.setProfessionisti(professionisti);
        List<ImpresaDTO> imprese = anagrafeTributariaService.findImpresePratica(pratica);
        model.addAttribute("imprese", imprese);
        anagrafeTributaria.setImprese(imprese);
        List<IndirizzoRichiestaDTO> indirizziRichiesta = anagrafeTributariaService.findIndirizzoRichiesta(pratica);
        model.addAttribute("indirizziRichiesta", indirizziRichiesta);
        if (!CollectionUtils.isEmpty(indirizziRichiesta)) {
            anagrafeTributaria.setIndirizzo(indirizziRichiesta.get(0));
        }
    }

    private void valorizzaDatiLookupTributariaEdilizia(Model model) throws Exception {
        List<Select> tipoRichiesta = getPropertiesSelect("tipo_richiesta");
        model.addAttribute("tipoRichiesta", tipoRichiesta);
        List<Select> tipoIntervento = getPropertiesSelect("tipologia_intervento");
        model.addAttribute("tipoIntervento", tipoIntervento);
        List<Select> tipologiaRichiesta = getPropertiesSelect("tipologia_richiesta");
        model.addAttribute("tipologiaRichiesta", tipologiaRichiesta);
        List<Select> tipoUnita = anagrafeTributariaService.findAllTipoUnita();
        model.addAttribute("tipoUnita", tipoUnita);
        List<Select> tipologiaParticella = anagrafeTributariaService.findAllTipoParticella();
        model.addAttribute("tipologiaParticella", tipologiaParticella);
        List<Select> tipologiaCatasto = getPropertiesSelect("tipo_catasto");
        model.addAttribute("tipologiaCatasto", tipologiaCatasto);
        List<Select> albiProfessionali = anagrafeTributariaService.findAllAlbiProfessionali();
        model.addAttribute("albiProfessionali", albiProfessionali);
        List<Select> provinceAlbo = anagrafeTributariaService.findAllProvincie();
        model.addAttribute("provinceAlbo", provinceAlbo);
        List<QualificaLookupDTO> qualifiche = anagrafeTributariaService.findLookupQualificaByCondizione("RICHIEDENTE");
        model.addAttribute("qualifiche", qualifiche);
        List<QualificaLookupDTO> qualificheProfessionista = anagrafeTributariaService.findLookupQualificaByCondizione("TECNICO");
        model.addAttribute("qualificaProfessionista", qualificheProfessionista);
    }

    @RequestMapping(value = "/pratica/anagrafe_tributaria_edilizia/submit", method = RequestMethod.POST)
    public String indexAnagrafeTributariaEdiliziaSubmit(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            AnagrafeTributariaDTO anagrafeTributaria,
            BindingResult result) throws Exception {
        List<String> errors = new ArrayList<String>();
        try {
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            List<String> errori = validaDatiAnagrafeTributariaEdilizia(anagrafeTributaria);
            if (!CollectionUtils.isEmpty(errori)) {
                Pratica pratica = praticheService.getPratica(anagrafeTributaria.getIdPratica());
                valorizzaDatiLookupTributariaEdilizia(model);
                List<IndirizzoRichiestaDTO> indirizziRichiesta = anagrafeTributariaService.findIndirizzoRichiesta(pratica);
                model.addAttribute("indirizziRichiesta", indirizziRichiesta);
                model.addAttribute("anagrafeTributaria", anagrafeTributaria);
                Message msg = new Message();
                msg.setMessages(errori);
                model.addAttribute("message", msg);
                return ANAGRAFE_TRIBUTARIA_EDILIZIA;
            }
            anagrafeTributariaAction.salvaRecordEdilizia(anagrafeTributaria, utenteConnesso);
        } catch (Exception e) {
            Log.APP.error("Si è verificato un errore cercando di salvare il record di dettaglio per l'anagrafe tributaria", e);
//            gestisciErroreAnagrafeTributariaEdilizia(model, anagrafeTributaria);
            valorizzaDatiLookupTributariaEdilizia(model);
            Pratica pratica = praticheService.getPratica(anagrafeTributaria.getIdPratica());
            List<IndirizzoRichiestaDTO> indirizziRichiesta = anagrafeTributariaService.findIndirizzoRichiesta(pratica);
            model.addAttribute("indirizziRichiesta", indirizziRichiesta);
            errors.add(e.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            model.addAttribute("anagrafeTributaria", anagrafeTributaria);
            return ANAGRAFE_TRIBUTARIA_EDILIZIA;
        }
        return EventoController.REDIRECT_HOME_PAGE;
    }

    private List<String> validaDatiAnagrafeTributariaEdilizia(AnagrafeTributariaDTO anagrafeTributaria) throws Exception {
        List<String> errori = new ArrayList();
        if (anagrafeTributaria.getTipoRichiesta().equals("0")) {
            //Se è PDC deve essere valorizzata la data inizio lavori
            if (Strings.isNullOrEmpty(anagrafeTributaria.getDataInizioLavori())) {
                errori.add("Il campo 'Data inizio lavori' e' obbligatorio");
            }
        }

        if (CollectionUtils.isEmpty(anagrafeTributaria.getDatiCatastali())) {
            errori.add("E' necessario indicare almeno un dato catastale");
        } else {
            for (DatiCatastaliDTO dto : anagrafeTributaria.getDatiCatastali()) {
                if (dto.getDesTipoCatasto().equals("TAVOLARE")) {
                    if (Strings.isNullOrEmpty(dto.getEstensioneParticella())
                            || dto.getIdTipologiaParticella() == null) {
                        errori.add("Per i dati catastali relativi al catasto Ordinario sono obbligatori i seguenti campi: estensione particella, tipo particella");
                    }
                } else if (dto.getDesTipoCatasto().equals("ORDINARIO")) {
                    if (dto.getIdTipoUnita() == null || Strings.isNullOrEmpty(dto.getFoglio())
                            || Strings.isNullOrEmpty(dto.getParticella())) {
                        errori.add("Per i dati catastali relativi al catasto Tavolare sono obbligatori i seguenti campi: tipo unita', foglio, particella");
                    }
                }
            }
        }

        if (Strings.isNullOrEmpty(anagrafeTributaria.getTipoRichiesta())) {
            errori.add("E' necessario indicare il tipo di richiesta");
        }

        if (Strings.isNullOrEmpty(anagrafeTributaria.getTipologiaRichiesta())) {
            errori.add("E' necessario indicare la tipologia di richiesta");
        }
        if (Strings.isNullOrEmpty(anagrafeTributaria.getTipoIntervento())) {
            errori.add("E' necessario indicare il tipo di intervento");
        }
        if (anagrafeTributaria.getIndirizzo() == null) {
            errori.add("E' necessario indicare l'indirizzo dell'intervento");
        }

        if (CollectionUtils.isEmpty(anagrafeTributaria.getRichiedenti())) {
            errori.add("E' necessario indicare almeno un richiedente");
        } else {
            AnagraficaDTO richiedente = anagrafeTributaria.getRichiedenti().get(0);
            if (richiedente.getIdQualifica() == null) {
                errori.add("E' necessario indicare la qualifica del richiedente");
            }
        }

        if (CollectionUtils.isEmpty(anagrafeTributaria.getBeneficiari())) {
            errori.add("E' necessario indicare almeno un beneficiario");
        }

        if (CollectionUtils.isEmpty(anagrafeTributaria.getProfessionisti())) {
            errori.add("E' necessario indicare almeno un professionista");
        } else {
            List<AnagraficaDTO> professionisti = anagrafeTributaria.getProfessionisti();
            for (AnagraficaDTO professionista : professionisti) {
                if (professionista.getIdQualifica() == null) {
                    errori.add("E' necessario indicare la qualifica per il professionista " + professionista.getDescrizione());
                }
                if (professionista.getIdTipoCollegio() == null) {
                    errori.add("E' necessario indicare il collegio per il professionista " + professionista.getDescrizione());
                }
                if (Strings.isNullOrEmpty(professionista.getCodProvinciaIscrizione())) {
                    errori.add("E' necessario indicare la provincia di iscrizione per il professionista " + professionista.getDescrizione());
                }
                if (Strings.isNullOrEmpty(professionista.getNumeroIscrizioneAlbo())) {
                    errori.add("E' necessario indicare il numero di iscrizione all'albo del professionista " + professionista.getDescrizione());
                }
            }
        }

        return errori;
    }

//    private void gestisciErroreAnagrafeTributariaEdilizia(Model model, AnagrafeTributariaDTO anagrafeTributaria) throws Exception {
//        Pratica pratica = praticheService.getPratica(anagrafeTributaria.getIdPratica());
//        List<QualificaLookupDTO> qualifiche = anagrafeTributariaService.findLookupQualificaByCondizione("RICHIEDENTE");
//        model.addAttribute("qualifiche", qualifiche);
//        List<AnagraficaDTO> richiedenti = anagrafeTributariaService.findRichiedentiPratica(pratica);
//        model.addAttribute("richiedenti", richiedenti);
//        List<Select> tipoRichiesta = getPropertiesSelect("tipo_richiesta");
//        model.addAttribute("tipoRichiesta", tipoRichiesta);
//        List<Select> tipoIntervento = getPropertiesSelect("tipologia_intervento");
//        model.addAttribute("tipoIntervento", tipoIntervento);
//        List<Select> tipologiaRichiesta = getPropertiesSelect("tipologia_richiesta");
//        model.addAttribute("tipologiaRichiesta", tipologiaRichiesta);
//        List<IndirizzoRichiestaDTO> indirizziRichiesta = anagrafeTributariaService.findIndirizzoRichiesta(richiedenti);
//        model.addAttribute("indirizziRichiesta", indirizziRichiesta);
//        List<Select> tipoUnita = anagrafeTributariaService.findAllTipoUnita();
//        model.addAttribute("tipoUnita", tipoUnita);
//        List<Select> tipologiaCatasto = getPropertiesSelect("tipo_catasto");
//        model.addAttribute("tipologiaCatasto", tipologiaCatasto);
//        List<Select> tipologiaParticella = anagrafeTributariaService.findAllTipoParticella();
//        model.addAttribute("tipologiaParticella", tipologiaParticella);
//        List<AnagraficaDTO> beneficiari = anagrafeTributariaService.findAllAnagraficheByTipologia(pratica, null);
//        model.addAttribute("beneficiario", beneficiari);
//        model.addAttribute("qualificheBeneficiario", qualifiche);
//        List<AnagraficaDTO> professionisti = anagrafeTributariaService.findAllAnagraficheByTipologia(pratica, PERSONA_FISICA);
//        model.addAttribute("professionista", professionisti);
//        List<Select> albiProfessionali = anagrafeTributariaService.findAllAlbiProfessionali();
//        model.addAttribute("albiProfessionali", albiProfessionali);
//        List<Select> provinceAlbo = anagrafeTributariaService.findAllProvincie();
//        model.addAttribute("provinceAlbo", provinceAlbo);
//        List<QualificaLookupDTO> qualificheProfessionista = anagrafeTributariaService.findLookupQualificaByCondizione("TECNICO");
//        model.addAttribute("qualificaProfessionista", qualificheProfessionista);
//        List<AnagraficaDTO> imprese = anagrafeTributariaService.findAllAnagraficheByTipologia(pratica, PERSONA_GIURIDICA);
//        model.addAttribute("imprese", imprese);
//        List<IndirizzoRichiestaDTO> indirizziSede = anagrafeTributariaService.findIndrizzoSede(imprese);
//        model.addAttribute("indirizziSede", indirizziSede);
//        model.addAttribute("anagrafeTributaria", anagrafeTributaria);
//    }
    @RequestMapping("/pratica/anagrafe_tributaria_commercio/index")
    public String indexCommercio(
            Model model,
            @ModelAttribute("id_pratica_selezionata") Integer idPratica,
            @ModelAttribute("id_evento") Integer idEvento,
            BindingResult result, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String codFornitura = COD_FORNITURA_TRIBUTARIA_COMMERCIO;
            AnagrafeTributariaCommercioListDTO anagraficheList = new AnagrafeTributariaCommercioListDTO();
            List<AnagrafeTributariaCommercioDTO> anagrafiche = new ArrayList<AnagrafeTributariaCommercioDTO>();
            Pratica pratica = praticheService.getPratica(idPratica);
            List<AtRecordDettaglio> records = anagrafeTributariaService.findRecordDettaglio(pratica.getIdPratica(), codFornitura);

            if (records != null) {

                //Questo evento lo posso fare N volte. Se il record esiste già in DB, devo estrarre l'XML e caricare AnagrafeTributariaDTO 
                for (AtRecordDettaglio record : records) {
                    it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio recordDettaglio = JAXB.unmarshal(new StringReader(new String(record.getRecordDettaglio())), it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio.class);
                    AnagrafeTributariaCommercioDTO dto = serializer.serializeAnagrafeTributariaCommercio(recordDettaglio);

                    dto.setIdDettaglio(record.getIdAtRecordDettaglio());
                    dto.setCodFornitura(codFornitura);

                    anagrafiche.add(dto);
                }
            } else {
                AnagrafeTributariaCommercioDTO anagrafeTributaria = new AnagrafeTributariaCommercioDTO();
                anagrafeTributaria.setCodFornitura(codFornitura);
                anagrafiche.add(anagrafeTributaria);
            }
            anagraficheList.setAnagrafiche(anagrafiche);
            anagraficheList.setIdEvento(idEvento);
            anagraficheList.setIdPratica(idPratica);
            valorizzaDatiTributariaCommercio(model, pratica);
            model.addAttribute("anagrafeCommercio", anagraficheList);
            return ANAGRAFE_TRIBUTARIA_COMMERCIO;
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare l'evento di anagrafeTributaria", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            return EventoController.EVENTO_SCELTA;
        }
    }

    private void valorizzaDatiTributariaCommercio(Model model, Pratica pratica) throws Exception {
        List<Select> provvedimenti = getPropertiesSelect("codice_provvedimento");
        model.addAttribute("provvedimenti", provvedimenti);
        List<AnagraficaDTO> richiedenti = anagrafeTributariaService.findRichiedentiPratica(pratica);
        model.addAttribute("soggetti", richiedenti);
    }

    @RequestMapping(value = "/pratica/anagrafe_tributaria_commercio/submit", method = RequestMethod.POST)
    public String indexAnagrafeTributariaCommercioSubmit(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            AnagrafeTributariaCommercioListDTO anagrafiche,
            BindingResult result) throws Exception {
        Pratica pratica = praticheService.getPratica(anagrafiche.getIdPratica());
        try {
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            List<AnagrafeTributariaCommercioDTO> anagraficheList = anagrafiche.getAnagrafiche();
            for (AnagrafeTributariaCommercioDTO anagrafeTributaria : anagraficheList) {
                anagrafeTributariaAction.salvaRecordCommercio(anagrafiche.getIdPratica(), anagrafeTributaria, utenteConnesso);
            }
        } catch (Exception e) {
            Log.APP.info("Si è verificato un errore generando l'anagrafe tributaria", e);
            valorizzaDatiTributariaCommercio(model, pratica);
            model.addAttribute("anagrafeCommercio", anagrafiche);
//            gestisciErroreAnagrafeTributariaCommercio(model, anagrafiche);
            List<String> errors = new ArrayList<String>();
            errors.add("Si è verificato un errore generando il tracciato");
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            return ANAGRAFE_TRIBUTARIA_COMMERCIO;
        }
        return EventoController.REDIRECT_HOME_PAGE;
    }

//    private void gestisciErroreAnagrafeTributariaCommercio(Model model, AnagrafeTributariaCommercioListDTO anagrafiche) throws Exception {
//        List<Select> provvedimenti = getPropertiesSelect("codice_provvedimento");
//        model.addAttribute("provvedimenti", provvedimenti);
//        Pratica pratica = praticheService.getPratica(anagrafiche.getIdPratica());
//        List<RichiedenteDTO> richiedenti = anagrafeTributariaService.findRichiedentiPratica(pratica);
//        model.addAttribute("soggetti", richiedenti);
//        model.addAttribute("anagrafeCommercio", anagrafiche);
//    }
    @RequestMapping(value = "/anagrafe_tributaria/export")
    public String richiediAnagrafeTributaria(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AnagrafeTributariaDTO anagrafeTributaria = new AnagrafeTributariaDTO();
        List<Select> comuni = anagrafeTributariaService.findAllComuni();
        List<Select> enti = anagrafeTributariaService.findAllEnti();
        List<Select> codiceFornitura = getPropertiesSelect("codice_fornitura");
        List<Select> naturaUfficio = getPropertiesSelect("natura_ufficio");
        model.addAttribute("anagrafeTributaria", anagrafeTributaria);
        model.addAttribute("codiceFornitura", codiceFornitura);
        model.addAttribute("naturaUfficio", naturaUfficio);
        model.addAttribute("comuni", comuni);
        model.addAttribute("enti", enti);
        UtenteDTO authUser = utentiService.getUtenteConnessoDTO(request);
        model.addAttribute("utenteConnesso", authUser);
        return ANAGRAFE_TRIBUTARIA_EXPORT;
    }

    @RequestMapping(value = "/anagrafe_tributaria/export/genera")
    public void generaAnagrafeTributaria(
            Model model,
            AnagrafeTributariaDTO anagrafeTributaria,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult result) throws Exception {
        try {
            String exportAnagrafeTributaria = "";
            String filename = "";
            if (anagrafeTributaria.getCodFornitura().equals(COD_FORNITURA_TRIBUTARIA_EDILIZIA)) {
                exportAnagrafeTributaria = serializeAnagrafeTributariaEdilizia(anagrafeTributaria);
                filename = "anagrafe_tributaria_edilizia_";
            } else if (anagrafeTributaria.getCodFornitura().equals(COD_FORNITURA_TRIBUTARIA_COMMERCIO)) {
                exportAnagrafeTributaria = serializeAnagrafeTributariaCommercio(anagrafeTributaria);
                filename = "anagrafe_tributaria_commercio_";
            }

            byte[] documentBody = exportAnagrafeTributaria.getBytes("UTF-8");
            response.setContentType("plain/text");
            response.addHeader("Content-Disposition", "attachment; filename=" + filename + anagrafeTributaria.getAnnoRiferimento() + ".txt");
            OutputStream out = response.getOutputStream();
            out.write(documentBody);
            out.flush();
            out.close();
//            HttpHeaders header = new HttpHeaders();
//            header.setContentType(MediaType.valueOf("plain/text"));
//            header.set("Content-Disposition", "attachment; filename=" + filename + anagrafeTributaria.getAnnoRiferimento() + ".txt");
//            header.setContentLength(documentBody.length);
//            return new HttpEntity<byte[]>(documentBody, header);
        } catch (Exception ex) {
            String error = "Non è stato possibile generare il file di anagrafe tributaria";
            Log.APP.error(error, ex);
            response.setContentType("text/plain");
            OutputStream out = response.getOutputStream();
            out.write(error.getBytes());
            out.flush();
            out.close();
//            HttpHeaders header = new HttpHeaders();
//            header.setContentType(MediaType.TEXT_PLAIN);
//            return new HttpEntity<byte[]>(error.getBytes(), header);
        }
    }

    private List<Select> getPropertiesSelect(String filename) throws Exception {
        URL resource = this.getClass().getResource("../properties/" + filename + ".properties");
        return getPropertiesSelect(resource);
    }

    private List<Select> getPropertiesSelect(URL fileUrl) throws Exception {
        List<Select> optionList = new ArrayList<Select>();
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(fileUrl.toURI())));

        Select optionLoop;
        for (String key : properties.stringPropertyNames()) {
            optionLoop = new Select();
            optionLoop.setItemValue(key);
            optionLoop.setItemLabel(StringEscapeUtils.escapeJavaScript(properties.getProperty(key)));
            optionList.add(optionLoop);
        }
        Collections.sort(optionList);
        return optionList;
    }

    private String serializeAnagrafeTributariaEdilizia(AnagrafeTributariaDTO anagrafeTributaria) throws Exception {
        it.wego.cross.xml.anagrafetributaria.edilizia.Controllo controllo = new it.wego.cross.xml.anagrafetributaria.edilizia.Controllo();
        controllo.setCarattereControllo("A");
        it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto soggettoObbligato = getSoggettoObbligato(anagrafeTributaria);
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordControllo recordTesta = new it.wego.cross.xml.anagrafetributaria.edilizia.RecordControllo();
        recordTesta.setTipoRecord("0");
        recordTesta.setCodiceIdentificativoFornitura(anagrafeTributaria.getCodFornitura());
        recordTesta.setCodiceNumericoFornitura("29");
        recordTesta.setSoggettoObbligato(soggettoObbligato);
        recordTesta.setAnnoRiferimento(String.valueOf(anagrafeTributaria.getAnnoRiferimento()));
        recordTesta.setControllo(controllo);
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordControllo recordCoda = new it.wego.cross.xml.anagrafetributaria.edilizia.RecordControllo();
        recordCoda.setTipoRecord("9");
        recordCoda.setCodiceIdentificativoFornitura(anagrafeTributaria.getCodFornitura());
        recordCoda.setCodiceNumericoFornitura("29");
        recordCoda.setControllo(controllo);
        recordCoda.setSoggettoObbligato(soggettoObbligato);
        recordCoda.setAnnoRiferimento(String.valueOf(anagrafeTributaria.getAnnoRiferimento()));
        AnagrafeTributariaEdilizia anagrafeTributariaEdilizia = new AnagrafeTributariaEdilizia();
        anagrafeTributariaEdilizia.setRecordTesta(recordTesta);
        anagrafeTributariaEdilizia.setRecordsDettaglio(new AnagrafeTributariaEdilizia.RecordsDettaglio());
        //Query per recuperare l'elenco di AtRecordDettaglio che matcha con l'annoRiferimento
        List<AtRecordDettaglio> recordsDettaglio = anagrafeTributariaService.getRecordDettaglio(anagrafeTributaria.getCodFornitura(), anagrafeTributaria.getAnnoRiferimento(), anagrafeTributaria.getIdSoggettoObbligato());
        for (AtRecordDettaglio rt : recordsDettaglio) {
            //Estraggo da AtRecordDettaglio l'xml
            String xmlRecordDettaglio = new String(rt.getRecordDettaglio());
            it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio recordDettaglioLoop = JAXB.unmarshal(new StringReader(xmlRecordDettaglio), it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.class);
            //Unmarshallo il recordDettaglioXml in un RecordDettaglio
            anagrafeTributariaEdilizia.getRecordsDettaglio()
                    .getRecordDettaglio().add(recordDettaglioLoop);
        }
        anagrafeTributariaEdilizia.setRecordCoda(recordCoda);
        String exportAnagrafeTributaria = anagrafeTributariaService.getDocumentoAnagrafeTributariaEdilizia(anagrafeTributariaEdilizia);
        return exportAnagrafeTributaria;
    }

    private it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto getSoggettoObbligato(AnagrafeTributariaDTO anagrafeTributaria) {
        it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto soggetto = new it.wego.cross.xml.anagrafetributaria.edilizia.Soggetto();
        soggetto.setCodiceFiscale(anagrafeTributaria.getCodiceFiscaleSoggettoObbligato());
        it.wego.cross.xml.anagrafetributaria.edilizia.SoggettoGiuridico soggettoGiuridico = new it.wego.cross.xml.anagrafetributaria.edilizia.SoggettoGiuridico();
        LkComuni comune = lookupService.findComuneById(anagrafeTributaria.getIdSoggettoObbligato());
        soggettoGiuridico.setCodiceCatastaleSedeLegale(comune.getCodCatastale());
        soggettoGiuridico.setDenominazione(comune.getDescrizione());
        soggetto.setSoggettoGiuridico(soggettoGiuridico);
        return soggetto;
    }

    private String serializeAnagrafeTributariaCommercio(AnagrafeTributariaDTO anagrafeTributaria) throws Exception {
        it.wego.cross.xml.anagrafetributaria.commercio.RecordControllo recordDiTesta = new it.wego.cross.xml.anagrafetributaria.commercio.RecordControllo();
        recordDiTesta.setTipoRecord("0");
        Enti ente = anagrafeTributariaService.findEnteById(anagrafeTributaria.getIdEnte());
        recordDiTesta.setCodiceFiscaleUfficio(anagrafeTributaria.getCodiceFiscaleSoggettoObbligato());
        recordDiTesta.setDenominazioneUfficio(Utils.truncate(ente.getDescrizione(), 60));
        recordDiTesta.setDomicilioFiscaleUfficio(Utils.truncate(ente.getCitta(), 35));
        recordDiTesta.setProvinciaDomicilioUfficio(Utils.truncate(ente.getProvincia(), 2));
        recordDiTesta.setIndirizzoUfficio(Utils.truncate(ente.getIndirizzo(), 35));
        if (anagrafeTributaria.getIdNaturaUfficio() != null) {
            recordDiTesta.setNaturaUfficio(String.valueOf(anagrafeTributaria.getIdNaturaUfficio()));
        }
        if (ente.getCap() != null) {
            recordDiTesta.setCapUfficio(Utils.truncate(String.valueOf(ente.getCap()), 5));
        }
        recordDiTesta.setAnnoRiferimento(String.valueOf(anagrafeTributaria.getAnnoRiferimento()));
        recordDiTesta.setCodiceFornitura("CC");
        String progressivoInvio = String.valueOf(anagrafeTributaria.getAnnoRiferimento()) + "001";
        recordDiTesta.setProgressivoInvio(progressivoInvio);
        DateFormat atFormat = new SimpleDateFormat("ddMMyyyy");
        recordDiTesta.setDataInvio(atFormat.format(new Date()));
        recordDiTesta.setFlagRiciclo("O");
        recordDiTesta.setTotaleRecordInviati(0);
        recordDiTesta.setFiller(null);
        AnagrafeTributariaCommercio anagrafeTributariaCommercio = new AnagrafeTributariaCommercio();
        anagrafeTributariaCommercio.setRecordTesta(recordDiTesta);
        anagrafeTributariaCommercio.setRecordsDettaglio(new AnagrafeTributariaCommercio.RecordsDettaglio());
        List<AtRecordDettaglio> recordsDettaglio = anagrafeTributariaService.getRecordDettaglio(anagrafeTributaria.getCodFornitura(), anagrafeTributaria.getAnnoRiferimento(), anagrafeTributaria.getIdSoggettoObbligato());
        int counter = 1;
        for (AtRecordDettaglio rt : recordsDettaglio) {
            //Estraggo da AtRecordDettaglio l'xml
            String xmlRecordDettaglio = new String(rt.getRecordDettaglio());
            it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio recordDettaglioLoop = JAXB.unmarshal(new StringReader(xmlRecordDettaglio), it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio.class);
            //Unmarshallo il recordDettaglioXml in un RecordDettaglio
            recordDettaglioLoop.setProgessivoRecord(counter);

            anagrafeTributariaCommercio.getRecordsDettaglio()
                    .getRecordDettaglio().add(recordDettaglioLoop);
            counter = counter + 1;
        }
        it.wego.cross.xml.anagrafetributaria.commercio.RecordControllo recordDiCoda = new it.wego.cross.xml.anagrafetributaria.commercio.RecordControllo();
        recordDiCoda.setTipoRecord("9");
        recordDiCoda.setCodiceFiscaleUfficio(anagrafeTributaria.getCodiceFiscaleSoggettoObbligato());
        recordDiCoda.setDenominazioneUfficio(Utils.truncate(ente.getDescrizione(), 60));
        recordDiCoda.setDomicilioFiscaleUfficio(Utils.truncate(ente.getCitta(), 35));
        recordDiCoda.setProvinciaDomicilioUfficio(Utils.truncate(ente.getProvincia(), 2));
        recordDiCoda.setIndirizzoUfficio(Utils.truncate(ente.getIndirizzo(), 35));
        if (anagrafeTributaria.getIdNaturaUfficio() != null) {
            recordDiCoda.setNaturaUfficio(String.valueOf(anagrafeTributaria.getIdNaturaUfficio()));
        }
        if (ente.getCap() != null) {
            recordDiCoda.setCapUfficio(Utils.truncate(String.valueOf(ente.getCap()), 5));
        }
        recordDiCoda.setTotaleRecordInviati(recordsDettaglio.size());
        recordDiCoda.setAnnoRiferimento(String.valueOf(anagrafeTributaria.getAnnoRiferimento()));
        recordDiCoda.setCodiceFornitura("CC");
        recordDiCoda.setProgressivoInvio(progressivoInvio);
        recordDiCoda.setDataInvio(atFormat.format(new Date()));
        recordDiCoda.setFlagRiciclo("O");
        recordDiCoda.setFiller(null);
        anagrafeTributariaCommercio.setRecordCoda(recordDiCoda);
        String exportAnagrafeTributaria = anagrafeTributariaService.getDocumentoAnagrafeTributariaCommercio(anagrafeTributariaCommercio);
        return exportAnagrafeTributaria;
    }
}
