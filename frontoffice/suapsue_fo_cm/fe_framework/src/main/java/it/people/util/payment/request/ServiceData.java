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
/*
 * Created on 20-apr-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.util.payment.request;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceData {
    protected String idServizio;
    protected String datiSpecifici;
    protected String numeroDocumento;
    protected String annoDocumento;
    protected String descrizione;

    public ServiceData(String idServizio, String datiSpecifici,
	    String annoDocumento, String numeroDocumento, String descrizione) {
	this.idServizio = idServizio;
	this.datiSpecifici = datiSpecifici;
	this.annoDocumento = annoDocumento;
	this.numeroDocumento = numeroDocumento;
	this.descrizione = descrizione;
    }

    public ServiceData() {
    }

    /**
     * @return Returns the annoDocumento.
     */
    public String getAnnoDocumento() {
	return annoDocumento;
    }

    /**
     * @param annoDocumento
     *            The annoDocumento to set.
     */
    public void setAnnoDocumento(String annoDocumento) {
	this.annoDocumento = annoDocumento;
    }

    /**
     * @return Returns the datiSpecifici.
     */
    public String getDatiSpecifici() {
	return datiSpecifici;
    }

    /**
     * Dati specifici che saranno registrati nei flussi di rendicontazione e
     * serviranno ad identificare il servizio. Pu� essere usato un qualunque
     * testo anche in formato XML, un esempio di dati specifici potrebbe essere
     * la serializzazione della ProcessData del servizio stesso, pu� essere
     * anche un sottinsieme pi� limitato di informazioni.
     * 
     * @param datiSpecifici
     *            I Dati Specifici da impostare.
     */
    public void setDatiSpecifici(String datiSpecifici) {
	this.datiSpecifici = datiSpecifici;
    }

    /**
     * @return Returns the idServizio.
     */
    public String getIdServizio() {
	return idServizio;
    }

    /**
     * Identificativo del servizio utilizzato dal MIP
     * 
     * @param idServizio
     *            I'identificativo del servizio da impostare.
     */
    public void setIdServizio(String idServizio) {
	this.idServizio = idServizio;
    }

    /**
     * @return Returns the numeroDocumento.
     */
    public String getNumeroDocumento() {
	return numeroDocumento;
    }

    /**
     * @param numeroDocumento
     *            The numeroDocumento to set.
     */
    public void setNumeroDocumento(String numeroDocumento) {
	this.numeroDocumento = numeroDocumento;
    }

    public String getDescrizione() {
	return descrizione;
    }

    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }
}
