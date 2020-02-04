/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.dozer.converters.IdentificatoreProtocolloIstanzaConverter;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.client.clear.stubs.CodicePraticaSimo;
import it.wego.cross.webservices.client.clear.stubs.CodiceProtocollo;
import it.wego.cross.webservices.client.clear.stubs.CodiceProtocolloPrecedente;
import it.wego.cross.webservices.client.clear.stubs.Esito;
import it.wego.cross.webservices.client.clear.stubs.Evento;
import it.wego.cross.webservices.client.clear.stubs.EventoArray;
import it.wego.cross.webservices.client.clear.stubs.ObjectFactory;
import it.wego.cross.webservices.client.clear.stubs.PraticaSimoExtended;
import it.wego.cross.webservices.client.clear.stubs.Richiedente;
import it.wego.cross.webservices.client.clear.stubs.RichiedenteArray;
import it.wego.cross.webservices.client.clear.stubs.SiNo;
import it.wego.cross.webservices.client.clear.stubs.SimoAssociaEventoExtended;
import it.wego.cross.webservices.client.clear.stubs.SimoCreaProcedimento;
import it.wego.cross.webservices.client.clear.stubs.StatiEsito;
import it.wego.cross.webservices.client.clear.stubs.TipiSoggetto;
import it.wego.cross.webservices.client.clear.stubs.TipiStatoPost;
import it.wego.cross.webservices.client.clear.stubs.TipiVerso;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
@Component
public class ClearSerializer {

    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private AnagraficheService anagraficheService;
    ObjectFactory clearObjectFactory = new ObjectFactory();

    public SimoCreaProcedimento getProcedimentoClear(ProcedimentiEnti procedimentoEnteCross) {
        SimoCreaProcedimento procedimentoClear = new SimoCreaProcedimento();
        procedimentoClear.setCodice(procedimentoEnteCross.getIdProc().getCodProc());
        procedimentoClear.setDescrizione(procedimentiDao.getDescrizioneProcedimento(procedimentoEnteCross.getIdProc().getIdProc(), "it"));
        EventoArray eventi = clearObjectFactory.createEventoArray();

        procedimentoClear.setEventi(clearObjectFactory.createSimoCreaProcedimentoEventi(eventi));

        Evento eventoClear;
        for (ProcessiEventi eventoCross : procedimentoEnteCross.getIdProcesso().getProcessiEventiList()) {
            if (eventoCross.getStatoPost() != null) {
                eventoClear = new Evento();
                eventoClear.setCodice(eventoCross.getCodEvento());
                eventoClear.setDescrizione(eventoCross.getDesEvento());
                eventoClear.setFlgChiusura(eventoCross.getStatoPost().getGrpStatoPratica().equals("C") ? SiNo.S : SiNo.S);
                if (eventoCross.getStatoPost().getCodice().equalsIgnoreCase("S")) {
                    eventoClear.setStatoPostEvento(TipiStatoPost.S);
                } else if (eventoCross.getStatoPost().getGrpStatoPratica().equalsIgnoreCase("C")) {
                    eventoClear.setStatoPostEvento(TipiStatoPost.C);
                } else {
                    eventoClear.setStatoPostEvento(TipiStatoPost.A);
                }
                eventoClear.setFlgRiavvio(SiNo.N);
                eventoClear.setStatoPostEvento(TipiStatoPost.A);
                //non siamo in grado di recuperare questo campo da cross
                eventoClear.setTerminiEvasione(clearObjectFactory.createEventoTerminiEvasione(BigInteger.ZERO));

                eventi.getEvento().add(eventoClear);
            }
        }

        return procedimentoClear;
    }

