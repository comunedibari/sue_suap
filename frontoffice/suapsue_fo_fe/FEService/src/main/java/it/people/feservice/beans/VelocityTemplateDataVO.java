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
 * Contains Velocity Template data
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 *
 */
public class VelocityTemplateDataVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 3128407980674333341L;
	
	VelocityTemplateBean[] velocityTemplates;
	
	
	public VelocityTemplateDataVO() {
		
	}

	public VelocityTemplateDataVO(VelocityTemplateBean[] velocityTemplates) {
		this.velocityTemplates = velocityTemplates;
	}


	/**
	 * @return the velocityTemplates
	 */
	public VelocityTemplateBean[] getVelocityTemplates() {
		return velocityTemplates;
	}

	/**
	 * @param velocityTemplates the velocityTemplates to set
	 */
	public void setVelocityTemplates(VelocityTemplateBean[] velocityTemplates) {
		this.velocityTemplates = velocityTemplates;
	}

	
	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VelocityTemplateDataVO)) return false;
        VelocityTemplateDataVO other = (VelocityTemplateDataVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.velocityTemplates == null && other.getVelocityTemplates()  == null) || 
             (this.velocityTemplates != null && this.velocityTemplates.equals((other.getVelocityTemplates()))));
             
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
        if (getVelocityTemplates() != null) {
            _hashCode += getVelocityTemplates().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description
    		.TypeDesc(VelocityTemplateDataVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateDataVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();

        elemField.setFieldName("velocityTemplates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "velocityTemplates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateBean"));
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
