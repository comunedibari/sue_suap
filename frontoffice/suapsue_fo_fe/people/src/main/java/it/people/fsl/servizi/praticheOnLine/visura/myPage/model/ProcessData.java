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
package it.people.fsl.servizi.praticheOnLine.visura.myPage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.core.PeopleContext;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBeanExtended;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.TipologiaPraticheSelezionabili;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.data.AbstractData;
import it.people.process.data.PplPersistentData;
import it.people.vsl.PipelineData;
import it.wego.cross.webservices.cxf.interoperability.Evento;
import it.wego.cross.webservices.cxf.interoperability.TipologiaEventoIntegrazione;

public class ProcessData  extends AbstractData {
	
	public static final String ORDINAMENTO_CRESCENTE = "asc";
	public static final String ORDINAMENTO_DECRESCENTE = "desc";
	
	private static Logger logger = LoggerFactory.getLogger(ProcessData.class);
	
	String codicePratica;
	String dataCreazioneDa;
	String dataCreazioneA;
	String dataUltimaModificaDa;
	String dataUltimaModificaA;
	String dataInvioDa;
	String dataInvioA;
	boolean inCompilazione;
//	boolean inviate;
	String oggetto;
	String numPratichePerPag;
	String ordinamentoPraticheInviate;
	String ordinamentoPraticheInCompilazione;
// PC - Paginazione inizio
        String numPratiche;
// PC - Paginazione fine          
	
	ArrayList listaPratiche;
	PraticaBean praticaSelezionata;
	PraticaBeanExtended dettaglioPratica;
    String tipologiaSelezionata;
	int oid;
	
	private boolean pendingPayments = false;
	private boolean failedPayments = false;

    private int failedSendsNumber;

    private boolean failedSends;

    Vector failedSendOids;
    /* Requisito R6*/
    it.wego.cross.webservices.cxf.interoperability.Evento[] eventi;
    /* Requisito R6*/
    private FormFile uploadFile;
    private List<Attachment> allegatiSalvati;
    private List<TipologiaEventoIntegrazione> listaTipologiaEventi;
    private String tipologiaEventoIntegrazioneSelez;
    private String messaggioErrore;
    
	public ProcessData(){
		this.codicePratica="";
		this.dataCreazioneDa="";
		this.dataCreazioneA="";
		this.dataUltimaModificaDa="";
		this.dataUltimaModificaA="";
		this.dataInvioDa="";
		this.dataInvioA="";
		this.inCompilazione=true;
//		this.inviate=true;
		this.oggetto="";
		this.listaPratiche=new ArrayList();
		this.numPratichePerPag="10";
// PC - Paginazione inizio
                this.numPratiche="0";
// PC - Paginazione fine                  
		this.praticaSelezionata=null;
		this.dettaglioPratica=null;
		this.setOrdinamentoPraticheInviate(ORDINAMENTO_DECRESCENTE);
		this.setOrdinamentoPraticheInCompilazione(ORDINAMENTO_DECRESCENTE);
		this.oid=-1;
		this.setFailedSendOids(new Vector());
		this.setFailedSends(false);
		this.setFailedSendsNumber(0);
        this.setTipologiaSelezionata(TipologiaPraticheSelezionabili.inCompilazione.getCodice());
        this.uploadFile = null;
        this.allegatiSalvati = null;
        this.listaTipologiaEventi = null;
        this.tipologiaEventoIntegrazioneSelez ="";
        this.messaggioErrore = "";
	}

	
	protected void doDefineValidators() {
		// TODO Auto-generated method stub
		
	}


	public void exportToPipeline(PipelineData arg0) {
		// TODO Auto-generated method stub
		
	}


	public void initialize(PeopleContext arg0, AbstractPplProcess arg1) {
		// TODO Auto-generated method stub
		
	}

	public String getCodicePratica() {
		return codicePratica;
	}

	public void setCodicePratica(String codicePratica) {
		this.codicePratica = codicePratica;
	}

	public String getDataCreazioneDa() {
		return dataCreazioneDa;
	}

	public void setDataCreazioneDa(String dataCreazioneDa) {
		this.dataCreazioneDa = dataCreazioneDa;
	}

	public String getDataCreazioneA() {
		return dataCreazioneA;
	}

	public void setDataCreazioneA(String dataCreazioneA) {
		this.dataCreazioneA = dataCreazioneA;
	}

	public String getDataUltimaModificaDa() {
		return dataUltimaModificaDa;
	}

	public void setDataUltimaModificaDa(String dataUltimaModificaDa) {
		this.dataUltimaModificaDa = dataUltimaModificaDa;
	}

	public String getDataUltimaModificaA() {
		return dataUltimaModificaA;
	}

	public void setDataUltimaModificaA(String dataUltimaModificaA) {
		this.dataUltimaModificaA = dataUltimaModificaA;
	}

	public boolean isInCompilazione() {
		return inCompilazione;
	}

