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
package it.people.fsl.servizi.oggetticondivisi.luogo;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 * 
 */
public class IndirizzoEstero extends Indirizzo {

    private String nazioneCodiceIstat;
    private String nazioneDescrizione;
    private String capEstero;

    public IndirizzoEstero() {

    }

    /**
     * @return the nazioneCodiceIstat
     */
    public String getNazioneCodiceIstat() {
	return nazioneCodiceIstat;
    }

    /**
     * @param nazioneCodiceIstat
     *            the nazioneCodiceIstat to set
     */
    public void setNazioneCodiceIstat(String nazioneCodiceIstat) {
	this.nazioneCodiceIstat = nazioneCodiceIstat;
    }

    /**
     * @return the nazioneDescrizione
     */
    public String getNazioneDescrizione() {
	return nazioneDescrizione;
    }

    /**
     * @param nazioneDescrizione
     *            the nazioneDescrizione to set
     */
    public void setNazioneDescrizione(String nazioneDescrizione) {
	this.nazioneDescrizione = nazioneDescrizione;
    }

    /**
     * @return the capEstero
     */
    public String getCapEstero() {
	return capEstero;
    }

    /**
     * @param capEstero
     *            the capEstero to set
     */
    public void setCapEstero(String capEstero) {
	this.capEstero = capEstero;
    }

}
