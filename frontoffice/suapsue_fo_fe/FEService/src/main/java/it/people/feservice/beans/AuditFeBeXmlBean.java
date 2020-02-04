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
import java.util.Calendar;

public class AuditFeBeXmlBean implements Serializable {

	private int id; 
	private java.lang.String xmlIn;
	private java.lang.String xmlOut; 
	private java.util.Calendar time_stamp; 
	private java.lang.String userid;

	public AuditFeBeXmlBean() {
	}

	public AuditFeBeXmlBean(int id, java.lang.String audit_users_ref, java.lang.String communeid, java.lang.String xmlOut,
			Calendar pjp_time_stamp, /*Timestamp time_stamp,*/ Calendar time_stamp,
			java.lang.String process_name, java.lang.String userid, java.lang.String xmlIn, int include_audit_febe_xml) {
		
		this.id = id;
		this.xmlIn = xmlIn;
		this.xmlOut = xmlOut;
		this.time_stamp = time_stamp;
		this.userid = userid;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
	public java.lang.String getXmlOut() {
		return xmlOut;
	}

	public void setXmlOut(java.lang.String xmlOut) {
		this.xmlOut = xmlOut;
	}

	
	public java.util.Calendar getTime_stamp() {
		return time_stamp;
	}
	
	public void setTime_stamp(java.util.Calendar time_stamp) {
		this.time_stamp = time_stamp;
	}


	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getXmlIn() {
		return xmlIn;
	}

	public void setXmlIn(java.lang.String xmlIn) {
		this.xmlIn = xmlIn;
	}
	
	
	 private java.lang.Object __equalsCalc = null;
	    public synchronized boolean equals(java.lang.Object obj) {
	        if (!(obj instanceof AuditFeBeXmlBean)) return false;
	        AuditFeBeXmlBean other = (AuditFeBeXmlBean) obj;
	        if (obj == null) return false;
	        if (this == obj) return true;
	        if (__equalsCalc != null) {
	            return (__equalsCalc == obj);
	        }
	        __equalsCalc = obj;
	        boolean _equals;
	        _equals = true && 
	            ((this.xmlOut==null && other.getXmlOut()==null) || 
	     	     (this.xmlOut!=null &&
	     	      this.xmlOut.equals(other.getXmlOut()))) &&
	            ((this.time_stamp==null && other.getTime_stamp()==null) || 
	             (this.time_stamp!=null &&
	         	  this.time_stamp.equals(other.getTime_stamp()))) &&
	         	((this.userid==null && other.getUserid()==null) || 
	         	 (this.userid!=null &&
	         	  this.userid.equals(other.getUserid()))) &&
	         	((this.xmlIn==null && other.getXmlIn()==null) || 
	         	 (this.xmlIn!=null &&
	         	  this.xmlIn.equals(other.getXmlIn()))) &&
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
	        if (getXmlOut() != null) {
	        	_hashCode += getXmlOut().hashCode();
	        }
	        if (getTime_stamp() != null) {
	        	_hashCode += getTime_stamp().hashCode();
	        }
	        if (getUserid() != null) {
	            _hashCode += getUserid().hashCode();
	        }
	        if (getXmlIn() != null) {
	        	_hashCode += getXmlIn().hashCode();
	        }
	        _hashCode += getId();
	        __hashCodeCalc = false;
	        return _hashCode;
	    }

	    // Type metadata
	    private static org.apache.axis.description.TypeDesc typeDesc =
	        new org.apache.axis.description.TypeDesc(AuditFeBeXmlBean.class, true);

	    static {
	        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditFeBeXmlBean"));
	        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	        
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("xmlIn");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "xmlIn"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("xmlOut");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "xmlOut"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("time_stamp");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "time_stamp"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("userid");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "userid"));
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
