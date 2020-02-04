package it.wego.cross.plugins.protocollo;

import it.wego.cross.action.ProtocolloDemoAction;
import it.wego.cross.constants.DemoConstans;
import it.wego.cross.constants.ProtocolloConstants;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Pratica;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author CS
 */
public class GestioneProtocolloDemo implements GestioneProtocollo {

    @Autowired
    private ProtocolloDemoAction protocolloAction;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private ConfigurationService configurationService;

    private static final String TIPODOMICILIO = "DOMICILIO";
    private static final String PROTOCOLLO_INPUT = "I";
    private String aoo;

    @Override
    public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
        DocumentoProtocolloResponse response = new DocumentoProtocolloResponse();
        Enti ente = null;
        LkComuni comune = null;
        Integer idPratica = documentoProtocollo.getIdPratica();
        if (idPratica != null) {
            it.wego.cross.entity.Pratica pratica = praticheService.getPratica(idPratica);
            if (pratica != null) {
                ente = pratica.getIdProcEnte().getIdEnte();
                comune = pratica.getIdComune();
            }
        }
        for (Allegato allegato : documentoProtocollo.getAllegati()) {
            String id = protocolloAction.salvaAllegato(allegato, ente, comune);
            allegato.setIdEsterno(id);
        }
        response.setAllegati(documentoProtocollo.getAllegati());
        response.setSoggetti(documentoProtocollo.getSoggetti());
        response.setAnnoProtocollo(configurationService.getCachedConfiguration(DemoConstans.ANNO_PROTOCOLLO, (ente != null) ? ente.getIdEnte() : null, (comune != null) ? comune.getIdComune() : null));
        response.setCodRegistro(configurationService.getCachedConfiguration(DemoConstans.REGISTRO_PROTOCOLLO, (ente != null) ? ente.getIdEnte() : null, (comune != null) ? comune.getIdComune() : null));
        response.setNumeroProtocollo(protocolloAction.getNumeroProtocollo(ente, comune));
        if (documentoProtocollo.getCodiceFascicolo() != null && !"".equals(documentoProtocollo.getCodiceFascicolo().trim())) {
            response.setFascicolo(documentoProtocollo.getCodiceFascicolo());
        } else {
            response.setFascicolo(protocolloAction.getNumeroFascicolo(ente, comune));
        }
        response.setDataProtocollo(new Date());
        response.setAllegatoOriginale(documentoProtocollo.getAllegatoOriginale());
        response.setDestinatario(null);
        response.setIdDocumento(null);
        response.setOggetto(documentoProtocollo.getOggetto());
        response.setTipoDocumento(documentoProtocollo.getTipoDoc());
        //Risposta risposta = port.process(richiesta);
        return response;
    }

    @Override
    public DocumentoProtocolloResponse findByProtocollo(Integer annoRiferimento, String numeroProtocollo, Enti ente, LkComuni comune) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DocumentoProtocolloResponse> queryProtocollo(FilterProtocollo filterProtocollo, Enti ente, LkComuni comune) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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

    @Override
    public DocumentoProtocolloRequest getDocumentoProtocolloDaXml(Pratica praticaCross, String verso) throws Exception {
        DocumentoProtocolloRequest protocolloRequest = new DocumentoProtocolloRequest();
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
                        if (tipoIndirizzo.getDescrizione().equals(TIPODOMICILIO)) {
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
        if (verso.equalsIgnoreCase(PROTOCOLLO_INPUT)) {
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
        protocolloRequest.setTipoDoc("UNICO");
        //Prendilo da UO Enti
        Enti ente = entiService.getEnte(praticaCross.getIdEnte().intValue());
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
        protocolloRequest.setClassifica(proc == null ? null : proc.getClassifica());
        return protocolloRequest;
    }

    @Override
    public DocumentoProtocolloRequest getDocumentoProtocolloDaDatabase(it.wego.cross.entity.Pratica pratica, PraticheEventi praticaEvento, List<Allegato> allegatiNuovi, List<SoggettoProtocollo> soggettiProtocollo, String verso, String oggetto) throws Exception {
        DocumentoProtocolloRequest protocollo = new DocumentoProtocolloRequest();
        protocollo.setIdPratica(pratica != null ? pratica.getIdPratica() : null);
        protocollo.setIdPraticaEvento(praticaEvento != null ? praticaEvento.getIdPraticaEvento() : null);

        protocollo.setSoggetti(soggettiProtocollo);
        List<it.wego.cross.plugins.commons.beans.Allegato> allegatiProtocollo = new ArrayList<it.wego.cross.plugins.commons.beans.Allegato>();
        for (Allegato a : allegatiNuovi) {
            if (!Utils.e(a.getIdEsterno())) {
                a.setFile(protocolloAction.getAllegato(a.getIdEsterno(), pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune()));
                allegatiProtocollo.add(a);
            } else if (a.getFile() != null) {
                allegatiProtocollo.add(a);
            }
        }
        protocollo.getAllegatoOriginale();
        protocollo.setAllegati(allegatiProtocollo);

        //E per documenti in entrata, U per i documenti in uscita
        if (verso.equalsIgnoreCase(PROTOCOLLO_INPUT)) {
            protocollo.setDirezione("E");
            protocollo.setTipoDoc("DOCARR");
        } else {
            protocollo.setDirezione("U");
            protocollo.setTipoDoc("LET");
        }

        //Anno corrente
        Integer annoFascicolo = pratica.getAnnoRiferimento();
        if (annoFascicolo == null) {
            //La pratica è nuova, quindi setto come anno fascicolo l'anno corrente
            annoFascicolo = Calendar.getInstance().get(Calendar.YEAR);
            //Poiché si tratta di una pratica nuova faccio l'override del tipo documento
            protocollo.setTipoDoc("UNICO");
        }
        protocollo.setAnnoFascicolo(annoFascicolo);
        protocollo.setAoo("aoo");//<<<<<<<<<<<<<------------------------------------
        //Oggetto della pratica
        protocollo.setOggetto(oggetto);
        //Fisso a PORTALE CROSS
        protocollo.setSource("PORTALE CROSS");
        //Valori ammessi:
        //DOCARR: Documento generico in arrivo
        //LET: Lettera in uscita
        //UNICO: Procedimento Unico
        //Prendilo da UO Enti
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        protocollo.setUo(ente.getUnitaOrganizzativa());

        //Fisso a 11
        protocollo.setTitolario("11");
        Procedimenti proc = null;
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
        protocollo.setClassifica(proc == null ? null : proc.getClassifica());

        //Gestione caso di protocollazione da ente terzo
        if (pratica.getIdPraticaPadre() != null) {
            protocollo.setDirezione("E");
            protocollo.setTipoDoc("DOCARR");
            ente = pratica.getIdPraticaPadre().getIdProcEnte().getIdEnte();
            protocollo.setUo(ente.getUnitaOrganizzativa());
        }

        return protocollo;
    }

    public String getQuery(FilterProtocollo filter) {
        List<String> predicates = new ArrayList<String>();

        if (!Utils.e(filter.getNumeroProtocollo())) {
            predicates.add("NUMERO_PROT:" + filter.getNumeroProtocollo());
        }
        if (!Utils.e(filter.getAnno())) {
            predicates.add("ANNO_PROT:" + filter.getAnno());
        }
        if (!Utils.e(filter.getTipoDocumento())) {
            predicates.add("TIPO_DOC:" + filter.getTipoDocumento());
        }
        if (filter.getDataDocumentoDa() != null && filter.getDataDocumentoA() != null) {
            predicates.add("DATA_PROT:[" + qq(filter.getDataDocumentoDa()) + " TO " + qq(filter.getDataDocumentoA()) + "]");
        }

        if (filter.getDataDocumentoDa() != null && filter.getDataDocumentoA() == null) {
            predicates.add("DATA_PROT:[" + qq(filter.getDataDocumentoDa()) + " TO " + qq(Calendar.getInstance().getTime()) + "]");
        }

        String query = org.apache.commons.lang.StringUtils.join(predicates, " AND ");
        return query;
    }

//    @Override
//    public String getTipologiaDocumentoPerPratica(Integer idEnte) {
//        return "UNICO";
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

    public String qq(Date vl) {
        if (vl == null) {
            return "null";
        }
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(vl);
    }
}
