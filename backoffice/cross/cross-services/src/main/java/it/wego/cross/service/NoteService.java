/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.NotaDTO;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public interface NoteService {


    public NotaDTO aggiungi(NotaDTO notaDTO) throws Exception;
    
    public NotaDTO findNotaById(Integer idNota);

}
