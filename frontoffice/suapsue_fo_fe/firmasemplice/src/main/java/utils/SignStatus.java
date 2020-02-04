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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Giuseppe
 */
public class SignStatus {

private String signatureName;
private String signatureValidityStatus;
private OCSPStatus certificateStatus;
private String errorMessage;
private SignGeneralStatus generalStatus;

    public SignStatus (String signatureName, String signatureValidityStatus, OCSPStatus certificateStatus, String errorMessage, SignGeneralStatus generalStatus) {
        this.signatureName = signatureName;
        this.signatureValidityStatus = signatureValidityStatus;
        this.certificateStatus = certificateStatus;
        this.errorMessage = errorMessage;
        this.generalStatus = generalStatus;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public OCSPStatus getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(OCSPStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSignatureValidityStatus() {
        return signatureValidityStatus;
    }

    public void setSignatureValidityStatus(String signatureValidityStatus) {
        this.signatureValidityStatus = signatureValidityStatus;
    }

    public SignGeneralStatus getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(SignGeneralStatus generalStatus) {
        this.generalStatus = generalStatus;
    }
}
