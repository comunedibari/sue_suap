/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.service;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazionerea.bean.AnagraficaPraticaDTO;
import it.wego.cross.events.comunicazionerea.bean.ComunicazioneReaDTO;
import it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea;
import it.wego.cross.plugins.commons.beans.Allegato;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface ComunicazioneReaService {

    public List<Allegato> comunicazioneArrivoPraticaSuap(Pratica pratica, ComunicazioneReaDTO istruttoria, Utente user,  String statoPratica) throws Exception;

    public List<AnagraficaPraticaDTO> getAnagraficheFisichePraticaList(Pratica pratica);

    public List<AnagraficaPraticaDTO> getAnagraficheGiuridichePraticaList(Pratica pratica);

    public RiIntegrazioneRea getIntegrazioneRea(Pratica pratica);

    public List<AllegatoDTO> getAllegati(Pratica pratica);
}
