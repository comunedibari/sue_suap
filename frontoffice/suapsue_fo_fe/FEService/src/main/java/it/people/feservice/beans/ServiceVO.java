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

public class ServiceVO  implements java.io.Serializable {
    private int logLevel;
    private java.lang.String nome;
    private java.lang.String servicePackage;
    private int receiptMailIncludeAttachment;
    private java.lang.String communeId;
    private int signEnabled;
    private it.people.feservice.beans.DependentModule[] dependentModules;
    private it.people.feservice.beans.ConfigParameter[] configParameters;
    private java.lang.String sottoattivita;
    private java.lang.String process;
    private java.lang.String attivita;
    private int stato;
    private int sendmailtoowner;
    private int embedAttachmentInXml;
    //Privacy 
	private int showPrivacyDisclaimer;
	private int privacyDisclaimerRequireAcceptance;
    //Firma OnLine-OffLine
	private int onlineSign;
	private int offlineSign;
	

    public ServiceVO() {
    }

    public ServiceVO(
           int logLevel,
           java.lang.String nome,
           java.lang.String servicePackage,
           int receiptMailIncludeAttachment,
           java.lang.String communeId,
           int signEnabled,
           it.people.feservice.beans.DependentModule[] dependentModules,
           it.people.feservice.beans.ConfigParameter[] configParameters,
           java.lang.String sottoattivita,
           java.lang.String process,
           java.lang.String attivita,
           int stato, int sendmailtoowner, int embedAttachmentInXml,
           int showPrivacyDisclaimer, int privacyDisclaimerRequireAcceptance,
           int onlineSign, int offlineSign) {
           this.logLevel = logLevel;
           this.nome = nome;
           this.servicePackage = servicePackage;
           this.receiptMailIncludeAttachment = receiptMailIncludeAttachment;
           this.communeId = communeId;
           this.signEnabled = signEnabled;
           this.dependentModules = dependentModules;
           this.configParameters = configParameters;
           this.sottoattivita = sottoattivita;
           this.process = process;
           this.attivita = attivita;
           this.stato = stato;
           this.sendmailtoowner = sendmailtoowner;
           this.embedAttachmentInXml = embedAttachmentInXml;
           this.showPrivacyDisclaimer = showPrivacyDisclaimer;
           this.privacyDisclaimerRequireAcceptance = privacyDisclaimerRequireAcceptance;
           this.setOnlineSign(onlineSign);
           this.setOfflineSign(offlineSign);
    }


    /**
     * Gets the logLevel value for this ServiceVO.
     * 
     * @return logLevel
     */
    public int getLogLevel() {
        return logLevel;
    }


