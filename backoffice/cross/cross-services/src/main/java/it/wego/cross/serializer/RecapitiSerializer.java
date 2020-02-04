/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.LkDug;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author giuseppe
 */
@Component
public class RecapitiSerializer {

    @Autowired
    private LookupDao lookupDao;
    
    public static RecapitoDTO serialize(Recapiti recapito, LkTipoIndirizzo tipoIndirizzo) {
        RecapitoDTO dto = new RecapitoDTO();
        if (recapito.getIdComune() != null) {
            dto.setIdComune(recapito.getIdComune().getIdComune());
            dto.setDescComune(recapito.getIdComune().getDescrizione());
        }
        dto.setIdRecapito(recapito.getIdRecapito());
        dto.setLocalita(recapito.getLocalita());
        dto.setIndirizzo(recapito.getIndirizzo());
        dto.setnCivico(recapito.getNCivico());
        dto.setCap(recapito.getCap());
        dto.setCasellaPostale(recapito.getCasellaPostale());
        dto.setTelefono(recapito.getTelefono());
        dto.setCellulare(recapito.getCellulare());
        dto.setFax(recapito.getFax());
        dto.setEmail(recapito.getEmail());
        dto.setPec(recapito.getPec());
        if (tipoIndirizzo != null) {
            dto.setIdTipoIndirizzo(tipoIndirizzo.getIdTipoIndirizzo());
            dto.setDescTipoIndirizzo(tipoIndirizzo.getDescrizione());
        }
        dto.setPresso(recapito.getPresso());
        dto.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        return dto;
    }

    public static RecapitoDTO serialize(it.wego.cross.plugins.commons.beans.Recapito recapito) {
        RecapitoDTO dto = new RecapitoDTO();
        dto.setDescComune(recapito.getComune());
        dto.setLocalita(recapito.getLocalita());
        dto.setIndirizzo(recapito.getIndirizzo());
        dto.setnCivico(recapito.getnCivico());
        dto.setCap(recapito.getCap());
        dto.setCasellaPostale(recapito.getCasellaPostale());
        dto.setTelefono(recapito.getTelefono());
        dto.setCellulare(recapito.getCellulare());
        dto.setFax(recapito.getFax());
        dto.setEmail(recapito.getEmail());
        dto.setPec(recapito.getPec());
        dto.setDescTipoIndirizzo(recapito.getTipoRecapito());
        dto.setPresso(recapito.getPresso());
        dto.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        return dto;
    }

    public static it.wego.cross.xml.Recapito serialize(RecapitoDTO dto, Integer counter, LkTipoIndirizzo tipoIndirizzo) {
        if (dto == null) {
            return null;
        }
        it.wego.cross.xml.Recapito recapito = new it.wego.cross.xml.Recapito();
        recapito.setAltreInfoIndirizzo(dto.getAltreInfoIndirizzo());
        recapito.setCap(dto.getCap());
        recapito.setCasellaPostale(dto.getCasellaPostale());
        recapito.setCellulare(dto.getCellulare());
        if (counter != null) {
            recapito.setCounter(Utils.bi(counter));
        }
        recapito.setDesComune(dto.getDescComune());
        recapito.setDesProvincia(dto.getDescProvincia());
        recapito.setDesStato(dto.getDescStato());
        if (tipoIndirizzo != null) {
            recapito.setDesTipoIndirizzo(tipoIndirizzo.getDescrizione());
            recapito.setIdTipoIndirizzo(Utils.bi(tipoIndirizzo.getIdTipoIndirizzo()));
        }
        recapito.setEmail(dto.getEmail());
        recapito.setFax(dto.getFax());
        if (dto.getIdComune() != null) {
            recapito.setIdComune(Utils.bi(dto.getIdComune()));
        }
        if (dto.getIdDug() != null) {
            recapito.setIdDug(Utils.bi(dto.getIdDug()));
        }
        if (dto.getIdProvincia() != null) {
            recapito.setIdProvincia(Utils.bi(dto.getIdProvincia()));
        }
        if (dto.getIdRecapito() != null) {
            recapito.setIdRecapito(Utils.bi(dto.getIdRecapito()));
        }
        if (dto.getIdStato() != null) {
            recapito.setIdStato(Utils.bi(dto.getIdStato()));
        }
        recapito.setIndirizzo(dto.getIndirizzo());
        recapito.setLocalita(dto.getLocalita());
        recapito.setNCivico(dto.getnCivico());
        recapito.setPec(dto.getPec());
        recapito.setTelefono(dto.getTelefono());
        recapito.setPresso(dto.getPresso());
        recapito.setAltreInfoIndirizzo(dto.getAltreInfoIndirizzo());
        return recapito;
    }

