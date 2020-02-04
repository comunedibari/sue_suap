package it.wego.cross.service;

import it.wego.cross.beans.grid.GridEntiBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Utente;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface EntiService {

    /**
     * Fa query + serializzazione EntiSerializer
     *
     * @param idEnte EnteDTO
     * @return
     */
    public EnteDTO getEnteDTO(Integer idEnte);

    /**
     * Fa query diretta in DB. nessun controllo
     *
     * @param idEnte EnteDTO
     * @return Enti entity
     */
    public Enti getEnte(Integer idEnte);

    public List<EnteDTO> getEntiFromUtente(Utente utente) throws Exception;

    public GridEntiBean getEnti(HttpServletRequest request, JqgridPaginator paginator, String filterName) throws Exception;

    public Enti findByIdEnte(Integer idEnte);

    public Long countAll(Filter filter);

    public List<Enti> findAll(Filter filter);

    public Long countComuniCollegati(Integer idEnte, Filter filter);

    public List<LkComuni> getComuniCollegati(Integer idEnte, Filter filter);

    public List<LkComuni> search(String descrizione, String codiceCatastale);

    public Long countComuniFiltrati(Filter filter);

    public List<LkComuni> findComuniFiltrati(Filter filter);

    public Enti findByComune(ComuneDTO comune);

    public Enti findByUnitaOrganizzativa(String unitaOrganizzativa);

    public Enti findByIdentificativoSuap(String valueOf);

    public boolean isCrossUser(Enti ente);

    public Enti findByCodEnte(String codEnte);

    public EnteDTO findEnte(ComuneDTO comune);

    public List<Enti> getListaEntiPerRicerca(UtenteDTO utente);

    public String isSuap(Integer idEnte);

    public it.wego.cross.xml.Enti serializeEnti(it.wego.cross.entity.Pratica pratica);

    public List<EnteDTO> searchEntiLike(String ricerca);
}
