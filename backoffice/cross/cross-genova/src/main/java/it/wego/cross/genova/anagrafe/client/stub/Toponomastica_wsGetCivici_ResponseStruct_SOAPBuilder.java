// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3
package it.wego.cross.genova.anagrafe.client.stub;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Toponomastica_wsGetCivici_ResponseStruct_SOAPBuilder implements SOAPInstanceBuilder {

    private Toponomastica_wsGetCivici_ResponseStruct _instance;
    private java.lang.String wsGetCiviciReturn;
    private static final int myWSGETCIVICIRETURN_INDEX = 0;

    public Toponomastica_wsGetCivici_ResponseStruct_SOAPBuilder() {
    }

    public void setWsGetCiviciReturn(java.lang.String wsGetCiviciReturn) {
        this.wsGetCiviciReturn = wsGetCiviciReturn;
    }

    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myWSGETCIVICIRETURN_INDEX:
                return GATES_INITIALIZATION | REQUIRES_CREATION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void construct() {
    }

    public void setMember(int index, java.lang.Object memberValue) {
        try {
            switch (index) {
                case myWSGETCIVICIRETURN_INDEX:
                    _instance.setWsGetCiviciReturn((java.lang.String) memberValue);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        } catch (java.lang.RuntimeException e) {
            throw e;
        } catch (java.lang.Exception e) {
            throw new DeserializationException(new LocalizableExceptionAdapter(e));
        }
    }

    public void initialize() {
    }

    public void setInstance(java.lang.Object instance) {
        _instance = (Toponomastica_wsGetCivici_ResponseStruct) instance;
    }

    public java.lang.Object getInstance() {
        return _instance;
    }
}
