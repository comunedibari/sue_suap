// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3

package it.wego.cross.client.stub.inseriscifascicolo;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class WSInserimentoFasc_service_ResponseStruct_SOAPBuilder implements SOAPInstanceBuilder {
    private it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_ResponseStruct _instance;
    private java.lang.String serviceReturn;
    private static final int mySERVICERETURN_INDEX = 0;
    
    public WSInserimentoFasc_service_ResponseStruct_SOAPBuilder() {
    }
    
    public void setServiceReturn(java.lang.String serviceReturn) {
        this.serviceReturn = serviceReturn;
    }
    
    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case mySERVICERETURN_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public void construct() {
    }
    
    public void setMember(int index, java.lang.Object memberValue) {
        try {
            switch(index) {
                case mySERVICERETURN_INDEX:
                    _instance.setServiceReturn((java.lang.String)memberValue);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }
        catch (java.lang.RuntimeException e) {
            throw e;
        }
        catch (java.lang.Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }
    
    public void initialize() {
    }
    
    public void setInstance(java.lang.Object instance) {
        _instance = (it.wego.cross.client.stub.inseriscifascicolo.WSInserimentoFasc_service_ResponseStruct)instance;
    }
    
    public java.lang.Object getInstance() {
        return _instance;
    }
}
