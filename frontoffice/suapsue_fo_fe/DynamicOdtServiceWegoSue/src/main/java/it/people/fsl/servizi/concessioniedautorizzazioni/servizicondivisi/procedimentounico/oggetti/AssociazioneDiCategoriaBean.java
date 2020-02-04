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

public class AssociazioneDiCategoriaBean extends BaseBean 
{
    public AssociazioneDiCategoriaBean()
    {
        denominazione = "";
        codiceSportello = "";
        email = "";
        codiceControllo = "";
        abilitato = false;
    }
  
    private String denominazione;
    private String codiceSportello;
    private String email;
    private String codiceControllo;
    private boolean abilitato;
    
    public String getCodiceControllo() {
        return codiceControllo;
    }
    public void setCodiceControllo(String codiceControllo) {
        this.codiceControllo = codiceControllo;
    }
    public String getCodiceSportello() {
        return codiceSportello;
    }
    public void setCodiceSportello(String codiceSportello) {
        this.codiceSportello = codiceSportello;
    }
    public String getDenominazione() {
        return denominazione;
    }
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isAbilitato() {
        return abilitato;
    }
    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }
}
