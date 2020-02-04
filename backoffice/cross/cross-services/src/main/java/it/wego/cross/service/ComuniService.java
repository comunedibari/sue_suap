/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public interface ComuniService {
    
     public List<ComuneDTO> findComuniEnti(String descrizione);

    public List<ComuneDTO> trovaComune(String ricerca, String Date) throws java.text.ParseException;

    public Map<Integer, String> trovaProvince(String ricerca) throws java.text.ParseException;

    public ComuneDTO trovaComune(String ricerca, Date date) throws java.text.ParseException;

    public ComuneDTO trovaComuneSingolo(String ricerca, String date) throws java.text.ParseException;

    public Map<Integer, String> trovaProvinceInfocamere(String ricerca) throws java.text.ParseException;

    public LkProvincie findProvinciaByCodice(String codice, Boolean isInfocamere);

    public List<ComuneDTO> findComuneByIdEnte(Integer idEnte, String comune);

    public List<ComuneDTO> findComuneByIdEnte(Integer idEnte);

    public ComuneDTO findComuneByCodCatastale(String codiceCatastale);

    public LkComuni findLkComuneByCodCatastale(String codCatastaleComune);

    public List<LkComuni> findComuneEnteByDesc(String descrizione);

    public List<LkProvincie> findAllProvincieAttive(Date date);
    
     public List<ComuneDTO> findComuniByEnte(Integer idEnte, String query);
}