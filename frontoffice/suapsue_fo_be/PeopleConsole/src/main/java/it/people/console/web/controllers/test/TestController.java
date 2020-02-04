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
package it.people.console.web.controllers.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import it.people.console.beans.support.JasperReportSettings;
import it.people.console.beans.support.P12UploadItem;
import it.people.console.domain.UserAccount;
import it.people.console.dto.ReportSettingsDTO;
import it.people.console.jasper.JasperReportView;
import it.people.console.security.ITrustStoreUtils;
import it.people.console.security.exceptions.CertAliasException;
import it.people.console.security.exceptions.CredentialsOrCertTypeException;
import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.utils.MimeTypeFinder;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/dic/2010 09.19.55
 *
 */
@Controller
@RequestMapping("/Test")
public class TestController extends MessageSourceAwareController {

	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private ITrustStoreUtils trustStoreUtils;
	
    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	model.addAttribute(new P12UploadItem());
    	
    	return "test/testPage";
    	
    }

    @RequestMapping(value = "/test.do", method = RequestMethod.POST)
    public String processSubmit(P12UploadItem p12UploadItem, BindingResult result, 
    		HttpServletRequest request) {

    	if (p12UploadItem.getFileData() != null && !p12UploadItem.getFileData().isEmpty()) {

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
    		
            System.out.println("-------------------------------------------");
            System.out.println("Uploaded file password: " + p12UploadItem.getPassword());
            System.out.println("Uploaded original file name: " + p12UploadItem.getFileData().getOriginalFilename());
            System.out.println("Uploaded file mime type: " + mimeType);
            System.out.println("Valid mime type: " + validMimeType);
            System.out.println("Valid password: " + validP12andPassword);
            System.out.println("Trusted certificate: " + trustedCertificate);
            System.out.println("-------------------------------------------");
            
            
            if (!validMimeType) {
        		result.addError(new ObjectError("p12UploadItem", 
        				this.getProperty("error.certificate.invalidFileType")));
        		return "test/testPage"; 
            } else if (!validP12andPassword) {
        		result.addError(new ObjectError("p12UploadItem", 
        				this.getProperty("error.certificate.invalidPassword")));
        		return "test/testPage"; 
            } else if (!trustedCertificate) {
        		result.addError(new ObjectError("p12UploadItem", 
        				this.getProperty("error.certificate.untrusted")));
        		return "test/testPage"; 
            } else {
            	return "redirect:/Test/test.do";
            }
                		
    	} else {
    		result.addError(new ObjectError("p12UploadItem", 
    				this.getProperty("error.certificate.noFileUploaded")));
    		return "test/testPage"; 
    	}
    	
    }
     

    @RequestMapping(value = "/testJR.do", method = RequestMethod.GET)
    public String doPDFJR(ModelMap model, HttpServletRequest request) {

    	ReportSettingsDTO reportSettingsDTO = new ReportSettingsDTO();
    	
    	JRDataSource datasource  = this.getJRDataSource();
    	
    	JasperReportSettings jasperReportSettings = new JasperReportSettings();
    	
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());

    	model.addAttribute("reportSettingsDTO", reportSettingsDTO);
    	
    	return "test/testJasper";
    	
    }

    @RequestMapping(value = "/testJR.do", method = RequestMethod.POST)
    public String submitPDFJR(ModelMap model, 
    		@ModelAttribute("reportSettingsDTO") ReportSettingsDTO reportSettingsDTO, 
    		BindingResult result, HttpServletRequest request) {

    	JRDataSource datasource  = this.getJRDataSource();
    	
    	JasperReportSettings jasperReportSettings = new JasperReportSettings();
    	
    	model.addAttribute("reportTypes", jasperReportSettings.getReportTypes());

    	model.addAttribute("reportSettingsDTO", reportSettingsDTO);
    	
    	
    	
//    	JasperReportView jasperReportView = new JasperReportView();
    	
    	
    	
    	return "test/testJasper";
    	
    }
    
    @RequestMapping(value = "/viewReport.do", method = RequestMethod.GET)
    public String setupViewReport(ModelMap model, HttpServletRequest request) {

    	JRDataSource datasource  = this.getJRDataSource();
    	    	
    	return "test/viewJasperReport";
    	
    }
    
    
    
    public JRDataSource getJRDataSource() {
    	
		// Create an array list of Sales 
		List<Sales> items = new ArrayList<Sales>();
		
		// We'll add three Sales items
		// You can populate data from a custom JDBC or DAO layer
		// For this demo, we'll create an in-memory list of items
		
		// Create first item
		Sales item1 = new Sales();
		item1.setId(1001L);
		item1.setName("Pencil");
		item1.setDescription("This is used for sketching drawings");
		item1.setPrice(10.50);
		
		// Create second item
		Sales item2 = new Sales();
		item2.setId(1002L);
		item2.setName("Pen");
		item2.setDescription("This is used for signing autographs");
		item2.setPrice(15.00);
		
		// Create third item
		Sales item3 = new Sales();
		item3.setId(1003L);
		item3.setName("Bag");
		item3.setDescription("This is used for storing other items");
		item3.setPrice(50.00);
		
		// Add to list
		items.add(item1);
		items.add(item2);
		items.add(item3);
		
		// Wrap the collection in a JRBeanCollectionDataSource
		// This is one of the collections that Jasper understands
		JRDataSource ds = new JRBeanCollectionDataSource(items);	
		
    	
    	return ds;
    	
    }

    public class Sales {

    	private Long id;
    	private String name;
    	private Double price;
    	private String description;
    	
    	public Long getId() {
    		return id;
    	}
    	public void setId(Long id) {
    		this.id = id;
    	}
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	public Double getPrice() {
    		return price;
    	}
    	public void setPrice(Double price) {
    		this.price = price;
    	}
    	public String getDescription() {
    		return description;
    	}
    	public void setDescription(String description) {
    		this.description = description;
    	}
    	
    }

	/**
	 * @return the trustStoreUtils
	 */
	private ITrustStoreUtils getTrustStoreUtils() {
		return trustStoreUtils;
	}

}
