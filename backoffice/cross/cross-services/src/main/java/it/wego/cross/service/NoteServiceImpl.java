/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.entity.NotePratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.PraticheSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private UsefulService usefulService;

    @Override
    public NotaDTO aggiungi(NotaDTO dto) throws Exception {
        it.wego.cross.entity.NotePratica nota = praticheSerializer.serialize(dto);
        Pratica pratica = praticaDao.findPratica(dto.getIdPratica());
        Utente utente = utentiDao.findUtenteByIdUtente(dto.getIdUtente());
        nota.setIdUtente(utente);
        nota.setIdPratica(pratica);
        praticaDao.insert(nota);
        usefulService.flush();
        dto.setIdNota(nota.getIdNotePratica());
        dto.setDesUtente(utente.getCognome() + " " + utente.getNome());
        dto.setIdNota(nota.getIdNotePratica());
        dto.setIdPratica(nota.getIdPratica().getIdPratica());
        dto.setIdUtente(utente.getIdUtente());
        dto.setTesto(nota.getTesto());
        String testoBreve = StringUtils.abbreviate(nota.getTesto(), 80);
        dto.setTestoBreve(testoBreve);
        return dto;
    }

    @Override
    public NotaDTO findNotaById(Integer idNota) {
        NotePratica nota = praticaDao.findNotaPraticaById(idNota);
        NotaDTO dto = new NotaDTO();
        dto.setDataInserimento(nota.getDataInserimento());
        Utente idUtente = nota.getIdUtente();
        dto.setDesUtente(idUtente.getCognome() + " " + idUtente.getNome());
        dto.setIdNota(idNota);
        dto.setIdPratica(nota.getIdPratica().getIdPratica());
        dto.setIdUtente(idUtente.getIdUtente());
        dto.setTesto(nota.getTesto());
        String testoBreve = StringUtils.abbreviate(nota.getTesto(), 80);
        dto.setTestoBreve(testoBreve);
        return dto;
    }

}
