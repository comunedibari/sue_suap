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
package it.people.console.beans.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.people.console.domain.PairElement;
import it.people.console.system.MessageSourceAwareClass;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/giu/2011 22.03.46
 *
 */
public class JasperReportSettings extends MessageSourceAwareClass {
	
	private Collection<PairElement<String, String>> reportTypes = null;

	private static Map<String, String> reportTypesLabels = new HashMap<String, String>();
	
	static {
		reportTypesLabels.put(ReportTypes.CSV_TYPE_KEY, "jasper.report.csv.label");
		reportTypesLabels.put(ReportTypes.HTML_TYPE_KEY, "jasper.report.html.label");
		reportTypesLabels.put(ReportTypes.PDF_TYPE_KEY, "jasper.report.pdf.label");
		reportTypesLabels.put(ReportTypes.XLS_TYPE_KEY, "jasper.report.xls.label");
	}
	
	public JasperReportSettings() {
		this.setReportTypes(initReportTypes());
	}

	/**
	 * @return the reportTypes
	 */
	public final Collection<PairElement<String, String>> getReportTypes() {
		return reportTypes;
	}

	/**
	 * @param reportTypes the reportTypes to set
	 */
	private void setReportTypes(
			Collection<PairElement<String, String>> reportTypes) {
		this.reportTypes = reportTypes;
	}

	protected Collection<PairElement<String, String>> initReportTypes() {
		
		Collection<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>(ReportTypes.PDF_TYPE_KEY, 
				clearValue(this.getProperty(reportTypesLabels.get(ReportTypes.PDF_TYPE_KEY)))));
		result.add(new PairElement<String, String>(ReportTypes.HTML_TYPE_KEY, 
				clearValue(this.getProperty(reportTypesLabels.get(ReportTypes.HTML_TYPE_KEY)))));
		result.add(new PairElement<String, String>(ReportTypes.CSV_TYPE_KEY, 
				clearValue(this.getProperty(reportTypesLabels.get(ReportTypes.CSV_TYPE_KEY)))));
		result.add(new PairElement<String, String>(ReportTypes.XLS_TYPE_KEY, 
				clearValue(this.getProperty(reportTypesLabels.get(ReportTypes.XLS_TYPE_KEY)))));
		
		return result;
		
	}

	/**
	 * @param value
	 * @return
	 */
	private String clearValue(String value) {
		return value.replace("\r", "").replace("\t", "\t").trim();
	}
	
	public final class ReportTypes {
		
		public static final String CSV_TYPE_KEY = "CSV";

		public static final String HTML_TYPE_KEY = "HTML";

		public static final String PDF_TYPE_KEY = "PDF";

		public static final String XLS_TYPE_KEY = "XLS";
		
	}
	
}
