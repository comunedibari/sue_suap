// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3
package it.wego.cross.genova.anagrafe.client.stub;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Toponomastica_wsGetDatiCatastaliDaToponomastici_ResponseStruct_SOAPBuilder implements SOAPInstanceBuilder {

    private Toponomastica_wsGetDatiCatastaliDaToponomastici_ResponseStruct _instance;
    private java.lang.String wsGetDatiCatastaliDaToponomasticiReturn;
    private static final int myWSGETDATICATASTALIDATOPONOMASTICIRETURN_INDEX = 0;

    public Toponomastica_wsGetDatiCatastaliDaToponomastici_ResponseStruct_SOAPBuilder() {
    }

    public void setWsGetDatiCatastaliDaToponomasticiReturn(java.lang.String wsGetDatiCatastaliDaToponomasticiReturn) {
        this.wsGetDatiCatastaliDaToponomasticiReturn = wsGetDatiCatastaliDaToponomasticiReturn;
    }

    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case myWSGETDATICATASTALIDATOPONOMASTICIRETURN_INDEX:
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
                case myWSGETDATICATASTALIDATOPONOMASTICIRETURN_INDEX:
                    _instance.setWsGetDatiCatastaliDaToponomasticiReturn((java.lang.String) memberValue);
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
        _instance = (Toponomastica_wsGetDatiCatastaliDaToponomastici_ResponseStruct) instance;
    }

    public java.lang.Object getInstance() {
        return _instance;
    }
}
