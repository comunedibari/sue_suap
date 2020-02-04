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

public class AllegatoBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = -5898275115360458307L;
    private String nomeFile;
    //private String codiceIntervento;
    private String flagAutocertificazione;
    private String copie;
    private String codiceCondizione;
    private String flagDic;
    private String href;
    private String testoCondizione;
    private String titolo;
    private String codiceNormativaVisulizzata;
    private boolean checked;
    private boolean initialized;
    // private Set allegatiConDescUguale;
    private boolean inserito;
    private boolean daEliminare;
    private boolean flg_obb;
    private String tipologieAllegati;
    private String num_max_pag;
    private String dimensione_max;
    // PC - ordinamento allegati
    private Integer ordinamento;
    private Integer ordinamentoIntervento;
    private boolean indicatoreOptionAllegati;
    // PC - ordinamento allegati
    private boolean signVerify;

    public AllegatoBean() {
        this.nomeFile = "";
    	//this.codiceIntervento = "";
        this.flagAutocertificazione = "";
        this.copie = "";
        this.codiceCondizione = "";
        this.flagDic = "";
        this.href = "";
        this.testoCondizione = "";
        this.titolo = "";
        this.codiceNormativaVisulizzata = "";
        this.checked = false;
        this.inserito = false;
        this.daEliminare = false;

        this.flg_obb = false;
        this.tipologieAllegati = null;
        this.num_max_pag = null;
        this.dimensione_max = null;
        // PC - ordinamento allegati
        this.ordinamento = 0;
        this.ordinamentoIntervento = 0;
        // PC - ordinamento allegati
        this.indicatoreOptionAllegati = false;
        
        this.setSignVerify(false);
        
        this.setInitialized(false);
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }
// PC - ordinamento allegati

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public Integer getOrdinamentoIntervento() {
        return ordinamentoIntervento;
    }

    public void setOrdinamentoIntervento(Integer ordinamentoIntervento) {
        this.ordinamentoIntervento = ordinamentoIntervento;
    }
// PC - ordinamento allegati

    public String getFlagAutocertificazione() {
        return flagAutocertificazione;
    }

    public void setFlagAutocertificazione(String flagAutocertificazione) {
        this.flagAutocertificazione = flagAutocertificazione;
    }

    public String getCopie() {
        return copie;
    }

    public void setCopie(String copie) {
        this.copie = copie;
    }

    public String getCodiceCondizione() {
        return codiceCondizione;
    }

    public void setCodiceCondizione(String codiceCondizione) {
        this.codiceCondizione = codiceCondizione;
    }

    public String getFlagDic() {
        return flagDic;
    }

    public void setFlagDic(String flagDic) {
        this.flagDic = flagDic;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTestoCondizione() {
        return testoCondizione;
    }

    public void setTestoCondizione(String testoCondizione) {
        this.testoCondizione = testoCondizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isInserito() {
        return inserito;
    }

    public void setInserito(boolean inserito) {
        this.inserito = inserito;
    }

    public boolean isDaEliminare() {
        return daEliminare;
    }

    public void setDaEliminare(boolean daEliminare) {
        this.daEliminare = daEliminare;
    }

    public String getCodiceNormativaVisulizzata() {
        return codiceNormativaVisulizzata;
    }

    public void setCodiceNormativaVisulizzata(String codiceNormativaVisulizzata) {
        this.codiceNormativaVisulizzata = codiceNormativaVisulizzata;
    }

    public boolean isFlg_obb() {
        return flg_obb;
    }

    public void setFlg_obb(boolean flg_obb) {
        this.flg_obb = flg_obb;
    }

    public String getTipologieAllegati() {
        return tipologieAllegati;
    }

    public void setTipologieAllegati(String tipologieAllegati) {
        this.tipologieAllegati = tipologieAllegati;
    }

    public String getNum_max_pag() {
        return num_max_pag;
    }

    public void setNum_max_pag(String num_max_pag) {
        this.num_max_pag = num_max_pag;
    }

    public String getDimensione_max() {
        return dimensione_max;
    }

    public void setDimensione_max(String dimensione_max) {
        this.dimensione_max = dimensione_max;
    }

    public boolean isIndicatoreOptionAllegati() {
        return indicatoreOptionAllegati;
    }

    public void setIndicatoreOptionAllegati(boolean indicatoreOptionAllegati) {
        this.indicatoreOptionAllegati = indicatoreOptionAllegati;
    }

	public final boolean isSignVerify() {
		return signVerify;
	}

	public final void setSignVerify(boolean signVerify) {
		this.signVerify = signVerify;
	}

	/**
	 * @return the initialized
	 */
	public final boolean isInitialized() {
		return initialized;
	}

	/**
	 * @param initialized the initialized to set
	 */
	public final void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	
}
