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

import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.sign.entity.SignedInfo;
import it.people.util.DataPropertyUtils;
import it.people.util.PKCS7Parser;
import it.people.util.PeopleProperties;
import it.people.util.ProcessUtils;
import it.people.util.attach.UtilityAttach;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.xerces.utils.Base64;

/**
 * 
 * User: sergio Date: Oct 10, 2003 Time: 7:59:34 PM <br>
 * <br>
 * Salva i file caricati dall'utente. La classe controlla se il file ? firmato e
 * si comporta di conseguenza.
 */
public class SaveUploadFile extends Action {

    public static String PARAMETER_UPLOADDESCRIPTION = "uploadDescription";
    public static String PARAMETER_UPLOADNAME = "uploadName";
    public static String PARAMETER_PROPERTYNAME = "propertyName";
    public static String PARAMETER_SERIALIZETOPROPERTY = "serializeToProperty";
    public static String PARAMETER_UPLOADFILE = "uploadFile";

    private static Logger logger = Logger.getLogger(SaveUploadFile.class);

    /**
     * Salva il file in upload come SignedAttachment se si tratta di un file
     * firmato.
     * 
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
	AbstractPplProcess pplProcess = (AbstractPplProcess) form;

	FormFile ff = null;
	try {
	    ff = (FormFile) PropertyUtils.getProperty(pplProcess.getData(),
		    PARAMETER_UPLOADFILE);
	} catch (Exception ex) {
	    logger.error(ex);
	}
	/**
	 * ANDREA ZOPPELLO: MODIFICA IN SEGUITO AD ERRORI DI SERIALIZZAZIONE DI
	 * BETWITX
	 * 
	 * SE NON RIPULISCO IL CAMPO data.uploadFile QUANDO L'OGGETTO VIENE
	 * SERIALIZZATO CON BETWIXT CI POSSONO ESSERE ERRORI DI OUT OF MEMORY IN
	 * QUANTO BETWIXT SERIALIAZZA OGNI bye come <byte>1</byte> UN FILE DA 1M
	 * PRODUCE 1024 x 1024 RIGHE NELLA STRINGA CHE VA NEL DB PER QUESTO UNA
	 * VOLTA RICAVATA LA PROPRIETA LA METTO A NULL
	 * 
	 */

	// Una volta che ho ricavato il campo lo metto a null nel PplData
	try {
	    PropertyUtils.setProperty(pplProcess.getData(), "uploadFile", null);
	} catch (Exception ex) {
	    logger.error(ex);
	}

