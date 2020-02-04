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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class RiepilogoImporti 
{
	private long m_lImportoTotale;
	private long m_lImportoBollo;

	// Consuntivo spese rate scelte
	public String getImportoTotaleFmt() { return formatCurrencyEURO(m_lImportoTotale, 1); }
	public long getImportoTotale() { return m_lImportoTotale; }
	public void setImportoTotale(long lImportoTotale) { m_lImportoTotale = lImportoTotale; }

	// spesa di bollo applicata
	public String getImportoBolloFmt() { return formatCurrencyEURO(m_lImportoBollo, 1); }
	public long getImportoBollo() { return m_lImportoBollo; }
	public void setImportoBollo(long lImportoBollo) { m_lImportoBollo = lImportoBollo; }

	public RiepilogoImporti()
	{
		m_lImportoTotale = 0;
		m_lImportoBollo = 0;
	}

	private String formatCurrencyEURO(long lAmount){ 
		return formatCurrency(lAmount, "###,##0.00");
	}

	private String formatCurrencyEURO(long lAmount, int iType){
		return "&euro;&nbsp;" + formatCurrency(lAmount, "###,##0.00");
	}

	private String formatCurrency(long lAmount, String pattern)
	{
		double amount = (double)lAmount / 100;
		Locale locale = Locale.ITALIAN;
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		//NumberFormat nf = NumberFormat.getCurrencyInstance();
		DecimalFormat df = (DecimalFormat)nf;
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);
		df.applyPattern(pattern);
      
		return df.format(amount);
	}
}
