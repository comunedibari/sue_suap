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
 * IdentificatoreUnivoco.java
 *
 * Created on 15 febbraio 2005, 12.50
 */

package it.people.util;

import it.people.process.common.entity.AbstractEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author manelli
 */
public class IdentificatoreUnivoco extends AbstractEntity {
    private String codiceProgetto;
    private Date dataRegistrazione;
    private CodiceSistema codiceSistema;

    /** Creates a new instance of IdentificatoreUnivoco */
    public IdentificatoreUnivoco() {
	this.codiceSistema = new CodiceSistema();
    }

    /**
     * Costruttore dell'Identificatore Univoco
     * 
     * @param peopleId
     *            identificatore della pratica people
     * @param codiceEnte
     *            identificatore dell'Ente
     */
    public IdentificatoreUnivoco(String peopleId, String codiceEnte) {
	this();
	codiceSistema.setCodiceAmministrazione(codiceEnte);
	codiceSistema.setCodiceIdentificativoOperazione(peopleId);

	try {
	    codiceSistema.setNomeServer(InetAddress.getLocalHost()
		    .getHostAddress());
	} catch (UnknownHostException e) {
	}

	this.dataRegistrazione = Calendar.getInstance().getTime();
	codiceProgetto = (String) PeopleProperties.CODICE_PROGETTO
		.getValue(codiceEnte);
    }

    public class CodiceSistema extends AbstractEntity {
	private String nomeServer;
	private String codiceAmministrazione;
	private String codiceIdentificativoOperazione;

	public CodiceSistema() {
	}

	public String getNomeServer() {
	    return nomeServer;
	}

	public void setNomeServer(String nomeServer) {
	    this.nomeServer = nomeServer;
	}

	public String getCodiceAmministrazione() {
	    return codiceAmministrazione;
	}

	public void setCodiceAmministrazione(String codiceAmministrazione) {
	    this.codiceAmministrazione = codiceAmministrazione;
	}

	public String getCodiceIdentificativoOperazione() {
	    return codiceIdentificativoOperazione;
	}

	public void setCodiceIdentificativoOperazione(
		String codiceIdentificativoOperazione) {
	    this.codiceIdentificativoOperazione = codiceIdentificativoOperazione;
	}
    }

    public String getCodiceProgetto() {
	return codiceProgetto;
    }

    public void setCodiceProgetto(String codiceProgetto) {
	this.codiceProgetto = codiceProgetto;
    }

    /**
     * Ritorna la data di registrazione
     * 
     * @return Date contenente la data di registrazione
     */
    public Date getDataRegistrazione() {
	return this.dataRegistrazione;
    }

    /**
     * Imposta la data di registrazione, � valorizzato direttamente dal
     * framework
     * 
     * @param date
     */
    public void setDataRegistrazione(Date date) {
	this.dataRegistrazione = date;
    }

    /**
     * Ritorna la data di registrazione nel formato indicato in
     * PeopleProperties.FORMATO_DATA
     * 
     * @return stringa con la data di registrazione
     * @deprecated utilizzare getDataRegistrazione()
     */
    public String getDataDiRegistrazione() {
	DateFormat dateFormat = new SimpleDateFormat(
		(String) PeopleProperties.FORMATO_DATA.getValue());
	return dateFormat.format(this.dataRegistrazione);
    }

    /**
     * Imposta la data di sistema, � valorizzato dal framework
     * 
     * @param dataDiRegistrazione
     *            nel formato indicato nella PeopleProperties
     *            PeopleProperties.FORMATO_DATA
     * @deprecated utilizzare getDataRegistrazione()
     */
    public void setDataDiRegistrazione(String dataDiRegistrazione) {
	DateFormat dateFormat = new SimpleDateFormat(
		(String) PeopleProperties.FORMATO_DATA.getValue());
	try {
	    this.dataRegistrazione = dateFormat.parse(dataDiRegistrazione);
	} catch (ParseException pEx) {
	    // In caso di data non valida non viene modificato il precedente
	    // valore
	}
    }

    /**
     * Ritorna il codice sistema
     * 
     * @return
     */
    public CodiceSistema getCodiceSistema() {
	return codiceSistema;
    }

    /**
     * Imposta il codice sistema, � valorizzato dal framework
     * 
     * @param codiceSistema
     */
    public void setCodiceSistema(CodiceSistema codiceSistema) {
	this.codiceSistema = codiceSistema;
    }

    // Propriet� per il marshalling del codice sistema
    // L'attuale versione di betwixt non riesce a deserializzare
    // la inner class CodiceSistema
    public String getMarshallingNomeServer() {
	return this.codiceSistema.nomeServer;
    }

    public void setMarshallingNomeServer(String nomeServer) {
	this.codiceSistema.nomeServer = nomeServer;
    }

    public String getMarshallingCodiceAmministrazione() {
	return this.codiceSistema.codiceAmministrazione;
    }

    public void setMarshallingCodiceAmministrazione(String codiceAmministrazione) {
	this.codiceSistema.codiceAmministrazione = codiceAmministrazione;
    }

    public String getMarshallingCodiceIdentificativoOperazione() {
	return this.codiceSistema.codiceIdentificativoOperazione;
    }

    public void setMarshallingCodiceIdentificativoOperazione(
	    String codiceIdentificativoOperazione) {
	this.codiceSistema.codiceIdentificativoOperazione = codiceIdentificativoOperazione;
    }
}
