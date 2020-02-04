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
package it.people.console.web.controllers.users;

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriUtils;

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.ListColumnFilter;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.config.ConsoleConfiguration;
import it.people.console.domain.CertificatesAccountManager;
import it.people.console.domain.PairElement;
import it.people.console.domain.UserAccount;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.CertificateAccountDTO;
import it.people.console.dto.IMailAttachment;
import it.people.console.dto.MailAttachment;
import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.dto.UserAccountDTO;
import it.people.console.enumerations.EqualityOperators;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.persistence.jdbc.support.NullToStringConverter;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.ITrustStoreUtils;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;
import it.people.console.security.certificates.ICertificatesManager;
import it.people.console.system.mail.MailSender;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.controllers.exceptions.XMLCertificateManagerCertificateGenerationException;
import it.people.console.web.controllers.exceptions.XMLCertificateManagerCertificateToTruststoreException;
import it.people.console.web.controllers.validator.CertificatesManagerValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.servlet.tags.TagsConstants;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 26/giu/2011 11.45.25
 *
 */
@Controller
@RequestMapping("/Amministrazione/Utenti/Certificati")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "certificatesAccountManager", "certificateAccountDTO"})
public class XMLCertificatesManagerController extends AbstractListableController {

	private CertificatesManagerValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private ICertificatesManager certificatesManager;

	@Autowired
	private ITrustStoreUtils trustStoreUtils;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	private String usersWithCertificateQuery = "select users.id, users.tax_code 'Codice fiscale', users.first_name 'Nome', users.last_name 'Cognome', users.email 'E-mail', usersCert.alias 'Alias', (cast(usersCert.validity  as signed integer) - datediff(now(), usersCert.generationTimestamp)), if((cast(usersCert.validity  as signed integer) - datediff(now(), usersCert.generationTimestamp)) <= 0, 1, 0) 'Scaduto' from pc_users users join pc_users_certificates usersCert on usersCert.userRef = users.id";
	
	private String usersWithoutCertificateQuery = "select users.id, users.tax_code 'Codice fiscale', users.first_name 'Nome', users.last_name 'Cognome' from pc_users users join pc_users_authorities usersAuth on usersAuth.userRef = users.id join pc_authorities_catalog authCatalog on authCatalog.id = usersAuth.authorityRef where users.id not in (select userRef from pc_users_certificates) and authCatalog.authority = 'SECURITY_XML_LOGS_VIEWER'";
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	private Collection<PairElement<String, String>> nodesList = null;
	
	@Autowired
	public XMLCertificatesManagerController(CertificatesManagerValidator validator) {
		this.validator = validator;
	}
	
