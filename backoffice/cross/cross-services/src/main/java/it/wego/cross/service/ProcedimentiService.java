/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.beans.ProcedimentoPermessiBean;
import it.wego.cross.beans.grid.GridProcedimenti;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.TipoProcedimentoDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;

import java.math.BigInteger;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface ProcedimentiService {

    public Procedimenti findProcedimentoByIdProc(Integer idProc);

    public List<ProcedimentiTesti> findProcedimentiTestiByIdProc(Integer idProc);

    public Procedimenti findProcedimentoByCodProc(String codProc);

    public ProcedimentiEnti findProcedimentiEnti(Integer idEnte, Integer idProc);

//    public List<Procedimenti> findProcedimentiByCodEntePaginated(Integer codEnte, Filter filter);
//    public List<Procedimenti> findProcedimentiByIdEnte(Integer idEnte);
    public List<ProcedimentoDTO> findProcedimentiByDescriptionAndIdEnte(String description, Integer idEnte);

    public List<ProcedimentoDTO> findProcedimentiCollegatiByIdEnte(Integer idEnte);

    public ProcedimentoPermessiBean getProcedimentoPermessiBean(Integer codUtente, Integer codEnte, String codPermesso, Integer idProc, String lang);

    public List<ProcedimentiLocalizzatiView> getProcedimentiLocalizzati(String lang, Filter filter);

    public List<ProcedimentiLocalizzatiView> getAllProcedimentiLocalizzati(String lang);

    public ProcedimentiLocalizzatiView getProcedimentoLocalizzato(String lang, Integer idProc);

    public Procedimenti getProcedimento(String codiceProcedimento);

    public void eliminaProcedimentoEnte(ProcedimentiEnti procedimentoEnte) throws Exception;

    public Long countProcedimentiByCodEnte(int codEnte, Filter filter);

    public Long countProcedimentiLocalizzati(String it, Filter filter);

    public List<ProcedimentiEnti> findAllProcedimentiEnti();

    public List<ProcedimentiEnti> findProcedimentiEnti(ComuneDTO comune);

    public List<ProcedimentiEnti> findProcedimentiEntiByDescProcedimento(ProcedimentoDTO procedimento);

    public List<ProcedimentiEnti> findProcedimentiEntiByDescProcedimento(ProcedimentoDTO procedimento, Utente connectedUser);

    public List<ProcedimentiLocalizzatiView> findProcedimenti(Filter filter);

//   public Procedimenti inserisci(ProcedimentoDTO p) throws Exception;
//
//    public Procedimenti aggiorna(ProcedimentoDTO p) throws Exception;
//
//    public void elimina(Procedimenti procedimento) throws Exception;
//
//    public void elimina(List<ProcedimentiTesti> testi) throws Exception;
//    public Long countProcedimentiLocalizzatiAbilitati(String it, Integer idEnte, String desProc);
//
//    public Long countProcedimentiLocalizzatiNonAbilitati(String it, Integer idEnte, String desProc);
//    public List<ProcedimentiLocalizzatiView> findProcedimentiLocalizzatiAbilitati(String lang, Integer idEnte, Filter filter);
    public Long countProcedimenti(Filter filter, String localizzazione) throws Exception;

//    public List<ProcedimentiLocalizzatiView> findProcedimentiNonLocalizzatiAbilitati(String lang, Integer idEnte, Filter filter);
    public List<Enti> findEntiFromProcedimento(Integer idProc);

    public List<ProcedimentiLocalizzatiView> getProcedimentiLocalizzatiPaginate(String it, Filter filter);

    public Long countProcedimentiLocalizzatiPaginate(String it, Filter filter);

    public List<TipoProcedimentoDTO> findDistinctTipoProcedimento();

    public List<ProcedimentoDTO> getProcedimenti(Filter filter);

    public GridProcedimenti getProcedimentiLocalizzati(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception;

    public List<ProcedimentoDTO> cambiaDenominazione(List<ProcedimentiLocalizzatiView> searchProcedimenti);

    public ProcedimentoDTO getByID(ProcedimentoDTO proc) throws Exception;

    //non usato
    public List<ProcedimentoDTO> findEntiProcedimenti(ComuneDTO comune);

    public List<ProcedimentoDTO> findEntiProcedimenti(ProcedimentoDTO proc);

    //non usato
    public List<ProcedimentoDTO> findEntiProcedimenti(ProcedimentoDTO proc, Utente connectedUser);

    public List<ProcedimentoDTO> findProcedimenti(Integer idEnte, String query);

    public Long contaProcedimentiLocalizzati(Filter filter, Integer idEnte);

    public List<PermessiEnteProcedimentoDTO> getProcedimentiLocalizzati(Integer codEnte, Filter filter);

    public Procedimenti getProcedimento(ProcedimentoDTO dto) throws Exception;

//    public it.wego.cross.xml.Procedimento getProcedimentoXml(ProcedimentoDTO procedimento) throws NumberFormatException;

    public ProcedimentoDTO serializeProcedimento(it.wego.cross.xml.Procedimento p) throws Exception;

    String getDescrizioneProcedimentoLingua(Integer lingua, Integer idProc);

    public ProcedimentiEnti findProcedimentiEntyByKey(Integer idProcedimentoEnte);

    public void eliminaPraticaProcedimento(PraticaProcedimenti praticaProcedimentoToDelete) throws Exception;

    public ProcedimentiEnti requireProcedimentoEnte(Integer idProcedimento, Integer idEnte, Enti entePratica, LkComuni comunePratica) throws Exception;
    
    public ProcedimentiEnti requireProcedimentoEnte(Integer idProcedimentoEnte, Enti entePratica, LkComuni comunePratica) throws Exception;

	public ProcedimentiEnti findIdUfficioByIdProcIdEnte(Integer idProcedimento, BigInteger idEnteDestinatario);
}
