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
 * Created on 18-apr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.upload.FormFile;

import it.people.core.PeopleContext;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;

/**
 * @author fabmi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessData extends AbstractData {

	private String prova;
	private String valore1;
	private String valore2;
	private String valore3;
	private String valore4;
	private String valore5;
	private String valore6;
	private String valore7;
	private String valore8;
	private String valore9;
	private String valore10;
	private String rispostaWebService;
	private String tmpString;
	private boolean abilitaFirma;
	private String abilitaRiepilogo;
	private Data dataDiProva;
	private String dataDaValidare;
	private String booleano;
	private String codiceFiscale;
	private String partitaIVA;
	
	private String pulsantePremuto;
	private int pulsantePremutoIndice;
	
	// Validazione su pi� elementi
	// utilizzata per verificare la validazione custom
	// introdotta rispetto a struts 
	/*
	 La personalizzazione permette di definire un validatore per un input relativo ad una collezione 
	 di oggetti.
	 La pagina JSP mostra un elenco di elementi appartenenti ad una collezione, ogni elemento sar� la
	 riga di una tabella, e ogni riga avr� i campi di input abilitati-
   
   	 Es. ho un elenco di immobili, mostro sulla jsp una tabella con una riga per immobile, ogni riga
         avr� i campi input per modificare l'immobile.
	 */
	private List gruppi;
	
	// Validazione standard su una collezione di elementi
	private List utenti;
	
	// Gestione elenco
	private List elencoOggetti;
	private Elemento elemento;
	
	// Gestione Allegati
	private FormFile uploadFile=null;
	
    private String[] opsVec;

    public ProcessData() {
        super();
        this.m_clazz = ProcessData.class;
        this.elencoOggetti = new ArrayList();
        this.elemento = new Elemento();
        this.opsVec = new String[] {"false", "false", "false"};
        gruppi = new ArrayList();
        gruppi.add(new Gruppo("gruppo 1", 2));
        gruppi.add(new Gruppo("gruppo 2", 2));
        utenti = new ArrayList();
        utenti.add(new Utente("Mario", "Rossi"));
        utenti.add(new Utente("Giuseppe", "Verdi"));
    }

    public String[] getOpsVec() {
        return opsVec;
    }

    public void setOpsVec(String[] opsVec) {
        this.opsVec = opsVec;
    }

    public void addOpsVec(String str) {
        int size = this.opsVec.length + 1;
        String[] temp = new String[size];

        try {
            for (int i = 0; i < this.opsVec.length; i++) {
                temp[i] = this.opsVec[i];
            }
            temp[size - 1] = str;
            this.opsVec = temp;
        } catch (Exception e) {
        	System.out.println(e);
        }
    }

    /*
    public void addOpsVec(String str) {
        int size = this.opsVec.length + 1;
        String[] temp = new String[size];

        try {
            for (int i = 0; i < this.opsVec.length; i++) {
                temp[i] = this.opsVec[i];
            }
            temp[size - 1] = str;
            this.opsVec = temp;
        } catch (Exception e) {
        	System.out.println(e);
        }
    }	
	*/
	/**
	 * @return Returns the abilitaRiepilogo.
	 */
	public String getAbilitaRiepilogo() {
		return abilitaRiepilogo;
	}
	/**
	 * @param abilitaRiepilogo The abilitaRiepilogo to set.
	 */
	public void setAbilitaRiepilogo(String abilitaRiepilogo) {
		this.abilitaRiepilogo = abilitaRiepilogo;
	}
	
	public Data getDataDiProva() {
		return dataDiProva;
	}
	
	public void setDataDiProva(Data dataDiProva) {
		this.dataDiProva = dataDiProva;
	}

	public boolean isAbilitaFirma() {
		return abilitaFirma;
	}
	public void setAbilitaFirma(boolean abilitaFirma) {
		this.abilitaFirma = abilitaFirma;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile file) {
		uploadFile = file;
	}	
	
	public List getUtenti() {
		return this.utenti;
	}

	public void setUtenti(List utenti) {
		this.utenti = utenti;
	}	
	
	public List getGruppi() {
		return this.gruppi;
	}

	public void setGruppi(List gruppi) {
		this.gruppi = gruppi;
	}

	public List getElencoOggetti() {
		return elencoOggetti;
	}

	public void setElencoOggetti(List elencoOggetti) {
		this.elencoOggetti = elencoOggetti;
	}
	
    public void addElencoOggetti(Elemento elemento)
    {
    	elencoOggetti.add(elemento);
    }

    public void removeElencoOggetti()
    {
    	if (elencoOggetti.size() > 0)
    		elencoOggetti.remove(elencoOggetti.size() - 1);    	
    }

    public void removeElencoOggetti(int indice)
    {  	
      if (indice >= 0 && indice < elencoOggetti.size())
        elencoOggetti.remove(indice);
    }
    
    public void newElencoOggetti()
    {
    	String nome = this.elemento.getNome();
    	String valore = this.elemento.getValore();
    	elencoOggetti.add(new Elemento(nome, valore));
    }
    
    
	/* (non-Javadoc)
	 * @see it.people.process.PplData#initialize(it.people.core.PeopleContext, it.people.process.AbstractPplProcess)
	 */
	public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
		this.abilitaFirma = true;
		this.abilitaRiepilogo = "always";
	}

	/* (non-Javadoc)
	 * @see it.people.process.data.AbstractData#doDefineValidators()
	 */
	protected void doDefineValidators() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.people.process.PplData#exportToPipeline(it.people.vsl.PipelineData)
	 */
	public void exportToPipeline(PipelineData pd) {
		try {
			// Creo un microdocumento di prova
			// non � il modo giusto :-)
			String xml = "<CedafDemo>\n" +
				"\t<valore1>" + this.valore1 + "<valore1>\n" +
				"\t<valore2>" + this.valore2 + "<valore2>\n";
			
			Collection allegati = this.getAllegati();
			int i = 0;
			for (Iterator iter = allegati.iterator(); iter.hasNext();) {
				Attachment allegato = (Attachment) iter.next();
								
				System.out.print("\nnome allegato: '" + allegato.getName() + "' classe: '");
				xml += "\t<allegato" + i + ">";
				xml += allegato.getData();

				// Attenzione la classe SignedSummaryAttachment � introdotta
				// nella versione 1.2.2 di People, nelle versioni precedenti
				// per distinguere tra il riepilogo firmato alla fine e gli
				// allegati e caricati firmati � necessario verificare il nome 
				// del file che deve essere diverso da 'riepilogo.html.p7m'.
				
				if (allegato instanceof SignedSummaryAttachment) {
					System.out.print("SignedSummaryAttachment");
				} else if (allegato instanceof SignedAttachment) {
					System.out.print("SignedAttachment");				
				} else if (allegato instanceof UnsignedSummaryAttachment) {
					System.out.print("UnsignedSummaryAttachment");
				} else {
					System.out.print("Attachment");
				}
				System.out.println("'");
				
				xml += "\t</allegato" + i++ + ">\n";
			}
			xml += "<CedafDemo>\n";
			pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME, xml);		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
    public boolean validate() {
    	//if ("OK".equals(this.prova))
    		return true;
    	//else
    	//	return false;
    }
	/**
	 * @return Returns the prova.
	 */
	public String getProva() {
		return prova;
	}
	/**
	 * @param prova The prova to set.
	 */
	public void setProva(String prova) {
		this.prova = prova;
	}
	/**
	 * @return Returns the valore1.
	 */
	public String getValore1() {
		return valore1;
	}
	/**
	 * @param valore1 The valore1 to set.
	 */
	public void setValore1(String valore1) {
		this.valore1 = valore1;
	}
	/**
	 * @return Returns the valore2.
	 */
	public String getValore2() {
		return valore2;
	}
	/**
	 * @param valore2 The valore2 to set.
	 */
	public void setValore2(String valore2) {
		this.valore2 = valore2;
	}
	/**
	 * @return Returns the valore10.
	 */
	public String getValore10() {
		return valore10;
	}
	/**
	 * @param valore10 The valore10 to set.
	 */
	public void setValore10(String valore10) {
		this.valore10 = valore10;
	}
	/**
	 * @return Returns the valore3.
	 */
	public String getValore3() {
		return valore3;
	}
	/**
	 * @param valore3 The valore3 to set.
	 */
	public void setValore3(String valore3) {
		this.valore3 = valore3;
	}
	/**
	 * @return Returns the valore4.
	 */
	public String getValore4() {
		return valore4;
	}
	/**
	 * @param valore4 The valore4 to set.
	 */
	public void setValore4(String valore4) {
		this.valore4 = valore4;
	}
	/**
	 * @return Returns the valore5.
	 */
	public String getValore5() {
		return valore5;
	}
	/**
	 * @param valore5 The valore5 to set.
	 */
	public void setValore5(String valore5) {
		this.valore5 = valore5;
	}
	/**
	 * @return Returns the valore6.
	 */
	public String getValore6() {
		return valore6;
	}
	/**
	 * @param valore6 The valore6 to set.
	 */
	public void setValore6(String valore6) {
		this.valore6 = valore6;
	}
	/**
	 * @return Returns the valore7.
	 */
	public String getValore7() {
		return valore7;
	}
	/**
	 * @param valore7 The valore7 to set.
	 */
	public void setValore7(String valore7) {
		this.valore7 = valore7;
	}
	/**
	 * @return Returns the valore8.
	 */
	public String getValore8() {
		return valore8;
	}
	/**
	 * @param valore8 The valore8 to set.
	 */
	public void setValore8(String valore8) {
		this.valore8 = valore8;
	}
	/**
	 * @return Returns the valore9.
	 */
	public String getValore9() {
		return valore9;
	}
	/**
	 * @param valore9 The valore9 to set.
	 */
	public void setValore9(String valore9) {
		this.valore9 = valore9;
	}
	/**
	 * @return Returns the rispostaWebService.
	 */
	public String getRispostaWebService() {
		return rispostaWebService;
	}
	/**
	 * @param rispostaWebService The rispostaWebService to set.
	 */
	public void setRispostaWebService(String rispostaWebService) {
		this.rispostaWebService = rispostaWebService;
	}
	/**
	 * @return Returns the elemento.
	 */
	public Elemento getElemento() {
		return elemento;
	}
	/**
	 * @param elemento The elemento to set.
	 */
	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}
	
	/**
	 * @return Returns the dataDaValidare.
	 */
	public String getDataDaValidare() {
		return this.dataDaValidare;
	}
	
	/**
	 * @param dataDaValidare The dataDaValidare to set.
	 */
	public void setDataDaValidare(String dataDaValidare) {
		this.dataDaValidare = dataDaValidare;
	}
	/**
	 * @return Returns the booleano.
	 */
	public String getBooleano() {
		return booleano;
	}
	/**
	 * @param booleano The booleano to set.
	 */
	public void setBooleano(String booleano) {
		this.booleano = booleano;
	}
	/**
	 * @return Returns the codiceFiscale.
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	/**
	 * @param codiceFiscale The codiceFiscale to set.
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	/**
	 * @return Returns the partitaIVA.
	 */
	public String getPartitaIVA() {
		return partitaIVA;
	}
	/**
	 * @param partitaIVA The partitaIVA to set.
	 */
	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}
	
    public String getPulsantePremuto() {
        return pulsantePremuto;
    }
    
    public void setPulsantePremuto(String pulsantePremuto) {
        this.pulsantePremuto = pulsantePremuto;
    }
    
    public int getPulsantePremutoIndice() {
        return pulsantePremutoIndice;
    }
    
    public void setPulsantePremutoIndice(int pulsantePremutoIndice) {
        this.pulsantePremutoIndice = pulsantePremutoIndice;
    }

	/**
	 * @return the tmpString
	 */
	public final String getTmpString() {
		return this.tmpString;
	}

	/**
	 * @param tmpString the tmpString to set
	 */
	public final void setTmpString(String tmpString) {
		this.tmpString = tmpString;
	}
    
    
    
}
