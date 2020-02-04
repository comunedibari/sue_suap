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

import it.people.process.common.entity.AbstractEntity;

public class Comune extends AbstractEntity {

    public Comune() {
	this.provincia = new Provincia();
    }

    /**
     * 
     * @return possible object is {@link java.math.BigInteger}
     */
    private Provincia provincia;

    /**
     * 
     * @return possible object is
     *         {@link it.diviana.egov.oggetticondivisiegovluogo.Comune.CodiceType}
     */
    private int codice;

    /**
     * 
     * @return possible object is {@link java.lang.String}
     */
    private java.lang.String nome;

    /**
     * @return
     */
    public int getCodice() {
	return codice;
    }

    /**
     * @return
     */
    public java.lang.String getNome() {
	return nome;
    }

    /**
     * @return
     */
    public Provincia getProvincia() {
	return provincia;
    }

    /**
     * @param i
     */
    public void setCodice(int i) {
	codice = i;
    }

    /**
     * @param string
     */
    public void setNome(java.lang.String string) {
	nome = string;
    }

    /**
     * @param provincia
     */
    public void setProvincia(Provincia provincia) {
	this.provincia = provincia;
    }

}
