/**
 * PRIGetClassInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

public class PRIGetClassInfoResponse extends PRIOut implements java.io.Serializable {
    private it.asitech.webservice.protocollo.ClassDef classInfo;

    public PRIGetClassInfoResponse() {
        super();
    }

    public PRIGetClassInfoResponse(
           it.asitech.webservice.protocollo.ClassDef classInfo,
           it.asitech.webservice.protocollo.ResponseMessage returnCode) {
           super( returnCode );
           this.classInfo = classInfo;
    }


    /**
     * Gets the classInfo _value for this PRIGetClassInfoResponse.
     * 
     * @return classInfo
     */
    public it.asitech.webservice.protocollo.ClassDef getClassInfo() {
        return classInfo;
    }


    /**
     * Sets the classInfo _value for this PRIGetClassInfoResponse.
     * 
     * @param classInfo
     */
    public void setClassInfo(it.asitech.webservice.protocollo.ClassDef classInfo) {
        this.classInfo = classInfo;
    }


    /**
     * Gets the returnCode _value for this PRIGetClassInfoResponse.
     * 
     * @return returnCode
     */
    public it.asitech.webservice.protocollo.ResponseMessage getReturnCode() {
        return returnCode;
    }


    /**
     * Sets the returnCode _value for this PRIGetClassInfoResponse.
     * 
     * @param returnCode
     */
    public void setReturnCode(it.asitech.webservice.protocollo.ResponseMessage returnCode) {
        this.returnCode = returnCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PRIGetClassInfoResponse)) return false;
        PRIGetClassInfoResponse other = (PRIGetClassInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.classInfo==null && other.getClassInfo()==null) || 
             (this.classInfo!=null &&
              this.classInfo.equals(other.getClassInfo()))) &&
            ((this.returnCode==null && other.getReturnCode()==null) || 
             (this.returnCode!=null &&
              this.returnCode.equals(other.getReturnCode())));
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
        if (getClassInfo() != null) {
            _hashCode += getClassInfo().hashCode();
        }
        if (getReturnCode() != null) {
            _hashCode += getReturnCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PRIGetClassInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetClassInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ClassDef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "returnCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ResponseMessage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
