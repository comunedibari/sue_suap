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
package it.people.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.envelope.EnvelopeFactory_modellazioneb116_envelopeb002;
import it.people.envelope.IEnvelopeFactory;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.util.EnvelopeHelper;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.SignedData;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.PeopleProperties;
import it.people.util.ProcessUtils;
import it.people.util.UnsignedPrintModule;
import it.people.util.attach.UtilityAttach;
import it.people.vsl.Pipeline;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataHelper;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.PipelineFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xerces.utils.Base64;

/**
 * User: sergio Date: Sep 18, 2003 Time: 11:38:11 AM <br>
 * <br>
 * Questa classe realizza la funzione dell'invio di un processo mettendolo nella
 * opportuna pipeline
 */
public class SendProcess extends Action {
    protected Logger log = Logger.getLogger(SendProcess.class);

    /**
     * @param mapping
     *            Oggetto contenente la "mappatura" delle azioni e dei forward
     * @param form
     *            Form inviata
     * @param request
     *            Request
     * @param response
     *            Respose
     * @return Restituisce il forward da eseguire a seconda dell'esito
     *         dell'azione
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	super.execute(mapping, form, request, response);

	AbstractPplProcess pplProcess = (AbstractPplProcess) form;
	AbstractData data = (AbstractData) pplProcess.getData();
	String peopleProtocolID = data.getIdentificatorePeople()
		.getIdentificatoreProcedimento();

	if (!pplProcess.isInizialized()) {
	    log.error("Tentativo di invio di una pratica non inizializzata.");
	    setError(request, "SendProcess.notInitialized");
	} else if (pplProcess.getSent().booleanValue()) {
	    log.warn("Tentativo di invio di una pratica gi� inviata.");
	    setError(request, "SendProcess.alreadySent");
	} else {
	    PeopleContext pplContext = PeopleContext.create(request);

	    if (pplContext.getUser().isAnonymous()) {
		log.info("Tentato l'accesso come utente anonimo.");
		ProcessUtils.clearProcessSessionClearableData(request);
		return mapping.findForward("failed");
	    }

	    try {
		// Salva il procedimento prima di iniziare qualunque operazione
		// di invio
		ProcessManager.getInstance().set(pplContext, pplProcess);
	    } catch (peopleException pplEx) {
		log.error(pplEx);
		ProcessUtils.clearProcessSessionClearableData(request);
		return mapping.findForward("failed");
	    }

	    PipelineData pd = PipelineDataHelper.createPipelineData(pplContext,
		    pplProcess.getCommune(), pplProcess, pplProcess.getData());
	    // String nuovaGestioneFile =
	    // request.getSession().getServletContext().getInitParameter("remoteAttachFile");
	    pd.setAttribute(PipelineDataImpl.IS_DELEGATED,
		    ProcessUtils.isDelegate(request));
	    if (pplProcess.isSignEnabled()) {
		// Allega il riepilogo firmato
		// if (nuovaGestioneFile!=null &&
		// nuovaGestioneFile.equalsIgnoreCase("true")){
		if (!pplProcess.isEmbedAttachmentInXml()) {
		    SignedData signedData = pplProcess.getSign()
			    .getSignedDataVerion("Default");
		    String ss = signedData.getPath();
		    File f = new File(ss);
		    byte[] fd = getBytesFromFile(f);
		    pd.setAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME, fd);
		} else {
		    SignedData signedData = pplProcess.getSign()
			    .getSignedDataVerion("Default");
		    pd.setAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME,
			    signedData.getSignedContent().getPkcs7Data());
		}
	    } else {
		// if (nuovaGestioneFile!=null &&
		// nuovaGestioneFile.equalsIgnoreCase("true")){
		//
		// } else {
		// Ricava il riepilogo non firmato,
		String summaryData = UnsignedPrintModule.getUnsignedData(
			pplProcess, request, response);
		summaryData = "<html><body>" + summaryData + "</body></html>";
		addUnsignedSummary(summaryData, pplProcess, request, response);
		// e lo inserisce nella pipelinedata.
		pd.setAttribute(PipelineDataImpl.PRINTPAGE_PARAMNAME,
			summaryData);
		// }
	    }

	    data.exportToPipeline(pd);

	    request.setAttribute("PeopleProtocollID", peopleProtocolID);
	    request.setAttribute("redirectPage", "completed");

	    // estraggo il tracciato dalla pipeline, lo imbusto e lo reinserisco
	    String tracciatoRichiesta = (String) pd
		    .getAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME);

	    IRequestEnvelope envelopeBean = EnvelopeHelper
		    .createEnvelopeFromPplProcessAndSession(pplProcess,
			    request.getSession());

	    envelopeBean.setContenuto(new ContenutoBusta(tracciatoRichiesta));
	    envelopeBean.setNomeServizio("unknown ("
		    + envelopeBean.getContestoServizio() + ")");

	    IEnvelopeFactory envelopeFactory = new EnvelopeFactory_modellazioneb116_envelopeb002();

	    String envelopeXML = envelopeFactory
		    .createEnvelopeXmlText(envelopeBean);

	    pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME,
		    envelopeXML);

	    // Inserisco i dati nella pipeline
	    Pipeline pipeline = PipelineFactory.getInstance()
		    .getPipelineForName(pplProcess.getCommune().getKey(),
			    pplProcess.getProcessName());

	    String key = pipeline.put(pplContext, pd);

	    // salvo nella request la chiave della pipeline e procedo...
	    try {
		// Aggiorno il processo con il flag di INVIATO settato a true
		pplProcess.setSent(new Boolean(true));
		ProcessManager.getInstance().set(pplContext, pplProcess);
	    } catch (peopleException pplEx) {
		log.error(pplEx);
	    }

	    request.setAttribute("dataKey", key);
	    ProcessUtils.clearProcessSessionClearableData(request);
	    return mapping.findForward("success");
	}
	ProcessUtils.clearProcessSessionClearableData(request);
	return mapping.findForward("failed");
    }

    protected void setError(HttpServletRequest request, String errorKey) {
	City commune = (City) request.getSession().getAttribute(
		PeopleConstants.SESSION_NAME_COMMUNE);
	if (commune != null) {
	    errorMessage error = MessagesFactory.getInstance().getErrorMessage(
		    commune.getKey(), errorKey);
	    error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    request.setAttribute("errorMessage", error);
	}

    }

    /**
     * Agggiunge a PeopleData il riepilogo non firmato
     * 
     * @param process
     * @param request
     * @param response
     */
    private void addUnsignedSummary(String summaryData,
	    AbstractPplProcess process, HttpServletRequest request,
	    HttpServletResponse response) {
	// Crea l'allegato
	String basePathName = null;
	String idUnivoco = null;
	// String nuovaGestioneFile =
	// request.getSession().getServletContext().getInitParameter("remoteAttachFile");
	// if (nuovaGestioneFile!=null &&
	// nuovaGestioneFile.equalsIgnoreCase("true")){
	if (!process.isEmbedAttachmentInXml()) {
	    try {
		basePathName = PeopleProperties.UPLOAD_DIRECTORY
			.getValueString(process.getCommune().getKey());
	    } catch (Exception e) {
	    }
	    if (basePathName != null) {
		File rootDirNodo = new File(basePathName + File.separator
			+ process.getCommune().getKey());
		if (!rootDirNodo.exists()) {
		    rootDirNodo.mkdir();
		}
		File rootDirPratica = new File(basePathName + File.separator
			+ process.getCommune().getKey() + File.separator
			+ process.getIdentificatoreProcedimento());
		if (!rootDirPratica.exists()) {
		    rootDirPratica.mkdirs();
		}
		basePathName = basePathName + File.separator
			+ process.getCommune().getKey() + File.separator
			+ process.getIdentificatoreProcedimento();
		idUnivoco = Long.toString((new Date()).getTime());
		// File uploadFile = new File(basePathName,
		// idUnivoco+"_"+"riepilogo.html");
		try {
		    FileWriter out = new FileWriter(basePathName
			    + File.separator + idUnivoco + "_"
			    + "riepilogo.html");
		    out.write(summaryData);
		    out.close();
		} catch (IOException e) {
		    log.error(new String(
			    "Errore nel salvataggio del riepilogo non firmato"));
		}
	    }
	}

	Attachment summary = new UnsignedSummaryAttachment(new String(
		Base64.encode(summaryData.getBytes())));

	// if (nuovaGestioneFile!=null &&
	// nuovaGestioneFile.equalsIgnoreCase("true")){
	if (!process.isEmbedAttachmentInXml()) {
	    UtilityAttach ua = new UtilityAttach(process, request);
	    summary.setDescrizione("riepilogo");
	    summary.setName("riepilogo");
	    summary.setFileLenght(summaryData.length());
	    summary.setPath(basePathName + File.separator + idUnivoco + "_"
		    + "riepilogo.html");
	    String metaData = ua.getMetaInfoFile(summary);
	    summary.setData(metaData);
	} else {
	    summary.setFileLenght(summaryData.length());
	    // summary.setPath("");
	}
	AbstractData data = ((AbstractData) process.getData());
	data.addAllegati(summary);
	data.setPresentiAllegati(true);
    }

    private byte[] getBytesFromFile(File file) throws IOException {
	InputStream is = new FileInputStream(file);
	// Get the size of the file
	long length = file.length();
	// You cannot create an array using a long type.
	// It needs to be an int type.
	// Before converting to an int type, check
	// to ensure that file is not larger than Integer.MAX_VALUE.
	if (length > Integer.MAX_VALUE) {
	    // File is too large
	}
	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];
	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length
		&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
	    throw new IOException("Could not completely read file"
		    + file.getName());
	}
	// Close the input stream and return bytes
	is.close();
	return bytes;
    }

}
