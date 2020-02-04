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

 Licenza:	    Licenza Progetto PEOPLE
 Fornitore:    CEFRIEL
 Autori:       M. Pianciamore, P. Selvini

 Questo codice sorgente � protetto dalla licenza valida nell'ambito del
 progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
 esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
 sede legale in via Renato Fucini 2, 20133 Milano (MI).

 Disclaimer:

 COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
 WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
 LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
 FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
 QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
 CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
 OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
 CORRECTION.

 */

/*
 * Created on 13-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.oggetticondivisi.profili;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author max
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ProfiloPersonaFisica implements Serializable {

    protected String nome;
    protected String cognome;
    protected String codiceFiscale;
    protected String sesso;
    protected Date dataNascita;
    protected String dataNascitaString;
    protected String luogoNascita;
    protected String provinciaNascita;
    protected String indirizzoResidenza;
    // FIX 2007-06-13 - Aggiunto domicilio elettronico
    protected String domicilioElettronico;

    public static final String[] ACCEPTED_DATE_PATTERNS = { "dd/MM/yyyy",
	    "dd-MM-yyyy", "ddMMyyyy", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd",
	    "EEE MMM dd HH:mm:ss z yyyy" };
    public static final String DATE_PATTERN = "dd/MM/yyyy";

    /**
	 * 
	 */
    public ProfiloPersonaFisica() {
	super();
	clear();
    }

    /**
     * @param ppf
     */
    public ProfiloPersonaFisica(ProfiloPersonaFisica ppf) {
	this.nome = ppf.nome;
	this.cognome = ppf.cognome;
	this.codiceFiscale = ppf.codiceFiscale;
	this.sesso = ppf.sesso;
	this.dataNascita = ppf.dataNascita;
	this.dataNascitaString = ppf.dataNascitaString;
	this.luogoNascita = ppf.luogoNascita;
	this.provinciaNascita = ppf.provinciaNascita;
	this.indirizzoResidenza = ppf.indirizzoResidenza;
	this.domicilioElettronico = ppf.domicilioElettronico;
    }

    /*
     * public void initProfilo(ProfiloPersonaFisica initProfile) { nome =
     * initProfile.getNome(); cognome = initProfile.getCognome(); codiceFiscale
     * = initProfile.getCodiceFiscale(); sesso = initProfile.getSesso();
     * dataNascita = initProfile.getDataNascita(); luogoNascita =
     * initProfile.getLuogoNascita(); provinciaNascita=
     * initProfile.getProvinciaNascita(); indirizzoResidenza=
     * initProfile.getIndirizzoResidenza();
     * 
     * }
     */

    /**
	 * 
	 */
    public void clear() {
	nome = "";
	cognome = "";
	codiceFiscale = "";
	sesso = "";
	dataNascita = null;
	luogoNascita = "";
	provinciaNascita = "";
	indirizzoResidenza = "";
	domicilioElettronico = "";
    }

    /**
     * @return Returns the codiceFiscale.
     */
    public String getCodiceFiscale() {
	return codiceFiscale;
    }

    /**
     * @param codiceFiscale
     *            The codiceFiscale to set.
     */
    public void setCodiceFiscale(String codiceFiscale) {
	this.codiceFiscale = codiceFiscale;
    }

    /**
     * @return Returns the cognome.
     */
    public String getCognome() {
	return cognome;
    }

    /**
     * @param cognome
     *            The cognome to set.
     */
    public void setCognome(String cognome) {
	this.cognome = cognome;
    }

    /**
     * @return Returns the dataNascita.
     */
    public Date getDataNascita() {
	return dataNascita;
    }

    /**
     * @param dataNascita
     *            The dataNascita to set.
     */
    public void setDataNascita(Date dataNascita) {
	this.dataNascita = dataNascita;
    }

    /**
     * @return Returns the indirizzoResidenza.
     */
    public String getIndirizzoResidenza() {
	return indirizzoResidenza;
    }

    /**
     * @param indirizzoResidenza
     *            The indirizzoResidenza to set.
     */
    public void setIndirizzoResidenza(String indirizzoResidenza) {
	this.indirizzoResidenza = indirizzoResidenza;
    }

    /**
     * @return Returns the luogoNascita.
     */
    public String getLuogoNascita() {
	return luogoNascita;
    }

    /**
     * @param luogoNascita
     *            The luogoNascita to set.
     */
    public void setLuogoNascita(String luogoNascita) {
	this.luogoNascita = luogoNascita;
    }

    /**
     * @return Returns the nome.
     */
    public String getNome() {
	return nome;
    }

    /**
     * @param nome
     *            The nome to set.
     */
    public void setNome(String nome) {
	this.nome = nome;
    }

    /**
     * @return Returns the provinciaNascita.
     */
    public String getProvinciaNascita() {
	return provinciaNascita;
    }

    /**
     * @param provinciaNascita
     *            The provinciaNascita to set.
     */
    public void setProvinciaNascita(String provinciaNascita) {
	this.provinciaNascita = provinciaNascita;
    }

    /**
     * @return Returns the sesso.
     */
    public String getSesso() {
	return sesso;
    }

    /**
     * @param sesso
     *            The sesso to set.
     */
    public void setSesso(String sesso) {
	this.sesso = sesso;
    }

    /**
     * @return Returns the domicilioElettronico.
     */
    public String getDomicilioElettronico() {
	return domicilioElettronico;
    }

    /**
     * @param domicilioElettronico
     *            The domicilioElettronico to set.
     */
    public void setDomicilioElettronico(String domicilioElettronico) {
	this.domicilioElettronico = domicilioElettronico;
    }

    /**
     * @return Returns the dataNascitaString.
     */
    public String getDataNascitaString() {
	try {
	    dataNascitaString = formatDateToString(dataNascita);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    dataNascitaString = "";
	}
	return dataNascitaString;
    }

    /**
     * @param dataNascitaString
     *            The dataNascitaString to set.
     */
    public void setDataNascitaString(String dataNascitaString) throws Exception {
	this.dataNascitaString = dataNascitaString;
	Date dataNascitaParsed = null;
	try {
	    dataNascitaParsed = parseDateString(dataNascitaString);
	} catch (Exception e) {
	}
	setDataNascita(dataNascitaParsed);
    }

    /**
     * @param dateString
     * @return
     * @throws Exception
     */
    private Date parseDateString(String dateString) throws Exception {
	String[] acceptedPatterns = ACCEPTED_DATE_PATTERNS;
	Date tempDate = null;
	for (int i = 0; i < acceptedPatterns.length; i++) {
	    try {
		tempDate = parseDateString(dateString, acceptedPatterns[i]);
		if (tempDate != null) {
		    break;
		}
	    } catch (Exception e) {
		continue;
	    }
	}
	if (tempDate == null) {
	    throw new Exception(
		    "Unable to find suitable date pattern for the date string: "
			    + dateString);
	}

	return tempDate;
    }

    /**
     * @param dateString
     * @param datePattern
     * @return
     * @throws Exception
     */
    private Date parseDateString(String dateString, String datePattern)
	    throws Exception {
	Date parsedDate = null;
	if (isDate(dateString, datePattern)) {
	    SimpleDateFormat formatter = new SimpleDateFormat(datePattern,
		    Locale.ENGLISH);
	    parsedDate = formatter.parse(dateString);
	} else
	    throw new Exception("Invalid Date:" + dateString);
	return parsedDate;
    }

    /**
     * @param val
     * @param pattern
     * @return
     */
    private boolean isDate(String val, String pattern) {

	SimpleDateFormat formatter = new SimpleDateFormat(pattern,
		Locale.ENGLISH);
	formatter.setLenient(false);

	try {
	    formatter.parse(val);
	} catch (ParseException e) {
	    // System.out.println(e.toString());
	    return false;
	}

	return true;
    }

    /**
     * @param date
     * @return
     * @throws Exception
     */
    private static String formatDateToString(Date date) throws Exception {
	// if (date == null) return "";
	SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
	return formatter.format(date);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

	return new StringBuilder()
		.append("Nome: " + this.getNome())
		.append("Cognome: " + this.getCognome())
		.append("Codice fiscale: " + this.getCodiceFiscale())
		.append("Sesso: " + this.getSesso())
		.append("Data nascita: " + this.getDataNascitaString())
		.append("Luogo nascita: " + this.getLuogoNascita())
		.append("Provincia nascita: " + this.getProvinciaNascita())
		.append("Indirizzo residenza: " + this.getIndirizzoResidenza())
		.append("Domicilio elettronico: "
			+ this.getDomicilioElettronico()).toString();

    }

}
