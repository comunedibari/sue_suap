/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.collect.ImmutableMap;
import static it.wego.cross.actions.SystemEventAction.SYSTEM_EVENTS;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaCommercioDTO;
import it.wego.cross.events.anagrafetributaria.bean.AnagrafeTributariaDTO;
import it.wego.cross.events.anagrafetributaria.entity.AtRecordDettaglio;
import it.wego.cross.events.anagrafetributaria.serializer.AnagraficaTributariaSerializer;
import it.wego.cross.events.anagrafetributaria.service.AnagrafeTributariaService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.anagrafetributaria.edilizia.IdentificazioneRichiesta;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Giuseppe
 */
@Component
public class AnagrafeTributariaAction {

    @Autowired
    private AnagraficaTributariaSerializer serializer;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagrafeTributariaService anagrafeTributariaService;
    @Autowired
    private ProcessiService processiService;

    public static final String EVENTO_ANAGRAFE_TRIBUTARIA = "PREP_ANA_TRIB";

    public static final ImmutableMap<String, String> ANAGRAFE_TRIBUTARIA_EVENTS
            = new ImmutableMap.Builder<String, String>()
            .put(EVENTO_ANAGRAFE_TRIBUTARIA, "Preparazione dati per anagrafe tributaria")
            .build();

    @Transactional(rollbackFor = Exception.class)
    public void salvaRecordCommercio(Integer idPratica, AnagrafeTributariaCommercioDTO anagrafeTributaria, Utente utenteConnesso) throws Exception {
        AtRecordDettaglio recordDettaglio;
        Pratica pratica = praticheService.getPratica(idPratica);
        if (anagrafeTributaria.getIdDettaglio() != null) {
            recordDettaglio = anagrafeTributariaService.findRecordDettaglioById(anagrafeTributaria.getIdDettaglio());
        } else {
            recordDettaglio = new AtRecordDettaglio();
        }
        recordDettaglio.setAnnoRiferimento(Calendar.getInstance().get(Calendar.YEAR));
        recordDettaglio.setDataInserimento(new Date());
        recordDettaglio.setIdPratica(pratica);
        recordDettaglio.setCodFornitura(anagrafeTributaria.getCodFornitura());
        it.wego.cross.xml.anagrafetributaria.commercio.RecordDettaglio dettaglio = serializer.serializeRecordDettaglioCommercio(anagrafeTributaria, pratica);
        String record = Utils.marshall(dettaglio);
        recordDettaglio.setRecordDettaglio(record.getBytes());
        anagrafeTributariaService.salvaRecordDettaglio(recordDettaglio);
        salvaEvento(pratica, EVENTO_ANAGRAFE_TRIBUTARIA, null, utenteConnesso);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaRecordEdilizia(AnagrafeTributariaDTO anagrafeTributaria, Utente utenteConnesso) throws Exception {
        AtRecordDettaglio recordDettaglio;
        Pratica pratica = praticheService.getPratica(anagrafeTributaria.getIdPratica());
        if (anagrafeTributaria.getIdDettaglio() != null) {
            recordDettaglio = anagrafeTributariaService.findRecordDettaglioById(anagrafeTributaria.getIdDettaglio());
        } else {
            recordDettaglio = new AtRecordDettaglio();
        }
        recordDettaglio.setAnnoRiferimento(Calendar.getInstance().get(Calendar.YEAR));
        recordDettaglio.setDataInserimento(new Date());
        recordDettaglio.setIdPratica(pratica);
        recordDettaglio.setCodFornitura(anagrafeTributaria.getCodFornitura());
        String record = serializeRecordDettaglioEdilizia(anagrafeTributaria, pratica);
        recordDettaglio.setRecordDettaglio(record.getBytes());
        anagrafeTributariaService.salvaRecordDettaglio(recordDettaglio);
        salvaEvento(pratica, EVENTO_ANAGRAFE_TRIBUTARIA, null, utenteConnesso);
    }

    private String serializeRecordDettaglioEdilizia(AnagrafeTributariaDTO anagrafeTributaria, Pratica pratica) throws Exception {
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio record = new it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio();
        IdentificazioneRichiesta identificazioneRichiesta = serializer.serializeIdentificazioneRichiesta(anagrafeTributaria, pratica);
        record.setIdentificazioneRichiesta(identificazioneRichiesta);
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Beneficiari beneficiari = serializer.serializeBeneficiari(anagrafeTributaria, pratica);
        record.setBeneficiari(beneficiari);
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.DatiCatastali datiCatastali = serializer.serializeDatiCatastali(anagrafeTributaria, pratica);
        record.setDatiCatastali(datiCatastali);
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Professionisti professionisti = serializer.serializeProfessionisti(anagrafeTributaria, pratica);
        record.setProfessionisti(professionisti);
        it.wego.cross.xml.anagrafetributaria.edilizia.RecordDettaglio.Imprese imprese = serializer.serializeImprese(anagrafeTributaria, pratica);
        record.setImprese(imprese);
        String recordDettaglio = Utils.marshall(record);
        return recordDettaglio;
    }

    private PraticheEventi salvaEvento(Pratica pratica, String codEvento, String note, Utente utenteConnesso) throws Exception {
        PraticheEventi evento = new PraticheEventi();
        evento.setIdPratica(pratica);
        String desEvento = SYSTEM_EVENTS.get(codEvento);
        ProcessiEventi evt = processiService.requireEventoDiSistema(codEvento, desEvento, pratica.getIdProcesso());
        evento.setIdEvento(evt);
        evento.setDataEvento(new Date());
        evento.setIdUtente(utenteConnesso);
        evento.setVisibilitaCross("S");
        evento.setVisibilitaUtente("N");
        evento.setDescrizioneEvento(desEvento);
        evento.setNote(note);
        praticheService.saveProcessoEvento(evento);
        return evento;
    }

}
