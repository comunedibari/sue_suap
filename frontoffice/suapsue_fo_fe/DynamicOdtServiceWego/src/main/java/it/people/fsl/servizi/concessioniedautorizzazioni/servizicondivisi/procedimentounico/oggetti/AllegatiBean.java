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

import java.util.Set;
import java.util.TreeSet;

public class AllegatiBean extends BaseBean {
    public AllegatiBean() {
        allegatiConDescUguale = new TreeSet(new AllegatiBeanComparatorCodice());
        inserito = false;
    }
    
    private String nomeFile;
    private String codiceIntervento;
    private String flagAutocertificazione;
    private String copie;
    private String codiceCondizione;
    private String flagDic;
    private String href;
    private String testoCondizione;
    private String titolo;
    private boolean checked;
    private Set allegatiConDescUguale;
    private boolean inserito;
    private boolean daEliminare;

    public void setCodiceIntervento(String codiceIntervento) {
        this.codiceIntervento = codiceIntervento;
    }

    public String getCodiceIntervento() {
        return codiceIntervento;
    }

    public void setFlagAutocertificazione(String flagAutocertificazione) {
        this.flagAutocertificazione = flagAutocertificazione;
    }

    public String getFlagAutocertificazione() {
        return flagAutocertificazione;
    }

    public void setCopie(String copie) {
        this.copie = copie;
    }

    public String getCopie() {
        return copie;
    }

    /**
     * @return Returns the codiceCondizione.
     */
    public String getCodiceCondizione() {
        return codiceCondizione;
    }

    /**
     * @param codiceCondizione
     *            The codiceCondizione to set.
     */
    public void setCodiceCondizione(String codiceCondizione) {
        this.codiceCondizione = codiceCondizione;
    }
    
    /**
     * @return Returns the nomeFile.
     */
    public String getNomeFile() {
        return nomeFile;
    }
    /**
     * @param nomeFile The nomeFile to set.
     */
    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }
    /**
     * @return Returns the flagDic.
     */
    public String getFlagDic() {
        return flagDic;
    }
    /**
     * @param flagDic The flagDic to set.
     */
    public void setFlagDic(String flagDic) {
        this.flagDic = flagDic;
    }
    /**
     * @return Returns the href.
     */
    public String getHref() {
        return href;
    }
    /**
     * @param href The href to set.
     */
    public void setHref(String href) {
        this.href = href;
    }
    /**
     * @return Returns the testoCondizione.
     */
    public String getTestoCondizione() {
        return testoCondizione;
    }
    /**
     * @param testoCondizione The testoCondizione to set.
     */
    public void setTestoCondizione(String testoCondizione) {
        this.testoCondizione = testoCondizione;
    }
    /**
     * @return Returns the titolo.
     */
    public String getTitolo() {
        return titolo;
    }
    /**
     * @param titolo The titolo to set.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    /**
     * @return Returns the checked.
     */
    public boolean isChecked() {
        return checked;
    }
    /**
     * @param checked The checked to set.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    /**
     * @return Returns the allegatiConDescUguale.
     */
    public Set getAllegatiConDescUguale() {
        return allegatiConDescUguale;
    }
    /**
     * @param allegatiConDescUguale The allegatiConDescUguale to set.
     */
    public void setAllegatiConDescUguale(Set allegatiConDescUguale) {
        this.allegatiConDescUguale = allegatiConDescUguale;
    }
    
    public void addAllegatiConDescUguale(AllegatiBean bean) {
        try {
            allegatiConDescUguale.add(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return Returns the inserito.
     */
    public boolean isInserito() {
        return inserito;
    }
    /**
     * @param inserito The inserito to set.
     */
    public void setInserito(boolean inserito) {
        this.inserito = inserito;
    }

    /**
     * @return Returns the daEliminare.
     */
    public boolean isDaEliminare() {
        return daEliminare;
    }

    /**
     * @param daEliminare The daEliminare to set.
     */
    public void setDaEliminare(boolean daEliminare) {
        this.daEliminare = daEliminare;
    }
}
