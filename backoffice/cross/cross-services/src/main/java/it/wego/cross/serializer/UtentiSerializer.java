/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.ProcedimentiTestiDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.UtenteMinifiedDTO;
import it.wego.cross.dto.dozer.EnteDTO;
import it.wego.cross.dto.dozer.PermessoDTO;
import it.wego.cross.dto.dozer.ProcedimentoDTO;
import it.wego.cross.dto.UtenteRuoloEnteDTO;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.entity.UtenteRuoloProcedimento;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author giuseppe
 */
@Component
public class UtentiSerializer {

    @Autowired
    private Mapper mapper;

    public UtenteDTO serializeUtente(Utente utente) {
        UtenteDTO user = serializeUtenteSenzaRuoli(utente);
        if (utente.getUtenteRuoloEnteList() != null && utente.getUtenteRuoloEnteList().size() > 0) {
            user.setUtenteRuoloEnte(new ArrayList<UtenteRuoloEnteDTO>());
            for (UtenteRuoloEnte ruolo : utente.getUtenteRuoloEnteList()) {
                user.getUtenteRuoloEnte().add(serializeRuolo(ruolo));
            }
        }
        return user;
    }

    public static UtenteDTO serializeUtenteSenzaRuoli(Utente utente) {
        UtenteDTO user = new UtenteDTO();
        user.setCodiceFiscale(utente.getCodiceFiscale());
        user.setCodiceUtente(utente.getIdUtente());
        user.setIdUtente(utente.getIdUtente());
        user.setDataAttivazione(utente.getDataAttivazione());
        user.setNominativo(utente.getCognome() + " " + utente.getNome());
        user.setUsername(utente.getUsername());
        user.setCognome(utente.getCognome());
        user.setDataAttivazione(utente.getDataAttivazione());
        user.setEmail(utente.getEmail());
        user.setIdUtente(utente.getIdUtente());
        user.setNome(utente.getNome());
        user.setNote(utente.getNote());
        user.setStatus(utente.getStatus());
        user.setSuperuser(Character.toString(utente.getSuperuser()));
        user.setTelefono(utente.getTelefono());
        user.setPassword(utente.getPassword());
        return user;
    }

    public static Utente serializeUtenteDTOSenzaRuoli(UtenteDTO utente) {
        Utente user = new Utente();
        user.setCodiceFiscale(utente.getCodiceFiscale());
        user.setIdUtente(utente.getIdUtente());
        user.setDataAttivazione(utente.getDataAttivazione());
        user.setUsername(utente.getUsername());
        user.setCognome(utente.getCognome());
        user.setDataAttivazione(utente.getDataAttivazione());
        user.setEmail(utente.getEmail());
        user.setIdUtente(utente.getIdUtente());
        user.setNome(utente.getNome());
        user.setNote(utente.getNote());
        user.setStatus(utente.getStatus());
        user.setSuperuser(utente.getSuperuser().charAt(0));
        user.setTelefono(utente.getTelefono());
        user.setEstrazioniUser(utente.getEstrazioniUser().charAt(0));
        user.setEstrazioniCilaTodoUser(utente.getEstrazioniCilaTodoUser().charAt(0));
        return user;
    }

    public UtenteRuoloEnteDTO serializeRuolo(UtenteRuoloEnte ruolo) {
        UtenteRuoloEnteDTO ruoloDTO = new UtenteRuoloEnteDTO();

        if (ruolo.getCodPermesso() != null) {
            ruoloDTO.setCodPermesso(ruolo.getCodPermesso().getCodPermesso());
            ruoloDTO.setDesPermesso(ruolo.getCodPermesso().getDescrizione());
        }

        if (ruolo.getIdEnte() != null) {
            ruoloDTO.setIdEnte(ruolo.getIdEnte().getIdEnte());
            ruoloDTO.setDesEnte(ruolo.getIdEnte().getDescrizione());
        }
        if (ruolo.getUtenteRuoloProcedimentoList() != null && !ruolo.getUtenteRuoloProcedimentoList().isEmpty()){
            for (UtenteRuoloProcedimento pe :ruolo.getUtenteRuoloProcedimentoList()){
                if (pe.getProcedimentiEnti() != null){
                    ProcedimentiTestiDTO pet = new ProcedimentiTestiDTO();
                    pet.setIdProcedimento(pe.getProcedimentiEnti().getIdProc().getIdProc());
                    pet.setDescrizione(pe.getProcedimentiEnti().getIdProc().getProcedimentiTestiList().get(0).getDesProc());
                    pet.setCodLingua("it");
                    ruoloDTO.getProcedimentiList().add(pet);
                }
            }
            
        }
        return ruoloDTO;
    }

    public static UtenteMinifiedDTO serializeUtenteMinified(Utente utente) {
        UtenteMinifiedDTO user = new UtenteMinifiedDTO();
        user.setId(utente.getIdUtente());
        user.setDataAttivazione(utente.getDataAttivazione());
        user.setDescription(utente.getCognome() + " " + utente.getNome());
        user.setUsername(utente.getUsername());
        user.setCognome(utente.getCognome());
        user.setDataAttivazione(utente.getDataAttivazione());
        user.setEmail(utente.getEmail());
        user.setNome(utente.getNome());
        return user;
    }
}
