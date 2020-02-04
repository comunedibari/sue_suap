package it.wego.cross.service;

import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.DizionarioErrori;
import it.wego.cross.entity.Errori;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ErroriService {

    public DizionarioErrori findDizionarioErroriByCod(String codice);

    public void inserisci(Errori errori) throws Exception;

    public void aggiorna(Errori errori) throws Exception;

//    public Long countErrori();
    public Long countErrori(Filter filter);

//    public Long countErroriAttivi();
    public List<ErroreDTO> getErrori(Filter filter);

    public ErroreDTO findErroreById(Integer id);
}
