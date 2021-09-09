/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.serializer;

import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.Indirizzo;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Provincia;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.pratica.AdempimentoSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante;
import it.gov.impresainungiorno.schema.suap.pratica.Carica;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiDichiarante;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.FormaGiuridica;
import it.gov.impresainungiorno.schema.suap.pratica.ImpiantoProduttivo;
import it.gov.impresainungiorno.schema.suap.pratica.Intestazione;
import it.gov.impresainungiorno.schema.suap.pratica.ModelloAttivita;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProcuraSpeciale;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.Struttura;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;
import it.gov.impresainungiorno.schema.suap.pratica.VersioneSchema;
import it.gov.impresainungiorno.schema.suap.rea.AllegatoGenerico;
import it.gov.impresainungiorno.schema.suap.rea.AllegatoSuap;
import it.gov.impresainungiorno.schema.suap.rea.ComunicazioneREA;
import it.gov.impresainungiorno.schema.suap.rea.EsitoScia;
import it.gov.impresainungiorno.schema.suap.rea.OggettoComunicazioneREA;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea;
import it.wego.cross.events.comunicazionerea.service.ComunicazioneReaService;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.PluginService;
import it.wego.cross.utils.Utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component
public class ComunicazioneReaSerializer {

    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private ComunicazioneReaService comunicazioneReaService;

