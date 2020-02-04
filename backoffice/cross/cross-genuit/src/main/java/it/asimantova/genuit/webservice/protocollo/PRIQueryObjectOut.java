/**
 * PRIQueryObjectOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asimantova.genuit.webservice.protocollo;

public class PRIQueryObjectOut  implements java.io.Serializable {
    private it.asimantova.genuit.webservice.protocollo.PriObject[] priObj;

    private int pageCount;

    private int recordCount;

    private int returnPpage;

    private it.asimantova.genuit.webservice.protocollo.ResponseMessage returnCode;

    public PRIQueryObjectOut() {
    }

    public PRIQueryObjectOut(
           it.asimantova.genuit.webservice.protocollo.PriObject[] priObj,
           int pageCount,
           int recordCount,
           int returnPpage,
           it.asimantova.genuit.webservice.protocollo.ResponseMessage returnCode) {
           this.priObj = priObj;
           this.pageCount = pageCount;
           this.recordCount = recordCount;
           this.returnPpage = returnPpage;
           this.returnCode = returnCode;
    }


    /**
     * Gets the priObj value for this PRIQueryObjectOut.
     * 
     * @return priObj
     */
    public it.asimantova.genuit.webservice.protocollo.PriObject[] getPriObj() {
        return priObj;
    }


    /**
     * Sets the priObj value for this PRIQueryObjectOut.
     * 
     * @param priObj
     */
    public void setPriObj(it.asimantova.genuit.webservice.protocollo.PriObject[] priObj) {
        this.priObj = priObj;
    }


    /**
     * Gets the pageCount value for this PRIQueryObjectOut.
     * 
     * @return pageCount
     */
    public int getPageCount() {
        return pageCount;
    }


    /**
     * Sets the pageCount value for this PRIQueryObjectOut.
     * 
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    /**
     * Gets the recordCount value for this PRIQueryObjectOut.
     * 
     * @return recordCount
     */
    public int getRecordCount() {
        return recordCount;
    }


    /**
     * Sets the recordCount value for this PRIQueryObjectOut.
     * 
     * @param recordCount
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }


    /**
     * Gets the returnPpage value for this PRIQueryObjectOut.
     * 
     * @return returnPpage
     */
    public int getReturnPpage() {
        return returnPpage;
    }


    /**
     * Sets the returnPpage value for this PRIQueryObjectOut.
     * 
     * @param returnPpage
     */
    public void setReturnPpage(int returnPpage) {
        this.returnPpage = returnPpage;
    }


    /**
     * Gets the returnCode value for this PRIQueryObjectOut.
     * 
     * @return returnCode
     */
    public it.asimantova.genuit.webservice.protocollo.ResponseMessage getReturnCode() {
        return returnCode;
    }


    /**
     * Sets the returnCode value for this PRIQueryObjectOut.
     * 
     * @param returnCode
     */
    public void setReturnCode(it.asimantova.genuit.webservice.protocollo.ResponseMessage returnCode) {
        this.returnCode = returnCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PRIQueryObjectOut)) return false;
        PRIQueryObjectOut other = (PRIQueryObjectOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.priObj==null && other.getPriObj()==null) || 
             (this.priObj!=null &&
              java.util.Arrays.equals(this.priObj, other.getPriObj()))) &&
            this.pageCount == other.getPageCount() &&
            this.recordCount == other.getRecordCount() &&
            this.returnPpage == other.getReturnPpage() &&
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
        if (getPriObj() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPriObj());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPriObj(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getPageCount();
        _hashCode += getRecordCount();
        _hashCode += getReturnPpage();
        if (getReturnCode() != null) {
            _hashCode += getReturnCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PRIQueryObjectOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIQueryObjectOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priObj");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priObj"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recordCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnPpage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "returnPpage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("returnCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "returnCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ResponseMessage"));
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
