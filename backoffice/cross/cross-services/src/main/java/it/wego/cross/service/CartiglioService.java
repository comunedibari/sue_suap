/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import org.springframework.stereotype.Service;

import it.wego.cross.dto.Cartiglio;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.entity.Pratica;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public interface CartiglioService {

    public Cartiglio createCartiglio(Pratica pratica);

    public Cartiglio createCartiglio(Pratica pratica, EventoDTO evento);
    
}
