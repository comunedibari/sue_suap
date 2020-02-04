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
package it.people.console.web.controllers.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.rpc.ServiceException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.console.domain.AccreditamentiManagementFilter;
import it.people.console.domain.AccreditamentiQualificheFilter;
import it.people.feservice.beans.AuditConversationsBean;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.services.accr.management.IAccreditamentiManagementWS;
import it.people.sirac.services.accr.management.IAccreditamentiManagementWSServiceLocator;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 01/lug/2011 11.29.50
 * 
 */

public class AccreditamentiManagementViewer {
	
	private static Logger logger = LoggerFactory.getLogger(AccreditamentiManagementViewer.class);
    
	private DataSource dataSourcePeopleDB;
    protected int startingPoint;
    protected int pageSize;
    private String endpointURLString;
    
    
	public void goToLastPage(int lastPage) {
		if (lastPage == 0) {
			this.startingPoint = 0;
		} else {
			// (lastPage -1 ) * pageSize
			this.startingPoint = (lastPage - 1) * pageSize;
		}
	}
    
	public void increaseStartingPoint(int res_count) {
		if ((startingPoint + pageSize) < res_count) {
			this.startingPoint = startingPoint + pageSize;
		}
	}

	public void decreaseStartingPoint() {
		if (startingPoint >= pageSize) {
			this.startingPoint = startingPoint - pageSize;
		}
	}

