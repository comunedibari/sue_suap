// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3
package it.wego.cross.genova.anagrafe.client.stub;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;

public class Toponomastica_wsGetResidentiDaCodiceFiscale_RequestStruct_SOAPBuilder implements SOAPInstanceBuilder {

    private Toponomastica_wsGetResidentiDaCodiceFiscale_RequestStruct _instance;
    private java.lang.String sXmlFile;
    private static final int mySXMLFILE_INDEX = 0;

    public Toponomastica_wsGetResidentiDaCodiceFiscale_RequestStruct_SOAPBuilder() {
    }

    public void setSXmlFile(java.lang.String sXmlFile) {
        this.sXmlFile = sXmlFile;
    }

    public int memberGateType(int memberIndex) {
        switch (memberIndex) {
            case mySXMLFILE_INDEX:
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
                case mySXMLFILE_INDEX:
                    _instance.setSXmlFile((java.lang.String) memberValue);
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
        _instance = (Toponomastica_wsGetResidentiDaCodiceFiscale_RequestStruct) instance;
    }

    public java.lang.Object getInstance() {
        return _instance;
    }
}
