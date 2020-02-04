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

import java.io.Serializable;

public class AuditConversationsBean implements Serializable {

	private int id;
	private java.lang.String audit_users_ref;
	private java.lang.String communeid;
	private java.lang.String tax_code;
	private java.util.Date pjp_time_stamp;
//	private java.sql.Timestamp audit_timestamp;
//	private java.util.Date audit_timestamp;
	private java.util.Calendar audit_timestamp;
	private java.util.Date audit_timestamp_date;
	private java.lang.String process_name;
	private java.lang.String action_name;
	private java.lang.String message;
	private java.lang.String audit_users_link;
	private int include_audit_febe_xml;
	private java.lang.String audit_users_accr;

	public AuditConversationsBean() {
	}

	public AuditConversationsBean(int id, java.lang.String audit_users_ref, java.lang.String communeid, java.lang.String tax_code,
			java.util.Date pjp_time_stamp, java.util.Calendar audit_timestamp, /* java.sql.Timestamp audit_timestamp, */ /*java.util.Date audit_timestamp,*/
			java.lang.String process_name, java.lang.String action_name, java.lang.String message, int include_audit_febe_xml, java.lang.String audit_users_accr) {
		
		this.id = id;
		this.audit_users_ref = audit_users_ref;
		this.communeid = communeid;
		this.tax_code = tax_code;
		this.pjp_time_stamp = pjp_time_stamp;
		this.audit_timestamp = audit_timestamp;
		this.process_name = process_name;
		this.action_name = action_name;
		this.message = message;
		this.include_audit_febe_xml = include_audit_febe_xml;
		this.audit_users_accr = audit_users_accr;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
	public java.lang.String getAudit_users_ref() {
		return audit_users_ref;
	}

	public void setAudit_users_ref(java.lang.String audit_users_ref) {
		this.audit_users_ref = audit_users_ref;
	}

	public java.lang.String getCommuneid() {
		return communeid;
	}

	public void setCommuneid(java.lang.String communeid) {
		this.communeid = communeid;
	}

	public java.lang.String getTax_code() {
		return tax_code;
	}

	public void setTax_code(java.lang.String tax_code) {
		this.tax_code = tax_code;
	}

	public java.util.Date getPjp_time_stamp() {
		return pjp_time_stamp;
	}

	public void setPjp_time_stamp(java.util.Date pjp_time_stamp) {
		this.pjp_time_stamp = pjp_time_stamp;
	}

	public java.util.Calendar getAudit_timestamp() {
		return audit_timestamp;
	}

	public void setAudit_timestamp(java.util.Calendar audit_timestamp) {
		this.audit_timestamp = audit_timestamp;
	}
//	
//	public java.sql.Timestamp getAudit_timestamp() {
//		return audit_timestamp;
//	}
//
//	public void setAudit_timestamp(java.sql.Timestamp audit_timestamp) {
//		this.audit_timestamp = audit_timestamp;
//	}
	
	public java.util.Date getAudit_timestamp_date() {
		return audit_timestamp_date;
	}
	
	public void setAudit_timestamp_date(java.util.Date audit_timestamp_date) {
		this.audit_timestamp_date = audit_timestamp_date;
	}

	public java.lang.String getProcess_name() {
		return process_name;
	}

	public void setProcess_name(java.lang.String process_name) {
		this.process_name = process_name;
	}

	public java.lang.String getAction_name() {
		return action_name;
	}

	public void setAction_name(java.lang.String action_name) {
		this.action_name = action_name;
	}

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String message) {
		this.message = message;
	}
	
	/***/
    
	public java.lang.String getAudit_users_link() {
		return audit_users_link;
	}

	public void setAudit_users_link(java.lang.String audit_users_link) {
		this.audit_users_link = audit_users_link;
	}
	
	/***/
    
	public int getInclude_audit_febe_xml() {
		return include_audit_febe_xml;
	}

	public void setInclude_audit_febe_xml(int include_audit_febe_xml) {
		this.include_audit_febe_xml = include_audit_febe_xml;
	}
	
	/***/
	
    
	public java.lang.String getAudit_users_accr() {
		return audit_users_accr;
	}

