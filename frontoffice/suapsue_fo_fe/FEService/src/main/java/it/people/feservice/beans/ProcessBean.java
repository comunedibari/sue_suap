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

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * This bean contains a process also known as "Pratica" 
 * i.e. "Visura" (not submittable process)
 * i.e. "Istanza" (submittable process) 
 * 
 * @author Andrea Piemontese
 * @created 10/set/2012
 *
 */
public class ProcessBean implements java.io.Serializable {
    
	private static final long serialVersionUID = -7744380594149174348L;
	
	private String codiceEnte;
	private String attivitaName;
	private String serviceName;

	private String processName;
	
	private java.util.Calendar timestamp;
	
	private String stringTimestamp;
	
	private String processType;

	private String username;
	
    public ProcessBean() {
    	
    }

	public ProcessBean(String codiceEnte, String attivitaName,
			String serviceName, String processName, Calendar timestamp, String stringTimestamp,
			String processType, String username) {

		this.codiceEnte = codiceEnte;
		this.attivitaName = attivitaName;
		this.serviceName = serviceName;
		this.processName = processName;
		this.timestamp = timestamp;
		this.stringTimestamp = stringTimestamp;
		this.processType = processType;
		this.username = username;
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


	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}


	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
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

	/**
	 * 
	 * @return the timestamp in string format
	 */
//	public String getStringTimeStamp() {
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		return dateFormat.format(timestamp.getTime());
//	}
	

	/**
	 * @return the stringTimestamp
	 */
	public String getStringTimestamp() {
		return stringTimestamp;
	}

	/**
	 * @param stringTimestamp the stringTimestamp to set
	 */
	public void setStringTimestamp(String stringTimestamp) {
		this.stringTimestamp = stringTimestamp;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}



	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessBean)) return false;
        ProcessBean other = (ProcessBean) obj;
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

    		 ((this.getTimestamp() == null && other.getTimestamp() == null) || 
                     (this.getTimestamp() != null && this.getTimestamp().equals(other.getTimestamp()))) &&
                     
    		 ((this.getProcessType() == null && other.getProcessType() == null) || 
                     (this.getProcessType() != null && this.getProcessType().equals(other.getProcessType()))) &&        
             
             ((this.getProcessName() == null && other.getProcessName() == null) || 
                     (this.getProcessName() != null && this.getProcessName().equals(other.getProcessName())));    
    		 
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
        
        if (getTimestamp() != null) {
        	_hashCode += getTimestamp().hashCode();
        }
        
        if (getProcessName() != null) {
            _hashCode += getProcessName().hashCode();
        }
        
        if (getProcessType() != null) {
            _hashCode += getProcessType().hashCode();
        }
         
        __hashCodeCalc = false;
        return _hashCode;
    }
	

	// Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessBean"));
        
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
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stringTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "stringTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "processType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "username"));
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


}
