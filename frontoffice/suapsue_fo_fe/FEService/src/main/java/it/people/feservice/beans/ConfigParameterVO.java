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


public class ConfigParameterVO implements java.io.Serializable {
	
    private java.lang.String servicePackage;
    private java.lang.String communeId;
    private it.people.feservice.beans.ConfigParameter configParameter;
    private it.people.feservice.beans.ConfigParameter oldConfigParameter;
    private java.lang.String action;
    
    public ConfigParameterVO(){
    }
    
	public ConfigParameterVO(java.lang.String servicePackage, java.lang.String communeId,
			it.people.feservice.beans.ConfigParameter configParameter,
			it.people.feservice.beans.ConfigParameter oldConfigParameter,
			java.lang.String action) {
		this.servicePackage = servicePackage;
		this.communeId = communeId;
		this.configParameter = configParameter;
		this.oldConfigParameter = configParameter;
		this.action = action;
	}


	public java.lang.String getServicePackage() {
		return servicePackage;
	}
	public void setServicePackage(java.lang.String servicePackage) {
		this.servicePackage = servicePackage;
	}
	public java.lang.String getCommuneId() {
		return communeId;
	}
	public void setCommuneId(java.lang.String communeId) {
		this.communeId = communeId;
	}
	public it.people.feservice.beans.ConfigParameter getConfigParameter() {
		return configParameter;
	}
	public void setConfigParameter(
			it.people.feservice.beans.ConfigParameter configParameter) {
		this.configParameter = configParameter;
	}    
	public it.people.feservice.beans.ConfigParameter getOldConfigParameter() {
		return oldConfigParameter;
	}
	public void setOldConfigParameter(
			it.people.feservice.beans.ConfigParameter oldConfigParameter) {
		this.oldConfigParameter = oldConfigParameter;
	}
    public java.lang.String getAction() {
		return action;
	}
	public void setAction(java.lang.String action) {
		this.action = action;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfigParameterVO)) return false;
        ConfigParameterVO other = (ConfigParameterVO) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            
            ((this.servicePackage==null && other.getServicePackage()==null) || 
             (this.servicePackage!=null &&
              this.servicePackage.equals(other.getServicePackage()))) &&

            ((this.communeId==null && other.getCommuneId()==null) || 
             (this.communeId!=null &&
              this.communeId.equals(other.getCommuneId()))) &&

            ((this.configParameter==null && other.getConfigParameter()==null) || 
             (this.configParameter!=null &&
              this.configParameter.equals(other.getConfigParameter()))) &&
              
            ((this.oldConfigParameter==null && other.getOldConfigParameter()==null) || 
        	 (this.oldConfigParameter!=null &&
        	  this.oldConfigParameter.equals(other.getOldConfigParameter()))) &&

            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction())));
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
        if (getServicePackage() != null) {
            _hashCode += getServicePackage().hashCode();
        }
        if (getCommuneId() != null) {
            _hashCode += getCommuneId().hashCode();
        }
        if (getConfigParameter() != null) {
        	_hashCode += getConfigParameter().hashCode();
        }
        if (getOldConfigParameter() != null) {
        	_hashCode += getOldConfigParameter().hashCode();
        }
        if (getAction() != null) {
        	_hashCode += getAction().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
    
    
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConfigParameterVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameterVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicePackage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "servicePackage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configParameter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "configParameter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameter"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldConfigParameter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "oldConfigParameter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameter"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "action"));
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
