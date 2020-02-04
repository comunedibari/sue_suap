/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import it.wego.cross.constants.Constants;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AnagraficaRecapitoDTO;
import it.wego.cross.dto.AttoriComunicazioneDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.PraticheEventiRidDTO;
import it.wego.cross.dto.ProcessoEventoDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.ScadenzaEventoDTO;
import it.wego.cross.dto.dozer.PraticaEventoDTO;
import it.wego.cross.dto.dozer.ProcessoEventoAnagraficaDTO;
import it.wego.cross.dto.dozer.ProcessoEventoEnteDTO;
import it.wego.cross.dto.dozer.RecapitoAnagraficaDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiAnagrafica;
import it.wego.cross.entity.ProcessiEventiEnti;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component
public class EventiSerializer {

    @Autowired
    private AnagraficaUtils anagraficaUtils;
    @Autowired
    private EmailSerializer emailSerializer;
    @Autowired
    private Mapper mapper;

    public it.wego.cross.xml.Eventi serialize(Pratica pratica) throws DatatypeConfigurationException {
        it.wego.cross.xml.Eventi eventiXml = new it.wego.cross.xml.Eventi();
        it.wego.cross.xml.Evento eventoXml;

        for (PraticheEventi praticaEvento : pratica.getPraticheEventiList()) {
            eventoXml = serialize(praticaEvento);
            eventiXml.getEvento().add(eventoXml);
        }

        return eventiXml;
    }

    public it.wego.cross.xml.Evento serialize(PraticheEventi praticaEvento) throws DatatypeConfigurationException {
        ProcessiEventi evento = praticaEvento.getIdEvento();
        it.wego.cross.xml.Evento eventoXml = new it.wego.cross.xml.Evento();
        if (evento != null) {
            eventoXml.setIdEvento(Utils.bi(evento.getIdEvento()));
            eventoXml.setDescrizioneEvento(evento.getDesEvento());
        }
        eventoXml.setIdPraticaEvento(Utils.bi(praticaEvento.getIdPraticaEvento()));
        eventoXml.setDataEvento(Utils.dateToXmlGregorianCalendar(praticaEvento.getDataEvento()));
        if (praticaEvento.getIdUtente() != null) {
            eventoXml.setIdUtente(Utils.bi(praticaEvento.getIdUtente().getIdUtente()));
            eventoXml.setCognome(praticaEvento.getIdUtente().getCognome());
            eventoXml.setNome(praticaEvento.getIdUtente().getNome());
        }
        eventoXml.setNote(praticaEvento.getNote());
        eventoXml.setNumeroProtocollo(praticaEvento.getProtocollo());
        eventoXml.setVerso(praticaEvento.getVerso());
        eventoXml.setVisibilitaCross(praticaEvento.getVisibilitaCross());
        eventoXml.setVisibilitaUtente(praticaEvento.getVisibilitaUtente());

        it.wego.cross.xml.Allegati allegatiXml = new it.wego.cross.xml.Allegati();
        it.wego.cross.xml.Allegato allegatoXml;
        if (praticaEvento.getPraticheEventiAllegatiList() != null) {
            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                Allegati allegato = pea.getAllegati();
                allegatoXml = new it.wego.cross.xml.Allegato();
                allegatoXml.setIdAllegato(Utils.bi(allegato.getId()));
                allegatoXml.setDescrizione(allegato.getDescrizione());
                allegatoXml.setNomeFile(allegato.getNomeFile());
                allegatoXml.setTipoFile(allegato.getTipoFile());
                allegatoXml.setPathFile(allegato.getPathFile());
                allegatoXml.setIdFileEsterno(allegato.getIdFileEsterno());

                allegatiXml.getAllegato().add(allegatoXml);
            }
        }
        eventoXml.setAllegati(allegatiXml);

