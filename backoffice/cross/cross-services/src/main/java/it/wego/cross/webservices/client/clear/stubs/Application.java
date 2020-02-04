
package it.wego.cross.webservices.client.clear.stubs;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Application", targetNamespace = "http://www.simo.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Application {


    /**
     * Parameters:
     * 
     *         ``codice_protocollo`` -- codice_protocollo
     *         ``codice_procedimento_rur`` -- string (may be None)
     *         ``procedimento_non_rur`` -- string (optional)
     *         ``identificazione_pratica`` -- string (optional)
     *         ``responsabile_procedimento`` -- string
     *         ``responsabile_istruttoria`` -- string
     *         ``importo_richiesto`` -- decimal (optional)
     *         ``oggetto`` -- string (may be None)
     *         ``richiedenti`` -- richiedenti
     *         ``localizzazioni`` -- localizzazioni (optional)
     * 
     *         Returns: log
     *         
     * 
     * @param codiceProtocollo
     * @param procedimentoNonRur
     * @param richiedenti
     * @param identificazionePratica
     * @param oggetto
     * @param localizzazioni
     * @param codiceProcedimentoRur
     * @param responsabileIstruttoria
     * @param importoRichiesto
     * @param responsabileProcedimento
     * @return
     *     returns it.wego.cross.webservices.client.clear.stubs.Log
     */
    @WebMethod(operationName = "pratica_simo", action = "pratica_simo")
    @WebResult(name = "pratica_simoResult", targetNamespace = "http://www.simo.org")
    @RequestWrapper(localName = "pratica_simo", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.PraticaSimo")
    @ResponseWrapper(localName = "pratica_simoResponse", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.PraticaSimoResponse")
    public Log praticaSimo(
        @WebParam(name = "codice_protocollo", targetNamespace = "http://www.simo.org")
        CodiceProtocollo codiceProtocollo,
        @WebParam(name = "codice_procedimento_rur", targetNamespace = "http://www.simo.org")
        String codiceProcedimentoRur,
        @WebParam(name = "procedimento_non_rur", targetNamespace = "http://www.simo.org")
        String procedimentoNonRur,
        @WebParam(name = "identificazione_pratica", targetNamespace = "http://www.simo.org")
        String identificazionePratica,
        @WebParam(name = "responsabile_procedimento", targetNamespace = "http://www.simo.org")
        String responsabileProcedimento,
        @WebParam(name = "responsabile_istruttoria", targetNamespace = "http://www.simo.org")
        String responsabileIstruttoria,
        @WebParam(name = "importo_richiesto", targetNamespace = "http://www.simo.org")
        BigDecimal importoRichiesto,
        @WebParam(name = "oggetto", targetNamespace = "http://www.simo.org")
        String oggetto,
        @WebParam(name = "richiedenti", targetNamespace = "http://www.simo.org")
        RichiedenteArray richiedenti,
        @WebParam(name = "localizzazioni", targetNamespace = "http://www.simo.org")
        LocalizzazioneArray localizzazioni);

    /**
     * Parameters:
     * 
     *         ``codice_protocollo`` -- codice_protocollo
     *         ``codice_procedimento_rur`` -- string (may be None)
     *         ``identificazione_pratica`` -- string (optional)
     *         ``responsabile_procedimento`` -- string
     *         ``responsabile_istruttoria`` -- string
     *         ``importo_richiesto`` -- decimal (optional)
     *         ``oggetto`` -- string (may be None)
     *         ``richiedenti`` -- richiedenti
     *         ``localizzazioni`` -- localizzazioni (optional)
     *         ``data_ora_ricevimento`` -- dateTime
     *         ``data_ora_protocollazione`` -- dateTime
     *         ``data_avvio_monitoraggio`` -- date
     *         ``verso`` -- verso
     *         ``codice_protocollo_precedente`` -- codice_protocollo_precedente (optional)
     * 
     *         Returns: log
     *         
     * 
     * @param codiceProtocolloPrecedente
     * @param richiedenti
     * @param codiceProcedimentoRur
     * @param responsabileIstruttoria
     * @param codiceUfficio
     * @param verso
     * @param responsabileProcedimento
     * @param codiceProtocollo
     * @param dataOraProtocollazione
     * @param dataOraRicevimento
     * @param dataAvvioMonitoraggio
     * @param identificazionePratica
     * @param oggetto
     * @param localizzazioni
     * @param importoRichiesto
     * @return
     *     returns it.wego.cross.webservices.client.clear.stubs.Log
     */
    @WebMethod(operationName = "pratica_simo_extended", action = "pratica_simo_extended")
    @WebResult(name = "pratica_simo_extendedResult", targetNamespace = "http://www.simo.org")
    @RequestWrapper(localName = "pratica_simo_extended", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.PraticaSimoExtended")
    @ResponseWrapper(localName = "pratica_simo_extendedResponse", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.PraticaSimoExtendedResponse")
    public Log praticaSimoExtended(
        @WebParam(name = "codice_protocollo", targetNamespace = "http://www.simo.org")
        CodiceProtocollo codiceProtocollo,
        @WebParam(name = "codice_procedimento_rur", targetNamespace = "http://www.simo.org")
        String codiceProcedimentoRur,
        @WebParam(name = "identificazione_pratica", targetNamespace = "http://www.simo.org")
        String identificazionePratica,
        @WebParam(name = "responsabile_procedimento", targetNamespace = "http://www.simo.org")
        String responsabileProcedimento,
        @WebParam(name = "responsabile_istruttoria", targetNamespace = "http://www.simo.org")
        String responsabileIstruttoria,
        @WebParam(name = "importo_richiesto", targetNamespace = "http://www.simo.org")
        BigDecimal importoRichiesto,
        @WebParam(name = "oggetto", targetNamespace = "http://www.simo.org")
        String oggetto,
        @WebParam(name = "richiedenti", targetNamespace = "http://www.simo.org")
        RichiedenteArray richiedenti,
        @WebParam(name = "localizzazioni", targetNamespace = "http://www.simo.org")
        LocalizzazioneArray localizzazioni,
        @WebParam(name = "data_ora_ricevimento", targetNamespace = "http://www.simo.org")
        XMLGregorianCalendar dataOraRicevimento,
        @WebParam(name = "data_ora_protocollazione", targetNamespace = "http://www.simo.org")
        XMLGregorianCalendar dataOraProtocollazione,
        @WebParam(name = "data_avvio_monitoraggio", targetNamespace = "http://www.simo.org")
        XMLGregorianCalendar dataAvvioMonitoraggio,
        @WebParam(name = "verso", targetNamespace = "http://www.simo.org")
        TipiVerso verso,
        @WebParam(name = "codice_ufficio", targetNamespace = "http://www.simo.org")
        String codiceUfficio,
        @WebParam(name = "codice_protocollo_precedente", targetNamespace = "http://www.simo.org")
        CodiceProtocolloPrecedente codiceProtocolloPrecedente);

    /**
     * Parameters:
     * 
     *         ``codice_protocollo`` -- codice_protocollo
     *         ``codice_pratica_simo`` -- codice_pratica_simo
     *         ``codice_evento`` -- int (may be None)
     *         ``altro_evento`` -- altro_evento (optional)
     *         ``annotazioni_evento`` -- string (optional)
     *         ``esito`` -- esito (may be None)
     *         ``annotazioni_esito`` -- string (optional)
     *         ``importo_finanziato`` -- decimal (optional)
     * 
     *         Returns: log
     *         
     * 
     * @param codiceProtocollo
     * @param importoFinanziato
     * @param annotazioniEvento
     * @param codicePraticaSimo
     * @param esito
     * @param codiceEvento
     * @param annotazioniEsito
     * @param altroEvento
     * @return
     *     returns it.wego.cross.webservices.client.clear.stubs.Log
     */
    @WebMethod(operationName = "simo_associa_evento", action = "simo_associa_evento")
    @WebResult(name = "simo_associa_eventoResult", targetNamespace = "http://www.simo.org")
    @RequestWrapper(localName = "simo_associa_evento", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.SimoAssociaEvento")
    @ResponseWrapper(localName = "simo_associa_eventoResponse", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.SimoAssociaEventoResponse")
    public Log simoAssociaEvento(
        @WebParam(name = "codice_protocollo", targetNamespace = "http://www.simo.org")
        CodiceProtocollo codiceProtocollo,
        @WebParam(name = "codice_pratica_simo", targetNamespace = "http://www.simo.org")
        CodicePraticaSimo codicePraticaSimo,
        @WebParam(name = "codice_evento", targetNamespace = "http://www.simo.org")
        BigInteger codiceEvento,
        @WebParam(name = "altro_evento", targetNamespace = "http://www.simo.org")
        AltroEvento altroEvento,
        @WebParam(name = "annotazioni_evento", targetNamespace = "http://www.simo.org")
        String annotazioniEvento,
        @WebParam(name = "esito", targetNamespace = "http://www.simo.org")
        Esito esito,
        @WebParam(name = "annotazioni_esito", targetNamespace = "http://www.simo.org")
        String annotazioniEsito,
        @WebParam(name = "importo_finanziato", targetNamespace = "http://www.simo.org")
        BigDecimal importoFinanziato);

    /**
     * Parameters:
     * 
     *         ``codice_protocollo`` -- codice_protocollo
     *         ``codice_pratica_simo`` -- codice_pratica_simo
     *         ``codice_evento`` -- string (may be None)
     *         ``annotazioni_evento`` -- string (optional)
     *         ``esito`` -- esito (may be None)
     *         ``annotazioni_esito`` -- string (optional)
     *         ``importo_finanziato`` -- decimal (optional)
     *         ``data_ora_ricevimento`` -- dateTime
     *         ``data_ora_protocollazione`` -- dateTime
     *         ``verso`` -- verso
     * 
     *         Returns: log
     *         
     * 
     * @param codiceProtocollo
     * @param dataOraProtocollazione
     * @param importoFinanziato
     * @param annotazioniEvento
     * @param dataOraRicevimento
     * @param codicePraticaSimo
     * @param esito
     * @param codiceEvento
     * @param annotazioniEsito
     * @param verso
     * @return
     *     returns it.wego.cross.webservices.client.clear.stubs.Log
     */
    @WebMethod(operationName = "simo_associa_evento_extended", action = "simo_associa_evento_extended")
    @WebResult(name = "simo_associa_evento_extendedResult", targetNamespace = "http://www.simo.org")
    @RequestWrapper(localName = "simo_associa_evento_extended", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.SimoAssociaEventoExtended")
    @ResponseWrapper(localName = "simo_associa_evento_extendedResponse", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.SimoAssociaEventoExtendedResponse")
    public Log simoAssociaEventoExtended(
        @WebParam(name = "codice_protocollo", targetNamespace = "http://www.simo.org")
        CodiceProtocollo codiceProtocollo,
        @WebParam(name = "codice_pratica_simo", targetNamespace = "http://www.simo.org")
        CodicePraticaSimo codicePraticaSimo,
        @WebParam(name = "codice_evento", targetNamespace = "http://www.simo.org")
        String codiceEvento,
        @WebParam(name = "annotazioni_evento", targetNamespace = "http://www.simo.org")
        String annotazioniEvento,
        @WebParam(name = "esito", targetNamespace = "http://www.simo.org")
        Esito esito,
        @WebParam(name = "annotazioni_esito", targetNamespace = "http://www.simo.org")
        String annotazioniEsito,
        @WebParam(name = "importo_finanziato", targetNamespace = "http://www.simo.org")
        BigDecimal importoFinanziato,
        @WebParam(name = "data_ora_ricevimento", targetNamespace = "http://www.simo.org")
        XMLGregorianCalendar dataOraRicevimento,
        @WebParam(name = "data_ora_protocollazione", targetNamespace = "http://www.simo.org")
        XMLGregorianCalendar dataOraProtocollazione,
        @WebParam(name = "verso", targetNamespace = "http://www.simo.org")
        TipiVerso verso);

    /**
     * Parameters:
     * 
     *         ``codice`` -- string
     *         ``descrizione`` -- string
     *         ``eventi`` -- eventi
     * 
     *         Returns: log
     *         
     * 
     * @param eventi
     * @param descrizione
     * @param codice
     * @return
     *     returns it.wego.cross.webservices.client.clear.stubs.Log
     */
    @WebMethod(operationName = "simo_crea_procedimento", action = "simo_crea_procedimento")
    @WebResult(name = "simo_crea_procedimentoResult", targetNamespace = "http://www.simo.org")
    @RequestWrapper(localName = "simo_crea_procedimento", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.SimoCreaProcedimento")
    @ResponseWrapper(localName = "simo_crea_procedimentoResponse", targetNamespace = "http://www.simo.org", className = "it.wego.cross.webservices.client.clear.stubs.SimoCreaProcedimentoResponse")
    public Log simoCreaProcedimento(
        @WebParam(name = "codice", targetNamespace = "http://www.simo.org")
        String codice,
        @WebParam(name = "descrizione", targetNamespace = "http://www.simo.org")
        String descrizione,
        @WebParam(name = "eventi", targetNamespace = "http://www.simo.org")
        EventoArray eventi);

}