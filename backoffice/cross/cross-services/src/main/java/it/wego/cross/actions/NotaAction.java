package it.wego.cross.actions;

import it.wego.cross.dto.NotaDTO;
import it.wego.cross.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Component
public class NotaAction {

    @Autowired
    private NoteService noteService;

    @Transactional
    public NotaDTO aggiungiTransactional(NotaDTO nota) throws Exception {
        return noteService.aggiungi(nota);
    }
}
