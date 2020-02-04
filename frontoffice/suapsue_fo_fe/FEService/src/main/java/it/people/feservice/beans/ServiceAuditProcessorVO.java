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

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica
 * 
 * <br/> This class represents the Value Object for operations with service audit processors
 * 
 * <br/> 13-07-2012
 */
public class ServiceAuditProcessorVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String auditProcessor;
	
	private String servicePackage;
	
	private int active;
	
	private String communeId;
	
	public ServiceAuditProcessorVO() {
		
	}
	
	public ServiceAuditProcessorVO(String auditProcessor,
			String servicePackage, int active, String communeId) {
		super();
		this.auditProcessor = auditProcessor;
		this.servicePackage = servicePackage;
		this.active = active;
		this.communeId = communeId;
	}


	/**
	 * @return the auditProcessor
	 */
	public String getAuditProcessor() {
		return auditProcessor;
	}

	/**
	 * @param auditProcessor the auditProcessor to set
	 */
	public void setAuditProcessor(String auditProcessor) {
		this.auditProcessor = auditProcessor;
	}

	/**
	 * @return the servicePackage
	 */
	public String getServicePackage() {
		return servicePackage;
	}

	/**
	 * @param servicePackage the servicePackage to set
	 */
	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * @return the communeId
	 */
	public String getCommuneId() {
		return communeId;
	}

	/**
	 * @param communeId the communeId to set
	 */
	public void setCommuneId(String communeId) {
		this.communeId = communeId;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceAuditProcessorVO)) return false;
        
        ServiceAuditProcessorVO other = (ServiceAuditProcessorVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
       
        boolean _equals;
        _equals = true && 

    				((this.getAuditProcessor() == null && other.getAuditProcessor()  == null) || 
    						(this.getAuditProcessor() != null && this.getAuditProcessor().equalsIgnoreCase(other.getAuditProcessor()))) &&
    				
    				((this.getCommuneId() == null && other.getCommuneId() == null) || 
            				(this.getCommuneId()  != null && this.getCommuneId() .equalsIgnoreCase(other.getCommuneId()))) &&		
					
            		(this.getActive() == other.getActive()) &&		
            								
    				((this.getServicePackage() == null && other.getServicePackage() == null) || 
    						(this.getServicePackage() != null && this.getServicePackage().equalsIgnoreCase(other.getServicePackage())));
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
        _hashCode = _hashCode + getActive();
        
        if (getAuditProcessor() != null) {
        	_hashCode = _hashCode + getAuditProcessor().hashCode();
        }
        
        if (getCommuneId() != null) {
        	_hashCode = _hashCode + getCommuneId().hashCode();
        }

        if (getServicePackage() != null) {
        	_hashCode = _hashCode + getServicePackage().hashCode();
        }
        
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceAuditProcessorVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceAuditProcessorVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auditProcessor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "auditProcessor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicePackage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "servicePackage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("active");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "active"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeId"));
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
