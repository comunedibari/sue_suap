/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo;

// <editor-fold defaultstate="collapsed" desc="Import">
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import it.wego.cross.avbari.constants.AvBariConstants;
import it.wego.cross.avbari.linksmt.fascicolo.CreazioneFascicolo;
import it.wego.cross.avbari.linksmt.fascicolo.CreazioneFascicoloResponse;
import it.wego.cross.avbari.linksmt.fascicolo.FascicoloServer;
import it.wego.cross.avbari.linksmt.fascicolo.FascicoloServerImplService;
import it.wego.cross.avbari.linksmt.protocollo.ContattoDestinatario;
import it.wego.cross.avbari.linksmt.protocollo.Destinatario;
import it.wego.cross.avbari.linksmt.protocollo.Documento;
import it.wego.cross.avbari.linksmt.protocollo.Mittente;
import it.wego.cross.avbari.linksmt.protocollo.PersonaFisica;
import it.wego.cross.avbari.linksmt.protocollo.PersonaGiuridica;
import it.wego.cross.avbari.linksmt.protocollo.ProtocolloServer;
import it.wego.cross.avbari.linksmt.protocollo.ProtocolloServerImplService;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocollo;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocollo.ProtocolloRequest;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloFasc;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloFascResponse;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloResponse;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaFasc;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaFasc.ProtocolloUscitaFascRequest;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaFascResponse;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.serializer.ProtocolloSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Pratica;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
// </editor-fold>

/**
 *
 * @author Gabriele
 */
@Repository
public class GestioneProtocolloAvBari implements GestioneProtocollo {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private PraticheService praticheService;

    @Override

    public DocumentoProtocolloRequest getDocumentoProtocolloDaDatabase(it.wego.cross.entity.Pratica pratica, PraticheEventi praticaEvento, List<Allegato> allegatiNuovi, List<SoggettoProtocollo> soggettiProtocollo, String verso, String oggetto) throws Exception {

        Preconditions.checkNotNull(pratica, "Impossibile creare una registrazione di protocollo su una pratica inesistente");
        Preconditions.checkNotNull(praticaEvento, "Impossibile creare una registrazione di protocollo su un evento inesistente");

        DocumentoProtocolloRequest documentoProtocolloRequest = new DocumentoProtocolloRequest();

        documentoProtocolloRequest.setSoggetti(ricalcolaSoggetti(pratica, praticaEvento, soggettiProtocollo, verso));

        documentoProtocolloRequest.setAllegatoOriginale(Iterables.filter(allegatiNuovi, new Predicate<Allegato>() {
            @Override
            public boolean apply(Allegato allegato) {
                return allegato.getFileOrigine();
            }
        }).iterator().next());
        documentoProtocolloRequest.setAllegati(allegatiNuovi);

        //E per documenti in entrata, U per i documenti in uscita
        if (verso.equalsIgnoreCase(AvBariConstants.PROTOCOLLO_INPUT)) {
            documentoProtocolloRequest.setDirezione(AvBariConstants.PROTOCOLLO_INPUT);
        } else {
            documentoProtocolloRequest.setDirezione(AvBariConstants.PROTOCOLLO_OUTPUT);
        }

        Enti ente = pratica.getIdProcEnte().getIdEnte();
        documentoProtocolloRequest.setAoo(ente.getCodiceAoo());
        documentoProtocolloRequest.setAmministrazione(ente.getCodiceAmministrazione());
        //Oggetto della pratica
        documentoProtocolloRequest.setOggetto(oggetto);
        //Fisso a PORTALE CROSS
        documentoProtocolloRequest.setSource("PORTALE CROSS");
        documentoProtocolloRequest.setIdEnte(ente.getIdEnte());
        documentoProtocolloRequest.setIdComune(pratica.getIdComune().getIdComune());

        documentoProtocolloRequest.setCodiceFascicolo(pratica.getProtocollo());
        Integer idEnte = ente.getIdEnte();
        Integer idComune = pratica.getIdComune().getIdComune();
        String classifica = configurationService.getCachedPluginConfiguration(AvBariConstants.CLASSIFICAZIONE, idEnte, idComune);
        if (Strings.isNullOrEmpty(classifica)) {
            documentoProtocolloRequest.setClassifica(pratica.getIdProcEnte().getIdProc().getClassifica());
        } else {
            documentoProtocolloRequest.setClassifica(classifica);
        }

        return documentoProtocolloRequest;
    }

