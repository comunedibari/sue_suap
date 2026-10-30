package it.wego.cross.service;

import java.util.Date;
import java.util.List;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.StatoDTO;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.utils.Utils;

public class LookupServiceImpl {

	private LookupDao lookupDao;
	
	
	public LookupServiceImpl(LookupDao lookupDao) {
		super();
		this.lookupDao = lookupDao;
	}

	public ComuneDTO getComuneDTOFromId(Integer id) {
        ComuneDTO result = null;
        if (id != null) {
            LkComuni lk = findComuneById(id);
            if (lk != null) {
                result = new ComuneDTO();
                result.setCodCatastale(lk.getCodCatastale());
                result.setDataFineValidita(lk.getDataFineValidita());
                result.setDescrizione(lk.getDescrizione());
                result.setIdComune(lk.getIdComune());
                result.setFlgTavolare(lk.getFlgTavolare());
                ProvinciaDTO provinciaDTO = new ProvinciaDTO();
                provinciaDTO.setCodCatastale(lk.getIdProvincia().getCodCatastale());
                provinciaDTO.setDataFineValidita(lk.getIdProvincia().getDataFineValidita());
                provinciaDTO.setDescrizione(lk.getIdProvincia().getDescrizione());
                provinciaDTO.setFlgInfocamere(Character.toString(lk.getIdProvincia().getFlgInfocamere()));
                provinciaDTO.setIdProvincie(lk.getIdProvincia().getIdProvincie());
                result.setProvincia(provinciaDTO);
                StatoDTO statoDTO = new StatoDTO();
                statoDTO.setCodIstat(lk.getIdStato().getCodIstat());
                statoDTO.setDataFine(lk.getIdStato().getDataFine());
                statoDTO.setDataInizio(lk.getIdStato().getDataInizio());
                statoDTO.setDescrizione(lk.getIdStato().getDescrizione());
                statoDTO.setIdStato(lk.getIdStato().getIdStato());
                result.setStato(statoDTO);
            }
        }
        return result;
    }
	
	public LkComuni findComuneById(Integer idComune) {
        return lookupDao.findComuneById(idComune);
    }
	
	public ComuneDTO getComuneDTOFromNomePreciso(String nome) {
        ComuneDTO result = null;
        if (!Utils.e(nome)) {
            Date dataOggi = new Date();
            List<LkComuni> comuniTrovati = lookupDao.findComuniByDescrizionePrecisa(nome, dataOggi);
            if (comuniTrovati.size() > 0) {
                result = new ComuneDTO();
                result.setCodCatastale(comuniTrovati.get(0).getCodCatastale());
                result.setDataFineValidita(comuniTrovati.get(0).getDataFineValidita());
                result.setDescrizione(comuniTrovati.get(0).getDescrizione());
                result.setIdComune(comuniTrovati.get(0).getIdComune());
                ProvinciaDTO provinciaDTO = new ProvinciaDTO();
                provinciaDTO.setCodCatastale(comuniTrovati.get(0).getIdProvincia().getCodCatastale());
                provinciaDTO.setDataFineValidita(comuniTrovati.get(0).getIdProvincia().getDataFineValidita());
                provinciaDTO.setDescrizione(comuniTrovati.get(0).getIdProvincia().getDescrizione());
                provinciaDTO.setFlgInfocamere(Character.toString(comuniTrovati.get(0).getIdProvincia().getFlgInfocamere()));
                provinciaDTO.setIdProvincie(comuniTrovati.get(0).getIdProvincia().getIdProvincie());
                result.setProvincia(provinciaDTO);
                StatoDTO statoDTO = new StatoDTO();
                statoDTO.setCodIstat(comuniTrovati.get(0).getIdStato().getCodIstat());
                statoDTO.setDataFine(comuniTrovati.get(0).getIdStato().getDataFine());
                statoDTO.setDataInizio(comuniTrovati.get(0).getIdStato().getDataInizio());
                statoDTO.setDescrizione(comuniTrovati.get(0).getIdStato().getDescrizione());
                statoDTO.setIdStato(comuniTrovati.get(0).getIdStato().getIdStato());
                result.setStato(statoDTO);
            }
        }
        return result;
    }
	
	public CittadinanzaDTO getCittadinanzaFromId(Integer id) {
        CittadinanzaDTO result = null;
        if (id != null && id > 0) {
            LkCittadinanza lk = lookupDao.findCittadinanzaByID(id);
            result = new CittadinanzaDTO();
            result.setCodCittadinanza(lk.getCodCittadinanza());
            result.setDescrizione(lk.getDescrizione());
            result.setIdCittadinanza(lk.getIdCittadinanza());
        }
        return result;

    }
	
	public CittadinanzaDTO getCittadinanzaFromDescrizionePrecisa(String descrizione) {
        CittadinanzaDTO cittadinanza = null;
        List<LkCittadinanza> cittadinanzeTrovate = lookupDao.findCittadinanzaByDescrizionePrecisa(descrizione);
        if (cittadinanzeTrovate.size() > 0) {
            cittadinanza = new CittadinanzaDTO();
            cittadinanza.setCodCittadinanza(cittadinanzeTrovate.get(0).getCodCittadinanza());
            cittadinanza.setDescrizione(cittadinanzeTrovate.get(0).getDescrizione());
            cittadinanza.setIdCittadinanza(cittadinanzeTrovate.get(0).getIdCittadinanza());
        }
        return cittadinanza;
    }

    public NazionalitaDTO getNazionalitaFromId(Integer id) {
        NazionalitaDTO result = null;
        if (id != null && id > 0) {
            LkNazionalita lk = lookupDao.findNazionalitaByID(id);
            result = new NazionalitaDTO();
            result.setCodNazionalita(lk.getCodNazionalita());
            result.setDescrizione(lk.getDescrizione());
            result.setIdNazionalita(lk.getIdNazionalita());
        }
        return result;
    }
    
    public NazionalitaDTO getNazionalitaFromDescrizionePrecisa(String descrizione) {
        NazionalitaDTO nazionalita = null;
        List<LkNazionalita> cittadinanzeTrovate = lookupDao.findNazionalitaByDescrizionePrecisa(descrizione);
        if (cittadinanzeTrovate.size() > 0) {
            nazionalita = new NazionalitaDTO();
            nazionalita.setCodNazionalita(cittadinanzeTrovate.get(0).getCodNazionalita());
            nazionalita.setDescrizione(cittadinanzeTrovate.get(0).getDescrizione());
            nazionalita.setIdNazionalita(cittadinanzeTrovate.get(0).getIdNazionalita());
        }
        return nazionalita;
    }
}