    public PraticaSimoExtended getPraticaClear(Pratica praticaCross) throws Exception {
//        GestioneProtocollo gestioneProtocollo = pluginService.getGestioneProtocollo(praticaCross.getIdProcEnte().getIdEnte().getIdEnte(), null);

        PraticaSimoExtended praticaClear = new PraticaSimoExtended();
        praticaClear.setIdentificazionePratica(clearObjectFactory.createPraticaSimoIdentificazionePratica(praticaCross.getIdentificativoPratica()));
        praticaClear.setOggetto(praticaCross.getOggettoPratica());
        praticaClear.setVerso(TipiVerso.I);

        praticaClear.setResponsabileIstruttoria(clearObjectFactory.createPraticaSimoExtendedResponsabileIstruttoria(praticaCross.getIdUtente() == null ? "" : (praticaCross.getIdUtente().getCognome() + " " + praticaCross.getIdUtente().getNome())));
        praticaClear.setResponsabileProcedimento(clearObjectFactory.createPraticaSimoExtendedResponsabileProcedimento(StringUtils.isEmpty(praticaCross.getResponsabileProcedimento()) ? "ALTRO" : praticaCross.getResponsabileProcedimento()));
        praticaClear.setDataOraRicevimento(clearObjectFactory.createPraticaSimoExtendedDataOraRicevimento(Utils.dateToXmlGregorianCalendar(praticaCross.getDataRicezione())));

        PraticheEventi eventoRicezione = IdentificatoreProtocolloIstanzaConverter.getEventoRicezionePratica(praticaCross);
        praticaClear.setDataOraProtocollazione(clearObjectFactory.createPraticaSimoExtendedDataOraProtocollazione(Utils.dateToXmlGregorianCalendar(eventoRicezione.getDataEvento())));
//        Protocollo protocolloEventoRicezioneDbBean = gestioneProtocollo.getProtocolloBean(eventoRicezione);

        CodiceProtocollo codiceProtocollo = new CodiceProtocollo();
        codiceProtocollo.setProtReg("NOPROTO");
        codiceProtocollo.setProtNum(BigInteger.valueOf(Long.valueOf(praticaCross.getIdPratica())));
        codiceProtocollo.setProtAoo(praticaCross.getIdProcEnte().getIdEnte().getCodiceAoo());
        codiceProtocollo.setProtAnno(String.valueOf(praticaCross.getAnnoRiferimento()));
        praticaClear.setCodiceProtocollo(codiceProtocollo);

        RichiedenteArray richiedenteClear = new RichiedenteArray();
        praticaClear.setRichiedenti(clearObjectFactory.createPraticaSimoExtendedRichiedenti(richiedenteClear));

        Richiedente richiedenteClearLoop;
        Recapiti recapitoCrossLoop;
        for (PraticaAnagrafica praticaAnagrafica : praticaCross.getPraticaAnagraficaList()) {
            if (praticaAnagrafica.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                recapitoCrossLoop = anagraficheService.getRecapitoRiferimentoAnagrafica(praticaAnagrafica);

                richiedenteClearLoop = new Richiedente();
                richiedenteClearLoop.setCodicefiscalePartitaiva(praticaAnagrafica.getAnagrafica().getCodiceFiscale());

                if (!StringUtils.isEmpty(praticaAnagrafica.getAnagrafica().getDenominazione())) {
                    richiedenteClearLoop.setDenominazione(praticaAnagrafica.getAnagrafica().getDenominazione());
                } else {
                    richiedenteClearLoop.setDenominazione(praticaAnagrafica.getAnagrafica().getCognome() + " " + praticaAnagrafica.getAnagrafica().getNome());
                }
                richiedenteClearLoop.setComuneRichiedente(clearObjectFactory.createRichiedenteComuneRichiedente(recapitoCrossLoop.getIdComune().getCodCatastale()));
                richiedenteClearLoop.setIndirizzoRichiedente(clearObjectFactory.createRichiedenteIndirizzoRichiedente(recapitoCrossLoop.getIndirizzo() + " " + recapitoCrossLoop.getNCivico()));
                richiedenteClearLoop.setLocalitaRichiedente(clearObjectFactory.createRichiedenteLocalitaRichiedente(recapitoCrossLoop.getLocalita()));
                richiedenteClearLoop.setProvinciaRichiedente(recapitoCrossLoop.getIdComune().getIdProvincia().getCodCatastale());
                richiedenteClearLoop.setTipologiaSoggetto(TipiSoggetto.I);

                richiedenteClear.getRichiedente().add(richiedenteClearLoop);
            }

        }

        praticaClear.setCodiceProcedimentoRur(praticaCross.getIdProcEnte().getIdProc().getCodProc());
        praticaClear.setDataAvvioMonitoraggio(clearObjectFactory.createPraticaSimoExtendedDataAvvioMonitoraggio(Utils.dateToXmlGregorianCalendar(praticaCross.getDataRicezione())));
        praticaClear.setImportoRichiesto(clearObjectFactory.createPraticaSimoExtendedImportoRichiesto(BigDecimal.ZERO));
        praticaClear.setLocalizzazioni(clearObjectFactory.createPraticaSimoExtendedLocalizzazioni(null));
        praticaClear.setCodiceUfficio(clearObjectFactory.createPraticaSimoExtendedCodiceUfficio(praticaCross.getIdProcEnte().getIdEnte().getCodEnte()));
        if (praticaCross.getIdPraticaPadre() != null) {
//            PraticheEventi eventoRicezionePadre = getEventoRicezionePratica(praticaCross.getIdPraticaPadre());
//            Protocollo protocolloEventoRicezionePadreDbBean = gestioneProtocollo.getProtocolloBean(eventoRicezionePadre);
            CodiceProtocolloPrecedente pp = new CodiceProtocolloPrecedente();
            pp.setProtReg(clearObjectFactory.createCodiceProtocolloPrecedenteProtReg("NOPROTO"));
            pp.setProtNum(BigInteger.valueOf(Long.valueOf(praticaCross.getIdPraticaPadre().getIdPratica())));
            pp.setProtAoo(clearObjectFactory.createCodiceProtocolloPrecedenteProtReg(praticaCross.getIdPraticaPadre().getIdProcEnte().getIdEnte().getCodiceAoo()));
            pp.setProtAnno(clearObjectFactory.createCodiceProtocolloPrecedenteProtAnno(String.valueOf(praticaCross.getIdPraticaPadre().getAnnoRiferimento())));
            clearObjectFactory.createPraticaSimoExtendedCodiceProtocolloPrecedente(pp);
        } else {
            praticaClear.setCodiceProtocolloPrecedente(clearObjectFactory.createPraticaSimoExtendedCodiceProtocolloPrecedente(null));
        }
        praticaClear.setCodiceProtocolloPrecedente(clearObjectFactory.createPraticaSimoExtendedCodiceProtocolloPrecedente(null));

        return praticaClear;
    }