    @Override
    public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
        String fascicolo = documentoProtocollo.getCodiceFascicolo();
        DocumentoProtocolloResponse documentoProtocolloResponse = null;
        it.wego.cross.entity.Pratica pratica = null;
        if (Strings.isNullOrEmpty(fascicolo)) {
            fascicolo = protocollaCreaFascicolo(documentoProtocollo);
            // aggiorno il fascicolo sulla pratica
            Integer idPratica = documentoProtocollo.getIdPratica();
            pratica = praticheService.getPratica(idPratica);
//            pratica.setProtocollo(fascicolo);
//            pratica.setAnnoRiferimento(Calendar.getInstance().get(Calendar.YEAR));
//
//            praticheService.aggiornaPratica(pratica);
            documentoProtocollo.setCodiceFascicolo(fascicolo);
        }

        if (documentoProtocollo.getDirezione().equals(AvBariConstants.PROTOCOLLO_INPUT)) {
            documentoProtocolloResponse = protocollaIngressoFasc(documentoProtocollo);
        } else {
            documentoProtocolloResponse = protocollaUscitaFasc(documentoProtocollo);
        }
        if (pratica != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(documentoProtocolloResponse.getDataProtocollo());
            pratica.setDataProtocollazione(documentoProtocolloResponse.getDataProtocollo());
            pratica.setProtocollo(documentoProtocolloResponse.getFascicolo());
            pratica.setAnnoRiferimento(c.get(Calendar.YEAR));
            pratica.setCodRegistro(documentoProtocolloResponse.getCodRegistro());
            praticheService.aggiornaPratica(pratica);
        }
        return documentoProtocolloResponse;
    }

    @Override
    public Protocollo getProtocolloBean(PraticheEventi praticaEvento) throws Exception {
        String numeroProtocollo = praticaEvento.getProtocollo();
        Protocollo protocollo = null;
        if (numeroProtocollo != null && !numeroProtocollo.isEmpty()) {
            String[] splitted = numeroProtocollo.split("/");
            if (splitted.length == 3) {
                protocollo = new Protocollo();
                protocollo.setCodiceAoo(splitted[0]);
                protocollo.setAnno(splitted[1]);
                protocollo.setNumeroRegistrazione(splitted[2]);
                protocollo.setRegistro(splitted[0]);
                protocollo.setDataRegistrazione(praticaEvento.getDataEvento());
            }
        }
        return protocollo;
    }

    // <editor-fold defaultstate="collapsed" desc="Metodi non implementati nel plugin vista l'assenza della funzionalitÃ  di 'Importa documenti da protocollo'">
    @Override
    public DocumentoProtocolloResponse findByProtocollo(Integer annoRiferimento, String numeroProtocollo, Enti ente, LkComuni comune) throws Exception {
        return null;
    }

    @Override
    public List<DocumentoProtocolloResponse> queryProtocollo(FilterProtocollo filterProtocollo, Enti ente, LkComuni comune) throws Exception {
        return new ArrayList<DocumentoProtocolloResponse>();
    }

    @Override
    @Deprecated
    public DocumentoProtocolloRequest getDocumentoProtocolloDaXml(Pratica praticaCross, String verso) throws Exception {
        return null;
    }

    @Override
    public String getTipologiaDocumentoPerPratica(Integer idEnte) {
        return null;
    }

    @Override
    public String getTipologiaDocumentoArrivo(Integer idEnte) {
        return null;
    }
    // </editor-fold>

    private DocumentoProtocolloResponse protocollaIngressoFasc(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
        Integer idEnte = documentoProtocollo.getIdEnte();
        Integer idComune = documentoProtocollo.getIdComune();

        String endPointServizioProtocollo = configurationService.getCachedPluginConfiguration(AvBariConstants.WS_PROTOCOLLO_ENDPOINT, idEnte, idComune);
        if (StringUtils.isEmpty(endPointServizioProtocollo)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.WS_PROTOCOLLO_ENDPOINT + "'");
        }
        Log.PLUGIN.debug("VALORE INDICATO PER LA PROPRIETA " + AvBariConstants.WS_PROTOCOLLO_ENDPOINT + ": " + endPointServizioProtocollo);

        String stringUtenteProtocollazione = configurationService.getCachedPluginConfiguration(AvBariConstants.ID_UTENTE_PROTOCOLLATORE, idEnte, idComune);
        if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.ID_UTENTE_PROTOCOLLATORE + "'");
        }
        Integer utenteProtocollazione = Integer.valueOf(stringUtenteProtocollazione);

        String amministrazione = documentoProtocollo.getAmministrazione();
        if (StringUtils.isEmpty(amministrazione)) {
            throw new Exception("Manca il valore sulla tabella enti il codice amministrazione ");
        }

        if (StringUtils.isEmpty(documentoProtocollo.getAoo())) {
            throw new Exception("Manca il valore sulla tabella enti relativamente all'Unita' organizzaztiva ");
        }

        String username = configurationService.getCachedPluginConfiguration(AvBariConstants.PROTOCOLLO_USERNAME, idEnte, idComune);
        if (StringUtils.isEmpty(username)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.PROTOCOLLO_USERNAME + "'");
        }
        String password = configurationService.getCachedPluginConfiguration(AvBariConstants.PROTOCOLLO_PASSWORD, idEnte, idComune);
        if (StringUtils.isEmpty(password)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.PROTOCOLLO_PASSWORD + "'");
        }

        ProtocolloServerImplService service = new ProtocolloServerImplService();
        ProtocolloServer port = service.getProtocolloServerImplPort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointServizioProtocollo);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(username));
        headers.put("Password", Collections.singletonList(password));
        ((BindingProvider) port).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        RichiestaProtocolloFasc.ProtocolloFascRequest protocolloRequest = new RichiestaProtocolloFasc.ProtocolloFascRequest();

        protocolloRequest.setAmministrazione(amministrazione);
        protocolloRequest.setAreaOrganizzativaOmogenea(documentoProtocollo.getAoo());
        protocolloRequest.setDocumento(new Documento());
        protocolloRequest.setIdUtente(utenteProtocollazione);
        protocolloRequest.setMittente(new Mittente());
        protocolloRequest.setOggetto(documentoProtocollo.getOggetto());
        protocolloRequest.setIdFascicolo(documentoProtocollo.getCodiceFascicolo());
        protocolloRequest.setIdTitolario(documentoProtocollo.getClassifica());
        Log.PLUGIN.debug("Amministrazione : " + amministrazione);
        Log.PLUGIN.debug("Aoo : " + documentoProtocollo.getAoo());
        Log.PLUGIN.debug("Utente : " + utenteProtocollazione);
        Log.PLUGIN.debug("Oggetto : " + documentoProtocollo.getOggetto());
        Log.PLUGIN.debug("Fascicolo : " + documentoProtocollo.getCodiceFascicolo());
        Log.PLUGIN.debug("Titolario : " + documentoProtocollo.getClassifica());

