/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.protocollo;

import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.xml.Pratica;
import java.util.List;
import javax.annotation.Nullable;

/**
 *
 * @author giuseppe
 * I metodi:
 *  findByProtocollo
 *  queryProtocollo
 *  getTipologiaDocumentoPerPratica
 *  getTipologiaDocumentoArrivo
 * devono essere implementati solo in caso di integrazione con il protocollo in cui vengono importate da questo le pratiche (o in modo schedulato o in automatico).
 */
public interface GestioneProtocollo {

    //Utilizzato per costruire la richiesta di protocollazione
    public DocumentoProtocolloRequest getDocumentoProtocolloDaDatabase(it.wego.cross.entity.Pratica pratica, PraticheEventi praticaEvento, List<Allegato> allegatiNuovi, List<SoggettoProtocollo> soggettiProtocollo, String verso, String oggetto) throws Exception;

    //Esegue la protocollazione del documento ricevuto in input
    public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo) throws Exception;

    //Utilizzato per costruire un oggetto che rappresenta una segnatura di protocollo
    public Protocollo getProtocolloBean(PraticheEventi praticaEvento) throws Exception;
    
    //Utilizzato da caricamento manuale controller per:
    //1. recuperare il protocollo nella funzione "nuova pratica da numero"
    //2. recuperare gli allegati nella funzione "caricamento manuale" partito da una pratica proveniente da protocollo (tramite scheduler)
    //3. recuperare gli allegati nella funzionae Comunicazioni in ingresso->Scelgo un documento-> Scelgo la pratica di appartenenza-> Visualizzo tutti gli eventi da scegliere
    public DocumentoProtocolloResponse findByProtocollo(Integer annoRiferimento, String numeroProtocollo, Enti ente, LkComuni comune) throws Exception;

    //Utilizzato dallo scheduler di lettura documenti/pratiche da protocollo per recuperare l'elenco dei documenti
    public List<DocumentoProtocolloResponse> queryProtocollo(FilterProtocollo filterProtocollo, Enti ente, LkComuni comune) throws Exception;

    @Deprecated
    public DocumentoProtocolloRequest getDocumentoProtocolloDaXml(Pratica praticaCross, String verso) throws Exception;

    //Utilizzato per sapere che tipologia di record della tabella pratica_protocollo visualizzare sul nella sezione "Istanze in ingresso". Si aggiunge sempre al tipo "PRATICA_MANUALE_CROSS"
    @Nullable
    public String getTipologiaDocumentoPerPratica(Integer idEnte);

    //Utilizzato per sapere che tipologia di record della tabella pratica_protocollo visualizzare sul nella sezione "Comunicazioni in ingresso". Si aggiunge sempre al tipo "DOCUMENTO_MANUALE_CROSS"
    @Nullable
    public String getTipologiaDocumentoArrivo(Integer idEnte);
}
