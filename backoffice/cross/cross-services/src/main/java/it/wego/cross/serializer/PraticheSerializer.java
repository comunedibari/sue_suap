/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import com.google.common.base.Strings;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.StatoPraticaProtocollo;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.PraticaProtocolloDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.ProtocolloDTO;
import it.wego.cross.dto.comunica.DichiaranteDTO;
import it.wego.cross.dto.comunica.ImpresaDTO;
import it.wego.cross.dto.comunica.InterventoDTO;
import it.wego.cross.dto.comunica.LegaleRappresentanteDTO;
import it.wego.cross.dto.comunica.PraticaComunicaDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.NotePratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheDirittiSegreteria;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.IndirizziInterventoService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.mypage.syncronizer.stub.SetPraticaRequest;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.ContributiCosto;
import it.wego.cross.xml.DirittiSegreteria;
import it.wego.cross.xml.DirittiSegreteriaGaranzie;
import it.wego.cross.xml.DirittiSegreteriaGenerali;
import it.wego.cross.xml.DirittiSegreteriaRata;
import it.wego.cross.xml.DirittiSegreteriaRate;
import it.wego.cross.xml.OneriUrbanizzazione;
import it.wego.cross.xml.Procedimenti;
import it.wego.cross.xml.Procedimento;
import it.wego.cross.xml.Recapiti;
import it.wego.cross.xml.Recapito;
import it.wego.cross.xml.ServiziTariffaIndividuale;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component
public class PraticheSerializer {

    @Autowired
    private ConfigurationService configuration;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private DatiCatastaliSerializer datiCatastaliSerializer;
    @Autowired
    private EventiSerializer eventiSerializer;
    @Autowired
    private IndirizziInterventoService indirizziInterventoService;
    @Autowired
    private EntiService entiService;