    public SimoAssociaEventoExtended getEventoClear(PraticheEventi eventoCross) throws Exception {
//        GestioneProtocollo gestioneProtocollo = pluginService.getGestioneProtocollo(eventoCross.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte(), null);

        SimoAssociaEventoExtended eventoClear = new SimoAssociaEventoExtended();

//        PraticheEventi eventoRicezione = getEventoRicezionePratica(eventoCross.getIdPratica());
//        Protocollo protocolloEventoRicezioneDbBean = gestioneProtocollo.getProtocolloBean(eventoRicezione);
        CodicePraticaSimo codicePraticaSimo = new CodicePraticaSimo();
        codicePraticaSimo.setProtAnnoSimo(String.valueOf(eventoCross.getIdPratica().getAnnoRiferimento()));
        codicePraticaSimo.setProtAooSimo(eventoCross.getIdPratica().getIdProcEnte().getIdEnte().getCodiceAoo());
        codicePraticaSimo.setProtNumSimo(BigInteger.valueOf(Long.valueOf(eventoCross.getIdPratica().getIdPratica())));
        codicePraticaSimo.setProtRegSimo("NOPROTO");
        eventoClear.setCodicePraticaSimo(codicePraticaSimo);
        eventoClear.setCodiceEvento(eventoCross.getIdEvento().getCodEvento());

//        if ("S".equalsIgnoreCase(eventoCross.getIdEvento().getFlgProtocollazione())) {
////            Protocollo protocolloEventoAttualeDbBean = gestioneProtocollo.getProtocolloBean(eventoCross);
//            CodiceProtocollo codiceProtocolloEvento = new CodiceProtocollo();
//            codiceProtocolloEvento.setProtAnno(protocolloEventoAttualeDbBean.getAnno());
//            codiceProtocolloEvento.setProtAoo(protocolloEventoAttualeDbBean.getCodiceAoo());
//            codiceProtocolloEvento.setProtNum(BigInteger.valueOf(Long.valueOf(protocolloEventoAttualeDbBean.getNumeroRegistrazione())));
//            codiceProtocolloEvento.setProtReg(protocolloEventoAttualeDbBean.getRegistro());
//            eventoClear.setCodiceProtocollo(codiceProtocolloEvento);
//        } else {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        CodiceProtocollo codiceProtocolloEvento = new CodiceProtocollo();
        codiceProtocolloEvento.setProtAnno(sdf.format(eventoCross.getDataEvento()));
        codiceProtocolloEvento.setProtAoo(eventoCross.getIdPratica().getIdProcEnte().getIdEnte().getCodiceAoo());
        codiceProtocolloEvento.setProtNum(BigInteger.valueOf(eventoCross.getIdPraticaEvento().intValue()));
        codiceProtocolloEvento.setProtReg("NOPROTO");
        eventoClear.setCodiceProtocollo(codiceProtocolloEvento);
//        }

        eventoClear.setAnnotazioniEsito(clearObjectFactory.createSimoAssociaEventoExtendedAnnotazioniEsito(""));
        eventoClear.setAnnotazioniEvento(eventoCross.getNote());
        eventoClear.setDataOraProtocollazione(Utils.dateToXmlGregorianCalendar(eventoCross.getDataEvento()));
        eventoClear.setDataOraRicevimento(Utils.dateToXmlGregorianCalendar(eventoCross.getDataEvento()));
        Esito esito = new Esito();
        esito.setEsito(StatiEsito.X);
        if (eventoCross.getIdEvento().getStatoPost().getGrpStatoPratica().equals("C")) {
            if (eventoCross.getIdEvento().getStatoPost().getCodice().equals("C")) {
                esito.setEsito(StatiEsito.A);
            }
            if (eventoCross.getIdEvento().getStatoPost().getCodice().equals("X")) {
                esito.setEsito(StatiEsito.R);
            }
        }
        eventoClear.setEsito(esito);
        eventoClear.setImportoFinanziato(clearObjectFactory.createSimoAssociaEventoExtendedImportoFinanziato(BigDecimal.ZERO));
        eventoClear.setVerso("I".equalsIgnoreCase(eventoCross.getVerso()) ? TipiVerso.I : TipiVerso.O);

        return eventoClear;
    }

}
