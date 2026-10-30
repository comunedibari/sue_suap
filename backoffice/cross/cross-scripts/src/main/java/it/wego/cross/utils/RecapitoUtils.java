/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.StatoDTO;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoIndirizzo;

/**
 *
 * @author CS
 */
@Component
public class RecapitoUtils {

    private LookupDao lookupDao;

    public RecapitoUtils(LookupDao lookupDao) {
		super();
		this.lookupDao = lookupDao;
	}

    public static void copyEntity2Comune(LkComuni comuneEntity, ComuneDTO comuneDTO) {
        if (comuneEntity.getIdProvincia() != null) {
            if (comuneDTO.getProvincia() == null) {
                comuneDTO.setProvincia(new ProvinciaDTO());
            }
            RecapitoUtils.copyEntity2Provincia(comuneEntity.getIdProvincia(), comuneDTO.getProvincia());
        }
        if (comuneEntity.getIdStato() != null) {
            if (comuneDTO.getStato() == null) {
                comuneDTO.setStato(new StatoDTO());
            }
            RecapitoUtils.copyEntity2Stato(comuneEntity.getIdStato(), comuneDTO.getStato());
        }
        comuneDTO.setCodCatastale(comuneEntity.getCodCatastale());
        comuneDTO.setDataFineValidita(comuneEntity.getDataFineValidita());
        comuneDTO.setDescrizione(comuneEntity.getDescrizione());
        comuneDTO.setIdComune(comuneEntity.getIdComune());
    }
    
    public static void copyEntity2Stato(it.wego.cross.entity.LkStati statoEntity, StatoDTO statoDTO) {
        statoDTO.setCodIstat(statoEntity.getCodIstat());
        statoDTO.setDataFine(statoEntity.getDataFine());
        statoDTO.setDataInizio(statoEntity.getDataInizio());
        statoDTO.setDescrizione(statoEntity.getDescrizione());
        statoDTO.setIdStato(statoEntity.getIdStato());
    }
    
    public static void copyEntity2Provincia(LkProvincie provinciaEntity, ProvinciaDTO provinciaDTO) {
        provinciaDTO.setCodCatastale(provinciaEntity.getCodCatastale());
        provinciaDTO.setDataFineValidita(provinciaEntity.getDataFineValidita());
        provinciaDTO.setDescrizione(provinciaEntity.getDescrizione());
        provinciaDTO.setFlgInfocamere(Utils.flag(provinciaEntity.getFlgInfocamere()));
        provinciaDTO.setIdProvincie(provinciaEntity.getIdProvincie());
    }
    
    public static void copyEntity2Recapito(it.wego.cross.entity.Recapiti recapitoEntity, RecapitoDTO recapitoDTO) {
        recapitoDTO.setAltreInfoIndirizzo(recapitoEntity.getAltreInfoIndirizzo());
        recapitoDTO.setCap(recapitoEntity.getCap());

        recapitoDTO.setCasellaPostale(recapitoEntity.getCasellaPostale());
        recapitoDTO.setCellulare(recapitoEntity.getCellulare());
        if (recapitoEntity.getIdComune() != null) {
            recapitoDTO.setDescComune(recapitoEntity.getIdComune().getDescrizione());
            recapitoDTO.setIdComune(recapitoEntity.getIdComune().getIdComune());
            if (recapitoEntity.getIdComune().getIdProvincia() != null) {
                recapitoDTO.setIdProvincia(recapitoEntity.getIdComune().getIdProvincia().getIdProvincie());
                recapitoDTO.setDescProvincia(recapitoEntity.getIdComune().getIdProvincia().getDescrizione());
            }
            if (recapitoEntity.getIdComune().getIdStato() != null) {
                recapitoDTO.setIdStato(recapitoEntity.getIdComune().getIdStato().getIdStato());
                recapitoDTO.setDescStato(recapitoEntity.getIdComune().getIdStato().getDescrizione());
            }
        }
        recapitoDTO.setEmail(recapitoEntity.getEmail());
        recapitoDTO.setFax(recapitoEntity.getFax());

        if (recapitoEntity.getIdDug() != null && recapitoEntity.getIdDug().getIdDug() != null) {
            recapitoDTO.setDescDug(recapitoEntity.getIdDug().getDescrizione());
            recapitoDTO.setIdDug(recapitoEntity.getIdDug().getIdDug());
        }
        recapitoDTO.setIdRecapito(recapitoEntity.getIdRecapito());
        recapitoDTO.setIndirizzo(recapitoEntity.getIndirizzo());
        recapitoDTO.setLocalita(recapitoEntity.getLocalita());
        recapitoDTO.setnCivico(recapitoEntity.getNCivico());
        recapitoDTO.setPec(recapitoEntity.getPec());
        recapitoDTO.setTelefono(recapitoEntity.getTelefono());
        recapitoDTO.setPresso(recapitoEntity.getPresso());
        recapitoDTO.setCodCivico(recapitoEntity.getCodCivico());
        recapitoDTO.setCodVia(recapitoEntity.getCodVia());
        recapitoDTO.setInternoLettera(recapitoEntity.getInternoLettera());
        recapitoDTO.setInternoNumero(recapitoEntity.getInternoNumero());
        recapitoDTO.setInternoScala(recapitoEntity.getInternoScala());
    }
    
