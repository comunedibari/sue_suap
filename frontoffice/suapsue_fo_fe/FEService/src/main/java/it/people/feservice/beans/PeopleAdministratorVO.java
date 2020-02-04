/**
 * 
 */
package it.people.feservice.beans;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 05/ott/2012 08:30:02
 */
public class PeopleAdministratorVO implements Serializable {

	private static final long serialVersionUID = 3071127766507309065L;

	public String userId;
	
	public String userName;
	
	public String eMail;
	
	public String eMailsReceiverTypesFlags;
	
	public String[] allowedCommune;
	
	/**
	 * 
	 */
	public PeopleAdministratorVO() {
		this.setUserId("");
		this.setUserName("");
		this.seteMail("");
		this.seteMailsReceiverTypesFlags("");
		this.setAllowedCommune(null);
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @param eMailsReceiverTypesFlags the eMailsReceiverTypesFlags to set
	 */
	public void seteMailsReceiverTypesFlags(String eMailsReceiverTypesFlags) {
		this.eMailsReceiverTypesFlags = eMailsReceiverTypesFlags;
	}

	/**
	 * @param allowedCommune the allowedCommune to set
	 */
	public void setAllowedCommune(String[] allowedCommune) {
		this.allowedCommune = allowedCommune;
	}

	/**
	 * @return the userId
	 */
	public final String getUserId() {
		return this.userId;
	}

	/**
	 * @return the userName
	 */
	public final String getUserName() {
		return this.userName;
	}

	/**
	 * @return the eMail
	 */
	public final String geteMail() {
		return this.eMail;
	}

	/**
	 * @return the eMailsReceiverTypesFlags
	 */
	public final String geteMailsReceiverTypesFlags() {
		return this.eMailsReceiverTypesFlags;
	}

	/**
	 * @return the allowedCommune
	 */
	public final String[] getAllowedCommune() {
		return this.allowedCommune;
	}
	

	
	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PeopleAdministratorVO)) return false;
        PeopleAdministratorVO other = (PeopleAdministratorVO) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (geteMail() != null) {
        	_hashCode += geteMail().hashCode();
        }
        if (geteMailsReceiverTypesFlags() != null) {
        	_hashCode += geteMailsReceiverTypesFlags().hashCode();
        }
        if (getAllowedCommune() != null && getAllowedCommune().length > 0) {
        	for (int index = 0; index < getAllowedCommune().length; index++) {
        		String commune = getAllowedCommune()[index];
        		if (commune != null && !StringUtils.isBlank(commune)) {
        			_hashCode += commune.hashCode();
        		}
        	}
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
    
    
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PeopleAdministratorVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "PeopleAdministratorVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "eMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eMailsReceiverTypesFlags");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "eMailsReceiverTypesFlags"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedCommune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "allowedCommune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_soapenc_string"));
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
