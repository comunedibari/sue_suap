// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.3, build R1)
// Generated source version: 1.1.3
package it.wego.cross.genova.anagrafe.client.stub;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;

public class Toponomastica_wsGetEsistenzaInterno_ResponseStruct_SOAPSerializer extends ObjectSerializerBase implements Initializable {

    private static final javax.xml.namespace.QName ns1_wsGetEsistenzaInternoReturn_QNAME = new QName("", "wsGetEsistenzaInternoReturn");
    private static final javax.xml.namespace.QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final int myWSGETESISTENZAINTERNORETURN_INDEX = 0;

    public Toponomastica_wsGetEsistenzaInterno_ResponseStruct_SOAPSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
        super(type, encodeType, isNullable, encodingStyle);
    }

    public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer) registry.getSerializer(SOAPConstants.NS_SOAP_ENCODING, java.lang.String.class, ns3_string_TYPE_QNAME);
    }

    public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,
            SOAPDeserializationContext context) throws java.lang.Exception {
        Toponomastica_wsGetEsistenzaInterno_ResponseStruct instance = new Toponomastica_wsGetEsistenzaInterno_ResponseStruct();
        Toponomastica_wsGetEsistenzaInterno_ResponseStruct_SOAPBuilder builder = null;
        java.lang.Object member;
        boolean isComplete = true;
        javax.xml.namespace.QName elementName;

        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsGetEsistenzaInternoReturn_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_wsGetEsistenzaInternoReturn_QNAME, reader, context);
                if (member instanceof SOAPDeserializationState) {
                    if (builder == null) {
                        builder = new Toponomastica_wsGetEsistenzaInterno_ResponseStruct_SOAPBuilder();
                    }
                    state = registerWithMemberState(instance, state, member, myWSGETESISTENZAINTERNORETURN_INDEX, builder);
                    isComplete = false;
                } else {
                    instance.setWsGetEsistenzaInternoReturn((java.lang.String) member);
                }
                reader.nextElementContent();
            } else {
                throw new DeserializationException("soap.unexpectedElementName", new Object[]{ns1_wsGetEsistenzaInternoReturn_QNAME, elementName});
            }
        }

        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (isComplete ? (java.lang.Object) instance : (java.lang.Object) state);
    }

    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        Toponomastica_wsGetEsistenzaInterno_ResponseStruct instance = (Toponomastica_wsGetEsistenzaInterno_ResponseStruct) obj;

    }

    public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {
        Toponomastica_wsGetEsistenzaInterno_ResponseStruct instance = (Toponomastica_wsGetEsistenzaInterno_ResponseStruct) obj;

        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getWsGetEsistenzaInternoReturn(), ns1_wsGetEsistenzaInternoReturn_QNAME, null, writer, context);
    }

    protected void verifyName(XMLReader reader, javax.xml.namespace.QName expectedName) throws java.lang.Exception {
    }
}
