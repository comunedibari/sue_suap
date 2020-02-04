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
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 25/ott/2011 10.21.16
 */
public class FEServiceReferenceVO implements java.io.Serializable {

	private static final long serialVersionUID = -2232702617768178879L;

	private String communeId;
	
	private String activity;
	
	private String subActivity;
	
	private String process;
	
	private String servicePackage;
	
	private String reference;
	
	/**
	 * 
	 */
	public FEServiceReferenceVO() {
		
	}
	
	/**
	 * @param communeId
	 * @param servicePackage
	 * @param reference
	 */
	public FEServiceReferenceVO(final String communeId, final String servicePackage, final String activity, 
			final String subActivity, final String process, final String reference) {
		this.setCommuneId(communeId);
		this.setServicePackage(servicePackage);
		this.setReference(reference);
		this.setActivity(subActivity);
		this.setSubActivity(subActivity);
		this.setProcess(process);
	}

	/**
	 * @return the communeId
	 */
	public final String getCommuneId() {
		return this.communeId;
	}

	/**
	 * @param communeId the communeId to set
	 */
	public final void setCommuneId(String communeId) {
		this.communeId = communeId;
	}

	/**
	 * @return the servicePackage
	 */
	public final String getServicePackage() {
		return this.servicePackage;
	}

	/**
	 * @param _package the servicePackage to set
	 */
	public final void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	/**
	 * @return the reference
	 */
	public final String getReference() {
		return this.reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public final void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the activity
	 */
	public final String getActivity() {
		return this.activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public final void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the subActivity
	 */
	public final String getSubActivity() {
		return this.subActivity;
	}

	/**
	 * @param subActivity the subActivity to set
	 */
	public final void setSubActivity(String subActivity) {
		this.subActivity = subActivity;
	}

	/**
	 * @return the process
	 */
	public final String getProcess() {
		return this.process;
	}

	/**
	 * @param process the process to set
	 */
	public final void setProcess(String process) {
		this.process = process;
	}



	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FEServiceReferenceVO)) return false;
        FEServiceReferenceVO other = (FEServiceReferenceVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getCommuneId() == null && other.getCommuneId() == null) || 
             (this.getCommuneId() != null && this.getCommuneId().equalsIgnoreCase(other.getCommuneId()))) &&
             ((this.getServicePackage() == null && other.getServicePackage() == null) || 
                     (this.getServicePackage() != null && this.getServicePackage().equalsIgnoreCase(other.getServicePackage()))) &&
                     ((this.getReference() == null && other.getReference() == null) || 
                             (this.getReference() != null && this.getReference().equalsIgnoreCase(other.getReference()))) &&
                             ((this.getActivity() == null && other.getActivity() == null) || 
                                     (this.getActivity() != null && this.getActivity().equalsIgnoreCase(other.getActivity()))) &&
                                     ((this.getSubActivity() == null && other.getSubActivity() == null) || 
                                             (this.getSubActivity() != null && this.getSubActivity().equalsIgnoreCase(other.getSubActivity()))) &&
                                             ((this.getProcess() == null && other.getProcess() == null) || 
                                                     (this.getProcess() != null && this.getProcess().equalsIgnoreCase(other.getProcess())));
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
        if (getCommuneId() != null) {
            _hashCode += getCommuneId().hashCode();
        }
        if (getServicePackage() != null) {
            _hashCode += getServicePackage().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getActivity() != null) {
            _hashCode += getActivity().hashCode();
        }
        if (getSubActivity() != null) {
            _hashCode += getSubActivity().hashCode();
        }
        if (getProcess() != null) {
            _hashCode += getProcess().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommunePackageVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "CommunePackageVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servicePackage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "servicePackage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "activity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);

        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subActivity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "subActivity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("process");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "process"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "reference"));
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
