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
package it.people.process.sign;

import it.people.process.sign.entity.SignedData;
import it.people.process.sign.entity.SignedInfo;
import it.people.process.sign.entity.SigningData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Luigi Corollo Date: 21-gen-2004 Time: 17.03.31 <br>
 * <br>
 * Classe per la firma di Attachment.
 */
public class AttachmentSignDo extends StepSignDoImpl {
    public AttachmentSignDo(SigningData data) {
	super(data);
    }

    public void doBeforeSign(HttpServletRequest request,
	    HttpServletResponse response) {
	request.setAttribute(ConcreteSign.Request.SIGNINGDATA, m_data);
    }

    public SignedData doAfterSign(SignedInfo si) {
	return new SignedData(m_data.getKey(), m_data.getFriendlyName(), si);
    }
}
