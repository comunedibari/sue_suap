// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3
package it.wego.cross.client.stub.inseriscifascicolo;

import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.soap.streaming.*;
import com.sun.xml.rpc.soap.message.*;
import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import com.sun.xml.rpc.client.SenderException;
import com.sun.xml.rpc.client.*;
import com.sun.xml.rpc.client.http.*;
import it.wego.cross.client.transport.EGrammataTransport;
import javax.xml.rpc.handler.*;
import javax.xml.rpc.JAXRPCException;

public class WSInserimentoFasc_Stub
        extends com.sun.xml.rpc.client.StubBase
        implements it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc {

    private Integer TIMEOUT = 60000;

    /*
     *  public constructor
     */
    public WSInserimentoFasc_Stub(HandlerChain handlerChain, Integer timeout) {
        super(handlerChain);
        if (timeout != null && timeout > 0) {
            this.TIMEOUT = timeout;
        }
        _setProperty(ENDPOINT_ADDRESS_PROPERTY, "http://protocollotest.comune.genova.it/axis/services/WSInserimentoFasc");
    }

    /*
     *  implementation of service
     */
    public java.lang.String service(java.lang.String codEnte, java.lang.String userid, java.lang.String password, java.lang.String indirizzoIP, java.lang.String xml, java.lang.String hash)
            throws java.rmi.RemoteException {

        try {

            StreamingSenderState _state = _start(_handlerChain);

            InternalSOAPMessage _request = _state.getRequest();
            _request.setOperationCode(service_OPCODE);
            it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_RequestStruct _myWSInserimentoFasc_service_RequestStruct
                    = new it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_RequestStruct();

            _myWSInserimentoFasc_service_RequestStruct.setCodEnte(codEnte);
            _myWSInserimentoFasc_service_RequestStruct.setUserid(userid);
            _myWSInserimentoFasc_service_RequestStruct.setPassword(password);
            _myWSInserimentoFasc_service_RequestStruct.setIndirizzoIP(indirizzoIP);
            _myWSInserimentoFasc_service_RequestStruct.setXml(xml);
            _myWSInserimentoFasc_service_RequestStruct.setHash(hash);

            SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(ns1_service_service_QNAME);
            _bodyBlock.setValue(_myWSInserimentoFasc_service_RequestStruct);
            _bodyBlock.setSerializer(ns1_myWSInserimentoFasc_service_RequestStruct_SOAPSerializer);
            _request.setBody(_bodyBlock);

            _state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, "");

            _send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);

            it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_ResponseStruct _myWSInserimentoFasc_service_ResponseStruct = null;
            Object _responseObj = _state.getResponse().getBody().getValue();
            if (_responseObj instanceof SOAPDeserializationState) {
                _myWSInserimentoFasc_service_ResponseStruct
                        = (it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_ResponseStruct) ((SOAPDeserializationState) _responseObj).getInstance();
            } else {
                _myWSInserimentoFasc_service_ResponseStruct
                        = (it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_ResponseStruct) _responseObj;
            }

            return _myWSInserimentoFasc_service_ResponseStruct.getServiceReturn();
        } catch (RemoteException e) {
            // let this one through unchanged
            throw e;
        } catch (JAXRPCException e) {
            throw new RemoteException(e.getMessage(), e);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RemoteException(e.getMessage(), e);
            }
        }
    }

    /*
     *  this method deserializes the request/response structure in the body
     */
    protected void _readFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        int opcode = state.getRequest().getOperationCode();
        switch (opcode) {
            case service_OPCODE:
                _deserialize_service(bodyReader, deserializationContext, state);
                break;
            default:
                throw new SenderException("sender.response.unrecognizedOperation", java.lang.Integer.toString(opcode));
        }
    }

    /*
     * This method deserializes the body of the service operation.
     */
    private void _deserialize_service(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
        java.lang.Object myWSInserimentoFasc_service_ResponseStructObj
                = ns2_myWSInserimentoFasc_service_ResponseStruct_SOAPSerializer.deserialize(ns2_service_serviceResponse_QNAME,
                        bodyReader, deserializationContext);

        SOAPBlockInfo bodyBlock = new SOAPBlockInfo(ns2_service_serviceResponse_QNAME);
        bodyBlock.setValue(myWSInserimentoFasc_service_ResponseStructObj);
        state.getResponse().setBody(bodyBlock);
    }

    public java.lang.String _getDefaultEnvelopeEncodingStyle() {
        return SOAPNamespaceConstants.ENCODING;
    }

    public java.lang.String _getImplicitEnvelopeEncodingStyle() {
        return "";
    }

    public java.lang.String _getEncodingStyle() {
        return SOAPNamespaceConstants.ENCODING;
    }

    public void _setEncodingStyle(java.lang.String encodingStyle) {
        throw new UnsupportedOperationException("cannot set encoding style");
    }

    /*
     * This method returns an array containing (prefix, nsURI) pairs.
     */
    protected java.lang.String[] _getNamespaceDeclarations() {
        return myNamespace_declarations;
    }

    /*
     * This method returns an array containing the names of the headers we understand.
     */
    public javax.xml.namespace.QName[] _getUnderstoodHeaders() {
        return understoodHeaderNames;
    }

    public void _initialize(InternalTypeMappingRegistry registry) throws Exception {
        super._initialize(registry);
        ns2_myWSInserimentoFasc_service_ResponseStruct_SOAPSerializer = (CombinedSerializer) registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_ResponseStruct.class, ns2_serviceResponse_TYPE_QNAME);
        ns1_myWSInserimentoFasc_service_RequestStruct_SOAPSerializer = (CombinedSerializer) registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_RequestStruct.class, ns1_service_TYPE_QNAME);
    }

    private static final javax.xml.namespace.QName _portName = new QName("http://protocollotest.comune.genova.it/axis/services/WSInserimentoFasc", "WSInserimentoFasc");
    private static final int service_OPCODE = 0;
    private static final javax.xml.namespace.QName ns1_service_service_QNAME = new QName("http://protocollo.webservices.eng", "service");
    private static final javax.xml.namespace.QName ns1_service_TYPE_QNAME = new QName("http://protocollo.webservices.eng", "service");
    private CombinedSerializer ns1_myWSInserimentoFasc_service_RequestStruct_SOAPSerializer;
    private static final javax.xml.namespace.QName ns2_service_serviceResponse_QNAME = new QName("http://protocollotest.comune.genova.it/axis/services/WSInserimentoFasc", "serviceResponse");
    private static final javax.xml.namespace.QName ns2_serviceResponse_TYPE_QNAME = new QName("http://protocollotest.comune.genova.it/axis/services/WSInserimentoFasc", "serviceResponse");
    private CombinedSerializer ns2_myWSInserimentoFasc_service_ResponseStruct_SOAPSerializer;
    private static final java.lang.String[] myNamespace_declarations
            = new java.lang.String[]{
                "ns0", "http://protocollo.webservices.eng",
                "ns1", "http://protocollotest.comune.genova.it/axis/services/WSInserimentoFasc"
            };

    private static final QName[] understoodHeaderNames = new QName[]{};
    
    @Override
    protected ClientTransport _getTransport() {
        EGrammataTransport transport = new EGrammataTransport(TIMEOUT);
        return transport;
    }
}