/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.EstrazioniAgibDTO;
import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniPdcDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;
import it.wego.cross.dto.PraticaBarDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dao.PraticaDao.IntervalloScadenzeMultiple;
import it.wego.cross.dto.comunicazione.DettaglioPraticaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Scadenze;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ScadenzeDaChiudereView;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public interface PraticheService {

    public Long countFiltrate(Filter filter);

    public List<PraticaNuova> findFiltrate(Filter filter) throws Exception;

    public Long countDaAprire(Filter filter);

    public List<PraticaNuova> findDaAprire(Filter filter) throws Exception;

    public Long countScadenze(Filter filter);

    public List<ScadenzaDTO> findScadenze(Filter filter);

    public PraticaDTO dettaglioXmlPratica(Integer idPratica) throws Exception;

    public Pratica getPratica(Integer idPratica) throws Exception;

    public List<EventoDTO> getEventiPratica(Pratica pratica) throws Exception;

    public EventoDTO getDettaglioPraticaEvento(Integer idEventoPratica) throws Exception;

    public EventoDTO getDettaglioProcessoEvento(Integer idEventoPratica);

    public EventoDTO getEvento(Integer idProcessiEventi);

    public ProcessiEventi findProcessiEventi(Integer idEvento);

    public List<AllegatoDTO> getAllegatiPratica(Integer idPratica) throws Exception;

    public List<AllegatoDTO> getAllegatiPratica(Pratica pratica);

    public Pratica getPraticaRoot(Pratica pratica);

    public Pratica getPraticaEnte(Pratica pratica, Enti ente);

    public Pratica getPraticaByIdentificativo(String identificativoPratica);

    public LkStatoPratica findLookupStatoPratica(Integer idLookup);

    public LkStatoPratica findLookupStatoPratica(String codiceStatoPratica);

    public List<LkStatoPratica> findAllLookupStatoPratica();

    public List<LkStatiScadenze> findAllLookupScadenze();

    public LkStatiScadenze findStatoScadenzaById(String id);

    public List<PraticaNuova> findDaAssegnare(Filter filter) throws Exception;

    public Long countDaAssegnare(Filter filter);

    public PraticaProcedimenti findPraticaProcedimento(Integer idPratica, Integer idProcedimento, Integer idEnte) throws Exception;

    public PraticaProcedimenti findEnteDaPraticaProcedimento(Integer idPratica, Integer idProcedimento) throws Exception;

    public ProcessiEventi findProcessiEventiByCodEventoIdProcesso(String codiceEvento, Integer idProcesso);

    public List<ScadenzeDaChiudereView> getScadenzeDaChiudere(Integer idEvento, Integer idPratica);

    public Scadenze findScadenzeByIdScadenza(Integer idScadenza);

    public IntervalloScadenzeMultiple findIntervalloScadenzeMultipleSospensione(Integer idPratica);

    public Scadenze findScadenzaPratica(Pratica pratica);

    public List<PraticaAnagrafica> findAziendePratica(Pratica pratica);

    public Pratica findByRegistroFascicoloProtocolloAnno(String codRegistro, String fascicolo, String numeroProtocollo, Integer annoProtocollo);

    public List<Pratica> findByRegistroFascicoloAnno(String codRegistro, String fascicolo, Integer annoProtocollo);
    
    public List<Pratica> findPraticheDaRiprotocollare(String protocollo);

    public Pratica findByAnnoProtocollo(Integer anno, String numeroProtocollo);

    public List<PraticaBarDTO> findScadenzeForBar(Utente connectedUser);

    public List<PraticaBarDTO> findDaAprireForBar(Utente connectedUser);

    public List<Pratica> findPraticheFiglie(Pratica praticaPadre, Enti idEnte, Filter filter);

    public Long countPraticheFiglie(Pratica praticaPadre, Enti idEnte, Filter filter);

    public PraticaAnagrafica findPraticaAnagraficaByKey(Integer idPratica, Integer idAnagrafica, Integer ruolo);

    public Boolean praticaHasPastEvent(Pratica pratica, List<String> codEventoList) throws Exception;

    public Boolean praticaHasPastEvent(Pratica pratica, String codEvento) throws Exception;

    @Deprecated
    public DettaglioPraticaDTO getDettaglioPratica(Pratica pratica) throws Exception;

    public List<ScadenzaDTO> getScadenzeDaChiudereDTOList(Integer idPratica, Integer idEvento) throws Exception;

    public Integer getIdProcedimentoSuap(PraticaDTO pratica) throws Exception;

    public PraticaDTO serializePratica(PraticheProtocollo pratica, it.wego.cross.xml.Pratica xml) throws Exception;

    public Pratica serializePratica(PraticheProtocollo praticaProtocollo, Staging staging, it.wego.cross.xml.Pratica praticaCross) throws Exception;

    public it.wego.cross.xml.Recapito getNotifica(PraticaDTO pratica);

    public List<LkStatoPratica> getLookupsStatoPratica(String statoPratica);

    public List<PraticaDTO> getPraticheNonProtocollate(Filter filter);

    public Long countPraticheNonProtocollate();

    public void cancellaPratica(String identificativoPratica) throws Exception;

    public void aggiornaPratica(Pratica pratica) throws Exception;

    public Pratica findByFascicoloAnno(String nFascicolo, Integer annoFascicolo, Integer idEnte);

    public void startCommunicationProcess(Pratica pratica, PraticheEventi praticaEvento, EventoBean eventoBean) throws Exception;

    public void deletePraticaAnagrafica(PraticaAnagrafica pa) throws Exception;

    public void saveProcessoEvento(PraticheEventi evento) throws Exception;

    public void saveAnagraficaEvento(PraticheEventiAnagrafiche pea) throws Exception;

    public void savePraticaAnagrafica(PraticaAnagrafica pa) throws Exception;

    public void updatePraticaAnagrafica(PraticaAnagrafica pa) throws Exception;

    public DatiCatastali findDatiCatastali(Integer idImmobile);

    public void eliminaDatiCatastali(DatiCatastali datiDB);

    public void saveDatiCatastali(DatiCatastali datiDB) throws Exception;

    public void updateDatiCatastali(DatiCatastali datiDB) throws Exception;

    public IndirizziIntervento findIndirizziInterventoById(Integer integer);

    public void eliminaIndirizzoIntervento(IndirizziIntervento datiDB) throws Exception;

    public void saveIndirizzoIntervento(IndirizziIntervento datiDB) throws Exception;

    public void updateIndirizzoIntervento(IndirizziIntervento datiDB) throws Exception;

    public void salvaPraticaProcedimento(PraticaProcedimenti praticaProcedimenti) throws Exception;

    public Pratica findPraticaWithPraticaPadreAndEnteAndProcedimento(Integer idPratica, Integer idEnte, Integer idProc);

    public PraticheEventi getPraticaEvento(Integer idPraticaEvento);

    public void updatePraticaEvento(PraticheEventi evento) throws Exception;

    public void startSendMailProcess(Email email) throws Exception;

    public void aggiornaResponsabileProcedimentoSuPratiche(ProcedimentiEnti procedimentiEnti) throws Exception;

    public Scadenze findScadenzaById(Integer idScadenza);

    public List<String> popolaListaAnni();

    public List<Scadenze> findScadenzaPraticaAttive(Pratica pratica);

	public List<EstrazioniCilaDTO> findPraticheCILA(Filter filter) throws Exception;

	public List<EstrazioniCilaDTO> listPraticheCILA(Filter filter) throws Exception;

	public List<EstrazioniSciaDTO> findPraticheSCIA(Filter filter) throws Exception;

	public List<EstrazioniSciaDTO> listPraticheSCIA(Filter filter) throws Exception;

	public List<EstrazioniPdcDTO> findPratichePDC(Filter filter) throws Exception;
	
	public List<EstrazioniPdcDTO> listPratichePDC(Filter filter) throws Exception;

	public List<EstrazioniAgibDTO> findPraticheAGIB(Filter filter) throws Exception;

	public List<EstrazioniAgibDTO> listPraticheAGIB(Filter filter) throws Exception;

	public List<ProcessiEventi> getProcessiEventiChiusura(Processi idProcesso) throws Exception;

	public void aggiornaPraticaArchivio(Pratica pratica) throws Exception;

	public PraticheEventiAllegati findPraticheEventiAllegatiByIdAllegato(Integer idAllegato);

	public List<EstrazioniCilaDTO> listPraticheCILAToDo(Filter filter) throws Exception;

	public List<PraticheEventi> findPraticheEventiDaRiprotocollare();

}
