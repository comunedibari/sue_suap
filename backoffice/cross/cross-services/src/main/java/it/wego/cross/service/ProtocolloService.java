/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import org.springframework.stereotype.Service;

import it.wego.cross.plugins.commons.beans.Allegato;

/**
 *
 * @author giuseppe
 */
@Service
public interface ProtocolloService {

    public Allegato getStampedDocument(String protocolId, String attachmentName) throws Exception;
    
    public byte[] getDocumentiProtocollatiRuvo(String protocolId, String nomeFileZippato) throws Exception;
    
    public byte[] getFileProtocollatoTimbratoRuvo(String protocolId, String nomeFile) throws Exception;
    
    public String getNomeFileZippato(Integer idPratica, Integer idPraticaEvento);
    
    public byte[] recuperaZipfileTimbrati(String nomefileZippato);
    
}
