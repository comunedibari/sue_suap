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

public class DatiAziendaNoProfitDeleganteBean extends PersonaBean 
{
    public DatiAziendaNoProfitDeleganteBean()
    {
        denominazione="";
        sede=new SedeBean();
        codiceMotivazioneRappresentanza="";;
        descrizioneMotivazioneRappresentanza="";
        altraMotivazione="";
        regioneRegistro="";
        numeroRegistro="";
    }
    
    private String denominazione;
    private SedeBean sede;
    private String codiceMotivazioneRappresentanza;
    private String descrizioneMotivazioneRappresentanza;
    private String altraMotivazione;
    private String regioneRegistro;
    private String numeroRegistro;
    
    /**
     * @return Returns the denominazione.
     */
    public String getDenominazione() {
        return denominazione;
    }
    /**
     * @param denominazione The denominazione to set.
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
    /**
     * @return Returns the sede.
     */
    public SedeBean getSede() {
        return sede;
    }
    /**
     * @param sede The sede to set.
     */
    public void setSede(SedeBean sede) {
        this.sede = sede;
    }
    public String getAltraMotivazione() {
        return altraMotivazione;
    }
    public void setAltraMotivazione(String altraMotivazione) {
        this.altraMotivazione = altraMotivazione;
    }
    public String getCodiceMotivazioneRappresentanza() {
        return codiceMotivazioneRappresentanza;
    }
    public void setCodiceMotivazioneRappresentanza(String codiceMotivazioneRappresentanza) {
        this.codiceMotivazioneRappresentanza = codiceMotivazioneRappresentanza;
    }
    public String getDescrizioneMotivazioneRappresentanza() {
        return descrizioneMotivazioneRappresentanza;
    }
    public void setDescrizioneMotivazioneRappresentanza(String descrizioneMotivazioneRappresentanza) {
        this.descrizioneMotivazioneRappresentanza = descrizioneMotivazioneRappresentanza;
    }
    public String getNumeroRegistro() {
        return numeroRegistro;
    }
    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }
    public String getRegioneRegistro() {
        return regioneRegistro;
    }
    public void setRegioneRegistro(String regioneRegistro) {
        this.regioneRegistro = regioneRegistro;
    }
}