    /**
     * Sets the logLevel value for this ServiceVO.
     * 
     * @param logLevel
     */
    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }


    /**
     * Gets the nome value for this ServiceVO.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this ServiceVO.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the servicePackage value for this ServiceVO.
     * 
     * @return servicePackage
     */
    public java.lang.String getServicePackage() {
        return servicePackage;
    }


    /**
     * Sets the servicePackage value for this ServiceVO.
     * 
     * @param servicePackage
     */
    public void setServicePackage(java.lang.String servicePackage) {
        this.servicePackage = servicePackage;
    }


    /**
     * Gets the receiptMailIncludeAttachment value for this ServiceVO.
     * 
     * @return receiptMailIncludeAttachment
     */
    public int getReceiptMailIncludeAttachment() {
        return receiptMailIncludeAttachment;
    }


    /**
     * Sets the receiptMailIncludeAttachment value for this ServiceVO.
     * 
     * @param receiptMailIncludeAttachment
     */
    public void setReceiptMailIncludeAttachment(int receiptMailIncludeAttachment) {
        this.receiptMailIncludeAttachment = receiptMailIncludeAttachment;
    }


    /**
     * Gets the communeId value for this ServiceVO.
     * 
     * @return communeId
     */
    public java.lang.String getCommuneId() {
        return communeId;
    }


    /**
     * Sets the communeId value for this ServiceVO.
     * 
     * @param communeId
     */
    public void setCommuneId(java.lang.String communeId) {
        this.communeId = communeId;
    }


    /**
     * Gets the signEnabled value for this ServiceVO.
     * 
     * @return signEnabled
     */
    public int getSignEnabled() {
        return signEnabled;
    }


    /**
     * Sets the signEnabled value for this ServiceVO.
     * 
     * @param signEnabled
     */
    public void setSignEnabled(int signEnabled) {
        this.signEnabled = signEnabled;
    }


    /**
     * Gets the dependentModules value for this ServiceVO.
     * 
     * @return dependentModules
     */
    public it.people.feservice.beans.DependentModule[] getDependentModules() {
        return dependentModules;
    }


    /**
     * Sets the dependentModules value for this ServiceVO.
     * 
     * @param dependentModules
     */
    public void setDependentModules(it.people.feservice.beans.DependentModule[] dependentModules) {
        this.dependentModules = dependentModules;
    }


    /**
     * Gets the configParameters value for this ServiceVO.
     * 
     * @return configParameters
     */
    public it.people.feservice.beans.ConfigParameter[] getConfigParameters() {
        return configParameters;
    }


    /**
     * Sets the configParameters value for this ServiceVO.
     * 
     * @param configParameters
     */
    public void setConfigParameters(it.people.feservice.beans.ConfigParameter[] configParameters) {
        this.configParameters = configParameters;
    }


    /**
     * Gets the sottoattivita value for this ServiceVO.
     * 
     * @return sottoattivita
     */
    public java.lang.String getSottoattivita() {
        return sottoattivita;
    }


    /**
     * Sets the sottoattivita value for this ServiceVO.
     * 
     * @param sottoattivita
     */
    public void setSottoattivita(java.lang.String sottoattivita) {
        this.sottoattivita = sottoattivita;
    }


    /**
     * Gets the process value for this ServiceVO.
     * 
     * @return process
     */
    public java.lang.String getProcess() {
        return process;
    }


    /**
     * Sets the process value for this ServiceVO.
     * 
     * @param process
     */
    public void setProcess(java.lang.String process) {
        this.process = process;
    }


    /**
     * Gets the attivita value for this ServiceVO.
     * 
     * @return attivita
     */
    public java.lang.String getAttivita() {
        return attivita;
    }


    /**
     * Sets the attivita value for this ServiceVO.
     * 
     * @param attivita
     */
    public void setAttivita(java.lang.String attivita) {
        this.attivita = attivita;
    }


    /**
     * Gets the stato value for this ServiceVO.
     * 
     * @return stato
     */
    public int getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this ServiceVO.
     * 
     * @param stato
     */
    public void setStato(int stato) {
        this.stato = stato;
    }

    /**
	 * @return the sendmailtoowner
	 */
	public int getSendmailtoowner() {
		return this.sendmailtoowner;
	}

	/**
	 * @param sendmailtoowner the sendmailtoowner to set
	 */
	public void setSendmailtoowner(int sendmailtoowner) {
		this.sendmailtoowner = sendmailtoowner;
	}


	/**
	 * @return the embedAttachmentInXml
	 */
	public int getEmbedAttachmentInXml() {
		return embedAttachmentInXml;
	}

	/**
	 * @param embedAttachmentInXml the embedAttachmentInXml to set
	 */
	public void setEmbedAttachmentInXml(int embedAttachmentInXml) {
		this.embedAttachmentInXml = embedAttachmentInXml;
	}


	/**
	 * @return the showPrivacyDisclaimer
	 */
	public int getShowPrivacyDisclaimer() {
		return showPrivacyDisclaimer;
	}

	/**
	 * @param showPrivacyDisclaimer the showPrivacyDisclaimer to set
	 */
	public void setShowPrivacyDisclaimer(int showPrivacyDisclaimer) {
		this.showPrivacyDisclaimer = showPrivacyDisclaimer;
	}


	/**
	 * @return the privacyDisclaimerRequireAcceptance
	 */
	public int getPrivacyDisclaimerRequireAcceptance() {
		return privacyDisclaimerRequireAcceptance;
	}

	/**
	 * @param privacyDisclaimerRequireAcceptance the privacyDisclaimerRequireAcceptance to set
	 */
	public void setPrivacyDisclaimerRequireAcceptance(
			int privacyDisclaimerRequireAcceptance) {
		this.privacyDisclaimerRequireAcceptance = privacyDisclaimerRequireAcceptance;
	}


	/**
	 * @return the onlineSign
	 */
	public int getOnlineSign() {
		return onlineSign;
	}

	/**
	 * @param onlineSign the onlineSign to set
	 */
	public void setOnlineSign(int onlineSign) {
		this.onlineSign = onlineSign;
	}


	/**
	 * @return the offlineSign
	 */
	public int getOfflineSign() {
		return offlineSign;
	}

	/**
	 * @param offlineSign the offlineSign to set
	 */
	public void setOfflineSign(int offlineSign) {
		this.offlineSign = offlineSign;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceVO)) return false;
        ServiceVO other = (ServiceVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.logLevel == other.getLogLevel() &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.servicePackage==null && other.getServicePackage()==null) || 
             (this.servicePackage!=null &&
              this.servicePackage.equals(other.getServicePackage()))) &&
            this.receiptMailIncludeAttachment == other.getReceiptMailIncludeAttachment() &&
            ((this.communeId==null && other.getCommuneId()==null) || 
             (this.communeId!=null &&
              this.communeId.equals(other.getCommuneId()))) &&
            this.signEnabled == other.getSignEnabled() && 
            this.sendmailtoowner == other.getSendmailtoowner() &&
            this.embedAttachmentInXml == other.getEmbedAttachmentInXml() &&
            //Privacy
            this.showPrivacyDisclaimer == other.showPrivacyDisclaimer &&
            this.privacyDisclaimerRequireAcceptance == other.privacyDisclaimerRequireAcceptance &&
            //Firma online-offline
            this.onlineSign == other.onlineSign &&
            this.offlineSign == other.onlineSign &&
            
            ((this.dependentModules==null && other.getDependentModules()==null) || 
             (this.dependentModules!=null &&
              java.util.Arrays.equals(this.dependentModules, other.getDependentModules()))) &&
            ((this.configParameters==null && other.getConfigParameters()==null) || 
             (this.configParameters!=null &&
              java.util.Arrays.equals(this.configParameters, other.getConfigParameters()))) &&
            ((this.sottoattivita==null && other.getSottoattivita()==null) || 
             (this.sottoattivita!=null &&
              this.sottoattivita.equals(other.getSottoattivita()))) &&
            ((this.process==null && other.getProcess()==null) || 
             (this.process!=null &&
              this.process.equals(other.getProcess()))) &&
            ((this.attivita==null && other.getAttivita()==null) || 
             (this.attivita!=null &&
              this.attivita.equals(other.getAttivita()))) &&
            this.stato == other.getStato();
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
        _hashCode += getLogLevel();
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getServicePackage() != null) {
            _hashCode += getServicePackage().hashCode();
        }
        _hashCode += getReceiptMailIncludeAttachment();
        if (getCommuneId() != null) {
            _hashCode += getCommuneId().hashCode();
        }
        _hashCode += getSignEnabled();
        _hashCode += getSendmailtoowner();
        _hashCode += getEmbedAttachmentInXml();
        
        //Privacy
        _hashCode += getShowPrivacyDisclaimer();
        _hashCode += getPrivacyDisclaimerRequireAcceptance();
        
        //firma online-offline
        _hashCode += getOnlineSign();
        _hashCode += getOfflineSign();
        
        if (getDependentModules() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDependentModules());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDependentModules(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConfigParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConfigParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConfigParameters(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSottoattivita() != null) {
            _hashCode += getSottoattivita().hashCode();
        }
        if (getProcess() != null) {
            _hashCode += getProcess().hashCode();
        }
        if (getAttivita() != null) {
            _hashCode += getAttivita().hashCode();
        }
        _hashCode += getStato();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "logLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "nome"));
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
        elemField.setFieldName("receiptMailIncludeAttachment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "receiptMailIncludeAttachment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("communeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "communeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signEnabled");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "signEnabled"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendmailtoowner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "sendmailtoowner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);

        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("embedAttachmentInXml");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "embedAttachmentInXml"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        
        //Privacy fields
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("showPrivacyDisclaimer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "showPrivacyDisclaimer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("privacyDisclaimerRequireAcceptance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "privacyDisclaimerRequireAcceptance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        
        //Firma online-offline
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlineSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "onlineSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offlineSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "offlineSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        
        
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependentModules");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "dependentModules"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "DependentModule"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://feservice.people.it/", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "configParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameter"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://feservice.people.it/", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sottoattivita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "sottoattivita"));
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
        elemField.setFieldName("attivita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "attivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "stato"));
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
