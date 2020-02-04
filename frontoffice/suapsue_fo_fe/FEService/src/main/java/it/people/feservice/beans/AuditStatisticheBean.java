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

public class AuditStatisticheBean implements Serializable {

	private java.lang.String communeid;
	private java.lang.String nomeEnte;
	private java.lang.String process_name;
	private java.lang.String nomeServizio;
	private java.lang.String action_name;
	private java.util.Calendar audit_timestamp;

	private int risultati;

	public AuditStatisticheBean() {
	}

	public AuditStatisticheBean(
			java.lang.String communeid, 
			java.lang.String process_name,
			java.lang.String nomeEnte,
			java.lang.String nomeServizio, 
			java.lang.String action_name,
			Calendar audit_timestamp,
			int risultati
			) {
	
		this.communeid = communeid;
		this.process_name = process_name;
		this.nomeEnte = nomeEnte;
		this.nomeServizio = nomeServizio;
		this.action_name = action_name;
		this.audit_timestamp = audit_timestamp;
		this.risultati = risultati;
	}


	public java.lang.String getCommuneid() {
		return communeid;
	}

	public void setCommuneid(java.lang.String communeid) {
		this.communeid = communeid;
	}
	
	public java.lang.String getProcess_name() {
		return process_name;
	}

	public void setProcess_name(java.lang.String process_name) {
		this.process_name = process_name;
	}

	public java.lang.String getNomeEnte() {
		return nomeEnte;
	}

	public void setNomeEnte(java.lang.String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	
	public java.lang.String getNomeServizio() {
		return nomeServizio;
	}
	
	public void setNomeServizio(java.lang.String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}

	
	public java.lang.String getAction_name() {
		return action_name;
	}

	public void setAction_name(java.lang.String action_name) {
		this.action_name = action_name;
	}

	
	public java.util.Calendar getAudit_timestamp() {
		return audit_timestamp;
	}
	
	public void setAudit_timestamp(java.util.Calendar audit_timestamp) {
		this.audit_timestamp = audit_timestamp;
	}

    
	public int getRisultati() {
		return risultati;
	}

	public void setRisultati(int risultati) {
		this.risultati = risultati;
	}
	
	
	/***/
	
    
	
	 private java.lang.Object __equalsCalc = null;
	    public synchronized boolean equals(java.lang.Object obj) {
	        if (!(obj instanceof AuditStatisticheBean)) return false;
	        AuditStatisticheBean other = (AuditStatisticheBean) obj;
	        if (obj == null) return false;
	        if (this == obj) return true;
	        if (__equalsCalc != null) {
	            return (__equalsCalc == obj);
	        }
	        __equalsCalc = obj;
	        boolean _equals;
	        _equals = true && 
	            ((this.communeid==null && other.getCommuneid()==null) || 
	             (this.communeid!=null &&
	              this.communeid.equals(other.getCommuneid()))) &&
	            ((this.process_name==null && other.getProcess_name()==null) || 
   	         	 (this.process_name!=null &&
   	         	  this.process_name.equals(other.getProcess_name()))) &&
	            ((this.nomeEnte==null && other.getNomeEnte()==null) || 
	     	     (this.nomeEnte!=null &&
	     	      this.nomeEnte.equals(other.getNomeEnte()))) &&
	     	    ((this.nomeServizio==null && other.getNomeServizio()==null) || 
	     	     (this.nomeServizio!=null &&
	     	      this.nomeServizio.equals(other.getNomeServizio()))) &&
	         	((this.action_name==null && other.getAction_name()==null) || 
	         	 (this.action_name!=null &&
	         	  this.action_name.equals(other.getAction_name()))) &&
	         	((this.audit_timestamp==null && other.getAudit_timestamp()==null) || 
     			 (this.audit_timestamp!=null &&
 				  this.audit_timestamp.equals(other.getAudit_timestamp()))) &&
 		          this.risultati == other.getRisultati();

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
	        if (getCommuneid() != null) {
	            _hashCode += getCommuneid().hashCode();
	        }
	        if (getProcess_name() != null) {
	        	_hashCode += getProcess_name().hashCode();
	        }
	        if (getNomeEnte() != null) {
	        	_hashCode += getNomeEnte().hashCode();
	        }
	        if (getNomeServizio() != null) {
	        	_hashCode += getNomeServizio().hashCode();
	        }
	        if (getAction_name() != null) {
	        	_hashCode += getAction_name().hashCode();
	        }
	        if (getAudit_timestamp() != null) {
	        	_hashCode += getAudit_timestamp().hashCode();
	        }
	        _hashCode += getRisultati();
	        __hashCodeCalc = false;
	        return _hashCode;
	    }

	    // Type metadata
	    private static org.apache.axis.description.TypeDesc typeDesc =
	        new org.apache.axis.description.TypeDesc(AuditStatisticheBean.class, true);

	    static {
	        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditStatisticheBean"));
	        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("communeid");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeid"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("process_name");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "process_name"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("nomeEnte");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "nomeEnte"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("nomeServizio");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "nomeServizio"));
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
	        elemField.setFieldName("audit_timestamp");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "audit_timestamp"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("risultati");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "risultati"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