	public void setStartingPoint(int startingPoint) {
		this.startingPoint = startingPoint;
	}
	public int getStartingPoint() {
		return this.startingPoint;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageSize() {
		return this.pageSize;
	}

	protected IAccreditamentiManagementWS accrService() 
    throws ServiceException{
		IAccreditamentiManagementWSServiceLocator accrClientLocator = new IAccreditamentiManagementWSServiceLocator();
		accrClientLocator.setIAccreditamentiManagementWSEndpointAddress(endpointURLString);
		IAccreditamentiManagementWS accrService = accrClientLocator.getIAccreditamentiManagementWS();
		
    	return accrService;
    }
    
	
    public AccreditamentiManagementViewer(DataSource dataSourcePeopleDB, HttpServletRequest request, int startingPoint, int pageSize) {
        this.dataSourcePeopleDB = dataSourcePeopleDB;
        this.startingPoint = startingPoint;
        this.pageSize = pageSize;
        setEndPointAddress(request);
    }
    
    public AccreditamentiManagementViewer(DataSource dataSourcePeopleDB, HttpServletRequest request) {
    	this.dataSourcePeopleDB = dataSourcePeopleDB;
    	this.startingPoint = 0;
    	this.pageSize = 0;
    	setEndPointAddress(request);
    }
    
    public AccreditamentiManagementViewer(HttpServletRequest request, int startingPoint, int pageSize) {
        this.startingPoint = startingPoint;
        this.pageSize = pageSize;
    	setEndPointAddress(request);
    }
    
    private void setEndPointAddress(HttpServletRequest request){
    	String IAccreditamentiManagementURLString = 
	    	request.getSession().getServletContext().getInitParameter("people.sirac.webservice.accreditamentiManagement.address");
    	this.endpointURLString = IAccreditamentiManagementURLString;
    }
    
    /** Elenco Qualifiche */
	public Qualifica[] getQualifiche() throws AccreditamentiManagementViewerException {

		try {
			Qualifica[] qualifiche = accrService().getQualifiche(Integer.toString(0), Integer.toString(0));
			return qualifiche;

		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare le qualifiche presenti.", e);
		}
	}
	
	
	/** Elenco Comuni */
	public String[] getComuni() throws AccreditamentiManagementViewerException {
		
		try {
			String[] comuni = accrService().getComuni();
			
			return comuni;
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare gli enti presenti.", e);
		}
	}
	
	
	/** Elenco TipiQualifica */
	public String[] getTipiQualifica() throws AccreditamentiManagementViewerException {
		
		try {
			String[] tipiQualifica = accrService().getTipiQualifica();
			
			return tipiQualifica;
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare i tipi qualifica presenti.", e);
		}
	}
	
	 /** Elenco Qualifiche (filtrate) */
	public Qualifica[] getQualifiche(AccreditamentiQualificheFilter filter) throws AccreditamentiManagementViewerException {

		try {
			Qualifica[] qualifiche = accrService().getQualifiche(Integer.toString(this.startingPoint), Integer.toString(this.pageSize));
			return qualifiche;

		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare le qualifiche presenti.", e);
		}
	}
	
	/** Qualifica */
	public Qualifica getQualifica(String idQualifica) throws AccreditamentiManagementViewerException {
		
		try {
			Qualifica qualifica = accrService().getQualifica(idQualifica);
			return qualifica;
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile recuperare la qualifica selezionata.", e);
		}
	}
	
	public String[] getInfoQualifica(String id_qualifica) throws AccreditamentiManagementViewerException {
		try {
    		return accrService().getInfoQualifica(id_qualifica);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile recuperare le informazioni relative alla qualifica : "+id_qualifica+" .", e);
		}
	}
	
	/** Accreditamenti  */
	public Accreditamento[] getAccreditamenti(AccreditamentiManagementFilter filter) throws AccreditamentiManagementViewerException {
		
		try {
			
			String idQualifica = filter.getTipoQualifica();
    		String idComune = filter.getNomeEnte();
    		String codiceFiscaleUtenteAccreditato = filter.getTaxcodeUtente();
    		String codiceFiscaleIntermediario = filter.getTaxcodeIntermediario();
    		String partitaIvaIntermediario = filter.getVatnumberIntermediario();
    		String domicilioElettronicoIntermediario = filter.getE_addressIntermediario();
    		String denominazioneIntermediario = filter.getTipologiaIntermediario();
    		String sedeLegale = filter.getSedeLegale();
    		String statoAccreditamento = filter.getStatoAccreditamento();

    		Accreditamento[] accreditamenti = accrService().getAccreditamentiBySearch(
    				idQualifica, idComune, codiceFiscaleUtenteAccreditato, codiceFiscaleIntermediario,  
    	    		partitaIvaIntermediario, domicilioElettronicoIntermediario, denominazioneIntermediario, sedeLegale, 
    	    		statoAccreditamento, Integer.toString(this.startingPoint), Integer.toString(this.pageSize));
			   		
    		return accreditamenti;
    		
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare i tipi qualifica presenti.", e);
		}
	}
    
	/** Accreditamenti - numero risultati */
	public String getNumAccreditamenti(AccreditamentiManagementFilter filter) throws AccreditamentiManagementViewerException {
		
		try {
			
			String idQualifica = filter.getTipoQualifica();
			String idComune = filter.getNomeEnte();
			String codiceFiscaleUtenteAccreditato = filter.getTaxcodeUtente();
			String codiceFiscaleIntermediario = filter.getTaxcodeIntermediario();
			String partitaIvaIntermediario = filter.getVatnumberIntermediario();
			String domicilioElettronicoIntermediario = filter.getE_addressIntermediario();
			String denominazioneIntermediario = filter.getTipologiaIntermediario();
			String sedeLegale = filter.getSedeLegale();
			String statoAccreditamento = filter.getStatoAccreditamento();
			
			String numRisultati = accrService().getNumAccreditamentiBySearch(
					idQualifica, idComune, codiceFiscaleUtenteAccreditato, codiceFiscaleIntermediario,  
					partitaIvaIntermediario, domicilioElettronicoIntermediario, denominazioneIntermediario, sedeLegale, 
					statoAccreditamento);
			
			return numRisultati;
			
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare il numero totale di accreditamenti presenti.", e);
		}
	}
	
	/** Accreditamento  */
	public Accreditamento getAccreditamentoById(String idAccreditamento) throws AccreditamentiManagementViewerException {
		
		try {
    		return accrService().getAccreditamentoById(Integer.parseInt(idAccreditamento));
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile recuperare l'accreditamento con id: "+idAccreditamento+" .", e);
		}
	}
	
	
	/** Operatori  */
	public Accreditamento[] getOperatoriAssociazione(String idComune, String codiceFiscaleIntermediario, String partitaIvaIntermediario) 
		throws AccreditamentiManagementViewerException {
		
		try {
			
    		Accreditamento[] operatori = accrService().getOperatoriAssociazione(idComune, codiceFiscaleIntermediario, partitaIvaIntermediario, 
    				Integer.toString(this.startingPoint), Integer.toString(this.pageSize));
    		
    		return operatori;
    		
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare gli operatori dell'associazione.", e);
		}
	}
	
	/** Accreditamento deleted  */
	public boolean isAccreditamentoDeleted(int idAccreditamento) 
	throws AccreditamentiManagementViewerException {
		
		try {
			
			return accrService().isAccreditamentoDeleted(idAccreditamento);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile determinare se l'accreditamento è stato eliminato.", e);
		}
	}
	
	/** Accreditamenti imposta 'attivo'  */
	public boolean setAccreditamenti_Attivo(String[] attivo, String[] non_attivo)
	throws AccreditamentiManagementViewerException {
		
		try {
			
			return accrService().setAccreditamenti_Attivo(attivo, non_attivo);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile impostare il valore dell'attributo 'attivo' agli accreditamenti selezionati.", e);
		}
	}
	
	/** Accreditamenti imposta 'deleted'  */
	public boolean setAccreditamenti_Deleted(String[] deleted, String[] non_deleted)
	throws AccreditamentiManagementViewerException {
		
		try {
			
			return accrService().setAccreditamenti_Deleted(deleted, non_deleted);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile impostare il valore dell'attributo 'deleted' agli accreditamenti selezionati.", e);
		}
	}
	 
    
    protected ArrayList<AuditConversationsBean> getAuditConversationsList(AuditConversationsBean[] auditConvArray){
    	ArrayList<AuditConversationsBean> auditConvsList = new ArrayList<AuditConversationsBean>();
    	if (auditConvArray == null)
    		return auditConvsList;
    	
    	for(int i = 0; i < auditConvArray.length; i++) {
    		auditConvsList.add(auditConvArray[i]);
    	}
    	return auditConvsList;
    }
    
    protected ArrayList<String> getAuditUsersList(String[] auditUsersArray){
    	ArrayList<String> auditUsersList = new ArrayList<String>();
    	if (auditUsersArray == null)
    		return auditUsersList;
    	
    	for(int i = 0; i < auditUsersArray.length; i++) {
    		auditUsersList.add(auditUsersArray[i]);
    	}
    	return auditUsersList;
    }
    
    
    protected String getServiceName(int idServizio) 
    throws SQLException, Exception {
    	Connection connection = null;
    	Statement stmt = null;
    	String serviceName="";
    	try {
    		connection = dataSourcePeopleDB.getConnection();
    		stmt = connection.createStatement();
    		ResultSet res;
    		
    		res = stmt.executeQuery("SELECT package FROM service WHERE id = " + idServizio);
    		if (res.next())
				serviceName = res.getString("package");    		
    		
    	} finally {
    		try { if (stmt != null) connection.close();} catch (SQLException e) {}
    		try { if (connection != null) connection.close();} catch (SQLException e) {}
    	}
    	return serviceName;    	
    }
    
    
	/** Autocertificazione  
	 * 
	 * @param idAccreditamento
	 * @return auto_certificazione_filename, auto_certificazione
	 * @throws AccreditamentiManagementViewerException
	 */
	public String[] getAutocertificazione(String idAccreditamento) throws AccreditamentiManagementViewerException {
		
		try {
    		return accrService().getAutocertificazione(Integer.parseInt(idAccreditamento));
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException("Impossibile recuperare l'accreditamento con id: "+idAccreditamento+" .", e);
		}
	}
    
	
	/** Accreditamento imposta stato:
	 * @param idAccreditamento
	 * @param status: [1] attivo, [0] disattivo
	 */
	public void setStatoAccreditamento(String idAccreditamento, String status)
	throws AccreditamentiManagementViewerException {
		
		try {
			accrService().setAccreditamentoAttivo(Integer.parseInt(idAccreditamento), status);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException(
					"Impossibile impostare il valore dell'attributo 'attivo' per l'accreditamento avente id:" + idAccreditamento + ".", e);
		}
		
	}
	
	/** Inserisci una nuova qualifica:
	 * @param id_qualifica
	 * @param descrizione
	 * @param tipo_qualifica
	 * @param auto_certificabile
	 * @param has_rappresentante_legale
	 */
	public void insertQualifica(String id_qualifica, String descrizione,
			String tipo_qualifica, String auto_certificabile,
			String has_rappresentante_legale)
			throws AccreditamentiManagementViewerException {
		
		try {
			accrService().insertQualifica(id_qualifica, descrizione, tipo_qualifica, auto_certificabile, has_rappresentante_legale);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException(
					"Impossibile inserire la qualifica.", e);
		}
		
	}
	
	/** Aggiorna una qualifica:
	 * @param id_qualifica
	 * @param descrizione
	 * @param tipo_qualifica
	 * @param auto_certificabile
	 * @param has_rappresentante_legale
	 */
	public void updateQualifica(String id_qualifica, String descrizione,
			String tipo_qualifica, String auto_certificabile,
			String has_rappresentante_legale)
	throws AccreditamentiManagementViewerException {
		
		try {
			accrService().updateQualifica(id_qualifica, descrizione, tipo_qualifica, auto_certificabile, has_rappresentante_legale);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException(
					"Impossibile aggiornare la qualifica.", e);
		}
		
	}
	
	/** Elimina una qualifica:
	 * @param id_qualifica
	 */
	public void deleteQualifica(String id_qualifica)
	throws AccreditamentiManagementViewerException {
		
		try {
			accrService().deleteQualifica(id_qualifica);
			
		} catch (Exception e) {
			logger.error("", e);
			throw new AccreditamentiManagementViewerException(
					"Impossibile eliminare la qualifica.", e);
		}
		
	}
    
    
    public class AccreditamentiManagementViewerException extends Exception {
		private static final long serialVersionUID = -1038008912855306410L;

		public AccreditamentiManagementViewerException(String message) {
    		super(message);
    	}
    	
    	public AccreditamentiManagementViewerException(String message, Throwable inner) {
    		super(message, inner);
    	}

    }




}
