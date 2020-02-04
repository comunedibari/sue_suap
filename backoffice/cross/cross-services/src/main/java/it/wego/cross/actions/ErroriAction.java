package it.wego.cross.actions;

import it.wego.cross.beans.layout.Message;
import it.wego.cross.dao.ErroriDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Errori;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.ErroriSerializer;
import it.wego.cross.service.ErroriService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Component
public class ErroriAction {

    @Autowired
    private ErroriService erroriService;
    @Autowired
    private ErroriSerializer erroriSerializer;
    @Autowired
    private ErroriDao erroriDao;
    @Autowired
    protected UsefulDao usefulDao;

    @Transactional
    public Integer saveError(ErroreDTO errore) {
        try {
            Log.APP.error(errore.getDescrizione(), errore.getException());
            Errori errori = erroriSerializer.serialize(errore);
            erroriService.inserisci(errori);
            usefulDao.flush();
            return errori.getIdErrore();
        } catch (Exception ex) {
            Message msg = new Message();
            msg.setMessages(Arrays.asList("Impossibile salvare il messaggio di errore."));
            Log.APP.error(msg.toString(), ex);
            return null;
        }
    }

    public ErroreDTO getError(String errorCode, String description, Exception e, Pratica pratica, Utente utente) {
        ErroreDTO errore = new ErroreDTO();
        errore.setCodErrore(errorCode);
        errore.setData(new Date());
        errore.setDescrizione(description);
        errore.setStatus("A");
        errore.setTrace(Utils.getStackTrace(e));
        errore.setIdPratica(pratica != null ? pratica.getIdPratica() : null);
        errore.setIdUtente(utente != null ? utente.getIdUtente() : null);
        errore.setException(e);
        return errore;
    }

    @Transactional
    public void setErrorToDeleted(Integer idErrore) throws Exception {
        Errori errore = erroriDao.findByIdErrore(idErrore);
        errore.setStatus('E');
        erroriDao.update(errore);
    }

    @Transactional
    public void setErrorToActive(Integer idErrore) throws Exception {
        Errori errore = erroriDao.findByIdErrore(idErrore);
        errore.setStatus('A');
        erroriDao.update(errore);
    }
}
