/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo;

import it.wego.cross.client.EGrammataProtocol;
import it.wego.cross.client.CrossProtocolEnvelope;
import it.eng.people.connects.interfaces.protocollo.IProtocolEnvelope;
import it.eng.people.connects.interfaces.protocollo.beans.IdentificatoreDiProtocollo;
import it.wego.cross.beans.EGrammataFascicoloBean;
import it.wego.cross.beans.EGrammataIdentificatoreDiProtocollo;
import it.wego.cross.constants.EGrammata;
import it.wego.cross.constants.EGrammataConfiguration;
import it.wego.cross.constants.ProtocolloConstants;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.documenti.GestioneAllegatiGenova;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Pratica;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

public class GestioneProtocolloGenova implements GestioneProtocollo {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private ConfigurationService configurationService;
    private static final String PROTOCOLLO_INPUT = "I";
    private static final String SUAP_GENOVA = "1001";
    private static final String SUE_GENOVA = "2001";
    private static final String CEMENTI_GENOVA = "3001";
    private static final String EVENTO_RICEZIONE = "RIC";

    @Override
    public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
        it.wego.cross.entity.Pratica pratica = praticheService.getPratica(documentoProtocollo.getIdPratica());
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(documentoProtocollo.getIdPraticaEvento());
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();
        IProtocolEnvelope envelope = new CrossProtocolEnvelope(documentoProtocollo);
        EGrammataProtocol protocollo = new EGrammataProtocol();
        HashMap<String, Object> configurazioneProtocollo = getConfigurazioneProtocollo(ente, comune);
        String fascicolo = null;
        String annoFascicolo = null;
        Date dataFascicolo = null;

        configurazioneProtocollo.put(EGrammata.NUMERO_FASCICOLO, documentoProtocollo.getCodiceFascicolo());
        configurazioneProtocollo.put(EGrammata.ANNO_FASCICOLO, documentoProtocollo.getAnnoFascicolo() == null ? null : String.valueOf(documentoProtocollo.getAnnoFascicolo()));
        Log.PLUGIN.info(EGrammata.NUMERO_FASCICOLO + ": " + configurazioneProtocollo.get(EGrammata.NUMERO_FASCICOLO));
        Log.PLUGIN.info(EGrammata.ANNO_FASCICOLO + ": " + configurazioneProtocollo.get(EGrammata.ANNO_FASCICOLO));

        if (!Utils.e(documentoProtocollo.getCodiceFascicolo())) {
            EGrammataFascicoloBean fascicoloBean = protocollo.getFascicolo(configurazioneProtocollo);
            if (Utils.e(fascicoloBean.getIdFascicolo())) {
                throw new Exception("Impossibile trovare il fascicolo su cui è richiesta la protocollazione");
            }
            fascicolo = fascicoloBean.getNumeroFascicolo();
            annoFascicolo = fascicoloBean.getAnnoFascicolo();
            dataFascicolo = pratica.getDataProtocollazione();
            configurazioneProtocollo.put(EGrammata.ID_FASCICOLO, fascicoloBean.getIdFascicolo());
        } else {
            //Se sono SUAP di Genova e sto ricevendo la pratica, devo crearmi il fascicolo ...
            if ((ente.getCodEnte().equals(SUAP_GENOVA) || ente.getCodEnte().equals(CEMENTI_GENOVA)) && praticaEvento.getIdEvento().getCodEvento().equals(EVENTO_RICEZIONE)) {
                EGrammataFascicoloBean fascicoloBean = protocollo.createFascicolo(pratica.getIdentificativoPratica(), configurazioneProtocollo);
                if (fascicoloBean == null || Utils.e(fascicoloBean.getIdFascicolo())) {
                    throw new Exception("Si è verificato un errore durante la creazione del fascicolo");
                }
                //Imposto il fascicolo che sarà poi associato alla pratica
                fascicolo = fascicoloBean.getNumeroFascicolo();
                annoFascicolo = fascicoloBean.getAnnoFascicolo();
                dataFascicolo = new Date();
                configurazioneProtocollo.put(EGrammata.ID_FASCICOLO, fascicoloBean.getIdFascicolo());
            }
            //Se sono il SUE, il fascicolo viene creato dal Backoffice dell'edilizia
        }