    @RequestMapping(value = "/certificatiAccessoXML.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	this.setCommandObjectName("certificatesAccountManager");
    	CertificatesAccountManager certificatesAccountManager = null;
    	
    	if (request.getSession().getAttribute("certificatesAccountManager") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("certificatiAccessoXML.do")) {
    		certificatesAccountManager = new CertificatesAccountManager();

        	try {

        		Map<String, Decodable> converters = new HashMap<String, Decodable>();
        		converters.put("first_name", new NullToStringConverter(""));
        		converters.put("last_name", new NullToStringConverter(""));
        		
        		List<String> rowColumnsIdentifiersAccountsWithoutCertificates = new ArrayList<String>();
        		rowColumnsIdentifiersAccountsWithoutCertificates.add("id");

        		ILazyPagedListHolder pagedListHolderAccountsWithoutCertificates = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.ACCOUNTS_WITHOUT_CERTIFICATES_LIST, dataSourcePeopleDB, usersWithoutCertificateQuery, 10, 
    					rowColumnsIdentifiersAccountsWithoutCertificates, getUsersWithoutCertificateRowActions());
        		
        		List<String> visibleColumnsNamesAccountsWithoutCertificates = new ArrayList<String>();
        		visibleColumnsNamesAccountsWithoutCertificates.add("tax_code");
        		visibleColumnsNamesAccountsWithoutCertificates.add("first_name");
        		visibleColumnsNamesAccountsWithoutCertificates.add("last_name");
        		pagedListHolderAccountsWithoutCertificates.setVisibleColumnsNames(visibleColumnsNamesAccountsWithoutCertificates);
        		
        		pagedListHolderAccountsWithoutCertificates.setConverters(converters);

        		certificatesAccountManager.addPagedListHolder(pagedListHolderAccountsWithoutCertificates);


        		
        		
        		List<String> rowColumnsIdentifiersAccounts = new ArrayList<String>();
        		rowColumnsIdentifiersAccounts.add("id");

        		ILazyPagedListHolder pagedListHolderAccounts = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.CERTIFICATE_ACCOUNTS_LIST, dataSourcePeopleDB, usersWithCertificateQuery, 10, rowColumnsIdentifiersAccounts, getRowActions());
        		
        		List<String> visibleColumnsNamesAccounts = new ArrayList<String>();
        		visibleColumnsNamesAccounts.add("tax_code");
        		visibleColumnsNamesAccounts.add("first_name");
        		visibleColumnsNamesAccounts.add("last_name");
        		visibleColumnsNamesAccounts.add("email");
        		visibleColumnsNamesAccounts.add("alias");
        		visibleColumnsNamesAccounts.add("Scaduto");
        		pagedListHolderAccounts.setVisibleColumnsNames(visibleColumnsNamesAccounts);
        		
        		pagedListHolderAccounts.setConverters(converters);

        		certificatesAccountManager.addPagedListHolder(pagedListHolderAccounts);
        		
    		} catch (PagedListHoldersCacheException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
    	else {

    		certificatesAccountManager = (CertificatesAccountManager)request.getSession().getAttribute("certificatesAccountManager");
    		processListHoldersRequests(request.getQueryString(), certificatesAccountManager);
    		
    	}

    	try {
			applyColumnSorting(request, certificatesAccountManager);
		} catch (LazyPagedListHolderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("certificatesAccountManager", certificatesAccountManager);

//		if (rolesList == null) {
//			rolesList = this.getAllRoles();
//		}
    	
//    	model.addAttribute("rolesList", rolesList);
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "admin.certificates.listAndAdd.title", null, "certificatesM");
    	
    	return getStaticProperty("accounts.certificates.liastAndAdd.view");
    	
    }

