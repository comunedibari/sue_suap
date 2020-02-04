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
package it.people.vsl;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 12-set-2003 Time: 16.37.24 To
 * change this template use Options | File Templates.
 */
public class PipelineDataImpl implements PipelineData, java.io.Serializable {

    private static final long serialVersionUID = 9176066181963036758L;

    public static String HTTP_REQUEST_PARAMNAME = "httpRequestParam";
    public static String HTTP_RESPONSE_PARAMNAME = "httpResponseParam";
    public static String COMMUNE_PARAMNAME = "communeParam";
    public static String XML_PROCESSDATA_PARAMNAME = "processDataParam";
    public static String EDITABLEPROCESS_ID_PARAMNAME = "editableProcessId";
    public static String EDITABLEPROCESS_PARAMNAME = "editableProcess";
    public static String ATTACHMENT_PARAMNAME = "attachmentParam";
    public static String PEOPLE_PROTOCOLL_ID_PARAMNAME = "peopleProtocollID";
    public static String TRANSPORT_TRACKINGNUMBER_PARAMNAME = "transportTrackingNumber";

    public static String USER_PARAMNAME = "userParam";
    public static String USER_MAILADDRESS_PARAMNAME = "userMailAddress";
    public static String PRINTPAGE_PARAMNAME = "PrintPage";

    public static String SIGNED_ATTACHMENT_NAME = "signedAttachmentName";
    public static String SIGNED_PRINTPAGE_NAME = "signedPrintPage";
    public static String PROCESS_TITLE = "processTitle";
    public static String PROCESS_OID = "processOid";
    public static String ATTACHMENTS = "ATTACHMENTS";

    // Indica se gli allegati devono essere inviati nella mail di ricevuta del
    // cittadino
    public static String RECEIPT_MAIL_ATTACHMENT = "receiptMailAttachment";

    // Indica se deve essere inviata la mail di ricevuta al cittadino
    // Eng-28072011->
    public static String SEND_RECEIPT_MAIL = "sendReceiptMail";
    // <-Eng-28072011

    public static String DOWNLOADED_DOCUMENT_HASH = "downloadedDocumentHash";

    public static String WAITING_FOR_SIGNED_DOCUMENT = "waitingForSignedDocument";

    public static String IS_DELEGATED = "isDelegated";

    public static String IS_RESEND = "isResend";

    public PipelineDataImpl() {
	m_attributes = new HashMap();
    }

    public void setAttribute(String name, Object value) {

	if (name == null || "".equals(name))
	    return;

	m_attributes.put(name, value);
    }

    public Object getAttribute(String name) {

	if (name == null || "".equals(name))
	    return null;

	return m_attributes.get(name);
    }

    public void removeAttribute(String name) {

	if (name == null || "".equals(name))
	    return;

	m_attributes.remove(name);
    }

    private HashMap m_attributes;
}
