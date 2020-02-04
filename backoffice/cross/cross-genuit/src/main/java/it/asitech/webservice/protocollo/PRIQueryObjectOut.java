/**
 * PRIQueryObjectOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;


public class PRIQueryObjectOut extends PRIOut implements java.io.Serializable {
    /* righe ritornate */
    private it.asitech.webservice.protocollo.PriObject[] priObj;

    /* numero pagine trovate */
    private int pageCount;

    /* numero righe trovate */
    private int recordCount;

    /* numero della pagina di cui sono
     *                              ritornate le righe */
    private int returnPpage;


    public PRIQueryObjectOut() {
        super();
    }
    public PRIQueryObjectOut(
            it.asitech.webservice.protocollo.PriObject[] priObj,
            int recordCount,
            int returnPpage,
            it.asitech.webservice.protocollo.ResponseMessage returnCode) {
        this( priObj, (int)Math.round( (double)recordCount / 50), recordCount, returnPpage, returnCode);
     }

    public PRIQueryObjectOut(
           it.asitech.webservice.protocollo.PriObject[] priObj,
           int pageCount,
           int recordCount,
           int returnPpage,
           it.asitech.webservice.protocollo.ResponseMessage returnCode) {
           this.priObj = priObj;
           this.pageCount = pageCount;
           this.recordCount = recordCount;
           this.returnPpage = returnPpage;
    }

    /**
     * Gets the priObj _value for this PRIQueryObjectOut.
     *
     * @return priObj   * righe ritornate
     */
    public it.asitech.webservice.protocollo.PriObject[] getPriObj() {
        return this.priObj;
    }


    /**
     * Sets the priObj _value for this PRIQueryObjectOut.
     *
     * @param priObj   * righe ritornate
     */
    public void setPriObj(it.asitech.webservice.protocollo.PriObject[] priObj) {
        this.priObj = priObj;
    }


    /**
     * Gets the pageCount _value for this PRIQueryObjectOut.
     *
     * @return pageCount   * numero pagine trovate
     */
    public int getPageCount() {
        return this.pageCount;
    }


    /**
     * Sets the pageCount _value for this PRIQueryObjectOut.
     *
     * @param pageCount   * numero pagine trovate
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    /**
     * Gets the recordCount _value for this PRIQueryObjectOut.
     *
     * @return recordCount   * numero righe trovate
     */
    public int getRecordCount() {
        return this.recordCount;
    }


    /**
     * Sets the recordCount _value for this PRIQueryObjectOut.
     *
     * @param recordCount   * numero righe trovate
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }


    /**
     * Gets the returnPpage _value for this PRIQueryObjectOut.
     *
     * @return returnPpage   * numero della pagina di cui sono
     *                              ritornate le righe
     */
    public int getReturnPpage() {
        return this.returnPpage;
    }


    /**
     * Sets the returnPpage _value for this PRIQueryObjectOut.
     *
     * @param returnPpage   * numero della pagina di cui sono
     *                              ritornate le righe
     */
    public void setReturnPpage(int returnPpage) {
        this.returnPpage = returnPpage;
    }


    private java.lang.Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PRIQueryObjectOut)) return false;
        PRIQueryObjectOut other = (PRIQueryObjectOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (this.__equalsCalc != null) {
            return (this.__equalsCalc == obj);
        }
        this.__equalsCalc = obj;
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
        this.__equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
    public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
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
        this.__hashCodeCalc = false;
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
