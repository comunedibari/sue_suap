package it.wego.cross.serializer;

import it.wego.cross.dto.DizionarioErroriDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.DizionarioErrori;
import it.wego.cross.entity.Errori;
import it.wego.cross.entity.Pratica;
import it.wego.cross.service.ErroriService;
import it.wego.cross.service.PraticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class ErroriSerializer {

    @Autowired
    private ErroriService erroriService;
    @Autowired
    private PraticheService praticheService;

    public Errori serialize(ErroreDTO erroreDTO) throws Exception {
        Errori errore = new Errori();
        if (erroreDTO != null) {
            DizionarioErrori dizionario = erroriService.findDizionarioErroriByCod(erroreDTO.getCodErrore());
            errore.setCodErrore(dizionario);
            errore.setData(erroreDTO.getData());
            errore.setDescrizione(erroreDTO.getDescrizione());
            errore.setIdErrore(erroreDTO.getIdErrore());
            if (erroreDTO.getIdPratica() != null) {
                Pratica pratica = praticheService.getPratica(erroreDTO.getIdPratica());
                errore.setIdPratica(pratica);
            }
            if ((erroreDTO.getStatus() != null) && (!"".equals(erroreDTO.getStatus()))) {
                errore.setStatus(erroreDTO.getStatus().charAt(0));
            }
            errore.setTrace(erroreDTO.getTrace());
        }
        return errore;
    }

    public ErroreDTO serialize(Errori errore) {
        ErroreDTO dto = new ErroreDTO();
        if (errore.getCodErrore() != null) {
            dto.setCodErrore(errore.getCodErrore().getCodErrore());
        }
        dto.setData(errore.getData());
        dto.setDescrizione(errore.getDescrizione());
        dto.setIdErrore(errore.getIdErrore());
        if (errore.getIdPratica() != null) {
            dto.setIdPratica(errore.getIdPratica().getIdPratica());
        }
        if (errore.getIdUtente() != null) {
            dto.setIdUtente(errore.getIdUtente().getIdUtente());
        }
        if (errore.getStatus() != null) {
            dto.setStatus(errore.getStatus().toString());
        }
        dto.setTrace(errore.getTrace());
        return dto;
    }

    public it.wego.cross.entity.DizionarioErrori serialize(DizionarioErroriDTO dto) {
        DizionarioErrori dizionario = new DizionarioErrori();
        dizionario.setCodErrore(dto.getCodErrore());
        dizionario.setDescrizione(dto.getDescrizione());
        dizionario.setNote(dto.getNote());
        return dizionario;
    }

    public DizionarioErroriDTO serialize(DizionarioErrori dizionario) {
        DizionarioErroriDTO dto = new DizionarioErroriDTO();
        dto.setCodErrore(dizionario.getCodErrore());
        dto.setDescrizione(dizionario.getDescrizione());
        dto.setNote(dizionario.getNote());
        return dto;
    }
}