        return eventoXml;
    }

    public static PraticheEventiRidDTO getPraticheEventiRidDaPraticheEventi(PraticaEventoDTO pe) {
        PraticheEventiRidDTO result = new PraticheEventiRidDTO();
        result.setDataEvento(pe.getDataEvento());
        result.setDataProtocollo(pe.getDataProtocollo());
        if (pe.getIdUtente() != null) {
            result.setDenominazioneUtente(pe.getIdUtente().getCognome() + " " + pe.getIdUtente().getNome());
        }
        result.setDescrizioneEvento(pe.getDescrizioneEvento());
        result.setIdPraticaEvento(pe.getIdPraticaEvento());
        result.setProtocollo(pe.getProtocollo());
        if (pe.getStatoMail() != null) {
            result.setStatoMail(pe.getStatoMail().getDescrizione());
            result.setIdStatoMail(pe.getStatoMail().getIdStatiMail());
            result.setCodStatoMail(pe.getStatoMail().getCodice());
        }
        if ("I".equals(pe.getVerso())) {
            result.setVerso("Ingresso");
        } else if ("O".equals(pe.getVerso())) {
            result.setVerso("Uscita");
        } else {
            result.setVerso("Non specificato");
        }
        List<String> soggetti = new ArrayList<String>();
        if (pe.getEntiList() != null && !pe.getEntiList().isEmpty()) {
            for (it.wego.cross.dto.dozer.EnteDTO ente : pe.getEntiList()) {
                soggetti.add(ente.getDescrizione());
            }
        }
        if (pe.getRecapitoAnagraficaList() != null && !pe.getRecapitoAnagraficaList().isEmpty()) {
            for (RecapitoAnagraficaDTO dto : pe.getRecapitoAnagraficaList()) {
                if (dto.getAnagrafica() != null) {
                    it.wego.cross.dto.dozer.AnagraficaDTO anagrafica = dto.getAnagrafica();
                    soggetti.add(!Strings.isNullOrEmpty(anagrafica.getCognome()) ? anagrafica.getCognome() + " " + anagrafica.getNome() : anagrafica.getDenominazione());
                }
            }
        }
        result.setSoggetti(Joiner.on("<br />").join(soggetti));
        return result;
    }

    public EventoDTO serializeDettaglioEvento(PraticheEventi praticaEvento) throws Exception {
        //Essendo utilizzato anche per la comunicazione, vengono escluse le anagrafiche senza recapiti
        //quindi re-inserisco le anagrafiche complete
        EventoDTO dto = serializeEvento(praticaEvento);
        AttoriComunicazioneDTO destinatari = new AttoriComunicazioneDTO();
        List<AnagraficaRecapitoDTO> recapiti = getAnagraficheEvento(praticaEvento.getPraticheEventiAnagraficheList());
        destinatari.setAnagrafiche(recapiti);
        destinatari.setEnti(dto.getDestinatari().getEnti());
        destinatari.setNotifica(dto.getDestinatari().getNotifica());
        dto.setDestinatari(destinatari);
        return dto;
    }

    public EventoDTO serializeEvento(PraticheEventi praticaEvento) throws Exception {
        EventoDTO e = new EventoDTO();
        //Forzo oggetto e contenuto
        e.setOggetto("OGGETTO DELLA COMUNICAZIONE");
        e.setContenuto("CONTENUTO DELLA EMAIL DA INVIARE\nIl testo &egrave; in html.\nCordiali saluti");
        //FINE
        e.setIdPratica(praticaEvento.getIdPratica().getIdPratica());
        e.setIdPraticaEvento(praticaEvento.getIdPraticaEvento());
        AttoriComunicazioneDTO destinatari = new AttoriComunicazioneDTO();
        List<AnagraficaRecapitoDTO> recapiti = getAnagraficheRecapito(praticaEvento.getPraticheEventiAnagraficheList());
        destinatari.setAnagrafiche(recapiti);
        List<EnteDTO> enti = getEnti(praticaEvento.getEntiList());
        destinatari.setEnti(enti);
        if (praticaEvento.getIdRecapitoNotifica() != null) {
            RecapitoDTO rec = new RecapitoDTO();
            RecapitoUtils.copyEntity2Recapito(praticaEvento.getIdRecapitoNotifica(), rec);
            destinatari.setNotifica(rec);
        }
        e.setNumProtocollo(praticaEvento.getProtocollo());
        e.setDataProtocollo(praticaEvento.getDataProtocollo());
        e.setDestinatari(destinatari);
        if (praticaEvento.getIdUtente() != null) {
            String operatore = praticaEvento.getIdUtente().getCognome() + " " + praticaEvento.getIdUtente().getNome();
            e.setOperatore(operatore);
        }
        if (praticaEvento.getDataEvento() != null) {
            e.setDataEvento(Utils.dateItalianFormat(praticaEvento.getDataEvento()));
        }
        if (praticaEvento.getIdUtente() != null) {
            e.setIdUtente(praticaEvento.getIdUtente().getIdUtente());
            e.setUsername(praticaEvento.getIdUtente().getUsername());
        }
        e.setNote(praticaEvento.getNote());

        if (praticaEvento.getIdEvento() != null) {
            e.setIdEvento(praticaEvento.getIdEvento().getIdEvento());
            e.setDescrizione(praticaEvento.getIdEvento().getDesEvento());
            e.setStatoPost(praticaEvento.getIdEvento().getStatoPost());
            //TODO: gestione scadenza custom
//            e.setVisualizzaGiorniScadenzaCustom(pe.getIdEvento().getFlgVisualizzaScadenzaCustom());
            if (praticaEvento.getIdEvento().getIdTipoMittente() != null) {
                e.setIdTipoMittente(praticaEvento.getIdEvento().getIdTipoMittente().getIdTipoAttore());
                e.setMittente(praticaEvento.getIdEvento().getIdTipoMittente().getDesTipoAttore());
            }
            if (praticaEvento.getIdEvento().getIdTipoDestinatario() != null) {
                e.setIdTipoDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getIdTipoAttore());
                e.setDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getDesTipoAttore());
            }