    public it.wego.cross.xml.Pratica serialize(Pratica pratica, PraticheEventi ultimoEvento, Utente user) throws Exception {
        it.wego.cross.xml.Pratica xml = new it.wego.cross.xml.Pratica();
        xml.setIdPratica(Utils.bi(pratica.getIdPratica()));
        if (pratica.getIdProcEnte().getIdProc() != null) {
            xml.setIdProcedimentoSuap(pratica.getIdProcEnte().getIdProc().getCodProc());
        }
        xml.setIdentificativoPratica(pratica.getIdentificativoPratica());
        xml.setIdentificativoEsterno(pratica.getIdentificativoEsterno());
        xml.setOggetto(pratica.getOggettoPratica());
        xml.setResponsabileProcedimento(pratica.getResponsabileProcedimento());
        String istruttore = "";
        if (user != null) {
            istruttore = user.getCognome() + " " + user.getNome();
        }
        xml.setIstruttore(istruttore);

        //Termini di evasione
        //VECCHIA LOGICA NON PIU RISPONDENTE AI REQUISITI DI LEGGE
//        if (pratica.getPraticaProcedimentiList() != null && pratica.getPraticaProcedimentiList().size() > 0) {
//            int termini = 0;
//            for (it.wego.cross.entity.PraticaProcedimenti p : pratica.getPraticaProcedimentiList()) {
//                if (p.getProcedimenti() != null && p.getProcedimenti().getTermini() != null) {
//                    if (p.getProcedimenti().getTermini() > termini) {
//                        termini = p.getProcedimenti().getTermini();
//                    }
//                }
//            }
//            xml.setTerminiEvasionePratica(Utils.bi(termini));
//        }        
        xml.setTerminiEvasionePratica(Utils.bi(pratica.getIdProcEnte().getIdProc().getTermini()));

        //Recapito di notifica
        if (pratica.getIdRecapito() != null) {
            it.wego.cross.xml.Recapito notifica = RecapitiSerializer.serialize(pratica.getIdRecapito());
            notifica.setDesTipoIndirizzo(Constants.INDIRIZZO_NOTIFICA);
            xml.setNotifica(notifica);
        }

        //Comune di riferimento della pratica
        if (pratica.getIdComune() != null) {
            xml.setDesComune(pratica.getIdComune().getDescrizione());
            xml.setCodCatastaleComune(pratica.getIdComune().getCodCatastale());
        }

        //Ente collegato alla pratica
        if (pratica.getIdProcEnte().getIdEnte() != null) {
            xml.setDesEnte(pratica.getIdProcEnte().getIdEnte().getDescrizione());
            xml.setFaxEnte(pratica.getIdProcEnte().getIdEnte().getFax());
            xml.setEmailEnte(pratica.getIdProcEnte().getIdEnte().getEmail());
            xml.setIndirizzoEnte(pratica.getIdProcEnte().getIdEnte().getIndirizzo());
            xml.setPecEnte(pratica.getIdProcEnte().getIdEnte().getPec());
            xml.setTelefonoEnte(pratica.getIdProcEnte().getIdEnte().getTelefono());
            xml.setCapEnte(String.valueOf(pratica.getIdProcEnte().getIdEnte().getCap()));
            xml.setIdEnte(Utils.bi(pratica.getIdProcEnte().getIdEnte().getIdEnte()));
        }

        //Protocollo
        xml.setRegistro(pratica.getCodRegistro());
        xml.setProtocollo(pratica.getProtocollo());
// TODO: protocollo        
//        xml.setFascicolo(pratica.getCodFascicolo());
        xml.setAnno(String.valueOf(pratica.getAnnoRiferimento()));
        xml.setDataProtocollo(Utils.dateToXmlGregorianCalendar(pratica.getDataProtocollazione()));
        xml.setDataRicezione(Utils.dateToXmlGregorianCalendar(pratica.getDataRicezione()));

        //Anagrafiche
        if (pratica.getPraticaAnagraficaList() != null && pratica.getPraticaAnagraficaList().size() > 0) {
            xml.setAnagrafiche(new ArrayList<it.wego.cross.xml.Anagrafiche>());
            int counter = 0;
            for (it.wego.cross.entity.PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
                it.wego.cross.xml.Anagrafiche anagraficheXML = AnagraficheSerializer.serialize(praticaAnagrafica);
                anagraficheXML.getAnagrafica().setCounter(BigInteger.valueOf(counter));
                //Serializzo da banca dati in quanto ha gia passato la verifica di completezza
                anagraficheXML.getAnagrafica().setConfermata("true");
                xml.getAnagrafiche().add(anagraficheXML);
                counter++;
            }
        }

        //Dati catastali
        xml.setDatiCatastali(datiCatastaliSerializer.serialize(pratica));

        //Indirizzi Intervento
        xml.setIndirizziIntervento(indirizziInterventoService.serializeIndirizziIntervento(pratica));

        //Procedimenti
        xml.setProcedimenti(ProcedimentiSerializer.serialize(pratica));

        //Allegati
        xml.setAllegati(AllegatiSerializer.serialize(pratica));

        //Eventi
        xml.setEventi(eventiSerializer.serialize(pratica));
        if (ultimoEvento != null) {
            xml.setEventoCorrente(eventiSerializer.serialize(ultimoEvento));
        }

        //Scadenze
        if (pratica.getScadenzeList() != null && pratica.getScadenzeList().size() > 0) {
            xml.setScadenze(new it.wego.cross.xml.Scadenze());

            for (it.wego.cross.entity.Scadenze scadenza : pratica.getScadenzeList()) {
                if (scadenza.getIdStato().getGrpStatoScadenza().equals("A")) {
                    it.wego.cross.xml.Scadenza scadenzaXML = ScadenzeSerializer.serializeXML(scadenza);
                    xml.getScadenze().getScadenza().add(scadenzaXML);
                }
            }
        }

        //Dati da front
        if (pratica.getIdStaging() != null && pratica.getIdStaging().getXmlRicevuto() != null) {
            byte[] praticaRicevuta = Base64.encodeBase64(pratica.getIdStaging().getXmlRicevuto());
            xml.setDatiDaFront(new String(praticaRicevuta));
        }
        // stato pratica
        xml.setDataChiusura(Utils.dateToXmlGregorianCalendar(pratica.getDataChiusura()));
        if (pratica.getIdStatoPratica() != null) {
            xml.setIdStatoPratica(BigInteger.valueOf(pratica.getIdStatoPratica().getIdStatoPratica()));
            xml.setCodStatoPratica(pratica.getIdStatoPratica().getCodice());
            xml.setDesStatoPratica(pratica.getIdStatoPratica().getDescrizione());
        }
        //Enti
        xml.setEnti(entiService.serializeEnti(pratica));

        //Diritti segreteria
        if (pratica.getDirittiSegreteria() != null) {
            PraticheDirittiSegreteria ds = pratica.getDirittiSegreteria();
            DirittiSegreteria dsx = null;
            if (ds.getTarImportoConguaglio() != null
                    || ds.getTarImportoDovuto() != null
                    || ds.getTarImportoPagato() != null) {
                dsx = new DirittiSegreteria();
                ServiziTariffaIndividuale sti = new ServiziTariffaIndividuale();
                sti.setImportoConguaglio(ds.getTarImportoConguaglio());
                sti.setImportoDovuto(ds.getTarImportoDovuto());
                sti.setImportoPagato(ds.getTarImportoPagato());
                dsx.setServiziTariffaIndividuale(sti);
            }
            if (ds.getConBanca() != null
                    || ds.getConData() != null
                    || ds.getConImporto() != null
                    || ds.getConRataunoData() != null
                    || ds.getConRataunoDataPrevista() != null
                    || ds.getConRataunoImporto() != null
                    || ds.getConRatadueData() != null
                    || ds.getConRatadueDataPrevista() != null
                    || ds.getConRatadueImporto() != null
                    || ds.getConPagamentoUnicaSoluzione() != null
                    || ds.getConTotale() != null) {
                if (dsx == null) {
                    dsx = new DirittiSegreteria();
                }
                ContributiCosto cc = new ContributiCosto();
                if (ds.getConTotale() != null
                        || ds.getConPagamentoUnicaSoluzione() != null) {
                    DirittiSegreteriaGenerali dsg = new DirittiSegreteriaGenerali();
                    dsg.setTotale(ds.getConTotale());
                    if ("TRUE".equalsIgnoreCase(ds.getConPagamentoUnicaSoluzione())) {
                        dsg.setUnicaSoluzione("S");
                    }
                    cc.setDirittiSegreteriaGenerali(dsg);
                }
                if (ds.getConBanca() != null
                        || ds.getConData() != null
                        || ds.getConImporto() != null) {
                    DirittiSegreteriaGaranzie dsg = new DirittiSegreteriaGaranzie();
                    dsg.setBanca(ds.getConBanca());
                    dsg.setData(ds.getConData());
                    dsg.setImporto(ds.getConImporto());
                    cc.setDirittiSegreteriaGaranzie(dsg);
                }
                if (ds.getConRataunoData() != null
                        || ds.getConRataunoDataPrevista() != null
                        || ds.getConRataunoImporto() != null
                        || ds.getConRatadueData() != null
                        || ds.getConRatadueDataPrevista() != null
                        || ds.getConRatadueImporto() != null) {
                    DirittiSegreteriaRate dsr = new DirittiSegreteriaRate();
                    List<DirittiSegreteriaRata> rate = new ArrayList<DirittiSegreteriaRata>();
                    if (ds.getConRataunoData() != null
                            || ds.getConRataunoDataPrevista() != null
                            || ds.getConRataunoImporto() != null) {
                        DirittiSegreteriaRata dsra = new DirittiSegreteriaRata();
                        dsra.setDataPagamento(ds.getConRataunoData());
                        dsra.setDataPrevista(ds.getConRataunoDataPrevista());
                        dsra.setImporto(ds.getConRataunoImporto());
                        rate.add(dsra);
                    }
                    if (ds.getConRatadueData() != null
                            || ds.getConRatadueDataPrevista() != null
                            || ds.getConRatadueImporto() != null) {
                        DirittiSegreteriaRata dsra = new DirittiSegreteriaRata();
                        dsra.setDataPagamento(ds.getConRatadueData());
                        dsra.setDataPrevista(ds.getConRatadueDataPrevista());
                        dsra.setImporto(ds.getConRatadueImporto());
                        rate.add(dsra);
                    }
                    dsr.setDirittiSegreteriaRata(rate);
                    cc.setDirittiSegreteriaRate(dsr);
                }
                dsx.setContributiCosto(cc);
            }

            if (ds.getUrbBanca() != null
                    || ds.getUrbData() != null
                    || ds.getUrbImporto() != null
                    || ds.getUrbPagamentoUnicaSoluzione() != null
                    || ds.getUrbTotale() != null
                    || ds.getUrbRataunoData() != null
                    || ds.getUrbRataunoDataPrevista() != null
                    || ds.getUrbRataunoImporto() != null
                    || ds.getUrbRatadueData() != null
                    || ds.getUrbRatadueDataPrevista() != null
                    || ds.getUrbRatadueImporto() != null
                    || ds.getUrbRatatreData() != null
                    || ds.getUrbRatatreDataPrevista() != null
                    || ds.getUrbRatatreImporto() != null) {
                if (dsx == null) {
                    dsx = new DirittiSegreteria();
                }
                OneriUrbanizzazione ou = new OneriUrbanizzazione();
                if (ds.getUrbTotale() != null
                        || ds.getUrbPagamentoUnicaSoluzione() != null) {
                    DirittiSegreteriaGenerali dsg = new DirittiSegreteriaGenerali();
                    dsg.setTotale(ds.getUrbTotale());
                    if ("TRUE".equalsIgnoreCase(ds.getUrbPagamentoUnicaSoluzione())) {
                        dsg.setUnicaSoluzione("S");
                    }
                    ou.setDirittiSegreteriaGenerali(dsg);
                }
                if (ds.getUrbBanca() != null
                        || ds.getUrbData() != null
                        || ds.getUrbImporto() != null) {
                    DirittiSegreteriaGaranzie dsg = new DirittiSegreteriaGaranzie();
                    dsg.setBanca(ds.getUrbBanca());
                    dsg.setData(ds.getUrbData());
                    dsg.setImporto(ds.getUrbImporto());
                    ou.setDirittiSegreteriaGaranzie(dsg);
                }
                if (ds.getUrbRataunoData() != null
                        || ds.getUrbRataunoDataPrevista() != null
                        || ds.getUrbRataunoImporto() != null
                        || ds.getUrbRatadueData() != null
                        || ds.getUrbRatadueDataPrevista() != null
                        || ds.getUrbRatadueImporto() != null
                        || ds.getUrbRatatreData() != null
                        || ds.getUrbRatatreDataPrevista() != null
                        || ds.getUrbRatatreImporto() != null) {
                    DirittiSegreteriaRate dsr = new DirittiSegreteriaRate();
                    List<DirittiSegreteriaRata> rate = new ArrayList<DirittiSegreteriaRata>();
                    if (ds.getUrbRataunoData() != null
                            || ds.getUrbRataunoDataPrevista() != null
                            || ds.getUrbRataunoImporto() != null) {
                        DirittiSegreteriaRata dsra = new DirittiSegreteriaRata();
                        dsra.setDataPagamento(ds.getUrbRataunoData());
                        dsra.setDataPrevista(ds.getUrbRataunoDataPrevista());
                        dsra.setImporto(ds.getUrbRataunoImporto());
                        rate.add(dsra);
                    }
                    if (ds.getUrbRatadueData() != null
                            || ds.getUrbRatadueDataPrevista() != null
                            || ds.getUrbRatadueImporto() != null) {
                        DirittiSegreteriaRata dsra = new DirittiSegreteriaRata();
                        dsra.setDataPagamento(ds.getUrbRatadueData());
                        dsra.setDataPrevista(ds.getUrbRatadueDataPrevista());
                        dsra.setImporto(ds.getUrbRatadueImporto());
                        rate.add(dsra);
                    }
                    if (ds.getUrbRatatreData() != null
                            || ds.getUrbRatatreDataPrevista() != null
                            || ds.getUrbRatatreImporto() != null) {
                        DirittiSegreteriaRata dsra = new DirittiSegreteriaRata();
                        dsra.setDataPagamento(ds.getUrbRatatreData());
                        dsra.setDataPrevista(ds.getUrbRatatreDataPrevista());
                        dsra.setImporto(ds.getUrbRatatreImporto());
                        rate.add(dsra);
                    }
                    dsr.setDirittiSegreteriaRata(rate);
                    ou.setDirittiSegreteriaRate(dsr);
                }
                dsx.setOneriUrbanizzazione(ou);
            }
            xml.setDirittiSegreteria(dsx);
        }

        return xml;
    }

