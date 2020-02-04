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

public class DatiPersonaDeleganteBean extends PersonaBean 
{
    public DatiPersonaDeleganteBean()
    {
        codiceMotivazioneRappresentanza = "";
        descrizioneMotivazioneRappresentanza = "";
        altraMotivazione = "";
    }
    
    private String codiceMotivazioneRappresentanza;
    private String descrizioneMotivazioneRappresentanza;
    private String altraMotivazione;
    
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
}
