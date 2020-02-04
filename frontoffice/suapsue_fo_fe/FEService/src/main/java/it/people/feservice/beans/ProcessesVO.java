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

import it.people.feservice.beans.interfaces.IpagedArrayResult;


/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica - Bologna
 * 10/set/2012
 * 
 */
public class ProcessesVO implements java.io.Serializable, IpagedArrayResult {


	private static final long serialVersionUID = 8852008275564508265L;

	private int totalResultCount = 0;
	
	private ProcessBean[] processes;

	public ProcessesVO() {
		
	}
	
	public ProcessesVO(int totalResultCount, ProcessBean[] indicators) {
		super();
		this.totalResultCount = totalResultCount;
		this.processes = indicators;
	}	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] getPartialResult() {
		
		return (T[]) getProcesses();
	}
	


	/**
	 * @return the totalResultCount
	 */
	public int getTotalResultCount() {
		return totalResultCount;
	}
	


	/**
	 * @param totalResultCount the totalResultCount to set
	 */
	public void setTotalResultCount(int totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	/**
	 * @return the indicators
	 */
	public ProcessBean[] getProcesses() {
		return processes;
	}

	/**
	 * @param processes the indicators to set
	 */
	public void setProcesses(ProcessBean[] processes) {
		this.processes = processes;
	}
	
	
	
	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessesVO)) return false;
        ProcessesVO other = (ProcessesVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getProcesses() == null && other.getProcesses()  == null) || 
             (this.getProcesses() != null && this.getProcesses().equals((other.getProcesses()))) &&
             
             (this.getTotalResultCount() == other.getTotalResultCount()));
        
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
        if (getProcesses() != null) {
            _hashCode += getProcesses().hashCode();
        }

        _hashCode += getTotalResultCount();
      
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessesVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessesVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalResultCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "totalResultCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indicators");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "indicators"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessBean"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://feservice.people.it/", "item"));
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