    public PraticaNuova serializePraticaNuova(Pratica pratica, Utente utenteConnesso) throws Exception {
        PraticaNuova nuova = new PraticaNuova();
        nuova.setDataRicezione(pratica.getDataRicezione());
        nuova.setDescPratica(pratica.getOggettoPratica());
        nuova.setEnte(pratica.getIdProcEnte().getIdEnte().getDescrizione());
        nuova.setIdPratica(String.valueOf(pratica.getIdPratica()));
        if (pratica.getIdUtente() != null) {
            String cognome = pratica.getIdUtente().getCognome() != null ? pratica.getIdUtente().getCognome() : "";
            String nome = pratica.getIdUtente().getNome() != null ? pratica.getIdUtente().getNome() : "";
            nuova.setInCarico(cognome + " " + nome);
            nuova.setCodiceUtente(pratica.getIdUtente().getIdUtente());
        }

        if (pratica.getProtocollo() != null && pratica.getAnnoRiferimento() != null) {
            nuova.setProtocollo(pratica.getProtocollo() + "/" + pratica.getAnnoRiferimento());
        } else {
            nuova.setProtocollo(pratica.getIdentificativoPratica());
        }

        if (StringUtils.isNotEmpty(pratica.getProtocollo())) {
            nuova.setFascicolo(pratica.getProtocollo() + "/" + pratica.getAnnoRiferimento());
        }
        nuova.setIdentificativoPratica(pratica.getIdentificativoPratica());
        nuova.setIdentificativoEsterno(pratica.getIdentificativoEsterno());
        nuova.setOggettoPratica(pratica.getOggettoPratica());
        if (pratica.getIdStatoPratica() != null) {
            nuova.setStatoPratica(pratica.getIdStatoPratica().getDescrizione());
        } else {
            Log.APP.error("ERRORE GRAVE: STATO PRATICA _= NULL id pratica = " + pratica.getIdPratica());
        }

        if (pratica.getIdComune() != null) {
            nuova.setComune(pratica.getIdComune().getDescrizione());
        }

        nuova.setRichiedente(getRichiedentiFromPratica(pratica));
        nuova.setRichiedenteDaXml(getRichiedentiFromPraticaDaXml(pratica));
        if (pratica.getStatoEmail() != null) {
            nuova.setIdStatoEmail(pratica.getStatoEmail().getIdStatiMail());
            nuova.setStatoEmail(pratica.getStatoEmail().getDescrizione());
            nuova.setCodStatoEmail(pratica.getStatoEmail().getCodice());
        }
        nuova.setIsSameUser(false);
        if (utenteConnesso != null) {
            boolean isAdminOnProcEnte = utentiService.isAdminOnProcEnte(utenteConnesso, pratica.getIdProcEnte());
            nuova.setAdminOnProcEnte(isAdminOnProcEnte);
            if (pratica.getIdUtente() != null && utenteConnesso.getIdUtente().equals(pratica.getIdUtente().getIdUtente())) {
                nuova.setIsSameUser(true);
            }
        }
        if(pratica.getIntegrazione() != null)
        	nuova.setIntegrazione(pratica.getIntegrazione());
        return nuova;
    }