//        protocolloRequest.getMittente().setComune("");
//        protocolloRequest.getMittente().setEmail("");
//        protocolloRequest.getMittente().setIndirizzo("");
//        protocolloRequest.getMittente().setNazione("");
//        protocolloRequest.getMittente().setPecEmail("");
        SoggettoProtocollo mittente = null;
        for (SoggettoProtocollo s : documentoProtocollo.getSoggetti()) {
            if (s.getTipo().equals(AvBariConstants.SOGGETTO_MITTENTE)) {
                mittente = s;
                break;
            }
        }
        if (mittente.getTipoPersona().equals(Constants.PERSONA_FISICA)) {
            protocolloRequest.getMittente().setPersonaFisica(new PersonaFisica());
            protocolloRequest.getMittente().getPersonaFisica().setCodiceFiscale(mittente.getCodiceFiscale());
            protocolloRequest.getMittente().getPersonaFisica().setCognome(mittente.getCognome());
            protocolloRequest.getMittente().getPersonaFisica().setNome(mittente.getNome());
        }
        if (mittente.getTipoPersona().equals(Constants.PERSONA_GIURIDICA)) {
            protocolloRequest.getMittente().setPersonaGiuridica(new PersonaGiuridica());
            protocolloRequest.getMittente().getPersonaGiuridica().setPartitaIVA(mittente.getPartitaIva());
            if (Strings.isNullOrEmpty(mittente.getDenominazione())) {
                protocolloRequest.getMittente().getPersonaGiuridica().setRagioneSociale(mittente.getCognome() + " " + mittente.getNome());
            } else {
                protocolloRequest.getMittente().getPersonaGiuridica().setRagioneSociale(mittente.getDenominazione());
            }
        }

        for (Allegato allegato : documentoProtocollo.getAllegati()) {
            Documento documento;
            if (allegato.getFileOrigine()) {
                documento = protocolloRequest.getDocumento();
                documento.setDettaglio("Documento principale");
            } else {
                documento = new Documento();
                it.wego.cross.avbari.linksmt.protocollo.Allegato allegatoPerProtocollo = new it.wego.cross.avbari.linksmt.protocollo.Allegato();
                documento.setDettaglio("Allegato");
                allegatoPerProtocollo.setDocumento(documento);
                protocolloRequest.getAllegati().add(allegatoPerProtocollo);
            }

//            documento.setClassifica("");
//            documento.setCollocazioneTelematica("");
            documento.setContenuto(allegato.getFile());
            documento.setTitolo(allegato.getDescrizione());
//            documento.setDettaglio("");
//            documento.setImprontaMIME(null);
            documento.setNomeFile(allegato.getNomeFile());
            documento.setSunto(allegato.getDescrizione());
//            documento.setTipoRiferimento("");
        }

