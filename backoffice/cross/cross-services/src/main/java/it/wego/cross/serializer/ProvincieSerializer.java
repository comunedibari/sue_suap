/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.entity.LkProvincie;

/**
 *
 * @author giuseppe
 */
public class ProvincieSerializer {
    public static ProvinciaDTO serialize(LkProvincie provincia){
        ProvinciaDTO p = new ProvinciaDTO();
        p.setCodCatastale(provincia.getCodCatastale());
        p.setDataFineValidita(provincia.getDataFineValidita());
        p.setDescrizione(provincia.getDescrizione());
        p.setFlgInfocamere(String.valueOf(provincia.getFlgInfocamere()));
        p.setIdProvincie(provincia.getIdProvincie());
        return p;
    }
}
