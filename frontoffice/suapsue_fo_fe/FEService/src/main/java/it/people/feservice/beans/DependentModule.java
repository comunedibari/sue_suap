/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.feservice.beans;

public class DependentModule  implements java.io.Serializable {
    private java.lang.String valore;
    private int attachXmlInMail;
    private java.lang.String name;
    private java.lang.String mailAddress;

    public DependentModule() {
    }

    public DependentModule(
           java.lang.String valore,
           int attachXmlInMail,
           java.lang.String name,
           java.lang.String mailAddress) {
           this.valore = valore;
           this.attachXmlInMail = attachXmlInMail;
           this.name = name;
           this.mailAddress = mailAddress;
    }


    /**
     * Gets the valore value for this DependentModule.
     * 
     * @return valore
     */
    public java.lang.String getValore() {
        return valore;
    }


    /**
     * Sets the valore value for this DependentModule.
     * 
     * @param valore
     */
    public void setValore(java.lang.String valore) {
        this.valore = valore;
    }


    /**
     * Gets the attachXmlInMail value for this DependentModule.
     * 
     * @return attachXmlInMail
     */
    public int getAttachXmlInMail() {
        return attachXmlInMail;
    }


    /**
     * Sets the attachXmlInMail value for this DependentModule.
     * 
     * @param attachXmlInMail
     */
    public void setAttachXmlInMail(int attachXmlInMail) {
        this.attachXmlInMail = attachXmlInMail;
    }


    /**
     * Gets the name value for this DependentModule.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DependentModule.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the mailAddress value for this DependentModule.
     * 
     * @return mailAddress
     */
    public java.lang.String getMailAddress() {
        return mailAddress;
    }


    /**
     * Sets the mailAddress value for this DependentModule.
     * 
     * @param mailAddress
     */
    public void setMailAddress(java.lang.String mailAddress) {
        this.mailAddress = mailAddress;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DependentModule)) return false;
        DependentModule other = (DependentModule) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.valore==null && other.getValore()==null) || 
             (this.valore!=null &&
              this.valore.equals(other.getValore()))) &&
            this.attachXmlInMail == other.getAttachXmlInMail() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.mailAddress==null && other.getMailAddress()==null) || 
             (this.mailAddress!=null &&
              this.mailAddress.equals(other.getMailAddress())));
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
        if (getValore() != null) {
            _hashCode += getValore().hashCode();
        }
        _hashCode += getAttachXmlInMail();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getMailAddress() != null) {
            _hashCode += getMailAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DependentModule.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "DependentModule"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "valore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachXmlInMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "attachXmlInMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "mailAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