	public void copyXML2Recapito(it.wego.cross.xml.Recapito recapitoXML, RecapitoDTO recapitoDTO) {
        if (recapitoXML.getCounter() != null) {
            recapitoDTO.setCounter(Utils.ib(recapitoXML.getCounter()));
        }
        recapitoDTO.setAltreInfoIndirizzo(recapitoXML.getIndirizzo());
        recapitoDTO.setCap(recapitoXML.getCap());
        recapitoDTO.setCasellaPostale(recapitoXML.getAltreInfoIndirizzo());
        recapitoDTO.setCellulare(recapitoXML.getCellulare());
        recapitoDTO.setDescTipoIndirizzo(recapitoXML.getDesTipoIndirizzo());
        recapitoDTO.setDug(Utils.ib(recapitoXML.getIdDug()));
        recapitoDTO.setEmail(recapitoXML.getEmail());
        recapitoDTO.setFax(recapitoXML.getFax());
        recapitoDTO.setDescComune(recapitoXML.getDesComune());
        recapitoDTO.setDescProvincia(recapitoXML.getDesProvincia());
        recapitoDTO.setDescStato(recapitoXML.getDesStato());
        LkComuni comune = null;
        if (recapitoXML.getIdComune() != null) {
            comune = lookupDao.findComuneById(Utils.ib(recapitoXML.getIdComune()));
        } else if (recapitoXML.getIdComune() == null && !Strings.isNullOrEmpty(recapitoXML.getDesComune())) {
            String descrizioneComune = recapitoXML.getDesComune();
            if (descrizioneComune.contains("(")){
                descrizioneComune = descrizioneComune.split("\\(")[0];
            }
            comune = lookupDao.findComuneByDescrizione(descrizioneComune);
        }
        if (comune != null) {
            recapitoDTO.setIdComune(comune.getIdComune());
            recapitoDTO.setDescComune(comune.getDescrizione());
            recapitoDTO.setIdProvincia(comune.getIdProvincia().getIdProvincie());
            recapitoDTO.setDescProvincia(comune.getIdProvincia().getDescrizione());
            recapitoDTO.setIdStato(comune.getIdStato().getIdStato());
            recapitoDTO.setDescStato(comune.getIdStato().getDescrizione());
        }
        recapitoDTO.setIdDug(Utils.ib(recapitoXML.getIdDug()));
        recapitoDTO.setIdRecapito(Utils.ib(recapitoXML.getIdRecapito()));
        recapitoDTO.setIdTipoIndirizzo(Utils.ib(recapitoXML.getIdTipoIndirizzo()));
        recapitoDTO.setIndirizzo(recapitoXML.getIndirizzo());
        recapitoDTO.setLocalita(recapitoXML.getLocalita());
        recapitoDTO.setnCivico(recapitoXML.getNCivico());
        recapitoDTO.setPec(recapitoXML.getPec());
        recapitoDTO.setTelefono(recapitoXML.getTelefono());
        recapitoDTO.setPresso(recapitoXML.getPresso());
    }

    public static void copyRecapito2XML(RecapitoDTO recapitoDTO, it.wego.cross.xml.Recapito recapitoXML, LkTipoIndirizzo tipoIndirizzo) {

        recapitoXML.setIdRecapito(Utils.bi((recapitoDTO.getIdRecapito())));
        recapitoXML.setCounter(Utils.bi((recapitoDTO.getCounter())));
        recapitoXML.setIdComune(Utils.bi(recapitoDTO.getIdComune()));
        recapitoXML.setIdProvincia(Utils.bi(recapitoDTO.getIdProvincia()));
        recapitoXML.setIdStato(Utils.bi(recapitoDTO.getIdStato()));
        if (tipoIndirizzo != null) {
            recapitoXML.setIdTipoIndirizzo(Utils.bi(tipoIndirizzo.getIdTipoIndirizzo()));
            recapitoXML.setDesTipoIndirizzo(tipoIndirizzo.getDescrizione());
        }
        recapitoXML.setDesComune(recapitoDTO.getDescComune());
        recapitoXML.setLocalita(recapitoDTO.getLocalita());
        recapitoXML.setDesProvincia(recapitoDTO.getDescProvincia());
        recapitoXML.setDesStato(recapitoDTO.getDescStato());
        recapitoXML.setIdDug(Utils.bi(recapitoDTO.getDug()));
        recapitoXML.setIndirizzo(recapitoDTO.getIndirizzo());
        recapitoXML.setNCivico(recapitoDTO.getnCivico());
        recapitoXML.setCap(recapitoDTO.getCap());
        recapitoXML.setCasellaPostale(recapitoDTO.getCasellaPostale());
        recapitoXML.setIndirizzo(recapitoDTO.getIndirizzo());
        recapitoXML.setTelefono(recapitoDTO.getTelefono());
        recapitoXML.setCellulare(recapitoDTO.getCellulare());
        recapitoXML.setFax(recapitoDTO.getFax());
        recapitoXML.setEmail(recapitoDTO.getEmail());
        recapitoXML.setPec(recapitoDTO.getPec());
        recapitoXML.setPresso(recapitoDTO.getPresso());
    }
    
    public static void copyRecapito2Entity(RecapitoDTO recapitoDTO, it.wego.cross.entity.Recapiti recapitoEntity) {
        recapitoEntity.setAltreInfoIndirizzo(recapitoDTO.getAltreInfoIndirizzo());
        recapitoEntity.setCap(recapitoDTO.getCap());
        recapitoEntity.setCasellaPostale(recapitoDTO.getCasellaPostale());
        recapitoEntity.setCellulare(recapitoDTO.getCellulare());
        recapitoEntity.setEmail(recapitoDTO.getEmail());
        recapitoEntity.setFax(recapitoDTO.getFax());
        recapitoEntity.setIndirizzo(recapitoDTO.getIndirizzo());
        recapitoEntity.setLocalita(recapitoDTO.getLocalita());
        recapitoEntity.setNCivico(recapitoDTO.getnCivico());
        recapitoEntity.setPec(recapitoDTO.getPec());
        recapitoEntity.setTelefono(recapitoDTO.getTelefono());
        recapitoEntity.setPresso(recapitoDTO.getPresso());
    }
}
