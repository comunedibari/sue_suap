/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.search.FormaGiuridicaDTO;
import it.wego.cross.entity.LkFormeGiuridiche;

/**
 *
 * @author giuseppe
 */
public class FormeGiuridicheSerializer {

    public static FormaGiuridicaDTO serialize(LkFormeGiuridiche formaGiuridica) {
        FormaGiuridicaDTO f = new FormaGiuridicaDTO();
        f.setCodFormaGiuridica(formaGiuridica.getCodFormaGiuridica());
        f.setDescrizione(formaGiuridica.getDescrizione());
        f.setIdFormaGiuridica(formaGiuridica.getIdFormeGiuridiche());
        return f;
    }
}
