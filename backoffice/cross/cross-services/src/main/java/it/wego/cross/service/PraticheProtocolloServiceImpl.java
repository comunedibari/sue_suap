/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.constants.Constants;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.PraticaProtocolloDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class PraticheProtocolloServiceImpl implements PraticheProtocolloService {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UtentiService utentiService;

    @Override
    public Long countPraticheProtocollo(Filter filter, Integer idEnte) throws Exception {
        GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);
        List<String> tipiDocumentiPratica = new ArrayList<String>();
        String tipoDocumentoManuale = Constants.CARICAMENTO_MANUALE_PRATICA;
        tipiDocumentiPratica.add(tipoDocumentoManuale);
        if (gp != null) {
            String tipoDocumentoPraticaProtocollo = gp.getTipologiaDocumentoPerPratica(idEnte);
            tipiDocumentiPratica.add(tipoDocumentoPraticaProtocollo);
        }
        filter.setTipiDocumentiProtocollo(tipiDocumentiPratica);
        return praticaDao.countPraticheProtocollo(filter, entiDao.findByIdEnte(idEnte));
    }

    @Override
    public List<PraticaProtocolloDTO> findPraticheProtocollo(Filter filter, Integer idEnte) throws Exception {
        GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);
        List<String> tipiDocumentiPratica = new ArrayList<String>();
        String tipoDocumentoManuale = Constants.CARICAMENTO_MANUALE_PRATICA;
        tipiDocumentiPratica.add(tipoDocumentoManuale);
        if (gp != null) {
            String tipoDocumentoPraticaProtocollo = gp.getTipologiaDocumentoPerPratica(idEnte);
            tipiDocumentiPratica.add(tipoDocumentoPraticaProtocollo);
        }
        filter.setTipiDocumentiProtocollo(tipiDocumentiPratica);
        List<PraticheProtocollo> praticheProtocollo = praticaDao.findPraticheProtocollo(filter, entiDao.findByIdEnte(idEnte));
        List<PraticaProtocolloDTO> result = new ArrayList<PraticaProtocolloDTO>();
        if (praticheProtocollo != null && !praticheProtocollo.isEmpty()) {
            for (PraticheProtocollo pratica : praticheProtocollo) {
                PraticaProtocolloDTO dto = praticheSerializer.serialize(pratica, entiDao.findByIdEnte(idEnte));
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public PraticheProtocollo findPraticaProtocolloById(Integer idProtocollo) {
        PraticheProtocollo pratica = praticaDao.findPraticaProtocolloById(idProtocollo);
        return pratica;
    }

    @Override
    public PraticheProtocollo findPraticaProtocolloByIdentificativoPratica(String identificativoProtocollo) {
        PraticheProtocollo pratica = praticaDao.findPraticaProtocolloByIdentificativoPratica(identificativoProtocollo);
        return pratica;
    }

    @Override
    public PraticheProtocollo findPraticaProtocolloByAnnoProtocollo(Integer anno, String protocollo) {
        PraticheProtocollo pratica = praticaDao.findPraticaProtocolloByAnnoProtocollo(anno, protocollo);
        return pratica;
    }

    @Override
    public void aggiorna(PraticheProtocollo praticaProtocollo) throws Exception {
        praticaDao.update(praticaProtocollo);
    }

    @Override
    public void inserisci(PraticheProtocollo praticaProtocollo) throws Exception {
        praticaDao.insert(praticaProtocollo);
    }

    @Override
    public List<PraticaProtocolloDTO> findDocumentiProtocollo(Filter filter, Integer idEnte) throws Exception {
        GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);

        List<String> tipiDocumentiArrivo = new ArrayList<String>();
        String tipoDocumentoArrivoManuale = Constants.CARICAMENTO_MANUALE_DOCUMENTO;
        tipiDocumentiArrivo.add(tipoDocumentoArrivoManuale);
        if (gp != null) {
            String tipoDocumentoProtocollo = gp.getTipologiaDocumentoArrivo(idEnte);
            tipiDocumentiArrivo.add(tipoDocumentoProtocollo);
        }
        filter.setTipiDocumentiProtocollo(tipiDocumentiArrivo);
        List<PraticheProtocollo> praticheProtocollo = praticaDao.findDocumentiProtocollo(filter, entiDao.findByIdEnte(idEnte));
        List<PraticaProtocolloDTO> result = new ArrayList<PraticaProtocolloDTO>();
        if (praticheProtocollo != null) {
            for (PraticheProtocollo praticaProtocollo : praticheProtocollo) {
                Pratica praticaDb = praticheService.findByFascicoloAnno(praticaProtocollo.getNFascicolo(), praticaProtocollo.getAnnoFascicolo(), idEnte);
                if (praticaDb != null && praticaDb.getIdUtente() != null) {
                    praticaProtocollo.setIdUtentePresaInCarico(praticaDb.getIdUtente());
                    aggiorna(praticaProtocollo);
//                    usefulService.update(praticaProtocollo);
                } else {
                    if (praticaProtocollo.getIdUtentePresaInCarico() == null) {
                        Utente utenteSystem = utentiService.findUtenteDaUsername("SYSTEM");
                        //Setto come utente di default SYSTEM, l'utente di sistema
                        praticaProtocollo.setIdUtentePresaInCarico(utenteSystem);
                        aggiorna(praticaProtocollo);
//                        usefulService.update(praticaProtocollo);
                    }
                }
                PraticaProtocolloDTO dto = praticheSerializer.serialize(praticaProtocollo, entiDao.findByIdEnte(idEnte));
                //Aggiunto secondo controllo per gestire i casi in cui arrivi un documento su una pratica che nn è ancora stata 
                //presa in carico (20/06/2013 - Team Diagnosi)
//                if (praticaDb != null && praticaDb.getIdUtente() != null) {
//                    dto.setIdUtentePresaInCarico(praticaDb.getIdUtente().getIdUtente());
//                    String user = praticaDb.getIdUtente().getCognome() + " " + praticaDb.getIdUtente().getNome();
//                    dto.setDesUtentePresaInCarico(user);
//                }
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public Long countDocumentiProtocollo(Filter filter, Integer idEnte) throws Exception {
        GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);
        List<String> tipiDocumentiArrivo = new ArrayList<String>();
        String tipoDocumentoArrivoManuale = Constants.CARICAMENTO_MANUALE_DOCUMENTO;
        tipiDocumentiArrivo.add(tipoDocumentoArrivoManuale);
        if (gp != null) {
            String tipoDocumentoProtocollo = gp.getTipologiaDocumentoArrivo(idEnte);
            tipiDocumentiArrivo.add(tipoDocumentoProtocollo);
        }
        filter.setTipiDocumentiProtocollo(tipiDocumentiArrivo);
        Long count = praticaDao.countDocumentiProtocollo(filter, entiDao.findByIdEnte(idEnte));
        return count;
    }

    @Override
    public List<PraticheProtocollo> findByCodDocumento(String idDocumento) {
        List<PraticheProtocollo> pratiche = praticaDao.findByCodDocumento(idDocumento);
        return pratiche;
    }

    @Override
    public Date findMaxDataSincronizzazione() {
        Date dataMax = praticaDao.findMaxDataSincronizzazioneProtocollo();
        return dataMax;
    }

    @Override
    public PraticheProtocollo findByRegistroFascicoloAnnoTipo(String codRegistro, String fascicolo, Integer annoProtocollo, String tipoDocumentoPratica) {
        return praticaDao.findPraticheProtocolloByRegistroFascicoloProtocolloAnnoTipo(codRegistro, fascicolo, annoProtocollo, tipoDocumentoPratica);
    }

    @Override
    public List<AllegatoDTO> getAllegatiProtocollo(PraticheProtocollo pratica) throws Exception {
        String xmlPratica = new String(pratica.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaCross = PraticaUtils.getPraticaFromXML(xmlPratica);
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
        for (it.wego.cross.xml.Allegato allegatoXML : praticaCross.getAllegati().getAllegato()) {
            AllegatoDTO allegato = AllegatiSerializer.serializeAllegatoXML(allegatoXML);
            allegati.add(allegato);
        }
        return allegati;
    }

    @Override
    public List<AllegatoDTO> getAllegatiProtocollo(DocumentoProtocolloResponse documentoProtocolloResponse) {
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
        it.wego.cross.plugins.commons.beans.Allegato allegatoOriginale = documentoProtocolloResponse.getAllegatoOriginale();
        List<it.wego.cross.plugins.commons.beans.Allegato> allegatiProtocollo = documentoProtocolloResponse.getAllegati();
        for (it.wego.cross.plugins.commons.beans.Allegato allegato : allegatiProtocollo) {
            AllegatoDTO dto = new AllegatoDTO();
            dto.setDescrizione(allegato.getDescrizione());
            dto.setNomeFile(allegato.getNomeFile());
            dto.setIdFileEsterno(allegato.getIdEsterno());
            dto.setModelloPratica(false);
            allegati.add(dto);
        }
        AllegatoDTO dto = new AllegatoDTO();
        dto.setDescrizione(allegatoOriginale.getDescrizione());
        dto.setNomeFile(allegatoOriginale.getNomeFile());
        dto.setIdFileEsterno(allegatoOriginale.getIdEsterno());
        dto.setModelloPratica(true);
        allegati.add(dto);
        return allegati;
    }

    @Override
    public Staging getStaging(PraticheProtocollo praticaProtocollo, it.wego.cross.xml.Pratica praticaCross, Utente utenteConnesso) throws Exception {
        Log.APP.info("Preparo lo staging per il salvataggio");
        Staging staging;
        if (praticaProtocollo.getIdStaging() != null) {
            staging = praticaProtocollo.getIdStaging();
        } else {
            staging = new Staging();
            staging.setDataRicezione(new Date());
        }
        Log.APP.info("Imposto oggetto: " + praticaProtocollo.getOggetto());
        staging.setOggetto(praticaProtocollo.getOggetto());
        Log.APP.info("Tipo messaggio: " + Constants.CARICAMENTO_MANUALE);
        staging.setTipoMessaggio(Constants.CARICAMENTO_MANUALE);
        String codiceEnte = getCodiceEnte(praticaCross);
        Log.APP.info("Codice ente: " + codiceEnte);
        Enti ente = entiService.findByIdEnte(Integer.valueOf(codiceEnte));
        staging.setIdEnte(ente);
        Log.APP.info("Setto l'XML della pratica");
        String xmlPratica = PraticaUtils.getXmlFromPratica(praticaCross);
        Log.APP.info(xmlPratica);
        staging.setXmlPratica(xmlPratica.getBytes());
        Log.APP.info("Salvo l'utente connesso come utente con in carico la pratica");
        staging.setIdUtenteApertura(utenteConnesso);
        Log.APP.info("Salvo lo staging");
        return staging;
    }

    ///????????????
    private String getCodiceEnte(it.wego.cross.xml.Pratica praticaCross) {
        List<it.wego.cross.xml.Procedimento> procedimenti = praticaCross.getProcedimenti().getProcedimento();
        String codiceEnte = null;
        for (it.wego.cross.xml.Procedimento procedimento : procedimenti) {
            codiceEnte = procedimento.getCodEnteDestinatario();
        }
        return codiceEnte;
    }

    @Override
    public DocumentoProtocolloResponse getDocumentoProtocollo(PraticheProtocollo praticaProtocollo, Enti ente) throws Exception {
        GestioneProtocollo gestioneProcollo;
        gestioneProcollo = pluginService.getGestioneProtocollo(ente == null ? null : ente.getIdEnte(), null);
        //TODO: passare anche comune?
        DocumentoProtocolloResponse documentoProtocolloResponse = gestioneProcollo.findByProtocollo(praticaProtocollo.getAnnoRiferimento(), praticaProtocollo.getNProtocollo(), ente, null);
        return documentoProtocolloResponse;
    }
}
