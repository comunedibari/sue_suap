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
package it.people.fsl.servizi.admin.sirac.accreditamento.dto;

import it.people.process.dto.PeopleDto;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.Qualifica;

public class OperatoreDto extends PeopleDto {

    /** l'utente people */
    private String codiceFiscale;

    
    /** l'id del comune presso cui vale questo accreditamento
     *  (lo stesso utente pu� accreditarsi presso diversi comuni, e un <i>Sirac</i>
     * 	pu� gestire pi� comuni) 
     */
    private String idComune;

    /** la qualifica */
    private Qualifica qualifica;

    /** id univoco */
    private int id;
    
    /** attivo/non attivo */
    private boolean attivo;
    
    /** eliminato/non eliminato */
    private boolean deleted;
    
    /** il profilo di accreditamento */
    private ProfiloAccreditamento profilo;

	public OperatoreDto() {
	}

	
	/**
     * @return la qualifica.
     */
    public Qualifica getQualifica() {
        return qualifica;
    }
    
    /**
     * @param qualifica la qualifica
     */
    public void setQualifica(Qualifica qualifica) {
        this.qualifica = qualifica;
    }
    
    /**
     * @return restituisce il codiceFiscale.
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    
    /**
     * @param codiceFiscale il codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
    
    /**
     * @return l'id del comune
     */
    public String getIdComune() {
        return idComune;
    }
    
    /**
     * @param idComune l'id del comune
     */
    public void setIdComune(String idComune) {
        this.idComune = idComune;
    }
    
    /**
     * @return Returns the profilo.
     */
    public ProfiloAccreditamento getProfilo() {
        return profilo;
    }
    
    /**
     * @param profilo The profilo to set.
     */
    public void setProfilo(ProfiloAccreditamento profilo) {
        this.profilo = profilo;
    }
    
    /**
     * @return attivo.
     */
    public boolean isAttivo() {
        return attivo;
    }
    
    /**
     * @param attivo modifica il campo attivo
     */
    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }
    
    /**
     * @return deleted.
     */
    public boolean isDeleted() {
    	return deleted;
    }
    
    /**
     * @param attivo modifica il campo deleted
     */
    public void setDeleted(boolean deleted) {
    	this.deleted = deleted;
    }
    
    /**
     * @return id.
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id modifica il campo id
     */
    public void setId(int id) {
        this.id = id;
    }
    
	/**
	 * Metodo pubblico che copia nel dto i dati dell'accreditamento aggiungendo il valore della propriet� deleted
	 * @param accreditamento
	 * @param deleted
	 */
	public void accreditamentoToOperatore(Accreditamento accreditamento,
			boolean deleted) {
		
		setCodiceFiscale(accreditamento.getCodiceFiscale());
	    setIdComune(accreditamento.getIdComune());
	    setQualifica(accreditamento.getQualifica());
	    setId(accreditamento.getId());
	    setAttivo(accreditamento.isAttivo());
	    setDeleted(deleted);
	}
}
