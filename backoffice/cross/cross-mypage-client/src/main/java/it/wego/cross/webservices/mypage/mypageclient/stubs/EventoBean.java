/**
 * EventoBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.mypage.mypageclient.stubs;

public class EventoBean  implements java.io.Serializable {
    private java.lang.String altreInfo;

    private java.lang.String descrizione_evento;

    private java.lang.String id_bo;

    private java.lang.String process_data_id;

    private long timestamp_evento;

    private java.lang.String url_visura;

    private boolean visibilita;

    public EventoBean() {
    }

    public EventoBean(
           java.lang.String altreInfo,
           java.lang.String descrizione_evento,
           java.lang.String id_bo,
           java.lang.String process_data_id,
           long timestamp_evento,
           java.lang.String url_visura,
           boolean visibilita) {
           this.altreInfo = altreInfo;
           this.descrizione_evento = descrizione_evento;
           this.id_bo = id_bo;
           this.process_data_id = process_data_id;
           this.timestamp_evento = timestamp_evento;
           this.url_visura = url_visura;
           this.visibilita = visibilita;
    }


    /**
     * Gets the altreInfo value for this EventoBean.
     * 
     * @return altreInfo
     */
    public java.lang.String getAltreInfo() {
        return altreInfo;
    }


    /**
     * Sets the altreInfo value for this EventoBean.
     * 
     * @param altreInfo
     */
    public void setAltreInfo(java.lang.String altreInfo) {
        this.altreInfo = altreInfo;
    }


    /**
     * Gets the descrizione_evento value for this EventoBean.
     * 
     * @return descrizione_evento
     */
    public java.lang.String getDescrizione_evento() {
        return descrizione_evento;
    }


    /**
     * Sets the descrizione_evento value for this EventoBean.
     * 
     * @param descrizione_evento
     */
    public void setDescrizione_evento(java.lang.String descrizione_evento) {
        this.descrizione_evento = descrizione_evento;
    }


    /**
     * Gets the id_bo value for this EventoBean.
     * 
     * @return id_bo
     */
    public java.lang.String getId_bo() {
        return id_bo;
    }


    /**
     * Sets the id_bo value for this EventoBean.
     * 
     * @param id_bo
     */
    public void setId_bo(java.lang.String id_bo) {
        this.id_bo = id_bo;
    }


    /**
     * Gets the process_data_id value for this EventoBean.
     * 
     * @return process_data_id
     */
    public java.lang.String getProcess_data_id() {
        return process_data_id;
    }


    /**
     * Sets the process_data_id value for this EventoBean.
     * 
     * @param process_data_id
     */
    public void setProcess_data_id(java.lang.String process_data_id) {
        this.process_data_id = process_data_id;
    }


    /**
     * Gets the timestamp_evento value for this EventoBean.
     * 
     * @return timestamp_evento
     */
    public long getTimestamp_evento() {
        return timestamp_evento;
    }


    /**
     * Sets the timestamp_evento value for this EventoBean.
     * 
     * @param timestamp_evento
     */
    public void setTimestamp_evento(long timestamp_evento) {
        this.timestamp_evento = timestamp_evento;
    }


    /**
     * Gets the url_visura value for this EventoBean.
     * 
     * @return url_visura
     */
    public java.lang.String getUrl_visura() {
        return url_visura;
    }


    /**
     * Sets the url_visura value for this EventoBean.
     * 
     * @param url_visura
     */
    public void setUrl_visura(java.lang.String url_visura) {
        this.url_visura = url_visura;
    }


    /**
     * Gets the visibilita value for this EventoBean.
     * 
     * @return visibilita
     */
    public boolean isVisibilita() {
        return visibilita;
    }


    /**
     * Sets the visibilita value for this EventoBean.
     * 
     * @param visibilita
     */
    public void setVisibilita(boolean visibilita) {
        this.visibilita = visibilita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EventoBean)) return false;
        EventoBean other = (EventoBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.altreInfo==null && other.getAltreInfo()==null) || 
             (this.altreInfo!=null &&
              this.altreInfo.equals(other.getAltreInfo()))) &&
            ((this.descrizione_evento==null && other.getDescrizione_evento()==null) || 
             (this.descrizione_evento!=null &&
              this.descrizione_evento.equals(other.getDescrizione_evento()))) &&
            ((this.id_bo==null && other.getId_bo()==null) || 
             (this.id_bo!=null &&
              this.id_bo.equals(other.getId_bo()))) &&
            ((this.process_data_id==null && other.getProcess_data_id()==null) || 
             (this.process_data_id!=null &&
              this.process_data_id.equals(other.getProcess_data_id()))) &&
            this.timestamp_evento == other.getTimestamp_evento() &&
            ((this.url_visura==null && other.getUrl_visura()==null) || 
             (this.url_visura!=null &&
              this.url_visura.equals(other.getUrl_visura()))) &&
            this.visibilita == other.isVisibilita();
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
        if (getAltreInfo() != null) {
            _hashCode += getAltreInfo().hashCode();
        }
        if (getDescrizione_evento() != null) {
            _hashCode += getDescrizione_evento().hashCode();
        }
        if (getId_bo() != null) {
            _hashCode += getId_bo().hashCode();
        }
        if (getProcess_data_id() != null) {
            _hashCode += getProcess_data_id().hashCode();
        }
        _hashCode += new Long(getTimestamp_evento()).hashCode();
        if (getUrl_visura() != null) {
            _hashCode += getUrl_visura().hashCode();
        }
        _hashCode += (isVisibilita() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EventoBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("setEventService", "EventoBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altreInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altreInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione_evento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione_evento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_bo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id_bo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("process_data_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "process_data_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp_evento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timestamp_evento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url_visura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url_visura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "visibilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
