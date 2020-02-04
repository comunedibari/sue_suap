
package it.wego.cross.webservices.cxf.cripal;

import it.wego.cross.webservices.cxf.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CripalPraticheService", targetNamespace = "http://www.wego.it/cross", wsdlLocation = "file:/C:/WegoSvn/WegoRepository/Maven/Edilgo/mavenproject1/src/wsdl/cripal-pratiche-service.wsdl")
public class CripalPraticheService_Service
    extends Service
{

    private final static URL CRIPALPRATICHESERVICE_WSDL_LOCATION;
    private final static WebServiceException CRIPALPRATICHESERVICE_EXCEPTION;
    private final static QName CRIPALPRATICHESERVICE_QNAME = new QName("http://www.wego.it/cross", "CripalPraticheService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/WegoSvn/WegoRepository/Maven/Edilgo/mavenproject1/src/wsdl/cripal-pratiche-service.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CRIPALPRATICHESERVICE_WSDL_LOCATION = url;
        CRIPALPRATICHESERVICE_EXCEPTION = e;
    }

    public CripalPraticheService_Service() {
        super(__getWsdlLocation(), CRIPALPRATICHESERVICE_QNAME);
    }

    public CripalPraticheService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), CRIPALPRATICHESERVICE_QNAME, features);
    }

    public CripalPraticheService_Service(URL wsdlLocation) {
        super(wsdlLocation, CRIPALPRATICHESERVICE_QNAME);
    }

    public CripalPraticheService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CRIPALPRATICHESERVICE_QNAME, features);
    }

    public CripalPraticheService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CripalPraticheService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CripalPraticheService
     */
    @WebEndpoint(name = "CripalPraticheService")
    public CripalPraticheService getCripalPraticheService() {
        return super.getPort(new QName("http://www.wego.it/cross", "CripalPraticheService"), CripalPraticheService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CripalPraticheService
     */
    @WebEndpoint(name = "CripalPraticheService")
    public CripalPraticheService getCripalPraticheService(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.wego.it/cross", "CripalPraticheService"), CripalPraticheService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CRIPALPRATICHESERVICE_EXCEPTION!= null) {
            throw CRIPALPRATICHESERVICE_EXCEPTION;
        }
        return CRIPALPRATICHESERVICE_WSDL_LOCATION;
    }

}