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

public class LogBean  implements java.io.Serializable {
    private java.lang.String logLevel;
    private java.lang.String messaggio;
    private java.lang.String idcomune;
    private java.lang.String servizio;
    private int idservizio;
    private java.util.Calendar date;
    private java.lang.String dateString;
    private int id;
    private int idloglevel;

    public LogBean() {
    }

    public LogBean(
           java.lang.String logLevel,
           java.lang.String messaggio,
           java.lang.String idcomune,
           java.lang.String servizio,
           int idservizio,
           java.util.Calendar date,
           java.lang.String dateString,
           int id,
           int idloglevel) {
           this.logLevel = logLevel;
           this.messaggio = messaggio;
           this.idcomune = idcomune;
           this.servizio = servizio;
           this.idservizio = idservizio;
           this.date = date;
           this.dateString = dateString;
           this.id = id;
           this.idloglevel = idloglevel;
    }


    /**
     * Gets the logLevel value for this LogBean.
     * 
     * @return logLevel
     */
    public java.lang.String getLogLevel() {
        return logLevel;
    }


    /**
     * Sets the logLevel value for this LogBean.
     * 
     * @param logLevel
     */
    public void setLogLevel(java.lang.String logLevel) {
        this.logLevel = logLevel;
    }


    /**
     * Gets the messaggio value for this LogBean.
     * 
     * @return messaggio
     */
    public java.lang.String getMessaggio() {
        return messaggio;
    }


    /**
     * Sets the messaggio value for this LogBean.
     * 
     * @param messaggio
     */
    public void setMessaggio(java.lang.String messaggio) {
        this.messaggio = messaggio;
    }


    /**
     * Gets the idcomune value for this LogBean.
     * 
     * @return idcomune
     */
    public java.lang.String getIdcomune() {
        return idcomune;
    }


    /**
     * Sets the idcomune value for this LogBean.
     * 
     * @param idcomune
     */
    public void setIdcomune(java.lang.String idcomune) {
        this.idcomune = idcomune;
    }


    /**
     * Gets the servizio value for this LogBean.
     * 
     * @return servizio
     */
    public java.lang.String getServizio() {
        return servizio;
    }


    /**
     * Sets the servizio value for this LogBean.
     * 
     * @param servizio
     */
    public void setServizio(java.lang.String servizio) {
        this.servizio = servizio;
    }


    /**
     * Gets the idservizio value for this LogBean.
     * 
     * @return idservizio
     */
    public int getIdservizio() {
        return idservizio;
    }


    /**
     * Sets the idservizio value for this LogBean.
     * 
     * @param idservizio
     */
    public void setIdservizio(int idservizio) {
        this.idservizio = idservizio;
    }


    /**
     * Gets the date value for this LogBean.
     * 
     * @return date
     */
    public java.util.Calendar getDate() {
        return date;
    }


    /**
     * Sets the date value for this LogBean.
     * 
     * @param date
     */
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }


    /**
     * Gets the dateString value for this LogBean.
     * 
     * @return dateString
     */
    public java.lang.String getDateString() {
        return dateString;
    }


    /**
     * Sets the dateString value for this LogBean.
     * 
     * @param dateString
     */
    public void setDateString(java.lang.String dateString) {
        this.dateString = dateString;
    }


    /**
     * Gets the id value for this LogBean.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this LogBean.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the idloglevel value for this LogBean.
     * 
     * @return idloglevel
     */
    public int getIdloglevel() {
        return idloglevel;
    }


    /**
     * Sets the idloglevel value for this LogBean.
     * 
     * @param idloglevel
     */
    public void setIdloglevel(int idloglevel) {
        this.idloglevel = idloglevel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LogBean)) return false;
        LogBean other = (LogBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.logLevel==null && other.getLogLevel()==null) || 
             (this.logLevel!=null &&
              this.logLevel.equals(other.getLogLevel()))) &&
            ((this.messaggio==null && other.getMessaggio()==null) || 
             (this.messaggio!=null &&
              this.messaggio.equals(other.getMessaggio()))) &&
            ((this.idcomune==null && other.getIdcomune()==null) || 
             (this.idcomune!=null &&
              this.idcomune.equals(other.getIdcomune()))) &&
            ((this.servizio==null && other.getServizio()==null) || 
             (this.servizio!=null &&
              this.servizio.equals(other.getServizio()))) &&
            this.idservizio == other.getIdservizio() &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.dateString==null && other.getDateString()==null) || 
             (this.dateString!=null &&
              this.dateString.equals(other.getDateString()))) &&
            this.id == other.getId() &&
            this.idloglevel == other.getIdloglevel();
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
        if (getLogLevel() != null) {
            _hashCode += getLogLevel().hashCode();
        }
        if (getMessaggio() != null) {
            _hashCode += getMessaggio().hashCode();
        }
        if (getIdcomune() != null) {
            _hashCode += getIdcomune().hashCode();
        }
        if (getServizio() != null) {
            _hashCode += getServizio().hashCode();
        }
        _hashCode += getIdservizio();
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getDateString() != null) {
            _hashCode += getDateString().hashCode();
        }
        _hashCode += getId();
        _hashCode += getIdloglevel();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "logLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "messaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idcomune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "idcomune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servizio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "servizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idservizio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "idservizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateString");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "dateString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idloglevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "idloglevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