	public void setAudit_users_accr(java.lang.String audit_users_accr) {
		this.audit_users_accr = audit_users_accr;
	}
	
	 private java.lang.Object __equalsCalc = null;
	    public synchronized boolean equals(java.lang.Object obj) {
	        if (!(obj instanceof AuditConversationsBean)) return false;
	        AuditConversationsBean other = (AuditConversationsBean) obj;
	        if (obj == null) return false;
	        if (this == obj) return true;
	        if (__equalsCalc != null) {
	            return (__equalsCalc == obj);
	        }
	        __equalsCalc = obj;
	        boolean _equals;
	        _equals = true && 
	            ((this.audit_users_ref==null && other.getAudit_users_ref()==null) || 
	             (this.audit_users_ref!=null &&
	              this.audit_users_ref.equals(other.getAudit_users_ref()))) &&
	            ((this.communeid==null && other.getCommuneid()==null) || 
	             (this.communeid!=null &&
	              this.communeid.equals(other.getCommuneid()))) &&
	            ((this.tax_code==null && other.getTax_code()==null) || 
	     	     (this.tax_code!=null &&
	     	      this.tax_code.equals(other.getTax_code()))) &&
	            ((this.pjp_time_stamp==null && other.getPjp_time_stamp()==null) || 
	             (this.pjp_time_stamp!=null &&
	              this.pjp_time_stamp.equals(other.getPjp_time_stamp()))) &&
	            ((this.audit_timestamp==null && other.getAudit_timestamp()==null) || 
	             (this.audit_timestamp!=null &&
	         	  this.audit_timestamp.equals(other.getAudit_timestamp()))) &&
	         	((this.process_name==null && other.getProcess_name()==null) || 
	         	 (this.process_name!=null &&
	         	  this.process_name.equals(other.getProcess_name()))) &&
	         	((this.action_name==null && other.getAction_name()==null) || 
	         	 (this.action_name!=null &&
	         	  this.action_name.equals(other.getAction_name()))) &&
	         	((this.message==null && other.getMessage()==null) || 
	         	 (this.message!=null &&
	         	  this.message.equals(other.getMessage()))) &&
	            ((this.audit_users_accr==null && other.getAudit_users_accr()==null) || 
   	             (this.audit_users_accr!=null &&
   	              this.audit_users_accr.equals(other.getAudit_users_accr()))) &&
	            this.include_audit_febe_xml == other.getInclude_audit_febe_xml() &&
	            this.id == other.getId();
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
	        if (getAudit_users_ref() != null) {
	            _hashCode += getAudit_users_ref().hashCode();
	        }
	        if (getCommuneid() != null) {
	            _hashCode += getCommuneid().hashCode();
	        }
	        if (getTax_code() != null) {
	        	_hashCode += getTax_code().hashCode();
	        }
	        if (getPjp_time_stamp() != null) {
	        	_hashCode += getPjp_time_stamp().hashCode();
	        }
	        if (getAudit_timestamp() != null) {
	        	_hashCode += getAudit_timestamp().hashCode();
	        }
	        if (getProcess_name() != null) {
	            _hashCode += getProcess_name().hashCode();
	        }
	        if (getAction_name() != null) {
	            _hashCode += getAction_name().hashCode();
	        }
	        if (getMessage() != null) {
	        	_hashCode += getMessage().hashCode();
	        }
	        if (getAudit_users_accr() != null) {
	            _hashCode += getAudit_users_accr().hashCode();
	        }
	        _hashCode += getInclude_audit_febe_xml();
	        _hashCode += getId();
	        __hashCodeCalc = false;
	        return _hashCode;
	    }

	    // Type metadata
	    private static org.apache.axis.description.TypeDesc typeDesc =
	        new org.apache.axis.description.TypeDesc(AuditConversationsBean.class, true);

	    static {
	        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
	        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("audit_users_ref");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "audit_users_ref"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("communeid");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeid"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("tax_code");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "tax_code"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("pjp_time_stamp");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "pjp_time_stamp"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("audit_timestamp");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "audit_timestamp"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("process_name");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "process_name"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("action_name");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "action_name"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("message");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "message"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("include_audit_febe_xml");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "include_audit_febe_xml"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("audit_users_accr");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "audit_users_accr"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("id");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "id"));
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