//            if (pe.getIdEvento().getVerso() != null) {
//                e.setVerso(pe.getIdEvento().getVerso().toString());
//            }
            e.setIdScript(praticaEvento.getIdEvento().getIdScriptEvento());
            e.setPubblicazionePortale(praticaEvento.getIdEvento().getFlgPortale());

            e.setInvioEmail(praticaEvento.getIdEvento().getFlgMail());
            e.setAllegatoEmail(praticaEvento.getIdEvento().getFlgAllMail());
            e.setProtocollo(praticaEvento.getIdEvento().getFlgProtocollazione());
            e.setScaricaRicevuta(praticaEvento.getIdEvento().getFlgRicevuta());
            e.setMostraDestinatari(praticaEvento.getIdEvento().getFlgDestinatari());
            e.setCaricaDocumentoFirmato(praticaEvento.getIdEvento().getFlgFirmato());

            e.setApriSottopratiche(praticaEvento.getIdEvento().getFlgApriSottopratica());
            e.setDestinatariSoloEnti(praticaEvento.getIdEvento().getFlgDestinatariSoloEnti());
            e.setVisualizzaProcedimentiRiferimento(praticaEvento.getIdEvento().getFlgVisualizzaProcedimenti());
            e.setProcedimentoRiferimento(praticaEvento.getIdEvento().getIdProcedimentoRiferimento());
            e.setNumeroMassimoDestinatari(praticaEvento.getIdEvento().getMaxDestinatari());

            if (praticaEvento.getIdEvento().getIdTipoDestinatario() != null) {
                e.setIdTipoDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getIdTipoAttore());
                e.setDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getDesTipoAttore());
            }
            //Il verso di un evento Pratica è il verso di evento_pratica non dell'evento collegato...che puo cambiare nel tempo.
            if (praticaEvento.getIdEvento().getVerso() != null) {
                e.setVerso(praticaEvento.getVerso());
            }
            e.setFunzioneApplicativa(praticaEvento.getIdEvento().getFunzioneApplicativa());
        } else {
            e.setPubblicazionePortale(praticaEvento.getVisibilitaUtente());
        }

        if (praticaEvento.getPraticheEventiAllegatiList() != null) {
            List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                Allegati allegato = pea.getAllegati();
                AllegatoDTO al = new AllegatoDTO();
                al.setDescrizione(allegato.getDescrizione());
                al.setIdAllegato(allegato.getId());
                al.setNomeFile(allegato.getNomeFile());
                al.setTipoFile(allegato.getTipoFile());
                allegati.add(al);
            }
            e.setAllegati(allegati);
        }

        /**
         * ^^CS AGGIUNTA
         */
