/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.entity.Comunicazione;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface ComunicazioniService {

    public List<Comunicazione> getComunicazioniUtente(Filter filter);

    public Long countComunicazioniUtente(Filter filter);

    public Comunicazione findComunicazioneById(Integer idComunicazione);

    public void salva(Comunicazione comunicazione) throws Exception;

    public void initializeComunicazione(it.wego.cross.dto.dozer.forms.ComunicazioneDTO comunicazione, Pratica pratica, ProcessiEventi processoEvento, Utente utente) throws Exception;

    public ComunicazioneDTO getDettaglioComunicazione(Pratica pratica, Enti ente, EventoDTO evento, UtenteDTO user) throws Exception;

    public List<DestinatariDTO> getListDestinatari(Integer idEvento, Integer idPratica, List<String> previousDestinatariIdList) throws Exception;

    @Deprecated
    public List<DestinatariDTO> getListDestinatari(Integer idEvento, Pratica pratica, Enti ente) throws Exception;

    public List<DestinatariDTO> getListMittenti(Integer idEvento, Integer idPratica, List<String> previousMittentiIdList) throws Exception;

    public List<TemplateDTO> getTemplates(Integer idEvento, Integer idEnte, Integer idProcedimento) throws Exception;
    
}
