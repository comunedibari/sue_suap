/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaProtocolloDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface PraticheProtocolloService {

    public Long countPraticheProtocollo(Filter filter, Integer idEnte) throws Exception;

    public List<PraticaProtocolloDTO> findPraticheProtocollo(Filter filter, Integer idEnte) throws Exception;

    public Long countDocumentiProtocollo(Filter filter, Integer idEnte) throws Exception;

    public List<PraticaProtocolloDTO> findDocumentiProtocollo(Filter filter, Integer idEnte) throws Exception;

    public PraticheProtocollo findPraticaProtocolloById(Integer idProtocollo);
    
    public PraticheProtocollo findPraticaProtocolloByIdentificativoPratica(String identificativoProtocollo);

    //Usato da caricamento manuale "daNumeroProtocolloSubmit". E' giusto che cerchi il record "PraticaProtocollo" per protocollo. E' il protocollo dell'istanza in input.
    public PraticheProtocollo findPraticaProtocolloByAnnoProtocollo(Integer anno, String protocollo);

    public void aggiorna(PraticheProtocollo praticaProtocollo) throws Exception;
    
    public void inserisci(PraticheProtocollo praticaProtocollo) throws Exception;

    public List<PraticheProtocollo> findByCodDocumento(String idDocumento);

    public Date findMaxDataSincronizzazione();

    public PraticheProtocollo findByRegistroFascicoloAnnoTipo(String codRegistro, String fascicolo, Integer annoProtocollo, String tipoDocumentoPratica);

    public List<AllegatoDTO> getAllegatiProtocollo(PraticheProtocollo pratica) throws Exception;

    public List<AllegatoDTO> getAllegatiProtocollo(DocumentoProtocolloResponse documentoProtocolloResponse);

    public Staging getStaging(PraticheProtocollo praticaProtocollo, it.wego.cross.xml.Pratica praticaCross, Utente utenteConnesso) throws Exception;

    public DocumentoProtocolloResponse getDocumentoProtocollo(PraticheProtocollo praticaProtocollo, Enti ente) throws Exception;
}
