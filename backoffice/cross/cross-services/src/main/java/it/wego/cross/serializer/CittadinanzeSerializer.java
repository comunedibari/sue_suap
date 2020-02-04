/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.entity.LkCittadinanza;

/**
 *
 * @author giuseppe
 */
public class CittadinanzeSerializer {
    public static CittadinanzaDTO serialize(LkCittadinanza lookup){
        CittadinanzaDTO dto= new CittadinanzaDTO();
        dto.setCodCittadinanza(lookup.getCodCittadinanza());
        dto.setDescrizione(lookup.getDescrizione());
        dto.setIdCittadinanza(lookup.getIdCittadinanza());
        return dto;
    }
}
