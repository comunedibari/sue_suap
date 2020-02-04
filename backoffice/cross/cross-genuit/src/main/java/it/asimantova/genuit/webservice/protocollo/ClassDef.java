/**
 * ClassDef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asimantova.genuit.webservice.protocollo;

public class ClassDef  implements java.io.Serializable {
    private java.lang.String className;

    private java.lang.String descr;

    private it.asimantova.genuit.webservice.protocollo.FieldDef[] fieldDef;

    private java.lang.String flags;

    private java.lang.String parentClass;

    public ClassDef() {
    }

    public ClassDef(
           java.lang.String className,
           java.lang.String descr,
           it.asimantova.genuit.webservice.protocollo.FieldDef[] fieldDef,
           java.lang.String flags,
           java.lang.String parentClass) {
           this.className = className;
           this.descr = descr;
           this.fieldDef = fieldDef;
           this.flags = flags;
           this.parentClass = parentClass;
    }


    /**
     * Gets the className value for this ClassDef.
     * 
     * @return className
     */
    public java.lang.String getClassName() {
        return className;
    }


    /**
     * Sets the className value for this ClassDef.
     * 
     * @param className
     */
    public void setClassName(java.lang.String className) {
        this.className = className;
    }


    /**
     * Gets the descr value for this ClassDef.
     * 
     * @return descr
     */
    public java.lang.String getDescr() {
        return descr;
    }


    /**
     * Sets the descr value for this ClassDef.
     * 
     * @param descr
     */
    public void setDescr(java.lang.String descr) {
        this.descr = descr;
    }


    /**
     * Gets the fieldDef value for this ClassDef.
     * 
     * @return fieldDef
     */
    public it.asimantova.genuit.webservice.protocollo.FieldDef[] getFieldDef() {
        return fieldDef;
    }


    /**
     * Sets the fieldDef value for this ClassDef.
     * 
     * @param fieldDef
     */
    public void setFieldDef(it.asimantova.genuit.webservice.protocollo.FieldDef[] fieldDef) {
        this.fieldDef = fieldDef;
    }


    /**
     * Gets the flags value for this ClassDef.
     * 
     * @return flags
     */
    public java.lang.String getFlags() {
        return flags;
    }


    /**
     * Sets the flags value for this ClassDef.
     * 
     * @param flags
     */
    public void setFlags(java.lang.String flags) {
        this.flags = flags;
    }


    /**
     * Gets the parentClass value for this ClassDef.
     * 
     * @return parentClass
     */
    public java.lang.String getParentClass() {
        return parentClass;
    }


    /**
     * Sets the parentClass value for this ClassDef.
     * 
     * @param parentClass
     */
    public void setParentClass(java.lang.String parentClass) {
        this.parentClass = parentClass;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClassDef)) return false;
        ClassDef other = (ClassDef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.className==null && other.getClassName()==null) || 
             (this.className!=null &&
              this.className.equals(other.getClassName()))) &&
            ((this.descr==null && other.getDescr()==null) || 
             (this.descr!=null &&
              this.descr.equals(other.getDescr()))) &&
            ((this.fieldDef==null && other.getFieldDef()==null) || 
             (this.fieldDef!=null &&
              java.util.Arrays.equals(this.fieldDef, other.getFieldDef()))) &&
            ((this.flags==null && other.getFlags()==null) || 
             (this.flags!=null &&
              this.flags.equals(other.getFlags()))) &&
            ((this.parentClass==null && other.getParentClass()==null) || 
             (this.parentClass!=null &&
              this.parentClass.equals(other.getParentClass())));
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
        if (getClassName() != null) {
            _hashCode += getClassName().hashCode();
        }
        if (getDescr() != null) {
            _hashCode += getDescr().hashCode();
        }
        if (getFieldDef() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFieldDef());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFieldDef(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFlags() != null) {
            _hashCode += getFlags().hashCode();
        }
        if (getParentClass() != null) {
            _hashCode += getParentClass().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClassDef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ClassDef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("className");
        elemField.setXmlName(new javax.xml.namespace.QName("", "className"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldDef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldDef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "FieldDef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flags");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flags"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentClass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