    public PraticaProtocolloDTO serialize(PraticheProtocollo praticaProtocollo, Enti ente) throws Exception {

//        Pratica praticaDb = praticheService.findByFascicoloAnno(praticaProtocollo.getNFascicolo(), praticaProtocollo.getAnnoFascicolo(), ente.getIdEnte());
//        if (praticaDb != null) {
//            praticaProtocollo.setIdUtentePresaInCarico(praticaDb.getIdUtente());
//            usefulService.update(praticaProtocollo);
//        } else {
//            if (praticaProtocollo.getIdUtentePresaInCarico() == null) {
//                Utente utenteSystem = utentiService.findUtenteDaUsername("SYSTEM");
//                //Setto come utente di default SYSTEM, l'utente di sistema
//                praticaProtocollo.setIdUtentePresaInCarico(utenteSystem);
//                usefulService.update(praticaProtocollo);
//            }
//        }
        PraticaProtocolloDTO dto = new PraticaProtocolloDTO();
        dto.setDataPresaInCaricoCross(praticaProtocollo.getDataPresaInCaricoCross());
        dto.setDataRiferimento(praticaProtocollo.getDataProtocollazione());
        dto.setDataRicezione(praticaProtocollo.getDataRicezione());
        if (praticaProtocollo.getIdUtentePresaInCarico() != null) {
            String utente = praticaProtocollo.getIdUtentePresaInCarico().getCognome() + " " + praticaProtocollo.getIdUtentePresaInCarico().getNome();
            dto.setDesUtentePresaInCarico(utente);
            dto.setIdUtentePresaInCarico(praticaProtocollo.getIdUtentePresaInCarico().getIdUtente());
        }
        dto.setDestinatario(praticaProtocollo.getDestinatario());
        dto.setEnteRiferimento(praticaProtocollo.getEnteRiferimento());
        dto.setIdProtocollo(praticaProtocollo.getIdProtocollo());
        if (praticaProtocollo.getIdPratica() != null) {
            dto.setIdPratica(praticaProtocollo.getIdPratica().getIdPratica());
        }
        dto.setIdentificativoPratica(praticaProtocollo.getIdentificativoPratica());
        dto.setMittente(praticaProtocollo.getMittente());
        dto.setOggetto(praticaProtocollo.getOggetto());
        dto.setStato(praticaProtocollo.getStato());
        dto.setnFascicolo(praticaProtocollo.getNFascicolo());
        dto.setCodRegistro(praticaProtocollo.getCodRegistro());
        dto.setnProtocollo(praticaProtocollo.getNProtocollo());
        dto.setAnnoRiferimento(praticaProtocollo.getAnnoRiferimento());
        dto.setAnnoFascicolo(praticaProtocollo.getAnnoFascicolo());
        dto.setTipoDocumento(praticaProtocollo.getTipoDocumento());
        dto.setDescPraticaProtocollo(dto.getnProtocollo() + "/" + dto.getAnnoRiferimento());
        return dto;
    }

    public ProtocolloDTO serializeProtocollo(PraticheProtocollo praticaProtocollo) {
        ProtocolloDTO dto = new ProtocolloDTO();
        dto.setDataProtocollazione(praticaProtocollo.getDataProtocollazione());
        dto.setDataRicezione(praticaProtocollo.getDataRicezione());
        dto.setProtocollo(praticaProtocollo.getNProtocollo());
        dto.setOggetto(praticaProtocollo.getOggetto());
        dto.setAnno(praticaProtocollo.getAnnoRiferimento());
        //^^CS AGGIUNTA
        dto.setIdProtocollo(praticaProtocollo.getIdProtocollo());
        dto.setRegistro(praticaProtocollo.getCodRegistro());
        if (praticaProtocollo.getIdPratica() != null) {
            dto.setIdPratica(praticaProtocollo.getIdPratica().getIdPratica());
        }
        return dto;
    }
    /*
     * ^^CS AGGIUNTA 
     */

    public it.wego.cross.entity.NotePratica serialize(NotaDTO notaDTO) throws Exception {
        NotePratica notaDB = new NotePratica();

        notaDB.setDataInserimento(notaDTO.getDataInserimento());
        notaDB.setTesto(notaDTO.getTesto());
        return notaDB;
    }

    public NotaDTO serialize(it.wego.cross.entity.NotePratica notaDB) {
        NotaDTO notaDTO = new NotaDTO();
        notaDTO.setIdPratica(notaDB.getIdPratica().getIdPratica());
        notaDTO.setIdUtente(notaDB.getIdUtente().getIdUtente());
        notaDTO.setIdNota(notaDB.getIdNotePratica());
        notaDTO.setDesUtente(notaDB.getIdUtente().getCognome() + " " + notaDB.getIdUtente().getNome());
        notaDTO.setDataInserimento(notaDB.getDataInserimento());
        notaDTO.setTesto(notaDB.getTesto());
        if (notaDB.getTesto().length() > 24) {
            notaDTO.setTestoBreve(notaDB.getTesto().substring(0, 20) + " ...");
        } else {
            notaDTO.setTestoBreve(notaDB.getTesto());
        }
        return notaDTO;
    }

