/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Recapiti;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public interface AnagraficheService {

    public AnagraficaDTO cercaAnagraficaDuplicata(AnagraficaDTO anagraficaDTO) throws Exception;

    public AnagraficaDTO getAnagrafica(AnagraficaDTO anagrafica) throws Exception;

    public AnagraficaDTO getAnagraficaXML(AnagraficaDTO anagraficaDTO) throws Exception;

    public Anagrafica findAnagraficaById(Integer idAnagrafica);

    public Map<Integer, String> getListaTipoRuolo();

    public Recapiti findRecapitoById(Integer idRecapito);

    public List<CittadinanzaDTO> findCittadinanze(String nome);

    public Long countAnagrafiche(Filter filter);

    public List<AnagraficaDTO> searchAnagrafiche(Filter filter);
    
    public List<AnagraficaDTO> searchAnagraficheLike(String ricerca);

    public Anagrafica findAnagraficaByCodFiscale(String codiceFiscale);

    public Anagrafica findAnagraficaByCodFiscaleNotId(String codiceFiscale, Integer idAnagrafica);

    public Anagrafica findAnagraficaByPartitaIvaNotId(String partitaIva, Integer idAnagrafica);

    public Anagrafica findAnagraficaByPartitaIva(String partitaIva);

    public Map<Integer, String> getListaTipoIndirizzo();

    public void eliminaAnagrafiadXML(AnagraficaDTO anagrafica) throws Exception;

    public Recapiti getRecapitoRiferimentoAnagrafica(Anagrafica anagrafica) throws Exception;
    
    public AnagraficaRecapiti getAnagraficaRecapitoRiferimentoAnagrafica(Anagrafica anagrafica) throws Exception;

    public List<NazionalitaDTO> findNazionalita(String descrizione);

    public Recapiti getRecapitoRiferimentoAnagrafica(PraticaAnagrafica praticaAnagrafica) throws Exception;
    
    public RecapitoDTO getPrimoRecapito(Integer idAnagrafica);

    public boolean isTipoPersonaModificabile(String tipoPersona, Integer idAnagrafica, String codiceFiscale);
    
    public AnagraficaRecapiti findAnagraficaRecapitiById(Integer idAnagraficaRecapiti);
}
