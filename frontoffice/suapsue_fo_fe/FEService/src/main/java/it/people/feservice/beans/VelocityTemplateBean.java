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

public class VelocityTemplateBean implements java.io.Serializable {

	private static final long serialVersionUID = -5367396645098713584L;
	
	private String communeId;
	private String serviceId;
	private String servicePackage;
	private String key;
	private String value;
	private String description;
	
	public VelocityTemplateBean() {	
	}
	
	public VelocityTemplateBean(String communeId, String serviceId, String key, String value, String servicePackage, String description) {
		super();
		this.communeId = communeId;
		this.serviceId = serviceId;
		this.servicePackage = servicePackage;
		this.key = key;
		this.value = value;
		this.description = description;
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

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	private boolean __hashCodeCalc = false;
	@Override
	public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        
		final int prime = 31;
		int result = 1;
		result = prime * result + ((communeId == null) ? 0 : communeId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((servicePackage == null) ? 0 : servicePackage.hashCode());
		
		__hashCodeCalc = false;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	private java.lang.Object __equalsCalc = null;
	@Override
	public synchronized boolean equals(Object obj) {
		
		if (!(obj instanceof VelocityTemplateBean)) 
			return false;
		VelocityTemplateBean other = (VelocityTemplateBean) obj;
        if (this == obj) 
        	return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
			((this.communeId == null && other.getCommuneId() == null)||
				(this.communeId != null &&
					this.communeId.equals(other.getCommuneId()))) &&
        	
			((this.description == null && other.getDescription() == null)||
					(this.description != null &&
						this.description.equals(other.getDescription()))) &&

			((this.key == null && other.getKey() == null)||
					(this.key != null && this.key.equals(other.getKey()))) &&
			
			((this.serviceId == null && other.getServiceId() == null)||
					(this.serviceId != null &&
						this.serviceId.equals(other.getServiceId()))) &&
								
			((this.servicePackage == null && other.getServicePackage() == null)||
					(this.servicePackage != null &&
						this.servicePackage.equals(other.getServicePackage()))) &&
						
			((this.value == null && other.getValue() == null)||
					(this.value != null &&
						this.value.equals(other.getValue())));

        __equalsCalc = null;
        return _equals; 				
	}
	
	
	
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VelocityTemplateBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateBean"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "value"));
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
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "description"));
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
