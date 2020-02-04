/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.search.TipoCollegioDTO;
import it.wego.cross.entity.LkTipoCollegio;

/**
 *
 * @author giuseppe
 */
public class TipoCollegioSerializer {

    public static TipoCollegioDTO serialize(LkTipoCollegio tipoCollegio) {
        TipoCollegioDTO dto = new TipoCollegioDTO();
        dto.setCodCollegio(tipoCollegio.getCodCollegio());
        dto.setDescrizione(tipoCollegio.getDescrizione());
        dto.setIdTipoCollegio(tipoCollegio.getIdTipoCollegio());
        return dto;
    }
}