        if (!Utils.e(fascicolo)) {
            pratica.setProtocollo(fascicolo);
            pratica.setAnnoRiferimento(Integer.valueOf(annoFascicolo));
            pratica.setDataProtocollazione(dataFascicolo);
            pratica.setCodRegistro((String) configurazioneProtocollo.get(EGrammata.REGISTRO_FASCICOLO));
        }

        configurazioneProtocollo.put("DOCUMENTO_PROTOCOLLO", documentoProtocollo);

        EGrammataIdentificatoreDiProtocollo egrammataIdProtocollo;
        if (documentoProtocollo.getDirezione().equalsIgnoreCase(PROTOCOLLO_INPUT)) {
            egrammataIdProtocollo = protocollo.doProtocol(envelope, configurazioneProtocollo);
        } else {
            Log.PLUGIN.info("doProtocolOut READY TO CALL");
            if (ente.getCodEnte().equals(SUAP_GENOVA)) {
                Log.PLUGIN.info("IS SUAP GENOVA TRUE");
                Log.PLUGIN.info("OVERRIDE_ID_UO_IN: " + configurazioneProtocollo.get("ID_POSTAZ_LAVORO"));
                configurazioneProtocollo.put("OVERRIDE_ID_UO_IN", configurazioneProtocollo.get("ID_POSTAZ_LAVORO"));
            }
            egrammataIdProtocollo = protocollo.doProtocolOut(envelope, configurazioneProtocollo);
        }

        IdentificatoreDiProtocollo idProtocollo = egrammataIdProtocollo.getIdentificatoreDiProtocollo();
        String aoo = idProtocollo.getCodiceAOO();
        Date dataProtocollazione = idProtocollo.getDataDiRegistrazione();
        long numeroProtocollo = idProtocollo.getNumeroDiRegistrazione();
        DocumentoProtocolloResponse response = new DocumentoProtocolloResponse();
        response.setNumeroProtocollo(String.valueOf(numeroProtocollo));
        DateFormat annoProtocolloDateFormat = new SimpleDateFormat("yyyy");
        String anno = annoProtocolloDateFormat.format(dataProtocollazione);
        response.setAnnoProtocollo(anno);
        response.setFascicolo(fascicolo);
        response.setAnnoFascicolo(annoFascicolo);
        response.setCodRegistro(aoo);
        response.setDataProtocollo(dataProtocollazione);
        response.setAllegatoOriginale(documentoProtocollo.getAllegatoOriginale());
        response.setAllegati(documentoProtocollo.getAllegati());
        response.setDataCreazioneFascicolo(dataFascicolo);
        response.setNote(egrammataIdProtocollo.getMessage());

