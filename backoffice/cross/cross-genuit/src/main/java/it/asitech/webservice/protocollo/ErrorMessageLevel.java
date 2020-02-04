/**
 * ErrorMessageLevel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

public class ErrorMessageLevel implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ErrorMessageLevel(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FATAL = "FATAL";
    public static final java.lang.String _ERROR = "ERROR";
    public static final java.lang.String _WARNING = "WARNING";
    public static final java.lang.String _INFO = "INFO";
    public static final ErrorMessageLevel FATAL = new ErrorMessageLevel(_FATAL);
    public static final ErrorMessageLevel ERROR = new ErrorMessageLevel(_ERROR);
    public static final ErrorMessageLevel WARNING = new ErrorMessageLevel(_WARNING);
    public static final ErrorMessageLevel INFO = new ErrorMessageLevel(_INFO);
    //public static final ErrorMessageLevel NONE = new ErrorMessageLevel("");
    public java.lang.String getValue() { return _value_;}
    public static ErrorMessageLevel fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ErrorMessageLevel enumeration = (ErrorMessageLevel)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ErrorMessageLevel fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ErrorMessageLevel.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", ">ErrorMessage>level"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    //aggiunte....
    private static final java.lang.String[] LEVELS = {ErrorMessageLevel._INFO, ErrorMessageLevel._WARNING, ErrorMessageLevel._ERROR, ErrorMessageLevel._FATAL};
    
    private int getLevel(ErrorMessageLevel l){
        for( int i = 0; i < LEVELS.length; i++ ) {
            if( LEVELS[i].equals( l._value_ ) ) return i;
        }
        return -1;
    }
    
    public int compareTo( Object o ) {
        if( this == o ) return 0;
        if( o == null ) return -1;
        if( getClass() != o.getClass() ) return -1;
        
        return Integer.signum( getLevel(this) - getLevel((ErrorMessageLevel)o) );
    }

}
