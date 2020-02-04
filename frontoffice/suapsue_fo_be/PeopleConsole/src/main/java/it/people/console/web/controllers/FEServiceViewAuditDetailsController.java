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
package it.people.console.web.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import it.people.console.beans.support.JasperReportSettings;
import it.people.console.beans.support.P12UploadItem;
import it.people.console.domain.AuditConversationDetails;
import it.people.console.jasper.IJasperReportView;
import it.people.console.security.CryptoUtils;
import it.people.console.security.ICryptoUtils;
import it.people.console.security.ITrustStoreUtils;
import it.people.console.security.exceptions.CertAliasException;
import it.people.console.security.exceptions.CredentialsOrCertTypeException;
import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.security.exceptions.SignCorruptionException;
import it.people.console.utils.Base64;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.utils.MimeTypeFinder;
import it.people.console.web.controllers.utils.AuditConversationsViewer;
import it.people.console.web.controllers.utils.AuditConversationsViewer.AuditConversationsViewerException;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.feservice.beans.AuditConversationsBean;
import it.people.feservice.beans.AuditFeBeXmlBean;
import it.people.feservice.beans.AuditUserBean;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 16/giu/2011 11.41.33
 *
 */
@Controller
@RequestMapping("/ServiziFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY})
public class FEServiceViewAuditDetailsController extends MessageSourceAwareController {
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IJasperReportView jasperGenericReport;
	
	@Autowired
	private ITrustStoreUtils trustStoreUtils;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	private AuditConversationsViewer viewer = null;

	public FEServiceViewAuditDetailsController() {
		this.setCommandObjectName("auditDetail");
	}
	
    @RequestMapping(value = "/viewAuditConversation.do", method = RequestMethod.GET)
	public String setupForm(@RequestParam String action,
			@RequestParam String id, 
			@RequestParam String communeid, 
			@RequestParam String serviceid,  
			ModelMap model, 
			HttpServletRequest request) {

    	model.addAttribute("serviceid", serviceid);	

    	createAuditViewer(communeid); 
    	AuditConversationsBean bean = getAuditConversation(id);
    	AuditConversationDetails details = getAuditDetails(bean);
    	
    	//verifico se il certificato è stato già fornito
    	details = checkAuditKeyProvided(request, details);
    	model.addAttribute("auditDetail", details);
    	
    	model.addAttribute("p12UploadItem", new P12UploadItem());

     	JasperReportSettings jasperReportSettings = new JasperReportSettings(); 	
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());   	
    	
    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);	

    	this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
    	
		return getStaticProperty("feservices.auditConversationDetails.view");
    	
    }
    
    @RequestMapping(value = "/viewAuditConversation.do", method = RequestMethod.POST )
    public String processSubmit( 
    		@RequestParam String id, 
    		@RequestParam String communeid, 
			ModelMap model,
    		@RequestParam String serviceid,	
    		P12UploadItem p12UploadItem, 
    		BindingResult result, 
    		HttpServletRequest request, 
			HttpServletResponse response) {
    	
    	boolean isCancel = isPrefixParamInRequest(request, "cancel");
    	
		boolean isBack = isPrefixParamInRequest(request, "back"); //torna alla pagina da report
		boolean isGenerateReport = this.isParamInRequest(request, "generateReport");
		model.addAttribute("reportGenerationErrorMessage", null);
		model.addAttribute("reportGenerationErrorException", null);
 
    	if(isCancel){
    		model.addAttribute("action", "viewAuditConversations");
    		model.addAttribute("id", serviceid);
    		model.addAttribute("plhId", "nodesList");
    		model.addAttribute("detail", true);
    		return "redirect:viewAuditConversations.do";
    	 
    	} else if (isGenerateReport) {
			String reportType = request.getParameter("reportSettings.reportType");
			
			if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.PDF_TYPE_KEY)) {
				//PDF
				try {
					jasperGenericReport.renderReport(reportType, "auditDetails", 
							getJRDataSource(id), null, "audit", request, response);
					
				} catch (Exception je) {
					
					createAuditViewer(communeid); 
					AuditConversationsBean bean = getAuditConversation(id);
					AuditConversationDetails details = getAuditDetails(bean);

					model.addAttribute("id", id);
					model.put("includeTopbarLinks", true);
					model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
					model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);	  

					model.addAttribute("reportGenerationErrorMessage", "Si è verificato un errore durante la generazione del report.");
					model.addAttribute("reportGenerationErrorException", CastUtils.exceptionToString(je));
					
					// in caso di errore è necessario gestire anche qua l'upload del certificato
					JasperReportSettings jasperReportSettings = new JasperReportSettings();
					model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
					
					// verifico se il certificato è stato già fornito
					details = checkAuditKeyProvided(request, details);
					model.addAttribute("auditDetail", details);
					
					if (p12UploadItem.getFileData() != null && 
							!p12UploadItem.getFileData().isEmpty() && isBack==false) {

						return p12Manager(model, p12UploadItem, result, request, details);
									
					} else {
						if(!isBack && !isGenerateReport){
							result.addError(new ObjectError("p12UploadItem", 
									this.getProperty("error.certificate.noFileUploaded")));
						}
						this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
						return getStaticProperty("feservices.auditConversationDetails.view");

					}

				}
				
				model.put("includeTopbarLinks", true);
				model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
				model.addAttribute("pdf_complete_path", 
						String.valueOf(request.getSession().
								getAttribute(Constants.Reports.REPORT_FILE_PREVIEW_SESSION_KEY)));
				return getStaticProperty("reports.page.view");
				
			} else {
			
				//CSV - HTML - XLS
				try{
					saveReport(id, request, response, reportType);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				createAuditViewer(communeid); 
				AuditConversationsBean bean = getAuditConversation(id);
				AuditConversationDetails details = getAuditDetails(bean);
				
				model.addAttribute("id", id);
				model.put("includeTopbarLinks", true);
				model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
				model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);	  

				// è necessario gestire anche qua l'upload del certificato
				JasperReportSettings jasperReportSettings = new JasperReportSettings();
				model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
				
				// verifico se il certificato è stato già fornito
				details = checkAuditKeyProvided(request, details);
				model.addAttribute("auditDetail", details);
				
				if (p12UploadItem.getFileData() != null && 
						!p12UploadItem.getFileData().isEmpty() && isBack==false) {

					return p12Manager(model, p12UploadItem, result, request, details);
								
				} else {
					if(!isBack && !isGenerateReport){
						result.addError(new ObjectError("p12UploadItem", 
								this.getProperty("error.certificate.noFileUploaded")));
					}
					this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
					return getStaticProperty("feservices.auditConversationDetails.view");

				}
			}

		} else { 

			model.addAttribute("communeid", communeid);

			createAuditViewer(communeid); 
	    	AuditConversationsBean bean = getAuditConversation(id);
	    	AuditConversationDetails details = getAuditDetails(bean);
	    	
	    	model.addAttribute("id", id);
	    	model.put("includeTopbarLinks", true);
	    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
	    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);	  
	    	
	    	// gestione upload del certificato
	    	JasperReportSettings jasperReportSettings = new JasperReportSettings();
	    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());
		
	    	// verifico se il certificato è stato già fornito
	    	details = checkAuditKeyProvided(request, details);
	    	model.addAttribute("auditDetail", details);
	    	
	    	if (p12UploadItem.getFileData() != null && 
	    			!p12UploadItem.getFileData().isEmpty() && !isBack) {
	
	        	return p12Manager(model, p12UploadItem, result, request, details);
	                		
	    	} else {
				if(!isBack){
					result.addError(new ObjectError("p12UploadItem", 
							this.getProperty("error.certificate.noFileUploaded")));
				}
	
				this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
				
	    		return getStaticProperty("feservices.auditConversationDetails.view");
	
	    	}
    	}
	}

	/**
	 * @param request
	 * @param details
	 * @return
	 */
	private AuditConversationDetails checkAuditKeyProvided(
			HttpServletRequest request, AuditConversationDetails details) {
		
		if(details.getC_includexml()!=0){
			
			//verifico se il certificato è stato già fornito
			if(request.getSession().getAttribute("AuditKeyProvided")!=null){
				details = getAuditFeBeXml(details);
			}
		}
		return details;
	}

	/**
	 * @param id
	 * @param request
	 * @param response
	 * @param reportType
	 * @throws Exception
	 * @throws IOException
	 */
	private void saveReport(String id, HttpServletRequest request,
			HttpServletResponse response, String reportType) throws Exception,
			IOException {
		String fileName = "dettaglio_conversazione";
		
		if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.CSV_TYPE_KEY)  ||
			reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.HTML_TYPE_KEY) ||
			reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.XLS_TYPE_KEY) ){

			fileName+="."+reportType.toLowerCase();
		}
				
		byte[] output = jasperGenericReport.saveReport(reportType, 
				"auditDetails", 
				getJRDataSource(id), null, "audit", request, response);
		
		InputStream inStream = new ByteArrayInputStream(output);
		response.setHeader("Pragma", "private");
		response.setHeader("Cache-Control", "private, must-revalidate");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		ServletOutputStream out = response.getOutputStream();
		 
		while(inStream.read(output) != -1) {
			out.write(output);
		}
		inStream.close();
		out.flush();
		out.close();
	}

	/**
	 * @param model
	 * @param p12UploadItem
	 * @param result
	 * @param request
	 * @param details
	 * @return
	 */
	private String p12Manager(ModelMap model, P12UploadItem p12UploadItem,
			BindingResult result, HttpServletRequest request,
			AuditConversationDetails details) {
		String tmpFileName = "tmpFile_" + request.getSession().getId();
		String mimeType = "";

		File tmpFile = new File(WebUtils.getTempDir(request.getSession().getServletContext()), tmpFileName);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
			fileOutputStream.write(p12UploadItem.getFileData().getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();

			FileInputStream fileInputStream = new FileInputStream(tmpFile);
			byte[] mimeBytes = new byte[8];
			fileInputStream.read(mimeBytes);
			fileInputStream.close();
			
			mimeType = MimeTypeFinder.findMymeType(mimeBytes);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean trustedCertificate = false;
		boolean validP12andPassword = false;
		boolean validMimeType = mimeType.equalsIgnoreCase(MimeTypeFinder.P12_MIME_TYPE);
		Vector<ObjectError> objectErrors = null;
		if (validMimeType) {
			try {
				validP12andPassword = this.getTrustStoreUtils().isValidP12Password(tmpFile.getAbsolutePath(), p12UploadItem.getPassword());
				objectErrors = this.getTrustStoreUtils().isTrustedCertificate(tmpFile.getAbsolutePath(), p12UploadItem.getPassword(), "p12UploadItem");
			} catch (CryptoUtilsException e) {
				e.printStackTrace();
			} catch (CredentialsOrCertTypeException e) {
				e.printStackTrace();
			} catch (CertAliasException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		trustedCertificate = objectErrors != null && objectErrors.isEmpty();
		
		if (!validMimeType) {
			result.addError(new ObjectError("p12UploadItem", 
					this.getProperty("error.certificate.invalidFileType")));
			this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
			return getStaticProperty("feservices.auditConversationDetails.view");
		} else if (!validP12andPassword) {
			result.addError(new ObjectError("p12UploadItem", 
					this.getProperty("error.certificate.invalidPassword")));
			this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
			return getStaticProperty("feservices.auditConversationDetails.view");
		} else if (!trustedCertificate) {
			result.addError(new ObjectError("p12UploadItem", 
					this.getProperty("error.certificate.untrusted")));
			this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
			return getStaticProperty("feservices.auditConversationDetails.view");
		} else {
			
			request.getSession().setAttribute("AuditKeyProvided", true);
			details = getAuditFeBeXml(details);
			model.addAttribute("auditDetail", details);
			
			this.setPageInfo(model, "auditConversationDetails.title", null, "feServiceVAD");
			return getStaticProperty("feservices.auditConversationDetails.view");
		}
	}
    	
    
    
	/**
	 * @param id
	 * @return l'AuditConversationsViewer per il nodo id
	 */
	private void createAuditViewer(String id) {
		
		/* Trovo il reference del web service */
		String[] reference_communeid = getReferenceCommuneid(id);
		try {
			viewer = new AuditConversationsViewer(new URL(reference_communeid[0]), reference_communeid[1],
					dataSourcePeopleDB);
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
		}
	}
	
	/**
	 * @param id
	 * @return reference e codicecomune, parametri per la connessione
	 */
	private String[] getReferenceCommuneid(String id) {

		String[] references = new String[2];

        String query = "SELECT codicecomune, reference FROM fenode WHERE id = " + id;
        
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				references[0] = resultSet.getString("reference");
				references[1] = resultSet.getString("codicecomune");
			}
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return references;
	}
    
	/**
	 * @param convBean
	 */
	private AuditConversationDetails getAuditDetails(AuditConversationsBean convBean) {
		
		String userId = convBean.getAudit_users_ref();
		String userAccrId = convBean.getAudit_users_accr();
		
		AuditUserBean userBean = new AuditUserBean();
		try {
			userBean = viewer.getAuditUser(userId, userAccrId);
		} catch (AuditConversationsViewerException e) {
			logger.error("Non è stato possibile recuperare l'utente. " + e.getMessage());
		}
		
		AuditConversationDetails details = new AuditConversationDetails();
		if(userBean.getAnon_user()==1){
			details.setAnon_user(true);
		} else {
			details.setNp_first_name(userBean.getNp_first_name());
			details.setNp_last_name(userBean.getNp_last_name());
			details.setNp_tax_code(userBean.getNp_tax_code());
			details.setNp_address(userBean.getNp_address());
			details.setNp_e_address(userBean.getNp_e_address());
			
		}
		
		details.setC_action(convBean.getAction_name());
		details.setC_timestamp_date(convBean.getAudit_timestamp().getTime());
		details.setC_message(convBean.getMessage());
		details.setC_includexml((long) convBean.getInclude_audit_febe_xml());
		details.setDescr_qualifica(userBean.getDescr_qualifica());
		details.setTipo_qualifica(userBean.getTipo_qualifica());

		Base64 base64 = new Base64();

//		//fare come richiedente servizio (ma forse è inutile)
//		ProfiloPersonaFisica operatore; 
//		String strOperatore = userBean.getOperatore();
//		if(strOperatore!=null){
//				operatore = (ProfiloPersonaFisica) unmarshall(ProfiloPersonaFisica.class, new String(base64.decode(strOperatore)));
//				if(operatore!=null){
//					details.setOperatore(operatore.getCodiceFiscale());
//				}
//		}
		
		//Richiedente il servizio
		ProfiloPersonaFisica richiedente = null; 
		String strRichiedente = userBean.getRichiedente();
		String CFrichiedente = "";
		if(strRichiedente!=null){
			byte[] buf_Richiedente = base64.decode(strRichiedente);
			if (buf_Richiedente != null) {
				ObjectInputStream objectIn;
				try {
					objectIn = new ObjectInputStream(new ByteArrayInputStream(buf_Richiedente));
					richiedente = (ProfiloPersonaFisica) objectIn.readObject();
					objectIn.close();
				} catch (IOException e) {
					logger.error("", e);
				} catch (ClassNotFoundException e) {
					logger.error("", e);
				}
			}
			if(richiedente.getCodiceFiscale().equalsIgnoreCase(userBean.getNp_tax_code())){
				//richiedente = utente (operatore)
				details.setRichiedente_utente(true);
//				details.setRichiedente("vedere utente");
			} else {
				details.setRichiedente(".");
				details.setRichiedente_utente(false);
				details.setRichiedente_first_name(richiedente.getNome().toUpperCase());
				details.setRichiedente_last_name(richiedente.getCognome().toUpperCase());
				CFrichiedente = richiedente.getCodiceFiscale().toUpperCase();
				details.setRichiedente_tax_code(CFrichiedente);
				details.setRichiedente_address(richiedente.getIndirizzoResidenza());
				details.setRichiedente_e_address(richiedente.getDomicilioElettronico());
			}
		}
		
		
		//Titolare del servizio
		String strTitolare = userBean.getTitolare();
		if(strTitolare!=null){
			byte[] buf_Titolare = base64.decode(strTitolare);
			AbstractProfile titolare = null;
			if (buf_Titolare != null) {
				ObjectInputStream objectIn;
				try {
					objectIn = new ObjectInputStream(new ByteArrayInputStream(buf_Titolare));
					titolare = (AbstractProfile) objectIn.readObject();
					objectIn.close();
				} catch (IOException e) {
					logger.error("", e);
				} catch (ClassNotFoundException e) {
					logger.error("", e);
				}
			}
			
			
			if (titolare.isPersonaFisica()) {
				details.setTitolare_pf(true);
				ProfiloPersonaFisica profiloTitolarePF = (ProfiloPersonaFisica) titolare;
				if (profiloTitolarePF.getCodiceFiscale().equalsIgnoreCase(userBean.getNp_tax_code())) {
					// titolare = utente (operatore)
//					details.setTitolare_utente(true);
//					details.setTitolare("vedere utente");
				} else if (profiloTitolarePF.getCodiceFiscale().equalsIgnoreCase(CFrichiedente)) {
					// titolare = richiedente
//					details.setTitolare_utente(true);
					details.setTitolare_richiedente(true);
//					details.setTitolare("vedere richiedente");
				} else {
					details.setTitolare(".");
					details.setTitolare_utente(false);
					details.setTitolare_first_name(profiloTitolarePF.getNome().toUpperCase());
					details.setTitolare_last_name(profiloTitolarePF.getCognome().toUpperCase());
					details.setTitolare_tax_code(profiloTitolarePF.getCodiceFiscale().toUpperCase());
					details.setTitolare_address(profiloTitolarePF.getIndirizzoResidenza());
					details.setTitolare_e_address(profiloTitolarePF.getDomicilioElettronico());
				}
				
				
			} else if (titolare.isPersonaGiuridica()) {
				details.setTitolare_pf(false);
				details.setTitolare(".");
				ProfiloPersonaGiuridica profiloTitolarePG = (ProfiloPersonaGiuridica) titolare;
				
				details.setTitolare_pg_business_name(profiloTitolarePG.getDenominazione());
				details.setTitolare_pg_address(profiloTitolarePG.getSedeLegale());
				details.setTitolare_pg_tax_code(profiloTitolarePG.getCodiceFiscale());
				details.setTitolare_pg_vat_number(profiloTitolarePG.getPartitaIva());
				
				ProfiloPersonaFisica rappLegale = profiloTitolarePG.getRappresentanteLegale();
				if(rappLegale.getCodiceFiscale().equalsIgnoreCase(userBean.getNp_tax_code())){
					//rappLegale = utente (operatore)
					details.setRapprLegale_utente(true);
					details.setRapprLegale("vedere utente");
				} else {
					details.setRapprLegale_utente(false);
					details.setTitolare_first_name(rappLegale.getNome().toUpperCase());
					details.setTitolare_last_name(rappLegale.getCognome().toUpperCase());
					details.setTitolare_tax_code(rappLegale.getCodiceFiscale().toUpperCase());
					details.setTitolare_address(rappLegale.getIndirizzoResidenza());
					details.setTitolare_e_address(rappLegale.getDomicilioElettronico());
					
				}
			}
		}

		return details;
	}
	

	private AuditConversationsBean getAuditConversation(String id) {
		
		AuditConversationsBean bean = new AuditConversationsBean();
		try {
			bean = viewer.getAuditConversation(id);
		} catch (AuditConversationsViewerException e) {
			logger.error("Non è stato possibile recuperare la conversazione. " + e.getMessage());
		}
		
		return bean;
	}
	
	
	private AuditConversationDetails getAuditFeBeXml(AuditConversationDetails details) {
		
		String idXml = Long.toString(details.getC_includexml());
		
		AuditFeBeXmlBean bean = new AuditFeBeXmlBean();
		try {
			bean = viewer.getAuditFeBeXml(idXml);
		} catch (AuditConversationsViewerException e) {
			logger.error("Non è stato possibile recuperare la conversazione. " + e.getMessage());
		}
		
		details.setC_includexml((long) bean.getId()); 
		
		String xmlIn_encrypted = bean.getXmlIn(); 
		String xmlOut_encrypted = bean.getXmlOut(); 
		
		ICryptoUtils cryptoUtils = null;
		try {
			cryptoUtils = new CryptoUtils();
		} catch (CryptoUtilsException e) {
		
			e.printStackTrace();
		}
		
		String xmlIn_decrypted = null;
		String xmlOut_decrypted = null;
		
		try {
			xmlIn_decrypted = cryptoUtils.getDecryptedData(xmlIn_encrypted);
			xmlOut_decrypted = cryptoUtils.getDecryptedData(xmlOut_encrypted);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (CryptoUtilsException e) {
			e.printStackTrace();
		} catch (SignCorruptionException e) {
			e.printStackTrace();
		}
		
		details.setXmlIn(xmlIn_decrypted);
		details.setXmlOut(xmlOut_decrypted);
		
		return details;
		
	}
	
	/**
	 * @return the trustStoreUtils
	 */
	private ITrustStoreUtils getTrustStoreUtils() {
		return trustStoreUtils;
	}
	/**
	 * @param idcommune
	 * @return
	 */
	private JRDataSource getJRDataSource(String id) {
		
    	AuditConversationsBean bean = getAuditConversation(id);
    	AuditConversationDetails details = getAuditDetails(bean);
		List<AuditConversationDetails> list = new ArrayList<AuditConversationDetails>();
		list.add(details);
		
		return new JRBeanCollectionDataSource(list);
		
	}
}
