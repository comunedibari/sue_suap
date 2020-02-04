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
 * <br/> This class represents the Value Object for operations with Tablevalues properties
 * 
 * <br/> 26-06-2012
 */
public class TableValuePropertyVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String value;
	
	private String oldValue;
	
	private int tableValueRef;
	
	private String action;
	
	public TableValuePropertyVO() {
		
	}
	
	public TableValuePropertyVO(int id, String oldValue, String value, int tableValueRef, String action) {
		this.id = id;
		this.oldValue = oldValue;
		this.value = value;
		this.tableValueRef = tableValueRef;
		this.action = action;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the tableValueRef
	 */
	public int getTableValueRef() {
		return tableValueRef;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param tableValueRef the tableValueRef to set
	 */
	public void setTableValueRef(int tableValueRef) {
		this.tableValueRef = tableValueRef;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TableValuePropertyVO)) return false;
        
        TableValuePropertyVO other = (TableValuePropertyVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
       
        boolean _equals;
        _equals = true && 
        		((this.getId() == other.getId()) &&
        		
        				((this.getValue() == null && other.getValue() == null) || 
        						(this.getValue() != null && this.getValue().equalsIgnoreCase(other.getValue()))) &&
        				
        				((this.getOldValue() == null && other.getOldValue() == null) || 
                				(this.getOldValue() != null && this.getOldValue().equalsIgnoreCase(other.getOldValue()))) &&		
        						
        				(this.getTableValueRef() == other.getTableValueRef()) &&
        				
        				((this.getAction() == null && other.getAction() == null) || 
        						(this.getAction() != null && this.getAction().equalsIgnoreCase(other.getAction()))));
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
        _hashCode = _hashCode + getId();
        
        if (getOldValue() != null) {
        	_hashCode = _hashCode + getOldValue().hashCode();
        }
        
        if (getValue() != null) {
        	_hashCode = _hashCode + getValue().hashCode();
        }
        _hashCode = _hashCode + getTableValueRef();
        if (getAction() != null) {
        	_hashCode = _hashCode + getAction().hashCode();
        }
        
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TableValuePropertyVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "TableValuePropertyVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldvValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "oldValue"));
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
        elemField.setFieldName("tableValueRef");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "tableValueRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
