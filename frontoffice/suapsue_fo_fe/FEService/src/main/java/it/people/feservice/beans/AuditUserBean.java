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

public class AuditUserBean implements Serializable {

	private int id;
	private java.lang.String sessionid;
	private java.util.Calendar pjp_time_stamp;
	private java.lang.String np_first_name;
	private java.lang.String np_last_name;
	private java.lang.String np_tax_code;
	private java.lang.String np_address;
	private java.lang.String np_e_address;

	private int anon_user;
	private java.lang.String commune_key;
	
	private java.lang.String tipo_qualifica;
	private java.lang.String descr_qualifica;
	private java.lang.String operatore;
	private java.lang.String richiedente;
	private java.lang.String titolare;
	
	
	public AuditUserBean(int id, String sessionid, Calendar pjp_time_stamp,
			String np_first_name, String np_last_name, String np_tax_code,
			String np_address, String np_e_address, int anon_user, String commune_key, 
			String tipo_qualifica, String descr_qualifica, String operatore, String richiedente, String titolare) {
		
		this.id = id;
		this.sessionid = sessionid;
		this.pjp_time_stamp = pjp_time_stamp;
		this.np_first_name = np_first_name;
		this.np_last_name = np_last_name;
		this.np_tax_code = np_tax_code;
		this.np_address = np_address;
		this.np_e_address = np_e_address;
		this.anon_user = anon_user;
		this.commune_key = commune_key;
		this.tipo_qualifica = tipo_qualifica;
		this.descr_qualifica = descr_qualifica;
		this.operatore = operatore;
		this.richiedente = richiedente;
		this.titolare = titolare;
	}

	public AuditUserBean() {
	}


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getSessionid() {
		return sessionid;
	}

	public void setSessionid(java.lang.String sessionid) {
		this.sessionid = sessionid;
	}

	public java.util.Calendar getPjp_time_stamp() {
		return pjp_time_stamp;
	}

	public void setPjp_time_stamp(java.util.Calendar pjp_time_stamp) {
		this.pjp_time_stamp = pjp_time_stamp;
	}

	public java.lang.String getNp_first_name() {
		return np_first_name;
	}

	public void setNp_first_name(java.lang.String np_first_name) {
		this.np_first_name = np_first_name;
	}

	public java.lang.String getNp_last_name() {
		return np_last_name;
	}

	public void setNp_last_name(java.lang.String np_last_name) {
		this.np_last_name = np_last_name;
	}

	public java.lang.String getNp_tax_code() {
		return np_tax_code;
	}

	public void setNp_tax_code(java.lang.String np_tax_code) {
		this.np_tax_code = np_tax_code;
	}

	public java.lang.String getNp_address() {
		return np_address;
	}

	public void setNp_address(java.lang.String np_address) {
		this.np_address = np_address;
	}

	public java.lang.String getNp_e_address() {
		return np_e_address;
	}

	public void setNp_e_address(java.lang.String np_e_address) {
		this.np_e_address = np_e_address;
	}

	public java.lang.String getTipo_qualifica() {
		return tipo_qualifica;
	}

	public void setTipo_qualifica(java.lang.String tipo_qualifica) {
		this.tipo_qualifica = tipo_qualifica;
	}

	public java.lang.String getDescr_qualifica() {
		return descr_qualifica;
	}

	public void setDescr_qualifica(java.lang.String descr_qualifica) {
		this.descr_qualifica = descr_qualifica;
	}

	public java.lang.String getOperatore() {
		return operatore;
	}

	public void setOperatore(java.lang.String operatore) {
		this.operatore = operatore;
	}

	public java.lang.String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(java.lang.String richiedente) {
		this.richiedente = richiedente;
	}

	
	public java.lang.String getTitolare() {
		return titolare;
	}

	public void setTitolare(java.lang.String titolare) {
		this.titolare = titolare;
	}
	public int getAnon_user() {
		return anon_user;
	}
	
	public void setAnon_user(int anon_user) {
		this.anon_user = anon_user;
	}
	


	public java.lang.String getCommune_key() {
		return commune_key;
	}

	public void setCommune_key(java.lang.String commune_key) {
		this.commune_key = commune_key;
	}

	/***/

