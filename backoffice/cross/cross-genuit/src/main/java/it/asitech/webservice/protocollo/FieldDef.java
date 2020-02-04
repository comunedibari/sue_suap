/**
 * FieldDef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

public class FieldDef  implements java.io.Serializable {
    private java.lang.String descr;

    private java.lang.String fieldType;

    private java.lang.String flags;

    private java.lang.Integer length;

    private java.lang.Integer maxOccurs;

    private java.lang.Integer minOccurs;

    private java.lang.String name;

    private java.lang.Integer precision;

    private java.lang.Integer scale;

    private java.lang.String subType;

    private java.lang.String type;

    public FieldDef() {
    }

    public FieldDef(
           java.lang.String descr,
           java.lang.String fieldType,
           java.lang.String flags,
           java.lang.Integer length,
           java.lang.Integer maxOccurs,
           java.lang.Integer minOccurs,
           java.lang.String name,
           java.lang.Integer precision,
           java.lang.Integer scale,
           java.lang.String subType,
           java.lang.String type) {
           this.descr = descr;
           this.fieldType = fieldType;
           this.flags = flags;
           this.length = length;
           this.maxOccurs = maxOccurs;
           this.minOccurs = minOccurs;
           this.name = name;
           this.precision = precision;
           this.scale = scale;
           this.subType = subType;
           this.type = type;
    }


    /**
     * Gets the descr _value for this FieldDef.
     * 
     * @return descr
     */
    public java.lang.String getDescr() {
        return descr;
    }


    /**
     * Sets the descr _value for this FieldDef.
     * 
     * @param descr
     */
    public void setDescr(java.lang.String descr) {
        this.descr = descr;
    }


    /**
     * Gets the fieldType _value for this FieldDef.
     * 
     * @return fieldType
     */
    public java.lang.String getFieldType() {
        return fieldType;
    }


    /**
     * Sets the fieldType _value for this FieldDef.
     * 
     * @param fieldType
     */
    public void setFieldType(java.lang.String fieldType) {
        this.fieldType = fieldType;
    }


    /**
     * Gets the flags _value for this FieldDef.
     * 
     * @return flags
     */
    public java.lang.String getFlasg() {
        return flags;
    }


    /**
     * Sets the flags _value for this FieldDef.
     * 
     * @param flags
     */
    public void setFlasg(java.lang.String flasg) {
        this.flags = flasg;
    }


    /**
     * Gets the length _value for this FieldDef.
     * 
     * @return length
     */
    public java.lang.Integer getLength() {
        return length;
    }


    /**
     * Sets the length _value for this FieldDef.
     * 
     * @param length
     */
    public void setLength(java.lang.Integer length) {
        this.length = length;
    }


    /**
     * Gets the maxOccurs _value for this FieldDef.
     * 
     * @return maxOccurs
     */
    public java.lang.Integer getMaxOccurs() {
        return maxOccurs;
    }


    /**
     * Sets the maxOccurs _value for this FieldDef.
     * 
     * @param maxOccurs
     */
    public void setMaxOccurs(java.lang.Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }


    /**
     * Gets the minOccurs _value for this FieldDef.
     * 
     * @return minOccurs
     */
    public java.lang.Integer getMinOccurs() {
        return minOccurs;
    }


    /**
     * Sets the minOccurs _value for this FieldDef.
     * 
     * @param minOccurs
     */
    public void setMinOccurs(java.lang.Integer minOccurs) {
        this.minOccurs = minOccurs;
    }


    /**
     * Gets the name _value for this FieldDef.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name _value for this FieldDef.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the precision _value for this FieldDef.
     * 
     * @return precision
     */
    public java.lang.Integer getPrecision() {
        return precision;
    }


    /**
     * Sets the precision _value for this FieldDef.
     * 
     * @param precision
     */
    public void setPrecision(java.lang.Integer precision) {
        this.precision = precision;
    }


    /**
     * Gets the scale _value for this FieldDef.
     * 
     * @return scale
     */
    public java.lang.Integer getScale() {
        return scale;
    }


    /**
     * Sets the scale _value for this FieldDef.
     * 
     * @param scale
     */
    public void setScale(java.lang.Integer scale) {
        this.scale = scale;
    }


    /**
     * Gets the subType _value for this FieldDef.
     * 
     * @return subType
     */
    public java.lang.String getSubType() {
        return subType;
    }


    /**
     * Sets the subType _value for this FieldDef.
     * 
     * @param subType
     */
    public void setSubType(java.lang.String subType) {
        this.subType = subType;
    }


    /**
     * Gets the type _value for this FieldDef.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type _value for this FieldDef.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FieldDef)) return false;
        FieldDef other = (FieldDef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descr==null && other.getDescr()==null) || 
             (this.descr!=null &&
              this.descr.equals(other.getDescr()))) &&
            ((this.fieldType==null && other.getFieldType()==null) || 
             (this.fieldType!=null &&
              this.fieldType.equals(other.getFieldType()))) &&
            ((this.flags==null && other.getFlasg()==null) || 
             (this.flags!=null &&
              this.flags.equals(other.getFlasg()))) &&
            ((this.length==null && other.getLength()==null) || 
             (this.length!=null &&
              this.length.equals(other.getLength()))) &&
            ((this.maxOccurs==null && other.getMaxOccurs()==null) || 
             (this.maxOccurs!=null &&
              this.maxOccurs.equals(other.getMaxOccurs()))) &&
            ((this.minOccurs==null && other.getMinOccurs()==null) || 
             (this.minOccurs!=null &&
              this.minOccurs.equals(other.getMinOccurs()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.precision==null && other.getPrecision()==null) || 
             (this.precision!=null &&
              this.precision.equals(other.getPrecision()))) &&
            ((this.scale==null && other.getScale()==null) || 
             (this.scale!=null &&
              this.scale.equals(other.getScale()))) &&
            ((this.subType==null && other.getSubType()==null) || 
             (this.subType!=null &&
              this.subType.equals(other.getSubType()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getDescr() != null) {
            _hashCode += getDescr().hashCode();
        }
        if (getFieldType() != null) {
            _hashCode += getFieldType().hashCode();
        }
        if (getFlasg() != null) {
            _hashCode += getFlasg().hashCode();
        }
        if (getLength() != null) {
            _hashCode += getLength().hashCode();
        }
        if (getMaxOccurs() != null) {
            _hashCode += getMaxOccurs().hashCode();
        }
        if (getMinOccurs() != null) {
            _hashCode += getMinOccurs().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPrecision() != null) {
            _hashCode += getPrecision().hashCode();
        }
        if (getScale() != null) {
            _hashCode += getScale().hashCode();
        }
        if (getSubType() != null) {
            _hashCode += getSubType().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FieldDef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "FieldDef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("", "length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxOccurs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxOccurs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minOccurs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minOccurs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("precision");
        elemField.setXmlName(new javax.xml.namespace.QName("", "precision"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
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

    private String qt(String vl){
        return "\""+vl+"\"";
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "{" )
          .append( this.getClass().getName() )
          .append( "> name=" ).append( qt(name) )
          .append( " descr=" ).append( qt(descr) )
          .append( " flags=[" ).append( flags ).append( "]" )
          .append( " length=" ).append( length )
          .append( " precision=" ).append( precision )
          .append( " scale=" ).append( scale )
          .append( " fieldType=" ).append( qt(fieldType) )
          .append( " type=" ).append( qt(type) )
          .append( " minOccurs=" ).append( minOccurs )
          .append( " maxOccurs=" ).append( maxOccurs )
          .append( "}" );        
        
        return sb.toString();
    }    

}
