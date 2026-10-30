package it.wego.cross.actions;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AnagraficaDaCollegareDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaAnagraficaPK;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.PraticheEventiAnagrafichePK;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.service.AnagraficheServiceImpl;
import it.wego.cross.service.ProcessiServiceImpl;
import it.wego.cross.service.UsefulServiceImpl;
import it.wego.cross.utils.AnagraficaUtils;

public class SystemEventAction {

	PraticaDao praticaDao;
	AnagraficheDao anagraficheDao;
	LookupDao lookupDao;
	ProcessiServiceImpl processiService;
	AnagraficheServiceImpl anagraficheServiceImpl;
	AnagraficaAction anagraficaAction;
	AnagraficaUtils anagraficaUtils;
	UsefulServiceImpl useful;

	


	public SystemEventAction(PraticaDao praticaDao, AnagraficheDao anagraficheDao, LookupDao lookupDao,
			ProcessiServiceImpl processiService, AnagraficheServiceImpl anagraficheServiceImpl,
			AnagraficaAction anagraficaAction, AnagraficaUtils anagraficaUtils, UsefulServiceImpl useful) {
		super();
		this.praticaDao = praticaDao;
		this.anagraficheDao = anagraficheDao;
		this.lookupDao = lookupDao;
		this.processiService = processiService;
		this.anagraficheServiceImpl = anagraficheServiceImpl;
		this.anagraficaAction = anagraficaAction;
		this.anagraficaUtils = anagraficaUtils;
		this.useful = useful;
	}

	@Transactional(rollbackFor = Exception.class)
    public void inserisciCollegamentoPraticaAnagrafica(AnagraficaDaCollegareDTO anagrafica, Integer idPratica, Utente utenteConnesso, boolean updateStaging) throws Exception {
        inserisciCollegamentoPraticaAnagraficaNoTransactional(anagrafica, idPratica, utenteConnesso, updateStaging);
    }

    //Non è transactional perché viene chiamato solo da aggiornaAnagraficaStgTransactional in AnagraficaAction
    public void inserisciCollegamentoPraticaAnagraficaNoTransactional(AnagraficaDaCollegareDTO anagrafica, Integer idPratica, Utente utenteConnesso, boolean updateStaging) throws Exception {
//        Pratica pratica = praticheService.getPratica(idPratica);
        Pratica pratica = praticaDao.findPratica(idPratica);
        LkTipoQualifica qualifica = null;
        if (anagrafica.getQualificaRichiedente() != null && anagrafica.getQualificaRichiedente() > 0) {
            qualifica = lookupDao.findTipoQualificaById(anagrafica.getQualificaRichiedente());
        }
        if (anagrafica.getQualificaTecnico() != null && anagrafica.getQualificaTecnico() > 0) {
            qualifica = lookupDao.findTipoQualificaById(anagrafica.getQualificaTecnico());
        }
        PraticaAnagrafica pa = new PraticaAnagrafica();
        PraticaAnagraficaPK key = new PraticaAnagraficaPK();
        key.setIdAnagrafica(anagrafica.getIdAnagrafica());
        key.setIdPratica(idPratica);
        key.setIdTipoRuolo(anagrafica.getRuolo());
        pa.setPraticaAnagraficaPK(key);
        pa.setDataInizioValidita(new Date());
        pa.setIdTipoQualifica(qualifica);
        if (anagrafica.getDittaIndividuale() != null && anagrafica.getDittaIndividuale().equalsIgnoreCase("S")) {
            pa.setFlgDittaIndividuale("S");
        } else {
            pa.setFlgDittaIndividuale("N");
        }
        praticaDao.insert(pa);
        PraticheEventi evento = salvaEventoGenerico(pratica, "ANAG", utenteConnesso);
        useful.flush();
        PraticheEventiAnagrafiche pea = new PraticheEventiAnagrafiche();
        PraticheEventiAnagrafichePK peaKey = new PraticheEventiAnagrafichePK();
        peaKey.setIdAnagrafica(anagrafica.getIdAnagrafica());
        peaKey.setIdPraticaEvento(evento.getIdPraticaEvento());
        pea.setPraticheEventiAnagrafichePK(peaKey);
        praticaDao.insert(pea);
        if (updateStaging) {
            Anagrafica anag = anagraficheDao.findById(anagrafica.getIdAnagrafica());
            AnagraficaDTO dto = AnagraficheSerializer.serializeAnagrafica(anag);
            Integer idRuolo = anagrafica.getRuolo();
            LkTipoRuolo tipoRuolo = lookupDao.findTipoRuoloById(idRuolo);
            dto.setCodTipoRuolo(tipoRuolo.getCodRuolo());
            dto.setDesTipoRuolo(tipoRuolo.getDescrizione());
            dto.setIdTipoRuolo(tipoRuolo.getIdTipoRuolo());
            dto.setIdPratica(idPratica);
            dto.setDaRubrica("S");
            anagraficaAction.aggiungiAnagraficadaXML(dto);
            anagraficaAction.aggiornaAnagraficaStg(dto, utenteConnesso);
        }
    }
    
    private PraticheEventi salvaEventoGenerico(Pratica pratica, String codEvento, Utente utenteConnesso) throws Exception {
        return salvaEventoGenerico(pratica, codEvento, null, utenteConnesso);
    }

    public PraticheEventi salvaEventoGenerico(Pratica pratica, String codEvento, String note, Utente utenteConnesso) throws Exception {
        PraticheEventi evento = new PraticheEventi();
        evento.setIdPratica(pratica);
        String desEvento = "Collegamento nuova anagrafica";
        ProcessiEventi evt = processiService.requireEventoDiSistema(codEvento, desEvento, pratica.getIdProcesso());
        evento.setIdEvento(evt);
        evento.setDataEvento(new Date());
        evento.setIdUtente(utenteConnesso);
        evento.setVisibilitaCross("S");
        evento.setVisibilitaUtente("N");
        evento.setDescrizioneEvento(desEvento);
        evento.setNote(note);
        praticaDao.insert(evento);
        return evento;
    }
}
