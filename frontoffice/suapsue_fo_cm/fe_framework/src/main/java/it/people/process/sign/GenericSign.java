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

import it.people.ActivityState;
import it.people.StepState;
import it.people.core.PplACE;
import it.people.core.PplPermission;
import it.people.core.PplRole;
import it.people.core.SignedDataManager;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.PrintedSigningData;
import it.people.process.sign.entity.SignedData;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class GenericSign extends ConcreteSign {

    private String VERSION = "1";

    /**
     * Costruttore.
     */
    public GenericSign() {
	super();
    }

    /**
     * Costruttore.
     * 
     * @param parent
     */
    public GenericSign(AbstractPplProcess parent) {
	super(parent);
    }

    /**
     * Definisce le attivita' del processo.
     * 
     * @throws peopleException
     */
    protected void doDefineActivities(HttpServletRequest request)
	    throws peopleException {

	Attachment[] attachs = new Attachment[] {};

	AbstractData procData = (AbstractData) getParent().getData();
	if (procData.getAllegati() != null && procData.getAllegati().size() > 0) {
	    attachs = new Attachment[procData.getAllegati().size()];
	    procData.getAllegati().toArray(attachs);
	}

	PplACE[] ACL4richiedente = new PplACE[] { new PplACE(
		PplRole.RICHIEDENTE, PplPermission.SIGN) };
	PplACE[] ACL4coniuge = new PplACE[] { new PplACE(PplRole.CONIUGE,
		PplPermission.SIGN) };

	ArrayList signStepsList = new ArrayList();
	for (int i = 0; i < attachs.length; i++) {
	    if (!(attachs[i] instanceof SignedAttachment)) {

		/*
		 * Al momento non � prevista la firma online degli allegati,
		 * per ripristinarla decommentare la parte sotto.
		 */
		/*
		 * signStepsList.add(new StepSign( getSignPage(),
		 * //"/sign/rimborsoici/default/html/paginaDatiDaFirmare.jsp",
		 * "", StepState.ACTIVE, null , new AttachmentSignDo(new
		 * AttachmentSigningData(generateStepKey("Attachment" +
		 * Integer.toString(i),VERSION), attachs[i].getName(),
		 * attachs[i].getPath())), ACL4richiedente));
		 */
	    } else {
		// memorizza in un array dei file memorizzati il path del file
		// gi� firmato
		SignedData sd = SignedDataHelper.buildFromAttachment(
			generateStepKey("Attachment" + Integer.toString(i),
				VERSION), (SignedAttachment) attachs[i]);
		if (sd != null) {
		    addSignedData(sd);
		    SignedDataManager.getInstance().set(sd, m_parent, request);
		} else {
		    ; // todo decidere cosa fare nle caso non si reisca a creare
		      // il SignedData
		}

	    }
	}

	/*
	 * Modifica Cedaf Modificato il nome del Modulo aggiungendo l'estensione
	 * "html", questo permette di aprire l'allegato direttamente con
	 * programmi come dike.
	 */
	signStepsList
		.add(new StepSign(getSignPage(), "", StepState.ACTIVE, null,
			new ModuleSignDo(new PrintedSigningData(
				generateStepKey("Default", VERSION),
				"riepilogo.pdf", "printModuleDatiSubmitter"),
				m_parent), ACL4richiedente));

	createActivity("Verifica e Firma", ActivityState.ACTIVE,
		(StepSign[]) signStepsList.toArray(new StepSign[0]), 0);
    }

}