    public SetPraticaRequest serializeMyPage(it.wego.cross.xml.Pratica pratica, ProcessiEventi evento, Integer idEnte, Integer idComune) throws Exception {
        SetPraticaRequest pr = new SetPraticaRequest();
        pr.setIdPratica(pratica.getIdentificativoPratica());
        Log.APP.info("serializeMyPage: IdPratica " + pr.getIdPratica());
        pr.setCodiceNodo(configuration.getCachedConfiguration("MyPageSynchronizer.nodo", idEnte, idComune));
        Log.APP.info("serializeMyPage: CodiceNodo " + pr.getCodiceNodo());
        pr.setDataRicezione(pratica.getDataRicezione());
        pr.setDominio(configuration.getCachedConfiguration("MyPageSynchronizer.dominio", Utils.ib(pratica.getIdEnte()), null));
        Log.APP.info("serializeMyPage: Dominio " + pr.getDominio());
        pr.setIdBo(configuration.getCachedConfiguration("MyPageSynchronizer.idBo", Utils.ib(pratica.getIdEnte()), null));
        Log.APP.info("serializeMyPage: IdBo " + pr.getIdBo());
        pr.setUrlVisura(configuration.getCachedConfiguration("MyPageSynchronizer.url.visura", idEnte, idComune));
        Log.APP.info("serializeMyPage: UrlVisura " + pr.getUrlVisura());

        for (it.wego.cross.xml.Anagrafiche a : pratica.getAnagrafiche()) {
            if (a.getDesTipoRuolo().toUpperCase().equals("RICHIEDENTE")) {
                pr.setUseId(a.getAnagrafica().getCodiceFiscale());//CF
                Log.APP.info("serializeMyPage: UseId " + pr.getUseId());

                pr.setCognomeRichiedente(a.getAnagrafica().getCognome());
                Log.APP.info("serializeMyPage: CognomeRichiedente " + pr.getCognomeRichiedente());
                pr.setNomeRichiedente(a.getAnagrafica().getNome());
                Log.APP.info("serializeMyPage: NomeRichiedente " + pr.getNomeRichiedente());
                break;
            }
        }
        pr.setRuolo("Richiedente");
        Log.APP.info("serializeMyPage: Ruolo " + pr.getRuolo());
        pr.setContentName("Procedimento unico");
        Log.APP.info("serializeMyPage: ContentName " + pr.getContentName());
        pr.setDescrizioneEvento(evento.getDesEvento().toUpperCase());
        Log.APP.info("serializeMyPage: DescrizioneEvento " + pr.getDescrizioneEvento());
        pr.setDescrizioneProcesso(evento.getIdProcesso().getDesProcesso());
        Log.APP.info("serializeMyPage: DescrizioneProcesso" + pr.getDescrizioneProcesso());
        pr.setOggettoProcesso(pratica.getOggetto());
        Log.APP.info("serializeMyPage: OggettoProcesso " + pr.getOggettoProcesso());
        pr.setVisibilita(true);
        Log.APP.info("serializeMyPage: DataRicezione " + pr.getDataRicezione());

        return pr;
    }

    public PraticheProtocollo serializzaPratica(DocumentoProtocolloResponse doc, String modalita, Integer idEnte) throws NumberFormatException {
        PraticheProtocollo praticaProtocollo = new PraticheProtocollo();

//        Pratica praticaDb = praticheService.findByFascicoloAnno(doc.getNumeroProtocollo(), Integer.valueOf(doc.getAnnoProtocollo()));
        Pratica praticaDb = praticheService.findByFascicoloAnno(doc.getFascicolo(), Integer.valueOf(doc.getAnnoFascicolo()), idEnte);
        if (praticaDb != null) {
            praticaProtocollo.setIdUtentePresaInCarico(praticaDb.getIdUtente());
        }

// TODO: protocollo        
        if (!Strings.isNullOrEmpty(doc.getAnnoFascicolo())) {
            Log.SCHEDULER.info("Anno fascicolo: " + doc.getAnnoFascicolo());
            praticaProtocollo.setAnnoFascicolo(Integer.valueOf(doc.getAnnoFascicolo()));
        }
        Log.SCHEDULER.info("Anno di riferimento: " + doc.getAnnoProtocollo());
        praticaProtocollo.setAnnoRiferimento(Integer.valueOf(doc.getAnnoProtocollo()));
        praticaProtocollo.setModalita(modalita);
        Log.SCHEDULER.info("Registro: " + doc.getCodRegistro());
        praticaProtocollo.setCodRegistro(doc.getCodRegistro());
        Log.SCHEDULER.info("Data fascicolo: " + Utils.convertDataToString(doc.getDataCreazioneFascicolo()));
        praticaProtocollo.setDataFascicolo(doc.getDataCreazioneFascicolo());
// TODO: protocollo        
//        praticaProtocollo.setNFascicolo(doc.getFascicolo());
        Log.SCHEDULER.info("Numero protocollo: " + doc.getNumeroProtocollo());
        praticaProtocollo.setNProtocollo(doc.getNumeroProtocollo());
        Log.SCHEDULER.info("Oggetto: " + doc.getOggetto());
        praticaProtocollo.setOggetto(doc.getOggetto());
        //Utilizzo le informazioni di protocollazione per costruirmi l'identificativo della pratica
        String identificativoPratica = doc.getCodRegistro() + "/" + doc.getAnnoProtocollo() + "/" + doc.getNumeroProtocollo();
        Log.SCHEDULER.info("Identificativo pratica: " + identificativoPratica);
        praticaProtocollo.setIdentificativoPratica(identificativoPratica);
        Log.SCHEDULER.info("Tipo documento: " + doc.getTipoDocumento());
        praticaProtocollo.setTipoDocumento(doc.getTipoDocumento());
        Date dataRicezione = new Date();
        Log.SCHEDULER.info("Data ricezione: " + Utils.dateItalianFormat(dataRicezione));
        praticaProtocollo.setDataRicezione(dataRicezione);
        Log.SCHEDULER.info("Data protocollazione: " + Utils.dateItalianFormat(doc.getDataProtocollo()));
        praticaProtocollo.setDataProtocollazione(doc.getDataProtocollo());
        Log.SCHEDULER.info("Stato pratica: " + StatoPraticaProtocollo.RICEVUTA);
        praticaProtocollo.setStato(StatoPraticaProtocollo.RICEVUTA);
        Log.SCHEDULER.info("Destinatario: " + doc.getDestinatario());
        praticaProtocollo.setDestinatario(doc.getDestinatario());
        if (doc.getAllegatoOriginale() != null) {
            Log.SCHEDULER.info("Codice documento: " + doc.getAllegatoOriginale().getIdEsterno());
            praticaProtocollo.setCodDocumento(doc.getAllegatoOriginale().getIdEsterno());
        }
        Log.SCHEDULER.info("Setto la data sincronizzazione ad oggi");
        praticaProtocollo.setDataSincronizzazione(new Date());
        if (doc.getSoggetti() != null && !doc.getSoggetti().isEmpty()) {
            //Metto solo il primo e solo la denominazione (viene valorizzato solo questo campo)
            String mittente = doc.getSoggetti().get(0).getDenominazione();
            if (mittente != null && !mittente.isEmpty()) {
                praticaProtocollo.setMittente(mittente);
            }
        }
        praticaProtocollo.setNFascicolo(doc.getFascicolo());
//        praticaProtocollo.setModalita(identificativoPratica);
        return praticaProtocollo;
    }

