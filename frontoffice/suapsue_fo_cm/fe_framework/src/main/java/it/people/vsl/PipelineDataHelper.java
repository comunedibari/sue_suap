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

import it.people.City;
import it.people.content.ContentImpl;
import it.people.core.ContentManager;
import it.people.core.PeopleContext;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.SignedData;

/**
 * 
 * User: sergio Date: Sep 22, 2003 Time: 11:09:12 AM
 * 
 */
public class PipelineDataHelper {

    public static PipelineData createPipelineData(PeopleContext context,
	    City commune, AbstractPplProcess process, PplData data) {
	PipelineData pd = new PipelineDataImpl();

	// Recupera il titolo del procedimento
	ContentImpl content = null;
	try {
	    content = ContentManager.getInstance().getForProcessName(
		    process.getProcessName());
	} catch (peopleException e) {
	}
	String processTitle = (content != null ? content.getName() : "");

	pd.setAttribute(PipelineDataImpl.COMMUNE_PARAMNAME, commune);
	pd.setAttribute(PipelineDataImpl.USER_PARAMNAME, context.getUser());
	pd.setAttribute(PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME,
		process.getOid());
	pd.setAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME, process);
	pd.setAttribute(PipelineDataImpl.PROCESS_TITLE, processTitle);

	AbstractData abstractData = (AbstractData) process.getData();
	String peopleProtocolID = abstractData.getIdentificatorePeople()
		.getIdentificatoreProcedimento();

	pd.setAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME,
		peopleProtocolID);
	pd.setAttribute(PipelineDataImpl.USER_MAILADDRESS_PARAMNAME,
		getUserMailAddress(context, process));

	// Se esistono allegati li metto nel PD
	if (data != null && abstractData.getAllegati() != null
		&& !abstractData.getAllegati().isEmpty()) {
	    pd.setAttribute(PipelineDataImpl.ATTACHMENTS,
		    abstractData.getAllegati());
	}

	pd.setAttribute(PipelineDataImpl.RECEIPT_MAIL_ATTACHMENT, new Boolean(
		process.isReceiptMailAttachment()));

	// Eng-28072011->
	pd.setAttribute(PipelineDataImpl.SEND_RECEIPT_MAIL,
		new Boolean(process.isSendMailToOwner()));
	// <-Eng-28072011

	return pd;
    }

    private static String getUserMailAddress(PeopleContext context,
	    AbstractPplProcess process) {
	String emailAddress = null;

	if (process.isSignEnabled()) {
	    SignedData signedData = process.getSign().getSignedDataVerion(
		    "Default");
	    emailAddress = signedData.getSignedContent().getMailAddress();
	}

	if (emailAddress == null || emailAddress.length() == 0) {
	    emailAddress = context.getUser().getEMail();
	}

	return emailAddress;
    }
}