        //se suap genova se ricezione e ho il fascicolo
        //pratica.registro= idProtocollo.getCodiceAOO();
        return response;
    }

    @Override
    @Nullable
    public DocumentoProtocolloResponse findByProtocollo(Integer annoRiferimento, String numeroProtocollo, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        Integer idComune = comune == null ? null : comune.getIdComune();

        EGrammataProtocol protocollo = new EGrammataProtocol();
        HashMap<String, Object> configurazioneProtocollo = getConfigurazioneProtocollo(ente, comune);
        DocumentoProtocolloResponse response = protocollo.findByEstremiProtocolloGenerale(annoRiferimento, numeroProtocollo, configurazioneProtocollo);
        if (response == null) {
            return response;
        }
        //Poiché la ricerca è sempre su pratica, il valore lo prendo dal metodo getTipologiaDocumentoPerPratica()
        response.setTipoDocumento(getTipologiaDocumentoPerPratica(idEnte));
        GestioneAllegatiGenova gestioneAllegatiGenova = (GestioneAllegatiGenova) pluginService.getGestioneAllegati(idEnte, idComune);
        DocumentoProtocolloResponse responseConAllegati = gestioneAllegatiGenova.valorizzaAllegati(response, ente, comune);
        return responseConAllegati;
    }

    @Override
    public List<DocumentoProtocolloResponse> queryProtocollo(FilterProtocollo filterProtocollo, Enti ente, LkComuni comune) throws Exception {
        //NOT IMPLEMENTED: utilizzato per lo scheduler
        return null;
    }

    @Override
    public Protocollo getProtocolloBean(PraticheEventi praticaEvento) throws Exception {
        String numeroProtocollo = praticaEvento.getProtocollo();
        Protocollo protocollo = null;
        if (numeroProtocollo != null && !numeroProtocollo.isEmpty()) {
            //Il numero di protocollo deve essere nel formato PG/2013/85
            String[] splitted = numeroProtocollo.split("/");
            if (splitted.length == 3) {
                protocollo = new Protocollo();
                protocollo.setCodiceAoo(splitted[0]);
                protocollo.setNumeroRegistrazione(splitted[2]);
                protocollo.setDataRegistrazione(praticaEvento.getDataEvento());
            } else {
                //protocollo resta null
            }
        }
        return protocollo;
    }

    @Override
    public DocumentoProtocolloRequest getDocumentoProtocolloDaXml(Pratica praticaCross, String verso) throws Exception {
        //NOT IMPLEMENTED: non utilizzato in cross
        return null;
    }

    //Pratica nn può essere null
    @Override
    public DocumentoProtocolloRequest getDocumentoProtocolloDaDatabase(it.wego.cross.entity.Pratica pratica, PraticheEventi praticaEvento, List<Allegato> allegatiNuovi, List<SoggettoProtocollo> soggettiProtocollo, String verso, String oggetto) throws Exception {
        DocumentoProtocolloRequest protocollo = new DocumentoProtocolloRequest();
        protocollo.setIdPratica(pratica != null ? pratica.getIdPratica() : null);
        protocollo.setIdPraticaEvento(praticaEvento != null ? praticaEvento.getIdPraticaEvento() : null);

        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        Integer idComune = comune == null ? null : comune.getIdComune();

        List<SoggettoProtocollo> soggetti = new ArrayList<SoggettoProtocollo>();
        int i = 0;
        for (SoggettoProtocollo sp : soggettiProtocollo) {
            if (i == 0) {
                sp.setTitolare(Boolean.TRUE);
            } else {
                sp.setTitolare(Boolean.FALSE);
            }
            i++;
            soggetti.add(sp);
        }
        protocollo.setSoggetti(soggetti);
        List<it.wego.cross.plugins.commons.beans.Allegato> allegatiProtocollo = new ArrayList<it.wego.cross.plugins.commons.beans.Allegato>();
        for (Allegato a : allegatiNuovi) {
            if (a.getProtocolla()) {
                it.wego.cross.plugins.commons.beans.Allegato allegato = new it.wego.cross.plugins.commons.beans.Allegato();
                allegato.setDescrizione(a.getDescrizione());
                byte[] fileSuDisco;
                if (a.getPathFile() != null) {
                    fileSuDisco = FileUtils.getFileContent(new File(a.getPathFile()));
                } else if (a.getIdEsterno() != null) {
                    GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(idEnte, idComune);
                    it.wego.cross.plugins.commons.beans.Allegato all = gestioneAllegati.getFile(a.getIdEsterno(), ente, comune);
                    fileSuDisco = all.getFile();
                } else {
                    fileSuDisco = a.getFile();
                }
                allegato.setFile(fileSuDisco);
                allegato.setNomeFile(a.getNomeFile());
                if (a.getFileOrigine()) {
                    protocollo.setAllegatoOriginale(allegato);
                } else {
                    allegatiProtocollo.add(allegato);
                }
            }
        }

        protocollo.setOggetto(oggetto);
        protocollo.setIdentificativoPratica(pratica.getIdentificativoPratica());

        protocollo.setAllegati(allegatiProtocollo);
        if (verso.equalsIgnoreCase(PROTOCOLLO_INPUT)) {
            protocollo.setDirezione("I");
        } else {
            protocollo.setDirezione("O");
        }

        if (!Utils.e(pratica.getProtocollo())) {
            protocollo.setCodiceFascicolo(pratica.getProtocollo());
            protocollo.setAnnoFascicolo(pratica.getAnnoRiferimento());
        } else {
            String numeroFascicoloConfig = configurationService.getCachedConfiguration(EGrammataConfiguration.NUMERO_FASCICOLO, idEnte, idComune);
            String annoFascicoloConfig = configurationService.getCachedConfiguration(EGrammataConfiguration.ANNO_FASCICOLO, idEnte, idComune);
            if (!Utils.e(numeroFascicoloConfig)) {
                protocollo.setCodiceFascicolo(numeroFascicoloConfig);
                protocollo.setAnnoFascicolo(Integer.valueOf(annoFascicoloConfig));
            }

        }

        return protocollo;
    }

    private HashMap<String, Object> getConfigurazioneProtocollo(Enti ente, LkComuni comune) {
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        Integer idComune = comune == null ? null : comune.getIdComune();

        HashMap<String, Object> map = new HashMap<String, Object>();
        Log.PLUGIN.info(EGrammata.CODICE_ENTE + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.CODICE_ENTE, idEnte, idComune));
        map.put(EGrammata.CODICE_ENTE, configurationService.getCachedConfiguration(EGrammataConfiguration.CODICE_ENTE, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.TIMEOUT + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.TIMEOUT, idEnte, idComune));
        map.put(EGrammata.TIMEOUT, configurationService.getCachedConfiguration(EGrammataConfiguration.TIMEOUT, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.USERNAME + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.PROTOCOLLO_USERNAME, idEnte, idComune));
        map.put(EGrammata.USERNAME, configurationService.getCachedConfiguration(EGrammataConfiguration.PROTOCOLLO_USERNAME, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.PASSWORD + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.PROTOCOLLO_PASSWORD, idEnte, idComune));
        map.put(EGrammata.PASSWORD, configurationService.getCachedConfiguration(EGrammataConfiguration.PROTOCOLLO_PASSWORD, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.INDIRIZZO_WS_PROTOCOLLO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_PROTOCOLLO, idEnte, idComune));
        map.put(EGrammata.INDIRIZZO_WS_PROTOCOLLO, configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_PROTOCOLLO, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.INDIRIZZO_WS_RICERCA_PROTOCOLLO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_RICERCA_PROTOCOLLO, idEnte, idComune));
        map.put(EGrammata.INDIRIZZO_WS_RICERCA_PROTOCOLLO, configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_RICERCA_PROTOCOLLO, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.INDIRIZZO_WS_RICERCA_ANAGRAFICA + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_RICERCA_ANAGRAFICA, idEnte, idComune));
        map.put(EGrammata.INDIRIZZO_WS_RICERCA_ANAGRAFICA, configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_RICERCA_ANAGRAFICA, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_UO_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_UO_IN, idEnte, idComune));
        map.put(EGrammata.ID_UO_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_UO_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_UO_PROV + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_UO_PROV, idEnte, idComune));
        map.put(EGrammata.ID_UO_PROV, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_UO_PROV, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_UTE_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_UTE_IN, idEnte, idComune));
        map.put(EGrammata.ID_UTE_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_UTE_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.SUBJECT + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.SUBJECT, idEnte, idComune));
        map.put(EGrammata.SUBJECT, configurationService.getCachedConfiguration(EGrammataConfiguration.SUBJECT, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_TITOLAZIONE + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_TITOLAZIONE, idEnte, idComune));
        map.put(EGrammata.ID_TITOLAZIONE, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_TITOLAZIONE, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_INDICE_COPIE + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_INDICE_COPIE, idEnte, idComune));
        map.put(EGrammata.ID_INDICE_COPIE, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_INDICE_COPIE, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.COPY_OUC_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_OUC_IN, idEnte, idComune));
        map.put(EGrammata.COPY_OUC_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_OUC_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.COPY_OUS_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_OUS_IN, idEnte, idComune));
        map.put(EGrammata.COPY_OUS_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_OUS_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.COPY_POST_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_POST_IN, idEnte, idComune));
        map.put(EGrammata.COPY_POST_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_POST_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.COPY_SERV_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_SERV_IN, idEnte, idComune));
        map.put(EGrammata.COPY_SERV_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_SERV_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.COPY_SETT_IN + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_SETT_IN, idEnte, idComune));
        map.put(EGrammata.COPY_SETT_IN, configurationService.getCachedConfiguration(EGrammataConfiguration.COPY_SETT_IN, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.DESTINATARIO_IND_TELEM_TIPO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.DESTINATARIO_IND_TELEM_TIPO, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_INDICE + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_INDICE, idEnte, idComune));
        map.put(EGrammata.ID_INDICE, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_INDICE, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.ID_POSTAZIONE_LAVORO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ID_POSTAZ_LAVORO, idEnte, idComune));
        map.put(EGrammata.ID_POSTAZIONE_LAVORO, configurationService.getCachedConfiguration(EGrammataConfiguration.ID_POSTAZ_LAVORO, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.INDIRIZZO_WS_INSERIMENTO_FASCICOLO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_INSERIMENTO_FASCICOLO, idEnte, idComune));
        map.put(EGrammata.INDIRIZZO_WS_INSERIMENTO_FASCICOLO, configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_INSERIMENTO_FASCICOLO, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.INDIRIZZO_WS_RICERCA_FASCICOLO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_RICERCA_FASCICOLO, idEnte, idComune));
        map.put(EGrammata.INDIRIZZO_WS_RICERCA_FASCICOLO, configurationService.getCachedConfiguration(EGrammataConfiguration.ENDPOINT_RICERCA_FASCICOLO, idEnte, idComune));
        Log.PLUGIN.info(EGrammata.REGISTRO_FASCICOLO + ": " + configurationService.getCachedConfiguration(EGrammataConfiguration.REGISTRO_FASCICOLO, idEnte, idComune));
        map.put(EGrammata.REGISTRO_FASCICOLO, configurationService.getCachedConfiguration(EGrammataConfiguration.REGISTRO_FASCICOLO, idEnte, idComune));

        return map;
    }

//    @Override
//    public String getTipologiaDocumentoPerPratica(Integer idEnte) {
//        return Constants.CARICAMENTO_MANUALE_DOCUMENTO;
//    }
    @Override
    public String getTipologiaDocumentoPerPratica(Integer idEnte) {
        String tipologiaDocumentoPratica = configurationService.getCachedConfiguration(ProtocolloConstants.PROTOCOLLO_DOCUMENTO_FILTRA_PRATICA, idEnte, null);
        return Utils.e(tipologiaDocumentoPratica) ? null : tipologiaDocumentoPratica;
    }

    @Override
    public String getTipologiaDocumentoArrivo(Integer idEnte) {
        String tipologiaDocumentoArrivo = configurationService.getCachedConfiguration(ProtocolloConstants.PROTOCOLLO_DOCUMENTO_FILTRA_DOCUMENTI, idEnte, null);
        return Utils.e(tipologiaDocumentoArrivo) ? null : tipologiaDocumentoArrivo;
    }
}