	 private java.lang.Object __equalsCalc = null;
	    public synchronized boolean equals(java.lang.Object obj) {
	        if (!(obj instanceof AuditUserBean)) return false;
	        AuditUserBean other = (AuditUserBean) obj;
	        if (obj == null) return false;
	        if (this == obj) return true;
	        if (__equalsCalc != null) {
	            return (__equalsCalc == obj);
	        }
	        __equalsCalc = obj;
	        boolean _equals;
	        _equals = true && 
	            ((this.sessionid==null && other.getSessionid()==null) || 
	             (this.sessionid!=null &&
	              this.sessionid.equals(other.getSessionid()))) &&
	            ((this.pjp_time_stamp==null && other.getPjp_time_stamp()==null) || 
	     	     (this.pjp_time_stamp!=null &&
	     	      this.pjp_time_stamp.equals(other.getPjp_time_stamp()))) &&
	            ((this.np_first_name==null && other.getNp_first_name()==null) || 
	             (this.np_first_name!=null &&
	              this.np_first_name.equals(other.getNp_first_name()))) &&
	            ((this.np_last_name==null && other.getNp_last_name()==null) || 
	             (this.np_last_name!=null &&
	              this.np_last_name.equals(other.getNp_last_name()))) &&
	            ((this.np_tax_code==null && other.getNp_tax_code()==null) || 
	     	     (this.np_tax_code!=null &&
	     	      this.np_tax_code.equals(other.getNp_tax_code()))) &&
	     	    ((this.np_address==null && other.getNp_address()==null) || 
	     	     (this.np_address!=null &&
	     	      this.np_address.equals(other.getNp_address()))) &&
	     	    ((this.np_e_address==null && other.getNp_e_address()==null) || 
	     	     (this.np_e_address!=null &&
	     	      this.np_e_address.equals(other.getNp_e_address()))) &&	            
	            ((this.tipo_qualifica==null && other.getTipo_qualifica()==null) || 
	             (this.tipo_qualifica!=null &&
	         	  this.tipo_qualifica.equals(other.getTipo_qualifica()))) &&
		     	((this.descr_qualifica==null && other.getDescr_qualifica()==null) || 
		   	     (this.descr_qualifica!=null &&
		   	      this.descr_qualifica.equals(other.getDescr_qualifica()))) &&
		   	    ((this.operatore==null && other.getOperatore()==null) || 
			     (this.operatore!=null &&
			      this.operatore.equals(other.getOperatore()))) &&
	         	((this.richiedente==null && other.getRichiedente()==null) || 
	         	 (this.richiedente!=null &&
	         	  this.richiedente.equals(other.getRichiedente()))) &&
	         	((this.titolare==null && other.getTitolare()==null) || 
	         	 (this.titolare!=null &&
	         	  this.titolare.equals(other.getTitolare()))) &&
	         	((this.commune_key==null && other.getCommune_key()==null) || 
	         	 (this.commune_key!=null &&
	         	  this.commune_key.equals(other.getCommune_key()))) &&
	         	this.anon_user == other.getAnon_user() &&
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
	        if (getSessionid() != null) {
	            _hashCode += getSessionid().hashCode();
	        }
	        if (getPjp_time_stamp() != null) {
	        	_hashCode += getPjp_time_stamp().hashCode();
	        }
	        if (getNp_first_name() != null) {
	            _hashCode += getNp_first_name().hashCode();
	        }
	        if (getNp_last_name() != null) {
	        	_hashCode += getNp_last_name().hashCode();
	        }
	        if (getNp_tax_code() != null) {
	        	_hashCode += getNp_tax_code().hashCode();
	        }
	        if (getNp_address() != null) {
	            _hashCode += getNp_address().hashCode();
	        }
	        if (getNp_e_address() != null) {
	        	_hashCode += getNp_e_address().hashCode();
	        }
	        if (getTipo_qualifica() != null) {
	            _hashCode += getTipo_qualifica().hashCode();
	        }
	        if (getDescr_qualifica() != null) {
	        	_hashCode += getDescr_qualifica().hashCode();
	        }
	        if (getOperatore() != null) {
	        	_hashCode += getOperatore().hashCode();
	        }
	        if (getRichiedente() != null) {
	        	_hashCode += getRichiedente().hashCode();
	        }
	        if (getTitolare() != null) {
	        	_hashCode += getTitolare().hashCode();
	        }
	        _hashCode += getAnon_user();
	        _hashCode += getId();
	        __hashCodeCalc = false;
	        return _hashCode;
	    }

	    // Type metadata
	    private static org.apache.axis.description.TypeDesc typeDesc =
	        new org.apache.axis.description.TypeDesc(AuditUserBean.class, true);

	    static {
	        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditUserBean"));
	        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("sessionid");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "sessionid"));
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
	        elemField.setFieldName("np_first_name");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "np_first_name"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("np_last_name");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "np_last_name"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("np_tax_code");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "np_tax_code"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("np_address");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "np_address"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("np_e_address");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "np_e_address"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("tipo_qualifica");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "tipo_qualifica"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("descr_qualifica");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "descr_qualifica"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("operatore");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "operatore"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("richiedente");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "richiedente"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("titolare");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "titolare"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(true);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("anon_user");
	        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "anon_user"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
	        elemField.setNillable(false);
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