//        Destinatario destinatario = new Destinatario();
//        destinatario.setAmministrazione(amministrazione);
//        destinatario.setAreaOrganizzativaOmogenea(documentoProtocollo.getAoo());
//        for (SoggettoProtocollo dest : documentoProtocollo.getSoggetti()) {
//            if (dest.getTipo().equals(AvBariConstants.SOGGETTO_DESTINATARIO)) {
//                if (dest.getTipoPersona().equals(Constants.PERSONA_FISICA)) {
//                    PersonaFisica personaFisica = new PersonaFisica();
//                    personaFisica.setCodiceFiscale(dest.getCodiceFiscale());
//                    personaFisica.setCognome(dest.getCognome());
//                    personaFisica.setNome(dest.getNome());
//                }
//                if (dest.getTipoPersona().equals(Constants.PERSONA_GIURIDICA)) {
//                    PersonaGiuridica personaGiuridica = new PersonaGiuridica();
//                    personaGiuridica.setPartitaIVA(dest.getPartitaIva());
//                    if (Strings.isNullOrEmpty(dest.getDenominazione())) {
//                        personaGiuridica.setRagioneSociale(dest.getCognome() + " " + dest.getNome());
//                    } else {
//                        personaGiuridica.setRagioneSociale(dest.getDenominazione());
//                    }
//                }
//            }
//        }
//        protocolloRequest.getDestinatari().add(destinatario);
        //TODO Serialize Request
        RichiestaProtocolloFascResponse.Return result = port.richiestaProtocolloFasc(protocolloRequest);
        if (result != null && result.getErrore() != null) {
            throw new Exception(result.getErrore().getDescrizione());
        }
        DocumentoProtocolloResponse documentoProtocolloResponse = new DocumentoProtocolloResponse();
        documentoProtocolloResponse.setNumeroProtocollo(String.valueOf(result.getNumeroProtocollo()));
        documentoProtocolloResponse.setAnnoProtocollo(String.valueOf(result.getAnno()));
        documentoProtocolloResponse.setCodRegistro(documentoProtocollo.getAoo());
        documentoProtocolloResponse.setDataProtocollo(Utils.xmlGregorianCalendarToDate(result.getDataProtocollo()));
        documentoProtocolloResponse.setFascicolo(documentoProtocollo.getCodiceFascicolo());

        // response.setCodRegistro(aoo);
        documentoProtocolloResponse.setAllegatoOriginale(documentoProtocollo.getAllegatoOriginale());
        documentoProtocolloResponse.setAllegati(documentoProtocollo.getAllegati());
        //TODO Serialize Response

        return documentoProtocolloResponse;

    }

    private DocumentoProtocolloResponse protocollaUscitaFasc(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
        DocumentoProtocolloResponse documentoProtocolloResponse = null;
        Integer idEnte = documentoProtocollo.getIdEnte();
        Integer idComune = documentoProtocollo.getIdComune();

        String endPointServizioProtocollo = configurationService.getCachedPluginConfiguration(AvBariConstants.WS_PROTOCOLLO_ENDPOINT, idEnte, idComune);
        if (StringUtils.isEmpty(endPointServizioProtocollo)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.WS_PROTOCOLLO_ENDPOINT + "'");
        }
        Log.PLUGIN.debug("VALORE INDICATO PER LA PROPRIETA " + AvBariConstants.WS_PROTOCOLLO_ENDPOINT + ": " + endPointServizioProtocollo);

        String stringUtenteProtocollazione = configurationService.getCachedPluginConfiguration(AvBariConstants.ID_UTENTE_PROTOCOLLATORE, idEnte, idComune);
        if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.ID_UTENTE_PROTOCOLLATORE + "'");
        }
        Integer utenteProtocollazione = Integer.valueOf(stringUtenteProtocollazione);

        String amministrazione = documentoProtocollo.getAmministrazione();
        if (StringUtils.isEmpty(amministrazione)) {
            throw new Exception("Manca il valore sulla tabella enti il codice amministrazione ");
        }

        if (StringUtils.isEmpty(documentoProtocollo.getAoo())) {
            throw new Exception("Manca il valore sulla tabella enti relativamente all'Unita' organizzaztiva ");
        }

        String username = configurationService.getCachedPluginConfiguration(AvBariConstants.PROTOCOLLO_USERNAME, idEnte, idComune);
        if (StringUtils.isEmpty(username)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.PROTOCOLLO_USERNAME + "'");
        }
        String password = configurationService.getCachedPluginConfiguration(AvBariConstants.PROTOCOLLO_PASSWORD, idEnte, idComune);
        if (StringUtils.isEmpty(password)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.PROTOCOLLO_PASSWORD + "'");
        }

        ProtocolloServerImplService service = new ProtocolloServerImplService();
        ProtocolloServer port = service.getProtocolloServerImplPort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointServizioProtocollo);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(username));
        headers.put("Password", Collections.singletonList(password));
        ((BindingProvider) port).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        RichiestaProtocolloUscitaFasc.ProtocolloUscitaFascRequest request = new RichiestaProtocolloUscitaFasc.ProtocolloUscitaFascRequest();
        request.setAmministrazione(amministrazione);
        request.setAreaOrganizzativaOmogenea(documentoProtocollo.getAoo());
        request.setIdUtente(utenteProtocollazione);
        request.setOggetto(documentoProtocollo.getOggetto());
        request.setIdFascicolo(documentoProtocollo.getCodiceFascicolo());
        request.setIdTitolario(documentoProtocollo.getClassifica());

