/**
 * Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.extractmulti;

public class Service  implements java.io.Serializable {
    private java.lang.String codApplicazione;

    private java.lang.String istanzaApplicazione;

    private java.lang.String userName;

    private java.lang.String password;

    private java.lang.String xml;

    private java.lang.String hash;

    public Service() {
    }

    public Service(
           java.lang.String codApplicazione,
           java.lang.String istanzaApplicazione,
           java.lang.String userName,
           java.lang.String password,
           java.lang.String xml,
           java.lang.String hash) {
           this.codApplicazione = codApplicazione;
           this.istanzaApplicazione = istanzaApplicazione;
           this.userName = userName;
           this.password = password;
           this.xml = xml;
           this.hash = hash;
    }


    /**
     * Gets the codApplicazione value for this Service.
     * 
     * @return codApplicazione
     */
    public java.lang.String getCodApplicazione() {
        return codApplicazione;
    }


    /**
     * Sets the codApplicazione value for this Service.
     * 
     * @param codApplicazione
     */
    public void setCodApplicazione(java.lang.String codApplicazione) {
        this.codApplicazione = codApplicazione;
    }


    /**
     * Gets the istanzaApplicazione value for this Service.
     * 
     * @return istanzaApplicazione
     */
    public java.lang.String getIstanzaApplicazione() {
        return istanzaApplicazione;
    }


    /**
     * Sets the istanzaApplicazione value for this Service.
     * 
     * @param istanzaApplicazione
     */
    public void setIstanzaApplicazione(java.lang.String istanzaApplicazione) {
        this.istanzaApplicazione = istanzaApplicazione;
    }


    /**
     * Gets the userName value for this Service.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this Service.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the password value for this Service.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Service.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the xml value for this Service.
     * 
     * @return xml
     */
    public java.lang.String getXml() {
        return xml;
    }


    /**
     * Sets the xml value for this Service.
     * 
     * @param xml
     */
    public void setXml(java.lang.String xml) {
        this.xml = xml;
    }


    /**
     * Gets the hash value for this Service.
     * 
     * @return hash
     */
    public java.lang.String getHash() {
        return hash;
    }


    /**
     * Sets the hash value for this Service.
     * 
     * @param hash
     */
    public void setHash(java.lang.String hash) {
        this.hash = hash;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Service)) return false;
        Service other = (Service) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codApplicazione==null && other.getCodApplicazione()==null) || 
             (this.codApplicazione!=null &&
              this.codApplicazione.equals(other.getCodApplicazione()))) &&
            ((this.istanzaApplicazione==null && other.getIstanzaApplicazione()==null) || 
             (this.istanzaApplicazione!=null &&
              this.istanzaApplicazione.equals(other.getIstanzaApplicazione()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.xml==null && other.getXml()==null) || 
             (this.xml!=null &&
              this.xml.equals(other.getXml()))) &&
            ((this.hash==null && other.getHash()==null) || 
             (this.hash!=null &&
              this.hash.equals(other.getHash())));
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
        if (getCodApplicazione() != null) {
            _hashCode += getCodApplicazione().hashCode();
        }
        if (getIstanzaApplicazione() != null) {
            _hashCode += getIstanzaApplicazione().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getXml() != null) {
            _hashCode += getXml().hashCode();
        }
        if (getHash() != null) {
            _hashCode += getHash().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Service.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://extractmulti.webservices.repository2.auriga.eng.it", ">service"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codApplicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codApplicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("istanzaApplicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "istanzaApplicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xml");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hash");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hash"));
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
