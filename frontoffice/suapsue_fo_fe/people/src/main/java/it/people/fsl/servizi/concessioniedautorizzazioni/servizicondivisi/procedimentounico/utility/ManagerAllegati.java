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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.codec.Base64;

import it.infocamere.util.signature.SignedDocument;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.gateway.ISignatureFactory;
import it.people.gateway.SignatureFactory;
import it.people.process.common.entity.Attachment;
import it.people.util.PeopleProperties;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class ManagerAllegati {
	public final Log logger = LogFactory.getLog(this.getClass());

	private static final String ATTACHMENTS_MAX_TOTAL_SIZE_UM_BYTES = "B";
	private static final String ATTACHMENTS_MAX_TOTAL_SIZE_UM_KILOBYTES = "KB";
	private static final int ATTACHMENTS_MAX_TOTAL_SIZE_UM_KILOBYTES_TO_BYTES = 1024;
	private static final String ATTACHMENTS_MAX_TOTAL_SIZE_UM_MEGABYTES = "MB";
	private static final int ATTACHMENTS_MAX_TOTAL_SIZE_UM_MEGABYTES_TO_BYTES = 1048576;
	
	public boolean checkValidita(IRequestWrapper request,String oidComune,ProcessData dataForm, String codiceDocumento,Attachment attach,String hostOO,String portOO,String basePathName) {
		AllegatoBean allegato = null;
		boolean trovato = false;
		boolean tmp1 = true;
        ServiceParameters params = (ServiceParameters) request.getUnwrappedRequest().getSession().getAttribute("serviceParameters");
        String absPathToService = params.get("absPathToService");
        String resourcePath = absPathToService+System.getProperty("file.separator")+"risorse"+System.getProperty("file.separator");
        Properties props[] = UtilProperties.getProperties(resourcePath,"messaggi", oidComune);
        String messaggioErrore = "";
		Iterator it = dataForm.getListaAllegati().keySet().iterator();
		while (it.hasNext() && !trovato){
			String key = (String) it.next();
			allegato = (AllegatoBean) dataForm.getListaAllegati().get(key);
			if (allegato != null && allegato.getCodice() != null && allegato.getCodice().equalsIgnoreCase(codiceDocumento)) {
                trovato = true;
                tmp1 = isExtValida(allegato, attach.getName());
                if (!tmp1) {
                    messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.allegati.estensioniNonValide");
                    request.setAttribute("validitaError", messaggioErrore + " " + allegato.getTipologieAllegati());
                } else {
                    tmp1 = checkIsSigned(allegato, attach);
                    if (!tmp1) {
                        messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.allegati.firmaNonValida");
                        request.setAttribute("validitaError", (new StringBuilder(String.valueOf(messaggioErrore))).append(" ").append(attach.getName()).toString());
                    } else {
                        tmp1 = checkIsPDF(attach);
                        if (!tmp1) {
                            messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.allegati.nonDocummentoPdf");
                            request.setAttribute("validitaError", messaggioErrore + " " + allegato.getNomeFile());
                        } else {
                            tmp1 = isLunghezzaValida(allegato, attach, hostOO, portOO, basePathName);
                            if (!tmp1) {
                                messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.allegati.numeroPagineNonValide");
                                request.setAttribute("validitaError", messaggioErrore + " " + allegato.getNum_max_pag());
                            } else {
                                tmp1 = isDimensioneValida(allegato, attach.getFileLenght());
                                if (!tmp1) {
                                    messaggioErrore = UtilProperties.getPropertyKey(props[0], props[1], props[2], "error.allegati.dimensioniNonValide");
                                    request.setAttribute("validitaError", messaggioErrore + allegato.getDimensione_max() + "KB");
                                }
                            }
                        }
                    }
                }
            }
        }
        return tmp1;
	}

	private boolean isDimensioneValida(AllegatoBean allegato, int dimensioneAllegato) {
		boolean isDimeValida = false;
		if (allegato.getDimensione_max()!=null && !allegato.getDimensione_max().equalsIgnoreCase("")){
			int dim_max = 0;
			try {
				dim_max = Integer.parseInt(allegato.getDimensione_max());
			} catch (Exception e){
				dim_max = Integer.MAX_VALUE;
			}
			if (dim_max<(dimensioneAllegato/1024)){
				isDimeValida = false;
			} else {
				isDimeValida = true;
			}
		} else {
			isDimeValida = true;
		}
		return isDimeValida;
	}

	private boolean isLunghezzaValida(AllegatoBean allegato, Attachment attach,String hostOO, String portOO,String basePathName) {
		boolean isNumeroPagineValido = true;
		int indexExt = attach.getName().lastIndexOf(".");
		String ext = "";
		if (allegato.getNum_max_pag()!=null && !allegato.getNum_max_pag().equalsIgnoreCase("")){
			if (indexExt!=-1){
				ext = attach.getName().substring(indexExt+1,attach.getName().length() );
			}
			if (ext!=null && ext.equalsIgnoreCase("pdf")){
				int pageCount = 0;
				try {
					PdfReader reader = new PdfReader(Base64.decode(attach.getData()));
					pageCount = reader.getNumberOfPages();
				} catch (Exception e){
					
				}	
				int numMaxPag = 0;
				try {
					numMaxPag = Integer.parseInt(allegato.getNum_max_pag());
				} catch (Exception e){
					
				}
				if (numMaxPag<pageCount) {
					isNumeroPagineValido = false;
				}	
			}
			if (ext!=null && (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("odt")) && it.gruppoinit.commons.Utilities.isset(allegato.getNum_max_pag())){
				int pageCount = 0;
				try {
					ConnectToOfficeServer ctosi = new ConnectToOfficeServerImpl(hostOO,portOO);
					com.sun.star.frame.XComponentLoader xComponentLoader = ctosi.getConnection();
					// com.sun.star.uno.XComponentContext xContext = ctosi.getXComponentContext();
					if (xComponentLoader == null) {
						logger.error("ManagerAllegati - ERROR: Could not bootstrap default Office.");
		                System.err.println("ERROR: Could not bootstrap default Office.");
		            } else {
		            	Date d = new Date();
		            	String pathCompleto = basePathName+System.getProperty("file.separator")+d.getTime()+"_"+attach.getDescrizione();
		            	try {
		            		FileOutputStream fos = new FileOutputStream(new File(pathCompleto));
	            		    fos.write(Base64.decode(attach.getData()));
		            		fos.close();
		            	} catch (FileNotFoundException e2){
		            	    e2.printStackTrace();
		            	} catch (IOException e2){
		            		e2.printStackTrace();
		            	}
		            	com.sun.star.lang.XComponent xComponent =
		                    xComponentLoader.loadComponentFromURL(
//		                    "file:///C:/test/test_2.doc",
		                    "file:///"+pathCompleto,
		                    "_default",
		                    com.sun.star.frame.FrameSearchFlag.ALL,
		                    new com.sun.star.beans.PropertyValue[0]);

			            com.sun.star.frame.XModel xModel = (com.sun.star.frame.XModel)
			                    com.sun.star.uno.UnoRuntime.queryInterface(
			                    com.sun.star.frame.XModel.class, xComponent);
	
			            com.sun.star.frame.XController xController =
			                   xModel.getCurrentController();
	
			            com.sun.star.beans.XPropertySet xPropertySet = 
			                    (com.sun.star.beans.XPropertySet)
			                    com.sun.star.uno.UnoRuntime.queryInterface(
			                    com.sun.star.beans.XPropertySet.class, xController);
	
			            int nPageCount = com.sun.star.uno.AnyConverter.toInt(xPropertySet.getPropertyValue("PageCount"));
	
//			            System.out.println(nPageCount);
			            pageCount = nPageCount;
			            File ff = new File(pathCompleto);
			            ff.delete();
		            }
				} catch (Exception e){
					logger.error("ManagerAllegati - ERROR: Could not bootstrap default Office.");
	                System.err.println("ERROR: Could not bootstrap default Office.");
				}	
				int numMaxPag = 0;
				try {
					numMaxPag = Integer.parseInt(allegato.getNum_max_pag());
				} catch (Exception e){
					
				}
				if (numMaxPag<pageCount) {
					isNumeroPagineValido = false;
				}
			}
		} 
		return isNumeroPagineValido;
	}

	private boolean isExtValida(AllegatoBean allegato,String fileName) {
		
		boolean isEstensioneValida = false;
		if (allegato.getTipologieAllegati()!=null && !allegato.getTipologieAllegati().equalsIgnoreCase("")){
//			int indexExt = fileName.lastIndexOf(".");
//			String ext = "";
//			if (indexExt!=-1){
//				ext = fileName.substring(indexExt+1,fileName.length() );
//			}
			String[] estensioniValide = allegato.getTipologieAllegati().split(";");
			int i = 0;
			boolean trovato = false;
			if (estensioniValide!=null){
				while (i<estensioniValide.length && !trovato){
					// Requisito R15
					if (estensioniValide[i]!=null && fileName.toLowerCase().endsWith(estensioniValide[i].toLowerCase())){
						trovato = true;
					}
					i++;
				}
				if (trovato){
					isEstensioneValida = true;
				}
			}
		} else {
			isEstensioneValida = true;
		}
		return isEstensioneValida;
	}

	public boolean checkNumeroMinimoAllegati(ProcessData dataForm) {
		boolean isOk = true;
		Iterator it = dataForm.getListaAllegati().keySet().iterator();
		while (it.hasNext() && isOk){
			String key = (String) it.next();
			AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(key);
			if (allegato.getHref() == null && allegato.isFlg_obb()){
				boolean trovato= false;
				Iterator it2 = dataForm.getAllegati().iterator();
				while (it2.hasNext() && !trovato){
					Attachment attach = (Attachment) it2.next();
					if (allegato.getCodice()!=null && attach.getName()!=null && allegato.getCodice().equalsIgnoreCase(attach.getDescrizione())){
						trovato = true;
					}
				}
				if (!trovato){
					isOk = false;
				}
			}
		}
		
		return isOk;
	}

    public boolean checkIsSigned(AllegatoBean allegato, Attachment attach)
    {
    	/* Requisito R15 */
    	PdfReader reader = null;
    	InputStream is = null;
    	boolean isSigned = false;
    	
        if (attach.getName().substring(attach.getName().lastIndexOf(".") + 1, attach.getName().length()).equalsIgnoreCase("pdf")) {
			try {
				is = new FileInputStream(attach.getPath());
				reader = new PdfReader(is);
				
				if (reader.getFileLength() < 1){
	                 isSigned= false;
	            }
	            
	            AcroFields acroFields = reader.getAcroFields();
	            
	            List<String> signatureNames = acroFields.getSignatureNames();
	            
	            if (!signatureNames.isEmpty()) {
		            for (String name : signatureNames) {
		            	isSigned = acroFields.signatureCoversWholeDocument(name);
		            	if(!isSigned) {
		            		isSigned= false;
		            	}
		            }
	        	}
	            
	            if(signatureNames.size()>0) {
	            	isSigned= true;
	            }
				
				
			} catch (Exception e) {
				logger.debug("File non trovato", e);
			} finally {
	            if (reader != null) {
	                reader.close();
	            }
	        }
            
            return isSigned;
        }
        /* Fine Requisito R15 */
    	
        if(allegato.isSignVerify())
        {
            try
            {
                java.io.InputStream inputStream = new FileInputStream(attach.getPath());
                byte attachmentBytes[] = IOUtils.toByteArray(inputStream);
                ISignatureFactory signatureFactory = SignatureFactory.getInstance(true);
                SignedDocument signedDocument = signatureFactory.createDocument(attachmentBytes);
                return signedDocument.isSigned() && signedDocument.isValid();
            }
            catch(Exception e)
            {
                logger.error("Unable to check if document is signed.", e);
                return false;
            }
        } else
        {
            return true;
        }
    }

    public boolean checkIsPDF(Attachment attach) {
        PdfReader reader = null;
        try {
            if (attach.getName().substring(attach.getName().lastIndexOf(".") + 1, attach.getName().length()).equalsIgnoreCase("pdf")) {
                InputStream is = new FileInputStream(attach.getPath());
                reader = new PdfReader(is);
                if (reader.getFileLength() < 1){
                    return false;
                } 
            }
        } catch (Exception ex) {
            logger.debug("Il file non e' un PDF", ex);
            return false;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return true;
    }
    
    public boolean checkSignedPDF(Attachment attach) {
				
    	PdfReader reader = null;
    	boolean isSigned = false;
        try {
            if (attach.getName().substring(attach.getName().lastIndexOf(".") + 1, attach.getName().length()).equalsIgnoreCase("pdf")) {
                InputStream is = new FileInputStream(attach.getPath());
                reader = new PdfReader(is);
                if (reader.getFileLength() < 1){
                    //return isSigned;
                    return false; 
                }
                
                AcroFields acroFields = reader.getAcroFields();
                
                List<String> signatureNames = acroFields.getSignatureNames();
                
	            if(signatureNames.size()>0) {
	            	//isSigned = true;
	            	return true;
	            }
            }
        } catch (Exception ex) {
            logger.debug("Il file non e' un PDF", ex);
            return false;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    	return isSigned;
    	
    } 

	public void setAttachmentsMaxTotalSize(ProcessData dataForm, int attachmentsMaxTotalSize, String attachmentsMaxTotalSizeUM, boolean hasMailServer, boolean hasBackOffice) {
		if (dataForm == null) {
			return;
		}

    	int sportelloAttachmentsMaximumTotalSize = 0;
    	if (attachmentsMaxTotalSize == ProcessData.PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE) {
    		sportelloAttachmentsMaximumTotalSize = ProcessData.PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE;
    	} else if (attachmentsMaxTotalSize == ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
    		sportelloAttachmentsMaximumTotalSize = ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE;
    	} else {
    		if (attachmentsMaxTotalSizeUM.equalsIgnoreCase(ATTACHMENTS_MAX_TOTAL_SIZE_UM_BYTES)) {
    			sportelloAttachmentsMaximumTotalSize = attachmentsMaxTotalSize;
    		} else if (attachmentsMaxTotalSizeUM.equalsIgnoreCase(ATTACHMENTS_MAX_TOTAL_SIZE_UM_KILOBYTES)) {
    			sportelloAttachmentsMaximumTotalSize = attachmentsMaxTotalSize * ATTACHMENTS_MAX_TOTAL_SIZE_UM_KILOBYTES_TO_BYTES;
    		} else {
    			sportelloAttachmentsMaximumTotalSize = attachmentsMaxTotalSize * ATTACHMENTS_MAX_TOTAL_SIZE_UM_MEGABYTES_TO_BYTES;
    		}
    	}

		String entePeopleKey = (dataForm.getEntePeopleKey() == null || dataForm.getEntePeopleKey().equalsIgnoreCase("")) ? "0" : dataForm.getEntePeopleKey();
		int peopleAttachmentsMaximumTotalSize = 0;
    	try {
    		peopleAttachmentsMaximumTotalSize = Integer.parseInt(PeopleProperties.ATTACHMENT_MAX_TOTAL_SIZE.getValueString(entePeopleKey)) * 1024;
    	} catch(Exception ex) {
    		peopleAttachmentsMaximumTotalSize = sportelloAttachmentsMaximumTotalSize;
    		logger.warn("Impossibile determinare il parametro che indica la dimensione massima totale per l'elenco degli allegati di una pratica");
    	}
    	
    	if (hasMailServer) {
    		if (sportelloAttachmentsMaximumTotalSize == ProcessData.PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE || sportelloAttachmentsMaximumTotalSize == ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
    			dataForm.setAttachmentsMaximunSize(peopleAttachmentsMaximumTotalSize);
    		} else {
    			if (sportelloAttachmentsMaximumTotalSize <= peopleAttachmentsMaximumTotalSize) {
    				dataForm.setAttachmentsMaximunSize(sportelloAttachmentsMaximumTotalSize);
    			} else {
    				dataForm.setAttachmentsMaximunSize(peopleAttachmentsMaximumTotalSize);
    			}
    		}
    	} else {
    		if (sportelloAttachmentsMaximumTotalSize == ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE) {
    			dataForm.setAttachmentsMaximunSize(ProcessData.UNLIMITED_ATTACHMENTS_MAX_TOTAL_SIZE);
    		} else if (sportelloAttachmentsMaximumTotalSize == ProcessData.PEOPLE_ATTACHMENTS_MAX_TOTAL_SIZE) {
    			dataForm.setAttachmentsMaximunSize(peopleAttachmentsMaximumTotalSize);
    		} else {
    			dataForm.setAttachmentsMaximunSize(sportelloAttachmentsMaximumTotalSize);
    		}
    	}
    	
	}
	
	public String convertAttachmentsMaximumTotalSize(int attachmentsMaxTotalSize, String um) {
		if (um.equalsIgnoreCase(ATTACHMENTS_MAX_TOTAL_SIZE_UM_BYTES)) {
			return String.valueOf(attachmentsMaxTotalSize);
		} else if (um.equalsIgnoreCase(ATTACHMENTS_MAX_TOTAL_SIZE_UM_KILOBYTES)) {
			return String.valueOf(attachmentsMaxTotalSize / ATTACHMENTS_MAX_TOTAL_SIZE_UM_KILOBYTES_TO_BYTES);
		} else {
			return String.valueOf(attachmentsMaxTotalSize / ATTACHMENTS_MAX_TOTAL_SIZE_UM_MEGABYTES_TO_BYTES);
		}
	}
}