    // <editor-fold defaultstate="collapsed" desc="COMUNICAZIONE REA SERIALIZER">
    public ComunicazioneREA serializeComunicazioneREA(Pratica pratica, PraticheEventi praticaEvento, String statoPratica) throws Exception {
        ComunicazioneREA comunicazioneRea = new ComunicazioneREA();

        RiIntegrazioneRea integrazioneRea = comunicazioneReaService.getIntegrazioneRea(pratica);

        VersioneSchema versionSchemaComunicazioneRea = new VersioneSchema();
//        versionSchemaComunicazioneRea.setData(Calendar.getInstance().getTime());
        versionSchemaComunicazioneRea.setData(Utils.dateToXmlGregorianCalendar(Calendar.getInstance().getTime()));
        versionSchemaComunicazioneRea.setVersione("1.0.0");
        comunicazioneRea.setInfoSchema(versionSchemaComunicazioneRea);

        it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloSuapDocumentoUscita = serializeProtocolloEvento(praticaEvento);
        comunicazioneRea.setProtocolloSuap(protocolloSuapDocumentoUscita);

        ComunicazioneREA.SuapMittente suapMittente = new ComunicazioneREA.SuapMittente();
        suapMittente.setIdSuap(Utils.bi(Integer.valueOf(pratica.getIdProcEnte().getIdEnte().getCodEnteEsterno())));
        suapMittente.setValue(pratica.getIdProcEnte().getIdEnte().getDescrizione());
        comunicazioneRea.setSuapMittente(suapMittente);

        Anagrafica anagraficaAziendaRiferimento = integrazioneRea.getIdAziendaRiferimento();
        PraticaAnagrafica aziendaRiferimento = getPraticaAnagrafica(pratica, anagraficaAziendaRiferimento);

        if (aziendaRiferimento == null) {
            throw new Exception("Impossibile trovare un'azienda beneficiaria per la pratica '" + pratica.getIdentificativoPratica() + "'");
        }

        ComunicazioneREA.EstremiPraticaSuap.Impresa impresa = serializePraticaAnagrafica(aziendaRiferimento);

        OggettoComunicazioneREA oggettoComunicazioneRea = new OggettoComunicazioneREA();
        oggettoComunicazioneRea.setTipoIntervento(TipoIntervento.fromValue(integrazioneRea.getTipoIntervento()));
        oggettoComunicazioneRea.setTipoProcedimento(integrazioneRea.getTipoProcedimento());
        oggettoComunicazioneRea.setValue(praticaEvento.getIdEvento().getDesEvento());

        it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloPraticaSuap = serializeProtocolloPratica(pratica);
        ComunicazioneREA.EstremiPraticaSuap estremiPraticaSuap = new ComunicazioneREA.EstremiPraticaSuap();
        estremiPraticaSuap.setCodicePratica(escapeIdentificativoPratica(pratica));
        estremiPraticaSuap.setProtocolloSuap(protocolloPraticaSuap);
        estremiPraticaSuap.setImpresa(impresa);
        estremiPraticaSuap.setOggetto(oggettoComunicazioneRea);
        comunicazioneRea.setEstremiPraticaSuap(estremiPraticaSuap);
        comunicazioneRea.setStatoPratica(it.gov.impresainungiorno.schema.suap.rea.StatoPratica.fromValue(statoPratica));

        List<AllegatoGenerico> listaAllegatiComunicazioneRea;

        if (comunicazioneRea.getStatoPratica().equals(it.gov.impresainungiorno.schema.suap.rea.StatoPratica.ISTRUTTORIA)) {
            ComunicazioneREA.ComunicazioneScia comunicazioneScia = new ComunicazioneREA.ComunicazioneScia();
            listaAllegatiComunicazioneRea = (List) comunicazioneScia.getAllegato();

            AllegatoSuap allegatoPraticaSuap = new AllegatoSuap();
            allegatoPraticaSuap.setDescrittore("SUAP-XML");
            allegatoPraticaSuap.setNomeFile(escapeIdentificativoPratica(pratica) + ".SUAP.XML");
            allegatoPraticaSuap.setDescrizione("Descrittore pratica XML");
            allegatoPraticaSuap.setMime("text/xml");
            listaAllegatiComunicazioneRea.add(allegatoPraticaSuap);

            comunicazioneRea.setComunicazioneScia(comunicazioneScia);
        } else {
            ComunicazioneREA.ComunicazioneEsitoScia comunicazioneEsitoScia = new ComunicazioneREA.ComunicazioneEsitoScia();
            listaAllegatiComunicazioneRea = comunicazioneEsitoScia.getAttoEnte();

            EsitoScia esitoScia = new EsitoScia();
            esitoScia.setDescrizione("Scia " + statoPratica);
            esitoScia.setDettaglioEsito("La Scia è stata " + statoPratica);
            esitoScia.setData(Utils.dateToXmlGregorianCalendar(Utils.now()));
            comunicazioneEsitoScia.setEsito(esitoScia);

            comunicazioneRea.setComunicazioneEsitoScia(comunicazioneEsitoScia);
        }

        AllegatoSuap allegatoSuapRea;
        for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
            Allegati allegatoEvento = pea.getAllegati();
            allegatoSuapRea = new AllegatoSuap();

            if (allegatoEvento.equals(pratica.getIdModello())) {
                allegatoSuapRea.setDescrittore("MDA-PDF");
            } else {
                allegatoSuapRea.setDescrittore("ALTRO");
            }
            allegatoSuapRea.setNomeFile(allegatoEvento.getNomeFile());
            allegatoSuapRea.setDescrizione(allegatoEvento.getDescrizione());
            allegatoSuapRea.setMime(allegatoEvento.getTipoFile());

            listaAllegatiComunicazioneRea.add(allegatoSuapRea);
        }

