/**
 * ErrorMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;


public class ErrorMessage  implements java.io.Serializable {
    /* codice messaggio */
    private java.lang.String code;

    /* descrizione */
    private java.lang.String descr;

    /* livello di gravit del messaggio */
    private it.asitech.webservice.protocollo.ErrorMessageLevel level;

    public ErrorMessage() {
        //this("","",ErrorMessageLevel.NONE);
    }

    public ErrorMessage(
            java.lang.String code,
            java.lang.String descr,
            it.asitech.webservice.protocollo.ErrorMessageLevel level) {
            this.code = code;
            this.descr = descr;
            this.level = level;
     }


     /**
      * Gets the code _value for this ErrorMessage.
      * 
      * @return code   * codice messaggio
      */
     public java.lang.String getCode() {
         return code;
     }


     /**
      * Sets the code _value for this ErrorMessage.
      * 
      * @param code   * codice messaggio
      */
     public void setCode(java.lang.String code) {
         this.code = code;
     }


     /**
      * Gets the descr _value for this ErrorMessage.
      * 
      * @return descr   * descrizione
      */
     public java.lang.String getDescr() {
         return descr;
     }


     /**
      * Sets the descr _value for this ErrorMessage.
      * 
      * @param descr   * descrizione
      */
     public void setDescr(java.lang.String descr) {
         this.descr = descr;
     }


     /**
      * Gets the level _value for this ErrorMessage.
      * 
      * @return level   * livello di gravità del messaggio
      */
     public it.asitech.webservice.protocollo.ErrorMessageLevel getLevel() {
         return level;
     }


     /**
      * Sets the level _value for this ErrorMessage.
      * 
      * @param level   * livello di gravità del messaggio
      */
     public void setLevel(it.asitech.webservice.protocollo.ErrorMessageLevel level) {
         this.level = level;
     }

     private java.lang.Object __equalsCalc = null;
     public synchronized boolean equals(java.lang.Object obj) {
         if (!(obj instanceof ErrorMessage)) return false;
         ErrorMessage other = (ErrorMessage) obj;
         if (obj == null) return false;
         if (this == obj) return true;
         if (__equalsCalc != null) {
             return (__equalsCalc == obj);
         }
         __equalsCalc = obj;
         boolean _equals;
         _equals = true && 
             ((this.code==null && other.getCode()==null) || 
              (this.code!=null &&
               this.code.equals(other.getCode()))) &&
             ((this.descr==null && other.getDescr()==null) || 
              (this.descr!=null &&
               this.descr.equals(other.getDescr()))) &&
             ((this.level==null && other.getLevel()==null) || 
              (this.level!=null &&
               this.level.equals(other.getLevel())));
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
         if (getCode() != null) {
             _hashCode += getCode().hashCode();
         }
         if (getDescr() != null) {
             _hashCode += getDescr().hashCode();
         }
         if (getLevel() != null) {
             _hashCode += getLevel().hashCode();
         }
         __hashCodeCalc = false;
         return _hashCode;
     }

     // Type metadata
     private static org.apache.axis.description.TypeDesc typeDesc =
         new org.apache.axis.description.TypeDesc(ErrorMessage.class, true);

     static {
         typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ErrorMessage"));
         org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
         elemField.setFieldName("code");
         elemField.setXmlName(new javax.xml.namespace.QName("", "code"));
         elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
         elemField.setNillable(true);
         typeDesc.addFieldDesc(elemField);
         elemField = new org.apache.axis.description.ElementDesc();
         elemField.setFieldName("descr");
         elemField.setXmlName(new javax.xml.namespace.QName("", "descr"));
         elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
         elemField.setNillable(true);
         typeDesc.addFieldDesc(elemField);
         elemField = new org.apache.axis.description.ElementDesc();
         elemField.setFieldName("level");
         elemField.setXmlName(new javax.xml.namespace.QName("", "level"));
         elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", ">ErrorMessage>level"));
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

    private String qt(String vl){
        return "\""+vl+"\"";
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "{" )
          .append( this.getClass().getName() )          
          .append( "> code=" ).append( qt(this.code) ) 
          .append( " errors=" ).append( qt(this.descr) )
          .append( " level=" ).append( qt(this.level!=null ? this.level.getValue() : "null") )          
          .append( "}" );
        
        return sb.toString();
    }

}