	public void setInCompilazione(boolean inCompilazione) {
		this.inCompilazione = inCompilazione;
	}

//	public boolean isInviate() {
//		return inviate;
//	}
//
//	public void setInviate(boolean inviate) {
//		this.inviate = inviate;
//	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public ArrayList getListaPratiche() {
		return listaPratiche;
	}

	public void setListaPratiche(ArrayList listaPratiche) {
		this.listaPratiche = listaPratiche;
	}

	public void addListaPratiche(PraticaBean pratica) {
		if (this.listaPratiche==null){
			this.listaPratiche = new ArrayList();
		}
		this.listaPratiche.add(pratica);
	}


	public String getNumPratichePerPag() {
		return numPratichePerPag;
	}


	public void setNumPratichePerPag(String numPratichePerPag) {
		this.numPratichePerPag = numPratichePerPag;
	}


	public PraticaBean getPraticaSelezionata() {
		return praticaSelezionata;
	}


	public void setPraticaSelezionata(PraticaBean praticaSelezionata) {
		this.praticaSelezionata = praticaSelezionata;
	}


	public String getDataInvioDa() {
		return dataInvioDa;
	}


	public void setDataInvioDa(String dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}


	public String getDataInvioA() {
		return dataInvioA;
	}


	public void setDataInvioA(String dataInvioA) {
		this.dataInvioA = dataInvioA;
	}


	public PraticaBeanExtended getDettaglioPratica() {
		return dettaglioPratica;
	}


	public void setDettaglioPratica(PraticaBeanExtended dettaglioPratica) {
		this.dettaglioPratica = dettaglioPratica;
	}


	public int getOid() {
		return oid;
	}


	public void setOid(int oid) {
		this.oid = oid;
	}


	/**
	 * @return the ordinamentoPraticheInviate
	 */
	public final String getOrdinamentoPraticheInviate() {
		return this.ordinamentoPraticheInviate;
	}


	/**
	 * @param ordinamentoPraticheInviate the ordinamentoPraticheInviate to set
	 */
	public final void setOrdinamentoPraticheInviate(String ordinamentoPraticheInviate) {
		this.ordinamentoPraticheInviate = ordinamentoPraticheInviate;
	}


	/**
	 * @return the ordinamentoPraticheInCompilazione
	 */
	public final String getOrdinamentoPraticheInCompilazione() {
		return this.ordinamentoPraticheInCompilazione;
	}


	/**
	 * @param ordinamentoPraticheInCompilazione the ordinamentoPraticheInCompilazione to set
	 */
	public final void setOrdinamentoPraticheInCompilazione(String ordinamentoPraticheInCompilazione) {
		this.ordinamentoPraticheInCompilazione = ordinamentoPraticheInCompilazione;
	}


	/**
	 * @return the pendingPayments
	 */
	public final boolean isPendingPayments() {
		return this.pendingPayments;
	}


	/**
	 * @param pendingPayments the pendingPayments to set
	 */
	public final void setPendingPayments(boolean pendingPayments) {
		this.pendingPayments = pendingPayments;
	}


	/**
	 * @return the failedPayments
	 */
	public final boolean isFailedPayments() {
		return this.failedPayments;
	}


	/**
	 * @param failedPayments the failedPayments to set
	 */
	public final void setFailedPayments(boolean failedPayments) {
		this.failedPayments = failedPayments;
	}


	public final int getFailedSendsNumber() {
		return failedSendsNumber;
	}


	public final void setFailedSendsNumber(int failedSendsNumber) {
        this.failedSendsNumber = failedSendsNumber;
        setFailedSends(failedSendsNumber > 0);
	}


	public final Vector getFailedSendOids() {
		return failedSendOids;
	}


	public final void setFailedSendOids(Vector failedSendOids) {
		this.failedSendOids = failedSendOids;
	}


	public final boolean isFailedSends() {
		return failedSends;
	}


	public final void setFailedSends(boolean failedSends) {
		this.failedSends = failedSends;
	}


	public final String getTipologiaSelezionata() {
		return tipologiaSelezionata;
	}


	public final void setTipologiaSelezionata(String tipologiaSelezionata) {
		this.tipologiaSelezionata = tipologiaSelezionata;
	}
// PC - Paginazione inizio
	public String getNumPratiche() {
		return numPratiche;
	}


	public void setNumPratiche(String numPratiche) {
		this.numPratiche = numPratiche;
	}
// PC - Paginazione fine 	



	/* Requisito R6*/
	public it.wego.cross.webservices.cxf.interoperability.Evento[] getEventi() {
		return eventi;
	}
	public void setEventi(it.wego.cross.webservices.cxf.interoperability.Evento[] eventi) {
		this.eventi = eventi;
	}
	/* Requisito R6*/


	public FormFile getUploadFile() {
		return uploadFile;
	}


	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}


	public List<Attachment> getAllegatiSalvati() {
		return allegatiSalvati;
	}


	public void setAllegatiSalvati(List<Attachment> allegatiSalvati) {
		this.allegatiSalvati = allegatiSalvati;
	}


	public List<TipologiaEventoIntegrazione> getListaTipologiaEventi() {
		return listaTipologiaEventi;
	}


	public void setListaTipologiaEventi(List<TipologiaEventoIntegrazione> listaTipologiaEventi) {
		this.listaTipologiaEventi = listaTipologiaEventi;
	}


	public String getTipologiaEventoIntegrazioneSelez() {
		return tipologiaEventoIntegrazioneSelez;
	}


	public void setTipologiaEventoIntegrazioneSelez(String tipologiaEventoIntegrazioneSelez) {
		this.tipologiaEventoIntegrazioneSelez = tipologiaEventoIntegrazioneSelez;
	}


	public String getMessaggioErrore() {
		return messaggioErrore;
	}


	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	
	
	
	
	
}
