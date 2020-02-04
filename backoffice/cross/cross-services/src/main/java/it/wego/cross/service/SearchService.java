/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.UtenteMinifiedDTO;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.dto.search.FormaGiuridicaDTO;
import it.wego.cross.dto.search.TipoCollegioDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface SearchService {

    public List<DestinatariDTO> findDestinatari(Pratica pratica, String description, Boolean soloEnti);

    public List<DestinatariDTO> serializeDestinatari(Pratica pratica, AttoriComunicazione destinatari);

    public List<ProvinciaDTO> findProvincie(String descrizione, Boolean showOnlyProvinceInfocamere);

    public List<FormaGiuridicaDTO> findFormeGiuridiche(String descrizione);

    public List<CittadinanzaDTO> findCittadinanza(String description);

    public List<TipoCollegioDTO> findTipoCollegio(String description);

    public List<ComuneDTO> findComuni(String description, Date data);

    public List<UtenteMinifiedDTO> findUtenti(String query);

    public List<DestinatariDTO> getDestinatari(Pratica pratica, String query, EventoDTO evento);

    public List<ProvinciaDTO> findProvincie(String query, boolean displayOnlyProvinceInfocamere);

    public List<CittadinanzaDTO> findCittadinanze(String query);
 
}
