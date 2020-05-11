/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface AllegatiService {

    public AllegatoDTO findAllegatoByIdNullSafe(Integer idAllegato, Enti ente) throws Exception;
    
    public AllegatoDTO getAllegato(Integer idAllegato, Integer idEnte) throws Exception;

    public Allegati findAllegatoById(Integer idAllegato) throws Exception;

    public boolean checkAllegato(Pratica pratica, Integer idFile);

    public void aggiornaAllegato(Allegati allegato) throws Exception;

    public Allegati salvaAllegatoFS(AllegatoDTO allegato, Enti ente, LkComuni comune) throws Exception;
    
    public Allegati salvaAllegatoDB(AllegatoDTO allegato, Enti ente, LkComuni comune) throws Exception;

    public Allegati findAllegatoByIdFileEsterno(String idFileEsterno) throws Exception;

    public String getFile(String code);

    public String putFile(String code);
    
    public void deleteFileMap();
    
    public it.wego.cross.plugins.commons.beans.Allegato getAllegatoDaProtocollo(String idFile, Enti ente) throws Exception;

	public AllegatoDTO creaFileSuapEnte(Pratica pratica, ComunicazioneDTO comunicazione) throws DatatypeConfigurationException, FileNotFoundException, UnsupportedEncodingException, IOException, Exception;
   
    
}