        return comunicazioneRea;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Riepilogo Pratica SUAP Serializer ">
    public RiepilogoPraticaSUAP serializeRiepilogoPraticaSUAP(Pratica pratica, PraticheEventi evento) throws Exception {
        RiIntegrazioneRea integrazioneRea = comunicazioneReaService.getIntegrazioneRea(pratica);

        PraticaAnagrafica dichiarantePratica = getPraticaAnagrafica(pratica, integrazioneRea.getIdAnagraficaDichiarante());
        PraticaAnagrafica aziendaRiferimento = getPraticaAnagrafica(pratica, integrazioneRea.getIdAziendaRiferimento());
        PraticaAnagrafica rappresentanteAzienda = getPraticaAnagrafica(pratica, integrazioneRea.getIdAnagraficaLegaleRapp());

        if (dichiarantePratica == null) {
            throw new Exception("Impossibile trovare il dichiarante per la pratica '" + pratica.getIdentificativoPratica() + "'");
        }
        if (aziendaRiferimento == null) {
            throw new Exception("Impossibile trovare un'azienda beneficiaria per la pratica '" + pratica.getIdentificativoPratica() + "'");
        }
        if (rappresentanteAzienda == null) {
            throw new Exception("Impossibile trovare un rappresentate per l'azienda '" + aziendaRiferimento.getAnagrafica().getPartitaIva() + "' per la pratica '" + pratica.getIdentificativoPratica() + "'");
        }

        Recapiti recapitoDichiarantePratica = getRecapitoPraticaAnagrafica(dichiarantePratica);
        Recapiti recapitoAziendaRiferimento = getRecapitoPraticaAnagrafica(aziendaRiferimento);
        Recapiti recapitoRappresentanteAzienda = getRecapitoPraticaAnagrafica(rappresentanteAzienda);

        Comune comuneImpresa = new Comune();
        comuneImpresa.setCodiceCatastale(recapitoAziendaRiferimento.getIdComune().getCodCatastale());
        comuneImpresa.setValue(recapitoAziendaRiferimento.getIdComune().getDescrizione());

        Provincia provinciaImpresa = new Provincia();
        provinciaImpresa.setSigla(recapitoAziendaRiferimento.getIdComune().getIdProvincia().getCodCatastale());
        provinciaImpresa.setValue(recapitoAziendaRiferimento.getIdComune().getIdProvincia().getDescrizione());

        Stato statoImpresa = new Stato();
        if (recapitoAziendaRiferimento.getIdComune().getIdStato().getDescrizione().equalsIgnoreCase("ITALIA")) {
            statoImpresa.setCodice("I");
        } else {
            statoImpresa.setCodice("E");
        }
        statoImpresa.setCodiceCatastale(recapitoAziendaRiferimento.getIdComune().getIdStato().getCodIstat());
        statoImpresa.setValue(recapitoAziendaRiferimento.getIdComune().getIdStato().getDescrizione());

        it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloPraticaSuap = serializeProtocolloPratica(pratica);
        RiepilogoPraticaSUAP riepilogoPraticaSuap = new RiepilogoPraticaSUAP();

        VersioneSchema versionSchemaRiepilogoPraticaSuap = new VersioneSchema();
        versionSchemaRiepilogoPraticaSuap.setData(Utils.dateToXmlGregorianCalendar(Calendar.getInstance().getTime()));
//        versionSchemaRiepilogoPraticaSuap.setData(Calendar.getInstance().getTime());
        versionSchemaRiepilogoPraticaSuap.setVersione("1.0.0");
        riepilogoPraticaSuap.setInfoSchema(versionSchemaRiepilogoPraticaSuap);

        Stato statoAnagraficaDichiarante = new Stato();
        if (recapitoDichiarantePratica.getIdComune().getIdStato().getDescrizione().equalsIgnoreCase("ITALIA")) {
            statoAnagraficaDichiarante.setCodice("I");
        } else {
            statoAnagraficaDichiarante.setCodice("E");
        }
        statoAnagraficaDichiarante.setCodiceCatastale(recapitoDichiarantePratica.getIdComune().getIdStato().getCodIstat());
        statoAnagraficaDichiarante.setValue(recapitoDichiarantePratica.getIdComune().getIdStato().getDescrizione());

        Intestazione intestazioneRiepilogoPraticaSuap = new Intestazione();
        intestazioneRiepilogoPraticaSuap.setCodicePratica(escapeIdentificativoPratica(pratica));
        EstremiDichiarante estremiDichiarante = new EstremiDichiarante();
        estremiDichiarante.setCodiceFiscale(dichiarantePratica.getAnagrafica().getCodiceFiscale());
        estremiDichiarante.setCognome(dichiarantePratica.getAnagrafica().getCognome());
        estremiDichiarante.setNome(dichiarantePratica.getAnagrafica().getNome());
        estremiDichiarante.setNazionalita(statoAnagraficaDichiarante);
        estremiDichiarante.setPec(!Utils.e(recapitoDichiarantePratica.getPec()) ? recapitoDichiarantePratica.getPec() : recapitoDichiarantePratica.getEmail());
        estremiDichiarante.setQualifica(integrazioneRea.getQualificaDichiarantePratica());
        estremiDichiarante.setTelefono(recapitoDichiarantePratica.getTelefono());

        intestazioneRiepilogoPraticaSuap.setDichiarante(estremiDichiarante);
        AnagraficaImpresa anagraficaImpresa = new AnagraficaImpresa();
        anagraficaImpresa.setCodiceFiscale(aziendaRiferimento.getAnagrafica().getCodiceFiscale());
        anagraficaImpresa.setPartitaIva(aziendaRiferimento.getAnagrafica().getPartitaIva());
        anagraficaImpresa.setRagioneSociale(aziendaRiferimento.getAnagrafica().getDenominazione());
        FormaGiuridica formaGiuridicaImpresa = new FormaGiuridica();
        formaGiuridicaImpresa.setCodice(integrazioneRea.getFormaGiuridicaImpresa());
//        formaGiuridicaImpresa.setValue("ALTRO");
        anagraficaImpresa.setFormaGiuridica(formaGiuridicaImpresa);
//        if (aziendaRiferimento.getAnagrafica().getFlgAttesaIscrizioneRea() == 'N') {
//            CodiceREA codiceREA = new CodiceREA();
//            codiceREA.setDataIscrizione(Utils.dateToXmlGregorianCalendar(aziendaRiferimento.getAnagrafica().getDataIscrizioneRea()));
//            codiceREA.setProvincia(aziendaRiferimento.getAnagrafica().getIdProvinciaCciaa().getCodCatastale());
//            codiceREA.setValue(aziendaRiferimento.getAnagrafica().getNIscrizioneRea());
//            anagraficaImpresa.setCodiceREA(codiceREA);
//        }

        IndirizzoConRecapiti indirizzoConRecapitiImpresa = new IndirizzoConRecapiti();
        if (recapitoAziendaRiferimento.getIdComune().getIdStato().getDescrizione().equalsIgnoreCase("ITALIA")) {
            indirizzoConRecapitiImpresa.setCap(recapitoAziendaRiferimento.getCap());
            if (recapitoAziendaRiferimento.getIdDug() != null) {
                indirizzoConRecapitiImpresa.setToponimo(recapitoAziendaRiferimento.getIdDug().getDescrizione());
            } else {
                indirizzoConRecapitiImpresa.setToponimo(" ");
            }
        }
        indirizzoConRecapitiImpresa.setCittaStraniera(null);
        indirizzoConRecapitiImpresa.setDenominazioneStradale(recapitoAziendaRiferimento.getIndirizzo());
        if (!Utils.e(recapitoAziendaRiferimento.getLocalita())) {
            indirizzoConRecapitiImpresa.setFrazione(recapitoAziendaRiferimento.getLocalita());
        } else {
            indirizzoConRecapitiImpresa.setFrazione(" ");
        }

        indirizzoConRecapitiImpresa.setNumeroCivico(recapitoAziendaRiferimento.getNCivico());
        indirizzoConRecapitiImpresa.setComune(comuneImpresa);
        indirizzoConRecapitiImpresa.setProvincia(provinciaImpresa);
        indirizzoConRecapitiImpresa.setStato(statoImpresa);
        anagraficaImpresa.setIndirizzo(indirizzoConRecapitiImpresa);

        AnagraficaRappresentante anagraficaRappresentanteImpresa = new AnagraficaRappresentante();

        Carica caricaAnagraficaRappresentanteImpresa = new Carica();
        caricaAnagraficaRappresentanteImpresa.setCodice(integrazioneRea.getCaricaLegaleRapp());
//        caricaAnagraficaRappresentanteImpresa.setValue("ALTRO");

        Stato statoAnagraficaRappresentanteImpresa = new Stato();
        if (recapitoRappresentanteAzienda.getIdComune().getIdStato().getDescrizione().equalsIgnoreCase("ITALIA")) {
            statoAnagraficaRappresentanteImpresa.setCodice("I");
        } else {
            statoAnagraficaRappresentanteImpresa.setCodice("E");
        }
        statoAnagraficaRappresentanteImpresa.setCodiceCatastale(recapitoRappresentanteAzienda.getIdComune().getIdStato().getCodIstat());
        statoAnagraficaRappresentanteImpresa.setValue(recapitoRappresentanteAzienda.getIdComune().getIdStato().getDescrizione());

        anagraficaRappresentanteImpresa.setCodiceFiscale(rappresentanteAzienda.getAnagrafica().getCodiceFiscale());
//        anagraficaRappresentanteImpresa.setPartitaIva(null);
        anagraficaRappresentanteImpresa.setCognome(rappresentanteAzienda.getAnagrafica().getCognome());
        anagraficaRappresentanteImpresa.setNome(rappresentanteAzienda.getAnagrafica().getNome());
        anagraficaRappresentanteImpresa.setNazionalita(statoAnagraficaRappresentanteImpresa);
        anagraficaRappresentanteImpresa.setCarica(caricaAnagraficaRappresentanteImpresa);
        anagraficaImpresa.setLegaleRappresentante(anagraficaRappresentanteImpresa);

        intestazioneRiepilogoPraticaSuap.setImpresa(anagraficaImpresa);
        intestazioneRiepilogoPraticaSuap.setDomicilioElettronico((!Utils.e(recapitoAziendaRiferimento.getPec())) ? recapitoAziendaRiferimento.getPec() : recapitoAziendaRiferimento.getEmail());

        ImpiantoProduttivo impiantoProduttivo = new ImpiantoProduttivo();
        impiantoProduttivo.setDestinazioneUso(null);
        impiantoProduttivo.setDisponibilitaImmobile(null);

        Indirizzo indirizzoImpiantoProduttivo = new Indirizzo();
        if (recapitoAziendaRiferimento.getIdComune().getIdStato().getDescrizione().equalsIgnoreCase("ITALIA")) {
            indirizzoImpiantoProduttivo.setCap(recapitoAziendaRiferimento.getCap());
            if (recapitoAziendaRiferimento.getIdDug() != null) {
                indirizzoImpiantoProduttivo.setToponimo(recapitoAziendaRiferimento.getIdDug().getDescrizione());
            } else {
                indirizzoImpiantoProduttivo.setToponimo(" ");
            }
        }
        indirizzoImpiantoProduttivo.setCittaStraniera(null);
        indirizzoImpiantoProduttivo.setDenominazioneStradale(recapitoAziendaRiferimento.getIndirizzo());
        if (!Utils.e(recapitoAziendaRiferimento.getLocalita())) {
            indirizzoImpiantoProduttivo.setFrazione(recapitoAziendaRiferimento.getLocalita());
        } else {
            indirizzoImpiantoProduttivo.setFrazione(" ");
        }

        indirizzoImpiantoProduttivo.setNumeroCivico(recapitoAziendaRiferimento.getNCivico());
        indirizzoImpiantoProduttivo.setComune(comuneImpresa);
        indirizzoImpiantoProduttivo.setProvincia(provinciaImpresa);
        indirizzoImpiantoProduttivo.setStato(statoImpresa);
        intestazioneRiepilogoPraticaSuap.setImpresa(anagraficaImpresa);

        Comune comuneImpiantoProduttivo = new Comune();
        comuneImpiantoProduttivo.setCodiceCatastale(null);
        comuneImpiantoProduttivo.setCodiceIstat(null);
        comuneImpiantoProduttivo.setValue(null);
        impiantoProduttivo.setIndirizzo(indirizzoImpiantoProduttivo);
        intestazioneRiepilogoPraticaSuap.setImpiantoProduttivo(impiantoProduttivo);
        OggettoComunicazione oggettoComunicazioneSuap = new OggettoComunicazione();

        oggettoComunicazioneSuap.setTipoIntervento(TipoIntervento.fromValue(integrazioneRea.getTipoIntervento()));
        oggettoComunicazioneSuap.setTipoProcedimento(integrazioneRea.getTipoProcedimento());
        oggettoComunicazioneSuap.setValue(pratica.getOggettoPratica());
        intestazioneRiepilogoPraticaSuap.setOggettoComunicazione(oggettoComunicazioneSuap);
        intestazioneRiepilogoPraticaSuap.setProtocollo(protocolloPraticaSuap);
        EstremiSuap estremiSuap = new EstremiSuap();
        estremiSuap.setCodiceAmministrazione(pratica.getIdProcEnte().getIdEnte().getCodiceAmministrazione());
        estremiSuap.setCodiceAoo(pratica.getIdProcEnte().getIdEnte().getCodiceAoo());
        estremiSuap.setIdentificativoSuap(Utils.bi(Integer.valueOf(pratica.getIdProcEnte().getIdEnte().getCodEnteEsterno())));
        estremiSuap.setValue(pratica.getIdProcEnte().getIdEnte().getDescrizione());

        intestazioneRiepilogoPraticaSuap.setUfficioDestinatario(estremiSuap);

        riepilogoPraticaSuap.setIntestazione(intestazioneRiepilogoPraticaSuap);

        Struttura struttura = new Struttura();

        if (pratica.getIdModello() != null) {
//        ModelloAttivita modelloAttivita = new ModelloAttivita();
//        //CF-GGMMAAAA-HHMM.NNN.MDA.PDF.P7M   o   CF-GGMMAAAA-HHMM.NNN.MDA.PDF 
//        modelloAttivita.setNomeFile("DPRTNT00A01A012T-08072010-1733.00023.mDa.pdf.p7m");
//        modelloAttivita.setMime("P7M");
//
//        AdempimentoSUAP adempimentoSUAP = new AdempimentoSUAP();
//        adempimentoSUAP.setCod("123456");
//        adempimentoSUAP.setNome("dfghjk.pdf");
//        adempimentoSUAP.setDistintaModelloAttivita(modelloAttivita);
            ModelloAttivita modelloAttivita = new ModelloAttivita();
            String nomefile = rappresentanteAzienda.getAnagrafica().getCodiceFiscale() + getModelloAttivitaFileExtension(pratica.getDataRicezione(), pratica.getIdModello().getNomeFile());
            modelloAttivita.setNomeFile(nomefile);
            String tipoFile = getTipoFile(pratica.getIdModello().getNomeFile());
            modelloAttivita.setMime(tipoFile);

            AdempimentoSUAP adempimentoSUAP = new AdempimentoSUAP();
            adempimentoSUAP.setNome(pratica.getIdModello().getNomeFile());
            adempimentoSUAP.setDistintaModelloAttivita(modelloAttivita);
            struttura.getModulo().add(adempimentoSUAP);
        }
        riepilogoPraticaSuap.setStruttura(struttura);
        return riepilogoPraticaSuap;
    }