    public it.wego.cross.xml.Pratica serialize(PraticaComunicaDTO dto) throws ParseException, DatatypeConfigurationException {
        it.wego.cross.xml.Pratica p = new it.wego.cross.xml.Pratica();
        p.setIdProcedimentoSuap(dto.getIdProcedimentoSuap().toString());
        String identificativoPratica = dto.getProtocollo().getRegistro() + "/" + dto.getProtocollo().getAnno() + "/" + dto.getProtocollo().getNumero();
        p.setIdentificativoPratica(identificativoPratica);
        p.setOggetto(dto.getOggetto());
        p.setDesComune(dto.getComuneRiferimento().getDescription());
        p.setIdEnte(Utils.bi(dto.getSportello().getId()));
        p.setDesEnte(dto.getSportello().getDescrizione());

        Procedimenti procs = new Procedimenti();
        for (InterventoDTO intervento : dto.getInterventi()) {
            Procedimento proc = new Procedimento();
            proc.setIdProcedimento(intervento.getId());
            proc.setIdEnteDestinatario(Utils.bi(intervento.getIdEnte()));
            proc.setDesProcedimento(intervento.getDescrizione());
            proc.setDesEnteDestinatario(intervento.getDescrizioneEnte());
            procs.getProcedimento().add(proc);
        }
        p.setProcedimenti(procs);
        List<Anagrafiche> anags = new ArrayList<Anagrafiche>();
        int counter = 1;
        if (dto.getDichiarante() != null) {
            DichiaranteDTO d = dto.getDichiarante();
            Anagrafiche dic = new Anagrafiche();
            LkTipoRuolo tipoRuolo = lookupService.findLkTipoRuoloByCodRuolo("R");
            dic.setIdTipoRuolo(Utils.bi(tipoRuolo.getIdTipoRuolo()));
            dic.setCodTipoRuolo(tipoRuolo.getCodRuolo());
            dic.setDesTipoRuolo(tipoRuolo.getDescrizione());
            it.wego.cross.xml.Anagrafica a = new it.wego.cross.xml.Anagrafica();
            a.setTipoAnagrafica("F");
            a.setVarianteAnagrafica("F");
            a.setNome(d.getNome());
            a.setCognome(d.getCognome());
            a.setCodiceFiscale(d.getCodiceFiscale());
            a.setPartitaIva(d.getPartitaIva());
            if (!Utils.e(d.getNazionalita())) {
                LkNazionalita nazionalita = lookupService.findLkNazionalitaById(Integer.valueOf(d.getNazionalita()));
                a.setIdNazionalita(Utils.bi(nazionalita.getIdNazionalita()));
                a.setDesNazionalita(nazionalita.getDescrizione());
            }
            Recapiti recapiti = new Recapiti();
            Recapito recapito = new Recapito();
            recapito.setDesTipoIndirizzo(Constants.INDIRIZZO_RESIDENZA);
            recapito.setCounter(BigInteger.ONE);
            recapito.setPec(d.getPec());
            recapiti.getRecapito().add(recapito);
            a.setRecapiti(recapiti);
            a.setCounter(Utils.bi(counter));
            counter++;
            dic.setAnagrafica(a);
            anags.add(dic);
        }
        if (dto.getLegaleRappresentante() != null) {
            LegaleRappresentanteDTO l = dto.getLegaleRappresentante();
            Anagrafiche leg = new Anagrafiche();
            LkTipoQualifica qualifica = lookupService.findLkTipoQualificaByCodQualificaAndCondizione("4", "RICHIEDENTE");
            leg.setIdTipoQualifica(Utils.bi(qualifica.getIdTipoQualifica()));
            leg.setDesTipoQualifica(qualifica.getDescrizione());
            LkTipoRuolo tipoRuolo = lookupService.findLkTipoRuoloByCodRuolo("B");
            leg.setIdTipoRuolo(Utils.bi(tipoRuolo.getIdTipoRuolo()));
            leg.setCodTipoRuolo(tipoRuolo.getCodRuolo());
            leg.setDesTipoRuolo(tipoRuolo.getDescrizione());
            it.wego.cross.xml.Anagrafica a = new it.wego.cross.xml.Anagrafica();
            a.setTipoAnagrafica("F");
            a.setVarianteAnagrafica("F");
            a.setNome(l.getNome());
            a.setCognome(l.getCognome());
            a.setCodiceFiscale(l.getCodiceFiscale());
            a.setPartitaIva(l.getPartitaIva());
            if (!Utils.e(l.getNazionalita())) {
                LkNazionalita nazionalita = lookupService.findLkNazionalitaById(Integer.valueOf(l.getNazionalita()));
                a.setIdNazionalita(Utils.bi(nazionalita.getIdNazionalita()));
                a.setDesNazionalita(nazionalita.getDescrizione());
            }
            Recapiti recapiti = new Recapiti();
            Recapito recapito = new Recapito();
            recapito.setCounter(BigInteger.ONE);
            recapito.setPec(l.getPec());
            recapito.setDesTipoIndirizzo(Constants.INDIRIZZO_RESIDENZA);
            recapiti.getRecapito().add(recapito);
            a.setRecapiti(recapiti);
            a.setCounter(Utils.bi(counter));
            counter++;
            leg.setAnagrafica(a);
            anags.add(leg);
        }
        if (dto.getImpresa() != null) {
            Anagrafiche imp = new Anagrafiche();
            ImpresaDTO i = dto.getImpresa();
            LkTipoRuolo tipoRuolo = lookupService.findLkTipoRuoloByCodRuolo("B");
            imp.setIdTipoRuolo(Utils.bi(tipoRuolo.getIdTipoRuolo()));
            imp.setCodTipoRuolo(tipoRuolo.getCodRuolo());
            imp.setDesTipoRuolo(tipoRuolo.getDescrizione());
            it.wego.cross.xml.Anagrafica a = new it.wego.cross.xml.Anagrafica();
            //TODO: gestire ditta individuale
            a.setTipoAnagrafica("G");
            a.setVarianteAnagrafica("G");
            a.setDenominazione(i.getRagioneSociale());
            a.setPartitaIva(i.getPartitaIva());
            a.setCodiceFiscale(i.getCodiceFiscale());
            if (!Utils.e(i.getNumeroRea())) {
                a.setFlgAttesaIscrizioneRea(Boolean.FALSE);
            }
            a.setIdProvinciaCciaa(BigInteger.valueOf(Integer.valueOf(i.getProvinciaRea())));
            a.setNIscrizioneRea(i.getNumeroRea());
            Date dataIscrizione = Utils.italianFormatToDate(i.getDataIscrizione());
            a.setDataIscrizioneRea(Utils.dateToXmlGregorianCalendar(dataIscrizione));
            a.setIdFormaGiuridica(Utils.bi(Integer.valueOf(i.getCodFormaGiuridica())));
            a.setDesFormaGiuridica(i.getDesFormaGiuridica());
            Recapiti recapiti = new Recapiti();
            //Sede
            Recapito recapito = new Recapito();
            recapito.setCounter(BigInteger.ONE);
            recapito.setPec(i.getPec());
            recapito.setDesTipoIndirizzo(Constants.INDIRIZZO_SEDE);
            recapito.setDesComune(i.getDesComuneSede());
            lookupService.findComuneByCodCatastale(i.getCodCatastaleComuneSede());
            recapiti.getRecapito().add(recapito);
            a.setRecapiti(recapiti);
            a.setCounter(Utils.bi(counter));
            counter++;
            imp.setAnagrafica(a);
            anags.add(imp);
        }
        p.setAnagrafiche(anags);
        return p;
    }

