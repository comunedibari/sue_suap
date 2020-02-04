/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.client.aosta.protocollo;

import it.wego.cross.utils.Log;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 *
 * @author giuseppe
 */
public class GenuitProtocolloClient {

    private final String endpoint;

    public GenuitProtocolloClient(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<DettaglioProtocollo> getDocumentiProtocollo(XMLGregorianCalendar dataInizio, XMLGregorianCalendar dataFine) {
        OtherServices_Service service = new OtherServices_Service();
        OtherServices port = service.getOtherServicesImplPort();
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(600000);
        http.setClient(httpClientPolicy);
        BindingProvider bindingProvider = (BindingProvider) port;
        Log.PLUGIN.debug("**** ENDPOINT RICERCA DOCUMENTI DA PROTOCOLLO: " + endpoint + " *******");
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        List<DettaglioProtocollo> dettagli = new ArrayList<DettaglioProtocollo>();
        DettaglioProtocolloArray result = port.ricercaProtocollo(dataInizio, dataFine);
        if (result != null) {
            dettagli = result.getItem();
        }
        return dettagli;
    }
}