    public static RecapitoDTO serialize(it.wego.cross.xml.Recapito recapito) {
        RecapitoDTO dto = new RecapitoDTO();
        if (recapito.getCounter() != null) {
            dto.setCounter(recapito.getCounter().intValue());
        } else {
            dto.setCounter(1);
        }
        dto.setDescComune(recapito.getDesComune());
        dto.setLocalita(recapito.getLocalita());
        dto.setIndirizzo(recapito.getIndirizzo());
        dto.setnCivico(recapito.getNCivico());
        dto.setCap(recapito.getCap());
        dto.setCasellaPostale(recapito.getCasellaPostale());
        dto.setFax(recapito.getFax());
        dto.setEmail(recapito.getEmail());
        dto.setPec(recapito.getPec());
        if (recapito.getIdTipoIndirizzo() != null) {
            dto.setIdTipoIndirizzo(recapito.getIdTipoIndirizzo().intValue());
            dto.setDescTipoIndirizzo(recapito.getDesTipoIndirizzo());
        }
        dto.setPresso(recapito.getPresso());
        dto.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        return dto;
    }

    public static it.wego.cross.xml.Recapito serialize(it.wego.cross.entity.Recapiti recapito) {
        it.wego.cross.xml.Recapito xml = new it.wego.cross.xml.Recapito();
        xml.setIdRecapito(Utils.bi(recapito.getIdRecapito()));
        xml.setPresso(recapito.getPresso());
        if (recapito.getIdComune() != null) {
            xml.setDesComune(recapito.getIdComune().getDescrizione());
            xml.setIdComune(Utils.bi(recapito.getIdComune().getIdComune()));
            if (recapito.getIdComune().getIdStato() != null) {
                xml.setDesStato(recapito.getIdComune().getIdStato().getDescrizione());
                xml.setIdStato(Utils.bi(recapito.getIdComune().getIdStato().getIdStato()));
            }
            if (recapito.getIdComune().getIdProvincia() != null) {
                xml.setDesProvincia(recapito.getIdComune().getIdProvincia().getDescrizione());
                xml.setIdProvincia(Utils.bi(recapito.getIdComune().getIdProvincia().getIdProvincie()));
            }
        }
        xml.setLocalita(recapito.getLocalita());
        if (recapito.getIdDug() != null) {
            xml.setIdDug(Utils.bi(recapito.getIdDug().getIdDug()));
        }
        xml.setIndirizzo(recapito.getIndirizzo());
        xml.setCodiceVia(recapito.getCodVia());
        xml.setNCivico(recapito.getNCivico());
        xml.setCodiceCivico(recapito.getCodCivico());
        xml.setInternoLettera(recapito.getInternoLettera());
        xml.setInternoNumero(recapito.getInternoNumero());
        xml.setInternoScala(recapito.getInternoScala());
        xml.setLettera(recapito.getLettera());
        xml.setColore(recapito.getColore());
        xml.setCap(recapito.getCap());
        xml.setCasellaPostale(recapito.getCasellaPostale());
        xml.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        xml.setTelefono(recapito.getTelefono());
        xml.setCellulare(recapito.getCellulare());
        xml.setFax(recapito.getFax());
        xml.setEmail(recapito.getEmail());
        xml.setPec(recapito.getPec());
        xml.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        //Campi non gestiti
//        recapitoXML.setCounter(BigInteger.ZERO);
//        recapitoXML.setIdTipoIndirizzo(BigInteger.ZERO);
//        recapitoXML.setDesTipoIndirizzo(null);


        return xml;
    }
    
    public it.wego.cross.entity.Recapiti serializeEntity(it.wego.cross.xml.Recapito recapito) {
        it.wego.cross.entity.Recapiti entity = new it.wego.cross.entity.Recapiti();
        entity.setPresso(recapito.getPresso());
        entity.setLocalita(recapito.getLocalita());
        if (recapito.getIdDug() != null) {
            LkDug dug = lookupDao.findDugById(Utils.ib(recapito.getIdDug()));
            entity.setIdDug(dug);
        }
        entity.setIndirizzo(recapito.getIndirizzo());
        entity.setCodVia(recapito.getCodiceVia());
        entity.setNCivico(recapito.getNCivico());
        entity.setCodCivico(recapito.getCodiceCivico());
        entity.setInternoLettera(recapito.getInternoLettera());
        entity.setInternoNumero(recapito.getInternoNumero());
        entity.setInternoScala(recapito.getInternoScala());
        entity.setLettera(recapito.getLettera());
        entity.setColore(recapito.getColore());
        entity.setCap(recapito.getCap());
        entity.setCasellaPostale(recapito.getCasellaPostale());
        entity.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        entity.setTelefono(recapito.getTelefono());
        entity.setCellulare(recapito.getCellulare());
        entity.setFax(recapito.getFax());
        entity.setEmail(recapito.getEmail());
        entity.setPec(recapito.getPec());
        entity.setAltreInfoIndirizzo(recapito.getAltreInfoIndirizzo());
        return entity;
    }
}
