/**
 * ResponseMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

public class ResponseMessage  implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4940460283073434735L;

    /* numero errori inviati */
    private java.lang.Integer count;

    /* lista dei messaggi con il livello gi gravit */
    private it.asitech.webservice.protocollo.ErrorMessage[] errors;

    /* indica se l'operazione  fallita o meno */
    private java.lang.Boolean rejected;

    /* messaggio di risposta */
    private java.lang.String message;

    public ResponseMessage() {
        this(0, new ErrorMessage[0], false, "");
    }

    public ResponseMessage(boolean initDefault) {
        this(new ErrorMessage[0]);
    }

    public ResponseMessage(ErrorMessage[] errors) {
        this.setErrors( errors );
    }
    
    public ResponseMessage(
           java.lang.Integer count,
           it.asitech.webservice.protocollo.ErrorMessage[] errors,
           java.lang.Boolean rejected,
           java.lang.String message) {
           this.count = count;
           this.errors = errors;
           this.rejected = rejected;
           this.message = message;
    }


    /**
     * Gets the count _value for this ResponseMessage.
     * 
     * @return count   * numero errori inviati
     */
    public java.lang.Integer getCount() {
        return count;
    }


    /**
     * Sets the count _value for this ResponseMessage.
     * 
     * @param count   * numero errori inviati
     */
    public void setCount(java.lang.Integer count) {
        this.count = count;
    }


    /**
     * Gets the errors _value for this ResponseMessage.
     * 
     * @return errors   * lista dei messaggi con il livello gi gravità
     */
    public it.asitech.webservice.protocollo.ErrorMessage[] getErrors() {
        return errors;
    }


    private void setReject(){
        int f = 0;
        int e = 0;
        int w = 0;
        int i = 0;
        int cnt = 0;
        String m = "operazione completata correttamente";
        String me = "";
        if( this.errors!=null ){
            for( ErrorMessage er : this.errors ) {
                if( er.getLevel()== ErrorMessageLevel.FATAL ) f++;
                else if( er.getLevel()== ErrorMessageLevel.ERROR ) e++;
                else if( er.getLevel()== ErrorMessageLevel.WARNING ) w++;
                else i++;
                cnt++;
            }
        }

        if( i>0 ) {
            m = "operazione completata con segnalazioni";
            me = " INFO="+i;
        }                        
        if( w>0 ) {
            m = "operazione completata con segnalazioni";
            me = " WARNING="+w;
        }                
        if( e>0 ) {
            m = "operazione fallita";
            me = " ERROR="+e;
        }        
        if( f>0 ) {
            m = "operazione fallita";
            me = " FATAL="+f;
        }
        
        this.count = cnt;
        this.rejected = f>0 || e>0;
        this.message  = m+me;        
    }
    
    /**
     * Sets the errors _value for this ResponseMessage.
     * 
     * @param errors   * lista dei messaggi con il livello gi gravità
     */
    public void setErrors(it.asitech.webservice.protocollo.ErrorMessage[] errors) {        
        this.errors = errors;
        this.setReject();
    }
    


    /**
     * Gets the rejected _value for this ResponseMessage.
     * 
     * @return rejected   * indica se l'operazione è fallita o meno
     */
    public java.lang.Boolean isRejected() {
        return rejected;
    }


    /**
     * Sets the rejected _value for this ResponseMessage.
     * 
     * @param rejected   * indica se l'operazione è fallita o meno
     */
    public void setRejected(java.lang.Boolean rejected) {
        this.rejected = rejected;
    }


    /**
     * Gets the message _value for this ResponseMessage.
     * 
     * @return message   * messaggio di risposta
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message _value for this ResponseMessage.
     * 
     * @param message   * messaggio di risposta
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseMessage)) return false;
        ResponseMessage other = (ResponseMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.count==null && other.getCount()==null) || 
             (this.count!=null &&
              this.count.equals(other.getCount()))) &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors()))) &&
            ((this.rejected==null && other.isRejected()==null) || 
             (this.rejected!=null &&
              this.rejected.equals(other.isRejected()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage())));
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
        if (getCount() != null) {
            _hashCode += getCount().hashCode();
        }
        if (getErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (isRejected() != null) {
            _hashCode += isRejected().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ResponseMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("", "count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ErrorMessage"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rejected");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rejected"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("", "message"));
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

    private String qt(String vl){
        if( vl!=null)
            return "\""+vl+"\"";
        return vl.toString();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "{" )
          .append( this.getClass().getName() )          
          .append( "> count=" ).append( this.count )
          .append( "> message=" ).append( qt(this.message) )          
          .append( " errors=[" );
        if( this.errors==null ) {
            sb.append( "null" );
        } else {
            int i=0;
            for( ErrorMessage e : this.errors ) {
                if( i++ > 0 ) {
                    sb.append( ", " );
                }
                sb.append( e.toString() );            
            }
        }        
        sb.append( "]}" );

        return sb.toString();
    }

}