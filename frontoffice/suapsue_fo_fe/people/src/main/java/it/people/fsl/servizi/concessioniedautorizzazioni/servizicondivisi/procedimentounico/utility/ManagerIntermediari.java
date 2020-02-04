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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import noNamespace.PraticaDocument.Pratica;
import noNamespace.StatoDocument.Stato;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlOptions;

import it.gruppoinit.commons.Utilities;
import it.people.core.PeopleContext;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.IntermediariBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class ManagerIntermediari {

	public final Log logger = LogFactory.getLog(this.getClass());
	
	public static IntermediariBean buildBeanIntermediari(IRequestWrapper request){
		IntermediariBean intermediari =new IntermediariBean();
		String tmpRequestParameter = request.getUnwrappedRequest().getParameter("cfIntermediario");
		intermediari.setCfIntermediario(Utilities.NVL(tmpRequestParameter,""));
		
		tmpRequestParameter = request.getUnwrappedRequest().getParameter("ragSocIntermediario");
		intermediari.setRagSocIntermediario(Utilities.NVL(tmpRequestParameter,""));
		
		tmpRequestParameter = request.getUnwrappedRequest().getParameter("indirizzoIntermediario");
		intermediari.setIndirizzoIntermediario(Utilities.NVL(tmpRequestParameter,""));
		
		tmpRequestParameter = request.getUnwrappedRequest().getParameter("cfUtenteIntermediario");
		intermediari.setCfUtenteIntermediario(Utilities.NVL(tmpRequestParameter,""));
		
		tmpRequestParameter = request.getUnwrappedRequest().getParameter("nomeUtenteIntermediario");
		intermediari.setNomeUtenteIntermediario(Utilities.NVL(tmpRequestParameter,""));
		
		tmpRequestParameter = request.getUnwrappedRequest().getParameter("cognomeUtenteIntermediario");
		intermediari.setCognomeUtenteIntermediario(Utilities.NVL(tmpRequestParameter,""));
		
		
//		if (requestIntermediari!=null){
//			String[] tokenParametri = requestIntermediari.split(",");
//			if (tokenParametri!=null && tokenParametri.length>0){
//				intermediari.setCfIntermediario(tokenParametri[0]);
//				if (tokenParametri.length>1){
//					for (int i = 1; i < tokenParametri.length; i++) {
//						String parametro = tokenParametri[i];
//						String[] tokenVal = parametro.split("=");
//						if (tokenVal!=null && tokenVal.length==2){
//							if (tokenVal[0].equalsIgnoreCase("ragSocIntermediario")){
//								intermediari.setRagSocIntermediario(tokenVal[1]);
//							} else if (tokenVal[0].equalsIgnoreCase("indirizzoIntermediario")) {
//								intermediari.setIndirizzoIntermediario(tokenVal[1]);
//							} else if (tokenVal[0].equalsIgnoreCase("cfUtenteIntermediario")){
//								intermediari.setCfUtenteIntermediario(tokenVal[1]);
//							} else if (tokenVal[0].equalsIgnoreCase("nomeUtenteIntermediario")){
//								intermediari.setNomeUtenteIntermediario(tokenVal[1]);
//							} else if (tokenVal[0].equalsIgnoreCase("cognomeUtenteIntermediario")){
//								intermediari.setCognomeUtenteIntermediario(tokenVal[1]);
//							}
//						}
//					}
//				}
//			}
//		} 
		return intermediari;
	}

	
	public static void rebuildBeanIntermediari(IRequestWrapper request,ProcessData dataForm){
		try {
			IntermediariBean intermediari = dataForm.getIntermediari();
			String tmpRequestParameter = request.getUnwrappedRequest().getParameter("cfIntermediario");
			if (Utilities.isset(tmpRequestParameter)){
				intermediari.setCfIntermediario(Utilities.NVL(tmpRequestParameter,""));
			}
			
			tmpRequestParameter = request.getUnwrappedRequest().getParameter("ragSocIntermediario");
			if (Utilities.isset(tmpRequestParameter)){
				intermediari.setRagSocIntermediario(Utilities.NVL(tmpRequestParameter,""));
			}
			
			tmpRequestParameter = request.getUnwrappedRequest().getParameter("indirizzoIntermediario");
			if (Utilities.isset(tmpRequestParameter)){
				intermediari.setIndirizzoIntermediario(Utilities.NVL(tmpRequestParameter,""));
			}
			
			tmpRequestParameter = request.getUnwrappedRequest().getParameter("cfUtenteIntermediario");
			if (Utilities.isset(tmpRequestParameter)){
				intermediari.setCfUtenteIntermediario(Utilities.NVL(tmpRequestParameter,""));
			}
			
			tmpRequestParameter = request.getUnwrappedRequest().getParameter("nomeUtenteIntermediario");
			if (Utilities.isset(tmpRequestParameter)){
				intermediari.setNomeUtenteIntermediario(Utilities.NVL(tmpRequestParameter,""));
			}
			
			tmpRequestParameter = request.getUnwrappedRequest().getParameter("cognomeUtenteIntermediario");
			if (Utilities.isset(tmpRequestParameter)){
				intermediari.setCognomeUtenteIntermediario(Utilities.NVL(tmpRequestParameter,""));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void sendSegnalazionePratica(AbstractPplProcess process,IRequestWrapper request,String capAzienda,String cittaAzienda,String indirizzoAzienda,String mailLegaleRappresentanteAzienda,String telefonoLegaleRappresentanteAzienda) throws Exception, ServiceException {
		String output ="";
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			noNamespace.PraticaDocument praticaDocument = noNamespace.PraticaDocument.Factory.newInstance();
			Pratica pratica = praticaDocument.addNewPratica();
			
			ArrayList lista = dataForm.getAnagrafica().getListaCampi();
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				HrefCampiBean campoAnagrafica = (HrefCampiBean) iterator.next();
				String campoXMLmod = campoAnagrafica.getCampo_xml_mod();
				if (campoXMLmod!=null && campoXMLmod.equalsIgnoreCase("ANAG_RAPPSOC_CODFISC")){
					pratica.setCfAzienda(Utilities.NVL(campoAnagrafica.getValoreUtente(),"-"));
				} else if (campoXMLmod!=null && campoXMLmod.equalsIgnoreCase("ANAG_CODFISC_DICHIARANTE")){
					pratica.setCfLegaleRappresentanteAzienda(Utilities.NVL(campoAnagrafica.getValoreUtente(),"-"));
				} else if (campoXMLmod!=null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_COGNOME")){
					pratica.setCognomeLegaleRappresentanteAzienda(Utilities.NVL(campoAnagrafica.getValoreUtente(),"-"));
				} else if (campoXMLmod!=null && campoXMLmod.equalsIgnoreCase("ANAG_DICHIARANTE_NOME")){
					pratica.setNomeLegaleRappresentanteAzienda(Utilities.NVL(campoAnagrafica.getValoreUtente(),"-"));
				} else if (campoXMLmod!=null && campoXMLmod.equalsIgnoreCase("ANAG_RAPPSOC_DENOM")){
					pratica.setRagioneSocialeAzienda(Utilities.NVL(campoAnagrafica.getValoreUtente(),"-"));
				}
			}
			
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());
		    String communeId = pplContext.getCommune().getKey();
		    
			
        	HttpServletRequest tmpReq = request.getUnwrappedRequest();
        	String g = tmpReq.getRequestURL().toString();
        	int indice = g.lastIndexOf("/");
        	String url= g.substring(0, indice);
			url = url.concat("/")
			         .concat("initProcess.do?processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico")
			         .concat("&processId=")
			         .concat(Utilities.NVL(dataForm.getIdentificatorePeople().getIdentificatoreProcedimento(),""))
			         .concat("&communeCode="+Utilities.NVL(communeId,"")+"&selectingCommune=true");
			
			pratica.setUrlPratica(Utilities.NVL(url,"-"));
			
			pratica.setCfIntermediario(Utilities.NVL(dataForm.getIntermediari().getCfIntermediario(),"-"));
			pratica.setCfUtenteIntermediario(Utilities.NVL(dataForm.getIntermediari().getCfUtenteIntermediario(),"-"));
			pratica.setCodPratica(Utilities.NVL(dataForm.getIdentificatorePeople().getIdentificatoreProcedimento(),"-"));
			pratica.setDesPratica(Utilities.NVL(dataForm.getDescBookmark(),"-"));
			pratica.setStato(Stato.C); //P=presentata C=in compilazione
			

			pratica.setCapAzienda((capAzienda==null||capAzienda.equalsIgnoreCase(""))?"-":capAzienda);
			pratica.setCittaAzienda((cittaAzienda==null||cittaAzienda.equalsIgnoreCase(""))?"-":cittaAzienda);
			pratica.setIndirizzoAzienda((indirizzoAzienda==null||indirizzoAzienda.equalsIgnoreCase(""))?"-":indirizzoAzienda);
			pratica.setMailLegaleRappresentanteAzienda((mailLegaleRappresentanteAzienda==null||mailLegaleRappresentanteAzienda.equalsIgnoreCase(""))?"-":mailLegaleRappresentanteAzienda);
			pratica.setTelefonoLegaleRappresentanteAzienda((telefonoLegaleRappresentanteAzienda==null||telefonoLegaleRappresentanteAzienda.equalsIgnoreCase(""))?"-":telefonoLegaleRappresentanteAzienda);
			
			
			String xmlInput = "";
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			try {
				XmlOptions options = new XmlOptions();
				options.setSavePrettyPrint();
				praticaDocument.save(result,options);
		    	xmlInput = new String(result.toByteArray());
			} catch (Exception e){}	
			
			output = process.callService("INTERMEDIARI_WS_SEGNALAZIONE", xmlInput);
//			System.out.println(output);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		
	}




	public static void aggiornaBeanIntermediari(ProcessData dataForm,String href) {
		if (href.equalsIgnoreCase("ANAG")){
			ArrayList listaCampiAnagrafica = dataForm.getAnagrafica().getListaCampi();
		} else {
			SezioneCompilabileBean scb = (SezioneCompilabileBean)dataForm.getListaHref().get(href);
			if (scb!=null) {
				ArrayList listaCampi = scb.getCampi();
				if (listaCampi!=null && listaCampi.size()>0){
					for (Iterator iterator = listaCampi.iterator(); iterator.hasNext();) {
						HrefCampiBean campo = (HrefCampiBean) iterator.next();
						String campoXMLmod = Utilities.NVL(campo.getCampo_xml_mod(),"");
						if (campoXMLmod.equalsIgnoreCase("47_CF_PIVA")){
							dataForm.getIntermediari().setCfIntermediario(campo.getValoreUtente());
						} else if (campoXMLmod.equalsIgnoreCase("47_CF_UTENTE_INTERMEDIARIO")){
							dataForm.getIntermediari().setCfUtenteIntermediario(campo.getValoreUtente());
						} else if (campoXMLmod.equalsIgnoreCase("47_ILSOTTOSCRITTO_COGNOME")){
							dataForm.getIntermediari().setCognomeUtenteIntermediario(campo.getValoreUtente());
						} else if (campoXMLmod.equalsIgnoreCase("...")){
							//dataForm.getIntermediari().setIndirizzoIntermediario(campo.getValoreUtente());
						} else if (campoXMLmod.equalsIgnoreCase("47_ILSOTTOSCRITTO")){
							dataForm.getIntermediari().setNomeUtenteIntermediario(campo.getValoreUtente());
						} else if (campoXMLmod.equalsIgnoreCase("47_RAG_SOC_INTERMEDIARIO")){
							dataForm.getIntermediari().setRagSocIntermediario(campo.getValoreUtente());
						}
					}
					
					
				}
			}
		}
		
	}
	
}
