/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.EnteDTO;
import it.wego.cross.entity.Enti;

/**
 *
 * @author giuseppe
 */
public class EntiSerializer {
    
    public static EnteDTO serializer(Enti ente) {
        EnteDTO e = new EnteDTO();
        e.setCitta(ente.getCitta());
        e.setDescrizione(ente.getDescrizione());
        e.setEmail(ente.getEmail());
        e.setIdEnte(ente.getIdEnte());
        e.setPec(ente.getPec());
        e.setProvincia(ente.getProvincia());
        e.setTelefono(ente.getTelefono());
        e.setTipoEnte(ente.getTipoEnte());
        e.setCodiceAoo(ente.getCodiceAoo());
        e.setCodiceFiscale(ente.getCodiceFiscale());
        e.setPartitaIva(ente.getPartitaIva());
        e.setIndirizzo(ente.getIndirizzo());
        e.setCap(ente.getCap());
        e.setFax(ente.getFax());
        e.setCodiceIstat(ente.getCodiceIstat());
        e.setCodiceCatastale(ente.getCodiceCatastale());
        e.setIdentificativoSuap(ente.getIdentificativoSuap());
        e.setCodEnte((ente.getCodEnte()));
        String unitaOrganizzativa = ente.getUnitaOrganizzativa();
        if ((unitaOrganizzativa != null) && (!"".equals(unitaOrganizzativa))) {
            e.setUnitaOrganizzativa(ente.getUnitaOrganizzativa());
        } else {
            e.setUnitaOrganizzativa(ente.getCodEnte());
        }
        e.setCodiceAmministrazione(ente.getCodiceAmministrazione());
        return e;
    }
    
    public static Enti serializer(EnteDTO enteDTO) {
        Enti ente = new Enti();
        ente.setCodiceFiscale(enteDTO.getCodiceFiscale());
        ente.setPartitaIva(enteDTO.getPartitaIva());
        ente.setIndirizzo(enteDTO.getIndirizzo());
        ente.setCap(enteDTO.getCap());
        ente.setCitta(enteDTO.getCitta());
        ente.setProvincia(enteDTO.getProvincia());
        ente.setTelefono(enteDTO.getTelefono());
        ente.setFax(enteDTO.getFax());
        ente.setDescrizione(enteDTO.getDescrizione());
        ente.setEmail(enteDTO.getEmail());
        ente.setIdEnte(enteDTO.getIdEnte());
        ente.setPec(enteDTO.getPec());
        ente.setCodiceIstat(enteDTO.getCodiceIstat());
        ente.setTipoEnte(enteDTO.getTipoEnte());
        ente.setCodiceCatastale(enteDTO.getCodiceCatastale());
        ente.setIdentificativoSuap(enteDTO.getIdentificativoSuap());
        ente.setCodEnte(enteDTO.getCodEnte());
        ente.setCodiceAoo(enteDTO.getCodiceAoo());
        String unitaOrganizzativa = enteDTO.getUnitaOrganizzativa();
        if ((unitaOrganizzativa != null) && (!"".equals(unitaOrganizzativa))) {
            ente.setUnitaOrganizzativa(enteDTO.getUnitaOrganizzativa());
        } else {
            ente.setUnitaOrganizzativa(enteDTO.getCodEnte());
        }
        ente.setCodiceAmministrazione(enteDTO.getCodiceAmministrazione());
        return ente;
    }
}
