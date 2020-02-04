package it.wego.cross.service;

import it.wego.cross.dao.ErroriDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.DizionarioErrori;
import it.wego.cross.entity.Errori;
import it.wego.cross.serializer.ErroriSerializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErroriServiceImpl implements ErroriService {

    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private ErroriDao erroriDao;
    @Autowired
    private ErroriSerializer erroriSerializer;

    @Override

    public DizionarioErrori findDizionarioErroriByCod(String codice) {
        DizionarioErrori errore = lookupDao.findDizionarioErroriByCod(codice);
        return errore;
    }

    @Override
    public void inserisci(Errori errori) throws Exception {
        erroriDao.insert(errori);
        erroriDao.flush();
    }

    @Override
    public void aggiorna(Errori errori) throws Exception {
        erroriDao.update(errori);
        erroriDao.flush();
    }

//    @Override
//    public Long countErrori() {
//        Long count = erroriDao.countAllErrori();
//        return count;
//    }
    @Override
    public Long countErrori(Filter filter) {
        Long count = erroriDao.countErrori(filter);
        return count;
    }

    @Override
    public List<ErroreDTO> getErrori(Filter filter) {
        List<Errori> errori = erroriDao.findAll(filter);
        List<ErroreDTO> dtos = new ArrayList<ErroreDTO>();
        if (errori != null && !errori.isEmpty()) {
            for (Errori errore : errori) {
                ErroreDTO dto = erroriSerializer.serialize(errore);
                dtos.add(dto);
            }
        }
        return dtos;
    }

//    @Override
//    public Long countErroriAttivi() {
//        Long result;
//        result = erroriDao.countAllErroriAttivi();
//        return result;
//    }
    @Override
    public ErroreDTO findErroreById(Integer id) {
        Errori errore = erroriDao.findByIdErrore(id);
        if (errore != null) {
            ErroreDTO dto = erroriSerializer.serialize(errore);
            return dto;
        } else {
            return null;
        }
    }
}