//////////        if (praticaEvento.getStatoMail() != null) {
//////////            /*
//////////             if (!pe.getStatoMail().getCodice().equals(StatiEmail.CONFERMATA)) {
//////////             e.setStatoEmail(true);
//////////             } else {
//////////             e.setStatoEmail(false);
//////////             }
//////////             */
////////////            e.setStatoEmail(pe.getStatoMail().getCodice());
//////////            List<Email> emailPerEvento = praticaEvento.getEmailList();
//////////            String statoEmail = "";
//////////            for (Email email : emailPerEvento) {
//////////                if (email.getStato().getCodice().equals(StatiEmail.ERRORE_GENERICO)
//////////                        || email.getStato().getCodice().equals(StatiEmail.ERRORE_SERVER)) {
//////////                    statoEmail = email.getStato().getCodice();
//////////                    break;
//////////                } else {
//////////                    statoEmail = email.getStato().getCodice();
//////////                }
//////////            }
//////////            e.setStatoEmail(statoEmail);
//////////        }
        if (praticaEvento.getEmailList() != null && praticaEvento.getEmailList().size() > 0) {
            e.setEmail(emailSerializer.serialize(praticaEvento.getEmailList()));
        }
        return e;
    }

    public EventoDTO serializeEvento(ProcessiEventi pe) {
        EventoDTO evento = new EventoDTO();
        evento.setIdEvento(pe.getIdEvento());
        evento.setDescrizione(pe.getDesEvento());
        evento.setStatoPost(pe.getStatoPost());
        evento.setIdProcesso(pe.getIdProcesso().getIdProcesso());
        //TODO: gestione scadenza custom
//        evento.setVisualizzaGiorniScadenzaCustom(pe.getFlgVisualizzaScadenzaCustom());
        if (pe.getIdTipoMittente() != null) {
            evento.setIdTipoMittente(pe.getIdTipoMittente().getIdTipoAttore());
            evento.setMittente(pe.getIdTipoMittente().getDesTipoAttore());
        }
        if (pe.getIdTipoDestinatario() != null) {
            evento.setIdTipoDestinatario(pe.getIdTipoDestinatario().getIdTipoAttore());
            evento.setDestinatario(pe.getIdTipoDestinatario().getDesTipoAttore());
        }
        if (pe.getVerso() != null) {
            evento.setVerso(pe.getVerso().toString());
        }
        evento.setIdScript(pe.getIdScriptEvento());
        evento.setPubblicazionePortale(pe.getFlgPortale() != null ? pe.getFlgPortale() : "N");
        evento.setInvioEmail(pe.getFlgMail() != null ? pe.getFlgMail() : "N");
        evento.setAllegatoEmail(pe.getFlgAllMail() != null ? pe.getFlgAllMail() : "N");
        evento.setProtocollo(pe.getFlgProtocollazione() != null ? pe.getFlgProtocollazione() : "N");
        evento.setScaricaRicevuta(pe.getFlgRicevuta() != null ? pe.getFlgRicevuta() : "N");
        evento.setMostraDestinatari(pe.getFlgDestinatari() != null ? pe.getFlgDestinatari() : "N");
        evento.setCaricaDocumentoFirmato(pe.getFlgFirmato() != null ? pe.getFlgFirmato() : "N");
        //TODO: gestire flag per visualizzare le scadenze
//        evento.setVisualizzaScadenzeDaChiudere(pe.get);
        evento.setApriSottopratiche(pe.getFlgApriSottopratica() != null ? pe.getFlgApriSottopratica() : "N");
        evento.setDestinatariSoloEnti(pe.getFlgDestinatariSoloEnti() != null ? pe.getFlgDestinatariSoloEnti() : "N");
        evento.setVisualizzaProcedimentiRiferimento(pe.getFlgVisualizzaProcedimenti() != null ? pe.getFlgVisualizzaProcedimenti() : "N");
        evento.setProcedimentoRiferimento(pe.getIdProcedimentoRiferimento());
        evento.setNumeroMassimoDestinatari(pe.getMaxDestinatari());
        //TODO: correggi report
//        evento.setMimeTypeFileGenerato(pe.getMimeTypeFileGenerato());
//        evento.setNomeFileGenerato(pe.getNomeFileGenerato());

        return evento;
    }

    /**
     *
     * @param idEvento
     * @return
     */
    public EventoDTO serializer(ProcessiEventi idEvento) {

        EventoDTO eventoDTO = new EventoDTO();
        if (idEvento != null) {
            eventoDTO.setDescrizione(idEvento.getDesEvento());
            eventoDTO.setIdEvento(idEvento.getIdEvento());
        }
        return eventoDTO;
    }

    public ProcessoEventoDTO serializeProcessoEvento(ProcessiEventi pe) {
        ProcessoEventoDTO dto = new ProcessoEventoDTO();
        dto.setIdEvento(pe.getIdEvento());
        if (pe.getIdProcesso() != null) {
            dto.setIdProcesso(pe.getIdProcesso().getIdProcesso());
        }
        dto.setCodiceEvento(pe.getCodEvento());
        dto.setDescrizioneEvento(pe.getDesEvento());
        if (pe.getStatoPost() != null) {
            dto.setStatoPost(pe.getStatoPost().getIdStatoPratica());
        }
        if (pe.getIdTipoMittente() != null) {
            dto.setTipoMittente(pe.getIdTipoMittente().getIdTipoAttore());
        }
        if (pe.getIdTipoDestinatario() != null) {
            dto.setTipoDestinatario(pe.getIdTipoDestinatario().getIdTipoAttore());
        }
        dto.setScriptScadenzaEvento(pe.getScriptScadenzaEvento());
        if (pe.getVerso() != null) {
            dto.setVerso(pe.getVerso().toString());
        }
        dto.setFlgPortale(pe.getFlgPortale());
        dto.setFlgMail(pe.getFlgMail());
        dto.setFlgAllegatiEmail(pe.getFlgAllMail());
        dto.setFlgProtocollazione(pe.getFlgProtocollazione());
        dto.setFlgRicevuta(pe.getFlgRicevuta());
        dto.setFlgDestinatari(pe.getFlgDestinatari());
        dto.setFlgFirmato(pe.getFlgFirmato());
        dto.setFlgApriSottoPratica(pe.getFlgApriSottopratica());
        dto.setFlgDestinatariSoloEnti(pe.getFlgDestinatariSoloEnti() != null ? pe.getFlgDestinatariSoloEnti() : "N");
        dto.setFlgVisualizzaProcedimenti(pe.getFlgVisualizzaProcedimenti() != null ? pe.getFlgVisualizzaProcedimenti() : "N");
        if (pe.getIdProcedimentoRiferimento() != null) {
            dto.setIdProcedimentoRiferimento(pe.getIdProcedimentoRiferimento().getIdProc());
        }
        dto.setScriptEvento(pe.getIdScriptEvento());
        dto.setScriptProtocollo(pe.getIdScriptProtocollo());
        dto.setMaxDestinatari(pe.getMaxDestinatari());
        if (pe.getOggettoEmail() != null) {
            byte[] oggetto = Base64.decodeBase64(pe.getOggettoEmail());
            dto.setOggettoMail(new String(oggetto));
        }
        if (pe.getCorpoEmail() != null) {
            byte[] corpo = Base64.decodeBase64(pe.getCorpoEmail());
            dto.setCorpoMail(new String(corpo));
        }
        dto.setFunzioneApplicativa(pe.getFunzioneApplicativa());
        dto.setFlgAutomatico(pe.getFlgAutomatico());
        List<ProcessiEventiScadenze> scadenzeEvento = pe.getProcessiEventiScadenzeList();
        if (scadenzeEvento != null && !scadenzeEvento.isEmpty()) {
            List<ScadenzaEventoDTO> scadenze = new ArrayList<ScadenzaEventoDTO>();
            for (ProcessiEventiScadenze scadenza : scadenzeEvento) {
                ScadenzaEventoDTO scadenzaDto = serializeScadenzaProcesso(scadenza);
                scadenze.add(scadenzaDto);
            }
            dto.setScadenze(scadenze);
        }
        List<ProcessiEventiAnagrafica> processiEventiAnagrafica = pe.getProcessiEventiAnagraficaList();
        if (processiEventiAnagrafica != null && !processiEventiAnagrafica.isEmpty()) {
            List<ProcessoEventoAnagraficaDTO> destinatari = new ArrayList<ProcessoEventoAnagraficaDTO>();
            List<ProcessoEventoAnagraficaDTO> inoltro = new ArrayList<ProcessoEventoAnagraficaDTO>();
            for (ProcessiEventiAnagrafica peAnagrafica : processiEventiAnagrafica) {
                    ProcessoEventoAnagraficaDTO p = mapper.map(peAnagrafica.getProcessiEventiAnagraficaPK(), ProcessoEventoAnagraficaDTO.class);
                    mapper.map(peAnagrafica.getAnagrafica(), p);
                    destinatari.add(p);
            }
            dto.setAnagraficaDestinatari(destinatari);
        }
        List<ProcessiEventiEnti> processiEventiEnti = pe.getProcessiEventiEntiList();
        if (processiEventiEnti != null && !processiEventiEnti.isEmpty()) {
            List<ProcessoEventoEnteDTO> destinatari = new ArrayList<ProcessoEventoEnteDTO>();
            List<ProcessoEventoEnteDTO> inoltro = new ArrayList<ProcessoEventoEnteDTO>();
            for (ProcessiEventiEnti peEnte : processiEventiEnti) {
                    ProcessoEventoEnteDTO p = mapper.map(peEnte.getProcessiEventiEntiPK(), ProcessoEventoEnteDTO.class);
                    mapper.map(peEnte.getEnti(), p);
                    destinatari.add(p);
            }
            dto.setEnteDestinatari(destinatari);
        }    
        dto.setForzaChiusuraScadenze(pe.getForzaChiusuraScadenze());
        return dto;
    }

    public ScadenzaEventoDTO serializeScadenzaProcesso(ProcessiEventiScadenze scadenza) {
        ScadenzaEventoDTO dto = new ScadenzaEventoDTO();
        if (scadenza.getProcessiEventi() != null) {
            dto.setIdEvento(scadenza.getProcessiEventi().getIdEvento());
        }
        dto.setFlgVisualizzaScadenza(scadenza.getFlgVisualizzaScadenza());
        if (scadenza.getLkScadenze() != null) {
            dto.setIdAnaScadenza(scadenza.getLkScadenze().getIdAnaScadenze());
        }
        if (scadenza.getIdStatoScadenza() != null) {
            dto.setIdStatoScadenza(scadenza.getIdStatoScadenza().getIdStato());
        }
        dto.setScriptScadenza(scadenza.getScriptScadenza());
        dto.setTerminiScadenza(scadenza.getTerminiScadenza());
        return dto;
    }

    private List<AnagraficaRecapitoDTO> getAnagraficheRecapito(List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList) throws Exception {
        List<AnagraficaRecapitoDTO> recapiti = new ArrayList<AnagraficaRecapitoDTO>();
        if (praticheEventiAnagraficheList != null && !praticheEventiAnagraficheList.isEmpty()) {
            for (PraticheEventiAnagrafiche pea : praticheEventiAnagraficheList) {
                //TODO: togliere le entity dalle jsp
                AnagraficaRecapitoDTO ar = new AnagraficaRecapitoDTO();
                AnagraficaDTO an = new AnagraficaDTO();
                anagraficaUtils.copyEntity2Anagrafica(pea.getAnagrafica(), an);
                ar.setAnagrafica(an);
                RecapitoDTO rec = new RecapitoDTO();
                if (pea.getIdRecapito() != null) {
                    RecapitoUtils.copyEntity2Recapito(pea.getIdRecapito(), rec);
                    ar.setRecapito(rec);
                    recapiti.add(ar);
                }
            }
        }
        return recapiti;
    }

    private List<EnteDTO> getEnti(List<Enti> entiList) {
        List<EnteDTO> enti = new ArrayList<EnteDTO>();
        if (entiList != null) {
            for (Enti e : entiList) {
                EnteDTO en = EntiSerializer.serializer(e);
                enti.add(en);
            }
        }
        return enti;
    }

    private List<AnagraficaRecapitoDTO> getAnagraficheEvento(List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList) throws Exception {
        List<AnagraficaRecapitoDTO> recapiti = new ArrayList<AnagraficaRecapitoDTO>();
        if (praticheEventiAnagraficheList != null && !praticheEventiAnagraficheList.isEmpty()) {
            for (PraticheEventiAnagrafiche pea : praticheEventiAnagraficheList) {
                //TODO: togliere le entity dalle jsp
                AnagraficaRecapitoDTO ar = new AnagraficaRecapitoDTO();
                AnagraficaDTO an = new AnagraficaDTO();
                anagraficaUtils.copyEntity2Anagrafica(pea.getAnagrafica(), an);
                ar.setAnagrafica(an);
                RecapitoDTO rec = new RecapitoDTO();
                if (pea.getIdRecapito() != null) {
                    RecapitoUtils.copyEntity2Recapito(pea.getIdRecapito(), rec);
                    ar.setRecapito(rec);
                }
                recapiti.add(ar);
            }
        }
        return recapiti;
    }
}
