/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.asitech.webservice.protocollo.Field;
import it.asitech.webservice.protocollo.FieldExt;
import it.asitech.webservice.protocollo.PRIGetSidResponse;
import it.asitech.webservice.protocollo.PRIObjectOut;
import it.asitech.webservice.protocollo.PRIQueryObjectOut;
import it.asitech.webservice.protocollo.PriObject;
import it.asitech.webservice.protocollo.PriObjectExt;
import it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.Genuit;
import it.wego.cross.constants.ProtocolloConstants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.GestioneAbstractGenuit;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.GenuitUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.client.aosta.protocollo.DettaglioProtocollo;
import it.wego.cross.webservices.client.aosta.protocollo.GenuitProtocolloClient;
import it.wego.cross.xml.Pratica;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gabriele
 */
@Repository
public class GestioneProtocolloGenuit extends GestioneAbstractGenuit implements GestioneProtocollo {

    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private UsefulDao usefulDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private ApplicationContext appContext;
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").setPrettyPrinting().create();

    public GestioneProtocolloGenuit() {
    }

    //<editor-fold defaultstate="collapsed" desc="METODO PROTOCOLLA">
    @Override
    public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
//        Log.PLUGIN.trace(gson.toJson(documentoProtocollo));

//        it.wego.cross.entity.Pratica pratica = praticaDao.findPraticaByIdentificativo(documentoProtocollo.getIdentificativoPratica());
        it.wego.cross.entity.Pratica pratica = praticaDao.findPratica(documentoProtocollo.getIdPratica());
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        Integer idComune = pratica.getIdComune().getIdComune();
        Integer idPraticaEvento = documentoProtocollo.getIdPraticaEvento();
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);

        ProtocolloServiceSoapBindingStub service = getService(pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
        //Timeout in milliseconds. Force timeout to 10 minutes
        service.setTimeout(1000 * 60 * 10);
        DocumentoProtocolloResponse protocolloResponse = null;
        String documentoPratica = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_PRATICA, idEnte, idComune);

        if (documentoProtocollo.getSoggetti() == null || documentoProtocollo.getSoggetti().isEmpty()) {
            throw new Exception("Impossibile protocollare senza nessun soggetto.");
        }

        if (pratica.getIdPraticaPadre() != null) {
            //E' un ente terzo che fa una comunicazione allo sportello
            //Forzo il documento come documento in arrivo
            String documentoEntrata = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_ARRIVO, idEnte, idComune);
            documentoProtocollo.setTipoDoc(documentoEntrata);
            documentoProtocollo.setCodiceFascicolo(pratica.getIdPraticaPadre().getProtocollo());
            documentoProtocollo.setAnnoFascicolo(pratica.getIdPraticaPadre().getAnnoRiferimento());
        }

        PRIGetSidResponse priGetSid = null;
        try {
            Log.PLUGIN.info("Protocollo Genuit: Richiedo il Sid per l'utente " + user + ".");
            priGetSid = service.PRIGetSid(user, password);
            if (priGetSid == null || GenuitUtils.hasError(priGetSid.getReturnCode())) {
                throw new Exception("Impossibile stabilire la connessione con il protocollo. Errore: " + (priGetSid == null ? "SID NULL" : GenuitUtils.serializeResponseMessagge(priGetSid.getReturnCode())));
            }

            Log.PLUGIN.info("Protocollo Genuit: Sid ottenuto.");

            String oggetto = documentoProtocollo.getOggetto();
            if (documentoProtocollo.getTipoDoc().equalsIgnoreCase(documentoPratica)) {
                oggetto = pratica.getIdComune().getDescrizione() + " - " + oggetto;
            }

            PriObject doc = PriObjectExt.create(
                    "DOCUMENTO",
                    FieldExt.create("TIPO_DOC", documentoProtocollo.getTipoDoc()),
                    FieldExt.create("OGGETTO", oggetto),
                    FieldExt.create("DATA_DOC", new Date()),
                    FieldExt.create("AOO", documentoProtocollo.getAoo()),
                    FieldExt.create("DIREZIONE", documentoProtocollo.getDirezione()),
                    FieldExt.create("UO", documentoProtocollo.getUo()),
                    FieldExt.create("TITOLARIO", documentoProtocollo.getTitolario()),
                    FieldExt.create("CLASSIFICA", documentoProtocollo.getClassifica()),
                    FieldExt.create("SOURCE", documentoProtocollo.getSource()));

            Log.PLUGIN.info("Protocollo Genuit: Creato documento.");

            PriObjectExt docEx = new PriObjectExt(doc);

            if (documentoProtocollo.getTipoDoc().equalsIgnoreCase(documentoPratica)) {
                docEx.addFields(FieldExt.create("DIFFERIMENTO", pratica.getIdentificativoPratica()));
            }

            //creazione soggetti                      
            List<PriObject> soggettiProtocolloList = new ArrayList<PriObject>();
            List<Field> soggettoProtocolloList = new ArrayList<Field>();
            Field[] soggettoProtocollo;
            for (SoggettoProtocollo soggetto : documentoProtocollo.getSoggetti()) {

                if (!StringUtils.isEmpty(soggetto.getMezzo())) {
                    soggettoProtocolloList.add(FieldExt.create("MEZZO", soggetto.getMezzo()));
                } else {
                    soggettoProtocolloList.add(FieldExt.create("MEZZO", defaultSoggettoMezzo));
                }
                if (!StringUtils.isEmpty(soggetto.getTipo())) {
                    soggettoProtocolloList.add(FieldExt.create("TIPO", soggetto.getTipo()));
                } else {
                    soggettoProtocolloList.add(FieldExt.create("TIPO", defaultSoggettoTipo));
                }

                if (!StringUtils.isEmpty(soggetto.getCodiceFiscale())) {
                    soggettoProtocolloList.add(FieldExt.create("COD_FISC", soggetto.getCodiceFiscale()));
//                    soggettoProtocolloList.add(FieldExt.create("CODICE_FISCALE", soggetto.getCodiceFiscale()));
                }
                if (!StringUtils.isEmpty(soggetto.getPartitaIva())) {
                    soggettoProtocolloList.add(FieldExt.create("PART_IVA", soggetto.getPartitaIva()));
//                    soggettoProtocolloList.add(FieldExt.create("PARTITA_IVA", soggetto.getPartitaIva()));
                }
                if (!StringUtils.isEmpty(soggetto.getIndirizzo())) {
                    soggettoProtocolloList.add(FieldExt.create("INDIRIZZO", soggetto.getIndirizzo()));
                }
                if (!StringUtils.isEmpty(soggetto.getDenominazione())) {
//                    soggettoProtocolloList.add(FieldExt.create("RAGIONE_SOCIALE", soggetto.getDescrizione()));
                    soggettoProtocolloList.add(FieldExt.create("RAG_SOG", soggetto.getDenominazione()));
                }
                if (!StringUtils.isEmpty(soggetto.getCognome()) && !StringUtils.isEmpty(soggetto.getNome())) {
                    String denominazione = soggetto.getCognome() + " " + soggetto.getNome();
                    soggettoProtocolloList.add(FieldExt.create("RAG_SOG", denominazione));
                }

                soggettoProtocollo = (Field[]) soggettoProtocolloList.toArray(new Field[soggettoProtocolloList.size()]);
                soggettiProtocolloList.add(new PriObject("REGPROT_SOGG", soggettoProtocollo, 0));
            }
            Log.PLUGIN.info("Protocollo Genuit: Creati " + documentoProtocollo.getSoggetti().size() + " soggetti.");

            PriObject[] soggettiProtocollo = (PriObject[]) soggettiProtocolloList.toArray(new PriObject[soggettiProtocolloList.size()]);
            docEx.addFields(FieldExt.create("SOGGETTI", soggettiProtocollo));

            Log.PLUGIN.info("Protocollo Genuit: Aggiunti i soggetti al documento.");

            //Allegati
            List<AttachmentPart> attachList = new ArrayList<AttachmentPart>();

            if (documentoProtocollo.getAllegatoOriginale() != null) {
                docEx.addFields(FieldExt.create("FILE_ORIG", createAttach("ORIGIN", documentoProtocollo.getAllegatoOriginale(), attachList)));
            }
            if (documentoProtocollo.getAllegati() != null && !documentoProtocollo.getAllegati().isEmpty()) {
                List<PriObject> allegatiList = new ArrayList<PriObject>();
                for (Allegato allegato : documentoProtocollo.getAllegati()) {
                    PriObject idDocumento = createAttach("ALLEGEN", allegato, attachList);
                    allegatiList.add(idDocumento);
                }
                Log.PLUGIN.info("Protocollo Genuit: Creati " + documentoProtocollo.getAllegati().size() + " allegati.");
                //inserisco file originale nel documento
                PriObject[] allegati = (PriObject[]) allegatiList.toArray(new PriObject[allegatiList.size()]);
                docEx.addFields(FieldExt.create("ALLEGATI", allegati));

                Log.PLUGIN.info("Protocollo Genuit: Aggiunti gli allegati al documento.");
            }

            Log.PLUGIN.trace("Protocollo Genuit: Invio del documento '" + doc.toString() + "'");

//            String xxx = gson.toJson(docEx.getPriObject());
//            String xxx1 = gson.toJson(attachList);
            PRIObjectOut resp = service.PRIProtocollaDoc(priGetSid.getSid(), user, documentoProtocollo.getAnnoFascicolo(), documentoProtocollo.getCodiceFascicolo(), docEx.getPriObject(), attachList);

            Log.PLUGIN.info("Protocollo Genuit: Effettuata chiamata al web service PRIProtocollaDoc.");

            if (resp == null || GenuitUtils.hasError(resp.getReturnCode())) {
                throw new Exception("Protocollo Genuit: Impossibile registrare il documento. Errore: " + (resp == null ? "RESP IS NULL" : GenuitUtils.serializeResponseMessagge(resp.getReturnCode())));
            }
            protocolloResponse = serializeProtocolDocument(resp.getPriObj());
            if (praticaEvento != null && "Ricezione".equals(praticaEvento.getDescrizioneEvento())) {
                pratica.setProtocollo(protocolloResponse.getNumeroProtocollo());
                pratica.setCodRegistro(protocolloResponse.getCodRegistro());
                pratica.setDataProtocollazione(protocolloResponse.getDataProtocollo());
                pratica.setAnnoRiferimento(Integer.parseInt(protocolloResponse.getAnnoProtocollo()));
                praticaDao.update(pratica);
            }
            if (documentoProtocollo.getTipoDoc().equalsIgnoreCase(documentoPratica)) {
                protocolloResponse.setFascicolo(protocolloResponse.getNumeroProtocollo());
            } else {
                protocolloResponse.setFascicolo(documentoProtocollo.getCodiceFascicolo());
            }

            if (Log.PLUGIN.isTraceEnabled()) {
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").setPrettyPrinting().create();
                Log.PLUGIN.trace("REQUEST:\n\n" + gson.toJson(docEx));
                Log.PLUGIN.trace("RESPONSE:\n\n" + gson.toJson(resp));
            }
        } finally {
            if (priGetSid != null && !StringUtils.isEmpty(priGetSid.getSid())) {
                Log.PLUGIN.info("Protocollo Genuit: Richiedo la fine sessione per il sid '" + priGetSid.getSid() + "'.");
                service.PRIEndSession(priGetSid.getSid());
            }
        }
        Log.PLUGIN.info("Protocollo Genuit: Protocollazione terminata.");

        if (pratica.getIdPraticaPadre() != null) {
            //E' un ente terzo, inserisco in pratiche da protocollo il documento così può assegnarlo alla pratica
            PraticheProtocollo praticaProtocollo = serializzaPratica(protocolloResponse);
            //Lo forzo come documento in ricezione
            String documentoEntrata = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_ARRIVO, idEnte, idComune);
            praticaProtocollo.setTipoDocumento(documentoEntrata);
            praticaProtocollo.setMittente(pratica.getIdProcEnte().getIdEnte().getDescrizione());
            praticaProtocollo.setIdMittenteEnte(pratica.getIdProcEnte().getIdEnte());
            it.wego.cross.entity.Pratica praticaPadre = pratica.getIdPraticaPadre();
            praticaProtocollo.setIdUtentePresaInCarico(praticaPadre.getIdUtente());
            //L'anno del fascicolo è quello della pratica padre
            praticaProtocollo.setAnnoFascicolo(pratica.getIdPraticaPadre().getAnnoRiferimento());
            //Setto l'evento dell'ente che ha scatenato la protocollazione
            praticaProtocollo.setIdPraticaEventoSorgente(praticaEvento);
            usefulDao.update(praticaProtocollo);
            usefulDao.flush();
        }
        return protocolloResponse;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="METODO FIND DOCUMENTO BY PROTOCOLLO">

    @Override
    public DocumentoProtocolloResponse findByProtocollo(Integer annoRiferimento, String numeroProtocollo, Enti ente, LkComuni comune) throws Exception {
        List<DocumentoProtocolloResponse> protocolloResponseList = new ArrayList<DocumentoProtocolloResponse>();
        FilterProtocollo filterProtocollo = new FilterProtocollo();
        filterProtocollo.setAnno(String.valueOf(annoRiferimento));
        filterProtocollo.setNumeroProtocollo(numeroProtocollo);
        PriObject documento;
        ProtocolloServiceSoapBindingStub service = getService(ente, comune);

        PRIGetSidResponse priGetSid = null;
        try {
            priGetSid = service.PRIGetSid(user, password);
            if (priGetSid == null || StringUtils.isEmpty(priGetSid.getSid())) {
                throw new Exception("Impossibile stabilire la connessione con il protocollo. Errore: " + (priGetSid == null ? "SID NULL" : GenuitUtils.serializeResponseMessagge(priGetSid.getReturnCode())));
            }

            String query = getQuery(filterProtocollo);
            Log.PLUGIN.debug("Esegui la seguente query al protocollo: " + query);

            String[] fields = new String[]{"ID", "COD_REGISTRO", "ANNO_PROT", "TIPO_DOC", "NUMERO_PROT", "UO", "FASC_PRINC", "DATA_PROT", "OGGETTO", "SOGGETTI", "FILE_ORIG", "ALLEGATI"};
            PRIQueryObjectOut response = service.PRIQuery(priGetSid.getSid(), this.user, "DOCUMENTI", query, fields, filterProtocollo.getPagina());
//            PRIQueryObjectOut response = service.PRIQuery(priGetSid.getSid(), this.user, "DOCUMENTI", query, fields, 1);

            if (Log.PLUGIN.isTraceEnabled()) {
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
                Log.PLUGIN.trace("REQUEST:\n\n" + gson.toJson(fields));
                Log.PLUGIN.trace("RESPONSE:\n\n" + gson.toJson(response));
            }

            PriObject[] documentoArray = response.getPriObj();

            DocumentoProtocolloResponse protocolloResponse;
            for (PriObject documentoArray1 : documentoArray) {
                documento = documentoArray1;
                protocolloResponse = serializeProtocolDocument(documento);
                protocolloResponseList.add(protocolloResponse);
            }
            return protocolloResponseList.get(0);
        } finally {
            service.clearAttachments();
            if (priGetSid != null && !StringUtils.isEmpty(priGetSid.getSid())) {
                service.PRIEndSession(priGetSid.getSid());
            }
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="METODO QUERYPROTOCOLLO">

    @Override
    public List<DocumentoProtocolloResponse> queryProtocollo(FilterProtocollo filterProtocollo, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;
        List<DocumentoProtocolloResponse> protocolloResponseList = new ArrayList<DocumentoProtocolloResponse>();
        String endPointRecuperoPratica = configurationService.getCachedPluginConfiguration(Genuit.WS_RECUPERO_PRATICHE, idEnte, idComune);
        if (StringUtils.isEmpty(endPointRecuperoPratica)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.ENDOPOINT + "'");
        }
        Log.PLUGIN.debug("VALORE INDICATO PER LA PROPRIETA " + Genuit.WS_RECUPERO_PRATICHE + ": " + endPointRecuperoPratica);
        GenuitProtocolloClient client = new GenuitProtocolloClient(endPointRecuperoPratica);
        XMLGregorianCalendar dataInizio = Utils.dateToXmlGregorianCalendar(filterProtocollo.getDataDocumentoDa());
        XMLGregorianCalendar dataFine = Utils.dateToXmlGregorianCalendar(filterProtocollo.getDataDocumentoA());
        List<DettaglioProtocollo> items = client.getDocumentiProtocollo(dataInizio, dataFine);
        if (items != null && !items.isEmpty()) {
            for (DettaglioProtocollo dettaglio : items) {
                String documentoIngresso = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_ARRIVO, idEnte, idComune);
                Log.PLUGIN.debug("Tipo documento in ingresso: " + documentoIngresso);
                String documentoPrincipale = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_PRATICA, idEnte, idComune);
                Log.PLUGIN.debug("Tipo documento pratica: " + documentoPrincipale);
                if (filterProtocollo.getTipoDocumento() != null) {
                    if (filterProtocollo.getTipoDocumento().equals(dettaglio.getTipoDoc())) {
                        Log.PLUGIN.debug("Filtro i documenti di tipo: " + filterProtocollo.getTipoDocumento());
                        DocumentoProtocolloResponse protocolloResponse = serialize(dettaglio, ente, comune);
                        if (protocolloResponse != null) {
                            protocolloResponseList.add(protocolloResponse);
                        }
                    } else {
                        //Skip
                    }
                } else {
                    //Recupero solo quelle dei documenti in ingresso o pratica
                    Log.PLUGIN.debug("Filtro di ricerca non specificato: recupero solo i documenti in ingresso o le pratiche");
                    if (dettaglio.getTipoDoc().equals(documentoIngresso) || dettaglio.getTipoDoc().equals(documentoPrincipale)) {
                        DocumentoProtocolloResponse protocolloResponse = serialize(dettaglio, ente, comune);
                        if (protocolloResponse != null) {
                            protocolloResponseList.add(protocolloResponse);
                        }
                    }
                }

                //Escludo quelle di tipo LET
//                String documentoUscita = genuitService.getConfiguration(Genuit.DOCUMENTO_USCITA);
//                if (protocolloResponse != null && !protocolloResponse.getTipoDocumento().equalsIgnoreCase(documentoUscita)) {
//                    
//                }
            }
        }
        return protocolloResponseList;
    }

    //</editor-fold>
    @Override
    public Protocollo getProtocolloBean(PraticheEventi praticaEvento) throws Exception {
        Protocollo protocolloBean = new Protocollo();
        protocolloBean.setCodiceAmministrazione(praticaEvento.getIdPratica().getIdProcEnte().getIdEnte().getCodiceAmministrazione());
        protocolloBean.setCodiceAoo(praticaEvento.getIdPratica().getIdProcEnte().getIdEnte().getCodiceAoo());
        protocolloBean.setDataRegistrazione(praticaEvento.getDataProtocollo());
        String[] protocolloArray = praticaEvento.getProtocollo().split("/");
        protocolloBean.setNumeroRegistrazione(protocolloArray[protocolloArray.length - 1]);
        protocolloBean.setRegistro(protocolloArray[0]);
        protocolloBean.setAnno(protocolloArray[1]);
        return protocolloBean;
    }

    //<editor-fold defaultstate="collapsed" desc="METODI PRIVATI">
    private PraticheProtocollo serializzaPratica(DocumentoProtocolloResponse doc) throws NumberFormatException {
        PraticheProtocollo pratica = new PraticheProtocollo();
        pratica.setAnnoRiferimento(Integer.valueOf(doc.getAnnoProtocollo()));
        if (!Utils.e(doc.getAnnoFascicolo())) {
            pratica.setAnnoRiferimento(Integer.valueOf(doc.getAnnoFascicolo()));
        }
        pratica.setAnnoFascicolo(pratica.getAnnoRiferimento());
        pratica.setCodRegistro(doc.getCodRegistro());
        pratica.setNFascicolo(doc.getFascicolo());
        pratica.setNProtocollo(doc.getNumeroProtocollo());
        pratica.setOggetto(doc.getOggetto());
        //Utilizzo le informazioni di protocollazione per costruirmi l'identificativo della pratica
        String identificativoPratica = doc.getCodRegistro() + "/" + doc.getAnnoProtocollo() + "/" + doc.getNumeroProtocollo();
        pratica.setIdentificativoPratica(identificativoPratica);
        pratica.setTipoDocumento(doc.getTipoDocumento());
        Date dataRicezione = new Date();
        pratica.setDataRicezione(dataRicezione);
        pratica.setDataProtocollazione(doc.getDataProtocollo());
        pratica.setStato("RICEVUTA");
        pratica.setDestinatario(doc.getDestinatario());
        if (doc.getAllegatoOriginale() != null) {
            pratica.setCodDocumento(doc.getAllegatoOriginale().getIdEsterno());
        }
        pratica.setDataSincronizzazione(new Date());
        return pratica;
    }

    private DocumentoProtocolloResponse serialize(DettaglioProtocollo dettaglio, Enti ente, LkComuni comune) throws Exception {
        Log.PLUGIN.debug("Id dettaglio: " + dettaglio.getId());
        Log.PLUGIN.debug("Id file origine: " + dettaglio.getFileOrig());
        String documentoPrincipale = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_PRATICA, ente != null ? ente.getIdEnte() : null, comune != null ? comune.getIdComune() : null);
        if (dettaglio.getId() != null && dettaglio.getFileOrig() != null && dettaglio.getFileOrig().intValue() > 0) {
            DocumentoProtocolloResponse doc = new DocumentoProtocolloResponse();
            if (dettaglio.getId() != null) {
                doc.setIdDocumento(String.valueOf(dettaglio.getId()));
            }
            Log.PLUGIN.debug("Codice registro: " + dettaglio.getCodregistro());
            doc.setCodRegistro(dettaglio.getCodregistro());
            Log.PLUGIN.debug("Anno protocollo: " + dettaglio.getAnnoProt());
            if (dettaglio.getAnnoProt() != null) {
                doc.setAnnoProtocollo(String.valueOf(dettaglio.getAnnoProt()));
            }
            Log.PLUGIN.debug("Anno fascicolo: " + dettaglio.getAnnoFascicolo());
            if (dettaglio.getAnnoFascicolo() != null) {
                doc.setAnnoFascicolo(String.valueOf(dettaglio.getAnnoFascicolo()));
            }
            Log.PLUGIN.debug("Numero protocollo: " + dettaglio.getNumeroProt());
            if (dettaglio.getNumeroProt() != null) {
                doc.setNumeroProtocollo(String.valueOf(dettaglio.getNumeroProt()));
            }
            Log.PLUGIN.debug("Fascicolo: " + dettaglio.getNumFascicolo());
            doc.setFascicolo(dettaglio.getNumFascicolo());
            Log.PLUGIN.debug("Oggetto: " + dettaglio.getOggetto());
            doc.setOggetto(dettaglio.getOggetto());
            Log.PLUGIN.debug("Tipo documento: " + dettaglio.getTipoDoc());
            doc.setTipoDocumento(dettaglio.getTipoDoc());
            //Setto l'Unità Organizzativa come destinatario
            Log.PLUGIN.debug("UO: " + dettaglio.getUo());
            doc.setDestinatario(dettaglio.getUo());
            if (dettaglio.getDataProt() != null) {
                Log.PLUGIN.debug("Data protocollo: " + dettaglio.getDataProt());
                doc.setDataProtocollo(Utils.xmlGregorianCalendarToDate(dettaglio.getDataProt()));
                if (dettaglio.getTipoDoc().equalsIgnoreCase(documentoPrincipale)) {
                    //Sto trattando una pratica da protocollo. La data di creazione del fascicolo corrisponde a quella di protocollazione
                    doc.setDataCreazioneFascicolo(Utils.xmlGregorianCalendarToDate(dettaglio.getDataProt()));
                }
            }
            String mittente = dettaglio.getEmittente();
            List<SoggettoProtocollo> soggetti = new ArrayList<SoggettoProtocollo>();
            SoggettoProtocollo mittenteDocumento = new SoggettoProtocollo();
            mittenteDocumento.setDenominazione(mittente);
            soggetti.add(mittenteDocumento);
            doc.setSoggetti(soggetti);
            if (dettaglio.getFileOrig() != null) {
                try {
                    Log.PLUGIN.debug("Numero file origine: " + dettaglio.getFileOrig());
                    GestioneAllegati gestioneAllegati = getGestioneAllegatiPlugin(ente, comune);
                    Allegato allegato = gestioneAllegati.getFile(String.valueOf(dettaglio.getFileOrig()), ente, comune);
                    doc.setAllegatoOriginale(allegato);
                } catch (Exception ex) {
                    Log.PLUGIN.error("Non ho trovato il file fisico", ex);
                    Allegato allegato = new Allegato();
                    //Setto comunque l'id esterno
                    allegato.setIdEsterno(String.valueOf(dettaglio.getFileOrig()));
                    doc.setAllegatoOriginale(allegato);
                }
            }
            return doc;
        } else {
            return null;
        }
    }

    private PriObject createAttach(String className, Allegato allegato, List<AttachmentPart> attach) throws Exception {
        if (allegato != null) {
            ByteArrayDataSource byteDataSource = new ByteArrayDataSource(allegato.getFile(), !Strings.isNullOrEmpty(allegato.getTipoFile()) ? allegato.getTipoFile() : "application/octet-stream");
            DataHandler dh = new DataHandler(byteDataSource);
            AttachmentPart ap = new AttachmentPart(dh);
            attach.add(ap);

//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] thedigest = md.digest(allegato.getFile());
            String descrizione = allegato.getDescrizione() != null && !allegato.getDescrizione().equals("") ? allegato.getDescrizione() : Genuit.DOCUMENTO_SENZA_DESCRIZIONE;
            Field[] fdlr = new Field[]{
                new Field("FILE_NAME", new Object[]{allegato.getNomeFile()}),
                new Field("FILE_SALVATO", new Object[]{ap.getContentId()}),
                new Field("DESCRIZIONE", new Object[]{descrizione})
//            new Field("FILE_SIZE", new Object[]{allegato.getFile().length}),                    
//            new Field("HASH", new Object[]{new String(thedigest, "UTF-8")}),
//            new Field("IDFILE", new Object[]{ap.getContentId()})                    
            };

            return new PriObject(className, fdlr, 0);
        }
        return null;
    }

    private DocumentoProtocolloResponse serializeProtocolDocument(PriObject protocolDocument) {
        Object[] objArray;
        PriObject file;

        Field[] responseFields = protocolDocument.getFields();

        DocumentoProtocolloResponse documentoProtocolloResponse = new DocumentoProtocolloResponse();
        documentoProtocolloResponse.setIdDocumento(GenuitUtils.getFieldValue(responseFields, "id")[0].toString());
        documentoProtocolloResponse.setCodRegistro(GenuitUtils.getFieldValue(responseFields, "COD_REGISTRO")[0].toString());
        documentoProtocolloResponse.setAnnoProtocollo(GenuitUtils.getFieldValue(responseFields, "ANNO_PROT")[0].toString());
        documentoProtocolloResponse.setNumeroProtocollo(GenuitUtils.getFieldValue(responseFields, "NUMERO_PROT")[0].toString());
        documentoProtocolloResponse.setOggetto(GenuitUtils.getFieldValue(responseFields, "OGGETTO")[0].toString());
        //In che casi potrebbe essere nullo?
        if (GenuitUtils.getFieldValue(responseFields, "TIPO_DOC") != null) {
            documentoProtocolloResponse.setTipoDocumento(GenuitUtils.getFieldValue(responseFields, "TIPO_DOC")[0].toString());
        }

        if (GenuitUtils.getFieldValue(responseFields, "UO") != null) {
            //Setto l'Unità Organizzativa come destinatario
            documentoProtocolloResponse.setDestinatario(GenuitUtils.getFieldValue(responseFields, "UO")[0].toString());
        }

//        Object[] fascObjArray = getFieldValue(responseFields, "FASC_PRINC");
//        if (fascObjArray != null && fascObjArray.length > 0) {
//            documentoProtocolloResponse.setFascicolo(fascObjArray[0].toString());
//        }
        Object dataProtocolloObject = GenuitUtils.getFieldValue(responseFields, "DATA_PROT")[0];
        if (dataProtocolloObject instanceof GregorianCalendar) {
            GregorianCalendar dataProtocollo = (GregorianCalendar) dataProtocolloObject;
            documentoProtocolloResponse.setDataProtocollo(dataProtocollo.getTime());
            //Su genuit la data di creazione del fascicolo corrisponde a quella di protocollazione
            documentoProtocolloResponse.setDataCreazioneFascicolo(dataProtocollo.getTime());
        } else {
            Date dataProtocollo = (Date) dataProtocolloObject;
            documentoProtocolloResponse.setDataProtocollo(dataProtocollo);
            //Su genuit la data di creazione del fascicolo corrisponde a quella di protocollazione
            documentoProtocolloResponse.setDataCreazioneFascicolo(dataProtocollo);
        }

        Object[] filesOrigArray = (Object[]) GenuitUtils.getFieldValue(responseFields, "FILE_ORIG");
        if (filesOrigArray != null && filesOrigArray.length > 0) {
            file = (PriObject) filesOrigArray[0];

            Allegato allegatoOriginale = new Allegato();

            objArray = GenuitUtils.getFieldValue(file.getFields(), "ID");
            if (objArray != null && objArray.length > 0) {
                allegatoOriginale.setIdEsterno(objArray[0].toString());
            }

            objArray = GenuitUtils.getFieldValue(file.getFields(), "FILE_NAME");
            if (objArray != null && objArray.length > 0) {
                allegatoOriginale.setNomeFile(objArray[0].toString());
            }

            objArray = GenuitUtils.getFieldValue(file.getFields(), "DESCRIZIONE");
            if (objArray != null && objArray.length > 0 && objArray[0].toString() != null && !objArray[0].toString().equals("")) {
                allegatoOriginale.setDescrizione(objArray[0].toString());
            } else {
                allegatoOriginale.setDescrizione(Genuit.DOCUMENTO_SENZA_DESCRIZIONE);
            }

            objArray = GenuitUtils.getFieldValue(file.getFields(), "CONTENT_TYPE");
            if (objArray != null && objArray.length > 0) {
                allegatoOriginale.setTipoFile(objArray[0].toString());
            }

            documentoProtocolloResponse.setAllegatoOriginale(allegatoOriginale);
        }

        Object[] filesArray = (Object[]) GenuitUtils.getFieldValue(responseFields, "ALLEGATI");
        List<Allegato> allegati = new ArrayList<Allegato>();
        Allegato allegato;
        for (Object filesArray1 : filesArray) {
            file = (PriObject) filesArray1;
            allegato = new Allegato();
            objArray = GenuitUtils.getFieldValue(file.getFields(), "ID");
            if (objArray != null && objArray.length > 0) {
                allegato.setIdEsterno(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "FILE_NAME");
            if (objArray != null && objArray.length > 0) {
                allegato.setNomeFile(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "DESCRIZIONE");
            if (objArray != null && objArray.length > 0 && objArray[0].toString() != null && !objArray[0].toString().equals("")) {
                allegato.setDescrizione(objArray[0].toString());
            } else {
                allegato.setDescrizione(Genuit.DOCUMENTO_SENZA_DESCRIZIONE);
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "CONTENT_TYPE");
            if (objArray != null && objArray.length > 0) {
                allegato.setTipoFile(objArray[0].toString());
            }
            allegati.add(allegato);
        }
        documentoProtocolloResponse.setAllegati(allegati);

        Object[] soggettiArray = (Object[]) GenuitUtils.getFieldValue(responseFields, "SOGGETTI");
        List<SoggettoProtocollo> soggetti = new ArrayList<SoggettoProtocollo>();
        SoggettoProtocollo soggetto;
        for (Object soggettiArray1 : soggettiArray) {
            file = (PriObject) soggettiArray1;
            soggetto = new SoggettoProtocollo();
            objArray = GenuitUtils.getFieldValue(file.getFields(), "CODE_SOGG");
            if (objArray != null && objArray.length > 0) {
                soggetto.setCodice(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "RAG_SOG");
            if (objArray != null && objArray.length > 0) {
                soggetto.setDenominazione(objArray[0].toString());
                //Valorizza anche il campo cognome con la denominazione. Non ho modo di capire se è persona fisica o giuridica
                soggetto.setCognome(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "INDIRIZZO");
            if (objArray != null && objArray.length > 0) {
                soggetto.setIndirizzo(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "MEZZO");
            if (objArray != null && objArray.length > 0) {
                soggetto.setMezzo(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "TIPO");
            if (objArray != null && objArray.length > 0) {
                soggetto.setTipo(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "COD_FISC");
            if (objArray != null && objArray.length > 0) {
                soggetto.setCodiceFiscale(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "PART_IVA");
            if (objArray != null && objArray.length > 0) {
                soggetto.setPartitaIva(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "CITTA");
            if (objArray != null && objArray.length > 0) {
                soggetto.setCitta(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "CAP");
            if (objArray != null && objArray.length > 0) {
                soggetto.setCap(objArray[0].toString());
            }
            objArray = GenuitUtils.getFieldValue(file.getFields(), "EMAIL");
            if (objArray != null && objArray.length > 0) {
                soggetto.setMail(objArray[0].toString());
            }
            Log.PLUGIN.debug("CERCO DATA DI NASCITA");
            objArray = GenuitUtils.getFieldValue(file.getFields(), "DATA_NAS");
            if (objArray != null && objArray.length > 0) {
                Log.PLUGIN.debug("DATA DI NASCITA: " + objArray[0].toString());
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date dataNascita = null;
                try {
                    dataNascita = df.parse(objArray[0].toString());
                } catch (ParseException ex) {
                    Log.PLUGIN.warn("La data di nascita non rispetta il seguente pattern: dd/MM/yyyy");
                }
                soggetto.setDataNascita(dataNascita);
            }
            Log.PLUGIN.debug("CERCO COMUNE DI NASCITA");
            objArray = GenuitUtils.getFieldValue(file.getFields(), "COMUNE_NAS");
            if (objArray != null && objArray.length > 0) {
                Log.PLUGIN.debug("COMUNE DI NASCITA: " + objArray[0].toString());
                soggetto.setComuneNascita(objArray[0].toString());
            }
            Log.PLUGIN.debug("CERCO CITTADINANZA");
            objArray = GenuitUtils.getFieldValue(file.getFields(), "CITTADINANZA");
            if (objArray != null && objArray.length > 0) {
                Log.PLUGIN.debug("CITTADINANZA: " + objArray[0].toString());
                soggetto.setCittadinanza(objArray[0].toString());
            }
            soggetti.add(soggetto);
        }
        documentoProtocolloResponse.setSoggetti(soggetti);

        return documentoProtocolloResponse;
    }

    //</editor-fold>
    @Override
    public DocumentoProtocolloRequest getDocumentoProtocolloDaXml(Pratica praticaCross, String verso) throws Exception {
        DocumentoProtocolloRequest protocolloRequest = new DocumentoProtocolloRequest();

        Enti ente = entiDao.findByIdEnte(praticaCross.getIdEnte().intValue());
        LkComuni comune = lookupDao.findComuneByCodCatastale(praticaCross.getCodCatastaleComune());

        List<SoggettoProtocollo> soggettiProtocollo = new ArrayList<SoggettoProtocollo>();
        for (it.wego.cross.xml.Anagrafiche a : praticaCross.getAnagrafiche()) {
            it.wego.cross.xml.Anagrafica anagrafica = a.getAnagrafica();
            SoggettoProtocollo soggetto = new SoggettoProtocollo();
            soggetto.setCodiceFiscale(anagrafica.getCodiceFiscale());
            if (!Utils.e(anagrafica.getDenominazione())) {
                soggetto.setDenominazione(anagrafica.getDenominazione());
            } else {
                soggetto.setDenominazione(anagrafica.getCognome() + " " + anagrafica.getNome());
            }
            soggetto.setPartitaIva(anagrafica.getPartitaIva());
            if (anagrafica.getRecapiti() != null && anagrafica.getRecapiti().getRecapito() != null) {
                it.wego.cross.xml.Recapito ufficiale = null;
                for (it.wego.cross.xml.Recapito recapito : anagrafica.getRecapiti().getRecapito()) {
                    //Non considero recapiti senza tipo di indirizzo
                    if (recapito.getIdTipoIndirizzo() != null) {
                        LkTipoIndirizzo tipoIndirizzo = lookupDao.findTipoIndirizzoById(recapito.getIdTipoIndirizzo().intValue());
                        if (tipoIndirizzo.getDescrizione().equals(Genuit.TIPODOMICILIO)) {
                            ufficiale = recapito;
                            break;
                        } else {
                            ufficiale = recapito;
                        }
                    }
                }
                if (ufficiale != null) {
                    soggetto.setCap(ufficiale.getCap());
                    soggetto.setCitta(ufficiale.getDesComune());
                    soggetto.setIndirizzo(ufficiale.getIndirizzo());
                    if (ufficiale.getPec() != null) {
                        soggetto.setMail(ufficiale.getPec());
                    } else {
                        soggetto.setMail(ufficiale.getEmail());
                    }
                }
            }
//                    soggetto.setMezzo(data);
//                    soggetto.setTipo(data);
            soggettiProtocollo.add(soggetto);
        }
        protocolloRequest.setSoggetti(soggettiProtocollo);
        List<it.wego.cross.plugins.commons.beans.Allegato> allegatiProtocollo = new ArrayList<it.wego.cross.plugins.commons.beans.Allegato>();
        it.wego.cross.plugins.commons.beans.Allegato allegatoOriginale = new it.wego.cross.plugins.commons.beans.Allegato();
        for (it.wego.cross.xml.Allegato a : praticaCross.getAllegati().getAllegato()) {
            it.wego.cross.plugins.commons.beans.Allegato allegato = new it.wego.cross.plugins.commons.beans.Allegato();
            allegato.setDescrizione(a.getDescrizione());
            //Creo un file temporaneo su disco
            File file = new File(a.getPathFile());
            byte[] fileSuDisco = FileUtils.getFileContent(file);
            allegato.setFile(fileSuDisco);
            allegato.setNomeFile(a.getNomeFile());
            if (a.getRiepilogoPratica().equalsIgnoreCase("S")) {
                allegatoOriginale = allegato;
            } else {
                allegatiProtocollo.add(allegato);
            }
            //Cancello il file temporaneo creato
            file.delete();
        }
        protocolloRequest.setAllegatoOriginale(allegatoOriginale);
        protocolloRequest.setAllegati(allegatiProtocollo);
        //Anno corrente
        protocolloRequest.setAnnoFascicolo(Calendar.getInstance().get(Calendar.YEAR));
        protocolloRequest.setAoo(aoo);
        //E per documenti in entrata, U per i documenti in uscita
        if (verso.equalsIgnoreCase(Genuit.PROTOCOLLO_INPUT)) {
            protocolloRequest.setDirezione("E");
        } else {
            protocolloRequest.setDirezione("U");
        }
        //Oggetto della pratica
        protocolloRequest.setOggetto(praticaCross.getOggetto());
        //Fisso a PORTALE CROSS
        protocolloRequest.setSource("PORTALE CROSS");
        //Valori ammessi:
        //DOCARR: Documento generico in arrivo
        //LET: Lettera in uscita
        //UNICO: Procedimento Unico
        String documentoPratica = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_PRATICA, ente.getIdEnte(), (comune == null) ? null : comune.getIdComune());
        protocolloRequest.setTipoDoc(documentoPratica);
        protocolloRequest.setUo(ente.getUnitaOrganizzativa());
        //Fisso a 11
        protocolloRequest.setTitolario("11");
        //Lo prende dal procedimento. In caso di procedimenti multipli prende il procedimento di peso maggiore
        Procedimenti proc = null;
        if (praticaCross.getProcedimenti() != null && praticaCross.getProcedimenti().getProcedimento() != null) {
            List<it.wego.cross.xml.Procedimento> procedimenti = praticaCross.getProcedimenti().getProcedimento();
            for (it.wego.cross.xml.Procedimento p : procedimenti) {
                Procedimenti procedimento = procedimentiService.findProcedimentoByCodProc(p.getCodProcedimento());
                if (proc != null) {
                    if (proc.getPeso() < procedimento.getPeso()) {
                        proc = procedimento;
                    }
                } else {
                    proc = procedimento;
                }
            }
        }
        if (proc != null) {
            protocolloRequest.setClassifica(proc.getClassifica());
        }
        return protocolloRequest;
    }

    @Override
    public DocumentoProtocolloRequest getDocumentoProtocolloDaDatabase(it.wego.cross.entity.Pratica pratica, PraticheEventi praticaEvento, List<Allegato> allegatiNuovi, List<SoggettoProtocollo> soggettiProtocollo, String verso, String oggetto) throws Exception {
        DocumentoProtocolloRequest protocollo = new DocumentoProtocolloRequest();
        protocollo.setIdPratica(pratica != null ? pratica.getIdPratica() : null);
        protocollo.setIdPraticaEvento(praticaEvento != null ? praticaEvento.getIdPraticaEvento() : null);

        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();

        protocollo.setSoggetti(soggettiProtocollo);
        protocollo.setIdentificativoPratica(pratica.getIdentificativoPratica());
        List<it.wego.cross.plugins.commons.beans.Allegato> allegatiProtocollo = new ArrayList<it.wego.cross.plugins.commons.beans.Allegato>();
        for (Allegato a : allegatiNuovi) {
            if (a.getProtocolla()) {
                it.wego.cross.plugins.commons.beans.Allegato allegato = new it.wego.cross.plugins.commons.beans.Allegato();
                allegato.setDescrizione(a.getDescrizione());
                byte[] fileSuDisco;
                if (a.getPathFile() != null) {
                    fileSuDisco = FileUtils.getFileContent(new File(a.getPathFile()));
                } else if (a.getIdEsterno() != null) {
                    GestioneAllegati gestioneAllegati = getGestioneAllegatiPlugin(ente, comune);
                    it.wego.cross.plugins.commons.beans.Allegato all = gestioneAllegati.getFile(a.getIdEsterno(), pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
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
        protocollo.getAllegatoOriginale();
        protocollo.setAllegati(allegatiProtocollo);

        //E per documenti in entrata, U per i documenti in uscita
        if (verso.equalsIgnoreCase(Genuit.PROTOCOLLO_INPUT)) {
            protocollo.setDirezione("E");
            String documentoEntrata = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_ARRIVO, ente.getIdEnte(), comune.getIdComune());
            protocollo.setTipoDoc(documentoEntrata);
        } else {
            protocollo.setDirezione("U");
            String documentoUscita = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_USCITA, ente.getIdEnte(), comune.getIdComune());
            protocollo.setTipoDoc(documentoUscita);
        }

        //Anno corrente
        Integer annoFascicolo = pratica.getAnnoRiferimento();
        if (annoFascicolo == null) {
            //La pratica è nuova, quindi setto come anno fascicolo l'anno corrente
            annoFascicolo = Calendar.getInstance().get(Calendar.YEAR);
            //Poiché si tratta di una pratica nuova faccio l'override del tipo documento
            String documentoPratica = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_PRATICA, ente.getIdEnte(), comune.getIdComune());
            protocollo.setTipoDoc(documentoPratica);
        }
        protocollo.setAnnoFascicolo(annoFascicolo);
        protocollo.setAoo(aoo);
        //Oggetto della pratica
        protocollo.setOggetto(oggetto);
        //Fisso a PORTALE CROSS
        protocollo.setSource("PORTALE CROSS");
        //Valori ammessi:
        //DOCARR: Documento generico in arrivo
        //LET: Lettera in uscita
        //UNICO: Procedimento Unico

        //Fisso a 11
        protocollo.setTitolario("11");
        Procedimenti proc = null;
        if (pratica.getPraticaProcedimentiList() != null && !pratica.getPraticaProcedimentiList().isEmpty()) {
            for (PraticaProcedimenti pp : pratica.getPraticaProcedimentiList()) {
                Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(pp.getPraticaProcedimentiPK().getIdProcedimento());
                if (proc != null) {
                    if (proc.getPeso() < procedimento.getPeso()) {
                        proc = procedimento;
                    }
                } else {
                    proc = procedimento;
                }
            }
        } else {
            proc = pratica.getIdProcEnte().getIdProc();
        }
        if (proc != null) {
            protocollo.setClassifica(proc.getClassifica());
        }
        //La pratica è stata già protocollata
        if (!Strings.isNullOrEmpty(pratica.getProtocollo())) {
            protocollo.setCodiceFascicolo(pratica.getProtocollo());
        }
        protocollo.setUo(ente.getUnitaOrganizzativa());

        //Gestione caso di protocollazione da ente terzo. Se è ENTE ESTERNO LA PROTOCOLLO COME DOCARR
        if (pratica.getIdPraticaPadre() != null && pratica.getIdProcEnte().getIdEnte().getTipoEnte().equalsIgnoreCase("ENTE ESTERNO")) {
            protocollo.setDirezione("E");
            String documentoEntrata = configurationService.getCachedPluginConfiguration(Genuit.DOCUMENTO_ARRIVO, ente.getIdEnte(), comune.getIdComune());
            protocollo.setTipoDoc(documentoEntrata);
            ente = pratica.getIdPraticaPadre().getIdProcEnte().getIdEnte();
            protocollo.setUo(ente.getUnitaOrganizzativa());
            // Valorizzo con il fascicolo della pratica padre
            protocollo.setCodiceFascicolo(pratica.getIdPraticaPadre().getProtocollo());
            List<SoggettoProtocollo> enteTerzoList = new ArrayList<SoggettoProtocollo>();
            Enti enteTerzo = pratica.getIdProcEnte().getIdEnte();
            SoggettoProtocollo soggetto = getSoggettoEnteTerzo(enteTerzo);
            enteTerzoList.add(soggetto);
            protocollo.setSoggetti(enteTerzoList);
        }
        if (Utils.e(protocollo.getUo())) {
            throw new Exception("Impossibile protocollare con unità organizzativa nulla");
        }

        return protocollo;
    }

    public String getQuery(FilterProtocollo filter) {
        List<String> predicates = new ArrayList<String>();

        if (!StringUtils.isEmpty(filter.getNumeroProtocollo())) {
            predicates.add("NUMERO_PROT:" + filter.getNumeroProtocollo());
        }
        if (!StringUtils.isEmpty(filter.getAnno())) {
            predicates.add("ANNO_PROT:" + filter.getAnno());
        }
        if (!StringUtils.isEmpty(filter.getTipoDocumento())) {
            predicates.add("TIPO_DOC:" + filter.getTipoDocumento());
        }
        if (filter.getDataDocumentoDa() != null && filter.getDataDocumentoA() != null) {
            predicates.add("DATA_PROT:[" + qq(filter.getDataDocumentoDa()) + " TO " + qq(filter.getDataDocumentoA()) + "]");

//            predicates.add("DATA_PROT:" + "[03/12/2012 TO 04/12/2012]");
//            predicates.add("DATA_PROT:" + "[20121203000000 TO 20121204000000]");
//            predicates.add("DATA_PROT:" + "'20121203000000'");
//            predicates.add("DATA_PROT:" + "(year:2012 AND month:12 AND day:03) OR (year:2012 AND month:12 AND day:04)");
//            predicates.add("DATA_PROT:" + "(year:2012)");
//            predicates.add("DATA_PROT:" + "date[20121203 TO 20121204] ");
//            predicates.add("DATA_PROT:" + "date[20121203] ");
//            DateTools.dateToString(date, Resolution.SECOND)
        }

        if (filter.getDataDocumentoDa() != null && filter.getDataDocumentoA() == null) {
            predicates.add("DATA_PROT:[" + qq(filter.getDataDocumentoDa()) + " TO " + qq(Calendar.getInstance().getTime()) + "]");
        }

        String query = org.apache.commons.lang.StringUtils.join(predicates, " AND ");
        return query;
    }

    public String qq(Date vl) {
        if (vl == null) {
            return "null";
        }
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(vl);
    }

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

    private SoggettoProtocollo getSoggettoEnteTerzo(Enti enteTerzo) {
        SoggettoProtocollo soggetto = new SoggettoProtocollo();
        soggetto.setCap(String.valueOf(enteTerzo.getCap()));
        soggetto.setCitta(enteTerzo.getCitta());
        soggetto.setDenominazione(enteTerzo.getDescrizione());
        soggetto.setTipoPersona("G");
        soggetto.setIndirizzo(enteTerzo.getIndirizzo());
        soggetto.setMail(enteTerzo.getEmail());
        return soggetto;
    }

    private GestioneAllegati getGestioneAllegatiPlugin(Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;
        String allegatiPluginBeanId = configurationService.getCachedConfiguration(SessionConstants.DOCUMENTI_PLUGIN_ID, idEnte, idComune);
        if (StringUtils.isEmpty(allegatiPluginBeanId)) {
            throw new Exception("Parametro '" + SessionConstants.DOCUMENTI_PLUGIN_ID + "' non valorizzato nella tabella configuration.");
        }

        GestioneAllegati gestioneAllegati = (GestioneAllegati) appContext.getBean(allegatiPluginBeanId);
        return gestioneAllegati;
    }

}