    @RequestMapping(value = "/certificatiAccessoXML.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("certificatesAccountManager") CertificatesAccountManager certificatesAccountManager, BindingResult result, 
    		HttpServletRequest request) {

    	this.setCommandObjectName("certificatesAccountManager");
    	boolean isSaveInRequest = isParamInRequest(request, "saveNewAccount");
    	
    	if (isSaveInRequest) {
        	validator.validate(certificatesAccountManager, result);
    	}

    	if (logger.isDebugEnabled()) {
    		logger.debug("Saving filters state...");
    	}
    	List<FilterProperties> updatedAppliedFilters = updateAppliedFilters(request, filtersList);
    	if (logger.isDebugEnabled()) {
    		logger.debug("Saving filters state done.");
    	}
    	
    	if (isParamInRequest(request, "applyFilters")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Applying " + updatedAppliedFilters.size() + " active filters...");
        	}
        	try {
        		certificatesAccountManager.getPagedListHolder(Constants.PagedListHoldersIds.CERTIFICATE_ACCOUNTS_LIST).applyFilters(updatedAppliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
    	}

    	if (isParamInRequest(request, "clearFilters")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Clearing " + updatedAppliedFilters.size() + " active filters...");
        	}
        	try {
        		certificatesAccountManager.getPagedListHolder(Constants.PagedListHoldersIds.CERTIFICATE_ACCOUNTS_LIST).removeFilters();
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters cleared.");
        	}
    	}
    	
    	if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, certificatesAccountManager, model);
    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:conferma.do";
    		}
    	}
    	
    	if (isSaveInRequest && !result.hasErrors()) {
    		certificatesAccountManager.clear();
    		certificatesAccountManager.getPagedListHolder(Constants.PagedListHoldersIds.CERTIFICATE_ACCOUNTS_LIST).update();
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("certificatesAccountManager", certificatesAccountManager);

//    	model.addAttribute("rolesList", rolesList);
    	
    	setRowsPerPageDefaultModelAttributes(model);

    	this.setPageInfo(model, "admin.certificates.listAndAdd.title", null, "certificatesM");
    	
		return getStaticProperty("accounts.certificates.liastAndAdd.view");
    	
    }

    
    
    @RequestMapping(value = "/generaCertificato.do", method = RequestMethod.GET)
    public String setupCertificateGeneration(@RequestParam("id") int userId, 
    		ModelMap model, HttpServletRequest request) {

    	this.setCommandObjectName("certificateAccountDTO");
    	CertificateAccountDTO certificateAccountDTO = prepareCertificateAccountDTO(userId, request);
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("Certificate generation for user id = " + userId);
    	}

		if (nodesList == null) {
			nodesList = new ArrayList<PairElement<String, String>>();
			nodesList.add(new PairElement<String, String>(String.valueOf(Constants.UNBOUND_VALUE), "Qualsiasi"));
			nodesList.addAll(this.persistenceBroker.getRegisteredNodes());
		}
    	
    	model.put("nodesList", nodesList);
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.put("certificateAccountDTO", certificateAccountDTO);

    	this.setPageInfo(model, "admin.certificates.generation.title", null, "certificatesM");

		return getStaticProperty("accounts.certificates.certicateGeneration.view");
    	
    }
    
    @RequestMapping(value = "/generaCertificato.do", method = RequestMethod.POST)
    public String submitCertificateGeneration(ModelMap model, 
    		@ModelAttribute("certificateAccountDTO") CertificateAccountDTO 
    		certificateAccountDTO, BindingResult result, 
    		HttpServletRequest request) {

    	this.setCommandObjectName("certificateAccountDTO");
    	String nextView = "";
    	
    	boolean isGenerateCertificateInRequest = isParamInRequest(request, "generateCertificate");
    	boolean isCancelInRequest = isParamInRequest(request, "cancel");
    	boolean isResetInRequest = isParamInRequest(request, "reset");
    	
    	if (isGenerateCertificateInRequest) {
        	validator.validate(certificateAccountDTO, result);
    	}
    	
    	if (isGenerateCertificateInRequest) {
    		if (!result.hasErrors()) {
//    			request.getSession().setAttribute("certificateAccountDTO", certificateAccountDTO);
        		nextView = "redirect:inviaCertificato.do";
    		} else {
    	    	model.put("certificateAccountDTO", certificateAccountDTO);
    	    	model.put("nodesList", nodesList);
    	    	model.put("includeTopbarLinks", true);
    	    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	    	this.setPageInfo(model, "admin.certificates.generation.title", null, "certificatesM");
        		nextView = getStaticProperty("accounts.certificates.certicateGeneration.view");
    		}
    	} else if (isResetInRequest) {
    		certificateAccountDTO.clear();
	    	model.put("certificateAccountDTO", certificateAccountDTO);
	    	model.put("nodesList", nodesList);
	    	model.put("includeTopbarLinks", true);
	    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
	    	this.setPageInfo(model, "admin.certificates.generation.title", null, "certificatesM");
    		nextView = getStaticProperty("accounts.certificates.certicateGeneration.view");
    	} else if (isCancelInRequest) { 
    		nextView = "redirect:certificatiAccessoXML.do";
    	} else {
    		nextView = "redirect:inviaCertificato.do";
    	}

		return nextView;
    	
    }

    @RequestMapping(value = "/inviaCertificato.do", method = RequestMethod.GET)
    public String setupCertificateGenerated(ModelMap model, @ModelAttribute("certificateAccountDTO") CertificateAccountDTO 
    		certificateAccountDTO, HttpServletRequest request) {

//    	CertificateAccountDTO certificateAccountDTO = (CertificateAccountDTO)request.
//    		getSession().getAttribute("certificateAccountDTO");
    	
    	this.setCommandObjectName("certificateAccountDTO");
    	String downloadFileName = "";
    	if (!CastUtils.objectToBoolean(request.getSession().getAttribute("certificateGenerated"))) {
	    	try {
	    		downloadFileName = generateAndAddCertificateToTrustStore(certificateAccountDTO, request);
	    		request.getSession().setAttribute("certificateGenerated", true);
	    		request.getSession().setAttribute("certificateGeneratedFileName", 
	    				UriUtils.encodeFragment(downloadFileName, "UTF-8"));
			} catch (XMLCertificateManagerCertificateGenerationException e) {
				// TODO Error for certificate generation
				e.printStackTrace();
			} catch (XMLCertificateManagerCertificateToTruststoreException e) {
				// TODO Error for adding certificate to truststore
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Error for adding certificate to truststore
				e.printStackTrace();
			}
    	} else {
    		downloadFileName = (String)request.getSession().getAttribute("certificateGeneratedFileName");
    	}
    	
		
		model.put("messageAdminCertificatesGenerated", 
				this.getProperty("message.admin.certificates.generated", new String[] {
				"<a href=\"/PeopleConsole/download.do?fileName=" + 
				getFriendlyCertificateFileName(certificateAccountDTO) + 
				"&type=p12&key=" + downloadFileName + "\">certificato</a>", 
				certificateAccountDTO.getCommonName(), 
				certificateAccountDTO.getAlias()}));

		model.put("messageAdminCertificateSend", 
				this.getProperty("message.admin.certificates.send", new String[] {
				certificateAccountDTO.geteMail()}));
		
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.put("certificateAccountDTO", certificateAccountDTO);

		return getStaticProperty("accounts.certificates.certicateGenerated.view");
    	
    }

    @RequestMapping(value = "/inviaCertificato.do", method = RequestMethod.POST)
    public String submitCertificateGenerated(ModelMap model, 
    		@ModelAttribute("certificateAccountDTO") CertificateAccountDTO 
    		certificateAccountDTO, BindingResult result, 
    		HttpServletRequest request) {

    	this.setCommandObjectName("certificateAccountDTO");
    	//send data to user by mail

    	if (isParamInRequest(request, "cancel")) {
    		return "redirect:certificatiAccessoXML.do";
    	}

    	MailSender mailSender = new MailSender();
    	
    	IMailAttachment[] attachments = new IMailAttachment[] {
    			generateMailAttachment(certificateAccountDTO)};

    	boolean sentMail = false;
    	if (certificateAccountDTO.isSendDataByMail()) {
	    	try {
	    		sentMail = mailSender.sendMail(request, 
						getInternetAddress(certificateAccountDTO), 
						this.getProperty("certificate.send.mail.subject"), 
						this.getProperty("certificate.send.mail.body", 
								new String[] {certificateAccountDTO.getCommonName(), 
								certificateAccountDTO.getAlias(), 
								certificateAccountDTO.getPassword()}), 
						attachments);
	    		certificateAccountDTO.setSentMail(sentMail);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
		this.persistenceBroker.saveUserCertificate(certificateAccountDTO.getUserId(), 
				certificateAccountDTO.getAlias(), 
				CastUtils.stringToInt(certificateAccountDTO.getValidity()), 
				CastUtils.byteArrayOutputStreamToBase64(certificateAccountDTO.getX509Certificate()), 
				CastUtils.booleanToInt(sentMail));

    	if (certificateAccountDTO.isSendDataByMail()) {
    		return "redirect:risultatoInvioCertificato.do";
    	} else {
    		clearSessionData(request);
    		return "redirect:certificatiAccessoXML.do";
    	}
		    	
    }
    
    
    @RequestMapping(value = "/risultatoInvioCertificato.do", method = RequestMethod.GET)
    public String setupCertificateSentResult(ModelMap model, 
    		@ModelAttribute("certificateAccountDTO") CertificateAccountDTO 
    		certificateAccountDTO, HttpServletRequest request) {

    	this.setCommandObjectName("certificateAccountDTO");
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	String resultMessage = certificateAccountDTO.isSentMail() ? 
    			this.getProperty("message.admin.certificates.sent.successfull", 
    					new String[] {certificateAccountDTO.geteMail()}) : 
    				this.getProperty("message.admin.certificates.sent.unsuccessfull", 
    						new String[] {certificateAccountDTO.geteMail()});
    	model.put("resultMessage", resultMessage);
    	model.put("certificateAccountDTO", certificateAccountDTO);
    	
		return getStaticProperty("accounts.certificates.certicateSentResult.view");
    	
    }
    
    @RequestMapping(value = "/risultatoInvioCertificato.do", method = RequestMethod.POST)
    public String submitCertificateSentResult(ModelMap model, 
    		@ModelAttribute("certificateAccountDTO") CertificateAccountDTO 
    		certificateAccountDTO, HttpServletRequest request) {

    	this.setCommandObjectName("certificateAccountDTO");
    	clearSessionData(request);
    	
		return "redirect:certificatiAccessoXML.do";
    	
    }

    @RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {

    	this.setCommandObjectName("certificatesAccountManager");
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.put("message", "Si desidera eliminare l'elemento?");
    	
    	return getStaticProperty("confirm.view");
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
    public String processConferma(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("certificatesAccountManager") CertificatesAccountManager certificatesAccountManager, BindingResult result, 
    		HttpServletRequest request) {

    	this.setCommandObjectName("certificatesAccountManager");
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("certificatesAccountManager", certificatesAccountManager);

    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
    	
    	if (isConfirmAction) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action confirmed.");
    		}
    		ProcessActionDataHolder processActionDataHolder = this.popProcessActionData(request);
    		int userId = Integer.parseInt((String)processActionDataHolder.getEditableRowInputData().getRowIdentifiers().get("id"));
    		try {
				this.deleteUserCertificate(userId);
			} catch (PersistenceBrokerException e) {
				// TODO Redirect to error page
				e.printStackTrace();
			}
    	} else {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action canceled.");
    		}
    	}
    	
		return "redirect:certificatiAccessoXML.do";
    	
    }
    
    private void clearSessionData(HttpServletRequest request) {
    	request.getSession().removeAttribute("certificateGenerated");
    	request.getSession().removeAttribute("certificateGeneratedFileName");
    }
    
    private String getFriendlyCertificateFileName(CertificateAccountDTO certificateAccountDTO) {
    	return certificateAccountDTO.getAlias() + ".p12";
    }
    
    private IMailAttachment generateMailAttachment(CertificateAccountDTO certificateAccountDTO) {
    	
    	return new MailAttachment(new ByteArrayInputStream(
    			certificateAccountDTO.getX509Certificate().toByteArray()),
    			"application/x-x509-user-cert", getFriendlyCertificateFileName(certificateAccountDTO));
    	
    }
    
    private InternetAddress getInternetAddress(CertificateAccountDTO certificateAccountDTO) throws UnsupportedEncodingException {
    	
    	return new InternetAddress(certificateAccountDTO.geteMail(), 
    			certificateAccountDTO.getCommonName(), "iso-8859-1");
    	
    }
    
    private CertificateAccountDTO prepareCertificateAccountDTO(int userId, HttpServletRequest request) {
    	
    	CertificateAccountDTO result = new CertificateAccountDTO();
 
    	ConsoleConfiguration consoleConfiguration = (ConsoleConfiguration)request.getSession().getServletContext().getAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES);
    	
    	UserAccountDTO userAccountDTO = this.persistenceBroker.getAccountData(userId);
    	if (userAccountDTO != null) {
    		if (!StringUtils.isEmptyString(userAccountDTO.getLastName()) && !StringUtils.isEmptyString(userAccountDTO.getFirstName())) {
        		result.setAlias(userAccountDTO.getLastName().toLowerCase() + "." + 
        				userAccountDTO.getFirstName().toLowerCase());
        		result.setCommonName(userAccountDTO.getLastName() + " " + userAccountDTO.getFirstName());
    		} else {
        		result.setAlias("");
        		result.setCommonName("");
    		}
    		if (!StringUtils.isEmptyString(userAccountDTO.getEMail())) {
        		result.seteMail(userAccountDTO.getEMail());
    		} else {
        		result.seteMail("");
    		}
    		result.setValidity(String.valueOf(consoleConfiguration.getCertificatesStandardValidity()));
    		result.setUserId(userId);
    	}
    	    	
    	return result;
    	
    }
    
    /**
     * @param certificateAccountDTO
     * @throws XMLCertificateManagerCertificateGenerationException
     * @throws XMLCertificateManagerCertificateToTruststoreException
     */
    private String generateAndAddCertificateToTrustStore(CertificateAccountDTO certificateAccountDTO, 
    		HttpServletRequest request) throws XMLCertificateManagerCertificateGenerationException, XMLCertificateManagerCertificateToTruststoreException {
    	
    	String result = "";
    	
    	try {
    		
    		if (logger.isDebugEnabled()) {
    			logger.debug("Generating certificate for user " + certificateAccountDTO.getCommonName() + ".");
    		}
    		
			ByteArrayOutputStream certificateOutputStream = this.certificatesManager.
				generateCertificate(certificateAccountDTO.getAlias(), 
						certificateAccountDTO.getPassword(), 
						certificateAccountDTO.getCommonName(), 
						certificateAccountDTO.getOrganizationUnit(), 
						certificateAccountDTO.getOrganizationName(), 
						certificateAccountDTO.getLocality(), 
						certificateAccountDTO.getStateName(), 
						certificateAccountDTO.getCountry(), 
						certificateAccountDTO.geteMail(), 
						CastUtils.stringToInt(certificateAccountDTO.getValidity()), 
						certificateAccountDTO.getAllowedNodes());
			certificateAccountDTO.setX509Certificate(certificateOutputStream);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(certificateOutputStream.toByteArray());

    		if (logger.isDebugEnabled()) {
    			logger.debug("Opening generated certificate as keystore...");
    		}
			char[] password = certificateAccountDTO.getPassword().toCharArray();
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(byteArrayInputStream, password);
			
    		if (logger.isDebugEnabled()) {
    			logger.debug("Adding generated certificate to trust store...");
    		}
			this.trustStoreUtils.addCertificatetoTrustStore("jfdk43ufj56gf", 
					certificateAccountDTO.getAlias(), 
					keyStore.getCertificate(certificateAccountDTO.getAlias()));
			byteArrayInputStream.close();

    		if (logger.isDebugEnabled()) {
    			logger.debug("Generating temp file for download...");
    		}
    		result = request.getSession().getId() + UUID.randomUUID().toString();
			File tmpDir = WebUtils.getTempDir(request.getSession().getServletContext());
			File temporaryDownloadFile = new File(tmpDir, result);
			temporaryDownloadFile.createNewFile();
    		if (logger.isDebugEnabled()) {
    			logger.debug("Temp file for download = " + temporaryDownloadFile.getAbsolutePath());
    		}
    		FileOutputStream certificateFileOutputStream = new FileOutputStream(temporaryDownloadFile);
    		certificateFileOutputStream.write(certificateOutputStream.toByteArray());
    		certificateFileOutputStream.flush();
    		certificateFileOutputStream.close();
    		if (logger.isDebugEnabled()) {
    			logger.debug("Temp file for download generated.");
    		}
    		
		} catch (InvalidKeyException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (NoSuchProviderException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (InvalidKeySpecException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (SecurityException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (SignatureException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (CertificateException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (KeyStoreException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (IOException e) {
			throw new XMLCertificateManagerCertificateGenerationException(e);
		} catch (URISyntaxException e) {
			throw new XMLCertificateManagerCertificateToTruststoreException(e);
		}
    	
		return result;
		
    }
    
    @Override
    protected IFilters prepareFilters() {
    	    	
    	Vector<IFilter> filters = new Vector<IFilter>();

    	filters.add(getFirstNameFilter());
    	filters.add(getLastNameFilter());
    	filters.add(getAliasFilter());
    	filters.add(getEMailFilter());
		filters.add(getCertValidityFilter());
    	
    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }

    private IFilter getFirstNameFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Nome", "first_name", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }

    private IFilter getLastNameFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Cognome", "last_name", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }

    private IFilter getAliasFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Alias", "alias", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getEMailFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(LogicalOperators.is_not_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("E-Mail", "email", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getCertValidityFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	ListColumnFilter listColumnFilter = new ListColumnFilter("Scaduto", "Scaduto", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	listColumnFilter.addFilterAllowedValue(new Option("Sì", "Sì"));
    	listColumnFilter.addFilterAllowedValue(new Option("No", "No"));
    	
    	return listColumnFilter;
    	
    }

    private void deleteUserCertificate(int userRef) throws PersistenceBrokerException {
    	this.persistenceBroker.deleteUserCertificate(userRef);
    }
    
    private List<Command> getRowActions() {
    	
    	List<Command> result = new ArrayList<Command>();
    	
    	result.add(new InputCommand("deleteAccount", "deleteAccount", null, 
    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
    	
    	return result;
    	
    }

    
    private List<Command> getUsersWithoutCertificateRowActions() {

    	List<Command> result = new ArrayList<Command>();
    	
    	result.add(new LinkCommand("editAccount", "generaCertificato.do", "Genera certificato", 
    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
    	
    	return result;
    	
    }
    
    
	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processAction()
	 */
	@Override
	protected void processAction(String pagedListHolderId, AbstractCommand.CommandActions action, 
			EditableRowInputData editableRowInputData, HttpServletRequest request, 
			ModelMap modelMap) {

		if (logger.isDebugEnabled()) {
			logger.debug("Processing action...");
			logger.debug("Paged list holder id: " + pagedListHolderId);
			logger.debug("Action: " + action);
			logger.debug("Editable row input data: " + editableRowInputData);
		}
		
		if (action == AbstractCommand.CommandActions.delete) {
			if ((Boolean)request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED)) {
				if (logger.isDebugEnabled()) {
					logger.debug("No confirmation required, proceeding with delete action...");
				}
			} else {
	    		int userId = Integer.parseInt((String)editableRowInputData.getRowIdentifiers().get("id"));
	    		try {
					this.deleteUserCertificate(userId);
				} catch (PersistenceBrokerException e) {
					// TODO Redirect to error page
					e.printStackTrace();
				}
			}
		}
		
	}    
    
}
