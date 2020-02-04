/**
 * Evento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.cxf.interoperability;

public class Evento  implements java.io.Serializable {
    private it.wego.cross.webservices.cxf.interoperability.Allegato[] allegati;

    private java.lang.String codiceEnte;

    private java.lang.String codiceEvento;

    private it.wego.cross.webservices.cxf.interoperability.Comunicazione comunicazione;

    private java.util.Calendar dataEvento;

    private java.util.Calendar dataProtocollo;

    private java.lang.String descrizioneEvento;

    private java.lang.Integer idEvento;

    private java.lang.Integer idPratica;

    private java.lang.String identificativoPratica;

    private java.lang.String numeroProtocollo;

    private it.wego.cross.webservices.cxf.interoperability.Soggetto[] soggetto;

    public Evento() {
    }

    public Evento(
           it.wego.cross.webservices.cxf.interoperability.Allegato[] allegati,
           java.lang.String codiceEnte,
           java.lang.String codiceEvento,
           it.wego.cross.webservices.cxf.interoperability.Comunicazione comunicazione,
           java.util.Calendar dataEvento,
           java.util.Calendar dataProtocollo,
           java.lang.String descrizioneEvento,
           java.lang.Integer idEvento,
           java.lang.Integer idPratica,
           java.lang.String identificativoPratica,
           java.lang.String numeroProtocollo,
           it.wego.cross.webservices.cxf.interoperability.Soggetto[] soggetto) {
           this.allegati = allegati;
           this.codiceEnte = codiceEnte;
           this.codiceEvento = codiceEvento;
           this.comunicazione = comunicazione;
           this.dataEvento = dataEvento;
           this.dataProtocollo = dataProtocollo;
           this.descrizioneEvento = descrizioneEvento;
           this.idEvento = idEvento;
           this.idPratica = idPratica;
           this.identificativoPratica = identificativoPratica;
           this.numeroProtocollo = numeroProtocollo;
           this.soggetto = soggetto;
    }


    /**
     * Gets the allegati value for this Evento.
     * 
     * @return allegati
     */
    public it.wego.cross.webservices.cxf.interoperability.Allegato[] getAllegati() {
        return allegati;
    }


    /**
     * Sets the allegati value for this Evento.
     * 
     * @param allegati
     */
    public void setAllegati(it.wego.cross.webservices.cxf.interoperability.Allegato[] allegati) {
        this.allegati = allegati;
    }


    /**
     * Gets the codiceEnte value for this Evento.
     * 
     * @return codiceEnte
     */
    public java.lang.String getCodiceEnte() {
        return codiceEnte;
    }


    /**
     * Sets the codiceEnte value for this Evento.
     * 
     * @param codiceEnte
     */
    public void setCodiceEnte(java.lang.String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }


    /**
     * Gets the codiceEvento value for this Evento.
     * 
     * @return codiceEvento
     */
    public java.lang.String getCodiceEvento() {
        return codiceEvento;
    }


    /**
     * Sets the codiceEvento value for this Evento.
     * 
     * @param codiceEvento
     */
    public void setCodiceEvento(java.lang.String codiceEvento) {
        this.codiceEvento = codiceEvento;
    }


    /**
     * Gets the comunicazione value for this Evento.
     * 
     * @return comunicazione
     */
    public it.wego.cross.webservices.cxf.interoperability.Comunicazione getComunicazione() {
        return comunicazione;
    }


    /**
     * Sets the comunicazione value for this Evento.
     * 
     * @param comunicazione
     */
    public void setComunicazione(it.wego.cross.webservices.cxf.interoperability.Comunicazione comunicazione) {
        this.comunicazione = comunicazione;
    }


    /**
     * Gets the dataEvento value for this Evento.
     * 
     * @return dataEvento
     */
    public java.util.Calendar getDataEvento() {
        return dataEvento;
    }


    /**
     * Sets the dataEvento value for this Evento.
     * 
     * @param dataEvento
     */
    public void setDataEvento(java.util.Calendar dataEvento) {
        this.dataEvento = dataEvento;
    }


    /**
     * Gets the dataProtocollo value for this Evento.
     * 
     * @return dataProtocollo
     */
    public java.util.Calendar getDataProtocollo() {
        return dataProtocollo;
    }


    /**
     * Sets the dataProtocollo value for this Evento.
     * 
     * @param dataProtocollo
     */
    public void setDataProtocollo(java.util.Calendar dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }


    /**
     * Gets the descrizioneEvento value for this Evento.
     * 
     * @return descrizioneEvento
     */
    public java.lang.String getDescrizioneEvento() {
        return descrizioneEvento;
    }


    /**
     * Sets the descrizioneEvento value for this Evento.
     * 
     * @param descrizioneEvento
     */
    public void setDescrizioneEvento(java.lang.String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }


    /**
     * Gets the idEvento value for this Evento.
     * 
     * @return idEvento
     */
    public java.lang.Integer getIdEvento() {
        return idEvento;
    }


    /**
     * Sets the idEvento value for this Evento.
     * 
     * @param idEvento
     */
    public void setIdEvento(java.lang.Integer idEvento) {
        this.idEvento = idEvento;
    }


    /**
     * Gets the idPratica value for this Evento.
     * 
     * @return idPratica
     */
    public java.lang.Integer getIdPratica() {
        return idPratica;
    }


    /**
     * Sets the idPratica value for this Evento.
     * 
     * @param idPratica
     */
    public void setIdPratica(java.lang.Integer idPratica) {
        this.idPratica = idPratica;
    }


    /**
     * Gets the identificativoPratica value for this Evento.
     * 
     * @return identificativoPratica
     */
    public java.lang.String getIdentificativoPratica() {
        return identificativoPratica;
    }


    /**
     * Sets the identificativoPratica value for this Evento.
     * 
     * @param identificativoPratica
     */
    public void setIdentificativoPratica(java.lang.String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }


    /**
     * Gets the numeroProtocollo value for this Evento.
     * 
     * @return numeroProtocollo
     */
    public java.lang.String getNumeroProtocollo() {
        return numeroProtocollo;
    }


    /**
     * Sets the numeroProtocollo value for this Evento.
     * 
     * @param numeroProtocollo
     */
    public void setNumeroProtocollo(java.lang.String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }


    /**
     * Gets the soggetto value for this Evento.
     * 
     * @return soggetto
     */
    public it.wego.cross.webservices.cxf.interoperability.Soggetto[] getSoggetto() {
        return soggetto;
    }


    /**
     * Sets the soggetto value for this Evento.
     * 
     * @param soggetto
     */
    public void setSoggetto(it.wego.cross.webservices.cxf.interoperability.Soggetto[] soggetto) {
        this.soggetto = soggetto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Evento)) return false;
        Evento other = (Evento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allegati==null && other.getAllegati()==null) || 
             (this.allegati!=null &&
              java.util.Arrays.equals(this.allegati, other.getAllegati()))) &&
            ((this.codiceEnte==null && other.getCodiceEnte()==null) || 
             (this.codiceEnte!=null &&
              this.codiceEnte.equals(other.getCodiceEnte()))) &&
            ((this.codiceEvento==null && other.getCodiceEvento()==null) || 
             (this.codiceEvento!=null &&
              this.codiceEvento.equals(other.getCodiceEvento()))) &&
            ((this.comunicazione==null && other.getComunicazione()==null) || 
             (this.comunicazione!=null &&
              this.comunicazione.equals(other.getComunicazione()))) &&
            ((this.dataEvento==null && other.getDataEvento()==null) || 
             (this.dataEvento!=null &&
              this.dataEvento.equals(other.getDataEvento()))) &&
            ((this.dataProtocollo==null && other.getDataProtocollo()==null) || 
             (this.dataProtocollo!=null &&
              this.dataProtocollo.equals(other.getDataProtocollo()))) &&
            ((this.descrizioneEvento==null && other.getDescrizioneEvento()==null) || 
             (this.descrizioneEvento!=null &&
              this.descrizioneEvento.equals(other.getDescrizioneEvento()))) &&
            ((this.idEvento==null && other.getIdEvento()==null) || 
             (this.idEvento!=null &&
              this.idEvento.equals(other.getIdEvento()))) &&
            ((this.idPratica==null && other.getIdPratica()==null) || 
             (this.idPratica!=null &&
              this.idPratica.equals(other.getIdPratica()))) &&
            ((this.identificativoPratica==null && other.getIdentificativoPratica()==null) || 
             (this.identificativoPratica!=null &&
              this.identificativoPratica.equals(other.getIdentificativoPratica()))) &&
            ((this.numeroProtocollo==null && other.getNumeroProtocollo()==null) || 
             (this.numeroProtocollo!=null &&
              this.numeroProtocollo.equals(other.getNumeroProtocollo()))) &&
            ((this.soggetto==null && other.getSoggetto()==null) || 
             (this.soggetto!=null &&
              java.util.Arrays.equals(this.soggetto, other.getSoggetto())));
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
        if (getAllegati() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegati());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegati(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCodiceEnte() != null) {
            _hashCode += getCodiceEnte().hashCode();
        }
        if (getCodiceEvento() != null) {
            _hashCode += getCodiceEvento().hashCode();
        }
        if (getComunicazione() != null) {
            _hashCode += getComunicazione().hashCode();
        }
        if (getDataEvento() != null) {
            _hashCode += getDataEvento().hashCode();
        }
        if (getDataProtocollo() != null) {
            _hashCode += getDataProtocollo().hashCode();
        }
        if (getDescrizioneEvento() != null) {
            _hashCode += getDescrizioneEvento().hashCode();
        }
        if (getIdEvento() != null) {
            _hashCode += getIdEvento().hashCode();
        }
        if (getIdPratica() != null) {
            _hashCode += getIdPratica().hashCode();
        }
        if (getIdentificativoPratica() != null) {
            _hashCode += getIdentificativoPratica().hashCode();
        }
        if (getNumeroProtocollo() != null) {
            _hashCode += getNumeroProtocollo().hashCode();
        }
        if (getSoggetto() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSoggetto());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSoggetto(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Evento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "evento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "allegato"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "allegati"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comunicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comunicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "comunicazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPratica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPratica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoPratica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificativoPratica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProtocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProtocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "soggetto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "soggetti"));
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
