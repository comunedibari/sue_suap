/**
 * 
 */
package it.people.action;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.xml.sax.InputSource;

import it.ictechnology.certificate.parser.CertificateParser;
import it.ictechnology.certificate.parser.impl.ParserFactory;
import it.infocamere.util.signature.SignedDocument;
import it.infocamere.util.signature.impl.bouncyCastle.BCSignatureFactory;
import it.infocamere.util.signature.impl.bouncyCastle.BCSafeSignatureFactory;
import it.people.core.PeopleContext;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Richiedente;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaGiuridica;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloTitolare;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.security.Policy;
import it.people.util.MessageBundleHelper;
import it.people.wrappers.HttpServletRequestDelegateWrapper;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 22:19:53
 */
public class OffLineSignFileUploadLoopBack extends Action {

    private static Logger logger = LogManager
	    .getLogger(OffLineSignFileUploadLoopBack.class);

    private static String PARAMETER_UPLOADFILE = "signedDocumentUploadFile";

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_httpServletRequest,
	    HttpServletResponse p_httpServletResponse) throws Exception {

	super.execute(p_actionMapping, p_actionForm, p_httpServletRequest,
		p_httpServletResponse);

	AbstractPplProcess pplProcess = (AbstractPplProcess) p_actionForm;

	boolean validUpload = true;
	SignedDocument signedDocument = null;
	pplProcess.cleanErrors();

	String offLineSignFileDownloadLabel = MessageBundleHelper.message(
		"label.offLineSign.downloadDocument", null,
		pplProcess.getProcessName(), pplProcess.getCommune().getKey(),
		pplProcess.getContext().getLocale());
	String offLineSignFileUploadLabel = MessageBundleHelper.message(
		"label.offLineSign.uploadSignedDocument", null,
		pplProcess.getProcessName(), pplProcess.getCommune().getKey(),
		pplProcess.getContext().getLocale());

	try {

	    if (!pplProcess.isWaitingForOffLineSignedDocument()
		    || pplProcess.getOffLineSignDownloadedDocumentHash() == null
		    || (pplProcess.getOffLineSignDownloadedDocumentHash() != null && pplProcess
			    .getOffLineSignDownloadedDocumentHash()
			    .equalsIgnoreCase(""))) {
		pplProcess
			.addServiceError(MessageBundleHelper.message(
				"errors.offLineSign.documentNotDownloaded",
				new Object[] { offLineSignFileUploadLabel },
				pplProcess.getProcessName(), pplProcess
					.getCommune().getKey(), pplProcess
					.getContext().getLocale()));
		validUpload = false;
	    } else {

		String browser = p_httpServletRequest.getHeader("User-Agent");
		boolean isMSIE = browser.indexOf("MSIE") > 0;

		FormFile formFile = null;
		formFile = (FormFile) PropertyUtils.getProperty(
			pplProcess.getData(), PARAMETER_UPLOADFILE);

		if (isMSIE
			&& formFile != null
			&& (formFile.getFileData() == null || (formFile
				.getFileData() != null && formFile
				.getFileData().length == 0))) {
		    String browseButtonLabel = "Sfoglia...";
		    if (!pplProcess.getContext().getLocale()
			    .equals(Locale.ITALIAN)) {
			browseButtonLabel = "Browse...";
		    }
		    pplProcess.addServiceError(MessageBundleHelper.message(
			    "errors.offLineSign.ie.documentNotBrowsed",
			    new Object[] { browseButtonLabel,
				    offLineSignFileUploadLabel }, pplProcess
				    .getProcessName(), pplProcess.getCommune()
				    .getKey(), pplProcess.getContext()
				    .getLocale()));
		    validUpload = false;
		}

		if (validUpload) {
		    PropertyUtils.setProperty(pplProcess.getData(),
			    "signedDocumentUploadFile", null);

		    // Switched to BCSafe because we doesn't need for now to
		    // check the user tax code
		    // BCSignatureFactory signatureFactory = new
		    // BCSignatureFactory();
		    BCSafeSignatureFactory signatureFactory = new BCSafeSignatureFactory();
		    URL certificateDescriptions = OffLineSignFileUploadLoopBack.class
			    .getResource("/it/people/resources/certificate-descriptions.xml");

		    // System.out.println(p_httpServletRequest.getRealPath("/WEB-INF/");

		    CertificateParser certificateParser = null;
		    try {
			certificateParser = new ParserFactory()
				.parse(new InputSource(certificateDescriptions
					.toURI().toString()));
		    } catch (Exception e) {
			pplProcess.addServiceError(MessageBundleHelper.message(
				"errors.offLineSign.certificateParser", null,
				pplProcess.getProcessName(), pplProcess
					.getCommune().getKey(), pplProcess
					.getContext().getLocale()));
			validUpload = false;
		    }

		    signatureFactory.setCertificateParser(certificateParser);
		    signedDocument = signatureFactory.createDocument(formFile
			    .getFileData());

		    // Not signed error
		    if (!signedDocument.isSigned()
			    && pplProcess.getServiceError().isEmpty()) {
			pplProcess.addServiceError(MessageBundleHelper.message(
				"errors.offLineSign.notSignedDocument", null,
				pplProcess.getProcessName(), pplProcess
					.getCommune().getKey(), pplProcess
					.getContext().getLocale()));
			validUpload = false;
		    }

		    // File is signed so check hash
		    if (!pplProcess.getOffLineSignDownloadedDocumentHash()
			    .equalsIgnoreCase(signedDocument.getSHA256Hash())
			    && pplProcess.getServiceError().isEmpty()) {
			pplProcess.addServiceError(MessageBundleHelper.message(
				"errors.offLineSign.notMatchHash",
				new Object[] { offLineSignFileDownloadLabel,
					offLineSignFileUploadLabel },
				pplProcess.getProcessName(), pplProcess
					.getCommune().getKey(), pplProcess
					.getContext().getLocale()));
			validUpload = false;
		    }

		    // Check if all signs are valid
		    if (!signedDocument.isValid()
			    && pplProcess.getServiceError().isEmpty()) {
			pplProcess.addServiceError(MessageBundleHelper.message(
				"errors.offLineSign.signsNotValids", null,
				pplProcess.getProcessName(), pplProcess
					.getCommune().getKey(), pplProcess
					.getContext().getLocale()));
			validUpload = false;
		    }

		    // Get signers list. Currently not used
		    // List<Signature> l = signedDocument.getSignatures();
		    //
		    // int i=0;
		    // for (Iterator<Signature> iter = l.iterator();
		    // iter.hasNext();) {
		    // Signature e = iter.next();
		    //
		    // System.out.println("Firma n. " + (++i) + "\n" +
		    // e.toString());
		    //
		    // //si controlla se è valida
		    // assertTrue(e.isValid());
		    // }

		    // Get signers tax code
		    Set<String> signersTaxCodes = signedDocument.getSigners();

		    // No signers tax codes
		    // if (signersTaxCodes == null || (signersTaxCodes != null
		    // && signersTaxCodes.isEmpty())) {
		    // if (pplProcess.getServiceError().isEmpty()) {
		    // pplProcess.addServiceError(MessageBundleHelper.message("errors.offLineSign.noSignersTaxCodes",
		    // new Object[] {offLineSignFileDownloadLabel,
		    // offLineSignFileUploadLabel}, pplProcess.getProcessName(),
		    // pplProcess.getCommune().getKey(),
		    // pplProcess.getContext().getLocale()));
		    // validUpload = false;
		    // }
		    // } else {
		    // // If signers tax codes is not empty check if registrant
		    // tax code is present
		    // AbstractData data = (AbstractData)pplProcess.getData();
		    //
		    // ProfiloPersonaFisica richiedente =
		    // data.getProfiloRichiedente();
		    //
		    // String ownerTaxCode = "";
		    // boolean ownerFound = false;
		    // ProfiloTitolare titolare = data.getProfiloTitolare();
		    // if (titolare.getProfiloTitolarePF() != null) {
		    // ProfiloPersonaFisica personaFisica =
		    // titolare.getProfiloTitolarePF();
		    // ownerTaxCode = personaFisica.getCodiceFiscale();
		    // ownerFound = true;
		    // }
		    // if (titolare.getProfiloTitolarePG() != null) {
		    // ProfiloPersonaGiuridica personaGiuridica =
		    // titolare.getProfiloTitolarePG();
		    // if (personaGiuridica.getRappresentanteLegale() != null) {
		    // ProfiloPersonaFisica rappresentanteLegale =
		    // personaGiuridica.getRappresentanteLegale();
		    // ownerTaxCode = rappresentanteLegale.getCodiceFiscale();
		    // ownerFound = true;
		    // }
		    // }
		    //
		    // if (richiedente != null && richiedente.getCodiceFiscale()
		    // != null && ownerFound) {
		    // if
		    // ((!signersTaxCodes.contains(richiedente.getCodiceFiscale().trim())
		    // &&
		    // !signersTaxCodes.contains(ownerTaxCode.trim())) &&
		    // pplProcess.getServiceError().isEmpty()) {
		    // pplProcess.addServiceError(MessageBundleHelper.message("errors.offLineSign.noRegistrantorOwnerSign",
		    // new Object[] {offLineSignFileDownloadLabel,
		    // offLineSignFileUploadLabel}, pplProcess.getProcessName(),
		    // pplProcess.getCommune().getKey(),
		    // pplProcess.getContext().getLocale()));
		    // validUpload = false;
		    // }
		    // } else if (pplProcess.getServiceError().isEmpty()) {
		    // pplProcess.addServiceError(MessageBundleHelper.message("errors.offLineSign.noRegistrantorOwnerData",
		    // null, pplProcess.getProcessName(),
		    // pplProcess.getCommune().getKey(),
		    // pplProcess.getContext().getLocale()));
		    // validUpload = false;
		    // }
		    // }

		}
	    }

	    if (pplProcess.isInizialized()) {

		IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
			p_httpServletRequest);

		PeopleContext context = PeopleContext
			.create(p_httpServletRequest);

		if (validUpload) {

		    Base64 base64 = new Base64();
		    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    byteArrayOutputStream.write(base64.encode(signedDocument
			    .getDocumentBytes()));
		    byteArrayOutputStream.flush();
		    pplProcess.setOffLineSignedData(byteArrayOutputStream
			    .toString(), Policy.XMLTEXT.getId(),
			    p_httpServletRequest.getSession().getId(),
			    signedDocument.getSHA256Hash());
		    byteArrayOutputStream.close();

		    if (pplProcess
			    .getData()
			    .getClass()
			    .getName()
			    .equalsIgnoreCase(
				    "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData")) {
			pplProcess.getView().loopBack(pplProcess,
				requestWrapper, "multipleSign", 0);
			pplProcess.getView().draw(context,
				p_httpServletRequest, p_httpServletResponse);
			return null;
		    } else {
			if (pplProcess.isInizialized()) {
			    if (pplProcess
				    .getProcessName()
				    .equalsIgnoreCase(
					    "it.people.fsl.servizi.admin.sirac.accreditamento")) {
				return p_actionMapping.findForward("nextStep");
			    } else if (pplProcess.getSign().isInitialized()) {
				return p_actionMapping.findForward("success");
			    }
			}
		    }

		} else {

		    if (pplProcess
			    .getData()
			    .getClass()
			    .getName()
			    .equalsIgnoreCase(
				    "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData")) {
			pplProcess.getView().loopBack(pplProcess,
				requestWrapper, null, 0);
			pplProcess.getView().draw(context,
				p_httpServletRequest, p_httpServletResponse);
		    } else {
			if (pplProcess.isInizialized()) {
			    if (pplProcess
				    .getProcessName()
				    .equalsIgnoreCase(
					    "it.people.fsl.servizi.admin.sirac.accreditamento")) {
				pplProcess.getView().loopBack(pplProcess,
					requestWrapper, "", 0);
				pplProcess.getView().draw(context,
					p_httpServletRequest,
					p_httpServletResponse);
				return null;
			    } else if (pplProcess.getSign().isInitialized()) {
				pplProcess.getSign().beginStep(
					p_httpServletRequest,
					p_httpServletResponse);
			    }
			}
		    }

		    return null;

		}

	    } else {
		if (pplProcess.isInizialized()) {
		    IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
			    p_httpServletRequest);
		    PeopleContext context = PeopleContext
			    .create(p_httpServletRequest);
		    pplProcess.getView().draw(context, p_httpServletRequest,
			    p_httpServletResponse);
		    return null;
		}
	    }

	} catch (Exception e) {
	    logger.error(e);
	    if (pplProcess.isInizialized()) {
		IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
			p_httpServletRequest);
		PeopleContext context = PeopleContext
			.create(p_httpServletRequest);
		pplProcess.getView().draw(context, p_httpServletRequest,
			p_httpServletResponse);
	    }
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("failed ");
	}

	return null;

    }

}
