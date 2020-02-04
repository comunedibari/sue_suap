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

public class HrefCampiBean implements Serializable {

    private static final long serialVersionUID = -7408127535188089945L;
    private String nome;
    private String descrizione;
    private int riga;
    private int posizione;
    private int molteplicita;
    private String tipo;
    private String controllo;
    private String valore;
    private String valoreUtente;
    private int numCampo;
    private ArrayList opzioniCombo;
    private String web_serv;
    private String nome_xsd;
    private String campo_key;
    private String campo_dati;
    private String tp_controllo;
    private int lunghezza;
    private int decimali;
    private String edit;
    private String contatore;
    private String campo_xml_mod;
    private String raggruppamento_check;
    private String campo_collegato;
    private String val_campo_collegato;
    private ArrayList campiCollegati;
    private String precompilazione;
    private int livello;
    private String marcatore_incrociato;
    private String azione;
    private String flg_precompilazione;
    // PC - nuovi campi href
    private String pattern;
    private String err_msg;
    // PC - nuovi campi href
    private String campoTitolare;

    public HrefCampiBean() {
        this.nome = "";
        this.descrizione = "";
        this.riga = 0;
        this.posizione = 0;
        this.tipo = "";
        this.controllo = "";
        this.valore = "";
        this.valoreUtente = "";
        this.numCampo = 0;
        this.opzioniCombo = new ArrayList();
        this.web_serv = "";
        this.nome_xsd = "";
        this.campo_key = "";
        this.campo_dati = "";
        this.tp_controllo = "";
        this.lunghezza = 0;
        this.decimali = 0;
        this.edit = "";
        this.contatore = "";
        this.campo_xml_mod = "";
        this.raggruppamento_check = "";
        this.campo_collegato = "";
        this.val_campo_collegato = "";
        this.campiCollegati = new ArrayList();
        this.molteplicita = 0;
        this.precompilazione = "";
        this.marcatore_incrociato = "";
        this.azione = "";
        this.flg_precompilazione = "";
        // PC - nuovi campi href
        this.pattern = null;
        this.err_msg = null;
        // PC - nuovi campi href
        
        this.setCampoTitolare(null);

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getControllo() {
        return controllo;
    }

    public void setControllo(String controllo) {
        this.controllo = controllo;
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

    public int getNumCampo() {
        return numCampo;
    }

    public void setNumCampo(int numCampo) {
        this.numCampo = numCampo;
    }

    public ArrayList getOpzioniCombo() {
        return opzioniCombo;
    }

    public void setOpzioniCombo(ArrayList opzioniCombo) {
        this.opzioniCombo = opzioniCombo;
    }

    public void addOpzioniCombo(BaseBean opzioniCombo) {
        this.opzioniCombo.add(opzioniCombo);
    }

    public String getWeb_serv() {
        return web_serv;
    }

    public void setWeb_serv(String web_serv) {
        this.web_serv = web_serv;
    }

    public String getNome_xsd() {
        return nome_xsd;
    }

    public void setNome_xsd(String nome_xsd) {
        this.nome_xsd = nome_xsd;
    }

    public String getCampo_key() {
        return campo_key;
    }

    public void setCampo_key(String campo_key) {
        this.campo_key = campo_key;
    }

    public String getCampo_dati() {
        return campo_dati;
    }

    public void setCampo_dati(String campo_dati) {
        this.campo_dati = campo_dati;
    }

    public String getTp_controllo() {
        return tp_controllo;
    }

    public void setTp_controllo(String tp_controllo) {
        this.tp_controllo = tp_controllo;
    }

    public int getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    public int getDecimali() {
        return decimali;
    }

    public void setDecimali(int decimali) {
        this.decimali = decimali;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
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

    public String getRaggruppamento_check() {
        return raggruppamento_check;
    }

    public void setRaggruppamento_check(String raggruppamento_check) {
        this.raggruppamento_check = raggruppamento_check;
    }

    public String getCampo_collegato() {
        return campo_collegato;
    }

    public void setCampo_collegato(String campo_collegato) {
        this.campo_collegato = campo_collegato;
    }

    public String getVal_campo_collegato() {
        return val_campo_collegato;
    }

    public void setVal_campo_collegato(String val_campo_collegato) {
        this.val_campo_collegato = val_campo_collegato;
    }

    public ArrayList getCampiCollegati() {
        return campiCollegati;
    }

    public void setCampiCollegati(ArrayList campiCollegati) {
        this.campiCollegati = campiCollegati;
    }

//	public void addCampiCollegati(String[] campiCollegati) {
//		this.campiCollegati.add(campiCollegati);
//	}
    public int getMolteplicita() {
        return molteplicita;
    }

    public void setMolteplicita(int molteplicita) {
        this.molteplicita = molteplicita;
    }

    public String getPrecompilazione() {
        return precompilazione;
    }

    public void setPrecompilazione(String precompilazione) {
        this.precompilazione = precompilazione;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public String getMarcatore_incrociato() {
        return marcatore_incrociato;
    }

    public void setMarcatore_incrociato(String marcatore_incrociato) {
        this.marcatore_incrociato = marcatore_incrociato;
    }

    public String getAzione() {
        return azione;
    }

    public void setAzione(String azione) {
        this.azione = azione;
    }

    public String getFlg_precompilazione() {
        return flg_precompilazione;
    }

    public void setFlg_precompilazione(String flg_precompilazione) {
        this.flg_precompilazione = flg_precompilazione;
    }

    // PC - nuovi campi href
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }
    // PC - nuovi campi href

	public final String getCampoTitolare() {
		return campoTitolare;
	}

	public final void setCampoTitolare(String campoTitolare) {
		this.campoTitolare = campoTitolare;
	}
    
}
