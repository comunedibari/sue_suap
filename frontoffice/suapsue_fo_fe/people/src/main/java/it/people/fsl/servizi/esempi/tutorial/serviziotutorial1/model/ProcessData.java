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
/*
 * ProcessData.java
 *
 * Created on 26 dicembre 2004, 14.37
 */

package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model;

import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.oggetti.UtenteDocument;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.util.XmlObjectWrapper;
import it.people.vsl.PipelineDataImpl;
import org.apache.commons.codec.binary.Base64;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author zero
 */
public class ProcessData  extends AbstractData {
	private static Logger logger = LoggerFactory.getLogger(ProcessData.class);

	// Dati utilizzati per il test del salvataggio 
	// e ripristino di un array di istanze derivate
	private Collection familiari;	

	// Dati utilizzati per la comunicazione con il Back-End
    private String cognome;
    private String nome;
    
    private int anno;
    private int eta;
    
    private String rispostaWS;
    private boolean paginaIntermedia;
    
    private boolean abilitaPagamenti = true;

    // Dati pagamento
    private double importo = 0;
    private String emailAddress = "";

    
    // Risultato Pagamento
	private double importoPagato;
	private double importoCommissioni;
	private boolean pagamentoEffettuato;

	// Gestione Allegati
	private FormFile uploadFile=null;	
	public FormFile getUploadFile() { return uploadFile; }
	public void setUploadFile(FormFile file) { uploadFile = file; }	
	
    /** Creates a new instance of ProcessData */
    public ProcessData() {
    	this.m_clazz = ProcessData.class;
    	this.familiari = new ArrayList();
            	
    }    
    
    protected void doDefineValidators() {
    }

    public void exportToPipeline(it.people.vsl.PipelineData pd) {
        try {
            // La validit� dei dati inseriti � garantita dalla validazione
            // dello step di inserimento
             
            // Inizializzazione UtenteDocument
            UtenteDocument ud = UtenteDocument.Factory.newInstance();
            UtenteDocument.Utente utente = ud.addNewUtente();

            // Impostazione sottoelementi
            String idOperazione = this.getIdentificatoreUnivoco().getCodiceSistema().getCodiceIdentificativoOperazione();
            utente.setOperationId(idOperazione);
            utente.setNome(this.nome);
            utente.setCognome(this.cognome);
            
            // Inserimento riepilogo
            Iterator allegatiIterator = this.getAllegati().iterator(); 
            while (allegatiIterator.hasNext()) {
            	Attachment allegato = (Attachment) allegatiIterator.next();
                if (allegato instanceof UnsignedSummaryAttachment || allegato instanceof SignedSummaryAttachment) {
                	byte[] bytesContentPerOggettoXmlBeans = Base64.decodeBase64(allegato.getData().getBytes());
                }                                
            }            

            // invio il documento alla pipeline di PEOPLE
            pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME,
                            XmlObjectWrapper.generateXml(ud));
                        
        } catch (Exception ex) {
            logger.error("Errore nella creazione della pipeline data", ex);
        }   
    }

    public void initialize(it.people.core.PeopleContext context, it.people.process.AbstractPplProcess pplProcess) {        
    	// Dati Pagamento
		if (!context.getUser().isAnonymous())
			this.emailAddress = context.getUser().getEMail();
		this.importo = 100;
		
        paginaIntermedia = false;
    }
        
	public Collection getFamiliareCollection() {
		return this.familiari;
	}

	public void addFamiliare(Persona persona) {
		this.familiari.add(persona);
	}

    public boolean validate() {     
        return isNotEmpty(this.nome) && isNotEmpty(this.cognome);
    }
    
    protected boolean isNotEmpty(String value) {
        return value != null && !value.equals("");
    }
    
    // ACCESSORS    
    public void setNome(String s) {
        nome = s;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCognome() {
        return cognome;
    }
    
    public void setCognome(String s) {
        cognome = s;
    }
    
    public void setPaginaIntermedia(boolean v) {
        paginaIntermedia = v;
    }
    
    public boolean isPaginaIntermedia() {
        return paginaIntermedia;
    }

    public String getRispostaWS() {
        return rispostaWS;
    }

    public void setRispostaWS(String rispostaWS) {
        this.rispostaWS = rispostaWS;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }
	/**
	 * @return Returns the importoCommissioni.
	 */
	public double getImportoCommissioni() {
		return importoCommissioni;
	}
	/**
	 * @param importoCommissioni The importoCommissioni to set.
	 */
	public void setImportoCommissioni(double importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}
	/**
	 * @return Returns the importoPagato.
	 */
	public double getImportoPagato() {
		return importoPagato;
	}
	/**
	 * @param importoPagato The importoPagato to set.
	 */
	public void setImportoPagato(double importoPagato) {
		this.importoPagato = importoPagato;
	}
	/**
	 * @return Returns the pagamentoEffettuato.
	 */
	public boolean isPagamentoEffettuato() {
		return pagamentoEffettuato;
	}
	/**
	 * @param pagamentoEffettuato The pagamentoEffettuato to set.
	 */
	public void setPagamentoEffettuato(boolean pagamentoEffettuato) {
		this.pagamentoEffettuato = pagamentoEffettuato;
	}
	/**
	 * @return Returns the abilitaPagamenti.
	 */
	public boolean isAbilitaPagamenti() {
		return abilitaPagamenti;
	}
	/**
	 * @param abilitaPagamenti The abilitaPagamenti to set.
	 */
	public void setAbilitaPagamenti(boolean abilitaPagamenti) {
		this.abilitaPagamenti = abilitaPagamenti;
	}
	/**
	 * @return Returns the emailAddress.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress The emailAddress to set.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return Returns the importo.
	 */
	public double getImporto() {
		return importo;
	}
	/**
	 * @param importo The importo to set.
	 */
	public void setImporto(double importo) {
		this.importo = importo;
	}
	
	/**
	 * @return Returns the importoString.
	 */
	public String getImportoString() {
        return NumberFormat.getNumberInstance(Locale.ITALY).format(this.importo);
	}
	/**
	 * @param importoString The importoString to set.
	 */
	public void setImportoString(String importoString) {
		try {
			Number number = NumberFormat.getNumberInstance(Locale.ITALY).parse(importoString);
			this.importo = number.doubleValue();
		} catch(ParseException ex) {
			this.importo = 0;
		}
	}	
}