	if (ff != null && ff.getFileSize() > 0) {
	    String basePathName = PeopleProperties.UPLOAD_DIRECTORY
		    .getValueString(pplProcess.getCommune().getKey());
	    String originalFileName = ff.getFileName();

	    try {
		/**
		 * Carico il file su in bytearray
		 */
		// String nuovaGestioneFile =
		// request.getSession().getServletContext().getInitParameter("remoteAttachFile");
		// if (nuovaGestioneFile!=null &&
		// nuovaGestioneFile.equalsIgnoreCase("true")){
		if (!pplProcess.isEmbedAttachmentInXml()) {
		    File rootDirNodo = new File(basePathName + File.separator
			    + pplProcess.getCommune().getKey());
		    if (!rootDirNodo.exists()) {
			rootDirNodo.mkdir();
		    }
		    File rootDirPratica = new File(basePathName
			    + File.separator + pplProcess.getCommune().getKey()
			    + File.separator
			    + pplProcess.getIdentificatoreProcedimento());
		    if (!rootDirPratica.exists()) {
			rootDirPratica.mkdirs();
		    }
		    basePathName = basePathName + File.separator
			    + pplProcess.getCommune().getKey() + File.separator
			    + pplProcess.getIdentificatoreProcedimento();
		}

		File uploadFile = null;
		// if (nuovaGestioneFile!=null &&
		// nuovaGestioneFile.equalsIgnoreCase("true")){
		if (!pplProcess.isEmbedAttachmentInXml()) {
		    uploadFile = new File(basePathName, getUniqueFileName()
			    + "_" + ff.getFileName());
		} else {
		    uploadFile = new File(basePathName, getUniqueFileName());
		}
		String data = null;

		if (!uploadFile.exists()) {
		    uploadFile.createNewFile();
		}

		ByteArrayOutputStream boas = new ByteArrayOutputStream();

		InputStream stream = ff.getInputStream();
		int bytesRead = 0;

		byte[] buffer = new byte[8192];
		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
		    boas.write(buffer, 0, bytesRead);
		}

		stream.close();

		try {
		    /**
		     * Controllo se il file e' firmato
		     */
		    byte[] fileContent = boas.toByteArray();

		    PKCS7Parser p7mParser = new PKCS7Parser(fileContent);

		    Attachment newFile;

		    if (p7mParser.isP7mFile()) {
			p7mParser.decode(new SignedInfo());
			/**
			 * Se e' firmato lo salvo come SignedAttachment
			 */
			logger.debug("Saving SignedAttachment: "
				+ originalFileName);
			newFile = new SignedAttachment(originalFileName,
				uploadFile, new String(
					Base64.encode((byte[]) p7mParser
						.getSigner().get(0))));

			OutputStream bos = new FileOutputStream(uploadFile);
			bos.write(fileContent);
			bos.close();
		    } else {
			/**
			 * Se non e' firmato lo salvo come Attachment
			 */
			logger.debug("Saving Attachment: " + originalFileName);
			newFile = new Attachment(originalFileName, uploadFile);

			OutputStream bos = new FileOutputStream(uploadFile);
			bos.write(fileContent);
			bos.close();
		    }

		    newFile.setFileLenght(fileContent.length);

		    String attachmenDescription = request
			    .getParameter(PARAMETER_UPLOADDESCRIPTION);

		    if (!GenericValidator.isBlankOrNull(attachmenDescription))
			newFile.setDescrizione(attachmenDescription);
		    else
			newFile.setDescrizione(originalFileName);

		    String uploadName = request
			    .getParameter(PARAMETER_UPLOADNAME);

		    if (!GenericValidator.isBlankOrNull(uploadName))
			newFile.setName(uploadName);
		    else
			newFile.setName(originalFileName);

		    // Aggiungere la chiamata alla proprieta di add
		    String propertyName = request
			    .getParameter(PARAMETER_PROPERTYNAME);
		    if (propertyName != null && propertyName.length() > 0) {
			Method mtd = DataPropertyUtils.getAllegatiAddMethod(
				pplProcess.getData().getClass(), propertyName);
			if (mtd == null) {
			    mtd = DataPropertyUtils.getSetMethod(pplProcess
				    .getData().getClass(), propertyName);
			}
			mtd.invoke(pplProcess.getData(),
				new Object[] { newFile });
		    }

		    //
		    // -- Andrea Zoppello
		    //
		    // Se e' specificato il parametro serializeToProperty
		    // serializzo l'allegato sulla property specificata
		    //
		    String serializeToProperty = request
			    .getParameter(PARAMETER_SERIALIZETOPROPERTY);

		    if ((serializeToProperty != null)
			    && (serializeToProperty.trim().length() >= 0)) {
			if (p7mParser.isP7mFile()) {
			    PropertyUtils.setProperty(pplProcess,
				    serializeToProperty, Base64
					    .encode((byte[]) p7mParser
						    .getSigner().get(0)));
			} else {
			    PropertyUtils.setProperty(pplProcess,
				    serializeToProperty,
				    Base64.encode((byte[]) fileContent));
			}
		    } else {
			data = new String(Base64.encode(fileContent));
			// if (nuovaGestioneFile!=null &&
			// nuovaGestioneFile.equalsIgnoreCase("true")){
			if (!pplProcess.isEmbedAttachmentInXml()) {
			    UtilityAttach ua = new UtilityAttach(pplProcess,
				    request);
			    String metaData = ua.getMetaInfoFile(newFile);
			    newFile.setData(metaData);
			} else {
			    newFile.setData(data);
			}
		    }

		} catch (Exception p7mEx) {
		    logger.error(p7mEx);
		}
	    } catch (FileNotFoundException fnfe) {
		logger.error(fnfe);
		return mapping.findForward("failed");
	    } catch (IOException ioe) {
		logger.error(ioe);
		return mapping.findForward("failed");
	    }
	}
	logger.debug("success");
	return mapping.findForward("success");
    }

    /**
     * ritorna un nome univoco per il file.
     * 
     * @return
     */
    private String getUniqueFileName() {
	return Long.toString((new Date()).getTime());
    }
}
