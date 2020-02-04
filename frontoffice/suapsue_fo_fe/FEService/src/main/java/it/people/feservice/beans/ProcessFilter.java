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
 * This bean contains a process (also known as "Pratica") filter  
 * Used as param to call web service and fetch Processes
 * 
 * @author Andrea Piemontese
 * @created 10/aset/2012
 */
public class ProcessFilter implements java.io.Serializable {
    
	
	private static final long serialVersionUID = 5540657112995619932L;
	
	private String fromData;
	private String toData; 
	
	//older than 
	private int olderThanDays;
	
	//Process type filters
	private int onlyNotSubmittable;
	private int onlyPending;
	private int onlySubmitted;
	

	public ProcessFilter() {
    	
    }
	
	
	public ProcessFilter(String fromData, String toData, int olderThanDays,
			int onlyNotSubmittable, int onlyPending, int onlySubmitted) {
		super();
		this.fromData = fromData;
		this.toData = toData;
		this.olderThanDays = olderThanDays;
		this.onlyNotSubmittable = onlyNotSubmittable;
		this.onlyPending = onlyPending;
		this.onlySubmitted = onlySubmitted;
	}

	
	


	/**
	 * @return the fromData
	 */
	public String getFromData() {
		return fromData;
	}


	/**
	 * @param fromData the fromData to set
	 */
	public void setFromData(String fromData) {
		this.fromData = fromData;
	}


	/**
	 * @return the toData
	 */
	public String getToData() {
		return toData;
	}


	/**
	 * @param toData the toData to set
	 */
	public void setToData(String toData) {
		this.toData = toData;
	}
	
	
    
    /**
	 * @return the olderThanDays
	 */
	public int getOlderThanDays() {
		return olderThanDays;
	}



	/**
	 * @param olderThanDays the olderThanDays to set
	 */
	public void setOlderThanDays(int olderThanDays) {
		this.olderThanDays = olderThanDays;
	}



	/**
	 * @return the onlyNotSubmittable
	 */
	public int getOnlyNotSubmittable() {
		return onlyNotSubmittable;
	}



	/**
	 * @param onlyNotSubmittable the onlyNotSubmittable to set
	 */
	public void setOnlyNotSubmittable(int onlyNotSubmittable) {
		this.onlyNotSubmittable = onlyNotSubmittable;
	}



	/**
	 * @return the onlyPending
	 */
	public int getOnlyPending() {
		return onlyPending;
	}



	/**
	 * @param onlyPending the onlyPending to set
	 */
	public void setOnlyPending(int onlyPending) {
		this.onlyPending = onlyPending;
	}



	/**
	 * @return the onlySubmitted
	 */
	public int getOnlySubmitted() {
		return onlySubmitted;
	}



	/**
	 * @param onlySubmitted the onlySubmitted to set
	 */
	public void setOnlySubmitted(int onlySubmitted) {
		this.onlySubmitted = onlySubmitted;
	}


	


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessFilter)) return false;
        ProcessFilter other = (ProcessFilter) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getFromData() == null && other.getFromData() == null) || 
            		(this.getFromData() != null && this.getFromData().equals(other.getFromData()))) &&
             
             ((this.getToData() == null && other.getToData() == null) || 
            		 (this.getToData() != null && this.getToData().equals(other.getToData()))) &&
       
    		this.getOlderThanDays() == other.getOlderThanDays() &&
    		
	        this.getOnlyNotSubmittable() == other.getOnlyNotSubmittable() &&
	        this.getOnlyPending() == other.getOnlyPending() &&
	        this.getOnlySubmitted() == other.getOnlySubmitted();
    		 
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

        if (getFromData() != null) {
            _hashCode += getFromData().hashCode();
        }
        
        if (getToData() != null) {
            _hashCode += getToData().hashCode();
        }
        
        _hashCode += this.getOlderThanDays();
        
        _hashCode += this.getOnlyPending();
		_hashCode += this.getOnlySubmitted();
		_hashCode += this.getOnlyNotSubmittable();
        
        __hashCodeCalc = false;
        return _hashCode;
    }
	
	

	// Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessFilter.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorFilter"));
        
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "fromData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "toData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
                
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("olderThanDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "olderThanDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);

        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlySubmitted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "onlySubmitted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlyNotSubmittable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "onlyNotSubmittable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlyPending");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "onlyPending"));
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
