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
 * @author Andrea Piemontese - Engineering Ingegneria Informatica - Bologna
 * 10/set/2012
 * 
 */
public class ProcessesDeletionResultVO implements java.io.Serializable {


	private static final long serialVersionUID = 7822008275564508265L;

	private int deletedRows = -1;
	
	private String backupFilePath;

	private boolean error = false;
	
	private String errorMessage;

	
	public ProcessesDeletionResultVO() {
		
	}

	public ProcessesDeletionResultVO(int deletedRows, String backupFilePath, boolean error,
			String errorMessage) {
		super();
		this.deletedRows = deletedRows;
		this.backupFilePath = backupFilePath;
		this.error = error;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the deletedRows
	 */
	public int getDeletedRows() {
		return deletedRows;
	}

	/**
	 * @param deletedRows the deletedRows to set
	 */
	public void setDeletedRows(int deletedRows) {
		this.deletedRows = deletedRows;
	}

	/**
	 * @return the backupFilePath
	 */
	public String getBackupFilePath() {
		return backupFilePath;
	}

	/**
	 * @param backupFilePath the backupFilePath to set
	 */
	public void setBackupFilePath(String backupFilePath) {
		this.backupFilePath = backupFilePath;
	}

	/**
	 * @return the isError
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param isError the isError to set
	 */
	public void setError(boolean isError) {
		this.error = isError;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessesDeletionResultVO)) return false;
        ProcessesDeletionResultVO other = (ProcessesDeletionResultVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getBackupFilePath() == null && other.getBackupFilePath()  == null) || 
             (this.getBackupFilePath() != null && this.getBackupFilePath().equals((other.getBackupFilePath()))) &&
             
             ((this.getErrorMessage() == null && other.getErrorMessage() == null) || 
    		  (this.getErrorMessage() != null && this.getErrorMessage().equals((other.getErrorMessage()))) && 
             
             (this.getDeletedRows() == other.getDeletedRows()) &&
             
             (this.isError() == other.isError())));
        
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
        if (getBackupFilePath() != null) {
            _hashCode += getBackupFilePath().hashCode();
        }
        
        if (getErrorMessage() != null) {
            _hashCode += getErrorMessage().hashCode();
        }

        _hashCode += getDeletedRows();
        
        _hashCode += isError() ? 1 : 0;
      
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessesDeletionResultVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessesDeletionResultVO"));
        
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletedRows");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "deletedRows"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("backupFilePath");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "backupFilePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "errorMessage"));
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
