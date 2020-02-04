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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Procedimento {
    public Procedimento() {

    }
    
    private String codiceProcedimento;
    private String codiceClasseEnte;
    private String flagComune;
    private String codiceCud;
    private String codiceEnte;
    private String codiceSportello;
    private String flagPu;
    private String flagProcedimento;
    private String terminiEvasione;
    
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * @return Returns the codiceClasseEnte.
     */
    public String getCodiceClasseEnte() {
        return codiceClasseEnte;
    }

    /**
     * @param codiceClasseEnte The codiceClasseEnte to set.
     */
    public void setCodiceClasseEnte(String codiceClasseEnte) {
        this.codiceClasseEnte = codiceClasseEnte;
    }

    /**
     * @return Returns the codiceCud.
     */
    public String getCodiceCud() {
        return codiceCud;
    }

    /**
     * @param codiceCud The codiceCud to set.
     */
    public void setCodiceCud(String codiceCud) {
        this.codiceCud = codiceCud;
    }

    /**
     * @return Returns the codiceEnte.
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * @param codiceEnte The codiceEnte to set.
     */
    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    /**
     * @return Returns the codiceProcedimento.
     */
    public String getCodiceProcedimento() {
        return codiceProcedimento;
    }

    /**
     * @param codiceProcedimento The codiceProcedimento to set.
     */
    public void setCodiceProcedimento(String codiceProcedimento) {
        this.codiceProcedimento = codiceProcedimento;
    }

    /**
     * @return Returns the codiceSportello.
     */
    public String getCodiceSportello() {
        return codiceSportello;
    }

    /**
     * @param codiceSportello The codiceSportello to set.
     */
    public void setCodiceSportello(String codiceSportello) {
        this.codiceSportello = codiceSportello;
    }

    /**
     * @return Returns the flagComune.
     */
    public String getFlagComune() {
        return flagComune;
    }

    /**
     * @param flagComune The flagComune to set.
     */
    public void setFlagComune(String flagComune) {
        this.flagComune = flagComune;
    }

    /**
     * @return Returns the flagProcedimento.
     */
    public String getFlagProcedimento() {
        return flagProcedimento;
    }

    /**
     * @param flagProcedimento The flagProcedimento to set.
     */
    public void setFlagProcedimento(String flagProcedimento) {
        this.flagProcedimento = flagProcedimento;
    }

    /**
     * @return Returns the flagPu.
     */
    public String getFlagPu() {
        return flagPu;
    }

    /**
     * @param flagPu The flagPu to set.
     */
    public void setFlagPu(String flagPu) {
        this.flagPu = flagPu;
    }

    /**
     * @return Returns the terminiEvasione.
     */
    public String getTerminiEvasione() {
        return terminiEvasione;
    }

    /**
     * @param terminiEvasione The terminiEvasione to set.
     */
    public void setTerminiEvasione(String terminiEvasione) {
        this.terminiEvasione = terminiEvasione;
    }
}
