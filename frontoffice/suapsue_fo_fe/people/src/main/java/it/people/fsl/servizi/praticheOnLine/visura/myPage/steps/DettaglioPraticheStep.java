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
package it.people.fsl.servizi.praticheOnLine.visura.myPage.steps;

import it.init.myPage.visuraTypes.VisuraDettaglioPraticaDocument;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.dao.MyPageDAO;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.InfoBoBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.MyPageException;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBeanExtended;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.util.DataSourcePeopleDB;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;
import it.wego.cross.webservices.cxf.interoperability.Allegato;
import it.wego.cross.webservices.cxf.interoperability.CrossServices;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesException;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesImplService;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceLocator;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesProxy;
import it.wego.cross.webservices.cxf.interoperability.Evento;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;

public class DettaglioPraticheStep extends BaseStep{

	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(request)) {
				logger.debug("DettaglioPraticheStep - service method");
				ProcessData dataForm = (ProcessData) process.getData();
				int index = dataForm.getOid();
				MyPageDAO delegate = new MyPageDAO();
				PraticaBeanExtended pratica = delegate.getDettaglioPratica(request,process,index);
				ArrayList listaAltriDichiaranti = delegate.getAltriDichiaranti(request,process,pratica);
				pratica.setAltriDichiaranti(listaAltriDichiaranti);
				dataForm.setDettaglioPratica(pratica);
				if (!dataForm.isInCompilazione()){
					
					ServletContext sc = request.getUnwrappedRequest().getSession().getServletContext();
					String crossEndpoint = null;
					if(StringUtils.isNotBlank(sc.getInitParameter("crossEndpoint"))) {
						crossEndpoint=sc.getInitParameter("crossEndpoint");
					}
			        
					URL endpoint = new URL(crossEndpoint);
			        CrossServicesImplService service = new CrossServicesImplServiceLocator();
					CrossServices stub;
					try {
						stub = service.getCrossServicesImplPort(endpoint);
						org.apache.axis.client.Stub axisStub = (org.apache.axis.client.Stub) stub;
						String identificativoPratica = dataForm.getDettaglioPratica().getProcessDataID();
						Evento[] e = stub.getListaEventi(0, identificativoPratica, "");
						dataForm.setEventi(e);
						
					} catch (CrossServicesException e) {
						logger.error("Errore nell'esecuzione del servizio di integrazione: "+e);
					} catch (RemoteException e) {
						logger.error("Errore nell'esecuzione del servizio di integrazione: "+e);
					}
				}
				if (!dataForm.isInCompilazione()){
				    
				    List altriAllegati = delegate.getAltriFileAllegati(process, pratica);
					dataForm.getDettaglioPratica().setAltriAllegati(altriAllegati);
				    
					ArrayList listaCertificati = delegate.getListaCertificati(request,process,pratica);
					dataForm.getDettaglioPratica().setCertificato(listaCertificati);
					
					InfoBoBean infoBO = delegate.getInfoBO(dataForm);
					dataForm.getDettaglioPratica().setInfoBO(infoBO);
					
					if (infoBO!=null && infoBO.getUrl_visura()!=null && !infoBO.getUrl_visura().equalsIgnoreCase("")){
						String urlWS=infoBO.getUrl_visura();
						
						//String urlWS="http://demo.wego.it:8000/DynamicOdtServiceWego/services/DynamicOdtService?wsdl";
				        URL endpoint = new URL(urlWS);
				        Service service = new Service();
				        Call call = (Call)service.createCall();
				        call.setTargetEndpointAddress(endpoint);
				        call.addParameter("input", XMLType.XSD_STRING, ParameterMode.IN); 
				        call.setOperationName(new QName("process"));
				        call.setReturnType(XMLType.XSD_STRING);
				        Object rispostaWS = call.invoke(new Object[]{dataForm.getDettaglioPratica().getProcessDataID()});
				        String ret = (String)rispostaWS;
				        System.out.println("WS returned : ");
				        System.out.println(ret);
				        
						VisuraDettaglioPraticaDocument visuraDocument = VisuraDettaglioPraticaDocument.Factory.parse(ret);
						dataForm.getDettaglioPratica().setDettaglioAttivitaBO(visuraDocument.getVisuraDettaglioPratica().getDettaglioArray());

					}
					
				}
			} else {
				throw new MyPageException("Sessione scaduta");
			}
			logger.debug("DettaglioPraticheStep - service method END");
		} catch (Exception e){
			gestioneEccezioni(process,0,e);
		}
	}
	
	
	
    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
		try {
			logger.debug("DettaglioPraticheStep - loopBack method");
			logger.debug("propertyName=" + propertyName);
			ProcessData dataForm = (ProcessData) process.getData();
			if(propertyName.equals("allega_interop.jsp"))
				goToStep(process, request, 1);
//				showJsp(process, propertyName, false);
			else {
				if(propertyName.equals("dettaglioPratiche.jsp"))
					goToStep(process, request, -1);
			}
		} catch (Exception e) {
			gestioneEccezioni(process,0,e);
		}
    }
    
    private String invokeWS(String idPratica){
    	
    	return "";
    }
	
}
