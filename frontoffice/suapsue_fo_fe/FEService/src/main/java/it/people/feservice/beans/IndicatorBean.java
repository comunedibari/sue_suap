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

import java.util.Calendar;


/**
 * This bean contains an indicator for Monitoring
 * 
 * @author Andrea Piemontese
 * @created 23/lug/2012
 *
 */
public class IndicatorBean implements java.io.Serializable {
    
	private static final long serialVersionUID = 4449844295364734821L;
	
	private String codiceEnte;
	private String nomeEnte;
	private String attivitaName;
	private String serviceName;

	private String processName;

	//number of transaction (total)
	private String numOfTransactions;
	//number of transaction out of opening hours
	private String numOfTransactionsOutOfOpeningHours;
	
	private java.util.Calendar timestamp;
	
	private String exchangedDocs;
	
	private String intermediaryUser;
	
    public IndicatorBean() {
    	
    }

  
	public IndicatorBean(String codiceEnte, String nomeEnte, String attivitaName,
			String serviceName, String processName, String numOfTransactions,
			String numOfTransactionsOutOfOpeningHours, Calendar timestamp,
			String exchangedDocs, String intermediaryUser) {

		this.codiceEnte = codiceEnte;
		this.nomeEnte = nomeEnte;
		this.attivitaName = attivitaName;
		this.serviceName = serviceName;
		this.processName = processName;
		this.numOfTransactions = numOfTransactions;
		this.numOfTransactionsOutOfOpeningHours = numOfTransactionsOutOfOpeningHours;
		this.timestamp = timestamp;
		this.exchangedDocs = exchangedDocs;
		this.intermediaryUser = intermediaryUser;
	}


	/**
	 * @return the codiceEnte
	 */
	public String getCodiceEnte() {
		return codiceEnte;
	}

	/**
	 * @param codiceEnte the codiceEnte to set
	 */
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	/**
	 * @return the nomeEnte
	 */
	public final String getNomeEnte() {
	    return nomeEnte;
	}


	/**
	 * @param nomeEnte the nomeEnte to set
	 */
	public final void setNomeEnte(String nomeEnte) {
	    this.nomeEnte = nomeEnte;
	}


	/**
	 * @return the attivitaName
	 */
	public String getAttivitaName() {
		return attivitaName;
	}


	/**
	 * @param attivitaName the attivitaName to set
	 */
	public void setAttivitaName(String attivitaName) {
		this.attivitaName = attivitaName;
	}


	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}


	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	/**
	 * @return the numOfTransactions
	 */
	public String getNumOfTransactions() {
		return numOfTransactions;
	}


	/**
	 * @param numOfTransactions the numOfTransactions to set
	 */
	public void setNumOfTransactions(String numOfTransactions) {
		this.numOfTransactions = numOfTransactions;
	}



	/**
	 * @return the exchangedDocs
	 */
	public String getExchangedDocs() {
		return exchangedDocs;
	}


	/**
	 * @param exchangedDocs the exchangedDocs to set
	 */
	public void setExchangedDocs(String exchangedDocs) {
		this.exchangedDocs = exchangedDocs;
	}



	/**
	 * @return the intermediaryUser
	 */
	public String getIntermediaryUser() {
		return intermediaryUser;
	}


	/**
	 * @param intermediaryUser the intermediaryUser to set
	 */
	public void setIntermediaryUser(String intermediaryUser) {
		this.intermediaryUser = intermediaryUser;
	}


	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}


	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IndicatorBean)) return false;
        IndicatorBean other = (IndicatorBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCodiceEnte() == null && other.getCodiceEnte() == null) || 
             (this.getCodiceEnte()!=null && this.getCodiceEnte().equals(other.getCodiceEnte()))) &&
             
             ((this.getAttivitaName() == null && other.getAttivitaName() == null) || 
                     (this.getAttivitaName() != null && this.getAttivitaName().equals(other.getAttivitaName()))) &&
        
             ((this.getServiceName() == null && other.getServiceName() == null) || 
                     (this.getServiceName() != null && this.getServiceName().equals(other.getServiceName()))) &&
                     
             ((this.getNumOfTransactions() == null && other.getNumOfTransactions() == null) || 
                     (this.getNumOfTransactions() != null && this.getNumOfTransactions().equals(other.getNumOfTransactions()))) &&
                     
             ((this.getNumOfTransactionsOutOfOpeningHours() == null && other.getNumOfTransactionsOutOfOpeningHours() == null) || 
                     (this.getNumOfTransactionsOutOfOpeningHours() != null && this.getNumOfTransactionsOutOfOpeningHours().equals(other.getNumOfTransactionsOutOfOpeningHours()))) &&

    		 ((this.getTimestamp() == null && other.getTimestamp() == null) || 
                     (this.getTimestamp() != null && this.getTimestamp().equals(other.getTimestamp()))) &&
                     
             ((this.getProcessName() == null && other.getProcessName() == null) || 
                     (this.getProcessName() != null && this.getProcessName().equals(other.getProcessName()))) &&        
    		 
             ((this.getExchangedDocs() == null && other.getExchangedDocs() == null) || 
                     (this.getExchangedDocs() != null && this.getExchangedDocs().equals(other.getExchangedDocs()))) &&      
     
             ((this.getIntermediaryUser() == null && other.getIntermediaryUser() == null) || 
                     (this.getIntermediaryUser() != null && this.getIntermediaryUser().equals(other.getIntermediaryUser())));       
		
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

        if (getCodiceEnte() != null) {
            _hashCode += getCodiceEnte().hashCode();
        }
        
        if (getAttivitaName() != null) {
            _hashCode += getAttivitaName().hashCode();
        }
        
        if (getServiceName() != null) {
            _hashCode += getServiceName().hashCode();
        }
        
        if (getNumOfTransactions() != null) {
        	_hashCode += getNumOfTransactions().hashCode();
        }
        
        if (getNumOfTransactionsOutOfOpeningHours() != null) {
        	_hashCode += getNumOfTransactionsOutOfOpeningHours().hashCode();
        }
        
        if (getTimestamp() != null) {
        	_hashCode += getTimestamp().hashCode();
        }
        
        if (getProcessName() != null) {
            _hashCode += getProcessName().hashCode();
        }
         
        if (getExchangedDocs() != null) {
            _hashCode += getExchangedDocs().hashCode();
        }
       
        __hashCodeCalc = false;
        return _hashCode;
    }
	

	// Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IndicatorBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorBean"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "codiceEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivitaName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "attivitaName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "serviceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "processName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numOfTransactions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "numOfTransactions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numOfTransactionsOutOfOpeningHours");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "numOfTransactionsOutOfOpeningHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exchangedDocs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "exchangedDocs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intermediaryUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "intermediaryUser"));
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


	/**
	 * @return the numOfTransactionsOutOfOpeningHours
	 */
	public String getNumOfTransactionsOutOfOpeningHours() {
		return numOfTransactionsOutOfOpeningHours;
	}


	/**
	 * @param numOfTransactionsOutOfOpeningHours the numOfTransactionsOutOfOpeningHours to set
	 */
	public void setNumOfTransactionsOutOfOpeningHours(String numOfTransactionsOutOfOpeningHours) {
		this.numOfTransactionsOutOfOpeningHours = numOfTransactionsOutOfOpeningHours;
	}


	/**
	 * @return the timestamp
	 */
	public java.util.Calendar getTimestamp() {
		return timestamp;
	}


	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(java.util.Calendar timestamp) {
		this.timestamp = timestamp;
	}

}
