/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.serializer.LuoghiSerializer;
import it.wego.cross.utils.RecapitoUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public class ComuniServiceImpl implements ComuniService {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private EntiDao entiDao;
    
    @Override
        public List<ComuneDTO> findComuniEnti(String descrizione) {
        List<LkComuni> comuni = findComuneEnteByDesc(descrizione);
        List<ComuneDTO> comuniDTO = new ArrayList< ComuneDTO>();
        for (LkComuni comune : comuni) {
            comuniDTO.add(LuoghiSerializer.serialize(comune));
        }
        return comuniDTO;
    }
    
    @Override
    public List<ComuneDTO> trovaComune(String ricerca, String date) throws java.text.ParseException {
        Date dataValidazione = new Date();
        if (date != null && !date.equals("")) {
            dataValidazione = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(date);
        }
        List<ComuneDTO> comuni = new ArrayList<ComuneDTO>();
        List<it.wego.cross.entity.LkComuni> comuniEntity = lookupDao.findComuniByDescrizione(ricerca, dataValidazione);
        if (comuniEntity != null && comuniEntity.size() > 0) {
            for (it.wego.cross.entity.LkComuni comuneEntity : comuniEntity) {
                ComuneDTO comune = new ComuneDTO();
                RecapitoUtils.copyEntity2Comune(comuneEntity, comune);
                comuni.add(comune);
            }
        }
        return comuni;
    }

    @Override
    public List<LkProvincie> findAllProvincieAttive(Date date) {
        List<LkProvincie> provincie = lookupDao.findAllProvinceAttive(date);
        return provincie;
    }

    @Override
    public ComuneDTO trovaComuneSingolo(String ricerca, String date) throws java.text.ParseException {
        Date dataValidazione = new Date();
        if (date != null && !date.equals("")) {
            dataValidazione = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(date);
        }
        it.wego.cross.entity.LkComuni comuneEntity = lookupDao.findComuneByDescrizione(ricerca, dataValidazione);
        ComuneDTO comune = new ComuneDTO();
        RecapitoUtils.copyEntity2Comune(comuneEntity, comune);
        return comune;
    }

    @Override
    public ComuneDTO trovaComune(String ricerca, Date date) throws java.text.ParseException {
        it.wego.cross.entity.LkComuni comuneEntity = lookupDao.findComuneByDescrizione(ricerca, date);
        ComuneDTO comune = new ComuneDTO();
        RecapitoUtils.copyEntity2Comune(comuneEntity, comune);
        return comune;
    }

    @Override
    public ComuneDTO findComuneByCodCatastale(String codiceCatastale) {
        LkComuni comune = lookupDao.findComuneByCodCatastale(codiceCatastale);
        ComuneDTO dto = LuoghiSerializer.serialize(comune);
        return dto;
    }

    @Override
    public Map<Integer, String> trovaProvince(String ricerca) throws java.text.ParseException {
        Map<Integer, String> province = new HashMap<Integer, String>();
        List<it.wego.cross.entity.LkProvincie> provinceEntity = lookupDao.findProvinceByDescrizione(ricerca);
        if (provinceEntity != null && provinceEntity.size() > 0) {
            for (it.wego.cross.entity.LkProvincie provincia : provinceEntity) {
                province.put(provincia.getIdProvincie(), provincia.getDescrizione());
            }
        }
        return province;
    }

    @Override
    public Map<Integer, String> trovaProvinceInfocamere(String ricerca) throws java.text.ParseException {
        Map<Integer, String> province = new HashMap<Integer, String>();
        List<it.wego.cross.entity.LkProvincie> provinceEntity = lookupDao.findProvinceInfocamereByDescrizione(ricerca);
        if (provinceEntity != null && provinceEntity.size() > 0) {
            for (it.wego.cross.entity.LkProvincie provincia : provinceEntity) {
                province.put(provincia.getIdProvincie(), provincia.getDescrizione());
            }
        }
        return province;
    }

    @Override
    public LkProvincie findProvinciaByCodice(String codice, Boolean isInfocamere) {
        LkProvincie provincia = lookupDao.findLkProvinciaByCod(codice, isInfocamere);
        return provincia;
    }

    @Override
    public List<ComuneDTO> findComuneByIdEnte(Integer idEnte, String comuneST) {
        List<LkComuni> comuni = entiDao.findByIdEnte(idEnte, comuneST);
        List<ComuneDTO> comuniDTO = new ArrayList<ComuneDTO>();
        if (comuni != null) {
            for (LkComuni comune : comuni) {
                ComuneDTO dto = LuoghiSerializer.serialize(comune);
                comuniDTO.add(dto);
            }
        }
        return comuniDTO;
    }

    @Override
    public List<ComuneDTO> findComuneByIdEnte(Integer idEnte) {
        Enti ente = entiDao.findByIdEnte(idEnte);
        List<ComuneDTO> comuni = new ArrayList<ComuneDTO>();
        if (ente.getLkComuniList() != null && ente.getLkComuniList().size() > 0) {
            for (LkComuni comune : ente.getLkComuniList()) {
                ComuneDTO dto = LuoghiSerializer.serialize(comune);
                comuni.add(dto);
            }
        }
        return comuni;
    }

    @Override
    public LkComuni findLkComuneByCodCatastale(String codCatastaleComune) {
        return lookupDao.findComuneByCodCatastale(codCatastaleComune);
    }

    @Override
    public List<LkComuni> findComuneEnteByDesc(String descrizione) {
        List<LkComuni> comuni = lookupDao.findComuneEnteByDesc(descrizione);
        return comuni;
    }
    
    @Override
    public List<ComuneDTO> findComuniByEnte(Integer idEnte, String query) {
        List<ComuneDTO> comuni = findComuneByIdEnte(idEnte, query);
        return comuni;
    }
}