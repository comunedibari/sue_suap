/**
 * PriObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;


public class PriObject  implements java.io.Serializable {
    private static final long serialVersionUID = -7939094963968345464L;

    private java.lang.String className;

    private it.asitech.webservice.protocollo.Field[] fields;

    private java.lang.Integer id;

    public PriObject() {
        this(null, new Field[0], 0);
    }

    public PriObject(
           java.lang.String className,
           it.asitech.webservice.protocollo.Field[] fields,
           java.lang.Integer id)
    {
           this.className = className;
           this.fields = fields;
           this.id = id;
    }


    /**
     * Gets the className _value for this PriObject.
     *
     * @return className
     */
    public java.lang.String getClassName() {
        return this.className;
    }


    /**
     * Sets the className _value for this PriObject.
     *
     * @param className
     */
    public void setClassName(java.lang.String className) {
        this.className = className;
    }


    /**
     * Gets the fields _value for this PriObject.
     *
     * @return fields
     */
    public it.asitech.webservice.protocollo.Field[] getFields() {
        return this.fields;
    }


    /**
     * Sets the fields _value for this PriObject.
     *
     * @param fields
     */
    public void setFields(it.asitech.webservice.protocollo.Field[] fields) {
        this.fields = fields;
    }


    /**
     * Gets the id _value for this PriObject.
     *
     * @return id
     */
    public java.lang.Integer getId() {
        return this.id;
    }


    /**
     * Sets the id _value for this PriObject.
     *
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PriObject)) return false;
        PriObject other = (PriObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (this.__equalsCalc != null) {
            return (this.__equalsCalc == obj);
        }
        this.__equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.className==null && other.getClassName()==null) ||
             (this.className!=null &&
              this.className.equals(other.getClassName()))) &&
            ((this.fields==null && other.getFields()==null) ||
             (this.fields!=null &&
              java.util.Arrays.equals(this.fields, other.getFields()))) &&
            ((this.id==null && other.getId()==null) ||
             (this.id!=null &&
              this.id.equals(other.getId())));
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
        if (getClassName() != null) {
            _hashCode += getClassName().hashCode();
        }
        if (getFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PriObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("className");
        elemField.setXmlName(new javax.xml.namespace.QName("", "className"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "Field"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "{" )
          .append( this.getClass().getName() )
          .append( " id=" ).append( this.id )
          .append( " className=" ).append( this.className )
          .append( " fields=[" );

        int i=0;
        for( Field f : this.getFields() ) {
            if( i++ > 0 ) {
                sb.append( ", " );
            }
            sb.append( f.toString() );
        }
        sb.append( "]}" );

        return sb.toString();
    }
}
