/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Permessi;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.utils.wegoforms.FormEngine;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public interface UtentiService {

    public Long countDaAssegnarePratica(Integer idPratica, Filter filter);

    public List<UtenteDTO> findDaAssegnarePratica(Integer idPratica, Filter filter);

    public Utente findUtenteDaUsername(String username);

    public Utente findUtenteByIdUtente(Integer idUtente);

    public List<Utente> findAll(Filter filter);

    public Long countAll(Filter filter);

    public List<Permessi> findAllPermessi();

    public Permessi findByCodPermesso(String codPermesso);

    public UtenteRuoloEnte getRuoloByKey(Integer idUtente, Integer idEnte, String codPermesso);

    public PermessiEnteProcedimentoDTO getPermesso(ProcedimentiLocalizzatiView procedimento, int codEnte);

    public Utente getUtente(Integer idUtente);

    public Utente getUtenteConnesso(HttpServletRequest request);

    public UtenteDTO getUtenteConnessoDTO(HttpServletRequest request);

    public Boolean couldCreateNewEvent(Utente utente, Pratica pratica);

    public boolean isAdminOnProcEnte(Utente utente, ProcedimentiEnti idProcEnte) throws Exception;

    public List<UtenteDTO> findAdminSuPratica(Integer idPratica, Filter filter);

    public List<Utente> findAllSystemSuperusers();
    
    public List<String> findAllSystemSuperusersUsername();
    
    public String encodePasswordWithSsha(String cleartextPassword);
    
    public String getAlberoEntiRuoli(Integer idUtente) ;
    
    public List<UtenteRuoloEnte> getRuoliEnti(Integer idUtente);
    
    public void initRicercaProcedimentiRuoli(HttpServletRequest request, Model model, FormEngine formEngine, Map<String,Object> formParameters);
    
    public boolean testPsw(String password, String passwordFromDb);

	public List<Utente> findUtentiByEnte(List<Enti> listaEnti);

}