    private String getModelloAttivitaFileExtension(Date dataRicezione, String nomeFile) {
        DateFormat df = new SimpleDateFormat("ddMMyyyy-hhmm.SSS");
        String date = df.format(dataRicezione);
        String nomefile = "-" + date + ".MDA." + getTipoFile(nomeFile);
        return nomefile;
    }

    private String getTipoFile(String nomeFile) {
        String fileExtension;
        if (nomeFile.toUpperCase().endsWith(".P7M")) {
            fileExtension = "P7M";
        } else {
            fileExtension = "PDF";
        }
        return fileExtension;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Procura Speciale Serializer ">
    public ProcuraSpeciale serializeProcuraSpeciale(String codiceFiscale, Date dataRicezione, Integer idAllegato) throws Exception {
        Allegati allegato = allegatiService.findAllegatoById(idAllegato);
        ProcuraSpeciale procuraSpeciale = new ProcuraSpeciale();
        String nomefile = codiceFiscale + getProcuraSpecialeFileExtension(dataRicezione, allegato.getNomeFile());
        procuraSpeciale.setNomeFile(nomefile);
        String tipoFile = getTipoFile(nomefile);
        procuraSpeciale.setMime(tipoFile);
        return procuraSpeciale;
    }

    private String getProcuraSpecialeFileExtension(Date dataRicezione, String nomeFile) {
        //CF-GGMMAAAA-HHMM.NNN.PDF.P7M   o   CF-GGMMAAAA-HHMM.NNN.PDF          
        DateFormat df = new SimpleDateFormat("ddMMyyyy-hhmm.000");
        String date = df.format(dataRicezione);
        String nomefile = "-" + date + "." + getTipoFile(nomeFile);
        return nomefile;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private ComunicazioneREA.EstremiPraticaSuap.Impresa serializePraticaAnagrafica(PraticaAnagrafica praticaAnagrafica) throws Exception {
        Recapiti recapitoRiferimento;

        if (praticaAnagrafica.getIdRecapitoNotifica() != null) {
            recapitoRiferimento = praticaAnagrafica.getIdRecapitoNotifica();
        } else {
            recapitoRiferimento = anagraficheService.getRecapitoRiferimentoAnagrafica(praticaAnagrafica.getAnagrafica());
        }

        if (recapitoRiferimento == null) {
            throw new Exception("Nessun recapito associato alla pratica");
        }
        Indirizzo indirizzoImpresa = new Indirizzo();

        if (recapitoRiferimento.getCap().length() != 5) {
            throw new Exception("Il cap del recapito deve essere valorizzato e deve essere ");
        }

        Comune comuneImpresa = new Comune();
        comuneImpresa.setCodiceCatastale(recapitoRiferimento.getIdComune().getCodCatastale());
        comuneImpresa.setValue(recapitoRiferimento.getIdComune().getDescrizione());

        Provincia provinciaImpresa = new Provincia();
//        provinciaImpresa.setCodiceIstat(null);
        provinciaImpresa.setSigla(recapitoRiferimento.getIdComune().getIdProvincia().getCodCatastale());
        provinciaImpresa.setValue(recapitoRiferimento.getIdComune().getIdProvincia().getDescrizione());

        Stato statoImpresa = new Stato();
        if (recapitoRiferimento.getIdComune().getIdStato().getDescrizione().equalsIgnoreCase("ITALIA")) {
            statoImpresa.setCodice("I");
            indirizzoImpresa.setCap(recapitoRiferimento.getCap());
            if (recapitoRiferimento.getIdDug() != null) {
                indirizzoImpresa.setToponimo(recapitoRiferimento.getIdDug().getDescrizione());
            } else {
                indirizzoImpresa.setToponimo(" ");
            }
        } else {
            statoImpresa.setCodice("E");
        }
        statoImpresa.setCodiceCatastale(recapitoRiferimento.getIdComune().getIdStato().getCodIstat());
        statoImpresa.setValue(recapitoRiferimento.getIdComune().getIdStato().getDescrizione());

        indirizzoImpresa.setCittaStraniera(null);
        indirizzoImpresa.setDenominazioneStradale(recapitoRiferimento.getIndirizzo());
        if (!Utils.e(recapitoRiferimento.getLocalita())) {
            indirizzoImpresa.setFrazione(recapitoRiferimento.getLocalita());
        } else {
            indirizzoImpresa.setFrazione(" ");
        }

        indirizzoImpresa.setNumeroCivico(recapitoRiferimento.getNCivico());
        indirizzoImpresa.setComune(comuneImpresa);
        indirizzoImpresa.setProvincia(provinciaImpresa);
        indirizzoImpresa.setStato(statoImpresa);

        ComunicazioneREA.EstremiPraticaSuap.Impresa impresa = new ComunicazioneREA.EstremiPraticaSuap.Impresa();
        impresa.setDenominazione(praticaAnagrafica.getAnagrafica().getDenominazione());
        impresa.setCodiceFiscale(praticaAnagrafica.getAnagrafica().getCodiceFiscale());
        if (praticaAnagrafica.getAnagrafica().getIdProvinciaCciaa() != null) {
            impresa.setProvinciaCciaaCompetente(praticaAnagrafica.getAnagrafica().getIdProvinciaCciaa().getCodCatastale());
        }
        impresa.setIndirizzo(indirizzoImpresa);

        return impresa;
    }

    private it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP serializeProtocolloPratica(Pratica pratica) throws Exception {
        PraticheEventi eventoRicezione = null;
        for (PraticheEventi praticaEvento : pratica.getPraticheEventiList()) {
            if (praticaEvento.getIdEvento().getCodEvento().equalsIgnoreCase(AnaTipiEvento.RICEZIONE_PRATICA)) {
                eventoRicezione = praticaEvento;
                break;
            }
        }
        return serializeProtocolloEvento(eventoRicezione);
    }

    private it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP serializeProtocolloEvento(PraticheEventi evento) throws Exception {
        it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloPraticaSuap = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
        Enti ente = evento.getIdPratica().getIdProcEnte().getIdEnte();
        //TODO: gestire il caso Comune su Ente
        GestioneProtocollo gestioneProtocollo = pluginService.getGestioneProtocollo(ente.getIdEnte(), null);
        if (gestioneProtocollo != null) {
            Protocollo protocolloPraticaDbBean = gestioneProtocollo.getProtocolloBean(evento);
            protocolloPraticaSuap.setCodiceAmministrazione(protocolloPraticaDbBean.getCodiceAmministrazione());
            protocolloPraticaSuap.setCodiceAoo(protocolloPraticaDbBean.getCodiceAoo());
            protocolloPraticaSuap.setDataRegistrazione(protocolloPraticaDbBean.getDataRegistrazione());
//            protocolloPraticaSuap.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(protocolloPraticaDbBean.getDataRegistrazione()));
            protocolloPraticaSuap.setNumeroRegistrazione(Utils.leadingDigitString(protocolloPraticaDbBean.getNumeroRegistrazione(), 0, 7));
        } else {
            protocolloPraticaSuap.setCodiceAmministrazione(evento.getIdPratica().getIdProcEnte().getIdEnte().getCodiceAmministrazione());
            protocolloPraticaSuap.setCodiceAoo(evento.getIdPratica().getIdProcEnte().getIdEnte().getCodiceAoo());
            protocolloPraticaSuap.setDataRegistrazione(evento.getDataEvento());
//            protocolloPraticaSuap.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(evento.getDataEvento()));
            String[] numeroRegistrazione = evento.getProtocollo().split("/");
            protocolloPraticaSuap.setNumeroRegistrazione(numeroRegistrazione[numeroRegistrazione.length - 1]);
        }
        return protocolloPraticaSuap;
    }

    private PraticaAnagrafica getPraticaAnagrafica(Pratica pratica, Anagrafica anagrafica) {
        for (PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
            if (praticaAnagrafica.getAnagrafica().equals(anagrafica)) {
                return praticaAnagrafica;
            }
        }
        return null;
    }

    private Recapiti getRecapitoPraticaAnagrafica(PraticaAnagrafica praticaAnagrafica) throws Exception {
        Recapiti recapitoAnagrafica;
        if (praticaAnagrafica.getIdRecapitoNotifica() != null) {
            recapitoAnagrafica = praticaAnagrafica.getIdRecapitoNotifica();
        } else {
            recapitoAnagrafica = anagraficheService.getRecapitoRiferimentoAnagrafica(praticaAnagrafica.getAnagrafica());
        }

        if (recapitoAnagrafica.getCap().length() != 5) {
            throw new Exception("Il cap del recapito deve essere valorizzato e deve essere ");
        }

        return recapitoAnagrafica;
    }

    private String escapeIdentificativoPratica(Pratica pratica) {
        return pratica.getIdentificativoPratica().replaceAll("-", "");
    }
// </editor-fold>
}