    public PraticaDTO serialize(Pratica p) {
        PraticaDTO dto = new PraticaDTO();
        dto.setIdPratica(p.getIdPratica());
        dto.setCodPratica(p.getIdPratica());
        dto.setIdentificativoPratica(p.getIdentificativoPratica());
        dto.setOggettoPratica(p.getOggettoPratica());
        dto.setProtocollo(p.getProtocollo());
        dto.setResponsabileProcedimento(p.getResponsabileProcedimento());
        dto.setDataApertura(p.getDataApertura());
        dto.setDataChiusura(p.getDataChiusura());
        dto.setDataRicezione(p.getDataRicezione());
        if (p.getIdProcesso() != null) {
            dto.setIdProcesso(p.getIdProcesso().getIdProcesso());
        }
        if (p.getIdStaging() != null) {
            dto.setCodStaging(p.getIdStaging().getIdStaging());
        }
        if (p.getIdRecapito() != null) {
            dto.setCodRecapito(p.getIdRecapito().getIdRecapito());
        }
        if (p.getIdStatoPratica() != null) {
            dto.setStato(p.getIdStatoPratica().getDescrizione());
        }
        List<ProcedimentoDTO> procedimentiList = new ArrayList<ProcedimentoDTO>();
        List<PraticaProcedimenti> procedimenti = p.getPraticaProcedimentiList();
        if (procedimenti != null && !procedimenti.isEmpty()) {
            for (PraticaProcedimenti proc : procedimenti) {
                ProcedimentoDTO procedimento = ProcedimentiSerializer.serialize(proc.getProcedimenti());
                procedimentiList.add(procedimento);
            }
        }
        dto.setProcedimentiList(procedimentiList);
        List<AnagraficaDTO> anagraficaList = new ArrayList<AnagraficaDTO>();
        List<PraticaAnagrafica> anagrafiche = p.getPraticaAnagraficaList();
        if (anagrafiche != null && !anagrafiche.isEmpty()) {
            for (PraticaAnagrafica pa : anagrafiche) {
                AnagraficaDTO ana = AnagraficheSerializer.serializeAnagrafica(pa.getAnagrafica());
                anagraficaList.add(ana);
            }
        }
        dto.setAnagraficaList(anagraficaList);
        if (p.getIdComune() != null) {
            ComuneDTO comune = LuoghiSerializer.serialize(p.getIdComune());
            dto.setComune(comune);
            dto.setIdComune(p.getIdComune().getIdComune());
        }
        dto.setIdEnte(p.getIdProcEnte().getIdEnte().getIdEnte());
        dto.setAnnoRiferimento(p.getAnnoRiferimento());
        dto.setRegistro(p.getCodRegistro());
// TODO: protocollo
        //dto.setFascicolo(p.getCodFascicolo());

        return dto;
    }

