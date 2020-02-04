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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.gruppoinit.commons.DBCPManager;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ImportRavennaDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SettoreBean;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class ManagerImportRavenna {
	private String language;
	private DBCPManager db;
    
//    private String driverDB ="com.mysql.jdbc.Driver";
//    private String urlConnDB="jdbc:mysql://10.10.45.32:3306/people_dati_ravenna?user=people_demo&password=people_demo";
    
	public final Log logger = LogFactory.getLog(this.getClass());
	
	public ManagerImportRavenna(DBCPManager dbString,String language){
		this.language = language;
		this.db = dbString;
	}
	
	
	
	public String buildSingoloBookmark(AbstractPplProcess process,String bookmarkRavenna) throws Exception{
		String xml="";
		settaComuneSelezionato(process,bookmarkRavenna);
		settaSettoreScelto(process,bookmarkRavenna);
		settaAlberoOperazioni(process,bookmarkRavenna);
		settaInterventiFacoltativi(process,bookmarkRavenna);
		settaListaAllegati(process,bookmarkRavenna);
		
		settaDescBookmark(process,bookmarkRavenna);
		settaLastActivityId(process,bookmarkRavenna);
		setta_LastActivityIdx(process,bookmarkRavenna);
		settaLastStepId(process,bookmarkRavenna);
		setta_LastStepIdx(process,bookmarkRavenna);		
		settaNomeBookmark(process,bookmarkRavenna);
		xml = Bean2XML.marshallPplData(process.getData(), process.getContext().getCharacterEncoding());
		return xml;
	}


	
	private void settaNomeBookmark(AbstractPplProcess process,String bookmarkRavenna) {
		// TODO Auto-generated method stub
		
	}



	private void setta_LastStepIdx(AbstractPplProcess process,String bookmarkRavenna) {
		ProcessData dataForm = (ProcessData) process.getData();
		dataForm.setLastStepIdx(4);
	}



	private void settaLastStepId(AbstractPplProcess process,String bookmarkRavenna) {
		ProcessData dataForm = (ProcessData) process.getData();
		dataForm.setLastStepId("4");
		
	}



	private void setta_LastActivityIdx(AbstractPplProcess process,String bookmarkRavenna) {
		ProcessData dataForm = (ProcessData) process.getData();
		dataForm.setLastActivityIdx(0);
		
	}



	private void settaLastActivityId(AbstractPplProcess process,String bookmarkRavenna) {
		ProcessData dataForm = (ProcessData) process.getData();
		dataForm.setLastActivityId("0");
	}



	private void settaDescBookmark(AbstractPplProcess process,String bookmarkRavenna) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			dataForm.setDescBookmark("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void settaListaAllegati(AbstractPplProcess process,String bookmarkRavenna) {
		try {
//			ProcessData dataForm = (ProcessData) process.getData();
//			
//			HashMap nuovaListaAllegati = new HashMap();
//			HashMap listaAllegFac = new HashMap();
//			
//			Set set = dataForm.getListaAllegati().keySet();
//			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
//				String codAllegato = (String) iterator.next();
//				AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(codAllegato);
//				if (!(allegato.getCodiceCondizione()!=null && !allegato.getCodiceCondizione().equalsIgnoreCase(""))){
//					nuovaListaAllegati.put(codAllegato,allegato);
//				} else {
//					listaAllegFac.put(codAllegato,allegato);
//				}
//			}
//			dataForm.setListaAllegati(nuovaListaAllegati);
//			
//			
//			ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
//			ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm,nuovaListaAllegati);
		} catch (Exception e){
			e.printStackTrace();
		}
	}



	private void settaInterventiFacoltativi(AbstractPplProcess process,String bookmarkRavenna) {
		try {
//			String[] codiciIntervFac = getTagValue("/it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData/OperazioniValide/Operazione/InterventiFacoltativi/ArrayOfEntryOfStringIntervento");
//			for (int i = 0; i < codiciIntervFac.length; i++) {
//				System.out.println("-->"+codiciIntervFac[i]);
//			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}



	private void settaSettoreScelto(AbstractPplProcess process,String bookmarkRavenna) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
			ArrayList alberoSettori = new ArrayList();
			delegate.calcolaAlberoSettori2(dataForm.getComuneSelezionato().getCodEnte(),alberoSettori);
			// ricostruisco l'alberatura 
			for (Iterator iterator = alberoSettori.iterator(); iterator.hasNext();) {
				SettoreBean settore = (SettoreBean) iterator.next();
				for (Iterator iterator2 = alberoSettori.iterator(); iterator2.hasNext();) {
					SettoreBean sett = (SettoreBean) iterator2.next();
					if (settore.getCodiceRamo().equalsIgnoreCase(sett.getCodiceRamoPadre())) {
						settore.addListaCodiciFigli(sett.getCodiceRamo());
					}
				}
			}
			String[] codice = getTagValue("/it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData/Settore/Codice",bookmarkRavenna);
			if (codice!=null && codice.length==1){
				for (Iterator iterator = alberoSettori.iterator(); iterator.hasNext();) {
					SettoreBean settore = (SettoreBean) iterator.next();
					if (settore!=null && settore.getCodice()!=null && settore.getCodice().equalsIgnoreCase(codice[0])){
						settore.setSelezionato(true);
						dataForm.setSettoreScelto(settore);
					}
				}
			}
			dataForm.setAlberoSettori(alberoSettori);
			
			
			dataForm.setAlberoOperazioni(new ArrayList());
			dataForm.setFineSceltaOp(false);
			dataForm.setLivelloSceltaOp(1);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}



	private void settaComuneSelezionato(AbstractPplProcess process,String bookmarkRavenna) {
		try {
			String[] codice = getTagValue("/it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData/Comune/Codice",bookmarkRavenna);
			if (codice!=null && codice.length==1){
				ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
				ComuneBean comuneSelezionato = delegate.getDettaglioComune(codice[0]);
				ProcessData dataForm = (ProcessData) process.getData();
				dataForm.setComuneSelezionato(comuneSelezionato);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	private void settaAlberoOperazioni(AbstractPplProcess process,String bookmarkRavenna) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			String[] listaCodiciOperazioni = getTagValue("/it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData/OperazioniValide/Operazione/Codice",bookmarkRavenna);

			ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
			ArrayList alberoOperazioni = new ArrayList(); 
			delegate.calcolaAlberoOperazioni(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getComuneSelezionato().getTipAggregazione(),alberoOperazioni,dataForm.getSettoreScelto().getCodice());
			
			// ricostruisco alberatura
			for (Iterator iterator = alberoOperazioni.iterator(); iterator.hasNext();) {
				OperazioneBean op = (OperazioneBean) iterator.next();
				for (Iterator iterator2 = alberoOperazioni.iterator(); iterator2.hasNext();) {
					OperazioneBean op2 = (OperazioneBean) iterator2.next();
					if ( op2.getCodicePadre()!=null && op2.getCodicePadre().equalsIgnoreCase(op.getCodiceOperazione())){
						op.addListaCodiciFigli(op2.getCodiceOperazione());
					}
				}
			}
			
			for (int i = 0; i < listaCodiciOperazioni.length; i++) {
				boolean trovato = false;
				Iterator it2 = alberoOperazioni.iterator();
				while (it2.hasNext() && !trovato){
					OperazioneBean op = (OperazioneBean) it2.next();
					if (op!=null && op.getCodice()!=null && op.getCodice().equalsIgnoreCase(listaCodiciOperazioni[i])){
						op.setSelezionato(true);
						trovato=true;
					}
				}
			}
			
			String[] listaCodiciOperazioniPadri = getTagValue("/it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData/BkmkIdOperazioniSelezionate/string",bookmarkRavenna);
			if (listaCodiciOperazioniPadri!=null){
				for (int i = 0; i < listaCodiciOperazioniPadri.length; i++) {
					boolean trovato = false;
					Iterator it2 = alberoOperazioni.iterator();
					while (it2.hasNext() && !trovato){
						OperazioneBean op = (OperazioneBean) it2.next();
						if (op!=null && op.getCodiceOperazione()!=null && op.getCodiceOperazione().equalsIgnoreCase(listaCodiciOperazioniPadri[i])){
							op.setSelezionato(true);
							trovato=true;
						}
					}
				}
			}
			
			dataForm.setAlberoOperazioni(alberoOperazioni);
			dataForm.setFineSceltaOp(true);
			
			
			delegate.calcolaInterventi(dataForm);
			delegate.calcolaAllegatiPerInterventi(dataForm.getInterventi(),dataForm.getListaAllegati(),dataForm.getComuneSelezionato().getCodEnte());
			delegate.calcolaNormativePerInterventi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi(),dataForm.getListaNormative());
			String pythonModule="";
			pythonModule = delegate.calcolaProcedimenti(dataForm,false);
			delegate.calcolaSportelli(pythonModule, dataForm);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	protected String[] getTagValue(String XPath,String bookmarkRavenna) throws Exception{
		String[] ret=null;
		try {
			Vector ss = (Vector) getElemento(XPath,bookmarkRavenna);
			if (ss!=null) {
				ret = new String[ss.size()];
				for (int i=0;i<ss.size();i++){
					String val = (String) ss.get(i);
					ret[i] = val;
				}
			}				
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}
	
	protected Vector getElemento(String xpath,String bookmarkRavenna){

        Vector retVal = null;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        builderFactory.setValidating(false);
        DocumentBuilder builder = null;
        try{
           builder = builderFactory.newDocumentBuilder();
        }catch(ParserConfigurationException e){
             //e.printStackTrace();
            //logger.error("Errore:"+e.getMessage());
        }
        Document xmlDoc = null;
        try{
            byte[] b = bookmarkRavenna.getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            xmlDoc = builder.parse(bais);
        } catch(SAXException e){
        	//e.printStackTrace();
        	//logger.error("Errore:"+e.getMessage());
        } catch(IOException e){
        	//e.printStackTrace();
        	//logger.error("Errore:"+e.getMessage());
        }
       
        try {
            // Get the matching elements
            NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(xmlDoc, xpath);
        
            // Process the elements in the nodelist
            retVal = new Vector();
            for (int i=0; i<nodelist.getLength(); i++) {
                // Get element
                Element elem = (Element)nodelist.item(i);
                NodeList nd = elem.getChildNodes();
                if(nd.getLength()>1){// se l'elemento non � unico faccio un'ulteriore iterazione
                	for(int j=0;i<nd.getLength();i++){
                		Node n = nd.item(i);
                		if(n.getNodeName().equalsIgnoreCase("#text")){
                			if(n.getNodeValue() != null){
                				retVal.add(n.getNodeValue());
                			}
                		}
                	}
                }//fine nodelist con + elementi
                if(nd.getLength() == 1){// nodelist con un solo elemento
                  
                	Node n = nd.item(0);
                	if(n.getNodeName().equalsIgnoreCase("#text")){
                		retVal.add(n.getNodeValue());
                	}
                }// fine nodelist con un solo elemnto
            }
            return retVal;
        } catch (javax.xml.transform.TransformerException e) {
            //e.printStackTrace();
            //logger.error("Errore:"+e.getMessage());
            return retVal;
        }
    }



	public boolean saveSingoloBookmarkLibero(String newXML,String configuration,String codEnte,String codiceEventoVita,String descrizioneServizio,String titoloServizio) throws Exception{
		ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
		return delegate.saveNewBookmarkLibero(newXML,configuration,codEnte,codiceEventoVita,descrizioneServizio,titoloServizio);		
	}



	public String getXMLServizioNET(String codServ) throws Exception{
		ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
		String codEnte = "";
		String codiceEventoVita = "";
		String codiceServizio = "";
		String[] tokens = codServ.split("_");
		codEnte = tokens[0];
		codiceEventoVita = tokens[1];
		codiceServizio = tokens[2];
		String xml = delegate.getXMLServizio(codEnte,codiceEventoVita,codiceServizio);
		return xml;
	}



	public ArrayList getListaServiziDaImportare(String codEnte) throws Exception {
		ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
		ArrayList lista = delegate.getListaServiziNET(codEnte);
		return lista;
	}



	public void saveSingoloBookmark(String newXML, String configuration,String cod)  throws Exception {
		ImportRavennaDAO delegate = new ImportRavennaDAO(db, language);
		delegate.saveNewBookmark(newXML,configuration,cod);		
		return;
	}



	

    
//    protected void initialise2(IRequestWrapper request) {
//    	db = new DBCPManagerNoNaming(driverDB);
//    	db.setUrlDB(urlConnDB);	
//    }
    
    
	
}