//        List<ContattoDestinatario> destinatari = new ArrayList<ContattoDestinatario>();
        for (SoggettoProtocollo dest : documentoProtocollo.getSoggetti()) {
            ContattoDestinatario destinatario = new ContattoDestinatario();
            if (dest.getTipoPersona().equals(Constants.PERSONA_FISICA)) {
                destinatario.setCodiceFiscale(dest.getCodiceFiscale());
                destinatario.setCognome(dest.getCognome());
                destinatario.setNome(dest.getNome());
                destinatario.setModalitaSpedizione(4);
            }
            if (dest.getTipoPersona().equals(Constants.PERSONA_GIURIDICA)) {
                destinatario.setPiva(dest.getPartitaIva());
                if (Strings.isNullOrEmpty(dest.getDenominazione())) {
                    destinatario.setRagioneSociale(dest.getCognome() + " " + dest.getNome());
                } else {
                    destinatario.setRagioneSociale(dest.getDenominazione());
                }
                destinatario.setModalitaSpedizione(4);
            }
//            destinatari.add(destinatario);
            request.getDestinatari().add(destinatario);
        }
        request.setInvio(false);

        for (Allegato allegato : documentoProtocollo.getAllegati()) {
            Documento documento = new Documento();;
            if (allegato.getFileOrigine()) {
                documento.setDettaglio("Documento principale");
                request.setDocumento(documento);
            } else {
                it.wego.cross.avbari.linksmt.protocollo.Allegato allegatoPerProtocollo = new it.wego.cross.avbari.linksmt.protocollo.Allegato();
                documento.setDettaglio("Allegato");
                allegatoPerProtocollo.setDocumento(documento);
                request.getAllegati().add(allegatoPerProtocollo);
            }

//            documento.setClassifica("");
//            documento.setCollocazioneTelematica("");
            documento.setContenuto(allegato.getFile());
            documento.setTitolo(allegato.getDescrizione());
//            documento.setDettaglio("");
//            documento.setImprontaMIME(null);
            documento.setNomeFile(allegato.getNomeFile());
            documento.setSunto(allegato.getDescrizione());
//            documento.setTipoRiferimento("");
        }
        RichiestaProtocolloUscitaFascResponse.Return response = port.richiestaProtocolloUscitaFasc(request);
        if (response != null) {
            if (response.getErrore() != null) {
                throw new Exception(response.getErrore().getDescrizione());
            } else {
                documentoProtocolloResponse = new DocumentoProtocolloResponse();
                documentoProtocolloResponse.setNumeroProtocollo(String.valueOf(response.getNumeroProtocollo()));
                documentoProtocolloResponse.setAnnoProtocollo(String.valueOf(response.getAnno()));
                documentoProtocolloResponse.setCodRegistro(documentoProtocollo.getAoo());
                documentoProtocolloResponse.setDataProtocollo(Utils.xmlGregorianCalendarToDate(response.getDataProtocollo()));
                documentoProtocolloResponse.setFascicolo(documentoProtocollo.getCodiceFascicolo());

                // response.setCodRegistro(aoo);
                documentoProtocolloResponse.setAllegatoOriginale(documentoProtocollo.getAllegatoOriginale());
                documentoProtocolloResponse.setAllegati(documentoProtocollo.getAllegati());
            }
        }
        return documentoProtocolloResponse;
    }

    private String protocollaCreaFascicolo(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
        String fascicolo = null;
        Integer idEnte = documentoProtocollo.getIdEnte();
        Integer idComune = documentoProtocollo.getIdComune();

        String endPointServizioFascicolo = configurationService.getCachedPluginConfiguration(AvBariConstants.WS_FASCICOLO_ENDPOINT, idEnte, idComune);
        if (StringUtils.isEmpty(endPointServizioFascicolo)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.WS_FASCICOLO_ENDPOINT + "'");
        }
        String username = configurationService.getCachedPluginConfiguration(AvBariConstants.PROTOCOLLO_USERNAME, idEnte, idComune);
        if (StringUtils.isEmpty(username)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.PROTOCOLLO_USERNAME + "'");
        }
        String password = configurationService.getCachedPluginConfiguration(AvBariConstants.PROTOCOLLO_PASSWORD, idEnte, idComune);
        if (StringUtils.isEmpty(password)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.PROTOCOLLO_PASSWORD + "'");
        }

        String stringUtenteProtocollazione = configurationService.getCachedPluginConfiguration(AvBariConstants.ID_UTENTE_PROTOCOLLATORE, idEnte, idComune);
        if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.ID_UTENTE_PROTOCOLLATORE + "'");
        }

        Integer utenteProtocollazione = Integer.valueOf(stringUtenteProtocollazione);

        String stringModelloFascicolo = configurationService.getCachedPluginConfiguration(AvBariConstants.MODELLO_FASCICOLO, idEnte, idComune);
        if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.MODELLO_FASCICOLO + "'");
        }

        Integer modelloFascicolo = Integer.valueOf(stringModelloFascicolo);

        String autore = configurationService.getCachedPluginConfiguration(AvBariConstants.AUTORE_FASCICOLO, idEnte, idComune);
        if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
            throw new Exception("Sulla tabella di configurazione non Ã¨ stata valorizzata la chiave '" + AvBariConstants.AUTORE_FASCICOLO + "'");
        }

        if (StringUtils.isEmpty(documentoProtocollo.getClassifica())) {
            throw new Exception("Manca la definizione della classifica sul procedimento");
        }

        FascicoloServerImplService fascicoloServerImplService = new FascicoloServerImplService();
        FascicoloServer port = fascicoloServerImplService.getFascicoloServerImplPort();

        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointServizioFascicolo);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(username));
        headers.put("Password", Collections.singletonList(password));
        ((BindingProvider) port).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        CreazioneFascicolo.NuovoFascicoloRequest creazioneRequest = new CreazioneFascicolo.NuovoFascicoloRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataF = sdf.format(new Date());
        creazioneRequest.setAutore(autore);
        creazioneRequest.setDescrizione("Fascicolo - " + documentoProtocollo.getIdPratica() + " - " + dataF);
        creazioneRequest.setIdNodoPadre("");
        creazioneRequest.setIdNodoTitolario(documentoProtocollo.getClassifica());
        creazioneRequest.setIdModelloFascicolo(modelloFascicolo); // id modello fascicolo
        creazioneRequest.setProfilo(utenteProtocollazione); // id profilo
        creazioneRequest.setRiservato(false);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Date data = new Date();
        gregorianCalendar.setTime(data);
        XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

        creazioneRequest.setStartDate(startDate);
        //creazioneRequest.setEndDate(value); // not required

        CreazioneFascicoloResponse.Return response = port.creazioneFascicolo(creazioneRequest);

        if (response != null && response.getErrore() != null) {
            throw new Exception(response.getErrore().getDescrizione());
        }
        fascicolo = response.getIdFascicolo();

        return fascicolo;
    }

    private List<SoggettoProtocollo> ricalcolaSoggetti(it.wego.cross.entity.Pratica pratica, PraticheEventi praticaEvento, List<SoggettoProtocollo> soggettiProtocollo, String verso) {
        List<SoggettoProtocollo> soggetti = new ArrayList<SoggettoProtocollo>();
        if (verso.equals(AvBariConstants.PROTOCOLLO_INPUT)) {
            Anagrafica anagrafica = null;
            for (PraticheEventiAnagrafiche praticaEventoAnagrafica : praticaEvento.getPraticheEventiAnagraficheList()) {
                if (anagrafica == null) {
                    for (PraticaAnagrafica pa : praticaEventoAnagrafica.getAnagrafica().getPraticaAnagraficaList()) {
                        if (pa.getAnagrafica().getIdAnagrafica().equals(praticaEventoAnagrafica.getAnagrafica().getIdAnagrafica())) {
                            if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                                anagrafica = pa.getAnagrafica();
                                break;
                            }
                        }
                    }
                    if (anagrafica != null) {
                        SoggettoProtocollo s = ProtocolloSerializer.serialize(anagrafica);
                        s.setTipo(AvBariConstants.SOGGETTO_MITTENTE);
                        soggetti.add(s);
                    }
                }
            }
            if (anagrafica == null) {
                if (praticaEvento.getEntiList() != null && !praticaEvento.getEntiList().isEmpty()) {
                    SoggettoProtocollo s = ProtocolloSerializer.serialize(praticaEvento.getEntiList().get(0));
                    s.setTipo(AvBariConstants.SOGGETTO_MITTENTE);
                    soggetti.add(s);
                } else {
                    anagrafica = praticaEvento.getPraticheEventiAnagraficheList().get(0).getAnagrafica();
                    SoggettoProtocollo s = ProtocolloSerializer.serialize(anagrafica);
                    s.setTipo(AvBariConstants.SOGGETTO_MITTENTE);
                    soggetti.add(s);
                }
            }

            SoggettoProtocollo s = ProtocolloSerializer.serialize(pratica.getIdProcEnte().getIdEnte());
            s.setTipo(AvBariConstants.SOGGETTO_DESTINATARIO);
            soggetti.add(s);
        } else {
            soggetti = soggettiProtocollo;
        }
        return soggetti;
    }
}
