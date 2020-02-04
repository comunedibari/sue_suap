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
package it.people.fsl.servizi.oggetticondivisi.tipibase;

/*
 * Created on 4-giu-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author locastro
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import it.people.process.common.entity.AbstractEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Data extends AbstractEntity {

    public static final int ANNO = Calendar.YEAR;
    public static final int MESE = Calendar.MONTH;
    public static final int GIORNO = Calendar.DAY_OF_MONTH;
    public static final long MILLISECONDI_GIORNO = 86400000;
    public static final long MILLISECONDI_OFFSET = 43200000;
    public static final int HOUR_OFFSET = 12;
    public static final int MINUTE_OFFSET = 0;
    public static final int SECOND_OFFSET = 0;
    public static final int MILLISECOND_OFFSET = 0;
    public static final String[] ACCEPTED_DATE_PATTERNS = { "dd/MM/yyyy",
	    "dd-MM-yyyy", "ddMMyyyy", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd",
	    "EEE MMM dd HH:mm:ss z yyyy" };

    private GregorianCalendar m_calendar = new GregorianCalendar();

    private String marshallingYear;
    private String marshallingMonth;
    private String marshallingDay;

    public Data() {
	// costruttore necessario per il marshall
	m_calendar.set(GregorianCalendar.YEAR, 1900);
	m_calendar.set(GregorianCalendar.MONTH, 0);
	m_calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
    }

    /**
     * La data viene sempre impostata alle 12 del giorno passato nella data
     * argomento questa impostazione pu� essere comunque cambiata valorizzando
     * la costante MILLISECONDI_OFFSET (43200000=12 ore 0min 0sec 0
     * millisecondi)
     * 
     * @param data
     */
    public Data(Date data) {
	setData(data);
    }

    /**
     * 
     * @param pattern
     *            Il formato utilizzato � quello di java.text.SimpleDateFormat
     * @return
     */
    public Data(String data, String pattern) throws java.text.ParseException {
	setData(data, pattern);
    }

    /**
     * 
     * @param data
     *            Il formato atteso � "dd/MM/yyyy"
     * @throws java.text.ParseException
     */
    public Data(String data) throws java.text.ParseException {
	setData(data);
    }

    public void setData(Date data) {
	GregorianCalendar gcTemp = new GregorianCalendar();
	gcTemp.setTime(data);
	int anno = gcTemp.get(Calendar.YEAR);
	int mese = gcTemp.get(Calendar.MONTH);

	int giorno = gcTemp.get(Calendar.DAY_OF_MONTH);
	m_calendar.set(GregorianCalendar.YEAR, anno);
	m_calendar.set(GregorianCalendar.MONTH, mese);
	m_calendar.set(GregorianCalendar.DAY_OF_MONTH, giorno);
	m_calendar.set(GregorianCalendar.HOUR_OF_DAY, HOUR_OFFSET);
	m_calendar.set(GregorianCalendar.MINUTE, MINUTE_OFFSET);
	m_calendar.set(GregorianCalendar.SECOND, SECOND_OFFSET);
	m_calendar.set(GregorianCalendar.MILLISECOND, MILLISECOND_OFFSET);
    }

    /**
     * 
     * @param pattern
     *            Il formato utilizzato � quello di java.text.SimpleDateFormat
     * @return
     */
    public void setData(String data, String pattern)
	    throws java.text.ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	sdf.setLenient(false);
	setData(sdf.parse(data));

    }

    /**
     * 
     * @param data
     *            Il formato atteso � "dd/MM/yyyy"
     * @throws java.text.ParseException
     */
    public void setData(String data) throws java.text.ParseException {
	java.text.ParseException toThrow = null;
	for (String format : Data.ACCEPTED_DATE_PATTERNS) {
	    try {
		setData(data, format);
		toThrow = null;
		break;
	    } catch (java.text.ParseException e) {
		toThrow = e;
	    }
	}
	if (toThrow != null) {
	    throw toThrow;
	}
    }

    public String getAnno() {
	return String.valueOf(m_calendar.get(Calendar.YEAR));
    }

    public String getMese() {
	int month = m_calendar.get(Calendar.MONTH) + 1;
	return String.valueOf(month);
    }

    public String getGiorno() {
	return String.valueOf(m_calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setGiorno(String dd) {
	m_calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
    }

    public void setAnno(String yyyy) {
	m_calendar.set(Calendar.YEAR, Integer.parseInt(yyyy));
    }

    public void setMese(String MM) {
	m_calendar.set(Calendar.MONTH, Integer.parseInt(MM) - 1);
    }

    /**
     * Ritorna la data nel formato "dd/MM/yyyy"
     */
    public String toString() {
	return toString("dd/MM/yyyy");
    }

    /**
     * 
     * @param pattern
     *            Il formato utilizzato � quello di java.text.SimpleDateFormat
     * @return
     */
    public String toString(String pattern) {
	return (new SimpleDateFormat(pattern)).format(m_calendar.getTime());
    }

    public GregorianCalendar getCalendar() {
	return m_calendar;
    }

    public Date getDate() {
	return m_calendar.getTime();
    }

    /*******************************************************
     * Metodi per la serializzazione e deserializzazione Attenzione al fatto che
     * l'impostazione della data puo' essere fatta in modo atomico.
     ********************************************************/
    public String getMarshallingYear() {
	return String.valueOf(m_calendar.get(Calendar.YEAR));
    }

    public String getMarshallingMonth() {
	int month = m_calendar.get(Calendar.MONTH) + 1;
	return String.valueOf(month);
    }

    public String getMarshallingDay() {
	return String.valueOf(m_calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void setMarshallingDay(String day) {
	this.marshallingDay = day;
	initMarshallingDate();
    }

    public void setMarshallingMonth(String month) {
	this.marshallingMonth = month;
	initMarshallingDate();
    }

    public void setMarshallingYear(String year) {
	this.marshallingYear = year;
	initMarshallingDate();
    }

    protected void initMarshallingDate() {
	if (marshallingYear != null && marshallingMonth != null
		&& marshallingDay != null) {
	    // sono state impostate tutte le propriet� della data
	    this.setAnno(marshallingYear);
	    this.setMese(marshallingMonth);
	    this.setGiorno(marshallingDay);
	}
    }

    /*************************************************************************/

    /**
     * Aggiunge alla data la quantita di specificata nel parametro quantit�.
     * Se quantita < 0 effettua una sottrazione.
     * 
     * @param tipoQuantita
     *            Tipo della quantit�: pu� avere i valori: Data.ANNO
     *            Data.MESE Data.GIORNO
     * @param quantita
     *            Quantit&agrave; da sommare
     */

    public void aggiungi(int tipoQuantita, int quantita) {
	m_calendar.add(tipoQuantita, quantita);
    }

    /**
     * Restituisce la differenza in valore assoluto in termini di giornate tra
     * la data contenuta nell'oggetto e la data passata
     * 
     * @param data
     * @return
     */
    public long giorniDiDifferenza(Data data) {
	long millisecondi1 = this.getCalendar().getTimeInMillis();
	long millisecondi2 = data.getCalendar().getTimeInMillis();
	long risultato = millisecondi1 - millisecondi2;
	if (risultato < 0) {
	    risultato = -risultato;
	}
	return risultato / MILLISECONDI_GIORNO;
    }

    public boolean maggioreDi(Data data) {
	return m_calendar.after(data.getCalendar());
    }

    public boolean minoreDi(Data data) {
	return m_calendar.before(data.getCalendar());
    }

    public static void main(String[] args) {
	Data data;
	try {
	    data = new Data("1963-01-05 00:00:00.0");
	    System.out.println(data.toString());
	} catch (ParseException e) {
	    e.printStackTrace();
	}

    }

}
