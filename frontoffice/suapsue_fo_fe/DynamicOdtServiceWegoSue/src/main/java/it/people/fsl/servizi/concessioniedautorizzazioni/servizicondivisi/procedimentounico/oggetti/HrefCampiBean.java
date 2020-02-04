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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HrefCampiBean implements Serializable {

	
	private String nome;
	private String descrizione;
	private int riga;
	private int posizione;
	private String tipo;
	private String controllo;
	private String valore;
	private String valoreUtente;
	private int numCampo;
	private List opzioniCombo;
    private String web_serv;
    private String nome_xsd;
    private String campo_key;
    private String campo_dati;
    private String tp_controllo;
    private int lunghezza;
    private int decimali;
    private List tabellaPrecompilazione;
    private String edit;
    private String contatore;
	private String campo_xml_mod;
    private String raggruppamento_check;
    private String campo_collegato;
    private String val_campo_collegato;
    
	public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    /**
     * @return Returns the tabellaPrecompilazione.
     */
    /*public List getTabellaPrecompilazione() {
        return tabellaPrecompilazione;
    }*/

    /**
     * @param tabellaPrecompilazione The tabellaPrecompilazione to set.
     */
    /*public void setTabellaPrecompilazione(List tabellaPrecompilazione) {
        this.tabellaPrecompilazione = tabellaPrecompilazione;
    }
    
    public void addTabellaPrecompilazione(Vector vec){
        tabellaPrecompilazione.add(vec);
    }*/


    /**
     * @return Returns the campo_dati.
     */
    public String getCampo_dati() {
        return campo_dati;
    }

    /**
     * @param campo_dati The campo_dati to set.
     */
    public void setCampo_dati(String campo_dati) {
        this.campo_dati = campo_dati;
    }

    /**
     * @return Returns the campo_key.
     */
    public String getCampo_key() {
        return campo_key;
    }

    /**
     * @param campo_key The campo_key to set.
     */
    public void setCampo_key(String campo_key) {
        this.campo_key = campo_key;
    }

    /**
     * @return Returns the nome_xsd.
     */
    public String getNome_xsd() {
        return nome_xsd;
    }

    /**
     * @param nome_xsd The nome_xsd to set.
     */
    public void setNome_xsd(String nome_xsd) {
        this.nome_xsd = nome_xsd;
    }

    /**
     * @return Returns the web_serv.
     */
    public String getWeb_serv() {
        return web_serv;
    }

    /**
     * @param web_serv The web_serv to set.
     */
    public void setWeb_serv(String web_serv) {
        this.web_serv = web_serv;
    }

    public HrefCampiBean() {
		nome="";
		descrizione="";
		riga=1;
		posizione=1;
		tipo="";
		controllo="";
		valore="";
		valoreUtente="";
		numCampo=0;
		opzioniCombo = new ArrayList();
        //tabellaPrecompilazione = new LinkedList();
	}

	public String getControllo() {
		return controllo;
	}
	public void setControllo(String controllo) {
		this.controllo = controllo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPosizione() {
		return posizione;
	}
	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}
	public int getRiga() {
		return riga;
	}
	public void setRiga(int riga) {
		this.riga = riga;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getValoreUtente() {
		return valoreUtente;
	}
	public void setValoreUtente(String valoreUtente) {
		this.valoreUtente = valoreUtente;
	}
	/**
	 * @return Returns the numCampo.
	 */
	public int getNumCampo() {
		return numCampo;
	}
	/**
	 * @param numCampo The numCampo to set.
	 */
	public void setNumCampo(int numCampo) {
		this.numCampo = numCampo;
	}
    /**
     * @return Returns the opzioniCombo.
     */
    public List getOpzioniCombo() {
        return opzioniCombo;
    }
    /**
     * @param opzioniCombo The opzioniCombo to set.
     */
    public void setOpzioniCombo(List opzioniCombo) {
        this.opzioniCombo = opzioniCombo;
    }
    
    public void addOpzioniCombo(BaseBean bean){
        opzioniCombo.add(bean);
    }

    /**
     * @return Returns the tp_controllo.
     */
    public String getTp_controllo() {
        return tp_controllo;
    }

    /**
     * @param tp_controllo The tp_controllo to set.
     */
    public void setTp_controllo(String tp_controllo) {
        this.tp_controllo = tp_controllo;
    }

    /**
     * @return Returns the lunghezza.
     */
    public int getLunghezza() {
        return lunghezza;
    }

    /**
     * @param lunghezza The lunghezza to set.
     */
    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    /**
     * @return Returns the decimali.
     */
    public int getDecimali() {
        return decimali;
    }

    /**
     * @param decimali The decimali to set.
     */
    public void setDecimali(int decimali) {
        this.decimali = decimali;
    }

    public String getContatore() {
        return contatore;
    }

    public void setContatore(String contatore) {
        this.contatore = contatore;
    }

    public String getCampo_xml_mod() {
        return campo_xml_mod;
    }

    public void setCampo_xml_mod(String campo_xml_mod) {
        this.campo_xml_mod = campo_xml_mod;
    }

    public String getCampo_collegato() {
        return campo_collegato;
    }

    public void setCampo_collegato(String campo_collegato) {
        this.campo_collegato = campo_collegato;
    }

    public String getRaggruppamento_check() {
        return raggruppamento_check;
    }

    public void setRaggruppamento_check(String raggruppamento_check) {
        this.raggruppamento_check = raggruppamento_check;
    }

    public String getVal_campo_collegato() {
        return val_campo_collegato;
    }

    public void setVal_campo_collegato(String val_campo_collegato) {
        this.val_campo_collegato = val_campo_collegato;
    }
}
