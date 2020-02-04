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
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 25/ott/2011 10.21.16
 */
public class CommunePackageVO implements java.io.Serializable {

	private static final long serialVersionUID = -6434243436397596083L;

	private String communeId;
	
	private String servicePackage;
	
	private Long serviceId;
	
	/**
	 * 
	 */
	public CommunePackageVO() {
		
	}
	
	/**
	 * @param communeId
	 * @param _package
	 */
	public CommunePackageVO(final String communeId, final String servicePackage) {
		this.setCommuneId(communeId);
		this.setServicePackage(servicePackage);
	}

	/**
	 * @return the communeId
	 */
	public final String getCommuneId() {
		return this.communeId;
	}

	/**
	 * @param communeId the communeId to set
	 */
	public final void setCommuneId(String communeId) {
		this.communeId = communeId;
	}

	/**
	 * @return the servicePackage
	 */
	public final String getServicePackage() {
		return this.servicePackage;
	}

	/**
	 * @param _package the servicePackage to set
	 */
	public final void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	/**
	 * @return the serviceId
	 */
	public final Long getServiceId() {
		return this.serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public final void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommunePackageVO)) return false;
        CommunePackageVO other = (CommunePackageVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCommuneId() == null && other.getCommuneId() == null) || 
             (this.getCommuneId() != null && this.getCommuneId().equalsIgnoreCase(other.getCommuneId()))) &&
             ((this.getServicePackage() == null && other.getServicePackage() == null) || 
                     (this.getServicePackage() != null && this.getServicePackage().equalsIgnoreCase(other.getServicePackage()))) &&  
                     ((this.getServiceId() == null && other.getServiceId() == null) || 
             (this.getServiceId() != null && this.getServiceId().compareTo(other.getServiceId()) == 0));
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
        if (getCommuneId() != null) {
            _hashCode += getCommuneId().hashCode();
        }
        if (getServicePackage() != null) {
            _hashCode += getServicePackage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommunePackageVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "CommunePackageVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeId"));
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
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
