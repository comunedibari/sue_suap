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
package it.people.console.jasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

import it.people.console.beans.support.JasperReportSettings;
import it.people.console.utils.Constants;
import it.people.console.utils.FoldersUtils;
import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/giu/2011 22.56.00
 *
 */
public class JasperReportView extends AbstractJasperReportsSingleFormatView implements IJasperReportView {
	
	private static Logger logger = LoggerFactory.getLogger(JasperReportView.class);

	public byte[] saveReport(String reportType, String reportName, JRDataSource jrDataSource, 
			String nodeId, String reportDomain, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		ClassPathResource classPathResource = new ClassPathResource(this.getReportPath(nodeId, 
				reportDomain, reportName));
		JasperReport jasperReport = this.loadReport(classPathResource);
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JasperPrint populatedReport = JasperFillManager.fillReport(jasperReport, parameters,
				jrDataSource);
		
		byte[] output;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
 
        JRExporter exporter = getExporter(reportType);
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, populatedReport);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
       
        exporter.exportReport();
 
        output = baos.toByteArray();
 
        return output;
	}
	
	
	public void renderReport(String reportType, String reportName, JRDataSource jrDataSource, 
			String nodeId, String reportDomain, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(this.getReportPath(nodeId, 
				reportDomain, reportName));
		JasperReport jasperReport = this.loadReport(classPathResource);
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		JasperPrint populatedReport = JasperFillManager.fillReport(jasperReport, parameters,
				jrDataSource);

		FoldersUtils foldersUtils = FoldersUtils.instance();
		File reportPreview = new File(foldersUtils.getSessionTemporaryRepositoryPath(request), 
				request.getSession().getId() + Constants.Reports.REPORT_PREVIEW_EXTENSION);
		if (reportPreview.exists()) {
			reportPreview.delete();
		}
		reportPreview.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(reportPreview);
		JasperReportsUtils.render(getExporter(reportType), populatedReport, fileOutputStream);
		fileOutputStream.close();
		if (logger.isDebugEnabled()) {
			logger.debug("Generated report preview = " + 
					foldersUtils.sanitazeHttpPath(foldersUtils.getRelativeSessionTemporaryRepositoryPath() 
					+ System.getProperty("file.separator") + request.getSession().getId() + 
					Constants.Reports.REPORT_PREVIEW_EXTENSION));
			logger.debug("Setting report preview session key.");
		}
		request.getSession().setAttribute(Constants.Reports.REPORT_FILE_PREVIEW_SESSION_KEY, 
				foldersUtils.sanitazeHttpPath(foldersUtils.getRelativeSessionTemporaryRepositoryPath() 
				+ System.getProperty("file.separator") + request.getSession().getId()
				+ System.getProperty("file.separator") + request.getSession().getId() + 
				Constants.Reports.REPORT_PREVIEW_EXTENSION));

	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView#renderReport(net.sf.jasperreports.engine.JasperPrint, java.util.Map, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderReport(JasperPrint populatedReport, Map<String, Object> model,
			HttpServletResponse response) throws Exception {
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView#createExporter()
	 */
	@Override
	protected JRExporter createExporter() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView#useWriter()
	 */
	@Override
	protected boolean useWriter() {
		return false;
	}

	private JRExporter getExporter(String reportType) {

		JRExporter result = null;
		
		if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.PDF_TYPE_KEY)) {
			result = new JRPdfExporter();
		}

		if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.CSV_TYPE_KEY)) {
			result = new JRCsvExporter();
		}
		
		if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.HTML_TYPE_KEY)) {
			result = new JRHtmlExporter();
			result.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, new Boolean(false)); 
		}
		
		if (reportType.equalsIgnoreCase(JasperReportSettings.ReportTypes.XLS_TYPE_KEY)) {
			result = new JExcelApiExporter();
		}
		
		return result;
		
	}
	
	private String getReportPath(String nodeId, String reportDomain, String reportName) {
		
		String result = 
			System.getProperty("file.separator") + Constants.Reports.REPORTS_BASE_PATH_1 + 
			System.getProperty("file.separator") + Constants.Reports.REPORTS_BASE_PATH_2 + 
			System.getProperty("file.separator");
		
		if (StringUtils.isEmptyString(nodeId)) {
			result += Constants.Reports.REPORTS_DEFAULT_NODE_PATH;
		} else {
			result += nodeId;
		}
		
		result += System.getProperty("file.separator") + reportDomain 
			+ System.getProperty("file.separator") + reportName + 
			Constants.Reports.NOT_COMPILED_REPORTS_EXTENSION;
		
		return result;
		
	}
	
}
