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
 * This bean contains an indicator filter. 
 * Used as param to call web service and get Indcators
 * 
 * @author Andrea Piemontese
 * @created 3/ago/2012
 */
public class IndicatorFilter implements java.io.Serializable {
    
	
	private static final long serialVersionUID = 7540647712095639971L;
	
	private String fromData;
	private String toData; 
	
	//Office Hours for query
	private String officeMorningOpeningTime;
	private String officeMorningClosingTime;
	
	//Not used for now
	private String officeAfternoonOpeningTime;
	private String officeAfternoonClosingTime;

	public IndicatorFilter() {
    	
    }
	

	public IndicatorFilter(String fromData, String toData,
			String officeMorningOpeningTime, String officeMorningClosingTime,
			String officeAfternoonOpeningTime,
			String officeAfternoonClosingTime) {
		
		this.fromData = fromData;
		this.toData = toData;
		this.officeMorningOpeningTime = officeMorningOpeningTime;
		this.officeMorningClosingTime = officeMorningClosingTime;
		
		this.officeAfternoonOpeningTime = officeAfternoonOpeningTime;
		this.officeAfternoonClosingTime = officeAfternoonClosingTime;
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
	 * @return the officeMorningOpeningTime
	 */
	public String getOfficeMorningOpeningTime() {
		return officeMorningOpeningTime;
	}


	/**
	 * @param officeMorningOpeningTime the officeMorningOpeningTime to set
	 */
	public void setOfficeMorningOpeningTime(String officeMorningOpeningTime) {
		this.officeMorningOpeningTime = officeMorningOpeningTime;
	}


	/**
	 * @return the officeMorningClosingTime
	 */
	public String getOfficeMorningClosingTime() {
		return officeMorningClosingTime;
	}


	/**
	 * @param officeMorningClosingTime the officeMorningClosingTime to set
	 */
	public void setOfficeMorningClosingTime(String officeMorningClosingTime) {
		this.officeMorningClosingTime = officeMorningClosingTime;
	}


	/**
	 * @return the officeAfternoonOpeningTime
	 */
	public String getOfficeAfternoonOpeningTime() {
		return officeAfternoonOpeningTime;
	}


	/**
	 * @param officeAfternoonOpeningTime the officeAfternoonOpeningTime to set
	 */
	public void setOfficeAfternoonOpeningTime(String officeAfternoonOpeningTime) {
		this.officeAfternoonOpeningTime = officeAfternoonOpeningTime;
	}


	/**
	 * @return the officeAfternoonClosingTime
	 */
	public String getOfficeAfternoonClosingTime() {
		return officeAfternoonClosingTime;
	}


	/**
	 * @param officeAfternoonClosingTime the officeAfternoonClosingTime to set
	 */
	public void setOfficeAfternoonClosingTime(String officeAfternoonClosingTime) {
		this.officeAfternoonClosingTime = officeAfternoonClosingTime;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IndicatorFilter)) return false;
        IndicatorFilter other = (IndicatorFilter) obj;
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
		            		 
             ((this.getOfficeMorningOpeningTime() == null && other.getOfficeMorningOpeningTime() == null) || 
            		 (this.getOfficeMorningOpeningTime() != null && this.getOfficeMorningOpeningTime().equals(other.getOfficeMorningOpeningTime()))) &&		 
            		 
    		 ((this.getOfficeMorningClosingTime() == null && other.getOfficeMorningClosingTime() == null) || 
            		 (this.getOfficeMorningClosingTime() != null && this.getOfficeMorningClosingTime().equals(other.getOfficeMorningClosingTime()))) &&		 
            		 
    		 ((this.getOfficeAfternoonOpeningTime() == null && other.getOfficeAfternoonOpeningTime() == null) || 
            		 (this.getOfficeAfternoonOpeningTime() != null && this.getOfficeAfternoonOpeningTime().equals(other.getOfficeAfternoonOpeningTime()))) &&		 
            		 
    		 ((this.getOfficeAfternoonClosingTime() == null && other.getOfficeAfternoonClosingTime() == null) || 
            		 (this.getOfficeAfternoonClosingTime() != null && this.getOfficeAfternoonClosingTime().equals(other.getOfficeAfternoonClosingTime())));	 		 
    		    		 
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
        
        if (getOfficeMorningOpeningTime() != null) {
            _hashCode += getOfficeMorningOpeningTime().hashCode();
        }
        
        if (getOfficeMorningClosingTime() != null) {
            _hashCode += getOfficeMorningClosingTime().hashCode();
        }
        
        if (getOfficeAfternoonOpeningTime() != null) {
            _hashCode += getOfficeAfternoonOpeningTime().hashCode();
        }
        
        if (getOfficeAfternoonClosingTime() != null) {
            _hashCode += getOfficeAfternoonClosingTime().hashCode();
        }
        
        __hashCodeCalc = false;
        return _hashCode;
    }
	
	

	// Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IndicatorFilter.class, true);

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
        elemField.setFieldName("officeMorningOpeningTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "officeMorningOpeningTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeMorningClosingTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "officeMorningClosingTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);  
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeAfternoonOpeningTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "officeAfternoonOpeningTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officeAfternoonClosingTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "officeAfternoonClosingTime"));
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