    public static String getRichiedentiFromPratica(Pratica pratica) throws Exception {
        List<String> beneficiari = new ArrayList<String>();
        List<String> richiedenti = new ArrayList<String>();
        if (pratica.getPraticaAnagraficaList() != null && pratica.getPraticaAnagraficaList().size() > 0) {
            for (PraticaAnagrafica pa : pratica.getPraticaAnagraficaList()) {
                if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                    richiedenti.add(pa.getAnagrafica().getCognome() + " " + pa.getAnagrafica().getNome());
                } else if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.BENEFICIARIO)) {
                    if (String.valueOf(pa.getAnagrafica().getTipoAnagrafica()).equalsIgnoreCase("F")) {
                        //Visualizzo la denominazione solo se partecipo alla pratica come ditta individuale
                        if ("S".equals(pa.getFlgDittaIndividuale()) && !Strings.isNullOrEmpty(pa.getAnagrafica().getDenominazione())) {
                            beneficiari.add(pa.getAnagrafica().getDenominazione());
                        } else {
                            beneficiari.add(pa.getAnagrafica().getCognome() + " " + pa.getAnagrafica().getNome());
                        }
                    } else {
                        beneficiari.add(pa.getAnagrafica().getDenominazione());
                    }
                }
            }
        } else {
            it.wego.cross.xml.Pratica praticaCross = PraticaUtils.getPraticaFromXML(new String(pratica.getIdStaging().getXmlPratica()));
            List<Anagrafiche> anagrafiche = PraticaUtils.getRichiedenteBeneficiari(praticaCross);
            if (anagrafiche != null && !anagrafiche.isEmpty()) {
                for (Anagrafiche anagrafica : anagrafiche) {
                    //TODO: non posso avere anagrafiche null o tipo anagrafica non valorizzata
                    if (anagrafica.getAnagrafica() != null && anagrafica.getAnagrafica().getTipoAnagrafica() != null && anagrafica.getAnagrafica().getTipoAnagrafica().equalsIgnoreCase("F")) {
                        if (anagrafica.getCodTipoRuolo() != null && anagrafica.getCodTipoRuolo().equals(TipoRuolo.BENEFICIARIO) || anagrafica.getDesTipoRuolo().equalsIgnoreCase("BENEFICIARIO")) {
                            if (anagrafica.getAnagrafica().getVarianteAnagrafica() == null) {
                                anagrafica.getAnagrafica().setVarianteAnagrafica("F");
                            }
                            if (anagrafica.getAnagrafica().getVarianteAnagrafica().equalsIgnoreCase("I") && anagrafica.getAnagrafica().getDenominazione() != null) {
                                beneficiari.add(anagrafica.getAnagrafica().getDenominazione());
                            } else {
                                beneficiari.add(anagrafica.getAnagrafica().getCognome() + " " + anagrafica.getAnagrafica().getNome());
                            }
                        } else {
                            richiedenti.add(anagrafica.getAnagrafica().getCognome() + " " + anagrafica.getAnagrafica().getNome());
                        }

                    } else {
                        if ((anagrafica.getCodTipoRuolo() != null && anagrafica.getCodTipoRuolo().equals(TipoRuolo.BENEFICIARIO)) || (anagrafica.getDesTipoRuolo() != null && anagrafica.getDesTipoRuolo().equalsIgnoreCase("BENEFICIARIO"))) {
                            beneficiari.add(anagrafica.getAnagrafica().getDenominazione());
                        } else {
                            richiedenti.add(anagrafica.getAnagrafica().getDenominazione());
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
		for (String richiedente : richiedenti) {
			if(!richiedente.isEmpty() && richiedente!=null){
				sb.append("R: "+richiedente).append("<br />");
			}          
		}
		
		for (String beneficiario : beneficiari) {
			if(!beneficiario.isEmpty() && beneficiario!=null){
				sb.append("B: "+beneficiario).append("<br />");
			}
		}
		
		//Aggiunto il 18/01/201
		for (PraticaAnagrafica tecnico: pratica.getPraticaAnagraficaList() ) {
			if(tecnico!=null && tecnico.getLkTipoRuolo().getCodRuolo().equalsIgnoreCase(TipoRuolo.PROFESSIONISTA)){
				sb.append("TP: "+tecnico.getAnagrafica().getCognome()+" "+tecnico.getAnagrafica().getNome()).append("<br />  ");
			}
		}
		return sb.toString();
    }

    public static String getRichiedentiFromPraticaDaXml(Pratica pratica) throws Exception {
        List<String> beneficiari = new ArrayList<String>();
        List<String> richiedenti = new ArrayList<String>();
        it.wego.cross.xml.Pratica praticaCross = PraticaUtils.getPraticaFromXML(new String(pratica.getIdStaging().getXmlPratica()));
        List<Anagrafiche> anagrafiche = PraticaUtils.getRichiedenteBeneficiari(praticaCross);
        if (anagrafiche != null && !anagrafiche.isEmpty()) {
            for (Anagrafiche anagrafica : anagrafiche) {
                //TODO: non posso avere anagrafiche null o tipo anagrafica non valorizzata
                if (anagrafica.getAnagrafica() != null && anagrafica.getAnagrafica().getTipoAnagrafica() != null && anagrafica.getAnagrafica().getTipoAnagrafica().equalsIgnoreCase("F")) {
                    if (anagrafica.getCodTipoRuolo() != null && anagrafica.getCodTipoRuolo().equals(TipoRuolo.BENEFICIARIO) || anagrafica.getDesTipoRuolo().equalsIgnoreCase("BENEFICIARIO")) {
                        if (anagrafica.getAnagrafica().getVarianteAnagrafica() == null) {
                            anagrafica.getAnagrafica().setVarianteAnagrafica("F");
                        }
                        if (anagrafica.getAnagrafica().getVarianteAnagrafica().equalsIgnoreCase("I") && anagrafica.getAnagrafica().getDenominazione() != null) {
                            beneficiari.add(anagrafica.getAnagrafica().getDenominazione());
                        } else {
                            beneficiari.add(anagrafica.getAnagrafica().getCognome() + " " + anagrafica.getAnagrafica().getNome());
                        }
                    } else {
                        richiedenti.add(anagrafica.getAnagrafica().getCognome() + " " + anagrafica.getAnagrafica().getNome());
                    }

                } else {
                    if ((anagrafica.getCodTipoRuolo() != null && anagrafica.getCodTipoRuolo().equals(TipoRuolo.BENEFICIARIO)) || (anagrafica.getDesTipoRuolo() != null && anagrafica.getDesTipoRuolo().equalsIgnoreCase("BENEFICIARIO"))) {
                        beneficiari.add(anagrafica.getAnagrafica().getDenominazione());
                    } else {
                        richiedenti.add(anagrafica.getAnagrafica().getDenominazione());
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
		for (String richiedente : richiedenti) {
			if(!richiedente.isEmpty() && richiedente!=null){
				sb.append("R: "+richiedente).append("<br />");
			}          
		}
		
		for (String beneficiario : beneficiari) {
			if(!beneficiario.isEmpty() && beneficiario!=null){
				sb.append("B: "+beneficiario).append("<br />");
			}
		}
		
		//Aggiunto il 15/01/2016
		List<Anagrafiche> tecnici = PraticaUtils.getTecniciDaXml(praticaCross);
		for (Anagrafiche tecnico : tecnici) {
			if(tecnico!=null){
				sb.append("TP: "+tecnico.getAnagrafica().getCognome()+" "+tecnico.getAnagrafica().getNome()).append("<br />  ");
			}
		}
        return sb.toString();
    }

}
