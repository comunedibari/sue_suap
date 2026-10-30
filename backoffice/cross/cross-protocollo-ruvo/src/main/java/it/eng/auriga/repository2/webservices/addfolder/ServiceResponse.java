/**
 * ServiceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.addfolder;

public class ServiceResponse  implements java.io.Serializable {
    private java.lang.String serviceReturn;

    public ServiceResponse() {
    }

    public ServiceResponse(
           java.lang.String serviceReturn) {
           this.serviceReturn = serviceReturn;
    }


    /**
     * Gets the serviceReturn value for this ServiceResponse.
     * 
     * @return serviceReturn
     */
    public java.lang.String getServiceReturn() {
        return serviceReturn;
    }


    /**
     * Sets the serviceReturn value for this ServiceResponse.
     * 
     * @param serviceReturn
     */
    public void setServiceReturn(java.lang.String serviceReturn) {
        this.serviceReturn = serviceReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceResponse)) return false;
        ServiceResponse other = (ServiceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceReturn==null && other.getServiceReturn()==null) || 
             (this.serviceReturn!=null &&
              this.serviceReturn.equals(other.getServiceReturn())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getServiceReturn() != null) {
            _hashCode += getServiceReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://addfolder.webservices.repository2.auriga.eng.it", ">serviceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
