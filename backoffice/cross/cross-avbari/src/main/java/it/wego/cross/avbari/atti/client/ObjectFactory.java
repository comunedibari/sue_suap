
package it.wego.cross.avbari.atti.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.wego.cross.avbari.atti.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDocumentoDeterminaResponse_QNAME = new QName("http://server.ws.amministrazioneatti.avt.linksmt.it/", "getDocumentoDeterminaResponse");
    private final static QName _GetAllegatiDeterminaResponse_QNAME = new QName("http://server.ws.amministrazioneatti.avt.linksmt.it/", "getAllegatiDeterminaResponse");
    private final static QName _GetDocumentoDetermina_QNAME = new QName("http://server.ws.amministrazioneatti.avt.linksmt.it/", "getDocumentoDetermina");
    private final static QName _GetAllegatiDetermina_QNAME = new QName("http://server.ws.amministrazioneatti.avt.linksmt.it/", "getAllegatiDetermina");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.wego.cross.avbari.atti.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocumentoDeterminaResponse }
     * 
     */
    public DocumentoDeterminaResponse createDocumentoDeterminaResponse() {
        return new DocumentoDeterminaResponse();
    }

    /**
     * Create an instance of {@link AllegatiDeterminaResponse }
     * 
     */
    public AllegatiDeterminaResponse createAllegatiDeterminaResponse() {
        return new AllegatiDeterminaResponse();
    }

    /**
     * Create an instance of {@link GetAllegatiDeterminaResponse }
     * 
     */
    public GetAllegatiDeterminaResponse createGetAllegatiDeterminaResponse() {
        return new GetAllegatiDeterminaResponse();
    }

    /**
     * Create an instance of {@link GetAllegatiDeterminaResponse.Return }
     * 
     */
    public GetAllegatiDeterminaResponse.Return createGetAllegatiDeterminaResponseReturn() {
        return new GetAllegatiDeterminaResponse.Return();
    }

    /**
     * Create an instance of {@link GetDocumentoDeterminaResponse }
     * 
     */
    public GetDocumentoDeterminaResponse createGetDocumentoDeterminaResponse() {
        return new GetDocumentoDeterminaResponse();
    }

    /**
     * Create an instance of {@link GetDocumentoDeterminaResponse.Return }
     * 
     */
    public GetDocumentoDeterminaResponse.Return createGetDocumentoDeterminaResponseReturn() {
        return new GetDocumentoDeterminaResponse.Return();
    }

    /**
     * Create an instance of {@link GetAllegatiDetermina }
     * 
     */
    public GetAllegatiDetermina createGetAllegatiDetermina() {
        return new GetAllegatiDetermina();
    }

    /**
     * Create an instance of {@link GetDocumentoDetermina }
     * 
     */
    public GetDocumentoDetermina createGetDocumentoDetermina() {
        return new GetDocumentoDetermina();
    }

    /**
     * Create an instance of {@link DocumentoDeterminaResponse.Allegato }
     * 
     */
    public DocumentoDeterminaResponse.Allegato createDocumentoDeterminaResponseAllegato() {
        return new DocumentoDeterminaResponse.Allegato();
    }

    /**
     * Create an instance of {@link it.wego.cross.avbari.atti.client.DocumentoDeterminaRequest }
     * 
     */
    public it.wego.cross.avbari.atti.client.DocumentoDeterminaRequest createDocumentoDeterminaRequest() {
        return new it.wego.cross.avbari.atti.client.DocumentoDeterminaRequest();
    }

    /**
     * Create an instance of {@link it.wego.cross.avbari.atti.client.AllegatiDeterminaRequest }
     * 
     */
    public it.wego.cross.avbari.atti.client.AllegatiDeterminaRequest createAllegatiDeterminaRequest() {
        return new it.wego.cross.avbari.atti.client.AllegatiDeterminaRequest();
    }

    /**
     * Create an instance of {@link AllegatiDeterminaResponse.Allegati }
     * 
     */
    public AllegatiDeterminaResponse.Allegati createAllegatiDeterminaResponseAllegati() {
        return new AllegatiDeterminaResponse.Allegati();
    }

    /**
     * Create an instance of {@link GetAllegatiDeterminaResponse.Return.Allegati }
     * 
     */
    public GetAllegatiDeterminaResponse.Return.Allegati createGetAllegatiDeterminaResponseReturnAllegati() {
        return new GetAllegatiDeterminaResponse.Return.Allegati();
    }

    /**
     * Create an instance of {@link GetDocumentoDeterminaResponse.Return.Allegato }
     * 
     */
    public GetDocumentoDeterminaResponse.Return.Allegato createGetDocumentoDeterminaResponseReturnAllegato() {
        return new GetDocumentoDeterminaResponse.Return.Allegato();
    }

    /**
     * Create an instance of {@link GetAllegatiDetermina.AllegatiDeterminaRequest }
     * 
     */
    public GetAllegatiDetermina.AllegatiDeterminaRequest createGetAllegatiDeterminaAllegatiDeterminaRequest() {
        return new GetAllegatiDetermina.AllegatiDeterminaRequest();
    }

    /**
     * Create an instance of {@link GetDocumentoDetermina.DocumentoDeterminaRequest }
     * 
     */
    public GetDocumentoDetermina.DocumentoDeterminaRequest createGetDocumentoDeterminaDocumentoDeterminaRequest() {
        return new GetDocumentoDetermina.DocumentoDeterminaRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentoDeterminaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.amministrazioneatti.avt.linksmt.it/", name = "getDocumentoDeterminaResponse")
    public JAXBElement<GetDocumentoDeterminaResponse> createGetDocumentoDeterminaResponse(GetDocumentoDeterminaResponse value) {
        return new JAXBElement<GetDocumentoDeterminaResponse>(_GetDocumentoDeterminaResponse_QNAME, GetDocumentoDeterminaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllegatiDeterminaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.amministrazioneatti.avt.linksmt.it/", name = "getAllegatiDeterminaResponse")
    public JAXBElement<GetAllegatiDeterminaResponse> createGetAllegatiDeterminaResponse(GetAllegatiDeterminaResponse value) {
        return new JAXBElement<GetAllegatiDeterminaResponse>(_GetAllegatiDeterminaResponse_QNAME, GetAllegatiDeterminaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDocumentoDetermina }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.amministrazioneatti.avt.linksmt.it/", name = "getDocumentoDetermina")
    public JAXBElement<GetDocumentoDetermina> createGetDocumentoDetermina(GetDocumentoDetermina value) {
        return new JAXBElement<GetDocumentoDetermina>(_GetDocumentoDetermina_QNAME, GetDocumentoDetermina.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllegatiDetermina }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.amministrazioneatti.avt.linksmt.it/", name = "getAllegatiDetermina")
    public JAXBElement<GetAllegatiDetermina> createGetAllegatiDetermina(GetAllegatiDetermina value) {
        return new JAXBElement<GetAllegatiDetermina>(_GetAllegatiDetermina_QNAME, GetAllegatiDetermina.class, null, value);
    }

}
