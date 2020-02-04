/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.StatoDTO;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkStati;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
public class LuoghiSerializer {

    public static ComuneDTO serialize(LkComuni comune) {
        ComuneDTO c = new ComuneDTO();
        c.setCodCatastale(comune.getCodCatastale());
        c.setDataFineValidita(comune.getDataFineValidita());
        c.setDescrizione(comune.getDescrizione());
        c.setIdComune(comune.getIdComune());
        ProvinciaDTO provincia = serialize(comune.getIdProvincia());
        c.setProvincia(provincia);
        StatoDTO stato = serialize(comune.getIdStato());
        c.setStato(stato);
        return c;
    }

    public static ProvinciaDTO serialize(LkProvincie provincia) {
        ProvinciaDTO p = new ProvinciaDTO();
        p.setCodCatastale(provincia.getCodCatastale());
        p.setDataFineValidita(provincia.getDataFineValidita());
        p.setFlgInfocamere(Utils.flag(provincia.getFlgInfocamere()));
        p.setIdProvincie(provincia.getIdProvincie());
        p.setDescrizione(provincia.getDescrizione());
        return p;
    }

    public static StatoDTO serialize(LkStati stato) {
        StatoDTO s = new StatoDTO();
        s.setCodIstat(stato.getCodIstat());
        s.setDataFine(stato.getDataFine());
        s.setDataInizio(stato.getDataInizio());
        s.setDescrizione(stato.getDescrizione());
        s.setIdStato(stato.getIdStato());
        return s;
    }
}
