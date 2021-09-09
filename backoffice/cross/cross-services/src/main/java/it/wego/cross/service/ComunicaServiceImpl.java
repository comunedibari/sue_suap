/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.ComboDTO;
import it.wego.cross.dto.comunica.ComuneRiferimentoDTO;
import it.wego.cross.dto.comunica.SportelloDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GabrieleM
 */
@Service
public class ComunicaServiceImpl implements ComunicaService {
    @Autowired
    LookupService lookupService;
    @Autowired
    EntiService entiService;
    
     public List<ComboDTO> serializeProvincie(List<LkProvincie> provincie) {
        List<ComboDTO> combos = new ArrayList<ComboDTO>();
        if (provincie != null && !provincie.isEmpty()) {
            for (LkProvincie provincia : provincie) {
                ComboDTO combo = new ComboDTO();
                combo.setDescription(provincia.getDescrizione());
                combo.setId(String.valueOf(provincia.getIdProvincie()));
                combos.add(combo);
            }
        }
        return combos;

    }

    public List<ComboDTO> serializeFormeGiuridiche(List<LkFormeGiuridiche> formeGiuridiche) {
        List<ComboDTO> combos = new ArrayList<ComboDTO>();
        if (formeGiuridiche != null && !formeGiuridiche.isEmpty()) {
            for (LkFormeGiuridiche formagiuridica : formeGiuridiche) {
                ComboDTO combo = new ComboDTO();
                combo.setDescription(formagiuridica.getDescrizione());
                combo.setId(String.valueOf(formagiuridica.getIdFormeGiuridiche()));
                combos.add(combo);
            }
        }
        return combos;
    }

    public List<ComboDTO> serializeNazionalita(List<LkNazionalita> nazionalita) {
        List<ComboDTO> combos = new ArrayList<ComboDTO>();
        if (nazionalita != null && !nazionalita.isEmpty()) {
            for (LkNazionalita naz : nazionalita) {
                ComboDTO combo = new ComboDTO();
                combo.setDescription(naz.getDescrizione());
                combo.setId(String.valueOf(naz.getIdNazionalita()));
                combos.add(combo);
            }
        }
        return combos;
    }

    public List<ComboDTO> serializeEnti(List<Enti> enti) {
        List<ComboDTO> combos = new ArrayList<ComboDTO>();
        if (enti != null && !enti.isEmpty()) {
            for (Enti ente : enti) {
                ComboDTO combo = new ComboDTO();
                combo.setDescription(ente.getDescrizione());
                combo.setId(String.valueOf(ente.getIdEnte()));
                combos.add(combo);
            }
        }
        return combos;
    }

    public List<ComboDTO> serializeProcedimenti(List<ProcedimentiLocalizzatiView> procedimentiSportello) {
        List<ComboDTO> combos = new ArrayList<ComboDTO>();
        if (procedimentiSportello != null && !procedimentiSportello.isEmpty()) {
            for (ProcedimentiLocalizzatiView procedimento : procedimentiSportello) {
                ComboDTO combo = new ComboDTO();
                combo.setDescription(procedimento.getDesProc());
                combo.setId(String.valueOf(procedimento.getIdProc()));
                combos.add(combo);
            }
        }
        return combos;
    }
    
    public ComuneRiferimentoDTO getComuneRiferimento(it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP riepilogoPraticaSuap) throws Exception {
        String codiceAmministrazione = riepilogoPraticaSuap.getIntestazione().getUfficioDestinatario().getCodiceAmministrazione();
        //Il codice amministrazione è nella forma C_D969
        String codiceCatastaleComune = codiceAmministrazione.split("C_")[1];
        String t= codiceCatastaleComune;
        LkComuni comune = lookupService.findComuneByCodCatastale(codiceCatastaleComune);
        if (comune != null) {
            ComuneRiferimentoDTO dto = new ComuneRiferimentoDTO();
            dto.setDescription(comune.getDescrizione());
            dto.setId(comune.getIdComune());
            return dto;
        } else {
            Exception e = new Exception("Non e' stato trovato un comune con il codice catastale " + codiceCatastaleComune);
            throw e;
        }
    }
    
    public SportelloDTO getSportelloDestinazione(it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP riepilogoPraticaSuap) throws Exception {
        Integer identificativoSuap = Utils.ib(riepilogoPraticaSuap.getIntestazione().getUfficioDestinatario().getIdentificativoSuap());
        Enti ente = entiService.findByIdentificativoSuap(String.valueOf(identificativoSuap));
        if (ente != null) {
            SportelloDTO dto = new SportelloDTO();
            dto.setDescrizione(ente.getDescrizione());
            dto.setId(ente.getIdEnte());
            return dto;
        } else {
            throw new Exception("Non e' stato trovato nessun ente con codice il identificativio suap " + identificativoSuap);
        }
    }
}
