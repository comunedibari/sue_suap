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
package it.people.vsl.transport;

/**
 * <p>Title: TransportLayerFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Espin S.p.A.</p>
 * @author Mazzotta Vincenzo
 * @version 1.0
 */

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.SignedInfo;
import it.people.util.PKCS7Parser;
import it.people.util.dto.VelocityModelObject;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.SerializablePipelineData;
import it.people.vsl.exception.SendException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class TransportLayer {

    public static String ATTACHMENT_PARAM = "Attachements";
    public static String SIGNED_ATTACHMENT_PARAM = "SignedAttachment";
    public static String STREAMATTACHMENT_PARAM = "StreamAttachments";
    public static String SUBJECT_PARAM = "Subject";
    public static String USER_MAILADDRESS = "UserMailAddress";
    public static String SENDER_USER_NAME = "SenderUserName";
    public static String SENDER_MAILADDRESS = "SenderMailAddress";
    public static String REPLAYTO_PARAM = "ReplayTo";

    public static String MESSAGEBODY_PARAM = "MessageBody";
    public static String PRIORITY_PARAM = "Priority";

    public static String DELIVERY_STATUS = "DeliveryStatus";
    public static String DELIVERY_RESULT = "DeliveryResult";
    public static String DELIVERY_ACCEPTANCE_STATUS = "DeliveryAcceptanceStatus";
    public static String DELIVERY_STATUS_ERROR_DESCRIPTION = "DeliveryStatusErrorDescription";
    public static String PICKUP_DATE = "PickupDate";
    public static String RECEPIT = "Recepit";
    public static String RECIPIENT = "Recipient";

    public static String ACCEPTANCE_RECEPIT = "AcceptanceRecepit";
    public static String ACCEPTANCE_RECEPIT_TS = "AcceptanceRecepitTimestamp";
    public static String ATTACHMENT_DATA = "AttachmentDataSource";
    public static String MAIL_TRACKINGID_PARAM = "mailTrackingNumber";

    public static String XML_DATA = "xmlData";
    public static String CITY = "communeParam";
    public static String PROCESS_NAME = "processName";
    public static String PROCESS_ID = "processId";
    // public static String ATTACHMENTS = "ATTACHMENTS";
    public static String PEOPLE_PROTOCOLL_ID_PARAMNAME = "peopleProtocollID";

    public static String SEND_RECEIPT_WITH_MAIL_ATTACHMENT = "sendReceiptWithMailAttachment";

    protected String realServiceName = null;

    Logger logger = Logger.getLogger(TransportLayer.class);

    public void setRealServiceName(String name) {
	realServiceName = name;
    }

    protected HashMap m_inParameters = new HashMap();
    protected HashMap m_outParameters = new HashMap();

    private City comune = null;

    public void addInParameter(Object key, Object value) {
	m_inParameters.put(key, value);
    }

    public void removeInParameter(Object key) {
	m_inParameters.remove(key);
    }

    public Object getInParameter(Object key) {
	return m_inParameters.get(key);
    }

    public void addOutParameter(Object key, Object value) {
	m_outParameters.put(key, value);
    }

    public void removeOutParameter(Object key) {
	m_outParameters.remove(key);
    }

    public Object getOutParameter(Object key) {
	return m_outParameters.get(key);
    }

    public void cleanOutParameter() {
	m_outParameters.clear();
    }

    protected String createMessageBody(PipelineData pd) {
	Object signedDocument = pd
		.getAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME);
	String content = "";
	if (signedDocument != null) {
	    if (signedDocument instanceof byte[]) {
		SignedInfo si = new SignedInfo();
		PKCS7Parser parser = new PKCS7Parser((byte[]) signedDocument);
		parser.decode(si);
		// content = new String(si.getData());
		try {
		    // content = decode(si.getData());
		    String oid = (String) pd
			    .getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME);
		    // content =
		    // "La pratica "+oid+" è stata presa in carico da People";
		    // } catch(CharacterCodingException ex) {
		} catch (Exception ex) {
		    logger.error("Errore nella decodifica del MessageBody", ex);
		}
	    }
	} else
	    content = pd.getAttribute(PipelineDataImpl.PRINTPAGE_PARAMNAME)
		    .toString();
	return content;
    }

    protected String decode(byte[] buffer) throws CharacterCodingException {
	Charset charset = Charset.forName("UTF-16LE");
	CharsetDecoder decoder = charset.newDecoder();
	ByteBuffer bbuf = ByteBuffer.wrap(buffer);
	CharBuffer cbuf = decoder.decode(bbuf);
	String retValue = cbuf.toString();
	return retValue;
    }

    public abstract HashMap pipeline2transportData(PipelineData data);

    public abstract void transportData2pipeline(PipelineData data,
	    HashMap params);

    public abstract void send(HashMap inParameter, HashMap outParameters)
	    throws SendException;

    public abstract void chekStatus(HashMap inParameter, HashMap outParameters)
	    throws SendException;

    public abstract SerializablePipelineData pipelineData2SerializablePipelineData(
	    PipelineData data);

    public City getComune() {
	return comune;
    }

    public void setComune(City city) {
	comune = city;
    }

}
